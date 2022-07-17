package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Locale;

/**
 * Inventory object, used to manage the list of parts and products, and operations that manage that inventory.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Look for a product using it's id number
     * @param product, the product id number
     * @return the product object if it is found. Null if not found
     */
    public static Product lookupProduct(int product ) {

        for (Product item : Inventory.getAllProducts())
        {
            if(item.getId() == product)
                return item;
        }

        return null;
    }

    /**
     * Looks for a part using it's part ID number
     * @param partID, equals the part id number
     * @return the searched for part, if not found returns null
     */
    public static Part lookupPart(int partID) {
        for(Part item : Inventory.getAllParts())
        {
            if(item.getId() == partID)
            {
                return item;
            }
        }
        return null;
    }

    /**
     * Searches for partial name matches on the name of a product. Search is case insensitive.
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> possibleMatches = FXCollections.observableArrayList();
        String product = productName.toLowerCase();
        for(Product item :Inventory.getAllProducts())
        {
            if(item.getName().toLowerCase(Locale.ROOT).contains(product))
            {
                possibleMatches.add(item);
            }
        }


        return possibleMatches;
    }

    /**
     * Searches for partial string matches on name of Part. Lookup is case insensitive.
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> possibleMatches = FXCollections.observableArrayList();
        String part = partName.toLowerCase(Locale.ROOT);
        for(Part item :Inventory.getAllParts())
        {
            if(item.getName().toLowerCase(Locale.ROOT).contains(part))
            {
                possibleMatches.add(item);
            }
        }


        return possibleMatches;
    }

    /**
     * Updates the selected part, by adding tne new entry, and deleting the original.
     * PartID is retained.
     * @param index is the PartID
     * @param selectedPart is the desired object
     */
    public static void updatePart(int index, Part selectedPart) {
        int partIndex = 0;
        for(Part item : allParts)
        {
           if(item.getId() == selectedPart.getId()) {
              partIndex = allParts.indexOf(item);
           }

        }

        allParts.add(selectedPart);
        allParts.remove(partIndex);

   }

    /**
     * Replaces product with updated entry, by adding new entry and deleting the old. ProductID is retained.
     * @param index is Product ID
     * @param newProduct is object reference
     */
    public static void updateProduct(int index, Product newProduct) {
        int productIndex = 0;
        for(Product item : allProducts)
        {
            if(item.getId() == newProduct.getId()) {
                productIndex = allProducts.indexOf(item);
            }

        }
        allProducts.add(newProduct);
        allProducts.remove(productIndex);

    }

    /**
     * Deletes part.
     * @param selectedPart
     * @return true if delete was successful, false if it was not.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part item : allParts) {
            if (item.getId() == selectedPart.getId()) {
                return allParts.remove(selectedPart);
            }
        }
        return false;
    }

    /**
     * Deletes Product.
     * @param selectedProduct
     * @return true if successful, false if not.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product item : allProducts) {
            if (item.getId() == selectedProduct.getId()) {
                return allProducts.remove(selectedProduct);
            }
        }
        return false;
    }

    /**
     * Return all parts
     * @return All parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Return all products
     * @return all Products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }







}
