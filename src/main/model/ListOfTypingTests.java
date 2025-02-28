package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Class representing the ListOfTypingTests
public class ListOfTypingTests implements Writable {
    private ArrayList<TypingTest> listOfTypingTests;

    // effects: intialises the list of typing Tests for the instance
    public ListOfTypingTests() {
        this.listOfTypingTests = new ArrayList<>();

    }

    // modifies: this
    // effects: adds a typing test to the list of typing tests
    // : such that the first typing test added has the index
    // : of 0 and the last typing test will have the index of
    // : listofTypingTest.size()-1
    // : logs the addition of a typing test in the EventLog
    public void addTests(TypingTest typingTest) {
        this.listOfTypingTests.add(typingTest);
        EventLog.getInstance()
                .logEvent(new Event("Added typing test " + typingTest.getIdNo() + " to the list of typing tests"));

    }

    // REQUIRES: index to be within the bounds of the listOfTypingTest Array
    // effects: returns the typing test at the given index
    public TypingTest getTypingTestAtIndex(int index) {
        return listOfTypingTests.get(index);
    }

    public ArrayList<TypingTest> getListOfTypingTests() {
        return listOfTypingTests;
    }

    public int getSize() {
        return listOfTypingTests.size();
    }

    public boolean isEmpty() {
        return listOfTypingTests.isEmpty();
    }

    /*
     * Requires: index to be within the bounds
     * of the length of the listOfTypingTest and be
     * a non-negative number
     * Modifies: this
     * Effects: removes the typingTest from the specifed index
     * from the listOfTypingTest
     * Logs an event whenever a test is removed from the list
     */
    public void removeTypingTestFromTestAtIndex(int index) {
        TypingTest t1 = listOfTypingTests.get(index);
        listOfTypingTests.remove(index);
        EventLog.getInstance().logEvent(new Event("removed " + t1.getIdNo() + " from the list of typing tests"));
    }

    /*
     * Effects: if a blank or empty string is entered
     * Return an array list containing all the names
     * of the typing tests in the list
     * else, return all typing tests that contain
     * that have the wpm specified by the user
     * and logs the event
     */
    public ArrayList<String> filterTypingTest(String query) {
        ArrayList<String> ftt = new ArrayList<>();
        if (query.isBlank() || query.isEmpty()) {
            for (TypingTest t1 : listOfTypingTests) {
                ftt.add("" + t1.getIdNo());
            }
            EventLog.getInstance()
                    .logEvent(new Event("Filtering Tests that have wpm " + query + " from the list"));
            return ftt;
        } else {
            for (TypingTest t1 : listOfTypingTests) {
                String wpm = "" + t1.getStatistics().getWPM();
                if (wpm.contains(query)) {
                    ftt.add("" + t1.getIdNo());
                }
            }
            EventLog.getInstance()
                    .logEvent(new Event("Filtering Tests that have wpm " + query + " from the list"));
            return ftt;
        }
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("list of typing tests", typingTestsToJson());
        return json;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS : Helper method for toJson
    // : Converts all the typing tests in the listOfTypingTests into
    // : a JSON array and returns it to toJson
    public JSONArray typingTestsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (TypingTest ti : listOfTypingTests) {
            jsonArray.put(ti.toJson());
        }
        return jsonArray;

    }

}
