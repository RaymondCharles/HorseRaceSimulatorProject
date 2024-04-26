import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Race {
    private final int raceLength;
    private List<Horse> horses;
    private Map<Horse, ArrayList<Double>> horsePerformanceMetrics;

    public Race(int distance) {
        raceLength = distance;
        horses = new ArrayList<>();
        horsePerformanceMetrics = new HashMap<>();
    }

    public void addHorse(Horse theHorse, int laneNumber) {
        if (horses.size() >= 6) {
            System.out.println("Maximum number of horses reached.");
            return;
        }
        horses.add(theHorse);
    }

    public boolean placeBet(Horse horse, double amount) {
        // Placeholder implementation for placing a bet
        return true; // Change this as needed
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void startRace(JPanel racePanel) {
        if (horses.size() < 2) {
            System.out.println("Please add at least two horses to start the race.");
            return;
        }

        JLabel[] horseLabels = new JLabel[horses.size()];
        racePanel.removeAll();
        racePanel.revalidate();
        racePanel.repaint();

        racePanel.setLayout(new GridLayout(horses.size(), 1));

        for (int i = 0; i < horses.size(); i++) {
            horseLabels[i] = new JLabel();
            racePanel.add(horseLabels[i]);
        }

        // Loop until a horse wins or all fall
        while (true) {
            boolean raceFinished = true; // Assume race is finished
            boolean allFallen = true; // Assume all horses have fallen
            for (int i = 0; i < horses.size(); i++) {
                Horse horse = horses.get(i);
                if (!horse.hasFallen()) {
                    allFallen = false; // At least one horse is still running
                    moveHorse(horse);
                    horseLabels[i].setText(generateRaceLine(horse));

                    if (horse.getDistanceTravelled() < raceLength) {
                        raceFinished = false; // At least one horse is still running
                    }
                }
            }

            if (raceFinished || allFallen) {
                break; // All horses have fallen or reached the end
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display winner if exists
        Horse winner = null;
        for (Horse horse : horses) {
            if (horse.getDistanceTravelled() >= raceLength) {
                winner = horse;
                break;
            }
        }
        if (winner != null) {
            JOptionPane.showMessageDialog(null, "The winner is: " + winner.getName(), "Race Finished", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No winner. All horses have fallen.", "Race Finished", JOptionPane.INFORMATION_MESSAGE);
        }

        // Update performance metrics for each horse
        updatePerformanceMetrics();
    }


    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            // Randomly adjust confidence
            double confidenceChange = Math.random() * 0.1 - 0.05; // Random value between -0.05 and 0.05
            double newConfidence = theHorse.getConfidence() + confidenceChange;
            // Ensure confidence stays within the range [0, 1]
            newConfidence = Math.max(0, Math.min(1, newConfidence));
            theHorse.setConfidence(newConfidence);

            if (Math.random() < newConfidence) {
                theHorse.moveForward();
            }

            if (Math.random() < (0.1 * newConfidence * newConfidence)) {
                theHorse.fall();
            }
        }
    }


    private String generateRaceLine(Horse theHorse) {
        StringBuilder raceLine = new StringBuilder("|");

        for (int i = 0; i < theHorse.getDistanceTravelled(); i++) {
            raceLine.append(" ");
        }

        if (theHorse.hasFallen()) {
            raceLine.append("X");
        } else {
            raceLine.append(theHorse.getSymbol());
        }

        for (int i = theHorse.getDistanceTravelled(); i < raceLength; i++) {
            raceLine.append(" ");
        }

        raceLine.append("| ").append(theHorse.getName()).append(" (Current Confidence ").append(String.format("%.2f", theHorse.getConfidence())).append(")");
        return raceLine.toString();
    }

    private void updatePerformanceMetrics() {
        for (Horse horse : horses) {
            if (!horsePerformanceMetrics.containsKey(horse)) {
                horsePerformanceMetrics.put(horse, new ArrayList<>());
            }

            horsePerformanceMetrics.get(horse).add((double) horse.getDistanceTravelled());
        }
    }

    public Map<Horse, Double> getAverageSpeeds() {
        Map<Horse, Double> averageSpeeds = new HashMap<>();

        for (Horse horse : horsePerformanceMetrics.keySet()) {
            ArrayList<Double> distances = horsePerformanceMetrics.get(horse);
            double totalDistance = 0;
            for (Double distance : distances) {
                totalDistance += distance;
            }
            double averageDistance = totalDistance / distances.size();
            double averageSpeed = averageDistance / 10; // Assuming each step represents 1 meter
            averageSpeeds.put(horse, averageSpeed);
        }

        return averageSpeeds;
    }

    public Map<Horse, Double> getFinishingTimes() {
        Map<Horse, Double> finishingTimes = new HashMap<>();

        for (Horse horse : horsePerformanceMetrics.keySet()) {
            ArrayList<Double> distances = horsePerformanceMetrics.get(horse);
            double totalDistance = 0;
            for (Double distance : distances) {
                totalDistance += distance;
            }
            double finishingTime = totalDistance / 10; // Assuming each step represents 1 meter
            finishingTimes.put(horse, finishingTime);
        }

        return finishingTimes;
    }

    public Horse getHorseByName(String name) {
        for (Horse horse : horses) {
            if (horse.getName().equals(name)) {
                return horse;
            }
        }
        return null; // Return null if horse with the given name is not found
    }

    public Map<Horse, Double> getWinRatios() {
        Map<Horse, Double> winRatios = new HashMap<>();

        for (Horse horse : horsePerformanceMetrics.keySet()) {
            ArrayList<Double> distances = horsePerformanceMetrics.get(horse);
            int wins = 0;
            for (Double distance : distances) {
                if (distance.equals((double) raceLength)) {
                    wins++;
                }
            }
            double winRatio = (double) wins / distances.size();
            winRatios.put(horse, winRatio);
        }

        return winRatios;
    }
}
