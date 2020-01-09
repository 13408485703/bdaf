package com.best.mmi.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.best.bdaf.view.subtype.MyVFlowLayout;
import com.best.mmi.data.ToolList;
import com.best.mmi.data.tooltype.Text;
import com.best.mmi.main.App;

public class CoordInputWinView extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField m_jTextName;
	private JTextField m_jTextLong;
	private JTextField m_jTextLat;
	private JButton m_jBtnOk;
	private JButton m_jBtnCancel;

	public CoordInputWinView(Window parent, String str) {
		super(parent, str);
		getContentPane().setLayout(new MyVFlowLayout());

		final JPanel panel1 = new JPanel();
		getContentPane().add(panel1);

		final JLabel label1 = new JLabel();
		panel1.add(label1);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setText("Input LatLon format:263430N0995644E");

		final JPanel panel2 = new JPanel();
		getContentPane().add(panel2);

		m_jTextLat = new JTextField();
		m_jTextLat.setColumns(6);
		panel2.add(m_jTextLat);

		final JLabel label2 = new JLabel();
		label2.setText("N");
		panel2.add(label2);

		m_jTextLong = new JTextField();
		m_jTextLong.setColumns(7);
		panel2.add(m_jTextLong);

		final JLabel label3 = new JLabel();
		label3.setText("E");
		panel2.add(label3);

		final JPanel panel3 = new JPanel();
		getContentPane().add(panel3);

		final JLabel label4 = new JLabel();
		label4.setText("Input fix name(default using LatLong as blank)");
		panel3.add(label4);

		final JPanel panel4 = new JPanel();
		getContentPane().add(panel4);

		m_jTextName = new JTextField();
		m_jTextName.setColumns(15);
		panel4.add(m_jTextName);

		final JPanel panel5 = new JPanel();
		getContentPane().add(panel5);

		m_jBtnCancel = new JButton();
		m_jBtnCancel.setPreferredSize(new Dimension(60, 25));
		m_jBtnCancel.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnCancel.setContentAreaFilled(true);
		m_jBtnCancel.setText("Cancel");
		m_jBtnCancel.addActionListener(this);

		m_jBtnOk = new JButton();
		m_jBtnOk.setContentAreaFilled(true);
		m_jBtnOk.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnOk.setPreferredSize(new Dimension(60, 25));
		m_jBtnOk.setText("OK");
		m_jBtnOk.addActionListener(this);
		panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel5.add(m_jBtnOk);
		panel5.add(m_jBtnCancel);

		pack();
		setLocationRelativeTo(parent);
	}

	public void init() {

	}

	@Override
	public void setVisible(boolean b) {
		if (b == true)
			clearText();
		super.setVisible(b);
	}

	public void clearText() {
		m_jTextLat.setText("");
		m_jTextLong.setText("");
		m_jTextName.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == m_jBtnOk) {
			if (isInputValid()) {
				String latLong = m_jTextLat.getText().trim() + "N"
						+ m_jTextLong.getText().trim()+ "E";
				Point distanceP = ToolList
						.convertLatLongStringToDistanceP(latLong);
				System.out.println(distanceP);
				String name = m_jTextName.getText().toUpperCase().trim();
				if(name.equals(""))
					name = ToolList.convertDistancePToLatLongString(distanceP,
							1);
				Text newText = new Text(distanceP, name);
				App.getApp().getToolList().addText(newText);
				setVisible(false);
			}
		} else if (arg0.getSource() == m_jBtnCancel) {
			setVisible(false);
		}
		App.getApp().getRadarView(0).drawAll();
	}

	public boolean isInputValid() {
		if (Pattern.matches("[0-9]{6}", m_jTextLat.getText().trim())
				&& Pattern.matches("[0-9]{7}", m_jTextLong.getText().trim()))
			return true;
		else
			return false;
	}

}