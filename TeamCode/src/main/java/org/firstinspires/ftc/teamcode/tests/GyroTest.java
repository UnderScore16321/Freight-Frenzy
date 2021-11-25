package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.autos.AutoHardware;

@TeleOp
public class GyroTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        AutoHardware hw = new AutoHardware(this, false);

        hw.waitForImuCalibration(true);
        waitForStart();

//        double heading = 180;
//        while (opModeIsActive()) {
//            hw.goToHeading(heading);
//            telemetry.clear();
//            telemetry.addData("heading", hw.robotHeading());
//            telemetry.update();
//            Thread.sleep(2000);
//            heading += 180;
//            heading = heading % 360;
//        }
        hw.goToHeading(90);
        hw.goToHeading(270);
    }
}
