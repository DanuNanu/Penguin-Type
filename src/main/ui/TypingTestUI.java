package ui;

import model.Difficulty;
import model.ListOfDifficulties;
import model.ListOfTypingTests;
import model.TypingTest;
import java.util.ArrayList;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

// an application that allows people to practice typing on their keyboard
public class TypingTestUI {
    private ListOfDifficulties listOfDifficulties;
    private TypingTest typingTest;
    private ListOfTypingTests listOfTypingTests;
    private Boolean isProgramRunning;
    private Scanner scanner;
    private int currentTypingTestIndex = 0;
    private int currentDifficultyIndex = 0;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JASON_STORE = "./data/typingTest.json";

    // Effects: creates an instance of the typing test application
    public TypingTestUI() {
        TypingTest.resetID();
        init();

        System.out.println("Welcome to this Typing Test Application!");

        while (this.isProgramRunning) {
            handleMenu();

        }

    }

    /*
     * Modifies: this
     * effects: initialises many of the required objects
     * and resets the TypingTests id statically
     */
    public void init() {
        this.listOfDifficulties = new ListOfDifficulties();
        this.listOfTypingTests = new ListOfTypingTests();
        this.isProgramRunning = true;
        jsonWriter = new JsonWriter(JASON_STORE);
        jsonReader = new JsonReader(JASON_STORE);
        this.scanner = new Scanner(System.in);

    }

    // effects: Prints out the display menu for the user to read
    public void displayMenu() {
        System.out.println("Please select an option: \n");
        System.out.println("1: Start a normal typing test");
        System.out.println("2: View previous tests");
        System.out.println("3: Create a new difficulty");
        System.out.println("4: Create a new Test");
        System.out.println("s: to save the typing test application to file");
        System.out.println("l: to load typing test application from file");
        System.out.println("5: Quit the application");
    }

    // effects: displays and processes inputs for the main menu of this application
    public void handleMenu() {
        displayMenu();
        String userInput = scanner.nextLine();
        processUserInput(userInput);
    }

    // modifies: this
    // effects: processes the user's input in the main menu
    public void processUserInput(String userInput) {
        switch (userInput) {
            case "1":
                startTypingTest(new Difficulty("normal", 50, 15));
                break;
            case "2":
                displayPreviousTests();
                break;
        }
        processUserInputHelper(userInput);

    }

    // MODIFIES: this
    // effect: helper method used to process inputs
    public void processUserInputHelper(String userInput) {
        switch (userInput) {
            case "3":
                createDifficulty();
                break;
            case "4":
                createNewTests();
                break;
            case "5":
                quitApplication();
                break;
            case "s":
                saveLoTDandLoTT();
                break;
            case "l":
                loadLoTDandLoTT();
                break;

            default:
                System.out.println("");
        }
    }

    // modifies: this
    // effects: starts the typing test for the user to practice with
    /*
     * Minitors the user input against the generated test sentnence
     * tracks the users statitistics for the given test
     * if the user's time limit is reached or all the words are typed
     * the test is stopped
     * saves the completed test in the the listOfTypingTests
     */
    public void startTypingTest(Difficulty difficulty) {
        helperMethodForStartTest(difficulty);
        long endTime = typingTest.getStatistics().getStartingTime() + (typingTest.getTestDifficulty().getTime() * 1000);
        String[] testSentenceArray = typingTest.getSentences().split(" ");
        int typingIndex = 0;
        int correctWord = 0;
        int totalWords = 0;
        Boolean continueTest = true;
        while (System.currentTimeMillis() <= endTime && continueTest && typingIndex < testSentenceArray.length) {
            handleWordInput(testSentenceArray[typingIndex]);
            if (testConditionForLoop(testSentenceArray[typingIndex])) {
                typingIndex++;
                correctWord++;
                totalWords++;
            } else {
                System.out.println("You have entered an incorrect word, try again");
                totalWords++;
            }
        }
        if (!continueTest || System.currentTimeMillis() >= endTime) {
            calculateAndPrintResults(typingTest.getStatistics().getStartingTime(),
                    endTime, correctWord, totalWords, typingTest.getWordCount());
        }
        listOfTypingTests.addTests(typingTest);
    }

    // modifies: this
    // effects: helper method for the startTest method that intialises required
    // objects
    private void helperMethodForStartTest(Difficulty difficulty) {
        System.out.println("To begin the test, press any key");
        scanner.nextLine();
        this.typingTest = new TypingTest(difficulty);
        typingTest.generateTestSentence();
        typingTest.getStatistics().startTimer();

    }

