import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

/**
 * A class containing different methods to create a story with random adjectives.
 * The source for adjectives differs for each method, and every adjective will 
 * only be used once. 
 * 
 * Requirements for every method:
 * - a story in a .txt file containing instances of 'ADJEKTIV' (case-sensitive)
 * placed wherever a random adjective should be added. 
 * - the list of adjectives must contain at least as many words as there are 
 * 'ADJEKTIV' instances in the story.
 * 
 * Adjective sources:
 * Method "Oppgave 1" - uses words listed in a .txt file
 * Method "Oppgave 2" - each adjective are entered by the user in the terminal
 * Method "Oppgave 3" - parses a .txt file containing word / type pairs and uses
 *                      the results of the parsing to populate the story                  
 * 
 * @author Clément Marescaux
 * @version 2015-OCT-22
 */
public class StoryCreator
{
    // Added by Clement Marescaux
    private static final String[] PUNCT_ARRAY = {"." , "!" , "?", "..." , "," , ";" };
    private static final ArrayList<String> PUNCTUATION = new ArrayList<>(Arrays.asList(PUNCT_ARRAY)); // Easier to use than an array.
    private InputReader reader;
    private OutputWriter writer;
    private Random random;
    private ArrayList<String> story;
    private ArrayList<String> adjectives;
    private HashSet<String> tempSet;

    // Key strings used throughout the class
    // Easily modifiable if a story contains a placeholder other than 'ADJEKTIV' 
    // or if we need to return a different type of word from the dictionary
    private String tag = "ADJEKTIV", wordType = "adj"; 

    /**
     * Create a story creator to use the different story creation methods. 
     * 
     * NOTE: the 'story' and 'adjectives' variables are assigned a value 
     * in each method instead of the constructor, so as to avoid conflicts 
     * if the methods are called more than once per session.
     */
    public StoryCreator()
    {
        reader = new InputReader();
        writer = new OutputWriter();
        random = new Random();
    }

    //###########//
    // Oppgave 1 //   
    /**
     * Creates a story populated with random adjectives listed in a text file.      
     *   
     * @param storyFileName         the name of the original text file containing the story with placeholder tags
     * @param adjectivesFilename    the name of the text file containing the list of adjectives
     * @param outputFilename        the name of the output text file 
     */
    public void createAdjectiveStory(String storyFilename, String adjectivesFilename, String outputFilename)
    {
        story       = new ArrayList<String>(reader.getWordsInFile(storyFilename));
        adjectives  = new ArrayList<String>(reader.getWordsInFile(adjectivesFilename));   

        int tagCount = wordOccurences(story, tag);      // How many words can be replaced
        adjectives = normaliseList(adjectives);         // Eliminates all duplicates

        if(adjectives.size() < tagCount){       
            System.out.println("Beklager. Det var ikke tilstrekkelig med adjektiver for å lage historien."); 
            System.out.println("Vennligst lag en fyldigere adjektivliste og prøv igjen!");
        } 
        else{             
            story = processText(story, adjectives, tag);
            writer.write(story, outputFilename);                       
        }
    }    

    //###########//
    // Oppgave 2 //      
    /**
     * Creates a file with a story populated with random adjectives added by the user from the terminal.
     * 
     * @param storyFileName     the name of the original text file containing the story with placeholder tags
     * @param outputFilename    the name of the output text file 
     */
    public void createAdjectiveStory(String storyFilename, String outputFilename)
    {
        story       = new ArrayList<String>( reader.getWordsInFile(storyFilename) );
        adjectives  = new ArrayList<String>();                      

        HashSet<String> adjectiveSet = new HashSet<>();        
        int tagCount = wordOccurences(story, tag); 

        printIntro(tagCount);
        boolean running = true;

        // Terminal starts
        while (running)
        {
            HashSet<String> input = reader.getInput();

            for (String word : input)
            {
                if ( adjectiveSet.contains(word) && !(word.equals("exit")) ){
                    System.out.println("## '" + word + "' is already in your list!");
                }
                else{
                    adjectiveSet.add(word);
                }
            }   

            if ( !(adjectiveSet.remove("exit")) ){                
                if( adjectiveSet.size() < tagCount ){
                    System.out.println("## " + (tagCount - adjectiveSet.size()) + " more adjectives to go!"); 
                }
                else{
                    System.out.println("## Your list of adjectives contains " + adjectiveSet.size() + " words.");
                    System.out.println("## Since your story needed " + tagCount + " replacements, you can enter 'exit' to make your story.");
                }
            }
            else{
                running = false;                
            }
        }
        // Terminal ends

        printOutro(adjectiveSet, tagCount);

        if(adjectiveSet.size() >= tagCount){   // Creates the story only if enough adjectives are entered                      
            adjectives.addAll(adjectiveSet);

            story = processText(story, adjectives, tag);            
            writer.write(story, outputFilename);           
        }

    }    

