package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.ContactDetailsHelper;
import com.rootmind.helper.ContactDetailsWrapper;
import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class ContactDetailsController {
	
	
public AbstractWrapper validate(UsersWrapper usersProfileWrapper,ContactDetailsWrapper contactDetailsWrapper)throws Exception {
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Date currentDate=new Date();
		//String strCurrentDate=dmyFormat.format(currentDate);
       
		Vector<Object> vector = new Vector<Object>();
		ErrorWrapper errorWrapper=null;
		
		try {
			
				
				
				
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					contactDetailsWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(contactDetailsWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.contactDetailsWrapper=new ContactDetailsWrapper[1];
					dataArrayWrapper.contactDetailsWrapper[0]=contactDetailsWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					ContactDetailsHelper contactDetailsHelper=new ContactDetailsHelper(); 
	
			        
		        	System.out.println("Contact Details Controller:Update Details  ");
		        	//dataArrayWrapper = (DataArrayWrapper)passportHelper.updatePassportDetails(usersProfileWrapper,contactDetailsWrapper);
		        	
					
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
