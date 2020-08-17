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

public class CustomerSpendingHelper extends Helper {
	
	public AbstractWrapper fetchCustomerSpending(CustomerSpendingWrapper customerSpendingWrapper)throws Exception {

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
			
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT CIFNumber, AccountNumber, Date, Earnings,"
						+ "Expenses FROM CustomerSpending WHERE CIFNumber=? and AccountNumber=?");
				
				
				System.out.println("CustomerSpending CIFNumber is" + customerSpendingWrapper.cifNumber);
				System.out.println("CustomerSpending AccountNumber is" + customerSpendingWrapper.accountNumber);
				
				pstmt.setString(1,customerSpendingWrapper.cifNumber.trim());
				pstmt.setString(2,customerSpendingWrapper.accountNumber.trim());
		
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					customerSpendingWrapper = new CustomerSpendingWrapper();
				
					customerSpendingWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
					
					customerSpendingWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNumber"));
					customerSpendingWrapper.date=Utility.setDate(resultSet.getString("Date"));
				
					customerSpendingWrapper.earnings=Utility.trim(resultSet.getString("Earnings"));
					
					customerSpendingWrapper.expenses=Utility.trim(resultSet.getString("Expenses"));
					
					customerSpendingWrapper.recordFound=true;
					
				
					
					
					vector.addElement(customerSpendingWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.customerSpendingWrapper = new CustomerSpendingWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.customerSpendingWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
 
			if(resultSet !=null) resultSet.close();
			pstmt.close();

			System.out.println("Fetch Customer Spending Details Successful" );
			
			
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
