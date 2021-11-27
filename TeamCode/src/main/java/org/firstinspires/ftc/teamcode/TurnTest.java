package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
@Disabled
public class TurnTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        TeamHardwareMap hm = new TeamHardwareMap(this);

        waitForStart();

        hm.turnDegrees(360);
    }
}
