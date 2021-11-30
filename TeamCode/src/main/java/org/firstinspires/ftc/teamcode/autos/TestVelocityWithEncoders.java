package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class TestVelocityWithEncoders extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        AutoHardware hw = new AutoHardware(this, false);

        waitForStart();

        hw.setVelocity(1000);

        while (opModeIsActive()) {
            telemetry.addData("vel", hw.leftFrontVelocity());
            telemetry.update();
        }
    }
}
