package com.best.mmi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.best.bdaf.view.subtype.MyVFlowLayout;
import com.best.mmi.data.AirspaceList;
import com.best.mmi.data.MapData;
import com.best.mmi.data.MapList;
import com.best.mmi.data.SysInfo;
import com.best.mmi.main.App;

public class MapView extends JDialog implements ActionListener
{
	private JButton m_jBtnSaveColors;
	private JButton m_jBtnRings;
	private JCheckBox m_jBoxCenter;
	
	private JScrollPane spanel;
	private JPanel panel;
	private ArrayList<JCheckBox> m_sCheckBoxes;
	private HashMap<String,JButton> m_sBtns;
	private MapList m_pMapList;
	
	public MapView(Window parent,String str,MapList maplist)
	{
		super(parent,str);
		setLocationRelativeTo(parent);
		
		panel = new JPanel();
		panel.setLayout(new MyVFlowLayout());

		final JPanel panelNorth = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelNorth.setLayout(flowLayout);
		getContentPane().add(panelNorth, BorderLayout.NORTH);

		m_jBoxCenter = new JCheckBox();
		m_jBoxCenter.setText("Screen Center");
		if(App.getApp().getSysInfo().getShowCenter())
			m_jBoxCenter.setSelected(true);
		m_jBoxCenter.addActionListener(this);
		panelNorth.add(m_jBoxCenter);

		m_jBtnRings = new JButton();
		m_jBtnRings.setContentAreaFilled(true);
		m_jBtnRings.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnRings.setText("Range Ring");
		m_jBtnRings.addActionListener(this);
		panelNorth.add(m_jBtnRings);
		
		m_jBtnSaveColors = new JButton();
		m_jBtnSaveColors.setContentAreaFilled(true);
		m_jBtnSaveColors.setBorder(new BevelBorder(BevelBorder.RAISED));
		m_jBtnSaveColors.setText("Save Colors");
		m_jBtnSaveColors.addActionListener(this);
		panelNorth.add(m_jBtnSaveColors);
		
		spanel = new JScrollPane(panel);
		getContentPane().add(spanel);
	
		
		m_pMapList = maplist;
		m_sCheckBoxes = new ArrayList<JCheckBox>();
		m_sBtns = new HashMap<String,JButton>();
	}
	
	public void init()
	{
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new MyVFlowLayout());
		
		JScrollPane spanelSouth = new JScrollPane(panelSouth);
		getContentPane().add(spanelSouth, BorderLayout.SOUTH);
		
		AirspaceList al = App.getApp().getAirspaceList();
		for(int i=1;i<=19;i++)
		{
			JPanel tmpPanel = new JPanel();
			tmpPanel.setLayout(new GridLayout(1, 0));
							
			String mapName = null;
			Color c = null;
			boolean isShow = true;
			switch(i)
			{
			case 2:
				mapName = "DangerArea";
				c = al.getColorDanger();
				isShow = al.isShowDanger();
				break;
			case 11:
				mapName = "ForbidArea";
				c = al.getColorForbid();
				isShow = al.isShowForbid();
				break;
			case 12:
				mapName = "RestrictArea";
				c = al.getColorRestrict();
				isShow = al.isShowRestrict();
				break;
			default:
				continue;
			}
			
			JCheckBox jcb = new JCheckBox(mapName,true);
			jcb.setSelected(isShow);
			jcb.addActionListener(this);
			tmpPanel.add(jcb);
			
			
			JButton btn = new JButton(mapName);
			btn.setPreferredSize(new Dimension(30,20));
			btn.setBounds(new Rectangle(new Dimension(20,20)));
			btn.setBackground(c);
			btn.setForeground(c);
			
			btn.addActionListener(this);
			tmpPanel.add(btn);
			
			panelSouth.add(tmpPanel);
			
			m_sCheckBoxes.add(jcb);
			m_sBtns.put(mapName,btn);
		}		
			
