package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypingTestTest {
    private TypingTest typingTest;
    private TypingTest typingTest2;
    private Difficulty difficulty1;
    private Difficulty difficulty2;

    @BeforeEach
    void runBefore() {
        difficulty1 = new Difficulty("Difficult", 50, 120);
        difficulty2 = new Difficulty("Easy",60, 100);
        typingTest = new TypingTest(difficulty1);
        typingTest2 = new TypingTest(difficulty2);
    }

    @Test
    void testConstructorNumberUnique() {
        int id1 = typingTest.getIdNo();
        int id2 = typingTest2.getIdNo();
        assertFalse(id1 == id2);
        assertEquals(typingTest.getTestDifficulty(),difficulty1);
        assertEquals(typingTest2.getTestDifficulty(), difficulty2);
        assertEquals(typingTest.getWordCount(), difficulty1.getWordCount());
        assertEquals(typingTest.getWordCount(), difficulty1.getWordCount());
        // checking if the statistics object of the typingTest object is the same for the typing test object
        // hence each typingTest would be intiailised with its own statistics objext
        assertEquals(typingTest.getStatistics(), typingTest.getStatistics()); 
        assertEquals(typingTest2.getStatistics(), typingTest2.getStatistics());
        // checking if the statistics object pointing to each typingTest object is unique to that object
        assertFalse(typingTest.getStatistics() == typingTest2.getStatistics());
    }


    @Test
    void testConstructorDifficulty() {
        assertEquals(typingTest.getTestDifficultyName(), "Difficult");
        assertEquals(typingTest2.getTestDifficultyName(), "Easy");

    }

    @Test
    void testConstructorSetSentences() {
        assertEquals(typingTest.getSentences(), "");
    }

    @Test
    void testGenerateTest() {
        typingTest.generateTestSentence();
        typingTest2.generateTestSentence();
        String s1 = typingTest.getSentences();
        String s2 = typingTest2.getSentences();
        assertFalse(s1.equals(s2));
        assertFalse(s1.endsWith(" "));
        TypingTest typingTest3 = new TypingTest(new Difficulty("word Count 2", 2, 15));
        typingTest3.generateTestSentence();
        assertTrue(typingTest3.getSentences().contains(" "));
        assertFalse(typingTest3.getSentences().endsWith(" "));
    }

}
