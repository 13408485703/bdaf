package com.best.bdaf.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_rule;
import com.best.bdaf.dao.Skp;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.BdafUtils;
import com.best.bdaf.data.ConstantPara;
import com.best.bdaf.view.subtype.MyVFlowLayout;

@SuppressWarnings("serial")
public class SkplDetailView extends JDialog implements ActionListener 
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
	
	private SkpDetailView parent;
	
	public SkplDetailView(SkpDetailView parent) throws Exception
	{
		super(parent, "SKPL");
		this.parent = parent;
		setSize(1100, 600);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	
		getContentPane().setLayout(new MyVFlowLayout());
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel);
		
		JLabel lblNum = new JLabel("SKP");
		panel.add(lblNum);
		
		field_num = new JTextField();
		field_num.setEditable(false);
		panel.add(field_num);
		field_num.setColumns(10);
		
		JLabel lblg2 = new JLabel("/");
		panel.add(lblg2);
		
		field_sum = new JTextField();
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
		
		field_callsign = new JTextField();
		field_callsign.addActionListener(this);
		panel_1.add(field_callsign);
		field_callsign.setColumns(10);
		
		JLabel lblActype = new JLabel("TYPE");
		panel_1.add(lblActype);
		
		field_actype = new JTextField();
		field_actype.setEditable(false);
		panel_1.add(field_actype);
		field_actype.setColumns(10);
		
		JLabel lblMode = new JLabel("MODE");
		panel_1.add(lblMode);
		
		field_mode = new JTextField();
		field_mode.setEditable(false);
		field_mode.setText("AC");
		panel_1.add(field_mode);
		field_mode.setColumns(10);
		
		JLabel lblAat = new JLabel("AAT");
		panel_1.add(lblAat);
		
		field_aat = new JTextField();
		field_aat.setEditable(false);
		panel_1.add(field_aat);
		field_aat.setColumns(10);
		
		JLabel lblUnit = new JLabel("UNIT");
		panel_1.add(lblUnit);
		
		field_unit = new JTextField();
		field_unit.setEditable(false);
		field_unit.setText("MET");
		panel_1.add(field_unit);
		field_unit.setColumns(10);
		
		JLabel lblFl = new JLabel("FL");
		panel_1.add(lblFl);
		
		field_fl = new JTextField();
		field_fl.setEditable(false);
		panel_1.add(field_fl);
		field_fl.setColumns(10);
		
		JLabel lblTas = new JLabel("TAS");
		panel_1.add(lblTas);
		
		field_tas = new JTextField();
		field_tas.setEditable(false);
		panel_1.add(field_tas);
		field_tas.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_2.getLayout();
		getContentPane().add(panel_2);
		
		JLabel lblAdep = new JLabel("ADEP");
		panel_2.add(lblAdep);
		
		field_adep = new JTextField();
		field_adep.setEditable(false);
		panel_2.add(field_adep);
		field_adep.setColumns(10);
		
		JLabel lblAdes = new JLabel("ADES");
		panel_2.add(lblAdes);
		
		field_ades = new JTextField();
		field_ades.setEditable(false);
		panel_2.add(field_ades);
		field_ades.setColumns(10);
		
		JLabel lblSsra = new JLabel("SSRA");
		panel_2.add(lblSsra);
		
		field_ssra = new JTextField();
		field_ssra.setEditable(false);
		panel_2.add(field_ssra);
		field_ssra.setColumns(10);
		
		JLabel lblSsrb = new JLabel("SSRB");
		panel_2.add(lblSsrb);
		
		field_ssrb = new JTextField();
		field_ssrb.setEditable(false);
		panel_2.add(field_ssrb);
		field_ssrb.setColumns(10);
		
		JLabel lblEtd = new JLabel("ETD");
		panel_2.add(lblEtd);
		
		field_etd = new JTextField();
		field_etd.setEditable(false);
		panel_2.add(field_etd);
		field_etd.setColumns(10);
		
		JLabel lblEet = new JLabel("EET");
		panel_2.add(lblEet);
		
		field_eet = new JTextField();
		field_eet.setEditable(false);
		panel_2.add(field_eet);
		field_eet.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_3.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_3);
		
		JLabel lblRequest_fl = new JLabel("RQS FL");
		panel_3.add(lblRequest_fl);
		
		field_request_fl = new JTextField();
		field_request_fl.setEditable(false);
		panel_3.add(field_request_fl);
		field_request_fl.setColumns(10);
		
		JLabel lblClear_fl = new JLabel("CLR FL");
		panel_3.add(lblClear_fl);
		
		field_clear_fl = new JTextField();
		field_clear_fl.setEditable(false);
		panel_3.add(field_clear_fl);
		field_clear_fl.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_4.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_4);
		
		JLabel lblField10 = new JLabel("FIELD 10a/b");
		panel_4.add(lblField10);
		
		field_10a = new JTextField();
		field_10a.setEditable(false);
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
		
		field_10b = new JTextField();
		field_10b.setEditable(false);
		panel_5.add(field_10b);
		field_10b.setColumns(40);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_6.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel_6);
		
		JLabel lblField18 = new JLabel("FIELD 18   ");
		panel_6.add(lblField18);
		
		ta_field18 = new JTextArea();
		ta_field18.setEditable(false);
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
		ta_route.setEditable(false);
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
		
		field_pilot = new JTextField();
		field_pilot.setEditable(false);
		panel_8.add(field_pilot);
		field_pilot.setColumns(10);
		
		JLabel lblFrules = new JLabel("FRULES");
		panel_8.add(lblFrules);
		
		field_frules = new JTextField();
		field_frules.setEditable(false);
		panel_8.add(field_frules);
		field_frules.setColumns(10);
		
		JLabel lblTrack = new JLabel("TRACK");
		panel_8.add(lblTrack);
		
		field_track = new JTextField();
		field_track.setEditable(false);
		field_track.setText("Y");
		panel_8.add(field_track);
		field_track.setColumns(10);
		
		JLabel lblFpl = new JLabel("(I)FPL");
		panel_8.add(lblFpl);
		
		field_fpl = new JTextField();
		field_fpl.setEditable(false);
		field_fpl.setText("Y");
		panel_8.add(field_fpl);
		field_fpl.setColumns(10);
		
		JLabel lblMestype = new JLabel("MESTYPE");
		panel_8.add(lblMestype);
		
		field_mestype = new JTextField();
		field_mestype.setEditable(false);
		field_mestype.setText("ICAO");
		panel_8.add(field_mestype);
		field_mestype.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_9.getLayout();
		getContentPane().add(panel_9);
		
		JLabel lblEst = new JLabel("EST");
		panel_9.add(lblEst);
		
		field_est = new JTextField();
		field_est.setEditable(false);
		panel_9.add(field_est);
		field_est.setColumns(10);
		
		JLabel lblCpl = new JLabel("CPL");
		panel_9.add(lblCpl);
		
		field_cpl = new JTextField();
		field_cpl.setEditable(false);
		panel_9.add(field_cpl);
		field_cpl.setColumns(10);
		
		JLabel lblPac = new JLabel("PAC");
		panel_9.add(lblPac);
		
		field_pac = new JTextField();
		field_pac.setEditable(false);
		panel_9.add(field_pac);
		field_pac.setColumns(10);
		
		JLabel lblToc = new JLabel("TOC");
		panel_9.add(lblToc);
		
		field_toc = new JTextField();
		field_toc.setEditable(false);
		panel_9.add(field_toc);
		field_toc.setColumns(10);
		
		JLabel lblDep = new JLabel("DEP");
		panel_9.add(lblDep);
		
		field_dep = new JTextField();
		field_dep.setEditable(false);
		field_dep.setText("N");
		panel_9.add(field_dep);
		field_dep.setColumns(10);
		
		JLabel lblEqp = new JLabel("EQP");
		panel_9.add(lblEqp);
		
		field_eqp = new JTextField();
		field_eqp.setEditable(false);
		panel_9.add(field_eqp);
		field_eqp.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		getContentPane().add(panel_10);
		
		btnOK = new JButton("Fill to SKP");
		btnOK.addActionListener(this);
		panel_10.add(btnOK);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panel_10.add(btnCancel);
		
		initFromSkplFile();
	}
	
	public void initFromSkplFile() throws Exception
	{
		ArrayList<Skp> skps = BdafUtils.getSkpsFromExeFile(ConstantPara.str_skplname);
		int skpnum = 0;
		String exenum = ConstantPara.str_skplname;
		isEdit = true;
		
		popup(skps, skpnum, exenum, isEdit);
	}
	
	public void popup(ArrayList<Skp> skps, int skpnum, String exenum, boolean isEdit) throws Exception
	{
		this.skps = skps;
		this.skpnum = skpnum;
		this.exenum = exenum;
		this.isEdit = isEdit;
		if(isEdit == true)
		{
			if(skpnum == skps.size()-1)
				btnNext.setEnabled(false);
			else
				btnNext.setEnabled(true);
			if(skpnum == 0)
				btnPrev.setEnabled(false);
			else
				btnPrev.setEnabled(true);
			field_num.setText(String.format("%03d", skpnum+1));
			field_sum.setText(String.format("%03d", skps.size()));
			
			Skp skp = skps.get(skpnum);
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
		}
		else
		{
			btnNext.setEnabled(false);
			btnPrev.setEnabled(false);
		}
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object o = arg0.getSource();
		if(o == btnOK)
		{
			try
			{
				parent.fillFromSkp(skps.get(skpnum));
				setVisible(false);
			}
			catch (Exception e) {
				AppLogger.error(e);
				AppLogger.info("Do SKP failed, please try again!");
			}	
		}
		else if(o == btnCancel)
		{
			setVisible(false);
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
							popup(skps, i, exenum, isEdit);
							return;
						}
					}	
				}
			}
			catch (Exception e) {
				AppLogger.error(e);
			}
		}	
	}
}
