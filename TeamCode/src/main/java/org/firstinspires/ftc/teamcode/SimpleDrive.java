package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Simple Drive OpMode")
public class SimpleDrive extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        TeamHardwareMap hm = new TeamHardwareMap(this);

        while(opModeIsActive()) {
            hm.setPower(gamepad1.left_stick_y, gamepad1.right_stick_y);
        }
    }
}
