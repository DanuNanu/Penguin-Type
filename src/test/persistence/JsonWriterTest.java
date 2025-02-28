package persistence;

import model.Difficulty;
import model.ListOfDifficulties;
import model.Statistics;
import model.TypingTest;
import model.ListOfTypingTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @BeforeEach
    void runBefore() {
        TypingTest.resetID();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfTypingTests t1 = new ListOfTypingTests();
            ListOfDifficulties d1 = new ListOfDifficulties();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLTTandLD() {
        try {
            ListOfTypingTests t1 = new ListOfTypingTests();
            ListOfDifficulties d1 = new ListOfDifficulties();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLTTandLD.json");
            writer.open();
            writer.write(t1, d1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLTTandLD.json");
            t1 = reader.readTypingTests();
            d1 = reader.readDifficulties();
            assertTrue(t1.getListOfTypingTests().isEmpty());
            assertTrue(d1.getListOfDifficulties().isEmpty());

        } catch (IOException e) {
            fail("Exception should have been thrown");
        }
    }

    @Test
    void testWriterGeneralLTandLD() {
        try {
            ListOfTypingTests t1 = new ListOfTypingTests();
            ListOfDifficulties d1 = new ListOfDifficulties();
            JsonWriter writer = new JsonWriter("data/testWriterGeneralLttandLD.json");
            ArrayList<String> words1 = new ArrayList<String>();
            words1.add("shrek");
            words1.add("is");
            words1.add("love");
            words1.add("life");
            ArrayList<String> words2 = new ArrayList<String>();
            words2.add("give");
            words2.add("me");
            words2.add("full");
            words2.add("marks");
            words2.add("please");
            d1.addDifficulties(new Difficulty("Easy", words1, 6, 30));
            d1.addDifficulties(new Difficulty("Medium", words2, 5, 45));
            TypingTest tt1 = new TypingTest(d1.getDifficultyAtIndex(0));
            tt1.setSentences("shrek is love shrek is life");
            TypingTest tt2 = new TypingTest(d1.getDifficultyAtIndex(1));
            tt2.setSentences("give me full marks please");
            t1.addTests(tt1);
            t1.addTests(tt2);
            writer.open();
            writer.write(t1, d1);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralLttandLD.json");
            t1 = reader.readTypingTests();
            d1 = reader.readDifficulties();
            assertEquals(t1.getListOfTypingTests().size(), 2);
            assertEquals(d1.getListOfDifficulties().size(), 2);
            ArrayList<TypingTest> lot = t1.getListOfTypingTests();
            ArrayList<Difficulty> lod = d1.getListOfDifficulties();
            Statistics s1 = lot.get(0).getStatistics();
            Statistics s2 = lot.get(1).getStatistics();
            checkTypingTest(1, lod.get(0), lot.get(0).getStatistics(), 6, words1, "shrek is love shrek is life",
                    lot.get(0));
            checkTypingTest(2, lod.get(1), lot.get(1).getStatistics(), 5, words2, "give me full marks please",
                    lot.get(1));
            checkDifficulty("Easy", 6, 30, words1, lod.get(0));
            checkDifficulty("Medium", 5, 45, words2, lod.get(1));
            checkStatistics(0, 0.0, 0, 0, 0, 0, s1);
            checkStatistics(0, 0.0, 0, 0, 0, 0, s2);

        } catch (Exception e) {
            fail("Excepition should not have been thrown");
        }
    }

}
