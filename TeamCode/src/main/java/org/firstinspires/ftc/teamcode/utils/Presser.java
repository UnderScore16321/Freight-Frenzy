package org.firstinspires.ftc.teamcode.utils;

/**
 * A utility class to determine if a button was pressed.
 */
public class Presser {
    private boolean lastState = false;

    /**
     * Returns if the button was pressed based on its current state. This method must be called
     * continuously in order for it to be accurate.
     * @param currentBtnState The state of the button now.
     * @return If the button was just pressed.
     */
    public boolean wasPressed(boolean currentBtnState) {
        if(currentBtnState && !lastState) {
            lastState = true;
            return true;
        } else if (!currentBtnState) {
            lastState = false;
        }
        return false;
    }
}