    // effects: helper condition for the startTest method
    // checks if the word entered by the user is the
    // same word in the test sentence
    private Boolean testConditionForLoop(String currentWord) {
        String userInput = scanner.next();
        return userInput.equals(currentWord);
    }

    // effects: helper method for the startTest methpd
    // handles the input entered by the user
    // while practicing a typing test
    private void handleWordInput(String currentWord) {
        System.out.println("type out the word " + currentWord);
    }

    // modifies: this
    // effects: calculates, sets, and prints the result of the statisitcs of the
    // individual typing test
    private void calculateAndPrintResults(long startingTime,
            long endTime, int correctWord, int totalWords, int wordCount) {
        typingTest.getStatistics().updateStatistics(totalWords, correctWord);
        typingTest.getStatistics().calculateAccuracy(correctWord, totalWords);
        typingTest.getStatistics().calculateTimeTaken(startingTime, endTime);
        long timeTaken = typingTest.getStatistics().getTimetaken();
        typingTest.getStatistics().calculateWPM(correctWord, timeTaken);
        System.out.println("\n");
        System.out.println("Your results are" + "\n");
        System.out.println("Your wpm was " + typingTest.getStatistics().getWPM() + "\n");
        System.out.println("Your total time taken was "
                + typingTest.getStatistics().getTimetaken() + " Seconds" + "\n");
        System.out.println("You typed " + totalWords + " words out of " + typingTest.getWordCount() + " words" + "\n");
        System.out.println("You typed " + totalWords
                + " words out of which " + correctWord + " words were correct" + "\n");
        System.out.println("Your accuracy was " + typingTest.getStatistics().getAccuracy() + "\n");
        System.out.println("press 'e' to move back to the menu");
        scanner.nextLine();
    }

    // effects: displays all the Typing tests one at a time
    public void displayPreviousTests() {
        displayAllTests(this.listOfTypingTests);
    }

    // modifies: this
    // effects: displays the list of typing tests and handles inputs related to
    // viewing the
    // individual tests
    public void displayAllTests(ListOfTypingTests listOfTypingTests) {
        if (listOfTypingTests.isEmpty()) {
            System.out.println("There are no previous tests that have been taken");
            return;
        }

        testMenu();
        String userInput = "";
        while (!userInput.equals("d")) {
            TypingTest currentTypingTest = listOfTypingTests.getTypingTestAtIndex(currentTypingTestIndex);
            displayCurrentTypingTest(currentTypingTest);
            userInput = this.scanner.nextLine();
            handleTestMenuCommands(userInput, listOfTypingTests);
        }
        this.currentTypingTestIndex = 0;
    }

    // effects: prints the menu for the test menu submenu
    public void testMenu() {
        System.out.println("Enter 'a' to see details related to this test");
        System.out.println("Enter 'b' to go to the next text");
        System.out.println("Enter 'c' to go to previous test");
        System.out.println("Enter 'd' exit ths menu");
    }

    // effects: prints the test detail for the user to see
    public void testDetails(TypingTest test) {
        System.out.println("\n");
        System.out.println("The difficulty of this test is " + test.getTestDifficultyName() + "\n");
        System.out.println("The sentence generated for this test was " + test.getSentences() + "\n");
        System.out.println("The time limit for this test was " + test.getTestDifficulty().getTime() + "\n");
        System.out.println("Your wpm for this test was " + test.getStatistics().getWPM() + "\n");
        System.out.println("Your accuracy for this test was " + test.getStatistics().getAccuracy() + "\n");
        System.out.println("Your timetaken for this test was " + test.getStatistics().getTimetaken() + "\n");
        System.out.println("Press 'd' to go the previous menu" + "\n");
    }

    // effects: displays the current typing test's index, allowing the user
    // : to identify which test they are looking at
    public void displayCurrentTypingTest(TypingTest typingTest) {
        System.out.println("test no is " + (this.currentTypingTestIndex + 1));
    }

    // modifies: this
    // effects: processes the user's input in view previous typing test menu
    public void handleTestMenuCommands(String userInput, ListOfTypingTests listOfTypingTests) {
        System.out.println("\n");
        TypingTest currentTypingTest = listOfTypingTests.getTypingTestAtIndex(currentTypingTestIndex);
        switch (userInput) {
            case "a":
                testDetails(currentTypingTest);
                break;
            case "b":
                getNextTypingTest(listOfTypingTests);
                break;
            case "c":
                getPreviousTypingTest();
                break;
            case "d":
                System.out.println("Returning to the menu");
                break;
            default:
                System.out.println("Invalid entry. Please try again");

        }
    }

