package com.best.bdaf.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.best.bdaf.dao.LocalFlightPlanDB;
import com.best.bdaf.dao.Skp;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.BdafUtils;
import com.best.bdaf.main.App;
import com.best.bdaf.view.subtype.ChangedTableCell;
import com.best.bdaf.view.subtype.MyVFlowLayout;

import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class ExePanel extends JPanel implements ActionListener,TableModelListener
{
	private JFormattedTextField fieldTime;
	private JButton btnBeginConvert;
	private JComboBox<String> cbExe;
	private JTextField fieldPilot;
	private JButton btnRemoveSkp;
	private JButton btnEditSkp;
	private JButton btnRefreshExe;
	private JButton btnAddSkp;
	private JButton btnDupSkp;
	private JTable tableSkp;
	private TableModel tableModelSkp;
	private String[] table_title = {"No","Callsign","AAT","ETD","EET","RQS FL","FL","CFL","TAS","ACTYPE","ADEP","ADES",
			"SSRA","SSRB","Pilot","EST","Route"};
	private boolean isSearching = false;
	private String editValue;
	private ArrayList<ChangedTableCell> changedTableCells = new ArrayList<ChangedTableCell>();
	
	private ArrayList<Skp> readedSkps;
	private JTextField fieldDuration;
	private JPanel panelSectornames;
	private ArrayList<JCheckBox> cbSectornames = new ArrayList<JCheckBox>();
	private JButton btnPreviewExe;
	private JButton btnBatchFillEet;
	private JButton btnBatchFillSsr;
	private JButton btnSaveAllSkp;
	private JTextField fieldReplaceFrom;
	private JTextField fieldReplaceTo;
	private JButton btnReplace;
	private JComboBox cbReplaceType;
	private JTextField fieldSearch;
	private JComboBox cbSearchType;
	private JButton btnSearch;
	private JPanel panel1;
	private JPanel panel2;
	private JScrollPane spanel3;
	private JButton btnBatchADSB;

	private ArrayList<String> sameCallsigns = new ArrayList<String>();
	private HashMap<String,Color> sameCallsignColors = new HashMap<String,Color>();
	private JRadioButton rbtnReality;
	private JRadioButton rbtnAftn;
	private JRadioButton rbtnSector;
	private JRadioButton rbtnFdrg;
	
	public ExePanel() 
	{
		Vector<String> vexe = new Vector<String>(999);
		for(int i=1;i<=999;i++)
			vexe.add(String.format("%03d", i));
				
		setLayout(new BorderLayout(0, 0));
				
		JPanel panelN = new JPanel();
		panelN.setLayout(new MyVFlowLayout());
		add(panelN, BorderLayout.NORTH);
		
		panel1 = new JPanel();
		panelN.add(panel1);
		
		JLabel label1 = new JLabel("Please input convert conditions: ");
		panel1.add(label1);
		
		JPanel panelExeNo = new JPanel();
		panel1.add(panelExeNo);
		
		JLabel lbl1 = new JLabel("Exe No: ");
		panelExeNo.add(lbl1);
		cbExe = new JComboBox<String>(vexe);
		panelExeNo.add(cbExe);
		
		cbExe.setSelectedIndex(892);
		
		panel2 = new JPanel();
		panelN.add(panel2);
		panel2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelTime = new JPanel();
		panel2.add(panelTime);
		
		JLabel labelTime = new JLabel("Time: ");
		panelTime.add(labelTime);
		
		DateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd--HH:mm");
		fieldTime = new JFormattedTextField(sdf_time);
		fieldTime.setColumns(17);
		fieldTime.setValue(new Date());
		panelTime.add(fieldTime);
		
		JPanel panelDuration = new JPanel();
		panel2.add(panelDuration);
		
		JLabel lblDuration = new JLabel("Duration: ");
		panelDuration.add(lblDuration);
		
		fieldDuration = new JTextField();
		fieldDuration.setText("0200");
		panelDuration.add(fieldDuration);
		fieldDuration.setColumns(10);
		
		JPanel panelPilot = new JPanel();
		panel2.add(panelPilot);
		
		JLabel labelPilot = new JLabel("Sum of pilot: ");
		panelPilot.add(labelPilot);
		
		fieldPilot = new JTextField();
		fieldPilot.setText("2");
		panelPilot.add(fieldPilot);
		fieldPilot.setColumns(10);
		
		JPanel panelRouteFrom = new JPanel();
//		panel2.add(panelRouteFrom);
		
		JLabel labelRouteFrom = new JLabel("Route From:");
		panelRouteFrom.add(labelRouteFrom);
		
		rbtnReality = new JRadioButton("Reality");
		rbtnReality.setSelected(true);
		panelRouteFrom.add(rbtnReality);
		
		rbtnAftn = new JRadioButton("AFTN");
		panelRouteFrom.add(rbtnAftn);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rbtnReality);
		bg1.add(rbtnAftn);
		
		JPanel panelRouteRange = new JPanel();
		panel2.add(panelRouteRange);
		
		JLabel labelRouteRange = new JLabel("Route Range:");
		panelRouteRange.add(labelRouteRange);
		
		rbtnSector = new JRadioButton("Sector");
		rbtnSector.setSelected(true);
		panelRouteRange.add(rbtnSector);
		
		rbtnFdrg = new JRadioButton("FDRG");
		panelRouteRange.add(rbtnFdrg);
		
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rbtnSector);
		bg2.add(rbtnFdrg);
		
		
		JPanel panelBeginConvert = new JPanel();
		panel2.add(panelBeginConvert);
		
		btnBeginConvert = new JButton("Begin Convert");
		btnBeginConvert.addActionListener(this);
		panelBeginConvert.add(btnBeginConvert);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		tableModelSkp = new DefaultTableModel(table_title, 0);
		tableModelSkp.addTableModelListener(this);
		tableSkp = new JTable(tableModelSkp) {
			
			//编辑结束前获取编辑前的值
			public void editingStopped(ChangeEvent ce)
			{
				int r = getEditingRow();
				int c = getEditingColumn();
				editValue = (String)tableSkp.getValueAt(r, c);

				TableCellEditor tce = getCellEditor();
				if(tce != null)
				{
					Object obj = tce.getCellEditorValue();
					setValueAt(obj, r, c);
					removeEditor();
				}
			}
			
			public boolean isCellEditable(int row, int column) {
			    if(tableSkp.getColumnName(column).equals("No")) 
			    	return false;
			    else
			    	return true;
			}
		};
		tableSkp.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableSkp.setAutoscrolls(false);
		tableSkp.getColumnModel().getColumn(tableSkp.getColumnCount()-1).setPreferredWidth(30*50);

		
		TableColumnModel tcm = tableSkp.getColumnModel();
	    for (int i = 0; i < tcm.getColumnCount(); i++) 
	    {
		    TableColumn tc = tcm.getColumn(i);
		    tc.setCellRenderer(new ThisTableColorRender());
	    }
		scrollPane.setViewportView(tableSkp);
		
		JPanel panelS = new JPanel();
		panelS.setLayout(new MyVFlowLayout());
		add(panelS, BorderLayout.SOUTH);
		
		JPanel panelS1 = new JPanel();
		panelS.add(panelS1);
		
		JPanel panelS2 = new JPanel();
		panelS.add(panelS2);
		
		btnAddSkp = new JButton("Add SKP");
		btnAddSkp.addActionListener(this);
		
		btnDupSkp = new JButton("Dup SKP");
		btnDupSkp.addActionListener(this);
		
		btnEditSkp = new JButton("Edit SKP");
		btnEditSkp.addActionListener(this);
		
		btnSaveAllSkp = new JButton("Save all SKPs");
		btnSaveAllSkp.addActionListener(this);
		btnSaveAllSkp.setBackground(Color.ORANGE);
		
		panelS1.add(btnSaveAllSkp);
		panelS1.add(btnEditSkp);
		panelS1.add(btnAddSkp);
		panelS1.add(btnDupSkp);
		
		btnRemoveSkp = new JButton("Remove SKP");
		btnRemoveSkp.setBackground(Color.RED);
		btnRemoveSkp.addActionListener(this);
		panelS1.add(btnRemoveSkp);
		
		btnRefreshExe = new JButton("Reload Exe");
		btnRefreshExe.setBackground(Color.CYAN);
		btnRefreshExe.addActionListener(this);
		panelS1.add(btnRefreshExe);
		
		btnPreviewExe = new JButton("Preview Exe");
		btnPreviewExe.addActionListener(this);
		panelS1.add(btnPreviewExe);
		
		btnBatchFillEet = new JButton("Fill EET");
		btnBatchFillEet.addActionListener(this);
		panelS1.add(btnBatchFillEet);
		
		btnBatchFillSsr = new JButton("Fill SSR");
		btnBatchFillSsr.addActionListener(this);
		panelS1.add(btnBatchFillSsr);
		
		btnBatchADSB = new JButton("Fill ADSB");
		btnBatchADSB.addActionListener(this);
		panelS1.add(btnBatchADSB);
		
		JLabel lblSearch = new JLabel("Search key: ");
		panelS2.add(lblSearch);
		
		cbSearchType = new JComboBox(new String[]{
			"Callsign","Actype",});	
		panelS2.add(cbSearchType);
		
		fieldSearch = new JTextField();
		fieldSearch.setColumns(10);
		panelS2.add(fieldSearch);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		panelS2.add(btnSearch);
		
		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		panelS2.add(horizontalStrut2);
		
		JLabel lblGlobeReplace = new JLabel("Globe Replace:");
		panelS2.add(lblGlobeReplace);
		
		cbReplaceType = new JComboBox(
			new String[] {"Route","Actype",});
		panelS2.add(cbReplaceType);
		
		JLabel lblReplaced = new JLabel("Replaced");
		panelS2.add(lblReplaced);
		
		fieldReplaceFrom = new JTextField();
		panelS2.add(fieldReplaceFrom);
		fieldReplaceFrom.setColumns(10);
		
		JLabel lblBy = new JLabel("By");
		panelS2.add(lblBy);
		
		fieldReplaceTo = new JTextField();
		panelS2.add(fieldReplaceTo);
		fieldReplaceTo.setColumns(10);
		
		btnReplace = new JButton("Replace");
		btnReplace.addActionListener(this);
		panelS2.add(btnReplace);
		
		
		panelSectornames = new JPanel();
		panelSectornames.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		spanel3 = new JScrollPane(panelSectornames);
		spanel3.setPreferredSize(new Dimension(getSize().width,60));
		panelN.add(spanel3);
		
		JLabel lblSectornames = new JLabel("Functional Sectors: ");
		panelSectornames.add(lblSectornames);
	}
	
	public void init()
	{
		ArrayList<String> sectornames = App.getApp().getNavData().getSectornames();
		for(String sectorname : sectornames)
		{
			JCheckBox cb = new JCheckBox(sectorname);
			cbSectornames.add(cb);
			panelSectornames.add(cb);
		}
		
		boolean onlyEditMode = Boolean.parseBoolean(
			App.getApp().getIniData().getSysPara("Config", "OnlyEditMode"));
		if(onlyEditMode == true)
		{
			panel2.setVisible(false);
			spanel3.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o == btnBeginConvert)
		{		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try
					{
						btnBeginConvert.setEnabled(false);
						
						String exenum = (String)cbExe.getSelectedItem();
						Date time = (Date)fieldTime.getValue();
						String duration = fieldDuration.getText();
						int durationsecs = Integer.parseInt(duration.substring(0,2))*3600+Integer.parseInt(duration.substring(2,4))*60;
						int pilot = Integer.parseInt(fieldPilot.getText());
						ArrayList<String> choosedsectornames = new ArrayList<String>();
						for(JCheckBox cb : cbSectornames)
						{
							if(cb.isSelected())
								choosedsectornames.add(cb.getText());
						}
						
						
						ArrayList<LocalFlightPlanDB> lfps = new ArrayList<LocalFlightPlanDB>(App.getApp().getDbService().getLocalFlightPlanDBs());
						Collections.sort(lfps);
						
						ArrayList<Skp> skps = BdafUtils.getSkpsFromFpls(exenum, time, durationsecs, choosedsectornames, pilot, 
							rbtnReality.isSelected(),rbtnSector.isSelected(),lfps);
						BdafUtils.writeSkpsToExe(exenum, skps);
						reloadSkps(exenum,null,null);
						AppLogger.info("Convert to exe file and reload it finished!");
					}
					catch (Exception ex) {
						AppLogger.error(ex);
						AppLogger.info("Convert to exe file and reload it failed, please try again!");
					}
					finally
					{
						btnBeginConvert.setEnabled(true);
					}
				}
			}).start();
		}
		else if(o == btnRefreshExe)
		{
			try
			{
				String exenum = (String)cbExe.getSelectedItem();
				reloadSkps(exenum,null,null);
				AppLogger.info("Reload exe file finished!");
			}catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Reload exe file failed, please try again!");
			}
		}
		else if(o == btnEditSkp)
		{
			try
			{
				int selectrow = tableSkp.getSelectedRow();
				if(selectrow == -1)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), "Should choose a SKP!");
					return;
				}
				else
				{
					String exenum = (String)cbExe.getSelectedItem();
					new SkpDetailView(App.getApp().getMainView()).popup(readedSkps, selectrow+1, exenum, true);
				}
			}catch (Exception ex) {
				AppLogger.error(ex);
			}	
		}
		else if(o == btnAddSkp)
		{
			try
			{		
				String exenum = (String)cbExe.getSelectedItem();
				new SkpDetailView(App.getApp().getMainView()).popup(readedSkps, -1, exenum, false);
				
			}catch (Exception ex) {
				AppLogger.error(ex);
			}	
		}
		else if(o == btnDupSkp)
		{
			try
			{		
				int selectrow = tableSkp.getSelectedRow();
				if(selectrow == -1)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), "Should choose a SKP!");
					return;
				}
				else
				{
					//在选中行下方增加复制该行
					Skp skp = readedSkps.get(selectrow);
					Skp newSkp = (Skp)skp.clone();
					readedSkps.add(selectrow, newSkp);
					
					String exenum = (String)cbExe.getSelectedItem();
					BdafUtils.writeSkpsToExe(exenum, readedSkps);
					BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
					reloadSkps(exenum,null,null);
					AppLogger.info("Dup SKP and reload exe file finished!");
				}
				
			}catch (Exception ex) {
				AppLogger.error(ex);
			}	
		}
		else if(o == btnRemoveSkp)
		{
			try
			{
				int selectrow = tableSkp.getSelectedRow();
				if(selectrow == -1)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), "Should choose a SKP!");
					return;
				}
				else
				{
					int r = JOptionPane.showConfirmDialog(App.getApp().getMainView(), "Sure to remove SKP?", 
							"Confirm do", JOptionPane.YES_NO_OPTION);
					if(r != JOptionPane.YES_OPTION)
						return;
					
					readedSkps.remove(selectrow);
					String exenum = (String)cbExe.getSelectedItem();
					BdafUtils.writeSkpsToExe(exenum, readedSkps);
					BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
					reloadSkps(exenum,null,null);
					AppLogger.info("Remove SKP and reload exe file finished!");
				}
			}catch (Exception ex) {
				AppLogger.info("Remove SKP and reload exe file failed, please try again!");
				AppLogger.error(ex);
			}	
		}
		else if(o == btnPreviewExe)
		{
			try
			{
				String exenum = (String)cbExe.getSelectedItem();
				this.readedSkps = BdafUtils.getSkpsFromExeFile(exenum);
				com.best.mmi.main.App.getApp().previewExe(readedSkps);
			}
			catch (Exception ex) {
				AppLogger.info("Preview exe file failed, please try again!");
				AppLogger.error(ex);
			}		
		}
		else if(o == btnSaveAllSkp)
		{
			try
			{
				if(readedSkps==null || readedSkps.size()==0)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), "Skps is empty!");
					return;
				}
				int r = JOptionPane.showConfirmDialog(App.getApp().getMainView(), "Sure to save all SKPs?", 
						"Confirm do", JOptionPane.YES_NO_OPTION);
				if(r != JOptionPane.YES_OPTION)
					return;
				
				int rows = tableSkp.getRowCount();						
				for(int i=0;i<rows;i++)
				{
					Skp skp = readedSkps.get(i);
					
					skp.setCallsign((String)tableModelSkp.getValueAt(i, 1));
					skp.setAat(skp.getAatFromString((String)tableModelSkp.getValueAt(i, 2)));
					skp.setEtd(skp.getEtdFromString((String)tableModelSkp.getValueAt(i, 3)));
					skp.setEet(skp.getEetFromString((String)tableModelSkp.getValueAt(i, 4)));
					int request_fl_m = skp.getRequest_flFromString((String)tableModelSkp.getValueAt(i, 5));
					float request_fl_im = request_fl_m*10/0.3048f;
					skp.setRequested_fl_m(request_fl_m);
					skp.setRequested_fl_im(request_fl_im);
					int fl_m = skp.getFlFromString((String)tableModelSkp.getValueAt(i, 6));
					float fl_im = fl_m*10/0.3048f;
					skp.setFl_m(fl_m);
					skp.setFl_im(fl_im);			
					int cfl_m = skp.getCflFromString((String)tableModelSkp.getValueAt(i, 7));
					float cfl_im = cfl_m*10/0.3048f;
					skp.setCfl_m(cfl_m);
					skp.setCfl_im(cfl_im);			
					int tas_m = skp.getTasFromString((String)tableModelSkp.getValueAt(i, 8));
					float tas_im = tas_m/1.852f;
					skp.setTas_m(tas_m);
					skp.setTas_im(tas_im);
					skp.setActype((String)tableModelSkp.getValueAt(i, 9));
					skp.setAdep((String)tableModelSkp.getValueAt(i, 10));
					skp.setDest((String)tableModelSkp.getValueAt(i, 11));
					skp.setSsra(Integer.parseInt((String)tableModelSkp.getValueAt(i, 12)));
					skp.setSsrb(Integer.parseInt((String)tableModelSkp.getValueAt(i, 13)));
					skp.setPilot(Integer.parseInt((String)tableModelSkp.getValueAt(i, 14)));
					skp.setTime_for_est(skp.getTime_for_estFromString((String)tableModelSkp.getValueAt(i, 15)));
					skp.setRoute((String)tableModelSkp.getValueAt(i, 16));
				}
				
				String exenum = (String)cbExe.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenum, readedSkps);
				BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
				reloadSkps(exenum,null,null);
				AppLogger.info("Save all skps finished!");
				
			}catch (Exception ex) {
				AppLogger.info("Remove SKP and reload exe file failed, please try again!");
				AppLogger.error(ex);
			}
		}
		else if(o == btnReplace)
		{
			try
			{
				if(readedSkps==null || readedSkps.size()==0)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), 
						"Skps is empty! Must reload exe file first.");
					return;
				}
				switch(cbReplaceType.getSelectedIndex())
				{
				case 0: //Route
					for(Skp readed_skp : readedSkps)
					{
						String oldRoute = readed_skp.getRoute();
						String newRoute = oldRoute.replace(
							fieldReplaceFrom.getText(), fieldReplaceTo.getText());
						readed_skp.setRoute(newRoute);
					}
					break;
				case 1: //Actype
					for(Skp readed_skp : readedSkps)
					{
						String oldActype = readed_skp.getActype();
						String newActype = oldActype.replace(
							fieldReplaceFrom.getText(), fieldReplaceTo.getText());
						readed_skp.setActype(newActype);
					}
					break;
				}
				String exenum = (String)cbExe.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenum, readedSkps);
				BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
				reloadSkps(exenum,null,null);
				
				AppLogger.info("Globe Replace OK!");
			}
			catch (Exception ex) {
				AppLogger.info("Globe Replace failed, please try again!");
				AppLogger.error(ex);
			}
		}
		else if(o == btnSearch)
		{
			try
			{
				String exenum = (String)cbExe.getSelectedItem();
				reloadSkps(exenum,(String)cbSearchType.getSelectedItem(),fieldSearch.getText().trim());
				
				AppLogger.info("Search skps in exe file finished!");
			}catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Search skps in exe file failed, please try again!");
			}
		}
		else if(o == btnBatchFillEet)
		{
			try
			{
				int r = JOptionPane.showConfirmDialog(App.getApp().getMainView(), 
					"Sure to fill EET using DEP&DES from hist-data for all skps?", 
					"Confirm choice", JOptionPane.YES_NO_OPTION);
				if(r != JOptionPane.YES_OPTION)
					return;
				if(readedSkps==null || readedSkps.size()==0)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), 
						"Skps is empty! Must reload exe file first.");
					return;
				}
				//使用DEP、DES在计划表中匹配EET时间，匹配不上则使用准备好的默认值0200
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 02);
				cal.set(Calendar.MINUTE, 00);
				cal.set(Calendar.SECOND, 00);
				HashMap<String, String> eets = App.getApp().getNavData().getEets();
				for(Skp skp : readedSkps)
				{
					String key = skp.getAdep()+skp.getDest()+
						skp.getActype().charAt(skp.getActype().length()-1);
					String eet = eets.get(key);
					if(eet != null)
					{
						cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(eet.substring(0, 2)));
						cal.set(Calendar.MINUTE, Integer.parseInt(eet.substring(2, 4)));
					}
					int n = cal.get(Calendar.HOUR_OF_DAY)*3600+cal.get(Calendar.MINUTE)*60;
					skp.setEet(n);
				}

				String exenum = (String)cbExe.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenum, readedSkps);
				BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
				reloadSkps(exenum,null,null);
				AppLogger.info("Fill EET for all skps in exe file finished!");
				
			}catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Fill EET for all skps in exe file failed, please try again!");
			}
		}
		else if(o == btnBatchFillSsr)
		{
			try
			{
				int r = JOptionPane.showConfirmDialog(App.getApp().getMainView(), 
					"Sure to fill SSR using auto dispatch for all skps?", 
					"Confirm choice", JOptionPane.YES_NO_OPTION);
				if(r != JOptionPane.YES_OPTION)
					return;
				if(readedSkps==null || readedSkps.size()==0)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), 
						"Skps is empty! Must reload exe file first.");
					return;
				}
				
				//分配策略
				//分配SSR建议，自动填充
				//SSR池
				//不能使用（2000、75、76、77等）
				int ssrPool = 1111;
				for(Skp skp : readedSkps)
				{
					skp.setSsra(ssrPool);
					skp.setSsrb(ssrPool);
					
					ssrPool++;
					int w4 = (ssrPool%10000)/1000;
					int w3 = (ssrPool%1000)/100;
					int w2 = (ssrPool%100)/10;
					int w1 = (ssrPool%10)/1;
					
					while(w4>7 || w3>7 || w2>7 || w1>7 ||
						ssrPool==2000 || ssrPool>=7500)
					{
						w4 = (ssrPool%10000)/1000;
						w3 = (ssrPool%1000)/100;
						w2 = (ssrPool%100)/10;
						w1 = (ssrPool%10)/1;
						
						ssrPool++;
					}
				}
				
				String exenum = (String)cbExe.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenum, readedSkps);
				BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
				reloadSkps(exenum,null,null);
				AppLogger.info("Fill SSR for all skps in exe file finished!");
				
			}catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Fill SSR for all skps in exe file failed, please try again!");
			}
		}
		else if(o == btnBatchADSB)
		{
			try
			{
				int r = JOptionPane.showConfirmDialog(App.getApp().getMainView(), 
					"Sure to fill FIELD18 and EQP using CODE/ABCxxx and AS for all skps?", 
					"Confirm choice", JOptionPane.YES_NO_OPTION);
				if(r != JOptionPane.YES_OPTION)
					return;
				if(readedSkps==null || readedSkps.size()==0)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), 
						"Skps is empty! Must reload exe file first.");
					return;
				}
				
				//用不重复的随机CODE填充field18域，用AS填充EQP域
				for(int i=0;i<readedSkps.size();i++)
				{
					Skp skp = readedSkps.get(i);
					String field18_old = skp.getRemarks();
					if(!field18_old.contains("CODE"))
					{
						String field18_new = field18_old+" CODE/ABC"+String.format("%03d", i+1);
						skp.setRemarks(field18_new);
					}
					skp.setSurveillance_equipment("AS");
				}

				String exenum = (String)cbExe.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenum, readedSkps);
				BdafUtils.writeSkpsumToExeList(exenum, readedSkps.size());
				reloadSkps(exenum,null,null);
				AppLogger.info("Fill EET for all skps in exe file finished!");
				
			}catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Fill EET for all skps in exe file failed, please try again!");
			}
		}
	}
	
	public void reloadSkps(String exenum,String searchType,String searchValue) throws Exception
	{
		this.readedSkps = BdafUtils.getSkpsFromExeFile(exenum);
		
		if(searchType!=null && searchValue!=null && !searchValue.equals(""))
		{
			for(int i=readedSkps.size()-1;i>=0;i--)	
			{
				Skp skp = readedSkps.get(i);
				String attr = null;
				if(searchType.equals("Callsign"))
					attr = skp.getCallsign();
				else if(searchType.equals("Actype"))
					attr = skp.getActype();

				if(!attr.contains(searchValue))
				{
					readedSkps.remove(skp);
					continue;
				}	
			}
		}

		
		DefaultTableModel dtm = (DefaultTableModel)tableSkp.getModel();
		dtm.setRowCount(0);
		changedTableCells.clear();

		for(int i=0;i<readedSkps.size();i++)
		{
			Skp skp = readedSkps.get(i);
			
			String[] rowdata = new String[]{String.format("%03d", i+1),skp.getCallsign(),skp.getAatToString(),skp.getEtdToString(),
				skp.getEetToString(),skp.getRequest_flToString(),
				skp.getFlToString(),skp.getCflToString(),skp.getTasToString(),skp.getActype(),skp.getAdep(),skp.getDest(),
				String.format("%04d", skp.getSsra()),String.format("%04d", skp.getSsrb()),
				String.format("%02d", skp.getPilot()),skp.getTime_for_estToString(),skp.getRoute()};
			
			dtm.addRow(rowdata);		
		}
		
		//判断航班号有无重复情况，以便颜色区分
		sameCallsigns.clear();
		sameCallsignColors.clear();
		outer:
		for(int i=0;i<readedSkps.size()-1;i++)
		{
			Skp skpi = readedSkps.get(i);
			String callsigni = skpi.getCallsign();
			if(sameCallsigns.contains(callsigni))
				continue outer;
			inner:
			for(int j=i+1;j<readedSkps.size();j++)
			{
				Skp skpj = readedSkps.get(j);
				String callsignj = skpj.getCallsign();
				if(callsigni.equals(callsignj))
				{
					sameCallsigns.add(callsigni);
					break inner;
				}
			}
		}	
		for(String sameCallsign : sameCallsigns)
		{
			Color color = new Color((int)(256*Math.random()), 
				(int)(256*Math.random()), (int)(256*Math.random()));
			sameCallsignColors.put(sameCallsign, color);
		}
	}
	
	class ThisTableColorRender extends DefaultTableCellRenderer 
	{
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			
			boolean isChanged = false;
			for(ChangedTableCell ctc : changedTableCells)
			{
				if(tableModelSkp.getValueAt(row, 0).equals(ctc.rowName) &&
					tableSkp.getColumnName(column).equals(ctc.columnName))
				{
					isChanged = true;
					break;
				}
			}
			if(isChanged)
				setBackground(Color.ORANGE);
			else
			{
				String callsign = (String)tableModelSkp.getValueAt(row, 1);
				Color color = sameCallsignColors.get(callsign);
				if(color != null)
					setBackground(color);
				else
					setBackground(Color.CYAN);
			}
			
			
			return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
		}
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub

		if(e.getType() == TableModelEvent.UPDATE){

			String newvalue = tableModelSkp.getValueAt(e.getLastRow(),e.getColumn()).toString();
			if(!editValue.equals(newvalue.trim())){
				ChangedTableCell ctc = new ChangedTableCell(		
					(String)tableModelSkp.getValueAt(e.getLastRow(), 0),
					tableModelSkp.getColumnName(e.getColumn()));
				
				changedTableCells.add(ctc);
				tableSkp.repaint();
				
				//校验输入合法性
				if(tableModelSkp.getColumnName(e.getColumn()).equals("Route")) //航路列
				{
					if(SkpDetailView.isValidRoute(newvalue) == false)
					{
						JOptionPane.showMessageDialog(this, 
							"Route invalid, include undefined fix name or first fix-name can't end with /B!");
						return;
					}
				}
			}
		}
	}	
}
