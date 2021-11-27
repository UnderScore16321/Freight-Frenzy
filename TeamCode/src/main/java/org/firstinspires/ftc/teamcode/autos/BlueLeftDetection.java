package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.PosDetector;

@Autonomous(group = "testing")
public class BlueLeftDetection extends DetectionAuto {
    @Override
    protected void preStart() throws InterruptedException {
        hw.setGrabberIsOpen(false);
    }

    @Override
    protected void autoCode() throws InterruptedException {
        int position = detectedPositionNow();
        int level = position == 0 ? 1 : 2; // 0 is leftmost is bottom level
        telemetry.addData("level", level);
        telemetry.update();

        hw.resetGrabberEncoder();
        hw.driveInches(24);
        hw.setGrabberHeight(level == 1 ? Hardware.GrabberHeight.FIRST_LEVEL : Hardware.GrabberHeight.SECOND_LEVEL);
        hw.goToHeading(45);
        hw.driveInches(5);
        hw.setGrabberIsOpen(true);
        hw.driveInches(-5);
        hw.goToHeading(-90);
    }

    @Override
    protected PosDetector getPosDetector() {
        return PosDetectorFactory.blueLeft(telemetry);
    }
}
