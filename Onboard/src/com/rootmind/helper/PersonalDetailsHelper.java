package com.rootmind.helper;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.Arrays;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;


public class PersonalDetailsHelper extends Helper {
	
	
	public AbstractWrapper insertPersonalDetails(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
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
		
		String workflowGroup=null;
		
		
		try {
			con = getConnection();
			
			sql=" INSERT INTO RMT_OnBoard(RefNo, ProductCode, ExistingCustomerFlag, ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, ExistingRelationship, RelationshipNo,CustomerName, FirstName, "
					+ " MiddleName ,LastName, Title, CategoryType, Branch, JointOwn, NatureOfRelation,ResidenceStatus,Nationality,DOB,Educated,MaritalStatus,Gender,PassportNo, PassportIssueDate, PassportExpDate, PassportIssueplace, "
					+ "PassportIssueCountry,EmiratesID, EmiratesIDExpDate,RecordStatus,MakerId,MakerDateTime) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			System.out.println("sql " + sql);
			
		
			
			PreparedStatement pstmt = con.prepareStatement(sql);
		
			personalDetailsWrapper.refNo=generateRefNo(personalDetailsWrapper);
			
			//This to be removed after incorporating firstname, middlename and lastname in iPad
			if(personalDetailsWrapper.customerName==null || personalDetailsWrapper.customerName.equals(""))
			{
				personalDetailsWrapper.customerName=personalDetailsWrapper.firstName + " " + personalDetailsWrapper.middleName+" "+personalDetailsWrapper.lastName;
			}
			/*if(personalDetailsWrapper.firstName==null && personalDetailsWrapper.middleName==null && personalDetailsWrapper.lastName==null)
			{
				personalDetailsWrapper.firstName=personalDetailsWrapper.customerName;
			}*/
			
			pstmt.setString(1,Utility.trim(personalDetailsWrapper.refNo));
			pstmt.setString(2,Utility.trim(personalDetailsWrapper.accountType)); //Product Code
			pstmt.setString(3,Utility.trim(personalDetailsWrapper.extCustomerFlag));
			pstmt.setString(4,Utility.trim(personalDetailsWrapper.cifNumber));
			pstmt.setString(5,Utility.trim(personalDetailsWrapper.extAccountNo));
			pstmt.setString(6,Utility.trim(personalDetailsWrapper.extCreditCardNo));
			pstmt.setString(7,Utility.trim(personalDetailsWrapper.extRelationship));
			pstmt.setString(8,Utility.trim(personalDetailsWrapper.relationshipNo));
			pstmt.setString(9,Utility.trim(personalDetailsWrapper.customerName));
			
			pstmt.setString(10,Utility.trim(personalDetailsWrapper.firstName));
			pstmt.setString(11,Utility.trim(personalDetailsWrapper.middleName));
			pstmt.setString(12,Utility.trim(personalDetailsWrapper.lastName));
//			pstmt.setString(13,Utility.trim(personalDetailsWrapper.motherMaidenName));
//			pstmt.setString(14,Utility.trim(personalDetailsWrapper.segment));
//			
			pstmt.setString(13,Utility.trim(personalDetailsWrapper.title));
			pstmt.setString(14,Utility.trim(personalDetailsWrapper.categoryType));
			pstmt.setString(15,Utility.trim(personalDetailsWrapper.branch));
			pstmt.setString(16,Utility.trim(personalDetailsWrapper.jointOwn));
			pstmt.setString(17,Utility.trim(personalDetailsWrapper.natureOfRelation));
			pstmt.setString(18,Utility.trim(personalDetailsWrapper.residenceStatus));
			pstmt.setString(19,Utility.trim(personalDetailsWrapper.nationality));
			pstmt.setDate(20,Utility.getDate(personalDetailsWrapper.dob));
			pstmt.setString(21,Utility.trim(personalDetailsWrapper.educated));
			pstmt.setString(22,Utility.trim(personalDetailsWrapper.maritalStatus));
			pstmt.setString(23,Utility.trim(personalDetailsWrapper.gender));
			pstmt.setString(24,Utility.trim(personalDetailsWrapper.passportNo));
			pstmt.setDate(25,Utility.getDate(personalDetailsWrapper.passportIssueDate));
			pstmt.setDate(26,Utility.getDate(personalDetailsWrapper.passportExpDate));
			pstmt.setString(27,Utility.trim(personalDetailsWrapper.passportIssuePlace));
			pstmt.setString(28,Utility.trim(personalDetailsWrapper.passportIssueCountry));
			pstmt.setString(29,Utility.trim(personalDetailsWrapper.emiratesID));
			pstmt.setDate(30,Utility.getDate(personalDetailsWrapper.emiratesIDExpDate));
			pstmt.setString(31,Utility.trim(personalDetailsWrapper.recordStatus));
			pstmt.setString(32,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
			System.out.println("insert usersProfileWrapper Userid "+usersProfileWrapper.userid);
			
			pstmt.setTimestamp(33,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
			pstmt.executeUpdate();
			pstmt.close();
			
			
/*			//---------insert PathStatus
			
			pstmt = con.prepareStatement("INSERT INTO RMT_PATHSTATUS(RefNo,PERSONAL) VALUES(?,?)");
			
			pstmt.setString(1,personalDetailsWrapper.refNo);
			pstmt.setString(2,"Y");
			
			pstmt.executeUpdate();
			
			pstmt.close();
			//-----------------------
*/			
			//---------update PathStatus
			PathStatusHelper pathStatusHelper=new PathStatusHelper();
			dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(personalDetailsWrapper.refNo),"PERSONAL");
			//--
			
			workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);

			//---------update workflowStatus
			WorkflowStatusHelper workflowStatusHelper=new WorkflowStatusHelper();
			workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),workflowGroup,"I"); // Status I=Inprogress
			 
			//--
			//-----------
			
			pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET WorkflowGroup=? WHERE RefNo=?");
			
			pstmt.setString(1,workflowGroup);
			pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
			
			///----Update Remarks
			personalDetailsWrapper.remarks="APPLICATION CREATED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime();
			updateRemarks(Utility.trim(personalDetailsWrapper.refNo),personalDetailsWrapper.remarks );
		
			
			//------------------------Insert RMT_WorkflowTrack----------------------------
			sql="INSERT INTO RMT_WorkflowTrack(RefNo,ProductCode,SeqNo,WorkflowGroup,Userid,Decline,ActionStatus,ActionDateTime,Remarks,TAT)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,Utility.trim(personalDetailsWrapper.refNo)); 
			pstmt.setString(2,Utility.trim(personalDetailsWrapper.accountType)); 
			pstmt.setString(3,"1"); //seqNo
			pstmt.setString(4,workflowGroup);
			pstmt.setString(5,Utility.trim(usersProfileWrapper.userid));
			pstmt.setString(6,Utility.trim(personalDetailsWrapper.decline));
			pstmt.setString(7,"CREATE");
			pstmt.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
			pstmt.setString(9,Utility.trim(personalDetailsWrapper.remarks));
			pstmt.setString(10,"0"); //TAT
			pstmt.executeUpdate();
			pstmt.close();
			
