package org.firstinspires.ftc.teamcode.teleops.tank_drive

import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.teleops.TeleopBase
import org.firstinspires.ftc.teamcode.utils.Side

abstract class TankDriveBase : TeleopBase() {

    abstract val side: Side

    override fun beforeStart() {
        hw.grabberIsOpen = true
    }

    override fun mainOpMode() {
        var duckTime = ElapsedTime()

        Thread {
            while (opModeIsActive()) {
                // drive
                when {
                    aGamepad1.leftTrigger > .1 -> hw.setAdjustedWheelPower(
                        -aGamepad1.leftTrigger,
                        -aGamepad1.leftTrigger
                    )
                    aGamepad1.rightTrigger > .1 -> hw.setAdjustedWheelPower(
                        aGamepad1.rightTrigger,
                        aGamepad1.rightTrigger
                    )
                    aGamepad1.leftBumper -> hw.setAdjustedWheelPower(-0.8, 0.8)
                    aGamepad1.rightBumper -> hw.setAdjustedWheelPower(0.8, -0.8)
                    else -> hw.setAdjustedWheelPower(aGamepad1.leftY, aGamepad1.rightY)
                }
            }
        }.start()

        while (opModeIsActive()) {
            if (aGamepad2.x) hw.resetGrabberEncoder()

            // duck spinner
            if (aGamepad2.leftBumper) {
                hw.setSpinnerPower(
                    when (duckTime.milliseconds().toInt()) {
                        in 0..300 -> 0.88
                        in 0..900 -> 1.2
                        else -> 1.5
                    },
                    side
                )
            } else {
                hw.setSpinnerPower(0.0, side)
                duckTime = ElapsedTime()
            }

            // grabber
            if (aGamepad2.rightBumperPressed) hw.toggleGrabberState()
//            if (aGamepad2.leftBumperPressed) hw.stowGrabbers()

            if (aGamepad2.dpadUpPressed) hw.raiseGrabberHeight()
            if (aGamepad2.dpadDownPressed) hw.lowerGrabberHeight()
            hw.grabberOffset = ((aGamepad2.leftY + aGamepad2.rightY) * 60).toInt()
        }
    }

}