package com.best.bdaf.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_rule;
import com.best.bdaf.dao.Skp;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.BdafUtils;
import com.best.bdaf.main.App;
import com.best.bdaf.view.subtype.MyTextField;
import com.best.bdaf.view.subtype.MyVFlowLayout;

@SuppressWarnings("serial")
public class SkpDetailView extends JDialog implements ActionListener 
{
	private JTextField field_num;
	private JTextField field_callsign;
	private JTextField field_actype;
	private JTextField field_mode;
	private JTextField field_aat;
	private JTextField field_unit;
	private JTextField field_fl;
	private JTextField field_tas;
	private JTextField field_adep;
	private JTextField field_ades;
	private JTextField field_ssra;
	private JTextField field_ssrb;
	private JTextField field_etd;
	private JTextField field_eet;
	private JTextField field_request_fl;
	private JTextField field_clear_fl;
	private JTextField field_10a;
	private JTextField field_10b;
	private JTextArea ta_field18;
	private JTextArea ta_route;
	private JTextField field_pilot;
	private JTextField field_frules;
	private JTextField field_track;
	private JTextField field_fpl;
	private JTextField field_mestype;
	private JTextField field_est;
	private JTextField field_cpl;
	private JTextField field_pac;
	private JTextField field_toc;
	private JTextField field_dep;
	private JTextField field_eqp;
	private JButton btnCancel;
	private JButton btnOK;
	private JTextField field_sum;
	private JButton btnPrev;
	private JButton btnNext;
	
	private boolean isEdit;
	private ArrayList<Skp> skps;
	private int skpnum;
	private String exenum;
	
	private SkplDetailView sdv;
	private JButton btnImportSkpl;
	
	public SkpDetailView(JFrame parent)
	{
		super(parent, "SKP");
		setSize(1100, 600);
		setLocationRelativeTo(parent);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(new MyVFlowLayout());
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel);
		
		JLabel lblNum = new JLabel("SKP");
		panel.add(lblNum);
		
		field_num = new MyTextField();
		field_num.setEditable(false);
		panel.add(field_num);
		field_num.setColumns(10);
		
		JLabel lblg2 = new JLabel("/");
		panel.add(lblg2);
		
		field_sum = new MyTextField();
		field_sum.setEditable(false);
		panel.add(field_sum);
		field_sum.setColumns(10);
		
