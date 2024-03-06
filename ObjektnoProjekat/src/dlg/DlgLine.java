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

import geometry.Line;

public class DlgLine extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	
	private boolean ok;
	private Line l;
	private Color fillColor;
	
	private JTextField txtX1;
	private JTextField txtY1;
	private JTextField txtX2;
	private JTextField txtY2;

	JButton btnFillColor;
	
	public DlgLine()
	{
		setTitle("Line");
		setModal(true);
		setResizable(false);
		setBounds((int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 155) / 2, 
				(int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 210) / 2, 155, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		{
			JLabel lblX1 = new JLabel("X1:");
			GridBagConstraints gbc_lblX1 = new GridBagConstraints();
			gbc_lblX1.anchor = GridBagConstraints.EAST;
			gbc_lblX1.insets = new Insets(0, 0, 5, 5);
			gbc_lblX1.gridx = 0;
			gbc_lblX1.gridy = 0;
			contentPane.add(lblX1, gbc_lblX1);
		}
		{
			txtX1 = new JTextField();
			GridBagConstraints gbc_txtX1 = new GridBagConstraints();
			gbc_txtX1.insets = new Insets(0, 0, 5, 0);
			gbc_txtX1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX1.gridx = 1;
			gbc_txtX1.gridy = 0;
			contentPane.add(txtX1, gbc_txtX1);
			txtX1.setColumns(10);
		}
		{
			JLabel lblY1 = new JLabel("Y1:");
			GridBagConstraints gbc_lblY1 = new GridBagConstraints();
			gbc_lblY1.anchor = GridBagConstraints.EAST;
			gbc_lblY1.insets = new Insets(0, 0, 5, 5);
			gbc_lblY1.gridx = 0;
			gbc_lblY1.gridy = 1;
			contentPane.add(lblY1, gbc_lblY1);
		}
		{
			txtY1 = new JTextField();
			GridBagConstraints gbc_txtY1 = new GridBagConstraints();
			gbc_txtY1.insets = new Insets(0, 0, 5, 0);
			gbc_txtY1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY1.gridx = 1;
			gbc_txtY1.gridy = 1;
			contentPane.add(txtY1, gbc_txtY1);
			txtY1.setColumns(10);
		}
		{
			JLabel lblX2 = new JLabel("X2:");
			GridBagConstraints gbc_lblX2 = new GridBagConstraints();
			gbc_lblX2.anchor = GridBagConstraints.EAST;
			gbc_lblX2.insets = new Insets(0, 0, 5, 5);
			gbc_lblX2.gridx = 0;
			gbc_lblX2.gridy = 2;
			contentPane.add(lblX2, gbc_lblX2);
		}
		{
			txtX2 = new JTextField();
			GridBagConstraints gbc_txtX2 = new GridBagConstraints();
			gbc_txtX2.insets = new Insets(0, 0, 5, 0);
			gbc_txtX2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX2.gridx = 1;
			gbc_txtX2.gridy = 2;
			contentPane.add(txtX2, gbc_txtX2);
			txtX2.setColumns(10);
		}
		{
			JLabel lblY2 = new JLabel("Y2:");
			GridBagConstraints gbc_lblY2 = new GridBagConstraints();
			gbc_lblY2.anchor = GridBagConstraints.EAST;
			gbc_lblY2.insets = new Insets(0, 0, 5, 5);
			gbc_lblY2.gridx = 0;
			gbc_lblY2.gridy = 3;
			contentPane.add(lblY2, gbc_lblY2);
		}
		{
			txtY2 = new JTextField();
			GridBagConstraints gbc_txtY2 = new GridBagConstraints();
			gbc_txtY2.insets = new Insets(0, 0, 5, 0);
			gbc_txtY2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY2.gridx = 1;
			gbc_txtY2.gridy = 3;
			contentPane.add(txtY2, gbc_txtY2);
			txtY2.setColumns(10);
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
			gbc_btnFillColor.gridy = 4;
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
						if (txtX1.getText().equals("") || txtY1.getText().equals("") || 
								txtX2.getText().equals("") || txtY2.getText().equals(""))
							JOptionPane.showMessageDialog(contentPane, "Please fill all input fields!", 
									"Warning", JOptionPane.WARNING_MESSAGE);
						else
						{
							try
							{
								l = new Line(Integer.parseInt(txtX1.getText()), Integer.parseInt(txtY1.getText()), 
										Integer.parseInt(txtX2.getText()), Integer.parseInt(txtY2.getText()));
								l.setSelected(false);
								l.setFillColor(fillColor);
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
	
	public Line getL()
	{
		return l;
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
	
	public void setTxtXY1(int x, int y)
	{
		txtX1.setText(Integer.toString(x));
		txtY1.setText(Integer.toString(y));
	}
	
	public void setTxtXY2(int x, int y)
	{
		txtX2.setText(Integer.toString(x));
		txtY2.setText(Integer.toString(y));
	}
}
