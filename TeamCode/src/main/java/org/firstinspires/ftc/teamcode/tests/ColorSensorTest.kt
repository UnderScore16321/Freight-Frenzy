package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DistanceSensor
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.teleops.TeleopBase

@TeleOp
@Disabled
class ColorSensorTest: TeleopBase() {
    override fun mainOpMode() {
        val colorSensor = hardwareMap[DistanceSensor::class.java, "front color"]

        while (opModeIsActive()) {
            telemetry.addData("dist", colorSensor.getDistance(DistanceUnit.INCH))
            telemetry.update()
        }
    }
}