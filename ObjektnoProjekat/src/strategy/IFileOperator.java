package strategy;

import java.io.IOException;

public interface IFileOperator
{
	void read(String path);
	void write(String path) throws IOException;
}
