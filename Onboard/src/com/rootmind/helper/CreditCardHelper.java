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

public class CreditCardHelper extends Helper {
	
	
	public AbstractWrapper insertCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

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
				
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
				
				
				System.out.println("insertCreditCard RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updateCreditCard(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
					
					sql=" INSERT INTO RMT_CreditCard(RefNo, CardType, BPMSubProduct, DeliveryOption, YearsInUAE, PerRefHomeCountryName, PerRefHomeCountryTelephone, "
							+ " PerRefUAEName1,PerRefUAETelephone1, PerRefUAEName2, PerRefUAETelephone2, SuppTitle, SuppName, SuppRelationship, SuppDOB, AccountNumber, "
							+ "AccDtlsAccountType, AutoPaymentAmount) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
					System.out.println("sql " + sql);
					
					 pstmt = con.prepareStatement(sql);
		
					pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
					pstmt.setString(2,Utility.trim(creditCardWrapper.cardType));
					pstmt.setString(3,Utility.trim(creditCardWrapper.bpmSubProduct));
					pstmt.setString(4,Utility.trim(creditCardWrapper.deliveryOption));
					pstmt.setString(5,Utility.trim(creditCardWrapper.yearsInUAE));
					pstmt.setString(6,Utility.trim(creditCardWrapper.perRefHomeCountryName));
					pstmt.setString(7,Utility.trim(creditCardWrapper.perRefHomeCountryTelephone));
					pstmt.setString(8,Utility.trim(creditCardWrapper.perRefUAEName1));
					pstmt.setString(9,Utility.trim(creditCardWrapper.perRefUAETelephone1));
					pstmt.setString(10,Utility.trim(creditCardWrapper.perRefUAEName2));
					pstmt.setString(11,Utility.trim(creditCardWrapper.perRefUAETelephone2));
					pstmt.setString(12,Utility.trim(creditCardWrapper.suppTitle));
					pstmt.setString(13,Utility.trim(creditCardWrapper.suppName));
					pstmt.setString(14,Utility.trim(creditCardWrapper.suppRelationship));
					pstmt.setDate(15,Utility.getDate(creditCardWrapper.suppDOB));
					pstmt.setString(16,Utility.trim(creditCardWrapper.accountNumber));
					pstmt.setString(17,Utility.trim(creditCardWrapper.accDtlsAccountType));
					pstmt.setString(18,Utility.trim(creditCardWrapper.autoPaymentAmount));
					

					pstmt.executeUpdate();
					pstmt.close();
					
					
					//---------update PathStatus
					//PathStatusHelper pathStatusHelper=new PathStatusHelper();
					//dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(creditCardWrapper.refNo),"CREDITCARD");
					//--
					
					creditCardWrapper.recordFound=true;
					
					dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
					dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
					
					dataArrayWrapper.recordFound=true;
					
					System.out.println("Credit Card Inserted");
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
	
	/*public AbstractWrapper insertCreditCard2(CreditCardWrapper creditCardWrapper)throws Exception {

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
			con = getConnection("ONBOARD");
			
			sql=" INSERT INTO RMT_CreditCard(RefNo, CardType, BPMSubProduct, DeliveryOption, YearsInUAE, PerRefHomeCountryName, PerRefHomeCountryTelephone, PerRefUAEName1, "
					+ "PerRefUAETelephone1, PerRefUAEName2, PerRefUAETelephone2, SuppTitle, SuppName, SuppRelationship, SuppDOB, AccountNumber, "
					+ "AccDtlsAccountType, AutoPayment, Amount, Product, NameOfBank, AccountCCNo, CreditLmtMonthPayments, CreditShield, AccidentCare, "
					+ "OSAName1, OSATelephone, OSAName2, OSAGSM, OSAName3, OSAAlShamil, WaselName, WaselAccountNumber, WaselAmount, WaselWeeklyDay, "
					+ "WaselMonthlyAmount, WaselMonthlyDate, RenewalOption, CISNumber1, CaseID1, PrimaryMobileNo, BeneficiaryTelephone, BeneficiaryType, "
					+ "CISNumber2, CaseID2, CampaignCode) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			System.out.println("sql " + sql);
			
			 pstmt = con.prepareStatement(sql);

			pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
			pstmt.setString(2,Utility.trim(creditCardWrapper.cardType));
			pstmt.setString(3,Utility.trim(creditCardWrapper.bpmSubProduct));
			pstmt.setString(13,Utility.trim(creditCardWrapper.deliveryOption));
			pstmt.setString(14,Utility.trim(creditCardWrapper.yearsInUAE));
			pstmt.setString(4,Utility.trim(creditCardWrapper.perRefHomeCountryName));
			pstmt.setString(5,Utility.trim(creditCardWrapper.perRefHomeCountryTelephone));
			pstmt.setString(6,Utility.trim(creditCardWrapper.perRefUAEName1));
			pstmt.setString(7,Utility.trim(creditCardWrapper.perRefUAETelephone1));
			pstmt.setString(8,Utility.trim(creditCardWrapper.perRefUAEName2));
			pstmt.setString(9,Utility.trim(creditCardWrapper.perRefUAETelephone2));
			pstmt.setString(10,Utility.trim(creditCardWrapper.suppTitle));
			pstmt.setString(11,Utility.trim(creditCardWrapper.suppName));
			pstmt.setString(12,Utility.trim(creditCardWrapper.suppRelationship));
			pstmt.setString(13,Utility.trim(creditCardWrapper.suppDOB));
			pstmt.setString(14,Utility.trim(creditCardWrapper.accountNumber));
			pstmt.setString(3,Utility.trim(creditCardWrapper.accDtlsAccountType));
			pstmt.setString(4,Utility.trim(creditCardWrapper.autoPaymentAmount));
			
			pstmt.setString(6,Utility.trim(creditCardWrapper.product));
			pstmt.setString(7,Utility.trim(creditCardWrapper.nameOfBank));
			pstmt.setString(8,Utility.trim(creditCardWrapper.accountCCNo));
			pstmt.setString(9,Utility.trim(creditCardWrapper.creditLmtMonthPayments));
			pstmt.setString(10,Utility.trim(creditCardWrapper.creditShield));
			pstmt.setString(11,Utility.trim(creditCardWrapper.accidentCare));
			pstmt.setString(12,Utility.trim(creditCardWrapper.osaName1));
			pstmt.setString(13,Utility.trim(creditCardWrapper.osaTelephone));
			pstmt.setString(14,Utility.trim(creditCardWrapper.osaName2));
			pstmt.setString(3,Utility.trim(creditCardWrapper.osaGSM));
			pstmt.setString(4,Utility.trim(creditCardWrapper.osaName3));
			pstmt.setString(5,Utility.trim(creditCardWrapper.osaAlShamil));
			pstmt.setString(6,Utility.trim(creditCardWrapper.waselName));
			pstmt.setString(7,Utility.trim(creditCardWrapper.waselAccountNumber));
			pstmt.setString(8,Utility.trim(creditCardWrapper.waselAmount));
			pstmt.setString(9,Utility.trim(creditCardWrapper.waselWeeklyDay));
			pstmt.setString(10,Utility.trim(creditCardWrapper.waselMonthlyAmount));
			pstmt.setDate(11,Utility.getDate(creditCardWrapper.waselMonthlyDate));
			pstmt.setString(12,Utility.trim(creditCardWrapper.renewalOption));
			pstmt.setString(13,Utility.trim(creditCardWrapper.cisNumber1));
			pstmt.setString(14,Utility.trim(creditCardWrapper.caseID1));
			pstmt.setString(14,Utility.trim(creditCardWrapper.primaryMobileNo));
			pstmt.setString(14,Utility.trim(creditCardWrapper.beneficiaryTelephone));
			pstmt.setString(14,Utility.trim(creditCardWrapper.beneficiaryType));
			pstmt.setString(14,Utility.trim(creditCardWrapper.cisNumber2));
			pstmt.setString(14,Utility.trim(creditCardWrapper.caseID2));
			pstmt.setString(14,Utility.trim(creditCardWrapper.campaignCode));

			
			pstmt.executeUpdate();
			pstmt.close();
			
			
			creditCardWrapper.recordFound=true;
			
			dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
			dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Credit Card Inserted");
			
			
			
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
	
	*/
	public AbstractWrapper fetchCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, CardType, BPMSubProduct, DeliveryOption, YearsInUAE, PerRefHomeCountryName, "
						+ "PerRefHomeCountryTelephone, PerRefUAEName1, PerRefUAETelephone1, PerRefUAEName2, PerRefUAETelephone2, SuppTitle, SuppName, "
						+ "SuppRelationship, SuppDOB, AccountNumber, AccDtlsAccountType, AutoPaymentAmount FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("CreditCard RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					creditCardWrapper = new CreditCardWrapper();

					creditCardWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("CreditCard RefNo" + creditCardWrapper.refNo);

					creditCardWrapper.cardType=Utility.trim(resultSet.getString("CardType"));
					creditCardWrapper.bpmSubProduct=Utility.trim(resultSet.getString("BPMSubProduct"));
					creditCardWrapper.deliveryOption=Utility.trim(resultSet.getString("DeliveryOption"));
					creditCardWrapper.yearsInUAE=Utility.trim(resultSet.getString("YearsInUAE"));
					creditCardWrapper.perRefHomeCountryName=Utility.trim(resultSet.getString("PerRefHomeCountryName"));
					creditCardWrapper.perRefHomeCountryTelephone=Utility.trim(resultSet.getString("PerRefHomeCountryTelephone"));
					creditCardWrapper.perRefUAEName1=Utility.trim(resultSet.getString("PerRefUAEName1"));
					creditCardWrapper.perRefUAETelephone1=Utility.trim(resultSet.getString("PerRefUAETelephone1"));
					creditCardWrapper.perRefUAEName2=Utility.trim(resultSet.getString("PerRefUAEName2"));
					creditCardWrapper.perRefUAETelephone2=Utility.trim(resultSet.getString("PerRefUAETelephone2"));
					creditCardWrapper.suppTitle=Utility.trim(resultSet.getString("SuppTitle"));
					creditCardWrapper.suppName=Utility.trim(resultSet.getString("SuppName"));
					creditCardWrapper.suppRelationship=Utility.trim(resultSet.getString("SuppRelationship"));
					creditCardWrapper.suppDOB=Utility.setDate(resultSet.getString("SuppDOB"));
					creditCardWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNumber"));
					creditCardWrapper.accDtlsAccountType=Utility.trim(resultSet.getString("AccDtlsAccountType"));
					creditCardWrapper.autoPaymentAmount=Utility.trim(resultSet.getString("AutoPaymentAmount"));

					
					creditCardWrapper.cardTypeValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.cardType, "CardType");
					creditCardWrapper.deliveryOptionValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.deliveryOption, "DebitCardDeliveryChnl");
					creditCardWrapper.autoPaymentAmountValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.autoPaymentAmount, "Decision");
					
					
					
					
					creditCardWrapper.recordFound=true;
					System.out.println(" CreditCard Fetched ");
	
					vector.addElement(creditCardWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[1];
				dataArrayWrapper.creditCardWrapper[0]= creditCardWrapper;
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
	
	public AbstractWrapper fetchCreditCard2(CreditCardWrapper creditCardWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,Product,NameOfBank,AccountCCNo,CreditLmtMonthPayments,CreditShield,AccidentCare,OSAName1, "
						+ "OSATelephone,OSAName2,OSAGSM,OSAName3,OSAAlShamil,WaselName,WaselAccountNumber,WaselAmount,WaselWeeklyDay,WaselMonthlyAmount, "
						+ "WaselMonthlyDate,RenewalOption, DBR, RulesCreditLimited FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("CreditCard RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					creditCardWrapper = new CreditCardWrapper();

					creditCardWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("CreditCard2 RefNo" + creditCardWrapper.refNo);

				
					
					creditCardWrapper.product=Utility.trim(resultSet.getString("Product"));
					creditCardWrapper.nameOfBank=Utility.trim(resultSet.getString("NameOfBank"));
					creditCardWrapper.accountCCNo=Utility.trim(resultSet.getString("AccountCCNo"));
					creditCardWrapper.creditLmtMonthPayments=Utility.trim(resultSet.getString("CreditLmtMonthPayments"));
					creditCardWrapper.creditShield=Utility.trim(resultSet.getString("CreditShield"));
					creditCardWrapper.accidentCare=Utility.trim(resultSet.getString("AccidentCare"));
					creditCardWrapper.osaName1=Utility.trim(resultSet.getString("OSAName1"));
					creditCardWrapper.osaTelephone=Utility.trim(resultSet.getString("OSATelephone"));
					creditCardWrapper.osaName2=Utility.trim(resultSet.getString("OSAName2"));
					creditCardWrapper.osaGSM=Utility.trim(resultSet.getString("OSAGSM"));
					creditCardWrapper.osaName3=Utility.trim(resultSet.getString("OSAName3"));					
					creditCardWrapper.osaAlShamil=Utility.trim(resultSet.getString("OSAAlShamil"));
					creditCardWrapper.waselName=Utility.trim(resultSet.getString("WaselName"));
					creditCardWrapper.waselAccountNumber=Utility.trim(resultSet.getString("WaselAccountNumber"));
					creditCardWrapper.waselAmount=Utility.trim(resultSet.getString("WaselAmount"));
					creditCardWrapper.waselWeeklyDay=Utility.trim(resultSet.getString("WaselWeeklyDay"));
					creditCardWrapper.waselMonthlyAmount=Utility.trim(resultSet.getString("WaselMonthlyAmount"));
					creditCardWrapper.waselMonthlyDate=Utility.setDate(resultSet.getString("WaselMonthlyDate"));
					creditCardWrapper.renewalOption=Utility.trim(resultSet.getString("RenewalOption"));
					creditCardWrapper.dbr=Utility.trim(resultSet.getString("DBR"));
					creditCardWrapper.rulesCreditLimited=Utility.trim(resultSet.getString("RulesCreditLimited"));
			
					
					creditCardWrapper.productValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.product, "CardType");
					creditCardWrapper.nameOfBankValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.nameOfBank, "NameOfBank");
					creditCardWrapper.waselWeeklyDayValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.waselWeeklyDay, "Week");
					creditCardWrapper.creditShieldValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.creditShield, "Decision");
					creditCardWrapper.accidentCareValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.accidentCare, "Decision");
					
					
					creditCardWrapper.recordFound=true;
					System.out.println(" CreditCard 2 Fetched ");
	
					vector.addElement(creditCardWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[1];
				dataArrayWrapper.creditCardWrapper[0]= creditCardWrapper;
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
	public AbstractWrapper fetchCreditCard3(CreditCardWrapper creditCardWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,CISNumber1,CaseID1,PrimaryMobileNo,BeneficiaryTelephone, "
						+ "BeneficiaryType,CISNumber2,CaseID2,CampaignCode FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("CreditCard 3 RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					creditCardWrapper = new CreditCardWrapper();

					creditCardWrapper.cisNumber1=Utility.trim(resultSet.getString("CISNumber1"));
					creditCardWrapper.caseID1=Utility.trim(resultSet.getString("CaseID1"));
					creditCardWrapper.primaryMobileNo=Utility.trim(resultSet.getString("PrimaryMobileNo"));
					creditCardWrapper.beneficiaryTelephone=Utility.trim(resultSet.getString("BeneficiaryTelephone"));
					creditCardWrapper.beneficiaryType=Utility.trim(resultSet.getString("BeneficiaryType"));
					creditCardWrapper.cisNumber2=Utility.trim(resultSet.getString("CISNumber2"));
					creditCardWrapper.caseID2=Utility.trim(resultSet.getString("CaseID2"));
					creditCardWrapper.campaignCode=Utility.trim(resultSet.getString("CampaignCode"));
					
					
					creditCardWrapper.beneficiaryTypeValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.beneficiaryType, "BeneficiaryType");
					
					
					creditCardWrapper.recordFound=true;
					System.out.println(" CreditCard 3 Fetched ");
	
					vector.addElement(creditCardWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[1];
				dataArrayWrapper.creditCardWrapper[0]= creditCardWrapper;
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
	
	
	public AbstractWrapper updateCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update CreditCard Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("credit Card RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCreditCard(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_CreditCard SET CardType=?, BPMSubProduct=?,DeliveryOption=?,YearsInUAE=?, PerRefHomeCountryName=?, "
							+ "PerRefHomeCountryTelephone=?, PerRefUAEName1=?, PerRefUAETelephone1=?, PerRefUAEName2=?, PerRefUAETelephone2=?, "
							+ "SuppTitle=?, SuppName=?, SuppRelationship=?, SuppDOB=?, AccountNumber=?, AccDtlsAccountType=?, AutoPaymentAmount=?  where RefNo=?");
	
	
					
					pstmt.setString(1,Utility.trim(creditCardWrapper.cardType));
					pstmt.setString(2,Utility.trim(creditCardWrapper.bpmSubProduct));
					pstmt.setString(3,Utility.trim(creditCardWrapper.deliveryOption));
					pstmt.setString(4,Utility.trim(creditCardWrapper.yearsInUAE));
					pstmt.setString(5,Utility.trim(creditCardWrapper.perRefHomeCountryName));
					pstmt.setString(6,Utility.trim(creditCardWrapper.perRefHomeCountryTelephone));
					pstmt.setString(7,Utility.trim(creditCardWrapper.perRefUAEName1));
					pstmt.setString(8,Utility.trim(creditCardWrapper.perRefUAETelephone1));
					pstmt.setString(9,Utility.trim(creditCardWrapper.perRefUAEName2));
					pstmt.setString(10,Utility.trim(creditCardWrapper.perRefUAETelephone2));
					pstmt.setString(11,Utility.trim(creditCardWrapper.suppTitle));
					pstmt.setString(12,Utility.trim(creditCardWrapper.suppName));
					pstmt.setString(13,Utility.trim(creditCardWrapper.suppRelationship));
					pstmt.setDate(14,Utility.getDate(creditCardWrapper.suppDOB));
					pstmt.setString(15,Utility.trim(creditCardWrapper.accountNumber));
					pstmt.setString(16,Utility.trim(creditCardWrapper.accDtlsAccountType));
					pstmt.setString(17,Utility.trim(creditCardWrapper.autoPaymentAmount));
					pstmt.setString(18,Utility.trim(creditCardWrapper.refNo));
	
	
					System.out.println("Credit Card Update RefNo " + creditCardWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					
					//---------update PathStatus
					//PathStatusHelper pathStatusHelper=new PathStatusHelper();
					//dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(creditCardWrapper.refNo),"CREDITCARD");
					//--

					creditCardWrapper.recordFound=true;
	
					dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
					dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Credit Card Details updated " );
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
	
	public AbstractWrapper updateCreditCard2(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update CreditCard2 Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
			
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCreditCard2(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();

					pstmt = con.prepareStatement("UPDATE RMT_CreditCard SET Product=?, NameOfBank=?, AccountCCNo=?, CreditLmtMonthPayments=?, "
								+ "CreditShield=?, AccidentCare=?, OSAName1=?, OSATelephone=?, OSAName2=?, OSAGSM =?, OSAName3 =?, OSAAlShamil=?, "
								+ " WaselName=?, WaselAccountNumber=?, WaselAmount=?, WaselWeeklyDay=?, WaselMonthlyAmount=?, WaselMonthlyDate=?, "
								+ "RenewalOption=?, DBR=?, RulesCreditLimited=? WHERE RefNo=?");
	
	
						pstmt.setString(1,Utility.trim(creditCardWrapper.product));
						pstmt.setString(2,Utility.trim(creditCardWrapper.nameOfBank));
						pstmt.setString(3,Utility.trim(creditCardWrapper.accountCCNo));
						pstmt.setString(4,Utility.trim(creditCardWrapper.creditLmtMonthPayments));
						pstmt.setString(5,Utility.trim(creditCardWrapper.creditShield));
						pstmt.setString(6,Utility.trim(creditCardWrapper.accidentCare));
						pstmt.setString(7,Utility.trim(creditCardWrapper.osaName1));
						pstmt.setString(8,Utility.trim(creditCardWrapper.osaTelephone));
						pstmt.setString(9,Utility.trim(creditCardWrapper.osaName2));
						pstmt.setString(10,Utility.trim(creditCardWrapper.osaGSM));
						pstmt.setString(11,Utility.trim(creditCardWrapper.osaName3));
						pstmt.setString(12,Utility.trim(creditCardWrapper.osaAlShamil));
						pstmt.setString(13,Utility.trim(creditCardWrapper.waselName));
						pstmt.setString(14,Utility.trim(creditCardWrapper.waselAccountNumber));
						pstmt.setString(15,Utility.trim(creditCardWrapper.waselAmount));
						pstmt.setString(16,Utility.trim(creditCardWrapper.waselWeeklyDay));
						pstmt.setString(17,Utility.trim(creditCardWrapper.waselMonthlyAmount));
						pstmt.setDate(18,Utility.getDate(creditCardWrapper.waselMonthlyDate));
						pstmt.setString(19,Utility.trim(creditCardWrapper.renewalOption));
						pstmt.setString(20,Utility.trim(creditCardWrapper.dbr));
						pstmt.setString(21,Utility.trim(creditCardWrapper.rulesCreditLimited));
						pstmt.setString(22,Utility.trim(creditCardWrapper.refNo));
		
						System.out.println("Update RefNo " + creditCardWrapper.refNo);
		
		
						pstmt.executeUpdate();
		
						pstmt.close();
						
					
		
					
						creditCardWrapper.recordFound=true;
		
						dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
						dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
		
						dataArrayWrapper.recordFound=true;
						System.out.println("Credit Card 2 Details updated " );
				
						
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
	
	public AbstractWrapper insertCreditCard2(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("insert CreditCard2 Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				
					pstmt = con.prepareStatement("INSERT INTO RMT_CreditCard(RefNo,Product, NameOfBank, AccountCCNo, CreditLmtMonthPayments, "
								+ "CreditShield, AccidentCare, OSAName1, OSATelephone, OSAName2, OSAGSM , OSAName3, OSAAlShamil, "
								+ " WaselName, WaselAccountNumber, WaselAmount, WaselWeeklyDay, WaselMonthlyAmount, WaselMonthlyDate, "
								+ "RenewalOption, DBR, RulesCreditLimited) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	
						pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
						pstmt.setString(2,Utility.trim(creditCardWrapper.product));
						pstmt.setString(3,Utility.trim(creditCardWrapper.nameOfBank));
						pstmt.setString(4,Utility.trim(creditCardWrapper.accountCCNo));
						pstmt.setString(5,Utility.trim(creditCardWrapper.creditLmtMonthPayments));
						pstmt.setString(6,Utility.trim(creditCardWrapper.creditShield));
						pstmt.setString(7,Utility.trim(creditCardWrapper.accidentCare));
						pstmt.setString(8,Utility.trim(creditCardWrapper.osaName1));
						pstmt.setString(9,Utility.trim(creditCardWrapper.osaTelephone));
						pstmt.setString(10,Utility.trim(creditCardWrapper.osaName2));
						pstmt.setString(11,Utility.trim(creditCardWrapper.osaGSM));
						pstmt.setString(12,Utility.trim(creditCardWrapper.osaName3));
						pstmt.setString(13,Utility.trim(creditCardWrapper.osaAlShamil));
						pstmt.setString(14,Utility.trim(creditCardWrapper.waselName));
						pstmt.setString(15,Utility.trim(creditCardWrapper.waselAccountNumber));
						pstmt.setString(16,Utility.trim(creditCardWrapper.waselAmount));
						pstmt.setString(17,Utility.trim(creditCardWrapper.waselWeeklyDay));
						pstmt.setString(18,Utility.trim(creditCardWrapper.waselMonthlyAmount));
						pstmt.setDate(19,Utility.getDate(creditCardWrapper.waselMonthlyDate));
						pstmt.setString(20,Utility.trim(creditCardWrapper.renewalOption));
						pstmt.setString(21,Utility.trim(creditCardWrapper.dbr));
						pstmt.setString(22,Utility.trim(creditCardWrapper.rulesCreditLimited));
		
						pstmt.executeUpdate();
		
						pstmt.close();
						
						creditCardWrapper.recordFound=true;
		
						dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
						dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
		
						dataArrayWrapper.recordFound=true;
						System.out.println("Credit Card 2 Details inserted " );
				
						
				
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
	
	public AbstractWrapper updateCreditCard3(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update CreditCard 3 Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
			
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCreditCard3(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();


					pstmt = con.prepareStatement("UPDATE RMT_CreditCard SET CISNumber1=?,CaseID1=?,PrimaryMobileNo=?,BeneficiaryTelephone=?, "
								+ "BeneficiaryType=?,CISNumber2=?,CaseID2=?,CampaignCode=?  where RefNo=?");
		
						pstmt.setString(1,Utility.trim(creditCardWrapper.cisNumber1));
						pstmt.setString(2,Utility.trim(creditCardWrapper.caseID1));
						pstmt.setString(3,Utility.trim(creditCardWrapper.primaryMobileNo));
						pstmt.setString(4,Utility.trim(creditCardWrapper.beneficiaryTelephone));
						pstmt.setString(5,Utility.trim(creditCardWrapper.beneficiaryType));
						pstmt.setString(6,Utility.trim(creditCardWrapper.cisNumber2));
						pstmt.setString(7,Utility.trim(creditCardWrapper.caseID2));
						pstmt.setString(8,Utility.trim(creditCardWrapper.campaignCode));
	
						pstmt.setString(9,Utility.trim(creditCardWrapper.refNo));
		
						System.out.println("Update RefNo " + creditCardWrapper.refNo);
	
						pstmt.executeUpdate();
		
						pstmt.close();
	
						creditCardWrapper.recordFound=true;
		
						dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
						dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
		
						dataArrayWrapper.recordFound=true;
						System.out.println("Credit Card 3 Details updated " );
				
						
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
	
	
	public AbstractWrapper insertCreditCard3(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		System.out.println("insert CreditCard 3 Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				

					pstmt = con.prepareStatement("INSERT INTO RMT_CreditCard(RefNo,CISNumber1,CaseID1,PrimaryMobileNo,BeneficiaryTelephone, "
								+ "BeneficiaryType,CISNumber2,CaseID2,CampaignCode) VALUES(?,?,?,?,?,?,?,?,?)");
						
						pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
						pstmt.setString(2,Utility.trim(creditCardWrapper.cisNumber1));
						pstmt.setString(3,Utility.trim(creditCardWrapper.caseID1));
						pstmt.setString(4,Utility.trim(creditCardWrapper.primaryMobileNo));
						pstmt.setString(5,Utility.trim(creditCardWrapper.beneficiaryTelephone));
						pstmt.setString(6,Utility.trim(creditCardWrapper.beneficiaryType));
						pstmt.setString(7,Utility.trim(creditCardWrapper.cisNumber2));
						pstmt.setString(8,Utility.trim(creditCardWrapper.caseID2));
						pstmt.setString(9,Utility.trim(creditCardWrapper.campaignCode));
				
						pstmt.executeUpdate();
		
						pstmt.close();
	
						creditCardWrapper.recordFound=true;
		
						dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
						dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
		
						dataArrayWrapper.recordFound=true;
						System.out.println("Credit Card 3 Details INSERTED " );
				
						
				
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
	/*public AbstractWrapper insertCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;

		//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		

	
		try {
			con = getConnection("ONBOARD");
			
			sql=" INSERT INTO CREDITCARD(RefNo, LastMonthSalary, LastMonthSalary2, LastMonthSalary3, CPVTypeVerified, IncomeDocPresent"
					+ "SalaryMode, SalaryType, STLFormat, AccommodationType, STLSalary, BankStmtFormat, EDMSID, CardType"
					+ "BPMCardSubProduct, WifeHusbandEmp, NoChildren, CreditShield, AccidentCare, PaymentMethod) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
			pstmt.setBigDecimal(2,Utility.trim(creditCardWrapper.lastMonthSalary));
			pstmt.setBigDecimal(3,Utility.trim(creditCardWrapper.lastMonthSalary2));
			pstmt.setBigDecimal(4,Utility.trim(creditCardWrapper.lastMonthSalary3));
			pstmt.setString(5,Utility.trim(creditCardWrapper.cPVTypeVerified));
			pstmt.setString(6,Utility.trim(creditCardWrapper.incomeDocPresent));
			pstmt.setString(7,Utility.trim(creditCardWrapper.salaryMode));
			pstmt.setString(8,Utility.trim(creditCardWrapper.salaryType));
			pstmt.setString(9,Utility.trim(creditCardWrapper.sTLFormat));
			pstmt.setString(10,Utility.trim(creditCardWrapper.accommodationType));
			pstmt.setString(11,Utility.trim(creditCardWrapper.sTLSalary));
			pstmt.setString(12,Utility.trim(creditCardWrapper.bankStmtFormat));
			pstmt.setString(13,Utility.trim(creditCardWrapper.eDMSID));
			pstmt.setString(14,Utility.trim(creditCardWrapper.cardType));
			pstmt.setString(15,Utility.trim(creditCardWrapper.bPMCardSubProduct));
			pstmt.setString(16,Utility.trim(creditCardWrapper.wifeHusbandEmp));
			pstmt.setString(17,Utility.trim(creditCardWrapper.noChildren));
			pstmt.setString(18,Utility.trim(creditCardWrapper.creditShield));
			pstmt.setString(19,Utility.trim(creditCardWrapper.accidentCare));
			pstmt.setString(20,Utility.trim(creditCardWrapper.paymentMethod));
			
			pstmt.executeUpdate();
			pstmt.close();

			creditCardWrapper.recordFound=true;
			
			
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

		return creditCardWrapper;
	}
	
	
	
	public AbstractWrapper fetchCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

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

		
		System.out.println(":"+ creditCardWrapper.lastMonthSalary);
	
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, LastMonthSalary, LastMonthSalary2, LastMonthSalary3, CPVTypeVerified,"
					+ " IncomeDocPresent, SalaryMode, SalaryType, STLFormat, AccommodationType, STLSalary, BankStmtFormat, EDMSID, CardType"
					+ "BPMCardSubProduct, WifeHusbandEmp, NoChildren, CreditShield, AccidentCare, PaymentMethod FROM CREDITCARD");
		
			pstmt.setString(1,occupationDetailsWrapper.dateOfJoinning.trim());
			pstmt.setString(2,occupationDetailsWrapper.employeeNo.trim());
			
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				
				
				creditCardWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + creditCardWrapper.refNo);
				

				creditCardWrapper.lastMonthSalary=Utility.trim(resultSet.getBigDecimal("LastMonthSalary"));
				System.out.println("LastMonthSalary " + creditCardWrapper.lastMonthSalary);
				
				creditCardWrapper.lastMonthSalary2=Utility.trim(resultSet.getBigDecimal("LastMonthSalary2"));
				
				creditCardWrapper.lastMonthSalary3=Utility.trim(resultSet.getBigDecimal("LastMonthSalary3"));
				
				creditCardWrapper.cPVTypeVerified=Utility.trim(resultSet.getString("CPVTypeVerified"));
				
				creditCardWrapper.incomeDocPresent=Utility.trim(resultSet.getString("IncomeDocPresent"));
				
				creditCardWrapper.salaryMode=Utility.trim(resultSet.getString("SalaryMode"));
				
				creditCardWrapper.salaryType=Utility.trim(resultSet.getString("SalaryType"));
				
				
				creditCardWrapper.sTLFormat=Utility.trim(resultSet.getString("STLFormat"));
				
				creditCardWrapper.accommodationType=Utility.trim(resultSet.getString("AccommodationType"));
				
				creditCardWrapper.sTLSalary=Utility.trim(resultSet.getString("STLSalary"));
				
				creditCardWrapper.bankStmtFormat=Utility.trim(resultSet.getString("BankStmtFormat"));
				
				creditCardWrapper.eDMSID=Utility.trim(resultSet.getString("EDMSID"));
				
				creditCardWrapper.cardType=Utility.trim(resultSet.getString("CardType"));
				
				creditCardWrapper.bPMCardSubProduct=Utility.trim(resultSet.getString("BPMCardSubProduct"));
				
				creditCardWrapper.wifeHusbandEmp=Utility.trim(resultSet.getString("WifeHusbandEmp"));
				
				creditCardWrapper.noChildren=Utility.trim(resultSet.getString("NoChildren"));
				
				creditCardWrapper.creditShield=Utility.trim(resultSet.getString("CreditShield"));
				
				creditCardWrapper.accidentCare=Utility.trim(resultSet.getString("AccidentCare"));
				
				creditCardWrapper.paymentMethod=Utility.trim(resultSet.getString("PaymentMethod"));
				
			
				creditCardWrapper.recordFound=true;
				
				System.out.println("CreditCardDetailsWrapper " );

				vector.addElement(creditCardWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

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
	}
	
	
	public AbstractWrapper updateCreditCard(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		//OccupationDetailsWrapper occupationDetailsWrapper=	new OccupationDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("updateCreditCard");
	
		try {
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE CREDITCARD SET LastMonthSalary=?, LastMonthSalary2=?, LastMonthSalary3=?, CPVTypeVerified=?,"
					+ " IncomeDocPresent=?, SalaryMode=?, SalaryType=?, STLFormat=?, AccommodationType=?, STLSalary=?, BankStmtFormat=?, EDMSID=?, CardType=?"
					+ "BPMCardSubProduct=?, WifeHusbandEmp=?, NoChildren=?, CreditShield=?, AccidentCare=?, PaymentMethod=? where RefNo=? ");
			
			pstmt.setBigDecimal(1,Utility.trim(creditCardWrapper.lastMonthSalary));
			pstmt.setBigDecimal(2,Utility.trim(creditCardWrapper.lastMonthSalary2));
			pstmt.setBigDecimal(3,Utility.trim(creditCardWrapper.lastMonthSalary3));
			pstmt.setString(4,Utility.trim(creditCardWrapper.cPVTypeVerified));
			pstmt.setString(5,Utility.trim(creditCardWrapper.incomeDocPresent));
			pstmt.setString(6,Utility.trim(creditCardWrapper.salaryMode));
			pstmt.setString(7,Utility.trim(creditCardWrapper.salaryType));
			pstmt.setString(8,Utility.trim(creditCardWrapper.sTLFormat));
			pstmt.setString(9,Utility.trim(creditCardWrapper.accommodationType));
			pstmt.setString(10,Utility.trim(creditCardWrapper.sTLSalary));
			pstmt.setString(11,Utility.trim(creditCardWrapper.bankStmtFormat));
			pstmt.setString(12,Utility.trim(creditCardWrapper.eDMSID));
			pstmt.setString(13,Utility.trim(creditCardWrapper.cardType));
			pstmt.setString(14,Utility.trim(creditCardWrapper.bPMCardSubProduct));
			pstmt.setString(15,Utility.trim(creditCardWrapper.wifeHusbandEmp));
			pstmt.setString(16,Utility.trim(creditCardWrapper.noChildren));
			pstmt.setString(17,Utility.trim(creditCardWrapper.creditShield));
			pstmt.setString(18,Utility.trim(creditCardWrapper.accidentCare));
			pstmt.setString(19,Utility.trim(creditCardWrapper.paymentMethod));
			
			pstmt.setString(20,Utility.trim(creditCardWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();

			
			
			creditCardWrapper.recordFound=true;
			
			dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
			dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
			
			System.out.println("CreditCard Details updated " );
			
			
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
	
	//-------------------------insertCreditCardCC is for capture all details from single Credit card screen--------------
	public AbstractWrapper insertCreditCardCC(CreditCardWrapper creditCardWrapper)throws Exception {

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
				
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
				
				
				System.out.println("insertCreditCard RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updateCreditCardCC(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
					
					sql=" INSERT INTO RMT_CreditCard(RefNo, CardType, BPMSubProduct, DeliveryOption, YearsInUAE, PerRefHomeCountryName, PerRefHomeCountryTelephone, "
							+ " PerRefUAEName1,PerRefUAETelephone1, PerRefUAEName2, PerRefUAETelephone2, SuppTitle, SuppName, SuppRelationship, SuppDOB, AccountNumber, "
							+ " AccDtlsAccountType, AutoPaymentAmount, AccountCCNo, CreditLmtMonthPayments, CreditShield, AccidentCare, DBR, RulesCreditLimited, "
							+ " NameOfBank, CISNumber1, CaseID1) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
					System.out.println("sql " + sql);
					
					 pstmt = con.prepareStatement(sql);
		
					pstmt.setString(1,Utility.trim(creditCardWrapper.refNo));
					pstmt.setString(2,Utility.trim(creditCardWrapper.cardType));
					pstmt.setString(3,Utility.trim(creditCardWrapper.bpmSubProduct));
					pstmt.setString(4,Utility.trim(creditCardWrapper.deliveryOption));
					pstmt.setString(5,Utility.trim(creditCardWrapper.yearsInUAE));
					pstmt.setString(6,Utility.trim(creditCardWrapper.perRefHomeCountryName));
					pstmt.setString(7,Utility.trim(creditCardWrapper.perRefHomeCountryTelephone));
					pstmt.setString(8,Utility.trim(creditCardWrapper.perRefUAEName1));
					pstmt.setString(9,Utility.trim(creditCardWrapper.perRefUAETelephone1));
					pstmt.setString(10,Utility.trim(creditCardWrapper.perRefUAEName2));
					pstmt.setString(11,Utility.trim(creditCardWrapper.perRefUAETelephone2));
					pstmt.setString(12,Utility.trim(creditCardWrapper.suppTitle));
					pstmt.setString(13,Utility.trim(creditCardWrapper.suppName));
					pstmt.setString(14,Utility.trim(creditCardWrapper.suppRelationship));
					pstmt.setDate(15,Utility.getDate(creditCardWrapper.suppDOB));
					pstmt.setString(16,Utility.trim(creditCardWrapper.accountNumber));
					pstmt.setString(17,Utility.trim(creditCardWrapper.accDtlsAccountType));
					pstmt.setString(18,Utility.trim(creditCardWrapper.autoPaymentAmount));
					
					pstmt.setString(19,Utility.trim(creditCardWrapper.accountCCNo));
					pstmt.setString(20,Utility.trim(creditCardWrapper.creditLmtMonthPayments));
					pstmt.setString(21,Utility.trim(creditCardWrapper.creditShield));
					pstmt.setString(22,Utility.trim(creditCardWrapper.accidentCare));
					pstmt.setString(23,Utility.trim(creditCardWrapper.dbr));
					pstmt.setString(24,Utility.trim(creditCardWrapper.rulesCreditLimited));
					pstmt.setString(25,Utility.trim(creditCardWrapper.nameOfBank));
					pstmt.setString(26,Utility.trim(creditCardWrapper.cisNumber1));
					pstmt.setString(27,Utility.trim(creditCardWrapper.caseID1));

					pstmt.executeUpdate();
					pstmt.close();
					
					//-------insert into RMT_Account(Product)
					AccountDetailsWrapper accountDetailsWrapper=new AccountDetailsWrapper();
					AccountDetailsHelper accountDetailsHelper=new AccountDetailsHelper();
					
					accountDetailsWrapper.refNo=creditCardWrapper.refNo;
					accountDetailsWrapper.accountNo=creditCardWrapper.accountNo;
					accountDetailsWrapper.accountIBAN=creditCardWrapper.accountIBAN;
					accountDetailsWrapper.accountType=creditCardWrapper.accountType;
					accountDetailsWrapper.currency=creditCardWrapper.currency;
					accountDetailsWrapper.statementCycle=creditCardWrapper.statementCycle;
					accountDetailsWrapper.onlineBankTrans=creditCardWrapper.onlineBankTrans;
					accountDetailsWrapper.accountTitle=creditCardWrapper.accountTitle;
					accountDetailsWrapper.nameCard=creditCardWrapper.nameCard;
					accountDetailsWrapper.favEmirate=creditCardWrapper.favEmirate;
					accountDetailsWrapper.debitCardDelvChnl=creditCardWrapper.debitCardDelvChnl;
					accountDetailsWrapper.pinMailerDelvChnl=creditCardWrapper.pinMailerDelvChnl;
					accountDetailsWrapper.transferSalary=creditCardWrapper.transferSalary;
					accountDetailsWrapper.interestRate=creditCardWrapper.interestRate;
					accountDetailsWrapper.supplCardReq=creditCardWrapper.supplCardReq;
					accountDetailsWrapper.purposeCode=creditCardWrapper.purposeCode;
					accountDetailsWrapper.businessCode=creditCardWrapper.businessCode;
					accountDetailsWrapper.insuranceCode=creditCardWrapper.insuranceCode;
					accountDetailsWrapper.campaignCode=creditCardWrapper.campaignCode;
					accountDetailsWrapper.sellerID=creditCardWrapper.sellerID;
					accountDetailsWrapper.sellerChannel=creditCardWrapper.sellerChannel;
					accountDetailsWrapper.schemeID=creditCardWrapper.schemeID;

					 accountDetailsHelper.updateAccountDetails(accountDetailsWrapper);
					//----------
					
					creditCardWrapper.recordFound=true;
					
					dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
					dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
					
					dataArrayWrapper.recordFound=true;
					
					System.out.println("Credit Card Inserted");
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
	
	public AbstractWrapper updateCreditCardCC(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update CreditCard Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("credit Card RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertCreditCardCC(creditCardWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_CreditCard SET CardType=?, BPMSubProduct=?,DeliveryOption=?,YearsInUAE=?, PerRefHomeCountryName=?, "
							+ " PerRefHomeCountryTelephone=?, PerRefUAEName1=?, PerRefUAETelephone1=?, PerRefUAEName2=?, PerRefUAETelephone2=?, "
							+ " SuppTitle=?, SuppName=?, SuppRelationship=?, SuppDOB=?, AccountNumber=?, AccDtlsAccountType=?, AutoPaymentAmount=?, "
							+ " AccountCCNo=?, CreditLmtMonthPayments=?, CreditShield=?, AccidentCare=?, DBR=?, RulesCreditLimited=?, NameOfBank=?, "
							+ " CISNumber1=?, CaseID1=? WHERE RefNo=?");
	
	
					
					pstmt.setString(1,Utility.trim(creditCardWrapper.cardType));
					pstmt.setString(2,Utility.trim(creditCardWrapper.bpmSubProduct));
					pstmt.setString(3,Utility.trim(creditCardWrapper.deliveryOption));
					pstmt.setString(4,Utility.trim(creditCardWrapper.yearsInUAE));
					pstmt.setString(5,Utility.trim(creditCardWrapper.perRefHomeCountryName));
					pstmt.setString(6,Utility.trim(creditCardWrapper.perRefHomeCountryTelephone));
					pstmt.setString(7,Utility.trim(creditCardWrapper.perRefUAEName1));
					pstmt.setString(8,Utility.trim(creditCardWrapper.perRefUAETelephone1));
					pstmt.setString(9,Utility.trim(creditCardWrapper.perRefUAEName2));
					pstmt.setString(10,Utility.trim(creditCardWrapper.perRefUAETelephone2));
					pstmt.setString(11,Utility.trim(creditCardWrapper.suppTitle));
					pstmt.setString(12,Utility.trim(creditCardWrapper.suppName));
					pstmt.setString(13,Utility.trim(creditCardWrapper.suppRelationship));
					pstmt.setDate(14,Utility.getDate(creditCardWrapper.suppDOB));
					pstmt.setString(15,Utility.trim(creditCardWrapper.accountNumber));
					pstmt.setString(16,Utility.trim(creditCardWrapper.accDtlsAccountType));
					pstmt.setString(17,Utility.trim(creditCardWrapper.autoPaymentAmount));
					
					pstmt.setString(18,Utility.trim(creditCardWrapper.accountCCNo));
					pstmt.setString(19,Utility.trim(creditCardWrapper.creditLmtMonthPayments));
					pstmt.setString(20,Utility.trim(creditCardWrapper.creditShield));
					pstmt.setString(21,Utility.trim(creditCardWrapper.accidentCare));
					pstmt.setString(22,Utility.trim(creditCardWrapper.dbr));
					pstmt.setString(23,Utility.trim(creditCardWrapper.rulesCreditLimited));
					pstmt.setString(24,Utility.trim(creditCardWrapper.nameOfBank));
					pstmt.setString(25,Utility.trim(creditCardWrapper.cisNumber1));
					pstmt.setString(26,Utility.trim(creditCardWrapper.caseID1));
					
					pstmt.setString(27,Utility.trim(creditCardWrapper.refNo));
					
					
	
	
					System.out.println("Credit Card Update RefNo " + creditCardWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					
					//---------update Product Details--
					
					//-------insert into RMT_Account(Product)
					AccountDetailsWrapper accountDetailsWrapper=new AccountDetailsWrapper();
					AccountDetailsHelper accountDetailsHelper=new AccountDetailsHelper();
					
					accountDetailsWrapper.refNo=creditCardWrapper.refNo;
					accountDetailsWrapper.accountNo=creditCardWrapper.accountNo;
					accountDetailsWrapper.accountIBAN=creditCardWrapper.accountIBAN;
					accountDetailsWrapper.accountType=creditCardWrapper.accountType;
					accountDetailsWrapper.currency=creditCardWrapper.currency;
					accountDetailsWrapper.statementCycle=creditCardWrapper.statementCycle;
					accountDetailsWrapper.onlineBankTrans=creditCardWrapper.onlineBankTrans;
					accountDetailsWrapper.accountTitle=creditCardWrapper.accountTitle;
					accountDetailsWrapper.nameCard=creditCardWrapper.nameCard;
					accountDetailsWrapper.favEmirate=creditCardWrapper.favEmirate;
					accountDetailsWrapper.debitCardDelvChnl=creditCardWrapper.debitCardDelvChnl;
					accountDetailsWrapper.pinMailerDelvChnl=creditCardWrapper.pinMailerDelvChnl;
					accountDetailsWrapper.transferSalary=creditCardWrapper.transferSalary;
					accountDetailsWrapper.interestRate=creditCardWrapper.interestRate;
					accountDetailsWrapper.supplCardReq=creditCardWrapper.supplCardReq;
					accountDetailsWrapper.purposeCode=creditCardWrapper.purposeCode;
					accountDetailsWrapper.businessCode=creditCardWrapper.businessCode;
					accountDetailsWrapper.insuranceCode=creditCardWrapper.insuranceCode;
					accountDetailsWrapper.campaignCode=creditCardWrapper.campaignCode;
					accountDetailsWrapper.sellerID=creditCardWrapper.sellerID;
					accountDetailsWrapper.sellerChannel=creditCardWrapper.sellerChannel;
					accountDetailsWrapper.schemeID=creditCardWrapper.schemeID;

					accountDetailsHelper.updateAccountDetails(accountDetailsWrapper);
					//----------
					
					
					
					//--

					creditCardWrapper.recordFound=true;
	
					dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
					dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Credit Card Details updated " );
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
	
	public AbstractWrapper fetchCreditCardCC(CreditCardWrapper creditCardWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmtSub=null;
		ResultSet resultSetSub = null;
		
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
				pstmt = con.prepareStatement("SELECT RefNo, CardType, BPMSubProduct, DeliveryOption, YearsInUAE, PerRefHomeCountryName, "
						+ "PerRefHomeCountryTelephone, PerRefUAEName1, PerRefUAETelephone1, PerRefUAEName2, PerRefUAETelephone2, SuppTitle, SuppName, "
						+ "SuppRelationship, SuppDOB, AccountNumber, AccDtlsAccountType, AutoPaymentAmount, AccountCCNo, CreditLmtMonthPayments, "
						+ "CreditShield, AccidentCare, DBR, RulesCreditLimited, NameOfBank, CISNumber1, CaseID1 FROM RMT_CreditCard WHERE RefNo=?");
				
			
				System.out.println("CreditCard RefNo is" + creditCardWrapper.refNo);
				
				pstmt.setString(1,creditCardWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while(resultSet.next()) 
				{
					
					creditCardWrapper = new CreditCardWrapper();

					creditCardWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("CreditCard RefNo" + creditCardWrapper.refNo);

					creditCardWrapper.cardType=Utility.trim(resultSet.getString("CardType"));
					creditCardWrapper.bpmSubProduct=Utility.trim(resultSet.getString("BPMSubProduct"));
					creditCardWrapper.deliveryOption=Utility.trim(resultSet.getString("DeliveryOption"));
					creditCardWrapper.yearsInUAE=Utility.trim(resultSet.getString("YearsInUAE"));
					creditCardWrapper.perRefHomeCountryName=Utility.trim(resultSet.getString("PerRefHomeCountryName"));
					creditCardWrapper.perRefHomeCountryTelephone=Utility.trim(resultSet.getString("PerRefHomeCountryTelephone"));
					creditCardWrapper.perRefUAEName1=Utility.trim(resultSet.getString("PerRefUAEName1"));
					creditCardWrapper.perRefUAETelephone1=Utility.trim(resultSet.getString("PerRefUAETelephone1"));
					creditCardWrapper.perRefUAEName2=Utility.trim(resultSet.getString("PerRefUAEName2"));
					creditCardWrapper.perRefUAETelephone2=Utility.trim(resultSet.getString("PerRefUAETelephone2"));
					creditCardWrapper.suppTitle=Utility.trim(resultSet.getString("SuppTitle"));
					creditCardWrapper.suppName=Utility.trim(resultSet.getString("SuppName"));
					creditCardWrapper.suppRelationship=Utility.trim(resultSet.getString("SuppRelationship"));
					creditCardWrapper.suppDOB=Utility.setDate(resultSet.getString("SuppDOB"));
					creditCardWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNumber"));
					creditCardWrapper.accDtlsAccountType=Utility.trim(resultSet.getString("AccDtlsAccountType"));
					creditCardWrapper.autoPaymentAmount=Utility.trim(resultSet.getString("AutoPaymentAmount"));
					creditCardWrapper.accountCCNo=Utility.trim(resultSet.getString("AccountCCNo"));
					creditCardWrapper.creditLmtMonthPayments=Utility.trim(resultSet.getString("CreditLmtMonthPayments"));
					creditCardWrapper.creditShield=Utility.trim(resultSet.getString("CreditShield"));
					creditCardWrapper.accidentCare=Utility.trim(resultSet.getString("AccidentCare"));
					creditCardWrapper.dbr=Utility.trim(resultSet.getString("DBR"));
					creditCardWrapper.rulesCreditLimited=Utility.trim(resultSet.getString("RulesCreditLimited"));
					creditCardWrapper.nameOfBank=Utility.trim(resultSet.getString("NameOfBank"));
					creditCardWrapper.cisNumber1=Utility.trim(resultSet.getString("CISNumber1"));
					creditCardWrapper.caseID1=Utility.trim(resultSet.getString("CaseID1"));
					
					creditCardWrapper.cardTypeValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.cardType, "CardType");
					creditCardWrapper.deliveryOptionValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.deliveryOption, "DebitCardDeliveryChnl");
					creditCardWrapper.autoPaymentAmountValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.autoPaymentAmount, "Decision");
					
					creditCardWrapper.creditShieldValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.creditShield, "Decision");
					creditCardWrapper.accidentCareValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.accidentCare, "Decision");
					creditCardWrapper.nameOfBankValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.nameOfBank, "NameOfBank");
					
					//----fetch AccountDetails-------------
					
					pstmtSub = con.prepareStatement("SELECT RefNo, AccountNo, AccountIBAN, AccountType, Currency, StatementCycle, OnlineBankTrans, "
							+ " AccOperationInstrs, AccountTitle, DebitCardRequired, NameOnCard, FavoriteEmirate, DebitCardDelvChnl, PinMailerDelvChnl, TransferSalary, ChequeBookRequired, "
							+ " OverdraftType, InterestOption, InterestRate, SupplCardReq, PurposeCode, BusinessCode, InsuranceCode, CampaignCode, SellerID, "
							+ " SellerChannel, SchemeID FROM RMT_AccountDetails WHERE RefNo=?");
				
					
					System.out.println(" RefNo is" + creditCardWrapper.refNo);
					
					pstmtSub.setString(1,creditCardWrapper.refNo.trim());
					
					resultSetSub = pstmtSub.executeQuery();
					if (resultSetSub.next()) 
					{
							
							//creditCardWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
							//System.out.println("RefNo" + creditCardWrapper.refNo);
							creditCardWrapper.accountNo=Utility.trim(resultSetSub.getString("AccountNo"));
							System.out.println("AccountNo " + creditCardWrapper.accountNo);
							creditCardWrapper.accountIBAN=Utility.trim(resultSetSub.getString("AccountIBAN"));
							creditCardWrapper.accountType=Utility.trim(resultSetSub.getString("AccountType"));
							creditCardWrapper.currency=Utility.trim(resultSetSub.getString("Currency"));
							creditCardWrapper.statementCycle=Utility.trim(resultSetSub.getString("StatementCycle"));
							creditCardWrapper.onlineBankTrans=Utility.trim(resultSetSub.getString("OnlineBankTrans"));
							creditCardWrapper.accountTitle=Utility.trim(resultSetSub.getString("AccountTitle"));
							creditCardWrapper.nameCard=Utility.trim(resultSetSub.getString("NameOnCard"));
							creditCardWrapper.favEmirate=Utility.trim(resultSetSub.getString("FavoriteEmirate"));
							creditCardWrapper.debitCardDelvChnl=Utility.trim(resultSetSub.getString("DebitCardDelvChnl"));
							creditCardWrapper.pinMailerDelvChnl=Utility.trim(resultSetSub.getString("PinMailerDelvChnl"));
							creditCardWrapper.transferSalary=Utility.trim(resultSetSub.getString("TransferSalary"));
							creditCardWrapper.interestRate=Utility.trim(resultSetSub.getString("InterestRate"));
							creditCardWrapper.supplCardReq=Utility.trim(resultSetSub.getString("SupplCardReq"));
							creditCardWrapper.purposeCode=Utility.trim(resultSetSub.getString("PurposeCode"));
							creditCardWrapper.businessCode=Utility.trim(resultSetSub.getString("BusinessCode"));
							creditCardWrapper.insuranceCode=Utility.trim(resultSetSub.getString("InsuranceCode"));
							creditCardWrapper.campaignCode=Utility.trim(resultSetSub.getString("CampaignCode"));
							creditCardWrapper.sellerID=Utility.trim(resultSetSub.getString("SellerID"));
							creditCardWrapper.sellerChannel=Utility.trim(resultSetSub.getString("SellerChannel"));
							creditCardWrapper.schemeID=Utility.trim(resultSetSub.getString("SchemeID"));
							creditCardWrapper.pinMailerDelvChnl=Utility.trim(resultSetSub.getString("PinMailerDelvChnl"));

							
							creditCardWrapper.accountTypeValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.accountType, "AccountType");
							creditCardWrapper.currencyValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.currency, "Currency");
							System.out.println("Currency Value " + creditCardWrapper.currencyValue);
							
							creditCardWrapper.statementCycleValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.statementCycle, "StatementCycle");
							creditCardWrapper.onlineBankTransValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.onlineBankTrans, "Decision");
							
							creditCardWrapper.favEmirateValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.favEmirate, "FavouriteCity");
							creditCardWrapper.debitCardDelvChnlValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.debitCardDelvChnl, "DebitCardDeliveryChnl");
							creditCardWrapper.pinMailerDelvChnlValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.pinMailerDelvChnl, "PinMailer");
							creditCardWrapper.transferSalaryValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.transferSalary, "Decision");
							
				
							//creditCardWrapper.interestRateValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.interestRate, "DECISION");
							creditCardWrapper.supplCardReqValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.supplCardReq, "Decision");
									
							creditCardWrapper.purposeCodeValue=popoverHelper.fetchPopoverDesc(creditCardWrapper.purposeCode, "PurposeCode");

							
							

	
					}
					if(resultSetSub !=null) resultSetSub.close();
					pstmtSub.close();
					//-----fetch AccountDetails end---------
					
					
					
					
					
					creditCardWrapper.recordFound=true;
					System.out.println(" CreditCard Fetched ");
					
					
					
	
					vector.addElement(creditCardWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.creditCardWrapper = new CreditCardWrapper[1];
				dataArrayWrapper.creditCardWrapper[0]= creditCardWrapper;
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
