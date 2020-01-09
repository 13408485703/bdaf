package com.best.mmi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import com.best.bdaf.data.IniData;
import com.best.mmi.data.ConstantData;
import com.best.mmi.data.RadarViewInfo;
import com.best.mmi.data.SkpPo;
import com.best.mmi.data.observer.Observer;
import com.best.mmi.main.App;


public class RadarView extends JFrame implements Observer,WindowFocusListener
{
	
	private static final long serialVersionUID = 1L;
	
	private MainToolView m_pMainToolView;
	private RadarCanvas m_pRadarCanvas;
	
	private boolean m_bDrawing;
	
	public RadarView(String str,int radarViewNum)
	{
		super(str);
		setBackground(ConstantData.Color_RadarViewBackground);
		getContentPane().setBackground(ConstantData.Color_RadarViewBackground);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
		
		setSize(App.getApp().getSysInfo().getRadarViewInfo(0).getScreenSize());
		setLocationRelativeTo(null);
		setResizable(false);
			
		m_bDrawing = false;
			
		m_pMainToolView = new MainToolView();
		getContentPane().add(m_pMainToolView,BorderLayout.NORTH);
		
		m_pRadarCanvas = new RadarCanvas(true,radarViewNum);
		getContentPane().add(m_pRadarCanvas,BorderLayout.CENTER);
		
		addWindowFocusListener(this);	
	}
	
	
	public void init()
	{
		App.getApp().getSysInfo().getRadarViewInfo(0).registerObserver(this);
		App.getApp().getSysInfo().getRadarViewInfo(0).notifyObservers(0);
	}
	
	
	public void drawAll()
	{
		m_bDrawing = true;		
		repaint();
		m_bDrawing = false;	
	}
	
	public boolean isDrawing() {return m_bDrawing;}
	public MainToolView getMainToolView() {return m_pMainToolView;}
	public RadarCanvas getRadarCanvas() {return m_pRadarCanvas;}
	
	public void updateColor()
	{
		Color c = App.getApp().getSysInfo().getSysColors().get("RadarView");
		if(c != null)
			setBackground(c);
	}
	
	@Override
	public void update(Object obj, int part)
	{
		if(part == 0)
		{
			if(obj instanceof RadarViewInfo)
			{
				App.getApp().getAirspaceList().updateScreenPosition(0);
				App.getApp().getMapList().updateScreenPosition(0);
				App.getApp().getToolList().updateScreenPosition(0);
				ArrayList<SkpPo> sps = App.getApp().getSkpPos();
				for(SkpPo sp : sps)
					sp.updateScreenPosition(0);
				
				if(!isDrawing())
					drawAll();
			}
		}
	}


	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		m_pRadarCanvas.requestFocus();
	}


	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}


