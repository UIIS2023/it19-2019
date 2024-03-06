package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Point extends Shape
{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public Point(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public Point(int x, int y, boolean selected)
	{
		this(x, y);
		setSelected(selected);
	}
	
	public Point(int x, int y, boolean selected, Color fillColor)
	{
		this(x, y, selected);
		setFillColor(fillColor);
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setX(int x) throws IllegalArgumentException
	{
		if (x > 0)
			this.x = x;
		else
			throw new IllegalArgumentException("X has to be greater value than 0!");
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setY(int y) throws IllegalArgumentException
	{
		if (y > 0)
			this.y = y;
		else
			throw new IllegalArgumentException("Y has to be greater value than 0!");
	}
	
	public double distance(int x, int y)
	{
		double dx = getX() - x;
		double dy = getY() - y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double distance(Point p)
	{
		return distance(p.getX(), p.getY());
	}
	
	@Override
	public void moveBy(int x, int y)
	{
		setX(getX() + x);
		setY(getY() + y);
	}
	
	@Override
	public String toString()
	{
		return "x: " + x + " y: " + y + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Point)
		{
			Point pom = (Point) o;
			if (x == pom.x && y == pom.y)
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Point)
			return (int) (distance(0, 0) - ((Point) o).distance(0, 0));
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		return distance(x, y) <= 5;
	}
	
	@Override
	public boolean contains(Point p)
	{
		return contains(p.getX(), p.getY());
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		g2d.setPaint(getFillColor());
		g2d.drawLine(x - 5, y, x + 5, y);
		g2d.drawLine(x, y - 5, x, y + 5);
			
		if(isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(x - 5, y - 5, 10, 10);
		}
	}

	@Override
	public void draw(Graphics g) {}
	
	@Override
	public Point clone()
	{
		return new Point(getX(), getY(), false, getFillColor());
	}
}