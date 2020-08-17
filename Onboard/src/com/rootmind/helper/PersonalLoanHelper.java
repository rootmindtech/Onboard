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

public class PersonalLoanHelper extends Helper {
	
	public AbstractWrapper insertPersonalLoan(PersonalLoanWrapper personalLoanWrapper)throws Exception {

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
				
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_PersonalLoan WHERE RefNo=?");
				
				
				System.out.println("PersonalLoan RefNo is" + personalLoanWrapper.refNo);
				
				pstmt.setString(1,personalLoanWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updatePersonalLoan(personalLoanWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
				
				
					sql="INSERT INTO RMT_PersonalLoan(RefNo, AccountNo, FinanceAccountNo, CampaignCode, WifeOrHusbandEmployed, NoOfChildren, Education, "
							+ "IWantApplyFor, PurposeOfLoan, PersonalRName, PersonalRTelephone, HealthQA1, HealthQA2, HealthQB1, HealthQB2, HealthAddress, "
							+ "NameBeneficiary1, NameBeneficiary2) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
	/*				,TotalFinance,InterestRate,Tenor,InstallmentAmount,StartingDate,LastInstallmentAmount, "
							+ "NoOfInstallments,ProcessingFee,InsuranceAmount,SettlementOwnResoures,SettlementRepeatLoan,SettlementOtherBank,OtherFees
	*/				
					System.out.println("sql " + sql);
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,Utility.trim(personalLoanWrapper.refNo));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.accountNo));
					pstmt.setString(3,Utility.trim(personalLoanWrapper.financeAccountNo));
					pstmt.setString(4,Utility.trim(personalLoanWrapper.campaignCode));
					pstmt.setString(5,Utility.trim(personalLoanWrapper.wifeOrHusbandEmployed));
					pstmt.setString(6,Utility.trim(personalLoanWrapper.noOfChildren));
					pstmt.setString(7,Utility.trim(personalLoanWrapper.education));
					pstmt.setString(8,Utility.trim(personalLoanWrapper.iWantApplyFor));
					pstmt.setString(9,Utility.trim(personalLoanWrapper.purposeOfLoan));
					pstmt.setString(10,Utility.trim(personalLoanWrapper.personalRName));
					pstmt.setString(11,Utility.trim(personalLoanWrapper.personalRTelephone));
					pstmt.setString(12,Utility.trim(personalLoanWrapper.healthQA1));
					pstmt.setString(13,Utility.trim(personalLoanWrapper.healthQA2));
					pstmt.setString(14,Utility.trim(personalLoanWrapper.healthQB1));
					pstmt.setString(15,Utility.trim(personalLoanWrapper.healthQB2));
					pstmt.setString(16,Utility.trim(personalLoanWrapper.healthAddress));
					pstmt.setString(17,Utility.trim(personalLoanWrapper.nameBeneficiary1));
					pstmt.setString(18,Utility.trim(personalLoanWrapper.nameBeneficiary2));
					
				/*	pstmt.setString(19,Utility.trim(personalLoanWrapper.totalFinance));
					pstmt.setString(20,Utility.trim(personalLoanWrapper.interestRate));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.tenor));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.installmentAmount));
					pstmt.setDate(2,Utility.getDate(personalLoanWrapper.startingDate));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.lastInstallmentAmount));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.noOfInstallments));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.processingFee));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.insuranceAmount));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementOwnResoures));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementRepeatLoan));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementOtherBank));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.otherFees))*/;
					
					pstmt.executeUpdate();
					pstmt.close();
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					pathStatusHelper.updatePathStatus(Utility.trim(personalLoanWrapper.refNo),"PERSONALLOAN");
					//--
					
					personalLoanWrapper.recordFound=true;
					
					dataArrayWrapper.personalLoanWrapper=new PersonalLoanWrapper[1];
					dataArrayWrapper.personalLoanWrapper[0]=personalLoanWrapper;
					
					dataArrayWrapper.recordFound=true;
					