			//---------------------------------------END-----------------------------------------
			
			
			
			
			
			
			personalDetailsWrapper.recordFound=true;

			
		
		
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			
			System.out.println("Successfully inserted into RMT_OnBoard");
			
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
	
	
	public AbstractWrapper updatePersonalDetails(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
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
		
		
		
		
		try {
				con = getConnection();
				
				
				PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET ProductCode=?, ExistingCustomerFlag=?, ExistingCIFNumber=?, "
						+ "ExistingAccountNo=?, ExistingCreditCardNo=?, ExistingRelationship=?, RelationshipNo=?, CustomerName=?, FirstName=?,MiddleName=?,LastName=?, "
						+ " Title=?,CategoryType=?, Branch=?, JointOwn=?, NatureOfRelation=?, ResidenceStatus=?, Nationality=?, "
						+ " DOB=?, Educated=?, MaritalStatus=?, Gender=?,PassportNo=?, PassportIssueDate=?, PassportExpDate=?, PassportIssueplace=?, "
					    + "PassportIssueCountry=?,EmiratesID=?, EmiratesIDExpDate=?,RecordStatus=?, ModifierId=?, ModifierDateTime=? where RefNo=?");
				
				
				pstmt.setString(1,Utility.trim(personalDetailsWrapper.accountType)); // ProductCode
				pstmt.setString(2,Utility.trim(personalDetailsWrapper.extCustomerFlag));
				pstmt.setString(3,Utility.trim(personalDetailsWrapper.cifNumber));
				pstmt.setString(4,Utility.trim(personalDetailsWrapper.extAccountNo));
				pstmt.setString(5,Utility.trim(personalDetailsWrapper.extCreditCardNo));
				pstmt.setString(6,Utility.trim(personalDetailsWrapper.extRelationship));
				pstmt.setString(7,Utility.trim(personalDetailsWrapper.relationshipNo));
				
				if(personalDetailsWrapper.customerName==null || personalDetailsWrapper.customerName.equals(""))
				{
					personalDetailsWrapper.customerName=personalDetailsWrapper.firstName + " " + personalDetailsWrapper.middleName+" "+personalDetailsWrapper.lastName;
				}
			
				pstmt.setString(8,Utility.trim(personalDetailsWrapper.customerName));
				
				
				pstmt.setString(9,Utility.trim(personalDetailsWrapper.firstName));
				pstmt.setString(10,Utility.trim(personalDetailsWrapper.middleName));
				pstmt.setString(11,Utility.trim(personalDetailsWrapper.lastName));
//				pstmt.setString(12,Utility.trim(personalDetailsWrapper.motherMaidenName));
//				pstmt.setString(13,Utility.trim(personalDetailsWrapper.segment));
//				
				pstmt.setString(12,Utility.trim(personalDetailsWrapper.title));
				pstmt.setString(13,Utility.trim(personalDetailsWrapper.categoryType));
				pstmt.setString(14,Utility.trim(personalDetailsWrapper.branch));
				pstmt.setString(15,Utility.trim(personalDetailsWrapper.jointOwn));
				pstmt.setString(16,Utility.trim(personalDetailsWrapper.natureOfRelation));
				pstmt.setString(17,Utility.trim(personalDetailsWrapper.residenceStatus));
				pstmt.setString(18,Utility.trim(personalDetailsWrapper.nationality));
				pstmt.setDate(19,Utility.getDate(personalDetailsWrapper.dob));
				pstmt.setString(20,Utility.trim(personalDetailsWrapper.educated));
				pstmt.setString(21,Utility.trim(personalDetailsWrapper.maritalStatus));
				pstmt.setString(22,Utility.trim(personalDetailsWrapper.gender));
				
				pstmt.setString(23,Utility.trim(personalDetailsWrapper.passportNo));
				pstmt.setDate(24,Utility.getDate(personalDetailsWrapper.passportIssueDate));
				pstmt.setDate(25,Utility.getDate(personalDetailsWrapper.passportExpDate));
				pstmt.setString(26,Utility.trim(personalDetailsWrapper.passportIssuePlace));
				pstmt.setString(27,Utility.trim(personalDetailsWrapper.passportIssueCountry));
				pstmt.setString(28,Utility.trim(personalDetailsWrapper.emiratesID));
				pstmt.setDate(29,Utility.getDate(personalDetailsWrapper.emiratesIDExpDate));
				
				
//				pstmt.setString(25,Utility.trim(personalDetailsWrapper.preferredLanguage));
//				pstmt.setString(26,Utility.trim(personalDetailsWrapper.familyInUAE));
//				pstmt.setString(27,Utility.trim(personalDetailsWrapper.familySizeUAE));
//				pstmt.setString(28,Utility.trim(personalDetailsWrapper.carOwnership));
//				pstmt.setString(29,Utility.trim(personalDetailsWrapper.carYear));
//				pstmt.setString(30,Utility.trim(personalDetailsWrapper.media));
//				pstmt.setString(31,Utility.trim(personalDetailsWrapper.favouriteCity));
//				pstmt.setString(32,Utility.trim(personalDetailsWrapper.domicile));
//				
				pstmt.setString(30,Utility.trim(personalDetailsWrapper.recordStatus));
				pstmt.setString(31,Utility.trim(usersProfileWrapper.userid)); //modifierID from userprofile
				pstmt.setTimestamp(32,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
	
				pstmt.setString(33,Utility.trim(personalDetailsWrapper.refNo));
				
				
				pstmt.executeUpdate();
				pstmt.close();
				
/*				//---------update PathStatus
				
				pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET PERSONAL=? WHERE RefNo=?");
				
				
				pstmt.setString(1,"Y");
				pstmt.setString(2,personalDetailsWrapper.refNo);
				pstmt.executeUpdate();
				
				pstmt.close();
				//-----------------------
*/	
				
				//---------update PathStatus
				PathStatusHelper pathStatusHelper=new PathStatusHelper();
				dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(personalDetailsWrapper.refNo),"PERSONAL");
				
				//--
				
				personalDetailsWrapper.recordFound=true;
				
			
			
				dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
				dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Successfully Personal Details Updated");
				
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
	
	public AbstractWrapper updateApplicationStatus(UsersWrapper usersProfileWrapper, PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
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
		PreparedStatement pstmt=null;
		
		String workflowGroup=null;
		
		try {
				con = getConnection();
				
				System.out.println("Update Application Status ");
				//---------update RMT_OnBoard for decline 
				
				pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET Decline=?,DeclineReason=? WHERE RefNo=?");
				
				pstmt.setString(1,personalDetailsWrapper.decline);
				pstmt.setString(2,personalDetailsWrapper.declineReason);
				pstmt.setString(3,personalDetailsWrapper.refNo);
				pstmt.executeUpdate();
				
				pstmt.close();
				//-----------------------
				
				
				pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET RecordStatus=? where RefNo=?");
			
				pstmt.setString(1,Utility.trim(personalDetailsWrapper.recordStatus));  //CREATE(SALESUSER SENDS APPLICATION TO APPROVER)
				pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
				
				pstmt.executeUpdate();
				pstmt.close();
	
				personalDetailsWrapper.recordFound=true;
				
				//-------------
				workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);
				personalDetailsWrapper.workflowGroup=workflowGroup;
				
				System.out.println("Update Application Status workflowGroup  "+personalDetailsWrapper.workflowGroup);
				System.out.println("Update Application Status recordStatus  "+personalDetailsWrapper.recordStatus);
				
				//WHEN RECORD IS REJECTED, IT GOES TO SALESUSER
				if(personalDetailsWrapper.recordStatus.equals("REJECTED"))
				{
					
					
					//------Update Record Status
					
					pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET RecordStatus=?, ModifierID=?, ModifierDateTime=? WHERE RefNo=?");
					
					pstmt.setString(1,"REJECTED");
					//pstmt.setString(2,Utility.trim(rejectWrapperArray[0].remarks));
					pstmt.setString(2,Utility.trim(usersProfileWrapper.userid)); //MODIFIER from userprofile
					pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));//MODIFIERDateTime
					pstmt.setString(4,Utility.trim(personalDetailsWrapper.refNo));
					pstmt.executeUpdate();
					pstmt.close();
					
					
					//----
					
					if(personalDetailsWrapper.remarks==null)
					{
						personalDetailsWrapper.remarks="";
					}
					
					updateRemarks(personalDetailsWrapper.refNo,personalDetailsWrapper.remarks +"\n" +"APPLICATION REJECTED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime());
					
					//-----UPDATE WORKFLOW TRACK-----------
					updateWorkflowTrack(usersProfileWrapper, personalDetailsWrapper);
					
					//---------update workflowStatus
					//reset all status in the workflow
					pstmt = con.prepareStatement("UPDATE RMT_WorkflowStatus SET Status=? where RefNo=?");

					pstmt.setString(1,"Q");
					pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
					pstmt.executeUpdate();
					pstmt.close();
					
					//then update salesuser to I
					WorkflowStatusHelper workflowStatusHelper=new WorkflowStatusHelper();
					workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),"SALESUSER","I");
					//--
					
						
					//--reset in RMT_OnBoard  WorkflowGroup  to "SALESUSER" 
					pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET WorkflowGroup=? WHERE RefNo=?");
					
					pstmt.setString(1,"SALESUSER");
					pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
					
					pstmt.executeUpdate();
					
