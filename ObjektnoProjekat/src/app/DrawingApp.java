package app;

import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class DrawingApp
{
	public static void main(String[] args)
	{
		DrawingModel drwMdl = new DrawingModel();
		DrawingFrame drwFrm = new DrawingFrame();
		DrawingController drwCtlr = new DrawingController(drwMdl, drwFrm);
		drwFrm.getDrwView().setDrwMdl(drwMdl);
		drwFrm.setDrwCtlr(drwCtlr);
		drwFrm.setVisible(true);
	}
}
