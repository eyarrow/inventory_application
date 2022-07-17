package masterinventory.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static masterinventory.JavaFXApplication.createpartIndex;
import static masterinventory.JavaFXApplication.main;

/**
 * Class that provides the controller for the Add Part UI view.
 */
public class AddPartController implements Initializable {

    //JavaFX required Stage and scene objects
    Stage stage;
    Parent scene;


    //Object declaration for error handling
    ErrorHandling error = new ErrorHandling();


    @FXML
    private RadioButton radioAddPartInHouse;

    @FXML
    private ToggleGroup sourceTG;

    @FXML
    private RadioButton radioAddPartOutsourced;

    @FXML
    private TextField txtPartId;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtPartInv;

    @FXML
    private TextField txtPartPrice;

    @FXML
    private TextField txtPartMax;

    @FXML
    private TextField txtPartMin;

    @FXML
    private Label lblSelectorSpecific;

    @FXML
    private TextField txtSelectorSpecific;

    @FXML
    private Button buttonAddPartSave;

    @FXML
    private Button buttonAddPartCancel;

    @FXML
    private Button buttonAddPartClear;

    /**
     * Creating a mouse event handler
     */
    //EventHandler<Mou>

    /**
     * Returns to the Main menu when the "cancel" button is pressed. Prompts the user to see if
     * they indeed would like to cancel.
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws Exception{
        if(error.confirmationDialogue(confirmationCodes.CANCELWARN)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
            stage.setScene(new Scene(scene, 1070, 400));
            stage.setTitle("Return to Main Menu");
            stage.show();
        }

    }

    /**
     * Clears the form field when the "clear" button is pressed. Confirmation is requested.
     * @param event
     */
    @FXML
    void onActionClear(ActionEvent event) {
        if(error.confirmationDialogue(confirmationCodes.CLEARITEMS)) {
            txtPartName.clear();
            txtPartPrice.clear();
            txtPartInv.clear();
            txtPartMin.clear();
            txtPartMax.clear();
        }

    }

    /**
     * Adjusts the display label to Machine ID when the InHouse radio button is selected
     * @param event
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        lblSelectorSpecific.setText("Machine ID");
    }

    /**
     * Adjusts the display label to Company Name when the "outsourced" radio button is selected.
     * @param event
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        lblSelectorSpecific.setText("Company Name");
    }

    /**
     *
     * RUNTIME ERROR - Found that if an input error occurred (for instance, a string valid was entered rather than
     * a double, a NumberFormatException would occur. To deal with this error, I used a try/catch statement
     * to catch the exception, and return a dialogue to the user that requires them to use the appropriate
     * data type.
     * @param event
     * @throws IOException, displays an error dialogue for format validation (catches NumberFormatException).
     * Also sends input through validation checks Error Handling
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        boolean validation = false;
        //if Outsourced create new object Outsourced
        Alert alert;
        Outsourced outPart;
        InHouse inPart;
        try {
            int index = createpartIndex();
            String name = txtPartName.getText();
            double price = Double.parseDouble(txtPartPrice.getText());
            int inventory = Integer.parseInt(txtPartInv.getText());
            int min = Integer.parseInt(txtPartMin.getText());
            int max = Integer.parseInt(txtPartMax.getText());
            if (radioAddPartInHouse.isSelected()) {
                int machineID = Integer.parseInt(txtSelectorSpecific.getText());
                inPart = new InHouse(index, name, price, inventory, min, max, machineID);
                //Run through validation
                validation = error.partValueValidation(inPart);
                if(validation) {
                    Inventory.addPart(inPart);
                }

            } else {
                String companyName = txtSelectorSpecific.getText();
                outPart = new Outsourced(index, name, price, inventory, min, max, companyName);
                //Run through validation
                validation = error.partValueValidation(outPart);
                if (validation) {
                    Inventory.addPart(outPart);
                }
            }
        } catch (NumberFormatException e) {
            error.errorHandling(errorCode.VALIDVALUES);
        }

        //if part passed validation, and was added, display the main menu
        if(validation) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
            stage.setScene(new Scene(scene, 1070, 400));
            stage.setTitle("Main Menu");
            stage.show();
        }


    }


    /*
     *   Initializes the AddPartController class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


}
