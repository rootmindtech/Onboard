package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class GroupMenuHelper extends Helper{
	
	
	public AbstractWrapper fetchGroupMenuList(GroupMenuWrapper groupMenuWrapper)throws Exception {

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

		PreparedStatement pstmt=null;
		
		String sql=null;
		
		boolean groupIDFound=false;
		
		System.out.println("fetchGroupMenuList");
	
		try {
			
				   //PopoverHelper popoverHelper=new PopoverHelper();
				con = getConnection();
				
				sql="SELECT GroupID from RMTSEC_GroupMenu WHERE GroupID=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,groupMenuWrapper.groupID.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					groupIDFound=true;
				}
				resultSet.close();
				pstmt.close();
	
				
				if(groupIDFound==true)
				{
						//1st condition
						sql="SELECT a.MenuId , a.MenuName, b.MenuID, b.GroupID, b.Assigned  from RMT_Menu a LEFT JOIN  RMTSEC_GroupMenu b ON a.MenuId=b.MenuID and b.GroupID=?";
	
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1,groupMenuWrapper.groupID.trim());
						
						resultSet = pstmt.executeQuery();
						while (resultSet.next()) 
						{
	
							groupMenuWrapper = new GroupMenuWrapper();
							
							//userGroupWrapper.code = Utility.trim(resultSet.getString("Code"));
							
							groupMenuWrapper.menuID = Utility.trim(resultSet.getString("MenuId"));
							System.out.println("menuID" + groupMenuWrapper.menuID);
							
							groupMenuWrapper.desc = Utility.trim(resultSet.getString("MenuName"));
							groupMenuWrapper.groupID = Utility.trim(resultSet.getString("GroupID"));
							System.out.println("groupID" + groupMenuWrapper.groupID);
						
							groupMenuWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
							
							
							groupMenuWrapper.recordFound = true;
							
							vector.addElement(groupMenuWrapper);
		
						}
						if (vector.size()>0)
						{
							dataArrayWrapper.groupMenuWrapper = new GroupMenuWrapper[vector.size()];
							vector.copyInto(dataArrayWrapper.groupMenuWrapper);
							dataArrayWrapper.recordFound=true;
			
							System.out.println("total trn. in fetch " + vector.size());
		
							System.out.println("Fetch GroupMenu Details Successful" );
						}
						
						resultSet.close();
						pstmt.close();
						
				}
	
				if(groupIDFound==false)
				{
					
						sql = "SELECT  MenuId, MenuName, 'N' as Assigned  from RMT_Menu";
					
						pstmt = con.prepareStatement(sql);
						resultSet = pstmt.executeQuery();
						while (resultSet.next()) 
						{
	
							groupMenuWrapper = new GroupMenuWrapper();
							
							groupMenuWrapper.menuID = Utility.trim(resultSet.getString("MenuId"));
							System.out.println("menuID" + groupMenuWrapper.menuID);
							
							groupMenuWrapper.desc = Utility.trim(resultSet.getString("MenuName"));
						
							groupMenuWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
							groupMenuWrapper.recordFound = true;
							
							vector.addElement(groupMenuWrapper);
		
						}
						if (vector.size()>0)
						{
							dataArrayWrapper.groupMenuWrapper = new GroupMenuWrapper[vector.size()];
							vector.copyInto(dataArrayWrapper.groupMenuWrapper);
							dataArrayWrapper.recordFound=true;
			
							System.out.println("total trn. in fetch " + vector.size());
		
							System.out.println("Fetch Group Menu List Details Successful" );
						}
						
						resultSet.close();
						pstmt.close();
	
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
	
	
	public AbstractWrapper updateGroupMenuList(UsersWrapper usersProfileWrapper,GroupMenuWrapper[] groupMenuWrapperArray)throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);

		PreparedStatement pstmt=null;
		String sql=null;
		//String currentAcademicYear=null;
		
		
		try {
				con = getConnection();
				
				for(int i=0;i<=groupMenuWrapperArray.length-1;i++)
				{
					//--if event is assigned to user than only insert
					
							sql="SELECT GroupID FROM RMTSEC_GroupMenu WHERE GroupID=? AND MenuID=?";
							pstmt = con.prepareStatement(sql);
							
							System.out.println(" groupID "+groupMenuWrapperArray[i].groupID);
							System.out.println(" menuID "+groupMenuWrapperArray[i].menuID);
							
							pstmt.setString(1,Utility.trim(groupMenuWrapperArray[i].groupID)); 
							pstmt.setString(2,Utility.trim(groupMenuWrapperArray[i].menuID)); 
						 
							
							resultSet = pstmt.executeQuery();
							
							if (!resultSet.next()) 
							{
									resultSet.close();
									pstmt.close();
									
									//if(userGroupWrapperArray[i].assignFlag.equals("Y")) 
									//{
											System.out.println(" Insert into RMTSEC_GroupMenu "+groupMenuWrapperArray[i].groupID);
										
											sql=" INSERT INTO RMTSEC_GroupMenu(GroupID, MenuID, Assigned, MakerID, MakerDateTime) Values (?,?,?,?,?)";
											
											System.out.println("sql " + sql);
											
											pstmt = con.prepareStatement(sql);
											
											pstmt.setString(1,Utility.trim(groupMenuWrapperArray[i].groupID)); 
											pstmt.setString(2,Utility.trim(groupMenuWrapperArray[i].menuID)); 
											pstmt.setString(3,Utility.trim(groupMenuWrapperArray[i].assignFlag)); 
											pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
											pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
									
											
											
											pstmt.executeUpdate();
											pstmt.close();
											
											groupMenuWrapperArray[i].recordFound=true;
											
											//----------------INSERT INTO RMTSEC_GroupMenu_Audit-----TO PERFORM AUDIT
											sql=" INSERT INTO RMTSEC_GroupMenu_Audit(GroupID, MenuID, Assigned, MakerID, MakerDateTime) Values (?,?,?,?,?)";
											
											System.out.println("sql " + sql);
											
											pstmt = con.prepareStatement(sql);
											
											pstmt.setString(1,Utility.trim(groupMenuWrapperArray[i].groupID)); 
											pstmt.setString(2,Utility.trim(groupMenuWrapperArray[i].menuID)); 
											pstmt.setString(3,Utility.trim(groupMenuWrapperArray[i].assignFlag)); 
											pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
											pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
											pstmt.executeUpdate();
											pstmt.close();
											//------------------
									
									
							}
							else
							{
									resultSet.close();
									pstmt.close();
								
									
										
									pstmt = con.prepareStatement("UPDATE RMTSEC_GroupMenu SET MenuID=?, Assigned=?, ModifierID=?, ModifierDateTime=? WHERE GroupID=? AND MenuID=?");
									
									pstmt.setString(1,Utility.trim(groupMenuWrapperArray[i].menuID)); 
									pstmt.setString(2,Utility.trim(groupMenuWrapperArray[i].assignFlag)); 
									pstmt.setString(3,Utility.trim(usersProfileWrapper.userid)); 
									pstmt.setTimestamp(4,Utility.getCurrentTime());
									pstmt.setString(5,Utility.trim(groupMenuWrapperArray[i].groupID)); 
									pstmt.setString(6,Utility.trim(groupMenuWrapperArray[i].menuID)); 
									
									pstmt.executeUpdate();
									pstmt.close();
									
									groupMenuWrapperArray[i].recordFound=true;
										
									
									//----------------INSERT INTO RMTSEC_GroupMenu_Audit-----TO PERFORM AUDIT--------
									
									sql=" INSERT INTO RMTSEC_GroupMenu_Audit(GroupID, MenuID, Assigned, ModifierID, ModifierDateTime) Values (?,?,?,?,?)";
									
									System.out.println("sql " + sql);
									
									pstmt = con.prepareStatement(sql);
									
									pstmt.setString(1,Utility.trim(groupMenuWrapperArray[i].groupID)); 
									pstmt.setString(2,Utility.trim(groupMenuWrapperArray[i].menuID)); 
									pstmt.setString(3,Utility.trim(groupMenuWrapperArray[i].assignFlag)); 
									pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
									pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
									pstmt.executeUpdate();
									pstmt.close();
									
									//----------------
									
							}
					}
						
				
				
				dataArrayWrapper.groupMenuWrapper=groupMenuWrapperArray;
				dataArrayWrapper.recordFound=true;
				
				
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
	
	
	public AbstractWrapper fetchGroupMenu(GroupMenuWrapper groupMenuWrapper)throws Exception {

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
		//ArrayList<String> groupIDArray = new ArrayList<String>();

		PreparedStatement pstmt=null;
		
		String sql=null;
		
		
		//boolean groupIDFound=false;
		
		System.out.println("fetchGroupMenu");
	
		try {
			
				   //PopoverHelper popoverHelper=new PopoverHelper();
				con = getConnection();
				
				
				//------to get groupID from RMTSEC_UserGroup
				sql="SELECT GroupID from RMTSEC_UserGroup WHERE Userid=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,groupMenuWrapper.userid.trim());
				
				resultSet = pstmt.executeQuery();
				while(resultSet.next()) 
				{
					groupMenuWrapper=new GroupMenuWrapper();
					
					groupMenuWrapper.groupID= Utility.trim(resultSet.getString("GroupID"));
					System.out.println("groupID from RMTSEC_UserGroup- " + groupMenuWrapper.groupID);
					groupMenuWrapper.recordFound=true;
					vector.addElement(groupMenuWrapper);
				}
				if (vector.size()>0)
				{
					dataArrayWrapper.groupMenuWrapper = new GroupMenuWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.groupMenuWrapper);
					dataArrayWrapper.recordFound=true;
	
				}
				
				resultSet.close();
				pstmt.close();
				vector.clear();
				//----------------------------------------
				
				//------to get MenuID from RMTSEC_GroupMenu
				if(dataArrayWrapper.groupMenuWrapper !=null && !dataArrayWrapper.groupMenuWrapper.equals("")){
					for(int i=0;i<=dataArrayWrapper.groupMenuWrapper.length-1;i++){
						
						
						sql="SELECT MenuID from RMTSEC_GroupMenu WHERE GroupID=? AND Assigned=?";
						pstmt = con.prepareStatement(sql);
						
						System.out.println("groupID- "+i+" " + groupMenuWrapper.groupID);
						pstmt.setString(1,dataArrayWrapper.groupMenuWrapper[i].groupID);
						pstmt.setString(2,"Y");
						resultSet = pstmt.executeQuery();
						while(resultSet.next()) 
						{
							groupMenuWrapper=new GroupMenuWrapper();
							
							groupMenuWrapper.menuID= Utility.trim(resultSet.getString("MenuID"));
							System.out.println("menuID- "+i+" " + groupMenuWrapper.menuID);
							groupMenuWrapper.recordFound=true;
							vector.addElement(groupMenuWrapper);
						}
						
						
						resultSet.close();
						pstmt.close();
					
					}
					
					if (vector.size()>0)
					{
						dataArrayWrapper.groupMenuWrapper = new GroupMenuWrapper[vector.size()];
						vector.copyInto(dataArrayWrapper.groupMenuWrapper);
						dataArrayWrapper.recordFound=true;
		
						System.out.println("total trn. in fetch " + vector.size());
	
						System.out.println("Fetch Group Menu List Details Successful" );
					}
				
				}
				else{
					
					dataArrayWrapper.groupMenuWrapper = new GroupMenuWrapper[1];
					dataArrayWrapper.groupMenuWrapper[0]=groupMenuWrapper;
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
	

}
