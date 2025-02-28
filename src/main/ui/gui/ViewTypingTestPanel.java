package ui.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.*;
import model.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//Represents the panel showcasing the TypingTests
public class ViewTypingTestPanel extends JPanel {
    private ListOfDifficulties lod;
    private ListOfTypingTests lott;
    private JList<String> testList;
    private JTextArea details;
    private JTextField searchField;
    private JButton deleteTest;
    private JButton backButton;
    private DefaultListModel listModel;
    private ArrayList<TypingTest> typingTests;
    private JPanel detailsPanel;
    private Statistics stats;
    private Difficulty difficulty;
    private JPanel searchPanel;
    private JLabel searchJLabel;
    private TypingTestGUI parentFrame;
    private JPanel topPanel;

    /*
     * Effect: constructs the ViewTypingTestPanel
     */
    public ViewTypingTestPanel(ListOfTypingTests lott, ListOfDifficulties lod, TypingTestGUI parentFrame) {
        this.lod = lod;
        this.lott = lott;
        this.parentFrame = parentFrame;
        this.typingTests = lott.getListOfTypingTests();
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        initPanel();
        initDetailPanel();
        initSearchBar();
        initBackButton();
        initRemoveButton();
        add(topPanel,BorderLayout.NORTH);
    }

    /*
     * Modifies: this
     * Effects: adds the testId of
     * all the tests to the listModel and
     * calls the initJList helper method
     */
    private void initPanel() {
        listModel = new DefaultListModel<>();
        for (TypingTest test : typingTests) {
            listModel.addElement("" + test.getIdNo());
        }
        initJList();
    }

    /*
     * Modifies; this
     * Effects: initialises the 
     * testList Jlist with desired property
     * creates a new JScrollPane 
     * and adds Jlist to it
     * adds Jlist to the JPanel
     */
    private void initJList() {
        testList = new JList<>(listModel);
        testList.setModel(listModel);
        testList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        testList.setBackground(Color.black);
        testList.setForeground(new Color(50, 50, 200));
        testList.setFont(new Font("Arial", Font.PLAIN, 18));
        testList.addListSelectionListener(new ListListenerAction());
        JScrollPane listScrollPane = new JScrollPane(testList);
        listScrollPane.setPreferredSize(new Dimension(200, 400));
        add(listScrollPane, BorderLayout.WEST);

    }



    /*
     * Modifies: this
     * Effects: intialises the panel
     * showcasing the details of each test
     */
    private void initDetailPanel() {
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBackground(Color.BLACK);
        details = new JTextArea();
        details.setEditable(false);
        details.setBackground(Color.BLACK);
        details.setForeground(new Color(50, 50, 200));
        details.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane detailScrollPane = new JScrollPane(details);
        detailScrollPane.setPreferredSize(new Dimension(400, 400));
        detailsPanel.add(detailScrollPane, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.CENTER);
    }

    /*
     * Modifies: this
     * Effects: sets up the text that goes
     * into the detail JTextAres
     */
    public void showTypingTestDetails(String testName) {
        for (TypingTest test : typingTests) {
            String testId = "" + test.getIdNo();
            if (testId.equals(testName)) {
                stats = test.getStatistics();
                difficulty = test.getTestDifficulty();
                String detail = "\n" + "\n";
                detail = "Test ID" + test.getIdNo() + "\n" + "\n";
                detail += "WPM: " + stats.getWPM() + "\n" + "\n";
                detail += "WPM: " + stats.getWPM() + "\n" + "\n";
                detail += "time taken : " + stats.getTimetaken() + " s" + "\n" + "\n";
                detail += "accuracy : " + stats.getAccuracy() + "\n" + "\n";
                detail += "correct words typed : " + stats.getCorrectWords() + " words" + "\n" + "\n";
                detail += "Total words typed : " + stats.getTotalWordsTyped() + " words" + "\n" + "\n";
                detail += "Word count : " + test.getWordCount() + " words" + "\n" + "\n";
                detail += "Difficulty name : " + test.getTestDifficultyName() + " words" + "\n" + "\n";
                detail += "test sentence : " + test.getSentences() + "\n" + "\n";
                detail += "List of words used in typing test : " + getSampleWord(difficulty).toString() + "\n" + "n";
                details.setText(detail);
                break;
            }
        }

    }

