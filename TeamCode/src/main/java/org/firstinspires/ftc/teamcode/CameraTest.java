//package org.firstinspires.ftc.teamcode;
//
//import android.graphics.Bitmap;
//import android.graphics.Color;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//@TeleOp
//public class CameraTest extends LinearOpMode {
//    TeamHardwareMap hm;
//
//    @Override
//    public void runOpMode() {
//        hm = new TeamHardwareMap(this);
//
//        waitForStart();
//
//        int cooldown = 0;
//        while (opModeIsActive()) {
//            if (gamepad1.a && cooldown <= 0) {
//                printTotalLuminance();
//                cooldown = 30;
//            }
//            if (cooldown > 0){
//                cooldown--;
//            }
//        }
//
//        hm.camera.closeCamera();
//    }
//
//    static Rectangle stone1 = new Rectangle(  0, 0, 100, 100);
//    static Rectangle stone2 = new Rectangle(100, 0, 100, 100);
//    static Rectangle stone3 = new Rectangle(200, 0, 100, 100);
//
//    private void printTotalLuminance() {
//        Bitmap image = hm.camera.getLatestFrame();
//
//        telemetry.clear();
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
//
//        } else {
//            telemetry.log().add("didn't get an image");
//        }
//        telemetry.update();
//    }
//
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
//
//}
