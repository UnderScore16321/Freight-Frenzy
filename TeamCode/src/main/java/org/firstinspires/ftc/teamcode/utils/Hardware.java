package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Bitmap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    protected LinearOpMode opMode;

    public Hardware(LinearOpMode opMode, boolean camera) throws InterruptedException {
        this.opMode = opMode;

        initWheels();
        initSpinner();
        initGrabber();
        initImu();
        if (camera) initCamera();
    }

    // Wheels --------------------------------------------------------------------------------------

    protected DcMotorEx leftFront;
    protected DcMotorEx leftBack;
    protected DcMotorEx rightFront;
    protected DcMotorEx rightBack;

    private void initWheels() {
        leftFront = (DcMotorEx) opMode.hardwareMap.dcMotor.get("front_left");
        leftBack = (DcMotorEx) opMode.hardwareMap.dcMotor.get("back_left");
        rightFront = (DcMotorEx) opMode.hardwareMap.dcMotor.get("front_right");
        rightBack = (DcMotorEx) opMode.hardwareMap.dcMotor.get("back_right");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Sets the power of the left and right wheels respectively.
     *
     * @param left  The power of the left wheels.
     * @param right The power of the right wheels.
     */
    public void setWheelPower(double left, double right) {
        leftFront.setPower(left);
        leftBack.setPower(left);
        rightFront.setPower(right);
        rightBack.setPower(right);
    }

    /**
     * Sets all four wheels to the same power.
     *
     * @param power The power to set the wheels to.
     */
    public void setWheelPower(double power) {
        setWheelPower(power, power);
    }

    public double leftFrontVelocity() {
        return leftFront.getVelocity();
    }

    // Spinner -------------------------------------------------------------------------------------

    protected DcMotor spinner;

    private void initSpinner() {
        spinner = opMode.hardwareMap.dcMotor.get("duck_spinner");
    }

    private static final double SPINNER_MAX_POWER = 0.7;

    /**
     * Sets the spinner to spin at a percentage of max speed for the given side.
     * @param power The percentage of max speed.
     * @param side The side we are trying to spin.
     */
    public void setSpinnerPower(double power, Side side) {
        spinner.setPower(power * SPINNER_MAX_POWER * (side == Side.RED ? -1 : 1));
    }


    // Grabber -------------------------------------------------------------------------------------

    protected DcMotor grabberMotor;
    protected Servo leftGrabber;
    protected Servo rightGrabber;

    private void initGrabber() throws InterruptedException {
        leftGrabber = opMode.hardwareMap.servo.get("left_grabber");
        rightGrabber = opMode.hardwareMap.servo.get("right_grabber");
        grabberMotor = opMode.hardwareMap.dcMotor.get("arm");
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberMotor.setPower(1);
        setGrabberHeight(GrabberHeight.DOWN);
        grabberMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        grabberMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Attempts to fix a mismatch between the grabber motors expected position and its actual one.
     * If the motor is initialized when it is not in the down position there will be such a mismatch.
     * This tries to return the motor to a down position. Control will be lost for half a second.
     *
     * @throws InterruptedException
     */
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

    private static final double RIGHT_CLOSED = 0.78;
    private static final double RIGHT_OPEN = 0.65;
    private static final double RIGHT_STOWED = 0.9;
    private static final double LEFT_CLOSED = 0.62;
    private static final double LEFT_OPEN = 0.75;
    private static final double LEFT_STOWED = 0.5;

    /**
     * Opens and closes the grabber.
     *
     * @param isOpen If the grabber should be open
     */
    public void setGrabberIsOpen(boolean isOpen) throws InterruptedException {
        grabberIsOpen = isOpen;
        if (isOpen) {
            leftGrabber.setPosition(LEFT_OPEN);
            rightGrabber.setPosition(RIGHT_OPEN);
        } else {
            leftGrabber.setPosition(LEFT_CLOSED);
            rightGrabber.setPosition(RIGHT_CLOSED);
        }
    }

    public void stowGrabbers() {
        leftGrabber.setPosition(LEFT_STOWED);
        rightGrabber.setPosition(RIGHT_STOWED);
    }

    /**
     * Switches the grabber between the opened and closed states.
     */
    public void toggleGrabberState() throws InterruptedException {
        setGrabberIsOpen(!grabberIsOpen);
    }

    /**
     * Returns if the grabber is open.
     */
    public boolean getGrabberIsOpen() {
        return grabberIsOpen;
    }

    /**
     * A height that the grabber can be set at.
     */
    public enum GrabberHeight {
        STOWED(580),
        THIRD_LEVEL(400),
        SECOND_LEVEL(245),
        FIRST_LEVEL(120),
        DOWN(0);

        private final int inTicks;

        GrabberHeight(int inTicks) {
            this.inTicks = inTicks;
        }

        /**
         * The grabber motor tick position relative to down as zero that this height is at.
         */
        public int getInTicks() {
            return inTicks;
        }

        /**
         * The GrabberHeight above this one. If there is no height above it returns this.
         */
        public GrabberHeight above() {
            if (this == THIRD_LEVEL) return THIRD_LEVEL;
            if (this == SECOND_LEVEL) return THIRD_LEVEL;
            if (this == FIRST_LEVEL) return SECOND_LEVEL;
            return FIRST_LEVEL;
        }

        /**
         * The GrabberHeight below this one. If there is no height below it returns this.
         */
        public GrabberHeight below() {
            if (this == THIRD_LEVEL) return SECOND_LEVEL;
            if (this == SECOND_LEVEL) return FIRST_LEVEL;
            if (this == FIRST_LEVEL) return DOWN;
            return DOWN;
        }
    }

    private GrabberHeight currentGrabberHeight;
    private int grabberOffset = 0;

    /**
     * Attempts to move the motor to, and maintain it at, the given height.
     *
     * @param position The height to go to.
     */
    public void setGrabberHeight(GrabberHeight position) throws InterruptedException {
        currentGrabberHeight = position;
        setGrabberTargetPosition();
    }

    /**
     * Lowers the motor to the height below this one. If there is no height below then the motor
     * maintains its current height.
     */
    public void lowerGrabberHeight() throws InterruptedException {
        setGrabberHeight(currentGrabberHeight.below());
    }

    /**
     * Raises the motor to the height above this one. If there is no height above the motor
     * maintains its current height.
     */
    public void raiseGrabberHeight() throws InterruptedException {
        setGrabberHeight(currentGrabberHeight.above());
    }

    /**
     * Sets a number of ticks that the motor should be offset from its current height.
     *
     * @param offset The number of ticks.
     */
    public void setGrabberOffset(int offset) throws InterruptedException {
        grabberOffset = offset;
        setGrabberTargetPosition();
    }

    protected void setGrabberTargetPosition() throws InterruptedException {
        grabberMotor.setTargetPosition(-currentGrabberHeight.getInTicks() - grabberOffset);
    }

    /**
     * Returns the grabber height we believe we are at.
     */
    public GrabberHeight getGrabberHeight() {
        return currentGrabberHeight;
    }

    /**
     * Returns the current grabber offset.
     */
    public int getGrabberOffset() {
        return grabberOffset;
    }

    // Gyro ----------------------------------------------------------------------------------------

    protected BNO055IMU imu;

    private void initImu() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public boolean imuCalibrated() {
        return imu.isGyroCalibrated();
    }

    public void waitForImuCalibration(boolean debug) throws InterruptedException {
        if (debug) {
            opMode.telemetry.addData("imu", "calibrating");
            opMode.telemetry.update();
        }
        while (!opMode.isStopRequested() && !imuCalibrated()) {
            Thread.sleep(100);
        }
        if (debug) {
            opMode.telemetry.addData("imu", "calibrated");
            opMode.telemetry.update();
        }
    }

    /**
     * The robots heading from 0 to 360 degrees in the clockwise direction.
     */
    public double robotHeading() {
        float negToPosHeading = -imu.getAngularOrientation().firstAngle;
        if (negToPosHeading < 0) negToPosHeading += 360;
        return negToPosHeading;
    }

    // Camera --------------------------------------------------------------------------------------

    protected SimpleCamera camera;

    private void initCamera() {
        camera = new SimpleCamera("camera", opMode);
    }

    public Bitmap getLatestFrame() {
        if (camera == null) return null;
        return camera.getLatestFrame();
    }

    public Bitmap forceGetFrame() throws InterruptedException {
        if (camera == null) return null;
        while (true) {
            Bitmap frame = camera.getLatestFrame();
            if (frame != null) return frame;
            Thread.sleep(100);
        }
    }

    public boolean isCameraStarted() {
        return camera.isCameraStarted();
    }

    public void waitForCameraStart() throws InterruptedException {
        while (!isCameraStarted()) {
            Thread.sleep(100);
        }
    }

    // ---------------------------------------------------------------------------------------------

    public void close() {
        if (camera != null) camera.closeCamera();
    }

}
