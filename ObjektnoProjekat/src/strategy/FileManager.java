package strategy;

import java.io.IOException;
import java.util.ArrayList;

import geometry.Shape;

public class FileManager implements IFileOperator
{
	private IFileOperator fileOperator;

	public FileManager(IFileOperator fileOperator)
	{
		this.fileOperator = fileOperator;
	}
	
	@Override
	public void read(String path)
	{
		fileOperator.read(path);
	}
	
	@Override
	public void write(String path) throws IOException
	{
		fileOperator.write(path);
	}
	
	public ArrayList<String> getLog()
	{
		if (fileOperator instanceof LogFile)
			return ((LogFile) fileOperator).getLog();
		return null;
	}
	
	public ArrayList<Shape> getDrawing()
	{
		if (fileOperator instanceof DrawingFile)
			return ((DrawingFile) fileOperator).getShapes();
		return null;
	}
}
