package org.firstinspires.ftc.teamcode.teleops.tank_drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Side;

@TeleOp(name = "Red Tank Drive", group = "Competition")
public class RedTankDrive extends TankDriveBase {
    @Override
    protected Side getSide() {
        return Side.RED;
    }
}
