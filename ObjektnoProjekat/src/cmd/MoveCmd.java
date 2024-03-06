package cmd;

import java.io.Serializable;

import geometry.Shape;
import mvc.DrawingModel;

public class MoveCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Shape shape;
	private int oldIndex;
	
	public MoveCmd(int newIndex, DrawingModel model, int index)
	{
		setIndex(newIndex);
		this.model = model;
		oldIndex = index;
	}
	
	public int getOldIndex()
	{
		return oldIndex;
	}
	
	@Override
	public void execute()
	{
		shape = model.getShape(oldIndex);
		model.removeShape(oldIndex);
		model.addShape(getIndex(), shape);
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
		shape = model.getShape(getIndex());
		model.removeShape(getIndex());
		model.addShape(oldIndex, shape);
	}

	@Override
	public String toString()
	{
		return "Moved shape from pos: " + Integer.toString(oldIndex) + " to " + Integer.toString(getIndex());
	}
}
