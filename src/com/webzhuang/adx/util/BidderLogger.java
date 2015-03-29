package com.webzhuang.adx.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class BidderLogger {
	private static Logger logger = Logger.getLogger("Bidder");
	private long reqNo = 0;
	private String reqId = "";
	private long threadid= 0;
	long startTime = System.currentTimeMillis();
	List<String> buffer = new ArrayList<String> ();
	List<Boolean> bufferTag = new ArrayList<Boolean> ();
	
	private boolean isBuffered = false;
	
	public BidderLogger(long reqNo, String reqId){
		this.reqNo = reqNo;
		this.reqId = reqId;
		this.threadid = Thread.currentThread().getId();
	}
	
	public void debug(String msg){
		debug(msg, false);
	}
	
	public void debug(String msg, boolean alwaysShow){
		if(isBuffered){
			buffer.add(formatMsg(msg));
			bufferTag.add(new Boolean(alwaysShow));
		}else{
			logger.debug(formatMsg(msg));
		}
	}
	public void debug(String msg, Exception ex){
		if(isBuffered){
			String errmsg = formatMsg(msg);
			buffer.add(errmsg);
			bufferTag.add(new Boolean(true));
			//Record exceptions immediately
			logger.debug(errmsg, ex);
			
		}else{
			logger.debug(formatMsg(msg), ex);
		}
		
	}
	
	public void info(String msg){
		if(isBuffered){
			buffer.add(formatMsg(msg));
			bufferTag.add(new Boolean(false));
		}else{
			logger.info(formatMsg(msg));
		}
		
	}
	
	public void info(String msg, Exception ex){
		if(isBuffered){
			String errmsg = formatMsg(msg);
			buffer.add(errmsg);
			bufferTag.add(new Boolean(true));
			//Record exceptions immediately
			logger.info(errmsg, ex);
			
		}else{
			logger.info(formatMsg(msg), ex);
		}
		
	}
	
	public void warn(String msg){
		
		if(isBuffered){
			buffer.add(formatMsg(msg));
			bufferTag.add(new Boolean(false));
		}else{
			logger.warn(formatMsg(msg));
		}
	}
	
	public void warn(String msg,Exception ex){	
		if(isBuffered){
			String errmsg = formatMsg(msg);
			buffer.add(errmsg);
			bufferTag.add(new Boolean(true));
			//Record exceptions immediately
			logger.warn(errmsg, ex);
		}else{
			logger.warn(formatMsg(msg), ex);
		}
	}
	
	public void error(String msg){
		if(isBuffered){
			buffer.add(formatMsg(msg));
			bufferTag.add(new Boolean(false));
		}else{
			logger.error(formatMsg(msg));
		}
	}
	
	public void error(String msg, Exception ex){	
		if(isBuffered){
			String errmsg = formatMsg(msg);
			buffer.add(errmsg);
			bufferTag.add(new Boolean(true));
			//Record exceptions immediately
			logger.error(errmsg, ex);
		}else{
			logger.error(formatMsg(msg),ex);
		}
	}


	private String formatMsg(String msg) {
		long processtime = System.currentTimeMillis() - startTime;
				
		StringBuffer sb = new StringBuffer();
		sb.append("Threadid=")
			.append(threadid)
			.append(",reqId=")
			.append(reqId)
			.append(",reqNo=")
			.append(reqNo)
			.append(",ptime=")
			.append(processtime).append("ms")
			.append("::")
			.append(msg);
		
		return sb.toString();
	}
	
	//0: detailed, 1:short
	public void output(int level, boolean hasBid){
		if(!isBuffered){
			return;
		}
		
		List<String> outBuffer = new ArrayList<String>();
		if(level==0 || hasBid){
			outBuffer =  buffer;
		}else{
			if (buffer.size() == bufferTag.size()){
				for (int i = 0; i < buffer.size();i++){
					if (bufferTag.get(i)){
						outBuffer.add(buffer.get(i));
					}
				}
			}else{
				for(String line : buffer){
					//Only output following lines:
					//if(line.contains("search googleId") || line.contains("Output response:")
					//		|| line.contains("Error when")
					//		|| line.contains("Bid Slot brief information:")){
						outBuffer.add(line);
					//}
				}
			}
		}
		
		for(String line : outBuffer){
			logger.info(line);
		}
	}
	

	public long getReqNo() {
		return reqNo;
	}

	public void setReqNo(long reqNo) {
		this.reqNo = reqNo;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public long getThreadid() {
		return threadid;
	}

	public void setThreadid(long threadid) {
		this.threadid = threadid;
	}
	
	public boolean isBuffered() {
		return isBuffered;
	}

	public void setBuffered(boolean isBuffered) {
		this.isBuffered = isBuffered;
	}
}
