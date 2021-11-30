package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.Telemetry

/**
 * A base class for opmodes. It provides access to a [Hardware] and [AdvancedGamepad]s.
 */
abstract class BaseOpMode<HardwareType: Hardware> : LinearOpMode() {
    protected lateinit var hw: HardwareType private set
    protected lateinit var aGamepad1: AdvancedGamepad private set
    protected lateinit var aGamepad2: AdvancedGamepad private set

    protected abstract fun getNewHardware(): HardwareType

    override fun runOpMode() {
        aGamepad1 = AdvancedGamepad(gamepad1)
        aGamepad2 = AdvancedGamepad(gamepad2)
        hw = getNewHardware()
        beforeStart()
        waitForStart()
        mainOpMode()
    }

    protected open fun beforeStart() {}

    protected abstract fun mainOpMode()

    fun Telemetry.print(value: Any) {
        this.addLine(value.toString())
        this.update()
    }

    fun Telemetry.printData(label: String, value: Any) {
        this.addData(label, value)
        this.update()
    }
}