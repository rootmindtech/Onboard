package com.rootmind.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.apphosting.api.ApiProxy;
import com.rootmind.wrapper.AbstractWrapper;


public class UserAuditHelper extends Helper {
	
	public  Connection getConnection1() throws SQLException, NamingException, Exception{
		Connection con=null;
		
		String environment="CLOUD";

		
		
		try{
			

			//-------enable for local development----

			if(environment.equals("LOCAL"))
			{
			
					Context initialContext = new InitialContext();
					String applicationId="ONBOARD";	//"RAJUNRAJU";	//"USERAUDIT"; 
					
					//initialContext = (Context)initialContext.lookup("java:comp/env");
					
					DataSource dataSource = (DataSource)initialContext.lookup("java:/" + applicationId + "DSN");
					con = dataSource.getConnection();
					
			}			
			//---------end local development

			
			//-----------for Google Cloud Platform ----
			if(environment.equals("CLOUD"))
			{
			
				  ApiProxy.Environment env = ApiProxy.getCurrentEnvironment();
			      Map<String,Object> attr = env.getAttributes();
			      String hostname = (String) attr.get("com.google.appengine.runtime.default_version_hostname");
	
			      String url = hostname.contains("localhost:")
			          ? System.getProperty("cloudonboardsql-local") : System.getProperty("cloudonboardsql"); //refer appengine-web.xml for configuration details
			      System.out.println("connecting to: " + url);
			      con = DriverManager.getConnection(url);
				
			}
			//-----------end Google Cloud Platform					
					
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " " + se.getMessage());
		}
		catch(NamingException ne)
		{
			ne.printStackTrace();
			throw new SQLException(ne.getMessage());
		}
		
		catch(Exception se)
		{
			se.printStackTrace();
			throw new Exception(se.getMessage());
		}
				return con; 		
	}
	
	
	public void releaseConnection1(ResultSet rs,Connection con)throws SQLException{
		if(rs!=null)
		{
			rs.close();
		}
		if(con!=null)
		{
			con.close();
		}

	}
	
	public AbstractWrapper updateUserAudit(String userid, String sessionid,String activity,String screenName,String message)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		UsersWrapper usersWrapper=	new UsersWrapper();

		
		System.out.println("Onboard user audit userid :"+ userid);

		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO UserAudit(userid,sessionid,Activity,"
					+ "Screenname,Datestamp,Message) values (?,?,?,?,?,?) ");
			
			pstmt.setString(1,(userid==null?null:userid.trim()));
			pstmt.setString(2,(sessionid==null?null:sessionid.trim()));
			pstmt.setString(3,(activity==null?null:activity.trim()));
			pstmt.setString(4,screenName.trim());
			pstmt.setTimestamp(5,Utility.getCurrentTime());
			pstmt.setString(6,(message==null?null:message.trim()));
			pstmt.executeUpdate();

			pstmt.close();

			/*UsersWrapper tmpUsersWrapper=	new UsersWrapper();
			tmpUsersWrapper=(UsersWrapper)getCIFNumber(userid);
			
			pstmt = con.prepareStatement("UPDATE Users set Lastlogindate=?, NoLoginRetry=? where userid=?");
			
			pstmt.setTimestamp(1,Utility.getCurrentTime());
			pstmt.setInt(2,tmpUsersWrapper.noLoginRetry+1);
			pstmt.setString(3,userid.trim());

			pstmt.executeUpdate();

			pstmt.close();*/
			
			usersWrapper.recordFound=true;
			
			System.out.println("Onboard user audit table updated " );
			
			
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

	
	

}
