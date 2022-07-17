package masterinventory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static masterinventory.JavaFXApplication.createProductIndex;

/**
 * Class that provides the controller for the Modify Product UI view.
 */
public class ModifyProductController implements Initializable {
    //JavaFX required object declarations
    Stage stage;
    Parent scene;

    //static variable to store passed parameters
    private static Product passedParameters;

    //Error handling object initialization
    ErrorHandling error = new ErrorHandling();

    /**
     * Passes saved product parameters from MainMenuController to ModifyProductController
     * @param item
     */
    public static void passParameters(Product item) {
        passedParameters = item;
    }

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> passedInParts = FXCollections.observableArrayList();



    @FXML
    private TextField txtPartSearch;

    @FXML
    private TextField txtProductMax;

    @FXML
    private TextField txtProductMin;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductInv;

    @FXML
    private TextField txtProductPrice;

    @FXML
    private TableView<Part> tableViewAllProducts;

    @FXML
    private TableColumn<Part, Integer> colPartID;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Integer> colInventory;

    @FXML
    private TableColumn<Part, Double> colPrice;

    @FXML
    private Button buttonAddAssoc;

    @FXML
    private TableView<Part> tableViewAssociatedProducts;

    @FXML
    private TableColumn<Part, Integer> colAssocId;

    @FXML
    private TableColumn<Part, String> colAssocName;

    @FXML
    private TableColumn<Part, Integer> colAssocInv;

    @FXML
    private TableColumn<Part, Double> colAssocPrice;

    @FXML
    private Button buttonRemoveAssoc;

    @FXML
    private Button buttonSaveAssoc;

    @FXML
    private Button buttonCancel;


    /**
     * Adds associated parts to the list of items to be associated with the product once save is clicked.
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionAddAssoc(ActionEvent event) throws Exception {
        Part item = tableViewAllProducts.getSelectionModel().getSelectedItem();

        //Check to see if user selected anything if not return exception
        if (tableViewAllProducts.getSelectionModel().getSelectedItem() == null) {
            error.errorHandling(errorCode.ASSOCPARTSELECTED);
            return;
        }

        //Make sure we aren't adding the same associated product twice, now that we know
        //something is selected
        for (Part search : associatedParts) {
            if (search.getId() == item.getId()) {
                error.errorHandling(errorCode.ALREADYASSOCPART);
                return;
            }

        }

        //Adds the associated part to the temporary list that will be saved, clears selection
        this.associatedParts.add(item);
        tableViewAssociatedProducts.setItems(associatedParts);


    }

    /**
     * Returns user to the main menu when "Cancel" is clicked
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionCancel(ActionEvent event) throws Exception {
        for(Part item : associatedParts) {
            System.out.println("On associated parts local list");
            System.out.println(item.getName());
        }

        for(Part item : passedParameters.getAllAssociatedParts()) {
            System.out.println("Passed in object from passed parameters");
            System.out.println(item.getName());
        }

        if(error.confirmationDialogue(confirmationCodes.CANCELWARN)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
            stage.setScene(new Scene(scene, 1070, 400));
            stage.setTitle("Return to Main Menu");
            stage.show();
        }
    }

    /**
     * Removes part from the associated list
     * @param event
     */
    @FXML
    void onActionRemoveAssoc(ActionEvent event) {
        Part item = tableViewAssociatedProducts.getSelectionModel().getSelectedItem();
        if(item != null) {
            if(error.confirmationDialogue(confirmationCodes.REMOVEPART)) {
                this.associatedParts.remove(item);
                tableViewAssociatedProducts.setItems(associatedParts);
            }
        }
        else {
            error.errorHandling(errorCode.NOITEMTODELETE);
            return;
        }
    }

    /**
     * Saves product after passing validation
     * @param event
     * @throws IOException for NumberFormatException - displays error dialogue box
     */
    @FXML
    void onActionSaveAssoc(ActionEvent event) throws IOException {
        boolean valid = false;
        try {
            int index = passedParameters.getId();
            String name = txtProductName.getText();
            double price = Double.parseDouble(txtProductPrice.getText());
            int inventory = Integer.parseInt(txtProductInv.getText());
            int min = Integer.parseInt(txtProductMin.getText());
            int max = Integer.parseInt(txtProductMax.getText());
            Product modified = new Product(index, name, price, inventory, min, max, associatedParts);
            //Validation
            valid = error.productValueValidation(modified);
            if(valid) {
                Inventory.updateProduct(index, modified);
            }

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
     * Search for parts either by ID or partial String search on name
     * @param event
     */
    @FXML
    void onActionSearchByPart(ActionEvent event) {
        String query = txtPartSearch.getText();
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
        tableViewAllProducts.setItems(results);
        txtPartSearch.setText("");
    }

    /*
     *   Initializes the ModifyProductController class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate form values
        txtProductId.setText(String.valueOf(passedParameters.getId()));
        txtProductName.setText(passedParameters.getName());
        txtProductInv.setText(String.valueOf(passedParameters.getStock()));
        txtProductMin.setText(String.valueOf(passedParameters.getMin()));
        txtProductMax.setText(String.valueOf(passedParameters.getMax()));
        txtProductPrice.setText(String.valueOf(passedParameters.getPrice()));

        //Get Table Values
        tableViewAllProducts.setItems(Inventory.getAllParts());
        this.passedInParts = passedParameters.getAssociatedParts();
        tableViewAssociatedProducts.setItems(passedInParts);

        //pass by value to temporary object
        for(Part item : passedInParts) {
            this.associatedParts.add(item);
        }

        //Populate Table Views
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colAssocId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAssocName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAssocInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAssocInv.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
