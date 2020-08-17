package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.KYCEDDHelper;
import com.rootmind.helper.KycDetailsWrapper;
//import com.rootmind.helper.UsersWrapper;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class KYCEDDController {
	
	
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
			
				Date preparedDate = dmyFormat.parse(kycDetailsWrapper.preparedDate);
				Date reviewedDate = dmyFormat.parse(kycDetailsWrapper.reviewedDate);
				Date approvedDate = dmyFormat.parse(kycDetailsWrapper.approvedDate);
				
				if(preparedDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="PDT001";
					errorWrapper.errorDesc="Prepared date should be before current date";
					vector.add(errorWrapper);
	        	}
				if(reviewedDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="RDT002";
					errorWrapper.errorDesc="Reviewed date should be before current date";
					vector.add(errorWrapper);
	        	}
				if(approvedDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="ADT003";
					errorWrapper.errorDesc="Approved date should be before current date";
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
					KYCEDDHelper kycEDDHelper=new KYCEDDHelper(); 
	
			        
		        	System.out.println("KYCEDD Details Controller:Update KYCEDD Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)kycEDDHelper.updateKYCEDD(kycDetailsWrapper);
		        	
					
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
