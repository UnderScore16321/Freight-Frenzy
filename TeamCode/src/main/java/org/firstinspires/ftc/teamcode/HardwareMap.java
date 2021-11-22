package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareMap extends Hardware {

    public HardwareMap(LinearOpMode opMode, boolean camera) {
        super(opMode, camera);
        initGrabber();
    }

    public HardwareMap(LinearOpMode opMode) {
        this(opMode, false);
    }

    // Camera --------------------------------------------------------------------------------------

    public Bitmap getLatestFrame() {
        return camera != null ? camera.getLatestFrame() : null;
    }

    public Bitmap forceGetFrame() throws InterruptedException {
        do {
            Bitmap frame = getLatestFrame();
            if (frame != null) return frame;
            Thread.sleep(100);
        } while (true);
    }

    public void closeCamera() {
        if(camera != null) {
            camera.closeCamera();
        }
    }

    // Wheels --------------------------------------------------------------------------------------

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

    private static final double WHEEL_MAX_POWER = 0.75;

    public void setWheelPower(double left, double right) {
        double leftSquared = left * left * left * WHEEL_MAX_POWER;
        leftFront.setPower(leftSquared);
        leftBack.setPower(leftSquared);
        double rightSquared = right * right * right * WHEEL_MAX_POWER;
        rightFront.setPower(rightSquared);
        rightBack.setPower(rightSquared);
    }

    public void setWheelPower(double power) {
        setWheelPower(power, power);
    }

    // Spinner -------------------------------------------------------------------------------------

    private static final double SPINNER_MAX_POWER = 0.5;

    public void setSpinnerPower(double power) {
        spinner.setPower(power * SPINNER_MAX_POWER);
    }

    // Grabber -------------------------------------------------------------------------------------

    private void initGrabber() {
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberMotor.setPower(1);
        setGrabberHeight(GrabberHeight.DOWN);
        grabberMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetGrabberEncoder() throws InterruptedException {
        grabberMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        grabberMotor.setPower(.5);
        Thread.sleep(500);
        grabberMotor.setPower(0);
        Thread.sleep(50);
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setGrabberHeight(GrabberHeight.DOWN);
        grabberMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabberMotor.setPower(1);
    }

    private boolean grabberIsOpen = false;

    private static final double RIGHT_CLOSED = 0.62;
    private static final double RIGHT_OPEN = 0.5;
    private static final double LEFT_CLOSED = 0.78;
    private static final double LEFT_OPEN = 0.9;

    public void setGrabberIsOpen(boolean isOpen) {
        grabberIsOpen = isOpen;
        if (isOpen) {
            leftGrabber.setPosition(LEFT_OPEN);
            rightGrabber.setPosition(RIGHT_OPEN);
        } else {
            leftGrabber.setPosition(LEFT_CLOSED);
            rightGrabber.setPosition(RIGHT_CLOSED);
        }
    }

    public void toggleGrabberState() {
        setGrabberIsOpen(!grabberIsOpen);
    }

    public boolean getGrabberIsOpen() {
        return grabberIsOpen;
    }

    public void waitForGrabbing() throws InterruptedException {
        Thread.sleep(300);
    }

    public enum GrabberHeight {
        SECOND_LEVEL(280),
        FIRST_LEVEL(100),
        DOWN(0);

        private final int inTicks;

        GrabberHeight(int inTicks) {
            this.inTicks = inTicks;
        }

        public int getInTicks() {
            return inTicks;
        }

        public GrabberHeight above() {
            if (this == SECOND_LEVEL) {
                return SECOND_LEVEL;
            } else if (this == FIRST_LEVEL) {
                return SECOND_LEVEL;
            } else {
                return FIRST_LEVEL;
            }
        }

        public GrabberHeight below() {
            if (this == SECOND_LEVEL) {
                return FIRST_LEVEL;
            } else if (this == FIRST_LEVEL) {
                return DOWN;
            } else {
                return DOWN;
            }
        }
    }

    private GrabberHeight currentGrabberHeight;
    private int grabberOffset = 0;

    public void setGrabberHeight(GrabberHeight position) {
        currentGrabberHeight = position;
        setGrabberTargetPosition();
    }

    public void lowerGrabberHeight() {
        setGrabberHeight(currentGrabberHeight.below());
    }

    public void raiseGrabberHeight() {
        setGrabberHeight(currentGrabberHeight.above());
    }

    public void setGrabberOffset(int offset) {
        grabberOffset = offset;
        setGrabberTargetPosition();
    }

    public void waitForGrabber() throws InterruptedException {
        while (grabberMotor.isBusy()) {
            Thread.sleep(100);
        }
    }

    private void setGrabberTargetPosition() {
        grabberMotor.setTargetPosition(-currentGrabberHeight.getInTicks() - grabberOffset);
    }

    public GrabberHeight getCurrentGrabberHeight() {
        return currentGrabberHeight;
    }

    public void displayGrabberTelemetry() {
        opMode.telemetry.addData("motor", grabberMotor.getCurrentPosition());
        opMode.telemetry.addData("target pos", grabberMotor.getTargetPosition());
        opMode.telemetry.addData("l servo", leftGrabber.getPosition());
        opMode.telemetry.addData("r servo", rightGrabber.getPosition());
        opMode.telemetry.update();
    }
}
