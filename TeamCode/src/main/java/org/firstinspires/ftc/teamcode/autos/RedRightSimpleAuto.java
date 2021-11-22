package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name = "[Red Right] Simple Auto (16/36)", group = "Competition")
public class RedRightSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AutoHardware hm = new AutoHardware(this, false);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();

        // drive to goal
        hm.driveInches(18);
        hm.turnDegrees(-30);

        //deposit on goal
        hm.setGrabberHeight(Hardware.GrabberHeight.FIRST_LEVEL);
        hm.driveInches(7);
        hm.setGrabberIsOpen(true);

        //drive to storage unit
        hm.driveInches(-7);
        hm.turnDegrees(120);
        hm.driveInches(43);
    }
}
