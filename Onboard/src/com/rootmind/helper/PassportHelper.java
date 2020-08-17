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

public class PassportHelper extends Helper {
	
	
	public AbstractWrapper updatePassportDetails(UsersWrapper usersProfileWrapper,PassportWrapper passportWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		//PreferredLanguage,FamilyInUAE,FamilySizeUAE, "+ "CarOwnership,CarYear,Media,FavouriteCity, Domicile
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("updatePassport");
	
		try {
			con = getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET MotherMaidenName=?, Segment=?, PreferredLanguage=?,FamilyInUAE=?,"
					+ "FamilySizeUAE=?,CarOwnership=?,CarYear=?,Media=?,FavouriteCity=?,Domicile=?,ImmiFileNumber=?, VisaIssuePlace=?, "
					+ "VisaIssueDate=?, VisaExpDate=?, DrivingLicenseNO=?, DrivingLicenseExpDate=?,"
					+ " LabourCardNo=?, LabourCardExpDate=?,ModifierId=?,ModifierDateTime=? where RefNo=?");
			
			
			//passportWrapper.refNo="CUS001";
			
//			pstmt.setString(1,Utility.trim(passportWrapper.passportNo));
//			pstmt.setDate(2,Utility.getDate(passportWrapper.passportIssueDate));
//			pstmt.setDate(3,Utility.getDate(passportWrapper.passportExpDate));
//			pstmt.setString(4,Utility.trim(passportWrapper.passportIssuePlace));
//			pstmt.setString(5,Utility.trim(passportWrapper.passportIssueCountry));
//			pstmt.setString(14,Utility.trim(passportWrapper.emiratesID));
//			pstmt.setDate(15,Utility.getDate(passportWrapper.emiratesIDExpDate));
//			
			pstmt.setString(1,Utility.trim(passportWrapper.motherMaidenName));
			pstmt.setString(2,Utility.trim(passportWrapper.segment));
			pstmt.setString(3,Utility.trim(passportWrapper.preferredLanguage));
			pstmt.setString(4,Utility.trim(passportWrapper.familyInUAE));
			pstmt.setString(5,Utility.trim(passportWrapper.familySizeUAE));
			pstmt.setString(6,Utility.trim(passportWrapper.carOwnership));
			pstmt.setString(7,Utility.trim(passportWrapper.carYear));
			pstmt.setString(8,Utility.trim(passportWrapper.media));
			pstmt.setString(9,Utility.trim(passportWrapper.favouriteCity));
			pstmt.setString(10,Utility.trim(passportWrapper.domicile));
						
			pstmt.setString(11,Utility.trim(passportWrapper.immiFileNumber));
			pstmt.setString(12,Utility.trim(passportWrapper.visaIssuePlace));
			pstmt.setDate(13,Utility.getDate(passportWrapper.visaIssueDate));
			pstmt.setDate(14,Utility.getDate(passportWrapper.visaExpDate));
			pstmt.setString(15,Utility.trim(passportWrapper.drivingLicenseNo));
			pstmt.setDate(16,Utility.getDate(passportWrapper.drivingLicenseExpDate));
			pstmt.setString(17,Utility.trim(passportWrapper.labourCardNo));
			pstmt.setDate(18,Utility.getDate(passportWrapper.labourCardExpDate));
			
			pstmt.setString(19,Utility.trim(usersProfileWrapper.userid));//modifierId
			pstmt.setTimestamp(20,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
			pstmt.setString(21,Utility.trim(passportWrapper.refNo));
			
			
			pstmt.executeUpdate();

			pstmt.close();

/*			//---------update PathStatus
			
			pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET IDENTIFICATION=? WHERE RefNo=?");
			
			pstmt.setString(1,"Y");
			pstmt.setString(2,passportWrapper.refNo);
			pstmt.executeUpdate();
			
			pstmt.close();
			//-----------------------
*/			
			
			
			//---------update PathStatus
			PathStatusHelper pathStatusHelper=new PathStatusHelper();
			dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(passportWrapper.refNo),"IDENTITY");
			//--
			
			
			passportWrapper.recordFound=true;
			
			dataArrayWrapper.passportWrapper=new PassportWrapper[1];
			dataArrayWrapper.passportWrapper[0]=passportWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Passport Details updated " );
			
			
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
	
	
	
	
	
	public AbstractWrapper fetchPassportDetails(PassportWrapper passportWrapper)throws Exception {

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
			
			
				PopoverHelper	popoverHelper = new PopoverHelper();
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT  MotherMaidenName, Segment, PreferredLanguage,FamilyInUAE,FamilySizeUAE,CarOwnership,CarYear,Media,FavouriteCity,Domicile,"
						+ " ImmiFileNumber, VisaIssuePlace, VisaIssueDate, VisaExpDate, DrivingLicenseNO, DrivingLicenseExpDate, LabourCardNo, "
						+ " LabourCardExpDate FROM RMT_OnBoard WHERE RefNo=?");
				
				
				System.out.println("passportWrapper RefNo is" + passportWrapper.refNo);
				
				pstmt.setString(1,passportWrapper.refNo.trim());
			
		
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					 passportWrapper = new PassportWrapper();
				
//					passportWrapper.passportNo=Utility.trim(resultSet.getString("PassportNo"));
//					System.out.println("PassportNo" + passportWrapper.passportNo);
//	
//					passportWrapper.passportIssueDate=Utility.setDate(resultSet.getString("PassportIssueDate"));
//					System.out.println("PassportIssueDate " + passportWrapper.passportIssueDate);
//					
//					passportWrapper.passportExpDate=Utility.setDate(resultSet.getString("PassportExpDate"));
//					
//					passportWrapper.passportIssuePlace=Utility.trim(resultSet.getString("PassportIssueplace"));
//					
//					passportWrapper.passportIssueCountry=Utility.trim(resultSet.getString("PassportIssueCountry"));
//					passportWrapper.emiratesID=Utility.trim(resultSet.getString("EmiratesID"));
//	    			passportWrapper.emiratesIDExpDate=Utility.setDate(resultSet.getString("EmiratesIDExpDate"));
					
					
					 passportWrapper.motherMaidenName=Utility.trim(resultSet.getString("MotherMaidenName"));
					 passportWrapper.segment=Utility.trim(resultSet.getString("Segment"));
					 passportWrapper.preferredLanguage=Utility.trim(resultSet.getString("PreferredLanguage"));
					
					 passportWrapper.familyInUAE=Utility.trim(resultSet.getString("FamilyInUAE"));
					
					 passportWrapper.familySizeUAE=Utility.trim(resultSet.getString("FamilySizeUAE"));
					
					 passportWrapper.carOwnership=Utility.trim(resultSet.getString("CarOwnership"));
					
					 passportWrapper.carYear=Utility.trim(resultSet.getString("CarYear"));
					
					 passportWrapper.media=Utility.trim(resultSet.getString("Media"));
					
					 passportWrapper.favouriteCity=Utility.trim(resultSet.getString("FavouriteCity"));
					 passportWrapper.domicile=Utility.trim(resultSet.getString("Domicile"));
				
					
					passportWrapper.immiFileNumber=Utility.trim(resultSet.getString("ImmiFileNumber"));
					
					
					passportWrapper.visaIssuePlace=Utility.trim(resultSet.getString("VisaIssuePlace"));
					
					
					passportWrapper.visaIssueDate=Utility.setDate(resultSet.getString("VisaIssueDate"));
					
					
					passportWrapper.visaExpDate=Utility.setDate(resultSet.getString("VisaExpDate"));
					
					passportWrapper.drivingLicenseNo=Utility.trim(resultSet.getString("DrivingLicenseNO"));
					
					
					passportWrapper.drivingLicenseExpDate=Utility.setDate(resultSet.getString("DrivingLicenseExpDate"));
				
					
					passportWrapper.labourCardNo=Utility.trim(resultSet.getString("LabourCardNo"));
					
					
					passportWrapper.labourCardExpDate=Utility.setDate(resultSet.getString("LabourCardExpDate"));
				
					
				
					
					
					passportWrapper.recordFound=true;
					
				
//					passportWrapper.passportIssueCountryValue=popoverHelper.fetchPopoverDesc(passportWrapper.passportIssueCountry, "NATIONALITY");
					
					passportWrapper.segmentValue=popoverHelper.fetchPopoverDesc(passportWrapper.segment, "Segment");
					passportWrapper.preferredLanguageValue=popoverHelper.fetchPopoverDesc(passportWrapper.preferredLanguage, "PreferredLanguage");
					passportWrapper.familyInUAEValue=popoverHelper.fetchPopoverDesc(passportWrapper.familyInUAE, "Decision");
					passportWrapper.familySizeUAEValue=popoverHelper.fetchPopoverDesc(passportWrapper.familySizeUAE, "NUMBER");
					passportWrapper.carOwnershipValue=popoverHelper.fetchPopoverDesc(passportWrapper.carOwnership, "Decision");
					
					passportWrapper.mediaValue=popoverHelper.fetchPopoverDesc(passportWrapper.media, "Media");
					passportWrapper.favouriteCityValue=popoverHelper.fetchPopoverDesc(passportWrapper.favouriteCity, "FavouriteCity");
					
					
					vector.addElement(passportWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.passportWrapper = new PassportWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.passportWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			
			if(resultSet !=null) resultSet.close();
			pstmt.close();

			System.out.println("Fetch Passport Details Successful" );
			
			
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
	
	
public PassportWrapper fetchPassportDetails(PassportWrapper passportWrapper,String refNo)throws Exception {
		
		
		
		try {
		
			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchPassportDetails(passportWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				passportWrapper = dataArrayWrapper.passportWrapper[0];
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

		return passportWrapper;
		
	}
	
	
/*	
	public AbstractWrapper insertPassportDetails(PassportWrapper passportWrapper)throws Exception {

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
			con = getConnection("ONBOARD");
			
			sql="INSERT INTO RMT_Passport(PassportNo,PassportIssueDate, PassportExpDate, PassportIssueplace, PassportIssueCountry, ImmigrationFileNo, "
					+ " VissaIssuePlace,VissaIssueDate, VissaExparyDate, DrivingLicenseNO, DrivingLicenseExperyDate, LabourCardNo, LabourCardExparyDate,"
					+ " EmiratesID, EmiratesIDExparyDate) Values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, Utility.trim(passportWrapper.refNo));
			pstmt.setString(2,Utility.trim(passportWrapper.passportNo));
			pstmt.setDate(3,Utility.getDate(passportWrapper.passportIssueDate));
			pstmt.setDate(4,Utility.getDate(passportWrapper.passportExpDate));
			pstmt.setString(5,Utility.trim(passportWrapper.passportIssuePlace));
			pstmt.setString(6,Utility.trim(passportWrapper.passportIssueCountry));
			pstmt.setString(7,Utility.trim(passportWrapper.immiFileNumber));
			pstmt.setString(8,Utility.trim(passportWrapper.vissaIssuePlace));
			pstmt.setDate(9,Utility.getDate(passportWrapper.vissaIssueDate));
			pstmt.setDate(10,Utility.getDate(passportWrapper.vissaExpDate));
			pstmt.setString(11,Utility.trim(passportWrapper.drivingLiceseNo));
			pstmt.setDate(12,Utility.getDate(passportWrapper.drivingLicenseExpDate));
			pstmt.setString(13,Utility.trim(passportWrapper.laborCardNo));
			pstmt.setDate(14,Utility.getDate(passportWrapper.labourCardExpDate));
			pstmt.setString(15,Utility.trim(passportWrapper.emiratesID));
			pstmt.setDate(16,Utility.getDate(passportWrapper.emiratesIDExpDate));
			
			

	
			pstmt.executeUpdate();
			pstmt.close();
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_Pasport(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(passportWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(passportWrapper.makerDate));
			pstmt.setString(3,Utility.trim(passportWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(passportWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
			//............................
			
			

			passportWrapper.recordFound=true;
			
			dataArrayWrapper.passportWrapper=new PassportWrapper[1];
			dataArrayWrapper.passportWrapper[0]=passportWrapper;
			
			
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
	}*/
	
}