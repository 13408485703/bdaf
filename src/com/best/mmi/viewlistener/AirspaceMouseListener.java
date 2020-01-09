package com.best.mmi.viewlistener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.best.mmi.data.airspace.Airspace;
import com.best.mmi.main.App;

public class AirspaceMouseListener implements MouseListener
{
	private int m_nType;
	
	public AirspaceMouseListener(int type)
	{
		m_nType = type;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == 1)
		{
			//if(arg0.getClickCount() == 2)
			{
				String name = ((JLabel)arg0.getSource()).getText();
				Airspace a = App.getApp().getAirspaceList().findAirspaceByName(name, m_nType);
				App.getApp().getAirspaceList().beginFlash(a);
				
				//System.out.println(name+" "+m_nType);
			}		
		}	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}