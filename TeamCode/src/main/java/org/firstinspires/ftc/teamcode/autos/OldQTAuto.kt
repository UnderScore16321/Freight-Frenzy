package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.ColorChangePosDetector

@Autonomous(preselectTeleOp = "Red Tank Drive")
@Disabled
class OldQTAuto : DetectionAuto() {
    override fun getNewPosDetector(): ColorChangePosDetector =
        ColorChangePosDetector(
            telemetry,
            arrayOf(
                PosDetector.ImRegion(0.00f, 0.5f, 0.0f, 0.1f),
                PosDetector.ImRegion(0.25f, 0.35f, 0.0f, 0.2f),
            )
        )

    override fun mainOpMode() {
        telemetry.clearAll()
        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.SECOND_LEVEL
            1 -> Hardware.GrabberHeight.THIRD_LEVEL
            else -> Hardware.GrabberHeight.FIRST_LEVEL
        }
        telemetry.addData("level", level)
        telemetry.update()

        hw.grabberIsOpen = false
        hw.grabberHeight = level
        hw.turnToHeading(5.0)
        hw.driveInches(18.0)
        hw.grabberIsOpen = true
        hw.driveInches(-5.0, speedIn = 0.4)
        hw.driveInches(-12.0)
        hw.turnToHeading(92.0)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
        hw.driveInches(45.0, speedIn = 0.4)
        hw.grabberIsOpen = false
        hw.grabberIsOpen = true
        hw.driveInches(3.0, speedIn = 0.3)
        hw.driveInches(-1.0, speedIn = 0.3)
        hw.grabberIsOpen = false
        hw.turnToHeading(87.0)
        hw.driveInches(-10.0, speedIn = 0.4)
        hw.turnToHeading(90.0)
        hw.driveInches(-34.0)
        hw.turnToHeading(0.0)
        hw.driveInches(-6.0)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        hw.turnToHeading(0.0)
        hw.driveInches(18.0, speedIn = 0.4)
        hw.grabberIsOpen = true
        hw.driveInches(-4.0)
        hw.turnToHeading(90.0)
        hw.driveInches(50.0)

    }
}