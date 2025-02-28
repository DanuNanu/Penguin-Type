package persistence;

import org.json.JSONObject;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Returns each writable aspect of this typing test as a JSON object for storage

public interface Writable {

    //EFFECTS: returns this as a JSON object

    JSONObject toJson();
    

}
