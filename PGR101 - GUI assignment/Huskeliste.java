import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class stores the functions for the Huskeliste program, and
 * starts the creation of the interface.
 * The implementation of these functions and their graphical triggers
 * must be implemented in subclasses.
 *
 * @Author Clément Marescaux
 * @Version 2016-03-08
 */
public abstract class Huskeliste extends JFrame implements ActionListener {

    private ArrayList<Avtale> appointments = new ArrayList<>();
    private JTextArea txaScreen;
    protected AbstractButton[] triggers;
    protected final String[] TRIGGER_TITLES = {
            "Ny avtale",            // index 0
            "Vis avtaler",          // index 1
            "Vis alle avtaler",     // index 2
            "Fjern avtale",         // index 3
            "Fjern avtaler",        // index 4
            "Fjern alle avtaler",   // index 5
            "Avslutt"               // index 6
    };

    /**
     * Declaration of constants for time types.
     * Each time type has:
     * - a name
     * - a pattern recognizable by SimpleDateFormat
     * - an example for correct input
     */
    private enum TimeType{
        DAY("Dag","dd.MM.yy","23.02.98"),
        TIMESTAMP("Tidspunkt","dd.MM.yy HH:mm","23.02.98 15:30");
        private final String TYPE, FORMAT, EXAMPLE;

        TimeType(String type, String format, String example){
            TYPE = type;
            FORMAT = format;
            EXAMPLE = example;
        }
    }

    /**
     * Constructor for the first half of the main interface
     * common to all versions of Huskeliste (the outer layout
     * and the text area). The final half must be implemented
     * by subclasses of Huskeliste.
     *
     */
    public Huskeliste() {
        setTitle("Huskeliste");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        txaScreen = new JTextArea(20,100);
        txaScreen.setEditable(false);
        add(new JScrollPane(txaScreen),BorderLayout.CENTER);

        test();
    }

    /**
     * Build the visual representation of the functions
     * defined in createTriggers()
     */
    protected abstract void render();

    /**
     * Populate the "triggers" array.
     * ActionListeners should be initialised in this method.
     */
    protected abstract void createTriggers();

    /**
     * Test the interface
     */
    private void test() {
        //Test appointments
        appointments.add(new Avtale("Doctor", "Renew medicine", "01.01.16 13:00"));
        appointments.add(new Avtale("Doctor", "Renew medicine", "01.01.16 13:00"));
        appointments.add(new Avtale("Doctor", "Renew medicine", "01.01.16 23:59"));
        appointments.add(new Avtale("Doctor", "Renew medicine", "20.10.16 23:59"));
        appointments.add(new Avtale("Doctor", "Renew medicine", "20.10.16 12:30"));
        appointments.add(new Avtale("Doctor", "Renew medicine", "12.12.16 07:00"));
        showAllAppointments();
    }

    /**
     * Call a method respective to each trigger
     * @param e the trigger activated in the Huskeliste
     */
    public void actionPerformed(ActionEvent e) {
        AbstractButton triggered = (AbstractButton) e.getSource();

        if (triggered == triggers[6]) {           //"Avslutt" function
            System.exit(0);
        }
        else if (triggered == triggers[0]) {      //"Ny avtale" function
            newAppointment();
            showAllAppointments();
        }
        else {
            // All remaining functions are useless if the appointment
            // list is empty, so we check for that first;
            if (appointments.isEmpty()) {
                txaScreen.setText("");
                txaScreen.append("Ingen avtaler.");
                JOptionPane.showMessageDialog(this, "Ingen avtaler.");
            }
            else if (triggered == triggers[1]) {  //"Vis avtale" function
                showAppointmentsPerDay();
            }
            else if (triggered == triggers[2]) {  //"Vis alle avtaler" function
                showAllAppointments();
            }
            else if (triggered == triggers[3]) {  //"Fjern avtale" function
                removeAppointment(TimeType.TIMESTAMP);
                showAllAppointments();
            }
            else if (triggered == triggers[4]) {  //"Fjern avtaler" function
                removeAppointment(TimeType.DAY);
                showAllAppointments();
            }
            else if (triggered == triggers[5]) {  //"Fjern alle avtaler" function
                removeAllAppointments();
            }
        }
    }

