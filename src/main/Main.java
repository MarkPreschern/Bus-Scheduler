package main;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

import conroller.Controller;
import model.Model;
import model.Person;

/**
 * Represents the entry point for the user, creating a new bus simulation.
 */
public class Main {
  /**
   * Main method instantiates a model and creates a new controller with the model.
   *
   * @param args user input
   * @throws IOException if unable to parse the file to get names
   */
  public static void main(String[] args) throws IOException {
    Random rand = new Random();
    int length = rand.nextInt(401) + 400;
    int buses = length / 40;
    int stops = length / 4;
    int maxPeoplePerBus = stops / buses + 1;
    int[] busGarage = {rand.nextInt(length / 2), rand.nextInt(length / 2)};
    int[] destination = {rand.nextInt(length / 2) + length / 2,
            rand.nextInt(length / 2) + length / 2};
    ArrayList<Person> stopLocations = gen(stops, busGarage, destination, length);

    Model m = new Model(buses, maxPeoplePerBus, busGarage, destination, stopLocations);
    m.makeRoutes(1);
    new Controller(m, length);
  }

  /**
   * Generates random people to participate in the bus simulation.
   *
   * @param number the number of people to generate
   * @param start  the start location
   * @param des    the destination
   * @param length the bounds of the simulation's screen
   * @return a list of randomly generated people
   * @throws IOException if unable to parse the file to get names
   */
  private static ArrayList<Person> gen(int number, int[] start, int[] des, int length)
          throws IOException {

    Random rand = new Random();
    ArrayList<Person> tempPerson = new ArrayList<>();
    ArrayList<String> names = getNames(number);
    int[] ages = new int[number];
    ArrayList<Integer[]> loc = new ArrayList<>();

    //randomly generates a person's age and stop location
    for (int i = 0; i < number; i++) {
      ages[i] = rand.nextInt(11) + 5;
      int[] tempStop = {rand.nextInt(length + 1), rand.nextInt(length + 1)};
      if (tempStop != start && tempStop != des) {
        Integer[] t = {tempStop[0], tempStop[1]};
        loc.add(t);
      } else i--;
    }

    //creates a person with the newly generated information for that person
    for (int i = 0; i < number; i++) {
      int[] temp = {loc.get(i)[0], loc.get(i)[1]};
      Person p = new Person(names.get(i), ages[i], temp);
      tempPerson.add(p);
    }
    return tempPerson;
  }

  /**
   * Randomly generates the given number of names by parsing a file of names.
   *
   * @param number the number of names to generate
   * @return a list of names
   * @throws IOException if the file can't be parsed
   */
  private static ArrayList<String> getNames(int number) throws IOException {
    Random rand = new Random();
    ArrayList<String> tempNames = new ArrayList<>();
    ArrayList<String> first = new ArrayList<>();
    ArrayList<String> last = new ArrayList<>();
    Scanner scan = new Scanner(new FileReader("LastNames.txt"));
    Scanner scan2 = new Scanner(new FileReader("FemaleFirstNames.txt"));
    Scanner scan3 = new Scanner(new FileReader("MaleFirstNames.txt"));

    //parses last names
    while (scan.hasNextLine()) {
      String temp = scan.nextLine();
      String n = "";
      for (int i = 0; i < temp.length(); i++)
        if (!temp.substring(i, i + 1).equals(" "))
          n = n + temp.substring(i, i + 1);
        else break;
      last.add(n);
    }
    scan.close();

    //parses female first names
    while (scan2.hasNextLine()) {
      String temp = scan2.nextLine();
      String n = "";
      for (int i = 0; i < temp.length(); i++)
        if (!temp.substring(i, i + 1).equals(" "))
          n += temp.substring(i, i + 1);
        else break;
      first.add(n);
    }
    scan2.close();

    //parses male first names
    while (scan3.hasNextLine()) {
      String temp = scan3.nextLine();
      String n = "";
      for (int i = 0; i < temp.length(); i++) {
        if (!temp.substring(i, i + 1).equals(" "))
          n += temp.substring(i, i + 1);
        else break;
      }
      first.add(n);
    }
    scan3.close();

    //gets random names for the parsed files
    for (int i = 0; i < number; i++) {
      String firstIndex = first.get(rand.nextInt(first.size()));
      String lastIndex = last.get(rand.nextInt(last.size()));
      tempNames.add(firstIndex + " " + lastIndex);
    }
    return tempNames;
  }
}
