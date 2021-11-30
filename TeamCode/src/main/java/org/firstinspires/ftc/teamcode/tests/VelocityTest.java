package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleops.TeleopBase;

@TeleOp
@Disabled
public class VelocityTest extends TeleopBase {
    @Override
    protected void mainOpMode() {
        getHw().setWheelPower(1.0);
        double maxVelocity = 0;
        while (opModeIsActive()) {
            double currentVelocity = getHw().leftFrontVelocity();
            maxVelocity = Math.max(maxVelocity, currentVelocity);

            telemetry.addData("curr", currentVelocity);
            telemetry.addData("max", maxVelocity);
            telemetry.update();
        }
    }
}
