package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.KycDetailsHelper;
import com.rootmind.helper.KycDetailsWrapper;
//import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class KYCDetailsController {
	
	
public AbstractWrapper validate(KycDetailsWrapper kycDetailsWrapper)throws Exception {
		
		
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
			
				Date kYCPreparedOn = dmyFormat.parse(kycDetailsWrapper.kYCPreparedOn);
				Date nextKYCReviewDate = dmyFormat.parse(kycDetailsWrapper.nextKYCReviewDate);
				
				
				if(kYCPreparedOn.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="KYC001";
					errorWrapper.errorDesc="KYC prepare date should be before current date";
					vector.add(errorWrapper);
	        	}
				if(nextKYCReviewDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="KYC002";
					errorWrapper.errorDesc="KYC review date should be future date";
					vector.add(errorWrapper);
	        	}
				
				
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					kycDetailsWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(kycDetailsWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.kycDetailsWrapper=new KycDetailsWrapper[1];
					dataArrayWrapper.kycDetailsWrapper[0]=kycDetailsWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					KycDetailsHelper kycDetailsHelper=new KycDetailsHelper(); 
	
			        
		        	System.out.println("KYC Details Controller:Update KYC Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)kycDetailsHelper.updateKycDetails(kycDetailsWrapper);
		        	
					
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
