package com.rootmind.helper;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import java.util.Locale;


import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

import java.util.Random;
public class OtpHelper extends Helper{

	
	public AbstractWrapper insertOTP(OtpWrapper otpWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		PreparedStatement pstmt=null;
		
		int otpExpLmit=0;
	
		try {
				
				con = getConnection();
				
				//-------- Fetch OTP Expiry Time Parameter
				pstmt = con.prepareStatement("SELECT OtpExpLimit from RMT_Parameter");
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{	
					otpExpLmit=resultSet.getInt("OtpExpLimit");
					System.out.println("Parameter " + otpExpLmit);
					
				}
				
				resultSet.close();
				pstmt.close();
				
				//-------
				
				//Insert OTP Details
				sql=" INSERT INTO RMT_OTP(RefNo,CIFNumber,AccountNumber,CreditCardNumber,MobileNumber,OTP,OTPGenDateTime, "
						+ "OTPExpDateTime,Status) Values(?,?,?,?,?,?,?,?,?)";
	
				System.out.println("sql " + sql);
				
				 pstmt = con.prepareStatement(sql);
	
				pstmt.setString(1,Utility.trim(otpWrapper.refNo));
				pstmt.setString(2,Utility.trim(otpWrapper.cifNumber));
				pstmt.setString(3,Utility.trim(otpWrapper.accountNumber));
				pstmt.setString(4,Utility.trim(otpWrapper.creditCardNumber));
				pstmt.setString(5,Utility.trim(otpWrapper.mobileNumber));	
				
				otpWrapper.otp=generateOTP();
				
				System.out.println("Generated OTP "+ otpWrapper.otp);
				
				pstmt.setString(6,otpWrapper.otp);
				
			
				pstmt.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis()));
				
				System.out.println("Generated Date Time "+ new java.sql.Timestamp(System.currentTimeMillis()));
				
				pstmt.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()+otpExpLmit*60*1000));
				
				System.out.println("Expiry Date Time "+ new java.sql.Timestamp(System.currentTimeMillis()+otpExpLmit*60*1000));
				
				pstmt.setString(9,Utility.trim("CREATED"));
				
				pstmt.executeUpdate();
				
				pstmt.close();	
	
				otpWrapper.recordFound=true;
				otpWrapper.generatedOTP=true;
				dataArrayWrapper.otpWrapper=new OtpWrapper[1];
				dataArrayWrapper.otpWrapper[0]=otpWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				System.out.println("OTP Inserted");
		
			
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
	
	public AbstractWrapper validateOTP(OtpWrapper otpWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		PreparedStatement pstmt=null;
		
	
		String sql=null;
		Timestamp expTimestamp=null;
		OtpWrapper otpWrapperSub=null;
		
		try 
		{
			con = getConnection();
			
			
			
			sql="SELECT OTP,OTPGenDateTime,OTPExpDateTime FROM RMT_OTP "; 
			
			if(otpWrapper.refNo !=null && !otpWrapper.refNo.trim().isEmpty())
			{
				sql=sql + " WHERE otpexpdatetime=(select max(a.otpexpdatetime) as maxexpdate from RMT_OTP a where a.refNo=?) and refNo=?";
				
				
			}
			else if(otpWrapper.cifNumber!=null && !otpWrapper.cifNumber.trim().isEmpty())
			{
				sql=sql + " WHERE otpexpdatetime=(select max(a.otpexpdatetime) as maxexpdate from RMT_OTP a where a.cifnumber=?) and cifnumber=?";
				
			}
			else if(otpWrapper.accountNumber!=null && !otpWrapper.accountNumber.trim().isEmpty())
			{
				sql=sql + " WHERE otpexpdatetime=(select max(a.otpexpdatetime) as maxexpdate from RMT_OTP a where a.accountNumber=?) and accountNumber=?";

			}
			else if(otpWrapper.creditCardNumber!=null && !otpWrapper.creditCardNumber.trim().isEmpty())
			{
				sql=sql +  "WHERE otpexpdatetime=(select max(a.otpexpdatetime) as maxexpdate from RMT_OTP a where a.creditCardNumber=?) and creditCardNumber=?";

			}
			
			System.out.println("validateOTP sql  " + sql);
			
			pstmt = con.prepareStatement(sql);
			if(otpWrapper.refNo !=null && !otpWrapper.refNo.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.refNo);
				pstmt.setString(2, otpWrapper.refNo);
			}
			
			else if(otpWrapper.cifNumber!=null && !otpWrapper.cifNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.cifNumber.trim());
				pstmt.setString(2, otpWrapper.cifNumber.trim());
				
				System.out.println("OTP CIF no " + otpWrapper.cifNumber);
				
			}
			else if(otpWrapper.accountNumber!=null && !otpWrapper.accountNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.accountNumber.trim());
				pstmt.setString(2, otpWrapper.accountNumber.trim());
				
			}
			else if(otpWrapper.creditCardNumber!=null && !otpWrapper.creditCardNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.creditCardNumber.trim());
				pstmt.setString(2, otpWrapper.creditCardNumber.trim());
			}
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) 
			{
				otpWrapperSub= new OtpWrapper();	
				otpWrapperSub.otp=Utility.trim(resultSet.getString("OTP"));
				
				System.out.println("OTP frm DB "+ otpWrapperSub.otp);
				
				otpWrapperSub.otpExpDateTime=Utility.trim(resultSet.getString("OTPExpDateTime"));
				
				 expTimestamp=resultSet.getTimestamp("OTPExpDateTime");
				 
				 System.out.println("expTimestamp "+ expTimestamp);
				
			}
			
			resultSet.close();
			pstmt.close();

			
			//Date otpExpDateTime  = dmyFormat.parse(otpWrapper.otpExpDateTime);
	
			
			if(expTimestamp.after(new java.sql.Timestamp(System.currentTimeMillis())))
			{
				System.out.println("System Time stamp "+ new java.sql.Timestamp(System.currentTimeMillis()));
				System.out.println("Time stamp validation success ");
				System.out.println("OTP frm user "+otpWrapper.otp);
				
				if(otpWrapper.otp.equals(otpWrapperSub.otp))
				{
					otpWrapper.verifiedOTP=true;
					
					System.out.println("OTP Matched");
					
					pstmt = con.prepareStatement("UPDATE RMT_OTP SET STATUS=? WHERE OTP=? and OTPExpDateTime=?");
					pstmt.setString(1,"VERIFIED");
					pstmt.setString(2,Utility.trim(otpWrapperSub.otp));
					pstmt.setTimestamp(3,expTimestamp);
					pstmt.executeUpdate();
					pstmt.close();
				
				}
				
			
			}
