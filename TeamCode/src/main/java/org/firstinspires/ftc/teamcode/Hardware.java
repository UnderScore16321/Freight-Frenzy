package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    protected LinearOpMode opMode;

    public Hardware(LinearOpMode opMode, boolean camera) {
        this.opMode = opMode;

        initWheels();
        initSpinner();
        initGrabber();
        if(camera) initCamera();
    }

    // Wheels --------------------------------------------------------------------------------------

    protected DcMotor leftFront;
    protected DcMotor leftBack;
    protected DcMotor rightFront;
    protected DcMotor rightBack;

    private void initWheels() {
        leftFront = opMode.hardwareMap.dcMotor.get("left front");
        leftBack = opMode.hardwareMap.dcMotor.get("left back");
        rightFront = opMode.hardwareMap.dcMotor.get("right front");
        rightBack = opMode.hardwareMap.dcMotor.get("right back");
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // Spinner -------------------------------------------------------------------------------------

    protected DcMotor spinner;

    private void initSpinner() {
        spinner = opMode.hardwareMap.dcMotor.get("spinner");
    }

    // Grabber -------------------------------------------------------------------------------------

    protected DcMotor grabberMotor;
    protected Servo leftGrabber;
    protected Servo rightGrabber;

    private void initGrabber() {
        grabberMotor = opMode.hardwareMap.dcMotor.get("grabber motor");
        leftGrabber = opMode.hardwareMap.servo.get("left grabber");
        rightGrabber = opMode.hardwareMap.servo.get("right grabber");
    }

    // Camera --------------------------------------------------------------------------------------

    protected SimpleCamera camera;

    private void initCamera() {
        camera = new SimpleCamera("camera", opMode);
    }


}
