package org.firstinspires.ftc.teamcode.autos.freight_auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory
import org.firstinspires.ftc.teamcode.utils.BlackPosDetector
import org.firstinspires.ftc.teamcode.utils.Side

@Autonomous(name = "Blue Freight Auto", preselectTeleOp = "Blue Tank Drive")
class BlueFreightAuto: FreightAutoBase() {
    override val side: Side
        get() = Side.BLUE

    override fun getNewPosDetector(): BlackPosDetector = PosDetectorFactory.blueLeft(telemetry)
}