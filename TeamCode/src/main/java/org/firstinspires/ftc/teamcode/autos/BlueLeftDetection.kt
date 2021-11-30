package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.PosDetector

@Autonomous(group = "testing")
class BlueLeftDetection : DetectionAuto() {

    override fun getNewPosDetector(): PosDetector = PosDetectorFactory.blueLeft(telemetry)

    override fun beforeStart() {
        hw.grabberIsOpen = false
    }

    override fun mainOpMode() {
        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.FIRST_LEVEL // 0 is leftmost is bottom level
            else -> Hardware.GrabberHeight.SECOND_LEVEL
        }
        telemetry.printData("level", level)

        hw.resetGrabberEncoder()

        // deposit preload
        hw.driveInches(24.0)
        hw.grabberHeight = level
        hw.turnToHeading(45.0)
        hw.driveInches(5.0)
        hw.grabberIsOpen = true

        // park
        hw.driveInches(-5.0)
        hw.turnToHeading(-90.0)
    }
}