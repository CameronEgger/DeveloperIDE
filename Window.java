import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Window extends JFrame
{
	public Window()
	{
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(2000,2000);
		setVisible(true);
		setTitle("DeveloperIDE");
	}
}
