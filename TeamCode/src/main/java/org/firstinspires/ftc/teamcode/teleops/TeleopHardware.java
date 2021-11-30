package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Hardware;
import org.firstinspires.ftc.teamcode.utils.Side;

/**
 * Stores all the hardware for the team robot with an interface geared toward teleops.
 */
public class TeleopHardware extends Hardware {
    public TeleopHardware(LinearOpMode opMode) throws InterruptedException {
        super(opMode, false);
    }

    // Wheels --------------------------------------------------------------------------------------

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

}
