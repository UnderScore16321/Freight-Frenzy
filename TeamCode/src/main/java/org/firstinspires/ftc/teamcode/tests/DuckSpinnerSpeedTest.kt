package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.teleops.TeleopBase
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp(group = "Test")
class DuckSpinnerSpeedTest : TeleopBase() {
    override fun mainOpMode() {
        var speed = 0.5

        while(opModeIsActive()) {
            telemetry.clearAll()
            telemetry.addData("Speed", speed)
            telemetry.update()
            hw.setSpinnerPower(speed, Side.RED)

            when {
                aGamepad1.dpadUpPressed -> speed += .1
                aGamepad1.dpadDownPressed -> speed -= .1
                aGamepad1.dpadLeftPressed -> speed -= .01
                aGamepad1.dpadRightPressed -> speed += .01
            }
        }
    }
}