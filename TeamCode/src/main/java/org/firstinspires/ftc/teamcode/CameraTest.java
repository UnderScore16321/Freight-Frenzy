package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class CameraTest extends LinearOpMode {
    TeamHardwareMap hm;

    @Override
    public void runOpMode() {
        hm = new TeamHardwareMap(this);

        waitForStart();

        int cooldown = 0;
        while (opModeIsActive()) {
            if (gamepad1.a && cooldown <= 0) {
                printTotalLumianance();
                cooldown = 30;
            }
            if (cooldown > 0){
                cooldown--;
            }
        }

        hm.camera.closeCamera();
    }

    private void printTotalLumianance() {
        Bitmap image = hm.camera.getLatestFrame();

        telemetry.clear();
        if (image != null) {
            int width = image.getWidth();
            int height = image.getHeight();

            int count = width * height;
            double totalL = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    totalL += luminance(image.getPixel(i, j));
                }
            }
            double totalLuminance = totalL / count;

            telemetry.addData("average luminance", totalLuminance);
            telemetry.update();

        } else {
            telemetry.log().add("didn't get an image");
            telemetry.update();
        }
    }

    private double luminance(int p) {
        return ((double) Color.red(p) +
                (double) Color.green(p) +
                (double) Color.blue(p)) / (3.0 * 255.0);
    }

}
