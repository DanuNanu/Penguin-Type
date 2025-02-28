package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

//Represents the panel showcasing the Statistics
public class StatisticsPanel extends JPanel {
    private TypingTest currentTypingTest;
    private JLabel wpmJLabel;
    private JLabel accuracyJLabel;
    private JLabel timeTakenJLabel;
    private JLabel correctWordsTypedJLabel;
    private JLabel totalWordsTypedJLabel;
    private JButton backButton;
    private JPanel resutsLabel;
    private Statistics statistics;
    private double wpm;
    private long timeTaken;
    private int correctWords;
    private int totalWords;
    private double accuracy;
    private TypingTestGUI parentFrame;
    private JLabel headJLabel;

    // Effects: constructs the statistics panel which
    // displays the results of the typing test

    public StatisticsPanel(TypingTest currentTypingTest, TypingTestGUI parentFrame) {
        this.currentTypingTest = currentTypingTest;
        this.parentFrame = parentFrame;
        this.statistics = this.currentTypingTest.getStatistics();
        headJLabel = setLabelProperties("Results");
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        initBackButton();
        setResultsPanel();
        add(headJLabel, BorderLayout.NORTH);
        add(resutsLabel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    /*
     * Modifies: this, parentFrame
     * EffectsL sets up the results panel
     * with all the required labels and buttons
     */
    public void setResultsPanel() {
        this.resutsLabel = new JPanel();
        resutsLabel.setLayout(new GridLayout(5, 1));
        resutsLabel.setBackground(Color.BLACK);
        updateStatistics();
        initLabels();
        addLabels();
    }

    /*
     * Modifies: this
     * Effects: updates the statistics
     * that are going to be displayed
     * in the Jlabels
     */
    public void updateStatistics() {
        this.wpm = statistics.getWPM();
        this.timeTaken = statistics.getTimetaken();
        this.accuracy = statistics.getAccuracy();
        this.correctWords = statistics.getCorrectWords();
        this.totalWords = statistics.getTotalWordsTyped();
    }

    /*
     * Modifies: this
     * Effects: intialises all the Jlabels
     */
    public void initLabels() {
        wpmJLabel = setLabelProperties("Your WPM is : " + wpm);
        timeTakenJLabel = setLabelProperties("The time taken was : " + timeTaken + "s");
        correctWordsTypedJLabel = setLabelProperties("You typed " + correctWords + " words correctly");
        totalWordsTypedJLabel = setLabelProperties("You typed " + totalWords + " words");
        accuracyJLabel = setLabelProperties("Your accuracy was " + accuracy);
    }

    /*
     * Modifies: this
     * Effects: helper method for
     * initLabels(), sets up
     * all the labels with the desired properties
     * and returns Jlabel back to intiLabels()
     */
    public JLabel setLabelProperties(String labelText) {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setForeground(new Color(50, 50, 200));
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }

    /*
     * Modifies: this
     * Effects: adds all the labels
     * to the resultsLabel
     */
    public void addLabels() {
        resutsLabel.add(wpmJLabel);
        resutsLabel.add(accuracyJLabel);
        resutsLabel.add(correctWordsTypedJLabel);
        resutsLabel.add(totalWordsTypedJLabel);
        resutsLabel.add(timeTakenJLabel);

    }

    /*
     * Modifies: this
     * Effects: initialses the backButton
     * with the desired properities
     */
    public void initBackButton() {
        this.backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setBackground(new Color(50, 50, 200));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setOpaque(true);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new BackAction());
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

}
