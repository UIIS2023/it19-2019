package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Observable
{
	protected PropertyChangeSupport pcs;
	
	public void addPropertyChangeListener(PropertyChangeListener pcl)
	{
		pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl)
	{
		pcs.removePropertyChangeListener(pcl);
	}
}
