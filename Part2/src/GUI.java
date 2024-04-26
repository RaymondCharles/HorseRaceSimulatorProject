import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {
    private JButton startRaceButton;
    private JButton settingsButton;
    private JButton exitButton;

    public GUI() {
        setTitle("Horse Racing and Betting System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(135, 206, 250)); // Set background color to sky blue
        setLayout(new BorderLayout());

        // Create game menu panel
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons for game menu
        startRaceButton = new JButton("Start Race");
        settingsButton = new JButton("Settings (Coming Soon)");
        exitButton = new JButton("Exit Game");

        // Add buttons to menu panel
        menuPanel.add(startRaceButton);
        menuPanel.add(settingsButton);
        menuPanel.add(exitButton);

        // Add menu panel to the center of the frame
        add(menuPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open race settings GUI
                openRaceSettings();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show message that settings will be implemented soon
                JOptionPane.showMessageDialog(null, "Settings will be implemented soon.", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the game
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void openRaceSettings() {
        // Create and show the race settings GUI
        RacingTrackGUI racingTrackGUI = new RacingTrackGUI();
        racingTrackGUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}

class RacingTrackGUI extends JFrame {
    // Existing code for RacingTrackGUI with race settings and visualization
    private JTextField trackLengthField;
    private JTextField numberOfTracksField;
    private JTextField[] horseNameFields;
    private JComboBox<String>[] breedComboBoxes;
    private JComboBox<String>[] colorComboBoxes;
    private JButton startRaceButton;
    private JButton placeBetButton;
    private JTextField betAmountField;
    private JComboBox<String> horseSelectionComboBox; // Updated type to String
    private JPanel racePanel;
    private JPanel statisticsPanel;
    private JPanel betHistoryPanel;
    private Race currentRace;
    private UserAccount currentUser;

    public RacingTrackGUI() {
        setTitle("Horse Racing and Betting System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(135, 206, 250)); // Set background color to sky blue

        // Panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel trackLengthLabel = new JLabel("Track Length (in meters):");
        trackLengthField = new JTextField();
        inputPanel.add(trackLengthLabel);
        inputPanel.add(trackLengthField);

        JLabel numberOfTracksLabel = new JLabel("Number of Horses (max 6):");
        numberOfTracksField = new JTextField();
        inputPanel.add(numberOfTracksLabel);
        inputPanel.add(numberOfTracksField);

        // Adding fields for horse names, breeds, and colors
        int maxHorses = 6;
        horseNameFields = new JTextField[maxHorses];
        breedComboBoxes = new JComboBox[maxHorses];
        colorComboBoxes = new JComboBox[maxHorses];

        for (int i = 0; i < maxHorses; i++) {
            JLabel horseNameLabel = new JLabel("Horse " + (i + 1) + " Name:");
            horseNameFields[i] = new JTextField();
            inputPanel.add(horseNameLabel);
            inputPanel.add(horseNameFields[i]);

            JLabel breedLabel = new JLabel("Breed:");
            breedComboBoxes[i] = new JComboBox<>(new String[]{"Thoroughbred", "Quarter Horse", "Appaloosa", "Arabian", "Other"});
            inputPanel.add(breedLabel);
            inputPanel.add(breedComboBoxes[i]);

            JLabel colorLabel = new JLabel("Color:");
            colorComboBoxes[i] = new JComboBox<>(new String[]{"Brown", "Black", "White", "Gray", "Other"});
            inputPanel.add(colorLabel);
            inputPanel.add(colorComboBoxes[i]);
        }

        // Panel for buttons and bet input
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        startRaceButton = new JButton("Start Race");
        buttonPanel.add(startRaceButton);

        placeBetButton = new JButton("Place Bet");
        buttonPanel.add(placeBetButton);

        betAmountField = new JTextField();
        buttonPanel.add(betAmountField);

        // Panel for horse selection and bet input
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        horseSelectionComboBox = new JComboBox<>();
        selectionPanel.add(horseSelectionComboBox);

        JLabel betAmountLabel = new JLabel("Bet Amount:");
        selectionPanel.add(betAmountLabel);

        // Container for buttons and bet input
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.add(buttonPanel, BorderLayout.CENTER);
        buttonContainer.add(selectionPanel, BorderLayout.EAST);

        // Panel for race visualization
        racePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRaceTrack(g);
            }
        };
        racePanel.setLayout(new GridLayout(maxHorses, 1));
        racePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        racePanel.setBackground(new Color(135, 206, 250)); // Set background color to blue

        // Panel for statistics and analytics
        statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new GridLayout(0, 1));
        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel for bet history
        betHistoryPanel = new JPanel();
        betHistoryPanel.setLayout(new GridLayout(0, 1));
        betHistoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Container for statisticsPanel and betHistoryPanel
        JPanel statsAndBetContainer = new JPanel(new GridLayout(2, 1));
        statsAndBetContainer.add(statisticsPanel);
        statsAndBetContainer.add(betHistoryPanel);

        // Container for racePanel and statsAndBetContainer
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.add(racePanel);
        contentPanel.add(statsAndBetContainer);

        add(inputPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonContainer, BorderLayout.SOUTH);

        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int trackLength = Integer.parseInt(trackLengthField.getText());
                int numberOfTracks = Integer.parseInt(numberOfTracksField.getText());

                currentRace = new Race(trackLength);

                for (int i = 0; i < numberOfTracks; i++) {
                    Horse horse = createCustomizedHorse(i);
                    currentRace.addHorse(horse, i + 1);
                }

                updateHorseSelectionComboBox();

                startRace();
            }
        });

        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRace == null) {
                    JOptionPane.showMessageDialog(null, "Please start the race first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double betAmount;
                try {
                    betAmount = Double.parseDouble(betAmountField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid bet amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (betAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Bet amount must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Horse selectedHorse = currentRace.getHorseByName((String) horseSelectionComboBox.getSelectedItem());
                if (selectedHorse != null) {
                    boolean betPlaced = currentRace.placeBet(selectedHorse, betAmount);
                    if (betPlaced) {
                        JOptionPane.showMessageDialog(null, "Bet placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        currentUser.addBet(selectedHorse.getName(), betAmount);
                        updateBetHistory();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to place bet. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Selected horse not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        currentUser = new UserAccount();

        setVisible(true);
    }

    private Horse createCustomizedHorse(int horseNumber) {
        String horseName = horseNameFields[horseNumber].getText();
        String breed = (String) breedComboBoxes[horseNumber].getSelectedItem();
        String color = (String) colorComboBoxes[horseNumber].getSelectedItem();
        double horseConfidence = Math.random(); // Random initial confidence between 0 and 1

        // Set horse symbol based on breed
        char horseSymbol;
        switch (breed) {
            case "Thoroughbred":
                horseSymbol = '♞'; // Unicode representation of horse
                break;
            case "Quarter Horse":
                horseSymbol = '♞'; // Unicode representation of horse
                break;
            case "Appaloosa":
                horseSymbol = '♞'; // Unicode representation of horse
                break;
            case "Arabian":
                horseSymbol = '♞'; // Unicode representation of horse
                break;
            default:
                horseSymbol = '❌'; // Unicode representation of fallen horse
        }

        //return new Horse(horseSymbol, horseName, horseConfidence, breed, color);
        Horse horse = new Horse(horseSymbol, horseName, horseConfidence, breed, color);
        horse.setLaneNumber(horseNumber); // Set the lane number for the horse
        return horse;
    }

    private void startRace() {
        // Create and start a new thread to run the race simulation
        new Thread(new Runnable() {
            @Override
            public void run() {
                currentRace.startRace(racePanel);

                // Update statistics panel after race finishes
                updateStatisticsPanel();
            }
        }).start();
    }

    private void drawRaceTrack(Graphics g) {
        if (currentRace != null) {
            for (Horse horse : currentRace.getHorses()) {
                drawLane(g, horse);
            }
        }
    }

    private void drawLane(Graphics g, Horse horse) {
        int numHorses = currentRace.getHorses().size();
        int panelHeight = racePanel.getHeight();
        int laneHeight = panelHeight / numHorses;
        int remainder = panelHeight % numHorses; // Calculate remainder to adjust for rounding errors

        int laneY = laneHeight * horse.getLaneNumber();
        // Adjust the lane height for the first horse if there's a remainder
        if (horse.getLaneNumber() == 0) {
            laneHeight += remainder;
        }

        // Draw grass
        g.setColor(new Color(34, 139, 34));
        g.fillRect(0, laneY, racePanel.getWidth(), laneHeight);

        // Draw track surface
        g.setColor(Color.GRAY);
        int trackHeight = laneHeight / 5;
        int trackY = (laneHeight - trackHeight) / 2 + laneY;
        g.fillRect(0, trackY, racePanel.getWidth(), trackHeight);

        // Draw track markers
        g.setColor(Color.WHITE);
        int numMarkers = 10;
        int markerGap = racePanel.getWidth() / numMarkers;
        for (int i = 1; i <= numMarkers; i++) {
            g.drawLine(i * markerGap, trackY, i * markerGap, trackY + trackHeight);
        }

        // Draw finish line
        g.setColor(Color.RED);
        g.drawLine(racePanel.getWidth() - 20, trackY, racePanel.getWidth() - 20, trackY + trackHeight);
    }


    private void updateStatisticsPanel() {
        // Clear statistics panel
        statisticsPanel.removeAll();

        // Add statistics for the current race
        JLabel raceStatisticsLabel = new JLabel("Race Statistics:");
        statisticsPanel.add(raceStatisticsLabel);

        // Get performance metrics for each horse
        Map<Horse, Double> averageSpeeds = currentRace.getAverageSpeeds();
        Map<Horse, Double> finishingTimes = currentRace.getFinishingTimes();
        Map<Horse, Double> winRatios = currentRace.getWinRatios();

        DecimalFormat df = new DecimalFormat("#.##");
        for (Horse horse : averageSpeeds.keySet()) {
            JLabel horseLabel = new JLabel("Horse: " + horse.getName() +
                    ", Average Speed: " + df.format(averageSpeeds.get(horse)) +
                    ", Finishing Time: " + df.format(finishingTimes.get(horse)) +
                    ", Win Ratio: " + df.format(winRatios.get(horse)));
            statisticsPanel.add(horseLabel);
        }

        // Refresh statistics panel
        statisticsPanel.revalidate();
        statisticsPanel.repaint();
    }

    private void updateHorseSelectionComboBox() {
        horseSelectionComboBox.removeAllItems();
        if (currentRace != null) {
            for (Horse horse : currentRace.getHorses()) {
                horseSelectionComboBox.addItem(horse.getName()); // Add horse names instead of horse objects
            }
        }
    }

    private void updateBetHistory() {
        // Clear the previous bet history
        betHistoryPanel.removeAll();

        // Update the UI with the latest bet history
        JLabel betHistoryLabel = new JLabel("Bet History:");
        betHistoryPanel.add(betHistoryLabel);

        for (String horse : currentUser.getBetHistory().keySet()) {
            double betAmount = currentUser.getBetHistory().get(horse);
            JLabel betLabel = new JLabel("Horse: " + horse + ", Bet Amount: " + betAmount);
            betHistoryPanel.add(betLabel);
        }

        // Refresh bet history panel
        betHistoryPanel.revalidate();
        betHistoryPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }

}

class UserAccount {
    private Map<String, Double> betHistory;
    private double totalWinnings;
    private double totalLosses;

    public UserAccount() {
        betHistory = new HashMap<>();
        totalWinnings = 0.0;
        totalLosses = 0.0;
    }

    public Map<String, Double> getBetHistory() {
        return betHistory;
    }

    public void addBet(String horseName, double betAmount) {
        betHistory.put(horseName, betAmount);
    }

    public void clearBetHistory() {
        betHistory.clear();
    }

    public double getTotalWinnings() {
        return totalWinnings;
    }

    public void addWinnings(double winnings) {
        totalWinnings += winnings;
    }

    public double getTotalLosses() {
        return totalLosses;
    }

    public void addLosses(double losses) {
        totalLosses += losses;
    }
}


