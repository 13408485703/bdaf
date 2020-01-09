package com.best.mmi.viewlistener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import com.best.bdaf.view.SkpDetailView;
import com.best.mmi.data.ConstantData;
import com.best.mmi.data.RadarViewInfo;
import com.best.mmi.data.SkpPo;
import com.best.mmi.data.SysInfo.FuncStat;
import com.best.mmi.data.ToolList;
import com.best.mmi.data.tooltype.Rbl;
import com.best.mmi.data.tooltype.Text;
import com.best.mmi.main.App;
import com.best.mmi.view.GotoView;



public class RadarViewMouseListener implements MouseInputListener,MouseWheelListener
{
	private int m_nRadarViewNum;
	
	private int m_nBeginX;
	private int m_nBeginY;
	private int m_nButton;
	private Point m_sDistancePBegin;
	private Point m_sViewInfoDistanceOffP;
	private SkpPo m_sSkpPo;
	
	private int m_nBeginLabelOffsetX;
	private int m_nBeginLabelOffsetY;
	
	private Timer mouseTimer;
	private MouseEvent mouseEvent;
	
	private int m_nStep; 
	
	private FuncStat m_nLastFuncStat;
	
	public RadarViewMouseListener(int radarViewNum)
	{
		m_nRadarViewNum = radarViewNum;
		m_nStep = 0;
		mouseTimer = new Timer(300, new ActionListener() 
		{ 
			public void actionPerformed(ActionEvent evt) 
			{ 
				if(mouseEvent != null)
					mouseClickedOnce(mouseEvent);
				mouseTimer.stop(); 
				mouseEvent = null;
			} 
		});
	}
	
	public void mousePressed(MouseEvent e)
	{	
		m_nBeginX = e.getX();
		m_nBeginY = e.getY();
		
		m_sSkpPo = App.getApp().findTrackPoFromXy(e.getX(), e.getY(), m_nRadarViewNum);
		
		if(e.getButton() == 1) 
		{   
			m_nButton = 1;
			
			switch(App.getApp().getSysInfo().getFuncStat())
			{		
			case NONE:
				if(m_sSkpPo != null)
				{
					m_nBeginLabelOffsetX = m_sSkpPo.getLabelOffsetX(m_nRadarViewNum); 
					m_nBeginLabelOffsetY = m_sSkpPo.getLabelOffsetY(m_nRadarViewNum);	
				}
				break;
			case MOUSE_WALK:
				RadarViewInfo viewInfo = App.getApp().getSysInfo().getRadarViewInfo(m_nRadarViewNum);
				m_sViewInfoDistanceOffP = new Point(viewInfo.getOffCenterX(),viewInfo.getOffCenterY());
				break;
			}
		}
		else if(e.getButton() == 2)
		{
			m_nButton = 2;

		}	
		else if(e.getButton() == 3) 
		{
			m_nButton = 3;	

		}
	}
	
