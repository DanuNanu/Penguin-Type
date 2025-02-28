package ui.gui;

import java.awt.*;
import model.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//Represents the panel to view/interact with the Difficulties
public class DifficultiesPanel extends JPanel {

    private ListOfDifficulties lod;
    private ListOfTypingTests lott;
    private JList<String> difficultyList;
    private JTextArea details;
    private JTextField searchField;
    private JButton addDifficulty;
    private JButton backButton;
    private DefaultListModel listModel;
    private ArrayList<Difficulty> difficulties;
    private JPanel detailsPanel;
    private JPanel searchPanel;
    private JLabel searchJLabel;
    private TypingTestGUI parentFrame;
    private JPanel topPanel;
    protected HashSet<String> existingNames;

    /*
     * Effect: constructs the DifficultiesPanel
     */
    public DifficultiesPanel(ListOfTypingTests lott, ListOfDifficulties lod, TypingTestGUI parentFrame) {
        this.lod = lod;
        existingNames = new HashSet<String>();
        this.lott = lott;
        this.parentFrame = parentFrame;
        this.difficulties = lod.getListOfDifficulties();
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        initPanel();
        initDetailPanel();
        initSearchBar();
        initBackButton();
        initAddButton();
        add(topPanel, BorderLayout.NORTH);
        refreshPanel();
    }

    /*
     * Modifies: this
     * Effects: add the Difficulty name of
     * all the Difficulties to the listModel and
     * calls the initJList helper method
     */
    private void initPanel() {
        listModel = new DefaultListModel<>();
        for (Difficulty d : difficulties) {
            existingNames.add(d.getDifficulty());
            listModel.addElement(d.getDifficulty());
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
        difficultyList = new JList<>(listModel);
        difficultyList.setModel(listModel);
        difficultyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        difficultyList.setBackground(Color.black);
        difficultyList.setForeground(new Color(50, 50, 200));
        difficultyList.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyList.addListSelectionListener(new ListListenerAction());
        JScrollPane listScrollPane = new JScrollPane(difficultyList);
        listScrollPane.setPreferredSize(new Dimension(200, 400));
        add(listScrollPane, BorderLayout.WEST);

    }

    /*
     * Modifies: this
     * Effects: intialises the panel
     * showcasing the details of each Difficulty
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
     * into the detail JTextAraes
     */
    public void showTypingTestDetails(String difficultyName) {
        details.setText("");
        for (Difficulty difficulty1 : difficulties) {
            String difficultyId = difficulty1.getDifficulty();
            if (difficultyId.equals(difficultyName)) {
                StringBuilder detail = new StringBuilder();
                detail.append("\n\n");
                detail.append("Difficulty name").append(difficulty1.getDifficulty()).append("\n\n");
                detail.append("word count : ").append(difficulty1.getWordCount()).append(" words").append("\n\n");
                detail.append("time limit : ").append(difficulty1.getTime()).append(" s").append("\n\n");
                detail.append("List of words used to make typing test : ").append(getSampleWord(difficulty1).toString())
                        .append("\n\n");
                details.setText(detail.toString());
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
        ArrayList<String> words = d1.getWords();
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
     * can search for Difficulty with the specific name
     */
    private void initSearchBar() {
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.BLACK);
        searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 200), 1));
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(new Color(50, 50, 200));
        searchField.getDocument().addDocumentListener(new SearchLabelListener());
        searchJLabel = new JLabel("Search Difficulty name");
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
         * Effects: calls filterDifficulties
         * Everytime text is added to the
         * searchField by the user
         */
        public void insertUpdate(DocumentEvent e) {
            filterDifficulties();
        }

        @Override
        /*
         * Modifies: this
         * Effects: calls filterDifficulties
         * Everytime text is removed from the
         * searchField by the user
         */
        public void removeUpdate(DocumentEvent e) {
            filterDifficulties();
        }

        @Override
        /*
         * Modifies: this
         * Effects: calls filterDifficulties
         * Everytime properties fo the searchLabel
         * are changed
         */
        public void changedUpdate(DocumentEvent e) {
            filterDifficulties();
        }
    }

    /*
     * Requires: UserQuery to include numbers and UserQuery>=0
     * Modifies: this
     * Effects: Calls filterListOfDifficulties and adds each
     * element of the resultant ArrayList of type String to the 
     * list model
     */
    private void filterDifficulties() {
        String userQuery = searchField.getText();
        ArrayList<String> filteredList = lod.filterListOfDifficulties(userQuery);
        listModel.clear();
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
     * Effects: intialises the addButton
     * button with the desired proeprties
     */
    private void initAddButton() {
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setBackground(Color.BLACK);
        addDifficulty = new JButton("Add");
        addDifficulty.setPreferredSize(new Dimension(80, 50));
        addDifficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDifficulty.setBackground(new Color(50, 50, 200));
        addDifficulty.setForeground(Color.WHITE);
        addDifficulty.setFont(new Font("Arial", Font.BOLD, 8));
        addDifficulty.setOpaque(true);
        addDifficulty.setFocusPainted(false);
        addDifficulty.setContentAreaFilled(true);
        addDifficulty.setBorderPainted(false);
        addDifficulty.setFocusPainted(false);
        addDifficulty.addActionListener(new DeleteActionListener());
        topLeftPanel.add(addDifficulty);
        topPanel.add(topLeftPanel, BorderLayout.EAST);
    }

    /*
     * Modifies : this, lott
     * Effects: refreshes the components
     * of the panel to showcase any updates
     */
    public void refreshPanel() {
        listModel.clear();
        difficulties = lod.getListOfDifficulties();
        existingNames.clear();
        for (Difficulty difficulty : difficulties) {
            existingNames.add(difficulty.getDifficulty());
            listModel.addElement(difficulty.getDifficulty());
        }

        details.setText("");
        revalidate();
        repaint();
    }

    /*
     * Modifies: this, lod
     * Effects: checks if a typing
     * creates an instance of AddDifficltiesPanel
     * and switches to it
     */
    private void addDifficulty() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new AddDifficultiesPanel(lott, lod, parentFrame, existingNames, this));
        parentFrame.revalidate();
        parentFrame.repaint();
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
            addDifficulty();

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
         * of the Difficulty
         * the user stops adjusting the
         * DifficultyList
         */
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String difficultyName = difficultyList.getSelectedValue();
                showTypingTestDetails(difficultyName);
            }
        }
    }
}
