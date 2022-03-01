package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.utils.Hardware
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.sqrt

class AutoHardware(opMode: LinearOpMode, camera: Boolean) : Hardware(opMode, camera) {

    init {
        initWheels()
        initSensors()
    }

    //Sensors
    lateinit var leftDist: DistanceSensor
    lateinit var rightDist: DistanceSensor
    lateinit var backDist: DistanceSensor

    private fun initSensors() {
        leftDist = opMode.hardwareMap[DistanceSensor::class.java, "distance sensor left"]
        rightDist = opMode.hardwareMap[DistanceSensor::class.java, "distance sensor right"]
        backDist = opMode.hardwareMap[DistanceSensor::class.java, "distance sensor back"]
    }

    fun driveToPoint(
        targetX: Double, targetY: Double,
        robotX: Double, robotY: Double,
        driveDistanceOffset: Double
    ): Double {
        val x = targetX - robotX
        val y = targetY - robotY

        val heading = Math.toDegrees(atan2(y, x))
        val distance = sqrt(x * x + y * y) - driveDistanceOffset

        turnToHeading(heading)
        driveInches(distance * 0.95)

        opMode.telemetry.clearAll()
        opMode.telemetry.addData("angle", heading)
        opMode.telemetry.addData("distance", distance)
        opMode.telemetry.update()

        return distance * 0.95
    }


    // Driving -------------------------------------------------------------------------------------
    private fun initWheels() {
        leftFront.targetPositionTolerance = 15
        leftBack.targetPositionTolerance = 15
        rightFront.targetPositionTolerance = 15
        rightBack.targetPositionTolerance = 15

        leftFront.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        leftBack.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightFront.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        rightBack.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setVelocity(speed: Double) {
        setWheelPower(1.0)
        setAllMotorModes(RunMode.RUN_USING_ENCODER)
        leftFront.velocity = speed
        leftBack.velocity = speed
        rightFront.velocity = speed
        rightBack.velocity = speed
    }

    fun driveInches(inches: Double, speedIn: Double = 0.8) {
        val speed = abs(speedIn).coerceIn(0.0, 1.0)
        setWheelPower(speed)
        setAllMotorModes(RunMode.STOP_AND_RESET_ENCODER)
        setAllTargetPosition(inchesToTicks(inches))
        setAllMotorModes(RunMode.RUN_TO_POSITION)

        val startAngle = robotHeading()
        while (!opMode.isStopRequested && anyMotorBusy) {
            // adjust relative speed based on heading error.
            val error = headingError(startAngle)
            var steer = (error * DRIVE_P).coerceIn(-1.0, 1.0)

            // if driving in reverse, the motor correction also needs to be reversed
            if (inches < 0) steer *= -1.0

            var leftSpeed = (speed + steer).addToAbs(MIN_DRIVE_SPEED)
            var rightSpeed = (speed - steer).addToAbs(MIN_DRIVE_SPEED)

            // Normalize speeds if either one exceeds +/- 1.0;
            val max = max(abs(leftSpeed), abs(rightSpeed))
            if (max > 1.0) {
                leftSpeed /= max
                rightSpeed /= max
            }

            setWheelPower(leftSpeed, rightSpeed)
        }

        setAllMotorModes(RunMode.RUN_WITHOUT_ENCODER)
        setWheelPower(0.0)
        turnToHeading(startAngle)
        Thread.sleep(500)
    }


    private fun Double.addToAbs(min: Double, max: Double = 1.0) = when {
        this >= 0 -> (this + min).coerceAtMost(max)
        else -> (this - min).coerceAtLeast(-max)
    }

    // TURNING: ------------------------------------------------------------------------------------

    fun turnToHeading(angle: Double) {
        var time = ElapsedTime()
        setAllMotorModes(RunMode.RUN_WITHOUT_ENCODER)

        var error = headingError(angle)

        val totalTime = ElapsedTime()
        val loopTime = ElapsedTime()

        var lastError = 0.0
        var integralSum = 0.0

        while ((time.milliseconds() < 1000) && !opMode.isStopRequested && (abs(error) > TURN_TOLERANCE || !wheelsAreStopped())) {
            error = headingError(angle)
            if (error > 5 || error < -5) time = ElapsedTime()
            val derivative = (error - lastError) / loopTime.seconds()
            integralSum += error * loopTime.seconds()

            opMode.telemetry.addData("error", error)
            opMode.telemetry.update()
            println("${totalTime.seconds()}, $error")

            val power = (TURN_P * error + TURN_I * integralSum + TURN_D * derivative).addToAbs(
                MIN_TURN_SPEED, MAX_TURN_SPEED
            ) * 0.8
            setWheelPower(power, -power)

            lastError = error
            loopTime.reset()
        }
        setWheelPower(0.0)
    }

    private fun wheelsAreStopped(): Boolean =
        leftFront.velocity < 50.0 &&
                leftBack.velocity < 50.0 &&
                rightFront.velocity < 50.0 &&
                rightBack.velocity < 50.0

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

    val anyMotorBusy: Boolean
        get() = leftBack.isBusy && leftFront.isBusy && rightBack.isBusy && rightFront.isBusy

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
        Thread.sleep(700)
    }

    override fun stowGrabbers() {
        super.stowGrabbers()
        Thread.sleep(700)
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

        private const val DRIVE_P = 0.02
        private const val MIN_DRIVE_SPEED = 0.3

        private const val TURN_P = 0.04

        //        private const val TURN_I = 0.0004
//        private const val TURN_D = 0.0025
        private const val TURN_I = 0
        private const val TURN_D = 0.001
        private const val MIN_TURN_SPEED = 0.2
        private const val MAX_TURN_SPEED = 0.4
        private const val TURN_TOLERANCE = 1.0

    }
}