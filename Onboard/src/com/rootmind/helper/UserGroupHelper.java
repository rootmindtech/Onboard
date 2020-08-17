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

public class UserGroupHelper extends Helper{
	
	public AbstractWrapper fetchUserGroupList(UserGroupWrapper userGroupWrapper)throws Exception {

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
		
		boolean useridFound=false;
		
		System.out.println("fetchUserGroupList");
	
		try {
			
				   //PopoverHelper popoverHelper=new PopoverHelper();
				con = getConnection();
				
				sql="SELECT Userid from RMTSEC_UserGroup WHERE Userid=?";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,userGroupWrapper.userid.trim());
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					useridFound=true;
				}
				resultSet.close();
				pstmt.close();
	
				
				if(useridFound==true)
				{
						//1st condition
						sql="SELECT a.Code , a.Description, b.Userid, b.GroupID, b.Assigned  from RMTSEC_Group a LEFT JOIN  RMTSEC_UserGroup b ON a.Code=b.GroupID and b.Userid=?";
	
						pstmt = con.prepareStatement(sql);
						
						pstmt.setString(1,userGroupWrapper.userid.trim());
						
						resultSet = pstmt.executeQuery();
						while (resultSet.next()) 
						{
	
							userGroupWrapper = new UserGroupWrapper();
							
							//userGroupWrapper.code = Utility.trim(resultSet.getString("Code"));
							
							userGroupWrapper.groupID = Utility.trim(resultSet.getString("Code"));
							System.out.println("groupID" + userGroupWrapper.groupID);
							
							userGroupWrapper.desc = Utility.trim(resultSet.getString("Description"));
							userGroupWrapper.userid = Utility.trim(resultSet.getString("Userid"));
							System.out.println("Userid" + userGroupWrapper.userid);
						
							userGroupWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
							
							
							userGroupWrapper.recordFound = true;
							
							vector.addElement(userGroupWrapper);
		
						}
						if (vector.size()>0)
						{
							dataArrayWrapper.userGroupWrapper = new UserGroupWrapper[vector.size()];
							vector.copyInto(dataArrayWrapper.userGroupWrapper);
							dataArrayWrapper.recordFound=true;
			
							System.out.println("total trn. in fetch " + vector.size());
		
							System.out.println("Fetch UserMenu Details Successful" );
						}
						
						resultSet.close();
						pstmt.close();
						
				}
	
