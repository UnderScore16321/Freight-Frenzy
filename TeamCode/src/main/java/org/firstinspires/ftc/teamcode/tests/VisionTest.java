package org.firstinspires.ftc.teamcode.tests;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.PosDetector;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class VisionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("status", "waiting for camera");
        telemetry.update();
        Hardware hw = new Hardware(this, true);
        PosDetector posDetector = new PosDetector(telemetry, new PosDetector.ImRegion[]{
                new PosDetector.ImRegion(0.25f, 0.5f, 0, 0.33f),
                new PosDetector.ImRegion(0.75f, 1.0f, 0, 0.33f),
        });
        hw.waitForCameraStart();
        telemetry.clear();
        telemetry.addData("status", "waiting for camera adjustment");
        telemetry.update();
        sleep(5000);
        telemetry.addData("status", "getting base image");
        telemetry.update();
        List<Bitmap> bases = new ArrayList<>();
        bases.add(hw.forceGetFrame());
        posDetector.useBaseStates(bases);
        telemetry.addData("status", "ready");
        telemetry.update();

        waitForStart();


        Bitmap latest = hw.forceGetFrame();
        while (opModeIsActive()) {
            Bitmap newFrame = hw.getLatestFrame();
            if (newFrame != null) {
                latest = newFrame;
            }
            int pos = posDetector.regionOfImage(latest);
            switch (pos) {
                case -1:
                    telemetry.addData("pos", "right");
                    break;
                case 0:
                    telemetry.addData("pos", "left");
                    break;
                case 1:
                    telemetry.addData("pos", "middle");
                    break;
            }
            telemetry.update();
        }

    }
}
