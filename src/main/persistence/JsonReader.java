package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Represents a reader that reads ListOfTypingTests and 
// ListOfDifficulties from JSON data stored in file
public class JsonReader {
    String source;

    // EFFECTS: constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS : reads the ListOfTypingTests from file and returns it
    // throws IOException if an error occurs reading data from the file
    public ListOfTypingTests readTypingTests() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLoTT(jsonObject);
    }

    // EFFECTS : reads the ListOfDifficulties from file and returns it
    // throws IOException if an error occurs reading data from the file
    public ListOfDifficulties readDifficulties() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDifficulties(jsonObject);

    }

    // EFFECTS : reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }
        return stringBuilder.toString();
    }


    //EFFECTS: parses the ListOfTypingTests from Json object and returns it
    private ListOfTypingTests parseLoTT(JSONObject jsonObject) {
        ListOfTypingTests lott = new ListOfTypingTests();
        addTypingTests(lott, jsonObject);
        return lott;
    }

    //MODIFIES: lott
    //EFFECTS: patses typingTests from JSON object and adds them to the ListOfTypingTests
    private void addTypingTests(ListOfTypingTests lott, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list of typing tests");
        for (Object json : jsonArray) {
            JSONObject nextTypingTest = (JSONObject) json;
            addTypingTest(lott, nextTypingTest);

        }
    }

    //MODIFIES: Lott
    // EFFECTS: parses TypingTests from the JSON object and adds it to the ListOfTypingTests
    private void addTypingTest(ListOfTypingTests lott, JSONObject jsonObject) {
        Difficulty difficulty = parseDifficulty(jsonObject.getJSONObject("difficulty"));
        int id = jsonObject.getInt("id");
        TypingTest typingTest = new TypingTest(difficulty, id);
        String sentences = jsonObject.getString("test sentence");
        typingTest.setSentences(sentences);

        JSONObject statsJson = jsonObject.getJSONObject("statistics");

        typingTest.getStatistics().setTimeTaken(
                statsJson.getLong("time taken"));

        typingTest.getStatistics().setStartTime(
                statsJson.getLong("starting time"));

        typingTest.getStatistics().calculateAccuracy(
                statsJson.getInt("correct words"),
                statsJson.getInt("total words typed"));

        typingTest.getStatistics().calculateWPM(
                statsJson.getInt("correct words"),
                statsJson.getLong("time taken"));

        typingTest.getStatistics().updateStatistics(
                statsJson.getInt("total words typed"),
                statsJson.getInt("correct words"));

        lott.addTests(typingTest);
    }


    //EFFECTS: Parses difficulties from the JSON object, constructs 
    // new difficulty and returns it
    private Difficulty parseDifficulty(JSONObject jsonObject) {
        String difficultyName = jsonObject.getString("difficulty name");
        int wordCount = jsonObject.getInt("word count");
        int timeLimit = jsonObject.getInt("time limit");

        ArrayList<String> words = new ArrayList<>();
        JSONArray jsonWords = jsonObject.getJSONArray("list of words");
        for (Object json : jsonWords) {
            words.add((String) json);
        }

        return new Difficulty(difficultyName, words, wordCount, timeLimit);
    }

    //EFFECTS: parses a ListOfDifficulties from JSON object and returns it
    private ListOfDifficulties parseDifficulties(JSONObject jsonObject) {
        ListOfDifficulties listOfDifficulties = new ListOfDifficulties();
        addDifficulties(listOfDifficulties, jsonObject);
        return listOfDifficulties;
    }


    //MODIFIES: lod
    //EFFECTS: parses Difficulties from JSON object and adds them to the ListOfDifficulties
    private void addDifficulties(ListOfDifficulties lod, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list of difficulties");
        for (Object json : jsonArray) {
            JSONObject nextDifficulty = (JSONObject) json;
            addDifficulty(lod, nextDifficulty);
        }
    }

    //MODIFIES: lod
    //EFFECTS: Parses difficulties from the JSON object and then returns it
    //        : Also parses an array of words from the JSON object and then
    //        : constructs difficulty using it
    private void addDifficulty(ListOfDifficulties lod, JSONObject jsonObject) {
        String difficultyName = jsonObject.getString("difficulty name");
        ArrayList<String> words = new ArrayList<>();
        int wordCount = jsonObject.getInt("word count");
        int timeLimit = jsonObject.getInt("time limit");
        JSONArray jsonWords = jsonObject.getJSONArray("list of words");
        for (Object json : jsonWords) {
            words.add((String) json);
        }

        Difficulty difficulty = new Difficulty(difficultyName, words, wordCount, timeLimit);
        lod.addDifficulties(difficulty);

    }

}
