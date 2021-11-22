package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareMap;

@Autonomous(name = "[Red Left] Duck Auto", group = "Competition")
public class RedDuck extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hm = new HardwareMap(this);

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
        hm.setGrabberHeight(HardwareMap.GrabberHeight.FIRST_LEVEL);
        hm.driveInches(35);
        hm.setGrabberIsOpen(true);
        hm.waitForGrabbing();

        hm.driveInches(-7);
        hm.turnDegrees(-110);
        hm.setGrabberHeight(HardwareMap.GrabberHeight.SECOND_LEVEL);
        hm.driveInches(20);
    }
}
