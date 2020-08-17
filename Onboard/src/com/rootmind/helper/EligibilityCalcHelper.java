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

public class EligibilityCalcHelper extends Helper {
	
	public AbstractWrapper insertEligibilityCalc(EligibilityCalcWrapper eligibilityCalcWrapper)throws Exception {

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
			
			con = getConnection();
			
			sql=" INSERT INTO RMT_EligibilityCalc(RefNo, Nationality, LengRsMashreqBank, PrivateBankCust, MashreqGoldCust, IncomePermonth, "
					+ "SourceIncome, TransSalaryMashreqBank, SearchCompany, Company, CompanyType, Profession, EmploymentStatus, "
					+ "CardsFlag, PersonalLoan, HomeLoan, CarLoan, RevolvingOverdraft) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,Utility.trim(eligibilityCalcWrapper.refNo));
			pstmt.setString(2,Utility.trim(eligibilityCalcWrapper.nationality));
			pstmt.setString(3,Utility.trim(eligibilityCalcWrapper.lengRsMashreqBank));
			pstmt.setString(4,Utility.trim(eligibilityCalcWrapper.privateBankCust));
			pstmt.setString(5,Utility.trim(eligibilityCalcWrapper.mashreqGoldCust));
			pstmt.setString(6,Utility.trim(eligibilityCalcWrapper.incomePermonth));
			pstmt.setString(7,Utility.trim(eligibilityCalcWrapper.sourceIncome));
			pstmt.setString(8,Utility.trim(eligibilityCalcWrapper.transSalaryMashreqBank));
			pstmt.setString(9,Utility.trim(eligibilityCalcWrapper.searchCompany));
			pstmt.setString(10,Utility.trim(eligibilityCalcWrapper.company));
			pstmt.setString(11,Utility.trim(eligibilityCalcWrapper.companyType));
			pstmt.setString(12,Utility.trim(eligibilityCalcWrapper.profession));
			pstmt.setString(13,Utility.trim(eligibilityCalcWrapper.employmentStatus));
			pstmt.setString(14,Utility.trim(eligibilityCalcWrapper.cardsFlag));
			pstmt.setString(15,Utility.trim(eligibilityCalcWrapper.personalLoan));
			pstmt.setString(16,Utility.trim(eligibilityCalcWrapper.homeLoan));
			pstmt.setString(17,Utility.trim(eligibilityCalcWrapper.carLoan));
			pstmt.setString(18,Utility.trim(eligibilityCalcWrapper.revolvingOverdraft));
		
	
			pstmt.executeUpdate();
			pstmt.close();
			
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_Eligibility(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(eligibilityCalcWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(eligibilityCalcWrapper.makerDate));
			pstmt.setString(3,Utility.trim(eligibilityCalcWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(eligibilityCalcWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
	
			//------------------
			

			eligibilityCalcWrapper.recordFound=true;
			
			
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

		return eligibilityCalcWrapper;
	}
	
	
	public AbstractWrapper fetchEligibilityCalc(EligibilityCalcWrapper eligibilityCalcWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//EligibilityCalcWrapper eligibilityCalcWrapper=new EligibilityCalcWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("Nationality:"+ eligibilityCalcWrapper.nationality);
	
	
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, Nationality, LengRsMashreqBank, PrivateBankCust, MashreqGoldCust, "
					+ " IncomePermonth,SourceIncome, TransSalaryMashreqBank, SearchCompany, Company, CompanyType, Profession, EmploymentStatus, "
					+ "CardsFlag, PersonalLoan, HomeLoan, CarLoan, RevolvingOverdraft FROM RMT_EligibilityCalc");
		
			
			//pstmt.setString(1,eligibilityCalcWrapper.trim());
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				eligibilityCalcWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + eligibilityCalcWrapper.refNo);

				eligibilityCalcWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
				System.out.println("Nationality " + eligibilityCalcWrapper.nationality);
				
				eligibilityCalcWrapper.lengRsMashreqBank=Utility.trim(resultSet.getString("LengRsMashreqBank"));
				eligibilityCalcWrapper.privateBankCust=Utility.trim(resultSet.getString("PrivateBankCust"));
				eligibilityCalcWrapper.mashreqGoldCust=Utility.trim(resultSet.getString("MashreqGoldCust"));
				eligibilityCalcWrapper.incomePermonth=Utility.trim(resultSet.getString("IncomePermonth"));
				eligibilityCalcWrapper.sourceIncome=Utility.trim(resultSet.getString("SourceIncome"));
				eligibilityCalcWrapper.transSalaryMashreqBank=Utility.trim(resultSet.getString("TransSalaryMashreqBank"));
				eligibilityCalcWrapper.searchCompany=Utility.trim(resultSet.getString("SearchCompany"));
				eligibilityCalcWrapper.company=Utility.trim(resultSet.getString("Company"));
				eligibilityCalcWrapper.companyType=Utility.trim(resultSet.getString("CompanyType"));
				eligibilityCalcWrapper.profession=Utility.trim(resultSet.getString("Profession"));
				eligibilityCalcWrapper.employmentStatus=Utility.trim(resultSet.getString("EmploymentStatus"));
				eligibilityCalcWrapper.cardsFlag=Utility.trim(resultSet.getString("CardsFlag"));
				eligibilityCalcWrapper.personalLoan=Utility.trim(resultSet.getString("PersonalLoan"));
				eligibilityCalcWrapper.homeLoan=Utility.trim(resultSet.getString("HomeLoan"));
				eligibilityCalcWrapper.carLoan=Utility.trim(resultSet.getString("CarLoan"));
				eligibilityCalcWrapper.revolvingOverdraft=Utility.trim(resultSet.getString("RevolvingOverdraft"));
				
			
			
				eligibilityCalcWrapper.recordFound=true;
				
				System.out.println("EligibilityCalcWrapper");

				vector.addElement(eligibilityCalcWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.eligibilityCalcWrapper = new EligibilityCalcWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.eligibilityCalcWrapper);
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
	
	public AbstractWrapper updatePassportDetails(EligibilityCalcWrapper eligibilityCalcWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println(" Update EligibilityCalcWrapper");
	
		try {
			con = getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_EligibilityCalc SET Nationality=?, LengRsMashreqBank=?, "
					+ "PrivateBankCust=?, MashreqGoldCust=?, IncomePermonth=?,SourceIncome=?, TransSalaryMashreqBank=?, SearchCompany=?, "
					+ " Company=?, CompanyType=?, Profession=?, EmploymentStatus=?, CardsFlag=?, PersonalLoan=?, HomeLoan=?, "
					+ " CarLoan=?, RevolvingOverdraft=? where RefNo=? ");
			
			
		
			
			pstmt.setString(1,Utility.trim(eligibilityCalcWrapper.nationality));
			pstmt.setString(2,Utility.trim(eligibilityCalcWrapper.lengRsMashreqBank));
			pstmt.setString(3,Utility.trim(eligibilityCalcWrapper.privateBankCust));
			pstmt.setString(4,Utility.trim(eligibilityCalcWrapper.mashreqGoldCust));
			pstmt.setString(5,Utility.trim(eligibilityCalcWrapper.incomePermonth));
			pstmt.setString(6,Utility.trim(eligibilityCalcWrapper.sourceIncome));
			pstmt.setString(7,Utility.trim(eligibilityCalcWrapper.transSalaryMashreqBank));
			pstmt.setString(8,Utility.trim(eligibilityCalcWrapper.searchCompany));
			pstmt.setString(9,Utility.trim(eligibilityCalcWrapper.company));
			pstmt.setString(10,Utility.trim(eligibilityCalcWrapper.companyType));
			pstmt.setString(11,Utility.trim(eligibilityCalcWrapper.profession));
			pstmt.setString(12,Utility.trim(eligibilityCalcWrapper.employmentStatus));
			pstmt.setString(13,Utility.trim(eligibilityCalcWrapper.cardsFlag));
			pstmt.setString(14,Utility.trim(eligibilityCalcWrapper.personalLoan));
			pstmt.setString(15,Utility.trim(eligibilityCalcWrapper.homeLoan));
			pstmt.setString(16,Utility.trim(eligibilityCalcWrapper.carLoan));
			pstmt.setString(17,Utility.trim(eligibilityCalcWrapper.revolvingOverdraft));
		
			pstmt.setString(18,Utility.trim(eligibilityCalcWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();
			
		

			
			
			eligibilityCalcWrapper.recordFound=true;
			
			//dataArrayWrapper.eligibilityCalcWrapper=new EligibilityCalcWrapper[1];
			//dataArrayWrapper.eligibilityCalcWrapper[0]=eligibilityCalcWrapper;
			
			System.out.println("eligibilityCalcWrapper updated " );
			
			
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
