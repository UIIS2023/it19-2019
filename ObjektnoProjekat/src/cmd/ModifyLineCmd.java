package cmd;

import java.io.Serializable;

import geometry.Line;
import mvc.DrawingModel;

public class ModifyLineCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Line line;
	private Line oldL;
	
	public ModifyLineCmd(Line line, Line newL, DrawingModel model, int index)
	{
		oldL = line.clone();
		oldL.setSelected(false);
		this.line = newL;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.removeShape(getIndex());
		model.addShape(getIndex(), line);
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
		model.addShape(getIndex(), oldL);
	}

	@Override
	public String toString()
	{
		return "Modified line from " + oldL.toString() + " to " + line.toString() + " i: " + getIndex();
	}
}
