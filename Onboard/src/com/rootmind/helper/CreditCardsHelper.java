package com.rootmind.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class CreditCardsHelper extends Helper {
	
	public AbstractWrapper fetchCreditCards(CreditCardsWrapper creditCardsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		String sql=null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("cc Customer number :"+ creditCardsWrapper.cifNumber);
	
		try {
			con = getConnection();
			sql= "SELECT CIFNumber,CreditCardNumber,CustomerName,ProductName,CreditCardType,"
					+ "CreditCardStatus,TotalLimitAmount,LimitCurrency,CurrentOSBalance,PaymentDueDate,"
					+ "LastPaymentDate,LastPaymentAmount,StatementDate,AvailableCreditLimit,AutoPaymentOption,"
					+ "AvailableCashLimit,TotalPaymentDue,MinPaymentDue,CashWithdrawLimit "
					+ "from CreditCards  where CIFNumber=?";
			
		
			if(creditCardsWrapper.creditCardNumber!=null)
			{
				
				sql = sql + " and CreditCardNumber=?";
			}
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,creditCardsWrapper.cifNumber.trim());
			
			if(creditCardsWrapper.creditCardNumber!=null)
			{

				pstmt.setString(2,creditCardsWrapper.creditCardNumber.trim());
			}
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				creditCardsWrapper=new CreditCardsWrapper();
				
				creditCardsWrapper.cifNumber=(resultSet.getString("CIFNumber")==null?"":resultSet.getString("CIFNumber").trim());
				System.out.println("CIFNumber " + creditCardsWrapper.cifNumber);

				creditCardsWrapper.creditCardNumber=(resultSet.getString("CreditCardNumber")==null?"":resultSet.getString("CreditCardNumber").trim());
				System.out.println("cc Number " + creditCardsWrapper.creditCardNumber);
				
				creditCardsWrapper.customerName=(resultSet.getString("CustomerName")==null?"":resultSet.getString("CustomerName").trim());

				creditCardsWrapper.productName=(resultSet.getString("ProductName")==null?"":resultSet.getString("ProductName").trim());


				creditCardsWrapper.creditCardType=(resultSet.getString("CreditCardType")==null?"":resultSet.getString("CreditCardType").trim());
				System.out.println("cc type " + creditCardsWrapper.creditCardType);
				
				creditCardsWrapper.creditCardStatus=(resultSet.getString("CreditCardStatus")==null?"":resultSet.getString("CreditCardStatus").trim());
				
				creditCardsWrapper.totalLimitAmount= (resultSet.getBigDecimal("TotalLimitAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("TotalLimitAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.limitCurrency= (resultSet.getString("LimitCurrency")==null?"":resultSet.getString("LimitCurrency").trim());
				creditCardsWrapper.currentOSBalance=(resultSet.getBigDecimal("CurrentOSBalance")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("CurrentOSBalance").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.paymentDueDate= (resultSet.getDate("PaymentDueDate")==null?"":dmyFormat.format(resultSet.getDate("PaymentDueDate")));
				creditCardsWrapper.lastPaymentDate= (resultSet.getDate("LastPaymentDate")==null?"":dmyFormat.format(resultSet.getDate("PaymentDueDate")));
				creditCardsWrapper.lastPaymentAmount=(resultSet.getBigDecimal("LastPaymentAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("LastPaymentAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.statementDate= (resultSet.getDate("StatementDate")==null?"":dmyFormat.format(resultSet.getDate("StatementDate")));
				creditCardsWrapper.availableCreditLimit= (resultSet.getBigDecimal("AvailableCreditLimit")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("AvailableCreditLimit").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.autoPaymentOption=(resultSet.getString("AutoPaymentOption")==null?"":resultSet.getString("AutoPaymentOption").trim());
				creditCardsWrapper.availableCashLimit=(resultSet.getBigDecimal("AvailableCashLimit")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("AvailableCashLimit").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.totalPaymentDue= (resultSet.getBigDecimal("TotalPaymentDue")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("TotalPaymentDue").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.minPaymentDue= (resultSet.getBigDecimal("MinPaymentDue")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("MinPaymentDue").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardsWrapper.cashWithdrawLimit= (resultSet.getBigDecimal("CashWithdrawLimit")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("CashWithdrawLimit").setScale(2,BigDecimal.ROUND_DOWN)));

			
				creditCardsWrapper.recordFound=true;
				
				System.out.println("creditCardsWrapper " );
				
	

				vector.addElement(creditCardsWrapper);
				
				

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardsWrapper = new CreditCardsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

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
	
	
	
	
	
	public AbstractWrapper fetchCreditCardTrn(CreditCardTrnWrapper creditCardTrnWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");

		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("Customer number :"+ creditCardTrnWrapper.cifNumber);
		System.out.println("creditCardNumber  :"+ creditCardTrnWrapper.creditCardNumber);
	
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT CIFNumber,CreditCardNumber,TrnDate,"
					+ "TrnType,Narration1,Narration2,TrnAmount,CRDRflag,TrnCurrency "
					+ "from CreditCardTrn  where CIFNumber=? and CreditCardNumber=? order by TrnDate desc");
			
			pstmt.setString(1,creditCardTrnWrapper.cifNumber.trim());
			pstmt.setString(2,creditCardTrnWrapper.creditCardNumber.trim());
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				creditCardTrnWrapper=new CreditCardTrnWrapper();
				
				creditCardTrnWrapper.cifNumber=(resultSet.getString("CIFNumber")==null?"":resultSet.getString("CIFNumber").trim());
				System.out.println("CIFNumber " + creditCardTrnWrapper.cifNumber);

				creditCardTrnWrapper.creditCardNumber=(resultSet.getString("CreditCardNumber")==null?"":resultSet.getString("CreditCardNumber").trim());
				System.out.println("CreditCardNumber " + creditCardTrnWrapper.creditCardNumber);
				
				creditCardTrnWrapper.trnDate= (resultSet.getDate("TrnDate")==null?"":dmyFormat.format(resultSet.getDate("TrnDate")));
				creditCardTrnWrapper.trnType=(resultSet.getString("TrnType")==null?"":resultSet.getString("TrnType").trim());
		
				creditCardTrnWrapper.narration1=(resultSet.getString("Narration1")==null?"":resultSet.getString("Narration1").trim());
				creditCardTrnWrapper.narration2=(resultSet.getString("Narration2")==null?"":resultSet.getString("Narration2").trim());
				creditCardTrnWrapper.trnAmount= (resultSet.getBigDecimal("TrnAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("TrnAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				creditCardTrnWrapper.CRDRflag= (resultSet.getString("CRDRflag")==null?"":resultSet.getString("CRDRflag").trim());
				creditCardTrnWrapper.trnCurrency=(resultSet.getString("TrnCurrency")==null?"":resultSet.getString("TrnCurrency").trim());
				
				creditCardTrnWrapper.recordFound=true;
				
				System.out.println("creditCardTrnWrapper " );

				vector.addElement(creditCardTrnWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.creditCardTrnWrapper = new CreditCardTrnWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.creditCardTrnWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

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
