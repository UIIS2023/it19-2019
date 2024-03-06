package cmd;

import java.io.Serializable;

import geometry.Rectangle;
import mvc.DrawingModel;

public class RemoveRectangleCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Rectangle rectangle;
	
	public RemoveRectangleCmd(Rectangle rectangle, DrawingModel model, int index)
	{
		this.rectangle = rectangle;
		this.rectangle.setSelected(false);
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
		model.addShape(getIndex(), rectangle);
	}
	
	@Override
    public String toString()
	{
        return "Removed rectangle " + rectangle.toString() + " i: " + Integer.toString(getIndex());
    }
}