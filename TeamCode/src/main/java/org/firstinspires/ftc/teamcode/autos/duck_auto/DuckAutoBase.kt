package org.firstinspires.ftc.teamcode.autos.duck_auto

import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.Side

abstract class DuckAutoBase: DetectionAuto() {

    abstract val side: Side

    override fun mainOpMode() {
        val mult = if (side == Side.RED) 1 else -1

        hw.driveInches(10.0)
        hw.turnToHeading(90.0 * mult)
        hw.driveInches(-20.0, speedIn = 0.5)
        hw.setWheelPower(-0.3)
        sleep(1000)
        hw.setWheelPower(0.0)
        hw.turnToHeading(0.0)
        hw.driveInches(-6.0)

        hw.setSpinnerPower(0.6, Side.RED)
        sleep(5000)
        hw.setSpinnerPower(0.0, Side.RED)

        hw.turnToHeading(0.0)
        hw.driveInches(24.0)
        hw.turnToHeading(90.0 * mult)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        hw.driveInches(-3.0)
        hw.driveInches(20.0)

        hw.driveInches(-20.0)
    }

}