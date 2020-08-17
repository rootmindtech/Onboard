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

public class KycDetailsHelper extends Helper {
	
	
	public AbstractWrapper insertKycDetails(KycDetailsWrapper kycDetailsWrapper)throws Exception {

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
		
	
		try {
			
				con = getConnection();
				
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_KYCDetails WHERE RefNo=?");
				
			
				System.out.println("KYC Details RefNo is" + kycDetailsWrapper.refNo);
				
				pstmt.setString(1,kycDetailsWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updateKycDetails(kycDetailsWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
				
					
					sql=" INSERT INTO RMT_KYCDetails(RefNo, CustomerName, UAEResident, KYCType, CISNumber, POAGiven, KYCPreparedOn, NextKYCReviewDate, "
							+ "KYCComments, QA1ExposedPerson, QA2NonResident, QA3Individuals, QA4SelectCountries, QA5CustHRBusiness, QA6HoldMailCustomer, "
							+ "QA7ReputationalAspects) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
		
					
					System.out.println("sql " + sql);
					
					 pstmt = con.prepareStatement(sql);
				
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
					
				/*	pstmt.setString(17,Utility.trim(kycDetailsWrapper.qB1AnnualCredits));
					pstmt.setString(18,Utility.trim(kycDetailsWrapper.qB2AggregateBalance));
					pstmt.setString(19,Utility.trim(kycDetailsWrapper.qB3AssetProducts));
					pstmt.setString(20,Utility.trim(kycDetailsWrapper.qB4InsvetmentPortfolio));
					pstmt.setString(21,Utility.trim(kycDetailsWrapper.qB5ResidentCustomer));
					pstmt.setString(22,Utility.trim(kycDetailsWrapper.qB6RiskClassification));
					pstmt.setString(23,Utility.trim(kycDetailsWrapper.custReasOpenAccount));*/
					
					pstmt.executeUpdate();
					pstmt.close();
					
		/*			//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYC=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,kycDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
		*/			
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYC");
					//--
					
					System.out.println("KYC Details Inserted");
					
					kycDetailsWrapper.recordFound=true;
					
					//maker and checker 
					
			/*		pstmt = con.prepareStatement("INSERT INTO RMT_KYCDetails(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");
		
					pstmt.setString(1,Utility.trim(kycDetailsWrapper.makerId));
					pstmt.setDate(2,Utility.getDate(kycDetailsWrapper.makerDate));
					pstmt.setString(3,Utility.trim(kycDetailsWrapper.approverId));
					pstmt.setDate(4,Utility.getDate(kycDetailsWrapper.approverDate));
					
					pstmt.executeUpdate();
		
					pstmt.close();*/
		
					dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
				}
				
			
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
	
	
	public AbstractWrapper fetchKycDetails(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		Vector<Object> vector = new Vector<Object>();
		
	
		try {
				PopoverHelper	popoverHelper = new PopoverHelper();
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, CustomerName, UAEResident, KYCType,CISNumber, POAGiven, "
						+ " KYCPreparedOn, NextKYCReviewDate, KYCComments, QA1ExposedPerson, QA2NonResident, QA3Individuals, QA4SelectCountries, "
						+ " QA5CustHRBusiness, QA6HoldMailCustomer, QA7ReputationalAspects  FROM RMT_KYCDetails WHERE RefNo=?");
	
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.refNo));
				
				System.out.println("KYC RefNo" + kycDetailsWrapper.refNo);
				
		
			
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					 kycDetailsWrapper=new KycDetailsWrapper();
					
					kycDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + kycDetailsWrapper.refNo);
					
					kycDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
					System.out.println("CustomerName" + kycDetailsWrapper.customerName);
	
					kycDetailsWrapper.uAEResident=Utility.trim(resultSet.getString("UAEResident"));
					System.out.println("UAEResident" + kycDetailsWrapper.uAEResident);
	
					kycDetailsWrapper.kYCType=Utility.trim(resultSet.getString("KYCType"));
					System.out.println("KYCType" + kycDetailsWrapper.kYCType);
					
					kycDetailsWrapper.cisNoForExistingCustomer=Utility.trim(resultSet.getString("CISNumber"));
					kycDetailsWrapper.pOAGiven=Utility.trim(resultSet.getString("POAGiven"));
					kycDetailsWrapper.kYCPreparedOn= Utility.setDate(resultSet.getString("KYCPreparedOn"));
					kycDetailsWrapper.nextKYCReviewDate= Utility.setDate(resultSet.getString("NextKYCReviewDate"));
					kycDetailsWrapper.kYCComments=Utility.trim(resultSet.getString("KYCComments"));
					
					kycDetailsWrapper.qA1ExposedPerson=Utility.trim(resultSet.getString("QA1ExposedPerson"));
					kycDetailsWrapper.qA2NonResident=Utility.trim(resultSet.getString("QA2NonResident"));
					kycDetailsWrapper.qA3Individuals=Utility.trim(resultSet.getString("QA3Individuals"));
					kycDetailsWrapper.qA4SelectCountries=Utility.trim(resultSet.getString("QA4SelectCountries"));
					kycDetailsWrapper.qA5CustHRBusiness=Utility.trim(resultSet.getString("QA5CustHRBusiness"));
					kycDetailsWrapper.qA6HoldMailCustomer=Utility.trim(resultSet.getString("QA6HoldMailCustomer"));
					kycDetailsWrapper.qA7ReputationalAspects=Utility.trim(resultSet.getString("QA7ReputationalAspects"));
					
		
					kycDetailsWrapper.uAEResidentValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.uAEResident, "Residence");
					kycDetailsWrapper.kYCTypeValue =popoverHelper.fetchPopoverDesc(kycDetailsWrapper.kYCType, "KYCType");
					kycDetailsWrapper.pOAGivenValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.pOAGiven, "Decision");
					kycDetailsWrapper.qA1ExposedPersonValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA1ExposedPerson, "Decision");
					kycDetailsWrapper.qA2NonResidentValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA2NonResident, "Decision");
					kycDetailsWrapper.qA3IndividualsValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA3Individuals, "Decision");
					kycDetailsWrapper.qA4SelectCountriesValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA4SelectCountries, "NATIONALITY");
					kycDetailsWrapper.qA5CustHRBusinessValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA5CustHRBusiness, "Decision");
					kycDetailsWrapper.qA6HoldMailCustomerValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA6HoldMailCustomer, "Decision");
					kycDetailsWrapper.qA7ReputationalAspectsValue=popoverHelper.fetchPopoverDesc(kycDetailsWrapper.qA7ReputationalAspects, "Decision");
					

					
					
					kycDetailsWrapper.recordFound=true;
					System.out.println("kycDetails info data fetch completed " );
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
	
	
	
	
	public AbstractWrapper updateKycDetails(KycDetailsWrapper kycDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//KycDetailsWrapper kycDetailsWrapper=new KycDetailsWrapper();
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		System.out.println("update Kyc Details");
	
		
		PreparedStatement pstmt=null;
	
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
					dataArrayWrapper=(DataArrayWrapper)insertKycDetails(kycDetailsWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
			
			
			
					pstmt = con.prepareStatement("UPDATE RMT_KYCDetails SET CustomerName=?, UAEResident=?, KYCType=?, CISNumber=?, POAGiven=?, KYCPreparedOn=?, NextKYCReviewDate=?, "
							+ " KYCComments=?, QA1ExposedPerson=?, QA2NonResident=?, QA3Individuals=?, QA4SelectCountries=?, QA5CustHRBusiness=?, QA6HoldMailCustomer=?, "
							+ " QA7ReputationalAspects=? where RefNo=?");
			
			
			
			
/*			 			QB1AnnualCredits=?, QB2AggregateBalance=?, QB3AssetProducts=?, QB4InvestmentsPortfolio=?, QB5ResidentCustomer=?,"
						+ " QB6RiskClassification=?, CustReasOpenAccount=?, SourceFundAccntReg=?, ApproxAnnualTurnOverExpt=?, NoOfTransMonthCash=?, NoOfTransMonthCheq=?,"
						+ " TotalAmtTransAEDCash=?, TotalAmtTransAEDCheq=?, PurposeTransCash=?, PurposeTransCheq=?, DepNoOfTransMonthCash=?, DepNoOfTransMonthCheq=?,"
						+ " DepTotalAmtTransAEDCash=?, DepTotalAmtTransAEDCheq=?, DepPurposeTransCash=?, DepPurposeTransCheq=?, MaxDepositsCash=?, MaxDepositsCheq=?,"
						+ " MaxWithdrawalsCash=?, MaxWithdrawalsCheq=?, OutNoOfTransMonthInternational=?, OutNoOfTransMonthLocal=?, OutTotalAmtTransAEDInternational=?,"
						+ " OutTotalAmtTransAEDLocal=?, OutPurposeTransInternational=?, OutPurposeTransLocal=?, InNoOfTransMonthInternational=?, InNoOfTransMonthLocal=?,"
						+ " InTotalAmtTransAEDInternational=?, InTotalAmtTransAEDLocal=?, InPurposeTransInternational=?, InPurposeTransLocal=?, MaxSingleAmtInward=?,"
						+ " MaxSingleAmtOutward=?, InternationalRem3MjrContriesIn=?, InternationalRem3MjrContriesOut=?, EddDetailsLnsBusiness=?, EddHowProspectIntroduced=?,"
						+ " EddDescClientsSourceWealth=?, EddProdUsgTransProfile=?, EddNonResident=?, EddDetailsOpenAcc=?, EddAreYouAwereOfAnyAspect=?,"
						+ " IConfirmCheckbox=?, PreparedBy=?, PreparedDate=?, ReviewedBy=?,ReviewedDate=?, ApprovedBy=?, ApprovedDate=? 
			*/
				
				pstmt.setString(1,Utility.trim(kycDetailsWrapper.customerName));
				pstmt.setString(2,Utility.trim(kycDetailsWrapper.uAEResident));
				pstmt.setString(3,Utility.trim(kycDetailsWrapper.kYCType));
				pstmt.setString(4,Utility.trim(kycDetailsWrapper.cisNoForExistingCustomer));
				pstmt.setString(5,Utility.trim(kycDetailsWrapper.pOAGiven));
				pstmt.setDate(6,Utility.getDate(kycDetailsWrapper.kYCPreparedOn));
				pstmt.setDate(7,Utility.getDate(kycDetailsWrapper.nextKYCReviewDate));
				pstmt.setString(8,Utility.trim(kycDetailsWrapper.kYCComments));
				pstmt.setString(9,Utility.trim(kycDetailsWrapper.qA1ExposedPerson));
				pstmt.setString(10,Utility.trim(kycDetailsWrapper.qA2NonResident));
				pstmt.setString(11,Utility.trim(kycDetailsWrapper.qA3Individuals));
				pstmt.setString(12,Utility.trim(kycDetailsWrapper.qA4SelectCountries));
				pstmt.setString(13,Utility.trim(kycDetailsWrapper.qA5CustHRBusiness));
				pstmt.setString(14,Utility.trim(kycDetailsWrapper.qA6HoldMailCustomer));
				pstmt.setString(15,Utility.trim(kycDetailsWrapper.qA7ReputationalAspects));
				pstmt.setString(16,Utility.trim(kycDetailsWrapper.refNo));
				
				
			/*	pstmt.setString(16,Utility.trim(kycDetailsWrapper.qB1AnnualCredits));
				pstmt.setString(17,Utility.trim(kycDetailsWrapper.qB2AggregateBalance));
				pstmt.setString(18,Utility.trim(kycDetailsWrapper.qB3AssetProducts));
				pstmt.setString(19,Utility.trim(kycDetailsWrapper.qB4InsvetmentPortfolio));
				pstmt.setString(20,Utility.trim(kycDetailsWrapper.qB5ResidentCustomer));
				pstmt.setString(21,Utility.trim(kycDetailsWrapper.qB6RiskClassification));
				pstmt.setString(22,Utility.trim(kycDetailsWrapper.custReasOpenAccount));
				pstmt.setString(23,Utility.trim(kycDetailsWrapper.sourceFundAccntReg));
				pstmt.setString(24,Utility.trim(kycDetailsWrapper.approxAnnualTurnOverExpt));
							
							
				pstmt.setString(25,Utility.trim(kycDetailsWrapper.noOfTransMonthCash));
				pstmt.setString(26,Utility.trim(kycDetailsWrapper.noOfTransMonthCheq));
				pstmt.setString(27,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCash));
				pstmt.setString(28,Utility.trim(kycDetailsWrapper.totalAmtTransAEDCheq));
				pstmt.setString(29,Utility.trim(kycDetailsWrapper.purposeTransCash));
				pstmt.setString(30,Utility.trim(kycDetailsWrapper.purposeTransCheq));
							
				pstmt.setString(31,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCash));
				pstmt.setString(32,Utility.trim(kycDetailsWrapper.depNoOfTransMonthCheq));
				pstmt.setString(33,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCash));
				pstmt.setString(34,Utility.trim(kycDetailsWrapper.depTotalAmtTransAEDCheq));
				pstmt.setString(35,Utility.trim(kycDetailsWrapper.depPurposeTransCash));
				pstmt.setString(36,Utility.trim(kycDetailsWrapper.depPurposeTransCheq));
							
							
				pstmt.setString(37,Utility.trim(kycDetailsWrapper.maxDepositsCash));
				pstmt.setString(38,Utility.trim(kycDetailsWrapper.maxDepositsCheq));
				pstmt.setString(39,Utility.trim(kycDetailsWrapper.maxWithdrawalsCash));
				pstmt.setString(40,Utility.trim(kycDetailsWrapper.maxWithdrawalsCheq));
							
				pstmt.setString(41,Utility.trim(kycDetailsWrapper.outNoOfTransMonthInternational));
				pstmt.setString(42,Utility.trim(kycDetailsWrapper.outNoOfTransInMntLocal));
				pstmt.setString(43,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDInternational));
				pstmt.setString(44,Utility.trim(kycDetailsWrapper.outTotalAmtTransAEDLocal));
				pstmt.setString(45,Utility.trim(kycDetailsWrapper.outPurposeTransInternational));
				pstmt.setString(46,Utility.trim(kycDetailsWrapper.outPurposeTransLocal));
							
							
				pstmt.setString(47,Utility.trim(kycDetailsWrapper.inNoOfTransMonthInternational));
				pstmt.setString(48,Utility.trim(kycDetailsWrapper.inNoOfTransMonthLocal));
				pstmt.setString(49,Utility.trim(kycDetailsWrapper.inTotalAmtTransAEDInternational));
				pstmt.setString(50,Utility.trim(kycDetailsWrapper.inTotlAmtTransAEDLocal));
				pstmt.setString(51,Utility.trim(kycDetailsWrapper.inPurposeTransInternational));
				pstmt.setString(52,Utility.trim(kycDetailsWrapper.inPurposeTransLocal));
							
							
				pstmt.setString(53,Utility.trim(kycDetailsWrapper.maxSingleAmtInward));
				pstmt.setString(54,Utility.trim(kycDetailsWrapper.maxSingleAmtOutward));
				pstmt.setString(55,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesIn));
				pstmt.setString(56,Utility.trim(kycDetailsWrapper.internationalRem3MjrContriesOut));
							
							
				pstmt.setString(57,Utility.trim(kycDetailsWrapper.eddDetailsLnsBusiness));
				pstmt.setString(58,Utility.trim(kycDetailsWrapper.eddHowProspectIntroduced));
				pstmt.setString(59,Utility.trim(kycDetailsWrapper.eddDescClientsSourceWealth));
				pstmt.setString(60,Utility.trim(kycDetailsWrapper.eddProdUsgTransProfile));
				pstmt.setString(61,Utility.trim(kycDetailsWrapper.eddNonResident));
				pstmt.setString(62,Utility.trim(kycDetailsWrapper.eddDetailsOpenAcc));
				
				pstmt.setString(63,Utility.trim(kycDetailsWrapper.eddAreYouAwereOfAnyAspect));
							
				pstmt.setString(64,Utility.trim(kycDetailsWrapper.iConfirmCheckbox));
							
				pstmt.setString(65,Utility.trim(kycDetailsWrapper.preparedBy));
				pstmt.setDate(66,Utility.getDate(kycDetailsWrapper.preDate));
				pstmt.setString(67,Utility.trim(kycDetailsWrapper.reviewedBy));
				pstmt.setDate(68,Utility.getDate(kycDetailsWrapper.revDate));
				pstmt.setString(69,Utility.trim(kycDetailsWrapper.approvedBy));
				pstmt.setDate(70,Utility.getDate(kycDetailsWrapper.appDate));			
				pstmt.setString(71,Utility.trim(kycDetailsWrapper.refNo));	*/
							
				pstmt.executeUpdate();
				
				pstmt.close();
				
/*				//---------update PathStatus
				
				pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET KYC=? WHERE RefNo=?");
				
				pstmt.setString(1,"Y");
				pstmt.setString(2,kycDetailsWrapper.refNo);
				pstmt.executeUpdate();
				
				pstmt.close();
				//-----------------------
							*/
							
				//---------update PathStatus
				PathStatusHelper pathStatusHelper=new PathStatusHelper();
				dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(kycDetailsWrapper.refNo),"KYC");
				//--
				
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
	
	
	
/*	public AbstractWrapper updateKycTran(KycDetailsWrapper kycDetailsWrapper)throws Exception {

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
	}
	*/
	

}
