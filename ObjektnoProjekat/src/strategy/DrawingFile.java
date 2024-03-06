package strategy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import geometry.Shape;

public class DrawingFile implements IFileOperator
{
	protected ArrayList<Shape> shapes;
	
	public DrawingFile() {}
	
	public DrawingFile(ArrayList<Shape> shapes)
	{
		this.shapes = shapes;
	}
	
	public ArrayList<Shape> getShapes()
	{
		return shapes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void read(String path)
	{
		try
		{
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			shapes = (ArrayList<Shape>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		}
		catch (IOException | ClassNotFoundException exception)
		{
			exception.printStackTrace();
		}
	}

	@Override
	public void write(String path) throws IOException
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(shapes);
			objectOutputStream.close();
			fileOutputStream.close();
		}
		catch (IOException exception) 
		{
			shapes = null;
			exception.printStackTrace();
		}
	}
}
