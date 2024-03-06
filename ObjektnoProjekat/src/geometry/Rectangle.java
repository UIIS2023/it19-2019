package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Rectangle extends SurfaceShape
{
	private static final long serialVersionUID = 1L;
	private Point upperLeftPoint;
	private int width;
	private int height;
	
	public Rectangle(int x, int y, int width, int height)
	{
		setUpperLeftPoint(new Point(x, y));
		setWidth(width);
		setHeight(height);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height)
	{
		setUpperLeftPoint(upperLeftPoint);
		setWidth(width);
		setHeight(height);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected)
	{
		this(upperLeftPoint, width, height);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color fillColor)
	{
		this(upperLeftPoint, width, height, selected);
		setFillColor(fillColor);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color fillColor, Color borderColor)
	{
		this(upperLeftPoint, width, height, selected, fillColor);
		setBorderColor(borderColor);
	}
	
	public Point getUpperLeftPoint()
	{
		return upperLeftPoint;
	}
	
	public void setUpperLeftPoint(int x, int y)
	{
		upperLeftPoint.setX(x);
		upperLeftPoint.setY(y);
	}
	
	public void setUpperLeftPoint(Point upperLeftPoint)
	{
		this.upperLeftPoint = upperLeftPoint;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width) throws IllegalArgumentException
	{
		if (width > 0)
			this.width = width;
		else
			throw new IllegalArgumentException("Width has to be greater value than 0!");
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height) throws IllegalArgumentException
	{
		if (height > 0)
			this.height = height;
		else
			throw new IllegalArgumentException("Height has to be greater value than 0!");
	}

	public int area()
	{
		return width * height;
	}
	
	@Override
	public void moveBy(int x, int y)
	{
		upperLeftPoint.moveBy(x, y);
	}
	
	@Override
	public String toString()
	{
		return upperLeftPoint + " w: " + width + " h: " + height + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "") + (getBorderColor() != null ? " bc: " + getBorderColor().getRGB() : "");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Rectangle)
		{
			Rectangle pom = (Rectangle) o;
			if (upperLeftPoint.equals(pom.upperLeftPoint) && width == pom.width && height == pom.height)
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Rectangle)
			return (int) (area() - ((Rectangle) o).area());
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		if (upperLeftPoint.getX() <= x && 
			upperLeftPoint.getY() <= y &&
			x <= upperLeftPoint.getX() + width &&
			y <= upperLeftPoint.getY() + height)
			return true;
		return false;
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
		g2d.setColor(getBorderColor());
		g2d.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		
		if (isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(upperLeftPoint.getX() - 4, upperLeftPoint.getY() - 4, 8, 8);
			g2d.drawRect(upperLeftPoint.getX() + width - 4, upperLeftPoint.getY() - 4, 8, 8);
			g2d.drawRect(upperLeftPoint.getX() - 4, upperLeftPoint.getY() + height - 4, 8, 8);
			g2d.drawRect(upperLeftPoint.getX() + width - 4, upperLeftPoint.getY() + height - 4, 8, 8);
		}
	}
	
	@Override
	public void fill(Graphics2D g2d)
	{
		g2d.setPaint(getFillColor());
		g2d.fillRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
	}

	@Override
	public void draw(Graphics g) {}
	
	@Override
	public Rectangle clone()
	{
		return new Rectangle(getUpperLeftPoint().clone(), getWidth(), getHeight(), false, getFillColor(), getBorderColor());
	}
}