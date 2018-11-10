package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import conroller.Controller;
import model.Model;
import model.Person;

/**
 * A visual representation of bus routes.
 */
public class VisualView extends JPanel implements ActionListener {

  private JFrame frame;
  private final JButton animate;
  private final JButton back;

  private final Model model;
  private final Controller controller;
  private ArrayList<ArrayList<Person>> localRoutes;

  private ArrayList<Color> colors = new ArrayList<>();

  /**
   * Constructs a visual view on the given JFrame, holding a reference to the model and controller.
   *
   * @param model      the model
   * @param frame      the JFrame
   * @param controller the controller
   */
  public VisualView(Model model, JFrame frame, Controller controller) {

    this.model = model;
    this.frame = frame;
    this.controller = controller;
    this.localRoutes = new ArrayList<>();

    this.animate = new JButton();
    this.animate.addActionListener(this);
    this.animate.setText("Animate");
    this.animate.setAlignmentY(Component.CENTER_ALIGNMENT);
    this.add(this.animate);

    this.back = new JButton();
    this.back.addActionListener(this);
    this.back.setText("Go Back");
    this.back.setAlignmentY(Component.CENTER_ALIGNMENT);
    this.add(this.back);
  }

  @Override
  public void paintComponent(Graphics g)
  //displays objects
  {
    super.paintComponent(g);

    int[] busGarage = this.model.getBusGarage();
    int[] destination = this.model.getDestination();

    if (this.localRoutes != null) {
      Graphics2D lines = (Graphics2D) g;
      lines.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      for (int i = 0; i < this.localRoutes.size(); i++) {
        ArrayList<Person> route = this.localRoutes.get(i);
        lines.setColor(this.colors.get(i));
        lines.draw(new Line2D.Double(busGarage[0] + 2.5, busGarage[1] + 2.5,
                route.get(0).getStopLocation()[0] + 2.5, route.get(0).getStopLocation()[1] + 2.5));
        for (int j = 0; j < route.size() - 1; j++) {
          lines.draw(new Line2D.Double(route.get(j).getStopLocation()[0] + 2.5, route.get(j).getStopLocation()[1] + 2.5,
                  route.get(j + 1).getStopLocation()[0] + 2.5, route.get(j + 1).getStopLocation()[1] + 2.5));
        }
        lines.draw(new Line2D.Double(route.get(route.size() - 1).getStopLocation()[0] + 2.5, route.get(route.size() - 1).getStopLocation()[1] + 2.5,
                destination[0] + 2.5, destination[1] + 2.5));
      }
    }

    Graphics2D map = (Graphics2D) g;
    map.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    map.setColor(Color.red);
    map.fill(new Ellipse2D.Double(busGarage[0], busGarage[1], 5, 5));
    map.setColor(Color.blue);
    map.fill(new Ellipse2D.Double(destination[0], destination[1], 5, 5));
    map.setColor(Color.black);
    for (int i = 0; i < this.model.getStopLocations().size(); i++) {
      Person stopLocation = this.model.getStopLocations().get(i);
      map.fill(new Ellipse2D.Double(stopLocation.getStopLocation()[0],
              stopLocation.getStopLocation()[1], 5, 5));
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object button = e.getSource();

    if (button == this.animate) {
      this.animate();
    } else if (button == this.back) {
      this.close();
    }
  }

  /**
   * Represents an animates of the view.
   */
  private void animate() throws UnsupportedOperationException {
    this.localRoutes = new ArrayList<>();
    ArrayList<ArrayList<Person>> temp = this.model.getRoutes();
    Random rand = new Random();

    for (int i = 0; i < temp.size(); i++) {
      ArrayList<Person> temp1 = temp.get(i);
      ArrayList<Person> temp2 = new ArrayList<>(temp.size());
      this.colors.add(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
      for (Person person : temp1) {

        this.paintImmediately(0, 0, 1000, 1000);
        temp2.add(person);

        if (this.localRoutes.size() > i) {
          this.localRoutes.set(i, temp2);
        } else {
          this.localRoutes.add(temp2);
        }

        try {
          TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      }
    }
  }

  /**
   * Closes the view and goes back to the start screen.
   */
  private void close() {
    this.frame.setVisible(false);
    this.frame.remove(this);
    this.frame.setTitle(null);
    this.frame.add(this.controller);
    this.frame.revalidate();
    this.frame.pack();
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }
}
