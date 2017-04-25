/**
 * This class stores information for recurrent weekly contracts.
 * It stores the day a consultant comes to work for the client on 
 * a weekly basis.
 *    
 * @author Clément Marescaux
 * @version 2016-01-26
 */
public class Contract extends Service
{
    private String day;     // Weekday 

    /**
     * Constructor for services of type Contract
     * 
     * @param regNr     the record number of the prestation
     * @param contact   the first name and last name of the consultant (format: firstname lastname)
     * @param price     the cost of the prestation
     * @param day       the day on which the consultant works every week
     */  
    public Contract(String regNr, String contact, int price, String day)
    {
        super(regNr, contact, price);
        this.day = day;
    }

    /**
     * Empty constructor for services of type Contract.
     * Calls the default constructor of the superclass Service.
     */
    public Contract()
    {
        super();
        day = "*contract day undefined*";
    }    

    /**
     * Return a string containing the details of a Contract object.
     * 
     * @return      record number, name of the consultant, price and weekday
     */
    public String toString()
    {
        return super.toString() + " " + day;
    }

    //Methods

    /**
     * Change the week day on which the prestation is given
     * 
     * @param       the day on which the consultant works every week
     */
    public void setDay(String day)
    {
        this.day = day;
    }
    
    /**
     * Return the day of the week the consultant works for this client
     * 
     * @return       the week day the consultant works
     */
    public String getDay()
    {
        return day;
    }
}
