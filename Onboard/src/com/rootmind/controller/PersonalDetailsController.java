package com.rootmind.controller;



import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.PersonalDetailsHelper;
import com.rootmind.helper.PersonalDetailsWrapper;
import com.rootmind.helper.UsersWrapper;
import com.rootmind.helper.Utility;
import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class PersonalDetailsController {
	
	
public AbstractWrapper validate(UsersWrapper usersProfileWrapper,PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
		
		
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
			
				Date passportIssueDate = dmyFormat.parse(personalDetailsWrapper.passportIssueDate);
				Date passportExpDate = dmyFormat.parse(personalDetailsWrapper.passportExpDate);
				Date emiratesIDExpDate = dmyFormat.parse(personalDetailsWrapper.emiratesIDExpDate);
				
				
				if(Utility.dateDifference(personalDetailsWrapper.dob)<=15)
				{
				
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="BUS001";
					errorWrapper.errorDesc="Date of birth is less than 15 years";
					vector.add(errorWrapper);
				}

				if(passportIssueDate.after(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="PID002";
					errorWrapper.errorDesc="Passport issue date should be before current date";
					vector.add(errorWrapper);
	        	}
				if(passportExpDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="PED003";
					errorWrapper.errorDesc="Passport expiry date should be future date";
					vector.add(errorWrapper);
	        	}
				if(emiratesIDExpDate.before(currentDate)){
					
					errorWrapper = new ErrorWrapper();
					errorWrapper.errorCode="EID004";
					errorWrapper.errorDesc="Emirates id expiry date should be future date";
					vector.add(errorWrapper);
	        	}
				
				if (vector.size()>0)
				{
					
					dataArrayWrapper.errorWrapper = new ErrorWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.errorWrapper);
					personalDetailsWrapper.recordFound=false;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				
				if((vector.size()>0)&&(personalDetailsWrapper.recordFound==false))
				{
					
					System.out.println("Vector Size > 0 And RecordFound false " );
		        	dataArrayWrapper.personalDetailsWrapper=new PersonalDetailsWrapper[1];
					dataArrayWrapper.personalDetailsWrapper[0]=personalDetailsWrapper;
					dataArrayWrapper.recordFound=true;
			
					
					
				}
				else
				{
					//call helper
					PersonalDetailsHelper	personalDetailsHelper=new PersonalDetailsHelper(); 
	
			        
		        	System.out.println("PersonalController:Insert Personal Details  ");
		        	dataArrayWrapper = (DataArrayWrapper)personalDetailsHelper.insertPersonalDetails(usersProfileWrapper,personalDetailsWrapper);
		        	
					
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



		public AbstractWrapper fetchOnBoardQueue(UsersWrapper usersProfileWrapper, PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
			
			
			DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
			
			
			//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			formatter.applyPattern("###,###,###,##0.00");
			formatter.setDecimalFormatSymbols(symbols);
			
			
			
			try {
		
						//call helper
					     
			        	PersonalDetailsHelper	personalDetailsHelper=new PersonalDetailsHelper(); 

		        
		        		dataArrayWrapper = (DataArrayWrapper)personalDetailsHelper.fetchOnBoardQueue(usersProfileWrapper,personalDetailsWrapper);
	
				
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