					System.out.println("Personal Loan Data Inserted");
					
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

	
	
	public AbstractWrapper fetchPersonalLoan(PersonalLoanWrapper personalLoanWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, AccountNo, FinanceAccountNo, CampaignCode, WifeOrHusbandEmployed, "
						+ "NoOfChildren ,Education, IWantApplyFor, PurposeOfLoan, PersonalRName, PersonalRTelephone, HealthQA1, HealthQA2, HealthQB1, "
						+ "HealthQB2, HealthAddress, NameBeneficiary1, NameBeneficiary2 FROM RMT_PersonalLoan WHERE RefNo=?");
				
		
						
				System.out.println("Personal Loan RefNo is" + personalLoanWrapper.refNo);
				
				pstmt.setString(1,personalLoanWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					personalLoanWrapper = new PersonalLoanWrapper();
					
				
					
					personalLoanWrapper.refNo = Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + personalLoanWrapper.refNo);
					
	
					personalLoanWrapper.accountNo = Utility.trim(resultSet.getString("AccountNo"));
					personalLoanWrapper.financeAccountNo = Utility.trim(resultSet.getString("FinanceAccountNo"));
					personalLoanWrapper.campaignCode = Utility.trim(resultSet.getString("CampaignCode"));
					personalLoanWrapper.wifeOrHusbandEmployed = Utility.trim(resultSet.getString("WifeOrHusbandEmployed"));
					personalLoanWrapper.noOfChildren = Utility.trim(resultSet.getString("NoOfChildren"));
					personalLoanWrapper.education = Utility.trim(resultSet.getString("Education"));
					personalLoanWrapper.iWantApplyFor = Utility.trim(resultSet.getString("IWantApplyFor"));
					personalLoanWrapper.purposeOfLoan = Utility.trim(resultSet.getString("PurposeOfLoan"));
					personalLoanWrapper.personalRName = Utility.trim(resultSet.getString("PersonalRName"));
					personalLoanWrapper.personalRTelephone = Utility.trim(resultSet.getString("PersonalRTelephone"));
					personalLoanWrapper.healthQA1 = Utility.trim(resultSet.getString("HealthQA1"));
					personalLoanWrapper.healthQA2 = Utility.trim(resultSet.getString("HealthQA2"));
					personalLoanWrapper.healthQB1 = Utility.trim(resultSet.getString("HealthQB1"));
					personalLoanWrapper.healthQB2 = Utility.trim(resultSet.getString("HealthQB2"));
					personalLoanWrapper.healthAddress = Utility.trim(resultSet.getString("HealthAddress"));
					personalLoanWrapper.nameBeneficiary1 = Utility.trim(resultSet.getString("NameBeneficiary1"));
					personalLoanWrapper.nameBeneficiary2 = Utility.trim(resultSet.getString("NameBeneficiary2"));
					
			
					personalLoanWrapper.iWantApplyForValue=popoverHelper.fetchPopoverDesc(personalLoanWrapper.iWantApplyFor, "Decision");
					personalLoanWrapper.purposeOfLoanValue=popoverHelper.fetchPopoverDesc(personalLoanWrapper.purposeOfLoan, "Decision");
					personalLoanWrapper.healthQA1Value=popoverHelper.fetchPopoverDesc(personalLoanWrapper.healthQA1, "Decision");
					personalLoanWrapper.healthQB1Value=popoverHelper.fetchPopoverDesc(personalLoanWrapper.healthQB1, "Decision");
					
					personalLoanWrapper.recordFound=true;
					System.out.println("personal Loan Fetch");
	
