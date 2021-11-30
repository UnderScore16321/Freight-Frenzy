package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.utils.Hardware
import kotlin.math.abs

class AutoHardware(opMode: LinearOpMode, camera: Boolean) : Hardware(opMode, camera) {

    // Driving -------------------------------------------------------------------------------------
    private fun initWheels() {
        leftFront.targetPositionTolerance = 15
        leftBack.targetPositionTolerance = 15
        rightFront.targetPositionTolerance = 15
        rightBack.targetPositionTolerance = 15
    }

    fun setVelocity(speed: Double) {
        setWheelPower(1.0)
        setAllMotorModes(RunMode.RUN_USING_ENCODER)
        leftFront.velocity = speed
        leftBack.velocity = speed
        rightFront.velocity = speed
        rightBack.velocity = speed
    }

    fun driveInches(inches: Double) {
        setWheelPower(1.0)
        setAllMotorModes(RunMode.STOP_AND_RESET_ENCODER)
        setAllTargetPosition(inchesToTicks(inches))
        setAllMotorModes(RunMode.RUN_TO_POSITION)
        waitForEncoders()
    }

    // TURNING: ------------------------------------------------------------------------------------

    private fun Double.coerceAbs(min: Double) = when {
        this >= 0 -> this.coerceAtLeast(min)
        else -> this.coerceAtMost(-min)
    }

    fun turnToHeading(angle: Double) {
        var error = headingError(angle)

        val totalTime = ElapsedTime()
        val loopTime = ElapsedTime()

        var lastError = 0.0
        var integralSum = 0.0

        while (!opMode.isStopRequested && abs(error) > TURN_TOLERANCE) {
            error = headingError(angle)
            val derivative = (error - lastError) / loopTime.seconds()
            integralSum += error * loopTime.seconds()

            opMode.telemetry.addData("error", error)
            opMode.telemetry.update()
            println("${totalTime.seconds()}, $error")

            val power = (P * error + I * integralSum + D * derivative).coerceAbs(MIN_TURN_SPEED)
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

    // ---------------------------------------------------------------------------------------------

    val isEncoderDriving: Boolean
        get() = leftBack.isBusy || leftFront.isBusy || rightBack.isBusy || rightFront.isBusy

    fun waitForEncoders() {
        while (isEncoderDriving) {
            Thread.sleep(100)
        }
    }

    private fun inchesToTicks(inches: Double): Int {
        return (inches * TICKS_PER_INCH).toInt()
    }

    private fun setTargetPositions(lTicks: Int, rTicks: Int) {
        leftFront.targetPosition = lTicks
        leftBack.targetPosition = lTicks
        rightFront.targetPosition = rTicks
        rightBack.targetPosition = rTicks
    }

    private fun setAllTargetPosition(ticks: Int) {
        setTargetPositions(ticks, ticks)
    }

    private fun setAllMotorModes(mode: RunMode) {
        leftFront.mode = mode
        leftBack.mode = mode
        rightFront.mode = mode
        rightBack.mode = mode
    }

    // Grabber -------------------------------------------------------------------------------------
    override fun setGrabberIsOpen(isOpen: Boolean) {
        super.setGrabberIsOpen(isOpen)
        Thread.sleep(300)
    }

    override fun setGrabberTargetPosition() {
        super.setGrabberTargetPosition()
        while (grabberMotor.isBusy) {
            Thread.sleep(100)
        }
    }

    companion object {
        private const val TICKS_PER_ROT = 537.6
        private const val WHEEL_DIAMETER = 5.0
        private const val INCHES_PER_ROT = 3.14159 * WHEEL_DIAMETER
        private const val TICKS_PER_INCH = TICKS_PER_ROT / INCHES_PER_ROT

        private const val P = 0.06
        private const val I = 0.001
        private const val D = 0.002
        private const val MIN_TURN_SPEED = 0.3
        private const val TURN_TOLERANCE = 0.5

    }

    init {
        initWheels()
    }
}