package view;

import java.util.*;
import java.io.*;

import conroller.Controller;
import model.Model;
import model.Person;

/**
 * A textual representation of the bus routes, written to the user's desktop.
 */
public class TextView {

  private final Model model;

  /**
   * Constructor initializes a new text view with the given model.
   *
   * @param model the model
   * @throws IOException if the file can't be created
   */
  public TextView(Model model) throws IOException {
    this.model = model;

    createFile();
    System.out.println("The document 'BusSchedule.txt' has been created, and stored on your"
            + " desktop.");
  }

  /**
   * Writes all bus routes to the user's desktop.
   *
   * @throws IOException if the file can't be written to.
   */
  private void createFile() throws IOException
  //creates a file of the bus routes
  {
    File file = new File(System.getProperty("user.home") + "/Desktop/BusSchedule.txt");
    Writer w = new BufferedWriter(new FileWriter(file));
    ArrayList<ArrayList<Person>> routes = this.model.getRoutes();

    w.write("BUS SCHEDULE\n\n");
    for (int i = 0; i < routes.size(); i++) {
      w.write("Bus" + (i + 1) + "\n");

      int nameLength = 20;
      int ageLength = 5;
      int addressLength = 8;

      w.write("Name");
      for (int z = 0; z < 2 * nameLength - 14; z++) w.write(" ");
      w.write("Age");
      for (int z = 0; z < 2 * ageLength - 3; z++) w.write(" ");
      w.write("Address");
      for (int z = 0; z < 2 * addressLength - 7; z++) w.write(" ");
      w.write("Distance\n");

      for (int j = 0; j < routes.get(i).size(); j++) {
        String name = routes.get(i).get(j).getName();
        String age = Integer.toString(routes.get(i).get(j).getAge());
        String address = Arrays.toString(routes.get(i).get(j).getStopLocation());

        int nameDifference = nameLength + (nameLength - name.length());
        int ageDifference = ageLength + (ageLength - age.length());
        int addressDifference = addressLength + (addressLength - address.length());

        w.write(name);
        for (int z = 0; z < nameDifference - 10; z++) w.write(" ");
        w.write(age);
        for (int z = 0; z < ageDifference; z++) w.write(" ");
        w.write(address);
        for (int z = 0; z < addressDifference; z++) w.write(" ");

        if (j == 0) {
          w.write(Integer.toString((int) this.model.dist(this.model.getBusGarage(),
                  routes.get(i).get(j))));
        } else {
          w.write(Integer.toString((int) this.model.dist(routes.get(i).get(j - 1),
                  routes.get(i).get(j))));
        }
        w.write("\n");
      }
      w.write("Total Distance: " + ((int) this.model.getTotalDistance(routes.get(i))) + "\n\n");
    }

    w.close();
  }
}