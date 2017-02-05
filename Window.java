import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Window
{
	public static ArrayList<UserProcess> personalized;
	public static ArrayList<Button> fileButtons;
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
	private JPanel files;
	private JScrollPane allFiles;
	public Window()
	{
		fileButtons = new ArrayList<Button>();
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

		personalized = new ArrayList<UserProcess>();
		//menu = new JMenuBar();
		//add(menu);
		files = new JPanel();
		allFiles = new JScrollPane(files);
		allFiles.setPreferredSize(new Dimension(900,50));
		frame.add(allFiles);

		//this the area for editting files
		edit = new JTextArea(40,80);
		JScrollPane scroller = new JScrollPane(edit);
		frame.add(scroller);
		
		phials = new HashMap<String, String>();
		
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		frame.setTitle("DeveloperIDE");
		startup = false;
	}

	public void populateFromFiles(){
		//here we're deserializing the files in the process directory
		new File("processes").mkdir();
		File dir = new File(UserProcess.class.getProtectionDomain().getCodeSource().getLocation().getFile()+"processes/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				try{
					ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(child));
					UserProcess process = (UserProcess) objIn.readObject();
					process.init();
					System.out.println("took a command from a file!");
					// System.out.println("found a file, i'll work out how to eat it laters");
				}
				catch(Exception rats){
					rats.printStackTrace();
				}
			}
		}
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

		JMenuItem settingsProcessItem = new JMenuItem("Settings");
		settingsProcessItem.addActionListener(new settingsEvent());
		process.add(settingsProcessItem);

		menubar.add(process);

		frame.setJMenuBar(menubar);
  }

	public void updateWindow()
	{
		for(int a = 0; a < personalized.size(); a++)
		{
			//button is an instance of UserProcess
			UserProcess button = personalized.get(a);
			//if the button is enabled but not drawn yet, we want to add it to the button list
			if(button.isEnabled() && !button.isDrawn()){
				// System.out.println("added "+button.toString());
				// System.out.println(button.getButton());
				buttons.add(button.getButton());
				button.setDrawn(true);
			}
			//if the button is disabled but still drawn, we want to make sure it isn't drawn
			else if(!button.isEnabled() && button.isDrawn()){

				button.setDrawn(false);
				//this should take the button out of the jpanel
				buttons.remove(button.getButton());
				buttons.revalidate();
				buttons.repaint();
				// System.out.println("removed "+button.toString());

			}
				// System.out.println("added");

		}
		for(int a = 0; a < fileButtons.size(); a++)
		{
			files.add(fileButtons.get(a));
		}
		frame.revalidate();
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
	private	HashMap<String, String> phials;
	private String currentlyDisplayed;
	public class openingEvent implements ActionListener
	{
		
		//triggered when user clicks open file button
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String read = "";
				int returnCode = fileChooser.showOpenDialog(null);

				if(returnCode == JFileChooser.APPROVE_OPTION)
				{
					curr = fileChooser.getSelectedFile();
					
					Scanner file = new Scanner(curr);
					if(!phials.containsKey(curr.toString()))
					{
						while(file.hasNextLine())
						{
							read += file.nextLine() + "\n";
						}
						//resetting each time so that the file does not auto save when a new file is opened.
						//otherwise all progress would be lost if i just reread the file.
						if(edit.getText().equals(""))
						{
							edit.setText(read);
							phials.put(curr.toString(), read);
						}
						else
						{
							phials.put(currentlyDisplayed, edit.getText());
							edit.setText(read);
							phials.put(curr.toString(), read);
						}
						Button fileOpen = new Button(curr.toString().substring(curr.toString().lastIndexOf("\\")+1));
						fileOpener phial = new fileOpener(curr.toString());
						fileOpen.addActionListener(phial);
						fileButtons.add(fileOpen);
						currentlyDisplayed = curr.toString();
						Execute.update();
					}
					else
					{
						phials.put(currentlyDisplayed, edit.getText());
						currentlyDisplayed = curr.toString();
						edit.setText(phials.get(curr.toString()));
					}
					
				}

			}
			catch(Exception dagnabit)
			{
				dagnabit.printStackTrace();
			}
		}
	}
	//the buttons with the file names on them
	public class fileOpener implements ActionListener
	{
		private String fileName;
		public fileOpener(String name)
		{
			fileName = name;
		}
		public void actionPerformed(ActionEvent e)
		{
			if(edit.getText().equals(""))
			{
				System.out.println("This shouldn't happen");
				edit.setText(phials.get(fileName));
			}
			else
			{
				System.out.println(currentlyDisplayed + " " + fileName);
				phials.put(currentlyDisplayed, edit.getText());
				currentlyDisplayed = fileName;
				edit.setText(phials.get(fileName));
				curr = new File(fileName);
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

	//for the settings button from under the process menu
	public class settingsEvent implements ActionListener{
		public void actionPerformed(ActionEvent e){
			SettingsMenu settings = new SettingsMenu();
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
