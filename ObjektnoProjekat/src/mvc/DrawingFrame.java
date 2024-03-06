package mvc;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingFrame extends JFrame implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DrawingView drwView = new DrawingView();
	private DrawingController drwCtlr;
	
	private ButtonGroup btnAddSel = new ButtonGroup();
	
	private DefaultListModel<String> click = new DefaultListModel<String>();
	private JList<String> listClick;
	
	private DefaultListModel<String> log = new DefaultListModel<String>();
	private JList<String> listLog;
	
	JMenuItem mntmExportFile;
	JMenuItem mntmExportDrawing;
	
	JMenuItem mntmUndo;
	JMenuItem mntmRedo;
	
	JButton btnBtb;
	JButton btnTb;
	JButton btnTf;
	JButton btnBtf;
	
	JButton btnMod;
	JButton btnDel;
	JButton btnClr;
	
	JToggleButton tglbtnSel;
	JToggleButton tglbtnPoint;
	JToggleButton tglbtnLine;
	JToggleButton tglbtnCircle;
	JToggleButton tglbtnRect;
	JToggleButton tglbtnDonut;
	JToggleButton tglbtnHexa;
	
	public DrawingFrame()
	{
		setTitle("Petar Rakic IT19/2019");
		setResizable(false);
		setBackground(Color.WHITE);
		setBounds((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 1000) / 2, 
				(int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 750) / 2, 1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{115, 604, 0};
		gbl_contentPane.rowHeights = new int[]{0, 451, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel pnlFileEdit = new JPanel();
		pnlFileEdit.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlFileEdit.setBackground(Color.WHITE);
		GridBagConstraints gbc_pnlFileEdit = new GridBagConstraints();
		gbc_pnlFileEdit.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFileEdit.fill = GridBagConstraints.BOTH;
		gbc_pnlFileEdit.gridx = 0;
		gbc_pnlFileEdit.gridy = 0;
		contentPane.add(pnlFileEdit, gbc_pnlFileEdit);
		
		JMenuBar menuBar = new JMenuBar();
		pnlFileEdit.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmImportFile = new JMenuItem("Import File");
		mntmImportFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.importFile();
			}
		});
		mnFile.add(mntmImportFile);
		
		mntmExportFile = new JMenuItem("Export File");
		mntmExportFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.exportFile();
			}
		});
		mnFile.add(mntmExportFile);
		mntmExportFile.setEnabled(false);
		
		JMenuItem mntmImportDrawing = new JMenuItem("Import Drawing");
		mntmImportDrawing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.importDrawing();
			}
		});
		mnFile.add(mntmImportDrawing);
		
		mntmExportDrawing = new JMenuItem("Export Drawing");
		mntmExportDrawing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.exportDrawing();
			}
		});
		mnFile.add(mntmExportDrawing);
		mntmExportDrawing.setEnabled(false);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.undo();
			}
		});
		mnEdit.add(mntmUndo);
		mntmUndo.setEnabled(false);
		
		mntmRedo = new JMenuItem("Redo");
		mntmRedo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.redo();
			}
		});
		mnEdit.add(mntmRedo);
		mntmRedo.setEnabled(false);
		
		JPanel pnlUp = new JPanel();
		pnlUp.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlUp.setBackground(Color.WHITE);
		GridBagConstraints gbc_pnlUp = new GridBagConstraints();
		gbc_pnlUp.insets = new Insets(0, 0, 5, 0);
		gbc_pnlUp.fill = GridBagConstraints.BOTH;
		gbc_pnlUp.gridx = 1;
		gbc_pnlUp.gridy = 0;
		contentPane.add(pnlUp, gbc_pnlUp);
		
		JButton btnColor1 = new JButton("Color1");
		btnColor1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color color1 = JColorChooser.showDialog(btnColor1, "Color1", drwCtlr.getColor1());
				if (color1 != null)
					drwCtlr.setColor1(color1);
				btnColor1.setBackground(drwCtlr.getColor1());
			}
		});
		pnlUp.add(btnColor1);
		btnColor1.setForeground(new Color(0, 0, 0, 0));
		btnColor1.setBackground(Color.BLUE);
		
		JButton btnColor2 = new JButton("Color2");
		btnColor2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color color2 = JColorChooser.showDialog(btnColor2, "Color1", drwCtlr.getColor2());
				if (color2 != null)
					drwCtlr.setColor2(color2);
				btnColor2.setBackground(drwCtlr.getColor2());
			}
		});
		pnlUp.add(btnColor2);
		btnColor2.setForeground(new Color(0, 0, 0, 0));
		btnColor2.setBackground(Color.RED);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_4);
		
		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_6);
		
		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_7);
		
		Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_8);
		
		Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_9);
		
		Component rigidArea_10 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_10);
		
		Component rigidArea_11 = Box.createRigidArea(new Dimension(20, 20));
		pnlUp.add(rigidArea_11);
		
		btnBtb = new JButton("Bring To Back");
		btnBtb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.bringBack();
				btnBtb.setEnabled(false);
			}
		});
		pnlUp.add(btnBtb);
		btnBtb.setEnabled(false);
		
		btnTb = new JButton("To Back");
		btnTb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.toBack();
				int[] layers = drwCtlr.checkLayers();
				switch (layers[0])
				{
					case 0:
						btnBtb.setEnabled(false);
						btnTb.setEnabled(false);
						break;
					case 1:
						btnBtb.setEnabled(true);
						btnTb.setEnabled(true);
						break;
				}
				switch (layers[1])
				{
					case 0:
						btnTf.setEnabled(false);
						btnBtf.setEnabled(false);
						break;
					case 1:
						btnTf.setEnabled(true);
						btnBtf.setEnabled(true);
						break;
				}
			}
		});
		pnlUp.add(btnTb);
		btnTb.setEnabled(false);
		
		btnTf = new JButton("To Front");
		btnTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.toFront();
				int[] layers = drwCtlr.checkLayers();
				switch (layers[0])
				{
					case 0:
						btnBtb.setEnabled(false);
						btnTb.setEnabled(false);
						break;
					case 1:
						btnBtb.setEnabled(true);
						btnTb.setEnabled(true);
						break;
				}
				switch (layers[1])
				{
					case 0:
						btnTf.setEnabled(false);
						btnBtf.setEnabled(false);
						break;
					case 1:
						btnTf.setEnabled(true);
						btnBtf.setEnabled(true);
						break;
				}
			}
		});
		pnlUp.add(btnTf);
		btnTf.setEnabled(false);
		
		btnBtf = new JButton("Bring To Front");
		btnBtf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.bringFront();
				btnBtf.setEnabled(false);
			}
		});
		pnlUp.add(btnBtf);
		btnBtf.setEnabled(false);
		
		JPanel pnlLeft = new JPanel();
		pnlLeft.setBackground(Color.WHITE);
		pnlLeft.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		GridBagConstraints gbc_pnlLeft = new GridBagConstraints();
		gbc_pnlLeft.fill = GridBagConstraints.BOTH;
		gbc_pnlLeft.insets = new Insets(0, 0, 5, 5);
		gbc_pnlLeft.gridx = 0;
		gbc_pnlLeft.gridy = 1;
		contentPane.add(pnlLeft, gbc_pnlLeft);
		GridBagLayout gbl_pnlLeft = new GridBagLayout();
		gbl_pnlLeft.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlLeft.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlLeft.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		pnlLeft.setLayout(gbl_pnlLeft);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
		gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_1.gridx = 1;
		gbc_rigidArea_1.gridy = 1;
		pnlLeft.add(rigidArea_1, gbc_rigidArea_1);
		
		JLabel lblOperation = new JLabel("Operation:");
		GridBagConstraints gbc_lblOperation = new GridBagConstraints();
		gbc_lblOperation.insets = new Insets(0, 0, 5, 5);
		gbc_lblOperation.gridx = 1;
		gbc_lblOperation.gridy = 2;
		pnlLeft.add(lblOperation, gbc_lblOperation);
		
		tglbtnSel = new JToggleButton("Select");
		tglbtnSel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.SELECT);
			}
		});
		GridBagConstraints gbc_tglbtnSel = new GridBagConstraints();
		gbc_tglbtnSel.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnSel.gridx = 1;
		gbc_tglbtnSel.gridy = 3;
		pnlLeft.add(tglbtnSel, gbc_tglbtnSel);
		btnAddSel.add(tglbtnSel);
		
		btnMod = new JButton("Modify");
		btnMod.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.modify();
			}
		});
		GridBagConstraints gbc_btnMod = new GridBagConstraints();
		gbc_btnMod.insets = new Insets(0, 0, 5, 5);
		gbc_btnMod.gridx = 1;
		gbc_btnMod.gridy = 4;
		pnlLeft.add(btnMod, gbc_btnMod);
		btnMod.setEnabled(false);
		
		btnDel = new JButton("Delete");
		btnDel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.delete();
			}
		});
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.insets = new Insets(0, 0, 5, 5);
		gbc_btnDel.gridx = 1;
		gbc_btnDel.gridy = 5;
		pnlLeft.add(btnDel, gbc_btnDel);
		btnDel.setEnabled(false);
		
		btnClr = new JButton("Clear");
		btnClr.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.clear();
			}
		});
		GridBagConstraints gbc_btnClr = new GridBagConstraints();
		gbc_btnClr.insets = new Insets(0, 0, 5, 5);
		gbc_btnClr.gridx = 1;
		gbc_btnClr.gridy = 6;
		pnlLeft.add(btnClr, gbc_btnClr);
		btnClr.setEnabled(false);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_2 = new GridBagConstraints();
		gbc_rigidArea_2.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_2.gridx = 1;
		gbc_rigidArea_2.gridy = 7;
		pnlLeft.add(rigidArea_2, gbc_rigidArea_2);
		
		JLabel lblAdd = new JLabel("Add Operations:");
		GridBagConstraints gbc_lblAdd = new GridBagConstraints();
		gbc_lblAdd.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdd.gridx = 1;
		gbc_lblAdd.gridy = 9;
		pnlLeft.add(lblAdd, gbc_lblAdd);
		
		tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.POINT);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPoint.gridx = 1;
		gbc_tglbtnPoint.gridy = 10;
		pnlLeft.add(tglbtnPoint, gbc_tglbtnPoint);
		tglbtnPoint.setSelected(true);
		
		btnAddSel.add(tglbtnPoint);
		
		tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.LINE);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnLine.gridx = 1;
		gbc_tglbtnLine.gridy = 11;
		pnlLeft.add(tglbtnLine, gbc_tglbtnLine);
		btnAddSel.add(tglbtnLine);
		
		tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.CIRCLE);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnCircle.gridx = 1;
		gbc_tglbtnCircle.gridy = 12;
		pnlLeft.add(tglbtnCircle, gbc_tglbtnCircle);
		btnAddSel.add(tglbtnCircle);
		
		tglbtnRect = new JToggleButton("Rectangle");
		tglbtnRect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.RECT);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnRect = new GridBagConstraints();
		gbc_tglbtnRect.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnRect.gridx = 1;
		gbc_tglbtnRect.gridy = 13;
		pnlLeft.add(tglbtnRect, gbc_tglbtnRect);
		btnAddSel.add(tglbtnRect);
		
		tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.DONUT);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnDonut = new GridBagConstraints();
		gbc_tglbtnDonut.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnDonut.gridx = 1;
		gbc_tglbtnDonut.gridy = 14;
		pnlLeft.add(tglbtnDonut, gbc_tglbtnDonut);
		btnAddSel.add(tglbtnDonut);
		
		tglbtnHexa = new JToggleButton("Hexagon");
		tglbtnHexa.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drwCtlr.setModeAddSel(ModeAddSel.HEXAA);
				drwCtlr.clearSelected();
			}
		});
		GridBagConstraints gbc_tglbtnHexa = new GridBagConstraints();
		gbc_tglbtnHexa.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnHexa.gridx = 1;
		gbc_tglbtnHexa.gridy = 15;
		pnlLeft.add(tglbtnHexa, gbc_tglbtnHexa);
		btnAddSel.add(tglbtnHexa);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidArea_5 = new GridBagConstraints();
		gbc_rigidArea_5.insets = new Insets(0, 0, 5, 5);
		gbc_rigidArea_5.gridx = 1;
		gbc_rigidArea_5.gridy = 16;
		pnlLeft.add(rigidArea_5, gbc_rigidArea_5);
		
		listClick = new JList<String>();
		listClick.setVisibleRowCount(2);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridx = 1;
		gbc_list.gridy = 17;
		pnlLeft.add(listClick, gbc_list);
		listClick.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listClick.setModel(click);
		click.addElement("1: (x1,y1)");
		click.addElement("2: (x2,y2)");
		
		JPanel pnlRight = new JPanel();
		pnlRight.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		GridBagConstraints gbc_pnlRight = new GridBagConstraints();
		gbc_pnlRight.insets = new Insets(0, 0, 5, 0);
		gbc_pnlRight.fill = GridBagConstraints.BOTH;
		gbc_pnlRight.gridx = 1;
		gbc_pnlRight.gridy = 1;
		contentPane.add(pnlRight, gbc_pnlRight);
		pnlRight.setLayout(new CardLayout(0, 0));
		
		drwView.setBackground(Color.WHITE);
		drwView.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				drwCtlr.setClick2(drwCtlr.getClick1());
				drwCtlr.setClick1(e.getX(), e.getY());
				click.removeAllElements();
				if (drwCtlr.getClick1() != null)
					click.addElement("1: " + "(" + drwCtlr.getClick1().getX() + "," + drwCtlr.getClick1().getY() + ")");
				else
					click.addElement("1: (x1,y1)");
				if (drwCtlr.getClick2() != null)
					click.addElement("2: " + "(" + drwCtlr.getClick2().getX() + "," + drwCtlr.getClick2().getY() + ")");
				else
					click.addElement("2: (x2,y2)");
				drwCtlr.click();
			}
		});
		pnlRight.add(drwView, "");
		
		JScrollPane scrlLog = new JScrollPane();
		GridBagConstraints gbc_scrlLog = new GridBagConstraints();
		gbc_scrlLog.insets = new Insets(0, 0, 0, 5);
		gbc_scrlLog.fill = GridBagConstraints.BOTH;
		gbc_scrlLog.gridx = 0;
		gbc_scrlLog.gridy = 2;
		gbc_scrlLog.gridwidth = 2;
		contentPane.add(scrlLog, gbc_scrlLog);
		
		listLog = new JList<String>();
		listLog.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrlLog.setViewportView(listLog);
		listLog.setModel(log);
		listLog.setEnabled(false);
	}
	
	public DrawingView getDrwView()
	{
		return drwView;
	}
	
	public void setDrwCtlr(DrawingController drawingController)
	{
		this.drwCtlr = drawingController;
	}
	
	public void info(String msg, String title)
	{
		JOptionPane.showMessageDialog(contentPane, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void warn(String msg, String title)
	{
		JOptionPane.showMessageDialog(contentPane, msg, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public int confirm(String msg, String title)
	{
		return JOptionPane.showConfirmDialog(contentPane, msg, title, JOptionPane.YES_NO_OPTION);
	}
	
	public int getLogSize()
	{
		return log.getSize();
	}
	
	public void log(String log)
	{
		this.log.addElement(log);
	}
	
	public void clearLog()
	{
		log.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent pce)
	{
		if (pce.getPropertyName().equals("selected"))
		{
			ArrayList<Integer> selected = (ArrayList<Integer>) pce.getNewValue();
			if (selected.isEmpty())
			{
				btnMod.setEnabled(false);
				btnDel.setEnabled(false);
				
				btnBtb.setEnabled(false);
				btnTb.setEnabled(false);
				btnTf.setEnabled(false);
				btnBtf.setEnabled(false);
			}
			else
			{
				if (((ArrayList<Integer>) pce.getNewValue()).size() == 1)
				{
					btnMod.setEnabled(true);
					int[] layers = drwCtlr.checkLayers();
					switch (layers[0])
					{
						case 0:
							btnBtb.setEnabled(false);
							btnTb.setEnabled(false);
							break;
						case 1:
							btnBtb.setEnabled(true);
							btnTb.setEnabled(true);
							break;
					}
					switch (layers[1])
					{
						case 0:
							btnTf.setEnabled(false);
							btnBtf.setEnabled(false);
							break;
						case 1:
							btnTf.setEnabled(true);
							btnBtf.setEnabled(true);
							break;
					}
				}
				else
				{
					btnMod.setEnabled(false);
					btnBtb.setEnabled(false);
					btnTb.setEnabled(false);
					btnTf.setEnabled(false);
					btnBtf.setEnabled(false);
				}
				btnDel.setEnabled(true);
			}
		}
		
		if (pce.getPropertyName().equals("shapes"))
		{
			ArrayList<Shape> shapes = (ArrayList<Shape>) pce.getNewValue();
			if (shapes.isEmpty())
				mntmExportDrawing.setEnabled(false);
			else
				mntmExportDrawing.setEnabled(true);
				
		}

		if (pce.getPropertyName().equals("step"))
		{
			int step = (int) pce.getNewValue();
			
			if (step >= 0)
			{
				listLog.setSelectedIndex(step);
				listLog.repaint();
				mntmExportFile.setEnabled(true);
				btnClr.setEnabled(true);
			}
			else
			{
				listLog.clearSelection();
				mntmExportFile.setEnabled(false);
				btnClr.setEnabled(false);
			}
				
			
			try
			{
				log.get(step - 1);
				mntmUndo.setEnabled(true);
			}
			catch (ArrayIndexOutOfBoundsException exception)
			{
				if (step == 0)
					mntmUndo.setEnabled(true);
				else
					mntmUndo.setEnabled(false);
			}
			
			try
			{
				log.get(step + 1);
				mntmRedo.setEnabled(true);
			}
			catch (ArrayIndexOutOfBoundsException exception)
			{
				mntmRedo.setEnabled(false);
			}
		}
	}
}
