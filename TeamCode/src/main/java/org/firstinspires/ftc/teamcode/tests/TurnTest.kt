package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.autos.AutoBase

@Autonomous
class TurnTest : AutoBase() {
    override fun mainOpMode() {
        hw.waitForImuCalibration(false)

        hw.turnToHeading(90.0)
    }
}