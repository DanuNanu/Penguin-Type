package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Difficulty;
import model.ListOfDifficulties;
import model.ListOfTypingTests;
import model.TypingTest;

/*
 *  Represents a CustomTyingTestPanel
 */
public class CustomTypingTestPanel extends TypingTestPanel {
    private JComboBox<String> difficultyDropDown;
    private ArrayList<Difficulty> availableDifficulties;

    /*
     * EFFECTS: constructs a CustomTypingTestPanel
     */
    public CustomTypingTestPanel(ListOfTypingTests lott, ListOfDifficulties lotd, TypingTestGUI parentFrame) {
        super(lott, lotd, parentFrame);
        this.availableDifficulties = lotd.getListOfDifficulties();
        initUI();
    }


    /*
     * Modifies: this
     * Effects: intialises the
     * UI with the required properties
     */
    private void initUI() {
        setLayout(new BorderLayout());
        initDifficultyDropDown();
        JPanel dropDowPanel = new JPanel();
        dropDowPanel.setBackground(Color.black);
        JLabel label1 = new JLabel("Select Difficulty");
        label1.setBackground(Color.BLACK);
        label1.setForeground(new Color(50, 50, 200));
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        dropDowPanel.add(label1);
        dropDowPanel.add(difficultyDropDown);
        add(dropDowPanel, BorderLayout.NORTH);

    }

    /*
     * MODIFIES: this
     * Effects: initialiess the difficultyDropDown
     * with the desired properties
     */
    private void initDifficultyDropDown() {
        difficultyDropDown = new JComboBox<>();
        for (Difficulty difficulty : availableDifficulties) {
            difficultyDropDown.addItem(difficulty.getDifficulty());
        }

        difficultyDropDown.addActionListener(new DifficultySelection());
    }

    /*
     * Modifies: this
     * Effects: refreshes the panel
     * by getting rid of all the components
     * and then calling initPanel
     * to show the ui related
     * to the typingTest
     */
    private void refreshPanel() {
        availableDifficulties = lotd.getListOfDifficulties();
        difficultyDropDown.removeAllItems();
        for (Difficulty difficulty : availableDifficulties) {
            difficultyDropDown.addItem(difficulty.getDifficulty());
        }
        removeAll();
        initPanel();
        add(timer, BorderLayout.NORTH);
        add(testSentenceLabel, BorderLayout.CENTER);
        add(typingField, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    /*
     * Modifies: this
     * Effects: checks if the typingField is
     * not null, if this is case it requestsFocus
     * so that the user can type immediately
     * without interacting with the ui using the
     * mouse
     */
    @Override
    public void requestTextFieldFocus() {
        if (typingField != null) {
            SwingUtilities.invokeLater(() -> typingField.requestFocusInWindow());
        }
    }

    /*
     * Class that implements the ActionListener interface
     */
    private class DifficultySelection implements ActionListener {
        /*
         * Modifies: this, CustomTypingTestPanel
         * Effects: checks if the difficulty
         * selected is in the availableDiffculties
         * if that is the case it updated the
         * CustomTypingTest setting this.difficulty
         * to the selected difficulty. It also calls
         * refreshPanel() and requests focus for the typingField
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedDifficulty = (String) difficultyDropDown.getSelectedItem();
            for (Difficulty d : availableDifficulties) {
                if (selectedDifficulty.equals(d.getDifficulty())) {
                    CustomTypingTestPanel.this.difficulty = d;
                    break;
                }
            }
            refreshPanel();
            SwingUtilities.invokeLater(() -> typingField.requestFocusInWindow());
        }
    }

}