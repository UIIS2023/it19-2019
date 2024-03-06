package mvc;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;

import cmd.ICommand;
import geometry.Shape;
import observer.Observable;

public class DrawingModel extends Observable
{
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Integer> selected = new ArrayList<Integer>();
	private ArrayList<ICommand> cmds = new ArrayList<ICommand>();
	
	private int step = -1;
	
	public DrawingModel()
	{
		pcs = new PropertyChangeSupport(this);
	}
	
	public ArrayList<Shape> getShapes()
	{
		return shapes;
	}
	
	public void setShapes(ArrayList<Shape> shapes)
	{
		this.shapes = shapes;
		pcs.firePropertyChange("shapes", null, shapes);
	}
	
	public boolean isShapesEmpty()
	{
		return shapes.isEmpty();
	}
	
	public int getShapesSize()
	{
		return shapes.size();
	}
	
	public Shape getShape(int index)
	{
		return shapes.get(index);
	}
	
	public void addShape(Shape shape)
	{
		shapes.add(shape);
		pcs.firePropertyChange("shapes", null, shapes);
	}
	
	public void addShape(int i, Shape shape)
	{
		shapes.add(i, shape);
		pcs.firePropertyChange("shapes", null, shapes);
	}
	
	public void removeShape(int index)
	{
		shapes.remove(index);
		pcs.firePropertyChange("shapes", null, shapes);
	}
	
	public boolean isShapeSelected(int index)
	{
		return shapes.get(index).isSelected();
	}
	
	public void setShapeSelected(int index, boolean select)
	{
		shapes.get(index).setSelected(select);
	}
	
	public void clearShapes()
	{
		shapes.clear();
		pcs.firePropertyChange("shapes", null, shapes);
	}
	
	public int getSelected(int index)
	{
		return selected.get(index);
	}
	
	public int getSelectedSize()
	{
		return selected.size();
	}
	
	public boolean isSelectedEmpty()
	{
		return selected.isEmpty();
	}
	
	public void addSelected(int index)
	{
		selected.add(index);
		Collections.sort(selected, Collections.reverseOrder());
		pcs.firePropertyChange("selected", null, this.selected);
	}
	
	public void updateSelected(int index, int i)
	{
		selected.remove(i);
		selected.add(index);
		Collections.sort(selected, Collections.reverseOrder());
		pcs.firePropertyChange("selected", null, this.selected);
	}
	
	public void clearSelected()
	{
		for (int i = 0; i < getShapesSize(); i++)
			setShapeSelected(i, false);
		selected.clear();
		pcs.firePropertyChange("selected", null, this.selected);
	}
	
	public ICommand getCmd(int index)
	{
		return cmds.get(index);
	}
	
	public int getCmdSize()
	{
		return cmds.size();
	}
	
	public void addCmd(ICommand cmd)
	{
		cmds.add(cmd);
	}
	
	public ArrayList<ICommand> getCmds()
	{
		return cmds;
	}
	
	public void updateCmds()
	{
		if (step == -1)
			cmds = new ArrayList<ICommand>();
		else
		{
			ArrayList<ICommand> temp = new ArrayList<ICommand>();
			for (int i = 0; i <= step; i++)
				temp.add(cmds.get(i));
			cmds = temp;
		}
	}
	
	public void clearCmds()
	{
		cmds.clear();
		step = -1;
	}
	
	public int getStep()
	{
		return step;
	}
	
	public void setStep(int step)
	{
		this.step = step;
		pcs.firePropertyChange("step", null, this.step);
	}
}
