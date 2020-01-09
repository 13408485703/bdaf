package com.best.mmi.main;

import java.util.ArrayList;

import com.best.bdaf.dao.Skp;
import com.best.mmi.data.AirspaceList;
import com.best.mmi.data.ConstantData;
import com.best.mmi.data.IniData;
import com.best.mmi.data.LatLongData;
import com.best.mmi.data.MapList;
import com.best.mmi.data.NavData;
import com.best.mmi.data.SkpPo;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.view.CoordInputWinView;
import com.best.mmi.view.GotoView;
import com.best.mmi.view.MapView;
import com.best.mmi.view.MouseRightMenuView;
import com.best.mmi.view.RadarView;
import com.best.mmi.view.RangeRingsView;
import com.best.mmi.view.SkpPoControlView;

public class App
{ 
	private IniData m_pIniData;
	private SysInfo m_pSysInfo;
	private LatLongData m_pLatLongData;
	private MapList m_pMapList;
	private ToolList m_pToolList;
	private AirspaceList m_pAirspaceList;            
	private NavData m_pNavData; 
	private ArrayList<SkpPo> m_cSkpPos;
	
	private RadarView m_pRadarView;
	private MouseRightMenuView m_pMouseRightMenuView;
	private MapView m_pMapView;
	private RangeRingsView m_pRangeRingsView;
	private GotoView m_pGotoView;
	private CoordInputWinView m_pCoordInputWinView;
	private SkpPoControlView m_pSkpPoControlView;

	private static final App onlyOne = new App();
	
	public static App getApp()
	{
		return onlyOne;
	}
	
	private App() {}
		
	
//	public static void main(String[] args)
//	{
//		App.getApp().init(args);	
//	}
	
	public void init(String[] args)
	{
		try
		{	
			createObjs();
			
			initData();
			
			initView();	
		}
		catch(Exception e) {System.out.println("App init failed!");e.printStackTrace();}
	}
	
	public void createObjs()
	{
		m_pIniData = new IniData();
		ConstantData.init(m_pIniData);
		m_pSysInfo = new SysInfo();
		m_pSysInfo.init(m_pIniData);
		m_pLatLongData = new LatLongData();
		m_pLatLongData.init(m_pIniData);
			
		m_pMapList = new MapList();
		m_pToolList = new ToolList();
		
		m_pAirspaceList = new AirspaceList();
		m_pNavData = new NavData();
		m_cSkpPos = new ArrayList<SkpPo>(99);
		
		
		m_pRadarView = new RadarView("Main Display",0);
		
		m_pMouseRightMenuView = new MouseRightMenuView();
		m_pMapView = new MapView(m_pRadarView,"Map List Window",m_pMapList);
		m_pRangeRingsView = new RangeRingsView(m_pRadarView,"Range Ring Set Window");
		m_pGotoView = new GotoView(m_pRadarView,"Goto Window");
		m_pCoordInputWinView = new CoordInputWinView(m_pRadarView,"Coord Input Window");
		m_pSkpPoControlView = new SkpPoControlView(m_pRangeRingsView, "SkpPo Control Window");
	}
	
	public void initData()
	{
		m_pNavData.init();
		m_pToolList.init();
		m_pMapList.init();
		m_pAirspaceList.init();
	}
	
	public void initView()
	{
		m_pMouseRightMenuView.init();
		m_pMapView.init();
		m_pRangeRingsView.init();
		m_pGotoView.init();
		m_pCoordInputWinView.init();
		m_pSkpPoControlView.init();
		
		m_pRadarView.init();
	}
	
	public void previewExe(ArrayList<Skp> skps)
	{
		m_cSkpPos.clear();
		for(int i=0;i<skps.size();i++)
		{
			SkpPo sp = new SkpPo(skps.get(i));
			m_cSkpPos.add(sp);
		}
		
		m_pSkpPoControlView.reloadSkpPos(m_cSkpPos);
		
		m_pRadarView.setVisible(true);
	}
	

	public SysInfo getSysInfo() 			{return m_pSysInfo;}	
	public MapList getMapList() 			{return m_pMapList;}
	public ToolList getToolList()			{return m_pToolList;}	
	public IniData getIniData() 			{return m_pIniData;}
	public LatLongData getLatLongData() 	{return m_pLatLongData;}
	public AirspaceList getAirspaceList()	{return m_pAirspaceList;}
	public NavData getNavData() {return m_pNavData;}
	
	public RadarView getRadarView(int n) 		
	{
		if(n==0)
			return m_pRadarView;
		else
			return null;
	}
	public MapView getMapView() 			{return m_pMapView;}
	public RangeRingsView getRangeRingsView() {return m_pRangeRingsView;}
	public GotoView getGotoView() {return m_pGotoView;}
	public MouseRightMenuView getMouseRightMenuView() {return m_pMouseRightMenuView;}
	public CoordInputWinView  getCoordInputWinView() {return m_pCoordInputWinView;}
	public SkpPoControlView getSkpPoControlView() {return m_pSkpPoControlView;}
	public ArrayList<SkpPo> getSkpPos() {return m_cSkpPos;}
	
	
	public SkpPo findTrackPoFromXy(int x,int y,int radarViewNum)
	{
		SkpPo sp = null;
		
		for(int i=0;i<m_cSkpPos.size();i++) 
		{
			sp = m_cSkpPos.get(i);
			if(sp.getShow())
			{
				if((x > sp.getLabelX(radarViewNum)) &&   
					(x < (sp.getLabelX(radarViewNum) + sp.getLabelWidth())) && 
					(y > sp.getLabelY(radarViewNum)) &&
					(y < (sp.getLabelY(radarViewNum)) + sp.getLabelHeight()))
				{
					return sp;
				}
			}
		}
		return null;	
	}
}

