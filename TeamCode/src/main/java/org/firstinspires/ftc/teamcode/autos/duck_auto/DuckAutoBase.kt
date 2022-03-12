package org.firstinspires.ftc.teamcode.autos.duck_auto

import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.Side

abstract class DuckAutoBase: DetectionAuto() {

    abstract val side: Side

    override fun mainOpMode() {
        val mult = if (side == Side.RED) 1 else -1

        val level = when (detectedPositionNow() to side) {
            -1 to Side.RED -> Hardware.GrabberHeight.FIRST_LEVEL
            0 to Side.RED -> Hardware.GrabberHeight.SECOND_LEVEL
            1 to Side.RED -> Hardware.GrabberHeight.THIRD_LEVEL
            0 to Side.BLUE -> Hardware.GrabberHeight.FIRST_LEVEL
            1 to Side.BLUE -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.THIRD_LEVEL
        }
        telemetry.addData("level", level)
        telemetry.update()

        hw.grabberIsOpen = false

        hw.turnToHeading(-45.0 * mult)
        hw.driveInches(15.0)
        hw.turnToHeading(90.0 * mult)
        driveFromBackDistance(0.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(0.0)
        driveFromBackDistance(if (side == Side.RED) 8.0 else 9.0, hw.backDistance(), speed = 0.1)

        hw.setSpinnerPower(0.6, side)
        sleep(5000)
        hw.setSpinnerPower(0.0, side)

        hw.turnToHeading(0.0)
        driveFromBackDistance(48.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(90.0 * mult)
        hw.turnToHeading(90.0 * mult)
        hw.grabberHeight = level

        driveFromBackDistance(15.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(90.0 * mult)
        driveFromBackDistance(30.0, hw.backDistance(), speed = 0.3)
        hw.grabberIsOpen = true

        hw.driveInches(-5.0)
        hw.turnToHeading((if (side == Side.RED) 65.0 else 60.0) * mult)
        hw.driveInches(-22.0)
        hw.turnToHeading(90.0 * mult)
        driveFromBackDistance(2.0, hw.backDistance(), speed = 0.3)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
    }

}