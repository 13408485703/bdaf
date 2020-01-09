package com.best.mmi.view;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;


public class MainToolView extends JPanel implements ActionListener {
	
	private JLabel lblLatLon;
	private JButton btnMap;
	private JButton btnRangeRing;
	private JButton btnGoto;
	private JButton btnSkpPoControl;
	
	public MainToolView() {
		
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		lblLatLon = new JLabel("00 00 00N 000 00 00E");
		add(lblLatLon);
		
		btnMap = new JButton("Map");
		btnMap.addActionListener(this);
		add(btnMap);
		
		btnRangeRing = new JButton("Range Ring");
		btnRangeRing.addActionListener(this);
		add(btnRangeRing);
		
		btnGoto = new JButton("Goto");
		btnGoto.addActionListener(this);
		add(btnGoto);
		
		btnSkpPoControl = new JButton("SkpPo Control");
		btnSkpPoControl.addActionListener(this);
		add(btnSkpPoControl);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object src = arg0.getSource();
		if(src == btnMap)
		{
			App.getApp().getMapView().setVisible(true);
		}
		else if(src == btnRangeRing)
		{
			App.getApp().getRangeRingsView().setVisible(true);
		}
		else if(src == btnGoto)
		{
			App.getApp().getGotoView().setVisible(true);
		}
		else if(src == btnSkpPoControl)
		{
			App.getApp().getSkpPoControlView().setVisible(true);
		}
	}
	
	public void updateLatLon(int x, int y)
	{
		Point distanceP = ToolList.fromScreenToDistance(new Point(x,y),0);
		lblLatLon.setText(ToolList.convertDistancePToLatLongString(distanceP,1));	
	}

}
