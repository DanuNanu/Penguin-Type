package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.lang.Math;
import java.lang.StringBuilder;
// Represents a single typing test having an array of 
// words associated to it, difficulty, time, statistics such as wpm, and consistency

public class TypingTest implements Writable {

    private static int testNumber = 1; // Tracks the number of the test created
    private Difficulty difficulty; // difficulty of the test
    private Statistics statistics; // statistics related to the test
    private String sentences; // the sentence which the user had to type out in the individual tests
    private int wordCount; // word count of the array used to generate each sentence
    private int id; // Id of each typing test created
    private ArrayList<String> words;

    /*
     * EFFECTS: Creates a test with:
     * Sets unique test number to each test
     * Sets the difficulty of the test to the chosen difficulty3
     * creates new instance of Statistics
     * Intialises sentences to an empty string
     */
    public TypingTest(Difficulty difficulty) {
        this.id = testNumber++;
        this.difficulty = difficulty;
        this.statistics = new Statistics();
        this.wordCount = difficulty.getWordCount();
        this.words = difficulty.getWords();
        this.sentences = "";
    }

    /*
     * EFFECTS: constructor used to read ID directly into the test
     * for reading from JSON in the readDifficulty method of
     * the JsonReader class (method overloading)
     */
    public TypingTest(Difficulty difficulty, int id) {
        this.id = id;
        this.difficulty = difficulty;
        this.statistics = new Statistics();
        this.wordCount = difficulty.getWordCount();
        this.words = difficulty.getWords();
        this.sentences = "";

    }

    /*
     * Modifies: this
     * Effects: Generates a new random sentence for the user to practice with
     * : by picking a random string in the list words
     * : checks if the i-th is less than the word count
     * : if that is the case it places a space after each word
     * : Updates the wordCountOfSentence to accomodate for the spaces
     * : added to the sentence.
     */
    public void generateTestSentence() {
        StringBuilder sentenceBuilder = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            sentenceBuilder.append(words.get((int) (Math.random() * words.size())));

            if (i < (wordCount - 1)) {
                sentenceBuilder.append(" ");
            }

        }
        this.sentences = sentenceBuilder.toString();
    }

    public String getSentences() {
        return sentences;
    }

    public int getIdNo() {
        return this.id;
    }

    public String getTestDifficultyName() {
        return difficulty.getDifficulty();
    }

    public Difficulty getTestDifficulty() {
        return difficulty;
    }

    public int getWordCount() {
        return wordCount;
    }

    public Statistics getStatistics() {
        return this.statistics;
    }

    public void setSentences(String sentence) {
        this.sentences = sentence;
    }

    // MODIFIES: this
    // EFFECTS: resets the ID whenever a new instance of the
    // TypingTestUI class is created
    public static void resetID() {
        testNumber = 1;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("test sentence", this.sentences);
        json.put("id", this.id);
        json.put("difficulty", this.difficulty.toJson());
        json.put("test word count", wordCount);
        json.put("test words", this.words);
        json.put("statistics", this.statistics.toJson());
        return json;
    }

}
