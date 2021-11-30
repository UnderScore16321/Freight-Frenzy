package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.hardware.Gamepad

/**
 * Provides a superior interface for accessing gamepad information.
 */
class AdvancedGamepad(private val gamepad: Gamepad) {
    private val aPresser = Presser()
    private val bPresser = Presser()
    private val xPresser = Presser()
    private val yPresser = Presser()

    val a: Boolean get() = gamepad.a
    val b: Boolean get() = gamepad.b
    val x: Boolean get() = gamepad.x
    val y: Boolean get() = gamepad.y

    val aPressed: Boolean get() = aPresser.wasPressed(gamepad.a)
    val bPressed: Boolean get() = bPresser.wasPressed(gamepad.b)
    val xPressed: Boolean get() = xPresser.wasPressed(gamepad.x)
    val yPressed: Boolean get() = yPresser.wasPressed(gamepad.y)

    private val rBumperPresser = Presser()
    private val lBumperPresser = Presser()

    val rightBumper: Boolean get() = gamepad.right_bumper
    val leftBumper: Boolean get() = gamepad.left_bumper

    val rightBumperPressed: Boolean get() = rBumperPresser.wasPressed(gamepad.right_bumper)
    val leftBumperPressed: Boolean get() = lBumperPresser.wasPressed(gamepad.left_bumper)

    val rightTrigger: Double get() = gamepad.right_trigger.toDouble()
    val leftTrigger: Double get() = gamepad.left_trigger.toDouble()

    private val dUpPresser = Presser()
    private val dRightPresser = Presser()
    private val dDownPresser = Presser()
    private val dLeftPresser = Presser()

    val dpadUp: Boolean get() = gamepad.dpad_up
    val dpadRight: Boolean get() = gamepad.dpad_right
    val dpadDown: Boolean get() = gamepad.dpad_down
    val dpadLeft: Boolean get() = gamepad.dpad_left

    val dpadUpPressed: Boolean get() = dUpPresser.wasPressed(gamepad.dpad_up)
    val dpadRightPressed: Boolean get() = dRightPresser.wasPressed(gamepad.dpad_right)
    val dpadDownPressed: Boolean get() = dDownPresser.wasPressed(gamepad.dpad_down)
    val dpadLeftPressed: Boolean get() = dLeftPresser.wasPressed(gamepad.dpad_left)

    val leftX: Double get() = gamepad.left_stick_x.toDouble()
    val leftY: Double get() = -gamepad.left_stick_y.toDouble()
    val rightX: Double get() = gamepad.right_stick_x.toDouble()
    val rightY: Double get() = -gamepad.right_stick_y.toDouble()
}