		btnPrev = new JButton("Prev");
		btnPrev.addActionListener(this);
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		panel.add(horizontalStrut);
		panel.add(btnPrev);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		panel.add(btnNext);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_1);
		
		JLabel lblCallsign = new JLabel("CSN");
		panel_1.add(lblCallsign);
		
		field_callsign = new MyTextField();
		field_callsign.addActionListener(this);
		panel_1.add(field_callsign);
		field_callsign.setColumns(10);
		
		JLabel lblActype = new JLabel("TYPE");
		panel_1.add(lblActype);
		
		field_actype = new MyTextField();
		panel_1.add(field_actype);
		field_actype.setColumns(10);
		
		JLabel lblMode = new JLabel("MODE");
		panel_1.add(lblMode);
		
		field_mode = new MyTextField();
		field_mode.setEditable(false);
		field_mode.setText("AC");
		panel_1.add(field_mode);
		field_mode.setColumns(10);
		
		JLabel lblAat = new JLabel("AAT");
		panel_1.add(lblAat);
		
		field_aat = new MyTextField();
		panel_1.add(field_aat);
		field_aat.setColumns(10);
		
		JLabel lblUnit = new JLabel("UNIT");
		panel_1.add(lblUnit);
		
		field_unit = new MyTextField();
		field_unit.setEditable(false);
		field_unit.setText("MET");
		panel_1.add(field_unit);
		field_unit.setColumns(10);
		
		JLabel lblFl = new JLabel("FL");
		panel_1.add(lblFl);
		
		field_fl = new MyTextField();
		panel_1.add(field_fl);
		field_fl.setColumns(10);
		
		JLabel lblTas = new JLabel("TAS");
		panel_1.add(lblTas);
		
		field_tas = new MyTextField();
		panel_1.add(field_tas);
		field_tas.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		getContentPane().add(panel_2);
		
		JLabel lblAdep = new JLabel("ADEP");
		panel_2.add(lblAdep);
		
		field_adep = new MyTextField();
		panel_2.add(field_adep);
		field_adep.setColumns(10);
		
		JLabel lblAdes = new JLabel("ADES");
		panel_2.add(lblAdes);
		
		field_ades = new MyTextField();
		panel_2.add(field_ades);
		field_ades.setColumns(10);
		
		JLabel lblSsra = new JLabel("SSRA");
		panel_2.add(lblSsra);
		
		field_ssra = new MyTextField();
		panel_2.add(field_ssra);
		field_ssra.setColumns(10);
		
		JLabel lblSsrb = new JLabel("SSRB");
		panel_2.add(lblSsrb);
		
		field_ssrb = new MyTextField();
		panel_2.add(field_ssrb);
		field_ssrb.setColumns(10);
		
		JLabel lblEtd = new JLabel("ETD");
		panel_2.add(lblEtd);
		
		field_etd = new MyTextField();
		panel_2.add(field_etd);
		field_etd.setColumns(10);
		
		JLabel lblEet = new JLabel("EET");
		panel_2.add(lblEet);
		
		field_eet = new MyTextField();
		panel_2.add(field_eet);
		field_eet.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_3);
		
		JLabel lblRequest_fl = new JLabel("RQS FL");
		panel_3.add(lblRequest_fl);
		
		field_request_fl = new MyTextField();
		panel_3.add(field_request_fl);
		field_request_fl.setColumns(10);
		
		JLabel lblClear_fl = new JLabel("CLR FL");
		panel_3.add(lblClear_fl);
		
		field_clear_fl = new MyTextField();
		panel_3.add(field_clear_fl);
		field_clear_fl.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_4.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_4);
		
		JLabel lblField10 = new JLabel("FIELD 10a/b");
		panel_4.add(lblField10);
		
		field_10a = new MyTextField();
		panel_4.add(field_10a);
		field_10a.setColumns(80);
		
		JLabel lblg = new JLabel("/");
		panel_4.add(lblg);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_5.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_5);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(65);
		panel_5.add(horizontalStrut_1);
		
		field_10b = new MyTextField();
		panel_5.add(field_10b);
		field_10b.setColumns(40);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_6.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_6);
		
		JLabel lblField18 = new JLabel("FIELD 18   ");
		panel_6.add(lblField18);
		
		ta_field18 = new JTextArea();
		ta_field18.setLineWrap(true);
		ta_field18.setColumns(80);
		ta_field18.setRows(2);
		ta_field18.setWrapStyleWord(true);
		panel_6.add(ta_field18);
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_7.getLayout();
		flowLayout_7.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_7);
		
		JLabel lblRoute = new JLabel("ROUTE      ");
		panel_7.add(lblRoute);
		
		ta_route = new JTextArea();
		ta_route.setWrapStyleWord(true);
		ta_route.setLineWrap(true);
		ta_route.setRows(7);
		ta_route.setColumns(80);
		panel_7.add(ta_route);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_8.getLayout();
		getContentPane().add(panel_8);
		
		JLabel lblPilot = new JLabel("PILOT");
		panel_8.add(lblPilot);
		
		field_pilot = new MyTextField();
		panel_8.add(field_pilot);
		field_pilot.setColumns(10);
		
		JLabel lblFrules = new JLabel("FRULES");
		panel_8.add(lblFrules);
		
		field_frules = new MyTextField();
		field_frules.setEditable(false);
		panel_8.add(field_frules);
		field_frules.setColumns(10);
		
		JLabel lblTrack = new JLabel("TRACK");
		panel_8.add(lblTrack);
		
		field_track = new MyTextField();
		field_track.setEditable(false);
		field_track.setText("Y");
		panel_8.add(field_track);
		field_track.setColumns(10);
		
		JLabel lblFpl = new JLabel("(I)FPL");
		panel_8.add(lblFpl);
		
		field_fpl = new MyTextField();
		field_fpl.setEditable(false);
		field_fpl.setText("Y");
		panel_8.add(field_fpl);
		field_fpl.setColumns(10);
		
		JLabel lblMestype = new JLabel("MESTYPE");
		panel_8.add(lblMestype);
		
		field_mestype = new MyTextField();
		field_mestype.setEditable(false);
		field_mestype.setText("ICAO");
		panel_8.add(field_mestype);
		field_mestype.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_9.getLayout();
		getContentPane().add(panel_9);
		
		JLabel lblEst = new JLabel("EST");
		panel_9.add(lblEst);
		
		field_est = new MyTextField();
		panel_9.add(field_est);
		field_est.setColumns(10);
		
		JLabel lblCpl = new JLabel("CPL");
		panel_9.add(lblCpl);
		
		field_cpl = new MyTextField();
		field_cpl.setEditable(false);
		panel_9.add(field_cpl);
		field_cpl.setColumns(10);
		
		JLabel lblPac = new JLabel("PAC");
		panel_9.add(lblPac);
		
		field_pac = new MyTextField();
		field_pac.setEditable(false);
		panel_9.add(field_pac);
		field_pac.setColumns(10);
		
		JLabel lblToc = new JLabel("TOC");
		panel_9.add(lblToc);
		
		field_toc = new MyTextField();
		field_toc.setEditable(false);
		panel_9.add(field_toc);
		field_toc.setColumns(10);
		
		JLabel lblDep = new JLabel("DEP");
		panel_9.add(lblDep);
		
		field_dep = new MyTextField();
		field_dep.setEditable(false);
		field_dep.setText("N");
		panel_9.add(field_dep);
		field_dep.setColumns(10);
		
		JLabel lblEqp = new JLabel("EQP");
		panel_9.add(lblEqp);
		
		field_eqp = new MyTextField();
		field_eqp.setEditable(false);
		panel_9.add(field_eqp);
		field_eqp.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		getContentPane().add(panel_10);
		
		btnOK = new JButton("Ok");
		btnOK.addActionListener(this);
		panel_10.add(btnOK);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panel_10.add(btnCancel);
		
		btnImportSkpl = new JButton("Filled From SKP");
		btnImportSkpl.addActionListener(this);
		panel_10.add(btnImportSkpl);
		
		
	}
	
	public void popup(ArrayList<Skp> skps, int skpnum, String exenum, boolean isEdit) throws Exception
	{
		this.skps = skps;
		this.skpnum = skpnum;
		this.exenum = exenum;
		this.isEdit = isEdit;
		if(isEdit == true)
		{
			if(skpnum == skps.size())
				btnNext.setEnabled(false);
			else
				btnNext.setEnabled(true);
			if(skpnum == 1)
				btnPrev.setEnabled(false);
			else
				btnPrev.setEnabled(true);
			field_num.setText(String.format("%03d", skpnum));
			field_sum.setText(String.format("%03d", skps.size()));
			
			Skp skp = skps.get(skpnum-1);
			fillFromSkp(skp);
		}
		else
		{
			btnNext.setEnabled(false);
			btnPrev.setEnabled(false);
			
			//已加载练习后分配SSR
			if(skps != null)
			{
				//分配SSR建议，自动填充
				//SSR池
				reading_outer_loop:
				for(int i=1111;i<6666;i++)
				{
					int w4 = (i%10000)/1000;
					int w3 = (i%1000)/100;
					int w2 = (i%100)/10;
					int w1 = (i%10)/1;
					
					if(w4>7 || w3>7 || w2>7 || w1>7 ||
						i==2000 || i>=7500)
					{
						w4 = (i%10000)/1000;
						w3 = (i%1000)/100;
						w2 = (i%100)/10;
						w1 = (i%10)/1;
						
						continue reading_outer_loop;
					}
						
					boolean ocupied = false;
					reading_iner_loop:
					for(Skp readed_skp : skps)
					{
						if(readed_skp.getSsra() == i)
						{
							ocupied = true;
							break reading_iner_loop;
						}
					}
					//未被占用则分配
					if(ocupied == false)
					{
						field_ssra.setText(String.format("%04d", i));
						field_ssrb.setText(String.format("%04d", i));
						break reading_outer_loop;
					}
				}
			}
		}
		setVisible(true);
	}
	
	public void popup(Skp skp) 
	{
		this.skps = null;
		this.skpnum = -1;
		this.exenum = null;
		this.isEdit = false;
		
		btnPrev.setEnabled(false);
		btnNext.setEnabled(false);
		btnOK.setEnabled(false);
		btnCancel.setEnabled(false);
		btnImportSkpl.setEnabled(false);
		
		fillFromSkp(skp);
	
		setVisible(true);
	}
	
	public void fillFromSkp(Skp skp)
	{
		field_callsign.setText(skp.getCallsign());
		field_actype.setText(skp.getActype());
		field_aat.setText(skp.getAatToString());
		field_fl.setText(skp.getFlToString());
		field_tas.setText(skp.getTasToString());
		field_adep.setText(skp.getAdep());
		field_ades.setText(skp.getDest());
		field_ssra.setText(String.format("%04d", skp.getSsra()));
		field_ssrb.setText(String.format("%04d", skp.getSsrb()));
		field_etd.setText(skp.getEtdToString());
		field_eet.setText(skp.getEetToString());
		field_request_fl.setText(skp.getRequest_flToString());
		field_clear_fl.setText(skp.getCflToString());
		field_10a.setText(skp.getField10a());
		field_10b.setText(skp.getField10b());
		ta_field18.setText(skp.getRemarks());
		ta_route.setText(skp.getRoute());
		field_pilot.setText(String.format("%02d", skp.getPilot()));
		field_frules.setText(E_flight_rule.values()[skp.getFrules()].toString());
		field_est.setText(skp.getTime_for_estToString());
		field_eqp.setText(skp.getSurveillance_equipment());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == btnOK)
		{
			try
			{
				String route = ta_route.getText().trim();
				if(isValidRoute(route) == false)
				{
					JOptionPane.showMessageDialog(this, 
						"Route invalid, include undefined fix name or first fix-name can't end with /B!");
					return;
				}
				
				Skp skp = null;
				if(isEdit == true)
				{
					skp = skps.get(skpnum-1);	
				}
				else
				{
					//准备新增，加入到列表
					if(skps == null)
					{
						AppLogger.info("Should open exe file first, before SKP could be created!");
						return;
					}
					//新增对象
					skp = new Skp();
				}	
				
				skp.setCallsign(field_callsign.getText());
				skp.setActype(field_actype.getText());
				skp.setAat(skp.getAatFromString(field_aat.getText()));
				int fl_m = skp.getFlFromString(field_fl.getText());
				float fl_im = fl_m*10/0.3048f;
				skp.setFl_m(fl_m);
				skp.setFl_im(fl_im);
				int tas_m = skp.getTasFromString(field_tas.getText());
				float tas_im = tas_m/1.852f;
				skp.setTas_m(tas_m);
				skp.setTas_im(tas_im);
				skp.setAdep(field_adep.getText());
				skp.setDest(field_ades.getText());
				skp.setSsra(Integer.parseInt(field_ssra.getText()));
				skp.setSsrb(Integer.parseInt(field_ssrb.getText()));
				skp.setEtd(skp.getEtdFromString(field_etd.getText()));
				skp.setEet(skp.getEetFromString(field_eet.getText()));
				int request_fl_m = skp.getFlFromString(field_request_fl.getText());
				float request_fl_im = request_fl_m*10/0.3048f;
				skp.setRequested_fl_m(request_fl_m);
				skp.setRequested_fl_im(request_fl_im);
				int cfl_m = skp.getCflFromString(field_clear_fl.getText());
				float cfl_im = cfl_m*10/0.3048f;
				skp.setCfl_m(cfl_m);
				skp.setCfl_im(cfl_im);	
				skp.setField10a(field_10a.getText());
				skp.setField10b(field_10b.getText());
				skp.setRemarks(ta_field18.getText());
				skp.setRoute(ta_route.getText());
				skp.setPilot(Integer.parseInt(field_pilot.getText()));
				skp.setTime_for_est(Integer.parseInt(field_est.getText()));
				skp.setSurveillance_equipment(field_eqp.getText());
				
				//编辑区都没出错了，才确认加入SKP计划列表，
				//防止中途出错，有些剩余字段未设置值导致保存到文件出错
				if(isEdit == false)
					skps.add(skp);
				
				BdafUtils.writeSkpsToExe(exenum, skps);
				BdafUtils.writeSkpsumToExeList(exenum, skps.size());
				
				App.getApp().getMainView().getExprepView().reloadSkps(exenum,null,null);
				
				if(isEdit == true)
					AppLogger.info("Update SKP finished!");
				else
					AppLogger.info("Add SKP finished!");
				
				setVisible(false);
				dispose();
			}
			catch (Exception e) {
				AppLogger.error(e);
				AppLogger.info("Do SKP failed! Please try again!");
			}	
		}
		else if(o == btnCancel)
		{
			dispose();
		}
		else if(o == btnPrev)
		{
			try
			{
				popup(skps, skpnum-1, exenum, true);
			}
			catch (Exception e) {
				// TODO: handle exception
			}	
		}
		else if(o == btnNext)
		{
			try
			{
				popup(skps, skpnum+1, exenum, true);
			}
			catch (Exception e) {
				// TODO: handle exception
			}	
		}
		else if(o == field_callsign)
		{
			try
			{
				if(isEdit)
				{
					String callsign = field_callsign.getText().trim();
					for(int i=0;i<skps.size();i++)
					{
						Skp skp = skps.get(i);
						if(skp.getCallsign().equals(callsign))
						{
							popup(skps, i+1, exenum, isEdit);
							return;
						}
					}	
				}
			}
			catch (Exception e) {
				AppLogger.error(e);
			}
		}	
		else if(o == btnImportSkpl)
		{
			try
			{
				if(sdv == null)
					sdv = new SkplDetailView(this);
				sdv.setVisible(true);
					
			}
			catch (Exception e) {
				AppLogger.error(e);
			}
		}	
	}
	
	public static boolean isValidRoute(String str)
	{
		String[] fixNames = str.split(" ");
		HashMap<String, String> fixMap = App.getApp().getNavData().getFixnames();
		for(int i=0;i<fixNames.length;i++)
		{
			String fixName = fixNames[i];
			int indexSpecial = fixName.indexOf('/');
			String realFixName = null;
			if(indexSpecial != -1)
			{
				realFixName = fixName.substring(0, indexSpecial);
				String special = fixName.substring(indexSpecial);
				if(special.startsWith("/B"))
				{
					if(i == 0)
						return false;
				}
				else if(special.startsWith("/E"))
				{
					
				}
				else if(special.startsWith("/H"))
				{
					
				}
			}
			else
				realFixName = fixName;
			
			if(realFixName.matches("[0-9]{4}N[0-9]{5}E"))
				continue;
			else if(fixMap.get(realFixName) == null)
				return false;
			else
				continue;		
		}
		return true;
	}
}