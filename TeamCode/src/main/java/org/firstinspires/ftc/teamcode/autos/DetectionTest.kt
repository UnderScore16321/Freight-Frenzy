package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.ColorChangePosDetector

@Autonomous
class DetectionTest: DetectionAuto() {
    override fun getNewPosDetector() = PosDetectorFactory.redRight(telemetry)

    override fun mainOpMode() {
        while(!isStopRequested) {
            telemetry.clearAll()
            telemetry.addData("pos", detectedPositionNow())
            telemetry.update()
            sleep(10)
        }
    }
}