package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.CKYCContactWrapper;

public class CKYCContactHelper extends Helper {
	
	public AbstractWrapper insertCKYCContact(UsersWrapper usersProfileWrapper,CKYCContactWrapper ckycContactWrapper)throws Exception {

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
		
		PreparedStatement pstmt;
		
	
		try {
				con = getConnection();
				
				sql=" INSERT INTO CKYC_Contact(RefNo,OffTelephone,ResTelephone,MobileNo,FaxNo,EmailID, MakerId,MakerDateTime)"
						+ "  Values(?,?,?,?,?,?,?,?)";
				
				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,Utility.trim(ckycContactWrapper.refNo));
				pstmt.setString(2,Utility.trim(ckycContactWrapper.offTelephone));
				pstmt.setString(3,Utility.trim(ckycContactWrapper.resTelephone));
				pstmt.setString(4,Utility.trim(ckycContactWrapper.mobileNo));
				pstmt.setString(5,Utility.trim(ckycContactWrapper.faxNo));
				pstmt.setString(6,Utility.trim(ckycContactWrapper.emailID));
				pstmt.setString(7,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
				System.out.println("insertCKYCContact Userid "+usersProfileWrapper.userid);
				
				pstmt.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
			
				pstmt.executeUpdate();
				pstmt.close();
	
				ckycContactWrapper.recordFound=true;
				
				dataArrayWrapper.ckycContactWrapper=new CKYCContactWrapper[1];
				dataArrayWrapper.ckycContactWrapper[0]=ckycContactWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				System.out.println("CKYC_Address Inserted");
			
			
			
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
	
	public AbstractWrapper updateCKYCContact(UsersWrapper usersProfileWrapper,CKYCContactWrapper ckycContactWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update CKYCAddress");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM CKYC_Contact WHERE RefNo=?");
				
			
				System.out.println("CKYCAddress RefNo is" + ckycContactWrapper.refNo);
				
				pstmt.setString(1,ckycContactWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCKYCContact(usersProfileWrapper,ckycContactWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE CKYC_Contact SET OffTelephone=?,ResTelephone=?,MobileNo=?,FaxNo=?,EmailID=?, "
							+ " ModifierId=?, ModifierDateTime=? WHERE RefNo=?");
	

					System.out.println("Update CKYC_Contact refNo" + ckycContactWrapper.refNo);
					
					
					pstmt.setString(1,Utility.trim(ckycContactWrapper.offTelephone));
					pstmt.setString(2,Utility.trim(ckycContactWrapper.resTelephone));
					pstmt.setString(3,Utility.trim(ckycContactWrapper.mobileNo));
					pstmt.setString(4,Utility.trim(ckycContactWrapper.faxNo));
					pstmt.setString(5,Utility.trim(ckycContactWrapper.emailID));
					pstmt.setString(6,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
					pstmt.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
					pstmt.setString(8,Utility.trim(ckycContactWrapper.refNo));
	
					

					pstmt.executeUpdate();
					pstmt.close();
				
					
					ckycContactWrapper.recordFound=true;
	
					dataArrayWrapper.ckycContactWrapper=new CKYCContactWrapper[1];
					dataArrayWrapper.ckycContactWrapper[0]=ckycContactWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("CKYC_Contact  updated " );
			}
					
			
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
	
	public AbstractWrapper fetchCKYCContact(CKYCContactWrapper ckycContactWrapper)throws Exception {

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

		try {
			
				//PopoverHelper	popoverHelper = new PopoverHelper();
				
				con = getConnection();
				
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,OffTelephone,ResTelephone,MobileNo,FaxNo,EmailID FROM CKYC_Contact WHERE RefNo=?");
				
			
				System.out.println("fetchCKYCContact RefNo is" + ckycContactWrapper.refNo);
				
				pstmt.setString(1,ckycContactWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					ckycContactWrapper = new CKYCContactWrapper();
					
			
					ckycContactWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + ckycContactWrapper.refNo);

					ckycContactWrapper.offTelephone=Utility.trim(resultSet.getString("OffTelephone"));
					ckycContactWrapper.resTelephone=Utility.trim(resultSet.getString("ResTelephone"));
					ckycContactWrapper.mobileNo=Utility.trim(resultSet.getString("MobileNo"));
					ckycContactWrapper.faxNo=Utility.trim(resultSet.getString("FaxNo"));
					ckycContactWrapper.emailID=Utility.trim(resultSet.getString("EmailID"));
					
					
				
					//ckycContactWrapper.homeCountryValue=popoverHelper.fetchPopoverDesc(ckycContactWrapper.homeCountry, "NATIONALITY");
				
					
					ckycContactWrapper.recordFound=true;
					System.out.println("ckycContactWrapper");
	
					vector.addElement(ckycContactWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.ckycContactWrapper = new CKYCContactWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.ckycContactWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else	
			{
				dataArrayWrapper.ckycContactWrapper = new CKYCContactWrapper[1];
				dataArrayWrapper.ckycContactWrapper[0]= ckycContactWrapper;
				dataArrayWrapper.recordFound=true;
				
			}
 
			if(resultSet !=null) resultSet.close();
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

}
