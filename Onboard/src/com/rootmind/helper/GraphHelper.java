package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;


public class GraphHelper extends Helper{
	
	
	//-----------------Start ApplicationsCount count---------------------
	
	public AbstractWrapper fetchGraph(GraphWrapper graphWrapper)throws Exception {

			Connection con = null;
			ResultSet resultSet = null;
			
			DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();

			System.out.println("Graph ");
			
			Vector<Object> vector = new Vector<Object>();
			PreparedStatement pstmt=null;
			String sql=null;
			
			try {
					
				
					con = getConnection();
					sql="SELECT Count(WorkflowGroup) as WorflowGroupCount, WorkflowGroup, ProductCode, MakerID FROM RMT_OnBoard "
					+ " WHERE RecordStatus IN ('INPROGRESS', 'REJECTED', 'CREATE') ";
					
					if(graphWrapper.makerID !=null &&  !graphWrapper.makerID.equals(""))
					{
						sql=sql+" AND MakerID=? GROUP BY WorkflowGroup, ProductCode, MakerID";
					}
					else
					{
						sql=sql+" GROUP BY WorkflowGroup, ProductCode";
					}
					
					
					//------Applications Counts------------
					pstmt = con.prepareStatement(sql);
					
					if(graphWrapper.makerID !=null &&  !graphWrapper.makerID.equals(""))
					{
						pstmt.setString(1, graphWrapper.makerID);
					}
					
					
				
					resultSet = pstmt.executeQuery();
					
					while(resultSet.next()) 
					{
						graphWrapper=new GraphWrapper();
						graphWrapper.count=Utility.trim(resultSet.getString("WorflowGroupCount"));
						graphWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
						graphWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
						graphWrapper.makerID=Utility.trim(resultSet.getString("MakerID"));
						
						graphWrapper.recordFound=true;
						
						vector.addElement(graphWrapper);
						System.out.println("Applications Count successful");
					}
					if (vector.size()>0)
					{
						dataArrayWrapper.appsCountWrapper = new GraphWrapper[vector.size()];
						vector.copyInto(dataArrayWrapper.appsCountWrapper);
						dataArrayWrapper.recordFound=true;
			
						System.out.println("total trn. in fetch " + vector.size());
			
					}
					resultSet.close();
					pstmt.close();
					
				    //-----Applications Counts- END------------
					
					
					
					//------Average Application Processing Time ------------
					vector.clear();
					
					pstmt = con.prepareStatement("SELECT AVG(TAT) as AVGTAT, WorkflowGroup, ProductCode FROM RMT_WorkflowTrack GROUP BY WorkflowGroup,ProductCode");
				
					resultSet = pstmt.executeQuery();
					
					while(resultSet.next()) 
					{
						graphWrapper=new GraphWrapper();
						graphWrapper.avgTAT=Utility.trim(resultSet.getString("AVGTAT"));
						graphWrapper.workflowGroup=Utility.trim(resultSet.getString("WorkflowGroup"));
						graphWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
						
						graphWrapper.recordFound=true;
						
						vector.addElement(graphWrapper);
						System.out.println("Avg Apps Processing Time Count successful");

		
					}
					if (vector.size()>0)
					{
						dataArrayWrapper.avgAppProcessTimeWrapper = new GraphWrapper[vector.size()];
						vector.copyInto(dataArrayWrapper.avgAppProcessTimeWrapper);
						dataArrayWrapper.recordFound=true;
			
						System.out.println("total trn. in fetch " + vector.size());
			
					}
					resultSet.close();
					pstmt.close();
					//------Average Application Processing Time  END------------
					
					
					
					
				
						
						//------RecordStatus Count ------------
					vector.clear();
					
					sql="SELECT Count(RecordStatus) as RecordStatusCount, RecordStatus, ProductCode, MakerID FROM RMT_OnBoard ";
					if(graphWrapper.makerID !=null &&  !graphWrapper.makerID.equals(""))
					{
						sql=sql+" AND MakerID=? GROUP BY RecordStatus, ProductCode, MakerID";
					}
					else
					{
						sql=sql+" GROUP BY RecordStatus, ProductCode";
					}
					
					pstmt = con.prepareStatement(sql);
					
					if(graphWrapper.makerID !=null &&  !graphWrapper.makerID.equals(""))
					{
						pstmt.setString(1, graphWrapper.makerID);
					}
					
					
					resultSet = pstmt.executeQuery();
						
					while(resultSet.next()) 
					{
						graphWrapper=new GraphWrapper();
						graphWrapper.count=Utility.trim(resultSet.getString("RecordStatusCount"));
						graphWrapper.recordStatus=Utility.trim(resultSet.getString("RecordStatus"));
						graphWrapper.productCode=Utility.trim(resultSet.getString("ProductCode"));
						graphWrapper.makerID=Utility.trim(resultSet.getString("MakerID"));
						
						graphWrapper.recordFound=true;
						vector.addElement(graphWrapper);
						System.out.println("Record Status Count successful");
						

		
					}
					if (vector.size()>0)
					{
						dataArrayWrapper.recordStatusWrapper = new GraphWrapper[vector.size()];
						vector.copyInto(dataArrayWrapper.recordStatusWrapper);
						dataArrayWrapper.recordFound=true;
			
						System.out.println("total trn. in fetch " + vector.size());
			
					}
					resultSet.close();
					pstmt.close();
					
					//------RecordStatus CLOSE------------
					
					
	
				

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
	//-----------------End fetchStudentCount---------------------

}
