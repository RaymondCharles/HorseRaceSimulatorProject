/**
 * Write a description of class Horse here.
 * A simple horse class...
 * @author  Raymond Mario Charles
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
    String horseName;
    char horseSymbol;
    int DistanceTravelled;
    boolean fallen;
    double horseConfidence;



    //Constructor of class Horse

    //Constructor for objects of class Horse

    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
    }



    //Other methods of class Horse
    public void fall()
    {
        this.fallen = true;
    }

    public double getConfidence()
    {
        return this.horseConfidence;
    }

    public int getDistanceTravelled()
    {
        return this.DistanceTravelled;
    }

    public String getName()
    {
        return this.horseName;
    }

    public char getSymbol()
    {
        return this.horseSymbol;
    }

    public void goBackToStart()
    {
        this.DistanceTravelled = 0;
    }
    public boolean hasFallen()
    {
        return this.fallen;
    }

    public void moveForward()
    {
        this.DistanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        this.horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;
    }

}
