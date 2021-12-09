package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.PosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(group = "testing")
class BlueRightDetectionDuck : DetectionAuto() {

    override fun getNewPosDetector(): PosDetector = PosDetectorFactory.blueRight(telemetry)

    override fun beforeStart() {
        super.beforeStart()
        hw.grabberIsOpen = false

        telemetry.clearAll()

        var latestImage = hw.forceGetFrame()
        while (!isStarted) {
            latestImage = hw.latestFrame ?: latestImage
            if (aGamepad2.rightBumperPressed) hw.toggleGrabberState()
            if (aGamepad2.xPressed) {
                posDetector.useBaseStates(listOf(latestImage))
            }
            if (aGamepad2.dpadUpPressed) startDelay += 1000
            if (aGamepad2.dpadDownPressed) startDelay -= 1000
            if (aGamepad2.dpadRightPressed) startDelay += 100
            if (aGamepad2.dpadLeftPressed) startDelay -= 100

            telemetry.addData("diff", posDetector.allImageDiff(latestImage))
            telemetry.addData("start delay", startDelay.toDouble() / 1000.0)
            telemetry.update()
        }
    }

    override fun mainOpMode() {
        val level = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.THIRD_LEVEL // 0 is rightmost is third level
            1 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.FIRST_LEVEL
        }
        val driveDistance = if (level == Hardware.GrabberHeight.THIRD_LEVEL) 14.0 else 10.0
        telemetry.printData("level", level)

        hw.resetGrabberEncoder()

        // deposit preload
        hw.driveInches(39.0)
        hw.grabberHeight = level
        hw.turnToHeading(-90.0)
        hw.driveInches(driveDistance)
        hw.grabberIsOpen = true

        // drive to duck
        hw.driveInches(-driveDistance)
        hw.turnToHeading(0.0)
        hw.driveInches(-35.0)
        hw.turnToHeading(-90.0)
        hw.driveInches(-if (level == Hardware.GrabberHeight.FIRST_LEVEL) 20.0 else 23.0, 0.3)

        // spin duck
        hw.setSpinnerPower(1.0, Side.BLUE)
        sleep(5000)
        hw.setSpinnerPower(0.0, Side.BLUE)

        // park
        hw.turnToHeading(-90.0)
        hw.driveInches(10.0)
        hw.turnToHeading(0.0)
        hw.driveInches(-10.0, 0.3)
        hw.driveInches(25.0)
        hw.grabberHeight = Hardware.GrabberHeight.STOWED
        hw.turnToHeading(90.0)
        hw.driveInches(10.0, 0.3)
    }
}