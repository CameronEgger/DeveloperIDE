import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ButtonCreator extends JFrame
{
	private JLabel cmd;
	private JButton create;
	private JTextField text;
	private ButtonCreator curr;
	public ButtonCreator()
	{
		curr = this;
		cmd = new JLabel("Enter command");
		add(cmd);
		
		text = new JTextField(25);
		add(text);
		
		
		create = new JButton("Create");
		add(create);
		creationEvent creation = new creationEvent();
		create.addActionListener(creation);
		
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		setTitle("Create Button");
	}
	
	public class creationEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String written = text.getText();
			System.out.println(written);
			JButton button = new JButton();
			cmdEvent cmd = new cmdEvent(written);
			button.addActionListener(cmd);
			Window.personalized.add(button);
			curr.dispose();
		}
		
		public class cmdEvent implements ActionListener
		{
			private String com;
			public cmdEvent(String cmd)
			{
				com = cmd;
			}
			public void actionPerformed(ActionEvent e)
			{
				/* execute the command here*/
			}
		}
	}
	
	public void addButton()
	{
		
	}
}
