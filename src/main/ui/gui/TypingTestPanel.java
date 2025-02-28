package ui.gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import model.*;

// Class representing the typing test panel

public class TypingTestPanel extends JPanel {
    protected JLabel testSentenceLabel;
    protected JTextField typingField;
    protected int currentWord = 0;
    protected int currentCharacter = 0;
    protected long endTime;
    protected long startTime;
    protected ListOfDifficulties lotd;
    protected ListOfTypingTests lott;
    protected TypingTest test;
    protected Difficulty difficulty;
    protected String[] testSentence;
    protected Boolean testStarted = false;
    protected JLabel timer;
    protected Timer testTimer;
    protected int timeLimit;
    protected int totalWordsTyped = 0;
    protected int correctWordsTyped = 0;
    protected boolean incorrectCharacter = false;
    protected TypingTestGUI parentFrame;
    
    // EFFECTS: constructs typing test panel
    public TypingTestPanel(ListOfTypingTests lott, ListOfDifficulties lotd, TypingTestGUI parentFrame) {
        this.lotd = lotd;
        this.lott = lott;
        this.parentFrame = parentFrame;
        this.difficulty = this.lotd.getDifficultyAtIndex(0);
        initPanel();
        add(timer, BorderLayout.NORTH);
        add(testSentenceLabel, BorderLayout.CENTER);
        add(typingField, BorderLayout.SOUTH);
        SwingUtilities.invokeLater(() -> typingField.requestFocusInWindow());
    }

