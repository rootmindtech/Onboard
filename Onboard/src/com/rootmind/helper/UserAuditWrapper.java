package com.rootmind.helper;

import java.math.BigDecimal;
import java.util.Date;

import com.rootmind.wrapper.AbstractWrapper;

public class UserAuditWrapper extends AbstractWrapper{

	public String userid=null;
	public Date datestamp=null;
	public String message=null;
	public String sessionid=null;
	public String activity=null;
	public String screenName=null;
	public BigDecimal seqno;
	public boolean recordFound=false;
	
	
	
}
