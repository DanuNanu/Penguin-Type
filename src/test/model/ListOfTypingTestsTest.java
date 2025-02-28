package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.TypingTest;

import java.util.ArrayList;

public class ListOfTypingTestsTest {
    private ListOfTypingTests listOfTypingTests;

    @BeforeEach
    void runBefore() {
        listOfTypingTests = new ListOfTypingTests();
    }

    @Test
    void testConstructor() {
        ListOfTypingTests listOfTypingTests2 = new ListOfTypingTests();
        assertTrue(listOfTypingTests2.isEmpty());
        // checking if getListOfTypingTest() indeed works
        // by checking if the arraylist part of listOfTypingTest is empty when listOfTypingTest is intialised
        // since we know that when the listOfTypingTest is intialised the array field part of it
        // is empty
        assertTrue(listOfTypingTests2.getListOfTypingTests().isEmpty());

    }

    @Test
    void testAddTests() {
        assertEquals(listOfTypingTests.getSize(), 0);
        TypingTest test1 = new TypingTest(new Difficulty("Easy", 50, 100));
        TypingTest test2 = new TypingTest(new Difficulty("Hard", 50, 60));
        listOfTypingTests.addTests(test1);
        assertEquals(listOfTypingTests.getSize(), 1);
        listOfTypingTests.addTests(test2);
        assertEquals(listOfTypingTests.getSize(), 2);
        assertEquals(listOfTypingTests.getTypingTestAtIndex(0), test1);
        assertEquals(listOfTypingTests.getTypingTestAtIndex(1), test2);
    }


    @Test
    void testRemoveTypingTestFromTestAtIndex() {
        TypingTest test1 = new TypingTest(new Difficulty("Easy", 50, 100));
        TypingTest test2 = new TypingTest(new Difficulty("Hard", 50, 60));
        listOfTypingTests.addTests(test1);
        listOfTypingTests.addTests(test2);
        assertTrue(listOfTypingTests.getSize() == 2);
        listOfTypingTests.removeTypingTestFromTestAtIndex(0);
        assertTrue(listOfTypingTests.getSize() == 1);
        assertEquals(listOfTypingTests.getTypingTestAtIndex(0), test2);
        ListOfTypingTests lott = new ListOfTypingTests();
        lott.addTests(test1);
        lott.addTests(test2);
        assertTrue(lott.getSize() == 2);
        lott.removeTypingTestFromTestAtIndex(1);
        assertEquals(lott.getSize(), 1);
        assertEquals(lott.getTypingTestAtIndex(0), test1);
    }

    @Test
    void testFilterTypingTest() {

        Difficulty d1 = new Difficulty("Easy", 50, 100);
        Difficulty d2 = new Difficulty("Hard", 50, 60);
        TypingTest t1 = new TypingTest(d1);
        TypingTest t2 = new TypingTest(d2);
        TypingTest t3 = new TypingTest(d2);
        t1.getStatistics().setWpm(100);
        t2.getStatistics().setWpm(55.0);
        t3.getStatistics().setWpm(10);
        listOfTypingTests.addTests(t1);
        listOfTypingTests.addTests(t2);
        listOfTypingTests.addTests(t3);
        String sEmpty = "";
        assertTrue(sEmpty.isBlank() || sEmpty.isEmpty());
        ArrayList<String> filter1 =  listOfTypingTests.filterTypingTest(sEmpty);
        assertEquals(filter1.size(), 3);
        assertEquals(filter1.get(0), "" + t1.getIdNo());
        assertEquals(filter1.get(1), "" + t2.getIdNo());
        assertEquals(filter1.get(2), "" + t3.getIdNo());
        ArrayList<String> filter2 =  listOfTypingTests.filterTypingTest("55");
        assertEquals(filter2.size(), 1);
        assertEquals(filter2.get(0), "" + t2.getIdNo());
        ArrayList<String> filter3 =  listOfTypingTests.filterTypingTest("-1");
        assertTrue(filter3.size() == 0);
        filter3.clear();
        filter3 =  listOfTypingTests.filterTypingTest("100");
        assertTrue(filter3.size() == 1);
        assertEquals(filter3.get(0), "" + t1.getIdNo());
        String sblank = " ";
        assertTrue(sblank.isBlank() || sblank.isEmpty());
        ArrayList<String> filter5 =  listOfTypingTests.filterTypingTest(sblank);
        assertTrue(filter5.size() == 3);
        assertEquals(filter5.get(0), "" + t1.getIdNo());
        assertEquals(filter5.get(1), "" + t2.getIdNo());
        assertEquals(filter5.get(2), "" + t3.getIdNo());
        ArrayList<String> filter6 =  listOfTypingTests.filterTypingTest("1");
        assertTrue(filter6.size() == 2);
        assertEquals(filter6.get(0), "" + t1.getIdNo());
        assertEquals(filter6.get(1), "" + t3.getIdNo());
    }

}
