package com.best.bdaf.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.best.bdaf.dao.LocalFlightPlanDB;
import com.best.bdaf.dao.LocalFlightPlanFixesDB;
import com.best.bdaf.data.IniData;
import com.best.bdaf.main.App;
import com.best.bdaf.view.subtype.MyVFlowLayout;

@SuppressWarnings("serial")
public class FplDetailView extends JDialog {
	
	private SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");
	private JTable tableFix;
	
	private JTextField field_fpl_id;
	private JTextField field_record_time;
	private JTextField field_creation_time;
	private JTextField field_flight_origin;
	private JTextField field_callsign;
	private JTextField field_flight_plan_number;
	private JTextField field_adep;
	private JTextField field_ades;
	private JTextField field_aircraft_type;
	private JTextField field_aircraft_REG;
	private JTextField field_aircraft_address;
	private JTextField field_dof;
	private JTextField field_atd;
	private JTextField field_ata;
	private JTextField field_flight_rule;
	private JTextField field_flight_type;
	private JTextField field_flight_category;
	private JTextField field_movement_category;
	private JTextField field_distance_flown_in_fir;
	private JTextField field_sent_to_fdo;
	private JTextField field_entry_point;
	private JTextField field_entry_hour;
	private JTextField field_exit_point;
	private JTextField field_exit_hour;
	private JTextField field_assrcode;
	private JTextField field_pssrcode;
	private JTextField field_etd;
	private JTextField field_eta;
	private JTextField field_cfl;
	private JTextField field_ufl;
	private JTextField field_rfl;
	private JTextField field_field_10a;
	private JTextField field_field_10b;
	private JTextField field_exit_protocal;
	private JTextField field_wtc;
	private JTextField field_eet;
	private JTextField field_eobt;
	private JTextField field_fpl_status;
	private JTextField field_cfl_approach_mode;
	private JTextField field_last_fix_overflown;
	private JTextField field_dcc_identifier;
	private JTextField field_aircraft_operator_name;
	private JTextField field_route;
	
