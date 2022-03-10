package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.hardware.DistanceSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.autos.AutoBase
import org.firstinspires.ftc.teamcode.utils.Hardware

@Autonomous
@Disabled
class DistTest: AutoBase() {
    override fun mainOpMode() {
        while (opModeIsActive()) {
            telemetry.addData("dist", hw.frontDistance())
            telemetry.update()
        }
    }
}