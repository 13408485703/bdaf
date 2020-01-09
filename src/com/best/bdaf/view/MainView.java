package com.best.bdaf.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.ConstantPara;
import com.best.bdaf.data.IniData;
import com.best.bdaf.main.App;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener
{
	private JPanel panelFpl;
	private JPanel panelExe;
	private JPanel panelMerge;
	
	private JMenuBar menuBar;
	private JTextArea taLog;
	
	private JMenuItem itemAbout;
	private JMenuItem itemClearLog;
	
	private String line;
	private JPanel panel;
	private JLabel labelAbout;
	private JTabbedPane tabbedPane;
	
	public MainView()
	{
		super("--BEST Sim Convert Facility--");
		setSize(1600, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initEnv();
			
		JScrollPane scrollPaneS = new JScrollPane();
		scrollPaneS.setViewportBorder(new TitledBorder(null, "System Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(scrollPaneS, BorderLayout.SOUTH);
		
		
		scrollPaneS.setPreferredSize(new Dimension(scrollPaneS.getWidth(), 200));
		taLog = new JTextArea();
		taLog.setEditable(false);
		scrollPaneS.setViewportView(taLog);	
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelFpl = new FplPanel();
		tabbedPane.addTab("REC TO FPL", null, panelFpl, null);
		
		panelExe = new ExePanel();
		tabbedPane.addTab("FPL TO SKP", null, panelExe, null);
		
		panelMerge = new MergePanel();
		tabbedPane.addTab("MERGE EXE", null, panelMerge, null);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuSetup = new JMenu("Setup");
		menuBar.add(menuSetup);
		
		itemAbout = new JMenuItem("About");
		itemAbout.addActionListener(this);
		menuSetup.add(itemAbout);
		
		itemClearLog = new JMenuItem("Clear Log");
		itemClearLog.addActionListener(this);
		menuSetup.add(itemClearLog);
		
		panel = new JPanel();
		menuBar.add(panel);
		
		labelAbout = new JLabel("About");
		panel.add(labelAbout);
	}
	

	public void initEnv()
	{
		line = System.getProperty("line.separator");
	
		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
//		    UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		} catch (Exception e) {AppLogger.error(e);}	
			
	}
	
	public void init()
	{
		IniData id = App.getApp().getIniData();
		setSize(Integer.parseInt(id.getSysPara("Config", "WindowWidth")), 
			Integer.parseInt(id.getSysPara("Config", "WindowHeight")));
		setLocationRelativeTo(null);
		
		labelAbout.setText("Version:  "+ConstantPara.str_version);
		((ExePanel)panelExe).init();
		
		boolean onlyEditMode = Boolean.parseBoolean(
			App.getApp().getIniData().getSysPara("Config", "OnlyEditMode"));
		if(onlyEditMode == true)
			tabbedPane.removeTabAt(0);
		
		setVisible(true);
	}

	public void log(String str)
	{
		//modelLog.addElement(str);
		taLog.insert(str+line, 0);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object src = e.getSource();
		if(src == itemAbout)
		{
			JOptionPane.showMessageDialog(this, ConstantPara.str_version, 
					"System Version", JOptionPane.INFORMATION_MESSAGE);	
		}
		else if(src == itemClearLog)
		{
			taLog.setText("");
		}
		
	}
	
	public ExePanel getExprepView()
	{
		return (ExePanel)panelExe;
	}
}
