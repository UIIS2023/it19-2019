package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import prototype.IPrototype;

public abstract class Shape implements IMoveable, IPrototype, Comparable<Object>, Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean selected;
	private Color fillColor;
	
	public Shape()
	{
		
	}
	
	public Shape(boolean selected)
	{
		setSelected(selected);
	}
	
	public Shape(boolean selected, Color fillColor)
	{
		this(selected);
		setFillColor(fillColor);
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public Color getFillColor()
	{
		return fillColor;
	}
	
	public void setFillColor(Color fillColor)
	{
		this.fillColor = fillColor;
	}
	
	public abstract boolean contains(int x, int y);
	
	public abstract boolean contains(Point p);
	
	public abstract void draw(Graphics2D g2d);
	
	public abstract void draw(Graphics g);
	
	@Override
	public Shape clone()
	{
		try
		{
			return (Shape) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}