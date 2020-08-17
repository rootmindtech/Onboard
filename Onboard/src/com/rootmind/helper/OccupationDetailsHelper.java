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

public class OccupationDetailsHelper extends Helper {
	
	public AbstractWrapper insertOccupationDetails(OccupationDetailsWrapper occupationDetailsWrapper)throws Exception {

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
		
		
	
		try {
			con = getConnection();
			
			sql="INSERT INTO RMT_OnBoard(OccupationType, Occupation, Designation, "
					+ "OrgName, JoiningDate, MonthlySalary, EmployeeNo) Values "
					+ "(?,?,?,?,?,?,?)";
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			
	
			pstmt.setString(1,Utility.trim(occupationDetailsWrapper.occupationType));
			pstmt.setString(2,Utility.trim(occupationDetailsWrapper.occupation));
			pstmt.setString(3,Utility.trim(occupationDetailsWrapper.designation));
			pstmt.setString(4,Utility.trim(occupationDetailsWrapper.nameOfOrg));
			pstmt.setDate(5,Utility.getDate(occupationDetailsWrapper.doj));
			pstmt.setBigDecimal(6,Utility.trim(occupationDetailsWrapper.monthlySalary));
			pstmt.setString(7,Utility.trim(occupationDetailsWrapper.employeeNo));
			

	
			pstmt.executeUpdate();
			pstmt.close();
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_OnBoard(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(occupationDetailsWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(occupationDetailsWrapper.makerDate));
			pstmt.setString(3,Utility.trim(occupationDetailsWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(occupationDetailsWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
			//............................
			
			

			occupationDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.occupationDetailsWrapper=new OccupationDetailsWrapper[1];
			dataArrayWrapper.occupationDetailsWrapper[0]=occupationDetailsWrapper;
			
			
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
	
	
	public AbstractWrapper fetchOccupationDetails(OccupationDetailsWrapper occupationDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		//OccupationDetailsWrapper occupationDetailsWrapper=new OccupationDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();
		//OccupationDetailsWrapper occupationDetailsWrapper=null;
		
		//System.out.println(":"+ occupationDetailsWrapper.designation);
	
	
		try { 
				PopoverHelper popoverHelper=new PopoverHelper();
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, OccupationType, Occupation, Designation, "
						+ "OrgName, JoiningDate, MonthlySalary, EmployeeNo FROM RMT_OnBoard WHERE RefNo=?");
			
				
				
				System.out.println("occupationDetails RefNo is " + occupationDetailsWrapper.refNo);
				
				pstmt.setString(1,occupationDetailsWrapper.refNo.trim());
			
				
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					
					occupationDetailsWrapper=new OccupationDetailsWrapper();
					occupationDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo " + occupationDetailsWrapper.refNo);
					
	
					occupationDetailsWrapper.occupationType=Utility.trim(resultSet.getString("OccupationType"));
					System.out.println("OccupationType " + occupationDetailsWrapper.occupationType);
					
					occupationDetailsWrapper.occupation=Utility.trim(resultSet.getString("Occupation"));
					
					occupationDetailsWrapper.designation=Utility.trim(resultSet.getString("Designation"));
					
					occupationDetailsWrapper.nameOfOrg=Utility.trim(resultSet.getString("OrgName"));
					
					occupationDetailsWrapper.doj=Utility.setDate(resultSet.getString("JoiningDate"));
					
					occupationDetailsWrapper.monthlySalary=Utility.trim(resultSet.getBigDecimal("MonthlySalary"));
					
					occupationDetailsWrapper.employeeNo=Utility.trim(resultSet.getString("EmployeeNo"));
					
				
					occupationDetailsWrapper.recordFound=true;
					
					
					occupationDetailsWrapper.occupationTypeValue=popoverHelper.fetchPopoverDesc(occupationDetailsWrapper.occupationType, "OccupationType");
					occupationDetailsWrapper.occupationValue=popoverHelper.fetchPopoverDesc(occupationDetailsWrapper.occupation, "Occupation");
					
					System.out.println("occupationDetailsWrapper " );
	
					vector.addElement(occupationDetailsWrapper);
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.occupationDetailsWrapper = new OccupationDetailsWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.occupationDetailsWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
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
	
	
	
	
	
	public OccupationDetailsWrapper fetchOccupationDetails(OccupationDetailsWrapper occupationDetailsWrapper,String refNo)throws Exception {
		
		
		
		try {
		
			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchOccupationDetails(occupationDetailsWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				occupationDetailsWrapper = dataArrayWrapper.occupationDetailsWrapper[0];
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

		return occupationDetailsWrapper;
		
	}

	
	public AbstractWrapper updateOccupationDetails(UsersWrapper usersProfileWrapper,OccupationDetailsWrapper occupationDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		//OccupationDetailsWrapper occupationDetailsWrapper=	new OccupationDetailsWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("updateOccupationDetails");
	
		try {
			con = getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoard SET OccupationType=?,Occupation=?,Designation=?, "
					+ "OrgName=?,  JoiningDate=?, MonthlySalary=?, EmployeeNo=?, RecordStatus=?,ModifierId=?,ModifierDateTime=? where RefNo=? ");
			
			
			
			pstmt.setString(1,Utility.trim(occupationDetailsWrapper.occupationType));
			pstmt.setString(2,Utility.trim(occupationDetailsWrapper.occupation));
			pstmt.setString(3,Utility.trim(occupationDetailsWrapper.designation));
			pstmt.setString(4,Utility.trim(occupationDetailsWrapper.nameOfOrg));
			pstmt.setDate(5,Utility.getDate(occupationDetailsWrapper.doj));
			pstmt.setBigDecimal(6,Utility.trim(occupationDetailsWrapper.monthlySalary));
			pstmt.setString(7,Utility.trim(occupationDetailsWrapper.employeeNo));
			pstmt.setString(8,Utility.trim(occupationDetailsWrapper.recordStatus));
			
			pstmt.setString(9,Utility.trim(usersProfileWrapper.userid));//modifierId
			pstmt.setTimestamp(10,new java.sql.Timestamp(System.currentTimeMillis()));//modifierDateTime
			
			pstmt.setString(11,Utility.trim(occupationDetailsWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();
/*			
			//---------update PathStatus
			
			pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET OCCUPATION=? WHERE RefNo=?");
			
			pstmt.setString(1,"Y");
			pstmt.setString(2,occupationDetailsWrapper.refNo);
			pstmt.executeUpdate();
			
			pstmt.close();
			//-----------------------
			*/

			//---------update PathStatus
			PathStatusHelper pathStatusHelper=new PathStatusHelper();
			dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(occupationDetailsWrapper.refNo),"OCCUPATION");
			//--
			
			
			occupationDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.occupationDetailsWrapper=new OccupationDetailsWrapper[1];
			dataArrayWrapper.occupationDetailsWrapper[0]=occupationDetailsWrapper;
			
			dataArrayWrapper.recordFound=true;
			System.out.println("Occupation Details updated " );
			
			
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
	
	

}
