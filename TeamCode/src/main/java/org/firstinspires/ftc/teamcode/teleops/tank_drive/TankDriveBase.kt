package org.firstinspires.ftc.teamcode.teleops.tank_drive

import org.firstinspires.ftc.teamcode.utils.Side
import org.firstinspires.ftc.teamcode.teleops.TeleopBase

abstract class TankDriveBase : TeleopBase() {

    abstract val side: Side

    override fun beforeStart() {
        hw.grabberIsOpen = true
    }

    override fun mainOpMode() {
        while (opModeIsActive()) {
            if (aGamepad2.x) hw.resetGrabberEncoder()

            // drive
            hw.setAdjustedWheelPower(aGamepad1.leftY, aGamepad1.rightY)

            // duck spinner
            hw.setSpinnerPower(aGamepad2.leftY.coerceAtLeast(0.0) , side)

            // grabber
            if (aGamepad2.rightBumperPressed) hw.toggleGrabberState()
            if (aGamepad2.dpadUpPressed) hw.raiseGrabberHeight()
            if (aGamepad2.dpadDownPressed) hw.lowerGrabberHeight()
            hw.grabberOffset = (aGamepad2.leftTrigger * 50).toInt()
        }
    }

}