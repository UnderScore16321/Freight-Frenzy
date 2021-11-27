package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeamHardwareMap;

@Autonomous(name = "[Red Left] Duck Auto", group = "Competition")
@Disabled
public class RedDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TeamHardwareMap hm = new TeamHardwareMap(this);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();

        hm.setSpinnerPower(-0.8);
        hm.setWheelPower(0.8, 0);
        Thread.sleep(1000);
        hm.setWheelPower(0.5, 0);
        Thread.sleep(2000);
        hm.setSpinnerPower(0);
        hm.setWheelPower(0);

        hm.turnDegrees(12);
        hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.FIRST_LEVEL);
        hm.driveInches(35);
        hm.setGrabberIsOpen(true);
        hm.waitForGrabbing();

        hm.driveInches(-7);
        hm.turnDegrees(-110);
        hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.SECOND_LEVEL);
        hm.driveInches(20);
    }
}
