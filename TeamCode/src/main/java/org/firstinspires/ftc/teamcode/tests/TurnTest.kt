package org.firstinspires.ftc.teamcode.tests

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import kotlin.Throws
import org.firstinspires.ftc.teamcode.tests.TurnTest.TurnHardware
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.utils.Hardware
import kotlin.math.abs

@TeleOp
@Disabled
class TurnTest : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val hw = TurnHardware(this)
        hw.waitForImuCalibration(true)
        waitForStart()
        hw.turnToHeading(90.0)
        hw.turnToHeading(270.0)
        hw.turnToHeading(0.0)
    }

    internal class TurnHardware(opMode: LinearOpMode?) : Hardware(opMode, false) {
        @Throws(InterruptedException::class)
        fun turnToHeading(angle: Double) {
            var error = headingError(angle)
            val totalTime = ElapsedTime()
            val loopTime = ElapsedTime()
            var lastTime = 0.0
            var integralSum = 0.0
            var lastError = 0.0
            while (!opMode.isStopRequested && abs(error) > 0.5) {
                error = headingError(angle)
                val derivative = (error - lastError) / loopTime.seconds()
                integralSum += error * loopTime.seconds()
                opMode.telemetry.addData("error", error)
                opMode.telemetry.update()
                if (totalTime.seconds() - lastTime > 0.05) {
                    println("\t" + totalTime.seconds() + "\t" + robotHeading() + "\t" + error + "\t" + P * error + "\t" + I * integralSum + "\t" + D * derivative)
                    lastTime = totalTime.seconds()
                    //                    System.out.println("PID: " + (P * error) + ", " + (I * integralSum) + ",  " + (D * derivative));
                }
                var power = P * error + I * integralSum + D * derivative
                power = if (power > 0) {
                    Math.max(power, MIN_SPEED)
                } else {
                    Math.min(power, -MIN_SPEED)
                }
                setWheelPower(power, -power)
                lastError = error
                loopTime.reset()
            }
            setWheelPower(0.0)
            Thread.sleep(300)
        }

        private fun headingError(toAngle: Double): Double {
            return normalizeHeading(toAngle - robotHeading())
        }

        private fun normalizeHeading(angle: Double): Double {
            var correctedAngle = angle
            while (correctedAngle > 180) correctedAngle -= 360.0
            while (correctedAngle < -180) correctedAngle += 360.0
            return correctedAngle
        }

        companion object {
            private const val P = 0.06
            private const val I = 0.001
            private const val D = 0.002
            private const val MIN_SPEED = 0.3
        }
    }
}