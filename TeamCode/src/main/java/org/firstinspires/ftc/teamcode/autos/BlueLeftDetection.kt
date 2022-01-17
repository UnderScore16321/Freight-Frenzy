package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.PosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(preselectTeleOp = "Blue Tank Drive")
class BlueLeftDetection : DetectionAuto() {
    override fun getNewPosDetector(): PosDetector = PosDetectorFactory.blueLeft(telemetry)

    override fun beforeStart() {
        super.beforeStart()
        telemetry.clearAll()
    }

    override fun mainOpMode() {
        hw.grabberIsOpen = false

        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.THIRD_LEVEL
            1 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.FIRST_LEVEL
        }
        telemetry.printData("level", level)

        hw.resetGrabberEncoder()

        // deposit preload
        hw.driveInches(20.0)
        if (level == Hardware.GrabberHeight.THIRD_LEVEL) {
            hw.driveInches(20.0)
            hw.driveInches(-20.0)
            hw.turnToHeading(45.0)
            hw.grabberHeight = level
        } else {
            hw.turnToHeading(45.0)
            hw.grabberHeight = level
        }

        hw.driveInches(7.0)
        hw.grabberIsOpen = true

        //park
        hw.driveInches(-7.0)
        hw.turnToHeading(0.0)
        hw.driveInches(-10.0)
        hw.grabberHeight = Hardware.GrabberHeight.FIRST_LEVEL
        hw.turnToHeading(-90.0)
        hw.driveInches(45.0)
    }
}