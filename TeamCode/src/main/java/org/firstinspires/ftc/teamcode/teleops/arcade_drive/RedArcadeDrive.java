package org.firstinspires.ftc.teamcode.teleops.arcade_drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Side;

@TeleOp(name = "Red Arcade Drive", group = "Competition")
public class RedArcadeDrive extends ArcadeDriveBase {
    @Override
    protected Side getSide() {
        return Side.RED;
    }
}
