package BusScheduler;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
public class Print
//makes a text file with bus routes
{
    private final int[] busGarage;
    private final int[] destination;
    private final ArrayList<ArrayList<Person>> routes;
    public Print(int[] busGarage, int[] destination, ArrayList<ArrayList<Person>> routes) throws java.io.IOException
    //constructor
    {
        this.busGarage = busGarage;
        this.destination = destination;
        this.routes = routes;
        
        createFile();
    }
    public void createFile() throws java.io.IOException
    //creates a file of the bus routes
    {
        File file = new File("/Users/MarkPreschern/Desktop/BusSchedule.txt");
        Writer w = new BufferedWriter(new FileWriter(file));
        
        w.write("BUS SCHEDULE\n\n");        
        for(int i=0;i<routes.size();i++){
            w.write("Bus" + (i+1) + "\n"); 
            int nameLength = 20;
            int ageLength = 5;
            int addressLength = 8;
            w.write("Name"); for(int z=0;z<2*nameLength - 14;z++) w.write(" ");
            w.write("Age"); for(int z=0;z<2*ageLength - 3;z++) w.write(" ");
            w.write("Address"); for(int z=0;z<2*addressLength - 7;z++) w.write(" ");
            w.write("Distance\n");
            for(int j=0;j<routes.get(i).size();j++){
                String name = routes.get(i).get(j).getName();
                String age = Integer.toString(routes.get(i).get(j).getAge());
                String address = Arrays.toString(routes.get(i).get(j).getStopLocation());
                int nameDifference = nameLength + (nameLength - name.length());
                int ageDifference = ageLength + (ageLength - age.length());
                int addressDifference = addressLength + (addressLength - address.length());
                w.write(name); for(int z=0;z<nameDifference - 10;z++) w.write(" ");
                w.write(age); for(int z=0;z<ageDifference;z++) w.write(" ");
                w.write(address); for(int z=0;z<addressDifference;z++) w.write(" ");                
                if(j == 0) w.write(Integer.toString((int)dist(busGarage, routes.get(i).get(j))));
                else w.write(Integer.toString((int)dist(routes.get(i).get(j - 1), routes.get(i).get(j))));
                w.write("\n");
            }
            w.write("Total Distance: " + ((int)getTotalDistance(routes.get(i))) + "\n\n");
        }
        
        w.close();
    }
    public double dist(Person p1, Person p2)
    //returns the distance between two stops
    {
        int deltaX = Math.abs(p1.getStopLocation()[0] - p2.getStopLocation()[0]);
        int deltaY = Math.abs(p1.getStopLocation()[1] - p2.getStopLocation()[1]);
        return Math.sqrt(deltaX + deltaY);      
    }
    public double dist(int[] p1, Person p2)
    //returns the distance between busGarage and a stop or destination and a stop
    {
        int deltaX = Math.abs(p1[0] - p2.getStopLocation()[0]);
        int deltaY = Math.abs(p1[1] - p2.getStopLocation()[1]);
        return Math.sqrt(deltaX + deltaY);   
    }
    public double getTotalDistance(ArrayList<Person> p)
    //returns the total of a bus route
    {
        double d = dist(busGarage, p.get(0));
        for(int i=0;i<p.size() - 1;i++)
            d += dist(p.get(i), p.get(i + 1));
        d += dist(destination, p.get(p.size() - 1));
        return d;
    }
}
