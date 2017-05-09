package RA;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

@SuppressWarnings("serial")
public class Window extends JPanel {
	Aspect[] aspects = new Aspect[23];
	Category[] categories = new Category[4];
	String title;
	static int ROWS = 13;
	
	public Window(String title) throws Exception {
		for (int i = 0; i < 4; i++)
			categories[i] = new Category();
		for (int i = 0; i < 23; i++)
			aspects[i] = new Aspect();
		this.title = title;
		addMouseListener(new MouseListener());
		instantiate();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int boxHeight = getHeight() / ROWS;
		int boxWidth = getWidth() / 3;
		int fSize = 35;
		double prop = (double) getHeight()/getWidth();
		boolean p = prop > .9;
		final int b = 10;
		String name;
		
		//draw catergory boxes
		g.setFont(new Font("Century Gothic", Font.BOLD, 32));
		for (Category c : categories) {
			c.paintMe(g, b, boxHeight, getWidth());
		}

		//decide font size
		g.setFont(new Font("Calibri", Font.PLAIN, fSize));
		if(p){
			while (g.getFontMetrics().stringWidth("Click on each box to change its color. Color indicates Maturity level. Click "
					+ "\"SAVE\" when finished") > getWidth() - b) {
				fSize--;
				g.setFont(new Font("Calibri", Font.PLAIN, fSize));
			}
			while(g.getFontMetrics().getHeight() * 2 > boxHeight - b){
				fSize--;
				g.setFont(new Font("Calibri", Font.PLAIN, fSize));
			}
		}else{
			while (g.getFontMetrics().stringWidth("Integration With Business Applications") > boxWidth - b) {
				fSize--;
				g.setFont(new Font("Calibri", Font.PLAIN, fSize));
			}
			while(g.getFontMetrics().getHeight() > boxHeight - b){
				fSize--;
				g.setFont(new Font("Calibri", Font.PLAIN, fSize));
			}
		}
		
		//draw aspect boxes
		for (Aspect a : aspects) {
			a.paintMe(g, b, boxWidth, boxHeight, p);
		}
		
		//Draw the instructions
		name = "Click on each box to change its color. Color indicates Maturity level. Click "
				+ "\"SAVE\" when finished";
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
				(ROWS - 1) * boxHeight + g.getFontMetrics().getHeight() / 4 + boxHeight / 4);
		name = "Red = Immature       Yellow = Somewhat Mature       Green = Mature       Gray = "
				+ " Out of Scope";
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
				(int) ((ROWS - .5) * boxHeight + g.getFontMetrics().getHeight() / 4 + boxHeight / 4));
		
		//Draw the SAVE Button
		g.setColor(new Color(0, 206, 209));
		g.fillRoundRect(b / 2 + boxWidth * 2, 11 * boxHeight + b / 2, boxWidth
				- b, boxHeight - b, boxHeight / 3, boxHeight / 3);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC,
				(int) (fSize * 1.5)));
		g.drawString("SAVE", 2 * boxWidth
				+ (boxWidth - g.getFontMetrics().stringWidth("SAVE")) / 2, 11
				* boxHeight + g.getFontMetrics().getHeight() / 4 + boxHeight
				/ 2);
	}

	void instantiate() throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(
				"src/RA/Defaults/DefaultCategories.xml"));
		NodeList nList = doc.getElementsByTagName("category");
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			Element e = (Element) n;
			categories[i].setName(e.getElementsByTagName("name").item(0)
					.getTextContent());
			categories[i].setY(Integer.parseInt(e.getElementsByTagName("y")
					.item(0).getTextContent()));
		}
		doc = builder.parse(new File("src/RA/xml/" + title + ".xml"));
		nList = doc.getElementsByTagName("aspect");
		for (int i = 0; i < nList.getLength(); i++) {
			Node n = nList.item(i);
			Element e = (Element) n;
			aspects[i].setName(e.getElementsByTagName("name").item(0)
					.getTextContent());
			aspects[i].setColor(Integer.parseInt(e
					.getElementsByTagName("color").item(0).getTextContent()));
			aspects[i].setY(Integer.parseInt(e.getElementsByTagName("y")
					.item(0).getTextContent()));
			aspects[i].setX(Integer.parseInt(e.getElementsByTagName("x")
					.item(0).getTextContent()));
		}
	}

	public void save() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File("src/RA/xml/" + title + ".xml"));
		NodeList aspectList = doc.getElementsByTagName("aspect");
		for (int i = 0; i < aspectList.getLength(); i++) {
			Node aspect = aspectList.item(i);
			NodeList children = aspect.getChildNodes();
			Node color = children.item(3);
			color.setTextContent("" + aspects[i].getColorNum());
		}
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		JFrame j = null;
		FileDialog me = null;
		if (title == ".DefaultAspects") {
			j = new JFrame();
			me = new FileDialog(j, "Save as", FileDialog.SAVE);
			me.setDirectory("./src/RA/xml");
			me.setVisible(true);
			title = me.getFile();
		}
		StreamResult result = new StreamResult(new File("src/RA/xml/" + title
				+ ".xml"));
		transformer.transform(source, result);
	}

	public class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int x = (int) Math.floor((double) e.getX() * 3 / getWidth());
			int y = (int) Math.floor((double) e.getY() * ROWS / getHeight());
			for (Aspect a : aspects) {
				if (a.getX() == x && a.getY() == y)
					a.colorUp();
			}
			repaint();
			if (x == 2 && y == ROWS - 2) {
				try {
					save();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