		MapList ml = App.getApp().getMapList();
		ArrayList<String> mapNames = ml.getMapNames();	
		for(int i=0;i<mapNames.size();i++)
		{
			JPanel tmpPanel = new JPanel();
			tmpPanel.setLayout(new GridLayout(1, 0));
			
			String mapName = (String)mapNames.get(i);
			MapData md = ml.findMapDataFromName(mapName);
			
			JCheckBox jcb = new JCheckBox(mapName,true);
			jcb.setSelected(md.isShow());
			jcb.addActionListener(this);
			tmpPanel.add(jcb);
			
			JButton btn = new JButton(mapName);
			btn.setPreferredSize(new Dimension(30,20));
			btn.setBounds(new Rectangle(new Dimension(20,20)));
			btn.setBackground(md.getColor());
			btn.setForeground(md.getColor());
			btn.addActionListener(this);
			btn.setRolloverEnabled(false);
			btn.setFocusable(false);
			tmpPanel.add(btn);
			
			panel.add(tmpPanel);
			
			m_sCheckBoxes.add(jcb);
			m_sBtns.put(mapName,btn);	
		}		
	
		pack();
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o instanceof JCheckBox)
		{
			if(o == m_jBoxCenter)
			{
				App.getApp().getSysInfo().setShowCenter(m_jBoxCenter.isSelected());
			}
			else
			{
				boolean isShow = ((JCheckBox)o).isSelected();
				String str = ((JCheckBox)o).getText();
				if(str.equals("DangerArea"))
				{
					App.getApp().getAirspaceList().setShowDanger(isShow);		
				}
				else if(str.equals("ForbidArea"))
				{
					App.getApp().getAirspaceList().setShowForbid(isShow);		
				}
				else if(str.equals("RestrictArea"))
				{
					App.getApp().getAirspaceList().setShowRestrict(isShow);		
				}
				else				
					m_pMapList.findMapDataFromName(str).setIsShow(isShow);
			}
		}
		else if(o instanceof JButton)
		{
			if(o == m_jBtnRings)
			{
				App.getApp().getRangeRingsView().setVisible(true);
			}
			else if(o == m_jBtnSaveColors)
			{
				writeMapColorsToIni();
				writeMapShowsToIni();
			}
			else
			{
				JButton btnChoosing = (JButton)o;
				String str = btnChoosing.getText();
				Color colorChoosing = JColorChooser.showDialog(this, "Color Chooser", 
					App.getApp().getSysInfo().getSysColor("Default Map"));
				Color colorRecent = btnChoosing.getBackground();
				if(colorChoosing != null)
				{
					btnChoosing.setBackground(colorChoosing);
					btnChoosing.setForeground(colorChoosing);
					if(str.equals("DangerArea"))
					{
						App.getApp().getAirspaceList().setColorDanger(colorChoosing);	
					}
					else if(str.equals("ForbidArea"))
					{
						App.getApp().getAirspaceList().setColorForbid(colorChoosing);		
					}
					else if(str.equals("RestrictArea"))
					{
						App.getApp().getAirspaceList().setColorRestrict(colorChoosing);		
					}
					else				
						m_pMapList.findMapDataFromName(str).setColor(colorChoosing);
				}
				else
				{
					btnChoosing.setBackground(colorRecent);
					btnChoosing.setForeground(colorRecent);
				}
			}
		}
		App.getApp().getRadarView(0).drawAll();
	}
	
	public void writeMapColorsToIni()
	{
	
		try
		{
			File outFile = new File("tmpMapColors.tmp");
			outFile.createNewFile();
			File inFile = new File("resource//ini//private.ini");
			
		    FileInputStream fis = new FileInputStream(inFile);
		    BufferedReader in = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

		    FileOutputStream fos = new FileOutputStream(outFile);
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));

		    String thisLine;
		    
		    String strWrite = null;
			Color c = null;
			final String interval = "\n";
			
			ArrayList<String> mapNames = App.getApp().getMapList().getMapNames();
		    
		    while ((thisLine = in.readLine()) != null)
		    {
		    	out.write(thisLine+"\n");
		    	if(thisLine.equals("[Mapcolors]"))
		    	{
		    		for(int i=0;i<mapNames.size();i++)
					{

						String mapName = (String)mapNames.get(i);
						c = ((JButton)m_sBtns.get(mapName)).getBackground();
						strWrite = mapName+"="+c.getRed()+","+c.getGreen()+","+c.getBlue()+interval;
						out.write(strWrite);
					}	
		    		
		    		AirspaceList al = App.getApp().getAirspaceList();
		    		c = al.getColorQnh();
		    		strWrite = "QnhArea="+c.getRed()+","+c.getGreen()+","+c.getBlue()+interval;
					out.write(strWrite);
		    	
					c = al.getColorDanger();
		    		strWrite = "DangerArea="+c.getRed()+","+c.getGreen()+","+c.getBlue()+interval;
					out.write(strWrite);
						
					c = al.getColorForbid();
		    		strWrite = "ForbidArea="+c.getRed()+","+c.getGreen()+","+c.getBlue()+interval;
					out.write(strWrite);
					
					c = al.getColorRestrict();
		    		strWrite = "RestrictArea="+c.getRed()+","+c.getGreen()+","+c.getBlue()+interval;
					out.write(strWrite);
					
		    		out.write("\n");
		    		
		    		while((thisLine = in.readLine()) != null)
		    		{
		    			if(thisLine.startsWith("["))
		    			{
		    				out.write(thisLine+"\n");
		    				break;
		    			}		
		    		}
		    	}
		    }
		    out.flush();
		    out.close();
		    in.close();

		    inFile.delete();
		    outFile.renameTo(inFile);

		}
		catch(Exception e)
		{
			System.out.println("Restore mapColors to private.ini failed");
			e.printStackTrace();
		}	
	}
	
	public void writeMapShowsToIni()
	{
		try
		{
			File outFile = new File("tmpMapShows.tmp");
			outFile.createNewFile();
			File inFile = new File("resource//ini//private.ini");
			
		    FileInputStream fis = new FileInputStream(inFile);
		    BufferedReader in = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

		    FileOutputStream fos = new FileOutputStream(outFile);
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));

		    String thisLine;
		    
		    MapList ml = App.getApp().getMapList();
		    String strWrite = null;
			final String interval = "\n";
			
			ArrayList<String> mapNames = App.getApp().getMapList().getMapNames();
		    
		    while ((thisLine = in.readLine()) != null)
		    {
		    	out.write(thisLine+"\n");
		    	if(thisLine.equals("[Mapshows]"))
		    	{
		    		for(int i=0;i<mapNames.size();i++)
					{
						String mapName = (String)mapNames.get(i);
						MapData md = ml.findMapDataFromName(mapName);
						if(md.isShow() == true)
							strWrite = mapName+"=true"+interval;
						else
							strWrite = mapName+"=false"+interval;
						
						out.write(strWrite);
					}	
		    		
		    		AirspaceList al = App.getApp().getAirspaceList();
		    		strWrite = "QnhArea="+al.isShowQnh()+interval;
					out.write(strWrite);
		  
					
		    		strWrite = "DangerArea="+al.isShowDanger()+interval;
					out.write(strWrite);
					
					strWrite = "ForbidArea="+al.isShowForbid()+interval;
					out.write(strWrite);
					
					strWrite = "RestrictArea="+al.isShowRestrict()+interval;
					out.write(strWrite);
					
		    		out.write("\n");
		    		
		    		while((thisLine = in.readLine()) != null)
		    		{
		    			if(thisLine.startsWith("["))
		    			{
		    				out.write(thisLine+"\n");
		    				break;
		    			}		
		    		}
		    	}
		    }
		    out.flush();
		    out.close();
		    in.close();

		    inFile.delete();
		    outFile.renameTo(inFile);

		}
		catch(Exception e)
		{
			System.out.println("Restore mapShows to private.ini failed");
			e.printStackTrace();
		}	
	}
}

