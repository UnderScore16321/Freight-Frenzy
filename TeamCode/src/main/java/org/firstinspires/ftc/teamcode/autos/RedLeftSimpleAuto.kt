package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware

@Autonomous(name = "[Red Left] Simple Auto (12/32)", group = "Competition")
class RedLeftSimpleAuto : AutoBase() {

    override fun beforeStart() {
        hw.grabberIsOpen = false
    }

    override fun mainOpMode() {
        hw.resetGrabberEncoder()

        // drive to goal
        hw.driveInches(20.0)
        hw.turnToHeading(35.0)

        //deposit on goal
        hw.grabberHeight = Hardware.GrabberHeight.FIRST_LEVEL
        hw.driveInches(7.0)
        hw.grabberIsOpen = true

        //drive to storage unit
        hw.driveInches(-7.0)
        hw.turnToHeading(-45.0)
        hw.grabberHeight = Hardware.GrabberHeight.SECOND_LEVEL
        hw.driveInches(19.0)
        hw.turnToHeading(-90.0)
        hw.driveInches(8.0)
    }
}