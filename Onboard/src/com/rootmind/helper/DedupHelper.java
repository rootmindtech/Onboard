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

public class DedupHelper extends Helper{
	
	public AbstractWrapper dedupEnquiry(DedupWrapper dedupWrapper)throws Exception {

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
			
			sql="SELECT RefNo, ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, CustomerName,FirstName,MiddleName,LastName, DOB, PassportNo,EmiratesID,MakerId FROM RMT_OnBoard ";
			
			System.out.println("Dedup cifNumber " + dedupWrapper.cifNumber);
			System.out.println("Dedup accountNumber " + dedupWrapper.accountNumber);
			
			if(dedupWrapper.cifNumber!=null && !dedupWrapper.cifNumber.trim().isEmpty())
			{
				sql=sql + " WHERE ExistingCIFNumber=?";
				
				System.out.println("Search cif number " + sql);
				
			}
			else if(dedupWrapper.accountNumber!=null && !dedupWrapper.accountNumber.trim().isEmpty())
			{
				sql=sql + " WHERE ExistingAccountNo=?";
				
				System.out.println("search account number " + sql);
				
			}
			else if(dedupWrapper.creditCardNumber!=null && !dedupWrapper.creditCardNumber.trim().isEmpty())
			{
				sql=sql + " WHERE ExistingCreditCardNo=?";
				
				System.out.println("search credit card number " + sql);
				
			}
			else if(dedupWrapper.customerName!=null && !dedupWrapper.customerName.trim().isEmpty())
			{
				sql=sql + " WHERE UPPER(CustomerName) LIKE ?";
				
				System.out.println("search customer name " + sql);
				
				System.out.println("search customer name " + dedupWrapper.customerName);
				
			}
			else if(dedupWrapper.dob!=null && !dedupWrapper.dob.isEmpty())
			{
				sql=sql + " WHERE DOB=?";
				
				System.out.println("search Date Of Birth " + sql);
				
			}
			else if(dedupWrapper.passportNumber!=null && !dedupWrapper.passportNumber.trim().isEmpty())
			{
				sql=sql + " WHERE PassportNo=?";
				
				System.out.println("search Passport number " + sql);
				
			}
			else if(dedupWrapper.emiratesId!=null && !dedupWrapper.emiratesId.trim().isEmpty())
			{
				sql=sql + " WHERE EmiratesID=?";
				
				System.out.println("search Emirates ID " + sql);
				
			}
			else if(dedupWrapper.firstName!=null && !dedupWrapper.firstName.trim().isEmpty())
			{
				sql=sql + " WHERE UPPER(FirstName) LIKE ?";
				
				
				System.out.println("search firstName name " + dedupWrapper.firstName);
				
			}
			else if(dedupWrapper.middleName!=null && !dedupWrapper.middleName.trim().isEmpty())
			{
				sql=sql + " WHERE UPPER(MiddleName) LIKE ?";
				
				
				System.out.println("search MiddleName name " + dedupWrapper.middleName);
				
			}
			else if(dedupWrapper.lastName!=null && !dedupWrapper.lastName.trim().isEmpty())
			{
				sql=sql + " WHERE UPPER(LastName) LIKE ?";
				
				
				System.out.println("search lastName name " + dedupWrapper.lastName);
				
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
		
			
			System.out.println("SQl before setstring " + sql);
			
			if(dedupWrapper.cifNumber!=null && !dedupWrapper.cifNumber.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.cifNumber.trim());
				
				System.out.println("search CIF no " + dedupWrapper.cifNumber);
				
			}
			else if(dedupWrapper.accountNumber!=null && !dedupWrapper.accountNumber.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.accountNumber.trim());
				
			}
			else if(dedupWrapper.creditCardNumber!=null && !dedupWrapper.creditCardNumber.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.creditCardNumber.trim());
				
			}
			else if(dedupWrapper.customerName!=null && !dedupWrapper.customerName.trim().isEmpty())
			{
				pstmt.setString(1, '%' + dedupWrapper.customerName.trim().toUpperCase() + '%');
				
				System.out.println("search customer  " + dedupWrapper.customerName.trim().toUpperCase());
				
			}
			else if(dedupWrapper.dob!=null && !dedupWrapper.dob.isEmpty())
			{
			
				pstmt.setDate(1,Utility.getDate(dedupWrapper.dob));
				
			}
			else if(dedupWrapper.passportNumber!=null && !dedupWrapper.passportNumber.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.passportNumber.trim());
				
			}
			else if(dedupWrapper.emiratesId!=null && !dedupWrapper.emiratesId.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.emiratesId.trim());
				
			}
			else if(dedupWrapper.firstName!=null && !dedupWrapper.firstName.trim().isEmpty())
			{
				pstmt.setString(1, '%' + dedupWrapper.firstName.trim().toUpperCase() + '%');
				
				System.out.println("search firstName  " + dedupWrapper.firstName.trim().toUpperCase());
				
			}
			else if(dedupWrapper.middleName!=null && !dedupWrapper.middleName.trim().isEmpty())
			{
				pstmt.setString(1, '%' + dedupWrapper.middleName.trim().toUpperCase() + '%');
				
				System.out.println("search middleName  " + dedupWrapper.middleName.trim().toUpperCase());
				
			}
			else if(dedupWrapper.lastName!=null && !dedupWrapper.lastName.trim().isEmpty())
			{
				pstmt.setString(1, '%' + dedupWrapper.lastName.trim().toUpperCase() + '%');
				
				System.out.println("search lastName  " + dedupWrapper.lastName.trim().toUpperCase());
				
			}
		
