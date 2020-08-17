package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.CreditCardHelper;
import com.rootmind.helper.CreditCardWrapper;
import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.Utility;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class CreditCardController {
	
	
public AbstractWrapper validate(CreditCardWrapper creditCardWrapper)throws Exception {
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		
		//String strCurrentDate=dmyFormat.format(currentDate);
       
		Vector<Object> vector = new Vector<Object>();
		ErrorWrapper errorWrapper=null;
		
		try {
			
				
				
				if(Utility.dateDifference(creditCardWrapper.suppDOB)<=15)
				{
				
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="BUS001";
					errorWrapper.errorDesc="Date of birth is less than 15 years";
					vector.add(errorWrapper);
				}
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					creditCardWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(creditCardWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.creditCardWrapper=new CreditCardWrapper[1];
					dataArrayWrapper.creditCardWrapper[0]=creditCardWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					CreditCardHelper creditCardHelper=new CreditCardHelper(); 
	
			        
		        	System.out.println("CreditCard Controller:Update creditCard Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)creditCardHelper.updateCreditCard(creditCardWrapper);
		        	
					
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
