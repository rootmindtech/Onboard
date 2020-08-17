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


public class AccountDetailsHelper extends Helper {
	
	public AbstractWrapper insertAccountDetails(AccountDetailsWrapper accountDetailsWrapper)throws Exception {

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
				
				sql="INSERT INTO RMT_AccountDetails(RefNo, AccountNo, AccountIBAN, AccountType, Currency, StatementCycle, OnlineBankTrans, AccOperationInstrs, AccountTitle, "
						+ "DebitCardRequired, NameOnCard, FavoriteEmirate, DebitCardDelvChnl, PinMailerDelvChnl, TransferSalary, ChequeBookRequired, OverdraftType, InterestOption, "
						+ "InterestRate, SupplCardReq, PurposeCode, BusinessCode, InsuranceCode, CampaignCode, SellerID, SellerChannel, "
						+ "SchemeID) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				System.out.println("sql " + sql);
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,Utility.trim(accountDetailsWrapper.refNo));
				pstmt.setString(2,Utility.trim(accountDetailsWrapper.accountNo));
				pstmt.setString(3,Utility.trim(accountDetailsWrapper.accountIBAN));
				pstmt.setString(4,Utility.trim(accountDetailsWrapper.accountType));
				pstmt.setString(5,Utility.trim(accountDetailsWrapper.currency));
				pstmt.setString(6,Utility.trim(accountDetailsWrapper.statementCycle));
				pstmt.setString(7,Utility.trim(accountDetailsWrapper.onlineBankTrans));
				pstmt.setString(8,Utility.trim(accountDetailsWrapper.accOperInstrs));
				pstmt.setString(9,Utility.trim(accountDetailsWrapper.accountTitle));
				pstmt.setString(10,Utility.trim(accountDetailsWrapper.debitCardRequired));
				pstmt.setString(11,Utility.trim(accountDetailsWrapper.nameCard));
				pstmt.setString(12,Utility.trim(accountDetailsWrapper.favEmirate));
				pstmt.setString(13,Utility.trim(accountDetailsWrapper.debitCardDelvChnl));
				pstmt.setString(14,Utility.trim(accountDetailsWrapper.pinMailerDelvChnl));
				pstmt.setString(15,Utility.trim(accountDetailsWrapper.transferSalary));
				pstmt.setString(16,Utility.trim(accountDetailsWrapper.chequeBookRequired));
				pstmt.setString(17,Utility.trim(accountDetailsWrapper.overDraftType));
				pstmt.setString(18,Utility.trim(accountDetailsWrapper.interestOption));
				pstmt.setString(19,Utility.trim(accountDetailsWrapper.interestRate));
				pstmt.setString(20,Utility.trim(accountDetailsWrapper.supplCardReq));
				pstmt.setString(21,Utility.trim(accountDetailsWrapper.purposeCode));
				pstmt.setString(22,Utility.trim(accountDetailsWrapper.businessCode));
				pstmt.setString(23,Utility.trim(accountDetailsWrapper.insuranceCode));
				pstmt.setString(24,Utility.trim(accountDetailsWrapper.campaignCode));
				pstmt.setString(25,Utility.trim(accountDetailsWrapper.sellerID));
				pstmt.setString(26,Utility.trim(accountDetailsWrapper.sellerChannel));
				pstmt.setString(27,Utility.trim(accountDetailsWrapper.schemeID));
			
	
		
				pstmt.executeUpdate();
				pstmt.close();
				
	/*			//---------update PathStatus
				
				pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET PRODUCT=? WHERE RefNo=?");
				
				pstmt.setString(1,"Y");
				pstmt.setString(2,accountDetailsWrapper.refNo);
				pstmt.executeUpdate();
				
				pstmt.close();
				//-----------------------
	*/
				
