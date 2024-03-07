package dialogs;

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

import adapter.HexagonAdapter;

public class DlgHexagon extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public boolean isOk;

	private JTextField txtCenterX;
	private JTextField txtCenterY;
	private JTextField txtRadius;
	private HexagonAdapter hexagon;

	private JButton btnOutlineColor;
	private JButton btnInnerColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setModal(true);
		setTitle("Add/Modify Hexagon");
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 225);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(100, 149, 237));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 25, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCenterPointX = new JLabel("Center point X:");
			lblCenterPointX.setForeground(new Color(255, 255, 255));
			GridBagConstraints gbc_lblCenterPointX = new GridBagConstraints();
			gbc_lblCenterPointX.anchor = GridBagConstraints.EAST;
			gbc_lblCenterPointX.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterPointX.gridx = 0;
			gbc_lblCenterPointX.gridy = 0;
			contentPanel.add(lblCenterPointX, gbc_lblCenterPointX);
		}
		{
			txtCenterX = new JTextField();
			txtCenterX.setForeground(new Color(30, 144, 255));
			GridBagConstraints gbc_txtCenterX = new GridBagConstraints();
			gbc_txtCenterX.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterX.gridx = 2;
			gbc_txtCenterX.gridy = 0;
			contentPanel.add(txtCenterX, gbc_txtCenterX);
			txtCenterX.setColumns(10);
		}
		{
			JLabel lblCenterPointY = new JLabel("Center point Y:");
			lblCenterPointY.setForeground(new Color(255, 255, 255));
			GridBagConstraints gbc_lblCenterPointY = new GridBagConstraints();
			gbc_lblCenterPointY.anchor = GridBagConstraints.EAST;
			gbc_lblCenterPointY.insets = new Insets(0, 0, 5, 5);
			gbc_lblCenterPointY.gridx = 0;
			gbc_lblCenterPointY.gridy = 1;
			contentPanel.add(lblCenterPointY, gbc_lblCenterPointY);
		}
		{
			txtCenterY = new JTextField();
			txtCenterY.setForeground(new Color(30, 144, 255));
			GridBagConstraints gbc_txtCenterY = new GridBagConstraints();
			gbc_txtCenterY.anchor = GridBagConstraints.NORTH;
			gbc_txtCenterY.insets = new Insets(0, 0, 5, 0);
			gbc_txtCenterY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCenterY.gridx = 2;
			gbc_txtCenterY.gridy = 1;
			contentPanel.add(txtCenterY, gbc_txtCenterY);
			txtCenterY.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Radius:");
			lblHeight.setForeground(new Color(255, 255, 255));
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.anchor = GridBagConstraints.EAST;
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 2;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtRadius = new JTextField();
			txtRadius.setForeground(new Color(30, 144, 255));
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.gridx = 2;
			gbc_txtRadius.gridy = 2;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblOutlineColor = new JLabel("Outline color:");
			lblOutlineColor.setForeground(new Color(255, 255, 255));
			GridBagConstraints gbc_lblOutlineColor = new GridBagConstraints();
			gbc_lblOutlineColor.anchor = GridBagConstraints.EAST;
			gbc_lblOutlineColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblOutlineColor.gridx = 0;
			gbc_lblOutlineColor.gridy = 3;
			contentPanel.add(lblOutlineColor, gbc_lblOutlineColor);
		}
		{
			btnOutlineColor = new JButton("");
			btnOutlineColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color outlineColor = JColorChooser.showDialog(null, "Choose a outline Color",
							btnOutlineColor.getBackground());
					if (outlineColor != null)
						btnOutlineColor.setBackground(outlineColor);
				}
			});
			btnOutlineColor.setBackground(Color.BLACK);
			GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
			gbc_btnOutlineColor.fill = GridBagConstraints.BOTH;
			gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 0);
			gbc_btnOutlineColor.gridx = 2;
			gbc_btnOutlineColor.gridy = 3;
			contentPanel.add(btnOutlineColor, gbc_btnOutlineColor);
		}
		{
			JLabel lblInnerColor = new JLabel("Inner color:");
			lblInnerColor.setForeground(new Color(255, 255, 255));
			GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
			gbc_lblInnerColor.anchor = GridBagConstraints.EAST;
			gbc_lblInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblInnerColor.gridx = 0;
			gbc_lblInnerColor.gridy = 4;
			contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		}
		{
			btnInnerColor = new JButton("");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Color innerColor = JColorChooser.showDialog(null, "Choose a inner Color",
							btnInnerColor.getBackground());
					if (innerColor != null)
						btnInnerColor.setBackground(innerColor);
				}
			});
			btnInnerColor.setBackground(Color.WHITE);
			GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
			gbc_btnInnerColor.fill = GridBagConstraints.BOTH;
			gbc_btnInnerColor.gridx = 2;
			gbc_btnInnerColor.gridy = 4;
			contentPanel.add(btnInnerColor, gbc_btnInnerColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(100, 149, 237));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setForeground(new Color(30, 144, 255));
				okButton.setBackground(new Color(255, 255, 255));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (txtCenterX.getText().trim().isEmpty() || txtCenterY.getText().trim().isEmpty()
									|| txtRadius.getText().trim().isEmpty()) {
								isOk = false;
								JOptionPane.showMessageDialog(null, "Enter all values!", "Error",
										JOptionPane.ERROR_MESSAGE);
							} else {
								if (Integer.parseInt(txtRadius.getText().toString()) <= 0
										|| Integer.parseInt(txtCenterX.getText().toString()) < 0
										|| Integer.parseInt(txtCenterY.getText().toString()) < 0) {
									JOptionPane.showMessageDialog(null, "Enter values greater then 0!", "Error",
											JOptionPane.ERROR_MESSAGE);
								} else {
									hexagon = new HexagonAdapter(Integer.parseInt(getTxtCenterX().getText().toString()),
											Integer.parseInt(getTxtCenterY().getText().toString()),
											Integer.parseInt(getTxtRadius().getText().toString()),
											btnOutlineColor.getBackground(), btnInnerColor.getBackground());

									isOk = true;
									setVisible(false);
								}
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setForeground(new Color(30, 144, 255));
				cancelButton.setBackground(new Color(255, 255, 255));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTxtCenterX() {
		return txtCenterX;
	}

	public JTextField getTxtCenterY() {
		return txtCenterY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public HexagonAdapter getHexagon() {
		return hexagon;
	}

	public void setHexagon(HexagonAdapter hexagon) {
		this.hexagon = hexagon;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

}
