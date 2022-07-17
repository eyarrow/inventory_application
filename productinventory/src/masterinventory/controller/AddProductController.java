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

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static masterinventory.JavaFXApplication.createProductIndex;
import static masterinventory.JavaFXApplication.createpartIndex;


/**
 * Class that provides the controller for the Add Product UI view.
 */
public class AddProductController implements Initializable {

    //JavaFX required stage and scene objects
    Stage stage;
    Parent scene;

    //Error handling object declaration
    ErrorHandling error = new ErrorHandling();

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TextField txtProductSearch;

    @FXML
    private TextField txtMax;

    @FXML
    private TextField txtMin;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtInv;

    @FXML
    private TextField txtPrice;

    @FXML
    private TableView<Part> tableViewAllProducts;

    @FXML
    private TableColumn<Part, Integer> colAllProductPartID;

    @FXML
    private TableColumn<Part, String> colAllProductsName;

    @FXML
    private TableColumn<Part, Integer> colAllProductsInv;

    @FXML
    private TableColumn<Part, Double> colAllProductsPrice;

    @FXML
    private Button buttonAddAssociatedProduct;

    @FXML
    private TableView<Part> tableViewAssociatedProducts;

    @FXML
    private TableColumn<Part, Integer> colAssocProdID;

    @FXML
    private TableColumn<Part, String> colAssocProdName;

    @FXML
    private TableColumn<Part, Integer> colAssocProdInv;

    @FXML
    private TableColumn<Part, Double> colAssocProdPrice;

    @FXML
    private Button buttonRemoveAssocProd;

    @FXML
    private Button buttonSaveProd;

    @FXML
    private Button buttonCancelProd;



    /**
     * Adds Associated Part to the product
     * @param event
     * @exception throws error (popup) if the add button is pushed without choosing an associated part
     * Additional dialog is displayed if the user tries to enter the same associated product more than once
     */
    @FXML
    void onActionAddAssocProd(ActionEvent event) throws Exception{
        Part item = tableViewAllProducts.getSelectionModel().getSelectedItem();

        //Check to see if user selected anything if not return exception
        if(tableViewAllProducts.getSelectionModel().getSelectedItem() == null) {
            error.errorHandling(errorCode.ASSOCPARTSELECTED);
            return;
        }

        //Make sure we aren't adding the same associated product twice, now that we know
        //something is selected
        for(Part search: associatedParts) {
            if(search.getId() == item.getId()) {
                error.errorHandling(errorCode.ALREADYASSOCPART);
                return;
            }
        }

        //adds the associated part to the temporary list that will eventually be saved
        //to the product object. Clears the selection.
        this.associatedParts.add(item);
        tableViewAllProducts.getSelectionModel().clearSelection();

    }

    /**
     * Returns user to main menu when the "cancel" button is clicked
     * @param event
     * @throws Exception
     */
    @FXML
    void onActionCancelProd(ActionEvent event) throws Exception {
        if(error.confirmationDialogue(confirmationCodes.CANCELWARN)) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/masterinventory/view/MainMenu.fxml/"));
            stage.setScene(new Scene(scene, 1070, 400));
            stage.setTitle("Return to Main Menu");
            stage.show();
        }

    }

    /**
     * Allows user to search products by entering search terms in the search box and hitting enter.
     * Valid search optons include searching by product number and partial string match on name.
     * @param event
     */
    @FXML
    void onActionProductSearch(ActionEvent event) {
        String query = txtProductSearch.getText();
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
        txtProductSearch.setText("");
    }

    /**
     * Allows user to remove an associated part from a product. Displays a confirmation dialogue
     * before final deletion.
     * @param event
     */
    @FXML
    void onActionRemoveAssocProd(ActionEvent event) {
        Part item = tableViewAssociatedProducts.getSelectionModel().getSelectedItem();

        if(item != null) {
            if(error.confirmationDialogue(confirmationCodes.REMOVEPART)) {
                this.associatedParts.remove(item);
            }
        }
        else {
            error.errorHandling(errorCode.NOITEMTODELETE);
            return;
        }


    }

    /**
     * Saves product after it is run through validation.
     * @param event
     * @throws Exception dialogue displayed for NumberFormatException codes. If entered data is valid
     * the product will be saved, and user returned to the main menu. If not, they will be allowed to fix
     * their entry on the add product form.
     */
    @FXML
    void onActionSaveProd(ActionEvent event) throws Exception {
        boolean validation = false;
       try {
           //Create new product with assigned values
           int index = createProductIndex();
           String name = txtName.getText();
           double price = Double.parseDouble(txtPrice.getText());
           int inventory = Integer.parseInt(txtInv.getText());
           int min = Integer.parseInt(txtMin.getText());
           int max = Integer.parseInt(txtMax.getText());
           Product newproduct = new Product(index, name, price, inventory, min, max, associatedParts);

           //Perform Validation
           validation = error.productValueValidation(newproduct);
           if(validation) {
               Inventory.addProduct(newproduct);
           }

           //If validation passed new item was saved and the main menu will be displayed
           if(validation) {
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
     * Initialization method for AddProductController
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableViewAllProducts.setItems(Inventory.getAllParts());
        tableViewAssociatedProducts.setItems(this.associatedParts);

        colAllProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAllProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAllProductsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAllProductsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colAssocProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAssocProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAssocProdInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colAssocProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
