package cmd;

import java.io.Serializable;

import geometry.Point;
import mvc.DrawingModel;

public class RemovePointCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Point point;
	
	public RemovePointCmd(Point point, DrawingModel model, int index)
	{
		this.point = point;
		this.point.setSelected(false);
		this.model = model;
		setIndex(index);
	}

	@Override
	public void execute()
	{
		model.removeShape(getIndex());
	}
	
	@Override
	public void execute(DrawingModel model)
	{
		this.model = model;
		this.execute();
	}

	@Override
	public void unexecute()
	{
		model.addShape(getIndex(), point);
	}
	
	@Override
    public String toString()
	{
        return "Removed point " + point.toString() + " i: " + Integer.toString(getIndex());
    }
}