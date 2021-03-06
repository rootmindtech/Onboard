package com.rootmind.helper;

import java.math.BigDecimal;

import com.rootmind.wrapper.AbstractWrapper;

public class UsersWrapper extends AbstractWrapper{
	
	
	public String userid=null;
	public String name=null;
	public String mobileNo=null;
	public String email=null;
	public String password=null;
	public String status=null;
	public String cifNumber=null;
	public String lastLoginDate=null;
	public String deviceToken=null;
	public BigDecimal trnLimit=null;
	public BigDecimal trnDayLimit=null;	
	public int noLoginRetry=0;
	public int maxRetry=0;
	public String sessionid=null;
	public String pwdChgDate=null;
	public int otp=0;
	public String businessUnit=null;
	public int sessionExpiryTime=0;
	public String userGroup=null;
	public String oldPassword=null;
	public String ipAddress=null;
	public boolean validUser=false;
	public boolean passwordChanged=false;
	public boolean oldPasswordRepeat=false;
	public boolean invalidOldPassword=false;
	
	public String workflowGroup=null; //SALESUSER,TEAMLEAD,BDMUSER,BOUSER,CREDITUSER
	
	UserMenuWrapper[] userMenuWrapper=null;
	public boolean recordFound=false;
			

}
