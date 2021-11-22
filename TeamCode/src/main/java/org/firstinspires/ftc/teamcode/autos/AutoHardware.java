package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

public class AutoHardware extends Hardware {
    AutoHardware(LinearOpMode opMode, boolean camera) throws InterruptedException {
        super(opMode, camera);
    }

    // Driving -------------------------------------------------------------------------------------

    private void initWheels() {

    }

    private static final double TICKS_PER_ROT = 537.6;
    private static final double WHEEL_DIAMETER = 5;
    private static final double INCHES_PER_ROT = 3.14159 * WHEEL_DIAMETER;
    private static final double TICKS_PER_INCH = TICKS_PER_ROT / INCHES_PER_ROT;

    public void driveInches(double inches) throws InterruptedException {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllTargetPosition(-inchesToTicks(inches));
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        waitForEncoders();
    }

    private static final double TICKS_PER_DEGREE = 5.85;

    public void turnDegrees(double degrees) throws InterruptedException {
        setWheelPower(0.75);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int ticks = (int) (degrees * TICKS_PER_DEGREE);
        setTargetPositions(-ticks, ticks);
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);

        waitForEncoders();
    }

    public boolean isEncoderDriving() {
        return leftBack.isBusy() || leftFront.isBusy() || rightBack.isBusy() || rightFront.isBusy();
    }

    public void waitForEncoders() throws InterruptedException {
        while (isEncoderDriving()) {
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
        opMode.telemetry.addData("left front", leftFront.getCurrentPosition());
        opMode.telemetry.addData("left back", leftBack.getCurrentPosition());
        opMode.telemetry.addData("right front", rightFront.getCurrentPosition());
        opMode.telemetry.addData("right back", rightBack.getCurrentPosition());
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
