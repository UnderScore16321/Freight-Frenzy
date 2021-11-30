package org.firstinspires.ftc.teamcode.autos

import org.firstinspires.ftc.teamcode.utils.BaseOpMode

abstract class AutoBase: BaseOpMode<AutoHardware>() {
    override fun getNewHardware(): AutoHardware = AutoHardware(this, false)
}