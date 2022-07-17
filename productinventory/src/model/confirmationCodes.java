package model;

/**
 * Enumerations that represent valid confirmation codes. Used to display confirmation dialogues during runtime.
 * 1. CANCELWARN - cancel & return to main menu
 * 2. REMOVEPART - remove part confirmation
 * 3. REMOVEPRODUCT - remove product confirmation
 * 4. ENDPROGRAM - are you sure you want to exit?
 * 5. CLEARITEMS - are you sure you want to clear all of the text fields?
 */
public enum confirmationCodes {
    CANCELWARN,
    REMOVEPART,
    REMOVEPRODUCT,
    ENDPROGRAM,
    CLEARITEMS
}
