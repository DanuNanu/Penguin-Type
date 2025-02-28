package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ListOfDifficultiesTest {
    private ListOfDifficulties listOfDifficulties;

    @BeforeEach
    void runBefore() {
        listOfDifficulties = new ListOfDifficulties();
    }

    @Test
    void testConstructor() {
        ListOfDifficulties listOfDifficulties2 = new ListOfDifficulties();
        assertTrue(listOfDifficulties2.isEmpty());
        assertTrue(listOfDifficulties2.getListOfDifficulties().isEmpty());
    }

    @Test
    void testAddDificulties() {
        assertEquals(listOfDifficulties.getSize(), 0);
        Difficulty difficulty1 = new Difficulty("Easy", 100, 50);
        Difficulty difficulty2 = new Difficulty("Hard", 100, 100);
        listOfDifficulties.addDifficulties(difficulty1);
        assertEquals(listOfDifficulties.getSize(), 1);
        listOfDifficulties.addDifficulties(difficulty2);
        assertEquals(listOfDifficulties.getSize(), 2);
        assertEquals(listOfDifficulties.getDifficultyAtIndex(0), difficulty1);
        assertEquals(listOfDifficulties.getDifficultyAtIndex(1), difficulty2);
    }

    @Test 
    void testFilterListOfDifficulties() {
        Difficulty d1 = new Difficulty("Easy", 100, 50);
        Difficulty d2 = new Difficulty("Hard", 500, 30);
        listOfDifficulties.addDifficulties(d1);
        listOfDifficulties.addDifficulties(d2);
        String sEmpty = "";
        assertTrue(sEmpty.isBlank() || sEmpty.isEmpty());
        ArrayList<String> filter1 = listOfDifficulties.filterListOfDifficulties(sEmpty);
        assertEquals(filter1.size(), 2);
        assertEquals(filter1.get(0), d1.getDifficulty());
        assertEquals(filter1.get(1), d2.getDifficulty());
        ArrayList<String> filter2 = listOfDifficulties.filterListOfDifficulties("E");
        assertEquals(filter2.size(), 1);
        assertEquals(filter2.get(0), d1.getDifficulty());
        ArrayList<String> filter3 = listOfDifficulties.filterListOfDifficulties("A");
        assertTrue(filter3.size() == 0);
        filter3.clear();
        filter3 = listOfDifficulties.filterListOfDifficulties("a");
        assertTrue(filter3.size() == 2);
        assertEquals(filter3.get(0), d1.getDifficulty());
        assertEquals(filter3.get(1), d2.getDifficulty());
        ArrayList<String> filter4 = listOfDifficulties.filterListOfDifficulties("l");
        assertTrue(filter4.isEmpty());
        String sblank = " ";
        assertTrue(sblank.isBlank() || sblank.isEmpty());
        ArrayList<String> filter5 = listOfDifficulties.filterListOfDifficulties(sblank);
        assertTrue(filter5.size() == 2);
        assertEquals(filter5.get(0), d1.getDifficulty());
        assertEquals(filter5.get(1), d2.getDifficulty());

    }

}
