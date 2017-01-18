import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Window extends JFrame
{
	public static ArrayList<JButton> personalized;
	private JTextArea edit;
	private JButton buttonCreator;
	private JButton open;
	private JTextField toOpen;
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
		
		toOpen = new JTextField(25);
		add(toOpen);
		
		open = new JButton("Open File");
		add(open);
		
		openingEvent opener = new openingEvent();
		open.addActionListener(opener);
		
		edit = new JTextArea(50,80);
		JScrollPane scroller = new JScrollPane(edit);
		add(scroller);
		
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
			System.out.println("added");
			add(personalized.get(a));
		}
		repaint();
		setVisible(true);
		System.out.println("Repainted");
	}
	
	public class creationEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ButtonCreator create = new ButtonCreator();
		}
	}
	
	public class openingEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String read = "";
				Scanner file = new Scanner(new File(toOpen.getText()));
				while(file.hasNextLine())
				{
					read += file.nextLine() + "\n";
				}
				edit.setText(read);
				Execute.update();
			}
			catch(Exception E)
			{
				System.out.println("File not found");
			}
		}
	}
}
