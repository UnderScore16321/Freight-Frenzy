package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeamHardwareMap;
import org.firstinspires.ftc.teamcode.Toggler;

public class TankDriveBase extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        TeamHardwareMap hm = new TeamHardwareMap(this);
        Toggler grabberOpen = new Toggler();
        Toggler grabberDown = new Toggler();
        Toggler grabberUp = new Toggler();
        Toggler resetGrabber = new Toggler();

        hm.setGrabberIsOpen(false);

        waitForStart();

//        hm.resetGrabberEncoder();

        while (opModeIsActive()) {
            if (resetGrabber.shouldToggle(gamepad2.x)) {
                hm.resetGrabberEncoder();
            }

            hm.setWheelPower(gamepad1.left_stick_y, gamepad1.right_stick_y);

            hm.setSpinnerPower(Math.max(-gamepad2.left_stick_y, 0) * -1);

            if (grabberOpen.shouldToggle(gamepad2.right_bumper)) {
                hm.toggleGrabberState();
            }
            if (grabberDown.shouldToggle(gamepad2.dpad_up)) {
                hm.raiseGrabberHeight();
            } else if (grabberUp.shouldToggle(gamepad2.dpad_down)) {
                hm.lowerGrabberHeight();
            }
            hm.setGrabberOffset((int) (gamepad2.left_trigger * 50));
            hm.displayGrabberTelemetry();
        }
    }
}
