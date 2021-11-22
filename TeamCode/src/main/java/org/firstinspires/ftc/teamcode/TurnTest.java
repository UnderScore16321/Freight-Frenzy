package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class TurnTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hm = new HardwareMap(this);

        waitForStart();

        hm.turnDegrees(360);
    }
}