    /**
     * Ask the user for input to create a new appointment and adds it to the
     * list of appointments.
     * If an appointment with the same timestamp (date AND time) is already
     * in the list, the user is given the possibility to replace it with the
     * new entry.
     *
     * Only appointments with valid timestamps and no null arguments
     * will be created.
     */
    private void newAppointment() {
        String description = "", content = "", timestamp = "";
        boolean validInput = false;

        description = JOptionPane.showInputDialog(this, "Beskrivelse");
        if (description != null && !description.isEmpty()) {
            content = JOptionPane.showInputDialog(this, "Innhold");
            if (content != null && !content.isEmpty()) {
                timestamp = getTimeInput(TimeType.TIMESTAMP);
                validInput = timestamp != null;
            }
        }
        int index = 0;
        while(validInput && index < appointments.size()){

            if(appointments.get(index).getTidspunkt().equals(timestamp))
            {
                String msg = String.format(
                        "Det finnes en avtale på tidspunket %s: \n"
                        + "Beskrivelse: %s\n"
                        + "Innhold: %s\n\n"
                        + "Vil du erstatte den med din nye? \n"
                        + "Beskrivelse: %s\n"
                        + "Innhold: %s\n",
                        timestamp,
                        appointments.get(index).getBeskrivelse(),
                        appointments.get(index).getInnhold(),
                        description,content
                );
                int answer = JOptionPane.showConfirmDialog(
                        this,
                        msg,
                        "Tidspunkt allerede i bruk",
                        JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION){
                    appointments.set(index, new Avtale(description,content,timestamp));
                    return;
                }
                else{
                    validInput = false;
                }
            }
            index++;
        }

        if(validInput){
            appointments.add(new Avtale(description, content, timestamp));
            JOptionPane.showMessageDialog(this, "Avtalen ble lagt inn.");
        }
        else {
            JOptionPane.showMessageDialog(this, "Operasjon er avsluttet.");
        }
    }

    /**
     * Format and add the content of an appointment object
     * to the JTextArea
     * @param a     the appointment to output
     */
    private void printAppointment(Avtale a){
        String pattern = "%-20s %-40s %-40s";
        String formatted = String.format(pattern, a.getBeskrivelse(), a.getInnhold(), a.getTidspunkt());
        txaScreen.append(formatted + "\n");
    }

    /**
     * Request an input from the user and, if the input is
     * a valid day string, prints out all appointments whose
     * day substring matches the input.
     */
    private void showAppointmentsPerDay() {

        String day = getTimeInput(TimeType.DAY);
        if (day == null) return;

        boolean found = false;
        txaScreen.setText("");
        int dayLength = TimeType.DAY.FORMAT.length();
        for (Avtale a : appointments) {
            String dayString = a.getTidspunkt().substring(0, dayLength);
            if (dayString.equals(day)){
                found = true;
                printAppointment(a);
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingen avtaler på " + day,
                    "Ingen avtaler",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Print to the text area all appointments
     * present in the appointment list
     */
    private void showAllAppointments() {
        txaScreen.setText("");
        for (Avtale a : appointments){
            printAppointment(a);
        }
    }

    /**
     * Ask the user for a time input and remove any appointment
     * whose tidspunkt matches the input.
     * The validity of the input is checked according to the
     * timeType passed as parameter.
     * @param timeType  the time scope used to check the input
     */
    private void removeAppointment(TimeType timeType) {
        String time = getTimeInput(timeType);
        if (time == null) return;

        int listStartLength = appointments.size();
        int formatLength = timeType.FORMAT.length();

        Iterator<Avtale> it =  appointments.iterator();
        while (it.hasNext()){
            String timeStr = it.next().getTidspunkt().substring(0,formatLength);
            if(timeStr.equals(time)){
                it.remove();
            }
        }
        // Notify the user if no appointment matched the input
        if(listStartLength == appointments.size()){
            JOptionPane.showMessageDialog(this,"Ingen avtaler på " + time);
        }
    }

    /**
     * Remove all appointments from the list
     */
    private void removeAllAppointments() {
        appointments.clear();
        txaScreen.setText("");
        txaScreen.append("Ingen avtaler.\n");
    }

    /**
     * Prompt an input dialog requesting for a time string.
     * The input time is validated according to the
     * time type passed as parameter
     * @param timeType  the time type requested
     * @return  a valid time string, or null if the input was not valid
     */
    private String getTimeInput(TimeType timeType) {

        String type = timeType.TYPE;
        String format = timeType.FORMAT;

        String input = JOptionPane.showInputDialog(this, type + "\n(format: " + format + ")");

        return isValidTime(input,timeType) ? input.trim() : null;
    }

    /**
     * Check if a string corresponds to a given time format
     * The parse attempt and formatting reversal in the try-catch
     * body was inspired by the code on this page:
     * http://www.dreamincode.net/forums/topic/14886-date-validation-using-simpledateformat/
     *
     * @param time  the string to validate
     * @param timeType  the format to check the time String against
     * @return true only if time is not null AND is a valid date according to timeType.FORMAT
     */
    private boolean isValidTime(String time, TimeType timeType) {

        boolean isValid = true;

        if (time == null || time.isEmpty()) {
            return false;
        } else {

            String type = timeType.TYPE;
            String format = timeType.FORMAT;
            String example = timeType.EXAMPLE;
            try {

                // Check that the input does parse according
                // to the format given by the timeType
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.FRANCE);
                Date timeOutput = formatter.parse(time.trim());

                // Check that the parsed input corresponds to the
                // intended time (i.e: filter out calendar rollover)
                isValid = formatter.format(timeOutput).equals(time.trim());

            } catch (ParseException exc) {
                JOptionPane.showMessageDialog(
                        this,
                        type + " må følge format " + format + "\n" +
                                "eks: " + example,
                        "Feil tidsformat",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(
                        this,
                        type + " er ikke gyldig.",
                        "Feil tidspunkt",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        return isValid;
    }


}