				//---------update PathStatus
				PathStatusHelper pathStatusHelper=new PathStatusHelper();
				dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(accountDetailsWrapper.refNo),"PRODUCT");
				//--
				
				
				accountDetailsWrapper.recordFound=true;
				
				
				dataArrayWrapper.accountDetailsWrapper=new AccountDetailsWrapper[1];
				dataArrayWrapper.accountDetailsWrapper[0]=accountDetailsWrapper;
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
	
	
	
	public AbstractWrapper fetchAccountDetails(AccountDetailsWrapper accountDetailsWrapper)throws Exception {

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
				
				PopoverHelper popoverHelper=new PopoverHelper();
				
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, AccountNo, AccountIBAN, AccountType, Currency, StatementCycle, OnlineBankTrans, "
						+ " AccOperationInstrs, AccountTitle, DebitCardRequired, NameOnCard, FavoriteEmirate, DebitCardDelvChnl, PinMailerDelvChnl, TransferSalary, ChequeBookRequired, "
						+ " OverdraftType, InterestOption, InterestRate, SupplCardReq, PurposeCode, BusinessCode, InsuranceCode, CampaignCode, SellerID, "
						+ " SellerChannel, SchemeID FROM RMT_AccountDetails WHERE RefNo=?");
			
				
				System.out.println("PersonalDetails RefNo is" + accountDetailsWrapper.refNo);
				
				pstmt.setString(1,accountDetailsWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					 accountDetailsWrapper= new AccountDetailsWrapper();
					
					
					accountDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + accountDetailsWrapper.refNo);
					
	
					accountDetailsWrapper.accountNo=Utility.trim(resultSet.getString("AccountNo"));
					System.out.println("AccountNo " + accountDetailsWrapper.accountNo);
					
					accountDetailsWrapper.accountIBAN=Utility.trim(resultSet.getString("AccountIBAN"));
					
					accountDetailsWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					
					accountDetailsWrapper.currency=Utility.trim(resultSet.getString("Currency"));
					
					accountDetailsWrapper.statementCycle=Utility.trim(resultSet.getString("StatementCycle"));
					
					accountDetailsWrapper.onlineBankTrans=Utility.trim(resultSet.getString("OnlineBankTrans"));
					
					accountDetailsWrapper.accOperInstrs=Utility.trim(resultSet.getString("AccOperationInstrs"));				
		
					accountDetailsWrapper.accountTitle=Utility.trim(resultSet.getString("AccountTitle"));
					
					accountDetailsWrapper.debitCardRequired=Utility.trim(resultSet.getString("DebitCardRequired"));
					
					accountDetailsWrapper.nameCard=Utility.trim(resultSet.getString("NameOnCard"));
					
					accountDetailsWrapper.favEmirate=Utility.trim(resultSet.getString("FavoriteEmirate"));
					
					accountDetailsWrapper.debitCardDelvChnl=Utility.trim(resultSet.getString("DebitCardDelvChnl"));
					
					accountDetailsWrapper.pinMailerDelvChnl=Utility.trim(resultSet.getString("PinMailerDelvChnl"));
					
					accountDetailsWrapper.transferSalary=Utility.trim(resultSet.getString("TransferSalary"));
					
					accountDetailsWrapper.chequeBookRequired=Utility.trim(resultSet.getString("ChequeBookRequired"));
					
					
					accountDetailsWrapper.overDraftType=Utility.trim(resultSet.getString("OverdraftType"));
					
					accountDetailsWrapper.interestOption=Utility.trim(resultSet.getString("InterestOption"));
					
					accountDetailsWrapper.interestRate=Utility.trim(resultSet.getString("InterestRate"));
					
					accountDetailsWrapper.supplCardReq=Utility.trim(resultSet.getString("SupplCardReq"));
					
					
					accountDetailsWrapper.purposeCode=Utility.trim(resultSet.getString("PurposeCode"));
					
					accountDetailsWrapper.businessCode=Utility.trim(resultSet.getString("BusinessCode"));
					
					accountDetailsWrapper.insuranceCode=Utility.trim(resultSet.getString("InsuranceCode"));
					
					accountDetailsWrapper.campaignCode=Utility.trim(resultSet.getString("CampaignCode"));
					
					accountDetailsWrapper.sellerID=Utility.trim(resultSet.getString("SellerID"));
					
					accountDetailsWrapper.sellerChannel=Utility.trim(resultSet.getString("SellerChannel"));
					
					accountDetailsWrapper.schemeID=Utility.trim(resultSet.getString("SchemeID"));
					
				
				
					accountDetailsWrapper.recordFound=true;
					
					
					
					accountDetailsWrapper.accountTypeValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.accountType, "AccountType");
					accountDetailsWrapper.currencyValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.currency, "Currency");
					System.out.println("Currency Value " + accountDetailsWrapper.currencyValue);
					
					accountDetailsWrapper.statementCycleValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.statementCycle, "StatementCycle");
					accountDetailsWrapper.onlineBankTransValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.onlineBankTrans, "Decision");
					accountDetailsWrapper.debitCardRequiredValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.debitCardRequired, "Decision");
					accountDetailsWrapper.favEmirateValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.favEmirate, "FavouriteCity");
					accountDetailsWrapper.debitCardDelvChnlValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.debitCardDelvChnl, "DebitCardDeliveryChnl");
					accountDetailsWrapper.pinMailerDelvChnlValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.pinMailerDelvChnl, "PinMailer");
					accountDetailsWrapper.transferSalaryValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.transferSalary, "Decision");
					
					accountDetailsWrapper.chequeBookRequiredValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.chequeBookRequired, "Decision");
					accountDetailsWrapper.interestOptionValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.interestOption, "Decision");
					//accountDetailsWrapper.interestRateValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.interestRate, "DECISION");
					accountDetailsWrapper.supplCardReqValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.supplCardReq, "Decision");
							
					accountDetailsWrapper.purposeCodeValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.purposeCode, "PurposeCode");
					accountDetailsWrapper.accOperInstrsValue=popoverHelper.fetchPopoverDesc(accountDetailsWrapper.accOperInstrs, "AccountOperationInstr");
					
					
					
					accountDetailsWrapper.pinMailerDelvChnl=Utility.trim(resultSet.getString("PinMailerDelvChnl"));
					accountDetailsWrapper.accOperInstrs=Utility.trim(resultSet.getString("AccOperationInstrs"));	
			
					vector.addElement(accountDetailsWrapper);
					
					
				

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.accountDetailsWrapper = new AccountDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.accountDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.accountDetailsWrapper = new AccountDetailsWrapper[1];
				dataArrayWrapper.accountDetailsWrapper[0]= accountDetailsWrapper;
				dataArrayWrapper.recordFound=true;

				
			}
 
 
			if(resultSet !=null) resultSet.close();
			pstmt.close();

			System.out.println("Fetch Account Details Successful");
			
			
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
	public AccountDetailsWrapper fetchAccountDetails(AccountDetailsWrapper accountDetailsWrapper,String refNo)throws Exception {



		try {

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchAccountDetails(accountDetailsWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				accountDetailsWrapper = dataArrayWrapper.accountDetailsWrapper[0];
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

		return accountDetailsWrapper;

		}
	
	public AbstractWrapper updateAccountDetails(AccountDetailsWrapper accountDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
	

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		//String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);

		
		System.out.println("update AccountDetails");
		

		PreparedStatement pstmt=null;
		
		
		try {
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AccountDetails WHERE RefNo=?");
				
			
				System.out.println("Account Details RefNo is" + accountDetailsWrapper.refNo);
				
				pstmt.setString(1,accountDetailsWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertAccountDetails(accountDetailsWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();

	
					
					 pstmt = con.prepareStatement("UPDATE RMT_AccountDetails SET AccountNo=?, AccountIBAN=?, AccountType=?, Currency=?, StatementCycle=?, OnlineBankTrans=?, AccOperationInstrs=?, AccountTitle=?, "
							+ "DebitCardRequired=?, NameOnCard=?, FavoriteEmirate=?, DebitCardDelvChnl=?, PinMailerDelvChnl=?, TransferSalary=?, ChequeBookRequired=?, OverdraftType=?, InterestOption=?, "
							+ "InterestRate=?, SupplCardReq=?, PurposeCode=?, BusinessCode=?, InsuranceCode=?, CampaignCode=?, SellerID=?, SellerChannel=?, "
							+ "SchemeID=?  where RefNo=? ");
					
					pstmt.setString(1,Utility.trim(accountDetailsWrapper.accountNo));
					pstmt.setString(2,Utility.trim(accountDetailsWrapper.accountIBAN));
					pstmt.setString(3,Utility.trim(accountDetailsWrapper.accountType));
					pstmt.setString(4,Utility.trim(accountDetailsWrapper.currency));
					pstmt.setString(5,Utility.trim(accountDetailsWrapper.statementCycle));
					pstmt.setString(6,Utility.trim(accountDetailsWrapper.onlineBankTrans));
					pstmt.setString(7,Utility.trim(accountDetailsWrapper.accOperInstrs));
					pstmt.setString(8,Utility.trim(accountDetailsWrapper.accountTitle));
					pstmt.setString(9,Utility.trim(accountDetailsWrapper.debitCardRequired));			
					pstmt.setString(10,Utility.trim(accountDetailsWrapper.nameCard));
					
					pstmt.setString(11,Utility.trim(accountDetailsWrapper.favEmirate));
					
					pstmt.setString(12,Utility.trim(accountDetailsWrapper.debitCardDelvChnl));
					pstmt.setString(13,Utility.trim(accountDetailsWrapper.pinMailerDelvChnl));
					pstmt.setString(14,Utility.trim(accountDetailsWrapper.transferSalary));
					pstmt.setString(15,Utility.trim(accountDetailsWrapper.chequeBookRequired));
					pstmt.setString(16,Utility.trim(accountDetailsWrapper.overDraftType));
					pstmt.setString(17,Utility.trim(accountDetailsWrapper.interestOption));
					pstmt.setString(18,Utility.trim(accountDetailsWrapper.interestRate));
					pstmt.setString(19,Utility.trim(accountDetailsWrapper.supplCardReq));
					pstmt.setString(20,Utility.trim(accountDetailsWrapper.purposeCode));
					pstmt.setString(21,Utility.trim(accountDetailsWrapper.businessCode));
					pstmt.setString(22,Utility.trim(accountDetailsWrapper.insuranceCode));
					pstmt.setString(23,Utility.trim(accountDetailsWrapper.campaignCode));
					pstmt.setString(24,Utility.trim(accountDetailsWrapper.sellerID));
					pstmt.setString(25,Utility.trim(accountDetailsWrapper.sellerChannel));
					pstmt.setString(26,Utility.trim(accountDetailsWrapper.schemeID));
				
					pstmt.setString(27,Utility.trim(accountDetailsWrapper.refNo));
					
					pstmt.executeUpdate();
		
					pstmt.close();
					
/*					//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET PRODUCT=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,accountDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
		*/
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(accountDetailsWrapper.refNo),"PRODUCT");
					//--
					
					accountDetailsWrapper.recordFound=true;
					
					dataArrayWrapper.accountDetailsWrapper=new AccountDetailsWrapper[1];
					dataArrayWrapper.accountDetailsWrapper[0]=accountDetailsWrapper;
					dataArrayWrapper.recordFound=true;
					
					System.out.println("Account details updated");
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

	
	
	
}