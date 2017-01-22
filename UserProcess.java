import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.Runtime;
import java.lang.Process;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class UserProcess implements Serializable {
  private String name;
  private String command;
  public UserProcess(String n, String c){
    name = n;
    command = c;
    JButton button = new JButton(name);
    cmdEvent cmd = new cmdEvent(name,command);
    button.addActionListener(cmd);
    Window.personalized.add(button);
    Execute.update();
    save();
  }

  private void save() {
    try {
      new File("processes").mkdir();
      OutputStream fileStream = Files.newOutputStream(Paths.get(UserProcess.class.getProtectionDomain().getCodeSource().getLocation().getFile()+"processes/"+name));
      ObjectOutputStream oos = new ObjectOutputStream(fileStream);
      oos.writeObject(this);
    }
    catch(Exception consarnit){
      consarnit.printStackTrace();
    }
  }

  public class cmdEvent implements ActionListener {
    private String com;
    private String dir;
    ArrayList<String> commandList;
    //this method is called when the button is added to the frame, gets the command ready to be done
    public cmdEvent(String direct, String cmd) {
      com = cmd;
      dir = direct;
      //this is the list we construct to pass to ProcessBuilder
      commandList = new ArrayList<String>();
      Scanner chopper = new Scanner(com);
      while(chopper.hasNext()){
        commandList.add(chopper.next());
      }
      chopper.close();
    }

    //this is when the button is clicked
    public void actionPerformed(ActionEvent e) {
      try {
        System.out.println("Is this even working");
        //seperate the user's command input and feed it to the arguments of ProcessBuilder
        ProcessBuilder p = new ProcessBuilder(commandList);
        //this puts the stderr into the inputstream so there's not weird hanging issues
        p.redirectErrorStream(true);
        Process processObj = p.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(processObj.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        processObj.waitFor();
        //waitFor the thing to get done and then close the buffer
        in.close();
      }
      catch (Exception e1) {
        e1.printStackTrace();
      }

    }
  }
}
