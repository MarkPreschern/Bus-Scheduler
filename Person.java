package BusScheduler;

public class Person
//makes Person objects
{
    private final String name;
    private final int age;
    private final int[] stopLocation;
    public Person(String name, int age, int[] stopLocation)
    //constructor
    {
        this.name = name;
        this.age = age;
        this.stopLocation = stopLocation;
    }
    public String getName(){ //returns person's name
        return name;}
    public int getAge(){ //returns person's age
        return age;}
    public int[] getStopLocation(){ //return person's stop location
        return stopLocation;}
}
