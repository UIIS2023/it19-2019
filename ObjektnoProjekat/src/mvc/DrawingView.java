package mvc;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import adapter.HexagonAdapter;
import geometry.Shape;

public class DrawingView extends JPanel
{
	private static final long serialVersionUID = 1L;
	private DrawingModel drwMdl = new DrawingModel();
	
	public DrawingView() {  }
	
	@Override
	public void paintComponent(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		for(Shape s : drwMdl.getShapes())
		{
			if(s instanceof HexagonAdapter)
				s.draw(g);
			else
				s.draw(g2d);
		}
			
		g2d.dispose();
	}
	
	public void setDrwMdl(DrawingModel drwMdl)
	{
		this.drwMdl = drwMdl;
	}
}
