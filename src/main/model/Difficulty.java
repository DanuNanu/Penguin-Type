package model;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;
import persistence.Writable;

//Class representing the difficulty
public class Difficulty implements Writable {
    private String difficulty; // difficulty of the test
    private ArrayList<String> words; // the list of words used to generate the test sentence
    private int wordCount; // the word count of the test sentence
    private int time; // time limit for the typing test
    private static final String FILE_PATH = "src/main/TextFile/words.txt";

    /*
     * effects: creates a difficulty for the typing test with difficult
     * sets the name of the difficulty to string difficulty
     * 
     * sets the wordcount according to the wordcount passed through
     * sets the words used in the test as the words in the words.text file
     * sets the word count as the word count defined by the user
     * sets the time limit of the test as the time limit described by the uset
     */

    public Difficulty(String difficulty, int wordCount, int time) {
        this.difficulty = difficulty;
        this.wordCount = wordCount;
        this.time = time;
        this.words = new ArrayList<String>();
        try {
            this.words = readWordsFromFile(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // setter for the words field
    private ArrayList<String> readWordsFromFile(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String word = reader.readLine();
            ArrayList<String> words = new ArrayList<>();
            while (word != null) {
                words.add(word);
                word = reader.readLine();
            }

            return words;
        }
    }

    /*
     * Requires: the user to pass at least one non empty word to the constructor
     * effects: sets the difficulty of the test to the difficulty described
     * checks if the difficulty described by the user is not already defined
     * sets the words according to the list of words described by the user
     * sets the time according to the time passed by the user
     * sets the word count of the test as the word count set by the user
     */

    public Difficulty(String difficulty, ArrayList<String> words, int wordCount, int time) {
        this.difficulty = difficulty;
        this.words = words;
        this.time = time;
        this.wordCount = wordCount;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public ArrayList<String> getWords() {
        return this.words;

    }

    public int getWordCount() {
        return this.wordCount;
    }

    public int getTime() {
        return this.time;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("difficulty name", this.difficulty);
        json.put("list of words", this.words);
        json.put("word count", this.wordCount);
        json.put("time limit", this.time);
        return json;
    }
}
