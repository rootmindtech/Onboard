package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class KYCTranHelper extends Helper {
	
	
	public AbstractWrapper updateKYCTransaction(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		PreparedStatement pstmt=null;
		
		System.out.println("update KYC Transaction ");
	
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
				dataArrayWrapper=(DataArrayWrapper)insertKYCTransaction(kycDetailsWrapper);
			}
			else
			{
			
				resultSet.close();
				pstmt.close();
			
			
				 pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET SourceFundAccntReg=?, ApproxAnnualTurnOverExpt=?, TotalIncomeAED=?, NoOfTransMonthCash=?, NoOfTransMonthCheq=?, "
						+ "TotalAmtTransAEDCash=?, TotalAmtTransAEDCheq=?, PurposeTransCash=?, PurposeTransCheq=?, DepNoOfTransMonthCash=?, DepNoOfTransMonthCheq=?, "
						+ "DepTotalAmtTransAEDCash=?, DepTotalAmtTransAEDCheq=?, DepPurposeTransCash=?, DepPurposeTransCheq=?, MaxDepositsCash=?, MaxDepositsCheq=?, "
						+ "MaxWithdrawalsCash=?, MaxWithdrawalsCheq=?  where RefNo=?");
				
					
		
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.sourceFundAccntReg));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.approxAnnualTurnOverExpt));
								
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.totalIncomeAED));			
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.noOfTransMonthCash));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.noOfTransMonthCheq));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCash));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCheq));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.purposeTransCash));
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.purposeTransCheq));
								
					pstmt.setString(10,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCash));
					pstmt.setString(11,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCheq));
					pstmt.setString(12,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCash));
					pstmt.setString(13,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCheq));
					pstmt.setString(14,Utility.trim(kycDetailsWrapper.depPurposeTransCash));
					pstmt.setString(15,Utility.trim(kycDetailsWrapper.depPurposeTransCheq));
							
								
					pstmt.setString(16,Utility.trim(kycDetailsWrapper.maxDepositsCash));
					pstmt.setString(17,Utility.trim(kycDetailsWrapper.maxDepositsCheq));
					pstmt.setString(18,Utility.trim(kycDetailsWrapper.maxWithdrawalsCash));
					pstmt.setString(19,Utility.trim(kycDetailsWrapper.maxWithdrawalsCheq));
					pstmt.setString(20,Utility.trim(kycDetailsWrapper.refNo));
					
	
					pstmt.executeUpdate();
					
					pstmt.close();
					
	/*				//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCTRAN=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
	*/	
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCTRAN");
					//--
	
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Transaction Details updated " );
					
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
	
	
	public AbstractWrapper insertKYCTransaction(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		PreparedStatement pstmt=null;
		
		System.out.println("insertKYCTransaction ");
	
		try {
			
			con = getConnection();
			
			
	
				 pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(RefNo,SourceFundAccntReg, ApproxAnnualTurnOverExpt, "
				 		+ "TotalIncomeAED, NoOfTransMonthCash, NoOfTransMonthCheq, "
						+ "TotalAmtTransAEDCash, TotalAmtTransAEDCheq, PurposeTransCash, PurposeTransCheq, DepNoOfTransMonthCash, "
						+ " DepNoOfTransMonthCheq, "
						+ "DepTotalAmtTransAEDCash, DepTotalAmtTransAEDCheq, DepPurposeTransCash, DepPurposeTransCheq, MaxDepositsCash, "
						+ " MaxDepositsCheq, "
						+ "MaxWithdrawalsCash, MaxWithdrawalsCheq) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
					
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.sourceFundAccntReg));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.approxAnnualTurnOverExpt));
								
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.totalIncomeAED));			
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.noOfTransMonthCash));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.noOfTransMonthCheq));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCash));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCheq));
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.purposeTransCash));
					pstmt.setString(10,Utility.trim(kycDetailsWrapper.purposeTransCheq));
								
					pstmt.setString(11,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCash));
					pstmt.setString(12,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCheq));
					pstmt.setString(13,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCash));
					pstmt.setString(14,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCheq));
					pstmt.setString(15,Utility.trim(kycDetailsWrapper.depPurposeTransCash));
					pstmt.setString(16,Utility.trim(kycDetailsWrapper.depPurposeTransCheq));
							
								
					pstmt.setString(17,Utility.trim(kycDetailsWrapper.maxDepositsCash));
					pstmt.setString(18,Utility.trim(kycDetailsWrapper.maxDepositsCheq));
					pstmt.setString(19,Utility.trim(kycDetailsWrapper.maxWithdrawalsCash));
					pstmt.setString(20,Utility.trim(kycDetailsWrapper.maxWithdrawalsCheq));
					
					
	
					pstmt.executeUpdate();
					
					pstmt.close();
					
	/*				//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCTRAN=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
	*/	
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCTRAN");
					//--
	
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Transaction Details inserted " );
					
			
				
			
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
	
	

	public AbstractWrapper fetchKYCTransaction(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();

		System.out.println("Fetch KYC Tran RefNo" + kycDetailsWrapper.refNo);
		
		Vector<Object> vector = new Vector<Object>();
		
		try {
				//PopoverHelper popoverHelper = new PopoverHelper();
			
			con = getConnection();
				
				
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, SourceFundAccntReg, ApproxAnnualTurnOverExpt, TotalIncomeAED, NoOfTransMonthCash, NoOfTransMonthCheq, TotalAmtTransAEDCash, TotalAmtTransAEDCheq,"
						+ " PurposeTransCash, PurposeTransCheq, DepNoOfTransMonthCash, DepNoOfTransMonthCheq, DepTotalAmtTransAEDCash,"
						+ " DepTotalAmtTransAEDCheq, DepPurposeTransCash, DepPurposeTransCheq, MaxDepositsCash, MaxDepositsCheq, MaxWithdrawalsCash,"
						+ " MaxWithdrawalsCheq  FROM RMT_KYCDetails WHERE refNo=?");
	
			
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
				

				
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					kycDetailsWrapper=new KycDetailsWrapper();
					
					kycDetailsWrapper.refNo= Utility.trim(resultSet.getString("RefNo"));
					kycDetailsWrapper.sourceFundAccntReg= Utility.trim(resultSet.getString("SourceFundAccntReg"));
					kycDetailsWrapper.approxAnnualTurnOverExpt= Utility.trim(resultSet.getString("ApproxAnnualTurnOverExpt"));
					kycDetailsWrapper.totalIncomeAED= Utility.trim(resultSet.getString("TotalIncomeAED"));
					kycDetailsWrapper.noOfTransMonthCash= Utility.trim(resultSet.getString("NoOfTransMonthCash"));
					kycDetailsWrapper.noOfTransMonthCheq= Utility.trim(resultSet.getString("NoOfTransMonthCheq"));
					kycDetailsWrapper.totalAmtTransAEDCash= Utility.trim(resultSet.getString("TotalAmtTransAEDCash"));
					kycDetailsWrapper.totalAmtTransAEDCheq= Utility.trim(resultSet.getString("TotalAmtTransAEDCheq"));
					kycDetailsWrapper.purposeTransCash= Utility.trim(resultSet.getString("PurposeTransCash"));
					kycDetailsWrapper.purposeTransCheq= Utility.trim(resultSet.getString("PurposeTransCheq"));
					
					kycDetailsWrapper.depNoOfTransMonthCash= Utility.trim(resultSet.getString("DepNoOfTransMonthCash"));
					kycDetailsWrapper.depNoOfTransMonthCheq= Utility.trim(resultSet.getString("DepNoOfTransMonthCheq"));
					kycDetailsWrapper.depTotalAmtTransAEDCash= Utility.trim(resultSet.getString("DepTotalAmtTransAEDCash"));
					kycDetailsWrapper.depTotalAmtTransAEDCheq= Utility.trim(resultSet.getString("DepTotalAmtTransAEDCheq"));
					kycDetailsWrapper.depPurposeTransCash= Utility.trim(resultSet.getString("DepPurposeTransCash"));
					kycDetailsWrapper.depPurposeTransCheq= Utility.trim(resultSet.getString("DepPurposeTransCheq"));
					
					kycDetailsWrapper.maxDepositsCash= Utility.trim(resultSet.getString("MaxDepositsCash"));
					kycDetailsWrapper.maxDepositsCheq= Utility.trim(resultSet.getString("MaxDepositsCheq"));
					kycDetailsWrapper.maxWithdrawalsCash= Utility.trim(resultSet.getString("MaxWithdrawalsCash"));
					kycDetailsWrapper.maxWithdrawalsCheq= Utility.trim(resultSet.getString("MaxWithdrawalsCheq"));
		

					

					kycDetailsWrapper.recordFound=true;
					System.out.println("KYC Transaction fetched");
	
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
	
	
	public AbstractWrapper updateKYCTransaction2(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		PreparedStatement pstmt=null;
		
		System.out.println("update KYC Transaction 2 ");
	
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
				dataArrayWrapper=(DataArrayWrapper)insertKYCTransaction2(kycDetailsWrapper);
			}
			else
			{
			
				resultSet.close();
				pstmt.close();
			
			
				 pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET OutNoOfTransMonthInternational=?, OutNoOfTransMonthLocal=?, OutTotalAmtTransAEDInternational=?,"
						+ " OutTotalAmtTransAEDLocal=?, OutPurposeTransInternational=?, OutPurposeTransLocal=?, InNoOfTransMonthInternational=?, InNoOfTransMonthLocal=?,"
						+ " InTotalAmtTransAEDInternational=?, InTotalAmtTransAEDLocal=?, InPurposeTransInternational=?, InPurposeTransLocal=?, MaxSingleAmtInward=?,"
						+ " MaxSingleAmtOutward=?, InternationalRem3MjrContriesIn=?, InternationalRem3MjrContriesOut=? where RefNo=?");
					
				
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.outNoOfTransMonthInternational));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.outNoOfTransInMntLocal));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDInternational));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDLocal));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.outPurposeTransInternational));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.outPurposeTransLocal));		
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.inNoOfTransMonthInternational));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.inNoOfTransMonthLocal));
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.inTotalAmtTransAEDInternational));
					pstmt.setString(10,Utility.trim(kycDetailsWrapper.inTotlAmtTransAEDLocal));
					pstmt.setString(11,Utility.trim(kycDetailsWrapper.inPurposeTransInternational));
					pstmt.setString(12,Utility.trim(kycDetailsWrapper.inPurposeTransLocal));	
					pstmt.setString(13,Utility.trim(kycDetailsWrapper.maxSingleAmtInward));
					pstmt.setString(14,Utility.trim(kycDetailsWrapper.maxSingleAmtOutward));
					pstmt.setString(15,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesIn));
					pstmt.setString(16,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesOut));	
					pstmt.setString(17,Utility.trim(kycDetailsWrapper.refNo));
				
								
					pstmt.executeUpdate();
					
					pstmt.close();
					
	/*				//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCTRAN2=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
	*/
	
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCTRAN2");
					//--
					
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Transaction 2  updated " );
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
	
	public AbstractWrapper insertKYCTransaction2(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		PreparedStatement pstmt=null;
		
		System.out.println(" insertKYCTransaction2 ");
	
		try {
			
			con = getConnection();
			
			
			
				 pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(RefNo,OutNoOfTransMonthInternational, OutNoOfTransMonthLocal, "
				 		+ "OutTotalAmtTransAEDInternational, "
						+ " OutTotalAmtTransAEDLocal, OutPurposeTransInternational, OutPurposeTransLocal, InNoOfTransMonthInternational, "
						+ "InNoOfTransMonthLocal, "
						+ " InTotalAmtTransAEDInternational, InTotalAmtTransAEDLocal, InPurposeTransInternational, InPurposeTransLocal, "
						+ "MaxSingleAmtInward, "
						+ " MaxSingleAmtOutward, InternationalRem3MjrContriesIn, InternationalRem3MjrContriesOut) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					
				 	pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.outNoOfTransMonthInternational));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.outNoOfTransInMntLocal));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDInternational));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDLocal));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.outPurposeTransInternational));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.outPurposeTransLocal));		
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.inNoOfTransMonthInternational));
					pstmt.setString(9,Utility.trim(kycDetailsWrapper.inNoOfTransMonthLocal));
					pstmt.setString(10,Utility.trim(kycDetailsWrapper.inTotalAmtTransAEDInternational));
					pstmt.setString(11,Utility.trim(kycDetailsWrapper.inTotlAmtTransAEDLocal));
					pstmt.setString(12,Utility.trim(kycDetailsWrapper.inPurposeTransInternational));
					pstmt.setString(13,Utility.trim(kycDetailsWrapper.inPurposeTransLocal));	
					pstmt.setString(14,Utility.trim(kycDetailsWrapper.maxSingleAmtInward));
					pstmt.setString(15,Utility.trim(kycDetailsWrapper.maxSingleAmtOutward));
					pstmt.setString(16,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesIn));
					pstmt.setString(17,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesOut));	
					
				
								
					pstmt.executeUpdate();
					
					pstmt.close();


					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYCTRAN2");
					//--
					
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC Transaction 2  INSERTED " );
			
			
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
	
	
	public AbstractWrapper fetchKYCTransaction2(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
	
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();

		System.out.println("fetch KYC Transaction2 RefNo" + kycDetailsWrapper.refNo);
		
		Vector<Object> vector = new Vector<Object>();
		
		try {
				
				PopoverHelper popoverHelper=new PopoverHelper();
			
				con = getConnection();
				
				
				
					PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, OutNoOfTransMonthInternational, OutNoOfTransMonthLocal, OutTotalAmtTransAEDInternational, OutTotalAmtTransAEDLocal,"
							+ " OutPurposeTransInternational, OutPurposeTransLocal, InNoOfTransMonthInternational, InNoOfTransMonthLocal, InTotalAmtTransAEDInternational,"
							+ " InTotalAmtTransAEDLocal, InPurposeTransInternational, InPurposeTransLocal, MaxSingleAmtInward, MaxSingleAmtOutward,"
							+ " InternationalRem3MjrContriesIn, InternationalRem3MjrContriesOut FROM RMT_KYCDetails WHERE refNo=?");
		
				
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
	
					resultSet = pstmt.executeQuery();
					while (resultSet.next()) 
					{
						kycDetailsWrapper=new KycDetailsWrapper();
						
						kycDetailsWrapper.refNo= Utility.trim(resultSet.getString("RefNo"));
						kycDetailsWrapper.outNoOfTransMonthInternational= Utility.trim(resultSet.getString("OutNoOfTransMonthInternational"));
						kycDetailsWrapper.outNoOfTransInMntLocal= Utility.trim(resultSet.getString("OutNoOfTransMonthLocal"));
						kycDetailsWrapper.outTotalAmtTransAEDInternational= Utility.trim(resultSet.getString("OutTotalAmtTransAEDInternational"));
						kycDetailsWrapper.outTotalAmtTransAEDLocal= Utility.trim(resultSet.getString("OutTotalAmtTransAEDLocal"));
						kycDetailsWrapper.outPurposeTransInternational= Utility.trim(resultSet.getString("OutPurposeTransInternational"));
						kycDetailsWrapper.outPurposeTransLocal= Utility.trim(resultSet.getString("OutPurposeTransLocal"));
					
						kycDetailsWrapper.inNoOfTransMonthInternational= Utility.trim(resultSet.getString("InNoOfTransMonthInternational"));
						kycDetailsWrapper.inNoOfTransMonthLocal =Utility.trim(resultSet.getString("InNoOfTransMonthLocal"));
						kycDetailsWrapper.inTotalAmtTransAEDInternational= Utility.trim(resultSet.getString("InTotalAmtTransAEDInternational"));
						kycDetailsWrapper.inTotlAmtTransAEDLocal= Utility.trim(resultSet.getString("InTotalAmtTransAEDLocal"));
						kycDetailsWrapper.inPurposeTransInternational= Utility.trim(resultSet.getString("InPurposeTransInternational"));
						kycDetailsWrapper.inPurposeTransLocal= Utility.trim(resultSet.getString("InPurposeTransLocal"));
						
						kycDetailsWrapper.maxSingleAmtInward= Utility.trim(resultSet.getString("MaxSingleAmtInward"));
						kycDetailsWrapper.maxSingleAmtOutward= Utility.trim(resultSet.getString("MaxSingleAmtOutward"));
						kycDetailsWrapper.internationalRem3MjrContriesIn= Utility.trim(resultSet.getString("InternationalRem3MjrContriesIn"));
						kycDetailsWrapper.internationalRem3MjrContriesOut= Utility.trim(resultSet.getString("InternationalRem3MjrContriesOut"));
	
						kycDetailsWrapper.internationalRem3MjrContriesInValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.internationalRem3MjrContriesIn, "NATIONALITY");
						kycDetailsWrapper.internationalRem3MjrContriesOutValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.internationalRem3MjrContriesOut, "NATIONALITY");
						kycDetailsWrapper.recordFound=true;
						System.out.println("KYC Transaction 2 fetched");
		
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
	
	

	
	
	/*public AbstractWrapper updateKycTran(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		System.out.println("updateKycDetails");
	
		try {
			
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET SourceFundAccntReg=?, ApproxAnnualTurnOverExpt, NoOfTransMonthCash, NoOfTransMonthCheq,"
					+ " TotalAmtTransAEDCash, TotalAmtTransAEDCheq, PurposeTransCash, PurposeTransCheq, DepNoOfTransMonthCash, DepNoOfTransMonthCheq,"
					+ " DepTotalAmtTransAEDCash, DepTotalAmtTransAEDCheq, DepPurposeTransCash, DepPurposeTransCheq, MaxDepositsCash, MaxDepositsCheq,"
					+ " MaxWithdrawalsCash, MaxWithdrawalsCheq, OutNoOfTransMonthInternational, OutNoOfTransMonthLocal, OutTotalAmtTransAEDInternational,"
					+ " OutTotalAmtTransAEDLocal, OutPurposeTransInternational, OutPurposeTransLocal, InNoOfTransMonthInternational, InNoOfTransMonthLocal,"
					+ " InTotalAmtTransAEDInternational, InTotalAmtTransAEDLocal, InPurposeTransInternational, InPurposeTransLocal, MaxSingleAmtInward,"
					+ " MaxSingleAmtOutward, InternationalRem3MjrContriesIn, InternationalRem3MjrContriesOut, EddDetailsLnsBusiness, EddHowProspectIntroduced,"
					+ " EddDescClientsSourceWealth, EddProdUsgTransProfile, EddNonResident, EddDetailsOpenAcc, EddAreYouAwereOfAnyAspect,"
					+ " IConfirmCheckbox, PreparedBy, PreparedDate, ReviewedBy,ReviewedDate, ApprovedBy, ApprovedDate  where RefNo=?");

			
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.sourceFundAccntReg));
				pstmt.setString(2,Utility.trim(kycDetailsWrapper.approxAnnualTurnOverExpt));
				pstmt.setString(3,Utility.trim(kycDetailsWrapper.noOfTransMonthCash));
				pstmt.setString(4,Utility.trim(kycDetailsWrapper.noOfTransMonthCheq));
				pstmt.setString(5,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCash));
				pstmt.setString(6,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCheq));
				pstmt.setString(7,Utility.trim(kycDetailsWrapper.purposeTransCash));
				pstmt.setString(8,Utility.trim(kycDetailsWrapper.purposeTransCheq));
			
				pstmt.setString(9,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCash));
				pstmt.setString(10,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCheq));
				pstmt.setString(11,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCash));
				pstmt.setString(12,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCheq));
				pstmt.setString(13,Utility.trim(kycDetailsWrapper.depPurposeTransCash));
				pstmt.setString(14,Utility.trim(kycDetailsWrapper.depPurposeTransCheq));
				
				pstmt.setString(15,Utility.trim(kycDetailsWrapper.maxDepositsCash));
				pstmt.setString(16,Utility.trim(kycDetailsWrapper.maxDepositsCheq));
				pstmt.setString(17,Utility.trim(kycDetailsWrapper.maxWithdrawalsCash));
				pstmt.setString(18,Utility.trim(kycDetailsWrapper.maxWithdrawalsCheq));
			
				pstmt.setString(19,Utility.trim(kycDetailsWrapper.outNoOfTransMonthInternational));
				pstmt.setString(20,Utility.trim(kycDetailsWrapper.outNoOfTransInMntLocal));
				pstmt.setString(21,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDInternational));
				pstmt.setString(22,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDLocal));
				pstmt.setString(23,Utility.trim(kycDetailsWrapper.outPurposeTransInternational));
				pstmt.setString(24,Utility.trim(kycDetailsWrapper.outPurposeTransLocal));
				
				pstmt.setString(25,Utility.trim(kycDetailsWrapper.inNoOfTransMonthInternational));
				pstmt.setString(26,Utility.trim(kycDetailsWrapper.inNoOfTransMonthLocal));
				pstmt.setString(27,Utility.trim(kycDetailsWrapper.inTotalAmtTransAEDInternational));
				pstmt.setString(28,Utility.trim(kycDetailsWrapper.inTotlAmtTransAEDLocal));
				pstmt.setString(29,Utility.trim(kycDetailsWrapper.inPurposeTransInternational));
				pstmt.setString(30,Utility.trim(kycDetailsWrapper.inPurposeTransLocal));
			
				pstmt.setString(31,Utility.trim(kycDetailsWrapper.maxSingleAmtInward));
				pstmt.setString(32,Utility.trim(kycDetailsWrapper.maxSingleAmtOutward));
				pstmt.setString(33,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesIn));
				pstmt.setString(34,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesOut));
				
				pstmt.setString(35,Utility.trim(kycDetailsWrapper.eddDetailsLnsBusiness));
				pstmt.setString(36,Utility.trim(kycDetailsWrapper.eddHowProspectIntroduced));
				pstmt.setString(37,Utility.trim(kycDetailsWrapper.eddDescClientsSourceWealth));
				pstmt.setString(38,Utility.trim(kycDetailsWrapper.eddProdUsgTransProfile));
				pstmt.setString(39,Utility.trim(kycDetailsWrapper.eddNonResident));
				pstmt.setString(40,Utility.trim(kycDetailsWrapper.eddDetailsOpenAcc));
				pstmt.setString(41,Utility.trim(kycDetailsWrapper.eddAreYouAwereOfAnyAspect));
				
				pstmt.setString(42,Utility.trim(kycDetailsWrapper.iConfirmCheckbox));
				
				pstmt.setString(43,Utility.trim(kycDetailsWrapper.preparedBy));
				pstmt.setDate(44,Utility.getDate(kycDetailsWrapper.preparedDate));
				pstmt.setString(45,Utility.trim(kycDetailsWrapper.reviewedBy));
				pstmt.setDate(46,Utility.getDate(kycDetailsWrapper.reviewedDate));
				pstmt.setString(47,Utility.trim(kycDetailsWrapper.approvedBy));
				pstmt.setDate(48,Utility.getDate(kycDetailsWrapper.approvedDate));
								
				pstmt.setString(49,Utility.trim(kycDetailsWrapper.refNo));
							
				pstmt.executeUpdate();
				
				pstmt.close();
				
							
							
				kycDetailsWrapper.recordFound=true;
							
				dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
				dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
							
				System.out.println("KYC Details updated " );
			
			
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
	}*/
	
	/*	public AbstractWrapper insertKycDetails(KycDetailsWrapper kycDetailsWrapper)throws Exception {

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
	
	

	try {
		con = getConnection("ONBOARD");
		
		sql=" INSERT INTO RMT_KYCDetails(RefNo, CustomerName, UAEResident, KYCType,CISNoForExistingCustomer, POAGiven, KYCPreparedOn, NextKYCReviewDate, "
				+ "KYCComments, QA1ExposedPerson, QA2NonResident, QA3Individuals, QA4SelectCountries, QA5CustHRBusiness, QA6HoldMailCustomer, "
				+ "QA7ReputationalAspects, QB1AnnualCredits, QB2AggregateBalance, QB3AssetProducts, QB4InvestmentsPortfolio, QB5ResidentCustomer, "
				+ "QB6RiskClassification, CustReasOpenAccount) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		
		System.out.println("sql " + sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
	
		pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
		pstmt.setString(2,Utility.trim(kycDetailsWrapper.customerName));
		pstmt.setString(3,Utility.trim(kycDetailsWrapper.uAEResident));
		pstmt.setString(4,Utility.trim(kycDetailsWrapper.kYCType));
		pstmt.setString(5,Utility.trim(kycDetailsWrapper.cisNoForExistingCustomer));
		pstmt.setString(6,Utility.trim(kycDetailsWrapper.pOAGiven));
		pstmt.setDate(7,Utility.getDate(kycDetailsWrapper.kYCPreparedOn));
		pstmt.setDate(8,Utility.getDate(kycDetailsWrapper.nextKYCReviewDate));
		pstmt.setString(9,Utility.trim(kycDetailsWrapper.kYCComments));
		pstmt.setString(10,Utility.trim(kycDetailsWrapper.qA1ExposedPerson));
		pstmt.setString(11,Utility.trim(kycDetailsWrapper.qA2NonResident));
		pstmt.setString(12,Utility.trim(kycDetailsWrapper.qA3Individuals));
		pstmt.setString(13,Utility.trim(kycDetailsWrapper.qA4SelectCountries));
		pstmt.setString(14,Utility.trim(kycDetailsWrapper.qA5CustHRBusiness));
		pstmt.setString(15,Utility.trim(kycDetailsWrapper.qA6HoldMailCustomer));
		pstmt.setString(16,Utility.trim(kycDetailsWrapper.qA7ReputationalAspects));
		pstmt.setString(17,Utility.trim(kycDetailsWrapper.qB1AnnualCredits));
		pstmt.setString(18,Utility.trim(kycDetailsWrapper.qB2AggregateBalance));
		pstmt.setString(19,Utility.trim(kycDetailsWrapper.qB3AssetProducts));
		pstmt.setString(20,Utility.trim(kycDetailsWrapper.qB4InsvetmentPortfolio));
		pstmt.setString(21,Utility.trim(kycDetailsWrapper.qB5ResidentCustomer));
		pstmt.setString(22,Utility.trim(kycDetailsWrapper.qB6RiskClassification));
		pstmt.setString(23,Utility.trim(kycDetailsWrapper.custReasOpenAccount));
		
		
		
		
	
		pstmt.executeUpdate();
		pstmt.close();
		System.out.println("KYC Details Inserted");
		
		kycDetailsWrapper.recordFound=true;
		
		//maker and checker 
		
		pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

		pstmt.setString(1,Utility.trim(kycDetailsWrapper.makerId));
		pstmt.setDate(2,Utility.getDate(kycDetailsWrapper.makerDate));
		pstmt.setString(3,Utility.trim(kycDetailsWrapper.approverId));
		pstmt.setDate(4,Utility.getDate(kycDetailsWrapper.approverDate));
		
		pstmt.executeUpdate();

		pstmt.close();

		dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
		dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
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
}*/


	
	

}
