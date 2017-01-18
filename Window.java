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
	private JPanel buttons;
	private FileWriter fw;
	private File curr;
	private JButton save;
	//private JMenuBar menu;
	public Window()
	{
		setSize(1000,1000);
		//still need to make it so that its size is set.
		buttons = new JPanel();
		JScrollPane allButtons = new JScrollPane(buttons);
		//System.out.println(getHeight());
		//System.out.println(getWidth());
		allButtons.setPreferredSize(new Dimension(getWidth(),50));
		allButtons.setBorder(BorderFactory.createLineBorder(Color.black));
		add(allButtons);
		
		personalized = new ArrayList<JButton>();
		//menu = new JMenuBar();
		//add(menu);
		
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
		
		save = new JButton("Save");
		add(save);
		
		saveEvent sa = new saveEvent();
		save.addActionListener(sa);
		
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		setTitle("DeveloperIDE");
	}
	
	public void updateWindow()
	{
		for(int a = 0; a < personalized.size(); a++)
		{
			System.out.println("added");
			buttons.add(personalized.get(a));
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
				curr = new File(toOpen.getText());
				Scanner file = new Scanner(curr);
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
	
	public class saveEvent implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try 
			{
				fw = new FileWriter(curr, false);
				fw.write(edit.getText());
				fw.close();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
}
