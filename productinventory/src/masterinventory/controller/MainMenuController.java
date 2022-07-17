package masterinventory.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

/**
 * Class that provides the controller for the Main Menu UI view.
 */
public class MainMenuController implements Initializable {
    //JavaFX stage and scene objects
    Stage stage;
    Parent scene;

    //Error handling object declaration
    ErrorHandling error = new ErrorHandling();

    @FXML
    private TextField txtSearchPartMainMenu;

    @FXML
    private TableView<Part> tableViewParts;

    @FXML
    private TableColumn<Part, Integer> columnPartID;

    @FXML
    private TableColumn<Part, String> columnPartName;

    @FXML
    private TableColumn<Part, Integer> columnPartInventory;

    @FXML
    private TableColumn<Part, Double> columnPartCost;

    @FXML
    private TextField txtSearchProductMainMenu;

    @FXML
    private TableView<Product> tableViewProducts;

    @FXML
    private TableColumn<Product, Integer> columnProductID;

    @FXML
    private TableColumn<Product, String> columnProductName;

    @FXML
    private TableColumn<Product, Integer> columnProductInventory;

    @FXML
    private TableColumn<Product, Double> columnProductCost;

    @FXML
    private Button buttonAddPart;

    @FXML
    private Button buttonModifyPart;

    @FXML
    private Button buttonDeletePart;

    @FXML
    private Button buttonAddProduct;

    @FXML
    private Button buttonModifyProduct;

    @FXML
    private Button buttonDeleteProduct;

    @FXML
    private Button buttonExitMainMenu;

    @FXML
    private Button buttonClearSearchParts;

    @FXML
    private Button buttonClearSearch;



    /**
     * Returns the user to the main menu
     */
    public void returnToMainMenu() throws IOException {
        scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
        stage.setScene(new Scene(scene, 1070, 400));
        stage.setTitle("Return to Main Menu");
        stage.show();
    }

    /**
     * Launches add part screen
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws Exception {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/AddPart.fxml/"));
        stage.setScene(new Scene(scene, 800, 600));
        stage.setTitle("Add Part");
        stage.show();

    }


    /**
     * Launches "Add Product" screen
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws Exception {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/AddProduct.fxml/"));
        stage.setScene(new Scene(scene, 1070, 700));
        stage.setTitle("Add Product");
        stage.show();
    }

    /**
     * Allows deletion of a product, by clicking on desired object in table view, and clicking de
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part item = tableViewParts.getSelectionModel().getSelectedItem();
        if(item == null) {
           error.errorHandling(errorCode.NOITEMTODELETE);
           return;
        }

        if(error.confirmationDialogue(confirmationCodes.REMOVEPART)) {
            Inventory.deletePart(item);
        }

    }

    /**
     * Allows deletion of a product, by clicking on desired object in table view, and clicking delete.
     * @param event
     * @throws Exception if user attempts to delete a product that has associated parts.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws Exception {
        Product item = tableViewProducts.getSelectionModel().getSelectedItem();
        //Determine if an item is selected in the UI
        try {
           item.getAllAssociatedParts();
        }
        catch (NullPointerException e) {
            error.errorHandling(errorCode.NOITEMTODELETE);
            return;

        }

        //If item has associated parts return error. Else delete item.
        if(item.getAllAssociatedParts().size() != 0) {
            error.errorHandling(errorCode.ASSOCPARTDELERR);
        }
        else {

            if(error.confirmationDialogue(confirmationCodes.REMOVEPRODUCT)) {

                Inventory.deleteProduct(item);
            }
        }


    }

    /**
     * Allows user to modify existing part. Part must exist in the table view in order to be modified.
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws Exception {
            //pass data to the ModifyPartController
            Part item = tableViewParts.getSelectionModel().getSelectedItem();

        //Double check if the user selected a Part. If not prompt them to select a part.
        try {
            item.getId();
        }
        catch (NullPointerException e) {
            error.errorHandling(errorCode.NOMODIFYSELECTED);
            return;

        }

            ModifyPartController.passParameters(item);

            //Load Modify Part Screen
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/ModifyPart.fxml/"));
            stage.setScene(new Scene(scene, 800, 600));
            stage.setTitle("Modify Part");
            stage.show();
    }

    /**
     * Allows the user to modify products
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws Exception {
        Product item = tableViewProducts.getSelectionModel().getSelectedItem();

        //check to see if an item is selected in the UI
        try {
            item.getAllAssociatedParts();
        }
        catch (NullPointerException e) {
            error.errorHandling(errorCode.NOMODIFYSELECTED);
            return;

        }
        ModifyProductController.passParameters(item);

        //Load modify product screen
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/ModifyProduct.fxml/"));
        stage.setScene(new Scene(scene, 1070, 700));
        stage.setTitle("Modify Product");
        stage.show();
    }

    /**
     * Searches parts based on either Part ID or partial String match on name
     * @param event
     */
    @FXML
    void onActionPartSearch(ActionEvent event) {
        String query = txtSearchPartMainMenu.getText();
        ObservableList<Part> results = Inventory.lookupPart(query);


        if(results.size() == 0)
        {
            try {
                int idNumber = Integer.parseInt(query);
                Part item = Inventory.lookupPart(idNumber);
                if(item != null)
                    results.add(item);
                else {
                    error.errorHandling(errorCode.SEARCHFAIL);
                }
            }
            catch (NumberFormatException e) {
                error.errorHandling(errorCode.SEARCHFAIL);
            }
        }

        tableViewParts.setItems(results);
        txtSearchPartMainMenu.setText("");
    }

    /**
     * Restores the all Parts table view after a search to search all parts.
     * @param event
     */
    @FXML
    void onActionClearSearchParts(ActionEvent event) {
        tableViewParts.setItems(Inventory.getAllParts());
    }

    /**
     * Restores the All Products table view after a search to show all products
     * @param event
     */
    @FXML
    void onActionClickClearSearch(ActionEvent event) {
        tableViewProducts.setItems((Inventory.getAllProducts()));
    }

    /**
     * Searches products based on either Product ID or partial String match on name
     * @param event
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        String query = txtSearchProductMainMenu.getText();
        ObservableList<Product> results = Inventory.lookupProduct(query);

        if(results.size() == 0)
        {
            try {
                int idNumber = Integer.parseInt(query);
                Product item = Inventory.lookupProduct(idNumber);
                if(item != null)
                    results.add(item);
                else {
                    error.errorHandling(errorCode.SEARCHFAIL);

                }
            }
            catch (NumberFormatException e) {
                error.errorHandling(errorCode.SEARCHFAIL);
            }

        }

        tableViewProducts.setItems(results);
        txtSearchProductMainMenu.setText("");
    }

    /**
     * Closes the application when the exit button is clicked. Confirms before close.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {

           if (error.confirmationDialogue(confirmationCodes.ENDPROGRAM)) {
            Platform.exit();
             }
           else {
               return;
             }

    }


    /**
     * Initializes the main menu controller class
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewParts.setItems(Inventory.getAllParts());

        columnPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableViewProducts.setItems(Inventory.getAllProducts());

        columnProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
