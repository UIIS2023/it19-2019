package cmd;

import java.io.Serializable;

import geometry.Donut;
import mvc.DrawingModel;

public class ModifyDonutCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private Donut donut;
	private Donut oldD;
	
	public ModifyDonutCmd(Donut donut, Donut newD, DrawingModel model, int index)
	{
		oldD = donut.clone();
		oldD.setSelected(false);
		this.donut = newD;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.removeShape(getIndex());
		model.addShape(getIndex(), donut);
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
		model.addShape(getIndex(), oldD);
	}

	@Override
	public String toString()
	{
		return "Modified donut from " + oldD.toString() + " to " + donut.toString()  + " i: " + getIndex();
	}
}
