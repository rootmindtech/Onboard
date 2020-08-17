package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;




public class UsersHelper extends Helper{
	
	
	
	/*public AbstractWrapper getCIFNumber(String userid)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	UsersWrapper usersWrapper=	new UsersWrapper();

	SimpleDateFormat dmyFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy, hh:mm:ss");

	
	System.out.println("userid :"+ userid);

	try {
		con = getConnection("MOBILEROOT");
		PreparedStatement pstmt = con.prepareStatement("SELECT userid,password,status,CIFNumber,"
				+ "Lastlogindate,Devicetoken,NoLoginRetry from Users  where userid=?");
	
		pstmt.setString(1,userid.trim());
		resultSet = pstmt.executeQuery();
		if (resultSet.next()) 
		{

			
			usersWrapper.userid=resultSet.getString("userid");
			System.out.println("userid " + usersWrapper.userid);
			
			usersWrapper.password=resultSet.getString("password");
			System.out.println("password " + usersWrapper.password);

			usersWrapper.status=resultSet.getString("status");
			
			usersWrapper.cifNumber=resultSet.getString("CIFNumber");
			System.out.println("CIFNumber " + usersWrapper.cifNumber);
			
			System.out.println("last login date " + resultSet.getDate("Lastlogindate"));

			usersWrapper.lastLoginDate= (resultSet.getDate("Lastlogindate")==null?"":dmyFormat.format(resultSet.getTimestamp("Lastlogindate")));
			
			usersWrapper.deviceToken=resultSet.getString("Devicetoken");
			System.out.println("Devicetoken " + usersWrapper.deviceToken);
			
			usersWrapper.noLoginRetry=resultSet.getInt("NoLoginRetry");
			
			usersWrapper.recordFound=true;
			
			System.out.println("usersWrapper " + usersWrapper.lastLoginDate);
			

		}
		
	

		pstmt.close();


		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	return usersWrapper;
}*/



public AbstractWrapper updateUserAudit(String userid, String sessionid,String activity,String screenName,String message)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	UsersWrapper usersWrapper=	new UsersWrapper();

	
	System.out.println("userid :"+ userid);

	try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO UserAudit(userid,sessionid,Activity,"
					+ "Screenname,Datestamp,Message) values (?,?,?,?,?,?) ");
			
			pstmt.setString(1,(userid==null?null:userid.trim()));
			pstmt.setString(2,(sessionid==null?null:sessionid.trim()));
			pstmt.setString(3,(activity==null?null:activity.trim()));
			pstmt.setString(4,screenName.trim());
			pstmt.setTimestamp(5,new java.sql.Timestamp(System.currentTimeMillis()));
			//System.out.println("updateUserAudit Message "+message);
			pstmt.setString(6,(message==null?null:message.trim()));
			pstmt.executeUpdate();
	
			pstmt.close();
	
			/*UsersWrapper tmpUsersWrapper=	new UsersWrapper();
			tmpUsersWrapper=(UsersWrapper)getCIFNumber(userid);
			
			pstmt = con.prepareStatement("UPDATE Users set Lastlogindate=?, NoLoginRetry=? where userid=?");
			
			pstmt.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2,tmpUsersWrapper.noLoginRetry+1);
			pstmt.setString(3,userid.trim());
	
			pstmt.executeUpdate();
	
			pstmt.close();*/
			
			usersWrapper.recordFound=true;
			
			System.out.println("user audit table updated " );
		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	return usersWrapper;
}

