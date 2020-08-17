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

public class PathStatusHelper extends Helper{

	
/*public AbstractWrapper insertPathStatus(String refNo,String screen)throws Exception {
		
		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		
		PathStatusWrapper pathStatusWrapper=null;
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);

		try {
				con = getConnection("ONBOARD");
				
				sql=" INSERT INTO RMT_PathStatus(RefNo,Screen,Status) Values (?,?,?)";
				
				
				System.out.println("sql " + sql);

				
				PreparedStatement pstmt = con.prepareStatement(sql);
			
			
				pstmt.setString(1,Utility.trim(refNo));
				pstmt.setString(2,Utility.trim(screen)); 
				pstmt.setString(3,"Y");
				
				
				pstmt.executeUpdate();
				pstmt.close();
				
	
				
				//pathStatusWrapper.recordFound=true;

				dataArrayWrapper.pathStatusWrapper=new PathStatusWrapper[1];
				dataArrayWrapper.pathStatusWrapper[0]=pathStatusWrapper;
				
				dataArrayWrapper.recordFound=true;
				
				
				System.out.println("Successfully inserted into RMT_PathStatus");
				
				
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
	public AbstractWrapper fetchPathStatus(PathStatusWrapper pathStatusWrapper)throws Exception {
	
		Connection con = null;
		ResultSet resultSet = null;
		
		//PathStatusWrapper pathStatusWrapper=null;
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();
	
		try {
			
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,Screen,Status FROM RMT_PathStatus WHERE RefNo=?");
			
			
			System.out.println("PATHSTATUS RefNo is" + pathStatusWrapper.refNo);
			
			pstmt.setString(1,pathStatusWrapper.refNo.trim());
			
			
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				pathStatusWrapper= new PathStatusWrapper();
				
				pathStatusWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("RefNo" + pathStatusWrapper.refNo);
				
				pathStatusWrapper.screen=Utility.trim(resultSet.getString("Screen"));
				pathStatusWrapper.status=Utility.trim(resultSet.getString("Status"));
				
				pathStatusWrapper.recordFound=true;
				vector.addElement(pathStatusWrapper);
	
			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.pathStatusWrapper = new PathStatusWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.pathStatusWrapper);
				dataArrayWrapper.recordFound=true;
	
				System.out.println("total trn. in fetch " + vector.size());
	
			}
			else	
			{
				dataArrayWrapper.pathStatusWrapper = new PathStatusWrapper[1];
				dataArrayWrapper.pathStatusWrapper[0]= pathStatusWrapper;
				dataArrayWrapper.recordFound=true;
	
				
			}
			
			if(resultSet !=null) resultSet.close();
			pstmt.close();
			System.out.println("fetch PathStatus Successful " );
			
			
	
	
			
			
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


	public PathStatusWrapper[] updatePathStatus(String refNo,String screen)throws Exception {
	
		Connection con = null;
		ResultSet resultSet = null;
		
		//PathStatusWrapper pathStatusWrapper=null;
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = null;
		
	
		System.out.println("update PathStatus Details");
		
		PreparedStatement pstmt=null;
		PreparedStatement pstmtSub=null;
		String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo,Screen,Status FROM RMT_PathStatus WHERE RefNo=? and Screen=?");
				
			
				System.out.println("Update PathStatus RefNo is" + refNo.trim());
				
				pstmt.setString(1,refNo.trim());
				pstmt.setString(2,screen.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					
					resultSet.close();
					pstmt.close();
					
					sql=" INSERT INTO RMT_PathStatus(RefNo,Screen,Status) Values (?,?,?)";
					
					
					System.out.println("sql " + sql);
	
					
					 pstmtSub = con.prepareStatement(sql);
				
				
					 pstmtSub.setString(1,Utility.trim(refNo));
					 pstmtSub.setString(2,Utility.trim(screen)); 
					 pstmtSub.setString(3,"Y");
	
					 pstmtSub.executeUpdate();
					 pstmtSub.close();
	
				}
				else
				{	
					
					resultSet.close();
					pstmt.close();
	
					pstmtSub = con.prepareStatement("UPDATE RMT_PathStatus SET Status=? where RefNo=? and Screen=?");
	
	
					pstmtSub.setString(1,"Y");
					pstmtSub.setString(2,Utility.trim(refNo));
					pstmtSub.setString(3,Utility.trim(screen));
					
					System.out.println("Updated RefNo " + refNo);
	
	
					pstmtSub.executeUpdate();
					pstmtSub.close();
					
	
					
				}
				
				
				PathStatusWrapper pathStatusWrapper = new PathStatusWrapper();
				pathStatusWrapper.refNo=refNo.trim();
				
				dataArrayWrapper=(DataArrayWrapper)fetchPathStatus(pathStatusWrapper);
				
				//pathStatusWrapper.recordFound=true;
				//dataArrayWrapper.pathStatusWrapper=new PathStatusWrapper[1];
				//dataArrayWrapper.pathStatusWrapper[0]=pathStatusWrapper;
	
				//dataArrayWrapper.recordFound=true;
				System.out.println("PathStatus updated " );
				
					
			
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
	
		return dataArrayWrapper.pathStatusWrapper;
	}

	


	
	
}