					vector.addElement(personalLoanWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.personalLoanWrapper = new PersonalLoanWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalLoanWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.personalLoanWrapper = new PersonalLoanWrapper[1];
				dataArrayWrapper.personalLoanWrapper[0]= personalLoanWrapper;
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
	
	public PersonalLoanWrapper fetchPersonalLoan(PersonalLoanWrapper personalLoanWrapper,String refNo)throws Exception {



		try 
		{

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchPersonalLoan(personalLoanWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				personalLoanWrapper = dataArrayWrapper.personalLoanWrapper[0];
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
			
		}

		return personalLoanWrapper;

	}
	
	
	public AbstractWrapper updatePersonalLoan(PersonalLoanWrapper personalLoanWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Personal Loan");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_PersonalLoan WHERE RefNo=?");
				
			
				System.out.println("PersonalLoan RefNo is" + personalLoanWrapper.refNo);
				
				pstmt.setString(1,personalLoanWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertPersonalLoan(personalLoanWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_PersonalLoan SET AccountNo=?, FinanceAccountNo=?, CampaignCode=?, WifeOrHusbandEmployed=?, "
						+" NoOfChildren =?, Education=?, IWantApplyFor=?, PurposeOfLoan=?, PersonalRName=?, PersonalRTelephone=?, HealthQA1=?, HealthQA2=?, "
						+ "HealthQB1=?, HealthQB2=?, HealthAddress=?, NameBeneficiary1=?, NameBeneficiary2=? where RefNo=?");
					
					
				/*	,TotalFinance=?,InterestRate=?,Tenor=?,InstallmentAmount=?,StartingDate=?,LastInstallmentAmount=?, "
							+ "NoOfInstallments=?,ProcessingFee=?,InsuranceAmount=?,SettlementOwnResoures=?,SettlementRepeatLoan=?,SettlementOtherBank=?,OtherFees=?,
	*/
	


					
					pstmt.setString(1,Utility.trim(personalLoanWrapper.accountNo));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.financeAccountNo));
					pstmt.setString(3,Utility.trim(personalLoanWrapper.campaignCode));
					pstmt.setString(4,Utility.trim(personalLoanWrapper.wifeOrHusbandEmployed));
					pstmt.setString(5,Utility.trim(personalLoanWrapper.noOfChildren));
					pstmt.setString(6,Utility.trim(personalLoanWrapper.education));
					pstmt.setString(7,Utility.trim(personalLoanWrapper.iWantApplyFor));
					pstmt.setString(8,Utility.trim(personalLoanWrapper.purposeOfLoan));
					pstmt.setString(9,Utility.trim(personalLoanWrapper.personalRName));
					pstmt.setString(10,Utility.trim(personalLoanWrapper.personalRTelephone));
					pstmt.setString(11,Utility.trim(personalLoanWrapper.healthQA1));
					pstmt.setString(12,Utility.trim(personalLoanWrapper.healthQA2));
					pstmt.setString(13,Utility.trim(personalLoanWrapper.healthQB1));
					pstmt.setString(14,Utility.trim(personalLoanWrapper.healthQB2));
					pstmt.setString(15,Utility.trim(personalLoanWrapper.healthAddress));
					pstmt.setString(16,Utility.trim(personalLoanWrapper.nameBeneficiary1));
					pstmt.setString(17,Utility.trim(personalLoanWrapper.nameBeneficiary2));
					
				/*	pstmt.setString(2,Utility.trim(personalLoanWrapper.totalFinance));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.interestRate));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.tenor));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.installmentAmount));
					pstmt.setDate(2,Utility.getDate(personalLoanWrapper.startingDate));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.lastInstallmentAmount));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.noOfInstallments));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.processingFee));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.insuranceAmount));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementOwnResoures));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementRepeatLoan));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.settlementOtherBank));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.otherFees));
					*/
					pstmt.setString(18,Utility.trim(personalLoanWrapper.refNo));
	
					System.out.println("Personal Updated RefNo " + personalLoanWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					pathStatusHelper.updatePathStatus(Utility.trim(personalLoanWrapper.refNo),"PERSONALLOAN");
					//--

	
				
					personalLoanWrapper.recordFound=true;
	
					dataArrayWrapper.personalLoanWrapper=new PersonalLoanWrapper[1];
					dataArrayWrapper.personalLoanWrapper[0]=personalLoanWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Personal Loan updated " );
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
	
	
	
	
	
	public AbstractWrapper fetchPersonalLoan2(PersonalLoanWrapper personalLoanWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//OccupationDetailsWrapper occupationDetailsWrapper=new OccupationDetailsWrapper();
		
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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, TotalFinance, InterestRate, Tenor, InstallmentAmount, StartingDate, LastInstallmentAmount, "
					+ "NoOfInstallments, ProcessingFee, InsuranceAmount, SettlementOwnResoures, SettlementRepeatLoan, SettlementOtherBank, "
					+ "OtherFees FROM RMT_PersonalLoan WHERE RefNo=?");
				
			
				System.out.println("Fetch Personal Loan2 RefNo is" + personalLoanWrapper.refNo);
				
				pstmt.setString(1,personalLoanWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					personalLoanWrapper = new PersonalLoanWrapper();
					
				
					
					personalLoanWrapper.refNo = Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + personalLoanWrapper.refNo);
					
					personalLoanWrapper.totalFinance = Utility.trim(resultSet.getString("TotalFinance"));
					personalLoanWrapper.interestRate = Utility.trim(resultSet.getString("InterestRate"));
					personalLoanWrapper.tenor = Utility.trim(resultSet.getString("Tenor"));
					personalLoanWrapper.installmentAmount = Utility.trim(resultSet.getString("InstallmentAmount"));
					personalLoanWrapper.startingDate = Utility.setDate(resultSet.getString("StartingDate"));
					personalLoanWrapper.lastInstallmentAmount = Utility.trim(resultSet.getString("LastInstallmentAmount"));
					personalLoanWrapper.noOfInstallments = Utility.trim(resultSet.getString("NoOfInstallments"));
					personalLoanWrapper.processingFee = Utility.trim(resultSet.getString("ProcessingFee"));
					personalLoanWrapper.insuranceAmount = Utility.trim(resultSet.getString("InsuranceAmount"));
					personalLoanWrapper.settlementOwnResoures = Utility.trim(resultSet.getString("SettlementOwnResoures"));
					personalLoanWrapper.settlementRepeatLoan = Utility.trim(resultSet.getString("SettlementRepeatLoan"));
					personalLoanWrapper.settlementOtherBank = Utility.trim(resultSet.getString("SettlementOtherBank"));
					personalLoanWrapper.otherFees = Utility.trim(resultSet.getString("OtherFees"));
					
					/*personalLoanWrapper.homeCountryValue=popoverHelper.fetchPopoverDesc(personalLoanWrapper.homeCountry, "NATIONALITY");*/
					
					
					personalLoanWrapper.recordFound=true;
					System.out.println("personal Loan2 Fetch");
	
					vector.addElement(personalLoanWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.personalLoanWrapper = new PersonalLoanWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalLoanWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.personalLoanWrapper = new PersonalLoanWrapper[1];
				dataArrayWrapper.personalLoanWrapper[0]= personalLoanWrapper;
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
	
	
	
	public AbstractWrapper updatePersonalLoan2(PersonalLoanWrapper personalLoanWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Personal Loan2");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_PersonalLoan WHERE RefNo=?");
				
			
				System.out.println("PersonalLoan RefNo is" + personalLoanWrapper.refNo);
				
				pstmt.setString(1,personalLoanWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertPersonalLoan2(personalLoanWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_PersonalLoan SET TotalFinance=?,InterestRate=?,Tenor=?,InstallmentAmount=?,StartingDate=?, "
						+ "LastInstallmentAmount=?, NoOfInstallments=?,ProcessingFee=?,InsuranceAmount=?,SettlementOwnResoures=?,SettlementRepeatLoan=?, "
						+ "SettlementOtherBank=?, OtherFees=? where RefNo=?");
	
			
					pstmt.setString(1,Utility.trim(personalLoanWrapper.totalFinance));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.interestRate));
					pstmt.setString(3,Utility.trim(personalLoanWrapper.tenor));
					pstmt.setString(4,Utility.trim(personalLoanWrapper.installmentAmount));
					pstmt.setDate(5,Utility.getDate(personalLoanWrapper.startingDate));
					pstmt.setString(6,Utility.trim(personalLoanWrapper.lastInstallmentAmount));
					pstmt.setString(7,Utility.trim(personalLoanWrapper.noOfInstallments));
					pstmt.setString(8,Utility.trim(personalLoanWrapper.processingFee));
					pstmt.setString(9,Utility.trim(personalLoanWrapper.insuranceAmount));
					pstmt.setString(10,Utility.trim(personalLoanWrapper.settlementOwnResoures));
					pstmt.setString(11,Utility.trim(personalLoanWrapper.settlementRepeatLoan));
					pstmt.setString(12,Utility.trim(personalLoanWrapper.settlementOtherBank));
					pstmt.setString(13,Utility.trim(personalLoanWrapper.otherFees));
					
					pstmt.setString(14,Utility.trim(personalLoanWrapper.refNo));
	
					System.out.println("Personal Loan2 Update RefNo " + personalLoanWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					pathStatusHelper.updatePathStatus(Utility.trim(personalLoanWrapper.refNo),"PERSONALLOAN2");
					//--

				
					personalLoanWrapper.recordFound=true;
	
					dataArrayWrapper.personalLoanWrapper=new PersonalLoanWrapper[1];
					dataArrayWrapper.personalLoanWrapper[0]=personalLoanWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Personal Loan2 updated " );
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
	
	
	
	public AbstractWrapper insertPersonalLoan2(PersonalLoanWrapper personalLoanWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println(" insert PersonalLoan2");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				
					pstmt = con.prepareStatement("INSERT INTO RMT_PersonalLoan(RefNo, TotalFinance, InterestRate, Tenor, InstallmentAmount, StartingDate, "
						+ "LastInstallmentAmount, NoOfInstallments,ProcessingFee,InsuranceAmount,SettlementOwnResoures,SettlementRepeatLoan, "
						+ "SettlementOtherBank, OtherFees) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	
	
					pstmt.setString(1,Utility.trim(personalLoanWrapper.refNo));
					pstmt.setString(2,Utility.trim(personalLoanWrapper.totalFinance));
					pstmt.setString(3,Utility.trim(personalLoanWrapper.interestRate));
					pstmt.setString(4,Utility.trim(personalLoanWrapper.tenor));
					pstmt.setString(5,Utility.trim(personalLoanWrapper.installmentAmount));
					pstmt.setDate(6,Utility.getDate(personalLoanWrapper.startingDate));
					pstmt.setString(7,Utility.trim(personalLoanWrapper.lastInstallmentAmount));
					pstmt.setString(8,Utility.trim(personalLoanWrapper.noOfInstallments));
					pstmt.setString(9,Utility.trim(personalLoanWrapper.processingFee));
					pstmt.setString(10,Utility.trim(personalLoanWrapper.insuranceAmount));
					pstmt.setString(11,Utility.trim(personalLoanWrapper.settlementOwnResoures));
					pstmt.setString(12,Utility.trim(personalLoanWrapper.settlementRepeatLoan));
					pstmt.setString(13,Utility.trim(personalLoanWrapper.settlementOtherBank));
					pstmt.setString(14,Utility.trim(personalLoanWrapper.otherFees));
					
					
	
					System.out.println("Personal Loan2 insert RefNo " + personalLoanWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					pathStatusHelper.updatePathStatus(Utility.trim(personalLoanWrapper.refNo),"PERSONALLOAN2");
					//--

				
					personalLoanWrapper.recordFound=true;
	
					dataArrayWrapper.personalLoanWrapper=new PersonalLoanWrapper[1];
					dataArrayWrapper.personalLoanWrapper[0]=personalLoanWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Personal Loan2 inserted " );
			
				
			
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
}