public void updateUserDetails(String userid,int noLoginRetry,String sessionid, String deviceToken)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	//UsersWrapper usersWrapper=	new UsersWrapper();

	
	System.out.println("userid :"+ userid);

	try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("UPDATE Users set Lastlogindate=?, NoLoginRetry=?,sessionid=?,DeviceToken=?  where userid=?");
			
			pstmt.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setInt(2,noLoginRetry+1);
			pstmt.setString(3,(sessionid==null?"":sessionid.trim()));
			pstmt.setString(4,(deviceToken==null?"":deviceToken.trim()));
			pstmt.setString(5,(userid==null?"":userid.trim()));
	
			pstmt.executeUpdate();
	
			pstmt.close();
			//usersWrapper.noLoginRetry=usersWrapper.noLoginRetry+1;
			//usersWrapper.sessionid=(sessionid==null?"":sessionid.trim());
			//usersWrapper.deviceToken=(deviceToken==null?"":deviceToken.trim());
	
			
	
			//usersWrapper.recordFound=true;
			
			System.out.println("user table device updated " );
		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	//return usersWrapper;
}

public AbstractWrapper validateCredentials(UsersWrapper usersWrapper)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
	//UsersWrapper usersWrapper=	new UsersWrapper();
	usersWrapper.validUser=false;
	usersWrapper.recordFound=false;
	SimpleDateFormat dmyFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy, hh:mm:ss");
	
	PreparedStatement pstmt=null;
	Vector<Object> vector = new Vector<Object>();
	//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
	
	System.out.println("userid :"+ usersWrapper.userid);

	try {
		
			con = getConnection();
			pstmt = con.prepareStatement("SELECT userid,password,status,CIFNumber,"
					+ "Lastlogindate,Devicetoken,NoLoginRetry,MaxRetry,sessionid,PwdChgDate,OTP,BusinessUnit,SessionExpirytime,UserGroup, WorkflowGroup FROM Users  WHERE userid=?");
		
			pstmt.setString(1,usersWrapper.userid.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				 usersWrapper=new UsersWrapper();
				
				usersWrapper.userid=(resultSet.getString("userid")==null?"":resultSet.getString("userid").trim());
				System.out.println("userid " + usersWrapper.userid);
				
				usersWrapper.password=(resultSet.getString("password")==null?"":resultSet.getString("password").trim());
				System.out.println("password " + usersWrapper.password);
	
				usersWrapper.status=(resultSet.getString("status")==null?"":resultSet.getString("status").trim());
				
				usersWrapper.cifNumber=(resultSet.getString("CIFNumber")==null?"":resultSet.getString("CIFNumber").trim());
				System.out.println("CIFNumber " + usersWrapper.cifNumber);
				
				System.out.println("last login date " + resultSet.getDate("Lastlogindate"));
	
				usersWrapper.lastLoginDate= (resultSet.getDate("Lastlogindate")==null?"":dmyFormat.format(resultSet.getTimestamp("Lastlogindate")));
				
				usersWrapper.deviceToken=(resultSet.getString("Devicetoken")==null?"":resultSet.getString("Devicetoken").trim());
				System.out.println("Devicetoken " + usersWrapper.deviceToken);
				
				usersWrapper.noLoginRetry=resultSet.getInt("NoLoginRetry");
				
				usersWrapper.maxRetry=resultSet.getInt("MaxRetry");
				
				usersWrapper.sessionid=(resultSet.getString("sessionid")==null?"":resultSet.getString("sessionid").trim());
				
				usersWrapper.pwdChgDate= (resultSet.getDate("PwdChgDate")==null?"":dmyFormat.format(resultSet.getTimestamp("PwdChgDate")));
				
				usersWrapper.otp=resultSet.getInt("OTP");
				
				usersWrapper.businessUnit=(resultSet.getString("BusinessUnit")==null?"":resultSet.getString("BusinessUnit").trim());
				
				usersWrapper.sessionExpiryTime=resultSet.getInt("SessionExpirytime");
				
				usersWrapper.userGroup=(resultSet.getString("UserGroup")==null?"":resultSet.getString("UserGroup").trim());
				usersWrapper.workflowGroup=(resultSet.getString("WorkflowGroup")==null?"":resultSet.getString("WorkflowGroup").trim());
				
				usersWrapper.recordFound=true;
				
				
				System.out.println("usersWrapper " + usersWrapper.lastLoginDate);
				
	
			}
	
			resultSet.close();
			pstmt.close();
		
		/*dataArrayWrapper.usersWrapper=new UsersWrapper[1];
		dataArrayWrapper.usersWrapper[0]=usersWrapper;*/
		
		
		//------------User Menu id----------------
		
		if(usersWrapper.recordFound==true)
		{
				UserMenuWrapper userMenuWrapper=null;  //new UserMenuWrapper();
				pstmt = con.prepareStatement("SELECT Id,UserId,MenuId FROM RMT_UserMenu WHERE userid=?");
				pstmt.setString(1,usersWrapper.userid.trim());
		
				resultSet = pstmt.executeQuery();
				
				vector.clear();
				
				while(resultSet.next()) 
				{
					
					userMenuWrapper = new UserMenuWrapper();
					
					userMenuWrapper.id = Utility.trim(resultSet.getString("Id"));
					System.out.println("Id " + userMenuWrapper.id);
			
					userMenuWrapper.userId = Utility.trim(resultSet.getString("UserId"));
					System.out.println("UserId" + userMenuWrapper.userId);
			
					userMenuWrapper.menuId = Utility.trim(resultSet.getString("MenuId"));
					System.out.println("MenuId" + userMenuWrapper.menuId);
					
					userMenuWrapper.recordFound = true;
					
					vector.addElement(userMenuWrapper);
					
					
					System.out.println("Fetch UserMenu Details Successful" );
			
				}
		
				if (vector.size()>0)
				{
					
					usersWrapper.userMenuWrapper = new UserMenuWrapper[vector.size()];
					vector.copyInto(usersWrapper.userMenuWrapper);
					
					System.out.println("total trn. in fetch " + vector.size());
			
				}
				/*else // ----IF USER NOT FOUND-------
				{
					usersWrapper.userMenuWrapper = new UserMenuWrapper[1];
					usersWrapper.userMenuWrapper[0]= userMenuWrapper;
					
				}*/
				//dataArrayWrapper.recordFound=true;
				resultSet.close();
				pstmt.close();

		}
		//-----------User Menu Id end
		
		
		
		
	/*	//--user not found validation ---
		if(usersWrapper.recordFound==false)
		{
			System.out.println("usersWrapper.recordFound :"+ usersWrapper.recordFound);
			usersWrapper.validUser=false;
			//dataArrayWrapper.usersWrapper[0]=usersWrapper;
			//dataArrayWrapper.recordFound=true;
			return usersWrapper;
		}
		else if (usersWrapper.recordFound==true)
		{
			//--inactive user validation
			if(!usersWrapper.status.trim().equals("ACTIVE"))
			{
				System.out.println("usersWrapper.status :"+ usersWrapper.status);
				usersWrapper.validUser=false;
				//dataArrayWrapper.usersWrapper[0]=usersWrapper;
				//dataArrayWrapper.recordFound=true;
				return usersWrapper;
			}
			//--invalid session id validation
			else if(newSession==false && !sessionid.trim().equals(usersWrapper.sessionid))
			{
				System.out.println("sessionid :"+ sessionid);
				System.out.println("usersWrapper.sessionid :"+ usersWrapper.sessionid);
				usersWrapper.validUser=false;
				//dataArrayWrapper.usersWrapper[0]=usersWrapper;
				//dataArrayWrapper.recordFound=true;
				return usersWrapper;
			}
			else
			{
				//System.out.println("update usersWrapper.noLoginRetry :"+ usersWrapper.noLoginRetry);
				
				if(newSession==true)
				{
					pstmt = con.prepareStatement("UPDATE Users set Lastlogindate=?, NoLoginRetry=?,sessionid=?,DeviceToken=?  where userid=?");
					
					pstmt.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
					pstmt.setInt(2,usersWrapper.noLoginRetry+1);
					pstmt.setString(3,(sessionid==null?"":sessionid.trim()));
					pstmt.setString(4,(deviceToken==null?"":deviceToken.trim()));
					pstmt.setString(5,(userid==null?"":userid.trim()));

					pstmt.executeUpdate();

					pstmt.close();
					usersWrapper.noLoginRetry=usersWrapper.noLoginRetry+1;
					usersWrapper.sessionid=(sessionid==null?"":sessionid.trim());
					usersWrapper.deviceToken=(deviceToken==null?"":deviceToken.trim());
				}
				
				usersWrapper.validUser=true;
				//dataArrayWrapper.usersWrapper[0]=usersWrapper;
				//dataArrayWrapper.recordFound=true;
				System.out.println("validate credentials end");
			}
			
		}*/
		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	
	
	return usersWrapper;
	

}
public AbstractWrapper fetchPasswordPolicy(PasswordWrapper passwordWrapper)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	
	DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
	
	//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
	DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
	DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
	symbols.setGroupingSeparator(',');
	formatter.applyPattern("###,###,###,##0.00");
	formatter.setDecimalFormatSymbols(symbols);
	

	try {
		
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT MinLength, MaxLength, CapitalLetter, SpecialCharacter, OldPasswordRepeat FROM RMT_PasswordPolicy");
		
			
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				
				passwordWrapper = new PasswordWrapper();
				
				passwordWrapper.minLength=Utility.trim(resultSet.getString("MinLength"));
				System.out.println("minLength " + passwordWrapper.minLength);
				passwordWrapper.maxLength=Utility.trim(resultSet.getString("MaxLength"));
				passwordWrapper.capitalLetter=Utility.trim(resultSet.getString("CapitalLetter"));
				passwordWrapper.specialCharacter=Utility.trim(resultSet.getString("SpecialCharacter"));
				passwordWrapper.oldPasswordRepeat=Utility.trim(resultSet.getString("OldPasswordRepeat"));		
				
				passwordWrapper.recordFound=true;
			    passwordWrapper.policyAvailable=true;
			  
				System.out.println("PasswordWrapper");

			}
			
				dataArrayWrapper.passwordWrapper = new PasswordWrapper[1];				
				dataArrayWrapper.passwordWrapper[0]=passwordWrapper;
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetchPasswordPolicy ");

			
			
		resultSet.close();	
		pstmt.close();
        
		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	return dataArrayWrapper;
}

