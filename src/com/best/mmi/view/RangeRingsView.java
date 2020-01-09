package com.best.mmi.view;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import com.best.bdaf.view.subtype.MyVFlowLayout;
import com.best.mmi.data.ConstantData;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.data.SysInfo.FuncStat;
import com.best.mmi.data.maptype.FixPoint;
import com.best.mmi.data.tooltype.RangeRing;
import com.best.mmi.main.App;
import javax.swing.JPanel;


public class RangeRingsView extends JDialog implements ActionListener
{
	private JButton m_jBtnClose;
	private JButton m_jBtnOk;
	private JCheckBox m_jCheckShowRings;
	private JButton m_jBtnPed;
	private JTextField m_jTextCenterlatLon;
	private JSpinner m_jSpinnerRingsScale;
	private JSpinner m_jSpinnerRingsNum;
	private RangeRing m_pRangeRing;
	
	private static final long serialVersionUID = 1L;
	
	
	public RangeRingsView(Window parent,String title)
	{
		super(parent,title);
		getContentPane().setLayout(new MyVFlowLayout());
		
		JPanel panel1 = new JPanel();	
		getContentPane().add(panel1);
		final JLabel labelNum = new JLabel();
		labelNum.setText("Ring Num:");
		panel1.add(labelNum);
		
		SpinnerNumberModel model = new SpinnerNumberModel(
				ConstantData.n_MinRangeRingNum, ConstantData.n_MinRangeRingNum, 
				ConstantData.n_MaxRangeRingNum, 1);
		m_jSpinnerRingsNum = new JSpinner(model);
		panel1.add(m_jSpinnerRingsNum);

		final JLabel labelScale = new JLabel();
		labelScale.setText("Ring Interval:");
		panel1.add(labelScale);
	
		SpinnerNumberModel modelScale = new SpinnerNumberModel(
				ConstantData.n_MinRangeRingScale, ConstantData.n_MinRangeRingScale, 
				ConstantData.n_MaxRangeRingScale, 1);
		m_jSpinnerRingsScale = new JSpinner(modelScale);
		panel1.add(m_jSpinnerRingsScale);

		JPanel panel2 = new JPanel();	
		getContentPane().add(panel2);
		final JLabel label = new JLabel();
		label.setText("Input LatLong(263706N0991607E),or PED");
		panel2.add(label);
		
		JPanel panel3 = new JPanel();	
		getContentPane().add(panel3);
		final JLabel labelLatLon = new JLabel();
		labelLatLon.setText("Center:");
		panel3.add(labelLatLon);
		
		m_jTextCenterlatLon = new JTextField();
		m_jTextCenterlatLon.setColumns(20);
		panel3.add(m_jTextCenterlatLon);
		
		m_jBtnPed = new JButton();
		m_jBtnPed.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnPed.setContentAreaFilled(true);
		m_jBtnPed.setText("PED");
		m_jBtnPed.addActionListener(this);
		panel3.add(m_jBtnPed);
		
		JPanel panel4 = new JPanel();	
		getContentPane().add(panel4);
		
		m_jCheckShowRings = new JCheckBox();
		m_jCheckShowRings.setText("Show Range Ring");
		panel4.add(m_jCheckShowRings);

		m_jBtnOk = new JButton();
		m_jBtnOk.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnOk.setContentAreaFilled(true);
		m_jBtnOk.setText("OK");
		m_jBtnOk.addActionListener(this);
		panel4.add(m_jBtnOk);

		m_jBtnClose = new JButton();
		m_jBtnClose.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnClose.setContentAreaFilled(true);
		m_jBtnClose.setText("Cancel");
		m_jBtnClose.addActionListener(this);
		panel4.add(m_jBtnClose);

		pack();
		setLocationRelativeTo(parent);
	}
	
	public void init()
	{
		m_pRangeRing = App.getApp().getToolList().getRangeRing();
		updateView();
	}
	
	public void updateView()
	{
		m_jSpinnerRingsNum.setValue(m_pRangeRing.getRingNum());
		m_jSpinnerRingsScale.setValue(m_pRangeRing.getRingScale()/1000);
		m_jCheckShowRings.setSelected(m_pRangeRing.getIsShow());
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == m_jBtnOk)
		{
			int ringNum = ((Integer)m_jSpinnerRingsNum.getValue()).intValue();
			int ringScale = ((Integer)m_jSpinnerRingsScale.getValue()).intValue()*1000;
			Point distanceP = null;
			String centerLatLon = m_jTextCenterlatLon.getText().toUpperCase().trim();
			if(centerLatLon.equals(""))
				distanceP = new Point();
			else if(centerLatLon.matches("[0-9]{6}N[0-9]{7}E"))
				distanceP = ToolList.convertLatLongStringToDistanceP(centerLatLon);
			else
			{
				FixPoint fp =App.getApp().getNavData().getFixPoint(centerLatLon);
				if(fp == null)
				{
					JOptionPane.showMessageDialog(this, "Can't find this fix: "+centerLatLon+" !");
					return;
				}	
				distanceP = new Point(fp.getDistanceX(),fp.getDistanceY());
			}
			boolean isShow = m_jCheckShowRings.isSelected();
			m_pRangeRing.resetRing(distanceP, ringScale, ringNum);
			m_pRangeRing.setIsShow(isShow);	
		}
		else if(o == m_jBtnClose)
		{
			updateView();
			setVisible(false);
		}
		else if(o == m_jBtnPed)
		{
			App.getApp().getSysInfo().setFuncStat(FuncStat.RANGE_RING_PED);
			setVisible(false);
		}
		App.getApp().getRadarView(0).drawAll();
	}
	
	public void ped(int x,int y,int radarViewNum)
	{
		App.getApp().getSysInfo().setFuncStat(FuncStat.NONE);
		Point distanceP = ToolList.fromScreenToDistance(new Point(x,y), radarViewNum);
		m_jTextCenterlatLon.setText(
				ToolList.convertDistancePToLatLongString(distanceP, 0));
		setVisible(true);
	}
}

