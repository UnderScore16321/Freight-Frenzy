package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.autos.DetectionAuto
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory

@Autonomous
@Disabled
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