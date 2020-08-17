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
import com.rootmind.wrapper.CKYCAddressWrapper;

public class CKYCAddressHelper extends Helper{
	
	public AbstractWrapper insertCKYCAddress(UsersWrapper usersProfileWrapper,CKYCAddressWrapper ckycAddressWrapper)throws Exception {

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
			
			sql=" INSERT INTO CKYC_Address(RefNo,AddressMode,AddressType, AddressProof,AddressProofNo,CurrentAddressLine1,CurrentAddressLine2,CurrentAddressLine3,"
					+ " CurrentCity,CurrentDistrict,CurrentPIN,CurrentStateUTCode,SameAddressFlag,CurrentISO3166CountryCode,CorrAddressLine1,CorrAddressLine2,"
					+ " CorrAddressLine3,CorrCity,CorrDistrict,CorrPIN,CorrStateUTCode,CorrISO3166CountryCode,DeleteStatus,MakerId,MakerDateTime) "
					+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?)";
			

			
			System.out.println("sql " + sql);
			
			 pstmt = con.prepareStatement(sql);
			
			
			
			
			pstmt.setString(1,Utility.trim(ckycAddressWrapper.refNo));
			pstmt.setString(2,Utility.trim(ckycAddressWrapper.addressMode));
			pstmt.setString(3,Utility.trim(ckycAddressWrapper.addressType));
			pstmt.setString(4,Utility.trim(ckycAddressWrapper.addressProof));
			pstmt.setString(5,Utility.trim(ckycAddressWrapper.addressProofNo));
			pstmt.setString(6,Utility.trim(ckycAddressWrapper.currentAddressLine1));
			pstmt.setString(7,Utility.trim(ckycAddressWrapper.currentAddressLine2));
			pstmt.setString(8,Utility.trim(ckycAddressWrapper.currentAddressLine3));
			pstmt.setString(9,Utility.trim(ckycAddressWrapper.currentCity));
			pstmt.setString(10,Utility.trim(ckycAddressWrapper.currentDistrict));
			pstmt.setString(11,Utility.trim(ckycAddressWrapper.currentPIN));
			pstmt.setString(12,Utility.trim(ckycAddressWrapper.currentStateUTCode));
			pstmt.setString(13,Utility.trim(ckycAddressWrapper.sameAddressFlag));
			pstmt.setString(14,Utility.trim(ckycAddressWrapper.currentISO3166CountryCode));
			pstmt.setString(15,Utility.trim(ckycAddressWrapper.corrAddressLine1));
			pstmt.setString(16,Utility.trim(ckycAddressWrapper.corrAddressLine2));
			pstmt.setString(17,Utility.trim(ckycAddressWrapper.corrAddressLine3));
			pstmt.setString(18,Utility.trim(ckycAddressWrapper.corrCity));
			pstmt.setString(19,Utility.trim(ckycAddressWrapper.corrDistrict));
			pstmt.setString(20,Utility.trim(ckycAddressWrapper.corrPIN));
			pstmt.setString(21,Utility.trim(ckycAddressWrapper.corrStateUTCode));
			pstmt.setString(22,Utility.trim(ckycAddressWrapper.corrISO3166CountryCode));
			
