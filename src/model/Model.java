package model;

import java.util.ArrayList;

/**
 * Represents optimized bus routes and information regarding it's passengers.
 */
public class Model {

  private final int buses;
  private final int maxPeoplePerBus;

  private final int[] busGarage;
  private final int[] destination;
  private ArrayList<Person> stopLocations;
  private ArrayList<ArrayList<Person>> routes;

  /**
   * Constructor initializes model with given bus and passenger information.
   *
   * @param buses           number of buses
   * @param maxPeoplePerBus maximum number of people allowed on a bus
   * @param busGarage       the initial bus location
   * @param destination     the final stop location
   * @param stopLocations   the passenger's locations
   */
  public Model(int buses, int maxPeoplePerBus, int[] busGarage, int[] destination,
               ArrayList<Person> stopLocations) {
    this.buses = buses;
    this.maxPeoplePerBus = maxPeoplePerBus;
    this.busGarage = busGarage;
    this.destination = destination;
    this.stopLocations = stopLocations;
  }

  public int getBuses() {
    return buses;
  }

  public int getMaxPeoplePerBus() {
    return maxPeoplePerBus;
  }

  public int[] getBusGarage() {
    return busGarage;
  }

  public int[] getDestination() {
    return destination;
  }

  public ArrayList<Person> getStopLocations() {
    return stopLocations;
  }

  public ArrayList<ArrayList<Person>> getRoutes() { //returns bus routes
    return routes;
  }


  /**
   * Generates bus routes, such that overall distance between all routes is minimized.
   *
   * @param clusterDistance the maximum distance allowed for a bus stop in a bus route
   */
  public void makeRoutes(double clusterDistance) {
    ArrayList<ArrayList<Person>> temp = new ArrayList<>();
    ArrayList<Person> check = new ArrayList<>();

    for (int i = 0; i < stopLocations.size(); i++) {
      if (notUsed(check, stopLocations.get(i))) {

        ArrayList<Person> cluster = new ArrayList<>();
        cluster.add(stopLocations.get(i));
        check.add(stopLocations.get(i));

        for (Person stopLocation : stopLocations) {
          if (notUsed(check, stopLocation)) {
            if (dist(stopLocations.get(i), stopLocation) <= clusterDistance && cluster.size() < maxPeoplePerBus) {
              cluster.add(stopLocation);
              check.add(stopLocation);
            }
          }
        }
        temp.add(cluster);
      }
    }

    if (check.size() != this.stopLocations.size() || temp.size() > this.buses) {
      makeRoutes(clusterDistance + .01);
    } else {
      this.routes = optimize(temp);
    }

  }

  /**
   * Optimizes the distance traveled for all bus routes.
   *
   * @param list the list of bus routes
   * @return an optimized list of bus routes
   */
  private ArrayList<ArrayList<Person>> optimize(ArrayList<ArrayList<Person>> list) {
    ArrayList<ArrayList<Person>> optimized = new ArrayList<>();
    for (ArrayList<Person> aList : list) {
      optimized.add(optimizeRoute(aList));
    }
    return optimized;
  }

  /**
   * Optimizes the distance of the individual bus route given.
   *
   * @param p the bus route
   * @return an optimized bus route
   */
  private ArrayList<Person> optimizeRoute(ArrayList<Person> p) {
    ArrayList<ArrayList<Person>> opts = new ArrayList<>();
    ArrayList<Person> opt = new ArrayList<>();
    ArrayList<Person> temp = p;

    for (int z = 0; z < temp.size(); z++) {
      Person thisPerson = temp.get(z);
      opt.add(thisPerson);
      p.remove(thisPerson);

      while (p.size() > 0) {
        Person nextStop = new Person("", 0, null);
        double nextStopDistance = 9999999;
        for (Person aP : p) {
          double thisDistance = dist(thisPerson, aP);
          if (thisDistance < nextStopDistance) {
            nextStopDistance = thisDistance;
            nextStop = aP;
          }
        }
        opt.add(nextStop);
        thisPerson = nextStop;
        p.remove(nextStop);
      }
      opts.add(opt);
      opt = new ArrayList<>();
      p = temp;
    }

    ArrayList<Person> finalOpt = new ArrayList<>();
    double min = 9999999;
    for (ArrayList<Person> opt1 : opts) {
      double tempD = getTotalDistance(opt1);
      if (tempD < min) {
        finalOpt = opt1;
        min = tempD;
      }
    }
    return finalOpt;
  }

  /**
   * Calculates the distance between two bus stops.
   *
   * @param p1 a bus stop
   * @param p2 a different bus stop
   * @return the distance between the bus stops
   */
  public double dist(Person p1, Person p2) {
    int deltaX = Math.abs(p1.getStopLocation()[0] - p2.getStopLocation()[0]);
    int deltaY = Math.abs(p1.getStopLocation()[1] - p2.getStopLocation()[1]);
    return Math.sqrt(deltaX + deltaY);
  }

  /**
   * Calculates the distance between a point and a bus stop.
   *
   * @param p1 the point
   * @param p2 the bus stop
   * @return the distance between the point and bus stop
   */
  public double dist(int[] p1, Person p2) {
    int deltaX = Math.abs(p1[0] - p2.getStopLocation()[0]);
    int deltaY = Math.abs(p1[1] - p2.getStopLocation()[1]);
    return Math.sqrt(deltaX + deltaY);
  }

  /**
   * Calculates the total distance that a bus route travels.
   *
   * @param p the list of bus stops
   * @return the distance of the bus route
   */
  public double getTotalDistance(ArrayList<Person> p) {
    double d = dist(busGarage, p.get(0));
    for (int i = 0; i < p.size() - 1; i++)
      d += dist(p.get(i), p.get(i + 1));
    d += dist(destination, p.get(p.size() - 1));
    return d;
  }

  /**
   * Checks if the person given is already being picked up by another bus route.
   *
   * @param list people already being picked up
   * @param p    the given person
   * @return if the person is already being picked up
   */
  private boolean notUsed(ArrayList<Person> list, Person p) {
    for (Person aList : list) {
      if (aList == p)
        return false;
    }
    return true;
  }
}
