import java.util.ArrayList;

/**
 * Client class -- only used to contain and run the Oppgaven() method
 * 
 * The method creates an ArrayList called "services" that will contain 
 * objects of type Service.
 * It fills the arraylist with three anonymous objects of Service 
 * subtypes, namely:
 * - Emergency (Utrykning) 
 * - Contract (Kontrakt) 
 * - FixedTerm (Engasjement)
 * Each object is instantiated with the same parameters as the output 
 * shown in the description of Innlevering 1.
 * 
 * The first part of oppgaven() loops through the ArrayList and prints out 
 * each instance's toString() + its subtype of Service. 
 * Here, .getClass().getTypeName() returns exactly the String we need 
 * for the output, so we don't have to chain "instanceof" checks like in
 * the second part.
 * 
 * The second part of oppgaven() loops through the ArrayList and casts 
 * the appropriate subtype of each entry depending on which "instanceof" 
 * check succeeds, and formats a placeholder String accordingly with the 
 * subtype's specific accessor. 
 * Then it prints a formatted output of the values stored in the 
 * superclass through its accessors, and prints out the subtype-specific String.
 * 
 * The third part of oppgaven() prints the output of Service's equals()
 * method, comparing two different subtypes of Service constructed with the same
 * set of arguments.
 * 
 * @author Clément Marescaux
 * @version 2016-01-25
 */

public class Client
{
    public static void oppgaven()
    {
        ArrayList<Service> services = new ArrayList<>();
        
        services.add(new Emergency("123", "Per Persen", 12000, 1000));
        services.add(new Contract("113", "Petter Pettersen", 14000, "monday"));
        services.add(new FixedTerm("103", "Pernille Pernillesen", 12000, 7));
        
        // 1. toString print //
        
        System.out.println("Overview: ");
        for(Service service : services)
        {               
            System.out.println(service + " (" + service.getClass().getTypeName() + ")");     
        }        
        System.out.println();

        // 2. get-methods print //
        
        String serviceTypeInfo;
        for(Service service : services)
        {            
            
            // Formatting the last string to be printed according to the current Service's subtype
            if(service instanceof FixedTerm){
                FixedTerm fterm = (FixedTerm) service;
                serviceTypeInfo = "Duration: " + fterm.getDuration();
            }
            else if (service instanceof Contract){
                Contract contr = (Contract) service;
                serviceTypeInfo = "Day: " + contr.getDay();
            }
            else if(service instanceof Emergency){
                Emergency emerg = (Emergency) service;
                serviceTypeInfo = "Fee: " + emerg.getFee();             
            }
            else{
                serviceTypeInfo = "*type of service undefined*";
            }
            
            System.out.println("Contact: "  + service.getContact());
            System.out.println("Price: "    + service.getPrice());
            System.out.println("Reg.nr: "   + service.getRegistrationNr());
            System.out.println( serviceTypeInfo );
            System.out.println();
        }
        System.out.println();

        // 3. equality print //
        // We create two objects of different subtypes, to show that equality
        // only depends on attributes stored in superclass Service.
        
        FixedTerm fixedTerm_2 = new FixedTerm("1001", "Per", 1000, 14);
        Emergency emerg_2 = new Emergency("1001", "Per", 1000, 14);
        System.out.println(
            "Is \n" 
            + fixedTerm_2 + "\n"   // calling toString() automatically
            + "equal to \n" 
            + emerg_2 + "\n"  
            + "? \n" 
            + fixedTerm_2.equals(emerg_2)
        );
    }
}