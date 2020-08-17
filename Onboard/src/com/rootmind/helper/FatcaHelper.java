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

public class FatcaHelper extends Helper {
	
	
	public AbstractWrapper insertFATCA(FatcaWrapper fatcaWrapper)throws Exception {
		
		
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
		
		PreparedStatement pstmt =null;
	
		//System.out.println("Other Nationality :"+ fatcaWrapper.otherNationality);
	
		try {
			
				
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_Fatca WHERE RefNo=?");
				
				
				System.out.println("FATCA insert Details RefNo is" + fatcaWrapper.refNo);
				
				pstmt.setString(1,fatcaWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)updateFATCA(fatcaWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
				
					
				
					sql=" INSERT INTO RMT_Fatca(RefNo, AccClassif, OtherNationality, CountryOfBirth, UsTaxpayerFlag, UsmailAddFlag, "
							+ "UsmailAdd, UsmailAdd2, UsmailAddPOBox, UsmailAddPhone, OwnerUSTelePhNoFlag, UsCountryCode,"
							+ "PhoneNumber, OwnerUSPropFlag, OwnerUSPropPOBox, OwnerUSPropPhoneNo, UsPropAdd1, UsPropAdd2,"
							+ "UsPropAdd3, UBOAddUSFlag, UBOAddUSAdd1, UBOAddUSAdd2, UBOAddUSAdd3, UBOAddUSPhone, UBOAddUSPOBox,"
							+ "SignAuthAddUSFlag, SignUSAdd1, SignUSAdd2, SignUSAdd3, SignUSAddPhone, SignUSAddPOBox, UsGreenCardHolderFlag,	"
							+ "GreenCardNumber, UsSSNHolderFlag, UsSSN,SIUSBenificiaryFlag, SIAmount, SIFrequency, AccountNo,	"
							+ "AccountIBAN, NameOnCard, GeneratedCIS, InfoUpdated,MakerId,MakerDateTime,ApproverId,ApproverDateTime) Values "
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					System.out.println("sql " + sql);
					
				    pstmt = con.prepareStatement(sql);
				
					pstmt.setString(1,Utility.trim(fatcaWrapper.refNo));
					pstmt.setString(2,Utility.trim(fatcaWrapper.accClassif));
					pstmt.setString(3,Utility.trim(fatcaWrapper.otherNationality));
					pstmt.setString(4,Utility.trim(fatcaWrapper.countryOfBirth));
					pstmt.setString(5,Utility.trim(fatcaWrapper.usTaxpayerFlag));
					pstmt.setString(6,Utility.trim(fatcaWrapper.usmailAddFlag));
					pstmt.setString(7,Utility.trim(fatcaWrapper.usmailAdd));
					pstmt.setString(8,Utility.trim(fatcaWrapper.usmailAdd2));
					pstmt.setString(9,Utility.trim(fatcaWrapper.usmailAddPOBox));
					pstmt.setString(10,Utility.trim(fatcaWrapper.usmailAddPhone));
					pstmt.setString(11,Utility.trim(fatcaWrapper.ownerUSTelePhNoFlag));
					pstmt.setString(12,Utility.trim(fatcaWrapper.usCountryCode));
					pstmt.setString(13,Utility.trim(fatcaWrapper.phoneNumber));
					pstmt.setString(14,Utility.trim(fatcaWrapper.ownerUSPropFlag));
					pstmt.setString(15,Utility.trim(fatcaWrapper.ownerUSPropPOBox));
					pstmt.setString(16,Utility.trim(fatcaWrapper.ownerUSPropPhoneNo));
					pstmt.setString(17,Utility.trim(fatcaWrapper.usPropAdd1));
					pstmt.setString(18,Utility.trim(fatcaWrapper.usPropAdd2));
					pstmt.setString(19,Utility.trim(fatcaWrapper.usPropAdd3));
					pstmt.setString(20,Utility.trim(fatcaWrapper.sIUSBenificiaryFlag));
					pstmt.setString(21,Utility.trim(fatcaWrapper.uBOAddUSAdd1));
					pstmt.setString(22,Utility.trim(fatcaWrapper.uBOAddUSAdd2));
					pstmt.setString(23,Utility.trim(fatcaWrapper.uBOAddUSAdd3));
					pstmt.setString(24,Utility.trim(fatcaWrapper.uBOAddUSPhone));
					pstmt.setString(25,Utility.trim(fatcaWrapper.uBOAddUSPOBox));
					pstmt.setString(26,Utility.trim(fatcaWrapper.signAuthAddUSFlag));
					pstmt.setString(27,Utility.trim(fatcaWrapper.signUSAdd1));
					pstmt.setString(28,Utility.trim(fatcaWrapper.signUSAdd2));
					pstmt.setString(29,Utility.trim(fatcaWrapper.signUSAdd3));
					pstmt.setString(30,Utility.trim(fatcaWrapper.signUSAddPhone));
					pstmt.setString(31,Utility.trim(fatcaWrapper.signUSAddPOBox));
					pstmt.setString(32,Utility.trim(fatcaWrapper.usGreenCardHolderFlag));
					pstmt.setString(33,Utility.trim(fatcaWrapper.greenCardNumber));
					pstmt.setString(34,Utility.trim(fatcaWrapper.usSSNHolderFlag));
					pstmt.setString(35,Utility.trim(fatcaWrapper.usSSN));
					pstmt.setString(36,Utility.trim(fatcaWrapper.sIUSBenificiaryFlag));
					pstmt.setString(37,Utility.trim(fatcaWrapper.sIAmount));
					pstmt.setString(38,Utility.trim(fatcaWrapper.sIFrequency));
					pstmt.setString(39,Utility.trim(fatcaWrapper.accountNo));
					pstmt.setString(40,Utility.trim(fatcaWrapper.accountIBN));
					pstmt.setString(41,Utility.trim(fatcaWrapper.nameOnCard));
					pstmt.setString(42,Utility.trim(fatcaWrapper.generatedCIS));
					pstmt.setString(43,Utility.trim(fatcaWrapper.infoUpdated));
					
					pstmt.setString(44,Utility.trim(fatcaWrapper.makerId));
					pstmt.setTimestamp(45,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
					pstmt.setString(46,Utility.trim(fatcaWrapper.approverId));
					pstmt.setTimestamp(47,new java.sql.Timestamp(System.currentTimeMillis()));//ApproverDateTime
					
		
							
					pstmt.executeUpdate();
					pstmt.close();
					
					System.out.println("fatca details inserted");
					
				/*	//maker and checker 
					
					pstmt = con.prepareStatement("INSERT INTO  RMT_Fatca(MakerId,MakerDateTime,ApproverId,ApproverDateTime) values (?,?,?,?)");
		
					pstmt.setString(1,Utility.trim(fatcaWrapper.makerId));
					pstmt.setTimestamp(2,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
					pstmt.setString(3,Utility.trim(fatcaWrapper.approverId));
					pstmt.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));//ApproverDateTime
					pstmt.setString(5,Utility.trim(fatcaWrapper.refNo));
					
					pstmt.executeUpdate();
		
					pstmt.close();
			
					//------------------
		*/			
					
		
					fatcaWrapper.recordFound=true;
					
					dataArrayWrapper.fatcaWrapper=new FatcaWrapper[1];
					dataArrayWrapper.fatcaWrapper[0]=fatcaWrapper;
					dataArrayWrapper.recordFound=true;
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
	
	
	
	
	public AbstractWrapper fetchFATCA(FatcaWrapper fatcaWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
	
		
		Vector<Object> vector = new Vector<Object>();
		
		fatcaWrapper.recordFound=false;

		try {
			
			PopoverHelper	popoverHelper = new PopoverHelper();
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, AccClassif, OtherNationality, CountryOfBirth, UsTaxpayerFlag, UsmailAddFlag, "
					+ "UsmailAdd, UsmailAdd2, UsmailAddPOBox, UsmailAddPhone, OwnerUSTelePhNoFlag, UsCountryCode,PhoneNumber, OwnerUSPropFlag, "
					+ "OwnerUSPropPOBox, OwnerUSPropPhoneNo, UsPropAdd1, UsPropAdd2,UsPropAdd3, UBOAddUSFlag, UBOAddUSAdd1, UBOAddUSAdd2, "
					+ "UBOAddUSAdd3, UBOAddUSPhone, UBOAddUSPOBox,SignAuthAddUSFlag, SignUSAdd1, SignUSAdd2, SignUSAdd3, SignUSAddPhone, "
					+ "SignUSAddPOBox, UsGreenCardHolderFlag, GreenCardNumber, UsSSNHolderFlag, UsSSN,SIUSBenificiaryFlag, SIAmount, SIFrequency, "
					+ "AccountNo, AccountIBAN, NameOnCard, GeneratedCIS, InfoUpdated FROM RMT_Fatca WHERE RefNo=?");

			pstmt.setString(1,Utility.trim(fatcaWrapper.refNo));
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				fatcaWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + fatcaWrapper.refNo);

				fatcaWrapper.accClassif=Utility.trim(resultSet.getString("AccClassif"));
				System.out.println("AccClassif" + fatcaWrapper.accClassif);

				fatcaWrapper.otherNationality=Utility.trim(resultSet.getString("OtherNationality"));
				System.out.println("OtherNationality" + fatcaWrapper.otherNationality);
				
				fatcaWrapper.countryOfBirth=Utility.trim(resultSet.getString("CountryOfBirth"));
				
				fatcaWrapper.usTaxpayerFlag= Utility.trim(resultSet.getString("UsTaxpayerFlag"));
				fatcaWrapper.usmailAddFlag= Utility.trim(resultSet.getString("UsmailAddFlag"));

				fatcaWrapper.usmailAdd=Utility.trim(resultSet.getString("UsmailAdd"));
				
				fatcaWrapper.usmailAdd2=Utility.trim(resultSet.getString("UsmailAdd2"));
				
				fatcaWrapper.usmailAddPOBox=Utility.trim(resultSet.getString("UsmailAddPOBox"));
				fatcaWrapper.usmailAddPhone=Utility.trim(resultSet.getString("UsmailAddPhone"));
				fatcaWrapper.ownerUSTelePhNoFlag=Utility.trim(resultSet.getString("OwnerUSTelePhNoFlag"));
				fatcaWrapper.usCountryCode=Utility.trim(resultSet.getString("UsCountryCode"));
				fatcaWrapper.phoneNumber=Utility.trim(resultSet.getString("PhoneNumber"));
				fatcaWrapper.ownerUSPropFlag=Utility.trim(resultSet.getString("OwnerUSPropFlag"));
				
				fatcaWrapper.ownerUSPropPOBox=Utility.trim(resultSet.getString("OwnerUSPropPOBox"));
			
				fatcaWrapper.ownerUSPropPhoneNo= Utility.trim(resultSet.getString("OwnerUSPropPhoneNo"));
			
				fatcaWrapper.usPropAdd1= Utility.trim(resultSet.getString("UsPropAdd1"));
				fatcaWrapper.usPropAdd2=Utility.trim(resultSet.getString("UsPropAdd2"));
	
				fatcaWrapper.usPropAdd3= Utility.trim(resultSet.getString("UsPropAdd3"));
				
				fatcaWrapper.uBOAddUSFlag= Utility.trim(resultSet.getString("UBOAddUSFlag"));
				fatcaWrapper.uBOAddUSAdd1= Utility.trim(resultSet.getString("UBOAddUSAdd1"));
				fatcaWrapper.uBOAddUSAdd2= Utility.trim(resultSet.getString("UBOAddUSAdd2"));
				fatcaWrapper.uBOAddUSAdd3= Utility.trim(resultSet.getString("UBOAddUSAdd3"));
				fatcaWrapper.uBOAddUSPhone= Utility.trim(resultSet.getString("UBOAddUSPhone"));
				fatcaWrapper.uBOAddUSPOBox= Utility.trim(resultSet.getString("UBOAddUSPOBox"));
				
				
				fatcaWrapper.signAuthAddUSFlag= Utility.trim(resultSet.getString("SignAuthAddUSFlag"));
				fatcaWrapper.signUSAdd1= Utility.trim(resultSet.getString("SignUSAdd1"));
				fatcaWrapper.signUSAdd2= Utility.trim(resultSet.getString("SignUSAdd2"));
				fatcaWrapper.signUSAdd3= Utility.trim(resultSet.getString("SignUSAdd3"));
				fatcaWrapper.signUSAddPhone= Utility.trim(resultSet.getString("SignUSAddPhone"));
				
				fatcaWrapper.signUSAddPOBox=Utility.trim(resultSet.getString("SignUSAddPOBox"));
				fatcaWrapper.usGreenCardHolderFlag=Utility.trim(resultSet.getString("UsGreenCardHolderFlag"));
				fatcaWrapper.greenCardNumber=Utility.trim(resultSet.getString("GreenCardNumber"));
				fatcaWrapper.usSSNHolderFlag=Utility.trim(resultSet.getString("UsSSNHolderFlag"));
				fatcaWrapper.usSSN=Utility.trim(resultSet.getString("UsSSN"));
				fatcaWrapper.sIUSBenificiaryFlag=Utility.trim(resultSet.getString("SIUSBenificiaryFlag"));
				fatcaWrapper.sIAmount=Utility.trim(resultSet.getString("SIAmount"));
				fatcaWrapper.sIFrequency=Utility.trim(resultSet.getString("SIFrequency"));
				fatcaWrapper.accountNo=Utility.trim(resultSet.getString("AccountNo"));
				fatcaWrapper.accountIBN=Utility.trim(resultSet.getString("AccountIBAN"));
				fatcaWrapper.nameOnCard=Utility.trim(resultSet.getString("NameOnCard"));
				fatcaWrapper.generatedCIS=Utility.trim(resultSet.getString("GeneratedCIS"));
				fatcaWrapper.infoUpdated=Utility.trim(resultSet.getString("InfoUpdated"));
	
			
				
				fatcaWrapper.recordFound=true;
				
				
				fatcaWrapper.accClassifValue=popoverHelper.fetchPopoverDesc(fatcaWrapper.accClassif, "NATIONALITY");
				fatcaWrapper.otherNationalityValue=popoverHelper.fetchPopoverDesc(fatcaWrapper.otherNationality, "NATIONALITY");
				fatcaWrapper.countryOfBirthValue=popoverHelper.fetchPopoverDesc(fatcaWrapper.countryOfBirth, "NATIONALITY");
				
				/*if (resultSet.getString("NO_OF_INST_OVERDUE")!=null && !resultSet.getString("NO_OF_INST_OVERDUE").trim().equals(""))
				{
					finnoneWrapper.noofInstOverDue=new BigDecimal(resultSet.getString("NO_OF_INST_OVERDUE"));
				}else
				{
					finnoneWrapper.noofInstOverDue=null;
				}
				finnoneWrapper.amountOverDue=new BigDecimal(resultSet.getString("AMOUNT_OVERDUE"));
				finnoneWrapper.noofInstOutStanding=new BigDecimal(resultSet.getString("NO_OF_INST_OUTSTANDING"));
				finnoneWrapper.amountOutStanding=new BigDecimal(resultSet.getString("AMOUNT_OUTSTANDING"));
				finnoneWrapper.principalOverDue=new BigDecimal(resultSet.getString("PRINCIPLE_OVERDUE"));
				finnoneWrapper.principalOutStanding=new BigDecimal(resultSet.getString("PRINCIPLE_OUTSTANDING"));
				finnoneWrapper.interestOverDue=new BigDecimal(resultSet.getString("INTEREST_OVERDUE"));
				finnoneWrapper.interestOutStanding=new BigDecimal(resultSet.getString("INTEREST_OUTSTANDING"));
				System.out.println("bucket " + resultSet.getString("BUCKET"));
				finnoneWrapper.bucket=resultSet.getString("BUCKET");
				finnoneWrapper.delinquencyString= resultSet.getString("DELINQUENCY_STRING");
				finnoneWrapper.currentAccountBalance= new BigDecimal(resultSet.getString("CURRENT_ACC_BALANCE"));
				finnoneWrapper.lastPaymentAmount=new BigDecimal(resultSet.getString("LAST_PAYMENT_AMT"));
				finnoneWrapper.lastPaymentDate= resultSet.getString("LAST_PAYMENT_DATE");*/
				
				
				vector.addElement(fatcaWrapper);
				
				System.out.println("FatcaDetails info data fetch completed");

			}
 
			if (vector.size()>0)
			{
				dataArrayWrapper.fatcaWrapper = new FatcaWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.fatcaWrapper);
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
	
	
	
	
	public AbstractWrapper updateFATCA(FatcaWrapper fatcaWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//FatcaWrapper fatcaWrapper=new FatcaWrapper();
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper(); 
		
		System.out.println("updateFatca");
		
		PreparedStatement pstmt = null;
		
		try{
			
			
			con = getConnection();
			
			pstmt = con.prepareStatement("SELECT RefNo FROM RMT_Fatca WHERE RefNo=?");
			
			
			System.out.println("FATCA Update Details RefNo is" + fatcaWrapper.refNo);
			
			pstmt.setString(1,fatcaWrapper.refNo.trim());
			
			resultSet = pstmt.executeQuery();
			if (!resultSet.next()) 
			{
				resultSet.close();
				pstmt.close();
				dataArrayWrapper=(DataArrayWrapper)insertFATCA(fatcaWrapper);
			}
			else
			{
			
				 resultSet.close();
				 pstmt.close();
			
					
				
				 pstmt = con.prepareStatement("UPDATE RMT_Fatca SET AccClassif=?, OtherNationality=?, CountryOfBirth=?, "
						+ "UsTaxpayerFlag=?, UsmailAddFlag=?,UsmailAdd=?, UsmailAdd2=?, UsmailAddPOBox=?, UsmailAddPhone=?, OwnerUSTelePhNoFlag=?, "
						+ " UsCountryCode=?, PhoneNumber=?, OwnerUSPropFlag=?, OwnerUSPropPOBox=?, OwnerUSPropPhoneNo=?, UsPropAdd1=?, UsPropAdd2=?, "
						+ "UsPropAdd3=?, UBOAddUSFlag=?, UBOAddUSAdd1=?, UBOAddUSAdd2=?, UBOAddUSAdd3=?, UBOAddUSPhone=?, UBOAddUSPOBox=?, "
						+ "SignAuthAddUSFlag=?, SignUSAdd1=?, SignUSAdd2=?, SignUSAdd3=?, SignUSAddPhone=?, SignUSAddPOBox=?, UsGreenCardHolderFlag=?,	"
						+ "GreenCardNumber=?, UsSSNHolderFlag=?, UsSSN=?,SIUSBenificiaryFlag=?, SIAmount=?, SIFrequency=?, AccountNo=?, "
						+ "AccountIBAN=?, NameOnCard=?, GeneratedCIS=?, InfoUpdated=? where RefNo=? ");
				
						pstmt.setString(1,Utility.trim(fatcaWrapper.accClassif));
						pstmt.setString(2,Utility.trim(fatcaWrapper.otherNationality));
						pstmt.setString(3,Utility.trim(fatcaWrapper.countryOfBirth));
						pstmt.setString(4,Utility.trim(fatcaWrapper.usTaxpayerFlag));
						pstmt.setString(5,Utility.trim(fatcaWrapper.usmailAddFlag));
						pstmt.setString(6,Utility.trim(fatcaWrapper.usmailAdd));
						pstmt.setString(7,Utility.trim(fatcaWrapper.usmailAdd2));
						pstmt.setString(8,Utility.trim(fatcaWrapper.uBOAddUSPOBox));
						pstmt.setString(9,Utility.trim(fatcaWrapper.usmailAddPhone));
						pstmt.setString(10,Utility.trim(fatcaWrapper.ownerUSTelePhNoFlag));
						pstmt.setString(11,Utility.trim(fatcaWrapper.usCountryCode));
						pstmt.setString(12,Utility.trim(fatcaWrapper.phoneNumber));
						pstmt.setString(13,Utility.trim(fatcaWrapper.ownerUSPropFlag));
						pstmt.setString(14,Utility.trim(fatcaWrapper.ownerUSPropPOBox));
						pstmt.setString(15,Utility.trim(fatcaWrapper.ownerUSPropPhoneNo));
						pstmt.setString(16,Utility.trim(fatcaWrapper.usPropAdd1));
						pstmt.setString(17,Utility.trim(fatcaWrapper.usPropAdd2));
						pstmt.setString(18,Utility.trim(fatcaWrapper.usPropAdd3));
						pstmt.setString(19,Utility.trim(fatcaWrapper.uBOAddUSFlag));
						pstmt.setString(20,Utility.trim(fatcaWrapper.uBOAddUSAdd1));
						pstmt.setString(21,Utility.trim(fatcaWrapper.uBOAddUSAdd2));
						pstmt.setString(22,Utility.trim(fatcaWrapper.uBOAddUSAdd3));
						pstmt.setString(23,Utility.trim(fatcaWrapper.uBOAddUSPhone));
						pstmt.setString(24,Utility.trim(fatcaWrapper.uBOAddUSPOBox));
						pstmt.setString(25,Utility.trim(fatcaWrapper.signAuthAddUSFlag));
						pstmt.setString(26,Utility.trim(fatcaWrapper.signUSAdd1));
						pstmt.setString(27,Utility.trim(fatcaWrapper.signUSAdd2));
						pstmt.setString(28,Utility.trim(fatcaWrapper.signUSAdd3));
						pstmt.setString(29,Utility.trim(fatcaWrapper.signUSAddPhone));
						pstmt.setString(30,Utility.trim(fatcaWrapper.signUSAddPOBox));
						pstmt.setString(31,Utility.trim(fatcaWrapper.usGreenCardHolderFlag));
						pstmt.setString(32,Utility.trim(fatcaWrapper.greenCardNumber));
						pstmt.setString(33,Utility.trim(fatcaWrapper.usSSNHolderFlag));
						pstmt.setString(34,Utility.trim(fatcaWrapper.usSSN));
						pstmt.setString(35,Utility.trim(fatcaWrapper.sIUSBenificiaryFlag));
						pstmt.setString(36,Utility.trim(fatcaWrapper.sIAmount));
						pstmt.setString(37,Utility.trim(fatcaWrapper.sIFrequency));
						pstmt.setString(38,Utility.trim(fatcaWrapper.accountNo));
						pstmt.setString(39,Utility.trim(fatcaWrapper.accountIBN));
						pstmt.setString(40,Utility.trim(fatcaWrapper.nameOnCard));
						pstmt.setString(41,Utility.trim(fatcaWrapper.generatedCIS));
						pstmt.setString(42,Utility.trim(fatcaWrapper.infoUpdated));
				
						pstmt.setString(43,Utility.trim(fatcaWrapper.refNo));
				
				
		
				
						pstmt.executeUpdate();
	
						pstmt.close();
	
				
						fatcaWrapper.recordFound=true;
				
						dataArrayWrapper.fatcaWrapper=new FatcaWrapper[1];
						dataArrayWrapper.fatcaWrapper[0]=fatcaWrapper;
						dataArrayWrapper.recordFound=true;
						System.out.println("fatca details updated " );
				
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
