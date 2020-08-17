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


public class AccountsHelper extends Helper {

	
	public AbstractWrapper fetchAccounts(AccountsWrapper accountsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
	
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("CIF Number is :"+ accountsWrapper.cifNumber);
	
		try {
			con = getConnection();
			
			sql="SELECT CIFNumber,AccountNumber,CustomerName,AccountStatus,"
					+ "AccountCurrency,CurrentBalance,AvailableBalance,AccountType,AccountDesc,CustomerSegment,LoanType,"
					+ "LoanDesc,LoanStatus ,PrincipalAmount,PrincipalCurrency,InstallmentAmount,InstallmentCurrency,"
					+ "LoanCurrentBalance,LoanStartDate,NextInstlDate,LoanOSBalance,PaidAmount,NoPendingInstl,LastPaidAmount,"
					+ "LastPaymentDate,TotalInstallments,InstallmentsPaid,DepositAmount,DepositCurrency,DepositTenor,"
					+ "DepositDate,DepositMaturityDate,DepositRenewalType,DepositCreditAccount,DepositMaturityAmount,"
					+ "StatementRequest "
					+ "from Accounts  where CIFNumber=? ";
			
			if(accountsWrapper.accountNumber!=null)
			{
				
				sql = sql + " and AccountNumber=?";
			}
			if(accountsWrapper.accountType.equals("CASA"))
			{
				
				sql = sql + " and AccountType in ('SAVINGS','CURRENT')";
			}
			if(accountsWrapper.accountType.equals("DEPOSIT"))
			{
				
				sql = sql + " and AccountType in ('DEPOSIT')";
			}
			if(accountsWrapper.accountType.equals("LOANS"))
			{
				
				sql = sql + " and AccountType in ('LOANS')";
			}
			if(accountsWrapper.accountType.equals("ALL"))
			{
				
				sql = sql + " and AccountType in ('SAVINGS','CURRENT','LOANS','DEPOSIT')";
			}
				

			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,accountsWrapper.cifNumber.trim());
			if(accountsWrapper.accountNumber!=null)
			{

				pstmt.setString(2,accountsWrapper.accountNumber.trim());
			}


			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				accountsWrapper=new AccountsWrapper();
				
				accountsWrapper.cifNumber=(resultSet.getString("CIFNumber")==null?"":resultSet.getString("CIFNumber").trim());
				System.out.println("CIFNumber " + accountsWrapper.cifNumber);

				accountsWrapper.accountNumber=(resultSet.getString("AccountNumber")==null?"":resultSet.getString("AccountNumber").trim());
				System.out.println("accountNumber " + accountsWrapper.accountNumber);

				accountsWrapper.customerName=(resultSet.getString("CustomerName")==null?"":resultSet.getString("CustomerName").trim());
				System.out.println("customerName " + accountsWrapper.customerName);
				
				accountsWrapper.accountStatus=(resultSet.getString("AccountStatus")==null?"":resultSet.getString("AccountStatus").trim());
				
				accountsWrapper.accountCurrency= (resultSet.getString("AccountCurrency")==null?"":resultSet.getString("AccountCurrency").trim());
				accountsWrapper.currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("CurrentBalance").setScale(2,BigDecimal.ROUND_DOWN)));

				//accountsWrapper.availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("AvailableBalance").setScale(2,BigDecimal.ROUND_DOWN)));
			
				accountsWrapper.availableBalance= (resultSet.getString("AvailableBalance")==null?"":resultSet.getString("AvailableBalance").trim());
				accountsWrapper.accountType=(resultSet.getString("AccountType")==null?"":resultSet.getString("AccountType").trim());
				accountsWrapper.accountDesc=(resultSet.getString("AccountDesc")==null?"":resultSet.getString("AccountDesc").trim());
				accountsWrapper.customerSegment=(resultSet.getString("CustomerSegment")==null?"":resultSet.getString("CustomerSegment").trim());
				accountsWrapper.loanType=(resultSet.getString("LoanType")==null?"":resultSet.getString("LoanType").trim());
				accountsWrapper.loanDesc=(resultSet.getString("LoanDesc")==null?"":resultSet.getString("LoanDesc").trim());
				accountsWrapper.loanStatus=(resultSet.getString("LoanStatus")==null?"":resultSet.getString("LoanStatus").trim());
				accountsWrapper.principalAmount=(resultSet.getBigDecimal("PrincipalAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("PrincipalAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				//System.out.println("PrincipalCurrency " + resultSet.getString("PrincipalCurrency"));
				accountsWrapper.principalCurrency=(resultSet.getString("PrincipalCurrency")==null?"":resultSet.getString("PrincipalCurrency").trim());
			
				accountsWrapper.installmentAmount= (resultSet.getBigDecimal("InstallmentAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("InstallmentAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.installmentCurrency= (resultSet.getString("InstallmentCurrency")==null?"":resultSet.getString("InstallmentCurrency").trim());
				accountsWrapper.loanCurrentBalance=(resultSet.getBigDecimal("LoanCurrentBalance")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("LoanCurrentBalance").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.loanStartDate= (resultSet.getDate("LoanStartDate")==null?"":dmyFormat.format(resultSet.getDate("LoanStartDate")));
				accountsWrapper.nextInstlDate= (resultSet.getDate("NextInstlDate")==null?"":dmyFormat.format(resultSet.getDate("NextInstlDate")));
				accountsWrapper.loanOSBalance= (resultSet.getBigDecimal("LoanOSBalance")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("LoanOSBalance").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.paidAmount= (resultSet.getBigDecimal("PaidAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("PaidAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.noPendingInstl= resultSet.getInt("NoPendingInstl");
				accountsWrapper.lastPaidAmount= (resultSet.getBigDecimal("LastPaidAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("LastPaidAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.lastPaymentDate= (resultSet.getDate("LastPaymentDate")==null?"":dmyFormat.format(resultSet.getDate("LastPaymentDate")));
				accountsWrapper.totalInstallments= resultSet.getInt("TotalInstallments");
				accountsWrapper.installmentsPaid= resultSet.getInt("InstallmentsPaid");
				accountsWrapper.depositAmount= (resultSet.getBigDecimal("DepositAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("DepositAmount").setScale(2, BigDecimal.ROUND_DOWN)));
				accountsWrapper.depositCurrency= (resultSet.getString("DepositCurrency")==null?"":resultSet.getString("DepositCurrency").trim());
				accountsWrapper.depositTenor= resultSet.getInt("DepositTenor");
				accountsWrapper.depositDate= (resultSet.getDate("DepositDate")==null?"":dmyFormat.format(resultSet.getDate("DepositDate")));
				accountsWrapper.depositMaturityDate= (resultSet.getDate("DepositMaturityDate")==null?"":dmyFormat.format(resultSet.getDate("DepositMaturityDate")));
				accountsWrapper.depositRenewalType= (resultSet.getString("DepositRenewalType")==null?"":resultSet.getString("DepositRenewalType").trim());
				accountsWrapper.depositCreditAccount= (resultSet.getString("DepositCreditAccount")==null?"":resultSet.getString("DepositCreditAccount").trim());
				accountsWrapper.depositMaturityAmount= (resultSet.getBigDecimal("DepositMaturityAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("DepositMaturityAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				accountsWrapper.statementRequest= (resultSet.getString("StatementRequest")==null?"":resultSet.getString("StatementRequest").trim());
				
				
				accountsWrapper.recordFound=true;
				
				System.out.println("accountswrapper " );
				
	

				vector.addElement(accountsWrapper);
				
				

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.accountsWrapper = new AccountsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.accountsWrapper);
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
	
	
	

	
	
	public AbstractWrapper fetchAccountTrn(AccountTrnWrapper accountTrnWrapper)throws Exception {

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

		
		System.out.println("cif number :"+ accountTrnWrapper.cifNumber);
		System.out.println("account number :"+ accountTrnWrapper.accountNumber);
	
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency "
					+ "from AccountTrn  where CIFNumber=? and AccountNumber=? order by TrnDate desc");
		
			pstmt.setString(1,accountTrnWrapper.cifNumber.trim());
			pstmt.setString(2,accountTrnWrapper.accountNumber.trim());
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				accountTrnWrapper=new AccountTrnWrapper();
				
				accountTrnWrapper.cifNumber=(resultSet.getString("CIFNumber")==null?"":resultSet.getString("CIFNumber").trim());
				System.out.println("CIFNumber " + accountTrnWrapper.cifNumber);

				accountTrnWrapper.accountNumber=(resultSet.getString("AccountNumber")==null?"":resultSet.getString("AccountNumber").trim());
				System.out.println("accountNumber " + accountTrnWrapper.accountNumber);
				accountTrnWrapper.trnDate= (resultSet.getDate("TrnDate")==null?"":dmyFormat.format(resultSet.getDate("TrnDate")));
		
				accountTrnWrapper.narration1=(resultSet.getString("Narration1")==null?"":resultSet.getString("Narration1").trim());
				accountTrnWrapper.narration2=(resultSet.getString("Narration2")==null?"":resultSet.getString("Narration2").trim());
				accountTrnWrapper.trnAmount= (resultSet.getBigDecimal("TrnAmount")==null?formatter.format(BigDecimal.ZERO):formatter.format(resultSet.getBigDecimal("TrnAmount").setScale(2,BigDecimal.ROUND_DOWN)));
				accountTrnWrapper.CRDRflag= (resultSet.getString("CRDRflag")==null?"":resultSet.getString("CRDRflag").trim());
				accountTrnWrapper.trnCurrency=(resultSet.getString("TrnCurrency")==null?"":resultSet.getString("TrnCurrency").trim());
				
				accountTrnWrapper.recordFound=true;
				
				System.out.println("accountTrnWrapper " );

				vector.addElement(accountTrnWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.accountTrnWrapper = new AccountTrnWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.accountTrnWrapper);
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
	
	
	

	
	
	
	public AbstractWrapper doFundTransfer(String cifNumber,String sourceAccountNo,String benAccountNumber,String trnAmount,
			String trnCurrency,String reason)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//AccountTrnWrapper accountTrnWrapper=new AccountTrnWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();
		
		BigDecimal currentBalance=new BigDecimal(0);
		BigDecimal availableBalance=new BigDecimal(0);
		BigDecimal trnLimit=new BigDecimal(0);
		
		System.out.println("source account :"+ sourceAccountNo);
		System.out.println("ben account :"+ benAccountNumber);
		
		

		formatter.setParseBigDecimal(true);

		
		
	
		try {
			con = getConnection();
			
			
			// parse the string
			BigDecimal transactionAmount = (BigDecimal) formatter.parse(trnAmount);
			
			//---------card payment account
			PreparedStatement pstmt = con.prepareStatement("SELECT TrnLimit "
					+ "from Parameter");
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			

				trnLimit=(resultSet.getBigDecimal("TrnLimit")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TrnLimit"));

					
			}
			
			resultSet.close();
			pstmt.close();
			//-----------------------
			
			//compare with trnlimit from param table
			int res=transactionAmount.compareTo(trnLimit);
			System.out.println("trn amount " + transactionAmount);
			if(res==1){
				
				dataArrayWrapper.recordFound=false;
				dataArrayWrapper.errorCode="LMT001";
				dataArrayWrapper.errorDescription="Transaction Limit Exceeded !!!";
				return dataArrayWrapper;
				
			}
			
			//---------source account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		


			
			pstmt.setBigDecimal(1, currentBalance.subtract(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.subtract(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,sourceAccountNo.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
			//---------beneficiary account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,benAccountNumber.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
			
			pstmt.setBigDecimal(1, currentBalance.add(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.add(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,benAccountNumber.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
						
			//-------insert source account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Fund Transfer MB " + benAccountNumber);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"DR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			//-------insert beneficiary account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,benAccountNumber.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Fund Transfer MB " + sourceAccountNo);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"CR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
			dataArrayWrapper.recordFound=true;
			
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
	
	public AbstractWrapper doCardPayment(String cifNumber,String sourceAccountNo,String creditCardNumber,String trnAmount,
			String trnCurrency,String reason)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//AccountTrnWrapper accountTrnWrapper=new AccountTrnWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();
		
		BigDecimal currentBalance=new BigDecimal(0);
		BigDecimal availableBalance=new BigDecimal(0);
		BigDecimal creditCardOSBalance=new BigDecimal(0);
		String cardPaymentAccount=null;
		BigDecimal trnLimit=new BigDecimal(0); 
		
		System.out.println("source account :"+ sourceAccountNo);
		System.out.println("ben account :"+ creditCardNumber);
		
		

		formatter.setParseBigDecimal(true);

		
		
	
		try {
			con = getConnection();
			
			
			// parse the string
			BigDecimal transactionAmount = (BigDecimal) formatter.parse(trnAmount);
			
			//---------card payment account
			PreparedStatement pstmt = con.prepareStatement("SELECT CardPaymentAccount,TrnLimit "
					+ "from Parameter");
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			

				cardPaymentAccount=(resultSet.getString("CardPaymentAccount")==null?"":resultSet.getString("CardPaymentAccount").trim());
				trnLimit=(resultSet.getBigDecimal("TrnLimit")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TrnLimit"));

					
			}
			
			resultSet.close();
			pstmt.close();
			//-----------------------
			
			//compare with trnlimit from param table
			int res=transactionAmount.compareTo(trnLimit);
			if(res==1){
				
				dataArrayWrapper.recordFound=true;
				dataArrayWrapper.errorCode="LMT001";
				dataArrayWrapper.errorDescription="Transaction Limit Exceeded !!!";
				return dataArrayWrapper;
				
			}
			
			
			//---------source account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		


			System.out.println("trn amount " + transactionAmount);
			
			pstmt.setBigDecimal(1, currentBalance.subtract(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.subtract(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,sourceAccountNo.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
			
			//---------beneficiary account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,cardPaymentAccount.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
			
			pstmt.setBigDecimal(1, currentBalance.add(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.add(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,cardPaymentAccount.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
						
			//-------insert source account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Card Payment to " + creditCardNumber);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"DR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			//-------insert beneficiary account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,cardPaymentAccount.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Card Payment to  " + creditCardNumber);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"CR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
			//--------update card os and limit
			pstmt = con.prepareStatement("SELECT CurrentOSBalance "
					+ "from CreditCards where CIFNumber=? and CreditCardNumber=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,creditCardNumber.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				creditCardOSBalance=(resultSet.getBigDecimal("CurrentOSBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentOSBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			//--
			
			pstmt = con.prepareStatement("UPDATE CreditCards SET CurrentOSBalance=? "
					+ " where CIFNumber=? and CreditCardNumber=? ");
		
			pstmt.setBigDecimal(1, creditCardOSBalance.subtract(transactionAmount));
			pstmt.setString(2,cifNumber.trim());
			pstmt.setString(3,cardPaymentAccount.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			//--------------
			//-------insert credit card trn details
			pstmt = con.prepareStatement("INSERT INTO CreditCardTrn (CIFNumber,CreditCardNumber,TrnDate,TrnType,"
					+ "Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,creditCardNumber.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"CardPay");
			pstmt.setString(5,"Card Payment from " + sourceAccountNo);
			pstmt.setString(6,reason);
			pstmt.setBigDecimal(7, new BigDecimal(trnAmount));
			pstmt.setString(8,"CR");
			pstmt.setString(9,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
			dataArrayWrapper.recordFound=true;
			
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

	
	public AbstractWrapper doStatementRequest(String cifNumber,String accountNumber, String accountCurrency)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//AccountTrnWrapper accountTrnWrapper=new AccountTrnWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
	
		System.out.println("source account :"+ accountNumber);
	

	
		try {
			con = getConnection();
			
			
			
			//-------- statement request
			PreparedStatement pstmt = con.prepareStatement("UPDATE Accounts SET StatementRequest=? "
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
			
			pstmt.setString(1, "Y");
			pstmt.setString(2,cifNumber.trim());
			pstmt.setString(3,accountNumber.trim());
			pstmt.setString(4, accountCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------

			
			dataArrayWrapper.recordFound=true;
			
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
	
	public AbstractWrapper doBillPaymentByAccount(String cifNumber,String sourceAccountNo,String trnAmount,
			String trnCurrency,String reason,String billerName)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//AccountTrnWrapper accountTrnWrapper=new AccountTrnWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();
		
		BigDecimal currentBalance=new BigDecimal(0);
		BigDecimal availableBalance=new BigDecimal(0);
		BigDecimal trnLimit=new BigDecimal(0);
		String billerAccount=null;
		
		System.out.println("source account :"+ sourceAccountNo);
		//System.out.println("ben account :"+ benAccountNumber);
		
		

		formatter.setParseBigDecimal(true);

		
		
	
		try {
			con = getConnection();
			
			
			// parse the string
			BigDecimal transactionAmount = (BigDecimal) formatter.parse(trnAmount);
			
			//---------card payment account
			PreparedStatement pstmt = con.prepareStatement("SELECT BillerAccount,TrnLimit "
					+ "from Parameter");
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{		
				
				billerAccount=(resultSet.getString("BillerAccount")==null?"":resultSet.getString("BillerAccount").trim());
				trnLimit=(resultSet.getBigDecimal("TrnLimit")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TrnLimit"));

					
			}
			
			resultSet.close();
			pstmt.close();
			//-----------------------
			
			//compare with trnlimit from param table
			int res=transactionAmount.compareTo(trnLimit);
			System.out.println("trn amount " + transactionAmount);
			if(res==1){
				
				dataArrayWrapper.recordFound=true;
				dataArrayWrapper.errorCode="LMT001";
				dataArrayWrapper.errorDescription="Transaction Limit Exceeded !!!";
				return dataArrayWrapper;
				
			}
			
			//---------source account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		


			
			pstmt.setBigDecimal(1, currentBalance.subtract(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.subtract(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,sourceAccountNo.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
			//---------beneficiary account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,billerAccount.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
			
			pstmt.setBigDecimal(1, currentBalance.add(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.add(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,billerAccount.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
						
			//-------insert source account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,sourceAccountNo.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Bill Payment MB " + billerName);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"DR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			//-------insert beneficiary account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,billerAccount.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Bill Payment MB " + sourceAccountNo + ":" + billerName);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"CR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
					
			dataArrayWrapper.recordFound=true;
			
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
	
	public AbstractWrapper doBillPaymentByCreditCard(String cifNumber,String creditCardNumber,String trnAmount,
			String trnCurrency,String reason,String billerName)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//AccountTrnWrapper accountTrnWrapper=new AccountTrnWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();
		
		BigDecimal currentBalance=new BigDecimal(0);
		BigDecimal availableBalance=new BigDecimal(0);
		BigDecimal creditCardOSBalance=new BigDecimal(0);
		BigDecimal trnLimit=new BigDecimal(0);
		String cardPaymentAccount=null;
		String billerAccount=null;
		
		System.out.println("credit card number  :"+ creditCardNumber);
		
		formatter.setParseBigDecimal(true);

		
		
	
		try {
			con = getConnection();
			
			
			// parse the string
			BigDecimal transactionAmount = (BigDecimal) formatter.parse(trnAmount);
			
			//---------card payment account
			PreparedStatement pstmt = con.prepareStatement("SELECT CardPaymentAccount,BillerAccount,TrnLimit "
					+ "from Parameter");
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{		
				
				cardPaymentAccount=(resultSet.getString("CardPaymentAccount")==null?"":resultSet.getString("CardPaymentAccount").trim());
				billerAccount=(resultSet.getString("BillerAccount")==null?"":resultSet.getString("BillerAccount").trim());
				trnLimit=(resultSet.getBigDecimal("TrnLimit")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TrnLimit"));

					
			}
			
			resultSet.close();
			pstmt.close();
			//-----------------------
			
			//compare with trnlimit from param table
			int res=transactionAmount.compareTo(trnLimit);
			System.out.println("trn amount " + transactionAmount);
			if(res==1){
				
				dataArrayWrapper.recordFound=true;
				dataArrayWrapper.errorCode="LMT001";
				dataArrayWrapper.errorDescription="Transaction Limit Exceeded !!!";
				return dataArrayWrapper;
				
			}
			
			//---------source account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,cardPaymentAccount.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
	
			pstmt.setBigDecimal(1, currentBalance.subtract(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.subtract(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,cardPaymentAccount.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
			//---------beneficiary account
			pstmt = con.prepareStatement("SELECT CurrentBalance,AvailableBalance "
					+ "from Accounts where CIFNumber=? and AccountNumber=? and  AccountCurrency=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,billerAccount.trim());
			pstmt.setString(3, trnCurrency.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				currentBalance=(resultSet.getBigDecimal("CurrentBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentBalance"));

				availableBalance=(resultSet.getBigDecimal("AvailableBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("AvailableBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			
			pstmt = con.prepareStatement("UPDATE Accounts SET CurrentBalance=?, AvailableBalance=?"
					+ " where CIFNumber=? and AccountNumber=? and AccountCurrency=?");
		
			
			pstmt.setBigDecimal(1, currentBalance.add(transactionAmount));
			pstmt.setBigDecimal(2, availableBalance.add(transactionAmount));
			pstmt.setString(3,cifNumber.trim());
			pstmt.setString(4,billerAccount.trim());
			pstmt.setString(5, trnCurrency.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//---------------
						
			//-------insert source account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,cardPaymentAccount.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Bill Payment MB " + creditCardNumber  + ":" + billerName );
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"DR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			//-------insert beneficiary account trn details
			pstmt = con.prepareStatement("INSERT INTO AccountTrn (CIFNumber,AccountNumber,TrnDate,Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,billerAccount.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"Bill Payment MB " + creditCardNumber + ":" + billerName);
			pstmt.setString(5,reason);
			pstmt.setBigDecimal(6, new BigDecimal(trnAmount));
			pstmt.setString(7,"CR");
			pstmt.setString(8,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
			
			//--------update card os and limit
			pstmt = con.prepareStatement("SELECT CurrentOSBalance "
					+ "from CreditCards where CIFNumber=? and CreditCardNumber=?");
		
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,creditCardNumber.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{			
				creditCardOSBalance=(resultSet.getBigDecimal("CurrentOSBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("CurrentOSBalance"));
			
			}
			
			resultSet.close();
			pstmt.close();
			//--
			
			pstmt = con.prepareStatement("UPDATE CreditCards SET CurrentOSBalance=? "
					+ " where CIFNumber=? and CreditCardNumber=? ");
		
			pstmt.setBigDecimal(1, creditCardOSBalance.subtract(transactionAmount));
			pstmt.setString(2,cifNumber.trim());
			pstmt.setString(3,cardPaymentAccount.trim());
			pstmt.executeUpdate();
			
			pstmt.close();
			//--------------
			//-------insert credit card trn details
			pstmt = con.prepareStatement("INSERT INTO CreditCardTrn (CIFNumber,CreditCardNumber,TrnDate,TrnType,"
					+ "Narration1,Narration2,"
					+ "TrnAmount,CRDRflag,TrnCurrency) values (?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1,cifNumber.trim());
			pstmt.setString(2,creditCardNumber.trim());
			pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(4,"CardPay");
			pstmt.setString(5,"Bill Payment for " + billerName);
			pstmt.setString(6,reason);
			pstmt.setBigDecimal(7, new BigDecimal(trnAmount));
			pstmt.setString(8,"CR");
			pstmt.setString(9,trnCurrency);
			pstmt.executeUpdate();
			pstmt.close();
			//----------------
			
			
			dataArrayWrapper.recordFound=true;
			
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
	
	public AbstractWrapper fetchDashboard(DashboardWrapper dashboardWrapper)throws Exception {

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
		
		
		Vector<Object> vector = new Vector<Object>();


		//BigDecimal savingsAccountBalance=new BigDecimal(0);
		//BigDecimal currentAccountBalance=new BigDecimal(0);
		//BigDecimal depositsBalance=new BigDecimal(0);
		//BigDecimal customerTotalAssets=new BigDecimal(0);
		//BigDecimal loansBalance=new BigDecimal(0);
		//BigDecimal customerTotalLiability=new BigDecimal(0);

		String cifNumber=dashboardWrapper.cifNumber.trim();
		
		System.out.println("Customer number :"+ dashboardWrapper.cifNumber);
	
		try {
			
			dashboardWrapper=new DashboardWrapper();
			con = getConnection();
			
			
			//--------------customer assets------------
			sql="SELECT SUM(AvailableBalance) as  TotalCASA, SUM(DepositAmount) as TotalDeposits, AccountCurrency "
					+ "from Accounts  where CIFNumber=? and "
					+ "AccountType in ('SAVINGS','CURRENT','DEPOSIT') group by AccountCurrency";
			

			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,cifNumber);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) 
			{
				
				
				
				
				BigDecimal customerTotalAssets=new BigDecimal(0);
				BigDecimal totalCASABalance=new BigDecimal(0);
				BigDecimal totalDepositsBalance=new BigDecimal(0);
				
				
				System.out.println("total0... " + resultSet.getBigDecimal("TotalCASA"));
				
				
				customerTotalAssets=customerTotalAssets.add(resultSet.getBigDecimal("TotalCASA")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalCASA"));

				System.out.println("total1... " + customerTotalAssets);
				
				customerTotalAssets=customerTotalAssets.add(resultSet.getBigDecimal("TotalDeposits")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalDeposits"));
				
				System.out.println("total2... " + customerTotalAssets);
				System.out.println("total21... " + resultSet.getBigDecimal("TotalDeposits"));
				
				dashboardWrapper.customerTotalAssets=(customerTotalAssets==null?formatter.format(BigDecimal.ZERO):formatter.format(customerTotalAssets.setScale(2,BigDecimal.ROUND_DOWN)));

				System.out.println("total3... " + dashboardWrapper.customerTotalAssets);
	
				dashboardWrapper.accountCurrency= (resultSet.getString("AccountCurrency")==null?"":resultSet.getString("AccountCurrency").trim());

				System.out.println("total6... " +dashboardWrapper.accountCurrency);
				
				totalCASABalance=resultSet.getBigDecimal("TotalCASA")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalCASA");
				dashboardWrapper.totalCASABalance=(totalCASABalance==null?formatter.format(BigDecimal.ZERO):formatter.format(totalCASABalance.setScale(2,BigDecimal.ROUND_DOWN)));
				
				totalDepositsBalance=resultSet.getBigDecimal("TotalDeposits")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalDeposits");
				dashboardWrapper.totalDepositsBalance=(totalDepositsBalance==null?formatter.format(BigDecimal.ZERO):formatter.format(totalDepositsBalance.setScale(2,BigDecimal.ROUND_DOWN)));
				
				dashboardWrapper.recordFound=true;
				//vector.addElement(dashboardWrapper);
				
			}
			resultSet.close();
			pstmt.close();
			//-----------
			//-------------customer liabilities---loans
			
			
			sql="SELECT SUM(LoanOSBalance) as TotalLoans, AccountCurrency "
					+ "from Accounts  where CIFNumber=? and "
					+ "AccountType in ('LOANS') group by AccountCurrency";
			

			System.out.println("sql " + sql);
			
			pstmt = con.prepareStatement(sql);
			System.out.println(" cif number " + cifNumber);
		
			pstmt.setString(1,cifNumber);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) 
			{
				
				
				
				BigDecimal customerTotalLiability=new BigDecimal(0);
				
				customerTotalLiability=customerTotalLiability.add(resultSet.getBigDecimal("TotalLoans")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalLoans"));
		
				System.out.println("total4... " + customerTotalLiability);
				System.out.println("total41... " + resultSet.getBigDecimal("TotalLoans"));
				
				dashboardWrapper.customerTotalLiability=(customerTotalLiability==null?formatter.format(BigDecimal.ZERO):formatter.format(customerTotalLiability.setScale(2,BigDecimal.ROUND_DOWN)));

				System.out.println("total5... " + dashboardWrapper.customerTotalLiability);
				
				dashboardWrapper.accountCurrency= (resultSet.getString("AccountCurrency")==null?"":resultSet.getString("AccountCurrency").trim());

				System.out.println("total6... " +dashboardWrapper.accountCurrency);
				
				dashboardWrapper.recordFound=true;
				
				
			}			
			resultSet.close();
			pstmt.close();
			
			//--------
			//--------credit card
			/*sql="SELECT SUM(CurrentOSBalance) as TotalCardBalance, LimitCurrency "
					+ "from CreditCards  where CIFNumber=? group by LimitCurrency";
			

			System.out.println("sql " + sql);
			
			pstmt = con.prepareStatement(sql);
			
		
			pstmt.setString(1,cifNumber);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) 
			{
				
				
				
				BigDecimal customerTotalLiability=new BigDecimal(0);
				
				customerTotalLiability=customerTotalLiability.add(resultSet.getBigDecimal("TotalCardBalance")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalCardBalance"));
		
				System.out.println("total4... " + customerTotalLiability);
				System.out.println("total41... " + resultSet.getBigDecimal("TotalCardBalance"));
				
				dashboardWrapper.customerTotalLiability=(customerTotalLiability==null?formatter.format(BigDecimal.ZERO):formatter.format(customerTotalLiability.setScale(2,BigDecimal.ROUND_DOWN)));

				System.out.println("total5... " + dashboardWrapper.customerTotalLiability);
				
				dashboardWrapper.accountCurrency= (resultSet.getString("LimitCurrency")==null?"":resultSet.getString("LimitCurrency").trim());

				System.out.println("total6... " +dashboardWrapper.accountCurrency);
				
				dashboardWrapper.recordFound=true;
				vector.addElement(dashboardWrapper);
				
			}			
			*/
			
			vector.addElement(dashboardWrapper);
			
			if (vector.size()>0)
			{
				dataArrayWrapper.dashboardWrapper = new DashboardWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.dashboardWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
		
			resultSet.close();
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
	
	
	
	//---fetch spend Data
	
	
	public AbstractWrapper fetchSpendData(SpendDataWrapper spendDataWrapper)throws Exception {

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
		
		
		Vector<Object> vector = new Vector<Object>();


		//BigDecimal savingsAccountBalance=new BigDecimal(0);
		//BigDecimal currentAccountBalance=new BigDecimal(0);
		//BigDecimal depositsBalance=new BigDecimal(0);
		//BigDecimal customerTotalAssets=new BigDecimal(0);
		//BigDecimal loansBalance=new BigDecimal(0);
		//BigDecimal customerTotalLiability=new BigDecimal(0);

		String cifNumber=spendDataWrapper.cifNumber.trim();
		
		System.out.println("Customer number :"+ spendDataWrapper.cifNumber);
	
		try {
			
			
			con = getConnection();
			
			
			//--------------customer spend pattern------------
			
			
			sql="SELECT SUM(TrnAmount) as  TotalSpend,  Category "
					+ "from AccountTrn  where CIFNumber=?  group by Category";
			

			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,cifNumber);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()) 
			{
				
				spendDataWrapper=new SpendDataWrapper();
				
				BigDecimal trnAmount=new BigDecimal(0);
				
				
				
				System.out.println("total spend amount " + resultSet.getBigDecimal("TotalSpend"));
				
				
				trnAmount=resultSet.getBigDecimal("TotalSpend")==null?BigDecimal.ZERO:resultSet.getBigDecimal("TotalSpend");
				spendDataWrapper.trnAmount=(trnAmount==null?formatter.format(BigDecimal.ZERO):formatter.format(trnAmount.setScale(2,BigDecimal.ROUND_DOWN)));
				
				spendDataWrapper.category=resultSet.getString("Category").trim();

				
				spendDataWrapper.recordFound=true;
				vector.addElement(spendDataWrapper);
				
			}
			resultSet.close();
			pstmt.close();
			
			
			if (vector.size()>0)
			{
				dataArrayWrapper.spendDataWrapper = new SpendDataWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.spendDataWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
		
			resultSet.close();
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
	
	
	public AbstractWrapper createAccount(AccountsWrapper accountsWrapper)throws Exception {

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
	
		try {
			con = getConnection();
				
				sql="INSERT INTO Accounts(CIFNumber,AccountNumber,CustomerName,AccountStatus, "
						+ "AccountCurrency,CurrentBalance,AvailableBalance,AccountType,AccountDesc,CustomerSegment,LoanType, "
						+ "LoanDesc,LoanStatus ,PrincipalAmount,PrincipalCurrency,InstallmentAmount,InstallmentCurrency, "
						+ "LoanCurrentBalance,LoanStartDate,NextInstlDate,LoanOSBalance,PaidAmount,NoPendingInstl,LastPaidAmount, "
						+ "LastPaymentDate,TotalInstallments,InstallmentsPaid,DepositAmount,DepositCurrency,DepositTenor, "
						+ "DepositDate,DepositMaturityDate,DepositRenewalType,DepositCreditAccount,DepositMaturityAmount, "
						+ "StatementRequest ) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				System.out.println("sql " + sql);
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				System.out.println("Before generate cifNumber " +accountsWrapper.cifNumber);
				
				if(accountsWrapper.cifNumber ==null || accountsWrapper.cifNumber.equals(""))
				{	
					accountsWrapper.cifNumber=generateCIFNumber(accountsWrapper);
				}
				
				accountsWrapper.accountNumber=generateAccountNumber(accountsWrapper);
				
				pstmt.setString(1,Utility.trim(accountsWrapper.cifNumber));
				pstmt.setString(2,Utility.trim(accountsWrapper.accountNumber));
				pstmt.setString(3,Utility.trim(accountsWrapper.customerName));
				pstmt.setString(4,Utility.trim(accountsWrapper.accountStatus));
				pstmt.setString(5,Utility.trim(accountsWrapper.accountCurrency));
				pstmt.setString(6,Utility.trim(accountsWrapper.currentBalance));
				pstmt.setString(7,Utility.trim(accountsWrapper.availableBalance));
				pstmt.setString(8,Utility.trim(accountsWrapper.accountType));
				pstmt.setString(9,Utility.trim(accountsWrapper.accountDesc));
				pstmt.setString(10,Utility.trim(accountsWrapper.customerSegment));
				pstmt.setString(11,Utility.trim(accountsWrapper.loanType));
				pstmt.setString(12,Utility.trim(accountsWrapper.loanDesc));
				pstmt.setString(13,Utility.trim(accountsWrapper.loanStatus));
				pstmt.setString(14,Utility.trim(accountsWrapper.principalAmount));
				pstmt.setString(15,Utility.trim(accountsWrapper.principalCurrency));
				pstmt.setString(16,Utility.trim(accountsWrapper.installmentAmount));
				pstmt.setString(17,Utility.trim(accountsWrapper.installmentCurrency));
				pstmt.setString(18,Utility.trim(accountsWrapper.loanCurrentBalance));
				pstmt.setString(19,Utility.trim(accountsWrapper.loanStartDate));
				pstmt.setString(20,Utility.trim(accountsWrapper.nextInstlDate));
				pstmt.setString(21,Utility.trim(accountsWrapper.loanOSBalance));
				pstmt.setString(22,Utility.trim(accountsWrapper.paidAmount));
				pstmt.setInt(23,accountsWrapper.noPendingInstl);
				pstmt.setString(24,Utility.trim(accountsWrapper.lastPaidAmount));
				pstmt.setString(25,Utility.trim(accountsWrapper.lastPaymentDate));
				pstmt.setInt(26,accountsWrapper.totalInstallments);
				pstmt.setInt(27,accountsWrapper.installmentsPaid);
				pstmt.setString(28,Utility.trim(accountsWrapper.depositAmount));
				pstmt.setString(29,Utility.trim(accountsWrapper.depositCurrency));
				pstmt.setInt(30,accountsWrapper.depositTenor);
				pstmt.setString(31,Utility.trim(accountsWrapper.depositDate));
				pstmt.setString(32,Utility.trim(accountsWrapper.depositMaturityDate));
				pstmt.setString(33,Utility.trim(accountsWrapper.depositRenewalType));
				pstmt.setString(34,Utility.trim(accountsWrapper.depositCreditAccount));
				pstmt.setString(35,Utility.trim(accountsWrapper.depositMaturityAmount));
				pstmt.setString(36,Utility.trim(accountsWrapper.statementRequest));
			
	
		
				pstmt.executeUpdate();
				pstmt.close();
	
				accountsWrapper.recordFound=true;
				
				
				dataArrayWrapper.accountsWrapper=new AccountsWrapper[1];
				dataArrayWrapper.accountsWrapper[0]=accountsWrapper;
				dataArrayWrapper.recordFound=true;
				
				
			
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
	
	public String generateCIFNumber(AccountsWrapper accountsWrapper)throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("ddMMyyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		int cifNumber=0;
		String finalCIFNumber=null;
		
		
		try {
			con = getConnection();
			
			
			sql="SELECT CIFNumber from RMT_Parameter";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				
				cifNumber=resultSet.getInt("CIFNumber");
				System.out.println("CIFNumber" + cifNumber);
				
			}
			
			resultSet.close();
			pstmt.close();
			
			if(cifNumber==0)
			{
				cifNumber=1;
				
			}
			else
			{
				
				cifNumber=cifNumber+1;
			}
				
			sql="UPDATE RMT_Parameter set CIFNumber=?";
			
			
			System.out.println("sql " + sql);
			
			pstmt = con.prepareStatement(sql);
	
			pstmt.setInt(1,cifNumber);
			
			pstmt.executeUpdate();
			pstmt.close();

			int paddingSize=6-String.valueOf(cifNumber).length();
			
			//System.out.println("Account Type " + accountsWrapper.accountType);
			
			
			
			finalCIFNumber=dmyFormat.format(new java.util.Date()).toUpperCase()+String.format("%0" +paddingSize +"d",cifNumber);
			
			
			
			accountsWrapper.recordFound=true;
			
		
		
			dataArrayWrapper.accountsWrapper=new AccountsWrapper[1];
			dataArrayWrapper.accountsWrapper[0]=accountsWrapper;
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Successfully generated CIF Number " + finalCIFNumber);
			
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

		return finalCIFNumber;
	}
	
	public String generateAccountNumber(AccountsWrapper accountsWrapper)throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("ddMMyyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		int accountNumber=0;
		String finalAccountNumber=null;
		
		
		try {
			 con = getConnection();
			
			
			sql="SELECT AccountNumber from RMT_Parameter";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				
				accountNumber=resultSet.getInt("AccountNumber");
				System.out.println("Account Number" + accountNumber);
				
			}
			
			resultSet.close();
			pstmt.close();
			
			if(accountNumber==0)
			{
				accountNumber=1;
				
			}
			else
			{
				
				accountNumber=accountNumber+1;
			}
				
			sql="UPDATE RMT_Parameter set AccountNumber=?";
			
			
			System.out.println("sql " + sql);
			
			pstmt = con.prepareStatement(sql);
	
			pstmt.setInt(1,accountNumber);
			
			pstmt.executeUpdate();
			pstmt.close();

			int paddingSize=6-String.valueOf(accountNumber).length();
			
			//System.out.println("Savings Account  " + accountsWrapper.accountType);
			
			
			
			finalAccountNumber=dmyFormat.format(new java.util.Date()).toUpperCase()+String.format("%0" +paddingSize +"d",accountNumber);
			
			
			
			accountsWrapper.recordFound=true;
			
		
		
			dataArrayWrapper.accountsWrapper=new AccountsWrapper[1];
			dataArrayWrapper.accountsWrapper[0]=accountsWrapper;
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Successfully generated Account Number " + finalAccountNumber);
			
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

		return finalAccountNumber;
	}
	
	
	
	
}
