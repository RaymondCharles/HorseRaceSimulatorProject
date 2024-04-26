public class Main {
    public static void main(String[] args) {
        // Create horses
        Horse horse1 = new Horse('♞', "H1", 0.8); // High confidence
        Horse horse2 = new Horse('♞', "H2", 0.5); // Medium confidence
        Horse horse3 = new Horse('♞', "H3", 0.3); // Low confidence

        // Create race
        Race race = new Race(50); // 50 meters race

        // Add horses to lanes
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        race.startRace();
    }
}