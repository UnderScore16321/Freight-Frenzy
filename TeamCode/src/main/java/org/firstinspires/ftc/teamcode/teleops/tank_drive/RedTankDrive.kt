package org.firstinspires.ftc.teamcode.teleops.tank_drive

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp(name = "Red Tank Drive")
class RedTankDrive : TankDriveBase() {
    override val side: Side = Side.RED
}