/*			else{
				
				pstmt.close();
				
				pstmt = con.prepareStatement("UPDATE RMT_OTP SET STATUS=? WHERE OTP=?");
				pstmt.setString(1,"Expired");
				pstmt.setString(2,Utility.trim(otpWrapperSub.otp));
				pstmt.executeUpdate();
				pstmt.close();
			
				
			}*/
			


			otpWrapper.recordFound=true;
			dataArrayWrapper.otpWrapper=new OtpWrapper[1];
			dataArrayWrapper.otpWrapper[0]=otpWrapper;
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

	
	
	
	public String generateOTP()throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		int otpLength=0;
		
		StringBuilder pass=null;
		
		try {
				con = getConnection();
				
				PreparedStatement pstmt = con.prepareStatement("SELECT OTPLength from RMT_Parameter");
			
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					
					otpLength=resultSet.getInt("OTPLength");
					System.out.println("otpLength" + otpLength);
					
				}
				
				resultSet.close();
				pstmt.close();
			
	
				String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	                     + "0123456789";

		        final int PW_LENGTH = otpLength;
		        Random rnd = new SecureRandom();
		        pass = new StringBuilder();
		        for (int i = 0; i < PW_LENGTH; i++)
		        pass.append(chars.charAt(rnd.nextInt(chars.length())));

			
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

		return  pass.toString();
	}
	
	

	

	/*public AbstractWrapper fetchOTP(OtpWrapper otpWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		String sql=null;
		try {
			con = getConnection("ONBOARD");
			
			sql="SELECT RefNo,CIFNumber,AccountNumber,CreditCardNumber,MobileNumber,OTP,OTPGenDateTime, "
					+ "OTPExpDateTime,Status FROM OTP ";
			
			if(otpWrapper.refNo !=null && !otpWrapper.refNo.trim().isEmpty())
			{
				sql=sql + " WHERE RefNo!=?";
				
				System.out.println("Fetch OTP " + sql);
				
			}
			else if(otpWrapper.cifNumber!=null && !otpWrapper.cifNumber.trim().isEmpty())
			{
				sql=sql + " WHERE CIFNumber=?";
				
				System.out.println("Search cif number " + sql);
				
			}
			else if(otpWrapper.accountNumber!=null && !otpWrapper.accountNumber.trim().isEmpty())
			{
				sql=sql + " WHERE AccountNumber=?";
				
				System.out.println("search account number " + sql);
				
			}
			else if(otpWrapper.creditCardNumber!=null && !otpWrapper.creditCardNumber.trim().isEmpty())
			{
				sql=sql + " WHERE CreditCardNumber=?";
				
				System.out.println("search credit card number " + sql);
				
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			if(otpWrapper.refNo !=null && !otpWrapper.refNo.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.refNo);
			}
			
			else if(otpWrapper.cifNumber!=null && !otpWrapper.cifNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.cifNumber.trim());
				
				System.out.println("otp CIF no " + otpWrapper.cifNumber);
				
			}
			else if(otpWrapper.accountNumber!=null && !otpWrapper.accountNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.accountNumber.trim());
				
			}
			else if(otpWrapper.creditCardNumber!=null && !otpWrapper.creditCardNumber.trim().isEmpty())
			{
				pstmt.setString(1, otpWrapper.creditCardNumber.trim());
				
			}
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				otpWrapper= new OtpWrapper();
				otpWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				otpWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
				otpWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNumber"));
				otpWrapper.creditCardNumber=Utility.trim(resultSet.getString("CreditCardNumber"));
				otpWrapper.mobileNumber=Utility.trim(resultSet.getString("MobileNumber"));
				otpWrapper.otp=Utility.setDate(resultSet.getString("OTP"));
				otpWrapper.otpGenDateTime=Utility.trim(resultSet.getString("OTPGenDateTime"));
				otpWrapper.otpExpDateTime=Utility.trim(resultSet.getString("OTPExpDateTime"));
				otpWrapper.status=Utility.trim(resultSet.getString("Status"));
				otpWrapper.recordFound=true;

				vector.addElement(otpWrapper);
				System.out.println("OTP Details found " );
			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.otpWrapper = new OtpWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.otpWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
			{
				//dataArrayWrapper.dedupWrapper = new DedupWrapper[1];
				//dataArrayWrapper.dedupWrapper[0]= dedupWrapper;
				dataArrayWrapper.recordFound=true;
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

		return dataArrayWrapper;
	}*/
	
}
