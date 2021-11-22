package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.C;

@TeleOp
public class CameraTest extends LinearOpMode {
    TeamHardwareMap hm;

    private static final float PURPLE_TOL = 20;

    @Override
    public void runOpMode() throws InterruptedException {
        hm = new TeamHardwareMap(this, true);

        waitForStart();

//        telemetry.addLine("rgb: " + rT + ", " + gT + ", " + bT);
//        telemetry.addLine("hsl: " + hsl[0] + ", " + hsl[1] + ", " + hsl[2]);
//        telemetry.addData("purpleness", score);
//        telemetry.addData("block here", isPurple);
//        telemetry.update();

        telemetry.clear();

        while (opModeIsActive()) {
//            Thread.sleep(100);
            Bitmap frame = hm.forceGetFrame();

            if (frame != null) {
                boolean isPurpleLeft = blockLeft(hslInImageSector(frame, 2.0 / 5.0, 3.0 / 5.0, 2.0 / 3.0, 1.0));
//                telemetry.addData("block left", isPurpleLeft);
                boolean isPurpleRight = blockRight(hslInImageSector(frame, 4.0 / 5.0, 1.0, 2.0 / 3.0, 1.0));
//                telemetry.addData("block right", isPurpleRight);
                if (!isPurpleLeft && !isPurpleRight) {
                    telemetry.addData("position", "right");
                } else if (!isPurpleLeft && isPurpleRight) {
                    telemetry.addData("position", "middle");
                } else if (isPurpleLeft && !isPurpleRight) {
                    telemetry.addData("position", "left");
                } else if (isPurpleLeft && isPurpleRight) {
                    telemetry.addData("position", "?");
                }
                telemetry.update();
            }
        }
//        int cooldown = 0;
//        while (opModeIsActive()) {
//            if (cooldown <= 0) {
//                printTotalLuminance();
//                cooldown = 30;
//            }
//            if (cooldown > 0){
//                cooldown--;
//            }
//        }

        hm.closeCamera();
    }

    float[] hslInImageSector(Bitmap image, double imXStart, double imXEnd, double imYStart, double imYEnd) {
        int xStart = (int) (image.getWidth() * imXStart);
        int xEnd = (int) (image.getWidth() * imXEnd);
        int yStart = (int) (image.getHeight() * imYStart);
        int yEnd = (int) (image.getHeight() * imYEnd);

        int r = 0, g = 0, b = 0;
        int count = 0;

        for (int i = xStart; i < xEnd; i++) {
            for (int j = yStart; j < yEnd; j++) {
                count++;
                int pixel = image.getPixel(i, j);
                r += Color.red(pixel);
                g += Color.green(pixel);
                b += Color.blue(pixel);
            }
        }

        int rT = r / count;
        int gT = g / count;
        int bT = b / count;
        float[] hsl = hsl(rT, gT, bT);
//        telemetry.addLine("hsl: " + hsl[0] + ", " + hsl[1] + ", " + hsl[2]);
//        return isPurple(rT, gT, bT);
        return hsl;
    }

    static boolean blockLeft(float[] hsl) {
        boolean sInRange = hsl[1] < 40 && hsl[1] > 10;
        boolean hInRange = (hsl[0] < 15) || (hsl[0] > 320);
        return sInRange && hInRange;
    }

    static boolean blockRight(float[] hsl) {
        boolean sInRange = hsl[1] > 20 && hsl[1] < 35;
        boolean hInRange = hsl[0] > 250 && hsl[0] < 365;
        return sInRange && hInRange;
    }

    static float[] hsl(int rIn, int gIn, int bIn) {
        float r = (float) (rIn / 255.0);
        float g = (float) (gIn / 255.0);
        float b = (float) (bIn / 255.0);

        //	Minimum and Maximum RGB values are used in the HSL calculations
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        //  Calculate the Hue
        float h = 0;

        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * (b - r) / (max - min)) + 120;
        } else if (max == b) {
            h = (60 * (r - g) / (max - min)) + 240;
        }

        //  Calculate the Luminance
        float l = (max + min) / 2;

        //  Calculate the Saturation
        float s = 0;

        if (max == min) {
            s = 0;
        } else if (l <= .5f) {
            s = (max - min) / (max + min);
        } else {
            s = (max - min) / (2 - max - min);
        }

        return new float[]{h, s * 100, l * 100};
    }

//    static Rectangle stone1 = new Rectangle(  0, 0, 100, 100);
//    static Rectangle stone2 = new Rectangle(100, 0, 100, 100);
//    static Rectangle stone3 = new Rectangle(200, 0, 100, 100);

//    private void printTotalLuminance() {
//        Bitmap image = hm.getLatestFrame();
//
//        if (image != null) {
//            double stone1L = luminanceInRegion(image, stone1);
//            double stone2L = luminanceInRegion(image, stone2);
//            double stone3L = luminanceInRegion(image, stone3);
//            telemetry.addData("stone 1", stone1L);
//            telemetry.addData("stone 2", stone2L);
//            telemetry.addData("stone 3", stone3L);
//            String brightest = "";
//            if (stone1L > stone2L && stone1L > stone3L) brightest = "stone 1";
//            if (stone2L > stone1L && stone2L > stone3L) brightest = "stone 2";
//            if (stone3L > stone1L && stone3L > stone1L) brightest = "stone 1";
//            telemetry.addData("capstone at", brightest);
//            telemetry.clear();
//            telemetry.addData("width", image.getWidth());
//            telemetry.addData("height", image.getHeight());
//            telemetry.addData("im", images++);
//            telemetry.update();
//
//        } else {
//            telemetry.log().add("didn't get an image");
//        }
//    }

//    private static class Rectangle {
//        public int x, y, width, height;
//
//        Rectangle(int x, int y, int width, int height) {
//            this.x = x;
//            this.y = y;
//            this.width = width;
//            this.height = height;
//        }
//    }
//
//    private double luminanceInRegion(Bitmap image, Rectangle region) {
//        int pixels = region.width * region.height;
//        double totalL = 0;
//        for (int i = region.x; i < region.x + region.width; i++) {
//            for (int j = region.y; j < region.y + region.height; j++) {
//                totalL += luminance(image.getPixel(i, j));
//            }
//        }
//        return totalL / pixels;
//    }
//
//    private double luminance(int p) {
//        return ((double) Color.red(p) +
//                (double) Color.green(p) +
//                (double) Color.blue(p)) / (3.0 * 255.0);
//    }

}
