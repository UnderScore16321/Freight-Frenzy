package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.autos.AutoBase

@Autonomous
@Disabled
class DriveTest : AutoBase() {
    override fun mainOpMode() {
        hw.driveInches(48.0, 1.0)
        sleep(1000)
    }
}