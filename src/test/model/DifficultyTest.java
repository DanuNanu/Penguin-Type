package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DifficultyTest {
    private Difficulty difficulty1;
    private Difficulty difficulty2;
    ArrayList<String> words;

    @BeforeEach
    void runBefore() {
        difficulty1 = new Difficulty("Normal", 50, 50);
        words = new ArrayList<>();
        words.add("apple");
        words.add("ball");
        words.add("shrek");
        words.add("is");
        words.add("love");
        difficulty2 = new Difficulty("Easy", words, 50, 60);
    }

    @Test
    void testNormalConstructor() {
        assertEquals(difficulty1.getDifficulty(), "Normal");
        assertEquals((difficulty1.getWords().size()), 502);
        assertEquals(difficulty1.getWordCount(), 50);
        assertEquals(difficulty1.getTime(), 50);
        System.out.print(difficulty1.getWords());
    }

    @Test
    void testCustomWordConstructor() {
        assertEquals(difficulty2.getWords().size(), words.size());
        assertEquals(difficulty2.getWordCount(), 50);
        assertEquals(difficulty2.getTime(), 60);
    }

}
