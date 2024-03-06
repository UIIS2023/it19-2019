package cmd;

import java.io.Serializable;

import geometry.Line;
import mvc.DrawingModel;

public class RemoveLineCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Line line;
	
	public RemoveLineCmd(Line line, DrawingModel model, int index)
	{
		this.line = line;
		this.line.setSelected(false);
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
		model.addShape(getIndex(), line);
	}
	
	@Override
    public String toString()
	{
        return "Removed line " + line.toString() + " i: " + Integer.toString(getIndex());
    }
}