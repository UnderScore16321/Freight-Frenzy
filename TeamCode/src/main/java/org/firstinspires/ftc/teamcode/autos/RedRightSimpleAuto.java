package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareMap;

@Autonomous(name = "[Red Right] Simple Auto (16/36)", group = "Competition")
public class RedRightSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hm = new HardwareMap(this);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();

        // drive to goal
        hm.driveInches(18);
        hm.turnDegrees(-30);

        //deposit on goal
        hm.setGrabberHeight(HardwareMap.GrabberHeight.FIRST_LEVEL);
        hm.waitForGrabber();
        hm.driveInches(7);
        hm.setGrabberIsOpen(true);
        hm.waitForGrabbing();

        //drive to storage unit
        hm.driveInches(-7);
        hm.turnDegrees(120);
        hm.driveInches(43);
    }
}
