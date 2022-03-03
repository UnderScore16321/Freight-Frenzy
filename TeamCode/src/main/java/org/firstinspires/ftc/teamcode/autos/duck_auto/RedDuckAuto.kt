package org.firstinspires.ftc.teamcode.autos.duck_auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory
import org.firstinspires.ftc.teamcode.utils.BlackPosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(name = "Red Duck Auto", preselectTeleOp = "Red Tank Drive")
class RedDuckAuto: DuckAutoBase() {
    override val side: Side
        get() = Side.RED

    override fun getNewPosDetector(): BlackPosDetector = PosDetectorFactory.redLeft(telemetry)
}