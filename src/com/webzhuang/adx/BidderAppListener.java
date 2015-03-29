package com.webzhuang.adx;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.spi.Configurator;

/**
 * Application Lifecycle Listener implementation class BidderAppListener
 *
 */
@WebListener
public class BidderAppListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger("System");
	private FileWatchdog log4jWatchdog = null;
	private FileWatchdog conFileWatchdog = null;
	private FileWatchdog smartTraceWatchdog = null;

    /**
     * Default constructor. 
     */
    public BidderAppListener() {
        // TODO Auto-generated constructor stub
    }
    
    private void onConfigChanged(String filename, boolean islog4j) {
		logger.info("Config file changed, islog4j"+islog4j);
		if (islog4j) {
			new PropertyConfigurator().doConfigure(filename, LogManager.getLoggerRepository());
		} else {
			//
			
		}
	}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	String urlPath = arg0.getServletContext().getRealPath("/WEB-INF/config/log4j.properties");
    	PropertyConfigurator.configure(urlPath);
    	log4jWatchdog = new FileWatchdog(urlPath) {
			
			@Override
			protected void doOnChange() {
				// TODO Auto-generated method stub
				onConfigChanged(filename, true);
				
			}
		};
		log4jWatchdog.setDelay(180000);
		log4jWatchdog.start();
		logger.info("Setup Bidder environment");
		
		String configPath = arg0.getServletContext().getRealPath("/WEB-INF/config");
		String configFile = configPath+File.separator+"config.properties";
		conFileWatchdog = new FileWatchdog(configFile) {
			
			@Override
			protected void doOnChange() {
				// TODO Auto-generated method stub
				onConfigChanged(filename, false);
				
			}
		};
		conFileWatchdog.setDelay(180000);
		conFileWatchdog.start();
		
		String smartTracePath = arg0.getServletContext().getRealPath("/WEB-INF/config/smarttrace.properties");
		File smartTraceFile = new File(smartTracePath);
		if (smartTraceFile.exists()){
			smartTraceWatchdog = new FileWatchdog(smartTraceFile.getAbsolutePath()) {
				@Override
				protected void doOnChange() {
					//SmartTraceManager.getInstance().reload(filename);
				}
			};
			smartTraceWatchdog.setDelay(180000);
			smartTraceWatchdog.start();
		}
		
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
