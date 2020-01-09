package com.best.bdaf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import com.best.bdaf.dao.Skp;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.BdafUtils;
import com.best.bdaf.view.subtype.MyVFlowLayout;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.Component;

import javax.swing.Box;

@SuppressWarnings("serial")
public class MergePanel extends JPanel implements ActionListener
{
	private ArrayList<JComboBox> exes = new ArrayList<JComboBox>();
	private Vector<String> exeNames = new Vector<String>(999);
	private JButton btnAddExe;
	private JButton btnOk;
	private JButton btnCancel;
	private JButton btnRemoveExe;
	private JPanel panelExe;
	private JPanel panel_1;
	private JComboBox cbExeTo;
	
	public MergePanel() 
	{
		for(int i=1;i<=999;i++)
			exeNames.add(String.format("%03d", i));
		
		setLayout(new MyVFlowLayout());
		
		JPanel panel1 = new JPanel();
		add(panel1);
		
		JLabel labelPrompt = new JLabel("Choose EXEs you want to merge. Then press OK.");
		panel1.add(labelPrompt);
		
		btnAddExe = new JButton("+");
		btnAddExe.addActionListener(this);
		panel1.add(btnAddExe);
		
		btnRemoveExe = new JButton("-");
		btnRemoveExe.addActionListener(this);
		panel1.add(btnRemoveExe);
		
		panelExe = new JPanel();
		add(panelExe);
		
		JLabel labelMerge = new JLabel("Merge:");
		panelExe.add(labelMerge);
		
		JComboBox cbExe = new JComboBox(exeNames);
		panelExe.add(cbExe);
		exes.add(cbExe);
		
		cbExe = new JComboBox(exeNames);
		panelExe.add(cbExe);
		exes.add(cbExe);
		
		panel_1 = new JPanel();
		add(panel_1);
		
		JLabel lblTo = new JLabel("To:");
		panel_1.add(lblTo);
		
		cbExeTo = new JComboBox(exeNames);
		panel_1.add(cbExeTo);
		
		JPanel panel = new JPanel();
		add(panel);
		
		btnOk = new JButton("  OK  ");
		btnOk.addActionListener(this);
		panel.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		panel.add(btnCancel);
	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object src = e.getSource();
		if(src == btnAddExe)
		{
			JComboBox cbExe = new JComboBox(exeNames);			
			exes.add(cbExe);
			panelExe.add(cbExe);
			panelExe.updateUI();
		}
		else if(src == btnRemoveExe)
		{
			if(!exes.isEmpty())
			{
				JComboBox cbExe = exes.get(exes.size()-1);
				exes.remove(cbExe);
				panelExe.remove(cbExe);
				panelExe.updateUI();
			}	
		}
		else if(src == btnOk)
		{
			try {
				//合并练习
				ArrayList<Skp> newSkps = new ArrayList<Skp>();
				for(JComboBox cbExe : exes)
				{
					String exenum = (String)cbExe.getSelectedItem();
					ArrayList<Skp> skps = BdafUtils.getSkpsFromExeFile(exenum);					
					newSkps.addAll(skps);
				}
				//存入练习
				String exenumTo = (String)cbExeTo.getSelectedItem();
				BdafUtils.writeSkpsToExe(exenumTo, newSkps);
				BdafUtils.writeSkpsumToExeList(exenumTo, newSkps.size());
				
				AppLogger.info("Merge exe finished!");
				
			} catch (Exception e2) {
				AppLogger.error(e2);
				AppLogger.info("Merge exe failed! Read log to trace the bug.");
			}
		}
		else if(src == btnCancel)
		{
		
			
		}
		
	}
}
