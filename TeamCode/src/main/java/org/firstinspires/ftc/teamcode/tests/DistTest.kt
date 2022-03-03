package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.hardware.DistanceSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.autos.AutoBase
import org.firstinspires.ftc.teamcode.utils.Hardware

@Autonomous
@Disabled
class DistTest: AutoBase() {
    override fun mainOpMode() {
        val dist = hardwareMap[DistanceSensor::class.java, "dist"]

        hw.turnToHeading(90.0)

        val targetDist = 55.0
        val distToDrive = targetDist - dist.getDistance(DistanceUnit.INCH)
        telemetry.addData("dist", distToDrive)
        telemetry.update()

        hw.driveInches(-distToDrive * 0.9)
        hw.turnToHeading(-20.0)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        hw.driveInches(12.0)

        while (opModeIsActive());
    }
}