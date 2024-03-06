package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape
{
	private static final long serialVersionUID = 1L;
	private Point startPoint;
	private Point endPoint;
	
	public Line(int x1, int y1, int x2, int y2)
	{
		setStartPoint(new Point(x1, y1));
		setEndPoint(new Point(x2, y2));
	}
	
	public Line(Point startPoint, Point endPoint)
	{
		setStartPoint(startPoint);
		setEndPoint(endPoint);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected)
	{
		this(startPoint, endPoint);
		setSelected(selected);
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected, Color fillColor)
	{
		this(startPoint, endPoint, selected);
		setFillColor(fillColor);
	}
	
	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public void setStartPoint(int x, int y)
	{
		startPoint.setX(x);
		startPoint.setY(y);
	}
	
	public void setStartPoint(Point startPoint)
	{
		this.startPoint = startPoint;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
	
	public void setEndPoint(int x, int y)
	{
		endPoint.setX(x);
		endPoint.setY(y);
	}
	
	public void setEndPoint(Point endPoint)
	{
		this.endPoint = endPoint;
	}
	
	public double length()
	{
		return startPoint.distance(endPoint);
	}
	
	public Point middleOfLine()
	{
		return new Point((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2);
	}
	
	@Override
	public void moveBy(int x, int y)
	{
		startPoint.moveBy(x, y);
		endPoint.moveBy(x, y);
	}
	
	@Override
	public String toString()
	{
		return "start:: " + startPoint + " end:: " + endPoint + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Line)
		{
			Line pom = (Line) o;
			if (startPoint.equals(pom.startPoint) && endPoint.equals(pom.endPoint))
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Line)
			return (int) (length() - ((Line) o).length());
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		if ((startPoint.distance(x, y) + endPoint.distance(x, y)) - length() <= 1.5)
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
		g2d.setPaint(getFillColor());
		g2d.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		
		if (isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(startPoint.getX() - 4, startPoint.getY() - 4, 8, 8);
			g2d.drawRect(endPoint.getX() - 4, endPoint.getY() - 4, 8, 8);
			g2d.drawRect(middleOfLine().getX() - 4, middleOfLine().getY() - 4, 8, 8);
		}
	}

	@Override
	public void draw(Graphics g) {}
	
	@Override
	public Line clone()
	{
		return new Line(getStartPoint().clone(), getEndPoint().clone(), false, getFillColor());
	}
}