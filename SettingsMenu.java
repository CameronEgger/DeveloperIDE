import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class SettingsMenu extends JFrame {
  public static JList list;
  private JButton disable;
  private JButton enable;
  public SettingsMenu(){
    //convert the arraylist to an array and plop it into a JList
    UserProcess[] buttonArray = Window.personalized.toArray(new UserProcess[Window.personalized.size()]);
    list = new JList(buttonArray); //data has type Object[]

    //only allow the user to select one at a time
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list.setLayoutOrientation(JList.VERTICAL);
    list.setVisibleRowCount(-1);

    //create the pane to hold the list
    JScrollPane listScroller = new JScrollPane(list);
    listScroller.setPreferredSize(new Dimension(250, 80));

    //add it to the settings window
    add(listScroller);

    disable = new JButton("Disable");
    disable.addActionListener(new DisableActionListener(list));
    add(disable);

    enable = new JButton("Enable");
    enable.addActionListener(new EnableActionListener(list));
    add(enable);


    //other random stuff
    setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		setTitle("Process Settings");
  }

  public class DisableActionListener implements ActionListener{
    JList list;
    public DisableActionListener(JList list){
      this.list = list;
    }

    public void actionPerformed(ActionEvent e){
      ((UserProcess)list.getSelectedValue()).disable();
      Execute.update();
    }
  }

  public class EnableActionListener implements ActionListener{
    JList list;
    public EnableActionListener(JList list){
      this.list = list;
    }

    public void actionPerformed(ActionEvent e){
      ((UserProcess)list.getSelectedValue()).enable();
      Execute.update();
    }
  }
}
