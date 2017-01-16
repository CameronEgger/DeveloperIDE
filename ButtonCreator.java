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
	}
	
	public class creationEvent implements ActionListener
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
			public cmdEvent(String direct, String cmd)
			{
				com = cmd;
				dir = direct;
			}
			public void actionPerformed(ActionEvent e)
			{
				try {
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Is this even working");
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
