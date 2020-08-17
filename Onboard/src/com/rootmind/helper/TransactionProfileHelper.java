package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class TransactionProfileHelper extends Helper {

	

	public AbstractWrapper insertTransactionProfile(TransactionProfileWrapper transactionProfileWrapper)throws Exception {

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
		
		
	
		//System.out.println("Customer number :"+ personalDetailsWrapper.customerName);
	
		try {
				con = getConnection();
			
			sql=" INSERT INTO ONBOARD(RefNo, SrcAccRegBasisFlag, Details, AnnualTurnover, TIncome, NoTransMonth"
					+ " TAmountTrans, PurposeTrans, Deposits, Withdrawals, MaxAmtINRemittances, MajorCountriesFlag) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,Utility.trim(transactionProfileWrapper.refNo));
			pstmt.setString(2,Utility.trim(transactionProfileWrapper.srcAccRegBasisFlag));
			pstmt.setString(3,Utility.trim(transactionProfileWrapper.details));
			pstmt.setString(4,Utility.trim(transactionProfileWrapper.annualTurnover));
			pstmt.setString(5,Utility.trim(transactionProfileWrapper.tIncome));
			pstmt.setString(6,Utility.trim(transactionProfileWrapper.noTransMonth));
			pstmt.setString(7,Utility.trim(transactionProfileWrapper.tAmountTrans));
			pstmt.setString(8,Utility.trim(transactionProfileWrapper.purposeTrans));
			pstmt.setString(9,Utility.trim(transactionProfileWrapper.deposits));
			pstmt.setString(10,Utility.trim(transactionProfileWrapper.withdrawals));
			pstmt.setDate(11,Utility.getDate(transactionProfileWrapper.maxAmtINRemittances));
			pstmt.setString(12,Utility.trim(transactionProfileWrapper.majorCountriesFlag));
		
			
			pstmt.executeUpdate();
			pstmt.close();

			transactionProfileWrapper.recordFound=true;
			
			
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

		return transactionProfileWrapper;
	}
	
	
}
