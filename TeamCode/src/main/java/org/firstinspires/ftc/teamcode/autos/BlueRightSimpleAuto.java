package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;

@Autonomous(name = "[Blue Right] Simple Auto (12/32)", group = "Competition")
public class BlueRightSimpleAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AutoHardware hm = new AutoHardware(this, false);

        hm.setGrabberIsOpen(false);
        waitForStart();
        hm.resetGrabberEncoder();


        Thread.sleep(700);

        // drive to goal
        hm.driveInches(20);
        hm.turnDegrees(-35);

        //deposit on goal
        hm.setGrabberHeight(Hardware.GrabberHeight.FIRST_LEVEL);
        hm.driveInches(7);
        hm.setGrabberIsOpen(true);

        //drive to storage unit
        hm.driveInches(-7);
        hm.turnDegrees(80);
        hm.setGrabberHeight(Hardware.GrabberHeight.SECOND_LEVEL);
        hm.driveInches(19);
        hm.turnDegrees(40);
        hm.driveInches(8);
    }

}
