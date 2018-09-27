package Network;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

public class Gui {
  private JTextPane textPane1;
  private JTextField textField1;
  private JPanel panel;
  UDP udp;
  String from;

  public Gui(UDP udp, String from) {
    this.udp = udp;
    this.from = from;
    JFrame frame = new JFrame(from);
    frame.setContentPane(panel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(800,400);
    frame.setVisible(true);


    textField1.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          try {
            //textPane1.setText(textPane1.getText() + textField1.getText() + "\n");
            addToTextPanel(textField1.getText());
            udp.send(from + ": " + textField1.getText());
            textField1.setText("");
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      }
    });
  }


public void addToTextPanel(String text){
    textPane1.setText(textPane1.getText() + text + "\n");
}

  public static void main(String[] args) {
    UDP udp1 = new UDP(6710,6701);
    UDP udp2 = new UDP(6701,6710);
    Gui gui1 = new Gui(udp1, "6710");
    Gui gui2 = new Gui(udp2, "6701");
    udp1.setGui(gui1);
    udp2.setGui(gui2);
  }
}
