package org.firstinspires.ftc.teamcode.autos.duck_auto

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.Side

abstract class DuckAutoBase: DetectionAuto() {

    abstract val side: Side

    override fun mainOpMode() {
        val mult = if (side == Side.RED) 1 else -1

        val level = when (detectedPositionNow()) {
            -1 -> Hardware.GrabberHeight.FIRST_LEVEL
            0 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.THIRD_LEVEL
        }
        telemetry.addData("level", level)
        telemetry.update()

        hw.grabberIsOpen = false

        hw.driveInches(15.0)
        hw.turnToHeading(90.0 * mult)
        driveFromDistance(0.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(0.0)
        driveFromDistance(8.0, hw.backDistance(), speed = 0.3)

        hw.setSpinnerPower(0.6, Side.RED)
        sleep(5000)
        hw.setSpinnerPower(0.0, Side.RED)

        hw.turnToHeading(0.0)
//        hw.driveInches(24.0)
        driveFromDistance(48.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(90.0 * mult)
        hw.turnToHeading(90.0 * mult)
        hw.grabberHeight = level

        driveFromDistance(15.0, hw.backDistance(), speed = 0.3)
        hw.turnToHeading(90.0 * mult)
        driveFromDistance(30.0, hw.backDistance(), speed = 0.3)
        hw.grabberIsOpen = true

        hw.turnToHeading(60.0 * mult)
        hw.driveInches(-22.0)
        hw.turnToHeading(90.0 * mult)
        driveFromDistance(2.0, hw.backDistance(), speed = 0.3)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
    }

    private fun driveFromDistance(targetDistance: Double, currentDist: Double, speed: Double? = null) {
        val distToDrive = targetDistance - currentDist
        if (speed != null) {
            hw.driveInches(distToDrive * 0.9, speed)
        } else {
            hw.driveInches(distToDrive * 0.9)
        }
    }

}