    //###########//
    // Oppgave 3 //      
    /**
     * Outputs a story populated with random adjectives from a dictionary file.
     * 
     * The dictionary file must only consists of word pairs - the word first, and then
     * its type.   
     * 
     * @param storyFileName         the name of the original text file containing the story with placeholder tags
     * @param dictionaryFilename    the name of the text file containing the dictionary
     * @param outputFilename        the name of the output text file 
     */
    public void createAdjectiveStoryFromDictionary(String storyFilename, String dictionaryFilename, String outputFilename)
    {        
        story       = new ArrayList<String>(reader.getWordsInFile(storyFilename));
        adjectives  = new ArrayList<>( reader.getWordsInFile(dictionaryFilename) );

        adjectives = filterAdjectives(adjectives, wordType);

        int tagCount = wordOccurences(story, tag);      // How many words can be replaced      
        adjectives = normaliseList(adjectives);         // Eliminates all duplicates

        if(adjectives.size() < tagCount){       
            System.out.println("Beklager. Det var ikke tilstrekkelig med adjektiver for å lage historien."); 
            System.out.println("Vennligst lag en fyldigere adjektivliste og prøv igjen!");
        } 
        else{             
            story = processText(story, adjectives, tag);
            writer.write(story, outputFilename);                       
        }
    }

    //
    // Story processing method - used by all three oppgave methods;
    //
    /**
     * Returns an ArrayList copy of the story where all instances of a defined word have been 
     * replaced at random by an adjective. As soon as an adjective has been used, it is 
     * removed from the adjective pool.  
     * 
     * @param inputText         a story containing the placeholders
     * @param adjectiveList     a list containing adjectives 
     * @param tag               the tag to look for and replace in 'inputText'
     * @return                  the story with all instances of 'tag' replaced by an adjective from 'adjectiveList'
     */
    private ArrayList<String> processText(ArrayList<String> inputText, ArrayList<String> adjectiveList, String tag)
    {
        ArrayList<String> outputText = new ArrayList<>(inputText);                     
        String currentWord, adjective, processedWord;
        int randomIndex = 0;    
        int indexOffset = 0;    // Explicitely initialised as 0 just for the first iteration

        for(int i = 0; i < outputText.size(); i++)
        {
            currentWord = outputText.get(i);            

            if (isValidMatch(currentWord, tag)){     
                randomIndex = random.nextInt(adjectiveList.size());
                adjective =  adjectiveList.get(randomIndex);
                processedWord = currentWord.replaceFirst(tag, adjective.toLowerCase());

                adjectiveList.remove(adjective);    // Each adjective will be used only once
            }
            else{
                processedWord = currentWord;
            }            

            if(i == 0 || isEndOfSentence(outputText.get( i - indexOffset ) )) 
            {
                processedWord = capitaliseWord(processedWord);      // Capitalises every word beginning a sentence - not just tags
            }            

            outputText.set(i, processedWord);
            indexOffset = 1;            
        }

        return outputText;
    }

    //////////////////////////////////////////////////
    ///     List methods    //////////////////////////
    //////////////////////////////////////////////////

    /**
     * Returns the number of instances of tag in the story 
     * 
     * @param storyWords    the list of words from the story
     * @param tag           the tag to find in 'storyWords'
     * @return              the number of instances of 'tag' found in 'storyWords'
     */
    private int wordOccurences(ArrayList<String> storyWords, String tag)
    {   
        int occurences = 0;

        if(storyWords.size() == 0 || tag == null)
        {
            // Fail-proofing 
            // Skips and returns 0;
        }
        else{
            for(String word : storyWords)
            {
                if(isValidMatch(word, tag)){
                    occurences++;
                }
            }
        }
        return occurences;
    }    

    /**
     * Returns a duplicate-free version of the passed ArrayList. 
     * 
     * @param stringList    the ArrayList containing possible duplicates
     * @return              an ArrayList only composed of unique Strings   
     */
    private ArrayList<String> normaliseList(ArrayList<String> stringList)
    {
        tempSet = new HashSet<>(stringList);    // Filters all duplicates from stringList
        ArrayList<String> normalisedList = new ArrayList<>(tempSet);
        return normalisedList;
    }    

