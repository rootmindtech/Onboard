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

public class ContactDetailsHelper extends Helper {
	

	
	
	
	public AbstractWrapper fetchContactDetails(ContactDetailsWrapper contactDetailsWrapper)throws Exception {

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

		
		System.out.println(":"+ contactDetailsWrapper.mailOption);
	
	
		try {
			
				PopoverHelper popoverHelper = new PopoverHelper();
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,MailOption, DebitCardDeliveryChannel, Estatement, "
						+ "Email, Email2, Mobile, MobileSP, Mobile2, CourierAddress FROM RMT_OnBoard WHERE RefNo=?");
			
			
				System.out.println("contactDetails RefNo is" + contactDetailsWrapper.refNo);
				
				pstmt.setString(1,contactDetailsWrapper.refNo.trim());
			
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					 contactDetailsWrapper=new ContactDetailsWrapper();
				
					
					
					contactDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + contactDetailsWrapper.refNo);
					
	
					contactDetailsWrapper.mailOption=Utility.trim(resultSet.getString("MailOption"));
					System.out.println("OccupationType " + contactDetailsWrapper.mailOption);
					
					contactDetailsWrapper.debitCardDeliveryChnl=Utility.trim(resultSet.getString("DebitCardDeliveryChannel"));
					
					contactDetailsWrapper.eStatement=Utility.trim(resultSet.getString("Estatement"));
					
					contactDetailsWrapper.email=Utility.trim(resultSet.getString("Email"));
					
					contactDetailsWrapper.email2=Utility.trim(resultSet.getString("Email2"));
					
					contactDetailsWrapper.mobile=Utility.trim(resultSet.getString("Mobile"));
					
					contactDetailsWrapper.mobileSP=Utility.trim(resultSet.getString("MobileSP"));
					
					contactDetailsWrapper.mobile2=Utility.trim(resultSet.getString("Mobile2"));
					
					contactDetailsWrapper.courierAdd=Utility.trim(resultSet.getString("CourierAddress"));
					
				
					contactDetailsWrapper.recordFound=true;
					
					contactDetailsWrapper.mailOptionValue=popoverHelper.fetchPopoverDesc(contactDetailsWrapper.mailOption, "MailOption");
					contactDetailsWrapper.debitCardDeliveryChnlValue=popoverHelper.fetchPopoverDesc(contactDetailsWrapper.debitCardDeliveryChnl, "DebitCardDeliveryChnl");
				
					
					contactDetailsWrapper.eStatementValue=popoverHelper.fetchPopoverDesc(contactDetailsWrapper.eStatement, "Decision");
					contactDetailsWrapper.mobileSPValue=popoverHelper.fetchPopoverDesc(contactDetailsWrapper.mobileSP, "ServiceProvider");
					System.out.println("ContacDetailsWrapper " );
	
