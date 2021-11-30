package org.firstinspires.ftc.teamcode.teleops

import org.firstinspires.ftc.teamcode.utils.BaseOpMode

/**
 * Specializes [BaseOpMode] to use a [TeleopHardware]
 */
abstract class TeleopBase : BaseOpMode<TeleopHardware>() {
    override fun getNewHardware() = TeleopHardware(this)
}