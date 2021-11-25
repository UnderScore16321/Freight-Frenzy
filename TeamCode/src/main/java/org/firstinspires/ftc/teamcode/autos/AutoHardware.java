package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware;

public class AutoHardware extends Hardware {
    public AutoHardware(LinearOpMode opMode, boolean camera) throws InterruptedException {
        super(opMode, camera);
        initWheels();
    }

    // Driving -------------------------------------------------------------------------------------

    private void initWheels() {
        leftFront.setTargetPositionTolerance(15);
        leftBack.setTargetPositionTolerance(15);
        rightFront.setTargetPositionTolerance(15);
        rightBack.setTargetPositionTolerance(15);
    }

    public void setVelocity(double speed) {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setVelocity(speed);
        leftBack.setVelocity(speed);
        rightFront.setVelocity(speed);
        rightBack.setVelocity(speed);
    }

    private static final double TICKS_PER_ROT = 537.6;
    private static final double WHEEL_DIAMETER = 5;
    private static final double INCHES_PER_ROT = 3.14159 * WHEEL_DIAMETER;
    private static final double TICKS_PER_INCH = TICKS_PER_ROT / INCHES_PER_ROT;

    public void driveInches(double inches) throws InterruptedException {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllTargetPosition(inchesToTicks(inches));
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        waitForEncoders();
    }

    // -----

//    private static final double TICKS_PER_DEGREE = 5.85;
    private static final double TICKS_PER_DEGREE = 5.27;

    public void turnDegrees(double degrees) throws InterruptedException {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int ticks = (int) (degrees * TICKS_PER_DEGREE);
        setTargetPositions(ticks, -ticks);
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        waitForEncoders();
    }

    public void goToHeading(double angle) throws InterruptedException {
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetPositionBasedOnHeading(angle);
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        int correctCount = 0;
        while (!opMode.isStopRequested() && correctCount < 20) {
            double error = headingError(angle);
            setWheelPower(Range.clip(Math.abs(error) / 180.0, 0.08, 1.0) * 0.6);
            setTargetPositionBasedOnHeading(angle);
//            Thread.sleep(10);
            if (Math.abs(headingError(angle)) < 1) {
                correctCount++;
            } else {
                correctCount = 0;
            }
        }
    }

    private void setTargetPositionBasedOnHeading(double angle) {
        double error = headingError(angle);
        if (error < 0) error += 360;
        if (error > 180) error -= 360;
        int ticks = (int) (error * TICKS_PER_DEGREE);
//        setTargetPositions(ticks, -ticks);
        leftFront.setTargetPosition(leftFront.getCurrentPosition() + ticks);
        leftBack.setTargetPosition(leftBack.getCurrentPosition() + ticks);
        rightFront.setTargetPosition(rightFront.getCurrentPosition() - ticks);
        rightBack.setTargetPosition(rightBack.getCurrentPosition() - ticks);
        opMode.telemetry.addData("ticks", ticks);
        opMode.telemetry.addData("heading", robotHeading());
        opMode.telemetry.addData("error", error);
        opMode.telemetry.addData("lf t", leftFront.getTargetPosition());
        opMode.telemetry.addData("lf a", leftFront.getCurrentPosition());
        opMode.telemetry.update();

    }

    public double headingError(double targetAngle) {
        return (targetAngle - robotHeading()) % 360;
    }

    // -----

    public boolean isEncoderDriving() {
        return leftBack.isBusy() || leftFront.isBusy() || rightBack.isBusy() || rightFront.isBusy();
    }

    public void waitForEncoders() throws InterruptedException {
        while (isEncoderDriving()) {
            displayWheelTelemetry();
            Thread.sleep(100);
        }
    }

    private int inchesToTicks(double inches) {
        return (int) (inches * TICKS_PER_INCH);
    }

    private void setTargetPositions(int lTicks, int rTicks) {
        leftFront.setTargetPosition(lTicks);
        leftBack.setTargetPosition(lTicks);
        rightFront.setTargetPosition(rTicks);
        rightBack.setTargetPosition(rTicks);
    }

    private void setAllTargetPosition(int ticks) {
        setTargetPositions(ticks, ticks);
    }

    private void setAllMotorModes(DcMotor.RunMode mode) {
        leftFront.setMode(mode);
        leftBack.setMode(mode);
        rightFront.setMode(mode);
        rightBack.setMode(mode);
    }

    public void displayWheelTelemetry() {
        opMode.telemetry.addData("left front", leftFront.getTargetPosition() - leftFront.getCurrentPosition());
        opMode.telemetry.addData("left back", leftBack.getTargetPosition() - leftBack.getCurrentPosition());
        opMode.telemetry.addData("right front", rightFront.getTargetPosition() - rightFront.getCurrentPosition());
        opMode.telemetry.addData("right back", rightBack.getTargetPosition() - rightBack.getCurrentPosition());
        opMode.telemetry.update();
    }

    // Grabber -------------------------------------------------------------------------------------

    @Override
    public void setGrabberIsOpen(boolean isOpen) throws InterruptedException {
        super.setGrabberIsOpen(isOpen);
        Thread.sleep(300);
    }

    @Override
    protected void setGrabberTargetPosition() throws InterruptedException {
        super.setGrabberTargetPosition();
        while (grabberMotor.isBusy()) {
            Thread.sleep(100);
        }
    }

}
