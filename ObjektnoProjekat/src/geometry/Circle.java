package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle extends SurfaceShape
{
	private static final long serialVersionUID = 1L;
	private Point center;
	private int radius;
	
	public Circle(int x, int y, int radius)
	{
		setCenter(new Point(x, y));
		setRadius(radius);
	}
	
	public Circle(Point center, int radius)
	{
		setCenter(center);
		setRadius(radius);
	}
	
	public Circle(Point center, int radius, boolean selected)
	{
		this(center, radius);
		setSelected(selected);
	}
	
	public Circle(Point center, int radius, boolean selected, Color fillColor)
	{
		this(center, radius, selected);
		setFillColor(fillColor);
	}
	
	public Circle(Point center, int radius, boolean selected, Color fillColor, Color borderColor)
	{
		this(center, radius, selected, fillColor);
		setBorderColor(borderColor);
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	public void setCenter(int x, int y)
	{
		center.setX(x);
		center.setY(y);
	}
	
	public void setCenter(Point center)
	{
		this.center = center;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public void setRadius(int radius) throws IllegalArgumentException
	{
		if (radius > 0)
			this.radius = radius;
		else
			throw new IllegalArgumentException("Radius has to be greater value than 0!");
	}

	public double area()
	{
		return radius * radius * Math.PI;
	}
	
	@Override
	public void moveBy(int x, int y)
	{
		center.moveBy(x, y);
	}
	
	@Override
	public String toString()
	{
		return center + " r: " + radius + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "") + (getBorderColor() != null ? " bc: " + getBorderColor().getRGB() : "");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Circle)
		{
			Circle pom = (Circle) o;
			if (center.equals(pom.center) && radius == pom.radius)
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Circle)
			return (int) (radius - ((Circle) o).radius);
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		return center.distance(x, y) <= radius;
	}
	
	@Override
	public boolean contains(Point p)
	{
		return contains(p.getX(), p.getY());
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		fill(g2d);
		g2d.setPaint(getBorderColor());
		g2d.drawOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
		
		if (isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(center.getX() - 4, center.getY() - 4, 8, 8);
			g2d.drawRect(center.getX() - radius - 4, center.getY() - 4, 8, 8);
			g2d.drawRect(center.getX() + radius - 4, center.getY() - 4, 8, 8);
			g2d.drawRect(center.getX() - 4, center.getY() - radius - 4, 8, 8);
			g2d.drawRect(center.getX() - 4, center.getY() + radius - 4, 8, 8);
		}
	}
	
	@Override
	public void fill(Graphics2D g2d)
	{
		g2d.setPaint(getFillColor());
		g2d.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
	}

	@Override
	public void draw(Graphics g) {}
	
	@Override
	public Circle clone()
	{
		return new Circle(getCenter().clone(), getRadius(), false, getFillColor(), getBorderColor());
	}
}