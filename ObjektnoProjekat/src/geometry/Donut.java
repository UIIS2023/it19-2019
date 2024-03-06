package geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle
{
	private static final long serialVersionUID = 1L;
	private int innerRadius;
	
	public Donut(int x, int y, int radius, int innerRadius)
	{
		super(x, y, radius);
		setInnerRadius(innerRadius);
	}
	
	public Donut(Point center, int radius, int innerRadius)
	{
		super(center, radius);
		setInnerRadius(innerRadius);
	}
	
	public Donut(Circle c, int innerRadius)
	{
		this(c.getCenter(), c.getRadius(), innerRadius);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected)
	{
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color fillColor)
	{
		this(center, radius, innerRadius, selected);
		this.setFillColor(fillColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color fillColor, Color borderColor)
	{
		this(center, radius, innerRadius, selected, fillColor);
		this.setBorderColor(borderColor);
	}
	
	public int getInnerRadius()
	{
		return innerRadius;
	}
	
	public void setInnerRadius(int innerRadius) throws ArithmeticException, IllegalArgumentException
	{
		if (innerRadius > 0 && innerRadius < getRadius())
			this.innerRadius = innerRadius;
		else if (innerRadius > getRadius())
			throw new ArithmeticException("R has to be greater value than IR!");
		else
			throw new IllegalArgumentException("IR has to be greater value than 0!");
	}

	public double area()
	{
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	@Override
	public String toString()
	{
		return getCenter() + " r: " + getRadius() + " ir: " + innerRadius + (getFillColor() != null ? " fc: " + getFillColor().getRGB() : "") + (getBorderColor() != null ? " bc: " + getBorderColor().getRGB() : "");
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Donut)
		{
			Donut pom = (Donut) o;
			if (super.equals(o) && innerRadius == pom.innerRadius)
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Donut)
			return (int) (this.area() - ((Donut) o).area());
		return 0;
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		return super.contains(x, y) && getCenter().distance(x, y) >= innerRadius;
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
		g2d.drawOval(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), getRadius() * 2, getRadius() * 2);
		g2d.drawOval(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, innerRadius * 2, innerRadius * 2);
		
		if (isSelected())
		{
			g2d.setPaint(Color.BLUE);
			g2d.drawRect(getCenter().getX() - 4, getCenter().getY() - 4, 8, 8);
			g2d.drawRect(getCenter().getX() - getRadius() - 4, getCenter().getY() - 4, 8, 8);
			g2d.drawRect(getCenter().getX() + getRadius() - 4, getCenter().getY() - 4, 8, 8);
			g2d.drawRect(getCenter().getX() - 4, getCenter().getY() - getRadius() - 4, 8, 8);
			g2d.drawRect(getCenter().getX() - 4, getCenter().getY() + getRadius() - 4, 8, 8);
		}
	}
	
	@Override
	public void fill(Graphics2D g2d)
	{
		Ellipse2D.Float circles = new Ellipse2D.Float();
		circles.setFrame(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(), getRadius() * 2, getRadius() * 2);
		Area donut = new  Area(circles);
		circles.setFrame(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, innerRadius * 2, innerRadius * 2);
		donut.subtract(new Area(circles));
		
		g2d.setPaint(getFillColor());
		g2d.fill(donut);
	}
	
	@Override
	public Donut clone()
	{
		return new Donut(getCenter().clone(), getRadius(), getInnerRadius(), false, getFillColor(), getBorderColor());
	}
}