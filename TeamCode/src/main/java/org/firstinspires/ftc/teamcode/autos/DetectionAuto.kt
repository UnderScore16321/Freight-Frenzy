package org.firstinspires.ftc.teamcode.autos

import org.firstinspires.ftc.teamcode.utils.BlackPosDetector

abstract class DetectionAuto : AutoBase() {

    protected lateinit var posDetector: BlackPosDetector

    abstract fun getNewPosDetector(): BlackPosDetector

    override fun getNewHardware() = AutoHardware(this, true)

    override fun beforeStart() {
        super.beforeStart()
        posDetector = getNewPosDetector()
    }

    protected fun detectedPositionNow(): Int = posDetector.regionOfImage(hw.forceGetFrame())

}