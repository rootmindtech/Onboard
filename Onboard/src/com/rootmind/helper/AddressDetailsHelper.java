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

public class AddressDetailsHelper extends Helper{
	
	
	
	public AbstractWrapper insertAddressDetails(AddressDetailsWrapper addressDetailsWrapper)throws Exception {

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
		
		
		PreparedStatement pstmt;
		
	
		try {
			con = getConnection();
			
			sql=" INSERT INTO RMT_AddressDetails(RefNo, Department, OffStreetName, OffBuildingName, OffNearestLandmark, OffFlatNo, OffPOBox, "
					+ "OffEmirate, OffTelephone, OffExtension, OffFax, ResStreetName, ResBuildingName, ResNearestLandmark, ResFlatNo, ResPOBox, "
					+ "ResEmirate, ResTelephone, ResType, HomeCountry, HomeCity, HomeMobile, "
					+ "HomeTelephone, HomeAddress, HomeAddress2, HomeAddress3) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
	
			
			
			
			System.out.println("sql " + sql);
			
			 pstmt = con.prepareStatement(sql);
			
			
			
			pstmt.setString(1,Utility.trim(addressDetailsWrapper.refNo));
			pstmt.setString(2,Utility.trim(addressDetailsWrapper.department));
			pstmt.setString(3,Utility.trim(addressDetailsWrapper.offStreetName));
			pstmt.setString(4,Utility.trim(addressDetailsWrapper.offBuildingName));
			pstmt.setString(5,Utility.trim(addressDetailsWrapper.offNearestLandmark));
			pstmt.setString(6,Utility.trim(addressDetailsWrapper.offFlatNo));
			pstmt.setString(7,Utility.trim(addressDetailsWrapper.offPOBox));
			pstmt.setString(8,Utility.trim(addressDetailsWrapper.offEmirate));
			pstmt.setString(9,Utility.trim(addressDetailsWrapper.offTelephone));
			pstmt.setString(10,Utility.trim(addressDetailsWrapper.offExtension));
			pstmt.setString(11,Utility.trim(addressDetailsWrapper.offFax));
			pstmt.setString(12,Utility.trim(addressDetailsWrapper.resStreetName));
			pstmt.setString(13,Utility.trim(addressDetailsWrapper.resBuildingName));
			pstmt.setString(14,Utility.trim(addressDetailsWrapper.resNearestLandmark));
			pstmt.setString(15,Utility.trim(addressDetailsWrapper.resFlatNo));
			pstmt.setString(16,Utility.trim(addressDetailsWrapper.resPOBox));
			pstmt.setString(17,Utility.trim(addressDetailsWrapper.resEmirate));
			pstmt.setString(18,Utility.trim(addressDetailsWrapper.resTelephone));
			pstmt.setString(19,Utility.trim(addressDetailsWrapper.resType));
			pstmt.setString(20,Utility.trim(addressDetailsWrapper.homeCountry));
			pstmt.setString(21,Utility.trim(addressDetailsWrapper.homeCity));
			pstmt.setString(22,Utility.trim(addressDetailsWrapper.homeMobile));
			pstmt.setString(23,Utility.trim(addressDetailsWrapper.homeTelephone));
			pstmt.setString(24,Utility.trim(addressDetailsWrapper.homeAddress1));
			pstmt.setString(25,Utility.trim(addressDetailsWrapper.homeAddress2));
			pstmt.setString(26,Utility.trim(addressDetailsWrapper.homeAddress3));
			
			
			pstmt.executeUpdate();
			pstmt.close();
			
/*			//---------update PathStatus
			
			pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET ADDRESS=? WHERE RefNo=?");
			
			pstmt.setString(1,"Y");
			pstmt.setString(2,addressDetailsWrapper.refNo);
			pstmt.executeUpdate();
			
			pstmt.close();
			//-----------------------
*/			
			
			//---------update PathStatus
			PathStatusHelper pathStatusHelper=new PathStatusHelper();
			dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(addressDetailsWrapper.refNo),"ADDRESS");
			//--
			
			addressDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.addressDetailsWrapper=new AddressDetailsWrapper[1];
			dataArrayWrapper.addressDetailsWrapper[0]=addressDetailsWrapper;
			
			dataArrayWrapper.recordFound=true;
			
			System.out.println("Address Details Inserted");
			
			
			
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

	
	
	public AbstractWrapper fetchAddressDetails(AddressDetailsWrapper addressDetailsWrapper)throws Exception {

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

		
		
	
	
		try {
			
				PopoverHelper	popoverHelper = new PopoverHelper();
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo, Department, OffStreetName, OffBuildingName, OffNearestLandmark, OffFlatNo, "
						+ " OffPOBox, OffEmirate, OffTelephone, OffExtension, OffFax, ResStreetName, ResBuildingName, ResNearestLandmark, ResFlatNo, "
						+ " ResPOBox, ResEmirate, ResTelephone, ResType, HomeCountry, HomeCity, HomeMobile, HomeTelephone, HomeAddress, HomeAddress2, "
						+ " HomeAddress3 FROM RMT_AddressDetails WHERE RefNo=?");
				
			
				System.out.println("AddressDetails RefNo is" + addressDetailsWrapper.refNo);
				
				pstmt.setString(1,addressDetailsWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
					addressDetailsWrapper = new AddressDetailsWrapper();
					
				
					
					addressDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + addressDetailsWrapper.refNo);
					
	
					addressDetailsWrapper.department=Utility.trim(resultSet.getString("Department"));
					System.out.println("Department " + addressDetailsWrapper.department);
					
					addressDetailsWrapper.offStreetName=Utility.trim(resultSet.getString("OffStreetName"));
					
					addressDetailsWrapper.offBuildingName=Utility.trim(resultSet.getString("OffBuildingName"));
					
					addressDetailsWrapper.offNearestLandmark=Utility.trim(resultSet.getString("OffNearestLandmark"));
					
					addressDetailsWrapper.offFlatNo=Utility.trim(resultSet.getString("OffFlatNo"));
					
					addressDetailsWrapper.offPOBox=Utility.trim(resultSet.getString("OffPOBox"));
					
					addressDetailsWrapper.offEmirate=Utility.trim(resultSet.getString("OffEmirate"));
				
					addressDetailsWrapper.offTelephone=Utility.trim(resultSet.getString("OffTelephone"));
					
					addressDetailsWrapper.offExtension=Utility.trim(resultSet.getString("OffExtension"));
					
					addressDetailsWrapper.offFax=Utility.trim(resultSet.getString("OffFax"));
					
					addressDetailsWrapper.resStreetName=Utility.trim(resultSet.getString("ResStreetName"));
					
					addressDetailsWrapper.resBuildingName=Utility.trim(resultSet.getString("ResBuildingName"));
					
					addressDetailsWrapper.resNearestLandmark=Utility.trim(resultSet.getString("ResNearestLandmark"));

					addressDetailsWrapper.resFlatNo=Utility.trim(resultSet.getString("ResFlatNo"));
					
					addressDetailsWrapper.resPOBox=Utility.trim(resultSet.getString("ResPOBox"));
					
					addressDetailsWrapper.resEmirate=Utility.trim(resultSet.getString("ResEmirate"));
					
					addressDetailsWrapper.resTelephone=Utility.trim(resultSet.getString("ResTelephone"));
		
					addressDetailsWrapper.resType=Utility.trim(resultSet.getString("ResType"));
					
					addressDetailsWrapper.homeCountry=Utility.trim(resultSet.getString("HomeCountry"));
					
					addressDetailsWrapper.homeCity=Utility.trim(resultSet.getString("HomeCity"));
					addressDetailsWrapper.homeMobile=Utility.trim(resultSet.getString("HomeMobile"));
					
					addressDetailsWrapper.homeTelephone=Utility.trim(resultSet.getString("HomeTelephone"));
					
					addressDetailsWrapper.homeAddress1=Utility.trim(resultSet.getString("HomeAddress"));
					addressDetailsWrapper.homeAddress2=Utility.trim(resultSet.getString("HomeAddress2"));
					addressDetailsWrapper.homeAddress3=Utility.trim(resultSet.getString("HomeAddress3"));
		
					
					
					addressDetailsWrapper.homeCountryValue=popoverHelper.fetchPopoverDesc(addressDetailsWrapper.homeCountry, "NATIONALITY");
					addressDetailsWrapper.offEmirateValue=popoverHelper.fetchPopoverDesc(addressDetailsWrapper.offEmirate, "FavouriteCity");
					addressDetailsWrapper.resEmirateValue=popoverHelper.fetchPopoverDesc(addressDetailsWrapper.resEmirate, "FavouriteCity");
					
					addressDetailsWrapper.recordFound=true;
					System.out.println("AddressDetailsWrapper");
	
					vector.addElement(addressDetailsWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.addressDetailsWrapper = new AddressDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.addressDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
				
			{
				dataArrayWrapper.addressDetailsWrapper = new AddressDetailsWrapper[1];
				dataArrayWrapper.addressDetailsWrapper[0]= addressDetailsWrapper;
				dataArrayWrapper.recordFound=true;

				
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
	
	public AddressDetailsWrapper fetchAddressDetails(AddressDetailsWrapper addressDetailsWrapper,String refNo)throws Exception {



		try 
		{

			
			DataArrayWrapper dataArrayWrapper=(DataArrayWrapper)fetchAddressDetails(addressDetailsWrapper);	
			
			if(dataArrayWrapper.recordFound==true)
			{			
				addressDetailsWrapper = dataArrayWrapper.addressDetailsWrapper[0];
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

		return addressDetailsWrapper;

	}
	
	
	public AbstractWrapper updateAddressDetails(AddressDetailsWrapper addressDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		

		System.out.println("update Address Details");
		
		PreparedStatement pstmt=null;
		//String sql=null;
	
		try {
			
				con = getConnection();
				
				pstmt = con.prepareStatement("SELECT RefNo FROM RMT_AddressDetails WHERE RefNo=?");
				
			
				System.out.println("AddressDetails RefNo is" + addressDetailsWrapper.refNo);
				
				pstmt.setString(1,addressDetailsWrapper.refNo.trim());
				
				resultSet = pstmt.executeQuery();
				if (!resultSet.next()) 
				{
					resultSet.close();
					pstmt.close();
					dataArrayWrapper=(DataArrayWrapper)insertAddressDetails(addressDetailsWrapper);
				}
				else
				{
				
					resultSet.close();
					pstmt.close();
	
	
					pstmt = con.prepareStatement("UPDATE RMT_AddressDetails SET Department=?, OffStreetName=?, OffBuildingName=?, OffNearestLandmark=?, OffFlatNo=?, "
							+ " OffPOBox=?, OffEmirate=?, OffTelephone=?, OffExtension=?, OffFax=?, ResStreetName=?, ResBuildingName=?, ResNearestLandmark=?, ResFlatNo=?, "
							+ " ResPOBox=?, ResEmirate=?, ResTelephone=?, ResType=?, HomeCountry=?, HomeCity=?, HomeMobile=?, HomeTelephone=?, HomeAddress=?, HomeAddress2=?, HomeAddress3=? where RefNo=?");
	
	


					pstmt.setString(1,Utility.trim(addressDetailsWrapper.department));
					System.out.println("Update Department " + addressDetailsWrapper.department);
	
					pstmt.setString(2,Utility.trim(addressDetailsWrapper.offStreetName));
					pstmt.setString(3,Utility.trim(addressDetailsWrapper.offBuildingName));
					pstmt.setString(4,Utility.trim(addressDetailsWrapper.offNearestLandmark));
					pstmt.setString(5,Utility.trim(addressDetailsWrapper.offFlatNo));
					pstmt.setString(6,Utility.trim(addressDetailsWrapper.offPOBox));
					pstmt.setString(7,Utility.trim(addressDetailsWrapper.offEmirate));
					pstmt.setString(8,Utility.trim(addressDetailsWrapper.offTelephone));
					pstmt.setString(9,Utility.trim(addressDetailsWrapper.offExtension));
					pstmt.setString(10,Utility.trim(addressDetailsWrapper.offFax));
					pstmt.setString(11,Utility.trim(addressDetailsWrapper.resStreetName));
					pstmt.setString(12,Utility.trim(addressDetailsWrapper.resBuildingName));
					pstmt.setString(13,Utility.trim(addressDetailsWrapper.resNearestLandmark));
					pstmt.setString(14,Utility.trim(addressDetailsWrapper.resFlatNo));
					pstmt.setString(15,Utility.trim(addressDetailsWrapper.resPOBox));
					pstmt.setString(16,Utility.trim(addressDetailsWrapper.resEmirate));
					pstmt.setString(17,Utility.trim(addressDetailsWrapper.resTelephone));
					pstmt.setString(18,Utility.trim(addressDetailsWrapper.resType));
					pstmt.setString(19,Utility.trim(addressDetailsWrapper.homeCountry));
					pstmt.setString(20,Utility.trim(addressDetailsWrapper.homeCity));
					pstmt.setString(21,Utility.trim(addressDetailsWrapper.homeMobile));
					pstmt.setString(22,Utility.trim(addressDetailsWrapper.homeTelephone));
					pstmt.setString(23,Utility.trim(addressDetailsWrapper.homeAddress1));
					pstmt.setString(24,Utility.trim(addressDetailsWrapper.homeAddress2));
					pstmt.setString(25,Utility.trim(addressDetailsWrapper.homeAddress3));
	
					pstmt.setString(26,Utility.trim(addressDetailsWrapper.refNo));
	
					System.out.println("Update RefNo " + addressDetailsWrapper.refNo);
	
	
					pstmt.executeUpdate();
	
					pstmt.close();
/*					
					//---------update PathStatus
					
					pstmt = con.prepareStatement("UPDATE RMT_PATHSTATUS SET ADDRESS=? WHERE RefNo=?");
					
					pstmt.setString(1,"Y");
					pstmt.setString(2,addressDetailsWrapper.refNo);
					pstmt.executeUpdate();
					
					pstmt.close();
					//-----------------------
*/					
					//---------update PathStatus
					PathStatusHelper pathStatusHelper=new PathStatusHelper();
					dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(Utility.trim(addressDetailsWrapper.refNo),"ADDRESS");
					//--
					
				
					addressDetailsWrapper.recordFound=true;
	
					dataArrayWrapper.addressDetailsWrapper=new AddressDetailsWrapper[1];
					dataArrayWrapper.addressDetailsWrapper[0]=addressDetailsWrapper;
	
					dataArrayWrapper.recordFound=true;
					System.out.println("Address Details updated " );
			}
					
			
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
