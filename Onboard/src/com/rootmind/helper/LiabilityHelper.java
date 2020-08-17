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


public class LiabilityHelper extends Helper{

	
	//-----------------Start insertLiability---------------------
	
			public AbstractWrapper insertLiability(UsersWrapper usersProfileWrapper, LiabilityWrapper liabilityWrapper)throws Exception {
					
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
		
					try {
							con = getConnection();
					
							sql="INSERT INTO RMT_Liability(RefNo, LiabilityType, BankName, FinancedAmount, EMI, Tenor, EMIPaid, "
									+ "BalanceTenor, BalanceOutstanding, CreditLimit) Values(?,?,?,?,?,?,?,?,?,?)";
							
							System.out.println("libility sql " + sql);
							
							pstmt = con.prepareStatement(sql);
					
							pstmt.setString(1,Utility.trim(liabilityWrapper.refNo));
							pstmt.setString(2,Utility.trim(liabilityWrapper.liabilityType));
							pstmt.setString(3,Utility.trim(liabilityWrapper.bankName));
							pstmt.setString(4,Utility.trim(liabilityWrapper.financedAmount));
							pstmt.setString(5,Utility.trim(liabilityWrapper.emi));  
							pstmt.setString(6,Utility.trim(liabilityWrapper.tenor)); 
							pstmt.setString(7,Utility.trim(liabilityWrapper.emiPaid)); 
							pstmt.setString(8,Utility.trim(liabilityWrapper.balanceTenor));  
							pstmt.setString(9,Utility.trim(liabilityWrapper.balanceOutstanding));  
							pstmt.setString(10,Utility.trim(liabilityWrapper.creditLimit));
					
							pstmt.executeUpdate();
							pstmt.close();
							
							liabilityWrapper.recordFound=true;
						
							dataArrayWrapper.liabilityWrapper=new LiabilityWrapper[1];
							dataArrayWrapper.liabilityWrapper[0]=liabilityWrapper;
							
							dataArrayWrapper.recordFound=true;
							
							System.out.println("Successfully inserted into Liability");
							
							
						
							
						
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
				
				//-----------------End insertLiability---------------------
			
			
			
			//-----------------Start updateLiability---------------------
			public AbstractWrapper updateLiability(UsersWrapper usersProfileWrapper, LiabilityWrapper liabilityWrapper)throws Exception {
				
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
			
					try {
							con = getConnection();
							pstmt = con.prepareStatement("SELECT RefNo FROM RMT_Liability WHERE SeqNo=? AND RefNo=? ");
							
							System.out.println("Liability RefNo is" + liabilityWrapper.refNo);
							
							pstmt.setString(1,Utility.trim(liabilityWrapper.seqNo));
							pstmt.setString(2,Utility.trim(liabilityWrapper.refNo));
							
							
							resultSet = pstmt.executeQuery();
							
							if (!resultSet.next()) 
							{
								resultSet.close();
								pstmt.close();
								dataArrayWrapper=(DataArrayWrapper)insertLiability(usersProfileWrapper,liabilityWrapper);
							}
							else
							{
								resultSet.close();
								pstmt.close();
								
								if(liabilityWrapper.deleteFlag.equals("Y"))
								{
									System.out.println("DeleteFlag RefNo is" + liabilityWrapper.deleteFlag);
									
									
									pstmt = con.prepareStatement("DELETE FROM RMT_Liability WHERE SeqNo=? AND RefNo=?");
									
									pstmt.setString(1,Utility.trim(liabilityWrapper.seqNo));
									pstmt.setString(2,Utility.trim(liabilityWrapper.refNo));
									pstmt.executeUpdate();
									pstmt.close();
									
									liabilityWrapper.recordFound=true;
									dataArrayWrapper.liabilityWrapper=new LiabilityWrapper[1];
									dataArrayWrapper.liabilityWrapper[0]=liabilityWrapper;
									dataArrayWrapper.recordFound=true;
									
								}
								else
								{
										
									if (resultSet!=null) resultSet.close();
									
									if(pstmt!=null) pstmt.close();
									
									System.out.println("UPDATE RefNo is" + liabilityWrapper.refNo);
								
									pstmt = con.prepareStatement("UPDATE RMT_Liability SET LiabilityType=?, BankName=?, FinancedAmount=?, "
									 		+ "EMI=?, Tenor=?, EMIPaid=?, BalanceTenor=?, BalanceOutstanding=?, "
									 		+ " CreditLimit=? WHERE SeqNo=? AND RefNo=?");
									pstmt.setString(1,Utility.trim(liabilityWrapper.liabilityType));
								    pstmt.setString(2,Utility.trim(liabilityWrapper.bankName));  
									pstmt.setString(3,Utility.trim(liabilityWrapper.financedAmount));  
									pstmt.setString(4,Utility.trim(liabilityWrapper.emi)); 
									pstmt.setString(5,Utility.trim(liabilityWrapper.tenor)); 
									pstmt.setString(6,Utility.trim(liabilityWrapper.emiPaid));  
									pstmt.setString(7,Utility.trim(liabilityWrapper.balanceTenor));  
									pstmt.setString(8,Utility.trim(liabilityWrapper.balanceOutstanding));  
									pstmt.setString(9,Utility.trim(liabilityWrapper.creditLimit));  
									pstmt.setString(10,Utility.trim(liabilityWrapper.seqNo));
									pstmt.setString(11,Utility.trim(liabilityWrapper.refNo));
								
									pstmt.executeUpdate();
									pstmt.close();
			
									liabilityWrapper.recordFound=true;
									dataArrayWrapper.liabilityWrapper=new LiabilityWrapper[1];
									dataArrayWrapper.liabilityWrapper[0]=liabilityWrapper;
									dataArrayWrapper.recordFound=true;
								
									System.out.println("Successfully Liability Updated");
								}
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
			//-----------------End updateLiability---------------------
			
	
			
			
			//-----------------Start fetchLiability---------------------
			
			public AbstractWrapper fetchLiability(UsersWrapper usersProfileWrapper, LiabilityWrapper liabilityWrapper)throws Exception {

					Connection con = null;
					ResultSet resultSet = null;
					
					DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
					
					Vector<Object> vector = new Vector<Object>();
					
					System.out.println("fetch Liability refNo  "+liabilityWrapper.refNo);
					
					try {
							PopoverHelper popoverHelper = new PopoverHelper();
							con = getConnection();
	
							PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, SeqNo, LiabilityType, BankName, FinancedAmount, EMI, Tenor, "
									+ " EMIPaid, BalanceTenor, BalanceOutstanding, CreditLimit FROM RMT_Liability WHERE RefNo=?");
							
							pstmt.setString(1,liabilityWrapper.refNo);
							resultSet = pstmt.executeQuery();
							
							while (resultSet.next()) 
							{
								
								liabilityWrapper=new LiabilityWrapper();
								
								liabilityWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
								liabilityWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
								liabilityWrapper.liabilityType=Utility.trim(resultSet.getString("LiabilityType"));
								liabilityWrapper.bankName=Utility.trim(resultSet.getString("BankName"));  
								liabilityWrapper.financedAmount=Utility.trim(resultSet.getString("FinancedAmount"));
								liabilityWrapper.emi=Utility.trim(resultSet.getString("EMI"));  
								liabilityWrapper.tenor=Utility.trim(resultSet.getString("Tenor"));
								liabilityWrapper.emiPaid=Utility.trim(resultSet.getString("EMIPaid"));
								liabilityWrapper.balanceTenor=Utility.trim(resultSet.getString("BalanceTenor"));  
								liabilityWrapper.balanceOutstanding=Utility.trim(resultSet.getString("BalanceOutstanding")); 
								liabilityWrapper.creditLimit=Utility.trim(resultSet.getString("CreditLimit"));  
								
								liabilityWrapper.liabilityTypeValue=popoverHelper.fetchPopoverDesc(liabilityWrapper.liabilityType,"Liability");
								
								liabilityWrapper.bankNameValue=popoverHelper.fetchPopoverDesc(liabilityWrapper.bankName,"NameOfBank");
								
								liabilityWrapper.recordFound=true;
		
								
				
								vector.addElement(liabilityWrapper);
					
							}
							
							if (vector.size()>0)
							{
								dataArrayWrapper.liabilityWrapper = new LiabilityWrapper[vector.size()];
								vector.copyInto(dataArrayWrapper.liabilityWrapper);
								dataArrayWrapper.recordFound=true;
					
								System.out.println("total trn. in fetch " + vector.size());
								
								System.out.println("Liability fetch successful");
					
							}
							else
							{
								dataArrayWrapper.liabilityWrapper = new LiabilityWrapper[1];
								dataArrayWrapper.liabilityWrapper[0]=liabilityWrapper;
								dataArrayWrapper.recordFound=true;
							}
							
							if (resultSet!=null)  resultSet.close();
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
			
			//-----------------End fetchLiability---------------------
			
			
			//-----------------Start fetchDBRLibilities---------------------
			
			public LiabilityWrapper fetchDBRLiabilities(LiabilityWrapper liabilityWrapper)throws Exception {

					Connection con = null;
					ResultSet resultSet = null;
					
					DataArrayWrapper dataArrayWrapper=new DataArrayWrapper();
					
					//Vector<Object> vector = new Vector<Object>();
					
					System.out.println("Inside fetchDBRLibilities refNo " +liabilityWrapper.refNo);
					
					
					
					try {
				
							con = getConnection();
	
							PreparedStatement pstmt = con.prepareStatement("SELECT a.RefNo, b.Category, SUM(a.EMI) as EMI, SUM(a.CreditLimit) as CreditLimit FROM RMT_Liability a LEFT JOIN Liability b ON a.LiabilityType=b.Code WHERE a.RefNo=?");
							
							pstmt.setString(1,liabilityWrapper.refNo);
							resultSet = pstmt.executeQuery();
							
							if(resultSet.next()) 
							{
								
								liabilityWrapper=new LiabilityWrapper();
								
								liabilityWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
								liabilityWrapper.category=Utility.trim(resultSet.getString("Category"));
								liabilityWrapper.emi=Utility.trim(resultSet.getString("EMI"));
								liabilityWrapper.creditLimit=Utility.trim(resultSet.getString("CreditLimit"));  

								liabilityWrapper.recordFound=true;
		
								System.out.println("fetchDBRLibilities successful");
				
								//vector.addElement(liabilityWrapper);
					
							}
							if (resultSet!=null)  resultSet.close();
							pstmt.close();
							
							
							
							//--Monthly Income Fetch
							
							pstmt = con.prepareStatement("SELECT MonthlySalary FROM RMT_OnBoard WHERE RefNo=?");
							pstmt.setString(1,liabilityWrapper.refNo);
							resultSet = pstmt.executeQuery();
							if(resultSet.next())
							{
								liabilityWrapper.monthlyIncome=Utility.trim(resultSet.getString("MonthlySalary"));
							}
							if (resultSet!=null)  resultSet.close();
							pstmt.close();
							//------
							
							/*if (vector.size()>0)
							{
								dataArrayWrapper.liabilityWrapper = new LiabilityWrapper[vector.size()];
								vector.copyInto(dataArrayWrapper.liabilityWrapper);
								dataArrayWrapper.recordFound=true;
					
								System.out.println("total trn. in fetch " + vector.size());
					
							}*/
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
		
					return liabilityWrapper;
					
			}
			
			//-----------------End fetchLiability---------------------
}	
		
		
		