package persistence;

import model.ListOfDifficulties;
import model.ListOfTypingTests;
import org.json.JSONObject;
import java.io.*;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a writer that writes JSON representation of ListOfDifficulties
// and ListOfTypingTests to a file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    
    //EFFECTS: constructs a writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS : writes Json representation of the ListOfTypingTests and
    // ListOfDifficulties to file
    public void write(ListOfTypingTests lott, ListOfDifficulties lod) {
        JSONObject json = new JSONObject();
        json.put("list of typing tests", lott.typingTestsToJson()); 
        json.put("list of difficulties", lod.difficultiesToJson()); 
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // Effects: closes writer
    public void close() {
        writer.close();
    }

    // Modifies: this
    // EFFECTS : writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }










}
