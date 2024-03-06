package cmd;

import java.io.Serializable;

import geometry.Circle;
import mvc.DrawingModel;

public class AddCircleCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Circle circle;
	
	public AddCircleCmd(Circle circle, DrawingModel model, int index)
	{
		this.circle = circle;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(circle);
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
        return "Created circle " + circle.toString() + " i: " + Integer.toString(getIndex());
    }
}
