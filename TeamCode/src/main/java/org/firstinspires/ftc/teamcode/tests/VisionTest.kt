package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.utils.ColorChangePosDetector
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory

@TeleOp
@Disabled
class VisionTest : DetectionAuto() {
    override fun getNewPosDetector() = PosDetectorFactory.redRight(telemetry)

    override fun mainOpMode() {
        while (!isStopRequested) {
            val frame = hw.forceGetFrame()
            telemetry.printData("position", posDetector.regionOfImage(frame))
        }
    }
}