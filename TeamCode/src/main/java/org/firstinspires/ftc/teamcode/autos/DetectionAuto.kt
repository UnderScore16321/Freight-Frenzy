package org.firstinspires.ftc.teamcode.autos

import org.firstinspires.ftc.teamcode.utils.PosDetector

abstract class DetectionAuto : AutoBase() {

    protected lateinit var posDetector: PosDetector

    abstract fun getNewPosDetector(): PosDetector

    override fun getNewHardware() = AutoHardware(this, true)

    override fun beforeStart() {
        super.beforeStart()
        telemetry.clearAll()
        telemetry.printData("status", "camera initializing")
        posDetector = getNewPosDetector()

        telemetry.clearAll()
        telemetry.printData("status", "camera calibrating")
        sleep(5000)

        telemetry.clearAll()
        telemetry.printData("status", "getting base state")
        posDetector.useBaseStates(arrayListOf(hw.forceGetFrame()))

        telemetry.clearAll()
        telemetry.printData("status", "ready")
    }

    protected fun detectedPositionNow(): Int = posDetector.regionOfImage(hw.forceGetFrame())

}