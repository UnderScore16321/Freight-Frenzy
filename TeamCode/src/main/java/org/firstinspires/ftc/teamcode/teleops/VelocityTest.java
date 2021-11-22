package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Side;

@TeleOp
public class VelocityTest extends TeleopBase {

    @Override
    protected void opModeCode() throws InterruptedException {
        waitForStart();

        hw.setWheelPower(1.0);
        double maxVelocity = 0;
        while (opModeIsActive()) {
            double currentVelocity = hw.leftFrontVelocity();
            maxVelocity = Math.max(maxVelocity, currentVelocity);

            telemetry.addData("curr", currentVelocity);
            telemetry.addData("max", maxVelocity);
            telemetry.update();
        }
    }

    @Override
    protected Side getSide() {
        return null;
    }
}
