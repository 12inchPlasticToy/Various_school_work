/**
 * This class stores information for fixed term missions.
 * A fixed term mission lasts for a defined amount of days, 
 * stored in the "duration" variable. 
 * 
 * @author Clément Marescaux
 * @version 2016-01-26
 */
public class FixedTerm extends Service
{
    private int duration;    // duration of the term in days

    /**
     * Constructor for services of type Contract
     * 
     * @param regNr     the record number of the prestation
     * @param contact   the first name and last name of the consultant (format: firstname lastname)
     * @param price     the cost of the prestation
     * @param duration  the duration of the mission in days
     */  
    public FixedTerm(String regNr, String contact, int price, int duration)
    {
        super(regNr, contact, price);
        this.duration = duration;
    }

    /**
     * Empty constructor for services of type FixedTerm
     * Calls the default constructor of the superclass Service
     */
    public FixedTerm()
    {
        super();
        duration = 0;
    }
    
    
    /**
     * Return a string containing the details of a mission.
     * 
     * @return      record number, name of the consultant, price and duration
     */
    public String toString()
    {
        return super.toString() + " " + duration;
    }    
    

    /**
     * Change the duration of the term.
     * 
     * @param duration  the duration of the term in days
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Return the duration of the term.
     * 
     * @return      the duration of the term in days
     */
    public int getDuration()
    {
        return duration;
    }

}