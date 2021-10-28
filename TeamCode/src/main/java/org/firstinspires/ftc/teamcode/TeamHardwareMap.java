package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class TeamHardwareMap {
    private final DcMotor leftFront;
    private final DcMotor leftBack;
    private final DcMotor rightFront;
    private final DcMotor rightBack;

    TeamHardwareMap(LinearOpMode opMode) {
        leftFront = opMode.hardwareMap.dcMotor.get("left_front");
        leftBack = opMode.hardwareMap.dcMotor.get("left_back");
        rightFront = opMode.hardwareMap.dcMotor.get("right_front");
        rightBack = opMode.hardwareMap.dcMotor.get("right_back");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power) { setPower(power, power); }

    public void setPower(double left, double right) {
        leftFront.setPower(left);
        leftBack.setPower(left);
        rightFront.setPower(right);
        rightBack.setPower(right);
    }
}