    // modifies: this
    // effects: if there is another typing test to display,increments the typing
    // test index
    public void getNextTypingTest(ListOfTypingTests listOfTypingTest) {
        if (this.currentTypingTestIndex >= listOfTypingTest.getSize() - 1) {
            System.out.println("There are no more typing tests to look at");
        } else {
            this.currentTypingTestIndex++;
        }
    }

    // modifies: this
    // effects: if there is a previous typing test to display, decrements the
    // current typing tets index.
    public void getPreviousTypingTest() {
        if (this.currentTypingTestIndex <= 0) {
            System.out.println("There are no previous typing tests to display");
        } else {
            this.currentTypingTestIndex--;
        }
    }
    /*
     * Modifies: this
     * Effects: Prompts the user whether they want custom words for their typing
     * test
     * if this is true, it collects the custom words from the user and creates a
     * difficulty
     * using these words. If this is false, it uses the words in the words.text file
     * to
     * generate sentences for the typing tests.
     * Asks the user for time limit, word count, and unique difficulty name
     * Checks if the difficulty name in unique. if it is not unique
     * it keeps asking the user for a unique name till one is provided
     * Adds the difficulty to the list of difficulties
     */

    public void createDifficulty() {
        if (wantCustomWords()) {
            ArrayList<String> customWords = getCustomWords();
            System.out.println("the custom words that you have chosen are " + customWords + "\n");
            String name2 = getUniqueDifficultyName();
            int time2 = getTimeLimit();
            int wordCount2 = getWordCount();
            Difficulty customDifficulty = new Difficulty(name2, customWords, wordCount2, time2);
            this.listOfDifficulties.addDifficulties(customDifficulty);
            return;
        }

        String name = getUniqueDifficultyName();
        int time = getTimeLimit();
        int wordCount = getWordCount();
        Difficulty difficulty = new Difficulty(name, wordCount, time);
        listOfDifficulties.addDifficulties(difficulty);
    }

    // Effects: ask the user if the user wants custom words
    // if this is true it returns true, otherwise returns false.
    private Boolean wantCustomWords() {
        System.out.println("Do you want your typing test to have custom words");
        return scanner.nextLine().equalsIgnoreCase("yes");
    }

