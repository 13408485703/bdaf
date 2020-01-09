package com.best.bdaf.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.best.bdaf.dao.LocalFlightPlanDB;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.BdafUtils;
import com.best.bdaf.main.App;
import java.awt.GridLayout;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class FplPanel extends JPanel implements ActionListener
{
	private String[] table_title = {"ID","Callsign","DOF","Actype","Wake","DEP","DES","ETD","ETA"};
	private SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");
	private JTable tableLocalFlightPlan;
	private JButton btnSearch;
	private JTextField fieldSearch;
	private JComboBox<String> cbSearchType;
	private JButton btnFplDetail;
	private JButton btnDecode;
	
	public FplPanel() 
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelN = new JPanel();
		add(panelN, BorderLayout.NORTH);
		DefaultComboBoxModel<String> modelSearchType = new DefaultComboBoxModel<String>();
		modelSearchType.addElement("Callsign");
		modelSearchType.addElement("ASSR");
		modelSearchType.addElement("Status");
		panelN.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelN1 = new JPanel();
		panelN.add(panelN1);
		panelN1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnDecode = new JButton("Decode REC");
		btnDecode.addActionListener(this);
		btnDecode.setToolTipText("<html><body>1. Please put REC folders to one folder, then press button<br />" +
				"2. Choose the folder <br />" +
				"System will decompress and decode the REC files automatically and save FPLS into database!</body></html>");
		panelN1.add(btnDecode);
		
		JPanel panelN2 = new JPanel();
		panelN.add(panelN2);
		
		JLabel labelSearchType = new JLabel("Search key: ");
		panelN2.add(labelSearchType);
		
		cbSearchType = new JComboBox<String>();
		panelN2.add(cbSearchType);
		cbSearchType.setModel(modelSearchType);
		
		fieldSearch = new JTextField();
		panelN2.add(fieldSearch);
		fieldSearch.addActionListener(this);
		fieldSearch.setColumns(10);
		
		btnSearch = new JButton("Search");
		panelN2.add(btnSearch);
		
		btnFplDetail = new JButton("FPL Detail");
		panelN2.add(btnFplDetail);
		btnFplDetail.addActionListener(this);
		btnSearch.addActionListener(this);
		
		JScrollPane scrollPaneC = new JScrollPane();
		add(scrollPaneC, BorderLayout.CENTER);
		
		tableLocalFlightPlan= new JTable(new DefaultTableModel(table_title, 0));
		TableColumnModel tcm = tableLocalFlightPlan.getColumnModel();
	    for (int i = 0; i < tcm.getColumnCount(); i++) 
	    {
		    TableColumn tc = tcm.getColumn(i);
		    tc.setCellRenderer(new ThisTableColorRender());
	    }
		scrollPaneC.setViewportView(tableLocalFlightPlan);	
	}
	
	public void reloadLocalFlightPlan(String searchType,String searchValue) throws Exception
	{	
		DefaultTableModel dtm = (DefaultTableModel)tableLocalFlightPlan.getModel();
		dtm.setRowCount(0);

		ArrayList<LocalFlightPlanDB> lfps = new ArrayList<LocalFlightPlanDB>(App.getApp().getDbService().getLocalFlightPlanDBs());
		Collections.sort(lfps);
		
		for(LocalFlightPlanDB lfp : lfps)
		{
			if(searchValue.equals(""))
			{
				
				
			}
			else if(searchType.equals("Callsign"))
			{
				if(!lfp.getCallsign().contains(searchValue))
					continue;		
			}
			else if(searchType.equals("ASSR"))
			{
				if(!lfp.getAssr_code().toString().contains(searchValue))
					continue;		
			}
			else if(searchType.equals("Status"))
			{
				if(!lfp.getFpl_status().contains(searchValue))
					continue;		
			}
			else 
				continue;	
			
			Object[] rowdata = new Object[]{lfp.getFpl_id(),lfp.getCallsign(),lfp.getDof(),lfp.getAircraft_type(),lfp.getWtc(),lfp.getAdep(),lfp.getAdes(),
					sdf_time.format(lfp.getEtd()),sdf_time.format(lfp.getEta())};
			dtm.addRow(rowdata);	
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src == btnSearch || src == fieldSearch)
		{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try
					{
						btnSearch.setEnabled(false);
						
						String searchValue = fieldSearch.getText().trim();
						String searchType = (String)cbSearchType.getSelectedItem();
						
						reloadLocalFlightPlan(searchType, searchValue);
		
					}catch (Exception ex) {
						AppLogger.error(ex);
						AppLogger.info("Search FPLS failed, please try again!");
					}
					finally
					{
						btnSearch.setEnabled(true);
					}
					
				}
			}).start();	
		}	
		else if(src == btnFplDetail)
		{
			try
			{
				int selectrow = tableLocalFlightPlan.getSelectedRow();
				if(selectrow == -1)
				{
					JOptionPane.showMessageDialog(App.getApp().getMainView(), "Should choose a FPL!");
					return;
				}
				else
				{
					int fpid = (Integer)tableLocalFlightPlan.getValueAt(selectrow, 0);
					new FplDetailView(App.getApp().getMainView()).popup(fpid);
				}
			}
			catch (Exception ex) {
				AppLogger.error(ex);
				AppLogger.info("Popup FPL datail failed, please try again!");
			}	
		}
		else if(src == btnDecode)
		{
			//转换开始，防止界面卡住，开启新线程完成，并在转换过程中禁止用户反复转换
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try
					{
						btnDecode.setEnabled(false);
							
						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
						int result = jfc.showOpenDialog(App.getApp().getMainView());
						if(result == JFileChooser.APPROVE_OPTION)
						{
							File file = jfc.getSelectedFile();
							if(!file.isDirectory())
								return;
							AppLogger.info("Begin remove old FPLS!");
							App.getApp().getDbService().clearLocalFlightPlanAndFixes();
							AppLogger.info("Finish remove old FPLS!");
							
							//删除数据库时重置主键池和读取对象池
							BdafUtils.pool_fpid= 1;
							BdafUtils.pool_fixid = 1;
							BdafUtils.cfds.clear();
							
							AppLogger.info("Begin read file!");
							BdafUtils.decompressFilesInFolder(file);
							BdafUtils.decodeFilesInFolder(file, false);
							AppLogger.info("Finish read file!");
							
//							AppLogger.info("开始存储新数据！");
//							BdafUtils.saveComFplDBsToDB();
//							AppLogger.info("结束存储新数据！");		
						}				
					}
					catch (Exception ex) {
						AppLogger.error(ex);
						AppLogger.info("Save FPLS to database failed, please try again!");	
					}
					finally
					{
						btnDecode.setEnabled(true);		
					}
				}
			}).start();
		}
	}
	
	class ThisTableColorRender extends DefaultTableCellRenderer 
	{
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			
//			if(row%2 == 0)
//				setBackground(Color.orange);
//			else
//				setBackground(Color.CYAN);
			
			setBackground(Color.LIGHT_GRAY);
			
			
			return super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
		}
		
	}
}