public AbstractWrapper changePassword(UsersWrapper usersWrapper)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();

	
	System.out.println("userid :"+ usersWrapper.userid);
    String oldPasswordRepeat=null;
	try {
		
		con = getConnection();
		PreparedStatement pstmt=null;
		
		//-----check password valid or not
		pstmt = con.prepareStatement("SELECT Password FROM Users WHERE USERID=?");
		pstmt.setString(1, usersWrapper.userid);
		resultSet = pstmt.executeQuery();
		if(resultSet.next()){
			
			
		  if(!usersWrapper.oldPassword.trim().equals(Utility.trim(resultSet.getString("Password"))))
		  {
			  usersWrapper.invalidOldPassword=true;
			  usersWrapper.recordFound=true;
		  }
		   
		   
		}
		resultSet.close();
		pstmt.close();
		//-------
		
		if(usersWrapper.invalidOldPassword==false)
		{
			
		
		
				//------------repeat password verification
				pstmt = con.prepareStatement("SELECT OldPasswordRepeat FROM RMT_PasswordPolicy");
				
				resultSet = pstmt.executeQuery();
				if(resultSet.next()){
					
				   oldPasswordRepeat=Utility.trim(resultSet.getString("OldPasswordRepeat"));	
				   
				   System.out.println("oldPasswordRepeat "+oldPasswordRepeat );
				}
				
				resultSet.close();
				pstmt.close();
				
				
				if(oldPasswordRepeat !=null && !oldPasswordRepeat.trim().equals(""))
				{
					pstmt = con.prepareStatement("SELECT OldPassword FROM RMT_PasswordHistory Where UserId=?");
					
					pstmt.setString(1,Utility.trim(usersWrapper.userid));
					
					resultSet = pstmt.executeQuery();
					
					ArrayList<String> oldPassword = new ArrayList<String>();
					while(resultSet.next()){
						
						oldPassword.add(Utility.trim(resultSet.getString("OldPassword")));	
						
						 System.out.println("OldPassword "+Utility.trim(resultSet.getString("OldPassword")));
					}
					
					resultSet.close();
					pstmt.close();
					
					for(int i=0;i<= Integer.parseInt(oldPasswordRepeat)-1;i++){
						
						System.out.println("PASSWORD "+usersWrapper.password.trim());
						System.out.println("OldPassword "+oldPassword.get(i).trim());
						
						if(usersWrapper.password.trim().equals(oldPassword.get(i).trim())){
							
							usersWrapper.oldPasswordRepeat=true;
							usersWrapper.recordFound=true;
						}
					}
				}
				
				if(usersWrapper.oldPasswordRepeat==false){
					
					pstmt = con.prepareStatement("UPDATE Users set Password=? where userid=?");
					pstmt.setString(1,Utility.trim(usersWrapper.password));
					pstmt.setString(2,Utility.trim(usersWrapper.userid));
		
					pstmt.executeUpdate();
		
					pstmt.close();
				
					
					System.out.println("Change password details updated " );
					
					
					pstmt = con.prepareStatement("INSERT INTO RMT_PasswordHistory (UserId,OldPassword,PasswordChngDT,IPAddress) values (?,?,?,?)");
					
					pstmt.setString(1,Utility.trim(usersWrapper.userid));
					pstmt.setString(2,Utility.trim(usersWrapper.oldPassword));
					pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
					pstmt.setString(4,Utility.trim(usersWrapper.ipAddress));
				
					pstmt.executeUpdate();
		
					pstmt.close();
					
					System.out.println("PasswordHistory Details Inserted" );
					
					usersWrapper.recordFound=true;
					usersWrapper.passwordChanged=true;
					
				}
				
		}		
		dataArrayWrapper.usersWrapper=new UsersWrapper[1];
		dataArrayWrapper.usersWrapper[0]=usersWrapper;
		
		dataArrayWrapper.recordFound=true;
		
		
	} catch (SQLException se) {
		se.printStackTrace();
		throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
	} catch (NamingException ne) {
		ne.printStackTrace();
		throw new NamingException(ne.getMessage());
	}
	 catch (Exception ex) {
		ex.printStackTrace();
		throw new Exception(ex.getMessage());
	}
	finally
	{
		try
		{
			releaseConnection(resultSet, con);
		} 
		catch (SQLException se)
		{
			se.printStackTrace();
			throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
		}
	}

	return dataArrayWrapper;
}

		public AbstractWrapper fetchUsersList(UsersWrapper usersWrapper)throws Exception {
		
			Connection con = null;
			ResultSet resultSet = null;
			
			DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
			
			//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Vector<Object> vector = new Vector<Object>();
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			formatter.applyPattern("###,###,###,##0.00");
			formatter.setDecimalFormatSymbols(symbols);
			//UsersWrapper usersWrapper=new UsersWrapper();
			String sql=null;
		
			try {
				
					
					con = getConnection();
					
					sql="SELECT userid, Name, MobileNo, Email, status, UserGroup FROM Users WHERE UserGroup='MAKER'";
				
					
					
					if(usersWrapper.userid !=null){
						
						sql=sql + " AND userid=?";
					}

					PreparedStatement pstmt = con.prepareStatement(sql);
					
					//pstmt.setString(1, "MAKER");
					
					if(usersWrapper.userid !=null){
						
						pstmt.setString(1,Utility.trim(usersWrapper.userid));	
					}
					
				
					
					resultSet = pstmt.executeQuery();
					while (resultSet.next()) 
					{
						
						usersWrapper = new UsersWrapper();
						
						usersWrapper.userid=Utility.trim(resultSet.getString("userid"));
						usersWrapper.name=Utility.trim(resultSet.getString("Name"));
						usersWrapper.mobileNo=Utility.trim(resultSet.getString("MobileNo"));
						usersWrapper.email=Utility.trim(resultSet.getString("Email"));
						System.out.println(" fetchUsersList userid  " + usersWrapper.userid);
						usersWrapper.status=Utility.trim(resultSet.getString("status"));
						usersWrapper.userGroup=Utility.trim(resultSet.getString("UserGroup"));
					
						
						usersWrapper.recordFound=true;
						
						vector.addElement(usersWrapper);
		
					}
					if(vector.size()>0){
						dataArrayWrapper.usersWrapper = new UsersWrapper[vector.size()];	
						vector.copyInto(dataArrayWrapper.usersWrapper);
						dataArrayWrapper.recordFound=true;
						
						System.out.println("total trn. in fetchUsersList ");
					}
					else{
						
						dataArrayWrapper.usersWrapper = new UsersWrapper[1];				
						dataArrayWrapper.usersWrapper[0]=usersWrapper;
						dataArrayWrapper.recordFound=true;
					}
					

					resultSet.close();	
					pstmt.close();
		        
				
				
			} catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				try
				{
					releaseConnection(resultSet, con);
				} 
				catch (SQLException se)
				{
					se.printStackTrace();
					throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
				}
			}
		
			return dataArrayWrapper;
		}
		
		public void updateSessionDetails(String userid,int noLoginRetry,String sessionid, String deviceToken)throws Exception {

			Connection con = null;
			ResultSet resultSet = null;
			//UsersWrapper usersWrapper=	new UsersWrapper();

			
			System.out.println("userid :"+ userid);
			String sql=null;
			PreparedStatement pstmt = null;
			String userGroup=null;

			try {
					con = getConnection();
					
					//----- code--
					
					sql="SELECT UserGroup from Users WHERE Userid=?";
					
				
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,userid.trim());
				
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) 
					{
							
						userGroup=Utility.trim(resultSet.getString("UserGroup"));
						
					}
					
					resultSet.close();
					pstmt.close();
					
					//System.out.println("user group is "+userGroup.trim() );
					
					
					
					//----------do not update Device id for Staff and admin, update only for Students when not null
					if(userGroup != null && userGroup.trim().equals("STUDENT") && deviceToken !=null && !deviceToken.trim().equals(""))
					{
						sql="UPDATE Users set  NoLoginRetry=?,sessionid=?,DeviceToken=?  where userid=?";
					}
					else
					{
						sql="UPDATE Users set  NoLoginRetry=?,sessionid=?  where userid=?";
					}
					
					pstmt = con.prepareStatement(sql);
					
					
					pstmt.setInt(1,noLoginRetry+1);
					pstmt.setString(2,(sessionid==null?"":sessionid.trim()));
					
					
					if(userGroup != null && userGroup.trim().equals("STUDENT") && deviceToken !=null && !deviceToken.trim().equals(""))
					{
						pstmt.setString(3,(deviceToken==null?"":deviceToken.trim()));
						pstmt.setString(4,(userid==null?"":userid.trim()));
					}
					else
					{
						pstmt.setString(3,(userid==null?"":userid.trim()));
					}
	
					pstmt.executeUpdate();
	
					pstmt.close();
					//usersWrapper.noLoginRetry=usersWrapper.noLoginRetry+1;
					//usersWrapper.sessionid=(sessionid==null?"":sessionid.trim());
					//usersWrapper.deviceToken=(deviceToken==null?"":deviceToken.trim());
	
					pstmt.close();
	
					//usersWrapper.recordFound=true;
					
					System.out.println("user table session updated " );
				
				
			} catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				try
				{
					releaseConnection(resultSet, con);
				} 
				catch (SQLException se)
				{
					se.printStackTrace();
					throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
				}
			}

			//return usersWrapper;
		}
		
		public AbstractWrapper fetchSessionDetails(String userid)throws Exception {

			Connection con = null;
			ResultSet resultSet = null;
			UsersWrapper usersWrapper=null;
			
			//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
			
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			formatter.applyPattern("###,###,###,##0.00");
			formatter.setDecimalFormatSymbols(symbols);
			PreparedStatement pstmt=null;
			int sessionTimeOut=0;

			try {
		
					con = getConnection();
					
					//--------
					
					
					pstmt = con.prepareStatement("SELECT SessionTimeOut from RMT_Parameter");
				
					resultSet = pstmt.executeQuery();
					if(resultSet.next()) 
					{
													
						sessionTimeOut=resultSet.getInt("SessionTimeOut");
						
					}
					
					resultSet.close();
					pstmt.close();
					//----------
					
					pstmt = con.prepareStatement("SELECT Userid,Lastlogindate,Sessionid FROM Users WHERE Userid=?");
				
					pstmt.setString(1,userid);
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) 
					{
						usersWrapper= new UsersWrapper();
						
						usersWrapper.userid=Utility.trim(resultSet.getString("Userid"));
						System.out.println("minLength " + usersWrapper.userid);
						
						usersWrapper.lastLoginDate=resultSet.getString("Lastlogindate");	
						
						usersWrapper.sessionid=Utility.trim(resultSet.getString("Sessionid"));
						
						usersWrapper.recordFound=true;
						
						/*System.out.println("Lastlogindate  "+usersWrapper.lastLoginDate);
						System.out.println("Time diff "+Utility.timeDifference(usersWrapper.lastLoginDate));
						System.out.println("SessionTimeOut "+sessionTimeOut);
						
						if(Utility.timeDifference(usersWrapper.lastLoginDate)>sessionTimeOut)
						{
							usersWrapper.sessionTimeOut=true;
							System.out.println("Session Timed Out ");
						}*/
						

					}
					

					resultSet.close();	
					pstmt.close();
		        
				
				
			} catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				try
				{
					releaseConnection(resultSet, con);
				} 
				catch (SQLException se)
				{
					se.printStackTrace();
					throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
				}
			}

			return usersWrapper;
		}
		
		
		public AbstractWrapper insertLoginProfile(UsersWrapper usersProfileWrapper,UsersWrapper usersWrapper)throws Exception {
			
			Connection con = null;
			ResultSet resultSet = null;
		
			DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
			
			PreparedStatement pstmt=null;
			
			//String defaultPassword=null;
			String sql=null;

			try {
						//AES128Crypto aes128Crypto=new AES128Crypto();
					
						con = getConnection();
						
						sql="INSERT INTO Users(Userid, Name, Password, MobileNo, Email, Status, UserGroup, "
							 		+ " MakerID, MakerDateTime) Values(?,?,?,?,?,?,?,?,?)";
						
						 pstmt = con.prepareStatement(sql); 
						 
					     System.out.println("Userid is "+Utility.trim(usersWrapper.userid) );
					     
						 pstmt.setString(1,Utility.trim(usersWrapper.userid));
						 pstmt.setString(2,Utility.trim(usersWrapper.name));
						 pstmt.setString(3,Utility.trim(usersWrapper.password));//aes128Crypto.md5DB(Utility.trim(usersWrapper.password)));
						 pstmt.setString(4,Utility.trim(usersWrapper.mobileNo));
						 pstmt.setString(5,Utility.trim(usersWrapper.email));
						 pstmt.setString(6,Utility.trim(usersWrapper.status));
						 pstmt.setString(7,Utility.trim(usersWrapper.userGroup));
						 pstmt.setString(8,Utility.trim(usersProfileWrapper.userid));		
						 pstmt.setTimestamp(9,Utility.getCurrentTime());
						 
						 pstmt.executeUpdate();
						 pstmt.close();
						 usersWrapper.password=null;
						 usersWrapper.recordFound=true;
						
						dataArrayWrapper.usersWrapper=new UsersWrapper[1];
						dataArrayWrapper.usersWrapper[0]=usersWrapper;
						dataArrayWrapper.recordFound=true;
					
						System.out.println("userprofile inserted " + usersWrapper.userid);
				
				
				
			}
			
			 catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				try
				{
					releaseConnection(resultSet, con);
				} 
				catch (SQLException se)
				{
					se.printStackTrace();
					throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
				}
			}
		
			return dataArrayWrapper;
		}
		
		public AbstractWrapper updateLoginProfile(UsersWrapper usersProfileWrapper,UsersWrapper usersWrapper)throws Exception {

			Connection con = null;
			ResultSet resultSet = null;
			
		
			DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
			
			PreparedStatement pstmt=null;
			
			//String defaultPassword=null;
			String sql=null;
			
		
		
			try {
					//AES128Crypto aes128Crypto=new AES128Crypto();
					
					con = getConnection();
					
					sql="UPDATE Users SET Name=?, MobileNo=?, Email=?, Status=?, ModifierID=?, ModifierDateTime=?  where Userid=? ";
					 
					 
					 pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1,Utility.trim(usersWrapper.name));				
				 	pstmt.setString(2,Utility.trim(usersWrapper.mobileNo));
				 	pstmt.setString(3,Utility.trim(usersWrapper.email));
				 	pstmt.setString(4,Utility.trim(usersWrapper.status));
					pstmt.setString(5,Utility.trim(usersProfileWrapper.userid));
					pstmt.setTimestamp(6,Utility.getCurrentTime());
					pstmt.setString(7,Utility.trim(usersWrapper.userid));			
					
					pstmt.executeUpdate();
					pstmt.close();
					
					
					if(Utility.trim(usersWrapper.password) != null){
						
						sql="UPDATE Users SET Password=?, ModifierID=?, ModifierDateTime=?  where Userid=? ";
						 
						 
						pstmt = con.prepareStatement(sql);
						
						
						pstmt.setString(1,Utility.trim(usersWrapper.password));    //aes128Crypto.md5DB(Utility.trim(usersWrapper.password)));
						pstmt.setString(2,Utility.trim(usersProfileWrapper.userid));
						pstmt.setTimestamp(3,Utility.getCurrentTime());
						pstmt.setString(4,Utility.trim(usersWrapper.userid));			
						
						pstmt.executeUpdate();
						pstmt.close();
						
					}
					usersWrapper.password=null;
					usersWrapper.recordFound=true;
								
					dataArrayWrapper.usersWrapper=new UsersWrapper[1];
					dataArrayWrapper.usersWrapper[0]=usersWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("Login Profile updated " );
					
				
				
				
			}
			
			 catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				try
				{
					releaseConnection(resultSet, con);
				} 
				catch (SQLException se)
				{
					se.printStackTrace();
					throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
				}
			}

			return dataArrayWrapper;
		}
		
		//----------------- Checking userid exist or not---------------
				public boolean fetchUserid(String userid)throws Exception {
			
					Connection con = null;
					ResultSet resultSet = null;
					boolean useridFound=false;
					
					try {
							
							con = getConnection();
							
							PreparedStatement pstmt = con.prepareStatement("SELECT Userid  FROM Users WHERE Userid=?");
						
							pstmt.setString(1,userid);
							
							resultSet = pstmt.executeQuery();
							
							if(resultSet.next()) 
							{
								useridFound=true;
							}
						
							resultSet.close();
							pstmt.close();
			
					} catch (SQLException se) {
					
						se.printStackTrace();
						throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
					
					} catch (NamingException ne) {
						
						ne.printStackTrace();
						throw new NamingException(ne.getMessage());
					}
					 catch (Exception ex) {
					
						 ex.printStackTrace();
						throw new Exception(ex.getMessage());
					}
				
					finally
					{
						try
						{
							releaseConnection(resultSet, con);
						} 
						catch (SQLException se)
						{
							se.printStackTrace();
							throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
						}
					}
			
					return useridFound;
			}
				
	
}
