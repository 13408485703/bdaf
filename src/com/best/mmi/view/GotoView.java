package com.best.mmi.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.best.mmi.data.ConstantData;
import com.best.mmi.data.RadarViewInfo;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.data.maptype.FixPoint;
import com.best.mmi.main.App;



public class GotoView extends JDialog implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	private JLabel m_jLabelPrompt;
	private JTextField m_jTextField;
	private JCheckBox m_jCBPed;
	
	public static Robot robot;
	private JPanel panel_1;
	private JLabel lblTitle;
	
	public GotoView(Window parent,String title)
	{
		super(parent,title);
		getContentPane().setLayout(new BorderLayout());		

		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		getContentPane().add(panel, BorderLayout.SOUTH);

		m_jLabelPrompt = new JLabel();
		m_jLabelPrompt.setText("Input");
		panel.add(m_jLabelPrompt);
		
		m_jCBPed = new JCheckBox("Only mouse move to");
		panel.add(m_jCBPed);

		m_jTextField = new JTextField();
		panel.add(m_jTextField);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblTitle = new JLabel("Goto fix using fix name,or LatLong(\"264045N0990403E\"");
		panel_1.add(lblTitle);
		m_jTextField.addKeyListener(this);	
		
		pack();
		setLocationRelativeTo(parent);
	}

	public void init()
	{
		try{
			robot = new Robot();		
		}catch(Exception e) {System.out.println("GotoView init failed!");e.printStackTrace();}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		String input = ((JTextField)arg0.getSource()).getText().toUpperCase().trim();
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(Pattern.matches("[0-9]{6}N[0-9]{7}E", input))
			{
				processLatLon(input);
			}
			else
			{
				processName(input);		
			}
			App.getApp().getRadarView(0).drawAll();
		}
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void processLatLon(String str)
	{
		Point distanceP = ToolList.convertLatLongStringToDistanceP(str);
		if(distanceP != null)
			moveToDistanceXY(distanceP,m_jCBPed.isSelected());
	}
	
	public void processName(String str)
	{
		String strPos = com.best.bdaf.main.App.getApp().getNavData().getFixnames().get(str);
		if(strPos != null)
		{
			Point distanceP = ToolList.convertLatLongStringToDistanceP(strPos);
			if(distanceP != null)
				moveToDistanceXY(distanceP,m_jCBPed.isSelected());
		}	
	}

	public static void moveToDistanceXY(Point p,boolean ped)
	{
		if(ped == false)
		{
			RadarViewInfo info0 = App.getApp().getSysInfo().getRadarViewInfo(0);
			info0.setOffCenterX(-p.x);
			info0.setOffCenterY(p.y);
			info0.notifyObservers(0);
			RadarViewInfo info1 = App.getApp().getSysInfo().getRadarViewInfo(1);
			info1.setOffCenterX(-p.x);
			info1.setOffCenterY(p.y);
			info1.notifyObservers(0);  
		}
		else 
		{		
			Point screenP = ToolList.fromDistanceToScreen(p, 0);
			Point windowLocation = App.getApp().getRadarView(0).getLocation();
			Point newScreenP = new Point(windowLocation.x+screenP.x, windowLocation.y+screenP.y);
			robot.mouseMove(newScreenP.x, newScreenP.y); 
		}	
	}
}