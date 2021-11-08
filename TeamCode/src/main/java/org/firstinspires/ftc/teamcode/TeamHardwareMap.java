package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class TeamHardwareMap {
    private LinearOpMode opMode;

    public TeamHardwareMap(LinearOpMode opMode) {
        this.opMode = opMode;

        initWheels();
        initSpinner();
        initGrabber();
    }

    // Wheels --------------------------------------------------------------------------------------

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    private void initWheels() {
        leftFront = opMode.hardwareMap.dcMotor.get("left front");
        leftBack = opMode.hardwareMap.dcMotor.get("left back");
        rightFront = opMode.hardwareMap.dcMotor.get("right front");
        rightBack = opMode.hardwareMap.dcMotor.get("right back");
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        setAllMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private static final double TICKS_PER_ROT = 537.6;
    private static final double WHEEL_DIAMETER = 5;
    private static final double INCHES_PER_ROT = 3.14159 * WHEEL_DIAMETER;
    private static final double TICKS_PER_INCH = TICKS_PER_ROT / INCHES_PER_ROT;

    public void driveInches(double inches) {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllTargetPosition(-inchesToTicks(inches));
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private static final double TICKS_PER_DEGREE = 6.17;

    public void turnDegrees(double degrees) {
        setWheelPower(1);
        setAllMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int ticks = (int) (degrees * TICKS_PER_DEGREE);
        setTargetPositions(ticks, -ticks);
        setAllMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean isEncoderDriving() {
        return leftBack.isBusy() || leftFront.isBusy() || rightBack.isBusy() || rightFront.isBusy();
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

    private DcMotor spinner;

    private void initSpinner() {
        spinner = opMode.hardwareMap.dcMotor.get("spinner");
    }

    private static final double SPINNER_MAX_POWER = 0.5;

    public void setSpinnerPower(double power) {
        spinner.setPower(power * SPINNER_MAX_POWER);
    }

    // Grabber -------------------------------------------------------------------------------------

    private DcMotor grabberMotor;
    private Servo leftGrabber;
    private Servo rightGrabber;

    private void initGrabber() {
        grabberMotor = opMode.hardwareMap.dcMotor.get("grabber motor");
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberMotor.setPower(0.5);
        setGrabberHeight(GrabberHeight.UP);
        leftGrabber = opMode.hardwareMap.servo.get("left grabber");
        rightGrabber = opMode.hardwareMap.servo.get("right grabber");
    }

    private boolean grabberIsOpen = false;

    private static final double LEFT_OPEN = 0;
    private static final double RIGHT_OPEN = 1;
    private static final double LEFT_CLOSED = 1;
    private static final double RIGHT_CLOSED = 0;

    public void setGrabberIsOpen(boolean isOpen) {
        grabberIsOpen = isOpen;
        if (isOpen) {
            leftGrabber.setPosition(LEFT_OPEN);
            rightGrabber.setPosition(RIGHT_OPEN);
        } else {
            leftGrabber.setPosition(LEFT_CLOSED);
            leftGrabber.setPosition(RIGHT_CLOSED);
        }
    }

    public void toggleGrabberState() {
        setGrabberIsOpen(!grabberIsOpen);
    }

    public boolean getGrabberIsOpen() {
        return grabberIsOpen;
    }

    public enum GrabberHeight {
        UP(0),
        DOWN(200);

        private final int inTicks;

        GrabberHeight(int inTicks) {
            this.inTicks = inTicks;
        }

        public int getInTicks() {
            return inTicks;
        }
    }

    private GrabberHeight currentGrabberHeight;

    public void setGrabberHeight(GrabberHeight position) {
        currentGrabberHeight = position;
        grabberMotor.setTargetPosition(position.getInTicks());
    }

    public GrabberHeight getCurrentGrabberHeight() {
        return currentGrabberHeight;
    }
}
