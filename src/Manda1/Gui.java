package Manda1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

public class Gui {
  private javax.swing.JTextPane chat;
  private JPanel mainPanel;
  private JTextField username;
  private JTextField IP;
  private JTextField port;
  private JButton connectButton;
  private JPanel fieldPanel;
  private JTextField chatField;
  private JScrollPane ScrollPane;

  public Gui() {

    DefaultCaret caret = (DefaultCaret)chat.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    JFrame frame = new JFrame();
    frame.setContentPane(mainPanel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);


    connectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String connectRespons = GuiClient.connect(username.getText(), IP.getText(), port.getText());
        chat.setText(chat.getText() + connectRespons + '\n');
        System.out.println(connectRespons);
        if (connectRespons.trim().equals("J_OK")){
          connectButton.setEnabled(false);
          chatField.setEnabled(true);
        }
      }
    });
    chatField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          GuiClient.sendMsg(username.getText(), chatField.getText());
          chat.setText(chat.getText() + username.getText() + ": " + chatField.getText()+ '\n');

          if(chatField.getText().trim().equals("QUIT"))
            frame.dispose();
          chatField.setText("");

        }
      }
    });
  }
  public void addToChat(String msg){
    chat.setText(chat.getText() + msg + '\n');
  }
}
