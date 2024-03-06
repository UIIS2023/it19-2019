package cmd;

import java.io.Serializable;

import geometry.Point;
import mvc.DrawingModel;

public class AddPointCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Point point;
	
	public AddPointCmd(Point point, DrawingModel model, int index)
	{
		this.point = point;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(point);
	}
	
	@Override
	public void execute(DrawingModel model)
	{
		this.model = model;
		execute();
	}

	@Override
	public void unexecute()
	{
		model.removeShape(getIndex());
	}
	
	@Override
    public String toString()
	{
        return "Created point " + point.toString() + " i: " + Integer.toString(getIndex());
    }
}