    /*
     * Modfies: this
     * Effects: initialises and sets up the main panel
     * timer, label showing the words to be typed
     * the label for the timer, and the textfield on which
     * the user types
     */
    public void initPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        this.timer = new JLabel("");
        timer.setFont(new Font("Arial", Font.BOLD, 24));
        timer.setForeground(new Color(50, 50, 200));
        timer.setBackground(Color.black);
        timer.setHorizontalAlignment(SwingConstants.CENTER);
        testSentenceLabel = new JLabel("Press any key to begin the test");
        testSentenceLabel.setForeground(new Color(50, 50, 200));
        testSentenceLabel.setBackground(Color.BLACK);
        testSentenceLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        iniTextFiled();
    }

    /*
     * Modifies: this
     * Effects: intialises the
     * text field on which the user types on
     */

    public void iniTextFiled() {
        this.typingField = new JTextField();
        typingField.setFocusable(true);
        typingField.setPreferredSize(new Dimension(0, 0));
        typingField.addKeyListener(new TypingAnyKey());
    }

    /*
     * Modifies: this
     * Effects: creates a new typing test
     * With the default difficulty
     * generates a new test sentence
     * starts the timer. sets the field for the timer
     * and the text field
     */
    protected void startTest() {
        this.test = new TypingTest(difficulty);
        typingField.setEditable(true);
        typingField.setFocusable(true);
        test.generateTestSentence();
        testSentence = test.getSentences().split(" ");
        test.getStatistics().startTimer();
        startTime = test.getStatistics().getStartingTime();
        endTime = startTime + (test.getTestDifficulty().getTime() * 1000);
        timeLimit = difficulty.getTime();
        timer.setText(String.valueOf(timeLimit));
        testStarted = true;
        testSentenceLabel.setText(formatTestSentence());
        startTimer();
    }

    /*
     * Modifies: this
     * Effects: dynamically changes the text label
     * showing the test sentence which the user
     * has to type.
     * sets up all the untyped characters with the colour blie
     * checks if the character typed is correct
     * if it is correct moves the cursor one character
     * ahead and changes the character to the colour green
     * if it is not correct, changes the correct character
     * to the colour red
     * Also puts an underscore over the current
     * character the user is on in the typing test
     */
    protected String formatTestSentence() {
        StringBuilder formattedSentence = new StringBuilder("<html>");
        for (int i = 0; i < testSentence.length; i++) {
            String word = testSentence[i];
            if (i > 0) {
                formattedSentence.append(" ");
            }
            for (int j = 0; j < word.length(); j++) {
                if (i < currentWord || (i == currentWord && j < currentCharacter)) {
                    formattedSentence.append("<span style='color: green;'>").append(word.charAt(j)).append("</span>");
                } else if (i == currentWord && j == currentCharacter) {
                    String color = incorrectCharacter ? "red" : "blue";
                    formattedSentence.append("<span style='color: ").append(color)
                            .append("; text-decoration: underline;'>")
                            .append(word.charAt(j)).append("</span>");
                } else {
                    formattedSentence.append(word.charAt(j));
                }
            }
        }
        formattedSentence.append("</html>");
        return formattedSentence.toString();
    }

    /*
     * Modifies: this
     * Effects: Starts the timer, making it count down
     * from the given default time limit to 0
     * once the time limit is over
     * it ends the typing test
     * and cancels the timmer
     * Dynamically updates the label
     * for the timer as long as the
     * timeLimit>0
     */
    public void startTimer() {
        testTimer = new Timer();
        testTimer.scheduleAtFixedRate(new TestTimer(), 1000, 1000);
    }

    protected class TestTimer extends TimerTask {
        @Override
        public void run() {
            if (timeLimit > 0) {
                timeLimit--;
                SwingUtilities.invokeLater(() -> timer.setText("Time: " + timeLimit + "s"));
            } else {
                testTimer.cancel();
                SwingUtilities.invokeLater(() -> endTest());
            }
        }
    }

    /*
     * Modifies: this, TypingTestGui
     * EFFECTS: ends the test
     * by making the textbox non editible
     * updating the statistics of the test
     * and adding the test to the list of tests
     * followed by showing a message dialog
     * telling the user that the time is up
     * It also removes the current panel
     * from the parent Jframe and replaces it
     * with a new instance of the Statistics panelÍ
     */
    protected void endTest() {
        typingField.setEditable(false);
        updateStatistics();
        this.lott.addTests(test);
        JOptionPane.showMessageDialog(this, "The test is over, now showing results");
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new StatisticsPanel(test, parentFrame), BorderLayout.CENTER);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    /*
     * Modifies: this
     * Updates the statistics field
     * for the given test
     */
    protected void updateStatistics() {
        test.getStatistics().updateStatistics(totalWordsTyped, correctWordsTyped);
        test.getStatistics().calculateAccuracy(correctWordsTyped, totalWordsTyped);
        test.getStatistics().calculateTimeTaken(startTime, endTime);
        long timeTaken = test.getStatistics().getTimetaken();
        test.getStatistics().calculateWPM(correctWordsTyped, timeTaken);
    }

    /*
     * Modifies: this
     * Effects: Uses lambda expressions
     * to request for the typingField component
     * to gain input focus when called on
     */
    public void requestTextFieldFocus() {
        SwingUtilities.invokeLater(() -> typingField.requestFocusInWindow());
    }

    /*
     * Represents a class that implements
     * the KeyAdapter interface
     */
    protected class TypingAnyKey extends KeyAdapter {

        /*
         * Modifes: this
         * Effects:
         * Checks if the test has started, if it hasnt
         * then it starts the test and then returns to the method
         * that calls keyPressed
         * If the test has already started,
         * then it checks if the character typed by the user
         * is the space bar, if it is does nothing
         * and returns back the the method that called this method
         * checks if the user entered a backspace,
         * if that is the case, it calls the handleBackspace()
         * helper method and then returns back to the method that
         * called this method
         * if none of the other if conditions are satisfied,
         * it calls the handleCondition method, followed by
         * setting the text of the label showing the
         * characters the user has to type out
         * by calling formatTestSentence() to update
         * the label according to the user's input
         * ignores input if the caps lock is pressed
         */
        @Override
        public void keyPressed(KeyEvent e) {
            if (!testStarted) {
                startTest();
                return;
            }
            int typedUserKey = e.getKeyCode();
    
            if (typedUserKey == KeyEvent.VK_SPACE) {
                return;
            }
            if (typedUserKey == KeyEvent.VK_CAPS_LOCK) {
                return;
            }
            if (typedUserKey == KeyEvent.VK_BACK_SPACE) {
                handleBackspace();
                return;
            }
            char typedUserChar = e.getKeyChar();
            String currentTestWord = testSentence[currentWord];
            handleCondition(currentTestWord, typedUserChar);
            testSentenceLabel.setText(formatTestSentence());
        }

        /*
         * Modifies: this
         * Effects: checks if the user
         * has inputed an incorrect character,
         * if that is the case then it changes
         * the incorrectCharacter field to false
         * and updates the testSentenceLabel by
         * calling the formatTestSentence() function
         * to change the red colour of the character
         * that the user initially inputed incorrectly
         * to blue
         * If the user did not input an incorrect character
         * the method just updates the testSentenceLabel
         */
        private void handleBackspace() {
            if (incorrectCharacter) {
                incorrectCharacter = false;
                testSentenceLabel.setText(formatTestSentence());

            } else {
                testSentenceLabel.setText(formatTestSentence());
            }
        }

        /*
         * Modifies: this
         * Effects: first checks if the user has inputed
         * an incorrect character, if the current character is in
         * the bounds of the length of the current word
         * and if the character typed by the user is
         * the same as character of the current word
         * in the typing test, if this is the case
         * it increments the character index by one
         * Then it checks if the current character has the
         * same index as the length of the current test word
         * if that is the case, this implies that the word
         * has been correctly typed out, it then increments
         * the currentWord index by one, the correctWordsTyped by one
         * and totalWordsTyped by one. It then resets the character index back
         * to 0. It then checks if the current word index is the same as the
         * length of the current sentence, if that is the case. This indicates that
         * all the words have been typed correctly and it ends the test.
         * If the first condition hasnt been met, this implies
         * that the character typed was incorrect so it changes the
         * incorrectCharacter field to true
         */
        private void handleCondition(String currentTestWord, char typedUserChar) {
            if (!incorrectCharacter && currentCharacter < currentTestWord.length()
                    && typedUserChar == currentTestWord.charAt(currentCharacter)) {
                currentCharacter++;
                if (currentCharacter == currentTestWord.length()) {
                    correctWordsTyped++;
                    currentWord++;
                    totalWordsTyped++;
                    currentCharacter = 0;
                    if (currentWord >= testSentence.length) {
                        testTimer.cancel();
                        endTest();
                    }
                }
            } else {
                incorrectCharacter = true;
            }

        }
    }
}
