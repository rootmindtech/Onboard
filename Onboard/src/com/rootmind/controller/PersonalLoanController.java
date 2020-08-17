package com.rootmind.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;



import com.rootmind.helper.PersonalLoanHelper;
import com.rootmind.helper.PersonalLoanWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class PersonalLoanController {
	
	
public AbstractWrapper validate(PersonalLoanWrapper personalLoanWrapper)throws Exception {
		
		
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
			
				Date startingDate = dmyFormat.parse(personalLoanWrapper.startingDate);
			
				
				if(startingDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="SDT001";
					errorWrapper.errorDesc="Starting date should be before current date";
					vector.add(errorWrapper);
	        	}
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					personalLoanWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(personalLoanWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.personalLoanWrapper=new PersonalLoanWrapper[1];
					dataArrayWrapper.personalLoanWrapper[0]=personalLoanWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					PersonalLoanHelper personalLoanHelper=new PersonalLoanHelper(); 
	
			        
		        	System.out.println("personalLoan Controller:Update personalLoan Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)personalLoanHelper.updatePersonalLoan2(personalLoanWrapper);
		        	
					
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
