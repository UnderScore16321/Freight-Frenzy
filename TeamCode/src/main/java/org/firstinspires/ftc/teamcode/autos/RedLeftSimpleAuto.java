package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeamHardwareMap;

@Autonomous(name = "[Red Left] Simple Auto (12/32)", group = "Competition")
public class RedLeftSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TeamHardwareMap hm = new TeamHardwareMap(this);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();

        // drive to goal
        hm.driveInches(20);
        hm.turnDegrees(35);

        //deposit on goal
        hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.FIRST_LEVEL);
        hm.waitForGrabber();
        hm.driveInches(7);
        hm.setGrabberIsOpen(true);
        hm.waitForGrabbing();

        //drive to storage unit
        hm.driveInches(-7);
        hm.turnDegrees(-80);
        hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.SECOND_LEVEL);
        hm.waitForGrabber();
        hm.driveInches(19);
        hm.turnDegrees(-40);
        hm.driveInches(8);
    }
}