					pstmt.close();
					
				}
				else
				{
					
					//when moving record from SALESUSER to TEAMLEAD
					
					System.out.println("Update Application Record Status Not Rejected ");
			
					//---------update workflowStatus
					WorkflowStatusHelper workflowStatusHelper=new WorkflowStatusHelper();
					workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),workflowGroup,"C");
					//--
					
					String nextWorkflowGroup=workflowStatusHelper.getNextWorkflowGroup(workflowGroup);
					
					System.out.println("Update Application Status nextWorkflowGroup "+nextWorkflowGroup);
					
					if(nextWorkflowGroup!=null && !workflowGroup.equals(nextWorkflowGroup))
					{
						
						System.out.println("Update Application update workflowStatus ");
						//---------update workflowStatus
						workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),nextWorkflowGroup,"I");
						
						
						//--
						if(pstmt !=null){pstmt.close();} 
						pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET WorkflowGroup=? WHERE RefNo=?");
						
						pstmt.setString(1,nextWorkflowGroup);
						pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
						
						pstmt.executeUpdate();
						
						pstmt.close();
						
					}
				
					updateRemarks(Utility.trim(personalDetailsWrapper.refNo), "APPLICATION SUBMITTED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime());
					
					//-----UPDATE WORKFLOW TRACK-----------
					updateWorkflowTrack(usersProfileWrapper, personalDetailsWrapper);
				}	
				
				
				
				
				
				
				
				dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
				dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Successfully Application Status Updated");
				
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
	
	
	public String generateRefNo(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
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
			
			System.out.println("Savings Account  " + personalDetailsWrapper.accountType);
			
			//System.out.println("Savings Account " + personalDetailsWrapper.accountType.substring(0,2));
			
			finalRefNo=personalDetailsWrapper.accountType.trim()+dmyFormat.format(new java.util.Date()).toUpperCase()+String.format("%0" +paddingSize +"d",refNo);
			
			
			
			//personalDetailsWrapper.recordFound=true;
			
		
		
			dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
			dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Successfully generated refno " + finalRefNo);
			
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
	
	
	
	public AbstractWrapper fetchPersonalDetails(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

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
			
			PopoverHelper popoverHelper = new PopoverHelper();
			
			
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,ProductCode, ExistingCustomerFlag,ExistingCIFNumber, ExistingAccountNo, "
					+ "ExistingCreditCardNo, ExistingRelationship, RelationshipNo,CustomerName, FirstName, MiddleName, LastName,"
					+ " Title, CategoryType, Branch, JointOwn, NatureOfRelation,ResidenceStatus,Nationality,DOB,Educated,MaritalStatus, "
					+ "Gender,PassportNo, PassportIssueDate, PassportExpDate, PassportIssueplace, "
					+ "PassportIssueCountry,EmiratesID, EmiratesIDExpDate FROM RMT_OnBoard WHERE RefNo=?");
			
			
			System.out.println("PersonalDetails RefNo is" + personalDetailsWrapper.refNo);
			
			pstmt.setString(1,personalDetailsWrapper.refNo.trim());
		
			
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				personalDetailsWrapper= new PersonalDetailsWrapper();
				
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + personalDetailsWrapper.refNo);
				
				personalDetailsWrapper.existingRefNo=Utility.trim(resultSet.getString("RefNo"));//for existingcustomer ref no capture
				System.out.println("In Personal Existing RefNo " + personalDetailsWrapper.existingRefNo);
				
				
				personalDetailsWrapper.accountType=Utility.trim(resultSet.getString("ProductCode"));
				System.out.println(" Account Type" + personalDetailsWrapper.accountType);
				
				personalDetailsWrapper.extCustomerFlag=Utility.trim(resultSet.getString("ExistingCustomerFlag"));
				System.out.println(" Existing Customer Flag" + personalDetailsWrapper.extCustomerFlag);
				
				personalDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));

				System.out.println("Personal Fetch Existing CIFNumber " + personalDetailsWrapper.cifNumber);
				
				personalDetailsWrapper.extAccountNo=Utility.trim(resultSet.getString("ExistingAccountNo"));
				
				personalDetailsWrapper.extCreditCardNo=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
				
				personalDetailsWrapper.extRelationship=Utility.trim(resultSet.getString("ExistingRelationship"));
				
				personalDetailsWrapper.relationshipNo=Utility.trim(resultSet.getString("RelationshipNo"));
				personalDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
				personalDetailsWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
				personalDetailsWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
				personalDetailsWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
				
//				personalDetailsWrapper.motherMaidenName=Utility.trim(resultSet.getString("MotherMaidenName"));
//				personalDetailsWrapper.segment=Utility.trim(resultSet.getString("Segment"));
//			
				personalDetailsWrapper.title=Utility.trim(resultSet.getString("Title"));
				
				personalDetailsWrapper.categoryType=Utility.trim(resultSet.getString("CategoryType"));
				
				personalDetailsWrapper.branch=Utility.trim(resultSet.getString("Branch"));
				
				personalDetailsWrapper.jointOwn=Utility.trim(resultSet.getString("JointOwn"));
				
				personalDetailsWrapper.natureOfRelation=Utility.trim(resultSet.getString("NatureOfRelation"));
				
				personalDetailsWrapper.residenceStatus=Utility.trim(resultSet.getString("ResidenceStatus"));
				
				personalDetailsWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
				
				personalDetailsWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
				
				personalDetailsWrapper.educated=Utility.trim(resultSet.getString("Educated"));
				
				personalDetailsWrapper.maritalStatus=Utility.trim(resultSet.getString("MaritalStatus"));
				
				personalDetailsWrapper.gender=Utility.trim(resultSet.getString("Gender"));
				
				
				personalDetailsWrapper.passportNo=Utility.trim(resultSet.getString("PassportNo"));

				personalDetailsWrapper.passportIssueDate=Utility.setDate(resultSet.getString("PassportIssueDate"));
				
				personalDetailsWrapper.passportExpDate=Utility.setDate(resultSet.getString("PassportExpDate"));
				
				personalDetailsWrapper.passportIssuePlace=Utility.trim(resultSet.getString("PassportIssueplace"));
				
				personalDetailsWrapper.passportIssueCountry=Utility.trim(resultSet.getString("PassportIssueCountry"));
				personalDetailsWrapper.emiratesID=Utility.trim(resultSet.getString("EmiratesID"));
				personalDetailsWrapper.emiratesIDExpDate=Utility.setDate(resultSet.getString("EmiratesIDExpDate"));
				
//				personalDetailsWrapper.preferredLanguage=Utility.trim(resultSet.getString("PreferredLanguage"));
//				
//				personalDetailsWrapper.familyInUAE=Utility.trim(resultSet.getString("FamilyInUAE"));
//				
//				personalDetailsWrapper.familySizeUAE=Utility.trim(resultSet.getString("FamilySizeUAE"));
//				
//				personalDetailsWrapper.carOwnership=Utility.trim(resultSet.getString("CarOwnership"));
//				
//				personalDetailsWrapper.carYear=Utility.trim(resultSet.getString("CarYear"));
//				
//				personalDetailsWrapper.media=Utility.trim(resultSet.getString("Media"));
//				
//				personalDetailsWrapper.favouriteCity=Utility.trim(resultSet.getString("FavouriteCity"));
//				personalDetailsWrapper.domicile=Utility.trim(resultSet.getString("Domicile"));
				
				
				personalDetailsWrapper.extRelationshipValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.extRelationship,"ExistingRelationship");
				personalDetailsWrapper.categoryTypeValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.categoryType,"CategoryType");
				personalDetailsWrapper.titleValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.title,"Title");
				personalDetailsWrapper.branchValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.branch,"Branch");
				personalDetailsWrapper.jointOwnValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.jointOwn, "Decision");
				personalDetailsWrapper.natureOfRelationValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.natureOfRelation, "NatureOfRelation");
				personalDetailsWrapper.residenceStatusValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.residenceStatus, "Residence");
				personalDetailsWrapper.nationalityValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.nationality, "NATIONALITY");
				personalDetailsWrapper.educatedValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.educated, "Education");
				personalDetailsWrapper.maritalStatusValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.maritalStatus, "MaritalStatus");
				personalDetailsWrapper.genderValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.gender, "Gender");
				personalDetailsWrapper.passportIssueCountryValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.passportIssueCountry, "NATIONALITY");
				System.out.println(" passportIssueCountryValue "+ personalDetailsWrapper.passportIssueCountryValue);
