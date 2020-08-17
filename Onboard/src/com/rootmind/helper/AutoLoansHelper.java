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

public class AutoLoansHelper extends Helper {
	
	public AbstractWrapper insertAutoLoans(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		//String sql=null;
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		PreparedStatement pstmt=null;

		try {
				con = getConnection();
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AutoLoans WHERE RefNo=?");
				
				
				System.out.println("AUTOLOANS RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updateAutoLoans(autoLoansWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
				
					
					
					pstmt = con.prepareStatement(" INSERT INTO RMT_AutoLoans(RefNo, SalesOfficerName, Code, CISNo, NoYearsUAE, NameOfSponsor, "
							+ " AccountNumber, YCISNo, BankName, Branch, "
								+ "SalaryTransfer, MonthlyPayments, DealerOrsellerName, Telephone, Fax, MakeOrModel, Mileage, NewOrUsed, NormalOrAlloy, "
								+ "Transmission, Sunroof, DealerSalesRepName) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	
					pstmt.setString(1,Utility.trim(autoLoansWrapper.refNo));
					pstmt.setString(2,Utility.trim(autoLoansWrapper.salesOfficerName));
					pstmt.setString(3,Utility.trim(autoLoansWrapper.code));
					pstmt.setString(4,Utility.trim(autoLoansWrapper.cisNo));
					pstmt.setString(5,Utility.trim(autoLoansWrapper.noYearsUAE));
					pstmt.setString(6,Utility.trim(autoLoansWrapper.nameOfSponsor));
					pstmt.setString(7,Utility.trim(autoLoansWrapper.accountNumber));
					pstmt.setString(8,Utility.trim(autoLoansWrapper.ycisNo));
					pstmt.setString(9,Utility.trim(autoLoansWrapper.bankName));
					pstmt.setString(10,Utility.trim(autoLoansWrapper.branch));
					pstmt.setString(11,Utility.trim(autoLoansWrapper.salaryTransfer));
					pstmt.setString(12,Utility.trim(autoLoansWrapper.monthlyPayments));
					pstmt.setString(13,Utility.trim(autoLoansWrapper.dealerOrsellerName));
					pstmt.setString(14,Utility.trim(autoLoansWrapper.telephone));
					pstmt.setString(15,Utility.trim(autoLoansWrapper.fax));
					pstmt.setString(16,Utility.trim(autoLoansWrapper.makeOrModel));
					pstmt.setString(17,Utility.trim(autoLoansWrapper.mileage));
					pstmt.setString(18,Utility.trim(autoLoansWrapper.neworUsed));
					pstmt.setString(19,Utility.trim(autoLoansWrapper.normalOrAlloy));
					pstmt.setString(20,Utility.trim(autoLoansWrapper.transmission));
					pstmt.setString(21,Utility.trim(autoLoansWrapper.sunroof));
					pstmt.setString(22,Utility.trim(autoLoansWrapper.dealerSalesRepName));
					
					pstmt.executeUpdate();
					pstmt.close();
					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					pathStatusHelper.updatePathStatus(Utility.trim(autoLoansWrapper.refNo),"AUTOLOAN");
					//--
	
					autoLoansWrapper.recordFound=true;
					
					dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
					dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
					dataArrayWrapper.recordFound=true;
					
					System.out.println("Auto Loans Inserted");
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

	
	
	public AbstractWrapper fetchAutoLoans(AutoLoansWrapper autoLoansWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, SalesOfficerName, Code, CISNo, NoYearsUAE, NameOfSponsor, AccountNumber, "
						+ "YCISNo, BankName, Branch, SalaryTransfer, MonthlyPayments, DealerOrsellerName, Telephone, Fax, MakeOrModel, Mileage, NewOrUsed, NormalOrAlloy, "
						+ "Transmission, Sunroof, DealerSalesRepName FROM RMT_AutoLoans WHERE RefNo=?");
				
				
				System.out.println("AutoLoansWrapper RefNo is " + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					autoLoansWrapper = new AutoLoansWrapper();
					
					autoLoansWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo " + autoLoansWrapper.refNo);
					autoLoansWrapper.salesOfficerName=Utility.trim(resultSet.getString("SalesOfficerName"));
					autoLoansWrapper.code=Utility.trim(resultSet.getString("Code"));
					autoLoansWrapper.cisNo=Utility.trim(resultSet.getString("CISNo"));
					autoLoansWrapper.noYearsUAE=Utility.trim(resultSet.getString("NoYearsUAE"));
					autoLoansWrapper.nameOfSponsor=Utility.trim(resultSet.getString("NameOfSponsor"));
					autoLoansWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNumber"));
					autoLoansWrapper.ycisNo=Utility.trim(resultSet.getString("YCISNo"));
					autoLoansWrapper.bankName=Utility.trim(resultSet.getString("BankName"));
					autoLoansWrapper.branch=Utility.trim(resultSet.getString("Branch"));
					autoLoansWrapper.salaryTransfer=Utility.trim(resultSet.getString("SalaryTransfer"));
					autoLoansWrapper.monthlyPayments=Utility.trim(resultSet.getString("MonthlyPayments"));
					autoLoansWrapper.dealerOrsellerName=Utility.trim(resultSet.getString("DealerOrsellerName"));
					autoLoansWrapper.telephone=Utility.trim(resultSet.getString("Telephone"));
					autoLoansWrapper.fax=Utility.trim(resultSet.getString("Fax"));
					autoLoansWrapper.makeOrModel=Utility.trim(resultSet.getString("MakeOrModel"));
					autoLoansWrapper.mileage=Utility.trim(resultSet.getString("Mileage"));
					autoLoansWrapper.neworUsed=Utility.trim(resultSet.getString("NewOrUsed"));
					autoLoansWrapper.normalOrAlloy=Utility.trim(resultSet.getString("NormalOrAlloy"));
					autoLoansWrapper.transmission=Utility.trim(resultSet.getString("Transmission"));
					autoLoansWrapper.sunroof=Utility.trim(resultSet.getString("Sunroof"));
					autoLoansWrapper.dealerSalesRepName=Utility.trim(resultSet.getString("DealerSalesRepName"));
					
					autoLoansWrapper.salaryTransferValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.salaryTransfer,"Decision");
					autoLoansWrapper.neworUsedValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.neworUsed,"NEWORUSED");
					autoLoansWrapper.normalOrAlloyValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.normalOrAlloy,"NORMALORALLOY");
					autoLoansWrapper.transmissionValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.transmission,"TRANSMISSION");
					autoLoansWrapper.sunroofValue=popoverHelper.fetchPopoverDesc(autoLoansWrapper.sunroof,"Decision");
					
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
	
	
	
	public AutoLoansWrapper fetchAutoLoans(AutoLoansWrapper autoLoansWrapper,String refNo)throws Exception {



		try 
		{

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchAutoLoans(autoLoansWrapper);	
			
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

	}
	
	
	public AbstractWrapper updateAutoLoans(AutoLoansWrapper autoLoansWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Auto Loans");
		
		PreparedStatement pstmt=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AutoLoans WHERE RefNo=?");
				
			
				System.out.println("AutoLoans RefNo is" + autoLoansWrapper.refNo);
				
				pstmt.setString(1,autoLoansWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertAutoLoans(autoLoansWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_AutoLoans SET SalesOfficerName=?, Code=?, CISNo=?, NoYearsUAE=?, NameOfSponsor=?, AccountNumber=?, "
							+ "YCISNo=?,BankName=?, Branch=?, SalaryTransfer=?, MonthlyPayments=?, DealerOrsellerName=?, Telephone=?, Fax=?, MakeOrModel=?, Mileage=?, "
							+ "NewOrUsed=?, NormalOrAlloy=?, Transmission=?, Sunroof=?, DealerSalesRepName=? where RefNo=?");
	
					
					pstmt.setString(1,Utility.trim(autoLoansWrapper.salesOfficerName));
					System.out.println("Update SalesOfficerName " + autoLoansWrapper.salesOfficerName);
					pstmt.setString(2,Utility.trim(autoLoansWrapper.code));
					pstmt.setString(3,Utility.trim(autoLoansWrapper.cisNo));
					pstmt.setString(4,Utility.trim(autoLoansWrapper.noYearsUAE));
					pstmt.setString(5,Utility.trim(autoLoansWrapper.nameOfSponsor));
					pstmt.setString(6,Utility.trim(autoLoansWrapper.accountNumber));	
					pstmt.setString(7,Utility.trim(autoLoansWrapper.ycisNo));
					pstmt.setString(8,Utility.trim(autoLoansWrapper.bankName));
					pstmt.setString(9,Utility.trim(autoLoansWrapper.branch));
					pstmt.setString(10,Utility.trim(autoLoansWrapper.salaryTransfer));
					pstmt.setString(11,Utility.trim(autoLoansWrapper.monthlyPayments));
					pstmt.setString(12,Utility.trim(autoLoansWrapper.dealerOrsellerName));
					pstmt.setString(13,Utility.trim(autoLoansWrapper.telephone));
					pstmt.setString(14,Utility.trim(autoLoansWrapper.fax));
					pstmt.setString(15,Utility.trim(autoLoansWrapper.makeOrModel));
					pstmt.setString(16,Utility.trim(autoLoansWrapper.mileage));
					pstmt.setString(17,Utility.trim(autoLoansWrapper.neworUsed));
					pstmt.setString(18,Utility.trim(autoLoansWrapper.normalOrAlloy));
					pstmt.setString(19,Utility.trim(autoLoansWrapper.transmission));
					pstmt.setString(20,Utility.trim(autoLoansWrapper.sunroof));
					pstmt.setString(21,Utility.trim(autoLoansWrapper.dealerSalesRepName));
					
					
					pstmt.setString(22,Utility.trim(autoLoansWrapper.refNo));
	
					System.out.println("Update AutoLoans RefNo " + autoLoansWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
	
	
	
					autoLoansWrapper.recordFound=true;
	
					dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
					dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Auto Loans updated " );
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
	

	
}
