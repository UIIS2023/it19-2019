package dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import geometry.Point;

public class DlgPoint extends JDialog
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	
	private boolean ok;
	private Point p;
	private Color fillColor;
	
	private JTextField txtX;
	private JTextField txtY;
	
	JButton btnFillColor;

	public DlgPoint()
	{
		setTitle("Point");
		setModal(true);
		setResizable(false);
		setBounds((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 160) / 2, 
				(int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 175) / 2, 160, 175);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		{
			JLabel lblX = new JLabel("X:");
			GridBagConstraints gbc_lblX = new GridBagConstraints();
			gbc_lblX.anchor = GridBagConstraints.EAST;
			gbc_lblX.insets = new Insets(0, 0, 5, 5);
			gbc_lblX.gridx = 0;
			gbc_lblX.gridy = 0;
			contentPane.add(lblX, gbc_lblX);
		}
		{
			txtX = new JTextField();
			GridBagConstraints gbc_txtX = new GridBagConstraints();
			gbc_txtX.insets = new Insets(0, 0, 5, 0);
			gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX.gridx = 1;
			gbc_txtX.gridy = 0;
			contentPane.add(txtX, gbc_txtX);
			txtX.setColumns(10);
		}
		{
			JLabel lblY = new JLabel("Y:");
			GridBagConstraints gbc_lblY = new GridBagConstraints();
			gbc_lblY.anchor = GridBagConstraints.EAST;
			gbc_lblY.insets = new Insets(0, 0, 5, 5);
			gbc_lblY.gridx = 0;
			gbc_lblY.gridy = 1;
			contentPane.add(lblY, gbc_lblY);
		}
		{
			txtY = new JTextField();
			GridBagConstraints gbc_txtY = new GridBagConstraints();
			gbc_txtY.insets = new Insets(0, 0, 5, 0);
			gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY.gridx = 1;
			gbc_txtY.gridy = 1;
			contentPane.add(txtY, gbc_txtY);
			txtY.setColumns(10);
		}
		{
			btnFillColor = new JButton("Fill Color");
			btnFillColor.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					setFillColor(JColorChooser.showDialog(btnFillColor, "Fill Color", fillColor));
				}
			});
			GridBagConstraints gbc_btnFillColor = new GridBagConstraints();
			gbc_btnFillColor.gridx = 1;
			gbc_btnFillColor.gridy = 2;
			contentPane.add(btnFillColor, gbc_btnFillColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (txtX.getText().equals("") || txtY.getText().equals(""))
							JOptionPane.showMessageDialog(contentPane, "Please fill all input fields!", 
									"Warning", JOptionPane.WARNING_MESSAGE);
						else
						{
							try
							{
								int x = Integer.parseInt(txtX.getText());
								int y = Integer.parseInt(txtY.getText());
								p = new Point(x, y, false, fillColor);
								ok = true;
								setVisible(false);	
							}
							catch (NumberFormatException exception)
							{
								ok = false;
								JOptionPane.showMessageDialog(contentPane, "Please use only positive integers!", 
										"Warning", JOptionPane.WARNING_MESSAGE);
							}
							catch (IllegalArgumentException exception)
							{
					        	ok = false;
					        	JOptionPane.showMessageDialog(contentPane, exception.getMessage(), 
					        			"Warning", JOptionPane.WARNING_MESSAGE);
					        }
						}
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ok = false;
						setVisible(false);
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
	public boolean isOk()
	{
		return ok;
	}
	
	public Point getP()
	{
		return p;
	}
	
	public void setFillColor(Color fillColor)
	{
		if (fillColor != null)
		{
			this.fillColor = fillColor;
			btnFillColor.setBackground(fillColor);
			btnFillColor.setForeground(Color.getColor("", ~(fillColor.getRGB() & 0xFFFFFF)));
		}
	}
	
	public void setTxtXY(int x, int y)
	{
		txtX.setText(Integer.toString(x));
		txtY.setText(Integer.toString(y));
	}
}
