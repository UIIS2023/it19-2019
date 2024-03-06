package adapter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import hexagon.Hexagon;
import prototype.IPrototype;
import geometry.Point;
import geometry.SurfaceShape;

public class HexagonAdapter extends SurfaceShape implements IShape, IPrototype
{
	private static final long serialVersionUID = 1L;
	private Hexagon hexagon;
	private Point center;
	
	public HexagonAdapter(Hexagon hexagon)
	{
		this.hexagon = hexagon;
	}

	public HexagonAdapter(Point center, int radius, boolean selected, Color innerColor, Color color)
	{
		this.center = center;
		hexagon = new Hexagon(center.getX(), center.getY(), radius);
		hexagon.setSelected(selected);
		hexagon.setAreaColor(innerColor);
		hexagon.setBorderColor(color); 
	}

	public int getX()
	{
		return hexagon.getX();
	}

	public void setX(int x)
	{
		hexagon.setX(x);
	}
	
	public int getY()
	{
		return hexagon.getY();
	}
	
	public void setY(int y)
	{
		hexagon.setY(y);
	}

	public int getLength()
	{
		return hexagon.getR();
	}
	
	public void setLength(int l)
	{
		hexagon.setR(l);
	}
	
	public String toString()
	{
		return center + " l: " + hexagon.getR() + (hexagon.getAreaColor() != null ? " fc: " + hexagon.getAreaColor().getRGB() : "") + (hexagon.getBorderColor() != null ? " bc: " + hexagon.getBorderColor().getRGB() : "");
	}
	
	@Override
	public Color getFillColor()
	{
		return hexagon.getBorderColor();
	}
	
	@Override
	public void setFillColor(Color fillColor)
	{
		hexagon.setBorderColor(fillColor);
	}

	@Override
	public Color getBorderColor()
	{
		return hexagon.getAreaColor();
	}
	
	@Override
	public void setBorderColor(Color borderColor)
	{
		hexagon.setAreaColor(borderColor);
	}
	
	public void draw(Graphics g)
	{
		hexagon.paint(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.hexagon.getX() - 3, this.hexagon.getY() - 3, 6, 6);
			g.drawRect(this.hexagon.getX() - this.hexagon.getR() / 2 - 3, (int) (this.hexagon.getY() - this.hexagon.getR() * Math.sqrt(3) / 2 - 3), 6, 6);
			g.drawRect(this.hexagon.getX() + this.hexagon.getR() / 2 - 3, (int) (this.hexagon.getY() - this.hexagon.getR() * Math.sqrt(3) / 2 - 3), 6, 6);
			g.drawRect(this.hexagon.getX() + this.hexagon.getR() - 3, this.hexagon.getY() - 3, 6, 6);
			g.drawRect(this.hexagon.getX() + this.hexagon.getR() / 2 - 3, (int) (this.hexagon.getY() + this.hexagon.getR() * Math.sqrt(3) / 2 - 3), 6, 6);
			g.drawRect(this.hexagon.getX() - this.hexagon.getR() / 2 - 3, (int) (this.hexagon.getY() + this.hexagon.getR() * Math.sqrt(3) / 2 - 3), 6, 6);
			g.drawRect(this.hexagon.getX() - this.hexagon.getR() - 3, this.hexagon.getY() - 3, 6, 6);
		}
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		
	}
	
	@Override
	public void fill(Graphics2D g2d)
	{
		
	}

	@Override
	public boolean contains(int x, int y)
	{
		return hexagon.doesContain(x, y);
	}

	@Override
	public void moveBy(int byX, int byY)
	{
		hexagon.setX(getX() + byX);
		hexagon.setY(getY() + byY);
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Hexagon)
			return (hexagon.getR() - ((Hexagon) o).getR());
		return 0;
	}

	public double area()
	{
		return 3 * Math.sqrt(3) / 2 * hexagon.getR() * hexagon.getR();
	}

	@Override
	public boolean contains(Point p)
	{
		return contains(p.getX(), p.getY());
	}
	
	@Override
	public HexagonAdapter clone()
	{
		return new HexagonAdapter(center, getLength(), false, getBorderColor(), getFillColor());
	}
}
