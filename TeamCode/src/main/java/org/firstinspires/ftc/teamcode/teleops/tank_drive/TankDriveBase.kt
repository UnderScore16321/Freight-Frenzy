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
            if (aGamepad1.leftBumper) {
                hw.setAdjustedWheelPower(1.0 + aGamepad1.rightTrigger, 1.0 + aGamepad1.rightTrigger)
            } else if (aGamepad1.rightBumper) {
                hw.setAdjustedWheelPower(-1.0, -1.0)
            } else {
                hw.setAdjustedWheelPower(aGamepad1.leftY, aGamepad1.rightY)
            }

            // duck spinner
            hw.setSpinnerPower(if (aGamepad2.leftBumper) 0.88 else 0.0 , side)

            // grabber
            if (aGamepad2.rightBumperPressed) hw.toggleGrabberState()
//            if (aGamepad2.leftBumperPressed) hw.stowGrabbers()

            if (aGamepad2.dpadUpPressed) hw.raiseGrabberHeight()
            if (aGamepad2.dpadDownPressed) hw.lowerGrabberHeight()
            hw.grabberOffset = -(aGamepad2.leftTrigger * 50).toInt()
            hw.grabberOffset += (aGamepad2.rightTrigger * 50).toInt()
        }
    }

}