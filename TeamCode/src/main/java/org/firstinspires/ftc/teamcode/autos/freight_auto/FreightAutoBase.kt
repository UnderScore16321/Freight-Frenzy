package org.firstinspires.ftc.teamcode.autos.freight_auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.Side

abstract class FreightAutoBase : DetectionAuto() {

    abstract val side: Side

    override fun mainOpMode() {
        val mult = if (side == Side.RED) 1 else -1

        telemetry.clearAll()
        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.FIRST_LEVEL
            1 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.THIRD_LEVEL
        }
        telemetry.addData("level", level)
        telemetry.update()

        hw.grabberIsOpen = false

        // deliver preload
        hw.driveInches(30.0) // clear the tse out of the way


        hw.driveInches(-15.0)
        hw.turnToHeading(-35.0 * mult)
        hw.grabberHeight = level

        val toDrive = when (level) {
            Hardware.GrabberHeight.FIRST_LEVEL -> 7.0
            Hardware.GrabberHeight.SECOND_LEVEL -> 5.0
            else -> 5.0
        }
        hw.driveInches(toDrive)
        hw.grabberIsOpen = true

        // drive into warehouse
        hw.driveInches(-toDrive)
        hw.turnToHeading(20.0 * mult)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
        hw.setWheelPower(-0.5)
        sleep(1000)
        hw.setWheelPower(0.0)
        hw.turnToHeading(90.0 * mult)
        hw.driveInches(30.0)

        // grab freight
        hw.grabberIsOpen = false
        hw.grabberIsOpen = true
        hw.driveInches(2.0)
        hw.driveInches(-1.0)
        hw.grabberIsOpen = false

        // deliver the freight
        hw.turnToHeading(85.0 * mult)
        hw.driveInches(-15.0)
        hw.turnToHeading(90.0 * mult)

        val targetDist = 55.0
        val distToDrive = targetDist - hw.frontDistance()

        hw.driveInches(-distToDrive * 0.9)
        hw.turnToHeading(-20.0 * mult)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        hw.driveInches(12.0)
        hw.grabberIsOpen = true

        // park
        hw.driveInches(-5.0)
        hw.turnToHeading(75.0 * mult)
        hw.driveInches(50.0)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
    }
}