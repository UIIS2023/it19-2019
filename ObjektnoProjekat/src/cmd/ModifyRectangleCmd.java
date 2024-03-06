package cmd;

import java.io.Serializable;

import geometry.Rectangle;
import mvc.DrawingModel;

public class ModifyRectangleCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Rectangle rectangle;
	private Rectangle oldR;
	
	public ModifyRectangleCmd(Rectangle rectangle, Rectangle newR, DrawingModel model, int index)
	{
		oldR = rectangle.clone();
		oldR.setSelected(false);
		this.rectangle = newR;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.removeShape(getIndex());
		model.addShape(getIndex(), rectangle);
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
		model.addShape(getIndex(), oldR);
	}

	@Override
	public String toString()
	{
		return "Modified rectangle from " + oldR.toString() + " to " + rectangle.toString() + " i: " + getIndex();
	}
}
