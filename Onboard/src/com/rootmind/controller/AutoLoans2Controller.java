package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.AutoLoans2Helper;
import com.rootmind.helper.AutoLoansWrapper;
import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class AutoLoans2Controller {
	
	
public AbstractWrapper validate(AutoLoansWrapper autoLoansWrapper)throws Exception {
		
		
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
			
				Date dueDate = dmyFormat.parse(autoLoansWrapper.dueDate);
				Date renewalDate = dmyFormat.parse(autoLoansWrapper.renewalDate);
		
				
				
				if(dueDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="VED002";
					errorWrapper.errorDesc="Due date should be future date";
					vector.add(errorWrapper);
	        	}
				if(renewalDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="DED003";
					errorWrapper.errorDesc="Renewal date should be future date";
					vector.add(errorWrapper);
				}
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					autoLoansWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(autoLoansWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.autoLoansWrapper=new AutoLoansWrapper[1];
					dataArrayWrapper.autoLoansWrapper[0]=autoLoansWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					AutoLoans2Helper autoLoans2Helper=new AutoLoans2Helper(); 
	
		        	System.out.println("AutoLoans2Controller:Update AutoLoans2 Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)autoLoans2Helper.updateAutoLoans2(autoLoansWrapper);
		        	
					
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
