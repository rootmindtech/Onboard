package com.rootmind.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;


import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.DocChecklistHelper;
import com.rootmind.helper.PersonalDetailsHelper;
import com.rootmind.helper.PersonalDetailsWrapper;
import com.rootmind.helper.UsersWrapper;

import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class FinalController {
	
	
	public AbstractWrapper validate(UsersWrapper usersProfileWrapper, PersonalDetailsWrapper personalDetailsWrapper)throws Exception {
			
			
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
				
					DocChecklistHelper docChecklistHelper=new DocChecklistHelper();
					
					
					
					if(docChecklistHelper.fetchPendingDocChecklist(personalDetailsWrapper.refNo)==true)
					{
					
						errorWrapper = new ErrorWrapper();
						errorWrapper.errorCode="BUS001";
						errorWrapper.errorDesc="Mandatory documents not uploaded";
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
						PersonalDetailsHelper	personalDetailsHelper=new PersonalDetailsHelper(); 

				        
			        	System.out.println("Update Application Status  " );
		        		dataArrayWrapper = (DataArrayWrapper)personalDetailsHelper.updateApplicationStatus(usersProfileWrapper, personalDetailsWrapper);
			        	
						
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
