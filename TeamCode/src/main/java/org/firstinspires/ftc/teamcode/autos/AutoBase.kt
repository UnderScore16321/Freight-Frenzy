package org.firstinspires.ftc.teamcode.autos

import org.firstinspires.ftc.teamcode.utils.BaseOpMode

abstract class AutoBase: BaseOpMode<AutoHardware>() {
    override fun getNewHardware(): AutoHardware = AutoHardware(this, false)

    override fun beforeStart() {
        super.beforeStart()
        hw.waitForImuCalibration(false)
    }

    protected fun driveFromBackDistance(targetDistance: Double, currentDist: Double, speed: Double? = null) {
        val distToDrive = targetDistance - currentDist
        if (speed != null) {
            hw.driveInches(distToDrive * 0.9, speed)
        } else {
            hw.driveInches(distToDrive * 0.9)
        }
    }

    protected fun driveFromFrontDistance(targetDistance: Double, currentDist: Double, speed: Double? = null) {
        val distToDrive = targetDistance - currentDist
        if (speed != null) {
            hw.driveInches(-distToDrive * 0.9, speed)
        } else {
            hw.driveInches(-distToDrive * 0.9)
        }
    }

}