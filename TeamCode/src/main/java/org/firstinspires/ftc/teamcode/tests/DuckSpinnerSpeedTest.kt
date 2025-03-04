package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.teleops.TeleopBase
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp
@Disabled
class DuckSpinnerSpeedTest : TeleopBase() {
    override fun mainOpMode() {
        var speed = 0.5

        while(opModeIsActive()) {
            telemetry.clearAll()
            telemetry.addData("Speed", speed)
            telemetry.update()
            if (gamepad1.x) {
                hw.setSpinnerPower(speed, Side.RED)
            } else {
                hw.setSpinnerPower(0.0, Side.RED)
            }

            when {
                aGamepad1.dpadUpPressed -> speed += .1
                aGamepad1.dpadDownPressed -> speed -= .1
                aGamepad1.dpadLeftPressed -> speed -= .01
                aGamepad1.dpadRightPressed -> speed += .01
            }
        }
    }
}