	public void mouseClickedOnce(MouseEvent e)
	{
		if(e.getButton() == 1) 
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case RANGE_RING_PED:
				App.getApp().getRangeRingsView().ped(e.getX(), e.getY(), m_nRadarViewNum);	
				break;
			}		
		}
		else if(e.getButton() == 2)
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				if(m_nStep == 0)
				{
					m_sDistancePBegin = ToolList.fromScreenToDistance(
							new Point(m_nBeginX,m_nBeginY), m_nRadarViewNum);
					App.getApp().getToolList().setTmpRblBegin(m_sDistancePBegin,99);
					
					m_nStep++;
				}
				else
				{
					Point screenPEnd = new Point(e.getX(),e.getY());
					Point distancePEnd = ToolList.fromScreenToDistance(screenPEnd,m_nRadarViewNum);
					if(m_sDistancePBegin != null)
					{							
						Point screenPBegin = ToolList.fromDistanceToScreen(m_sDistancePBegin, m_nRadarViewNum);
						if((Math.abs(screenPBegin.x-screenPEnd.x) > 10) || (Math.abs(screenPBegin.y-screenPEnd.y) > 10))
							App.getApp().getToolList().addRbl(new Rbl(m_sDistancePBegin,distancePEnd,99));
					}		
					App.getApp().getToolList().clearTmpRbl();
					m_nStep = 0;
				}
				break;
			}	
		}
		else if(e.getButton() == 3) 
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				if(m_sSkpPo != null)
				{
					//航迹右键菜单
					JPopupMenu pm = new JPopupMenu();
					JMenuItem mi = new JMenuItem("Show SKP");
					mi.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							new SkpDetailView(App.getApp().getRadarView(0)).popup(m_sSkpPo.getSkp());
						}
					});
					pm.add(mi);
					mi = new JMenuItem("Show Route");
					mi.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							m_sSkpPo.forceShowRoute = !m_sSkpPo.forceShowRoute;
							App.getApp().getRadarView(0).drawAll();
						}
					});
					pm.add(mi);
					pm.show(App.getApp().getRadarView(m_nRadarViewNum), e.getX()+5, e.getY());		
				}	
				
				App.getApp().getToolList().clearTmpRbl();
				m_nStep = 0;
				break;
			case RANGE_RING_PED:
				App.getApp().getRangeRingsView().setVisible(true);
				break;
			case MOUSE_WALK:
				App.getApp().getSysInfo().setFuncStat(m_nLastFuncStat);
				break;
			}			
		}
		App.getApp().getRadarView(0).drawAll();
	}

	public void mouseClickedDouble(MouseEvent e)
	{
		if(e.getButton() == 1) 
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				Rbl rblNow = App.getApp().getToolList().findRblFromXy(e.getX(), e.getY(),m_nRadarViewNum);
				Text textNow = App.getApp().getToolList().findTextFromXy(e.getX(), e.getY(),m_nRadarViewNum);
				if(rblNow != null)
					App.getApp().getToolList().removeRbl(rblNow);
				if(textNow != null)
					App.getApp().getToolList().removeText(textNow);
				break;
			}
		}
		else if(e.getButton() == 2)
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				RadarViewInfo info = App.getApp().getSysInfo().getRadarViewInfo(m_nRadarViewNum);
				Point offCenterP = ToolList.fromScreenToDistance(new Point(e.getX(),e.getY()), m_nRadarViewNum);
				info.setOffCenterX(-offCenterP.x);
				info.setOffCenterY(offCenterP.y);
				info.notifyObservers(0);
				GotoView.moveToDistanceXY(offCenterP, true);
				break;
			}
		}
		else if(e.getButton() == 3)
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				App.getApp().getMouseRightMenuView().popUp(e.getX(), e.getY(), m_nRadarViewNum);
				break;
			}
		}
		App.getApp().getRadarView(0).drawAll();
	}

	public void mouseMoved(MouseEvent e) 
	{
		if(m_nRadarViewNum == 0)
			App.getApp().getRadarView(m_nRadarViewNum).getMainToolView().updateLatLon(e.getX(), e.getY());
		
		switch(App.getApp().getSysInfo().getFuncStat())
		{
		case NONE:
			if(m_nStep != 0)
			{
				Point endP = ToolList.fromScreenToDistance(
					new Point(e.getX(),e.getY()), m_nRadarViewNum);
				App.getApp().getToolList().setTmpRblEnd(endP);		
				
				App.getApp().getRadarView(0).drawAll();
			}
			break;
		}
	}
	
	public void mouseDragged(MouseEvent e) 
	{
		if(m_nRadarViewNum == 0)
			App.getApp().getRadarView(m_nRadarViewNum).getMainToolView().updateLatLon(e.getX(), e.getY());
		
		if(m_nButton == 1)
		{
			switch(App.getApp().getSysInfo().getFuncStat())
			{
			case NONE:
				if(m_sSkpPo != null)
				{
					int newlabeloffsetx = e.getX()-m_nBeginX;
					int newlabeloffsety = e.getY()-m_nBeginY;		
					m_sSkpPo.setLabelOffset(newlabeloffsetx+m_nBeginLabelOffsetX, newlabeloffsety+m_nBeginLabelOffsetY,m_nRadarViewNum);
				}
				App.getApp().getRadarView(0).drawAll();
				break;
			case MOUSE_WALK:
				RadarViewInfo viewInfo = App.getApp().getSysInfo().getRadarViewInfo(m_nRadarViewNum);
				int diffScreenX = e.getX() - m_nBeginX;
				int diffScreenY = e.getY() - m_nBeginY;
				int diffDistanceX = (int)((double)diffScreenX / viewInfo.getXScale());
				int diffDistanceY = (int)((double)diffScreenY / viewInfo.getXScale());
				viewInfo.setOffCenterX(m_sViewInfoDistanceOffP.x+diffDistanceX);
				viewInfo.setOffCenterY(m_sViewInfoDistanceOffP.y+diffDistanceY);
				viewInfo.notifyObservers(0);
				break;
			}	
		}
	}
			
	
	
	public void mouseReleased(MouseEvent e)
	{
		
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		mouseEvent = e;
		if (e.getClickCount() == 1) 
		{ 
			mouseTimer.restart(); 
		} 
		else if (e.getClickCount() == 2 && mouseTimer.isRunning()) 
		{ 
			mouseClickedDouble(e);
			mouseTimer.stop();  
			mouseEvent = null;
		}
	}
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		// TODO Auto-generated method stub
		if(App.getApp().getSysInfo().getMouseWalk() == true)
		{
			RadarViewInfo info = App.getApp().getSysInfo().getRadarViewInfo(m_nRadarViewNum);
			
			double d = info.getRadarScope();
			
			if(e.getWheelRotation() > 0)
			{
				if(App.getApp().getSysInfo().getFuncStat() != FuncStat.MOUSE_WALK)
				{
					m_nLastFuncStat = App.getApp().getSysInfo().getFuncStat();
					App.getApp().getSysInfo().setFuncStat(FuncStat.MOUSE_WALK);
				}

				if(d<ConstantData.d_MaxRadarScope)
					info.setRadarScope(d+info.getRadarScope()/10);
				info.notifyObservers(0);
			}
			else
			{
				if(App.getApp().getSysInfo().getFuncStat() != FuncStat.MOUSE_WALK)
				{
					m_nLastFuncStat = App.getApp().getSysInfo().getFuncStat();
					App.getApp().getSysInfo().setFuncStat(FuncStat.MOUSE_WALK);
				}
				
				if(d>ConstantData.d_MinRadarScope)
					info.setRadarScope(d-info.getRadarScope()/10);
				info.notifyObservers(0);
			}
		}	
	}

	public void clearMouseStat()
	{
		m_nStep = 0;
		m_sDistancePBegin = null;
		App.getApp().getToolList().clearTmpRbl();
		App.getApp().getToolList().clearTmpEllipse();
		App.getApp().getToolList().clearTmpRect();
		App.getApp().getToolList().clearTmpAirspace();
	}
}