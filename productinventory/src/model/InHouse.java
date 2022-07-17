package model;

/**
 * In house object - extends part object. Used for managing in house inventory parts.
 */
public class InHouse extends Part{
    //data members
    private int machineID;

    /**
     * InHouse Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machID) {
        super(id, name, price, stock, min, max);
        this.machineID = machID;
    }

    /**
     * Returns the machineID
     * @return machineID
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Sets the machineID
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

}
