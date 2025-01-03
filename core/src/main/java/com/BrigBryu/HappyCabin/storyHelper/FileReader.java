package com.BrigBryu.HappyCabin.storyHelper;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Creates a node head of a dialog/adventure tree from a .story file
 * \n
 * .story files can be handled by this FileReader with these escape sequences:
 * \var nameOfVariable this uses what ever the string value is of nameOfVariable note must be accessible to FileReader
 * \parent n creates a pointer to the parent n steps above if it is null it will point to RETURN_TO_CABIN_KEY
 *         note: return on null can be configured by not using empty constructor
 * .story files should be structured as following
 * \n
 * example of a .story file:
 * /var START:message
 * {
 *     child1
 *     child2
 * }
 * child1:message2
 * {
 *     /parent 1
 * }
 * child2:message3
 * {
 *     child4
 *     /parent 1
 * }
 * //NOTE: \parent 1 in child1 is unneeded as empty options creates a continue that directs to its parent
 * child4:message5
 * {
 *     /parent 2
 * }
 */
public class FileReader {
    private final String PATH_TO_FILE_AFTER_NULL;
    private LinkedList<String> escapeSequences;

    public FileReader(){
        this.PATH_TO_FILE_AFTER_NULL = "cabinHub.story"; //TODO need to make this file
        initEscapeSequences();
    }

    private void initEscapeSequences() {
        escapeSequences = new LinkedList<>();
        escapeSequences.add("/parent");
        escapeSequences.add("/var");
    }

    public FileReader(String PATH_TO_FILE_AFTER_NULL){
        this.PATH_TO_FILE_AFTER_NULL = PATH_TO_FILE_AFTER_NULL;
    }

    public QuestionNode createStory(String path){
        return parseFile(path);
    }

    private QuestionNode parseFile(String path){
        Scanner file;
        try {
            file = new Scanner(Gdx.files.internal(path).file());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find file for parseFile when trying to find: " + path + e);
        }
        boolean insideChildAsginment;
        while(file.hasNextLine()){
            String line = file.nextLine();
            if(line.isEmpty()){
                continue;
            } else if(line.equals("{")){
                insideChildAsginment = true;
            } else if(line.equals("}")){
                insideChildAsginment = false;
            } else {
                //inside QuestionNode declaration
                String[] words = line.split(" ");
                for(int i = 0; i < words.length; i++){
                    //check for escape sequences
                    for(String sequence:escapeSequences){
                        if(words[i].equals(sequence.toString())){

                        }
                    }
                }
            }
        }
                return null;
    }
    //escape sequence
    public abstract class EscapeSequence {
        private String sequence;

        protected EscapeSequence(String sequence){
            this.sequence = sequence;
        }

        protected String getSequence(){
            return sequence;
        }

        protected abstract String processNextWord();
    }

    private class EscapeSequenceParent extends EscapeSequence{

        protected EscapeSequenceParent(String sequence) {
            super(sequence);
        }

        @Override
        protected String processNextWord() {
            return "";
        }

    }
}
