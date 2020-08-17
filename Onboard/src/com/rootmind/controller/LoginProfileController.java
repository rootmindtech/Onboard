package com.rootmind.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.UsersHelper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class LoginProfileController {
	
	public AbstractWrapper validate(UsersWrapper usersProfileWrapper,UsersWrapper usersWrapper)throws Exception {
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Date currentDate=new Date();
		//String strCurrentDate=dmyFormat.format(currentDate);
       
		Vector<Object> vector = new Vector<Object>();
		ErrorWrapper errorWrapper=null;
		
		try {
			

				//call helper
				UsersHelper usersHelper=new UsersHelper(); 
				
		        
	        	System.out.println("Insert Login Profile controller  ");
	        	
        		 boolean useridFound= usersHelper.fetchUserid(usersWrapper.userid);
        		 

				if(useridFound==true)
				{
						errorWrapper = new ErrorWrapper();
						errorWrapper.errorCode="ERR001";
						errorWrapper.errorDesc="Userid already exists, please assign new Userid";
						vector.add(errorWrapper);
				}
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					usersWrapper.recordFound=false;
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				if((vector.size()>0)&&(usersWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.usersWrapper=new UsersWrapper[1];
					dataArrayWrapper.usersWrapper[0]=usersWrapper;
					dataArrayWrapper.recordFound=true;
			
				}
				else
				{

		        	System.out.println("Insert Login profile controller  ");
	        		dataArrayWrapper = (DataArrayWrapper)usersHelper.insertLoginProfile(usersProfileWrapper,usersWrapper);

				}
				
				
				
			
		} 
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			/*try
			{
				
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}*/
		}

		return dataArrayWrapper;
	}

}