					vector.addElement(contactDetailsWrapper);
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.contactDetailsWrapper = new ContactDetailsWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.contactDetailsWrapper);
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
	
	public ContactDetailsWrapper fetchContactDetails(ContactDetailsWrapper contactDetailsWrapper,String refNo)throws Exception {



		try {

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchContactDetails(contactDetailsWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				contactDetailsWrapper = dataArrayWrapper.contactDetailsWrapper[0];
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

		return contactDetailsWrapper;

		}
	
	public AbstractWrapper updateContactDetails(UsersWrapper usersProfileWrapper,ContactDetailsWrapper contactDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("Update Contact Details");
	
		try {
			con = getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET MailOption=?, DebitCardDeliveryChannel=?, Estatement=?, "
					+ "Email=?, Email2=?, Mobile=?, MobileSP=?, Mobile2=?, CourierAddress=? ,ModifierId=?,ModifierDateTime=? where RefNo=?");
			
			
			
			//contactDetailsWrapper.refNo="CUS001";
			
			pstmt.setString(1,Utility.trim(contactDetailsWrapper.mailOption));
			pstmt.setString(2,Utility.trim(contactDetailsWrapper.debitCardDeliveryChnl));
			pstmt.setString(3,Utility.trim(contactDetailsWrapper.eStatement));
			pstmt.setString(4, Utility.trim(contactDetailsWrapper.email));
			pstmt.setString(5,Utility.trim(contactDetailsWrapper.email2));
			pstmt.setString(6, Utility.trim(contactDetailsWrapper.mobile));
			
			pstmt.setString(7, Utility.trim(contactDetailsWrapper.mobileSP));
			pstmt.setString(8, Utility.trim(contactDetailsWrapper.mobile2));
			pstmt.setString(9, Utility.trim(contactDetailsWrapper.courierAdd));
			pstmt.setString(10,Utility.trim(usersProfileWrapper.userid));//modifierId
			pstmt.setTimestamp(11,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
			
			pstmt.setString(12,Utility.trim(contactDetailsWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();
			
/*			//---------update PathStatus
			
			pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET CONTACT=? WHERE RefNo=?");
			
			pstmt.setString(1,"Y");
			pstmt.setString(2,contactDetailsWrapper.refNo);
			pstmt.executeUpdate();
			
			pstmt.close();
			//-----------------------
*/			
			
			//---------update PathStatus
			PathStatusHelper pathStatusHelper=new PathStatusHelper();
			dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(contactDetailsWrapper.refNo),"CONTACT");
			//--
			
			
			contactDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.contactDetailsWrapper=new ContactDetailsWrapper[1];
			dataArrayWrapper.contactDetailsWrapper[0]=contactDetailsWrapper;
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Contact details updated " );
			
			
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
	
	
	/*public AbstractWrapper insertContactDetails(ContactDetailsWrapper contactDetailsWrapper)throws Exception {

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
			con = getConnection("ONBOARD");
			
			sql="INSERT INTO RMT_ContactDetails(MailOption,DebitCardDeliveryChannel,EStatement, "
					+ "EmailAddress,EmailAddress2,MobileNumber,MobileNumber2,ServicerProvider, "
					+ "CourierAddress) Values "
					+ "(?,?,?,?,?,?,?,?,?)";
			
			
			
	
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			pstmt.setString(1,Utility.trim(contactDetailsWrapper.mailOption));
			pstmt.setString(2,Utility.trim(contactDetailsWrapper.debitCardDeliveryChannel));
			pstmt.setString(3,Utility.trim(contactDetailsWrapper.eStatement));
			pstmt.setString(4,Utility.trim(contactDetailsWrapper.emailAddress));
			pstmt.setString(5,Utility.trim(contactDetailsWrapper.emailAddress2));
			pstmt.setString(6,Utility.trim(contactDetailsWrapper.mobileNumber));
			pstmt.setString(7,Utility.trim(contactDetailsWrapper.mobileNumber2));
			pstmt.setString(8,Utility.trim(contactDetailsWrapper.serviceProvider));
			pstmt.setString(9,Utility.trim(contactDetailsWrapper.courierAddress));
		

	
			pstmt.executeUpdate();
			pstmt.close();
	//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_ContactDetails(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(contactDetailsWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(contactDetailsWrapper.makerDate));
			pstmt.setString(3,Utility.trim(contactDetailsWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(contactDetailsWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
	
			//------------------
			

			contactDetailsWrapper.recordFound=true;
			
			
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

		return contactDetailsWrapper;
	}
	
	
	
	public AbstractWrapper fetchContactDetails(ContactDetailsWrapper contactDetailsWrapper)throws Exception {

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

		
		System.out.println("Fetch ContactDetails dcdeliveryChannel:"+ contactDetailsWrapper.debitCardDeliveryChannel);
	
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,MailOption,DebitCardDeliveryChannel,EStatement, "
					+ "EmailAddress,EmailAddress2,MobileNumber,MobileNumber2,ServicerProvider, "
					+ "CourierAddress FROM RMT_ContactDetails");
		
			//pstmt.setString(1,contactDetailsWrapper.existingCIFNumber.trim());
			//pstmt.setString(2,contactDetailsWrapper.existingAccountNo.trim());
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				
				contactDetailsWrapper.mailOption=Utility.trim(resultSet.getString("MailOption"));
				System.out.println("MailOption" + contactDetailsWrapper.mailOption);

				contactDetailsWrapper.debitCardDeliveryChannel=Utility.trim(resultSet.getString("DebitCardDeliveryChannel"));
				System.out.println("DebitCardDeliveryChannel " + contactDetailsWrapper.debitCardDeliveryChannel);
				
				contactDetailsWrapper.eStatement=Utility.trim(resultSet.getString("EStatement"));
				System.out.println("EStatement" + contactDetailsWrapper.mailOption);

				contactDetailsWrapper.emailAddress=Utility.trim(resultSet.getString("EmailAddress"));
				System.out.println("EmailAddress" + contactDetailsWrapper.emailAddress);

				contactDetailsWrapper.emailAddress2=Utility.trim(resultSet.getString("EmailAddress2"));
				System.out.println("EmailAddress2" + contactDetailsWrapper.emailAddress2);

				contactDetailsWrapper.mobileNumber=Utility.trim(resultSet.getString("MobileNumber"));
				System.out.println("MobileNumber" + contactDetailsWrapper.mobileNumber);
				
				contactDetailsWrapper.mobileNumber2=Utility.trim(resultSet.getString("MobileNumber2"));
				System.out.println("MobileNumber2" + contactDetailsWrapper.mobileNumber2);
				
				contactDetailsWrapper.serviceProvider=Utility.trim(resultSet.getString("ServicerProvider"));
				System.out.println("ServicerProvider" + contactDetailsWrapper.serviceProvider);
				
				contactDetailsWrapper.courierAddress=Utility.trim(resultSet.getString("CourierAddress"));
				System.out.println("CourierAddress" + contactDetailsWrapper.courierAddress);
				

				
				
				
				contactDetailsWrapper.recordFound=true;
				
				System.out.println("contactDetailsWrapper " );

				vector.addElement(contactDetailsWrapper);

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
	
	
	
	
	
	public AbstractWrapper updateContactDetails(ContactDetailsWrapper contactDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		//ContactDetailsWrapper contactDetailsWrapper=new ContactDetailsWrapper();
		//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
	

		System.out.println("updateContactDetails");
	
		try {
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_ContactDetails SET MailOption=?,DebitCardDeliveryChannel=?, "
					+ "EStatement=?, EmailAddress=?,EmailAddress2=?,MobileNumber=?,MobileNumber2=?,ServicerProvider=?, "
					+ "CourierAddress=?  where RefNo=? ");
			
			pstmt.setString(1,Utility.trim(contactDetailsWrapper.mailOption));
			
			pstmt.setString(2,Utility.trim(contactDetailsWrapper.debitCardDeliveryChannel));
			pstmt.setString(3,Utility.trim(contactDetailsWrapper.eStatement));
			
			pstmt.setString(4,Utility.trim(contactDetailsWrapper.emailAddress));
			
			pstmt.setString(5,Utility.trim(contactDetailsWrapper.emailAddress2));
			
			pstmt.setString(6,Utility.trim(contactDetailsWrapper.mobileNumber));
			
			pstmt.setString(7,Utility.trim(contactDetailsWrapper.mobileNumber2));
			
			pstmt.setString(8,Utility.trim(contactDetailsWrapper.serviceProvider));
			
			pstmt.setString(9,Utility.trim(contactDetailsWrapper.courierAddress));
			
			pstmt.setString(10,Utility.trim(contactDetailsWrapper.refNo));
			
			
			pstmt.executeUpdate();

			pstmt.close();

			
			
			contactDetailsWrapper.recordFound=true;
			
			//dataArrayWrapper.contactDetailsWrapper=new ContactDetailsWrapper[1];
			//dataArrayWrapper.contactDetailsWrapper[0]=contactDetailsWrapper;
			
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

		return contactDetailsWrapper;
	}
	*/

}
