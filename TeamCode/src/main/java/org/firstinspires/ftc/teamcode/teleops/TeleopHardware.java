package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.Side;

/**
 * Stores all the hardware for the team robot with an interface geared toward teleops.
 */
public class TeleopHardware extends Hardware {
    public TeleopHardware(LinearOpMode opMode) {
        super(opMode, false);
    }

    // Wheels --------------------------------------------------------------------------------------

    /**
     * Sets the power of the left and right wheels respectively.
     * @param left The power of the left wheels.
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
     * @param power The power to set the wheels to.
     */
    public void setWheelPower(double power) {
        setWheelPower(power, power);
    }

    private static final double WHEEL_MAX_POWER = 0.75;

    /**
     * Sets the power of the wheels but with an adjustment designed to make the robot easier to
     * control. The wheels are capped at 75% power and the inputs are squared to allow finner
     * control at low powers.
     * @param left The input for the left wheels.
     * @param right The input for the right wheels.
     */
    public void setAdjustedWheelPower(double left, double right) {
        double adjustedLeft = Math.copySign(left * left * WHEEL_MAX_POWER, left);
        double adjustedRight = Math.copySign(right * right * WHEEL_MAX_POWER, right);
        setWheelPower(adjustedLeft, adjustedRight);
    }

    // Spinner -------------------------------------------------------------------------------------

    private static final double SPINNER_MAX_POWER = 0.5;

    /**
     * Sets the spinner to spin at a percentage of max speed for the given side.
     * @param power The percentage of max speed.
     * @param side The side we are trying to spin.
     */
    public void setSpinnerPower(double power, Side side) {
        spinner.setPower(power * SPINNER_MAX_POWER * (side == Side.RED ? -1 : 1));
    }

    // Grabber -------------------------------------------------------------------------------------

    private void initGrabber() {
        grabberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberMotor.setPower(1);
        setGrabberHeight(GrabberHeight.DOWN);
        grabberMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Attempts to fix a mismatch between the grabber motors expected position and its actual one.
     * If the motor is initialized when it is not in the down position there will be such a mismatch.
     * This tries to return the motor to a down position. Control will be lost for half a second.
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

    private static final double RIGHT_CLOSED = 0.62;
    private static final double RIGHT_OPEN = 0.5;
    private static final double LEFT_CLOSED = 0.78;
    private static final double LEFT_OPEN = 0.9;

    /**
     * Opens and closes the grabber.
     * @param isOpen If the grabber should be open
     */
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

    /**
     * Switches the grabber between the opened and closed states.
     */
    public void toggleGrabberState() {
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
        SECOND_LEVEL(280),
        FIRST_LEVEL(100),
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
            if (this == SECOND_LEVEL) {
                return SECOND_LEVEL;
            } else if (this == FIRST_LEVEL) {
                return SECOND_LEVEL;
            } else {
                return FIRST_LEVEL;
            }
        }

        /**
         * The GrabberHeight below this one. If there is no height below it returns this.
         */
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

    /**
     * Attempts to move the motor to, and maintain it at, the given height.
     * @param position The height to go to.
     */
    public void setGrabberHeight(GrabberHeight position) {
        currentGrabberHeight = position;
        setGrabberTargetPosition();
    }

    /**
     * Lowers the motor to the height below this one. If there is no height below then the motor
     * maintains its current height.
     */
    public void lowerGrabberHeight() {
        setGrabberHeight(currentGrabberHeight.below());
    }

    /**
     * Raises the motor to the height above this one. If there is no height above the motor
     * maintains its current height.
     */
    public void raiseGrabberHeight() {
        setGrabberHeight(currentGrabberHeight.above());
    }

    /**
     * Sets a number of ticks that the motor should be offset from its current height.
     * @param offset The number of ticks.
     */
    public void setGrabberOffset(int offset) {
        grabberOffset = offset;
        setGrabberTargetPosition();
    }

    private void setGrabberTargetPosition() {
        grabberMotor.setTargetPosition(-currentGrabberHeight.getInTicks() - grabberOffset);
    }

    /**
     * Returns the grabber height we believe we are at.
     */
    public GrabberHeight getCurrentGrabberHeight() {
        return currentGrabberHeight;
    }

}
