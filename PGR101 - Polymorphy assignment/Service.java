
/**
 * This class stores information (with their respective accessor/mutator methods) 
 * any type of prestation provided by the consultancy agency should have:
 * - record number (registrationNr) -- should be unique for each prestation
 * - first name and last name of the consultant (contact)
 * - cost of the prestation (price)
 * 
 * @author Clément Marescaux
 * @version 2016-01-26
 */
public class Service
{
    private String registrationNr;
    private String contact;       
    private int price;        

    /**
     * Constructor for a consultancy prestation entry.
     * 
     * @param regNr     the record number of the prestation
     * @param contact   the first name and last name of the consultant (format: firstname lastname)
     * @param price     the cost of the prestation
     */
    public Service(String regNr, String contact, int price)
    {
        registrationNr = regNr;
        this.contact = contact;
        this.price = price;        
    }

    /**
     * Default constructor, to add empty prestation entries.
     * The value of price is set to "-1" to simplify pruning of empty entries in collections
     * (for example, with Service.getPrice() < 0 ). 
     */    
    public Service()
    {
        this("*regNr undefined*", "*contact undefined*", -1);
    }

    /**
     * Return a string containing the details of a service object.
     * 
     * @return      record number, name of the consultant and price
     */
    public String toString()
    {
        return registrationNr + " " + contact + " " + price;
    }  

    /**
     * Return true if a service entry is equal to another.
     * 
     * Checks both the record number and the consultant's name, given that:
     * - a client could request several consultants for a single prestation/case 
     *   (i.e: 1 regNr / many contacts)
     * - a consultant can be assigned to multiple types of prestations at the same time 
     *   (i.e: 1 contact / many regNrs)
     *   
     * For instance, a consultant on a weekly Contract for every Monday 
     * could also be called for an Emergency on a Wednesday.
     * 
     * @return      true if and only if both record numbers and consultant names are identical 
     */
    public boolean equals(Object o)
    {
        if(this == o) 
        {            
            return true;
        }

        if( !(o instanceof Service) ) 
        {
            return false;
        }

        Service obj = (Service) o;
        
        return ( this.hashCode() == obj.hashCode() );
        //return registrationNr.equalsIgnoreCase(obj.registrationNr) &&
        //contact.equalsIgnoreCase(obj.contact);       
    }
    
    /**
     * Hashcode
     */
    public int hashCode(){
        int hash = 1;
        hash = hash * 17 + registrationNr.toLowerCase().hashCode();
        hash = hash * 31 + contact.toLowerCase().hashCode();
        return hash;
    }

    //Mutator methods

    /**
     * Change the record number of the prestation.
     * 
     * @param regNr     the record number of the prestation 
     */    
    public void setRegistrationNr(String regNr)
    {
        registrationNr = regNr;
    }

    /**
     * Change the name of the consultant.
     * 
     * @param contact   the name of the consultant 
     */    
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    /**
     * Change the amount charged for the prestation
     * 
     * @param price      the cost of the prestation
     */    
    public void setPrice(int price)
    {
        this.price = price;
    }

    //Accessor methods

    /**
     * Return the record number of the mission.
     * 
     * @return      the record number
     */    
    public String getRegistrationNr()
    { 
        return registrationNr; 
    }

    /**
     * Return the name of the consultant
     * 
     * @return      the name of the consultant
     */    
    public String getContact()
    {
        return contact;
    }

    /**
     * Return the amount charged for the prestation
     * 
     * @return      the cost of the prestation
     */
    public int getPrice()
    {
        return price;
    }    
}
