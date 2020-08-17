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


public class UserMenuHelper extends Helper {
	

	public AbstractWrapper fetchUserMenuDetails(String userId)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		UserMenuWrapper userMenuWrapper=null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		
	
		try {
			
				con = getConnection();
					PreparedStatement pstmt = con.prepareStatement("SELECT Id,UserId,MenuId FROM RMT_UserMenu WHERE userid=?");
					pstmt.setString(1,userId.trim());
		
					resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					
					
					userMenuWrapper = new UserMenuWrapper();

					userMenuWrapper.userId = Utility.trim(resultSet.getString("UserId"));
					System.out.println("UserId" + userMenuWrapper.userId);

					userMenuWrapper.menuId = Utility.trim(resultSet.getString("MenuId"));
					System.out.println("MenuId" + userMenuWrapper.menuId);
					
					vector.addElement(userMenuWrapper);
					
					userMenuWrapper.recordFound = true;
					
					

				}
			
				if (vector.size()>0)
				{
					dataArrayWrapper.userMenuWrapper = new UserMenuWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.userMenuWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());

					System.out.println("Fetch UserMenu Details Successful" );
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
	
	
	public AbstractWrapper fetchUserMenuList(UserMenuWrapper userMenuWrapper)throws Exception {

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
	
		try {
			        //PopoverHelper popoverHelper=new PopoverHelper();
					con = getConnection();
					
					sql="SELECT UserId from RMT_UserMenu WHERE UserId=?";
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1,userMenuWrapper.userId.trim());
					
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
							sql="SELECT a.Code , a.Description, b.UserId, b.MenuId, b.Assigned  from RMT_Menu a LEFT JOIN  RMT_UserMenu b ON a.Code=b.MenuId and b.UserId=?";
		
							pstmt = con.prepareStatement(sql);
							
							pstmt.setString(1,userMenuWrapper.userId.trim());
							
							resultSet = pstmt.executeQuery();
							while (resultSet.next()) 
							{
		
								userMenuWrapper = new UserMenuWrapper();
								
								//userMenuWrapper.code = Utility.trim(resultSet.getString("Code"));
								
								userMenuWrapper.menuId = Utility.trim(resultSet.getString("Code"));
								System.out.println("menuId" + userMenuWrapper.menuId);
								
								userMenuWrapper.desc = Utility.trim(resultSet.getString("Description"));
								userMenuWrapper.userId = Utility.trim(resultSet.getString("Userid"));
								System.out.println("Userid" + userMenuWrapper.userId);
							
								userMenuWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
								
								
								userMenuWrapper.recordFound = true;
								
								vector.addElement(userMenuWrapper);
			
							}
							if (vector.size()>0)
							{
								dataArrayWrapper.userMenuWrapper = new UserMenuWrapper[vector.size()];
								vector.copyInto(dataArrayWrapper.userMenuWrapper);
								dataArrayWrapper.recordFound=true;
				
								System.out.println("total trn. in fetch " + vector.size());
			
								System.out.println("Fetch UserMenu Details Successful" );
							}
							
							resultSet.close();
							pstmt.close();
							
					}