    // Effetcts: prompts the user for a unique difficult name adn checks this
    // against
    // the current list of difficulty names. The loop continues until the user
    // inputs a unique name. Returns the name when this condition is met.
    private String getUniqueDifficultyName() {
        System.out.println("What do you want as your unique difficulty name?");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (isUniqueDifficultyName(name)) {
                break;
            }
            System.out.println("This difficulty has already been defined, please choose another name");
        }
        return name;
    }

    // Effects: Loops through the listofDifficulties to check
    // if the name inputted by the user is unique
    // if it is not unique, it returns false otherwise returns true
    private Boolean isUniqueDifficultyName(String name) {
        for (Difficulty difficulty : this.listOfDifficulties.getListOfDifficulties()) {
            if (difficulty.getDifficulty().equals(name)) {
                return false;
            }
        }
        return true;

    }

    // Requires: Time limit > 0
    /*
     * Effects: ask the user to input a valid time limit for the typing test
     */
    private int getTimeLimit() {
        System.out.println("Please choose a time limit for your difficulty");
        return scanner.nextInt();
    }

    // Requires: word count >0
    /*
     * Effects: as the user for a valid wordcount
     */
    private int getWordCount() {
        System.out.println("Please choose a word count for your difficulty");
        return scanner.nextInt();
    }

    /*
     * Modifies: customWords
     * EFfects: allows the user to input custom words into an array
     * continues accepting words from the user until 0 is entered by the user
     * returns the customWords collected from the user
     */
    private ArrayList<String> getCustomWords() {
        ArrayList<String> customWords = new ArrayList<>();
        System.out.println("Enter your custom words (type '0' to stop):");

        while (true) {
            String word = scanner.nextLine();
            if (word.equals("0")) {
                break;
            }
            customWords.add(word);
        }
        return customWords;
    }

    /*
     * Requires: The listOfDifficulties to not be empty
     * Effects: calls upon helper method to display all available
     * difficulties one at a time
     */
    public void createNewTests() {
        displayAllDifficulties(this.listOfDifficulties);

    }

    /*
     * modifies: this
     * Effects: Displays all difficulties ne by one. Allows the user to navigate
     * through the list of difficulties and choose one to generate a new test
     * if no difficulties exist, exists the program and lets the user know
     */
    public void displayAllDifficulties(ListOfDifficulties listOfDifficulties) {
        if (listOfDifficulties.isEmpty()) {
            System.out.println("There are no difficulties to crete typing tests withh");
            return;
        }

        testMenuForCreatingTests();
        String userInput = "";
        while (!userInput.equals("e")) {
            Difficulty currentDifficulty = listOfDifficulties.getDifficultyAtIndex(currentDifficultyIndex);
            displayCurrentDifficulty(currentDifficulty);
            userInput = this.scanner.nextLine();
            handleTestMenuCommandsForDifficulties(userInput, listOfDifficulties);
        }
        this.currentDifficultyIndex = 0;

    }

    /*
     * EFfects: prints the options in the sub menu for creating tests for the user
     */
    public void testMenuForCreatingTests() {
        System.out.println("Enter 'a' to see details related to this difficulty");
        System.out.println("Press 'b' to select the current difficulty");
        System.out.println("Enter 'c' to go to the next difficulty");
        System.out.println("Enter 'd' to go to previous difficulty");
        System.out.println("Enter 'e' exit ths menu");
    }

    /*
     * Effects: displays the current difficulty's name and index
     * in the list listOfDifficulties
     */
    public void displayCurrentDifficulty(Difficulty difficulty) {
        System.out.println("the difficulty index is " + (this.currentDifficultyIndex + 1));
        System.out.println("the difficulty name is " + difficulty.getDifficulty());
    }

    /*
     * Requires: the current difficulty to not be null
     * Effects: prints out details regarding the difficulty
     * to make it easier to identify individual difficulties
     */
    public void difficultyDetails(Difficulty difficulty) {
        System.out.println("\n");
        System.out.println("The difficulty of this difficulty is " + difficulty.getDifficulty() + "\n");
        System.out.println("The words used for this test are " + difficulty.getWords() + "\n");
        System.out.println("The time limit for this test was " + difficulty.getTime() + "\n");
        System.out.println("The word count for this difficulty is " + difficulty.getWordCount() + "\n");
        System.out.println("Press 'e' to go the previous menu" + "\n");
    }

    /*
     * Modifies: this
     * Effects: processes the users inputs in this submenu of creating a new
     * difficulty
     */

    public void handleTestMenuCommandsForDifficulties(String userInput, ListOfDifficulties listOfDifficulties) {
        System.out.println("\n");
        Difficulty currentDifficulty = listOfDifficulties.getDifficultyAtIndex(currentDifficultyIndex);
        switch (userInput) {
            case "a":
                difficultyDetails(currentDifficulty);
                break;
            case "b":
                createNewTestUsingDifficulty(currentDifficulty);
                break;
            case "c":
                getNextDifficulty(listOfDifficulties);
                break;
            case "d":
                getPreviousDifficulty();
                break;
            case "e":
                System.out.println("Returning to the menu");
                break;
            default:
                System.out.println("Invalid entry. Please try again");

        }
    }

    /*
     * Effects: Starts the typing tests using the selected difficulties
     */
    public void createNewTestUsingDifficulty(Difficulty currentDifficulty) {
        startTypingTest(currentDifficulty);

    }

    /*
     * Modifies: this
     * Effects: Displays the next Difficulty in the listOfDifficulties list
     */
    public void getNextDifficulty(ListOfDifficulties listOfDifficulties) {
        if (this.currentDifficultyIndex >= listOfDifficulties.getSize() - 1) {
            System.out.println("There are no more Difficulty settings to look at");
        } else {
            this.currentDifficultyIndex++;
        }
    }

    /*
     * Modifies: this
     * Effects: shows the previous difficulty in the listOfDifficulty list
     * if there is one
     */
    public void getPreviousDifficulty() {
        if (this.currentDifficultyIndex <= 0) {
            System.out.println("There are no previous Difficulty settings to display");
        } else {
            this.currentDifficultyIndex--;
        }
    }

    // Modifies: this
    // effects: quits the application upon the users request
    public void quitApplication() {
        System.out.println("Thank you for using my typing test application");
        System.out.println("Have a great day");
        this.isProgramRunning = false;
    }

    // MODIFIES: this
    // EFFECTS : saves ListOfTypingTests and
    // : ListOfDifficulty to file
    private void saveLoTDandLoTT() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfTypingTests, listOfDifficulties);
            jsonWriter.close();
            System.out.println("Saved the typing tests and difficulties to " + JASON_STORE);
        } catch (Exception e) {
            System.out.println("Unable to write to file " + JASON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS : loads typing test application from file

    private void loadLoTDandLoTT() {
        try {
            listOfTypingTests = jsonReader.readTypingTests();
            listOfDifficulties = jsonReader.readDifficulties();
            System.out.println("loaded typing test application from " + JASON_STORE);
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JASON_STORE);
        }
    }

}
