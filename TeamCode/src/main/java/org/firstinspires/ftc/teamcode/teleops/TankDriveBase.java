package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeamHardwareMap;
import org.firstinspires.ftc.teamcode.Toggler;

public class TankDriveBase extends LinearOpMode {

    protected int SPINNER_MULTIPLIER = 1;

    @Override
    public void runOpMode() {
        TeamHardwareMap hm = new TeamHardwareMap(this);
        Toggler grabberOpen = new Toggler();
        Toggler grabberHeight = new Toggler();

        waitForStart();

        while (opModeIsActive()) {
            hm.setWheelPower(gamepad1.left_stick_y, gamepad1.right_stick_y);
            hm.setSpinnerPower(Math.max(gamepad2.left_stick_y * SPINNER_MULTIPLIER, 0));
            if (grabberOpen.shouldToggle(gamepad2.right_bumper)) {
                hm.toggleGrabberState();
            }
            if (grabberHeight.shouldToggle(gamepad2.left_bumper)) {
                if (hm.getCurrentGrabberHeight() == TeamHardwareMap.GrabberHeight.UP) {
                    hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.DOWN);
                } else {
                    hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.UP);
                }
            }
        }
    }
}
