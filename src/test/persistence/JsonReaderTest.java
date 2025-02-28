package persistence;

import model.Difficulty;
import model.Statistics;
import model.TypingTest;
import model.ListOfDifficulties;
import model.ListOfTypingTests;
import java.util.ArrayList;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @BeforeEach
    void runBefore() {
        TypingTest.resetID();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/shrek.json");
        try {
            ListOfTypingTests lot = reader.readTypingTests();
            ListOfDifficulties lod = reader.readDifficulties();
            fail("IOException expected");
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLotandLod() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLTTandLD.json");
        try {
            ListOfTypingTests lot = reader.readTypingTests();
            ListOfDifficulties lod = reader.readDifficulties();
            assertTrue(lot.isEmpty());
            assertTrue(lod.isEmpty());
        } catch (Exception e) {
            fail("Could not read from the file");
        }
    }

    @Test
    void testFileLoading() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLTTandLD.json");
        try {
            ListOfTypingTests t1 = reader.readTypingTests();
            ListOfDifficulties d1 = reader.readDifficulties();
            assertNotNull(t1);
            assertNotNull(d1);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the error to help debug
            fail("Couldnt read from the file: " + e.getMessage());
        }

    }

    @Test
    void testReaderGeneralLOTandLOD() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLTTandLD.json");
        try {
            ListOfTypingTests t1 = reader.readTypingTests();
            ListOfDifficulties d1 = reader.readDifficulties();
            assertEquals(t1.getListOfTypingTests().size(), 2);
            assertEquals(d1.getListOfDifficulties().size(), 2);
            ArrayList<TypingTest> lot = t1.getListOfTypingTests();
            ArrayList<Difficulty> lod = d1.getListOfDifficulties();
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
            Statistics s1 = lot.get(0).getStatistics();
            Statistics s2 = lot.get(1).getStatistics();
            checkTypingTest(1, lot.get(0).getTestDifficulty(), lot.get(0).getStatistics(), 6, words1,
                    "shrek is love shrek is life", lot.get(0));
            checkTypingTest(2, lot.get(1).getTestDifficulty(), lot.get(1).getStatistics(), 5, words2,
                    "give me full marks please", lot.get(1));
            checkDifficulty("Easy", 6, 30, words1, lod.get(0));
            checkDifficulty("Medium", 5, 45, words2, lod.get(1));
            checkStatistics(0, 0.0, 0, 0, 30, 0, s1);
            checkStatistics(0, 0.0, 0, 0, 45, 0, s2);

        } catch (Exception e) {
            fail("Couldnt read from the file");
        }
    }

}
