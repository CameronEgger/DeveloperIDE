import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.Runtime;
import java.lang.Process;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class ButtonCreator extends JFrame
{
	private JLabel namePrompt;
	private JTextField name;
	private JLabel cmd;
	private JButton create;
	private JButton cancel;
	private JTextField text;
	public ButtonCreator()
	{
		namePrompt = new JLabel("Enter name");
		add(namePrompt);

		name = new JTextField(10);
		add(name);

		cmd = new JLabel("Enter command");
		add(cmd);

		text = new JTextField(25);
		add(text);


		create = new JButton("Create");
		add(create);
		creationEvent creation = new creationEvent();
		create.addActionListener(creation);

		cancel = new JButton("Cancel");
		add(cancel);
		cancelEvent can = new cancelEvent();
		cancel.addActionListener(can);

		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		setTitle("Create Button");
	}

	public class creationEvent implements ActionListener, Serializable
	{
		public void actionPerformed(ActionEvent e)
		{
			String written = text.getText();
			String named = name.getText();
			JButton button = new JButton(named);
			cmdEvent cmd = new cmdEvent("ToDo",written);
			button.addActionListener(cmd);
			Window.personalized.add(button);
			Execute.update();
			dispose();
		}

		public class cmdEvent implements ActionListener
		{
			private String com;
			private String dir;
			ArrayList<String> commandList;
			public cmdEvent(String direct, String cmd)
			{
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
			public void actionPerformed(ActionEvent e)
			{
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
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
	}

	public class cancelEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			dispose();
		}
	}
}
