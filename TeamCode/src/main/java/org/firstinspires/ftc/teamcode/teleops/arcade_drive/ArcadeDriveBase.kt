package org.firstinspires.ftc.teamcode.teleops.arcade_drive

import org.firstinspires.ftc.teamcode.utils.Side
import org.firstinspires.ftc.teamcode.teleops.TeleopBase
import kotlin.math.abs

abstract class ArcadeDriveBase : TeleopBase() {
    abstract val side: Side

    override fun beforeStart() {
        hw.grabberIsOpen = false
    }

    override fun mainOpMode() {
        while (opModeIsActive()) {
            if (aGamepad2.xPressed) hw.resetGrabberEncoder()

            var leftPower = aGamepad1.leftY + aGamepad1.rightX
            var rightPower = aGamepad1.leftY - aGamepad1.rightX
            if ((leftPower > rightPower && leftPower > 1) || (leftPower < rightPower && leftPower < -1)) {
                val factor = abs(leftPower)
                leftPower /= factor
                rightPower /= factor
            } else if ((rightPower > leftPower && rightPower > 1) || (rightPower < leftPower && rightPower < -1)) {
                val factor = abs(rightPower)
                leftPower /= factor
                rightPower /= factor
            }

            // drive
            hw.setAdjustedWheelPower(leftPower, rightPower)

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