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
import com.rootmind.wrapper.CKYCRelatedPersonWrapper;

public class CKYCRelatedPersonHelper extends Helper{
	
	
	public AbstractWrapper insertCKYCRelatedPerson(UsersWrapper usersProfileWrapper,CKYCRelatedPersonWrapper ckycRelatedPersonWrapper)throws Exception {

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
				
				sql=" INSERT INTO CKYC_RelatedPerson(RefNo,Action,PersonKYCNo,PersonType,PersonPrefix,PersonFirstName,PersonMiddleName,PersonLastName,"
					+ " PersonPOI,PersonPOINo,PersonPOIExpiryDate,PersonPOIOther,PersonPOIIdentityNo,DeleteStatus,MakerId,MakerDateTime)"
					+ " Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,Utility.trim(ckycRelatedPersonWrapper.refNo));
				pstmt.setString(2,Utility.trim(ckycRelatedPersonWrapper.action));
				pstmt.setString(3,Utility.trim(ckycRelatedPersonWrapper.personKYCNo));
				pstmt.setString(4,Utility.trim(ckycRelatedPersonWrapper.personType));
				pstmt.setString(5,Utility.trim(ckycRelatedPersonWrapper.personPrefix));
				pstmt.setString(6,Utility.trim(ckycRelatedPersonWrapper.personFirstName));
				pstmt.setString(7,Utility.trim(ckycRelatedPersonWrapper.personMiddleName));
				pstmt.setString(8,Utility.trim(ckycRelatedPersonWrapper.personLastName));
				pstmt.setString(9,Utility.trim(ckycRelatedPersonWrapper.personPOI));
				pstmt.setString(10,Utility.trim(ckycRelatedPersonWrapper.personPOINo));
				pstmt.setDate(11,Utility.getDate(ckycRelatedPersonWrapper.personPOIExpiryDate));
				pstmt.setString(12,Utility.trim(ckycRelatedPersonWrapper.personPOIIdentityNo));
				pstmt.setString(13,Utility.trim(ckycRelatedPersonWrapper.personPOIOther));
				pstmt.setString(14,"N");
				pstmt.setString(15,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
				System.out.println("insertCKYCPOI Userid "+usersProfileWrapper.userid);
				pstmt.setTimestamp(16,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
				
				pstmt.executeUpdate();
				pstmt.close();
	
				ckycRelatedPersonWrapper.recordFound=true;
				
				dataArrayWrapper.ckycRelatedPersonWrapper=new CKYCRelatedPersonWrapper[1];
				dataArrayWrapper.ckycRelatedPersonWrapper[0]=ckycRelatedPersonWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				System.out.println("CKYC_RelatedPerson Inserted");
			
			
			
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
	
	public AbstractWrapper updateCKYCRelatedPerson(UsersWrapper usersProfileWrapper,CKYCRelatedPersonWrapper ckycRelatedPersonWrapper)throws Exception {

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
				
				pstmt = con.prepareStatement("SELECT RefNo FROM CKYC_RelatedPerson WHERE SeqNo=? AND RefNo=?");
				
			
				System.out.println("CKYCPOI RefNo is" + ckycRelatedPersonWrapper.refNo);
				
				pstmt.setString(1,ckycRelatedPersonWrapper.seqNo);
				pstmt.setString(2,Utility.trim(ckycRelatedPersonWrapper.refNo));
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCKYCRelatedPerson(usersProfileWrapper,ckycRelatedPersonWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE CKYC_RelatedPerson SET Action=?,PersonKYCNo=?,PersonType=?,PersonPrefix=?,PersonFirstName=?,PersonMiddleName=?,"
							+ "PersonLastName=?,PersonPOI=?,PersonPOINo=?,PersonPOIExpiryDate=?,PersonPOIOther=?,PersonPOIIdentityNo=?, "
							+ "DeleteStatus=?, ModifierId=?, ModifierDateTime=? WHERE SeqNo=? AND RefNo=?");
	

					System.out.println("Update CKYCRelatedPerson refNo" + ckycRelatedPersonWrapper.refNo);
					
					
					pstmt.setString(1,Utility.trim(ckycRelatedPersonWrapper.action));
					pstmt.setString(2,Utility.trim(ckycRelatedPersonWrapper.personKYCNo));
					pstmt.setString(3,Utility.trim(ckycRelatedPersonWrapper.personType));
					pstmt.setString(4,Utility.trim(ckycRelatedPersonWrapper.personPrefix));
					pstmt.setString(5,Utility.trim(ckycRelatedPersonWrapper.personFirstName));
					pstmt.setString(6,Utility.trim(ckycRelatedPersonWrapper.personMiddleName));
					pstmt.setString(7,Utility.trim(ckycRelatedPersonWrapper.personLastName));
					pstmt.setString(8,Utility.trim(ckycRelatedPersonWrapper.personPOI));
					pstmt.setString(9,Utility.trim(ckycRelatedPersonWrapper.personPOINo));
					
					pstmt.setDate(10,Utility.getDate(ckycRelatedPersonWrapper.personPOIExpiryDate));
					pstmt.setString(11,Utility.trim(ckycRelatedPersonWrapper.personPOIOther));
					pstmt.setString(12,Utility.trim(ckycRelatedPersonWrapper.personPOIIdentityNo));
					
					pstmt.setString(13,Utility.trim(ckycRelatedPersonWrapper.deleteFlag));
					pstmt.setString(14,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
					pstmt.setTimestamp(15,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
					pstmt.setString(16,Utility.trim(ckycRelatedPersonWrapper.seqNo));
					pstmt.setString(17,Utility.trim(ckycRelatedPersonWrapper.refNo));
					

					pstmt.executeUpdate();
					pstmt.close();
				
					
					ckycRelatedPersonWrapper.recordFound=true;
	
					dataArrayWrapper.ckycRelatedPersonWrapper=new CKYCRelatedPersonWrapper[1];
					dataArrayWrapper.ckycRelatedPersonWrapper[0]=ckycRelatedPersonWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("CKYCRelatedPerson  updated " );
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
	
	public AbstractWrapper fetchCKYCRelatedPerson(CKYCRelatedPersonWrapper ckycRelatedPersonWrapper)throws Exception {

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
				
				PreparedStatement pstmt = con.prepareStatement("SELECT SeqNo,RefNo,Action,PersonKYCNo,PersonType,PersonPrefix,PersonFirstName,PersonMiddleName,PersonLastName,"
						+ " PersonPOI,PersonPOINo,PersonPOIExpiryDate,PersonPOIOther,PersonPOIIdentityNo FROM CKYC_RelatedPerson WHERE RefNo=? AND DeleteStatus=?");
				
			
				System.out.println("fetchCKYCPOI RefNo is" + ckycRelatedPersonWrapper.refNo);
				
				pstmt.setString(1,ckycRelatedPersonWrapper.refNo.trim());
				pstmt.setString(2,"N");
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					 ckycRelatedPersonWrapper = new CKYCRelatedPersonWrapper();
					
					 ckycRelatedPersonWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
					 ckycRelatedPersonWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					 System.out.println("RefNo" + ckycRelatedPersonWrapper.refNo);

					 
					  ckycRelatedPersonWrapper.action =Utility.trim(resultSet.getString("Action"));
					  ckycRelatedPersonWrapper.personKYCNo =Utility.trim(resultSet.getString("PersonKYCNo"));
					  ckycRelatedPersonWrapper.personType =Utility.trim(resultSet.getString("PersonType"));
					  ckycRelatedPersonWrapper.personPrefix=Utility.trim(resultSet.getString("PersonPrefix"));
					  ckycRelatedPersonWrapper.personFirstName =Utility.trim(resultSet.getString("PersonFirstName"));
					  ckycRelatedPersonWrapper.personMiddleName =Utility.trim(resultSet.getString("PersonMiddleName"));
					  ckycRelatedPersonWrapper.personLastName =Utility.trim(resultSet.getString("PersonLastName"));
					  ckycRelatedPersonWrapper.personPOI =Utility.trim(resultSet.getString("PersonPOI"));
					  ckycRelatedPersonWrapper.personPOINo=Utility.trim(resultSet.getString("PersonPOINo"));
					  ckycRelatedPersonWrapper.personPOIExpiryDate=Utility.setDate(resultSet.getString("PersonPOIExpiryDate"));
					  ckycRelatedPersonWrapper.personPOIOther =Utility.trim(resultSet.getString("PersonPOIOther"));
					  ckycRelatedPersonWrapper.personPOIIdentityNo =Utility.trim(resultSet.getString("PersonPOIIdentityNo"));
					  
					  
				

					  ckycRelatedPersonWrapper.personTypeValue=popoverHelper.fetchPopoverDesc(ckycRelatedPersonWrapper.personType, "CKYC_RelatedPersonType");
					  ckycRelatedPersonWrapper.personPrefixValue=popoverHelper.fetchPopoverDesc(ckycRelatedPersonWrapper.personPrefix, "Title");
					  ckycRelatedPersonWrapper.personPOIValue=popoverHelper.fetchPopoverDesc(ckycRelatedPersonWrapper.personPOI, "CKYC_IDProof");
					  ckycRelatedPersonWrapper.recordFound=true;
					  
					  System.out.println("ckycRelatedPersonWrapper");
	
					vector.addElement(ckycRelatedPersonWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.ckycRelatedPersonWrapper = new CKYCRelatedPersonWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.ckycRelatedPersonWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else	
			{
				dataArrayWrapper.ckycRelatedPersonWrapper = new CKYCRelatedPersonWrapper[1];
				dataArrayWrapper.ckycRelatedPersonWrapper[0]= ckycRelatedPersonWrapper;
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
