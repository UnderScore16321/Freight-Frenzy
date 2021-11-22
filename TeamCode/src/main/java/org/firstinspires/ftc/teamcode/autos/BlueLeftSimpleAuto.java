package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TeamHardwareMap;

@Autonomous(name = "[Blue Left] Simple Auto (16/36)", group = "Competition")
public class BlueLeftSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TeamHardwareMap hm = new TeamHardwareMap(this);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();

        // drive to goal
        hm.driveInches(18);
        hm.turnDegrees(30);

        //deposit on goal
        hm.setGrabberHeight(TeamHardwareMap.GrabberHeight.FIRST_LEVEL);
        hm.waitForGrabber();
        hm.driveInches(7);
        hm.setGrabberIsOpen(true);
        hm.waitForGrabbing();

        //drive to storage unit
        hm.driveInches(-7);
        hm.turnDegrees(-120);
        hm.driveInches(43);
    }
}
