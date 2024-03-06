package cmd;

import java.io.Serializable;

import geometry.Hexagon;
import mvc.DrawingModel;

public class AddHexagonCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Hexagon hexagon;
	
	public AddHexagonCmd(Hexagon hexagon, DrawingModel model, int index)
	{
		this.hexagon = hexagon;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(hexagon);
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
        return "Created hexagon " + hexagon.toString() + " i: " + Integer.toString(getIndex());
    }
}