	public FplDetailView(JFrame parent)
	{
		super(parent, "FPL details");
		setSize(1600, 900);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panelC = new JPanel();
		panelC.setBorder(new TitledBorder(null, "FPL Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new MyVFlowLayout());
		
		JPanel panel_0 = new JPanel();
		panelC.add(panel_0);
		panel_0.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblFpl_id = new JLabel("FPL_ID:");
		panel_0.add(lblFpl_id);
		
		field_fpl_id = new JTextField();
		field_fpl_id.setColumns(10);
		panel_0.add(field_fpl_id);
		
		JLabel lblRecord_time = new JLabel("record_time:");
		panel_0.add(lblRecord_time);
		
		field_record_time = new JTextField();
		field_record_time.setColumns(10);
		panel_0.add(field_record_time);
		
		JLabel lblCfl = new JLabel("CFL：");
		panel_0.add(lblCfl);
		
		field_cfl = new JTextField();
		field_cfl.setColumns(10);
		panel_0.add(field_cfl);
		
		JPanel panel_1 = new JPanel();
		panelC.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblCreation_time = new JLabel("creation_time:");
		panel_1.add(lblCreation_time);
		
		field_creation_time = new JTextField();
		field_creation_time.setColumns(10);
		panel_1.add(field_creation_time);
		
		JLabel lblFlight_origin = new JLabel("flight_origin:");
		panel_1.add(lblFlight_origin);
		
		field_flight_origin = new JTextField();
		field_flight_origin.setColumns(10);
		panel_1.add(field_flight_origin);
		
		JLabel lblUfl = new JLabel("UFL:");
		panel_1.add(lblUfl);
		
		field_ufl = new JTextField();
		field_ufl.setColumns(10);
		panel_1.add(field_ufl);
		
		JPanel panel_2 = new JPanel();
		panelC.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblCallsign = new JLabel("callsign:");
		panel_2.add(lblCallsign);
		
		field_callsign = new JTextField();
		field_callsign.setColumns(10);
		panel_2.add(field_callsign);
		
		JLabel lblFlight_plan_number = new JLabel("flight_plan_number:");
		panel_2.add(lblFlight_plan_number);
		
		field_flight_plan_number = new JTextField();
		field_flight_plan_number.setColumns(10);
		panel_2.add(field_flight_plan_number);
		
		JLabel lblRfl = new JLabel("RFL:");
		panel_2.add(lblRfl);
		
		field_rfl = new JTextField();
		field_rfl.setColumns(10);
		panel_2.add(field_rfl);
		
		JPanel panel_3 = new JPanel();
		panelC.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblAdep = new JLabel("ADEP:");
		panel_3.add(lblAdep);
		
		field_adep = new JTextField();
		field_adep.setColumns(10);
		panel_3.add(field_adep);
		
		JLabel lblAdes = new JLabel("ADES:");
		panel_3.add(lblAdes);
		
		field_ades = new JTextField();
		field_ades.setColumns(10);
		panel_3.add(field_ades);
		
		JLabel lblFielda = new JLabel("field_10a:");
		panel_3.add(lblFielda);
		
		field_field_10a = new JTextField();
		field_field_10a.setColumns(10);
		panel_3.add(field_field_10a);
		
		JPanel panel_4 = new JPanel();
		panelC.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblAircraft_type = new JLabel("aircraft_type:");
		panel_4.add(lblAircraft_type);
		
		field_aircraft_type = new JTextField();
		field_aircraft_type.setColumns(10);
		panel_4.add(field_aircraft_type);
		
		JLabel lblAircraft_REG = new JLabel("aircraft_REG:");
		panel_4.add(lblAircraft_REG);
		
		field_aircraft_REG = new JTextField();
		field_aircraft_REG.setColumns(10);
		panel_4.add(field_aircraft_REG);
		
		JLabel lblFieldb = new JLabel("field_10b:");
		panel_4.add(lblFieldb);
		
		field_field_10b = new JTextField();
		field_field_10b.setColumns(10);
		panel_4.add(field_field_10b);
		
		JPanel panel_5 = new JPanel();
		panelC.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblAircraft_address = new JLabel("aircraft_address:");
		panel_5.add(lblAircraft_address);
		
		field_aircraft_address = new JTextField();
		field_aircraft_address.setColumns(10);
		panel_5.add(field_aircraft_address);
		
		JLabel lblDof = new JLabel("DOF:");
		panel_5.add(lblDof);
		
		field_dof = new JTextField();
		field_dof.setColumns(10);
		panel_5.add(field_dof);
		
		JLabel lblFplstatus = new JLabel("FPL_status:");
		panel_5.add(lblFplstatus);
		
		field_fpl_status = new JTextField();
		field_fpl_status.setColumns(10);
		panel_5.add(field_fpl_status);
		
		JPanel panel_6 = new JPanel();
		panelC.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblAtd = new JLabel("ATD:");
		panel_6.add(lblAtd);
		
		field_atd = new JTextField();
		field_atd.setColumns(10);
		panel_6.add(field_atd);
		
		JLabel lblAta = new JLabel("ATA:");
		panel_6.add(lblAta);
		
		field_ata = new JTextField();
		field_ata.setColumns(10);
		panel_6.add(field_ata);
		
		JLabel lblCflapproch = new JLabel("CFL_approach_mode:");
		panel_6.add(lblCflapproch);
		
		field_cfl_approach_mode = new JTextField();
		field_cfl_approach_mode.setColumns(10);
		panel_6.add(field_cfl_approach_mode);
		
		JPanel panel_7 = new JPanel();
		panelC.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblFlight_rule = new JLabel("flight_rule:");
		panel_7.add(lblFlight_rule);
		
		field_flight_rule = new JTextField();
		field_flight_rule.setColumns(10);
		panel_7.add(field_flight_rule);
		
		JLabel lblFlight_type = new JLabel("flight_type:");
		panel_7.add(lblFlight_type);
		
		field_flight_type = new JTextField();
		field_flight_type.setColumns(10);
		panel_7.add(field_flight_type);
		
		JLabel lblWtc = new JLabel("WTC:");
		panel_7.add(lblWtc);
		
		field_wtc = new JTextField();
		field_wtc.setColumns(10);
		panel_7.add(field_wtc);
		
		JPanel panel_8 = new JPanel();
		panelC.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblFlight_category = new JLabel("flight_category:");
		panel_8.add(lblFlight_category);
		
		field_flight_category = new JTextField();
		field_flight_category.setColumns(10);
		panel_8.add(field_flight_category);
		
		JLabel lblMovement_category = new JLabel("movement_category:");
		panel_8.add(lblMovement_category);
		
		field_movement_category = new JTextField();
		field_movement_category.setColumns(10);
		panel_8.add(field_movement_category);
		
		JLabel lblAircraftoperatorname = new JLabel("aircraft_operator：");
		panel_8.add(lblAircraftoperatorname);
		
		field_aircraft_operator_name = new JTextField();
		field_aircraft_operator_name.setColumns(10);
		panel_8.add(field_aircraft_operator_name);
		
		JPanel panel_9 = new JPanel();
		panelC.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblDistance_flown_in_fir = new JLabel("distance_flown_in_fir:");
		panel_9.add(lblDistance_flown_in_fir);
		
		field_distance_flown_in_fir = new JTextField();
		field_distance_flown_in_fir.setColumns(10);
		panel_9.add(field_distance_flown_in_fir);
		
		JLabel lblSent_to_fdo = new JLabel("sent_to_fdo:");
		panel_9.add(lblSent_to_fdo);
		
		field_sent_to_fdo = new JTextField();
		field_sent_to_fdo.setColumns(10);
		panel_9.add(field_sent_to_fdo);
		
		JLabel lblDccidentifier = new JLabel("DCC_identifier:");
		panel_9.add(lblDccidentifier);
		
		field_dcc_identifier = new JTextField();
		field_dcc_identifier.setColumns(10);
		panel_9.add(field_dcc_identifier);
		
		JPanel panel_10 = new JPanel();
		panelC.add(panel_10);
		panel_10.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblEntry_point = new JLabel("entry_point:");
		panel_10.add(lblEntry_point);
		
		field_entry_point = new JTextField();
		field_entry_point.setColumns(10);
		panel_10.add(field_entry_point);
		
		JLabel lblEntry_hour = new JLabel("entry_hour:");
		panel_10.add(lblEntry_hour);
		
		field_entry_hour = new JTextField();
		field_entry_hour.setColumns(10);
		panel_10.add(field_entry_hour);
		
		JLabel lblLast = new JLabel("last_fix_overflown:");
		panel_10.add(lblLast);
		
		field_last_fix_overflown = new JTextField();
		field_last_fix_overflown.setColumns(10);
		panel_10.add(field_last_fix_overflown);
		
		JPanel panel_11 = new JPanel();
		panelC.add(panel_11);
		panel_11.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblExit_point = new JLabel("exit_point:");
		panel_11.add(lblExit_point);
		
		field_exit_point = new JTextField();
		field_exit_point.setColumns(10);
		panel_11.add(field_exit_point);
		
		JLabel lblExit_hour = new JLabel("exit_hour:");
		panel_11.add(lblExit_hour);
		
		field_exit_hour = new JTextField();
		field_exit_hour.setColumns(10);
		panel_11.add(field_exit_hour);
		
		JLabel lblExitprotocal = new JLabel("exit_protocal:");
		panel_11.add(lblExitprotocal);
		
		field_exit_protocal = new JTextField();
		field_exit_protocal.setColumns(10);
		panel_11.add(field_exit_protocal);
		
		JPanel panel_12 = new JPanel();
		panelC.add(panel_12);
		panel_12.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblAssrcode = new JLabel("ASSRcode:");
		panel_12.add(lblAssrcode);
		
		field_assrcode = new JTextField();
		field_assrcode.setColumns(10);
		panel_12.add(field_assrcode);
		
		JLabel lblPssrcode = new JLabel("PSSR_code:");
		panel_12.add(lblPssrcode);
		
		field_pssrcode = new JTextField();
		field_pssrcode.setColumns(10);
		panel_12.add(field_pssrcode);
		
		JLabel lblEet = new JLabel("EET:");
		panel_12.add(lblEet);
		
		field_eet = new JTextField();
		field_eet.setColumns(10);
		panel_12.add(field_eet);
		
		JPanel panel_13 = new JPanel();
		panelC.add(panel_13);
		panel_13.setLayout(new GridLayout(0, 6, 0, 0));
		
		JLabel lblEtd = new JLabel("ETD:");
		panel_13.add(lblEtd);
		
		field_etd = new JTextField();
		field_etd.setColumns(10);
		panel_13.add(field_etd);
		
		JLabel lblEta = new JLabel("ETA:");
		panel_13.add(lblEta);
		
		field_eta = new JTextField();
		field_eta.setColumns(10);
		panel_13.add(field_eta);
		
		JLabel lblEobt = new JLabel("EOBT:");
		panel_13.add(lblEobt);
		
		field_eobt = new JTextField();
		field_eobt.setColumns(10);
		panel_13.add(field_eobt);
		
		JPanel panel_14 = new JPanel();
		panelC.add(panel_14);
		panel_14.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblRoute = new JLabel("Route:");
		panel_14.add(lblRoute);
		
		field_route = new JTextField();
		panel_14.add(field_route);
		field_route.setColumns(100);
		
		JScrollPane panelS = new JScrollPane();
		panelS.setBorder(new TitledBorder(null, "Fix Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panelS, BorderLayout.SOUTH);
		
		Field[] fields = LocalFlightPlanFixesDB.class.getDeclaredFields();
		String[] tablenames = new String[fields.length];
		for(int i=0;i<fields.length;i++)
			tablenames[i] = fields[i].getName();
		tableFix = new JTable(new DefaultTableModel(tablenames, 0));
		panelS.setViewportView(tableFix);	
		
		panelS.setPreferredSize(new Dimension(this.getWidth(), 300));
	}
	
	public void popup(int fpid) throws Exception
	{
		setVisible(true);
			
		LocalFlightPlanDB lfp = App.getApp().getDbService().getLocalFlightPlanDB(fpid);
		List<LocalFlightPlanFixesDB> lfpfs = App.getApp().getDbService().getLocalFlightPlanFixesDBs(fpid);
		Collections.sort(lfpfs);
	
		//未显示movement_date;storage_delay;fix_list_length;
		field_fpl_id.setText(""+lfp.getFpl_id());
		field_record_time.setText(sdf_datetime.format(lfp.getRecord_time()));
		field_creation_time.setText(sdf_datetime.format(lfp.getCreation_time()));
		field_flight_origin.setText(lfp.getFlight_origin());
		field_callsign.setText(lfp.getCallsign());
		field_flight_plan_number.setText(""+lfp.getFlight_plan_number());
		field_adep.setText(lfp.getAdep());
		field_ades.setText(lfp.getAdes());
		field_aircraft_type.setText(lfp.getAircraft_type());
		field_aircraft_REG.setText(lfp.getAircraft_registration());
		field_aircraft_address.setText(lfp.getAircraft_address());
		field_atd.setText(sdf_time.format(lfp.getAtd()));
		field_ata.setText(sdf_time.format(lfp.getAta()));
		field_flight_rule.setText(lfp.getFlight_rule());
		field_flight_type.setText(lfp.getFlight_type());
		field_flight_category.setText(lfp.getFlight_category());
		field_movement_category.setText(lfp.getMovement_category());
		field_dof.setText(lfp.getDof());
		field_distance_flown_in_fir.setText(""+lfp.getDistance_flown_in_fir());
		field_entry_point.setText(lfp.getEntry_point());
		field_entry_hour.setText(sdf_datetime.format(lfp.getEntry_hour()));
		field_exit_point.setText(lfp.getExit_point());
		field_exit_hour.setText(sdf_datetime.format(lfp.getExit_hour()));
		field_sent_to_fdo.setText(lfp.getSent_to_fdo().toString());
		field_assrcode.setText(String.format("%04d", lfp.getAssr_code()));
		field_pssrcode.setText(String.format("%04d", lfp.getPssr_code()));
		field_etd.setText(sdf_time.format(lfp.getEtd()));
		field_eta.setText(sdf_time.format(lfp.getEta()));
		field_rfl.setText(""+lfp.getRfl());
		field_ufl.setText(""+lfp.getUfl());
		field_cfl.setText(""+lfp.getCfl());
		field_exit_protocal.setText(lfp.getExit_protocol());
		field_field_10a.setText(lfp.getField10a());
		field_field_10b.setText(lfp.getField10b());
		field_wtc.setText(lfp.getWtc());
		field_eobt.setText(sdf_time.format(lfp.getEobt()));
		field_eet.setText(sdf_time.format(lfp.getEet()));
		field_fpl_status.setText(lfp.getFpl_status());
		field_cfl_approach_mode.setText(lfp.getCfl_approach_mode());
		field_last_fix_overflown.setText(""+lfp.getLast_fix_overflown());
		field_dcc_identifier.setText(lfp.getDcc_identifier());
		field_aircraft_operator_name.setText(lfp.getAircraft_operator_name());
		field_route.setText(lfp.getRoute_field());
		

		//fixes
		DefaultTableModel dtm = (DefaultTableModel)tableFix.getModel();
		dtm.setRowCount(0);

		for(LocalFlightPlanFixesDB lfpf : lfpfs)
		{
			Field[] fs = LocalFlightPlanFixesDB.class.getDeclaredFields();
			Object[] rowdata = new Object[fs.length];
			for(int i=0;i<fs.length;i++)
			{
				Field f = fs[i];
				f.setAccessible(true);
				rowdata[i] = f.get(lfpf);
			}
			dtm.addRow(rowdata);	
		}
	}
}
