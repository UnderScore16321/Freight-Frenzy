package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TeamHardwareMap {
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    public SimpleCamera camera;

    TeamHardwareMap(LinearOpMode opMode) {
//        leftFront = opMode.hardwareMap.dcMotor.get("left_front");
//        leftBack = opMode.hardwareMap.dcMotor.get("left_back");
//        rightFront = opMode.hardwareMap.dcMotor.get("right_front");
//        rightBack = opMode.hardwareMap.dcMotor.get("right_back");
//
//        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
//        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        camera = new SimpleCamera("webcam", opMode);
    }

    public void setPower(double power) { setPower(power, power); }

    public void setPower(double left, double right) {
//        leftFront.setPower(left);
//        leftBack.setPower(left);
//        rightFront.setPower(right);
//        rightBack.setPower(right);
    }
}
