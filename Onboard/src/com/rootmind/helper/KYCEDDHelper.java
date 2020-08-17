package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class KYCEDDHelper extends Helper {
	
	public AbstractWrapper updateKYCEDD(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		PreparedStatement pstmt=null;
		
		System.out.println("update KYC Enhanced Due diligence ");
	
		try {
			
			con = getConnection();
			
			
			pstmt = con.prepareStatement("SELECT RefNo FROM RMT_KYCDetails WHERE RefNo=?");
			
		
			System.out.println("KYC Details RefNo is" + kycDetailsWrapper.refNo);
			
			pstmt.setString(1,kycDetailsWrapper.refNo.trim());
			
			resultSet = pstmt.executeQuery();
			if (!resultSet.next()) 
			{
				resultSet.close();
				pstmt.close();
				dataArrayWrapper=(DataArrayWrapper)insertKYCEDD(kycDetailsWrapper);
			}
			else
			{
			
				resultSet.close();
				pstmt.close();
			
			
				 pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET EddDetailsLnsBusiness=?, EddHowProspectIntroduced=?, "
						+ " EddDescClientsSourceWealth=?, EddProdUsgTransProfile=?, EddNonResident=?, EddDetailsOpenAcc=?, EddAreYouAwereOfAnyAspect=?, "
						+ " IConfirmCheckbox=?, PreparedBy=?, PreparedDate=?, ReviewedBy=?,ReviewedDate=?, ApprovedBy=?, ApprovedDate=?  where RefNo=?");
				
								
				
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.eddDetailsLnsBusiness));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.eddHowProspectIntroduced));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.eddDescClientsSourceWealth));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.eddProdUsgTransProfile));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.eddNonResident));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.eddDetailsOpenAcc));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.eddAreYouAwereOfAnyAspect));			
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.iConfirmCheckbox));		
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.preparedBy));
					pstmt.setDate(10,Utility.getDate(kycDetailsWrapper.preparedDate));
					pstmt.setString(11,Utility.trim(kycDetailsWrapper.reviewedBy));
					pstmt.setDate(12,Utility.getDate(kycDetailsWrapper.reviewedDate));
					pstmt.setString(13,Utility.trim(kycDetailsWrapper.approvedBy));
					pstmt.setDate(14,Utility.getDate(kycDetailsWrapper.approvedDate));			
					pstmt.setString(15,Utility.trim(kycDetailsWrapper.refNo));
								
					pstmt.executeUpdate();
					
					pstmt.close();
					
					
	/*				//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCEDD=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
	*/
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCEDD");
					//--
					
					
					//---------update RMT_OnBoard for decline 
					
					pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET Decline=?,DeclineReason=? WHERE RefNo=?");
					
					pstmt.setString(1,kycDetailsWrapper.decline);
					pstmt.setString(2,kycDetailsWrapper.declineReason);
					pstmt.setString(3,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
					
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Enhanced Due Diligence updated " );
				
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
	
	public AbstractWrapper insertKYCEDD(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		PreparedStatement pstmt=null;
		
		System.out.println("insert KYC Enhanced Due diligence ");
	
		try {
			
				con = getConnection();
			
		
			
				 pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(RefNo,EddDetailsLnsBusiness, EddHowProspectIntroduced, "
						+ " EddDescClientsSourceWealth, EddProdUsgTransProfile, EddNonResident, EddDetailsOpenAcc, EddAreYouAwereOfAnyAspect, "
						+ " IConfirmCheckbox, PreparedBy, PreparedDate, ReviewedBy,ReviewedDate, ApprovedBy, ApprovedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
								
				 	pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.eddDetailsLnsBusiness));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.eddHowProspectIntroduced));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.eddDescClientsSourceWealth));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.eddProdUsgTransProfile));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.eddNonResident));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.eddDetailsOpenAcc));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.eddAreYouAwereOfAnyAspect));			
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.iConfirmCheckbox));		
					pstmt.setString(10,Utility.trim(kycDetailsWrapper.preparedBy));
					pstmt.setDate(11,Utility.getDate(kycDetailsWrapper.preparedDate));
					pstmt.setString(12,Utility.trim(kycDetailsWrapper.reviewedBy));
					pstmt.setDate(13,Utility.getDate(kycDetailsWrapper.reviewedDate));
					pstmt.setString(14,Utility.trim(kycDetailsWrapper.approvedBy));
					pstmt.setDate(15,Utility.getDate(kycDetailsWrapper.approvedDate));			
					
								
					pstmt.executeUpdate();
					
					pstmt.close();
					
					
	/*				//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCEDD=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
	*/
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCEDD");
					//--
					
					
					//---------update RMT_OnBoard for decline Status
					
					pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET Decline=?,DeclineReason=? WHERE RefNo=?");
					
					pstmt.setString(1,kycDetailsWrapper.decline);
					pstmt.setString(2,kycDetailsWrapper.declineReason);
					pstmt.setString(3,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Enhanced Due Diligence INSERTED " );
				
			
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
	

	public AbstractWrapper fetchKYCEDD(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();

		System.out.println("fetch KYC EDD RefNo" + kycDetailsWrapper.refNo);
		
		Vector<Object> vector = new Vector<Object>();
		
		try {
				//PopoverHelper popoverHelper = new PopoverHelper();
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT EddDetailsLnsBusiness, EddHowProspectIntroduced, "
						+ " EddDescClientsSourceWealth, EddProdUsgTransProfile, EddNonResident, EddDetailsOpenAcc, EddAreYouAwereOfAnyAspect, "
						+ " IConfirmCheckbox, PreparedBy, PreparedDate, ReviewedBy,ReviewedDate, ApprovedBy, ApprovedDate  FROM RMT_KYCDetails WHERE refNo=?");
	
			
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
	
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					kycDetailsWrapper=new KycDetailsWrapper();
					
					
				
				
					kycDetailsWrapper.eddDetailsLnsBusiness= Utility.trim(resultSet.getString("EddDetailsLnsBusiness"));
					kycDetailsWrapper.eddHowProspectIntroduced= Utility.trim(resultSet.getString("EddHowProspectIntroduced"));
					kycDetailsWrapper.eddDescClientsSourceWealth= Utility.trim(resultSet.getString("EddDescClientsSourceWealth"));
					kycDetailsWrapper.eddProdUsgTransProfile= Utility.trim(resultSet.getString("EddProdUsgTransProfile"));
					kycDetailsWrapper.eddNonResident= Utility.trim(resultSet.getString("EddNonResident"));
					kycDetailsWrapper.eddDetailsOpenAcc= Utility.trim(resultSet.getString("EddDetailsOpenAcc"));
					kycDetailsWrapper.eddAreYouAwereOfAnyAspect= Utility.trim(resultSet.getString("EddAreYouAwereOfAnyAspect"));
					
					
					kycDetailsWrapper.iConfirmCheckbox= Utility.trim(resultSet.getString("IConfirmCheckbox"));
					
					kycDetailsWrapper.preparedBy= Utility.trim(resultSet.getString("PreparedBy"));
					kycDetailsWrapper.preparedDate= Utility.setDate(resultSet.getString("PreparedDate"));
					kycDetailsWrapper.reviewedBy= Utility.trim(resultSet.getString("ReviewedBy"));
					kycDetailsWrapper.reviewedDate=Utility.setDate(resultSet.getString("ReviewedDate"));
					kycDetailsWrapper.approvedBy= Utility.trim(resultSet.getString("ApprovedBy"));
					kycDetailsWrapper.approvedDate= Utility.setDate(resultSet.getString("ApprovedDate"));
					

					kycDetailsWrapper.recordFound=true;
					System.out.println("KYC Enhanced Due diligence fetched");
	
					vector.addElement(kycDetailsWrapper);
	
			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.kycDetailsWrapper = new KycDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.kycDetailsWrapper);
				dataArrayWrapper.recordFound=true;
	
				System.out.println("total trn. in fetch " + vector.size());
	
			}
			else
				
			{
				dataArrayWrapper.kycDetailsWrapper = new KycDetailsWrapper[1];
				dataArrayWrapper.kycDetailsWrapper[0]= kycDetailsWrapper;
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
