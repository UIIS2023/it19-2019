package strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LogFile implements IFileOperator
{
	private ArrayList<String> log;
	
	public LogFile() 
	{
		log = new ArrayList<String>();
	}
	
	public LogFile(ArrayList<String> log)
	{
		this.log = log;
	}
	
	public ArrayList<String> getLog()
	{
		return log;
	}
	
	@Override
	public void read(String path)
	{
		BufferedReader bufferedReader;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(path));
			String line = bufferedReader.readLine();
			while (line != null)
			{
				log.add(line);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public void write(String path) throws IOException
	{
		FileWriter file = new FileWriter(path);
		try (PrintWriter printWriter = new PrintWriter(file))
		{
				for (int i = 0; i < log.size(); i++)
					printWriter.println(log.get(i));
				printWriter.close();
		}
		file.close();
	}
}
