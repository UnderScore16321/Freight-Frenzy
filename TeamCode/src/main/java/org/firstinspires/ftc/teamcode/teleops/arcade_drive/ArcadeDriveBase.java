package org.firstinspires.ftc.teamcode.teleops.arcade_drive;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.teleops.TeleopBase;

abstract public class ArcadeDriveBase extends TeleopBase {
    @Override
    final protected void opModeCode() throws InterruptedException {
        hw.setGrabberIsOpen(false);

        waitForStart();

        while (opModeIsActive()) {
            if (aGamepad2.xPressed()) hw.resetGrabberEncoder();

            // drive
            float left = Range.clip(aGamepad1.leftY() + aGamepad1.leftX(), -1, 1);
            float right = Range.clip(aGamepad1.leftY() - aGamepad1.leftX(), -1, 1);
            hw.setAdjustedWheelPower(left, right);

            // duck spinner
            hw.setSpinnerPower(Math.max(-gamepad2.left_stick_y, 0), getSide());

            // grabber
            if (aGamepad2.rightBumperPressed()) hw.toggleGrabberState();
            if (aGamepad2.dpadUpPressed()) hw.raiseGrabberHeight();
            if (aGamepad2.dpadDownPressed()) hw.lowerGrabberHeight();
            hw.setGrabberOffset((int) (gamepad2.left_trigger * 50));
        }
    }
}
