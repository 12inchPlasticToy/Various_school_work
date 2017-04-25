import javax.swing.*;

/**
 * This class contains the information to finalise the Huskeliste version
 * with menus.
 * The triggers are all JMenuItems.
 *
 * @author Clément Marescaux
 * @version 2016-03-17
 */
public class Huskeliste_Menu extends Huskeliste {

    private JMenu mnuFile, mnuOperations;

    /**
     * Constructor.
     *
     */
    public Huskeliste_Menu(){
        super();
        render();
    }

    /**
     * Finalise the Huskeliste and place all remaining components
     * for the menu version of the program
     */
    protected void render(){

        JMenuBar mnbMenuBar = new JMenuBar();
        setJMenuBar(mnbMenuBar);

        mnuFile = new JMenu("File");
        mnuOperations = new JMenu("Operasjoner");

        mnbMenuBar.add(mnuFile);
        mnbMenuBar.add(mnuOperations);

        createTriggers();
        mnuOperations.insertSeparator(3);

        pack();
        setVisible(true);
    }

    /**
     * Populate each menu with their respective functions,
     * activates them, and add them to the trigger array
     * in the parent class.
     */
    protected void createTriggers(){

        triggers = new JMenuItem[7];
        for (int i = 0; i < 7; i++) {
            triggers[i] = new JMenuItem(TRIGGER_TITLES[i]);
            triggers[i].addActionListener(this);
            if(i < 6){
                mnuOperations.add(triggers[i]);
            }
            else {
                mnuFile.add(triggers[i]);   // triggers[6] is "Avslutt"
            }
        }
    }
}