//				personalDetailsWrapper.preferredLanguageValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.preferredLanguage, "PREFERREDLANGUAGE");
//				personalDetailsWrapper.familyInUAEValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.familyInUAE, "DECISION");
//				personalDetailsWrapper.familySizeUAEValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.familySizeUAE, "NUMBER");
//				personalDetailsWrapper.carOwnershipValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.carOwnership, "DECISION");
//				
//				personalDetailsWrapper.mediaValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.media, "MEDIA");
//				personalDetailsWrapper.favouriteCityValue=popoverHelper.fetchPopoverDesc(personalDetailsWrapper.favouriteCity, "FAVOURITECITY");
				
				personalDetailsWrapper.recordFound=true;
			
				vector.addElement(personalDetailsWrapper);

			}
		
			if (vector.size()>0)
			{
				dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());
				
				System.out.println("fetch Personal Details Successful " );

			}
			else
				
			{
				dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[1];
				dataArrayWrapper.personalDetailsWrapper[0]= personalDetailsWrapper;
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
	
	public PersonalDetailsWrapper fetchPersonalDetails(PersonalDetailsWrapper personalDetailsWrapper,String refNo)throws Exception {
		
		
		
		try {
		
			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchPersonalDetails(personalDetailsWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				personalDetailsWrapper = dataArrayWrapper.personalDetailsWrapper[0];
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

		return personalDetailsWrapper;
		
	}
		
	public AbstractWrapper fetchOnBoardQueue(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		ResultSet resultSetSub = null;
		
		System.out.println("fetchOnBoardQueue");
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
		System.out.println("Fetch Queue RecordStatus "+ personalDetailsWrapper.recordStatus);
		String sql=null;
		String filterSQL="";
		String whereClause="";
		//boolean recordStatusParam=false;
		//boolean makerIdParam=false;
		PreparedStatement pstmt=null;
		PreparedStatement pstmtSub=null;
		int queueMaxRecords=0;
		int queueNoRecords=0;
		String workflowGroup=null;
		int n=0;
		int targetTAT=0;
		try {
			
				con = getConnection();
				
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
				
				workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);
				
				
				
				sql="SELECT RefNo, ProductCode, ExistingCustomerFlag,ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, ExistingRelationship, RelationshipNo,CustomerName,FirstName, "
						+ "MiddleName,LastName, Title, CategoryType, Branch, JointOwn, NatureOfRelation,ResidenceStatus, "
						+ "Nationality,DOB,Educated,MaritalStatus,Gender,PassportNo, PassportIssueDate, PassportExpDate, PassportIssueplace, "
					    + "PassportIssueCountry,EmiratesID, EmiratesIDExpDate,MakerId ,MakerDateTime ,RecordStatus, Decline, DeclineReason, WorkflowGroup, ModifierDateTime FROM RMT_OnBoard";
				
				
				if(personalDetailsWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					whereClause = " WHERE Makerid=? AND WorkflowGroup=? AND RecordStatus IN ('CREATE','INPROGRESS','REJECTED')";
				}	
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					whereClause=  " WHERE WorkflowGroup=? AND RecordStatus IN ('CREATE','INPROGRESS') ";
				}
				else if(personalDetailsWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					whereClause=  " WHERE Makerid=? ";
				}
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					whereClause=  " WHERE 1=1 ";
				}
				

			    if(personalDetailsWrapper.refNo !=null && !personalDetailsWrapper.refNo.trim().isEmpty())
				{
			    	filterSQL= " AND RefNo =? ";
					
					System.out.println("fetchOnBoardQueue RefNo " + sql);
					
				}
				
				else if(personalDetailsWrapper.customerName !=null && !personalDetailsWrapper.customerName.trim().isEmpty())
				{
					
					
					filterSQL=" AND (UPPER(CustomerName) LIKE ? OR UPPER(FirstName) LIKE ? OR UPPER(MiddleName) LIKE ? OR UPPER(LastName) LIKE ?)";
					
					
					System.out.println("fetchOnBoardQueue customerName " +personalDetailsWrapper.customerName);
					
					
				}
				
				else if(personalDetailsWrapper.searchStartDate !=null && !personalDetailsWrapper.searchStartDate.trim().isEmpty())
				{
					
					filterSQL= " AND MakerDateTime >= ?";
						
				}
				
				else if(personalDetailsWrapper.searchEndDate !=null && !personalDetailsWrapper.searchEndDate.trim().isEmpty())
				{
					
					filterSQL=" AND MakerDateTime <= ?";
					
					
				}

				
				sql = sql + whereClause + filterSQL + "  LIMIT " + queueMaxRecords ;
				
				
				System.out.println("personal details final sql "+sql);
				
				pstmt = con.prepareStatement(sql);
				
				
				if(personalDetailsWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, personalDetailsWrapper.makerId.trim());
					pstmt.setString(++n, workflowGroup);
					
					
				}	
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, workflowGroup);
					
					
				}
				else if(personalDetailsWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					
					pstmt.setString(++n, personalDetailsWrapper.makerId.trim());
				}
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					
				}
				
				
				if(personalDetailsWrapper.refNo !=null && !personalDetailsWrapper.refNo.trim().isEmpty())
				{
					pstmt.setString(++n, personalDetailsWrapper.refNo.trim());
					
					
				}
				
				else if(personalDetailsWrapper.customerName !=null && !personalDetailsWrapper.customerName.trim().isEmpty())
				{
						
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						
						System.out.println("fetchOnBoardQueue customerName2 " +personalDetailsWrapper.customerName);
				}
				
				
				else if(personalDetailsWrapper.searchStartDate !=null && !personalDetailsWrapper.searchStartDate.trim().isEmpty())
				{
					
						pstmt.setDate(++n, Utility.getDate(personalDetailsWrapper.searchStartDate.trim()));
				
				}
				
				else if(personalDetailsWrapper.searchEndDate !=null && !personalDetailsWrapper.searchEndDate.trim().isEmpty())
				{


						pstmt.setDate(++n, Utility.getDate(personalDetailsWrapper.searchEndDate.trim()));
					
				}
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					personalDetailsWrapper= new PersonalDetailsWrapper();
					
					personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + personalDetailsWrapper.refNo);
					
					personalDetailsWrapper.accountType=Utility.trim(resultSet.getString("ProductCode"));
					System.out.println("Fetch Queue account Type " + personalDetailsWrapper.accountType);
					
					personalDetailsWrapper.extCustomerFlag=Utility.trim(resultSet.getString("ExistingCustomerFlag"));
					System.out.println("Existing Customer FLag " + personalDetailsWrapper.extCustomerFlag);
					
					personalDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));
					
					personalDetailsWrapper.extAccountNo=Utility.trim(resultSet.getString("ExistingAccountNo"));
					
					personalDetailsWrapper.extCreditCardNo=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
					
					personalDetailsWrapper.extRelationship=Utility.trim(resultSet.getString("ExistingRelationship"));
					
					personalDetailsWrapper.relationshipNo=Utility.trim(resultSet.getString("RelationshipNo"));
					personalDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
					personalDetailsWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
					personalDetailsWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
					personalDetailsWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
					
					personalDetailsWrapper.title=Utility.trim(resultSet.getString("Title"));
					
					personalDetailsWrapper.branch=Utility.trim(resultSet.getString("Branch"));
					personalDetailsWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
					
					personalDetailsWrapper.emiratesID=Utility.trim(resultSet.getString("EmiratesID"));
					personalDetailsWrapper.makerId=Utility.trim(resultSet.getString("MakerId"));
					personalDetailsWrapper.makerDateTime=Utility.trim(resultSet.getString("MakerDateTime"));
					personalDetailsWrapper.recordStatus=Utility.trim(resultSet.getString("RecordStatus"));
					personalDetailsWrapper.decline=Utility.trim(resultSet.getString("Decline"));
					personalDetailsWrapper.declineReason=Utility.trim(resultSet.getString("DeclineReason"));
					
					personalDetailsWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
					
					personalDetailsWrapper.modifierDateTime=Utility.trim(resultSet.getString("ModifierDateTime"));
					
					queueNoRecords++;
					personalDetailsWrapper.queueNoRecords=queueNoRecords; // to assign value
					
					personalDetailsWrapper.recordFound=true;
					
					System.out.println("fetch OnBoard Queue Successful");
					
					
					
					//-------- Fetch Target TAT  From RMT_WorkflowGroup
					pstmtSub = con.prepareStatement("SELECT  TAT FROM RMT_WorkflowGroup WHERE  WorkflowGroup=?");
					
					pstmtSub.setString(1, personalDetailsWrapper.workflowGroup);
					resultSetSub = pstmtSub.executeQuery();
					if(resultSetSub.next()) 
					{	
						
						targetTAT=resultSetSub.getInt("TAT");
						personalDetailsWrapper.targetTAT=Utility.getTimeformat(targetTAT);
						
						System.out.println(" personalDetailsWrapper.targetTAT " + personalDetailsWrapper.targetTAT);
						
						
					}
					
					resultSetSub.close();
					pstmtSub.close();
					
					
					
					//--tat calculation
					if(personalDetailsWrapper.workflowGroup.equals("SALESUSER"))
					{
						
						personalDetailsWrapper.tat=Utility.getTimeformat(Utility.timeDifference(personalDetailsWrapper.makerDateTime, "M"));
						personalDetailsWrapper.tatColor=Utility.getTATColor(Utility.timeDifference(personalDetailsWrapper.makerDateTime, "M"),targetTAT);
						System.out.println(" personalDetailsWrapper.tat " + personalDetailsWrapper.tat);
						
						
					}
					else
					{
						
						personalDetailsWrapper.tat=Utility.getTimeformat(Utility.timeDifference(personalDetailsWrapper.modifierDateTime, "M"));
						personalDetailsWrapper.tatColor=Utility.getTATColor(Utility.timeDifference(personalDetailsWrapper.modifierDateTime, "M"),targetTAT);
						System.out.println(" personalDetailsWrapper.tat " + personalDetailsWrapper.tat);
						
					}

					
					//-------
	
					vector.addElement(personalDetailsWrapper);
					
					
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
					dataArrayWrapper.recordFound=true;
					
					
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else
				{
					dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[1];
					dataArrayWrapper.personalDetailsWrapper[0] = personalDetailsWrapper;
					personalDetailsWrapper.recordFound=false;
					dataArrayWrapper.recordFound=true;
					
				}
				
				
				personalDetailsWrapper.queueNoRecords=queueNoRecords; // to assign 
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
	

	/*public List<PersonalDetailsWrapper> fetchOnBoardQueue( PersonalDetailsWrapper personalDetailsWrapper,String recordStatus)throws Exception {
		
		
		List<PersonalDetailsWrapper> listPersonalDetailsWrapper=null;
		try {
			
			
			
			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchOnBoardQueue(usersProfileWrapper ,personalDetailsWrapper);
			
			listPersonalDetailsWrapper = Arrays.asList(dataArrayWrapper.personalDetailsWrapper);
			
	
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState() + " ; " + se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			
		}

		return listPersonalDetailsWrapper;
	}*/

	
	
	
	
	/*public AbstractWrapper fetchOnBoardSearch(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		ResultSet resultSetSub = null;
		
		
		System.out.println("fetchOnBoardSearch");
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		String workflowGroup=null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmtSub=null; 
		
		String sql=null;
		String whereClause=null;
		
		int n=0;
		
		try {
				con = getConnection();
				
				 System.out.println("OnBoardSearch makerid " +personalDetailsWrapper.makerId);
				
			
			    workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);
			    
			    
			    System.out.println("OnBoardSearch workflowGroup " +workflowGroup);
			    
			    System.out.println("OnBoardSearch searchCode " +personalDetailsWrapper.searchCode);
				
			    
			    if(personalDetailsWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					whereClause=" WHERE Makerid=? AND WorkflowGroup=? AND RecordStatus=?";
				}	
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					whereClause=" WHERE WorkflowGroup=? AND RecordStatus=? ";
				}
				else if(personalDetailsWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					whereClause=" WHERE Makerid=? ";
				}
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					whereClause=" WHERE 1=1 ";
				}
			
			   
			    if(personalDetailsWrapper.refNo !=null && !personalDetailsWrapper.refNo.trim().isEmpty())
				{
					sql= " AND RefNo =? ";
					
					System.out.println("fetchOnBoardSearch RefNo " + sql);
					
				}
				
				else if(personalDetailsWrapper.customerName !=null && !personalDetailsWrapper.customerName.trim().isEmpty())
				{
					
					
					sql=" AND (UPPER(CustomerName) LIKE ? OR UPPER(FirstName) LIKE ? OR UPPER(MiddleName) LIKE ? OR UPPER(LastName) LIKE ?)";
					
					
					System.out.println("fetchOnBoardSearch customerName " +personalDetailsWrapper.customerName);
					
					
				}
				
				else if(personalDetailsWrapper.searchStartDate !=null && !personalDetailsWrapper.searchStartDate.trim().isEmpty())
				{
					
						sql= " AND MakerDateTime >= ?";
						
				}
				
				else if(personalDetailsWrapper.searchEndDate !=null && !personalDetailsWrapper.searchEndDate.trim().isEmpty())
				{
					
						sql=" AND MakerDateTime <= ?";
					
					
				}
				
				sql=whereClause+sql;
				
				System.out.println("sql + whereClause  " + sql);
								
				 pstmt = con.prepareStatement("SELECT RefNo, ProductCode, ExistingCustomerFlag,ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, ExistingRelationship, RelationshipNo,CustomerName,FirstName, "
						+ "MiddleName,LastName, Title, CategoryType, Branch, JointOwn, NatureOfRelation,ResidenceStatus, "
						+ "Nationality,DOB,Educated,MaritalStatus,Gender,PassportNo, PassportIssueDate, PassportExpDate, PassportIssueplace, "
					    + "PassportIssueCountry,EmiratesID, EmiratesIDExpDate,MakerId ,MakerDateTime , RecordStatus, WorkflowGroup, ModifierDateTime FROM RMT_OnBoard" + sql);
				
				
				
				if(personalDetailsWrapper.searchCode.equals("MAKER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, personalDetailsWrapper.makerId.trim());
					pstmt.setString(++n, workflowGroup);
					pstmt.setString(++n, personalDetailsWrapper.recordStatus.trim());
					
					System.out.println("in makerqueuesearch  makerid " + personalDetailsWrapper.makerId.trim());
					System.out.println("in makerqueuesearch  workflowGroup " + workflowGroup);
					System.out.println("in makerqueuesearch  recordStatus " + personalDetailsWrapper.recordStatus.trim());
					
				}	
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_QUEUE_SEARCH"))
				{
					
					
					pstmt.setString(++n, workflowGroup);
					pstmt.setString(++n, personalDetailsWrapper.recordStatus.trim());
					
					
					
				}
				else if(personalDetailsWrapper.searchCode.equals("MAKER_ENQUIRY_SEARCH"))
				{
					
					pstmt.setString(++n, personalDetailsWrapper.makerId.trim());
				}
				else if(personalDetailsWrapper.searchCode.equals("APPROVER_ENQUIRY_SEARCH"))
				{
					
				}
				
				
				if(personalDetailsWrapper.refNo !=null && !personalDetailsWrapper.refNo.trim().isEmpty())
				{
					pstmt.setString(++n, personalDetailsWrapper.refNo.trim());
					
					
				}
				
				else if(personalDetailsWrapper.customerName !=null && !personalDetailsWrapper.customerName.trim().isEmpty())
				{
						
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						pstmt.setString(++n, '%' + personalDetailsWrapper.customerName.trim().toUpperCase() + '%');
						
						System.out.println("OnBoardSearch customerName2 " +personalDetailsWrapper.customerName);
				}
				
				
				else if(personalDetailsWrapper.searchStartDate !=null && !personalDetailsWrapper.searchStartDate.trim().isEmpty())
				{
					
						pstmt.setDate(++n, Utility.getDate(personalDetailsWrapper.searchStartDate.trim()));
				
				}
				
				else if(personalDetailsWrapper.searchEndDate !=null && !personalDetailsWrapper.searchEndDate.trim().isEmpty())
				{


						pstmt.setDate(++n, Utility.getDate(personalDetailsWrapper.searchEndDate.trim()));
					
				}

				
				
			
				
				
				
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					personalDetailsWrapper= new PersonalDetailsWrapper();
					
					personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + personalDetailsWrapper.refNo);
					
					personalDetailsWrapper.accountType=Utility.trim(resultSet.getString("ProductCode"));
					System.out.println("Fetch Queue account Type " + personalDetailsWrapper.accountType);
					
	
					personalDetailsWrapper.extCustomerFlag=Utility.trim(resultSet.getString("ExistingCustomerFlag"));
					System.out.println("Existing Customer FLag " + personalDetailsWrapper.extCustomerFlag);
					
					personalDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));
					
					personalDetailsWrapper.extAccountNo=Utility.trim(resultSet.getString("ExistingAccountNo"));
					
					personalDetailsWrapper.extCreditCardNo=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
					
					personalDetailsWrapper.extRelationship=Utility.trim(resultSet.getString("ExistingRelationship"));
					
					personalDetailsWrapper.relationshipNo=Utility.trim(resultSet.getString("RelationshipNo"));
					personalDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
					personalDetailsWrapper.firstName=Utility.trim(resultSet.getString("FirstName"));
					personalDetailsWrapper.middleName=Utility.trim(resultSet.getString("MiddleName"));
					personalDetailsWrapper.lastName=Utility.trim(resultSet.getString("LastName"));
					personalDetailsWrapper.title=Utility.trim(resultSet.getString("Title"));
					
					
					personalDetailsWrapper.branch=Utility.trim(resultSet.getString("Branch"));
					personalDetailsWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
					personalDetailsWrapper.gender=Utility.trim(resultSet.getString("Gender"));
					
					personalDetailsWrapper.passportNo=Utility.trim(resultSet.getString("PassportNo"));

					
					personalDetailsWrapper.emiratesID=Utility.trim(resultSet.getString("EmiratesID"));
					
					personalDetailsWrapper.makerId=Utility.trim(resultSet.getString("MakerId"));
					personalDetailsWrapper.makerDateTime=Utility.trim(resultSet.getString("MakerDateTime"));
					personalDetailsWrapper.recordStatus=Utility.trim(resultSet.getString("RecordStatus")); 
					personalDetailsWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
					
					personalDetailsWrapper.modifierDateTime=Utility.trim(resultSet.getString("ModifierDateTime"));
					
					
					
					//--tat calculation
					if(personalDetailsWrapper.workflowGroup.equals("SALESUSER"))
					{
						
						personalDetailsWrapper.tat=getTimeformat(Utility.timeDifference(personalDetailsWrapper.makerDateTime, "M"));
						System.out.println(" personalDetailsWrapper.tat " + personalDetailsWrapper.tat);
						
						
					}
					else
					{
						
						personalDetailsWrapper.tat=getTimeformat(Utility.timeDifference(personalDetailsWrapper.modifierDateTime, "M"));
						System.out.println(" personalDetailsWrapper.tat " + personalDetailsWrapper.tat);
						
					}
					
					
					//-------- Fetch Target TAT  From RMT_WorkflowGroup
					pstmtSub = con.prepareStatement("SELECT  TAT FROM RMT_WorkflowGroup WHERE  WorkflowGroup=?");
					
					pstmtSub.setString(1, personalDetailsWrapper.workflowGroup);
					resultSetSub = pstmtSub.executeQuery();
					if(resultSetSub.next()) 
					{	
						
						//personalDetailsWrapper.seqNo=resultSetSub.getString("SeqNo");
						
						personalDetailsWrapper.targetTAT=getTimeformat(resultSetSub.getInt("TAT"));
						System.out.println(" personalDetailsWrapper.targetTAT " + personalDetailsWrapper.targetTAT);
						
					}
					
					resultSetSub.close();
					pstmtSub.close();
					
					//-------
					
					personalDetailsWrapper.recordFound=true;
					
					System.out.println("search OnBoard Queue Successful");
	
					vector.addElement(personalDetailsWrapper);
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else
				{
					dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[1];
					dataArrayWrapper.personalDetailsWrapper[0] = personalDetailsWrapper;
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
	}*/
	/*public AbstractWrapper fetchEnquiry(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

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
		
		//System.out.println(" in fetch enquiry customerName" + personalDetailsWrapper.customerName);
		
		
		//PersonalDetailsWrapper personalDetailsWrapper=null;
	
	
		try {
			con = getConnection("ONBOARD");
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, ExistingCustomerFlag, ExistingCIFNumber, ExistingAccountNo, ExistingCreditCardNo, "
					+ "ExistingRelationship, RelationshipNo, CustomerName, Title, CategoryType, Branch, JointOwn, NatureOfRelation,ResidenceStatus, "
					+ "Nationality , DOB, Educated, MaritalStatus, Gender, PreferredLanguage, FamilyInUAE, FamilySizeUAE, CarOwnership, CarYear, "
					+ "Media, FavouriteCity FROM RMT_OnBoard WHERE ExistingCIFNumber=? OR  ExistingAccountNo=? OR CustomerName=?");
			
			
			//System.out.println("RefNo is" + personalDetailsWrapper.refNo);
			//
			
			
			pstmt.setString(1,personalDetailsWrapper.cifNumber.trim());
			System.out.println(" in fetch enquiry cifNumber" + personalDetailsWrapper.cifNumber);
		
			pstmt.setString(2,personalDetailsWrapper.extAccountNo.trim());
			System.out.println(" in fetch enquiry extAccountNo" + personalDetailsWrapper.extAccountNo);
	
			pstmt.setString(3,personalDetailsWrapper.customerName.trim());
			System.out.println(" in fetch enquiry customerName" + personalDetailsWrapper.customerName);
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) 
			{
				
			
				personalDetailsWrapper= new PersonalDetailsWrapper();
				
				personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + personalDetailsWrapper.refNo);
				

				personalDetailsWrapper.extCustomerFlag=Utility.trim(resultSet.getString("ExistingCustomerFlag"));
				System.out.println("OccupationType " + personalDetailsWrapper.extCustomerFlag);
				
				personalDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("ExistingCIFNumber"));
				
				personalDetailsWrapper.extAccountNo=Utility.trim(resultSet.getString("ExistingAccountNo"));
				
				personalDetailsWrapper.extCreditCardNo=Utility.trim(resultSet.getString("ExistingCreditCardNo"));
				
				personalDetailsWrapper.extRelationship=Utility.trim(resultSet.getString("ExistingRelationship"));
				
				personalDetailsWrapper.relationshipNo=Utility.trim(resultSet.getString("RelationshipNo"));
				
				personalDetailsWrapper.customerName=Utility.trim(resultSet.getString("CustomerName"));
				
				personalDetailsWrapper.title=Utility.trim(resultSet.getString("Title"));
				
				personalDetailsWrapper.categoryType=Utility.trim(resultSet.getString("CategoryType"));
				
				personalDetailsWrapper.branch=Utility.trim(resultSet.getString("Branch"));
				
				personalDetailsWrapper.jointOwn=Utility.trim(resultSet.getString("JointOwn"));
				
				personalDetailsWrapper.natureOfRelation=Utility.trim(resultSet.getString("NatureOfRelation"));
				
				personalDetailsWrapper.residenceStatus=Utility.trim(resultSet.getString("ResidenceStatus"));
				
				personalDetailsWrapper.nationality=Utility.trim(resultSet.getString("Nationality"));
				
				personalDetailsWrapper.dob=Utility.setDate(resultSet.getString("DOB"));
				
				personalDetailsWrapper.educated=Utility.trim(resultSet.getString("Educated"));
				
				personalDetailsWrapper.maritalStatus=Utility.trim(resultSet.getString("MaritalStatus"));
				
				personalDetailsWrapper.gender=Utility.trim(resultSet.getString("Gender"));
				
				personalDetailsWrapper.preferredLanguage=Utility.trim(resultSet.getString("PreferredLanguage"));
				
				personalDetailsWrapper.familyInUAE=Utility.trim(resultSet.getString("FamilyInUAE"));
				
				personalDetailsWrapper.familySizeUAE=Utility.trim(resultSet.getString("FamilySizeUAE"));
				
				personalDetailsWrapper.carOwnership=Utility.trim(resultSet.getString("CarOwnership"));
				
				personalDetailsWrapper.carYear=Utility.setDate(resultSet.getString("CarYear"));
				
				personalDetailsWrapper.media=Utility.trim(resultSet.getString("Media"));
				
				personalDetailsWrapper.favouriteCity=Utility.trim(resultSet.getString("FavouriteCity"));
				
			
			
				
				personalDetailsWrapper.recordFound=true;
				
				

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
			System.out.println("Fetch Enquiry Details Successful " );


			
			
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
	public AbstractWrapper approveRecord(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
		//RefNo,RecordStatus we received from Personal Details Wrapper
		Connection con = null;
		ResultSet resultSet = null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		PreparedStatement pstmt =null;
		
		System.out.println("approveRecord");
		String workflowGroup=null;
		String recordStatus=null;
		String decline=null;
		
	
		try {
				
				con = getConnection();
				
				workflowGroup=getWorkflowGroup(usersProfileWrapper.userid);

				if(workflowGroup.equals("CREDITUSER"))
				{
					recordStatus="APPROVED";
					
					 
					pstmt = con.prepareStatement("SELECT Decline FROM RMT_OnBoard");
					
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) 
					{
						
						decline=Utility.trim(resultSet.getString("Decline"));
						if(decline !=null && decline.equals("Y"))
						{
							recordStatus="DECLIEND";
						}
						
					}
					if(resultSet !=null) resultSet.close();
					pstmt.close();
					

							
				}
				else
				{
					recordStatus="CREATE";
				}
			
				
				pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET RecordStatus=?, Approverid=?, ApproverDateTime=? where RefNo=?");
				pstmt.setString(1,Utility.trim(recordStatus));
				pstmt.setString(2,Utility.trim(usersProfileWrapper.userid)); // approverId from user profile
				pstmt.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
				pstmt.setString(4,Utility.trim(personalDetailsWrapper.refNo));
				
				pstmt.executeUpdate();
	
				pstmt.close();
				
				
				//-------------
				personalDetailsWrapper.workflowGroup=workflowGroup;
	
				
				if(recordStatus.equals("CREATE"))
				{
					
					personalDetailsWrapper.remarks= "APPLICATION REVIEWED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime();
					
					updateRemarks(Utility.trim(personalDetailsWrapper.refNo),personalDetailsWrapper.remarks);
					
					//-----UPDATE WORKFLOW TRACK-----------
					updateWorkflowTrack(usersProfileWrapper, personalDetailsWrapper);
				}
				else if(recordStatus.equals("APPROVED")){
					
					personalDetailsWrapper.remarks="APPLICATION APPROVED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime();
					updateRemarks(Utility.trim(personalDetailsWrapper.refNo),personalDetailsWrapper.remarks );
					
					//-----UPDATE WORKFLOW TRACK-----------
					updateWorkflowTrack(usersProfileWrapper, personalDetailsWrapper);
					
				}
				else if(recordStatus.equals("DECLINED")){
					
					personalDetailsWrapper.remarks="APPLICATION DECLINED BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime();
					
					updateRemarks(Utility.trim(personalDetailsWrapper.refNo),personalDetailsWrapper.remarks );
					
					//-----UPDATE WORKFLOW TRACK-----------
					updateWorkflowTrack(usersProfileWrapper, personalDetailsWrapper);
					
				}

				//---------update workflowStatus
				WorkflowStatusHelper workflowStatusHelper=new WorkflowStatusHelper();
				workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),workflowGroup,"C");
				//--
				
				String nextWorkflowGroup=workflowStatusHelper.getNextWorkflowGroup(workflowGroup);
				
				
				
				if(nextWorkflowGroup!=null && !workflowGroup.equals(nextWorkflowGroup))
				{
					
					//---------update workflowStatus
				
					if(nextWorkflowGroup.equals("END"))
					{
						workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo), Utility.trim(personalDetailsWrapper.accountType),nextWorkflowGroup,"C");
					}	
					else
					{
						workflowStatusHelper.updateWorkflowStatus(Utility.trim(personalDetailsWrapper.refNo),Utility.trim(personalDetailsWrapper.accountType),nextWorkflowGroup,"I");
					}
					
					//--
					pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET WorkflowGroup=? WHERE RefNo=?");
					pstmt.setString(1,nextWorkflowGroup);
					pstmt.setString(2,Utility.trim(personalDetailsWrapper.refNo));
					pstmt.executeUpdate();
					pstmt.close();
					
				}
			
				
				
				personalDetailsWrapper.recordFound=true;
				
				dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
				dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Record Approved");
				
				
				//--Create Account
				AccountsHelper accountsHelper=new AccountsHelper();
				AccountsWrapper accountsWrapper=new AccountsWrapper();
				//PersonalDetailsWrapper subPersonalDetailsWrapper=new PersonalDetailsWrapper();
				//subPersonalDetailsWrapper.refNo=personalDetailsWrapper.refNo;
				
				//---------populate personal details
				PersonalDetailsWrapper subPersonalDetailsWrapper=fetchPersonalDetails(personalDetailsWrapper,personalDetailsWrapper.refNo);
				accountsWrapper.customerName=subPersonalDetailsWrapper.customerName;
				System.out.println("subPersonalDetailsWrapper cifNumber "+accountsWrapper.cifNumber);
				accountsWrapper.cifNumber=subPersonalDetailsWrapper.cifNumber;
				
				accountsWrapper.accountType="LOANS";
				accountsWrapper.accountDesc="Loan Account";
				
			
				
				//---------
				
				//----------populate product details
				AccountDetailsHelper accountDetailsHelper=new AccountDetailsHelper();
				AccountDetailsWrapper accountDetailsWrapper=new AccountDetailsWrapper();
				accountDetailsWrapper.refNo=personalDetailsWrapper.refNo;
				

				accountDetailsWrapper=accountDetailsHelper.fetchAccountDetails(accountDetailsWrapper,accountDetailsWrapper.refNo);
				accountsWrapper.accountStatus="ACTIVE";
				accountsWrapper.accountCurrency="AED";   	//accountDetailsWrapper.currency;
				accountsWrapper.currentBalance="10000";
				accountsWrapper.availableBalance="5000";
				accountsWrapper.customerSegment="GOLD";
				accountsWrapper.loanType="CARLOAN";
				accountsWrapper.loanDesc="CAR LOAN Account";
				accountsWrapper.loanStatus="ACTIVE";
				accountsWrapper.principalAmount="50000";
				accountsWrapper.principalCurrency="AED";   //	accountDetailsWrapper.currency;
				accountsWrapper.installmentAmount="4000";
				accountsWrapper.installmentCurrency="AED";   //accountDetailsWrapper.currency;
				accountsWrapper.loanCurrentBalance="20000";
				accountsWrapper.loanStartDate="2015-07-28";
				accountsWrapper.nextInstlDate="2015-08-28";
				accountsWrapper.loanOSBalance="30000";
				accountsWrapper.paidAmount="15000";
				accountsWrapper.noPendingInstl=2;
				accountsWrapper.lastPaidAmount="4000";
				accountsWrapper.lastPaymentDate="2015-07-28";
				accountsWrapper.totalInstallments=10;
				accountsWrapper.installmentsPaid=5;
				accountsWrapper.depositAmount="30000";
				accountsWrapper.depositCurrency="AED";                        //accountDetailsWrapper.currency;
				accountsWrapper.depositTenor=5;
				accountsWrapper.depositDate="2015-07-28";
				
				
				
				accountsWrapper.depositMaturityDate="2015-07-28";
				accountsWrapper.depositRenewalType="NORMAL";
				
				accountsWrapper.depositCreditAccount="30000";
				accountsWrapper.depositMaturityAmount="500000";
				accountsWrapper.statementRequest="YES";
			
				
				//------------
				
				//-----
				
		/*		CIFNumber,AccountNumber,CustomerName,AccountStatus, "
						+ "AccountCurrency,CurrentBalance,AvailableBalance,AccountType,AccountDesc,CustomerSegment,LoanType, "
						+ "LoanDesc,LoanStatus ,PrincipalAmount,PrincipalCurrency,InstallmentAmount,InstallmentCurrency, "
						+ "LoanCurrentBalance,LoanStartDate,NextInstlDate,LoanOSBalance,PaidAmount,NoPendingInstl,LastPaidAmount, "
						+ "LastPaymentDate,TotalInstallments,InstallmentsPaid,DepositAmount,DepositCurrency,DepositTenor, "
						+ "DepositDate,DepositMaturityDate,DepositRenewalType,DepositCreditAccount,DepositMaturityAmount, "
						+ "StatementRequest
				*/
				accountsHelper.createAccount(accountsWrapper);
			
			
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
	
	public AbstractWrapper fetchExistingCustomer(PersonalDetailsWrapper personalDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();

		
		System.out.println("Fetch Existig Customer "+ personalDetailsWrapper.cifNumber);
		String sql=null;
	
		try {
				con = getConnection();
				
				sql="SELECT RefNo FROM RMT_OnBoard ";
				
				if(personalDetailsWrapper.cifNumber!=null && !personalDetailsWrapper.cifNumber.trim().isEmpty())
				{			
					System.out.println("if search cif number " + sql);
					
					sql=sql + " WHERE ExistingCIFNumber=?";
								
				}
				else if(personalDetailsWrapper.extAccountNo!=null && !personalDetailsWrapper.extAccountNo.trim().isEmpty())
				{			
			
					sql=sql + " WHERE ExistingAccountNo=?";
								
				}
				else if(personalDetailsWrapper.extCreditCardNo!=null && !personalDetailsWrapper.extCreditCardNo.trim().isEmpty())
				{			
			
					sql=sql + " WHERE ExistingCreditCardNo=?";
								
				}
			
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				
				if(personalDetailsWrapper.cifNumber!=null && !personalDetailsWrapper.cifNumber.trim().isEmpty())
				{
					pstmt.setString(1, personalDetailsWrapper.cifNumber.trim());
					
				}
				else if(personalDetailsWrapper.extAccountNo!=null && !personalDetailsWrapper.extAccountNo.trim().isEmpty())
				{
					pstmt.setString(1, personalDetailsWrapper.extAccountNo.trim());
					
				}
				else if(personalDetailsWrapper.extCreditCardNo!=null && !personalDetailsWrapper.extCreditCardNo.trim().isEmpty())
				{			
			
					pstmt.setString(1, personalDetailsWrapper.extCreditCardNo.trim());
								
				}
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					
					personalDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + personalDetailsWrapper.refNo);
					
					// fetch Personal details
					
				    dataArrayWrapper=(DataArrayWrapper)fetchPersonalDetails(personalDetailsWrapper);
				    
				    
				    // -------------
					
					personalDetailsWrapper.recordFound=true;

					System.out.println("fetch RefNo completed");
					
				}
				else
				{
					dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[1];
					dataArrayWrapper.personalDetailsWrapper[0]= personalDetailsWrapper;
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
	
	
	public String getWorkflowGroup(String userid)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		PreparedStatement pstmt=null;
		
		String workflowGroup=null;
		
		try {
			
			
				con = getConnection();
				
				//----get WorkflowGroup from Users using userid
				
				pstmt = con.prepareStatement("SELECT WorkflowGroup FROM Users WHERE userid=?");
				pstmt.setString(1,Utility.trim(userid));
				
				resultSet = pstmt.executeQuery();
				if(resultSet.next()) 
				{
					workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
				}
				resultSet.close();
				pstmt.close();

				
				System.out.println("fetch workflowGroup Successful " );
			
			


			
			
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

		return workflowGroup;
	}
	
	public void updateRemarks(String refNo,String remarks)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		PreparedStatement pstmt=null;
		
		String dbRemarks=null;
		
		try {
			
			
				con = getConnection();
				
				
				//----------
				pstmt = con.prepareStatement("SELECT Remarks FROM RMT_OnBoard WHERE RefNo=?");
				pstmt.setString(1,Utility.trim(refNo));
				
				resultSet = pstmt.executeQuery();
				if(resultSet.next()) 
				{
					dbRemarks=Utility.trim(resultSet.getString("Remarks"));
				}
				resultSet.close();
				pstmt.close();
				
				//------
				
				if(dbRemarks==null)
				{
					dbRemarks="";
				}
				
				pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET Remarks=? WHERE RefNo=?");
				pstmt.setString(1,Utility.trim(remarks)+"\n"+dbRemarks);
				pstmt.setString(2,Utility.trim(refNo));
			
				pstmt.executeUpdate();
				pstmt.close();

				
				System.out.println("Remarks updated Successful " );
			
			
			
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

		
	}
	
	
	public AbstractWrapper updateWorkflowTrack(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
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
		PreparedStatement pstmt=null;
		
		String actionStatus=null;
		
		try {
				con = getConnection();
				
				
				if(personalDetailsWrapper.recordStatus.equals("REJECTED"))
				{
					
					actionStatus="REJECTED";
				}
				else
				{
					
				
					//--------- RMT_WorkflowGroup for decline 
					
					sql="SELECT ActionStatus from RMT_WorkflowGroup WHERE WorkflowGroup=?";
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, personalDetailsWrapper.workflowGroup);
				
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) 
					{
						actionStatus=resultSet.getString("ActionStatus");
						System.out.println("ActionStatus " + actionStatus);
						
					}
					
					resultSet.close();
					pstmt.close();
					//-----------------------
					
				}
				
				
				personalDetailsWrapper.remarks= "APPLICATION "+ actionStatus + " BY " + Utility.trim(usersProfileWrapper.userid)+ " @ " + Utility.getCurrentTime();
				
				sql="INSERT INTO RMT_WorkflowTrack(RefNo,ProductCode,SeqNo,WorkflowGroup,Userid,Decline,ActionStatus,ActionDateTime,Remarks,TAT)"
						+ " VALUES(?,?,?,?,?,?,?,?,?,?)";
				
				pstmt = con.prepareStatement(sql);
				
				String [] returnValues=generateSeqNo(personalDetailsWrapper.refNo);
				
				pstmt.setString(1,Utility.trim(personalDetailsWrapper.refNo)); 
				pstmt.setString(2,Utility.trim(personalDetailsWrapper.accountType));  // product code
				pstmt.setString(3,returnValues[0]); //seqNo
				pstmt.setString(4,Utility.trim(personalDetailsWrapper.workflowGroup));
				pstmt.setString(5,Utility.trim(usersProfileWrapper.userid));
				pstmt.setString(6,Utility.trim(personalDetailsWrapper.decline));
				pstmt.setString(7,Utility.trim(actionStatus));
				pstmt.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
				pstmt.setString(9,Utility.trim(personalDetailsWrapper.remarks));
				pstmt.setString(10,returnValues[1]); //TAT
				pstmt.executeUpdate();
				pstmt.close();

			
				
				System.out.println("Successfully inserted into RMT_WorkflowTrack");
				
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
	
	public String[] generateSeqNo(String refNo)throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;

		//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("ddMMMyyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		String [] returnValues = new String[2];
		
		int seqNo=0;
		String actionDateTime=null;
		int timeDifference=0;
		
		
		try {
				con = getConnection();
				
				
				sql="SELECT MAX(SeqNo) as SeqNo, Max(ActionDateTime) as ActionDateTime from RMT_WorkflowTrack WHERE RefNo=?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,refNo);
			
				resultSet = pstmt.executeQuery();
				
				if (resultSet.next()) 
				{
					
					seqNo=resultSet.getInt("SeqNo");
					actionDateTime=resultSet.getString("ActionDateTime");
					
					
				}
				else{
					seqNo=1;
					timeDifference=0;
				}
				
				resultSet.close();
				pstmt.close();
				
				if(seqNo==0)
				{
					seqNo=1;
					timeDifference=0;
					
				}
				else
				{
					
					seqNo=seqNo+1;
					timeDifference=Utility.timeDifference(actionDateTime, "M");

				}
				System.out.println("seqNo " + seqNo);
				System.out.println("timeDifference " + timeDifference);

				returnValues[0]=String.valueOf(seqNo);
				returnValues[1]=String.valueOf(timeDifference);

				
				
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

		return returnValues;
	}
	
	/*public String getTimeformat(int minutes) {
		
		
		 //String startTime = "00:00";
		 //int minutes = 190;
		 //int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
		 //int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
		 //String newtimeFormat = h+":"+m;
		 
		 String newtimeFormat=minutes/24/60 + ":" + minutes/60%24 + ':' + minutes%60;
		
		return newtimeFormat;
	}
	public String getTATColor(int tat,int targetTAT) {
		
		return (tat>targetTAT ? "RED" : "GREEN");
	}*/
	
	

	public static List<PersonalDetailsWrapper> getCustomers(List<PersonalDetailsWrapper> list, int from, int to)
	{
	
	  return list.subList(from, to);
	}
	
	public static PersonalDetailsWrapper findById(List<PersonalDetailsWrapper> list, int id)
	{
	
	  for (PersonalDetailsWrapper customer : list)
	  {
	    if (customer.getId() == id) return customer;
	  }
	
	  return null;
	}
	
	public static List<PersonalDetailsWrapper> findNotById(List<PersonalDetailsWrapper> list, int id, int from, int to)
	{
	  List<PersonalDetailsWrapper> sResult = new ArrayList<PersonalDetailsWrapper>();
	
	  for (PersonalDetailsWrapper customer : list)
	  {
	    if (customer.getId() != id) sResult.add(customer);
	  }
	
	  return sResult.subList(from, to);
	}
	
	public static List<PersonalDetailsWrapper> findGreaterAsId(List<PersonalDetailsWrapper> list, int id, int from, int to)
	{
	  List<PersonalDetailsWrapper> sResult = new ArrayList<PersonalDetailsWrapper>();
	
	  for (PersonalDetailsWrapper customer : list)
	  {
	    if (customer.getId() > id) sResult.add(customer);
	  }
	
	  return sResult.subList(from, to);
	}
	
	public static List<PersonalDetailsWrapper> findLesserAsId(List<PersonalDetailsWrapper> list, int id, int from, int to)
	{
	  List<PersonalDetailsWrapper> sResult = new ArrayList<PersonalDetailsWrapper>();
	
	  for (PersonalDetailsWrapper customer : list)
	  {
	    if (customer.getId() < id) sResult.add(customer);
	  }
	
	  return sResult.subList(from, to);
	}
	
	public static Integer getCustomersCount(List<PersonalDetailsWrapper> list)
	{
	
	  return list.size();
	}
		
	
			
}

	


