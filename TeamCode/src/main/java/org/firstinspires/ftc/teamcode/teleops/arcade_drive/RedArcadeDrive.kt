package org.firstinspires.ftc.teamcode.teleops.arcade_drive

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp(name = "Red Arcade Drive")
@Disabled
class RedArcadeDrive : ArcadeDriveBase() {
    override val side: Side = Side.RED
}