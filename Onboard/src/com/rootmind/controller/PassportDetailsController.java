package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.PassportHelper;
import com.rootmind.helper.PassportWrapper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class PassportDetailsController {
	
	
public AbstractWrapper validate(UsersWrapper usersProfileWrapper,PassportWrapper passportWrapper)throws Exception {
		
		
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
			
				Date visaIssueDate = dmyFormat.parse(passportWrapper.visaIssueDate);
				Date visaExpDate = dmyFormat.parse(passportWrapper.visaExpDate);
				Date drivingLicenseExpDate = dmyFormat.parse(passportWrapper.drivingLicenseExpDate);
				Date labourCardExpDate = dmyFormat.parse(passportWrapper.labourCardExpDate);
				
				if(visaIssueDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="VID001";
					errorWrapper.errorDesc="VISA issue date should be before current date";
					vector.add(errorWrapper);
	        	}
				if(visaExpDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="VED002";
					errorWrapper.errorDesc="VISA expiry date should be future date";
					vector.add(errorWrapper);
	        	}
				if(drivingLicenseExpDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="DED003";
					errorWrapper.errorDesc="Driving Licence  expiry date should be future date";
					vector.add(errorWrapper);
	        	}
				if(labourCardExpDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="LED004";
					errorWrapper.errorDesc="Labour card expiry date should be future date";
					vector.add(errorWrapper);
	        	}
				
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					passportWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(passportWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.passportWrapper=new PassportWrapper[1];
					dataArrayWrapper.passportWrapper[0]=passportWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					PassportHelper	passportHelper=new PassportHelper(); 
	
			        
		        	System.out.println("PassportController:Update Passport Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)passportHelper.updatePassportDetails(usersProfileWrapper,passportWrapper);
		        	
					
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
