package persistence;

import model.Difficulty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Statistics;
import model.TypingTest;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import java.util.ArrayList;

// concrete class including all tests common between the JsonReaderTest and JsonWriterTest class

public class JsonTest {
    protected void checkDifficulty(String difficulty, int wordCount, int time, ArrayList<String> words, Difficulty d1) {
        assertEquals(difficulty, d1.getDifficulty());
        assertEquals(wordCount, d1.getWordCount());
        assertEquals(time, d1.getTime());
        assertEquals(words, d1.getWords());
    }

    protected void checkTypingTest(int id, Difficulty d1, Statistics s1, int wordCount, ArrayList<String> words,
            String sentence, TypingTest t1) {
        assertEquals(id, t1.getIdNo());
        assertEquals(d1.getDifficulty(), t1.getTestDifficulty().getDifficulty());
        assertEquals(d1.getWordCount(), t1.getTestDifficulty().getWordCount());
        assertEquals(d1.getTime(), t1.getTestDifficulty().getTime());
        assertEquals(d1.getWords(), t1.getTestDifficulty().getWords());
        assertEquals(wordCount, t1.getWordCount());
        assertEquals(words, t1.getTestDifficulty().getWords());
        assertEquals(sentence, t1.getSentences());
        assertEquals(s1.getTimetaken(), t1.getStatistics().getTimetaken());
        assertEquals(s1.getStartingTime(), t1.getStatistics().getStartingTime());
        assertEquals(s1.getAccuracy(), t1.getStatistics().getAccuracy(), 0.01);
        assertEquals(s1.getWPM(), t1.getStatistics().getWPM(), 0.01);
        assertEquals(s1.getTotalWordsTyped(), t1.getStatistics().getTotalWordsTyped());
        assertEquals(s1.getCorrectWords(), t1.getStatistics().getCorrectWords());
    }

    protected void checkStatistics(int totalWordsTyped, double accuracy, int correctWords, long startTime,
            long timetaken, double wpm, Statistics s1) {
        assertEquals(totalWordsTyped, s1.getTotalWordsTyped());
        assertEquals(accuracy, s1.getAccuracy());
        assertEquals(correctWords, s1.getCorrectWords());
        assertEquals(startTime, s1.getStartingTime());
        assertEquals(timetaken, s1.getTimetaken());
        assertEquals(wpm, s1.getWPM());
    }

}