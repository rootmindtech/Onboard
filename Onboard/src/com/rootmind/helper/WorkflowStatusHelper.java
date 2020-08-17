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

public class WorkflowStatusHelper extends Helper{
	
	public AbstractWrapper fetchWorkflowStatus(WorkflowStatusWrapper workflowStatusWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		ResultSet resultSetSub = null;
		
		//workflowStatusWrapper workflowStatusWrapper=null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();

		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();
		
		PreparedStatement pstmtSub=null;

		int tat=0;
		try {
			
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, WorkflowGroup, Status FROM RMT_WorkflowStatus WHERE RefNo=?");
			
			
			System.out.println("WorkflowStatus RefNo is" + workflowStatusWrapper.refNo);
			
			pstmt.setString(1,workflowStatusWrapper.refNo.trim());
			
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				workflowStatusWrapper= new WorkflowStatusWrapper();
				
				workflowStatusWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + workflowStatusWrapper.refNo);
				
				workflowStatusWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
				workflowStatusWrapper.status=Utility.trim(resultSet.getString("Status"));
				
				tat=0;
			    pstmtSub = con.prepareStatement("SELECT RefNo, WorkflowGroup, SUM(TAT) as TAT FROM RMT_WorkflowTrack WHERE RefNo=? AND WorkflowGroup=? GROUP BY RefNo,WorkflowGroup");
			    
			    pstmtSub.setString(1,workflowStatusWrapper.refNo.trim());
			    pstmtSub.setString(2,workflowStatusWrapper.workflowGroup.trim());
			    resultSetSub = pstmtSub.executeQuery();
				if (resultSetSub.next())
				{
					tat=Integer.parseInt((resultSetSub.getString("TAT")==null?"0":resultSetSub.getString("TAT")));
					
					System.out.println(" fetchWorkflowStatus tat " + tat);
				}
				
				
				workflowStatusWrapper.tat=Utility.getTimeformat(tat);
				System.out.println(" workflowStatusWrapper tat " + workflowStatusWrapper.tat);
				if(resultSetSub !=null) resultSetSub.close();
				pstmtSub.close();
				
				
				workflowStatusWrapper.recordFound=true;
				vector.addElement(workflowStatusWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.workflowStatusWrapper = new WorkflowStatusWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.workflowStatusWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else	
			{
				dataArrayWrapper.workflowStatusWrapper = new WorkflowStatusWrapper[1];
				dataArrayWrapper.workflowStatusWrapper[0]= workflowStatusWrapper;
				dataArrayWrapper.recordFound=true;

				
			}
			
			if(resultSet !=null) resultSet.close();
			pstmt.close();
			System.out.println("fetch WorkflowStatus Successful " );
			
			


			
			
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


	public void updateWorkflowStatus(String refNo,String productCode,String workflowGroup, String status)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//workflowStatusWrapper workflowStatusWrapper=null;
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		//DataArrayWrapper dataArrayWrapper = null;
		

		System.out.println("update workflow Details");
		
		PreparedStatement pstmt=null;
		PreparedStatement pstmtSub=null;
		
		String sql=null;
		
		

		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo, WorkflowGroup, Status FROM RMT_WorkflowStatus WHERE RefNo=? and WorkflowGroup=?");
				
			
				System.out.println("Update workflow RefNo is" + refNo.trim());
				
				pstmt.setString(1,refNo.trim());
				pstmt.setString(2,workflowGroup.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					
					resultSet.close();
					pstmt.close();
					
					pstmt = con.prepareStatement("SELECT  SeqNo, WorkflowGroup FROM RMT_WorkflowGroup ORDER BY SeqNo ASC");
					
					resultSet = pstmt.executeQuery();
					
					while(resultSet.next())
					{
					
						
						sql=" INSERT INTO RMT_WorkflowStatus(RefNo,ProductCode,WorkflowGroup,Status) Values (?,?,?,?)";
						
						
						pstmtSub = con.prepareStatement(sql);
					
						pstmtSub.setString(1,Utility.trim(refNo));
						pstmtSub.setString(2,Utility.trim(productCode));
						pstmtSub.setString(3,Utility.trim(resultSet.getString("WorkflowGroup")));
						
						if(Utility.trim(resultSet.getString("SeqNo")).equals("1"))
						{
							pstmtSub.setString(4,Utility.trim("I"));
						}
						else
						{
							pstmtSub.setString(4,Utility.trim("Q"));
						}
	
						pstmtSub.executeUpdate();
						pstmtSub.close();
					}
					
					resultSet.close();
					pstmt.close();

				}
				else
				{	
					
					resultSet.close();
					pstmt.close();

					pstmt = con.prepareStatement("UPDATE RMT_WorkflowStatus SET Status=? where RefNo=? and WorkflowGroup=?");


					pstmt.setString(1,Utility.trim(status));
					pstmt.setString(2,Utility.trim(refNo));
					pstmt.setString(3,Utility.trim(workflowGroup));
					
					System.out.println("Updated RefNo " + refNo);


					pstmt.executeUpdate();
					pstmt.close();
					

					
				}
				
				if(resultSet !=null) resultSet.close();
				pstmt.close();
				
				//WorkflowStatusWrapper workflowStatusWrapper = new WorkflowStatusWrapper();
				//workflowStatusWrapper.refNo=refNo.trim();
				
				//dataArrayWrapper=(DataArrayWrapper)fetchWorkflowStatus(workflowStatusWrapper);
				
				
				System.out.println("WorkflowStatus updated " );
				
					
			
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

		//return dataArrayWrapper.workflowStatusWrapper;
	}

	public String getNextWorkflowGroup(String currentWorkflowGroup)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		Vector<Object> vector = new Vector<Object>();
		PreparedStatement pstmt=null;
		
		String workflowGroup=null;
		String [] workflowGroupArray=null; 
		String nextWorkflowGroup=null;
		try {
			
			
				con = getConnection();
				pstmt = con.prepareStatement("SELECT WorkflowGroup FROM RMT_WorkflowGroup ORDER BY SeqNo ASC");
				
			
				resultSet = pstmt.executeQuery();
				while(resultSet.next()) 
				{
					
					
					workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
					
					vector.addElement(workflowGroup);
	
				}
				resultSet.close();
				pstmt.close();

				
				workflowGroupArray= new String[vector.size()] ;
				vector.copyInto(workflowGroupArray);
				
				 for (int i = 0; i <= workflowGroupArray.length-1; i++)
				 {
					 	if(currentWorkflowGroup != null && currentWorkflowGroup.equals(workflowGroupArray[i]))
					 	{
					 		if(i<workflowGroupArray.length-1)
					 		{
					 			nextWorkflowGroup = workflowGroupArray[i+1];
					 			
					 		}
					 		else
					 		{
					 			
					 			nextWorkflowGroup=currentWorkflowGroup;
					 		}
					 	}
			            
		            
				  }
				
				System.out.println("fetch WorkflowStatus Successful " );
			
			


			
			
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

		return nextWorkflowGroup;
	}

}
