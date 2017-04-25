import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for reading input from file and terminal.
 * 
 * @author Clément Marescaux (only added the getInput() method)
 * @version 2015-OCT-21
 */
public class InputReader
{
    private Scanner reader;

    /**
     * Constructor for objects of class InputReader
     */
    public InputReader()
    {
        reader = new Scanner(System.in);
        
    }

    /**
     * Return all the words in a file.
     * 
     * @param    filename the name of the file
     * @return   an arraylist of all the words in the file
     */
    public ArrayList<String> getWordsInFile(String filename)
    {
        ArrayList<String> words = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename),
                        "8859_1"));
            String line = in.readLine();
            while(line != null) {
                String [] elements = line.split(" ");
                for(int i = 0 ; i < elements.length ; i++){
                    words.add(elements[i]);
                }
                line = in.readLine();
            }
            in.close();
        }
        catch(IOException exc) {
            System.out.println("Error reading words in file: " + exc);
        }
        return words;
    }
    
    /**
     * Starts a prompt and stores user input from the terminal into a HashSet 
     * (all duplicates will automatically be filtered away).
     * 
     * @return A HashSet containing Strings entered by the user
     */
    public HashSet<String> getInput()
    {
        System.out.print("> ");
        
        String inputLine = reader.nextLine().trim(); 
        String[] wordArray = inputLine.split(" ");
        
        HashSet<String> wordList = new HashSet<>();
        for (String word : wordArray)
        {
            if( word.equals("") ){
                System.out.println("You pressed 'enter' without entering a word. Try again.");
            }
            else{                        
                wordList.add(word);
            }
        }        
        
        return wordList;
    }   
}