				if(useridFound==false)
				{
					
						sql = "SELECT  Code, Description, 'N' as Assigned  from RMTSEC_Group";
					
						pstmt = con.prepareStatement(sql);
						resultSet = pstmt.executeQuery();
						while (resultSet.next()) 
						{
	
							userGroupWrapper = new UserGroupWrapper();
							
							userGroupWrapper.groupID = Utility.trim(resultSet.getString("Code"));
							System.out.println("groupID" + userGroupWrapper.groupID);
							
							userGroupWrapper.desc = Utility.trim(resultSet.getString("Description"));
						
							userGroupWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
							userGroupWrapper.recordFound = true;
							
							vector.addElement(userGroupWrapper);
		
						}
						if (vector.size()>0)
						{
							dataArrayWrapper.userGroupWrapper = new UserGroupWrapper[vector.size()];
							vector.copyInto(dataArrayWrapper.userGroupWrapper);
							dataArrayWrapper.recordFound=true;
			
							System.out.println("total trn. in fetch " + vector.size());
		
							System.out.println("Fetch User Menu List Details Successful" );
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
	
	
	public AbstractWrapper updateUserGroupList(UsersWrapper usersProfileWrapper,UserGroupWrapper[] userGroupWrapperArray)throws Exception {
		
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
				
				for(int i=0;i<=userGroupWrapperArray.length-1;i++)
				{
					//--if event is assigned to user than only insert
					
							sql="SELECT GroupID FROM RMTSEC_UserGroup WHERE GroupID=? AND Userid=?";
							pstmt = con.prepareStatement(sql);
							
							System.out.println(" groupID "+userGroupWrapperArray[i].groupID);
							System.out.println(" Userid "+userGroupWrapperArray[i].userid);
							
							pstmt.setString(1,Utility.trim(userGroupWrapperArray[i].groupID)); 
							pstmt.setString(2,Utility.trim(userGroupWrapperArray[i].userid)); 
						 
							
							resultSet = pstmt.executeQuery();
							
							if (!resultSet.next()) 
							{
									resultSet.close();
									pstmt.close();
									
									
										   System.out.println(" Insert into MST_UserGroup "+userGroupWrapperArray[i].groupID);
										
											sql=" INSERT INTO RMTSEC_UserGroup(Userid, GroupID, Assigned, MakerID, MakerDateTime) Values (?,?,?,?,?)";
											
											System.out.println("sql " + sql);
											
											pstmt = con.prepareStatement(sql);
											
											pstmt.setString(1,Utility.trim(userGroupWrapperArray[i].userid)); 
											pstmt.setString(2,Utility.trim(userGroupWrapperArray[i].groupID)); 
											pstmt.setString(3,Utility.trim(userGroupWrapperArray[i].assignFlag)); 
											pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
											pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
									
											System.out.println(" Userid "+userGroupWrapperArray[i].userid);
											
											pstmt.executeUpdate();
											pstmt.close();
											
											userGroupWrapperArray[i].recordFound=true;
											
											
											//-----INSERT INTO RMTSEC_UserGroup_Audit--TO PERFORM AUDIT-----------
											
											sql=" INSERT INTO RMTSEC_UserGroup_Audit(Userid, GroupID, Assigned, MakerID, MakerDateTime) Values (?,?,?,?,?)";
											
											System.out.println("sql " + sql);
											
											pstmt = con.prepareStatement(sql);
											
											pstmt.setString(1,Utility.trim(userGroupWrapperArray[i].userid)); 
											pstmt.setString(2,Utility.trim(userGroupWrapperArray[i].groupID)); 
											pstmt.setString(3,Utility.trim(userGroupWrapperArray[i].assignFlag)); 
											pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
											pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
									
											System.out.println(" Userid "+userGroupWrapperArray[i].userid);
											
											pstmt.executeUpdate();
											pstmt.close();
											
											//-----------
											
									
									
							}
							else
							{
									resultSet.close();
									pstmt.close();
								
									
										
									pstmt = con.prepareStatement("UPDATE RMTSEC_UserGroup SET GroupID=?, Assigned=?, ModifierID=?, ModifierDateTime=? WHERE GroupID=? AND Userid=?");
									
									pstmt.setString(1,Utility.trim(userGroupWrapperArray[i].groupID)); 
									pstmt.setString(2,Utility.trim(userGroupWrapperArray[i].assignFlag)); 
									pstmt.setString(3,Utility.trim(usersProfileWrapper.userid)); 
									pstmt.setTimestamp(4,Utility.getCurrentTime());
									pstmt.setString(5,Utility.trim(userGroupWrapperArray[i].groupID)); 
									pstmt.setString(6,Utility.trim(userGroupWrapperArray[i].userid)); 
									
									pstmt.executeUpdate();
									pstmt.close();
									
									userGroupWrapperArray[i].recordFound=true;
										
									
									//-----INSERT INTO RMTSEC_UserGroup_Audit--to PERFORM AUDIT-----------
									
									sql=" INSERT INTO RMTSEC_UserGroup_Audit(Userid, GroupID, Assigned, ModifierID, ModifierDateTime) Values (?,?,?,?,?)";
									
									System.out.println("sql " + sql);
									
									pstmt = con.prepareStatement(sql);
									
									pstmt.setString(1,Utility.trim(userGroupWrapperArray[i].userid)); 
									pstmt.setString(2,Utility.trim(userGroupWrapperArray[i].groupID)); 
									pstmt.setString(3,Utility.trim(userGroupWrapperArray[i].assignFlag)); 
									pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
									pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
							
									System.out.println(" Userid "+userGroupWrapperArray[i].userid);
									
									pstmt.executeUpdate();
									pstmt.close();
									
									//-----------
									
									
									
							}
					}
						
				
				
				dataArrayWrapper.userGroupWrapper=userGroupWrapperArray;
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
	
	
	
}
