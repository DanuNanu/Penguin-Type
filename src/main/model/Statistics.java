package model;

import org.json.JSONObject;
import persistence.Writable;

//Class represening the statistics
public class Statistics implements Writable {
    private int totalWordsTyped; // Total words typed in the typing test
    private int correctWords; // Number of correct words typed in the typing test
    private long startTime; // Time that the user began a test
    private long timeTaken; // Time taken for the user to complete the test
    private double accuracy; // Accuracy of the user
    private double wpm; // words per minute of the user

    // effects: initialises all the fields of the statistics class as 0
    public Statistics() {
        this.totalWordsTyped = 0;
        this.accuracy = 0.0;
        this.correctWords = 0;
        this.startTime = 0;
        this.timeTaken = 0;
        this.wpm = 0.0;
    }

    // modifies: this
    // effects: records the starting time of the user in milliseconds
    // by setting the startTime to the users current
    // when the test is started
    public void startTimer() {
        this.startTime = System.currentTimeMillis();

    }

    // setter for totalWordsTyped and correctWords
    public void updateStatistics(int totalWordsTyped, int correctWords) {
        this.totalWordsTyped = totalWordsTyped;
        this.correctWords = correctWords;

    }

    // setter for the time taken
    // Requires: endTime > startTime > 0
    // Modifies: this
    // effects: sets the time taken as the difference
    // between the end time and the start time in seconds
    // by dividing the difference by 1000
    public void calculateTimeTaken(long startTime, long endTime) {
        this.timeTaken = (endTime - startTime) / 1000;

    }

    // setter for the accuracy
    // requires totalWordsTyped > 0 (technically this field is only 0
    // when the correctWords typed is also 0)
    // modifies: this
    // effects: checks whether the correct words typed is 0, if true
    // it sets accuracy as 0.0. if not it calculates the accuracy
    // as the number of correctWords divided by the totalWordsTyped
    public void calculateAccuracy(int correctWords, int totalWordsTyped) {
        if (correctWords == 0) {
            this.accuracy = 0.0;
        } else {
            this.accuracy = (double) correctWords / totalWordsTyped;
        }
    }

    // setter for the wpm
    // requires: the time taken > 0
    // modifies: this
    // effects: checks if the number of correct words typed
    // is 0, if this is true it sets wpm as 0. otherwise, it sets
    // the wpm as the correctWords divided by the time taken in minutes
    public void calculateWPM(int correctWords, long timeTaken) {
        if (correctWords == 0) {
            this.wpm = 0.0;
        } else {
            this.wpm = (double) correctWords / (timeTaken / 60.0);
        }

    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }

    public long getTimetaken() {
        return this.timeTaken;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public long getStartingTime() {
        return startTime;
    }

    public double getWPM() {
        return this.wpm;
    }

    public int getTotalWordsTyped() {
        return totalWordsTyped;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public void setTimeTaken(long timetaken) {
        this.timeTaken = timetaken;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time taken", this.timeTaken);
        json.put("accuracy", this.accuracy);
        json.put("starting time", this.startTime);
        json.put("wpm", this.wpm);
        json.put("total words typed", this.totalWordsTyped);
        json.put("correct words", this.correctWords);
        return json;
    }
}
