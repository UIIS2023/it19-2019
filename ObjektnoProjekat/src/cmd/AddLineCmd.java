package cmd;

import java.io.Serializable;

import geometry.Line;
import mvc.DrawingModel;

public class AddLineCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Line line;
	
	public AddLineCmd(Line line, DrawingModel model, int index)
	{
		this.line = line;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(line);
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
        return "Created line " + line.toString() + " i: " + Integer.toString(getIndex());
    }
}
