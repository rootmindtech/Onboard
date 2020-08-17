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

public class RevolvingOverdraftHelper extends Helper {
	
	public AbstractWrapper insertRoD(RevolvingOverdraftWrapper revolvingOverdraftWrapper)throws Exception {

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
				
				sql="INSERT INTO RMT_Revolvingoverdraft(RefNo,	RoDSalaryMultiple, RoDOverdraftAmount, RoDRate, CISNo, AccountNo, SecurityChequeNo, "
						+ "OffTelephone, ResTelephone, MobileNo, CampaignCode, SalaryMultiple, OverdraftAmount, Rate, RefName1, RefContactNo1, "
						+ " RefName2, RefContactNo2 ) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				

				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,Utility.trim(revolvingOverdraftWrapper.refNo ));
				pstmt.setString(2,Utility.trim(revolvingOverdraftWrapper.rodSalaryMultiple ));
				pstmt.setString(3,Utility.trim(revolvingOverdraftWrapper.rodOverdraftAmount ));
				pstmt.setString(4,Utility.trim(revolvingOverdraftWrapper.rodRate ));
				pstmt.setString(5,Utility.trim(revolvingOverdraftWrapper.cisNo ));
				pstmt.setString(6,Utility.trim(revolvingOverdraftWrapper.accountNo ));
				pstmt.setString(7,Utility.trim(revolvingOverdraftWrapper.securityChequeNo ));
				pstmt.setString(8,Utility.trim(revolvingOverdraftWrapper.offTelephone ));
				pstmt.setString(9,Utility.trim(revolvingOverdraftWrapper.resTelephone ));
				pstmt.setString(10,Utility.trim(revolvingOverdraftWrapper.mobileNo ));
				pstmt.setString(11,Utility.trim(revolvingOverdraftWrapper.campaignCode ));
				pstmt.setString(12,Utility.trim(revolvingOverdraftWrapper.salaryMultiple ));
				pstmt.setString(13,Utility.trim(revolvingOverdraftWrapper.overdraftAmount ));
				pstmt.setString(14,Utility.trim(revolvingOverdraftWrapper.rate ));
				pstmt.setString(15,Utility.trim(revolvingOverdraftWrapper.refName1 ));
				pstmt.setString(16,Utility.trim(revolvingOverdraftWrapper.refContactNo1 ));
				pstmt.setString(17,Utility.trim(revolvingOverdraftWrapper.refName2 ));
				pstmt.setString(18,Utility.trim(revolvingOverdraftWrapper.refContactNo2)); 
				
				pstmt.executeUpdate();
				pstmt.close();
				
				
				revolvingOverdraftWrapper.recordFound=true;
				
				dataArrayWrapper.revolvingOverdraftWrapper=new RevolvingOverdraftWrapper[1];
				dataArrayWrapper.revolvingOverdraftWrapper[0]=revolvingOverdraftWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Revolving Overdraft Inserted");
				
			
			
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

	
	
	public AbstractWrapper fetchRoD(RevolvingOverdraftWrapper revolvingOverdraftWrapper)throws Exception {

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
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, RoDSalaryMultiple, RoDOverdraftAmount, RoDRate, CISNo, AccountNo, "
						+ "SecurityChequeNo, OffTelephone, ResTelephone, MobileNo, CampaignCode, SalaryMultiple, OverdraftAmount, Rate, RefName1, "
						+ "RefContactNo1, RefName2, RefContactNo2 FROM RMT_Revolvingoverdraft WHERE RefNo=?");
				
		
						
				System.out.println("Fetch Revolving Overdraft RefNo is" + revolvingOverdraftWrapper.refNo);
				
				pstmt.setString(1,revolvingOverdraftWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					revolvingOverdraftWrapper = new RevolvingOverdraftWrapper();
					
				
					
					revolvingOverdraftWrapper.refNo = Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + revolvingOverdraftWrapper.refNo);
					
	
					revolvingOverdraftWrapper.rodSalaryMultiple  = Utility.trim(resultSet.getString("RoDSalaryMultiple"));
					revolvingOverdraftWrapper.rodOverdraftAmount  = Utility.trim(resultSet.getString("RoDOverdraftAmount"));
					revolvingOverdraftWrapper.rodRate  = Utility.trim(resultSet.getString("RoDRate"));
					revolvingOverdraftWrapper.cisNo  = Utility.trim(resultSet.getString("CISNo"));
					revolvingOverdraftWrapper.accountNo  = Utility.trim(resultSet.getString("AccountNo"));
					revolvingOverdraftWrapper.securityChequeNo  = Utility.trim(resultSet.getString("SecurityChequeNo"));
					revolvingOverdraftWrapper.offTelephone  = Utility.trim(resultSet.getString("OffTelephone"));
					revolvingOverdraftWrapper.resTelephone  = Utility.trim(resultSet.getString("ResTelephone"));
					revolvingOverdraftWrapper.mobileNo  = Utility.trim(resultSet.getString("MobileNo"));
					revolvingOverdraftWrapper.campaignCode  = Utility.trim(resultSet.getString("CampaignCode"));
					revolvingOverdraftWrapper.salaryMultiple  = Utility.trim(resultSet.getString("SalaryMultiple"));
					revolvingOverdraftWrapper.overdraftAmount  = Utility.trim(resultSet.getString("OverdraftAmount"));
					revolvingOverdraftWrapper.rate  = Utility.trim(resultSet.getString("Rate"));
					revolvingOverdraftWrapper.refName1  = Utility.trim(resultSet.getString("RefName1"));
					revolvingOverdraftWrapper.refContactNo1  = Utility.trim(resultSet.getString("RefContactNo1"));
					revolvingOverdraftWrapper.refName2  = Utility.trim(resultSet.getString("RefName2"));
					revolvingOverdraftWrapper.refContactNo2 = Utility.trim(resultSet.getString("RefContactNo2")); 
					
