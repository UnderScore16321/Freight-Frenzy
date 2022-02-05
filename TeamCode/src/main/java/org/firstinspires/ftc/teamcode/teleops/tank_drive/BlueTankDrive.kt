package org.firstinspires.ftc.teamcode.teleops.tank_drive

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp(name = "Blue Tank Drive")
@Disabled
class BlueTankDrive : TankDriveBase() {
    override val side: Side = Side.BLUE
}