package com.best.mmi.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.best.mmi.data.ToolList;
import com.best.mmi.data.tooltype.Text;
import com.best.mmi.main.App;

public class MouseRightMenuView extends JPopupMenu implements ActionListener
{
	private int m_nScreenX;
	private int m_nScreenY;
	private int m_nRadarViewNum;
	
	private JMenuItem m_jItemQuickText;
	private JMenuItem m_jItemClearRbl;
	private JMenuItem m_jItemGrib;
	private JMenuItem m_jItemClearText;

	public MouseRightMenuView()
	{
		super();
		
		m_jItemQuickText = new JMenuItem("Free Text");
		m_jItemQuickText.addActionListener(this);
		add(m_jItemQuickText);
		
		m_jItemGrib = new JMenuItem("Show Grib");
		m_jItemGrib.addActionListener(this);
		add(m_jItemGrib);
		
		m_jItemClearRbl = new JMenuItem("Clear RBLs");
		m_jItemClearRbl.addActionListener(this);
		add(m_jItemClearRbl);
		
		m_jItemClearText = new JMenuItem("Clear Free Texts");
		m_jItemClearText.addActionListener(this);
		add(m_jItemClearText);

	}
	
	public void init()
	{
		
	}
	
	public void popUp(int x,int y,int radarViewNum)
	{
		m_nScreenX = x;
		m_nScreenY = y;
		m_nRadarViewNum = radarViewNum;
		
		if(radarViewNum == 0)
		{
			show(App.getApp().getRadarView(0), x, y+30);
		}
		else if(radarViewNum == 1)
		{
			
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == m_jItemQuickText)
		{
			Point distanceP = ToolList.fromScreenToDistance(new Point(m_nScreenX,m_nScreenY), 
				m_nRadarViewNum);
			String strDefault = ToolList.convertDistancePToLatLongString(distanceP,1);
			String input = JOptionPane.showInputDialog(App.getApp().getRadarView(0), 
				"Input fix name,default using LatLong as blank.","Input",JOptionPane.INFORMATION_MESSAGE);
			
			String name = null;
			if(input == null)
				return;
			if(input.trim().equals(""))
				name = strDefault;
			else
				name = input;
			Text newText = new Text(distanceP,name);
			App.getApp().getToolList().addText(newText);	
			
		}
		else if(o == m_jItemGrib)
		{
			
		}
		else if(o == m_jItemClearRbl)
		{
			App.getApp().getToolList().removeAllRbl();
		}
		else if(o == m_jItemClearText)
		{
			App.getApp().getToolList().removeAllText();
		}
		App.getApp().getRadarView(0).drawAll();
	}
}