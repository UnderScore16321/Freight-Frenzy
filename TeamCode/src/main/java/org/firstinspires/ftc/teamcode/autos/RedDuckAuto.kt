package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.ColorChangePosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(preselectTeleOp = "Red Tank Drive")
class RedDuckAuto: DetectionAuto() {
    override fun getNewPosDetector(): ColorChangePosDetector = PosDetectorFactory.redLeft(telemetry)

    override fun mainOpMode() {
        hw.driveInches(10.0)
        hw.turnToHeading(90.0)
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
        hw.turnToHeading(90.0)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        hw.driveInches(-3.0)
        hw.driveInches(20.0)

        hw.driveInches(-20.0)
    }
}