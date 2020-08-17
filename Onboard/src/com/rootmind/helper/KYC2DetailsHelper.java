package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class KYC2DetailsHelper extends Helper {
	
	
	public AbstractWrapper updateKYC2Details(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		PreparedStatement pstmt=null;
		
		System.out.println("updateKycDetails");
	
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
			
				
				pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(RefNo, QB1AnnualCredits, QB2AggregateBalance, "
						+ " QB3AssetProducts, QB4InvestmentsPortfolio, QB5ResidentCustomer, "
						+ " QB6RiskClassification, CustReasOpenAccount) values(?,?,?,?,?,?,?,?)");
				
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.qB1AnnualCredits));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.qB2AggregateBalance));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.qB3AssetProducts));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.qB4InvestmentsPortfolio));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.qB5ResidentCustomer));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.qB6RiskClassification));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.custReasOpenAccount));
					
					pstmt.executeUpdate();
					
					pstmt.close();
	
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYC2");
					//--
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC 2 Details inserted " );
				
				
			}
			else
			{
			
				resultSet.close();
				pstmt.close();
			
				pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET  QB1AnnualCredits=?, QB2AggregateBalance=?, "
						+ " QB3AssetProducts=?, QB4InvestmentsPortfolio=?, QB5ResidentCustomer=?, "
						+ " QB6RiskClassification=?, CustReasOpenAccount=? where RefNo=?");
				
	
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.qB1AnnualCredits));
					pstmt.setString(2,Utility.trim(kycDetailsWrapper.qB2AggregateBalance));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.qB3AssetProducts));
					pstmt.setString(4,Utility.trim(kycDetailsWrapper.qB4InvestmentsPortfolio));
					pstmt.setString(5,Utility.trim(kycDetailsWrapper.qB5ResidentCustomer));
					pstmt.setString(6,Utility.trim(kycDetailsWrapper.qB6RiskClassification));
					pstmt.setString(7,Utility.trim(kycDetailsWrapper.custReasOpenAccount));
					pstmt.setString(8,Utility.trim(kycDetailsWrapper.refNo));

								
					pstmt.executeUpdate();
					
					pstmt.close();
	
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYC2");
					//--
					
					kycDetailsWrapper.recordFound=true;
								
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
								
					System.out.println("KYC 2 Details updated " );
					
					
					
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
	
	

	public AbstractWrapper fetchKYC2Details(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();

		System.out.println("fetchKYC2Details RefNo" + kycDetailsWrapper.refNo);
		
		Vector<Object> vector = new Vector<Object>();
		
		try {
				PopoverHelper popoverHelper = new PopoverHelper();
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT  RefNo, QB1AnnualCredits, QB2AggregateBalance, QB3AssetProducts, "
						+ "QB4InvestmentsPortfolio, QB5ResidentCustomer, QB6RiskClassification, CustReasOpenAccount FROM RMT_KYCDetails WHERE refNo=?");
	
			
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
				
			
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					 kycDetailsWrapper=new KycDetailsWrapper();
					
					kycDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					kycDetailsWrapper.qB1AnnualCredits=Utility.trim(resultSet.getString("QB1AnnualCredits"));
					kycDetailsWrapper.qB2AggregateBalance= Utility.trim(resultSet.getString("QB2AggregateBalance"));
					kycDetailsWrapper.qB3AssetProducts= Utility.trim(resultSet.getString("QB3AssetProducts"));
					kycDetailsWrapper.qB4InvestmentsPortfolio=Utility.trim(resultSet.getString("QB4InvestmentsPortfolio"));
					kycDetailsWrapper.qB5ResidentCustomer= Utility.trim(resultSet.getString("QB5ResidentCustomer"));
					kycDetailsWrapper.qB6RiskClassification= Utility.trim(resultSet.getString("QB6RiskClassification"));
					kycDetailsWrapper.custReasOpenAccount= Utility.trim(resultSet.getString("CustReasOpenAccount"));

					
					kycDetailsWrapper.qB1AnnualCreditsValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB1AnnualCredits, "Decision");
					kycDetailsWrapper.qB2AggregateBalanceValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB2AggregateBalance, "Decision");
					kycDetailsWrapper.qB3AssetProductsValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB3AssetProducts, "Decision");
					kycDetailsWrapper.qB4InvestmentsPortfolioValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB4InvestmentsPortfolio, "Decision");
					kycDetailsWrapper.qB5ResidentCustomerValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB5ResidentCustomer, "Decision");
					kycDetailsWrapper.qB6RiskClassificationValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qB6RiskClassification, "Decision");
					kycDetailsWrapper.custReasOpenAccountValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.custReasOpenAccount, "ReasOpenAccount");
					
					
				
					
					
					kycDetailsWrapper.recordFound=true;
					System.out.println("KYC 2 Details fetched");
	
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
		PreparedStatement pstmt=null;
		System.out.println("updateKycTran");
	
		try {
			
			con = getConnection("ONBOARD");
			
			
			
			pstmt = con.prepareStatement("SELECT RefNo FROM RMT_KYCDetails WHERE RefNo=?");
			
		
			System.out.println("KYC Details RefNo is" + kycDetailsWrapper.refNo);
			
			pstmt.setString(1,kycDetailsWrapper.refNo.trim());
			
			resultSet = pstmt.executeQuery();
			if (!resultSet.next()) 
			{
				resultSet.close();
				pstmt.close();
				dataArrayWrapper=(DataArrayWrapper)insertKycTran(kycDetailsWrapper);
			}
			else
			{
			
				resultSet.close();
				pstmt.close();
			
			
					pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET SourceFundAccntReg=?, ApproxAnnualTurnOverExpt, NoOfTransMonthCash, NoOfTransMonthCheq,"
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
						
						//---------update PathStatus
						
						pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYCTran=? WHERE RefNo=?");
						
						pstmt.setString(1,"Y");
						pstmt.setString(2,kycDetailsWrapper.refNo);
						pstmt.executeUpdate();
						
						pstmt.close();
						//-----------------------
									
									
						kycDetailsWrapper.recordFound=true;
									
						dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
						dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
						dataArrayWrapper.recordFound=true;
						
									
						System.out.println("KYC Details updated " );
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
	*/
	



	

}