    /**
     * Returns a list of a specific type of word from a dictionary ArrayList.
     * The dictionary must be a structure of alternative pairs, so that each 
     * word is placed on an even index, and its corresponding type on the 
     * following index.    
     * 
     * @param dictionary    the ArrayList containing the word / type pairs
     * @param wordTypeTag   the word type we want to return (case-sensitive)
     * @return              An ArrayList containing all words from dictionary that were paired with 'wordTypeTag'
     */
    private ArrayList<String> filterAdjectives(ArrayList<String> dictionary, String wordTypeTag)
    {
        ArrayList<String> adjectiveList = new ArrayList<>();        

        // Check first that the dictionary respects the "pairs-only" requirement
        if((dictionary.size() % 2) != 0){      
            System.out.println("Your dictionary file contains an uneven number of words.");
            System.out.println("Please make sure it ONLY contains pairs of words and try again!");
            System.out.println();
        }
        else{
            for(int i = 0; i < dictionary.size(); i += 2 )   
            {
                if( i < dictionary.size() && dictionary.get(i+1).equals(wordTypeTag) ) {
                    adjectiveList.add(dictionary.get(i));
                }
            }
        }

        return adjectiveList;
    }

    //////////////////////////////////////////////////
    ///     String methods    ////////////////////////
    //////////////////////////////////////////////////

    /**
     * Returns true if a word matches a tag (case sensitive)
     *   
     * @param word      the word being checked 
     * @param tag       the tag that 'word' must match 
     * @return          true only if 'word' (incl. its punctuation) matches 'tag'
     */    
    private boolean isValidMatch(String word, String tag)
    {
        boolean isMatch = false;
        if (!(word.startsWith(tag))){
            // Definitely not a match - skips and returns false;
        }
        else{            
            String wordEnd = word.substring(tag.length(), word.length());         
            if( word.equals(tag) || PUNCTUATION.contains(wordEnd) ){   
                isMatch = true;                                           
            }
        }
        return isMatch;        
    }   

    /**
     * Returns true if a word ends a sentence (i.e. the last character is a punctuation mark).
     * Skips composed words and only handles proper punctuation.
     * 
     * @param word      the word to check
     * @return          true if the last character of 'word' is a valid punctuation mark
     */
    private boolean isEndOfSentence(String word)
    {
        boolean endOfSentence = false;

        if (word.length() > 0){
            String endOfWord = word.substring(word.length()-1, word.length());
            if (PUNCTUATION.subList(0,4).contains(endOfWord) ){     // If and only if word's last char is exactly '.' '!' '?' or '...'
                endOfSentence = true;
            }
        }        
        return endOfSentence;
    }

    /**
     * Returns a word with its first letter in upper case.
     * 
     * @param word      a String to process
     * @return          'word' with its first letter capitalised
     */
    private String capitaliseWord(String word)
    {
        if (word.length() == 0 || word == null) {
            return word;
        }        
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    
    //////////////////////////////////////////////////
    ///   Print methods (for oppgave 2 only)  ////////
    //////////////////////////////////////////////////

    /**
     * Prints the introduction text for the terminal interface.
     * 
     * @param tagCount  the number of tags to replace in the story
     */
    private void printIntro(int tagCount)
    {
        System.out.println( 
            "################### ADJECTIVE STORY ################### \n"
            +   "# Your story needs at least " + tagCount + " adjectives to look great!\n"
            +   "# Enter at least as many adjectives. \n"
            +   "# If a list of words contains 'exit', the programme \n"
            +   "# will still add all the other words from the same input \n"
            +   "# before stopping ('exit' itself will not be added \n"
            +   "# to your list). \n"
            +   "# If you quit with less than " + tagCount + " adjectives entered, \n"
            +   "# no story will be written."
        );
        System.out.println();
    }

    /**
     * Prints the outro text for the terminal interface.
     * 
     * @param adjectiveSet  the set of words entered in the terminal so far
     * @param tagCount      the number of tags to replace in the story
     */
    private void printOutro(HashSet<String> adjectiveSet, int tagCount)
    {
        if ( adjectiveSet.size() < 1 ){
            System.out.println("## No words were entered in the list, no story will be written.");
            System.out.println("## The programme will exit now.");            
        }
        else if (adjectiveSet.size() < tagCount){
            System.out.println("## Only " + adjectiveSet.size() + " out of the required " + tagCount + " adjectives were entered.");
            System.out.println("## No story will be written. Restart the programme and try again!");
        }        
        else{
            System.out.println("Your list of adjectives contains " + adjectiveSet.size() + " word(s):");        
            for (String adjective : adjectiveSet)
            {
                System.out.println(" - " + adjective);
            }
            System.out.println("Enjoy!");
        }
    }
    /*
     * 
    // This would have been better to filter out the adjectives from the dictionary...
    private ArrayList<String> findAdjectives(ArrayList<String> dictionary)
    {
        ArrayList<String> dictionaryAdjectives = new ArrayList<>();
        String previousWord = null;
        
        for (String word : dictionary)
        {
            if (word.equals("adj")){
                dictionaryAdjectives.add(previousWord);
            }
            else{
                previousWord = word;
            }       
        }
        return dictionaryAdjectives;
    }
    */
}
