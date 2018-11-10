package conroller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

import model.Model;
import view.TextView;
import view.VisualView;

/**
 * Represents the start screen and controls which view is being shown.
 */
public class Controller extends JPanel implements ActionListener {

  private JFrame frame;
  private final JButton visual;
  private final JButton text;
  private final JButton close;

  private final Model model;
  private final int viewLength;

  //private final Model model;

  /**
   * Constructor takes in a model for bus routes.
   *
   * @param model the model
   */
  public Controller(Model model, int length) {
    this.model = model;
    this.viewLength = length;

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.setBorder(new BorderUIResource.LineBorderUIResource(Color.black));

    JLabel text = new JLabel("      Bus Scheduler      ");
    text.setAlignmentX(Component.CENTER_ALIGNMENT);
    text.setBackground(Color.YELLOW);
    text.setFont(new Font(text.getName(), Font.BOLD, 15));
    text.setOpaque(true);
    text.setBorder(new BorderUIResource.LineBorderUIResource(Color.black));
    this.add(text);

    this.visual = new JButton();
    this.visual.addActionListener(this);
    this.visual.setText("Visual View");
    this.visual.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(this.visual);

    this.text = new JButton();
    this.text.addActionListener(this);
    this.text.setText("Generate Text File");
    this.text.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(this.text);

    this.close = new JButton();
    this.close.addActionListener(this);
    this.close.setText("Close Program");
    this.close.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.add(this.close);

    this.frame = new JFrame();
    this.frame.add(this);
    this.frame.setResizable(false);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.pack();
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object button = e.getSource();

    if (button == this.visual) {
      this.frame.setVisible(false);
      this.frame.remove(this);
      this.frame.setSize(this.viewLength + 5, this.viewLength + 30);
      this.frame.setTitle("Bus Scheduler");
      this.frame.add(new VisualView(this.model, this.frame, this));
      this.frame.revalidate();
      this.frame.setLocationRelativeTo(null);
      this.frame.setVisible(true);
    } else if (button == this.text) {
      try {
        new TextView(this.model);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (button == this.close) {
      this.frame.dispose();
    }
  }
}
