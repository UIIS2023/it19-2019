package cmd;

import java.io.Serializable;

import geometry.Donut;
import mvc.DrawingModel;

public class AddDonutCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Donut donut;
	
	public AddDonutCmd(Donut donut, DrawingModel model, int index)
	{
		this.donut = donut;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(donut);
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
        return "Created donut " + donut.toString() + " i: " + Integer.toString(getIndex());
    }
}
