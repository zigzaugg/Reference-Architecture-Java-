package RA;

import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.*;

public class Model {
	static int HEIGHT = 780;
	static int WIDTH = 900;

	public static void main(String[] args) throws Exception{
		try{
			String title = null;
			ImageIcon opin = new ImageIcon("RA/Defaults/Opin.png");
		
			Object[] options = {"New", "Open"};
			int n = JOptionPane.showOptionDialog(null, "Would you like to open a \ndoc or start a new doc?", 
					"File Finder", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, 
					opin, options, options[0]);
			
			if (n == 0)
				title = ".DefaultAspects";
			else if(n == -1)
				System.exit(0);
			else{
				JFrame j = new JFrame();
				FileDialog me = new FileDialog(j, "Choose file", FileDialog.LOAD);
				me.setDirectory("./src/RA/xml");
				me.setVisible(true);
				title = me.getFile();
				if(title == null){
					title = ".DefaultAspects.xml";
				}
				title = title.substring(0, title.length()-4);
			}
			if (title == null)
				System.exit(0);
			JFrame gui = new JFrame();
			gui.setTitle("Reference Architecture for " + title);
			title.replaceAll(" ", "\\ ");
			gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.setSize(WIDTH, HEIGHT + 22);
			Container pane = gui.getContentPane();
			pane.setLayout(new GridLayout(1, 1));
			Window window = new Window(title);
			window.setBackground(new Color(224,255,255));
			pane.add(window);
			gui.setVisible(true);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
}
