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

public class OnBoardHelper extends Helper{

	
	public AbstractWrapper insertPersonalDetails(OnBoardWrapper onBoardWrapper)throws Exception {

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
		
		
	
		System.out.println("Customer number :"+ onBoardWrapper.customerName);
	
		try {
			con = getConnection();
			
			sql=" INSERT INTO RMT_OnBoard(RefNo, ExistingCustomerFlag, ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, "
					+ "Title, CategoryType, CustomerName, ENameCheckFlag, ENamePerformedBy, ENamePerformedDate, ENameMatchFlag, "
					+ "ENamePerformedName, ENameResult, ENameComment, ShortName, Branch, JointOwn, NatureOfRelation, ResidenceStatus, "
					+ "Nationality,	DOB, Educated, MaritalStatus, Gender, PreferredLanguage, FamilyInUAE, FamilySizeUAE, CarOwnership, "
					+ "CarYear,	Media,FavouriteCity, Domicile,GuardianName, IntroName, IntroAccountNo, IntroCustType, IntroSegment, "
					+ "IntroIndustry, IntroBusinessGroup, IntroBranch, IntroGeography, IntroRMID, IntroSSO) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			onBoardWrapper.refNo="CUS001";
		
			pstmt.setString(1,Utility.trim(onBoardWrapper.refNo));
			pstmt.setString(2,Utility.trim(onBoardWrapper.existingCustomerFlag));
			pstmt.setString(3,Utility.trim(onBoardWrapper.existingCIFNumber));
			pstmt.setString(4,Utility.trim(onBoardWrapper.existingAccountNo));
			pstmt.setString(5,Utility.trim(onBoardWrapper.existingCreditCardNo));
			pstmt.setString(6,Utility.trim(onBoardWrapper.title));
			pstmt.setString(7,Utility.trim(onBoardWrapper.categoryType));
			pstmt.setString(8,Utility.trim(onBoardWrapper.customerName));
			pstmt.setString(9,Utility.trim(onBoardWrapper.eNameCheckFlag));
			pstmt.setString(10,Utility.trim(onBoardWrapper.eNamePerformedBy));
			pstmt.setDate(11,Utility.getDate(onBoardWrapper.eNamePerformedDate));
			pstmt.setString(12,Utility.trim(onBoardWrapper.eNameMatchFlag));
			pstmt.setString(13,Utility.trim(onBoardWrapper.eNamePerformedName));
			pstmt.setString(14,Utility.trim(onBoardWrapper.eNameResult));
			pstmt.setString(15,Utility.trim(onBoardWrapper.eNameComment));
			pstmt.setString(16,Utility.trim(onBoardWrapper.shortName));
			pstmt.setString(17,Utility.trim(onBoardWrapper.branch));
			pstmt.setString(18,Utility.trim(onBoardWrapper.jointOwn));
			pstmt.setString(19,Utility.trim(onBoardWrapper.natureOfRelation));
			pstmt.setString(20,Utility.trim(onBoardWrapper.residenceStatus));
			pstmt.setString(21,Utility.trim(onBoardWrapper.nationality));
			pstmt.setDate(22,Utility.getDate(onBoardWrapper.dob));
			pstmt.setString(23,Utility.trim(onBoardWrapper.educated));
			pstmt.setString(24,Utility.trim(onBoardWrapper.maritalStatus));
			pstmt.setString(25,Utility.trim(onBoardWrapper.gender));
			pstmt.setString(26,Utility.trim(onBoardWrapper.preferredLanguage));
			pstmt.setString(27,Utility.trim(onBoardWrapper.familyInUAE));
			pstmt.setInt(28,onBoardWrapper.familySizeUAE);
			pstmt.setString(29,Utility.trim(onBoardWrapper.carOwnership));
			pstmt.setString(30,Utility.trim(onBoardWrapper.carYear));
			pstmt.setString(31,Utility.trim(onBoardWrapper.media));
			pstmt.setString(32,Utility.trim(onBoardWrapper.favouriteCity));
			pstmt.setString(33,Utility.trim(onBoardWrapper.domicile));
			pstmt.setString(34,Utility.trim(onBoardWrapper.guardianName));
			pstmt.setString(35,Utility.trim(onBoardWrapper.introName));
			pstmt.setString(36,Utility.trim(onBoardWrapper.introAccountNo));
			pstmt.setString(37,Utility.trim(onBoardWrapper.introCustType));
			pstmt.setString(38,Utility.trim(onBoardWrapper.introSegment));
			pstmt.setString(39,Utility.trim(onBoardWrapper.introIndustry));
			pstmt.setString(40,Utility.trim(onBoardWrapper.introBusinessGroup));
			pstmt.setString(41,Utility.trim(onBoardWrapper.introBranch));
			pstmt.setString(42,Utility.trim(onBoardWrapper.introGeography));
			pstmt.setString(43,Utility.trim(onBoardWrapper.introRMID));
			pstmt.setString(44,Utility.trim(onBoardWrapper.introSSO));
			
			
			

			/*	pstmt.setString(40,Utility.trim(personalDetailsWrapper.duplicateCheck));
			pstmt.setString(41,Utility.trim(personalDetailsWrapper.occupationType));
			pstmt.setString(42,Utility.trim(personalDetailsWrapper.occupation));
			pstmt.setString(43,Utility.trim(personalDetailsWrapper.designation));
			pstmt.setString(44,Utility.trim(personalDetailsWrapper.orgName));
			pstmt.setString(45,Utility.trim(personalDetailsWrapper.otherOrgName));
			pstmt.setDate(46,Utility.getDate(personalDetailsWrapper.joiningDate));
			pstmt.setBigDecimal(47,Utility.trim(personalDetailsWrapper.monthlySalary));
			pstmt.setString(48,Utility.trim(personalDetailsWrapper.employeeNo));
			pstmt.setString(49,Utility.trim(personalDetailsWrapper.mailOption));
			pstmt.setString(50,Utility.trim(personalDetailsWrapper.debitCardDeliveryChnl));
			pstmt.setString(51,Utility.trim(personalDetailsWrapper.eStatement));
			pstmt.setString(52,Utility.trim(personalDetailsWrapper.email));
			pstmt.setString(53,Utility.trim(personalDetailsWrapper.email2));
			pstmt.setString(54,Utility.trim(personalDetailsWrapper.mobile));
			pstmt.setString(55,Utility.trim(personalDetailsWrapper.mobileSP));
			pstmt.setString(56,Utility.trim(personalDetailsWrapper.mobile2));
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());
			pstmt.setString(33,personalDetailsWrapper.emiratesID.trim());*/
			//,	DuplicateCheck,	"
			//+ "OccupationType,	Occupation,	Designation,	OrgName,	OtherOrgName,	JoiningDate,	"
			//+ "MonthlySalary,	EmployeeNo,	MailOption,	DebitCardDeliveryChnl,	eStatement,	email,	email2,	"
			//+ "Mobile,	MobileSP,	Mobile2
			
			pstmt.executeUpdate();
			pstmt.close();
		
			
			
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_OnBoard(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

//			pstmt.setString(1,Utility.trim(onBoardWrapper.makerId));
//			pstmt.setDate(2,Utility.getDate(onBoardWrapper.makerDate));
//			pstmt.setString(3,Utility.trim(onBoardWrapper.approverId));
//			pstmt.setDate(4,Utility.getDate(onBoardWrapper.approverDate));
//			
			pstmt.executeUpdate();

			pstmt.close();
	
			//------------------
			
			
			
			onBoardWrapper.recordFound=true;
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			//dataArrayWrapper.personalDetailsWrapper[0]=onBoardWrapper;
			
			
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
	
	
	
	/*public AbstractWrapper fetchPopoverData(PopoverWrapper popoverWrapper,String tableName)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//PersonalDetailsWrapper personalDetailsWrapper=new PersonalDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("existingCIFNumber:"+ personalDetailsWrapper.existingCIFNumber);
		System.out.println("existingAccountNo :"+ personalDetailsWrapper.existingAccountNo);
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT CODE,DESC  FROM ? ");
		
			pstmt.setString(1,tableName);
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				
				
				popoverWrapper.code=Utility.trim(resultSet.getString("code"));
				System.out.println("ExistingCustomerFlag" + personalDetailsWrapper.existingCustomerFlag);

				personalDetailsWrapper.desc=Utility.trim(resultSet.getString("desc"));
				System.out.println("ExistingCIFNumber " + personalDetailsWrapper.existingCIFNumber);
				
				
				i*/
	
	/*
	public AbstractWrapper fetchPersonalDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//PersonalDetailsWrapper personalDetailsWrapper=new PersonalDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("existingCIFNumber:"+ personalDetailsWrapper.existingCIFNumber);
		System.out.println("existingAccountNo :"+ personalDetailsWrapper.existingAccountNo);
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, ExistingCustomerFlag, ExistingCIFNumber, ExistingAccountNo, "
					+ "ExistingCreditCardNo, Title, CategoryType, CustomerName, ENameCheckFlag, ENamePerformedBy, ENamePerformedDate, "
					+ "ENameMatchFlag, ENamePerformedName, ENameResult, ENameComment, ShortName, Branch, JointOwn, NatureOfRelation, ResidenceStatus, "
					+ "	Nationality, DOB, Educated, MaritalStatus, Gender, PreferredLanguage, FamilyInUAE, FamilySizeUAE, CarOwnership,	"
					+ "CarYear,	Media, FavouriteCity, Domicile, GuardianName, IntroName, IntroAccountNo, IntroCustType, IntroSegment, IntroIndustry, "
					+ "IntroBusinessGroup, IntroBranch, IntroGeography, IntroRMID, IntroSSO  FROM RMT_OnBoard where ExistingCIFNumber=? and ExistingAccountNo=?");
		
			pstmt.setString(1,personalDetailsWrapper.existingCIFNumber.trim());
			pstmt.setString(2,personalDetailsWrapper.existingAccountNo.trim());
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				
				
				personalDetailsWrapper.existingCustomerFlag=Utility.trim(resultSet.getString("ExistingCustomerFlag"));
				System.out.println("ExistingCustomerFlag" + personalDetailsWrapper.existingCustomerFlag);

				personalDetailsWrapper.existingCIFNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));
				System.out.println("ExistingCIFNumber " + personalDetailsWrapper.existingCIFNumber);
				
				
				personalDetailsWrapper.existingAccountNo=Utility.trim(resultSet.getString("ExistingAccountNo"));
				personalDetailsWrapper.existingCreditCardNo=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
				personalDetailsWrapper.title=Utility.trim(resultSet.getString("Title"));
				personalDetailsWrapper.categoryType=Utility.trim(resultSet.getString("CategoryType"));
				personalDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
				personalDetailsWrapper.eNameCheckFlag=Utility.trim(resultSet.getString("ENameCheckFlag"));
				personalDetailsWrapper.eNamePerformedBy=Utility.trim(resultSet.getString("ENamePerformedBy"));
				personalDetailsWrapper.eNamePerformedDate=Utility.trim(resultSet.getString("ENamePerformedDate"));
				personalDetailsWrapper.eNameMatchFlag=Utility.trim(resultSet.getString("ENameMatchFlag"));
				personalDetailsWrapper.eNamePerformedName=Utility.trim(resultSet.getString("ENamePerformedName"));
				personalDetailsWrapper.eNameResult=Utility.trim(resultSet.getString("ENameResult"));
				personalDetailsWrapper.eNameComment=Utility.trim(resultSet.getString("ENameComment"));
				personalDetailsWrapper.shortName=Utility.trim(resultSet.getString("ShortName"));
				personalDetailsWrapper.branch=Utility.trim(resultSet.getString("Branch"));
				personalDetailsWrapper.jointOwn=Utility.trim(resultSet.getString("JointOwn"));
				personalDetailsWrapper.natureOfRelation=Utility.trim(resultSet.getString("NatureOfRelation"));
				personalDetailsWrapper.residenceStatus=Utility.trim(resultSet.getString("ResidenceStatus"));
				personalDetailsWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
				personalDetailsWrapper.dob=Utility.trim(resultSet.getString("DOB"));
				personalDetailsWrapper.educated=Utility.trim(resultSet.getString("Educated"));
				personalDetailsWrapper.maritalStatus=Utility.trim(resultSet.getString("MaritalStatus"));
				personalDetailsWrapper.gender=Utility.trim(resultSet.getString("Gender"));
				personalDetailsWrapper.preferredLanguage=Utility.trim(resultSet.getString("PreferredLanguage"));
				personalDetailsWrapper.familyInUAE=Utility.trim(resultSet.getString("FamilyInUAE"));	
				personalDetailsWrapper.familySizeUAE=(resultSet.getInt("FamilySizeUAE"));
				personalDetailsWrapper.carOwnership=Utility.trim(resultSet.getString("CarOwnership"));
				personalDetailsWrapper.carYear=Utility.trim(resultSet.getString("CarYear"));
				personalDetailsWrapper.media=Utility.trim(resultSet.getString("Media"));
				personalDetailsWrapper.favouriteCity=Utility.trim(resultSet.getString("FavouriteCity"));
				personalDetailsWrapper.domicile=Utility.trim(resultSet.getString("Domicile"));
			
				
				
				
				
			
				personalDetailsWrapper.recordFound=true;
				
				System.out.println("personalDetailsWrapper " );

				vector.addElement(personalDetailsWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
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
	
	
	public AbstractWrapper updatePersonalDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//PersonalDetailsWrapper personalDetailsWrapper=	new PersonalDetailsWrapper();
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("updatePersonalDetails");
		
		
		try {
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET  ExistingCustomerFlag=?, ExistingCIFNumber=?, ExistingAccountNo=?, ExistingCreditCardNo=?, "
					+ "Title=?, CategoryType=?, CustomerName=?, ENameCheckFlag=?, ENamePerformedBy=?, ENamePerformedDate=?, ENameMatchFlag=?, "
					+ "ENamePerformedName=?, ENameResult=?, ENameComment=?, ShortName=?, Branch=?, JointOwn=?, NatureOfRelation=?, ResidenceStatus=?, "
					+ "Nationality=?,	DOB=?, Educated=?, MaritalStatus=?, Gender=?, PreferredLanguage=?, FamilyInUAE=?, FamilySizeUAE=?, CarOwnership=?, "
					+ "CarYear=?,	Media=?,FavouriteCity=?, Domicile=?,GuardianName=?, IntroName=?, IntroAccountNo=?, IntroCustType=?, IntroSegment=?, "
					+ "IntroIndustry=?, IntroBusinessGroup=?, IntroBranch=?, IntroGeography=?, IntroRMID=?, IntroSSO=? where RefNo=? ");
			
			pstmt.setString(1,Utility.trim(personalDetailsWrapper.existingCustomerFlag));
			pstmt.setString(2,Utility.trim(personalDetailsWrapper.existingCIFNumber));
			pstmt.setString(3,Utility.trim(personalDetailsWrapper.existingAccountNo));
			pstmt.setString(4,Utility.trim(personalDetailsWrapper.existingCreditCardNo));
			pstmt.setString(5,Utility.trim(personalDetailsWrapper.title));
			pstmt.setString(6,Utility.trim(personalDetailsWrapper.categoryType));
			pstmt.setString(7,Utility.trim(personalDetailsWrapper.customerName));
			
			pstmt.setString(8,Utility.trim(personalDetailsWrapper.eNameCheckFlag));
			pstmt.setString(9,Utility.trim(personalDetailsWrapper.eNamePerformedBy));
			pstmt.setDate(10,Utility.getDate(personalDetailsWrapper.eNamePerformedDate));
			pstmt.setString(11,Utility.trim(personalDetailsWrapper.eNameMatchFlag));
			pstmt.setString(12,Utility.trim(personalDetailsWrapper.eNamePerformedName));
			pstmt.setString(13,Utility.trim(personalDetailsWrapper.eNameResult));
			pstmt.setString(14,Utility.trim(personalDetailsWrapper.eNameComment));
			pstmt.setString(15,Utility.trim(personalDetailsWrapper.shortName));
			pstmt.setString(16,Utility.trim(personalDetailsWrapper.branch));
			pstmt.setString(17,Utility.trim(personalDetailsWrapper.jointOwn));
			pstmt.setString(18,Utility.trim(personalDetailsWrapper.natureOfRelation));
			pstmt.setString(19,Utility.trim(personalDetailsWrapper.residenceStatus));
			pstmt.setString(20,Utility.trim(personalDetailsWrapper.nationality));
			pstmt.setDate(21,Utility.getDate(personalDetailsWrapper.dob));
			pstmt.setString(22,Utility.trim(personalDetailsWrapper.educated));
			pstmt.setString(23,Utility.trim(personalDetailsWrapper.maritalStatus));
			pstmt.setString(24,Utility.trim(personalDetailsWrapper.gender));
			pstmt.setString(25,Utility.trim(personalDetailsWrapper.preferredLanguage));
			pstmt.setString(26,Utility.trim(personalDetailsWrapper.familyInUAE));
			pstmt.setInt(27,personalDetailsWrapper.familySizeUAE);
			pstmt.setString(28,Utility.trim(personalDetailsWrapper.carOwnership));
			pstmt.setString(29,Utility.trim(personalDetailsWrapper.carYear));
			pstmt.setString(30,Utility.trim(personalDetailsWrapper.media));
			pstmt.setString(31,Utility.trim(personalDetailsWrapper.favouriteCity));
			pstmt.setString(32,Utility.trim(personalDetailsWrapper.domicile));
			pstmt.setString(33,Utility.trim(personalDetailsWrapper.guardianName));
			pstmt.setString(34,Utility.trim(personalDetailsWrapper.introName));
			pstmt.setString(35,Utility.trim(personalDetailsWrapper.introAccountNo));
			pstmt.setString(36,Utility.trim(personalDetailsWrapper.introCustType));
			pstmt.setString(37,Utility.trim(personalDetailsWrapper.introSegment));
			pstmt.setString(38,Utility.trim(personalDetailsWrapper.introIndustry));
			pstmt.setString(39,Utility.trim(personalDetailsWrapper.introBusinessGroup));
			pstmt.setString(40,Utility.trim(personalDetailsWrapper.introBranch));
			pstmt.setString(41,Utility.trim(personalDetailsWrapper.introGeography));
			pstmt.setString(42,Utility.trim(personalDetailsWrapper.introRMID));
			pstmt.setString(43,Utility.trim(personalDetailsWrapper.introSSO));
			
			pstmt.setString(44,Utility.trim(personalDetailsWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();

			
			
			personalDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
			
			System.out.println("Personal details updated " );
			
			
		}
		
		
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO RMT_OnBoard(userId,sessionId,Activity,"
					+ "Screen name,DateStamp,Message) values (?,?,?,?,?,?) ");
			
			pstmt.setString(1,userid.trim());
			pstmt.setString(2,sessionid.trim());
			pstmt.setString(3,activity.trim());
			pstmt.setString(4,screenName.trim());
			pstmt.setTimestamp(5,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(6,message.trim());
			pstmt.executeUpdate();

			pstmt.close();

			//PersonalDetailsWrapper tmpUsersWrapper=new PersonalDetailsWrapper();
			//tmpUsersWrapper=(ContactDetailsWrapper)getCIFNumber(userid);
			
			pstmt = con.prepareStatement("UPDATE RMT_OnBoard set Lastlogindate=?,noLoginRetry=? where userid=?");
			
			pstmt.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
			//pstmt.setInt(2,tmpUsersWrapper.noLoginRetry+1);
			pstmt.setString(3,userid.trim());

			pstmt.executeUpdate();

			pstmt.close();
			
			personalDetailsWrapper.recordFound=true;
			
			System.out.println("user table updated " );
			
			
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
	*/
	
	
	/*public AbstractWrapper insertPassportDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

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
		
		
		System.out.println("Passport number :"+ personalDetailsWrapper.passportNo);
		
		try {
			con = getConnection("ONBOARD");
			
			sql=" INSERT INTO RMT_OnBoard(RefNo, PassportNo, PassportIssueDate, PassportExpDate, PassportIssuePlace,	"
					+ "PassportIssueCountry, ImmiFileNumber, VisaIssuePlace, VisaIssueDate, VisaExpDate, DrivingLicenseNo,	"
					+ "DrivingLicenseExpDate, LaborCardNo,	LaborCardExpDate,	EmiratesID,	EmiratesIDExpDate) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			personalDetailsWrapper.refNo="CUS001";
		
			pstmt.setString(1,Utility.trim(personalDetailsWrapper.refNo));
			pstmt.setString(2,Utility.trim(personalDetailsWrapper.passportNo));
			pstmt.setDate(3,Utility.getDate(personalDetailsWrapper.passportIssueDate));
			pstmt.setDate(4,Utility.getDate(personalDetailsWrapper.passportExpDate));
			pstmt.setString(5,Utility.trim(personalDetailsWrapper.passportIssuePlace));
			pstmt.setString(6,Utility.trim(personalDetailsWrapper.passportIssueCountry));
			pstmt.setString(7,Utility.trim(personalDetailsWrapper.immiFileNumber));
			pstmt.setString(8,Utility.trim(personalDetailsWrapper.visaIssuePlace));
			pstmt.setDate(9,Utility.getDate(personalDetailsWrapper.visaIssueDate));
			pstmt.setDate(10,Utility.getDate(personalDetailsWrapper.visaExpDate));
			pstmt.setString(11,Utility.trim(personalDetailsWrapper.drivingLicenseNo));
			pstmt.setDate(12,Utility.getDate(personalDetailsWrapper.drivingLicenseExpDate));
			pstmt.setString(13,Utility.trim(personalDetailsWrapper.laborCardNo));
			pstmt.setDate(14,Utility.getDate(personalDetailsWrapper.laborCardExpDate));
			pstmt.setString(15,Utility.trim(personalDetailsWrapper.emiratesID));
			pstmt.setDate(16,Utility.getDate(personalDetailsWrapper.emiratesIDExpDate));
		
		
			pstmt.executeUpdate();
			pstmt.close();

			personalDetailsWrapper.recordFound=true;
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
			
			
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

	
	public AbstractWrapper fetchPassportDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//PersonalDetailsWrapper personalDetailsWrapper=new PersonalDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("Passport No:"+ personalDetailsWrapper.passportNo);

	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,PassportNo,PassportIssueDate,PassportExpDate,PassportIssuePlace, "
					+ "PassportIssueCountry,ImmiFileNumber,	VisaIssuePlace,VisaIssueDate,VisaExpDate,DrivingLicenseNo, "
					+ "DrivingLicenseExpDate,LaborCardNo,LaborCardExpDate,EmiratesID,EmiratesIDExpDate FROM RMT_OnBoard where PassportNo=?");
		
			pstmt.setString(1,personalDetailsWrapper.passportNo.trim());
		
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				
				
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + personalDetailsWrapper.refNo);

				personalDetailsWrapper.passportNo=Utility.trim(resultSet.getString("PassportNo"));
				System.out.println("PassportNo " + personalDetailsWrapper.passportNo);
				
				
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("PassportIssueDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("PassportExpDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("PassportIssuePlace"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("PassportIssueCountry"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("ImmiFileNumber"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("VisaIssuePlace"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("VisaIssueDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("VisaExpDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("DrivingLicenseNo"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("DrivingLicenseExpDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("LaborCardNo"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("LaborCardExpDate"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("EmiratesID"));
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("EmiratesIDExpDate"));
			
				
				
			
				personalDetailsWrapper.recordFound=true;
				
				System.out.println("personalDetailsWrapper " );

				vector.addElement(personalDetailsWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
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
	
	public AbstractWrapper updatePassportDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("updatepassportdetails");
	
		try {
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET PassportNo=?,PassportIssueDate=?,	PassportExpDate=?,"
					+ "PassportIssuePlace=?,PassportIssueCountry=?,	ImmiFileNumber=?,	VisaIssuePlace=?,VisaIssueDate=?,VisaExpDate=?,		"
					+ "DrivingLicenseNo=?,DrivingLicenseExpDate=?,	LaborCardNo=?,	LaborCardExpDate=?,	"
					+ "EmiratesID=?,	EmiratesIDExpDate=? where RefNo=? ");
			
			pstmt.setString(1,Utility.trim(personalDetailsWrapper.passportNo));
			pstmt.setDate(2,Utility.getDate(personalDetailsWrapper.passportIssueDate));
			pstmt.setDate(3,Utility.getDate(personalDetailsWrapper.passportExpDate));
			pstmt.setString(4, Utility.trim(personalDetailsWrapper.passportIssuePlace));
			pstmt.setString(5,Utility.trim(personalDetailsWrapper.passportIssueCountry));
			pstmt.setString(6, Utility.trim(personalDetailsWrapper.immiFileNumber));
			
			pstmt.setString(7, Utility.trim(personalDetailsWrapper.visaIssuePlace));
			pstmt.setDate(8, Utility.getDate(personalDetailsWrapper.visaIssueDate));
			pstmt.setDate(9, Utility.getDate(personalDetailsWrapper.visaExpDate));
			pstmt.setString(10, Utility.trim(personalDetailsWrapper.drivingLicenseNo));
			
			pstmt.setDate(11, Utility.getDate(personalDetailsWrapper.drivingLicenseExpDate));
			pstmt.setString(12, Utility.trim(personalDetailsWrapper.laborCardNo));
			
			pstmt.setDate(13, Utility.getDate(personalDetailsWrapper.laborCardExpDate));
			pstmt.setString(14, Utility.trim(personalDetailsWrapper.emiratesID));
			pstmt.setDate(15, Utility.getDate(personalDetailsWrapper.emiratesIDExpDate));
			
			pstmt.setString(16,Utility.trim(personalDetailsWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();

			
			
			personalDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
			
			System.out.println("Passport details updated " );
			
			
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
	*/
	
	
	
}