					if(useridFound==false)
					{
						
							sql = "SELECT  Code, Description, 'N' as Assigned  from RMT_Menu";
						
							pstmt = con.prepareStatement(sql);
							resultSet = pstmt.executeQuery();
							while (resultSet.next()) 
							{
		
								userMenuWrapper = new UserMenuWrapper();
								
								userMenuWrapper.menuId = Utility.trim(resultSet.getString("Code"));
								System.out.println("menuID" + userMenuWrapper.menuId);
								
								userMenuWrapper.desc = Utility.trim(resultSet.getString("Description"));
							
								userMenuWrapper.assignFlag = Utility.trim(resultSet.getString("Assigned"));
								
								
								userMenuWrapper.recordFound = true;
								
								vector.addElement(userMenuWrapper);
			
							}
							if (vector.size()>0)
							{
								dataArrayWrapper.userMenuWrapper = new UserMenuWrapper[vector.size()];
								vector.copyInto(dataArrayWrapper.userMenuWrapper);
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
	
	public AbstractWrapper updateUserMenuList(UsersWrapper usersProfileWrapper,UserMenuWrapper[] userMenuWrapperArray)throws Exception {
				
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
						
						for(int i=0;i<=userMenuWrapperArray.length-1;i++)
						{
							//--if event is assigned to user than only insert
							
									sql="SELECT MenuId FROM RMT_UserMenu WHERE MenuId=? AND UserId=?";
									pstmt = con.prepareStatement(sql);
									
									System.out.println(" MenuId "+userMenuWrapperArray[i].menuId);
									System.out.println(" UserId "+userMenuWrapperArray[i].userId);
									
									pstmt.setString(1,Utility.trim(userMenuWrapperArray[i].menuId)); 
									pstmt.setString(2,Utility.trim(userMenuWrapperArray[i].userId)); 
								 
									
									resultSet = pstmt.executeQuery();
									
									if (!resultSet.next()) 
									{
											resultSet.close();
											pstmt.close();
											
											//if(userMenuWrapperArray[i].assignFlag.equals("Y")) 
											//{
												   System.out.println(" Insert into RMT_UserMenu "+userMenuWrapperArray[i].menuId);
												
													sql=" INSERT INTO RMT_UserMenu(UserId, MenuId, Assigned, MakerID, MakerDateTime) Values (?,?,?,?,?)";
													
													System.out.println("sql " + sql);
													
													pstmt = con.prepareStatement(sql);
													
													pstmt.setString(1,Utility.trim(userMenuWrapperArray[i].userId)); 
													pstmt.setString(2,Utility.trim(userMenuWrapperArray[i].menuId)); 
													pstmt.setString(3,Utility.trim(userMenuWrapperArray[i].assignFlag)); 
													pstmt.setString(4,Utility.trim(usersProfileWrapper.userid)); 
													pstmt.setTimestamp(5,Utility.getCurrentTime()); // maker date time
											
													System.out.println(" Userid "+userMenuWrapperArray[i].userId);
													
													pstmt.executeUpdate();
													pstmt.close();
													
													userMenuWrapperArray[i].recordFound=true;
											//}
												//dataArrayWrapper.userMenuWrapper=new UserMenuWrapper[1];
												//dataArrayWrapper.userMenuWrapper[0]=userMenuWrapper;
												//dataArrayWrapper.recordFound=true;
											
									}
									else
									{
											resultSet.close();
											pstmt.close();
										
											
												
											pstmt = con.prepareStatement("UPDATE RMT_UserMenu SET MenuId=?, Assigned=?, ModifierID=?, ModifierDateTime=? WHERE MenuId=? AND UserId=?");
											
											pstmt.setString(1,Utility.trim(userMenuWrapperArray[i].menuId)); 
											pstmt.setString(2,Utility.trim(userMenuWrapperArray[i].assignFlag)); 
											pstmt.setString(3,Utility.trim(usersProfileWrapper.userid)); 
											pstmt.setTimestamp(4,Utility.getCurrentTime());
											pstmt.setString(5,Utility.trim(userMenuWrapperArray[i].menuId)); 
											pstmt.setString(6,Utility.trim(userMenuWrapperArray[i].userId)); 
											
											pstmt.executeUpdate();
											pstmt.close();
											
											userMenuWrapperArray[i].recordFound=true;
												
											
											
											
											//eventUserWrapperArray[i].recordFound=true;
											//dataArrayWrapper.userMenuWrapper=new UserMenuWrapper[1];
											//dataArrayWrapper.userMenuWrapper[0]=userMenuWrapper;
											//dataArrayWrapper.recordFound=true;
											
											//System.out.println(" User Menu already existed");
									}
							}
								
						
						
						dataArrayWrapper.userMenuWrapper=userMenuWrapperArray;
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