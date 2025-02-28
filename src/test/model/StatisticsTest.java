package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {
    private Statistics statistics1;

    @BeforeEach
    void runBefore() {
        statistics1 = new Statistics();
    }

    @Test
    void testConstructor() {
        assertEquals(statistics1.getWPM(), 0.0);
        assertEquals(statistics1.getCorrectWords(), 0);
        assertEquals(statistics1.getTimetaken(), 0);
        assertEquals(statistics1.getTotalWordsTyped(), 0);
        assertEquals(statistics1.getTimetaken(), 0);
        assertEquals(statistics1.getStartingTime(), 0);
    }

    @Test
    void testCalculateWPM() {
        statistics1.calculateWPM(0, 15);
        assertEquals(statistics1.getWPM(), 0.0);
        Statistics statistics2 = new Statistics();
        statistics2.calculateWPM(15, 15);
        assertEquals(statistics2.getWPM(), 60.0);
    }

    @Test
    void testCalculateAccuracy() {
        statistics1.calculateAccuracy(0, 15);
        assertEquals(statistics1.getAccuracy(), 0.0);
        Statistics statistics2 = new Statistics();
        statistics2.calculateAccuracy(15, 15);
        assertEquals(statistics2.getAccuracy(), 1.0);
    }

    @Test
    void testUpdateStatistics() {
        assertEquals(statistics1.getTotalWordsTyped(), 0);
        assertEquals(statistics1.getCorrectWords(), 0);
        statistics1.updateStatistics(15, 15);
        assertEquals(statistics1.getTotalWordsTyped(), 15);
        assertEquals(statistics1.getCorrectWords(), 15);

    }

    @Test
    void testSetTimetaken() {
        statistics1.startTimer();
        long startTime = statistics1.getStartingTime();
        long endTime = startTime + System.currentTimeMillis();
        statistics1.calculateTimeTaken(startTime, endTime);
        long timeTaken = statistics1.getTimetaken();
        long timetakenChecker = (endTime - startTime) / 1000;
        assertEquals(timeTaken, timetakenChecker);
    }
}
