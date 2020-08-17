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

public class SearchHelper extends Helper {
	
	public AbstractWrapper fetchEnquiry(SearchWrapper searchWrapper)throws Exception {

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

		String sql=null;
		try {
				con = getConnection();
				
				if(searchWrapper.creditCardNumber ==null || searchWrapper.creditCardNumber.equals(""))
				{	
					sql="SELECT CIFNumber, AccountNumber, CustomerName FROM Accounts ";
				
				}
				else
				{
					sql="SELECT CIFNumber, AccountNumber, CustomerName FROM CreditCards ";
				}
				
				if(searchWrapper.cifNumber!=null && !searchWrapper.cifNumber.trim().isEmpty())
				{			
					System.out.println("if search cif number " + sql);
					
					sql=sql + " WHERE CIFNumber=?";
								
				}
				else if(searchWrapper.accountNo!=null && !searchWrapper.accountNo.trim().isEmpty())
				{
					System.out.println("if search account number " + sql);
					sql=sql + " WHERE AccountNumber=?";
					
				}
			/*	else if(searchWrapper.creditCardNumber!=null && !searchWrapper.creditCardNumber.trim().isEmpty())
				{
					sql=sql + " WHERE CreditCardNo=?";
					
					System.out.println("search credit card number " + sql);
					
				}*/
				else if(searchWrapper.customerName!=null && !searchWrapper.customerName.trim().isEmpty())
				{
					
					
					sql=sql + " WHERE UPPER(CustomerName) LIKE ?";
					
					System.out.println("search customer name " + sql);
					
				}
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				if(searchWrapper.cifNumber!=null && !searchWrapper.cifNumber.trim().isEmpty())
				{
					pstmt.setString(1, searchWrapper.cifNumber.trim());
					
				}
				else if(searchWrapper.accountNo!=null && !searchWrapper.accountNo.trim().isEmpty())
				{
					pstmt.setString(1, searchWrapper.accountNo.trim());
					
				}
				/*else if(searchWrapper.creditCardNumber!=null && !searchWrapper.creditCardNumber.trim().isEmpty())
				{
					pstmt.setString(1, searchWrapper.creditCardNumber.trim());
					
				}*/
				else if(searchWrapper.customerName!=null && !searchWrapper.customerName.trim().isEmpty())
				{
					pstmt.setString(1, '%' + searchWrapper.customerName.trim().toUpperCase() + '%');
					
					System.out.println("search customer  " + searchWrapper.customerName.trim().toUpperCase());
					
				}
				
			
	//			System.out.println("RefNo is" + personalDetailsWrapper.refNo);
	//			
	//			pstmt.setString(1,personalDetailsWrapper.refNo.trim());
				//pstmt.setString(1,personalDetailsWrapper.refNo.trim());
			
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					searchWrapper= new SearchWrapper();
					
					searchWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
					System.out.println("Enquiry CIF Number " + searchWrapper.cifNumber);
					
					searchWrapper.accountNo=Utility.trim(resultSet.getString("AccountNumber"));
					
					//searchWrapper.creditCardNumber=Utility.trim(resultSet.getString("CreditCardNo"));
					
					searchWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
					
					
					searchWrapper.recordFound=true;
					
					
	
					vector.addElement(searchWrapper);
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.searchWrapper = new SearchWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.searchWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
	 
				if(resultSet !=null) resultSet.close();
				pstmt.close();
				System.out.println("Searched Details found " );


			
			
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



