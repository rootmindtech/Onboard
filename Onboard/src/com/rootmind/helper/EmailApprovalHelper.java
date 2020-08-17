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

public class EmailApprovalHelper extends Helper {
	

	public AbstractWrapper insertEmailApproval(EmailApprovalWrapper emailApprovalWrapper)throws Exception {

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
			
			sql=" INSERT INTO EMAILAPPROVAL(RefNo, ApprovalAuth, SSOComment) Values "
					+ "(?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,Utility.trim(emailApprovalWrapper.refNo));
			pstmt.setString(2,Utility.trim(emailApprovalWrapper.ApprovalAuth));
			pstmt.setString(3,Utility.trim(emailApprovalWrapper.SSOComment));
			

			pstmt.executeUpdate();
			pstmt.close();
			
			
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO EMAILAPPROVAL(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(emailApprovalWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(emailApprovalWrapper.makerDate));
			pstmt.setString(3,Utility.trim(emailApprovalWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(emailApprovalWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
	
			//------------------
			
			
			

			emailApprovalWrapper.recordFound=true;
			
			
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

		return emailApprovalWrapper;
	}




	public AbstractWrapper fetchEmailApproval(EmailApprovalWrapper emailApprovalWrapper)throws Exception {

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

		System.out.println("EmailApprovalWrapper:"+ emailApprovalWrapper.refNo);
		
		
		try {
			con = getConnection();
			
			
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, ApprovalAuth, SSOComment FROM EMAILAPPROVAL where RefNo=?");
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) 
			{
				
				emailApprovalWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + emailApprovalWrapper.refNo);
				
				emailApprovalWrapper. ApprovalAuth=Utility.trim(resultSet.getString(" ApprovalAuth"));
				System.out.println(" ApprovalAuth" + emailApprovalWrapper. ApprovalAuth);
				
				
				emailApprovalWrapper.SSOComment=Utility.trim(resultSet.getString("SSOComment"));
				System.out.println("SSOComment" + emailApprovalWrapper.SSOComment);
				
				
				
				emailApprovalWrapper.recordFound=true;
				
				System.out.println("emailApprovalWrapper " );

				vector.addElement(emailApprovalWrapper);
				
			}
			
			
			if (vector.size()>0)
			{
				
				dataArrayWrapper.emailApprovalWrapper = new EmailApprovalWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.emailApprovalWrapper);
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

	



public AbstractWrapper updateEmailApproval(EmailApprovalWrapper emailApprovalWrapper)throws Exception {

	Connection con = null;
	ResultSet resultSet = null;
	//OccupationDetailsWrapper occupationDetailsWrapper=	new OccupationDetailsWrapper();
	DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
	

	System.out.println("update Email Details");

	try {
		con = getConnection();
		
		PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET RefNo=?, ApprovalAuth=?, SSOComment=? FROM EMAILAPPROVAL where RefNo=? ");
		
		pstmt.setString(1,Utility.trim(emailApprovalWrapper.refNo));
		pstmt.setString(2,Utility.trim(emailApprovalWrapper.ApprovalAuth));
		pstmt.setString(3,Utility.trim(emailApprovalWrapper.SSOComment));
		
		pstmt.executeUpdate();

		pstmt.close();

		
		
		emailApprovalWrapper.recordFound=true;
		
		dataArrayWrapper.emailApprovalWrapper=new EmailApprovalWrapper[1];
		dataArrayWrapper.emailApprovalWrapper[0]=emailApprovalWrapper;
		
		System.out.println("Email Details updated " );
		
		
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


	
	
		
