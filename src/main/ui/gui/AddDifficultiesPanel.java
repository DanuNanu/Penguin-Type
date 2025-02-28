package ui.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.*;
/*
 * Represents an AddDifficultiesPanel
 */

public class AddDifficultiesPanel extends JPanel {
    private JTextField nameField;
    private JTextField timeLimit;
    private JTextField wordCount;
    private JCheckBox customWords;
    private JPanel customWordsPanel;
    private JTextArea customWordsArea;
    private JButton submitButton;
    private HashSet<String> exisitngNames;
    private TypingTestGUI parentFrame;
    private ListOfDifficulties lod;
    private ListOfTypingTests lott;
    private DifficultiesPanel diffPan;

    /*
     * Effects: initialies the fields with the required values
     */
    public AddDifficultiesPanel(ListOfTypingTests lott, ListOfDifficulties lod, TypingTestGUI parentFrame,
            HashSet<String> existingNames, DifficultiesPanel diffPan) {
        this.diffPan = diffPan;
        this.lod = lod;
        this.lott = lott;
        this.parentFrame = parentFrame;
        this.exisitngNames = existingNames;
        initPanel();
        initSubmitButton();
    }

    /*
     * Modifies: this
     * Effects: intialises the panel
     * with the required properties
     */
    private void initPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        addTextFieldPanel(inputPanel);
        addCustomWordsCheckbox(inputPanel);
        add(inputPanel);
        initCustomWordsPanel();
    }

    /*
     * Modifies: this
     * Effects: sets up the textFields
     * and their corresponding JPanels
     */
    private void addTextFieldPanel(JPanel panel) {
        panel.add(setLabelProperties("Difficulty Name"));
        nameField = setTextFieldProperties();
        panel.add(nameField);
        panel.add(setLabelProperties("Enter word count"));
        wordCount = setTextFieldProperties();
        addNumericalValidation(wordCount);
        panel.add(wordCount);
        panel.add(setLabelProperties("Enter time limit in seconds"));
        timeLimit = setTextFieldProperties();
        addNumericalValidation(timeLimit);
        panel.add(timeLimit);
    }

    /*
     * Modifies: this
     * intialises checkBox with the
     * desired properties
     */
    private void addCustomWordsCheckbox(JPanel panel) {
        customWords = new JCheckBox("Do you want to add custom words?");
        customWords.setBackground(Color.BLACK);
        customWords.setForeground(new Color(50, 50, 200));
        customWords.setFont(new Font("Arial", Font.BOLD, 18));
        customWords.addActionListener(this::toggleCustomWordsPanel);
        panel.add(customWords);
    }

    /*
     * Modfies: this
     * Effects: when the
     * user checks the checkBox
     * it makes the customWordsPanel visible
     */
    private void toggleCustomWordsPanel(ActionEvent e) {
        customWordsPanel.setVisible(customWords.isSelected());
    }

    /*
     * Modifies: this
     * EFfects: initialises
     * the customWordsPanel with
     * the desired properties
     */
    private void initCustomWordsPanel() {
        customWordsPanel = new JPanel();
        customWordsArea = new JTextArea(5, 20);
        customWordsPanel.setLayout(new BoxLayout(customWordsPanel, BoxLayout.Y_AXIS));
        customWordsPanel.add(setLabelProperties("Enter custom words (comma or newline separated) :"));
        customWordsPanel.add(new JScrollPane(customWordsArea));
        customWordsPanel.setVisible(false);
        add(customWordsPanel);
    }

    /*
     * Modifies: this
     * Effects: intialses the submit
     * buttom with the desired properties
     */
    private void initSubmitButton() {
        submitButton = new JButton("Create Difficulty");
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setBackground(new Color(50, 50, 200));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setOpaque(true);
        submitButton.setFocusPainted(false);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton, BorderLayout.SOUTH);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSubmit();
        }
    }

    /*
     * Modifies: field
     * Effects: ensures that only numerical inout is allowed in the given text field
     */
    private void validateNumericalInput(JTextField field) {
        String text = field.getText();
        if (!text.matches("\\d*")) {
            SwingUtilities.invokeLater(() -> {
                showErrorMessage("Only numeric inputs are allowed");
            });
        }
    }

    /*
     * Modifies: this
     * Effects: adds numerical validation to
     * the JTextField
     */
    private void addNumericalValidation(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateNumericalInput(field);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateNumericalInput(field);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateNumericalInput(field);
            }
        });
    }

    /*
     * Modifies: this, lod
     * Effects: Processes input values,
     * validates them
     * and adds the new difficulty
     */
    private void handleSubmit() {
        String name = nameField.getText().trim();
        String time = timeLimit.getText().trim();
        String count = wordCount.getText().trim();

        if (isInputValid(name, time, count)) {
            processInput(name, time, count);
        }
    }

    /*
     * Modifies: this
     * Requires: validates the input values
     * for creating the new difficulty
     */
    private boolean isInputValid(String name, String time, String count) {
        if (name.isEmpty()) {
            showErrorMessage("Please enter a valid difficulty name");
            return false;
        }
        if (exisitngNames.contains(name)) {
            showErrorMessage("This name already exists, enter another one");
            return false;
        }
        if (time.isEmpty()) {
            showErrorMessage("Please enter a time limit");
            return false;
        }
        if (count.isEmpty()) {
            showErrorMessage("Please enter a valid word count");
            return false;
        }
        return true;
    }

    /*
     * Modifies: this
     * Effects: displays an error message
     * in a joption dialog
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /*
     * Modifies: this, lod, existingNames, diffan
     * Effects: creates a new difficulty and adds
     * it to te lost. Updates the parentFrame and
     * the panels
     */
    private void processInput(String name, String timeLimit, String count) {
        int time = parseInteger(timeLimit, "time limit");
        int wordCount = parseInteger(count, "word count");

        if (time != -1 && wordCount != -1) {
            secondHelperForHandleSubmit(name, wordCount, time);
        }
    }

    /*
     * Modifies: this
     * Effects: tries to pass string as integer
     * shows an error if not possible
     */
    private int parseInteger(String input, String fieldName) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number for the " + fieldName);
            return -1;
        }
    }

    /*
     * Modifies: this
     * Effects: creates a new difficulty and adds
     * it to te lost. Updates the parentFrame and
     * the panels
     */
    private void secondHelperForHandleSubmit(String name, int wordCount, int time) {
        ArrayList<String> words = getCustomWords();
        exisitngNames.add(name);
        if (customWords.isSelected()) {
            lod.addDifficulties(new Difficulty(name, words, wordCount, time));
            parentFrame.refreshPanelsOnUpdating();
        } else {
            lod.addDifficulties(new Difficulty(name, wordCount, time));
            parentFrame.refreshPanelsOnUpdating();
        }

        if (diffPan != null) {
            diffPan.refreshPanel();
        }
        updatePanel();
    }

    /*
     * Modifies: thus
     * Effects: returns a list of custom words
     * enetered by the user if anh
     */
    private ArrayList<String> getCustomWords() {
        ArrayList<String> words = new ArrayList<>();
        if (customWords.isSelected()) {
            String input = customWordsArea.getText().trim();
            if (!input.isEmpty()) {
                String[] customWordArray = input.split("[,\\n]+");
                for (String word : customWordArray) {
                    words.add(word);
                }
            } else {
                showErrorMessage("Please enter Custom Words");
            }
        }
        return words;
    }

    /*
     * Modifies; this, parentFrame
     * Effects: updates the panels
     */
    private void updatePanel() {
        JPanel panel = parentFrame.returnPanel();
        parentFrame.getContentPane().removeAll();
        parentFrame.returnLayout().show(panel, "Difficulties");
        parentFrame.add(panel, BorderLayout.CENTER);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    /*
     * Modifies: this
     * Effects: sets up the Jlabels with the desired properties
     */
    private JLabel setLabelProperties(String labelText) {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }

    /*
     * Modifies: this
     * Effects: sets up the JTextFields with the desired
     * properties
     */
    private JTextField setTextFieldProperties() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.BLACK);
        textField.setForeground(new Color(50, 50, 200));
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        Border border = new LineBorder(new Color(50, 50, 200), 2);
        textField.setBorder(border);
        return textField;
    }

}
