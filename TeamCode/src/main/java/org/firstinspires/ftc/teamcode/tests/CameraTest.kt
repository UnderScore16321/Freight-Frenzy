package org.firstinspires.ftc.teamcode.tests

import android.graphics.Bitmap
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.autos.PosDetectorFactory
import org.firstinspires.ftc.teamcode.teleops.TeleopBase
import org.firstinspires.ftc.teamcode.utils.SimpleCamera

@TeleOp(group = "Test")
class CameraTest : TeleopBase() {
    override fun mainOpMode() {
        val camera = SimpleCamera("camera",this)

        val blueLeft = PosDetectorFactory.blueLeft(telemetry)
        val blueRight = PosDetectorFactory.blueRight(telemetry)
        val redLeft = PosDetectorFactory.redLeft(telemetry)
        val redRight = PosDetectorFactory.redRight(telemetry)

        var image: Bitmap? = camera.latestFrame
        while (image == null) {
            image = camera.latestFrame
        }
        blueLeft.useBaseStates(listOf(image))
        blueRight.useBaseStates(listOf(image))
        redLeft.useBaseStates(listOf(image))
        redRight.useBaseStates(listOf(image))
        while (opModeIsActive()) {
            image = camera.latestFrame

            if (image != null) {
                telemetry.clearAll()
                telemetry.addData("Blue Left", blueLeft.regionOfImage(image))
                telemetry.addData("Blue Right", blueRight.regionOfImage(image))
                telemetry.addData("Red Left", redLeft.regionOfImage(image))
                telemetry.addData("Red Right", redRight.regionOfImage(image))
                telemetry.update()
            }

            if (aGamepad1.xPressed) {
                while (image == null) {
                    image = camera.latestFrame
                }
                blueLeft.useBaseStates(listOf(image))
                blueRight.useBaseStates(listOf(image))
                redLeft.useBaseStates(listOf(image))
                redRight.useBaseStates(listOf(image))
            }
        }
    }
}