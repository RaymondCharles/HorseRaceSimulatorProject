public class Horse {
    private String horseName; //Name of Horse
    private char horseSymbol; //A unicode character to represent Horse
    private int distanceTravelled; //Distance
    private boolean fallen; //If the horse falled or not
    private double horseConfidence; //Decimal number from 0-1
    private String breed; // Type of horse breed

    private int laneNumber; // To store the lane number
    private String color; //Colour of Horse


    //Constructor for the Class
    public Horse(char horseSymbol, String horseName, double horseConfidence, String breed, String color) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.breed = breed;
        this.color = color;
    }

    // returns lane no.
    public int getLaneNumber() {
        return laneNumber;
    }

    // sets lane no. for a given lane no.
    public void setLaneNumber(int laneNumber) {
        this.laneNumber = laneNumber;
    }

    // sets horse as fallen
    public void fall() {
        this.fallen = true;
    }

    // returns con. rating
    public double getConfidence() {
        return this.horseConfidence;
    }

    // returns distance travelled by horse
    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    // returns horse name
    public String getName() {
        return this.horseName;
    }

    // returns char for horse
    public char getSymbol() {
        return this.horseSymbol;
    }

    // resets horse back to the start
    public void goBackToStart() {
        this.distanceTravelled = 0;
    }

    // returns true if horse falls, otherwise false
    public boolean hasFallen() {
        return this.fallen;
    }

    // increments horse distance by 1
    public void moveForward() {
        this.distanceTravelled++;
    }

    // sets confidence rating to given value
    public void setConfidence(double newConfidence) {
        this.horseConfidence = newConfidence;
    }

    // Sets char for horse for given char
    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }

    // Returns breed of horse
    public String getBreed() {
        return breed;
    }

    // Sets breed for a given breed
    public void setBreed(String breed) {
        this.breed = breed;
    }

    // returns colour of horse
    public String getColor() {
        return color;
    }

    // Sets colour for a given colour
    public void setColor(String color) {
        this.color = color;
    }
}

