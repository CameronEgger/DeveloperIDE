import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;


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

		getRootPane().setDefaultButton(create);
	}

	public class creationEvent implements ActionListener
	{
		//this is when the actual command is entered and the button is initialised
		public void actionPerformed(ActionEvent e)
		{
			String written = text.getText();
			String named = name.getText();
			UserProcess newProcess = new UserProcess(named,written);
			newProcess.init();
			//anything after
			dispose();
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
