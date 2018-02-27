package BusScheduler;

import java.util.ArrayList;
import java.util.Arrays;
public class Information
//takes in all input values
{  
    private final int length;
    private final int busses;
    private final int maxPeoplePerBus;
    private final int[] busGarage;
    private final int[] destination;
    private ArrayList<Person> stopLocations;    
    public Information(int length, int busses, int maxPeoplePerBus, int[] busGarage, int[] destination, ArrayList<Person> stopLocations)
    //constructor
    {
        this.length = length;
        this.busses = busses;
        this.maxPeoplePerBus = maxPeoplePerBus;
        this.busGarage = busGarage;
        this.destination = destination;
        this.stopLocations = sort(stopLocations);
        
        Display d = new Display(length, busses, maxPeoplePerBus, busGarage, destination, stopLocations, null);
    }
    public ArrayList<Person> sort(ArrayList<Person> loc)
    //sorts stop locations from least to greatest
    {
        for(int i=0;i<loc.size()-1;i++){
            if(loc.get(i).getStopLocation()[0] > loc.get(i+1).getStopLocation()[0] || (loc.get(i).getStopLocation()[0] == loc.get(i+1).getStopLocation()[0] && loc.get(i).getStopLocation()[1] > loc.get(i+1).getStopLocation()[1])){
                Person temp = loc.get(i);
                loc.set(i, loc.get(i+1));
                loc.set(i+1, temp);
                i = -1;
            }
        }
        return loc;
    }        
}
