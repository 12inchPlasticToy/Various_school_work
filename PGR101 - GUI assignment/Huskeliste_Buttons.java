import javax.swing.*;
import java.awt.*;

/**
 * This class contains the information to finalise the Huskeliste version
 * with buttons.
 * The triggers are all JButtons.
 *
 * @author Clément Marescaux
 * @version 2016-03-17
 */
public class Huskeliste_Buttons extends Huskeliste {

    private JPanel pnlButtons;

    /**
     * Constructor.
     *
     */
    public Huskeliste_Buttons(){
        super();
        render();
    }

    /**
     * Finalise the Huskeliste and place all remaining components
     * for the button version of the program
     */
    protected void render(){

        pnlButtons = new JPanel(new GridLayout(1, 7));
        createTriggers();
        add(pnlButtons, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Construct the buttons for the interface, activate them
     * and add them to the trigger array in the parent class.
     */
    protected void createTriggers() {

        triggers = new JButton[7];
        for (int i = 0; i < 7; i++) {
            triggers[i] = new JButton(TRIGGER_TITLES[i]);
            triggers[i].addActionListener(this);
            pnlButtons.add(triggers[i]);
        }
    }
}