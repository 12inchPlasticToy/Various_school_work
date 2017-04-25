import javax.swing.*;

/**
 * This class only contains the "main" start method.
 *
 */
public class Main {

    /**
     * Bootstrap method for the Huskeliste program.
     * If ran without arguments, the user is given a choice of interface type before
     * starting the program.
     *
     * @param args empty (drop down choice), "buttons" or "menu"
     */
    public static void main(String[] args) {

        // if an argument is passed
        if (args.length != 0) {
            if (args.length > 1) {
                System.out.println("Please run Main with options 'buttons' or 'menu'");
                System.out.println("Ex: java Main buttons ");
                System.exit(0);
            } else {
                switch (args[0].trim().toLowerCase()) {
                    case "buttons":
                        new Huskeliste_Buttons();
                        break;
                    case "menu":
                        new Huskeliste_Menu();
                        break;
                    default:
                        System.out.println("Please run Main with options 'buttons' or 'menu'");
                        System.out.println("Ex: java Main buttons ");
                        System.exit(0);
                }
            }

        }
        else {
            // default start procedure (no argument passed) with a
            // drop down menu dialog
            String[] options = {"Buttons", "Menu"};

            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Velg et grensesnitt:\n",
                    "Huskeliste",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == null) {
                System.exit(0);
            } else if (choice.equals(options[0])) {
                new Huskeliste_Buttons();
            } else if (choice.equals(options[1])) {
                new Huskeliste_Menu();
            }
        }
    }
}