//			System.out.println("RefNo is" + personalDetailsWrapper.refNo);
//			
//			pstmt.setString(1,personalDetailsWrapper.refNo.trim());
			//pstmt.setString(1,personalDetailsWrapper.refNo.trim());
		
			
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				dedupWrapper= new DedupWrapper();
				
				dedupWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				dedupWrapper.cifNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));
				System.out.println("ExistingCIFNumber " + dedupWrapper.cifNumber);
				
				dedupWrapper.accountNumber=Utility.trim(resultSet.getString("ExistingAccountNo"));
				
				dedupWrapper.creditCardNumber=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
				
				dedupWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
				
				dedupWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
				dedupWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
				dedupWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
				
				dedupWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
				
				dedupWrapper.passportNumber=Utility.trim(resultSet.getString("PassportNo"));
				dedupWrapper.emiratesId=Utility.trim(resultSet.getString("EmiratesID"));
				dedupWrapper.makerId=Utility.trim(resultSet.getString("MakerId"));

				dedupWrapper.recordFound=true;
				
				//--Insert into RMT_Dedup
				
				insertDedup(dedupWrapper);
				
				//-----------

				vector.addElement(dedupWrapper);
				System.out.println("Duplicate Details found " );
			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.dedupWrapper = new DedupWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.dedupWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
			{
				dataArrayWrapper.dedupWrapper = new DedupWrapper[1];
				
				System.out.println("no matching results found " + dedupWrapper.customerName);
				dedupWrapper.customerName= dedupWrapper.customerName + ' ' + "No matching results found";
				dedupWrapper.firstName= dedupWrapper.firstName + ' ' + "No matching results found";
				dedupWrapper.middleName= dedupWrapper.middleName + ' ' + "No matching results found";
				dedupWrapper.lastName= dedupWrapper.lastName + ' ' + "No matching results found";
				//insertDedup(dedupWrapper);
				dataArrayWrapper.dedupWrapper[0]= dedupWrapper;
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
	
	
	public AbstractWrapper insertDedup(DedupWrapper dedupWrapper)throws Exception {

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
			
			sql=" INSERT INTO RMT_Dedup(RefNo,CIFNumber,AccountNo,CreditCardNo,CustomerName,FirstName,MiddleName,LastName,DOB,PassportNo, "
					+ "EmiratesID,MakerId,MakerDateTime) Values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			System.out.println("sql " + sql);
			
			 pstmt = con.prepareStatement(sql);

			pstmt.setString(1,Utility.trim(dedupWrapper.refNo));
			pstmt.setString(2,Utility.trim(dedupWrapper.cifNumber));
			pstmt.setString(3,Utility.trim(dedupWrapper.accountNumber));
			pstmt.setString(4,Utility.trim(dedupWrapper.creditCardNumber));
			pstmt.setString(5,Utility.trim(dedupWrapper.customerName));
			pstmt.setString(6,Utility.trim(dedupWrapper.firstName));
			pstmt.setString(7,Utility.trim(dedupWrapper.middleName));
			pstmt.setString(8,Utility.trim(dedupWrapper.lastName));
			
			pstmt.setDate(9,Utility.getDate(dedupWrapper.dob));
			pstmt.setString(10,Utility.trim(dedupWrapper.passportNumber));
			pstmt.setString(11,Utility.trim(dedupWrapper.emiratesId));
			pstmt.setString(12,Utility.trim(dedupWrapper.makerId));
			pstmt.setTimestamp(13,new java.sql.Timestamp(System.currentTimeMillis()));

			pstmt.executeUpdate();
			pstmt.close();
			

			dedupWrapper.recordFound=true;
			
			dataArrayWrapper.dedupWrapper=new DedupWrapper[1];
			dataArrayWrapper.dedupWrapper[0]=dedupWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Dedup Inserted");
			
			
			
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

	
	public AbstractWrapper fetchDedup(DedupWrapper dedupWrapper)throws Exception {

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
			
			sql="SELECT RefNo,CIFNumber,AccountNo,CreditCardNo,CustomerName,DOB,PassportNo, "
					+ "EmiratesID,MakerId,MakerDateTime FROM RMT_Dedup ";
			
			if(dedupWrapper.refNo !=null && !dedupWrapper.refNo.trim().isEmpty())
			{
				sql=sql + " WHERE RefNo!=?";
				
				System.out.println("Fetch DEDUP " + sql);
				
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			if(dedupWrapper.refNo !=null && !dedupWrapper.refNo.trim().isEmpty())
			{
				pstmt.setString(1, dedupWrapper.refNo);
			}
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				dedupWrapper= new DedupWrapper();
				dedupWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				dedupWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
				dedupWrapper.accountNumber=Utility.trim(resultSet.getString("AccountNo"));
				dedupWrapper.creditCardNumber=Utility.trim(resultSet.getString("CreditCardNo"));
				dedupWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
				dedupWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
				dedupWrapper.passportNumber=Utility.trim(resultSet.getString("PassportNo"));
				dedupWrapper.emiratesId=Utility.trim(resultSet.getString("EmiratesID"));
				dedupWrapper.makerId=Utility.trim(resultSet.getString("MakerId"));
				dedupWrapper.makerDateTime=Utility.trim(resultSet.getString("MakerDateTime"));
				dedupWrapper.recordFound=true;

				vector.addElement(dedupWrapper);
				System.out.println("Dedup Details found " );
			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.dedupWrapper = new DedupWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.dedupWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
			{
				//dataArrayWrapper.dedupWrapper = new DedupWrapper[1];
				//dataArrayWrapper.dedupWrapper[0]= dedupWrapper;
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
