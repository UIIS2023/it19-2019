package geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class SurfaceShape extends Shape implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Color borderColor;
	
	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}
	
	public abstract void fill(Graphics2D g2d);
}