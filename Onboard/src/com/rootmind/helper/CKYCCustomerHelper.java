package com.rootmind.helper;

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
import com.rootmind.wrapper.CKYCCustomerWrapper;

public class CKYCCustomerHelper extends Helper{
	
	public AbstractWrapper insertCKYCCustomer(UsersWrapper usersProfileWrapper,CKYCCustomerWrapper ckycCustomerWrapper)throws Exception {
		
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
			
			sql=" INSERT INTO CKYC_Customer(RefNo, ApplicationType, KYCNo, AccountType, NamePrefix, FirstName, MiddleName, LastName, "
					+ " MaidenNamePrefix, MaidenFirstName, MaidenMiddleName, MaidenLastName, FatherSpousePrefix, FatherSpouseFirstName, FatherSpouseMiddleName, FatherSpouseLastName,"
					+ " MotherPrefix, MotherFirstName, MotherMiddleName, MotherLastName, DOB, Gender, MaritalStatus, Citizenship, "
					+ " ResidenceStatus, OccupationType, ResidenceTaxPurpose, ISO3166CountryCode, TIN, POB, ISO3166CountryCodeBirth, Remarks,"
					+ " ApplicationDeclarationDate, ApplicationDeclarationPlace, DocumentsReceived, KYCVerifiedDate, KYCVerifiedEmpName, KYCVerifiedEmpCode, KYCVerifiedEmpDesignation, KYCVerifiedEmpBranch,"
					+ " InstitutionDetails, InstitutionCode,RecordStatus, MakerId,MakerDateTime) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			System.out.println("sql " + sql);
			
		
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			
		
			ckycCustomerWrapper.refNo=generateRefNo();
			

			pstmt.setString(1,Utility.trim(ckycCustomerWrapper.refNo));
			pstmt.setString(2,Utility.trim(ckycCustomerWrapper.applicationType));
			pstmt.setString(3,Utility.trim(ckycCustomerWrapper.kycNo));
			pstmt.setString(4,Utility.trim(ckycCustomerWrapper.accountType));
			pstmt.setString(5,Utility.trim(ckycCustomerWrapper.namePrefix));
			pstmt.setString(6,Utility.trim(ckycCustomerWrapper.firstName));
			pstmt.setString(7,Utility.trim(ckycCustomerWrapper.middleName));
			pstmt.setString(8,Utility.trim(ckycCustomerWrapper.lastName));
			pstmt.setString(9,Utility.trim(ckycCustomerWrapper.maidenNamePrefix));
			pstmt.setString(10,Utility.trim(ckycCustomerWrapper.maidenFirstName));
			pstmt.setString(11,Utility.trim(ckycCustomerWrapper.maidenMiddleName));
			pstmt.setString(12,Utility.trim(ckycCustomerWrapper.maidenLastName));
			pstmt.setString(13,Utility.trim(ckycCustomerWrapper.fatherSpousePrefix));
			pstmt.setString(14,Utility.trim(ckycCustomerWrapper.fatherSpouseFirstName));
			pstmt.setString(15,Utility.trim(ckycCustomerWrapper.fatherSpouseMiddleName));
			pstmt.setString(16,Utility.trim(ckycCustomerWrapper.fatherSpouseLastName));
			pstmt.setString(17,Utility.trim(ckycCustomerWrapper.motherPrefix));
			pstmt.setString(18,Utility.trim(ckycCustomerWrapper.motherFirstName));
			pstmt.setString(19,Utility.trim(ckycCustomerWrapper.motherMiddleName));
			pstmt.setString(20,Utility.trim(ckycCustomerWrapper.motherLastName));
			
			pstmt.setDate(21,Utility.getDate(ckycCustomerWrapper.dob));
			pstmt.setString(22,Utility.trim(ckycCustomerWrapper.gender));
			pstmt.setString(23,Utility.trim(ckycCustomerWrapper.maritalStatus));
			pstmt.setString(24,Utility.trim(ckycCustomerWrapper.citizenship));
			pstmt.setString(25,Utility.trim(ckycCustomerWrapper.residenceStatus));
			pstmt.setString(26,Utility.trim(ckycCustomerWrapper.occupationType));
			pstmt.setString(27,Utility.trim(ckycCustomerWrapper.residenceTaxPurpose));
			pstmt.setString(28,Utility.trim(ckycCustomerWrapper.iso3166CountryCode));
			pstmt.setString(29,Utility.trim(ckycCustomerWrapper.tin)); 
			pstmt.setString(30,Utility.trim(ckycCustomerWrapper.pob));
			pstmt.setString(31,Utility.trim(ckycCustomerWrapper.iso3166CountryCodeBirth));
			pstmt.setString(32,Utility.trim(ckycCustomerWrapper.remarks));
			pstmt.setDate(33,Utility.getDate(ckycCustomerWrapper.appDeclDate));
			pstmt.setString(34,Utility.trim(ckycCustomerWrapper.appDeclPlace));
			pstmt.setString(35,Utility.trim(ckycCustomerWrapper.docsReceived));
			pstmt.setDate(36,Utility.getDate(ckycCustomerWrapper.kycVerifiedDate));
			pstmt.setString(37,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpName));
			pstmt.setString(38,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpCode));
			pstmt.setString(39,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpDesignation));
			pstmt.setString(40,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpBranch));
			pstmt.setString(41,Utility.trim(ckycCustomerWrapper.instDetails));
			pstmt.setString(42,Utility.trim(ckycCustomerWrapper.instCode));
			pstmt.setString(43,Utility.trim(ckycCustomerWrapper.recordStatus));
			pstmt.setString(44,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
			System.out.println("insertCKYCCustomer Userid "+usersProfileWrapper.userid);
			
			pstmt.setTimestamp(45,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
			pstmt.executeUpdate();
			pstmt.close();
			
			
			ckycCustomerWrapper.recordFound=true;

		
			dataArrayWrapper.ckycCustomerWrapper=new CKYCCustomerWrapper[1];
			dataArrayWrapper.ckycCustomerWrapper[0]=ckycCustomerWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			
			System.out.println("Successfully insertCKYCCustomer into CKYC_Customer");
			
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

	public AbstractWrapper updateCKYCCustomer(UsersWrapper usersProfileWrapper,CKYCCustomerWrapper ckycCustomerWrapper)throws Exception {
		
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
		
		String sql=null;
		
		System.out.println("ckycCustomer Update");
		
		try {
				con = getConnection();
				
				
				sql="UPDATE CKYC_Customer SET  ApplicationType=?, KYCNo=?, AccountType=?, NamePrefix=?, FirstName=?, MiddleName=?, LastName=?, "
						+ " MaidenNamePrefix=?, MaidenFirstName=?, MaidenMiddleName=?, MaidenLastName=?, FatherSpousePrefix=?, FatherSpouseFirstName=?, "
						+ " FatherSpouseMiddleName=?, FatherSpouseLastName=?, MotherPrefix=?, MotherFirstName=?, MotherMiddleName=?, MotherLastName=?, "
						+ " DOB=?, Gender=?, MaritalStatus=?, Citizenship=?, ResidenceStatus=?, OccupationType=?, ResidenceTaxPurpose=?, ISO3166CountryCode=?, "
						+ " TIN=?, POB=?, ISO3166CountryCodeBirth=?, Remarks=?, ApplicationDeclarationDate=?, ApplicationDeclarationPlace=?, DocumentsReceived=?, "
						+ " KYCVerifiedDate=?, KYCVerifiedEmpName=?, KYCVerifiedEmpCode=?, KYCVerifiedEmpDesignation=?, KYCVerifiedEmpBranch=?, InstitutionDetails=?, "
						+ " InstitutionCode=?,RecordStatus=?, ModifierId=?, ModifierDateTime=? WHERE RefNo=?";
				
				System.out.println("ckycCustomer SQL is ");
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				
				
				pstmt.setString(1,Utility.trim(ckycCustomerWrapper.applicationType));
				pstmt.setString(2,Utility.trim(ckycCustomerWrapper.kycNo));
				pstmt.setString(3,Utility.trim(ckycCustomerWrapper.accountType));
				pstmt.setString(4,Utility.trim(ckycCustomerWrapper.namePrefix));
				pstmt.setString(5,Utility.trim(ckycCustomerWrapper.firstName));
				pstmt.setString(6,Utility.trim(ckycCustomerWrapper.middleName));
				pstmt.setString(7,Utility.trim(ckycCustomerWrapper.lastName));
				pstmt.setString(8,Utility.trim(ckycCustomerWrapper.maidenNamePrefix));
				pstmt.setString(9,Utility.trim(ckycCustomerWrapper.maidenFirstName));
				pstmt.setString(10,Utility.trim(ckycCustomerWrapper.maidenMiddleName));
				pstmt.setString(11,Utility.trim(ckycCustomerWrapper.maidenLastName));
				pstmt.setString(12,Utility.trim(ckycCustomerWrapper.fatherSpousePrefix));
				pstmt.setString(13,Utility.trim(ckycCustomerWrapper.fatherSpouseFirstName));
				pstmt.setString(14,Utility.trim(ckycCustomerWrapper.fatherSpouseMiddleName));
				pstmt.setString(15,Utility.trim(ckycCustomerWrapper.fatherSpouseLastName));
				pstmt.setString(16,Utility.trim(ckycCustomerWrapper.motherPrefix));
				pstmt.setString(17,Utility.trim(ckycCustomerWrapper.motherFirstName));
				pstmt.setString(18,Utility.trim(ckycCustomerWrapper.motherMiddleName));
				pstmt.setString(19,Utility.trim(ckycCustomerWrapper.motherLastName));
				pstmt.setDate(20,Utility.getDate(ckycCustomerWrapper.dob));
				pstmt.setString(21,Utility.trim(ckycCustomerWrapper.gender));
				pstmt.setString(22,Utility.trim(ckycCustomerWrapper.maritalStatus));
				System.out.println("ckycCustomer Update citizenship "+ckycCustomerWrapper.citizenship);
				pstmt.setString(23,Utility.trim(ckycCustomerWrapper.citizenship));
				System.out.println("ckycCustomer Update residenceStatus "+ckycCustomerWrapper.residenceStatus);
				pstmt.setString(24,Utility.trim(ckycCustomerWrapper.residenceStatus));
				pstmt.setString(25,Utility.trim(ckycCustomerWrapper.occupationType));
				pstmt.setString(26,Utility.trim(ckycCustomerWrapper.residenceTaxPurpose));
				pstmt.setString(27,Utility.trim(ckycCustomerWrapper.iso3166CountryCode));
				pstmt.setString(28,Utility.trim(ckycCustomerWrapper.tin)); 
				pstmt.setString(29,Utility.trim(ckycCustomerWrapper.pob));
				pstmt.setString(30,Utility.trim(ckycCustomerWrapper.iso3166CountryCodeBirth));
				pstmt.setString(31,Utility.trim(ckycCustomerWrapper.remarks));
				pstmt.setDate(32,Utility.getDate(ckycCustomerWrapper.appDeclDate));
				pstmt.setString(33,Utility.trim(ckycCustomerWrapper.appDeclPlace));
				pstmt.setString(34,Utility.trim(ckycCustomerWrapper.docsReceived));
				pstmt.setDate(35,Utility.getDate(ckycCustomerWrapper.kycVerifiedDate));
				pstmt.setString(36,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpName));
				pstmt.setString(37,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpCode));
				pstmt.setString(38,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpDesignation));
				pstmt.setString(39,Utility.trim(ckycCustomerWrapper.kycVerifiedEmpBranch));
				pstmt.setString(40,Utility.trim(ckycCustomerWrapper.instDetails));
				pstmt.setString(41,Utility.trim(ckycCustomerWrapper.instCode));
				pstmt.setString(42,Utility.trim(ckycCustomerWrapper.recordStatus));
				pstmt.setString(43,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
				pstmt.setTimestamp(44,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
	
				pstmt.setString(45,Utility.trim(ckycCustomerWrapper.refNo));
				
				
				
				pstmt.executeUpdate();
				pstmt.close();
			
				ckycCustomerWrapper.recordFound=true;
				
			
			
				dataArrayWrapper.ckycCustomerWrapper=new CKYCCustomerWrapper[1];
				dataArrayWrapper.ckycCustomerWrapper[0]=ckycCustomerWrapper;
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Successfully ckycCustomer Updated");
				
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
	
	public AbstractWrapper fetchCKYCCustomer(CKYCCustomerWrapper ckycCustomerWrapper)throws Exception {

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
			
				//PopoverHelper popoverHelper = new PopoverHelper();
				
				
				
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, ApplicationType, KYCNo, AccountType, NamePrefix, FirstName, MiddleName, LastName, "
					+ " MaidenNamePrefix, MaidenFirstName, MaidenMiddleName, MaidenLastName, FatherSpousePrefix, FatherSpouseFirstName, FatherSpouseMiddleName, FatherSpouseLastName,"
					+ " MotherPrefix, MotherFirstName, MotherMiddleName, MotherLastName, DOB, Gender, MaritalStatus, Citizenship, "
					+ " ResidenceStatus, OccupationType, ResidenceTaxPurpose, ISO3166CountryCode, TIN, POB, ISO3166CountryCodeBirth, Remarks,"
					+ " ApplicationDeclarationDate, ApplicationDeclarationPlace, DocumentsReceived, KYCVerifiedDate, KYCVerifiedEmpName, KYCVerifiedEmpCode, KYCVerifiedEmpDesignation, KYCVerifiedEmpBranch,"
					+ " InstitutionDetails, InstitutionCode,RecordStatus FROM CKYC_Customer WHERE RefNo=?");
				
				
				System.out.println("ckycCustomer RefNo is" + ckycCustomerWrapper.refNo);
				
				pstmt.setString(1,ckycCustomerWrapper.refNo.trim());
			
				
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					ckycCustomerWrapper= new CKYCCustomerWrapper();
					
					ckycCustomerWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + ckycCustomerWrapper.refNo);
					
					
					ckycCustomerWrapper.applicationType=Utility.trim(resultSet.getString("ApplicationType"));
					ckycCustomerWrapper.kycNo=Utility.trim(resultSet.getString("KYCNo"));
					ckycCustomerWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					ckycCustomerWrapper.namePrefix=Utility.trim(resultSet.getString("NamePrefix"));
					ckycCustomerWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
					ckycCustomerWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
					ckycCustomerWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
					ckycCustomerWrapper.maidenNamePrefix=Utility.trim(resultSet.getString("MaidenNamePrefix"));
					ckycCustomerWrapper.maidenFirstName=Utility.trim(resultSet.getString("MaidenFirstName"));
					ckycCustomerWrapper.maidenMiddleName=Utility.trim(resultSet.getString("MaidenMiddleName"));
					ckycCustomerWrapper.maidenLastName=Utility.trim(resultSet.getString("MaidenLastName"));
					ckycCustomerWrapper.fatherSpousePrefix=Utility.trim(resultSet.getString("FatherSpousePrefix"));
					ckycCustomerWrapper.fatherSpouseFirstName=Utility.trim(resultSet.getString("FatherSpouseFirstName"));
					ckycCustomerWrapper.fatherSpouseMiddleName=Utility.trim(resultSet.getString("FatherSpouseMiddleName"));
					ckycCustomerWrapper.fatherSpouseLastName=Utility.trim(resultSet.getString("FatherSpouseLastName"));
					ckycCustomerWrapper.motherPrefix=Utility.trim(resultSet.getString("MotherPrefix"));
					ckycCustomerWrapper.motherFirstName=Utility.trim(resultSet.getString("MotherFirstName"));
					ckycCustomerWrapper.motherMiddleName=Utility.trim(resultSet.getString("MotherMiddleName"));
					ckycCustomerWrapper.motherLastName=Utility.trim(resultSet.getString("MotherLastName"));
					ckycCustomerWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
					ckycCustomerWrapper.gender=Utility.trim(resultSet.getString("Gender"));
					ckycCustomerWrapper.maritalStatus=Utility.trim(resultSet.getString("MaritalStatus"));
					ckycCustomerWrapper.citizenship=Utility.trim(resultSet.getString("Citizenship"));
					ckycCustomerWrapper.residenceStatus=Utility.trim(resultSet.getString("ResidenceStatus"));
					ckycCustomerWrapper.occupationType=Utility.trim(resultSet.getString("OccupationType"));
					ckycCustomerWrapper.residenceTaxPurpose=Utility.trim(resultSet.getString("ResidenceTaxPurpose"));
					ckycCustomerWrapper.iso3166CountryCode=Utility.trim(resultSet.getString("ISO3166CountryCode"));
					ckycCustomerWrapper.tin=Utility.trim(resultSet.getString("TIN")); 
					ckycCustomerWrapper.pob=Utility.trim(resultSet.getString("POB"));
					ckycCustomerWrapper.iso3166CountryCodeBirth=Utility.trim(resultSet.getString("ISO3166CountryCodeBirth"));
					ckycCustomerWrapper.remarks=Utility.trim(resultSet.getString("Remarks"));
					ckycCustomerWrapper.appDeclDate=Utility.setDate(resultSet.getString("ApplicationDeclarationDate"));
					ckycCustomerWrapper.appDeclPlace=Utility.trim(resultSet.getString("ApplicationDeclarationPlace"));
					ckycCustomerWrapper.docsReceived=Utility.trim(resultSet.getString("DocumentsReceived"));
					ckycCustomerWrapper.kycVerifiedDate=Utility.setDate(resultSet.getString("KYCVerifiedDate"));
					ckycCustomerWrapper.kycVerifiedEmpName=Utility.trim(resultSet.getString("KYCVerifiedEmpName"));
					ckycCustomerWrapper.kycVerifiedEmpCode=Utility.trim(resultSet.getString("KYCVerifiedEmpCode"));
					ckycCustomerWrapper.kycVerifiedEmpDesignation=Utility.trim(resultSet.getString("KYCVerifiedEmpDesignation"));
					ckycCustomerWrapper.kycVerifiedEmpBranch=Utility.trim(resultSet.getString("KYCVerifiedEmpBranch"));
					ckycCustomerWrapper.instDetails=Utility.trim(resultSet.getString("InstitutionDetails"));
					ckycCustomerWrapper.instCode=Utility.trim(resultSet.getString("InstitutionCode"));
					
				
				
		
					ckycCustomerWrapper.recordFound=true;
				
					vector.addElement(ckycCustomerWrapper);
	
				}
			
				if (vector.size()>0)
				{
					dataArrayWrapper.ckycCustomerWrapper = new CKYCCustomerWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.ckycCustomerWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
					
					System.out.println("fetch Personal Details Successful " );
	
				}
				else
					
				{
					dataArrayWrapper.ckycCustomerWrapper = new CKYCCustomerWrapper[1];
					dataArrayWrapper.ckycCustomerWrapper[0]= ckycCustomerWrapper;
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
	
	public String generateRefNo()throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;
	
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("ddMMMyyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		int refNo=0;
		String finalRefNo=null;
		
		
		try {
			con = getConnection();
			
			
			sql="SELECT RefNo from RMT_Parameter";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				
				refNo=resultSet.getInt("RefNo");
				System.out.println("RefNo" + refNo);
				
			}
			
			resultSet.close();
			pstmt.close();
			
			if(refNo==0)
			{
				refNo=1;
				
			}
			else
			{
				
				refNo=refNo+1;
			}
				
			sql="UPDATE RMT_Parameter set RefNo=?";
			
			
			System.out.println("sql " + sql);
			
			pstmt = con.prepareStatement(sql);
	
			pstmt.setInt(1,refNo);
			
			pstmt.executeUpdate();
			pstmt.close();
	
			int paddingSize=6-String.valueOf(refNo).length();
			
			System.out.println("CKYC  ");
			
			//System.out.println("Savings Account " + ckycCustomerWrapper.accountType.substring(0,2));
			
			finalRefNo="KY"+dmyFormat.format(new java.util.Date()).toUpperCase()+String.format("%0" +paddingSize +"d",refNo);
			
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Successfully generated CKYC refno " + finalRefNo);
			
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
	
		return finalRefNo;
	}
	
	
	public AbstractWrapper fetchCKYCCustomerQueue(UsersWrapper usersProfileWrapper,CKYCCustomerWrapper ckycCustomerWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//ResultSet resultSetSub = null;
		
		System.out.println("fetchCKYCCustomerQueue");
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		//System.out.println("Fetch Queue RecordStatus "+ ckycCustomerWrapper.recordStatus);
		String sql=null;
		String filterSQL="";
		String whereClause="";
		//boolean recordStatusParam=false;
		//boolean makerIdParam=false;
		PreparedStatement pstmt=null;
		//PreparedStatement pstmtSub=null;
		int queueMaxRecords=0;
		int queueNoRecords=0;
		//String workflowGroup=null;
		//int n=0;
		//int targetTAT=0;
		try {
				con = getConnection();
				
				PopoverHelper popoverHelper = new PopoverHelper();
				
				//-------- Fetch QueueMaxRecords  Parameter
				pstmt = con.prepareStatement("SELECT QueueMaxRecords from RMT_Parameter");
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{	
					queueMaxRecords=resultSet.getInt("QueueMaxRecords");
					System.out.println(" queueMaxRecords " + queueMaxRecords);
					
				}
				
				resultSet.close();
				pstmt.close();
				
				//-------
				
				//workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);
				
				
				
				sql="SELECT RefNo, ApplicationType, KYCNo, AccountType, NamePrefix, FirstName, MiddleName, LastName, "
					+ " MaidenNamePrefix, MaidenFirstName, MaidenMiddleName, MaidenLastName, FatherSpousePrefix, FatherSpouseFirstName,"
					+ " FatherSpouseMiddleName, FatherSpouseLastName, MotherPrefix, MotherFirstName, MotherMiddleName, MotherLastName, DOB, "
					+ " Gender, MaritalStatus, Citizenship, ResidenceStatus, OccupationType, ResidenceTaxPurpose, ISO3166CountryCode, TIN,"
					+ " POB, ISO3166CountryCodeBirth, Remarks,ApplicationDeclarationDate, ApplicationDeclarationPlace, DocumentsReceived,"
					+ " KYCVerifiedDate, KYCVerifiedEmpName, KYCVerifiedEmpCode, KYCVerifiedEmpDesignation, KYCVerifiedEmpBranch, "
					+ " InstitutionDetails, InstitutionCode,RecordStatus,MakerID,MakerDateTime FROM CKYC_Customer";
				
				
				/*if(ckycCustomerWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					whereClause = " WHERE Makerid=? AND WorkflowGroup=? AND RecordStatus IN ('INPROGRESS','REJECTED')";
				}	
				else if(ckycCustomerWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					whereClause=  " WHERE WorkflowGroup=? AND RecordStatus IN ('CREATE') ";
				}
				else if(ckycCustomerWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					whereClause=  " WHERE Makerid=? ";
				}
				else if(ckycCustomerWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					whereClause=  " WHERE 1=1 ";
				}*/
				

			   /* if(ckycCustomerWrapper.refNo !=null && !ckycCustomerWrapper.refNo.trim().isEmpty())
				{
			    	filterSQL= " AND RefNo =? ";
					
					System.out.println("fetchOnBoardQueue RefNo " + sql);
					
				}
				
				else if(ckycCustomerWrapper.customerName !=null && !ckycCustomerWrapper.customerName.trim().isEmpty())
				{
					
					
					filterSQL=" AND (UPPER(CustomerName) LIKE ? OR UPPER(FirstName) LIKE ? OR UPPER(MiddleName) LIKE ? OR UPPER(LastName) LIKE ?)";
					
					
					System.out.println("fetchOnBoardQueue customerName " +ckycCustomerWrapper.customerName);
					
					
				}
				
				else if(ckycCustomerWrapper.searchStartDate !=null && !ckycCustomerWrapper.searchStartDate.trim().isEmpty())
				{
					
					filterSQL= " AND MakerDateTime >= ?";
						
				}
				
				else if(ckycCustomerWrapper.searchEndDate !=null && !ckycCustomerWrapper.searchEndDate.trim().isEmpty())
				{
					
					filterSQL=" AND MakerDateTime <= ?";
					
					
				}*/

				
				sql = sql + whereClause + filterSQL + "  LIMIT " + queueMaxRecords ;
				
				
				System.out.println("CKYCCustomer Queue final sql "+sql);
				
				pstmt = con.prepareStatement(sql);
				
				
				/*if(ckycCustomerWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, ckycCustomerWrapper.makerId.trim());
					pstmt.setString(++n, workflowGroup);
					
					
				}	
				else if(ckycCustomerWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, workflowGroup);
					
					
				}
				else if(ckycCustomerWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					
					pstmt.setString(++n, ckycCustomerWrapper.makerId.trim());
				}
				else if(ckycCustomerWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					
				}*/
				
				
				/*if(ckycCustomerWrapper.refNo !=null && !ckycCustomerWrapper.refNo.trim().isEmpty())
				{
					pstmt.setString(++n, ckycCustomerWrapper.refNo.trim());
					
					
				}
				
				else if(ckycCustomerWrapper.customerName !=null && !ckycCustomerWrapper.customerName.trim().isEmpty())
				{
						
						pstmt.setString(++n, '%' + ckycCustomerWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + ckycCustomerWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + ckycCustomerWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + ckycCustomerWrapper.customerName.trim().toUpperCase() + '%');
						
						System.out.println("fetchOnBoardQueue customerName2 " +ckycCustomerWrapper.customerName);
				}
				
				
				else if(ckycCustomerWrapper.searchStartDate !=null && !ckycCustomerWrapper.searchStartDate.trim().isEmpty())
				{
					
						pstmt.setDate(++n, Utility.getDate(ckycCustomerWrapper.searchStartDate.trim()));
				
				}
				
				else if(ckycCustomerWrapper.searchEndDate !=null && !ckycCustomerWrapper.searchEndDate.trim().isEmpty())
				{


						pstmt.setDate(++n, Utility.getDate(ckycCustomerWrapper.searchEndDate.trim()));
					
				}*/
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					ckycCustomerWrapper= new CKYCCustomerWrapper();
					
					ckycCustomerWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + ckycCustomerWrapper.refNo);
					
					
					ckycCustomerWrapper.applicationType=Utility.trim(resultSet.getString("ApplicationType"));
					ckycCustomerWrapper.kycNo=Utility.trim(resultSet.getString("KYCNo"));
					ckycCustomerWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					ckycCustomerWrapper.namePrefix=Utility.trim(resultSet.getString("NamePrefix"));
					ckycCustomerWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
					ckycCustomerWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
					ckycCustomerWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
					ckycCustomerWrapper.maidenNamePrefix=Utility.trim(resultSet.getString("MaidenNamePrefix"));
					ckycCustomerWrapper.maidenFirstName=Utility.trim(resultSet.getString("MaidenFirstName"));
					ckycCustomerWrapper.maidenMiddleName=Utility.trim(resultSet.getString("MaidenMiddleName"));
					ckycCustomerWrapper.maidenLastName=Utility.trim(resultSet.getString("MaidenLastName"));
					ckycCustomerWrapper.fatherSpousePrefix=Utility.trim(resultSet.getString("FatherSpousePrefix"));
					ckycCustomerWrapper.fatherSpouseFirstName=Utility.trim(resultSet.getString("FatherSpouseFirstName"));
					ckycCustomerWrapper.fatherSpouseMiddleName=Utility.trim(resultSet.getString("FatherSpouseMiddleName"));
					ckycCustomerWrapper.fatherSpouseLastName=Utility.trim(resultSet.getString("FatherSpouseLastName"));
					ckycCustomerWrapper.motherPrefix=Utility.trim(resultSet.getString("MotherPrefix"));
					ckycCustomerWrapper.motherFirstName=Utility.trim(resultSet.getString("MotherFirstName"));
					ckycCustomerWrapper.motherMiddleName=Utility.trim(resultSet.getString("MotherMiddleName"));
					ckycCustomerWrapper.motherLastName=Utility.trim(resultSet.getString("MotherLastName"));
					ckycCustomerWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
					ckycCustomerWrapper.gender=Utility.trim(resultSet.getString("Gender"));
					ckycCustomerWrapper.maritalStatus=Utility.trim(resultSet.getString("MaritalStatus"));
					ckycCustomerWrapper.citizenship=Utility.trim(resultSet.getString("Citizenship"));
					ckycCustomerWrapper.residenceStatus=Utility.trim(resultSet.getString("ResidenceStatus"));
					ckycCustomerWrapper.occupationType=Utility.trim(resultSet.getString("OccupationType"));
					ckycCustomerWrapper.residenceTaxPurpose=Utility.trim(resultSet.getString("ResidenceTaxPurpose"));
					ckycCustomerWrapper.iso3166CountryCode=Utility.trim(resultSet.getString("ISO3166CountryCode"));
					ckycCustomerWrapper.tin=Utility.trim(resultSet.getString("TIN")); 
					ckycCustomerWrapper.pob=Utility.trim(resultSet.getString("POB"));
					ckycCustomerWrapper.iso3166CountryCodeBirth=Utility.trim(resultSet.getString("ISO3166CountryCodeBirth"));
					ckycCustomerWrapper.remarks=Utility.trim(resultSet.getString("Remarks"));
					ckycCustomerWrapper.appDeclDate=Utility.setDate(resultSet.getString("ApplicationDeclarationDate"));
					ckycCustomerWrapper.appDeclPlace=Utility.trim(resultSet.getString("ApplicationDeclarationPlace"));
					ckycCustomerWrapper.docsReceived=Utility.trim(resultSet.getString("DocumentsReceived"));
					ckycCustomerWrapper.kycVerifiedDate=Utility.setDate(resultSet.getString("KYCVerifiedDate"));
					ckycCustomerWrapper.kycVerifiedEmpName=Utility.trim(resultSet.getString("KYCVerifiedEmpName"));
					ckycCustomerWrapper.kycVerifiedEmpCode=Utility.trim(resultSet.getString("KYCVerifiedEmpCode"));
					ckycCustomerWrapper.kycVerifiedEmpDesignation=Utility.trim(resultSet.getString("KYCVerifiedEmpDesignation"));
					ckycCustomerWrapper.kycVerifiedEmpBranch=Utility.trim(resultSet.getString("KYCVerifiedEmpBranch"));
					ckycCustomerWrapper.instDetails=Utility.trim(resultSet.getString("InstitutionDetails"));
					ckycCustomerWrapper.instCode=Utility.trim(resultSet.getString("InstitutionCode"));
					ckycCustomerWrapper.recordStatus=Utility.trim(resultSet.getString("RecordStatus"));
					ckycCustomerWrapper.makerID=Utility.trim(resultSet.getString("MakerID"));
					ckycCustomerWrapper.makerDateTime=Utility.trim(resultSet.getString("MakerDateTime"));
					ckycCustomerWrapper.namePrefixValue=popoverHelper.fetchPopoverDesc(ckycCustomerWrapper.namePrefix, "Title");
					ckycCustomerWrapper.genderValue=popoverHelper.fetchPopoverDesc(ckycCustomerWrapper.gender, "Gender");
					
					queueNoRecords++;
					ckycCustomerWrapper.queueNoRecords=queueNoRecords; // to assign value
					
					ckycCustomerWrapper.recordFound=true;
					
					System.out.println("fetch CKYCCustomer Queue Successful");
					
					
					vector.addElement(ckycCustomerWrapper);
					
					
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.ckycCustomerWrapper = new CKYCCustomerWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.ckycCustomerWrapper);
					dataArrayWrapper.recordFound=true;
					
					
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else
				{
					dataArrayWrapper.ckycCustomerWrapper = new CKYCCustomerWrapper[1];
					dataArrayWrapper.ckycCustomerWrapper[0] = ckycCustomerWrapper;
					ckycCustomerWrapper.recordFound=false;
					dataArrayWrapper.recordFound=true;
					
				}
				
				
				ckycCustomerWrapper.queueNoRecords=queueNoRecords; // to assign 
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
