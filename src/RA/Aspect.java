package RA;

import java.awt.Color;
import java.awt.Graphics;

public class Aspect {
	String name;
	int x;
	int y;
	int color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		switch (color) {
		case 1:
			return new Color(144, 238, 144);
		case 2:
			return new Color(250, 247, 60);
		case 3:
			return new Color(255, 80, 80);
		case 4:
			return new Color(211, 211, 211);
		default:
			return new Color(255, 255, 255);
		}
	}

	public int getColorNum() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void colorUp() {
		if (color > 4)
			color = 1;
		else
			color++;
	}

	public void paintMe(Graphics g, int b, int boxWidth, int boxHeight, boolean p) {
		g.setColor(getColor());
		g.fillRoundRect(b / 2 + boxWidth * x, y * boxHeight + b / 2, boxWidth - b,
				boxHeight - b, boxHeight / 3, boxHeight / 3);
		g.setColor(Color.black);
		if(name.contains("BREAK") && p){
			String[] split = name.split("BREAK");
			g.drawString(split[0], x * boxWidth + (boxWidth - g.getFontMetrics().stringWidth(split[0])) / 2, y 
					* boxHeight + g.getFontMetrics().getHeight() / 4 + boxHeight / 4 + b / 2);
			g.drawString(split[1], x * boxWidth + (boxWidth - g.getFontMetrics().stringWidth(split[1])) / 2, y 
					* boxHeight + g.getFontMetrics().getHeight() / 4 + 3 * boxHeight / 4 - b / 2);
		} else{
			String name2 = name.replaceAll("BREAK", " ");
			g.drawString(name2, x * boxWidth + (boxWidth - g.getFontMetrics().stringWidth(name2)) / 2, y
					* boxHeight + g.getFontMetrics().getHeight() / 4 + boxHeight / 2);
		}
	}
}
