package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware;

import java.util.EventListener;

@TeleOp
public class TurnTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        TurnHardware hw = new TurnHardware(this);

        hw.waitForImuCalibration(true);

        waitForStart();

        hw.turnToHeading(90);
        hw.turnToHeading(270);
        hw.turnToHeading(0);
    }

    static class TurnHardware extends Hardware {
        TurnHardware(LinearOpMode opMode) throws InterruptedException {
            super(opMode, false);
        }

        private static final double P = 0.06;
        private static final double I = 0.001;
        private static final double D = 0.002;
        private static final double MIN_SPEED = 0.3;

        public void turnToHeading(double angle) throws InterruptedException {
            double error = headingError(angle);

            ElapsedTime totalTime = new ElapsedTime();
            ElapsedTime loopTime = new ElapsedTime();
            double lastTime = 0;

            double integralSum = 0;
            double lastError = 0;

            while (!opMode.isStopRequested() && Math.abs(error) > 0.5) {
                error = headingError(angle);

                double derivative = (error - lastError) / loopTime.seconds();
                integralSum += error * loopTime.seconds();

                opMode.telemetry.addData("error", error);
                opMode.telemetry.update();
                if (totalTime.seconds() - lastTime > 0.05) {
                    System.out.println("\t" + totalTime.seconds() + "\t" + robotHeading() + "\t" + error + "\t" + (P * error) + "\t" + (I * integralSum) + "\t" + (D * derivative));
                    lastTime = totalTime.seconds();
//                    System.out.println("PID: " + (P * error) + ", " + (I * integralSum) + ",  " + (D * derivative));
                }

                double power = (P * error) + (I * integralSum) + (D * derivative);
                if (power > 0) {
                    power = Math.max(power, MIN_SPEED);
                } else {
                    power = Math.min(power, -MIN_SPEED);
                }

                setWheelPower(power, -power);

                lastError = error;
                loopTime.reset();
            }
            setWheelPower(0);
            Thread.sleep(300);
        }

        private double headingError(double toAngle) {
            return normalizeHeading(toAngle - robotHeading());
        }

        private double normalizeHeading(double angle) {
            double correctedAngle = angle;
            while (correctedAngle > 180) correctedAngle -= 360;
            while (correctedAngle < -180) correctedAngle += 360;
            return correctedAngle;
        }
    }
}
