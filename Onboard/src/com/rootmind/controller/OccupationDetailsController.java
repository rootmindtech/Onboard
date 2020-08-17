package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.OccupationDetailsHelper;
import com.rootmind.helper.OccupationDetailsWrapper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class OccupationDetailsController {
	
	
public AbstractWrapper validate(UsersWrapper usersProfileWrapper,OccupationDetailsWrapper occupationDetailsWrapper)throws Exception {
		
		
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
			
				Date visaIssueDate = dmyFormat.parse(occupationDetailsWrapper.doj);
				
				
				if(visaIssueDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="DOJ001";
					errorWrapper.errorDesc="Date of joining should be before current date";
					vector.add(errorWrapper);
	        	}
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					occupationDetailsWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(occupationDetailsWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.occupationDetailsWrapper=new OccupationDetailsWrapper[1];
					dataArrayWrapper.occupationDetailsWrapper[0]=occupationDetailsWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					OccupationDetailsHelper	occupationDetailsHelper=new OccupationDetailsHelper(); 
	
			        
		        	System.out.println("Occupation Details Controller:Update Occupatio Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)occupationDetailsHelper.updateOccupationDetails(usersProfileWrapper,occupationDetailsWrapper);
		        	
					
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
