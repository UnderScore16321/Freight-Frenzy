package org.firstinspires.ftc.teamcode.autos.duck_auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory
import org.firstinspires.ftc.teamcode.utils.BlackPosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(name = "Blue Duck Auto", preselectTeleOp = "Blue Tank Drive")
class BlueDuckAuto: DuckAutoBase() {
    override val side: Side
        get() = Side.BLUE

    override fun getNewPosDetector(): BlackPosDetector = PosDetectorFactory.blueRight(telemetry)
}