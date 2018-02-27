package BusScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
public class Display extends JPanel implements MouseListener
//displays the bus stops/routes/etc
{
    private final JFrame frame;
    
    private final int length;
    private final int busses;
    private final int maxPeoplePerBus;
    private final int[] busGarage;
    private final int[] destination;
    private final ArrayList<Person> stopLocations;
    private ArrayList<ArrayList<Person>> routes;
    public Display(int length, int busses, int maxPeoplePerBus, int[] busGarage, int[] destination, ArrayList<Person> stopLocations, ArrayList<ArrayList<Person>> routes)
    //constructor
    {
        this.length = length;
        this.busses = busses;
        this.maxPeoplePerBus = maxPeoplePerBus;
        this.busGarage = busGarage;
        this.destination = destination;
        this.stopLocations = stopLocations;
        this.routes = routes;
        
        frame = new JFrame();
        frame.setTitle("Bus Map");
        frame.setSize(length + 5,length + 30);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.addMouseListener(this);
        frame.setVisible(true);
    }
    public void paintComponent(Graphics g)
    //displays objects
    {
        super.paintComponent(g);
        
        if(routes != null){
            Random rand = new Random();
            Graphics2D lines = (Graphics2D) g;
            lines.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(int i=0;i<routes.size();i++){
                lines.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                lines.draw(new Line2D.Double(busGarage[0] + 2.5, busGarage[1] + 2.5,
                routes.get(i).get(0).getStopLocation()[0] + 2.5, routes.get(i).get(0).getStopLocation()[1] + 2.5));  
                for(int j=0;j<routes.get(i).size() - 1;j++){               
                    lines.draw(new Line2D.Double(routes.get(i).get(j).getStopLocation()[0] + 2.5, routes.get(i).get(j).getStopLocation()[1] + 2.5,
                    routes.get(i).get(j + 1).getStopLocation()[0] + 2.5, routes.get(i).get(j + 1).getStopLocation()[1] + 2.5));                    
                }
                lines.draw(new Line2D.Double(routes.get(i).get(routes.get(i).size() - 1).getStopLocation()[0] + 2.5, routes.get(i).get(routes.get(i).size() - 1).getStopLocation()[1] + 2.5,
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
        for(int i=0;i<stopLocations.size();i++)
            map.fill(new Ellipse2D.Double(
            stopLocations.get(i).getStopLocation()[0], stopLocations.get(i).getStopLocation()[1], 5, 5));
    }
    public void mouseClicked(MouseEvent e)
    //detects mouse clicks
    {
        if(SwingUtilities.isLeftMouseButton(e)){
            Algorithm alg = new Algorithm(busses, maxPeoplePerBus, busGarage, destination, stopLocations);
            routes = alg.getRoutes();
            repaint();
        }
        if(SwingUtilities.isRightMouseButton(e) && routes != null){
            try{
                Print p = new Print(busGarage, destination, routes);
            }catch(java.io.IOException a){}                
        }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
