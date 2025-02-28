package ui.gui;

import model.*;
import model.Event;
import persistence.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Main window the gui
public class TypingTestGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel panel;
    private ListOfDifficulties listOfDifficulties;
    private ListOfTypingTests listOfTypingTests;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Difficulty defaultDifficulty;
    private Boolean typingTestPanelExists;
    private Boolean customTypingTestPanelExists;
    private static final String JASON_STORE = "./data/typingTest.json";

    /*
     * Effects: sets up the main window
     * For the typing test application
     */
    public TypingTestGUI() {
        this.listOfDifficulties = new ListOfDifficulties();
        this.typingTestPanelExists = false;
        this.customTypingTestPanelExists = false;
        this.listOfTypingTests = new ListOfTypingTests();
        this.defaultDifficulty = new Difficulty("Normal", 50, 20);
        this.listOfDifficulties.getListOfDifficulties().add(defaultDifficulty);
        jsonWriter = new JsonWriter(JASON_STORE);
        jsonReader = new JsonReader(JASON_STORE);
        showSplashScreen();
        setVisible(true);
    }

    /*
     * Modifies: this
     * Effects: creates splashScreen of image
     * that dissapears in 3 seconds
     */
    private void showSplashScreen() {
        JFrame splashFrame = new JFrame();
        splashFrame.setSize(800,600);
        splashFrame.setUndecorated(true);
        splashFrame.setLocationRelativeTo(null);
        ImageIcon splashImage = new ImageIcon("./data/penguin.jpg");
        JLabel splashLabel = new JLabel(splashImage);
        splashFrame.add(splashLabel);
        splashFrame.setVisible(true);
        Timer splashTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splashFrame.setVisible(false);
                init();
                add(panel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        splashTimer.setRepeats(false);
        splashTimer.start();
    }


    /*
     * Modifies: this
     * Effects: intialises the main window
     * of the typing test gui
     */
    public void init() {
        setBackground(Color.MAGENTA);
        setTitle("Penguin Type");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        logEventsUponClosing();
        setLayout(new BorderLayout());
        initPanel();
        add(panel, BorderLayout.CENTER);
        createMenu();
    }

    public void logEventsUponClosing() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                System.out.println("Printing the event log: ");
                for (Event event : eventLog) {
                    System.out.println(event);
                }
                System.exit(0);
            }
        });
    }

    /*
     * Modifies: this
     * Helper method for the init() method
     * intialises the main panel
     * which is the main window of the
     * application
     */
    public void initPanel() {
        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);
        panel.setBackground(Color.BLACK);
        setMainLayout();
    }

    /*
     * Modifies: this
     * Creates the main layout
     * of the application
     * as well as creating the
     * layout of the first window of the application
     */
    public void setMainLayout() {
        JPanel buttonPanel = createButtonPanel();
        JPanel difficultyPanel = new DifficultiesPanel(listOfTypingTests, listOfDifficulties, this);
        JPanel customTypingTestPanel = new CustomTypingTestPanel(listOfTypingTests, listOfDifficulties, this);
        JPanel viewTypingPanel = new ViewTypingTestPanel(listOfTypingTests, listOfDifficulties, this);
        JPanel typingTestPanel = new TypingTestPanel(listOfTypingTests, listOfDifficulties, this);
        panel.add(buttonPanel, "Buttons");
        panel.add(typingTestPanel, "Regular Typing Test");
        panel.add(customTypingTestPanel, "Custom Typing Test");
        panel.add(difficultyPanel, "Difficulties");
        panel.add(viewTypingPanel, "View Typing Test");
    }

    /*
     * Modifies: this
     * Effects: refreshes the panels
     * on updating
     */
    public void refreshPanelsOnUpdating() {
        panel.removeAll();
        setMainLayout();
        revalidate();
        repaint();
    }

    /*
     * modifies: this
     * creates the panel required
     * for the main window
     * of the application
     */
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.black);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titLabel = new JLabel("PenguinType", JLabel.CENTER);
        titLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titLabel.setForeground(new Color(50, 50, 200));
        titLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(titLabel);
        buttonPanel.add(Box.createVerticalStrut(80));
        createButtons(buttonPanel);
        return buttonPanel;
    }

    /*
     * Modifies: this
     * effects: creates the buttons
     * for the buttonPanel
     */
    public void createButtons(JPanel buttonPanel) {
        String[] buttonNames = { "Regular Typing Test",
                "Custom Typing Test", "Difficulties", "View Typing Test" };

        for (String i1 : buttonNames) {
            JButton button = new JButton(i1);
            button.setPreferredSize(new Dimension(200, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(new Color(50, 50, 200));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setOpaque(true);
            button.setFocusPainted(false);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.addActionListener(new ButtonAction(i1));
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(40));

        }

    }

    /*
     * Modifies: this
     * Effects: creates new instance of
     * TypingTestPanel and then
     * adds it to the panel
     */
    public void createNewTypingTestPanel() {
        TypingTestPanel newTTP = new TypingTestPanel(listOfTypingTests, listOfDifficulties, this);
        panel.add(newTTP, "Regular Typing Test");
    }

    /*
     * Modifies: this
     * Effects: creates new instance of
     * CustomTypingTestPanel and then
     * adds it to the panel
     */
    public void createNewCustomTypingTestPanel() {
        CustomTypingTestPanel newTCP = new CustomTypingTestPanel(listOfTypingTests, listOfDifficulties, this);
        panel.add(newTCP, "Custom Typing Test");
        panel.revalidate();
        panel.repaint();
    }

    /*
     * Effects: creates menu bars
     * for the frame of the application
     */
    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu filMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        saveMenuItem.addActionListener(e -> saveLoTDandLoTT());
        loadMenuItem.addActionListener(e -> loadLoTDandLoTT());
        filMenu.add(saveMenuItem);
        filMenu.add(loadMenuItem);
        menuBar.add(filMenu);
        setJMenuBar(menuBar);
    }

    /*
     * Class implementing the ActionListener interface
     */
    private class ButtonAction implements ActionListener {
        private String panelName;

        public ButtonAction(String panelName) {
            this.panelName = panelName;
        }

        /*
         * Effects: implements actionlistener
         * for the buttons used in the
         * main screen of the application
         * Chekcs if the panelName is equal
         * to Regular Typing Test, if that
         * is the case then it iterates through
         * all the components in the JPanel panel
         * then checks if the comp is an instanceOf
         * TypingTestPanel, if that is the case
         * it calls the method requestTextFieldFocus()
         * and sets typingTestPanelExists to true
         */

        @Override
        /* 
         * Modifies: this
         * Effects: switches the current JPanel
         * to the chosen one. Also updates all the 
         * panels to reflect latest updates.
         * Checks if either of the panels are
         * the TypingTestPanel or the CustomTypingTestPanel
         * if that is the case, it requests focus for the
         * TextField of these panels
         */
        public void actionPerformed(ActionEvent e) {
            resetPanelForTypingTest();
            resetPanelForCustomTypingTest();
            updateViewTypingTestPanel();
            updateDifficultiesPanel();
            cardLayout.show(panel, this.panelName);

            if (panelName.equals("Regular Typing Test")) {
                typingTestPanelExists = true;
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof TypingTestPanel) {
                        ((TypingTestPanel) comp).requestTextFieldFocus();
                        break;
                    }
                }
            }

            if (panelName.equals("Custom Typing Test")) {
                customTypingTestPanelExists = true;
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof CustomTypingTestPanel) {
                        ((CustomTypingTestPanel) comp).requestTextFieldFocus();
                        break;
                    }
                }
            }

        }

        /*
         * Modifies: this, panel
         * Effects: checks whether an instance of
         * the TypingTestPanel exists and if the
         * button pressed is for starting a regular
         * if this is the case
         * It removes the current instance
         * of the TypingTestPanel from the
         * panel and replacees it with
         * a new instance of the TypingTestPanel
         */
        private void resetPanelForTypingTest() {
            if (typingTestPanelExists && this.panelName.equals("Regular Typing Test")) {
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof TypingTestPanel) {
                        panel.remove(comp);
                        break;
                    }
                    TypingTestGUI.this.createNewTypingTestPanel();
                }

            }
        }

        /*
         * Modifies: this, panel
         * Effects: checks whether an instance of
         * the CustomTypingTestPanel exists and if the
         * button pressed is for starting a regular
         * if this is the case
         * It removes the current instance
         * of the TypingTestPanel from the
         * panel and replacees it with
         * a new instance of the TypingTestPanel
         */
        private void resetPanelForCustomTypingTest() {
            if (customTypingTestPanelExists && this.panelName.equals("Custom Typing Test")) {
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof CustomTypingTestPanel) {
                        panel.remove(comp);
                        break;
                    }
                    TypingTestGUI.this.createNewCustomTypingTestPanel();

                }
            }
        }

        /*
         * Modifies: this
         * Effects: updates the ViewTypingTestPanel
         * by refreshing it
         */
        private void updateViewTypingTestPanel() {
            if (this.panelName.equals("View Typing Test")) {
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof ViewTypingTestPanel) {
                        ((ViewTypingTestPanel) comp).refreshPanel();
                        break;
                    }
                }

            }
        }

         /*
         * Modifies: this
         * Effects: updates the updateDifficultiesPanel
         * by refreshing it
         */
        private void updateDifficultiesPanel() {
            if (this.panelName.equals("Difficulties")) {
                for (Component comp : panel.getComponents()) {
                    if (comp instanceof DifficultiesPanel) {
                        ((DifficultiesPanel) comp).refreshPanel();
                        break;
                    }
                }

            }
        }
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
            refreshPanelsOnUpdating();
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JASON_STORE);
        }
    }

    public CardLayout returnLayout() {
        return cardLayout;
    }

    public JPanel returnPanel() {
        return panel;
    }

}
