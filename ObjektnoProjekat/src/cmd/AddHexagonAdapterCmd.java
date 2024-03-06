package cmd;

import java.io.Serializable;

import adapter.HexagonAdapter;
import mvc.DrawingModel;

public class AddHexagonAdapterCmd extends IndexCmd implements ICommand, Serializable
{
	private static final long serialVersionUID = 1L;
	private transient DrawingModel model;
	private HexagonAdapter hexaAdapter;
	
	public AddHexagonAdapterCmd(HexagonAdapter hexaAdapter, DrawingModel model, int index)
	{
		this.hexaAdapter = hexaAdapter;
		this.model = model;
		setIndex(index);
	}
	
	@Override
	public void execute()
	{
		model.addShape(hexaAdapter);
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
        return "Created hexagonAdapter " + hexaAdapter.toString() + " i: " + Integer.toString(getIndex());
    }
}
