package org.firstinspires.ftc.teamcode.teleops.arcade_drive

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.utils.Side

@TeleOp(name = "Blue Arcade Drive", group = "Competition")
class BlueArcadeDrive : ArcadeDriveBase() {
    override val side: Side = Side.BLUE
}