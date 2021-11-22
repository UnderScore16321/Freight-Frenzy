package org.firstinspires.ftc.teamcode.teleops.arcade_drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Side;

@TeleOp(name = "Blue Arcade Drive", group = "Competition")
public class BlueArcadeDrive extends ArcadeDriveBase {
    @Override
    protected Side getSide() {
        return Side.BLUE;
    }
}
