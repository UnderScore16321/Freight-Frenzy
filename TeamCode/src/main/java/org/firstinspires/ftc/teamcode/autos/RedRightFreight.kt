package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.utils.Hardware
import org.firstinspires.ftc.teamcode.utils.PosDetector

@Autonomous(name = "Red Right Freight", preselectTeleOp = "Red Tank Drive")
class RedRightFreight : DetectionAuto() {
    override fun getNewPosDetector(): PosDetector = PosDetectorFactory.redRight(telemetry)

    override fun mainOpMode() {
        telemetry.clearAll()
        telemetry.update()

        hw.grabberIsOpen = false

        // drop freight
        val height = when (detectedPositionNow()) {
            0 -> Hardware.GrabberHeight.FIRST_LEVEL
            1 -> Hardware.GrabberHeight.SECOND_LEVEL
            else -> Hardware.GrabberHeight.THIRD_LEVEL
        }
        hw.driveInches(5.0)
        hw.grabberHeight = height
        hw.turnToHeading(-25.0)
        hw.driveInches(12.0)
        hw.grabberIsOpen = true

        //pick up next freight
        hw.driveInches(-7.0)
        hw.grabberHeight = Hardware.GrabberHeight.SECOND_LEVEL
        hw.turnToHeading(70.0)
        hw.driveInches(40.0)
        hw.grabberHeight = Hardware.GrabberHeight.DOWN
        hw.turnToHeading(100.0)
        hw.driveInches(10.0, speedIn = 0.2)
        hw.grabberIsOpen = false
        hw.grabberIsOpen = true
        hw.driveInches(2.0)
        hw.driveInches(-1.0)
        hw.grabberIsOpen = false
        hw.driveInches(-10.0)

        //drop second freight
        hw.grabberHeight = Hardware.GrabberHeight.SECOND_LEVEL
        hw.turnToHeading(90.0)
        hw.driveInches(-30.0)
        hw.turnToHeading(-90.0)
        hw.setWheelPower(-0.4)
        sleep(1000)
        hw.setWheelPower(0.0)

        //drive to hub
        hw.driveInches(5.0)
        hw.grabberHeight = Hardware.GrabberHeight.THIRD_LEVEL
        val backDistance = driveToHub()
        hw.grabberIsOpen = true

        //park
        hw.driveInches(-backDistance)
        hw.turnToHeading(90.0)
        hw.driveInches(30.0)
    }

    private fun driveToHub(): Double {
        val distanceFromSideWall = hw.leftDist.getDistance(DistanceUnit.INCH)

        val distanceDriven = hw.driveToPoint(
            83.0, 48.0,
            55.0, distanceFromSideWall + 6.5,
            30.0
        )

        return distanceDriven
    }

}