package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Side;

/**
 * A base class for opmodes. It provides access to the teleop hardware and advances gamepads.
 */
public abstract class TeleopBase extends LinearOpMode {
    protected TeleopHardware hw;
    protected AdvancedGamepad aGamepad1;
    protected AdvancedGamepad aGamepad2;

    @Override
    public final void runOpMode() throws InterruptedException {
        hw = new TeleopHardware(this);
        aGamepad1 = new AdvancedGamepad(gamepad1);
        aGamepad2 = new AdvancedGamepad(gamepad2);
        opModeCode();
    }

    /**
     * The actual code to run the opmode.
     */
    abstract protected void opModeCode() throws InterruptedException;

    abstract protected Side getSide();
}
