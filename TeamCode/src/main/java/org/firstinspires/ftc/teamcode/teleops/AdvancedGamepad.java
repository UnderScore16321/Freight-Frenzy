package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Provides a superior interface for accessing gamepad information.
 */
public class AdvancedGamepad {
    private final Gamepad gamepad;

    AdvancedGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    private final Presser a = new Presser();
    private final Presser b = new Presser();
    private final Presser x = new Presser();
    private final Presser y = new Presser();

    public boolean a() { return gamepad.a; }
    public boolean b() { return gamepad.b; }
    public boolean x() { return gamepad.x; }
    public boolean y() { return gamepad.y; }

    public boolean aPressed() { return a.wasPressed(gamepad.a); }
    public boolean bPressed() { return b.wasPressed(gamepad.b); }
    public boolean xPressed() { return x.wasPressed(gamepad.x); }
    public boolean yPressed() { return y.wasPressed(gamepad.y); }

    private final Presser rBumper = new Presser();
    private final Presser lBumper = new Presser();

    public boolean rightBumper() { return rBumper.wasPressed(gamepad.right_bumper); }
    public boolean leftBumper() { return lBumper.wasPressed(gamepad.left_bumper); }

    public boolean rightBumperPressed() { return gamepad.right_bumper; }
    public boolean leftBumperPressed() { return gamepad.right_bumper; }

    public double rightTrigger() { return gamepad.right_trigger; }
    public double leftTrigger() { return gamepad.left_trigger; }

    private final Presser dUp = new Presser();
    private final Presser dRight = new Presser();
    private final Presser dDown = new Presser();
    private final Presser dLeft = new Presser();

    public boolean dpadUp() { return gamepad.dpad_up; }
    public boolean dpadRight() { return gamepad.dpad_right; }
    public boolean dpadDown() { return gamepad.dpad_down; }
    public boolean dpadLeft() { return gamepad.dpad_left; }

    public boolean dpadUpPressed() { return dUp.wasPressed(gamepad.dpad_up); }
    public boolean dpadRightPressed() { return dRight.wasPressed(gamepad.dpad_right); }
    public boolean dpadDownPressed() { return dDown.wasPressed(gamepad.dpad_down); }
    public boolean dpadLeftPressed() { return dLeft.wasPressed(gamepad.dpad_left); }

    public double leftX() { return gamepad.left_stick_x; }
    public double leftY() { return -gamepad.left_stick_y; }
    public double rightX() { return gamepad.right_stick_x; }
    public double rightY() { return -gamepad.right_stick_y; }
}
