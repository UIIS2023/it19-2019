package mvc;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import adapter.HexagonAdapter;
import cmd.*;
import dlg.*;
import geometry.*;
import strategy.*;

enum ModeAddSel { SELECT, POINT, LINE, CIRCLE, RECT, DONUT, HEXA, HEXAA };

public class DrawingController
{
	private DrawingModel drwMdl;
	private DrawingFrame drwFrm;
	
	private Point[] click = new Point[2];
	private Color[] color = { Color.BLUE, Color.RED };
	
	private ModeAddSel modeAddSel = ModeAddSel.POINT;
	
	private ICommand cmd = null;
	
	public DrawingController(DrawingModel drwMdl, DrawingFrame drawingFrame)
	{
		this.drwMdl = drwMdl;
		this.drwFrm = drawingFrame;
		this.drwMdl.addPropertyChangeListener(this.drwFrm);
	}
	
	public void click()
	{
		if (modeAddSel != null)
		{
			switch (modeAddSel)
			{
				case SELECT:
					findSelected();
					break;
				case POINT:
					addPoint();
					break;
				case LINE:
					addLine();
					break;
				case CIRCLE:
					addCircle();
					break;
				case RECT:
					addRect();
					break;
				case DONUT:
					addDonut();
					break;
				case HEXA:
					addHexagon();
					break;
				case HEXAA:
					addHexaAdapter();
					break;
			}
			drwFrm.getDrwView().repaint();		
		}
		else
			drwFrm.info("You need to select a shape to add.", "How To Start Drawing");
	}
	
	public void findSelected()
	{
		boolean ok = false;
		for (int i = drwMdl.getShapesSize() - 1; i >= 0; i--)
			if (drwMdl.getShape(i).contains(click[0]))
			{
				if (!drwMdl.isShapeSelected(i))
				{
					drwMdl.setShapeSelected(i, true);
					drwMdl.addSelected(i);
				}
				if (!ok)
					ok = true;
				break;
			}
		
		if (!ok)
		{
			for (int i = 0; i < drwMdl.getShapesSize(); i++)
				if (drwMdl.getShape(i).isSelected())
					drwMdl.setShapeSelected(i, false);
			drwMdl.clearSelected();
		}
	}
	
	public void modify()
	{
		Shape selected = drwMdl.getShape(drwMdl.getSelected(0));
		if (selected instanceof Point)
			modPoint();
		else if (selected instanceof Line)
			modLine();
		else if (selected instanceof Rectangle)
			modRect();
		else if (selected instanceof Donut)
			modDonut();
		else if (selected instanceof Circle)
			modCircle();
		else if (selected instanceof Hexagon)
			modHexagon();
		else if (selected instanceof HexagonAdapter)
			modHexaAdapter();
		drwFrm.getDrwView().repaint();
	}
	
	public void delete()
	{
		if (drwFrm.confirm("Are you sure?", "Confirm Deletion") == JOptionPane.YES_OPTION)
		{
			Shape deleted;
			for (int i = 0; i < drwMdl.getSelectedSize(); i++)
			{
				deleted = drwMdl.getShape(drwMdl.getSelected(i));
				if (deleted instanceof Point)
					cmd = new RemovePointCmd((Point) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof Line)
					cmd = new RemoveLineCmd((Line) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof Rectangle)
					cmd = new RemoveRectangleCmd((Rectangle) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof Donut)
					cmd = new RemoveDonutCmd((Donut) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof Circle)
					cmd = new RemoveCircleCmd((Circle) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof Hexagon)
					cmd = new RemoveHexagonCmd((Hexagon) deleted, drwMdl, drwMdl.getSelected(i));
				else if (deleted instanceof HexagonAdapter)
					cmd = new RemoveHexagonAdapterCmd((HexagonAdapter) deleted, drwMdl, drwMdl.getSelected(i));
				execute(cmd);
			}
			drwMdl.clearSelected();
			drwFrm.getDrwView().repaint();
		}
	}
	
