/**
 * Inventory Management System
 * Author: Elizabeth R Yarrow
 * Software I Performance Assessment
 * Javadocs available in the folder /javadocs-eyarrow
 */

package masterinventory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


public class JavaFXApplication extends Application {
    /**
     * Variable to track the part index. Incremented by 2 with every added item. New items are added in ascending order.
     * Even values only.
     */
    public static int partIndex = 0;

    /**
     * Variable to track the product index. Incremented by 2 with every added item. New items are added in ascending order.
     * Odd values only
     */
    public static int productIndex = 1;

    /**
     * Maintains index for unique Part id's.
     * Even numbers are used for part indexes.
     * @return new unused part index
     */
    public static int createpartIndex() {
        int newValue = partIndex+2;
        partIndex = newValue;
        return partIndex;
    }

    /**
     * Maintains index for unique product id's
     * Odd numbers are used for product indexes
     *
     * @return
     */
    public static int createProductIndex() {
        int newValue = productIndex+2;
        productIndex = newValue;
        return productIndex;
    }

    Stage stage;
    Parent scene;

    /**
     * Initializes program
     */
    @Override
    public void init() {
        System.out.println("Starting !");
    }

    /**
     * Loads the primary stage (ie main menu)
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/MainMenu.fxml"));
        primaryStage.setTitle("Product Inventory");
        primaryStage.setScene(new Scene(root, 1070, 400));
        primaryStage.show();


    }

    /**
     * Runs stop application command
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Exiting the Inventory Program. Goodbye!");
    }




    /**
     * Main. Loads test data.
     * FUTURE ENHANCEMENT - Would like to add the following enhancements:
     * 1. Current error handling returns a error and dialogue when the user enters invalid data (that doesn't provide
     * the correct data type. I would like to add additional feedback on what field needs to be corrected,
     * and which data type is required.
     * 2. The search currently meets the functionality required by this assignment in that hitting the enter
     * button will clear the search. I also added a clear button to clarify how this works. But I think the search
     * functionality could be made more clear for the user (more dialogues, maybe a description in the UI)
     * @param args
     *
     *
     */
    public static void main(String[] args) {
        //load initial data

        int partId;
        int productId;
        partId = createpartIndex();
        productId = createProductIndex();

        //create test parts
        InHouse whizzle = new InHouse(partId,"Whizzle", 15.72, 5, 2, 7, 712);
        partId = createpartIndex();

        InHouse wrench = new InHouse(partId,"Power Wrench", 225.70, 3, 1, 10, 775);
        partId = createpartIndex();

        Outsourced whatsits = new Outsourced(partId,"Whats its", 2.99, 5, 2, 7, "Acme");
        partId = createpartIndex();

        Outsourced monkey = new Outsourced(partId,"Monkey Wrench", 10.25, 3, 1, 10, "Jamie's Powerworks");

        Inventory.addPart(whizzle);
        Inventory.addPart(wrench);
        Inventory.addPart(whatsits);
        Inventory.addPart(monkey);

        //create test products
        ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();
        productAssociatedParts.add(whatsits);
        productAssociatedParts.add(whizzle);
        Product acmebomb = new Product(productId, "Acme Power Bomb", 1001.99, 2, 1, 2,productAssociatedParts);

        ObservableList<Part> productAssociatedParts2 = FXCollections.observableArrayList();
        productAssociatedParts2.add(monkey);
        productId = createProductIndex();
        Product wurlitzer = new Product(productId, "Wurlitzer", 99.99, 10, 5, 20,productAssociatedParts2);

        Inventory.addProduct(acmebomb);
        Inventory.addProduct(wurlitzer);

        launch(args);
    }
}


