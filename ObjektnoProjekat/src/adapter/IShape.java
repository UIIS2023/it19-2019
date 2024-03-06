package adapter;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IShape
{
	public int getX();
	public int getY();
	
	public void setX(int x);
	public void setY(int y);
	
	public int getLength();
	public void setLength(int l);
	
	boolean contains(int x, int y);
	
	Color getFillColor();
	void setFillColor(Color c);
	
	Color getBorderColor();
	void setBorderColor(Color c);
	
	void draw(Graphics2D g2d);
	public void fill(Graphics2D g2d);
}
