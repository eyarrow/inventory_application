package model;

/**
 * Outsourced object to manage the Outsourced class. Extends part.
 */
public class Outsourced extends Part{
    //data members
    private String companyName;

    /**
     * Outsourced object constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Return Company name
     * @return Company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set Company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
