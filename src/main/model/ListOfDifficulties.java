package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//Class representing the ListOfDifficulties
public class ListOfDifficulties implements Writable {
    private ArrayList<Difficulty> listOfDifficulties;
    // Effect: initialise the list of difficulties

    public ListOfDifficulties() {
        listOfDifficulties = new ArrayList<>();
    }

    /*
     * Modifies: this
     * Effects: adds a difficulty to the list of difficulty
     * with the first difficulty added being at index 0
     * and the last difficulty added being at index
     * listOfDifficulties.size()-1
     * logs the addition of a new difficulty to the list of difficulties
     */
    public void addDifficulties(Difficulty difficulty) {
        listOfDifficulties.add(difficulty);
        EventLog.getInstance()
                .logEvent(new Event("Added difficulty " + difficulty.getDifficulty() + " to the list of difficulties"));

    }

    // requires: the index passed to be within the bounds of the array list
    // effects: returns difficulty at the given index
    public Difficulty getDifficultyAtIndex(int index) {
        return listOfDifficulties.get(index);
    }

    public ArrayList<Difficulty> getListOfDifficulties() {
        return listOfDifficulties;
    }

    public int getSize() {
        return listOfDifficulties.size();
    }

    public Boolean isEmpty() {
        return listOfDifficulties.isEmpty();
    }

    /*
     * Effects: If the string inputted is blank or empty, then
     * the an arrayList of the name of all difficulties in the list is returned
     * else, all the difficulty names containing filter
     * the String filter are returned
     * if no matches are found, returns an empty list
     * logs the filteration of the list of difficulties
     */
    public ArrayList<String> filterListOfDifficulties(String filter) {
        ArrayList<String> filteredListOfDifficulties = new ArrayList<>();
        if (filter.isBlank() || filter.isEmpty()) {
            for (Difficulty d1 : listOfDifficulties) {
                filteredListOfDifficulties.add(d1.getDifficulty());
            }
            EventLog.getInstance()
                    .logEvent(new Event("Filtering Difficulties that contain the string " + filter + " from the list"));
            return filteredListOfDifficulties;
        } else {
            for (Difficulty d1 : listOfDifficulties) {
                if (d1.getDifficulty().contains(filter)) {
                    filteredListOfDifficulties.add(d1.getDifficulty());
                }
            }
            EventLog.getInstance()
                    .logEvent(new Event("Filtering Difficulties that contain the string " + filter + " from the list"));
            return filteredListOfDifficulties;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("list of difficulties", difficultiesToJson());
        return json;

    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS : returns difficulties in this listOfDifficulties as a JSON array
    // This is a helper method for the toJson method in this class
    public JSONArray difficultiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Difficulty di : listOfDifficulties) {
            jsonArray.put(di.toJson());
        }

        return jsonArray;
    }

}
