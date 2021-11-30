package org.firstinspires.ftc.teamcode.teleops.arcade_drive

import org.firstinspires.ftc.teamcode.utils.Side
import org.firstinspires.ftc.teamcode.teleops.TeleopBase

abstract class ArcadeDriveBase : TeleopBase() {
    abstract val side: Side

    override fun beforeStart() {
        hw.grabberIsOpen = false
    }

    override fun mainOpMode() {
        while (opModeIsActive()) {
            if (aGamepad2.xPressed) hw.resetGrabberEncoder()

            // drive
            hw.setAdjustedWheelPower(
                (aGamepad1.leftY + aGamepad1.leftX).coerceIn(-1.0, 1.0),
                (aGamepad1.leftY - aGamepad1.leftX).coerceIn(-1.0, 1.0)
            )

            // duck spinner
            hw.setSpinnerPower((-aGamepad2.leftY).coerceAtLeast(0.0), side)

            // grabber
            if (aGamepad2.rightBumperPressed) hw.toggleGrabberState()
            if (aGamepad2.dpadUpPressed) hw.raiseGrabberHeight()
            if (aGamepad2.dpadDownPressed) hw.lowerGrabberHeight()
            hw.grabberOffset = (gamepad2.left_trigger * 50).toInt()
        }
    }
}