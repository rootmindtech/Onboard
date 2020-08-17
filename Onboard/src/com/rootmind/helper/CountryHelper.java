package com.rootmind.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.rootmind.wrapper.AbstractWrapper;

public class CountryHelper extends Helper {


	
	public AbstractWrapper fetchCurrency(String countryCode)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		CountryWrapper countryWrapper=new CountryWrapper();

		System.out.println("code entry" + countryCode);

		
		try {
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT * from COUNTRY where code=?");
			pstmt.setString(1,countryCode.trim());
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) 
			{
				
				countryWrapper.code=resultSet.getString("code").trim();
				System.out.println("code " + countryWrapper.code);

				countryWrapper.name= resultSet.getString("name").trim();
				System.out.println("name " + countryWrapper.name);

				countryWrapper.continent=resultSet.getString("continent").trim();
				System.out.println("continent " + countryWrapper.continent);

				countryWrapper.region=resultSet.getString("region").trim();
				System.out.println("region " + countryWrapper.region);

				countryWrapper.lifeExpectancy=resultSet.getString("lifeExpectancy") == null ? new Double(0) : Double.parseDouble(resultSet.getString("lifeExpectancy").trim());
				System.out.println("life " + countryWrapper.lifeExpectancy);

				countryWrapper.gnp=resultSet.getString("gnp") == null ? new Double(0)  : Double.parseDouble(resultSet.getString("gnp").trim());
				System.out.println("gnp " + countryWrapper.gnp);


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

		return countryWrapper;

	}	
}
