//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//@TeleOp(name = "Gyro Test")
//public class GyroTest extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.loggingEnabled      = false;
//
//        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
//        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
//        // and named "imu".
//        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
//        imu.initialize(parameters);
//
//        telemetry.addData("Status", "calibrating");
//        telemetry.update();
//
//        while (!isStopRequested() && !imu.isGyroCalibrated()) {
//            sleep(100);
//            idle();
//        }
//
//        telemetry.addData("Status", "ready");
//        telemetry.update();
//
//        HardwareMap hm = new HardwareMap(this);
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            float heading = imu.getAngularOrientation().firstAngle;
//            telemetry.addData("heading", heading);
//            if (heading < -1) {
//                hm.setWheelPower(0.6, -0.6);
//            } else if (heading > 1) {
//                hm.setWheelPower(-0.6, 0.6);
//            } else {
//                hm.setWheelPower(0);
//            }
//
//            telemetry.update();
//        }
//    }
//}
