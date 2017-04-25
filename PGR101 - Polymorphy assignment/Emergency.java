/**
 * This class stores information for urgent consultancy services.
 * An emergency has an additional fee that comes on top of the usual
 * service price.
 * 
 * @author Clément Marescaux
 * @version 2016-01-26
 */
public class Emergency extends Service
{
    private int fee;  // emergency fee
    
    /**
     * Constructor for services of type Emergency
     * 
     * @param regNr     the record number of the prestation
     * @param contact   the first name and last name of the consultant (format: firstname lastname)
     * @param price     the cost of the prestation
     * @param fee       the additional fee of the emergency
     */  
    public Emergency(String regNr, String contact, int price, int fee)
    {
        super(regNr, contact, price);
        this.fee = fee;
    }
    
    /**
     * Empty constructor for services of type Emergency  
     * Calls the default constructor of the superclass Service
     */
    public Emergency()
    {
        super();
        fee = 0;
    }
    
    
    /**
     * Return a string containing the details of the Emergency.
     * 
     * @return      record number, name of the consultant, price and emergency fee
     */
    public String toString()
    {
        return super.toString() + " " + fee;
    } 
 
      
    /**
     * Change the fee of the emergency.
     * 
     * @param fee     the fee of the emergency
     */
    public void setFee(int fee)
    {
        this.fee = fee;
    }  
    
    /**
     * Return the fee of the emergency.
     * 
     * @return      the fee of the emergency
     */
    public int getFee()
    {
        return fee;
    }
}