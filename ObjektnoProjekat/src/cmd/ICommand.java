package cmd;

import mvc.DrawingModel;

public interface ICommand
{
	void execute();
	void execute(DrawingModel model);
	void unexecute();
	public int getIndex();
	public void setIndex(int index);
}
