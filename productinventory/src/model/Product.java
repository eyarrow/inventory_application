package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * A class to manage all Product objects and their properties
 */
public class Product {
    //data members
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor Product
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param associatedParts
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.setAssociatedParts(associatedParts);
    }

    /**
     * Return associated parts
     * @return associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * Set Associated Parts
     * @param associatedParts
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * Get ID
     * @return Product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set Product id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return stock level
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set stock level
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get Min
     * @return Min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get max
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Set max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add associated part
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete associated part
     * @param selectedAssociatedPart
     * @return true if successfully deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : associatedParts) {
            if(associatedParts == selectedAssociatedPart)
            {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }

        return false;

    }

    /**
     * Get all associated parts for product
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }





}
