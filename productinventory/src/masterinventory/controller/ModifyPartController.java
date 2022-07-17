package masterinventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static masterinventory.JavaFXApplication.createpartIndex;

/**
 * Class that provides the controller for the Modify Part UI view.
 */
public class ModifyPartController implements Initializable {
    //JavaFX - declares needed objects
    Stage stage;
    Parent scene;

    //Creates and object to "catch" passed parameters
    private static Part passedParameters;

    //Declares object for error handling methods
    ErrorHandling error = new ErrorHandling();

    /**
     * Passes saved part parameters from MainMenuController to ModifyPartController
     * @param item
     */
    public static void passParameters(Part item) {
        passedParameters = item;
    }

    @FXML
    private RadioButton radioModifyPartInHouse;

    @FXML
    private ToggleGroup sourceTG;

    @FXML
    private RadioButton radioModifyPartOutsourced;

    @FXML
    private TextField txtModifyPartId;

    @FXML
    private TextField txtModifyPartName;

    @FXML
    private TextField txtModifyPartInv;

    @FXML
    private TextField txtModifyPartPrice;

    @FXML
    private TextField txtModifyPartMax;

    @FXML
    private TextField txtModifyPartMin;

    @FXML
    private Button buttonModifyPartSave;

    @FXML
    private Button buttonModifyPartCancel;

    @FXML
    private Button buttonModifyPartClear;

    @FXML
    private Label lblSelectorSpecificModify;

    @FXML
    private TextField txtSelectorSpecificModify;


    /**
     * Adjusts label on last field to read Machine ID if the In House radio button is selected
     * @param event
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        lblSelectorSpecificModify.setText("Machine ID");
    }

    /**
     * Adjusts label on last field to read Company Name if the Outsourced radio button is selected
     * @param event
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        lblSelectorSpecificModify.setText("Company Name");
    }

    /**
     * Returns user to the main menu if the Cancel button is clicked
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionCancelPart(ActionEvent event) throws Exception {
        if(error.confirmationDialogue(confirmationCodes.CANCELWARN)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
            stage.setScene(new Scene(scene, 1070, 400));
            stage.setTitle("Return to Main Menu");
            stage.show();
        }
    }

    /**
     * Clears form input when the "Cancel" button is clicked
     * @param event
     */
    @FXML
    void onActionClearPart(ActionEvent event) {
       if(error.confirmationDialogue(confirmationCodes.CLEARITEMS)) {
           txtModifyPartName.clear();
           txtModifyPartInv.clear();
           txtModifyPartPrice.clear();
           txtModifyPartMax.clear();
           txtModifyPartMin.clear();
           txtSelectorSpecificModify.clear();
           lblSelectorSpecificModify.setText("Machine ID");
       }
    }

    /**
     * Saves part after checking for validation
     * @param event
     * @throws Exception, dialogue boxes shown for NumberFormatException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws Exception {

        try {
            //Capture input
            int index = passedParameters.getId();
            String name = txtModifyPartName.getText();
            double price = Double.parseDouble(txtModifyPartPrice.getText());
            int inventory = Integer.parseInt(txtModifyPartInv.getText());
            int min = Integer.parseInt(txtModifyPartMin.getText());
            int max = Integer.parseInt(txtModifyPartMax.getText());

            //Variable for storing whether valid input has been retrieved
            boolean valid = false;


            //Determine if object type change occurs (Outsource to Inhouse v Inhouse to Outsource)
            //CASE 1: CHANGED OUTSOURCE TO IN HOUSE
            if(passedParameters instanceof Outsourced && radioModifyPartInHouse.isSelected()) {
                int machineID = Integer.parseInt(txtSelectorSpecificModify.getText());
                InHouse item = new InHouse(index, name, price, inventory, min, max, machineID);
                if(error.partValueValidation(item)) {
                    Inventory.updatePart(index, item);
                    valid = true;
                }
            }
            //CASE 2: CHANGED IN HOUSE TO OUTSOURCE
            else if(passedParameters instanceof InHouse && radioModifyPartOutsourced.isSelected() ||
                    passedParameters instanceof Outsourced) {
                String companyName = txtSelectorSpecificModify.getText();
                Outsourced item = new Outsourced(index, name, price, inventory, min, max, companyName);
                if(error.partValueValidation(item)) {
                    Inventory.updatePart(index, item);
                    valid = true;
                }
            }
            //CASE 3: IN HOUSE AND TYPE HAS NOT CHANGED
            else if(passedParameters instanceof InHouse) {
                //create temporary object
                int machineID = Integer.parseInt(txtSelectorSpecificModify.getText());
                InHouse item = new InHouse(index, name, price, inventory, min, max, machineID);

                //If input is valid update the values
                if(error.partValueValidation(item)) {
                    ((InHouse) passedParameters).setMachineID(Integer.parseInt(txtSelectorSpecificModify.getText()));
                    passedParameters.setName(name);
                    passedParameters.setPrice(price);
                    passedParameters.setStock(inventory);
                    passedParameters.setMin(min);
                    passedParameters.setMax(max);

                    valid = true;
                }
            }
            //CASE 4: OUTSOURCED AND TYPE HAS NOT CHANGED
            else {
                //create temporary object
                String companyName = txtSelectorSpecificModify.getText();
                Outsourced item = new Outsourced(index, name, price, inventory, min, max, companyName);

                //If input is valid update the values
                if(error.partValueValidation(item)) {
                    ((Outsourced) passedParameters).setCompanyName(companyName);
                    passedParameters.setName(name);
                    passedParameters.setPrice(price);
                    passedParameters.setStock(inventory);
                    passedParameters.setMin(min);
                    passedParameters.setMax(max);
                    valid = true;
                }
            }

            //Return to main menu if the input was valid
            if(valid) {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
                stage.setScene(new Scene(scene, 1070, 400));
                stage.setTitle("Main Menu");
                stage.show();
            }

        }
        catch(NumberFormatException e) {
            error.errorHandling(errorCode.VALIDVALUES);
        }



    }


    /**
     * Initializes the ModifyPartController Class
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set text fields to caught values, from Main menu controller
        txtModifyPartId.setText(String.valueOf(passedParameters.getId()));
        txtModifyPartName.setText(passedParameters.getName());
        txtModifyPartInv.setText(String.valueOf(passedParameters.getStock()));
        txtModifyPartPrice.setText(String.valueOf(passedParameters.getPrice()));
        txtModifyPartMax.setText(String.valueOf(passedParameters.getMax()));
        txtModifyPartMin.setText(String.valueOf(passedParameters.getMin()));
        if(passedParameters instanceof InHouse) {
            radioModifyPartInHouse.setSelected(true);
            lblSelectorSpecificModify.setText("Machine ID");
            txtSelectorSpecificModify.setText(String.valueOf((((InHouse) passedParameters).getMachineID())));
        }
        else if(passedParameters instanceof Outsourced){
            radioModifyPartOutsourced.setSelected(true);
            lblSelectorSpecificModify.setText("Company Name");
            txtSelectorSpecificModify.setText(((Outsourced) passedParameters).getCompanyName());

        }


    }

}