					revolvingOverdraftWrapper.recordFound=true;
					System.out.println("Fetch Revolving Overdraft Completed ");
					
					vector.addElement(revolvingOverdraftWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.revolvingOverdraftWrapper = new RevolvingOverdraftWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.revolvingOverdraftWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.revolvingOverdraftWrapper = new RevolvingOverdraftWrapper[1];
				dataArrayWrapper.revolvingOverdraftWrapper[0]= revolvingOverdraftWrapper;
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
	
	public RevolvingOverdraftWrapper fetchRoD(RevolvingOverdraftWrapper revolvingOverdraftWrapper,String refNo)throws Exception {



		try 
		{

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchRoD(revolvingOverdraftWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				revolvingOverdraftWrapper = dataArrayWrapper.revolvingOverdraftWrapper[0];
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

		return revolvingOverdraftWrapper;

	}
	
	
	public AbstractWrapper updateRoD(RevolvingOverdraftWrapper revolvingOverdraftWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Revolving Overdraft");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_Revolvingoverdraft WHERE RefNo=?");
				
			
				System.out.println("Revolving Overdraft RefNo is" + revolvingOverdraftWrapper.refNo);
				
				pstmt.setString(1,revolvingOverdraftWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertRoD(revolvingOverdraftWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_Revolvingoverdraft SET RoDSalaryMultiple=?, RoDOverdraftAmount=?, RoDRate=?, CISNo=?, "
							+ "AccountNo=?, SecurityChequeNo=?, OffTelephone=?, ResTelephone=?, MobileNo=?, CampaignCode=?, SalaryMultiple=?, "
							+ "OverdraftAmount=?, Rate=?, RefName1=?, RefContactNo1=?, RefName2=?, RefContactNo2=? where RefNo=?");
					
					
	


					
					pstmt.setString(1,Utility.trim(revolvingOverdraftWrapper.rodSalaryMultiple ));
					pstmt.setString(2,Utility.trim(revolvingOverdraftWrapper.rodOverdraftAmount ));
					pstmt.setString(3,Utility.trim(revolvingOverdraftWrapper.rodRate ));
					pstmt.setString(4,Utility.trim(revolvingOverdraftWrapper.cisNo ));
					pstmt.setString(5,Utility.trim(revolvingOverdraftWrapper.accountNo ));
					pstmt.setString(6,Utility.trim(revolvingOverdraftWrapper.securityChequeNo ));
					pstmt.setString(7,Utility.trim(revolvingOverdraftWrapper.offTelephone ));
					pstmt.setString(8,Utility.trim(revolvingOverdraftWrapper.resTelephone ));
					pstmt.setString(9,Utility.trim(revolvingOverdraftWrapper.mobileNo ));
					pstmt.setString(10,Utility.trim(revolvingOverdraftWrapper.campaignCode ));
					pstmt.setString(11,Utility.trim(revolvingOverdraftWrapper.salaryMultiple ));
					pstmt.setString(12,Utility.trim(revolvingOverdraftWrapper.overdraftAmount ));
					pstmt.setString(13,Utility.trim(revolvingOverdraftWrapper.rate ));
					pstmt.setString(14,Utility.trim(revolvingOverdraftWrapper.refName1 ));
					pstmt.setString(15,Utility.trim(revolvingOverdraftWrapper.refContactNo1 ));
					pstmt.setString(16,Utility.trim(revolvingOverdraftWrapper.refName2 ));
					pstmt.setString(17,Utility.trim(revolvingOverdraftWrapper.refContactNo2)); 
					
					pstmt.setString(18,Utility.trim(revolvingOverdraftWrapper.refNo));
	
					System.out.println("Revolving Overdraft Update RefNo " + revolvingOverdraftWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
					
					
	
				
					revolvingOverdraftWrapper.recordFound=true;
	
					dataArrayWrapper.revolvingOverdraftWrapper=new RevolvingOverdraftWrapper[1];
					dataArrayWrapper.revolvingOverdraftWrapper[0]=revolvingOverdraftWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Revolving Overdraft updated " );
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
