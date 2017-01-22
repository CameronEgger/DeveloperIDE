import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Window
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
	private JFileChooser fileChooser;
	private JScrollPane allButtons;
	private JFrame frame;
	private boolean startup = true;
	private JMenuBar menubar;
	public Window()
	{
		frame = new JFrame();
		frame.addComponentListener(new resizeListener());
		frame.setSize(1000,1000);

		//create an instance of fileChooser to use with the open command
		fileChooser = new JFileChooser();

		//put all the menubar shiz in a different method b/c this constructor is complicated enough
		createMenuBar();


		//still need to make it so that its size is set.
		buttons = new JPanel();
		allButtons = new JScrollPane(buttons);
		//System.out.println(getHeight());
		//System.out.println(getWidth());
		allButtons.setPreferredSize(new Dimension(frame.getWidth(),50));
		allButtons.setBorder(BorderFactory.createLineBorder(Color.black));
		frame.add(allButtons);

		personalized = new ArrayList<JButton>();
		//menu = new JMenuBar();
		//add(menu);


		//this the area for editting files
		edit = new JTextArea(50,80);
		JScrollPane scroller = new JScrollPane(edit);
		frame.add(scroller);

		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		frame.setTitle("DeveloperIDE");
		startup = false;
	}
	private void createMenuBar() {
		menubar = new JMenuBar();

		//create the file menu
		JMenu file = new JMenu("File");

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new openingEvent());
		file.add(openMenuItem);

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new saveEvent());
		file.add(saveMenuItem);

		menubar.add(file);

		//create the process menu
		JMenu process = new JMenu("Processes");

		JMenuItem newProcessItem = new JMenuItem("New process");
		newProcessItem.addActionListener(new creationEvent());
		process.add(newProcessItem);

		menubar.add(process);

		frame.setJMenuBar(menubar);
  }

	public void updateWindow()
	{
		for(int a = 0; a < personalized.size(); a++)
		{
			System.out.println("added");
			buttons.add(personalized.get(a));
		}
		frame.repaint();
		frame.setVisible(true);
		System.out.println("Repainted");
	}

	public void resizeAll()
	{
		System.out.println("resized");
		allButtons.setPreferredSize(new Dimension(frame.getWidth(),50));
		System.out.println("Do i get here");
		//frame.add(allButtons);
		frame.repaint();
	}

	public class creationEvent implements ActionListener
	{
		//triggered when user clicks on "create process" button
		public void actionPerformed(ActionEvent e)
		{
			ButtonCreator create = new ButtonCreator();
		}
	}

	public class openingEvent implements ActionListener
	{
		//triggered when user clicks open file button
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String read = "";
				int returnCode = fileChooser.showOpenDialog(null);

				if(returnCode == JFileChooser.APPROVE_OPTION){
					curr = fileChooser.getSelectedFile();
					Scanner file = new Scanner(curr);
					while(file.hasNextLine())
					{
						read += file.nextLine() + "\n";
					}
					edit.setText(read);
					Execute.update();
				}

			}
			catch(Exception dagnabit)
			{
				dagnabit.printStackTrace();
			}
		}
	}

	public class saveEvent implements ActionListener
	{
		//triggered when user hits save button
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				//curr should be a File instance of the currently opened file
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

	private class resizeListener implements ComponentListener{
		public void componentHidden(ComponentEvent arg0)
		{
		}
		public void componentMoved(ComponentEvent arg0)
		{
		}
		public void componentResized(ComponentEvent arg0)
		{
			if(!startup)
			{
				System.out.println(frame.getWidth());
				resizeAll();
			}

		}
		public void componentShown(ComponentEvent arg0)
		{

		}
	}
}