			pstmt.setString(23,"N");
			pstmt.setString(24,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
			System.out.println("insertCKYCAddress Userid "+usersProfileWrapper.userid);
			pstmt.setTimestamp(25,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
			
			
			pstmt.executeUpdate();
			pstmt.close();

			
		
			
			ckycAddressWrapper.recordFound=true;
			
			dataArrayWrapper.ckycAddressWrapper=new CKYCAddressWrapper[1];
			dataArrayWrapper.ckycAddressWrapper[0]=ckycAddressWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Address Details Inserted");
			
			
			
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
	
	public AbstractWrapper updateCKYCAddress(UsersWrapper usersProfileWrapper,CKYCAddressWrapper ckycAddressWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Address Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM CKYC_Address WHERE SeqNo=? AND RefNo=?");
				
			
				System.out.println("CKYCAddress RefNo is" + ckycAddressWrapper.refNo);
				
				pstmt.setString(1,ckycAddressWrapper.seqNo);
				pstmt.setString(2,ckycAddressWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCKYCAddress(usersProfileWrapper,ckycAddressWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE CKYC_Address SET AddressMode=?,AddressType=?, AddressProof=?,AddressProofNo=?,CurrentAddressLine1=?,CurrentAddressLine2=?,"
						+ " CurrentAddressLine3=?,CurrentCity=?,CurrentDistrict=?,CurrentPIN=?,CurrentStateUTCode=?,SameAddressFlag=?,CurrentISO3166CountryCode=?,"
						+ " CorrAddressLine1=?,CorrAddressLine2=?,CorrAddressLine3=?,CorrCity=?,CorrDistrict=?,CorrPIN=?,CorrStateUTCode=?,CorrISO3166CountryCode=?,"
						+ " DeleteStatus=?, ModifierId=?, ModifierDateTime=? WHERE SeqNo=? AND RefNo=?");
	

					System.out.println("Update ckycAddress refNo" + ckycAddressWrapper.refNo);
					pstmt.setString(1,Utility.trim(ckycAddressWrapper.addressMode));
					pstmt.setString(2,Utility.trim(ckycAddressWrapper.addressType));
					pstmt.setString(3,Utility.trim(ckycAddressWrapper.addressProof));
					pstmt.setString(4,Utility.trim(ckycAddressWrapper.addressProofNo));
					pstmt.setString(5,Utility.trim(ckycAddressWrapper.currentAddressLine1));
					pstmt.setString(6,Utility.trim(ckycAddressWrapper.currentAddressLine2));
					pstmt.setString(7,Utility.trim(ckycAddressWrapper.currentAddressLine3));
					pstmt.setString(8,Utility.trim(ckycAddressWrapper.currentCity));
					pstmt.setString(9,Utility.trim(ckycAddressWrapper.currentDistrict));
					pstmt.setString(10,Utility.trim(ckycAddressWrapper.currentPIN));
					pstmt.setString(11,Utility.trim(ckycAddressWrapper.currentStateUTCode));
					pstmt.setString(12,Utility.trim(ckycAddressWrapper.sameAddressFlag));
					pstmt.setString(13,Utility.trim(ckycAddressWrapper.currentISO3166CountryCode));
					pstmt.setString(14,Utility.trim(ckycAddressWrapper.corrAddressLine1));
					pstmt.setString(15,Utility.trim(ckycAddressWrapper.corrAddressLine2));
					pstmt.setString(16,Utility.trim(ckycAddressWrapper.corrAddressLine3));
					pstmt.setString(17,Utility.trim(ckycAddressWrapper.corrCity));
					pstmt.setString(18,Utility.trim(ckycAddressWrapper.corrDistrict));
					pstmt.setString(19,Utility.trim(ckycAddressWrapper.corrPIN));
					pstmt.setString(20,Utility.trim(ckycAddressWrapper.corrStateUTCode));
					pstmt.setString(21,Utility.trim(ckycAddressWrapper.corrISO3166CountryCode));
					
					
					pstmt.setString(22,Utility.trim(ckycAddressWrapper.deleteFlag));
					pstmt.setString(23,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
					pstmt.setTimestamp(24,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
					pstmt.setString(25,Utility.trim(ckycAddressWrapper.seqNo));
					pstmt.setString(26,Utility.trim(ckycAddressWrapper.refNo));
					
					
	
					System.out.println("Update RefNo " + ckycAddressWrapper.refNo);

					pstmt.executeUpdate();
					pstmt.close();
				
					
					ckycAddressWrapper.recordFound=true;
	
					dataArrayWrapper.ckycAddressWrapper=new CKYCAddressWrapper[1];
					dataArrayWrapper.ckycAddressWrapper[0]=ckycAddressWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("ckycAddress  updated " );
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
	
	public AbstractWrapper fetchCKYCAddress(CKYCAddressWrapper ckycAddressWrapper)throws Exception {

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
			
				PopoverHelper	popoverHelper = new PopoverHelper();
				
				con = getConnection();
				
				sql="SELECT SeqNo,RefNo, AddressMode,AddressType, AddressProof,AddressProofNo,CurrentAddressLine1,CurrentAddressLine2,CurrentAddressLine3,"
						+ " CurrentCity,CurrentDistrict,CurrentPIN,CurrentStateUTCode,SameAddressFlag,CurrentISO3166CountryCode,CorrAddressLine1,CorrAddressLine2,"
						+ " CorrAddressLine3,CorrCity,CorrDistrict,CorrPIN,CorrStateUTCode,CorrISO3166CountryCode FROM CKYC_Address WHERE RefNo=? AND DeleteStatus=?";
				
				
				if(ckycAddressWrapper.seqNo != null && !ckycAddressWrapper.seqNo.equals(""))
				{
					
					sql=sql+ " AND SeqNo=?";
				}
				
				System.out.println("final sql " + sql);
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
			
				System.out.println("fetchCKYCAddress RefNo is " + ckycAddressWrapper.refNo);
				System.out.println("fetchCKYCAddress SefNo is " + ckycAddressWrapper.seqNo);
				
				
				
				pstmt.setString(1,ckycAddressWrapper.refNo.trim());
				pstmt.setString(2,"N");
				if(ckycAddressWrapper.seqNo != null && !ckycAddressWrapper.seqNo.equals(""))
				{
					
					pstmt.setString(3,ckycAddressWrapper.seqNo);
				}
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					ckycAddressWrapper = new CKYCAddressWrapper();
					
					ckycAddressWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
					ckycAddressWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + ckycAddressWrapper.refNo);
					
					
					ckycAddressWrapper.addressMode=Utility.trim(resultSet.getString("AddressMode"));
					ckycAddressWrapper.addressType=Utility.trim(resultSet.getString("AddressType"));
					ckycAddressWrapper.addressProof=Utility.trim(resultSet.getString("AddressProof"));
					ckycAddressWrapper.addressProofNo=Utility.trim(resultSet.getString("AddressProofNo"));
					ckycAddressWrapper.currentAddressLine1=Utility.trim(resultSet.getString("CurrentAddressLine1"));
					ckycAddressWrapper.currentAddressLine2=Utility.trim(resultSet.getString("CurrentAddressLine2"));
					ckycAddressWrapper.currentAddressLine3=Utility.trim(resultSet.getString("CurrentAddressLine3"));
					ckycAddressWrapper.currentCity=Utility.trim(resultSet.getString("CurrentCity"));
					ckycAddressWrapper.currentDistrict=Utility.trim(resultSet.getString("CurrentDistrict"));
					ckycAddressWrapper.currentPIN=Utility.trim(resultSet.getString("CurrentPIN"));
					ckycAddressWrapper.currentStateUTCode=Utility.trim(resultSet.getString("CurrentStateUTCode"));
					ckycAddressWrapper.sameAddressFlag=Utility.trim(resultSet.getString("SameAddressFlag"));
					ckycAddressWrapper.currentISO3166CountryCode=Utility.trim(resultSet.getString("CurrentISO3166CountryCode"));
					ckycAddressWrapper.corrAddressLine1=Utility.trim(resultSet.getString("CorrAddressLine1"));
					ckycAddressWrapper.corrAddressLine2=Utility.trim(resultSet.getString("CorrAddressLine2"));
					ckycAddressWrapper.corrAddressLine3=Utility.trim(resultSet.getString("CorrAddressLine3"));
					ckycAddressWrapper.corrCity=Utility.trim(resultSet.getString("CorrCity"));
					ckycAddressWrapper.corrDistrict=Utility.trim(resultSet.getString("CorrDistrict"));
					ckycAddressWrapper.corrPIN=Utility.trim(resultSet.getString("CorrPIN"));
					ckycAddressWrapper.corrStateUTCode=Utility.trim(resultSet.getString("CorrStateUTCode"));
					ckycAddressWrapper.corrISO3166CountryCode=Utility.trim(resultSet.getString("CorrISO3166CountryCode"));
					
					
					
					ckycAddressWrapper.addressTypeValue=popoverHelper.fetchPopoverDesc(ckycAddressWrapper.addressType, "CKYC_AddressProof");
					ckycAddressWrapper.addressProofValue=popoverHelper.fetchPopoverDesc(ckycAddressWrapper.addressProof, "CKYC_AddressProof");
					ckycAddressWrapper.currentStateUTCodeValue=popoverHelper.fetchPopoverDesc(ckycAddressWrapper.currentStateUTCode, "CKYC_State");
					ckycAddressWrapper.currentDistrictValue=popoverHelper.fetchPopoverDesc(ckycAddressWrapper.currentDistrict, "CKYC_District");
					
					ckycAddressWrapper.recordFound=true;
					System.out.println("ckycAddressWrapper");
	
					vector.addElement(ckycAddressWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.ckycAddressWrapper = new CKYCAddressWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.ckycAddressWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else	
			{
				dataArrayWrapper.ckycAddressWrapper = new CKYCAddressWrapper[1];
				dataArrayWrapper.ckycAddressWrapper[0]= ckycAddressWrapper;
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