	public void clear()
	{
		if (drwFrm.confirm("Are you sure?", "Clear Everything") == JOptionPane.YES_OPTION)
		{
				drwMdl.clearShapes();
				drwMdl.clearSelected();
				drwMdl.clearCmds();
				drwFrm.clearLog();
				drwMdl.setStep(-1);
				drwFrm.getDrwView().repaint();
		}
	}
	
	public void clearSelected()
	{
		drwMdl.clearSelected();
		drwFrm.getDrwView().repaint();
	}
	
	private void addPoint()
	{
		DlgPoint dlgPoint = new DlgPoint();
		dlgPoint.setTitle("Add Point");
		if (click[0] != null)
			dlgPoint.setTxtXY(click[0].getX(), click[0].getY());
		dlgPoint.setFillColor(color[0]);
		dlgPoint.setVisible(true);
		if (dlgPoint.isOk())
		{
			cmd = new AddPointCmd(dlgPoint.getP(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgPoint.dispose();
	}
	
	private void addLine()
	{
		DlgLine dlgLine = new DlgLine();
		dlgLine.setTitle("Add Line");
		if (click[0] != null)
			dlgLine.setTxtXY1(click[0].getX(), click[0].getY());
		if (click[1] != null)
			dlgLine.setTxtXY2(click[1].getX(), click[1].getY());
		dlgLine.setFillColor(color[0]);
		dlgLine.setVisible(true);
		if (dlgLine.isOk())
		{
			cmd = new AddLineCmd(dlgLine.getL(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgLine.dispose();
	}
	
	private void addCircle()
	{
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.setTitle("Add Circle");
		if (click[0] != null)
			dlgCircle.setTxtXY(click[0].getX(), click[0].getY());
		dlgCircle.setFillColor(color[0]);
		dlgCircle.setBorderColor(color[1]);
		dlgCircle.setVisible(true);
		if (dlgCircle.isOk())
		{
			cmd = new AddCircleCmd(dlgCircle.getC(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgCircle.dispose();
	}
	
	private void addRect()
	{
		DlgRectangle dlgRect = new DlgRectangle();
		dlgRect.setTitle("Add Rectangle");
		if (click[0] != null)
			dlgRect.setTxtXY(click[0].getX(), click[0].getY());
		dlgRect.setFillColor(color[0]);
		dlgRect.setBorderColor(color[1]);
		dlgRect.setVisible(true);
		if (dlgRect.isOk())
		{
			cmd = new AddRectangleCmd(dlgRect.getR(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgRect.dispose();
	}
	
	private void addDonut()
	{
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.setTitle("Add Donut");
		if (click[0] != null)
			dlgDonut.setTxtXY(click[0].getX(), click[0].getY());
		dlgDonut.setFillColor(color[0]);
		dlgDonut.setBorderColor(color[1]);
		dlgDonut.setVisible(true);
		if (dlgDonut.isOk())
		{
			cmd = new AddDonutCmd(dlgDonut.getD(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgDonut.dispose();
	}
	
	private void addHexagon()
	{
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.setTitle("Add Hexagon");
		if (click[0] != null)
			dlgHexagon.setTxtXY(click[0].getX(), click[0].getY());
		dlgHexagon.setFillColor(color[0]);
		dlgHexagon.setBorderColor(color[1]);
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isOk())
		{
			cmd = new AddHexagonCmd(dlgHexagon.getH(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgHexagon.dispose();
	}
	
	private void addHexaAdapter()
	{
		DlgHexagon dlgHexaAdapter = new DlgHexagon();
		dlgHexaAdapter.setTitle("Add HexagonAdapter");
		if (click[0] != null)
			dlgHexaAdapter.setTxtXY(click[0].getX(), click[0].getY());
		dlgHexaAdapter.setFillColor(color[0]);
		dlgHexaAdapter.setBorderColor(color[1]);
		dlgHexaAdapter.setVisible(true);
		if (dlgHexaAdapter.isOk())
		{
			cmd = new AddHexagonAdapterCmd(dlgHexaAdapter.getHA(), drwMdl, drwMdl.getShapesSize());
			execute(cmd);
		}
		dlgHexaAdapter.dispose();
	}
	
	private void modPoint()
	{
		Point p = (Point) drwMdl.getShape(drwMdl.getSelected(0));
		DlgPoint dlgPoint = new DlgPoint();
		dlgPoint.setTitle("Modify Point");
		dlgPoint.setTxtXY(p.getX(), p.getY());
		dlgPoint.setFillColor(p.getFillColor());
		dlgPoint.setVisible(true);
		if (dlgPoint.isOk())
		{	
			cmd = new ModifyPointCmd(p, dlgPoint.getP(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgPoint.dispose();
	}
	
	private void modLine()
	{
		Line l = (Line) drwMdl.getShape(drwMdl.getSelected(0));
		DlgLine dlgLine = new DlgLine();
		dlgLine.setTitle("Modify Line");
		dlgLine.setTxtXY1(l.getStartPoint().getX(), l.getStartPoint().getY());
		dlgLine.setTxtXY2(l.getEndPoint().getX(), l.getEndPoint().getY());
		dlgLine.setFillColor(l.getFillColor());
		dlgLine.setVisible(true);
		if (dlgLine.isOk())
		{	
			cmd = new ModifyLineCmd(l, dlgLine.getL(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgLine.dispose();
	}
	
	private void modCircle()
	{
		Circle c = (Circle) drwMdl.getShape(drwMdl.getSelected(0));
		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.setTitle("Modify Circle");
		dlgCircle.setTxtXY(c.getCenter().getX(), c.getCenter().getY());
		dlgCircle.setTxtR(c.getRadius());
		dlgCircle.setFillColor(c.getFillColor());
		dlgCircle.setBorderColor(c.getBorderColor());
		dlgCircle.setVisible(true);
		if (dlgCircle.isOk())
		{	
			cmd = new ModifyCircleCmd(c, dlgCircle.getC(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgCircle.dispose();
	}
	
	private void modRect()
	{
		Rectangle r = (Rectangle) drwMdl.getShape(drwMdl.getSelected(0));
		DlgRectangle dlgRect = new DlgRectangle();
		dlgRect.setTitle("Modify Rectangle");
		dlgRect.setTxtXY(r.getUpperLeftPoint().getX(), r.getUpperLeftPoint().getY());
		dlgRect.setTxtWH(r.getWidth(), r.getHeight());
		dlgRect.setFillColor(r.getFillColor());
		dlgRect.setBorderColor(r.getBorderColor());
		dlgRect.setVisible(true);
		if (dlgRect.isOk())
		{	
			cmd = new ModifyRectangleCmd(r, dlgRect.getR(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgRect.dispose();
	}
	
	private void modDonut()
	{
		Donut d = (Donut) drwMdl.getShape(drwMdl.getSelected(0));
		DlgDonut dlgDonut = new DlgDonut();
		dlgDonut.setTitle("Modify Donut");
		dlgDonut.setTxtXY(d.getCenter().getX(), d.getCenter().getY());
		dlgDonut.setTxtRIR(d.getRadius(), d.getInnerRadius());
		dlgDonut.setFillColor(d.getFillColor());
		dlgDonut.setBorderColor(d.getBorderColor());
		dlgDonut.setVisible(true);
		if (dlgDonut.isOk())
		{	
			cmd = new ModifyDonutCmd(d, dlgDonut.getD(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgDonut.dispose();
	}
	
	private void modHexagon()
	{
		Hexagon h = (Hexagon) drwMdl.getShape(drwMdl.getSelected(0));
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.setTitle("Modify Hexagon");
		dlgHexagon.setTxtXY(h.getCenter().getX(), h.getCenter().getY());
		dlgHexagon.setTxtL(h.getLength());
		dlgHexagon.setFillColor(h.getFillColor());
		dlgHexagon.setBorderColor(h.getBorderColor());
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isOk())
		{	
			cmd = new ModifyHexagonCmd(h, dlgHexagon.getH(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgHexagon.dispose();
	}
	
	private void modHexaAdapter()
	{
		HexagonAdapter ha = (HexagonAdapter) drwMdl.getShape(drwMdl.getSelected(0));
		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.setTitle("Modify HexagonAdapter");
		dlgHexagon.setTxtXY(ha.getX(), ha.getY());
		dlgHexagon.setTxtL(ha.getLength());
		dlgHexagon.setFillColor(ha.getFillColor());
		dlgHexagon.setBorderColor(ha.getBorderColor());
		dlgHexagon.setVisible(true);
		if (dlgHexagon.isOk())
		{
			cmd = new ModifyHexagonAdapterCmd(ha, dlgHexagon.getHA(), drwMdl, drwMdl.getSelected(0));
			execute(cmd);
			drwMdl.setShapeSelected(drwMdl.getSelected(0), true);
		}
		dlgHexagon.dispose();
	}
	
	public int[] checkLayers()
	{
		int[] layers = { 0, 0 };
		
		try
		{
			drwMdl.getShape(drwMdl.getSelected(0) - 1);
			layers[0] = 1;
		}
		catch (IndexOutOfBoundsException exception) {}
		
		try
		{
			drwMdl.getShape(drwMdl.getSelected(0) + 1);
			layers[1] = 1;
		}
		catch (IndexOutOfBoundsException exception) {}
		
		return layers;
	}
	
	public void toBack()
	{
		cmd = new MoveCmd(drwMdl.getSelected(0) - 1, drwMdl, drwMdl.getSelected(0));
		execute(cmd);
		drwMdl.updateSelected(drwMdl.getSelected(0) - 1, 0);
		drwFrm.getDrwView().repaint();
	}
	
	public void toFront()
	{
		cmd = new MoveCmd(drwMdl.getSelected(0) + 1, drwMdl, drwMdl.getSelected(0));
		execute(cmd);
		drwMdl.updateSelected(drwMdl.getSelected(0) + 1, 0);
		drwFrm.getDrwView().repaint();
	}
	
	public void bringBack()
	{
		cmd = new MoveCmd(0, drwMdl, drwMdl.getSelected(0));
		execute(cmd);
		drwMdl.updateSelected(0, 0);
		drwFrm.getDrwView().repaint();
	}
	
	public void bringFront()
	{
		cmd = new MoveCmd(drwMdl.getShapesSize() - 1, drwMdl, drwMdl.getSelected(0));
		execute(cmd);
		drwMdl.updateSelected(drwMdl.getShapesSize() - 1, 0);
		drwFrm.getDrwView().repaint();
	}
	
	public void execute(ICommand cmd)
	{
		try
		{
			drwMdl.getCmd(drwMdl.getStep() + 1);
			drwMdl.updateCmds();
			drwFrm.clearLog();
			ArrayList<ICommand> cmds = drwMdl.getCmds();
			for (int i = 0; i < cmds.size(); i++)
				drwFrm.log(cmds.get(i).toString());
		}
		catch (IndexOutOfBoundsException exception) {}
		
		drwMdl.addCmd(cmd);
		cmd.execute();
		drwFrm.log(cmd.toString());
		drwMdl.setStep(drwMdl.getStep() + 1);
	}
	
	public void undo()
	{
		if (drwMdl.getCmd(drwMdl.getStep()) instanceof AddPointCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddLineCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddCircleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddRectangleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddDonutCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddHexagonCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof AddHexagonAdapterCmd)
			drwMdl.clearSelected();
		
		drwMdl.getCmd(drwMdl.getStep()).unexecute();
		
		if (drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyPointCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyLineCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyCircleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyRectangleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyDonutCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyHexagonCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyHexagonAdapterCmd)
		{
			drwMdl.clearSelected();
			drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
			drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
		}
		else if (drwMdl.getCmd(drwMdl.getStep()) instanceof MoveCmd)
		{
			drwMdl.clearSelected();
			MoveCmd moveCmd = (MoveCmd) drwMdl.getCmd(drwMdl.getStep());
			int index = moveCmd.getOldIndex();
			drwMdl.addSelected(index);
			drwMdl.setShapeSelected(index, true);
		}
		
		if (drwMdl.getCmd(drwMdl.getStep()) instanceof RemovePointCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveLineCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveCircleCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveRectangleCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveDonutCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveHexagonCmd ||
			drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveHexagonAdapterCmd)
		{
			try
			{
				ICommand temp = drwMdl.getCmd(drwMdl.getStep() + 1);
				if (temp instanceof RemovePointCmd ||
					temp instanceof RemoveLineCmd ||
					temp instanceof RemoveCircleCmd ||
					temp instanceof RemoveRectangleCmd ||
					temp instanceof RemoveDonutCmd ||
					temp instanceof RemoveHexagonCmd ||
					temp instanceof RemoveHexagonAdapterCmd)
				{
					drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
					drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
				}
				else
				{
					drwMdl.clearSelected();
					drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
					drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
				}
			}
			catch (Exception exception)
			{
				drwMdl.clearSelected();
				drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
				drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
			}
		}
		
		drwMdl.setStep(drwMdl.getStep() - 1);
		drwFrm.getDrwView().repaint();
	}
	
	public void redo()
	{
		try
		{
			if (drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemovePointCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveLineCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveCircleCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveRectangleCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveDonutCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveHexagonCmd ||
					drwMdl.getCmd(drwMdl.getStep() + 1) instanceof RemoveHexagonAdapterCmd)
			{
				if (drwMdl.getCmd(drwMdl.getStep()) instanceof RemovePointCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveLineCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveCircleCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveRectangleCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveDonutCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveHexagonCmd ||
						drwMdl.getCmd(drwMdl.getStep()) instanceof RemoveHexagonAdapterCmd) {}
				else
					drwMdl.clearSelected();
			}	
		}
		catch (IndexOutOfBoundsException Exception)
		{
			drwMdl.clearSelected();
		}
		
		
		drwMdl.getCmd(drwMdl.getStep() + 1).execute();
		drwMdl.setStep(drwMdl.getStep() + 1);
		
		if (drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyPointCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyLineCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyCircleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyRectangleCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyDonutCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyHexagonCmd ||
				drwMdl.getCmd(drwMdl.getStep()) instanceof ModifyHexagonAdapterCmd)
		{
			drwMdl.clearSelected();
			drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
			drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
		}
		else if (drwMdl.getCmd(drwMdl.getStep()) instanceof MoveCmd)
		{
			drwMdl.clearSelected();
			drwMdl.addSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex());
			drwMdl.setShapeSelected(drwMdl.getCmd(drwMdl.getStep()).getIndex(), true);
		}
		
		drwFrm.getDrwView().repaint();
	}
	
	public void importFile()
	{
		JFileChooser fileChooser = new JFileChooser("../");
		int ok = fileChooser.showOpenDialog(drwFrm);
		if (ok == JFileChooser.APPROVE_OPTION)
		{
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			FileManager fileManager = new FileManager(new LogFile());
			fileManager.read(path);
			ArrayList<String> log = fileManager.getLog();
			ArrayList<ICommand> cmds = new ArrayList<ICommand>();
			
			for (int i = 0; i < log.size(); i++)
			{
				String temp[] = log.get(i).split(" ");
				switch(temp[0])
				{
					case "Created":
						switch(temp[1])
						{
							case "point":
								try
								{
									Point p = new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5]), false, new Color(Integer.parseInt(temp[7])));
									cmds.add(new AddPointCmd(p, drwMdl, Integer.parseInt(temp[9])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "line":
								try
								{
									Line l = new Line(Integer.parseInt(temp[4]), Integer.parseInt(temp[6]), Integer.parseInt(temp[9]), Integer.parseInt(temp[11]));
									l.setSelected(false);
									l.setFillColor(new Color(Integer.parseInt(temp[13])));
									cmds.add(new AddLineCmd(l, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "circle":
								try
								{
									Circle c = new Circle(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new AddCircleCmd(c, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "rectangle":
								try
								{
									Rectangle r = new Rectangle(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), Integer.parseInt(temp[9]), false, new Color(Integer.parseInt(temp[11])), new Color(Integer.parseInt(temp[13])));
									cmds.add(new AddRectangleCmd(r, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "donut":
								try
								{
									Donut d = new Donut(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), Integer.parseInt(temp[9]), false, new Color(Integer.parseInt(temp[11])), new Color(Integer.parseInt(temp[13])));
									cmds.add(new AddDonutCmd(d, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagon":
								try
								{
									Hexagon h = new Hexagon(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new AddHexagonCmd(h, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagonAdapter":
								try
								{
									HexagonAdapter ha = new HexagonAdapter(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new AddHexagonAdapterCmd(ha, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							default:
								drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
								return;
						}
						break;
					case "Removed":
						switch(temp[1])
						{
							case "point":
								try
								{
									Point p = new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5]), false, new Color(Integer.parseInt(temp[7])));
									cmds.add(new RemovePointCmd(p, drwMdl, Integer.parseInt(temp[9])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "line":
								try
								{
									Line l = new Line(Integer.parseInt(temp[4]), Integer.parseInt(temp[6]), Integer.parseInt(temp[9]), Integer.parseInt(temp[11]));
									l.setSelected(false);
									l.setFillColor(new Color(Integer.parseInt(temp[13])));
									cmds.add(new RemoveLineCmd(l, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "circle":
								try
								{
									Circle c = new Circle(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new RemoveCircleCmd(c, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "rectangle":
								try
								{
									Rectangle r = new Rectangle(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), Integer.parseInt(temp[9]), false, new Color(Integer.parseInt(temp[11])), new Color(Integer.parseInt(temp[13])));
									cmds.add(new RemoveRectangleCmd(r, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "donut":
								try
								{
									Donut d = new Donut(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), Integer.parseInt(temp[9]), false, new Color(Integer.parseInt(temp[11])), new Color(Integer.parseInt(temp[13])));
									cmds.add(new RemoveDonutCmd(d, drwMdl, Integer.parseInt(temp[15])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagon":
								try
								{
									Hexagon h = new Hexagon(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new RemoveHexagonCmd(h, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagonAdapter":
								try
								{
									HexagonAdapter ha = new HexagonAdapter(new Point(Integer.parseInt(temp[3]), Integer.parseInt(temp[5])), Integer.parseInt(temp[7]), false, new Color(Integer.parseInt(temp[9])), new Color(Integer.parseInt(temp[11])));
									cmds.add(new RemoveHexagonAdapterCmd(ha, drwMdl, Integer.parseInt(temp[13])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							default:
								drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
								return;
						}
						break;
					case "Modified":
						switch(temp[1])
						{
							case "point":
								try
								{
									Point p1 = new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6]), false, new Color(Integer.parseInt(temp[8])));
									Point p2 = new Point(Integer.parseInt(temp[11]), Integer.parseInt(temp[13]), false, new Color(Integer.parseInt(temp[15])));
									cmds.add(new ModifyPointCmd(p1, p2, drwMdl, Integer.parseInt(temp[17])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "line":
								try
								{
									Line l1 = new Line(Integer.parseInt(temp[5]), Integer.parseInt(temp[7]), Integer.parseInt(temp[10]), Integer.parseInt(temp[12]));
									l1.setSelected(false);
									l1.setFillColor(new Color(Integer.parseInt(temp[14])));
									
									Line l2 = new Line(Integer.parseInt(temp[18]), Integer.parseInt(temp[20]), Integer.parseInt(temp[23]), Integer.parseInt(temp[25]));
									l2.setSelected(false);
									l2.setFillColor(new Color(Integer.parseInt(temp[27])));
									
									cmds.add(new ModifyLineCmd(l1, l2, drwMdl, Integer.parseInt(temp[29])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "circle":
								try
								{
									Circle c1 = new Circle(new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6])), Integer.parseInt(temp[8]), false, new Color(Integer.parseInt(temp[10])), new Color(Integer.parseInt(temp[12])));
									Circle c2 = new Circle(new Point(Integer.parseInt(temp[15]), Integer.parseInt(temp[17])), Integer.parseInt(temp[19]), false, new Color(Integer.parseInt(temp[21])), new Color(Integer.parseInt(temp[23])));
									cmds.add(new ModifyCircleCmd(c1, c2, drwMdl, Integer.parseInt(temp[25])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "rectangle":
								try
								{
									Rectangle r1 = new Rectangle(new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6])), Integer.parseInt(temp[8]), Integer.parseInt(temp[10]), false, new Color(Integer.parseInt(temp[12])), new Color(Integer.parseInt(temp[14])));
									Rectangle r2 = new Rectangle(new Point(Integer.parseInt(temp[17]), Integer.parseInt(temp[19])), Integer.parseInt(temp[21]), Integer.parseInt(temp[23]), false, new Color(Integer.parseInt(temp[25])), new Color(Integer.parseInt(temp[27])));
									cmds.add(new ModifyRectangleCmd(r1, r2, drwMdl, Integer.parseInt(temp[29])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "donut":
								try
								{
									Donut d1 = new Donut(new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6])), Integer.parseInt(temp[8]), Integer.parseInt(temp[10]), false, new Color(Integer.parseInt(temp[12])), new Color(Integer.parseInt(temp[14])));
									Donut d2 = new Donut(new Point(Integer.parseInt(temp[17]), Integer.parseInt(temp[19])), Integer.parseInt(temp[21]), Integer.parseInt(temp[23]), false, new Color(Integer.parseInt(temp[25])), new Color(Integer.parseInt(temp[27])));
									cmds.add(new ModifyDonutCmd(d1, d2, drwMdl, Integer.parseInt(temp[29])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagon":
								try
								{
									Hexagon h1 = new Hexagon(new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6])), Integer.parseInt(temp[8]), false, new Color(Integer.parseInt(temp[10])), new Color(Integer.parseInt(temp[12])));
									Hexagon h2 = new Hexagon(new Point(Integer.parseInt(temp[15]), Integer.parseInt(temp[17])), Integer.parseInt(temp[19]), false, new Color(Integer.parseInt(temp[21])), new Color(Integer.parseInt(temp[23])));
									cmds.add(new ModifyHexagonCmd(h1, h2, drwMdl, Integer.parseInt(temp[25])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							case "hexagonAdapter":
								try
								{
									HexagonAdapter ha1 = new HexagonAdapter(new Point(Integer.parseInt(temp[4]), Integer.parseInt(temp[6])), Integer.parseInt(temp[8]), false, new Color(Integer.parseInt(temp[10])), new Color(Integer.parseInt(temp[12])));
									HexagonAdapter ha2 = new HexagonAdapter(new Point(Integer.parseInt(temp[15]), Integer.parseInt(temp[17])), Integer.parseInt(temp[19]), false, new Color(Integer.parseInt(temp[21])), new Color(Integer.parseInt(temp[23])));
									cmds.add(new ModifyHexagonAdapterCmd(ha1, ha2, drwMdl, Integer.parseInt(temp[25])));
								}
								catch(Exception exception)
								{
									drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
									return;
								}
								break;
							default:
								drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
								return;
						}
						break;
					case "Moved":
						try
						{
							cmds.add(new MoveCmd(Integer.parseInt(temp[6]), drwMdl, Integer.parseInt(temp[4])));
						}
						catch (Exception exception)
						{
							drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
							return;
						}
						break;
					default:
						drwFrm.warn("Log failed to import because corrupted or bad file!", "Failed To Import Log");
						return;
				}
			}
			
			drwMdl.clearShapes();
			drwMdl.clearSelected();
			drwMdl.clearCmds();
			drwFrm.clearLog();
			drwMdl.setStep(-1);
			drwFrm.getDrwView().repaint();
			
			for (int i = 0; i < cmds.size(); i++)
				if (drwFrm.confirm("Are you sure?", "Execute Command") == JOptionPane.YES_OPTION)
				{
					execute(cmds.get(i));
					drwFrm.getDrwView().repaint();
				}
				else
					break;
		}
		undo();
		redo();
	}
	
	public void exportFile()
	{
		JFileChooser fileChooser = new JFileChooser("../");
		int ok = fileChooser.showSaveDialog(drwFrm);
		if (ok == JFileChooser.APPROVE_OPTION)
		{
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			ArrayList<String> log = new ArrayList<String>();
			for (int i = 0; i <= drwMdl.getStep(); i++)
				log.add(drwMdl.getCmd(i).toString());
			FileManager fileManager = new FileManager(new LogFile(log));
			try
			{
				fileManager.write(path);
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	public void importDrawing()
	{
		JFileChooser fileChooser = new JFileChooser("../");
		int ok = fileChooser.showOpenDialog(drwFrm);
		if (ok == JFileChooser.APPROVE_OPTION)
		{
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			FileManager fileManager = new FileManager(new DrawingFile());
			fileManager.read(path);
			if (fileManager.getDrawing() != null)
			{
				drwMdl.setShapes(fileManager.getDrawing());
				drwMdl.clearSelected();
				drwMdl.clearCmds();
				drwFrm.clearLog();
				drwMdl.setStep(-1);
				drwFrm.getDrwView().repaint();
			}
			else
				drwFrm.warn("Drawing failed to import because corrupted or bad file!", "Failed To Import Drawing");
		}
	}
	
	public void exportDrawing()
	{
		JFileChooser fileChooser = new JFileChooser("../");
		int ok = fileChooser.showSaveDialog(drwFrm);
		if (ok == JFileChooser.APPROVE_OPTION)
		{
			String path = fileChooser.getSelectedFile().getAbsolutePath();
			FileManager fileManager = new FileManager(new DrawingFile(drwMdl.getShapes()));
			try
			{
				fileManager.write(path);
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	public Point getClick1()
	{
		return click[0];
	}
	
	public void setClick1(int x, int y)
	{
		click[0] = new Point(x, y);
	}
	
	public void setClick1(Point click)
	{
		this.click[0] = click;
	}
	
	public Point getClick2()
	{
		return click[1];
	}
	
	public void setClick2(int x, int y)
	{
		click[1] = new Point(x, y);
	}
	
	public void setClick2(Point click)
	{
		this.click[1] = click;
	}
	
	public ModeAddSel getModeAddSel()
	{
		return modeAddSel;
	}
	
	public void setModeAddSel(ModeAddSel modeAddSel)
	{
		this.modeAddSel = modeAddSel;
	}
	
	public Color getColor1()
	{
		return color[0];
	}
	
	public void setColor1(Color color)
	{
		this.color[0] = color;
	}
	
	public Color getColor2()
	{
		return color[1];
	}
	
	public void setColor2(Color color)
	{
		this.color[1] = color;
	}
}
