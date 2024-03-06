package cmd;

import java.io.Serializable;

import geometry.Circle;
import mvc.DrawingModel;

public class ModifyCircleCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Circle circle;
	private Circle oldC;
	
	public ModifyCircleCmd(Circle circle, Circle newC, DrawingModel model, int index)
	{
		oldC = circle.clone();
		oldC.setSelected(false);
		this.circle = newC;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.removeShape(getIndex());
		model.addShape(getIndex(), circle);
	}

	@Override
	public void execute(DrawingModel model)
	{
		execute();
	}
	
	@Override
	public void unexecute()
	{
		model.removeShape(getIndex());
		model.addShape(getIndex(), oldC);
	}

	@Override
	public String toString()
	{
		return "Modified circle from " + oldC.toString() + " to " + circle.toString() + " i: " + getIndex();
	}
}
