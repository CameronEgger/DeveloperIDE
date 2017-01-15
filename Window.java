import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Window extends JFrame
{
	public static ArrayList<JButton> personalized;
	private JButton buttonCreator;
	private JMenuBar menu;
	public Window()
	{
		personalized = new ArrayList<JButton>();
		menu = new JMenuBar();
		add(menu);
		
		buttonCreator = new JButton("Create Process");
		add(buttonCreator);
		
		creationEvent create = new creationEvent();
		buttonCreator.addActionListener(create);
		
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,1000);
		setVisible(true);
		setTitle("DeveloperIDE");
	}
	
	public void updateWindow()
	{
		for(int a = 0; a < personalized.size(); a++)
		{
			add(personalized.get(a));
		}
	}
	
	public class creationEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ButtonCreator create = new ButtonCreator();
		}
	}
}
