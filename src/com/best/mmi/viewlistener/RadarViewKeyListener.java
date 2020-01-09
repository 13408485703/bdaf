package com.best.mmi.viewlistener;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import com.best.mmi.data.ConstantData;
import com.best.mmi.data.RadarViewInfo;
import com.best.mmi.data.SysInfo;
import com.best.mmi.main.App;

public class RadarViewKeyListener implements KeyListener
{
	private int m_nRadarViewNum;
	private Robot m_jRobot;
	private Rectangle m_jRectangle;
	private String[] possibleCenters;
	
	public RadarViewKeyListener(int radarViewNum)
	{
		m_nRadarViewNum = radarViewNum;
		try
		{
			m_jRobot = new Robot();
			m_jRectangle= new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			possibleCenters =new String[App.getApp().getSysInfo().getHomeLength()];
			for(int i=0;i<possibleCenters.length;i++)
				possibleCenters[i] = "Center"+(i+1);
			
		} catch (AWTException e)
		{
			System.out.println("RadarViewKeyListener create Robot failed!");
			e.printStackTrace();
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		RadarViewInfo info = App.getApp().getSysInfo().getRadarViewInfo(m_nRadarViewNum);
		
		double d = info.getRadarScope();
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_PAGE_DOWN:
			if(d<ConstantData.d_MaxRadarScope)
				info.setRadarScope(d+info.getRadarScope()/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_PAGE_UP:
			if(d>ConstantData.d_MinRadarScope)
				info.setRadarScope(d-info.getRadarScope()/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_HOME:	
		    Object selectedCenter = JOptionPane.showInputDialog(App.getApp().getRadarView(m_nRadarViewNum),
		    	"Choose Center", "CNTR",     
		    JOptionPane.INFORMATION_MESSAGE, null,  possibleCenters, possibleCenters[0]);   
			if(selectedCenter == null)
				return;
		    int index = Integer.parseInt(((String)selectedCenter).substring(6));
			info.setRadarScope(App.getApp().getSysInfo().getHomeRadarScope(index-1));
			info.setOffCenterX(App.getApp().getSysInfo().getHomeOffCenterX(index-1));
			info.setOffCenterY(App.getApp().getSysInfo().getHomeOffCenterY(index-1));
			info.notifyObservers(0);
		    break;
		case KeyEvent.VK_UP:
			info.stepOffScreenY(info.getScreenSize().height/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_DOWN:
			info.stepOffScreenY(-info.getScreenSize().height/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_LEFT:
			info.stepOffScreenX(info.getScreenSize().height/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_RIGHT:
			info.stepOffScreenX(-info.getScreenSize().height/10);
			info.notifyObservers(0);
			break;
		case KeyEvent.VK_DELETE:
			
			break;
		case KeyEvent.VK_NUMPAD1:
			
			break;
		case KeyEvent.VK_NUMPAD2:
			
			break;
		case KeyEvent.VK_NUMPAD3:
			
			break;
		case KeyEvent.VK_ESCAPE:
			
			break;
		case KeyEvent.VK_F1:
			App.getApp().getCoordInputWinView().setVisible(true);
			break;
		case KeyEvent.VK_F2:
			break;
		case KeyEvent.VK_F3:
			App.getApp().getSysInfo().setMouseWalk(!App.getApp().getSysInfo().getMouseWalk());
			break;
		case KeyEvent.VK_F4:
			test();
			break;
		case KeyEvent.VK_PRINTSCREEN:
			break;
			
		default:
			break;
		}

	}
	
	public int getRadarViewNum() {return m_nRadarViewNum;}
	
	public void keyPressed(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void test()
	{
		//SendNetData.sendFplRequireToMrdp();
		//SendNetData.sendAidcTest();
		
		/*
		ArrayList<RouteFix> routeFixs1 = new ArrayList<RouteFix>(16);
		routeFixs1.add(new RouteFix("WWW", 4, "TWR", "1857", "", 200, 0));
		routeFixs1.add(new RouteFix("NIVUX", 4, "TWR", "1701", "", 200, 0));
		routeFixs1.add(new RouteFix("P413", 4, "TWR", "1703", "", 200, 0));
		routeFixs1.add(new RouteFix("ELASU", 4, "TWR", "1706", "", 200, 0));
		
		FplData fd = new FplData(12, -1, "CCA2289", "1234", "", "TWR","���Ժ", "���뻨԰",
			new ArrayList<String>(),routeFixs1);
		fd.setActype("A320");
		fd.setWake("M");
		fd.setFlightType("");
		fd.setFlightRule("");
		fd.setRvsm("W");
		fd.setEti("");
		fd.setAtd("2126");
		fd.setAta("");
		fd.setEtd("1200");
		fd.setEta("");
		fd.setFpStatus(0);
		fd.setFpControlStatus(0);
		
		
		SendNetData.sendFplDataToFdp(fd,4);
		*/
	}
}