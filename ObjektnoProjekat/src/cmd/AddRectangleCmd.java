package cmd;

import java.io.Serializable;

import geometry.Rectangle;
import mvc.DrawingModel;

public class AddRectangleCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Rectangle rectangle;
	
	public AddRectangleCmd(Rectangle rectangle, DrawingModel model, int index)
	{
		this.rectangle = rectangle;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(rectangle);
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
        return "Created rectangle " + rectangle.toString() + " i: " + Integer.toString(getIndex());
    }
}
