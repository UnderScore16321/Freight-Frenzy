package org.firstinspires.ftc.teamcode.autos.freight_auto

import org.firstinspires.ftc.teamcode.autos.DetectionAuto
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
        if (level != Hardware.GrabberHeight.THIRD_LEVEL) hw.driveInches(30.0) // clear the tse out of the way

        driveFromBackDistance(15.0, hw.backDistance())
        hw.turnToHeading(-35.0 * mult)
        hw.grabberHeight = level

        val toDrive = when (level) {
            Hardware.GrabberHeight.FIRST_LEVEL -> 8.0
            Hardware.GrabberHeight.SECOND_LEVEL -> 5.0
            else -> 5.0
        }
        hw.driveInches(toDrive)
        hw.grabberIsOpen = true

        // drive into warehouse
        hw.driveInches(-toDrive)
        hw.turnToHeading(20.0 * mult, log = true)
        println(1)
        Thread {
            hw.grabberHeight = Hardware.GrabberHeight.DOWN
        }.start()
        println(2)
        hw.setWheelPower(-0.5)
        println(3)
        sleep(1000)
        println(4)
        hw.setWheelPower(0.0)
        println(5)
        hw.turnToHeading(90.0 * mult)
        hw.driveInches(30.0)
        hw.turnToHeading(90.0 * mult)
        val frontDist = hw.frontDistance()
        if (frontDist > 22.0) driveFromFrontDistance(22.0, frontDist)

        // grab freight
        hw.grabberIsOpen = false
        hw.grabberIsOpen = true
        hw.driveInches(-1.0)
        hw.grabberIsOpen = false

        // deliver the freight
        hw.turnToHeading(85.0 * mult)
        hw.driveInches(-17.0)
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

        hw.turnToHeading(110.0 * mult)
    }
}