package org.firstinspires.ftc.teamcode.teleops.tank_drive;

import org.firstinspires.ftc.teamcode.teleops.TeleopBase;

abstract class TankDriveBase extends TeleopBase {

    @Override
    final protected void opModeCode() throws InterruptedException {
        hw.setGrabberIsOpen(false);

        waitForStart();

        while (opModeIsActive()) {
            if (aGamepad2.xPressed()) hw.resetGrabberEncoder();

            // drive
            hw.setAdjustedWheelPower(aGamepad1.leftY(), aGamepad1.rightY());

            // duck spinner
            hw.setSpinnerPower(Math.max(aGamepad1.leftY(), 0), getSide());

            // grabber
            if (aGamepad2.rightBumperPressed()) hw.toggleGrabberState();
            if (aGamepad2.dpadUpPressed()) hw.raiseGrabberHeight();
            if (aGamepad2.dpadDownPressed()) hw.lowerGrabberHeight();
            hw.setGrabberOffset((int) (aGamepad2.leftTrigger() * 50));
        }
    }

}
