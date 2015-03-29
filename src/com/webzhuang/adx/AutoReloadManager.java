package com.webzhuang.adx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class AutoReloadManager extends TimerTask {
	private static AutoReloadManager instance = null;
	private static Logger logger = Logger.getLogger("System");
	public static int CHECK_INTERVAL = 3*60*1000;
	private List<IAutoReloadable> reloadableList = new ArrayList<IAutoReloadable>();
	private Timer timer = null;
	
	static{
		instance = new AutoReloadManager();
	}
	
	private AutoReloadManager(){
		timer = new Timer();
		timer.schedule(this, CHECK_INTERVAL, CHECK_INTERVAL);
	}
	
	public static AutoReloadManager getInstance() {
		return instance;
	}
	
	public void add(IAutoReloadable ar) {
		synchronized (reloadableList) {
			reloadableList.add(ar);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("Fire reload manager: reloadSize:" + reloadableList.size());
		synchronized (reloadableList) {
			for (IAutoReloadable ar : reloadableList) {
				ar.reload();
			}
		}
	}
	
}
