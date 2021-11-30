package org.firstinspires.ftc.teamcode.autos

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.utils.Hardware

@Autonomous(name = "[Red Right] Simple Auto (16/36)", group = "Competition")
class RedRightSimpleAuto : AutoBase() {

    override fun beforeStart() {
        hw.grabberIsOpen = false
    }

    override fun mainOpMode() {
        hw.resetGrabberEncoder()

        // drive to goal
        hw.driveInches(18.0)
        hw.turnDegrees(-30.0)

        //deposit on goal
        hw.grabberHeight = Hardware.GrabberHeight.FIRST_LEVEL
        hw.driveInches(7.0)
        hw.grabberIsOpen = true

        //drive to storage unit
        hw.driveInches(-7.0)
        hw.turnDegrees(120.0)
        hw.driveInches(43.0)
    }
}