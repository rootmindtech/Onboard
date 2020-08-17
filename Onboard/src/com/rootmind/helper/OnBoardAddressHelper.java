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

public class OnBoardAddressHelper extends Helper {

	
	public AbstractWrapper insertOnBoardAddress(OnBoardAddressWrapper onBoardAddressWrapper)throws Exception {

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
		
		
	
		System.out.println("Customer number :"+ onBoardAddressWrapper.cifNumber);
	
		try {
			con = getConnection();
			
			sql=" INSERT INTO RMT_OnBoardAddress(RefNo,CIFNumber, AddressType,Department,StreetName,BuildingName,Ladmark, "
					+ "FlatNo,POBox,Emirate, Telephone,Extension,Fax,Mobile,Mobile2,Addressline,AddressLine2,AddressLine3, "
					+ "AddressLine4,City,Country) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			
			
			
			System.out.println("sql " + sql);
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			onBoardAddressWrapper.refNo="CUS001";
		
			pstmt.setString(1,Utility.trim(onBoardAddressWrapper.refNo));
			pstmt.setString(2,Utility.trim(onBoardAddressWrapper.cifNumber));
			pstmt.setString(3,Utility.trim(onBoardAddressWrapper.addType));
			pstmt.setString(4,Utility.trim(onBoardAddressWrapper.department));
			pstmt.setString(5,Utility.trim(onBoardAddressWrapper.strtName));
			pstmt.setString(6,Utility.trim(onBoardAddressWrapper.buidName));
			pstmt.setString(7,Utility.trim(onBoardAddressWrapper.landmark));
			pstmt.setString(8,Utility.trim(onBoardAddressWrapper.flatNo));
			pstmt.setString(9,Utility.trim(onBoardAddressWrapper.poBox));
			pstmt.setString(10,Utility.trim(onBoardAddressWrapper.emirate));
			pstmt.setDate(11,Utility.getDate(onBoardAddressWrapper.tphone));
			pstmt.setString(12,Utility.trim(onBoardAddressWrapper.extension));
			pstmt.setString(13,Utility.trim(onBoardAddressWrapper.fax));
			pstmt.setString(14,Utility.trim(onBoardAddressWrapper.mobile));
			pstmt.setString(15,Utility.trim(onBoardAddressWrapper.mobile2));
			pstmt.setString(16,Utility.trim(onBoardAddressWrapper.addline));
			pstmt.setString(17,Utility.trim(onBoardAddressWrapper.addline2));
			pstmt.setString(18,Utility.trim(onBoardAddressWrapper.addline3));
			pstmt.setString(19,Utility.trim(onBoardAddressWrapper.addline4));
			pstmt.setString(20,Utility.trim(onBoardAddressWrapper.city));
			pstmt.setString(21,Utility.trim(onBoardAddressWrapper.country));
			
			pstmt.executeUpdate();
			pstmt.close();
			
			
			
			//maker and checker 
			
			pstmt = con.prepareStatement("INSERT INTO RMT_OnBoardAddress(makerId,makerDate,approverId,approverDate) values (?,?,?,?)");

			pstmt.setString(1,Utility.trim(onBoardAddressWrapper.makerId));
			pstmt.setDate(2,Utility.getDate(onBoardAddressWrapper.makerDate));
			pstmt.setString(3,Utility.trim(onBoardAddressWrapper.approverId));
			pstmt.setDate(4,Utility.getDate(onBoardAddressWrapper.approverDate));
			
			pstmt.executeUpdate();

			pstmt.close();
	
			//------------------
			
			

			onBoardAddressWrapper.recordFound=true;
			
			
			
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
	
	
	public AbstractWrapper fetchOnBoardAddress()throws Exception {

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

		
		//System.out.println("OnBoardAddress:"+ onBoardAddressWrapper.cifNumber);


	
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,CIFNumber, AddressType,Department,StreetName,BuildingName, "
					+ "Landmark,FlatNo,POBox,Emirate, Telephone,Extension,Fax,Mobile,Mobile2,Addressline,AddressLine2,AddressLine3, "
					+ "AddressLine4,City,Country FROM RMT_OnBoardAddress");
		
	
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
				OnBoardAddressWrapper onBoardAddressWrapper = new OnBoardAddressWrapper();
				
				
				onBoardAddressWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
				System.out.println("ExistingCIFNumber" + onBoardAddressWrapper.refNo);

				onBoardAddressWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
				System.out.println("ExistingAccountNo " + onBoardAddressWrapper.cifNumber);
				
				onBoardAddressWrapper.addType=Utility.trim(resultSet.getString("AddressType"));
				System.out.println("AddressType" + onBoardAddressWrapper.addType);
				
				onBoardAddressWrapper.department=Utility.trim(resultSet.getString("Department"));
				System.out.println("Department" + onBoardAddressWrapper.department);
				
				onBoardAddressWrapper.strtName=Utility.trim(resultSet.getString("StreetName"));
				System.out.println("StreetName" + onBoardAddressWrapper.strtName);
				
				onBoardAddressWrapper.buidName=Utility.trim(resultSet.getString("BuildingName"));
				System.out.println("BuildingName" + onBoardAddressWrapper.buidName);
				
				onBoardAddressWrapper.landmark=Utility.trim(resultSet.getString("Landmark"));
				System.out.println("Ladmark" + onBoardAddressWrapper.landmark);
				
				onBoardAddressWrapper.flatNo=Utility.trim(resultSet.getString("FlatNo"));
				System.out.println("FlatNo" + onBoardAddressWrapper.flatNo);
				
				onBoardAddressWrapper.poBox=Utility.trim(resultSet.getString("POBox"));
				System.out.println("POBox" + onBoardAddressWrapper.poBox);
				
				onBoardAddressWrapper.emirate=Utility.trim(resultSet.getString("Emirate"));
				System.out.println("Emirate" + onBoardAddressWrapper.emirate);
				
				onBoardAddressWrapper.tphone=Utility.trim(resultSet.getString("Telephone"));
				System.out.println("Telephone" + onBoardAddressWrapper.tphone);
				
				onBoardAddressWrapper.extension=Utility.trim(resultSet.getString("Extension"));
				System.out.println("Extension" + onBoardAddressWrapper.extension);
				
				onBoardAddressWrapper.fax=Utility.trim(resultSet.getString("Fax"));
				System.out.println("Fax" + onBoardAddressWrapper.fax);
				
				onBoardAddressWrapper.mobile=Utility.trim(resultSet.getString("Mobile"));
				System.out.println("Mobile" + onBoardAddressWrapper.mobile);
				
				onBoardAddressWrapper.mobile2=Utility.trim(resultSet.getString("Mobile2"));
				System.out.println("Mobile2" + onBoardAddressWrapper.mobile2);
				
				onBoardAddressWrapper.addline=Utility.trim(resultSet.getString("Addressline"));
				System.out.println("Addressline" + onBoardAddressWrapper.addline);
				
				onBoardAddressWrapper.addline2=Utility.trim(resultSet.getString("AddressLine2"));
				System.out.println("AddressLine2" + onBoardAddressWrapper.addline2);
			
				onBoardAddressWrapper.addline3=Utility.trim(resultSet.getString("AddressLine3"));
				
				onBoardAddressWrapper.addline4=Utility.trim(resultSet.getString("AddressLine4"));
				
				onBoardAddressWrapper.city=Utility.trim(resultSet.getString("City"));
				
				onBoardAddressWrapper.country=Utility.trim(resultSet.getString("Country"));
				
				
				
				onBoardAddressWrapper.recordFound=true;
				
			

				vector.addElement(onBoardAddressWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.personalDetailsWrapper = new PersonalDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.personalDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
 
			if(resultSet !=null) resultSet.close();
			pstmt.close();

			System.out.println("Fetch OnBoard Address Successful" );
			
			
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
	
	
	public AbstractWrapper updateOnBoardAddress(OnBoardAddressWrapper onBoardAddressWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
	
		//OnBoardAddressWrapper onBoardAddressWrapper=new OnBoardAddressWrapper();
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		System.out.println("updateOnBoardAddress");
	
		try {
			con = getConnection();
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_OnBoardAddress SET CIFNumber, AddressType,Department,StreetName, "
					+ "BuildingName,Landmark,FlatNo,POBox,Emirate, Telephone,Extension,Fax,Mobile,Mobile2,Addressline,AddressLine2,"
					+ "AddressLine3,AddressLine4,City,Country where RefNo=? ");
			
			pstmt.setString(1,Utility.trim(onBoardAddressWrapper.cifNumber));
			pstmt.setString(2,Utility.trim(onBoardAddressWrapper.addType));
			pstmt.setString(3,Utility.trim(onBoardAddressWrapper.department));
			pstmt.setString(4,Utility.trim(onBoardAddressWrapper.strtName));
			pstmt.setString(5,Utility.trim(onBoardAddressWrapper.buidName));
			pstmt.setString(6,Utility.trim(onBoardAddressWrapper.landmark));
			pstmt.setString(7,Utility.trim(onBoardAddressWrapper.flatNo));
			pstmt.setString(8,Utility.trim(onBoardAddressWrapper.poBox));
			pstmt.setString(9,Utility.trim(onBoardAddressWrapper.emirate));
			pstmt.setString(10,Utility.trim(onBoardAddressWrapper.tphone));
			pstmt.setString(11,Utility.trim(onBoardAddressWrapper.extension));
			pstmt.setString(12,Utility.trim(onBoardAddressWrapper.fax));
			pstmt.setString(13,Utility.trim(onBoardAddressWrapper.mobile));
			pstmt.setString(14,Utility.trim(onBoardAddressWrapper.mobile2));
			pstmt.setString(15,Utility.trim(onBoardAddressWrapper.addline));
			pstmt.setString(16,Utility.trim(onBoardAddressWrapper.addline2));
			pstmt.setString(17,Utility.trim(onBoardAddressWrapper.addline3));
			pstmt.setString(18,Utility.trim(onBoardAddressWrapper.addline4));
			pstmt.setString(19,Utility.trim(onBoardAddressWrapper.city));
			pstmt.setString(20,Utility.trim(onBoardAddressWrapper.country));
		

	
			pstmt.setString(21,Utility.trim(onBoardAddressWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();
			
			
			onBoardAddressWrapper.recordFound=true;
			
			dataArrayWrapper.onBoardAddressWrapper=new OnBoardAddressWrapper[1];
			dataArrayWrapper.onBoardAddressWrapper[0]=onBoardAddressWrapper;
			
			System.out.println("OnBoardAddress Updated " );
			
			
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
