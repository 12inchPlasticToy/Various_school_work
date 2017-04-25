------------------------------------------------------------------------
Innlevering 2, PGR100
------------------------------------------------------------------------

PROJECT TITLE: Innlevering 2
PURPOSE OF PROJECT: Create a story with random words
VERSION or DATE: 2015-10-22
HOW TO START THIS PROJECT: 
AUTHORS:    Per Lauvås - project shell
            Clément Marescaux - main working methods
            
USER INSTRUCTIONS: 
Create a StoryCreator object and call one of the three method.
Each time a method asks for a file name, write the file name
in quotes with its extension (ex: "myStory.txt"):

createAdjectiveStory(storyFilename, adjectivesFilename, outputFilename)
    storyFilename - the name of the story file you want to process
    adjectivesFilename - the name of the file containing the adjectives
    outputFilename - the name of the file that will be created

createAdjectiveStory(storyFilename, outputFilename)
    storyFilename - the name of the story file you want to process
    outputFilename - the name of the file that will be created

createAdjectiveStoryFromDictionary(storyFilename, dictionaryFilename, outputFilename)
    storyFilename - the name of the story file you want to process
    dictionaryFilename - the name of the dictionary you want to parse
    outputFilename - the name of the file that will be created

Note: for createAdjectiveStoryFromDictionary, the dictionary's internal structure 
must be exclusively composed of alternate pairs: a word followed by its type.
Ex: 
eat verb 
move verb
apple subs
hairy adj
This structure must be consistent throughout the whole list. The method will only 
consider words of type "adj".
