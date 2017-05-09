package RA;
import java.awt.Color;
import java.awt.Graphics;


public class Category {
	String name;
	int y = 0;
	int id;

	public void setName(String name) {
		this.name = name;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public int getY() {
		return y;
	}
	
	public void paintMe(Graphics g, int b, int boxHeight, int width){
		g.setColor(new Color(0, 206, 209));
		g.fillRoundRect(b / 2, y * boxHeight + b / 2, width - b,
			boxHeight - b, boxHeight / 3, boxHeight / 3);
		g.setColor(Color.black);
		g.drawString(name,(width - g.getFontMetrics().stringWidth(name)) / 2, y
			* boxHeight + g.getFontMetrics().getHeight() / 4+ boxHeight / 2);
	}
}
