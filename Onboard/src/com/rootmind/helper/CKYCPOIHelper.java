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
import com.rootmind.wrapper.CKYCPOIWrapper;

public class CKYCPOIHelper extends Helper{
	
	public AbstractWrapper insertCKYCPOI(UsersWrapper usersProfileWrapper,CKYCPOIWrapper ckycPOIWrapper)throws Exception {

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
				
				sql=" INSERT INTO CKYC_POI(RefNo,POI,POINo,POIExpiryDate,POIIdentityNo,DeleteStatus,MakerId,MakerDateTime) Values(?,?,?,?,?,?,?,?)";
				
				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,Utility.trim(ckycPOIWrapper.refNo));
				pstmt.setString(2,Utility.trim(ckycPOIWrapper.poi));
				pstmt.setString(3,Utility.trim(ckycPOIWrapper.poiNo));
				pstmt.setDate(4,Utility.getDate(ckycPOIWrapper.poiExpiryDate));
				pstmt.setString(5,Utility.trim(ckycPOIWrapper.poiIdentityNo));
				pstmt.setString(6,"N");
				pstmt.setString(7,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
				System.out.println("insertCKYCPOI Userid "+usersProfileWrapper.userid);
				pstmt.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
				
				
			
				pstmt.executeUpdate();
				pstmt.close();
	
				ckycPOIWrapper.recordFound=true;
				
				dataArrayWrapper.ckycPOIWrapper=new CKYCPOIWrapper[1];
				dataArrayWrapper.ckycPOIWrapper[0]=ckycPOIWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				System.out.println("CKYCPOI Inserted");
			
			
			
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
	
	public AbstractWrapper updateCKYCPOI(UsersWrapper usersProfileWrapper,CKYCPOIWrapper ckycPOIWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("updateCKYCPOI");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM CKYC_POI WHERE SeqNo=? AND RefNo=?");
				
			
				System.out.println("CKYCPOI RefNo is" + ckycPOIWrapper.refNo);
				
				pstmt.setString(1,Utility.trim(ckycPOIWrapper.seqNo));
				pstmt.setString(2,Utility.trim(ckycPOIWrapper.refNo));
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCKYCPOI(usersProfileWrapper,ckycPOIWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
					
					
						pstmt = con.prepareStatement("UPDATE CKYC_POI SET POI=?,POINo=?,POIExpiryDate=?,POIIdentityNo=?,DeleteStatus=?, ModifierId=?, ModifierDateTime=?"
								+ " WHERE SeqNo=? AND RefNo=?");
		
	
						System.out.println("Update CKYCPOI refNo" + ckycPOIWrapper.refNo);
						
						
						pstmt.setString(1,Utility.trim(ckycPOIWrapper.poi));
						pstmt.setString(2,Utility.trim(ckycPOIWrapper.poiNo));
						pstmt.setDate(3,Utility.getDate(ckycPOIWrapper.poiExpiryDate));
						pstmt.setString(4,Utility.trim(ckycPOIWrapper.poiIdentityNo));
						pstmt.setString(5,Utility.trim(ckycPOIWrapper.deleteFlag));
						pstmt.setString(6,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
						pstmt.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
						pstmt.setString(8,Utility.trim(ckycPOIWrapper.seqNo));
						pstmt.setString(9,Utility.trim(ckycPOIWrapper.refNo));
						
	
						pstmt.executeUpdate();
						pstmt.close();
					
						
						ckycPOIWrapper.recordFound=true;
		
						dataArrayWrapper.ckycPOIWrapper=new CKYCPOIWrapper[1];
						dataArrayWrapper.ckycPOIWrapper[0]=ckycPOIWrapper;
		
						dataArrayWrapper.recordFound=true;
						System.out.println("CKYCPOI  updated " );
					
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
	
	public AbstractWrapper fetchCKYCPOI(CKYCPOIWrapper ckycPOIWrapper)throws Exception {

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
			
				PopoverHelper	popoverHelper = new PopoverHelper();
				
				con = getConnection();
				
				PreparedStatement pstmt = con.prepareStatement("SELECT SeqNo,RefNo,POI,POINo,POIExpiryDate,POIIdentityNo FROM CKYC_POI WHERE RefNo=? AND DeleteStatus=?");
				
			
				System.out.println("fetchCKYCPOI RefNo is" + ckycPOIWrapper.refNo);
				
				pstmt.setString(1,Utility.trim(ckycPOIWrapper.refNo));
				pstmt.setString(2,"N");
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					ckycPOIWrapper = new CKYCPOIWrapper();
					
					ckycPOIWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
					ckycPOIWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + ckycPOIWrapper.refNo);

					ckycPOIWrapper.poi=Utility.trim(resultSet.getString("POI"));
					ckycPOIWrapper.poiNo=Utility.trim(resultSet.getString("POINo"));
					ckycPOIWrapper.poiExpiryDate=Utility.setDate(resultSet.getString("POIExpiryDate"));
					ckycPOIWrapper.poiIdentityNo=Utility.trim(resultSet.getString("POIIdentityNo"));

					ckycPOIWrapper.poiValue=popoverHelper.fetchPopoverDesc(ckycPOIWrapper.poi, "CKYC_IDProof");
				
					
					ckycPOIWrapper.recordFound=true;
					System.out.println("ckycPOIWrapper");
	
					vector.addElement(ckycPOIWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.ckycPOIWrapper = new CKYCPOIWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.ckycPOIWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else	
			{
				dataArrayWrapper.ckycPOIWrapper = new CKYCPOIWrapper[1];
				dataArrayWrapper.ckycPOIWrapper[0]= ckycPOIWrapper;
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
