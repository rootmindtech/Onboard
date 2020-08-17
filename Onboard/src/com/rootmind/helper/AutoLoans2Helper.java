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


public class AutoLoans2Helper extends Helper {
	
	public AbstractWrapper updateAutoLoans2(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("update AutoLoans2");
		
		PreparedStatement pstmt=null;
	
		try {
			
				con = getConnection();
				
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AutoLoans WHERE RefNo=?");
				
				
				System.out.println("AUTOLOANS RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertAutoLoans2(autoLoansWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
				

					System.out.println("AutoLoans2 RefNo is " + autoLoansWrapper.refNo);
	
					pstmt = con.prepareStatement("UPDATE RMT_AutoLoans SET YearOfManufacture=?, Color=?, EngineNo=?,ChassisNo=?, "
							+ "RegistrationEmirate=?, PriceOfVehicle=?, VehicleInsurance=?, OtherCharges=?, TotalCost=?, DownPayment=?, "
							+ " TradeinValue=?, TotalFinancedAmount=?, InterestRate=?, RepaymentAmount=?, ProcessingFee=?, Tenor=?, EMI=?, "
							+ "InsuranceCompanyName=?, DueDate=?, RenewalDate=?,PolicyNo=? where RefNo=?");
		
						
					
							
						pstmt.setString(1,Utility.trim(autoLoansWrapper.yearOfManufacture));
						pstmt.setString(2,Utility.trim(autoLoansWrapper.color));
						pstmt.setString(3,Utility.trim(autoLoansWrapper.engineNo));
						pstmt.setString(4,Utility.trim(autoLoansWrapper.chassisNo));
						pstmt.setString(5,Utility.trim(autoLoansWrapper.registrationEmirate));
						pstmt.setString(6,Utility.trim(autoLoansWrapper.priceOfVehicle));
						pstmt.setString(7,Utility.trim(autoLoansWrapper.vehicleInsurance));
						pstmt.setString(8,Utility.trim(autoLoansWrapper.otherCharges));
						pstmt.setString(9,Utility.trim(autoLoansWrapper.totalCost));
						pstmt.setString(10,Utility.trim(autoLoansWrapper.downPayment));
						pstmt.setString(11,Utility.trim(autoLoansWrapper.tradeinValue));
						pstmt.setString(12,Utility.trim(autoLoansWrapper.totalFinancedAmount));
						pstmt.setString(13,Utility.trim(autoLoansWrapper.interestRate));
						pstmt.setString(14,Utility.trim(autoLoansWrapper.repaymentAmount));
						pstmt.setString(15,Utility.trim(autoLoansWrapper.processingFee));
						pstmt.setString(16,Utility.trim(autoLoansWrapper.tenor));
						pstmt.setString(17,Utility.trim(autoLoansWrapper.emi));
						pstmt.setString(18,Utility.trim(autoLoansWrapper.insuranceCompanyName));
						pstmt.setDate(19,Utility.getDate(autoLoansWrapper.dueDate));
						pstmt.setDate(20,Utility.getDate(autoLoansWrapper.renewalDate));
						pstmt.setString(21,Utility.trim(autoLoansWrapper.policyNo));
						pstmt.setString(22,Utility.trim(autoLoansWrapper.refNo));
						
		
						
		
						System.out.println("UpdateD AutoLoans2 RefNo " + autoLoansWrapper.refNo);
		
		
						pstmt.executeUpdate();
		
						pstmt.close();
						
						//---------update PathStatus
						PathStatusHelper pathStatusHelper=new PathStatusHelper();
						pathStatusHelper.updatePathStatus(Utility.trim(autoLoansWrapper.refNo),"AUTOLOAN2");
						//--
	
		
		
						autoLoansWrapper.recordFound=true;
		
						dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
						dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
						dataArrayWrapper.recordFound=true;
						
						System.out.println("AutoLoans 2 updated " );
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
	
	
	public AbstractWrapper insertAutoLoans2(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("insert AutoLoans2");
		
		PreparedStatement pstmt=null;
	
		try {
			
			con = getConnection();
				

					System.out.println("AutoLoans2 RefNo is " + autoLoansWrapper.refNo);
	
					pstmt = con.prepareStatement("INSERT INTO RMT_AutoLoans(RefNo,YearOfManufacture, Color, EngineNo, ChassisNo, "
							+ "RegistrationEmirate, PriceOfVehicle, VehicleInsurance, OtherCharges, TotalCost, DownPayment, "
							+ " TradeinValue, TotalFinancedAmount, InterestRate, RepaymentAmount, ProcessingFee, Tenor, EMI, "
							+ "InsuranceCompanyName, DueDate, RenewalDate,PolicyNo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
						
					
						pstmt.setString(1,Utility.trim(autoLoansWrapper.refNo));
						pstmt.setString(2,Utility.trim(autoLoansWrapper.yearOfManufacture));
						pstmt.setString(3,Utility.trim(autoLoansWrapper.color));
						pstmt.setString(4,Utility.trim(autoLoansWrapper.engineNo));
						pstmt.setString(5,Utility.trim(autoLoansWrapper.chassisNo));
						pstmt.setString(6,Utility.trim(autoLoansWrapper.registrationEmirate));
						pstmt.setString(7,Utility.trim(autoLoansWrapper.priceOfVehicle));
						pstmt.setString(8,Utility.trim(autoLoansWrapper.vehicleInsurance));
						pstmt.setString(9,Utility.trim(autoLoansWrapper.otherCharges));
						pstmt.setString(10,Utility.trim(autoLoansWrapper.totalCost));
						pstmt.setString(11,Utility.trim(autoLoansWrapper.downPayment));
						pstmt.setString(12,Utility.trim(autoLoansWrapper.tradeinValue));
						pstmt.setString(13,Utility.trim(autoLoansWrapper.totalFinancedAmount));
						pstmt.setString(14,Utility.trim(autoLoansWrapper.interestRate));
						pstmt.setString(15,Utility.trim(autoLoansWrapper.repaymentAmount));
						pstmt.setString(16,Utility.trim(autoLoansWrapper.processingFee));
						pstmt.setString(17,Utility.trim(autoLoansWrapper.tenor));
						pstmt.setString(18,Utility.trim(autoLoansWrapper.emi));
						pstmt.setString(19,Utility.trim(autoLoansWrapper.insuranceCompanyName));
						pstmt.setDate(20,Utility.getDate(autoLoansWrapper.dueDate));
						pstmt.setDate(21,Utility.getDate(autoLoansWrapper.renewalDate));
						pstmt.setString(22,Utility.trim(autoLoansWrapper.policyNo));
						
						
		
						
		
						System.out.println("INSERT AutoLoans2 RefNo " + autoLoansWrapper.refNo);
		
		
						pstmt.executeUpdate();
		
						pstmt.close();
						
						//---------update PathStatus
						PathStatusHelper pathStatusHelper=new PathStatusHelper();
						pathStatusHelper.updatePathStatus(Utility.trim(autoLoansWrapper.refNo),"AUTOLOAN2");
						//--
	
		
		
						autoLoansWrapper.recordFound=true;
		
						dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
						dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
						dataArrayWrapper.recordFound=true;
						
						System.out.println("AutoLoans 2 inserted " );
				
					
			
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
	public AbstractWrapper fetchAutoLoans2(AutoLoansWrapper autoLoansWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT YearOfManufacture,Color,EngineNo,ChassisNo,RegistrationEmirate,PriceOfVehicle, "
						+ "VehicleInsurance, OtherCharges, TotalCost, DownPayment, TradeinValue, TotalFinancedAmount, InterestRate, RepaymentAmount, "
						+ "ProcessingFee, Tenor, EMI,InsuranceCompanyName, DueDate, RenewalDate, PolicyNo FROM RMT_AutoLoans WHERE RefNo=?");
				
			
				System.out.println("AutoLoans2 RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					autoLoansWrapper = new AutoLoansWrapper();
					autoLoansWrapper.yearOfManufacture=Utility.trim(resultSet.getString("YearOfManufacture"));
					autoLoansWrapper.color=Utility.trim(resultSet.getString("Color"));
					autoLoansWrapper.engineNo=Utility.trim(resultSet.getString("EngineNo"));
					autoLoansWrapper.chassisNo=Utility.trim(resultSet.getString("ChassisNo"));
					autoLoansWrapper.registrationEmirate=Utility.trim(resultSet.getString("RegistrationEmirate"));	
					autoLoansWrapper.priceOfVehicle=Utility.trim(resultSet.getString("PriceOfVehicle"));
					autoLoansWrapper.vehicleInsurance=Utility.trim(resultSet.getString("VehicleInsurance"));
					autoLoansWrapper.otherCharges=Utility.trim(resultSet.getString("OtherCharges"));
					autoLoansWrapper.totalCost=Utility.trim(resultSet.getString("TotalCost"));
					autoLoansWrapper.downPayment=Utility.trim(resultSet.getString("DownPayment"));
					autoLoansWrapper.tradeinValue=Utility.trim(resultSet.getString("TradeinValue"));
					autoLoansWrapper.totalFinancedAmount=Utility.trim(resultSet.getString("TotalFinancedAmount"));
					autoLoansWrapper.interestRate=Utility.trim(resultSet.getString("InterestRate"));
					autoLoansWrapper.repaymentAmount=Utility.trim(resultSet.getString("RepaymentAmount"));
					autoLoansWrapper.processingFee=Utility.trim(resultSet.getString("ProcessingFee"));
					autoLoansWrapper.tenor=Utility.trim(resultSet.getString("Tenor"));
					autoLoansWrapper.emi=Utility.trim(resultSet.getString("EMI"));
					autoLoansWrapper.insuranceCompanyName=Utility.trim(resultSet.getString("InsuranceCompanyName"));
					autoLoansWrapper.dueDate=Utility.setDate(resultSet.getString("DueDate"));
					autoLoansWrapper.renewalDate=Utility.setDate(resultSet.getString("RenewalDate"));
					autoLoansWrapper.policyNo=Utility.trim(resultSet.getString("PolicyNo"));
					
					
					autoLoansWrapper.colorValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.color,"COLOR");
					autoLoansWrapper.registrationEmirateValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.registrationEmirate,"FavouriteCity");
	
					autoLoansWrapper.recordFound=true;
					System.out.println("AutoLoans Wrapper");
	
					vector.addElement(autoLoansWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.autoLoansWrapper = new AutoLoansWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.autoLoansWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.autoLoansWrapper = new AutoLoansWrapper[1];
				dataArrayWrapper.autoLoansWrapper[0]= autoLoansWrapper;
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
	
	public AbstractWrapper updateAutoLoans3(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();

		System.out.println("update Auto Loans3");
		
		PreparedStatement pstmt=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AutoLoans WHERE RefNo=?");
				
				
				System.out.println("AUTOLOANS RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertAutoLoans3(autoLoansWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
				
	
					System.out.println("AutoLoans 3 RefNo is " + autoLoansWrapper.refNo);
	
					pstmt = con.prepareStatement("UPDATE RMT_AutoLoans SET GuarantorName=?, GuarantorAddress=?, GuarantorTelephone=?, "
						+ "GuarantorMonthlyIncome=?, GuarantorBankName=?, GuarantorAccountNo=?, PersonalRefName1=?, "
						+ "PersonalRefAddress1=?, PersonalRefTelephone1=?,PersonalRefName2=?, PersonalRefAddress2=?, PersonalRefTelephone2=?, "
						+ "CaseOutcome=?  where RefNo=?");
	
	
						pstmt.setString(1,Utility.trim(autoLoansWrapper.guarantorName));
						pstmt.setString(2,Utility.trim(autoLoansWrapper.guarantorAddress));
						pstmt.setString(3,Utility.trim(autoLoansWrapper.guarantorTelephone));
						pstmt.setString(4,Utility.trim(autoLoansWrapper.guarantorMonthlyIncome));
						pstmt.setString(5,Utility.trim(autoLoansWrapper.guarantorBankName));
						pstmt.setString(6,Utility.trim(autoLoansWrapper.guarantorAccountNo));
						pstmt.setString(7,Utility.trim(autoLoansWrapper.personalRefName1));
						pstmt.setString(8,Utility.trim(autoLoansWrapper.personalRefAddress1));
						pstmt.setString(9,Utility.trim(autoLoansWrapper.personalRefTelephone1));
						pstmt.setString(10,Utility.trim(autoLoansWrapper.personalRefName2));
						pstmt.setString(11,Utility.trim(autoLoansWrapper.personalRefAddress2));
						pstmt.setString(12,Utility.trim(autoLoansWrapper.personalRefTelephone2));
						pstmt.setString(13,Utility.trim(autoLoansWrapper.caseOutcome));
						
						pstmt.setString(14,Utility.trim(autoLoansWrapper.refNo));
		
						
	
						pstmt.executeUpdate();
		
						pstmt.close();
	
						autoLoansWrapper.recordFound=true;
		
						dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
						dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
						dataArrayWrapper.recordFound=true;
						
						System.out.println("Auto Loans 3 updated " );
						
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
	
	public AbstractWrapper insertAutoLoans3(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();

		System.out.println("insert AutoLoans3");
		
		PreparedStatement pstmt=null;
	
		try {
			
				con = getConnection();

				System.out.println("AutoLoans 3 RefNo is " + autoLoansWrapper.refNo);

				pstmt = con.prepareStatement("INSERT INTO RMT_AutoLoans(RefNo, GuarantorName, GuarantorAddress, GuarantorTelephone, "
					+ "GuarantorMonthlyIncome, GuarantorBankName, GuarantorAccountNo, PersonalRefName1, "
					+ "PersonalRefAddress1, PersonalRefTelephone1,PersonalRefName2, PersonalRefAddress2, PersonalRefTelephone2, "
					+ "CaseOutcome) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					pstmt.setString(1,Utility.trim(autoLoansWrapper.refNo));
					pstmt.setString(2,Utility.trim(autoLoansWrapper.guarantorName));
					pstmt.setString(3,Utility.trim(autoLoansWrapper.guarantorAddress));
					pstmt.setString(4,Utility.trim(autoLoansWrapper.guarantorTelephone));
					pstmt.setString(5,Utility.trim(autoLoansWrapper.guarantorMonthlyIncome));
					pstmt.setString(6,Utility.trim(autoLoansWrapper.guarantorBankName));
					pstmt.setString(7,Utility.trim(autoLoansWrapper.guarantorAccountNo));
					pstmt.setString(8,Utility.trim(autoLoansWrapper.personalRefName1));
					pstmt.setString(9,Utility.trim(autoLoansWrapper.personalRefAddress1));
					pstmt.setString(10,Utility.trim(autoLoansWrapper.personalRefTelephone1));
					pstmt.setString(11,Utility.trim(autoLoansWrapper.personalRefName2));
					pstmt.setString(12,Utility.trim(autoLoansWrapper.personalRefAddress2));
					pstmt.setString(13,Utility.trim(autoLoansWrapper.personalRefTelephone2));
					pstmt.setString(14,Utility.trim(autoLoansWrapper.caseOutcome));
					
					pstmt.executeUpdate();
	
					pstmt.close();

					autoLoansWrapper.recordFound=true;
	
					dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
					dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
					dataArrayWrapper.recordFound=true;
					
					System.out.println("AutoLoans 3 insert " );
			
			
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
	
	
	
	
	public AbstractWrapper fetchAutoLoans3(AutoLoansWrapper autoLoansWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT GuarantorName, GuarantorAddress, GuarantorTelephone, GuarantorMonthlyIncome, "
						+ " GuarantorBankName, GuarantorAccountNo, PersonalRefName1, PersonalRefAddress1, PersonalRefTelephone1, PersonalRefName2, "
						+ "PersonalRefAddress2, PersonalRefTelephone2, CaseOutcome FROM RMT_AutoLoans WHERE RefNo=?");
				
			
				System.out.println("AutoLoans3 RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					autoLoansWrapper = new AutoLoansWrapper();

					
					
					autoLoansWrapper.guarantorName=Utility.trim(resultSet.getString("GuarantorName"));
					autoLoansWrapper.guarantorAddress=Utility.trim(resultSet.getString("GuarantorAddress"));
					autoLoansWrapper.guarantorTelephone=Utility.trim(resultSet.getString("GuarantorTelephone"));
					autoLoansWrapper.guarantorMonthlyIncome=Utility.trim(resultSet.getString("GuarantorMonthlyIncome"));
					autoLoansWrapper.guarantorBankName=Utility.trim(resultSet.getString("GuarantorBankName"));
					autoLoansWrapper.guarantorAccountNo=Utility.trim(resultSet.getString("GuarantorAccountNo"));
					autoLoansWrapper.personalRefName1=Utility.trim(resultSet.getString("PersonalRefName1"));
					autoLoansWrapper.personalRefAddress1=Utility.trim(resultSet.getString("PersonalRefAddress1"));
					autoLoansWrapper.personalRefTelephone1=Utility.trim(resultSet.getString("PersonalRefTelephone1"));
					autoLoansWrapper.personalRefName2=Utility.trim(resultSet.getString("PersonalRefName2"));
					autoLoansWrapper.personalRefAddress2=Utility.trim(resultSet.getString("PersonalRefAddress2"));
					autoLoansWrapper.personalRefTelephone2=Utility.trim(resultSet.getString("PersonalRefTelephone2"));
					autoLoansWrapper.caseOutcome=Utility.trim(resultSet.getString("CaseOutcome"));
	
					
					autoLoansWrapper.recordFound=true;
					System.out.println("AutoLoans Wrapper");
	
					vector.addElement(autoLoansWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.autoLoansWrapper = new AutoLoansWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.autoLoansWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.autoLoansWrapper = new AutoLoansWrapper[1];
				dataArrayWrapper.autoLoansWrapper[0]= autoLoansWrapper;
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
	
	
/*	public AutoLoansWrapper fetchAutoLoans2(AutoLoansWrapper autoLoansWrapper,String refNo)throws Exception {



		try 
		{

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchAutoLoans2(autoLoansWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				autoLoansWrapper = dataArrayWrapper.autoLoansWrapper[0];
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

		return autoLoansWrapper;

	}*/
	
	
	

	
}
