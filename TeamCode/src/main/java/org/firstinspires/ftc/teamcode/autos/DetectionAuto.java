package org.firstinspires.ftc.teamcode.autos;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.PosDetector;

import java.util.ArrayList;
import java.util.List;

abstract public class DetectionAuto extends LinearOpMode {

    protected AutoHardware hw = null;
    private PosDetector posDetector = null;

    @Override
    public void runOpMode() throws InterruptedException {
        hw = new AutoHardware(this, true);

        preStart();

        telemetry.addData("status", "waiting for camera");
        telemetry.update();
        posDetector = getPosDetector();
        hw.waitForCameraStart();
        telemetry.clearAll();
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
        autoCode();
    }

    protected int detectedPositionNow() throws InterruptedException {
        return posDetector.regionOfImage(hw.forceGetFrame());
    }

    /**
     * Code run before the auto begins.
     */
    abstract protected void preStart() throws InterruptedException;

    /**
     * The actual code to run the auto.
     */
    abstract protected void autoCode() throws InterruptedException;

    abstract protected PosDetector getPosDetector();
}
