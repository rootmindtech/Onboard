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


public class RejectHelper extends Helper{
	
	
	public AbstractWrapper insertRejectReason(UsersWrapper usersProfileWrapper,RejectWrapper[] rejectWrapperArray)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
		
		String sql=null;
		String workflowGroup=null;
		String refNo=null;
		String remarks=null;
		System.out.println("insertRejectReason");
	
		try {
			
				con = getConnection();
				
				if(rejectWrapperArray !=null)
				{
					for(int i=0;i<=rejectWrapperArray.length-1;i++)
					{
						sql="INSERT INTO RMT_RejectReason(RefNo, RejectCode, ModifierID, ModifierDateTime) VALUES(?,?,?,?)";
						
						PreparedStatement pstmt = con.prepareStatement(sql);
						
							
							pstmt.setString(1,Utility.trim(rejectWrapperArray[i].refNo));
							pstmt.setString(2,Utility.trim(rejectWrapperArray[i].rejectCode));
							pstmt.setString(3,Utility.trim(usersProfileWrapper.userid)); //makerid from userprofile
							pstmt.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));//makerDateTime
							
						
							rejectWrapperArray[i].recordFound=true;
							pstmt.executeUpdate();
							pstmt.close();
					}	
					
					PersonalDetailsWrapper personalDetailsWrapper=new PersonalDetailsWrapper();
					personalDetailsWrapper.refNo=rejectWrapperArray[0].refNo;
					personalDetailsWrapper.recordStatus=rejectWrapperArray[0].recordStatus;
					personalDetailsWrapper.remarks=rejectWrapperArray[0].remarks;
					personalDetailsWrapper.accountType=rejectWrapperArray[0].accountType;
					PersonalDetailsHelper personalDetailsHelper=new PersonalDetailsHelper();
					personalDetailsHelper.updateApplicationStatus(usersProfileWrapper, personalDetailsWrapper);
					//refNo=rejectWrapperArray[0].refNo;
					//remarks=rejectWrapperArray[0].remarks;
					System.out.println("inserted Reject Reason ");
				}	
					
						
				
				
			
				
				dataArrayWrapper.rejectWrapper=rejectWrapperArray;
				dataArrayWrapper.recordFound=true;
				
					
								
			
			
			
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
	
	public AbstractWrapper fetchRejectReason(RejectWrapper rejectWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		ResultSet resultSetSub = null;
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		PreparedStatement pstmtSub=null;
	
		try {
			
			PopoverHelper popoverHelper = new PopoverHelper();
			
			
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, RejectCode, ModifierID, ModifierDateTime "
					+ " FROM RMT_RejectReason WHERE RefNo=? ORDER BY ModifierDateTime DESC");
			
			
			System.out.println("fetchPersonalDetails RefNo is" + rejectWrapper.refNo);
			
			pstmt.setString(1,rejectWrapper.refNo.trim());
		
			boolean fetchRemarks=true;
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				rejectWrapper= new RejectWrapper();
				
				rejectWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + rejectWrapper.refNo);
				
				rejectWrapper.rejectCode=Utility.trim(resultSet.getString("RejectCode"));
				
				rejectWrapper.modifierID=Utility.trim(resultSet.getString("ModifierID"));
				rejectWrapper.modifierDateTime=Utility.setDate(resultSet.getString("ModifierDateTime"));
					
				rejectWrapper.rejectValue=popoverHelper.fetchPopoverDesc(rejectWrapper.rejectCode,"RejectReason");
				
					//----------------------Fetch Remarks------------------------------------ 
					if(fetchRemarks==true){
						pstmtSub = con.prepareStatement("SELECT Remarks FROM RMT_OnBoard WHERE RefNo=?");
						
						System.out.println("pstmtSub RefNo" + rejectWrapper.refNo);
						pstmtSub.setString(1,Utility.trim(rejectWrapper.refNo));
						
						resultSetSub = pstmtSub.executeQuery();
						
						if(resultSetSub.next()) 
						{
							rejectWrapper.remarks=Utility.trim(resultSetSub.getString("Remarks"));
						}
						if (resultSetSub!=null)  resultSetSub.close();
						pstmtSub.close();
					}
					//-------------

				rejectWrapper.recordFound=true;
			
				vector.addElement(rejectWrapper);
				
				fetchRemarks=false;

			}
		
			if (vector.size()>0)
			{
				dataArrayWrapper.rejectWrapper = new RejectWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.rejectWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());
				
				System.out.println("fetch RejectReason  Successful " );

			}
			else
				
			{
				dataArrayWrapper.rejectWrapper = new RejectWrapper[1];
				dataArrayWrapper.rejectWrapper[0]= rejectWrapper;
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
	
}
