package model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * A class to manage confirmation and alert dialogue boxes
 * Also provides error checking for Product and Part types to meet program requirements
 */
public class ErrorHandling {

    //Error Messages (Input Validation
    private String maxGreaterThanMin = "Max must be greater than Min. ";
    private String positiveValuesOnly = "Max, Min, Price, and Stock Level must be positive values. ";
    private String stockLevel = "The amount in inventory must be more than Min but less than Max. ";
    private String validValueException = "Please enter a valid value for each text field. ";
    private String noAssociatedPartSelected = "You did not select a part to add. Please try again. ";
    private String alreadyAssociatedPart = "This part is already associated with this product. ";
    private String noItemToDelete = "You did not select an item to delete. Select it from the listed items. ";
    private String associatedPartsDeletionError = "This product has associated parts. Modify the product by removing associated parts. ";
    private String noItemToModify = "You didn't select an item to modify. Select it from the listed items. ";
    private String searchFail = "The item you were looking for could not be found in the Inventory. Please try again. ";


    //Confirmations
    private String cancelWarning = "Are you sure you would like to Cancel and return to the main Menu?";
    private String removePart = "Are you sure you want to remove this part?";
    private String removeProduct = "Are you sure you want to remove this product";
    private String endProgram = "Are you sure you would like to exit the program?";
    private String addMoreItems = "Click ok if you would like to add more items, or cancel if you would like to return to the main menu?";
    private String clearItems = "Are you sure you want to clear all of your entries?";



    /**
     * Displays an error alert to the user. The message shown in the dialogue box is based on the
     * valid enumerated value passed in by the calling method.
     * @param Error - enum value that corresponds to the type of error checking that needs to be done.
     *              Valid values:
     *
     * @return a true value if the user clicked "Ok". Returns false if they select Cancel.
     */
    public void errorHandling(errorCode error) {
        String message = checkErrorCode(error);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialogue");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialogue with an ok button that checks if the user wants to proceed.
     * The text of the dialogue is a string that reflects the value of the enumerated value that is
     * passed in by the calling method.
     * @param code -enum of confirmationCodes type - represents the possible confirmation dialogue messages.
     * @return a boolean of true if the user presses the ok button. If cancel is pressed it returns false.
     */
    public boolean confirmationDialogue(confirmationCodes code) {
        //capture the correct confirmation message based on provided enum
        String message = this.checkConfirmationCode(code);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Evaluates the enumeration value passed in, and returns the String
     * that should be displayed in Confirmation dialogue popups.
     * @param code - enumeration value passed in
     * @return the string to display in the confirmation alert box
     */
    private String checkConfirmationCode (confirmationCodes code) {
        confirmationCodes passedValue = code;

        switch (passedValue) {
            case CANCELWARN:
                return this.cancelWarning;
            case ENDPROGRAM:
                return this.endProgram;
            case REMOVEPART:
                return this.removePart;
            case REMOVEPRODUCT:
                return this.removeProduct;
            case CLEARITEMS:
                return this.clearItems;
            default:
                return null;
        }
    }

    /**
     * Generates error strings to be displayed in dialogues based on the enum value provided
     * by the calling method.
     * @param code - an enumeration of errorCode type that reflects the type of error
     * @return the associated string that should be displayed to the user based on the error
     */
    private String checkErrorCode (errorCode code) {
        errorCode input = code;

        switch (input) {
            case MAXMINVAL:
                return this.maxGreaterThanMin;
            case POSVALUES:
                return this.positiveValuesOnly;
            case SEARCHFAIL:
                return this.searchFail;
            case STOCKLEVEL:
                return this.stockLevel;
            case VALIDVALUES:
                return this.validValueException;
            case NOITEMTODELETE:
                return this.noItemToDelete;
            case ASSOCPARTDELERR:
                return this.associatedPartsDeletionError;
            case ALREADYASSOCPART:
                return this.alreadyAssociatedPart;
            case NOMODIFYSELECTED:
                return this.noItemToModify;
            case ASSOCPARTSELECTED:
                return this.noAssociatedPartSelected;
            default:
                return null;
        }
    }

    /**
     * Performs part validation to check for the following:
     * 1. Max, Min, Price or Stock cannot be negative numbers
     * 2. Max must be greater than Min
     * 3. Stock cannot be less than Min, or greater or equal to max
     * Displays error dialogues if any of these conditions exist, else, it allows the user
     * to continue with adding the record.
     * @param item to validate
     * @return true if item passes validation, false if it has an error
     */
    public boolean partValueValidation(Part item) {
        if (item.getMax() < 0 || item.getMin() < 0 || item.getPrice() < 0 || item.getStock() < 0) {
            this.errorHandling(errorCode.POSVALUES);
            return false;
        }
        else if(item.getMax() < item.getMin()) {
            this.errorHandling(errorCode.MAXMINVAL);
            return false;
        }
        else if(item.getStock() < item.getMin() || item.getStock() > item.getMax()) {
            this.errorHandling(errorCode.STOCKLEVEL);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Performs product validation to check for the following:
     * 1. Max, Min, Price or Stock cannot be negative numbers
     * 2. Max must be greater than Min
     * 3. Stock cannot be less than Min, or greater or equal to max
     * Displays error dialogues if any of these conditions exist, else, it allows the user
     * to continue with adding the record.
     * @param item to validate
     * @return true if item passes validation, false if it has an error
     */
    public boolean productValueValidation(Product item) {
        if (item.getMax() < 0 || item.getMin() < 0 || item.getPrice() < 0 || item.getStock() < 0) {
            this.errorHandling(errorCode.POSVALUES);
            return false;
        }
        else if(item.getMax() < item.getMin()) {
            this.errorHandling(errorCode.MAXMINVAL);
            return false;
        }
        else if(item.getStock() < item.getMin() || item.getStock() > item.getMax()) {
            this.errorHandling(errorCode.STOCKLEVEL);
            return false;
        }
        else {
            return true;
        }
    }



}
