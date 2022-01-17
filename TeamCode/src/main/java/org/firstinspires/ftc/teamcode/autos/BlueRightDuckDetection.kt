package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.PosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(preselectTeleOp = "Blue Tank Drive")
class BlueRightDuckDetection : DetectionAuto() {
    override fun getNewPosDetector(): PosDetector = PosDetectorFactory.blueRight(telemetry)

    override fun beforeStart() {
        super.beforeStart()
        telemetry.clearAll()
    }

    override fun mainOpMode() {
        hw.grabberIsOpen = false

        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.FIRST_LEVEL
            1 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.THIRD_LEVEL
        }
        telemetry.printData("level", level)

        hw.resetGrabberEncoder()

        // deposit preload
        hw.turnToHeading(10.0)
        hw.driveInches(39.0)
        hw.grabberHeight = level
        hw.turnToHeading(-90.0)
        hw.driveInches(6.0)
        hw.grabberIsOpen = true

        // drive to duck
        hw.driveInches(-6.0)
        hw.turnToHeading(0.0)
        hw.driveInches(-48.0)
        hw.driveInches(5.0)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN

        hw.turnToHeading(-90.0)
        hw.driveInches(-23.0)
        hw.turnToHeading(-45.0)
        hw.driveInches(-3.0, speedIn = 0.4)

        // spin duck
        hw.setSpinnerPower(0.6, Side.BLUE)
        sleep(5000)
        hw.setSpinnerPower(0.0, Side.BLUE)

        // park
        hw.turnToHeading(0.0)
        hw.driveInches(24.0)
        hw.stowGrabbers()
        hw.turnToHeading(-90.0)
        hw.driveInches(-8.0)
    }
}