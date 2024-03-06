package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Hexagon extends SurfaceShape
{
	private static final long serialVersionUID = 1L;
	private Point center;
	private int length;
	
	private int[] xp = new int[6];
	private int[] yp = new int[6];
	
	public Hexagon(int x, int y, int length)
	{
		setCenter(new Point(x, y));
		setLength(length);
		
	}
	
	public Hexagon(Point center, int length)
	{
		setCenter(center);
		setLength(length);
	}
	
	public Hexagon(Point center, int length, boolean selected)
	{
		this(center, length);
		setSelected(selected);
	}
	
	public Hexagon(Point center, int length, boolean selected, Color fillColor)
	{
		this(center, length, selected);
		setFillColor(fillColor);
	}
	
	public Hexagon(Point center, int length, boolean selected, Color fillColor, Color borderColor)
	{
		this(center, length, selected, fillColor);
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
		if (length > 0)
			setXpYp(center.getX(), center.getY());
	}
	
	public void setCenter(Point center)
	{
		this.center = center;
		if (length > 0)
			setXpYp(this.center.getX(), this.center.getY());
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void setLength(int length) throws IllegalArgumentException
	{
		if (length > 0)
		{
			this.length = length;
			setXpYp(center.getX(), center.getY());
		}
		else
			throw new IllegalArgumentException("Length has to be greater value than 0!");
	}
	
	private void setXpYp(int x, int y)
	{
		xp[0] = x - length;
		yp[0] = y;
		
		xp[1] = x - (int) (length / 2);
		yp[1] = y + (int) ((Math.sqrt(3) * length) / 2);
		
		xp[2] = x + (int) (length / 2);
		yp[2] = y + (int) ((Math.sqrt(3) * length) / 2);
		
		xp[3] = x + length;
		yp[3] = y;
		
		xp[4] = x + (int) (length / 2);
		yp[4] = y - (int) ((Math.sqrt(3) * length) / 2);
		
		xp[5] = x - (int) (length / 2);
		yp[5] = y - (int) ((Math.sqrt(3) * length) / 2);
	}
	
	public double area()
	{
		return ((3 * Math.sqrt(3)) / 2) * length * length;
	}
	
	@Override
	public void moveBy(int x, int y)
	{
		center.moveBy(x, y);
		setXpYp(x, y);
	}
	
	@Override
	public String toString()
	{
		return center + " l: " + length + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "") + (getBorderColor() != null ? " bc: " + getBorderColor().getRGB() : "");
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Hexagon)
		{
			Hexagon pom = (Hexagon) o;
			if (center.equals(pom.center) && length == pom.length)
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Hexagon)
			return (int) (this.area() - ((Hexagon) o).area());
		return 0;
	}

	@Override
	public boolean contains(int x, int y)
	{
		return new Polygon(xp, yp, 6).contains(x, y);
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
		g2d.drawPolygon(xp, yp, 6);
		
		if (isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(getCenter().getX() - 4, getCenter().getY() - 4, 8, 8);
			g2d.drawRect(xp[0] - 4, yp[0]  - 4, 8, 8);
			g2d.drawRect(xp[1] - 4, yp[1]  - 4, 8, 8);
			g2d.drawRect(xp[2] - 4, yp[2]  - 4, 8, 8);
			g2d.drawRect(xp[3] - 4, yp[3]  - 4, 8, 8);
			g2d.drawRect(xp[4] - 4, yp[4]  - 4, 8, 8);
			g2d.drawRect(xp[5] - 4, yp[5]  - 4, 8, 8);
		}
	}
	
	@Override
	public void fill(Graphics2D g2d)
	{
		g2d.setPaint(getFillColor());
		g2d.fillPolygon(xp, yp, 6);
	}

	@Override
	public void draw(Graphics g) {}
	
	@Override
	public Hexagon clone()
	{
		return new Hexagon(getCenter().clone() , getLength(), false, getFillColor(), getBorderColor());
	}
}
