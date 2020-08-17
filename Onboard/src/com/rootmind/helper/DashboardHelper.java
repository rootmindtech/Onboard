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
import com.rootmind.wrapper.DashboardWebWrapper;

public class DashboardHelper extends Helper{
	
	public AbstractWrapper fetchDashboardWeb(UsersWrapper usersProfileWrapper,DashboardWebWrapper dashboardWebWrapper)throws Exception {

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
		String workflowGroup=null;
		
		Vector<Object> vector = new Vector<Object>();
		//DashboardWebWrapper dashboardWebWrapper=null;
		GraphWrapper graphWrapper=null;
		int n=0;
		System.out.println("fetchDashboardWeb :");
		
		System.out.println("workflowGroupFlag :"+dashboardWebWrapper.workflowGroupFlag);
	
		try {
			
				
				con = getConnection();
				
			
				//-------- Fetch WorkflowGroup---------  
				pstmt = con.prepareStatement("SELECT WorkflowGroup FROM Users WHERE Userid=?");
				pstmt.setString(1,usersProfileWrapper.userid);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{	
					workflowGroup=resultSet.getString("WorkflowGroup");
					System.out.println("workflowGroup " + workflowGroup);
					
				}
				
				resultSet.close();
				pstmt.close();
				
				//-------------------Applications Count ---------------------
				
				sql="SELECT ProductCode,RecordStatus,Count(RecordStatus) as Count FROM RMT_OnBoard WHERE 1=1";
				
				
				//-------------- --------------------------
				if(workflowGroup !=null && workflowGroup.equals("SALESUSER"))
				{
					sql=sql+" AND MakerId=?";
					
				}
				//Everytime WorkflowGroup adds, if it is Y workflowGroup is not added
				if(dashboardWebWrapper.workflowGroupFlag !=null && !dashboardWebWrapper.workflowGroupFlag.equals("Y"))  
				{
					sql=sql+" AND WorkflowGroup=?";
					
				}
				
				if(workflowGroup !=null && workflowGroup.equals("CREDITUSER"))
				{
					sql=sql+" OR WorkflowGroup=?";
				}
					
				sql= sql+" Group by ProductCode,RecordStatus";
				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				
				if(workflowGroup !=null && workflowGroup.equals("SALESUSER"))
				{
					n=n+1;
					pstmt.setString(n,usersProfileWrapper.userid);
					
				}
				if(dashboardWebWrapper.workflowGroupFlag !=null && !dashboardWebWrapper.workflowGroupFlag.equals("Y"))
				{
					n=n+1;
					pstmt.setString(n,workflowGroup);
				}
				if(workflowGroup !=null && workflowGroup.equals("CREDITUSER"))
				{
					n=n+1;
					pstmt.setString(n,"END");
				}
				resultSet = pstmt.executeQuery();
				while(resultSet.next()) 
				{
					dashboardWebWrapper=new DashboardWebWrapper();
					
					
					
					dashboardWebWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
					dashboardWebWrapper.recordStatus=Utility.trim(resultSet.getString("RecordStatus"));
					dashboardWebWrapper.count=Utility.trim(resultSet.getString("Count"));
					
					dashboardWebWrapper.recordFound=true;
					vector.addElement(dashboardWebWrapper);
					
				}
				if (vector.size()>0)
				{
					
					
					dataArrayWrapper.dashboardWebWrapper = new DashboardWebWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.dashboardWebWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else{
					dataArrayWrapper.dashboardWebWrapper = new DashboardWebWrapper[1];
					dataArrayWrapper.dashboardWebWrapper[0]=dashboardWebWrapper;
					dataArrayWrapper.recordFound=true;
				}
			
				resultSet.close();
				pstmt.close();
				
				
				
				//--------------user  belongs to credituser group than
				
				if(workflowGroup !=null && workflowGroup.equals("CREDITUSER"))
				{
					
					vector.clear();
					
					
					//sql="SELECT WorkflowGroup,ActionStatus,Count(actionstatus) as Count FROM RMT_WorkflowTrack  GROUP BY WorkflowGroup,ActionStatus";

					sql="SELECT WorkflowGroup, ProductCode, Count(ProductCode) as Count FROM RMT_WorkflowStatus WHERE Status='I' Group By WorkflowGroup,ProductCode";
					
					System.out.println("sql " + sql);
					
				    pstmt = con.prepareStatement(sql);
				
					resultSet = pstmt.executeQuery();
					while(resultSet.next()) 
					{
						graphWrapper=new GraphWrapper();
						
						graphWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
						graphWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
						graphWrapper.count=Utility.trim(resultSet.getString("Count"));
						
						graphWrapper.recordFound=true;
						vector.addElement(graphWrapper);
						
					}
					if (vector.size()>0)
					{
						dataArrayWrapper.creditUserWrapper=new GraphWrapper[vector.size()];
						vector.copyInto(dataArrayWrapper.creditUserWrapper);
						//dataArrayWrapper.webCreditUserWrapper = new DashboardWebWrapper[vector.size()];
						//vector.copyInto(dataArrayWrapper.webCreditUserWrapper);
						dataArrayWrapper.recordFound=true;
		
						System.out.println("total trn. in fetch " + vector.size());
		
					}
					
					
					resultSet.close();
					pstmt.close();
					
				}
				else
				{
					dataArrayWrapper.creditUserWrapper = new GraphWrapper[1];
					dataArrayWrapper.creditUserWrapper[0]=graphWrapper;
					dataArrayWrapper.recordFound=true;
				}
			
				
				//------------------------------Average Time------------------------
				
				System.out.println("Average Time to review application ");
				vector.clear();
				
				sql="SELECT WorkflowGroup, ProductCode, Avg(TAT) as Tat FROM RMT_WorkflowTrack Group By WorkflowGroup,ProductCode";
					
				
				System.out.println("sql " + sql);
				
				pstmt = con.prepareStatement(sql);
				
				resultSet = pstmt.executeQuery();
				while(resultSet.next()) 
				{
					graphWrapper=new GraphWrapper();
					
					
					
					graphWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
					graphWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
					graphWrapper.avgTAT=Utility.trim(resultSet.getString("Tat"));
					
					graphWrapper.recordFound=true;
					vector.addElement(graphWrapper);
					
				}
				if (vector.size()>0)
				{
					
					
					dataArrayWrapper.avgReviewTimeWrapper = new GraphWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.avgReviewTimeWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else{
					dataArrayWrapper.avgReviewTimeWrapper = new GraphWrapper[1];
					dataArrayWrapper.avgReviewTimeWrapper[0]=graphWrapper;
					dataArrayWrapper.recordFound=true;
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

}