    /*
     * Modifies: this
     * Effects:
     * Converts the array of words into a string builder
     * puts a " " between each word in array
     * places every 50 words in a new line
     * to make sure that each line is 
     * no longer than 50 words
     */
    public StringBuilder getSampleWord(Difficulty d1) {
        ArrayList<String> words = difficulty.getWords();
        int size = words.size();
        StringBuilder sentenceBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sentenceBuilder.append(words.get(i));

            if (i > 0 && i % 50 == 0) {
                sentenceBuilder.append("\n");
            }

            if (i < (size - 1)) {
                sentenceBuilder.append(" ");
            }

        }
        return sentenceBuilder;
    }

    /*
     * Modifies: this
     * Effects: intialises the searchPanel, searchField
     * and the searchJLabel with the desired value
     * to represent a searchBar where the user
     * can search for tests with the specific wpm
     */
    private void initSearchBar() {
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.BLACK);
        searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(50,50,200),1));
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(new Color(50, 50, 200));
        searchField.getDocument().addDocumentListener(new SearchLabelListener());
        searchJLabel = new JLabel("Search by WPM");
        searchJLabel.setBackground(Color.BLACK);
        searchJLabel.setForeground(new Color(50, 50, 200));
        searchJLabel.setFont(new Font("Arial", Font.BOLD, 18));
        searchPanel.add(searchJLabel);
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.WEST);
    }

    /*
     * Class that implements the DocumentListener interface
     */
    private class SearchLabelListener implements DocumentListener {
        @Override
        /*
         * Modifies: this
         * Effects: calls filterTypingTests
         * Everytime text is added to the
         * searchField by the user
         */
        public void insertUpdate(DocumentEvent e) {
            filterTypingTests();
        }

        @Override
        /*
         * Modifies: this
         * Effects: calls filterTypingTest
         * Everytime text is removed from the
         * searchField by the user
         */
        public void removeUpdate(DocumentEvent e) {
            filterTypingTests();
        }

        @Override
        /*
         * Modifies: this
         * Effects: calls filterTypingTest
         * Everytime properties fo the searchLabel
         * are changed
         */
        public void changedUpdate(DocumentEvent e) {
            filterTypingTests();
        }
    }

    /* Requires: UserQuery to include numbers and UserQuery>=0
     * Modifies: this
     * Effects: checks if the userQuery is 
     * empty, if that is the case
     * shows every typing test the user has
     * completed
     * else, checks the value of the wpm
     * entered by the user against
     * all the typingTests in the list
     * displays all the tests that the matches
     * the given wpm
     */
    private void filterTypingTests() {
        String userQuery = searchField.getText();
        listModel.clear();
        ArrayList<String> filteredList = lott.filterTypingTest(userQuery);
        for (String s1 : filteredList) {
            listModel.addElement(s1);
        }
        
    }

    /*
     * Modifies: this
     * Effects: initialises the backButton with the desired
     * properties
     */
    public void initBackButton() {
        JPanel bottonLeftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottonLeftJPanel.setBackground(Color.BLACK);
        this.backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 50));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 200));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 8));
        backButton.setOpaque(true);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new BackAction());
        bottonLeftJPanel.add(backButton);
        add(bottonLeftJPanel, BorderLayout.SOUTH);
    }

    /*
     * Modifies: this
     * Effects: intialises the deleteTest 
     * button with the desired proeprties
     */
    private void initRemoveButton() {
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setBackground(Color.BLACK);
        deleteTest = new JButton("Delete");
        deleteTest.setPreferredSize(new Dimension(80, 50));
        deleteTest.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteTest.setBackground(new Color(50, 50, 200));
        deleteTest.setForeground(Color.WHITE);
        deleteTest.setFont(new Font("Arial", Font.BOLD, 8));
        deleteTest.setOpaque(true);
        deleteTest.setFocusPainted(false);
        deleteTest.setContentAreaFilled(true);
        deleteTest.setBorderPainted(false);
        deleteTest.setFocusPainted(false);
        deleteTest.addActionListener(new DeleteActionListener());
        topLeftPanel.add(deleteTest);
        topPanel.add(topLeftPanel, BorderLayout.EAST);
    }

    /*
     * Modifies : this, lott
     * Effects: refreshes the components
     * of the panel to showcase any updates
     */
    public void refreshPanel() {
        listModel.clear();
        typingTests = lott.getListOfTypingTests();
        for (TypingTest test : typingTests) {
            listModel.addElement("" + test.getIdNo());
        }

        details.setText("");
        revalidate();
        repaint();
    }

    /* 
     * Modifies: this, lott
     * Effects: checks if a typing
     * test is selected, and deletes it
     * from the lott and updates the panel
     */
    private void deleteSelectedTest() {
        int selectedTestIndex = testList.getSelectedIndex();
        if (selectedTestIndex != -1) {
            listModel.remove(selectedTestIndex);
            lott.removeTypingTestFromTestAtIndex(selectedTestIndex);
            details.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "please Select test to delete");
        }
    }

    /*
     * Class implementing the ActionListener interface
     */
    private class DeleteActionListener implements ActionListener {
        @Override
        /*
         * Modifies: this, lott
         * Effects: calls deleteSelectedTest
         * everytime the deleteTest button is pressed
         */
        public void actionPerformed(ActionEvent e) {
            deleteSelectedTest();

        }
    }

    /*
     * Class overiding the actionPerformed method
     * in the ActionListener Class
     */
    private class BackAction implements ActionListener {

        /*
         * Modifies: parentFrame
         * Effects: removes everything from
         * the content pane of the parentFrame
         * and shows the "Buttons" panel
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = parentFrame.returnPanel();
            parentFrame.getContentPane().removeAll();
            parentFrame.returnLayout().show(panel, "Buttons");
            parentFrame.add(panel, BorderLayout.CENTER);
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    /*
     * Class implementing the ListSelectionListener interface
     */
    private class ListListenerAction implements ListSelectionListener {
        @Override
        /*
         * Modifies: this
         * Effects: updates the details
         * of the typingTest show when 
         * the user stops adjusting the
         * testList
         */
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String testId = testList.getSelectedValue();
                showTypingTestDetails(testId);
            }
        }
    }
}
