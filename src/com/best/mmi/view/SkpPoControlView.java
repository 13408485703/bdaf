package com.best.mmi.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.best.bdaf.view.subtype.MyVFlowLayout;
import com.best.mmi.data.SkpPo;
import com.best.mmi.data.SkpPoUtils;
import com.best.mmi.main.App;

public class SkpPoControlView extends JDialog implements ActionListener,ChangeListener
{
	private ArrayList<SkpPo> sps;
	
	private JComboBox cbSkpPo;
	private JButton btnAddOne;
	private JButton btnClearOne;
	private JButton btnAddAll;
	private JButton btnClearAll;
	private JCheckBox cbOnlyShowCP;
	private JSlider sliderTime;
	private JLabel lblTime;
	private int lastTime;
	private boolean forward = true;
	private JButton btnForward1;
	private JButton btnBackward1;
	private JButton btnBackward5;
	private JButton btnForward5;
	
	public SkpPoControlView(JDialog parent,String title)
	{
		super(parent, title);
		
		getContentPane().setLayout(new MyVFlowLayout());
		
		JPanel panel1 = new JPanel();
		getContentPane().add(panel1);
		
		cbSkpPo = new JComboBox();
		cbSkpPo.addItem("TEST001");
		panel1.add(cbSkpPo);
		
		btnAddOne = new JButton("Add One");
		btnAddOne.addActionListener(this);
		panel1.add(btnAddOne);
		
		btnClearOne = new JButton("Clear One");
		btnClearOne.addActionListener(this);
		panel1.add(btnClearOne);
		
		JPanel panel2 = new JPanel();
		getContentPane().add(panel2);
		
		cbOnlyShowCP = new JCheckBox("Only Show Current Pos");
		cbOnlyShowCP.addActionListener(this);
		panel2.add(cbOnlyShowCP);
		
		btnAddAll = new JButton("Add All");
		btnAddAll.addActionListener(this);
		panel2.add(btnAddAll);
		
		btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(this);
		panel2.add(btnClearAll);
		
		JPanel panel3 = new JPanel();
		getContentPane().add(panel3);
		
		btnBackward1 = new JButton("Backward-1");
		btnBackward1.addActionListener(this);
		
		btnBackward5 = new JButton("Backward-5");
		btnBackward5.addActionListener(this);
		panel3.add(btnBackward5);
		panel3.add(btnBackward1);
		
		JLabel lblTimeTitle = new JLabel("Time Axis:");
		panel3.add(lblTimeTitle);
		
		sliderTime = new JSlider();
		sliderTime.setValue(0);
		sliderTime.setMinimum(0);
		sliderTime.setMaximum(120);
		sliderTime.setMinorTickSpacing(10);
		sliderTime.setMajorTickSpacing(30);
		sliderTime.setPaintLabels(true);
		sliderTime.setPaintTicks(true);
		sliderTime.addChangeListener(this);
		panel3.add(sliderTime);
		sliderTime.setEnabled(false);
		
		lastTime = sliderTime.getValue();
		lblTime = new JLabel(String.format("%03d", sliderTime.getValue()));
		panel3.add(lblTime);
		
		btnForward1 = new JButton("Forward+1");
		btnForward1.addActionListener(this);
		panel3.add(btnForward1);
		
		btnForward5 = new JButton("Forward+5");
		btnForward5.addActionListener(this);
		panel3.add(btnForward5);
		
		pack();
		setLocationRelativeTo(parent);
	}
	
	public void init()
	{
		
	}
	
	public void reloadSkpPos(ArrayList<SkpPo> sps)
	{
		this.sps = sps;
		cbSkpPo.removeAllItems();
		for(SkpPo sp : sps)
			cbSkpPo.addItem(sp.getSkp().getCallsign());
				
		if(cbOnlyShowCP.isSelected() == false)
			cbOnlyShowCP.doClick();
		btnAddAll.doClick();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == btnAddOne)
		{
			sps.get(cbSkpPo.getSelectedIndex()).setShow(true);	
		}
		else if(o == btnClearOne)
		{
			sps.get(cbSkpPo.getSelectedIndex()).setShow(false);
		}
		else if(o == btnAddAll)
		{
			for(SkpPo sp :sps)
				sp.setShow(true);
		}
		else if(o == btnClearAll)
		{
			for(SkpPo sp :sps)
				sp.setShow(false);
		}
		else if(o == cbOnlyShowCP)
		{
			ArrayList<SkpPo> sps = App.getApp().getSkpPos();
			boolean b = cbOnlyShowCP.isSelected();
			for(SkpPo sp:sps)
				sp.setOnlyShowCurrentPos(b);
		}
		else if(o== btnBackward1)
		{
			sliderTime.setValue(sliderTime.getValue()-1);
		}
		else if(o == btnForward1)
		{
			sliderTime.setValue(sliderTime.getValue()+1);
		}
		else if(o== btnBackward5)
		{
			sliderTime.setValue(sliderTime.getValue()-5);
		}
		else if(o == btnForward5)
		{
			sliderTime.setValue(sliderTime.getValue()+5);
		}
		App.getApp().getRadarView(0).drawAll();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == sliderTime)
		{
			int time = sliderTime.getValue()*60;
			lblTime.setText(String.format("%03d", sliderTime.getValue()));

			int dtime = time-lastTime;
			if(dtime > 0)
			{
				for(SkpPo sp : sps)
				{
					if(forward == true)
						sp.nextRouteNum = sp.nextRouteNum;
					else
						sp.nextRouteNum = sp.nextRouteNum+1;
					sp.realNextRouteNum = sp.nextRouteNum;
					for(int i=0;i<dtime;i++)
						SkpPoUtils.updateSimTrack(sp, 1, lastTime+i+1);
					sp.updateScreenPosition(0);
				}	
				
				forward = true;
			}
			else if(dtime < 0)
			{
				for(SkpPo sp : sps)
				{
					if(forward == false)
						sp.nextRouteNum = sp.nextRouteNum;
					else
						sp.nextRouteNum = sp.nextRouteNum-1;
					sp.realNextRouteNum = sp.nextRouteNum+1;
					for(int i=0;i<Math.abs(dtime);i++)
						SkpPoUtils.updateSimTrack(sp, -1, lastTime-i-1);
					sp.updateScreenPosition(0);
				}	
	
				forward = false;
			}

			App.getApp().getRadarView(0).drawAll();
			
			lastTime = time;
		}
	}
}
