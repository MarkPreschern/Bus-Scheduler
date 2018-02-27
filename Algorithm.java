package BusScheduler;

import java.util.ArrayList;
public class Algorithm
//algorithm that creates bus routes
{
    private final int busses;
    private final int maxPeoplePerBus;
    private final int[] busGarage;
    private final int[] destination;
    private ArrayList<Person> stopLocations;
    private ArrayList<ArrayList<Person>> routes;
    public Algorithm(int busses, int maxPeoplePerBus, int[] busGarage, int[] destination, ArrayList<Person> stopLocations)
    //constructor
    {
        this.busses = busses;
        this.maxPeoplePerBus = maxPeoplePerBus;
        this.busGarage = busGarage;
        this.destination = destination;
        this.stopLocations = stopLocations;
        
        makeRoutes(.01);
    }
    public ArrayList<ArrayList<Person>> getRoutes(){ //returns bus routes
        return routes;}
    public void makeRoutes(double clusterDistance)
    //makes bus routes
    {
        ArrayList<ArrayList<Person>> temp = new ArrayList<ArrayList<Person>>();
        ArrayList<Person> check = new ArrayList<Person>();        
        for(int i=0;i<stopLocations.size();i++){
            if(used(check, stopLocations.get(i)) == false){
                ArrayList<Person> cluster = new ArrayList<Person>();
                cluster.add(stopLocations.get(i)); check.add(stopLocations.get(i));
                for(int j=0;j<stopLocations.size();j++){
                    if(used(check, stopLocations.get(j)) == false){   
                        if(dist(stopLocations.get(i), stopLocations.get(j)) <= clusterDistance && cluster.size() < maxPeoplePerBus){
                            cluster.add(stopLocations.get(j)); check.add(stopLocations.get(j));}
                    }
                }
                temp.add(cluster);
            }
        }
        
        if(check.size() != stopLocations.size() || temp.size() > busses)
            makeRoutes(clusterDistance + .01);
        else routes = optimize(temp);
    }
    public ArrayList<ArrayList<Person>> optimize(ArrayList<ArrayList<Person>> list)
    //optimizes each individual bus route
    {
        ArrayList<ArrayList<Person>> optimized = new ArrayList<ArrayList<Person>>();
        for(int i=0;i<list.size();i++)
            optimized.add(optimizeRoute(list.get(i)));
        return optimized;
    }
    public ArrayList<Person> optimizeRoute(ArrayList<Person> p)
    //returns a faster bus route of the given bus route
    {        
        ArrayList<ArrayList<Person>> opts = new ArrayList<ArrayList<Person>>();
        ArrayList<Person> opt = new ArrayList<Person>();
        ArrayList<Person> temp = p;
        for(int z=0;z<temp.size();z++){
            Person thisPerson = temp.get(z);
            opt.add(thisPerson);
            p.remove(thisPerson);
            while(p.size() > 0){
                Person nextStop = new Person("", 0, null);
                double nextStopDistance = 9999999;
                for(int i=0;i<p.size();i++){
                    double thisDistance = dist(thisPerson, p.get(i));
                    if(thisDistance < nextStopDistance){
                        nextStopDistance = thisDistance;
                        nextStop = p.get(i);
                    }
                }
                opt.add(nextStop);
                thisPerson = nextStop;
                p.remove(nextStop);
            }
            opts.add(opt);
            opt = new ArrayList<Person>();
            p = temp;
        }
        
        ArrayList<Person> finalOpt = new ArrayList<Person>();
        double min = 9999999;
        for(int i=0;i<opts.size();i++){
            double tempD = getTotalDistance(opts.get(i));
            if(tempD < min){
                finalOpt = opts.get(i);
                min = tempD;               
            }
        }
        return finalOpt;        
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
    public boolean used(ArrayList<Person> list, Person p)
    //checks if the person is already being used by another bus route
    {
        for(int i=0;i<list.size();i++){
                if(list.get(i) == p)
                    return true;}
        return false;                    
    }
}
