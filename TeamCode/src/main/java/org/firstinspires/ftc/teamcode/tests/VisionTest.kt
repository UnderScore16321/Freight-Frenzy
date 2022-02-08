package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.utils.PosDetector
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory

@TeleOp
@Disabled
class VisionTest : DetectionAuto() {
    override fun getNewPosDetector(): PosDetector {
        return PosDetectorFactory.blueRight(telemetry)
    }

    override fun mainOpMode() {
        while (!isStopRequested) {
            val frame = hw.forceGetFrame()
            telemetry.printData("position", posDetector.regionOfImage(frame))
        }
    }
}