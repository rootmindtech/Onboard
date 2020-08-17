package com.rootmind.helper;

import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class Utility {
	
	// SimpleDateFormat ddmmyyyyhhmm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	 //SimpleDateFormat yyyymmddhhmm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static java.sql.Timestamp getCurrentTime()
	{
		
		return new java.sql.Timestamp(System.currentTimeMillis()); // + (90*60*1000)); //add one and half hour for India time
		
	}

	
	public final static java.sql.Date getDate(String strDate)
	{
		
			//SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MMM-dd");
		
			SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
			
			java.sql.Date returnDate=null;
			
			try {
				
				if(strDate!=null)
				{
					//System.out.println("In Utility getdate" +strDate);
					
					//strDate=strDate.replace("\"", "");
					
					strDate=strDate.replaceAll("/","-");
					
					
					//System.out.println("In Utility getdate after replace" +strDate);
					
					returnDate=new java.sql.Date(dmyFormat.parse(strDate.trim()).getTime());
					
					
					System.out.println(" Utility Returning getdate " +returnDate);
					
					
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnDate;
		
	}
	public final static String setDate(String strDate)
	{
		
		
			SimpleDateFormat ymd = new SimpleDateFormat("yyyy-mm-dd");
		    SimpleDateFormat dmy = new SimpleDateFormat("dd-mm-yyyy");
			
			String returnDate=null;
			 Date date;
			try {
				
				if(strDate!=null)
				{
					//System.out.println("In Utility setDate" +strDate);
					
					date = ymd.parse(strDate);
					strDate = dmy.format(date);
					
					//System.out.println("In Utility setDate after format" +strDate);
					
					returnDate=strDate.replaceAll("-","/");
					
					System.out.println(" Utility Returning setDate " +returnDate);
					
					
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnDate;
		
	}
	public final static String setDate(String strDate, String format)
	{
		
		
			//SimpleDateFormat ymd = new SimpleDateFormat("yyyy-mm-dd");
		    //SimpleDateFormat dmy = new SimpleDateFormat("dd-mm-yyyy");
			
			String returnDate=null;
			 //Date date;
			try {
				
				if(strDate!=null)
				{
					//System.out.println("In Utility setDate" +strDate);
					
					java.util.Date initDate = new SimpleDateFormat("dd/mm/yyyy").parse(strDate);
				    SimpleDateFormat formatter = new SimpleDateFormat(format);
				    returnDate = formatter.format(initDate);
					
				
					
					System.out.println(" Utility Returning setDate " +returnDate);
					
					
				}
			}
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnDate;
		
	}
	
	public final static int dateDifference(String strDate)
	{
		
	
		int age=0;
		
			try {
				
				if(strDate!=null)
				{
					
					Calendar calDOB = Calendar.getInstance();
					calDOB.setTime(Utility.getDate(strDate));
					//System.out.println("calDOB year "+calDOB.get(Calendar.YEAR));
					//System.out.println("calDOB month "+calDOB.get(Calendar.MONTH));
					//System.out.println("calDOB day "+calDOB.get(Calendar.DAY_OF_MONTH));

					//System.out.println("System Today date "+ new java.sql.Timestamp(System.currentTimeMillis()));
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					
					
					//System.out.println("year "+cal.get(Calendar.YEAR));
					//System.out.println("month "+cal.get(Calendar.MONTH));
					//System.out.println("day "+cal.get(Calendar.DAY_OF_MONTH));

					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					start.set(calDOB.get(Calendar.YEAR),calDOB.get(Calendar.MONTH), calDOB.get(Calendar.DAY_OF_MONTH));
					end.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
					Date startDate = start.getTime();
					Date endDate = end.getTime();
					
					long startTime = startDate.getTime();
					long endTime = endDate.getTime();
					long diffTime = endTime - startTime;
					long diffDays = diffTime / (1000 * 60 * 60 * 24);
					//System.out.println("diffTime is "+diffTime);
					//System.out.println("diffDays is "+diffDays);
					age = Math.round(diffDays /365);
					
					System.out.println("Age is "+age);
					//DateFormat dateFormat = DateFormat.getDateInstance();
				/*	//System.out.println("The difference between "+
					  dateFormat.format(startDate)+" and "+
					  dateFormat.format(endDate)+" is "+
					  diffDays+" days.");*/
					
					
				}
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return age;
		
	}
	
	public final static int timeDifference(String strDate,String time)
	{
		
	
		int returnTime=0;
		
			try {
				
					if(strDate!=null)
					{	
						
						System.out.println("strDate "+ strDate);
						
					    java.sql.Timestamp tsstrDate = java.sql.Timestamp.valueOf(strDate);
						
					    System.out.println("tsstrDate "+ tsstrDate);
						
						Calendar calDOB = Calendar.getInstance();
						calDOB.setTime(tsstrDate);
					
						Calendar cal = Calendar.getInstance();
						cal.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
						
	
						/*
						 * Use getTimeInMillis() method to get the Calendar's time value in
						 * milliseconds. This method returns the current time as UTC
						 * milliseconds from the epoch
						 */
						long miliSecondForDate1 = calDOB.getTimeInMillis();
						long miliSecondForDate2 = cal.getTimeInMillis();
				 
						// Calculate the difference in millisecond between two dates
						long diffInMilis = miliSecondForDate2 - miliSecondForDate1;
						
						long diffInSecond = diffInMilis / 1000;
						long diffInMinute = diffInMilis / (60 * 1000);
						long diffInHour = diffInMilis / (60 * 60 * 1000);
						long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
				 
						System.out.println("Difference in Seconds : " + diffInSecond);
						System.out.println("Difference in Minute : " + diffInMinute);
						System.out.println("Difference in Hours : " + diffInHour);
						System.out.println("Difference in Days : " + diffInDays);
						
						
						
						if (time.equals("M"))
						{
							returnTime= Math.round(diffInMinute);
						}
						
						
						System.out.println("returnTime is "+returnTime);
						
						
					}
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return returnTime;
		
	}
	
	/*public final static boolean dateBeforeCurrent(String userDate)
	{
		
		 boolean dateBeforeFound=false;
		
		
			try {
				
				if(userDate!=null)
				{
					System.out.println("user input date "+ userDate);
					
					Calendar calUserDate = Calendar.getInstance();
					calUserDate.setTime(Utility.getDate(userDate));
					
					System.out.println("calUserDate year "+calUserDate.get(Calendar.YEAR));
					System.out.println("calUserDate month "+calUserDate.get(Calendar.MONTH));
					System.out.println("calUserDate day "+calUserDate.get(Calendar.DAY_OF_MONTH));

					
					
					Calendar systemDate = Calendar.getInstance();
					systemDate.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					
					
					System.out.println("year "+systemDate.get(Calendar.YEAR));
					System.out.println("month "+systemDate.get(Calendar.MONTH));
					System.out.println("day "+systemDate.get(Calendar.DAY_OF_MONTH));
					
					
					

					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					
					
					start.set(calUserDate.get(Calendar.YEAR),calUserDate.get(Calendar.MONTH), calUserDate.get(Calendar.DAY_OF_MONTH));
					end.set(systemDate.get(Calendar.YEAR),systemDate.get(Calendar.MONTH), systemDate.get(Calendar.DAY_OF_MONTH));
					
					Date givenDate = start.getTime();
					Date currentDate = end.getTime();
					
					long givenTime = givenDate.getTime();
					long currentTime = currentDate.getTime();
					
					if(givenTime<currentTime)
					{
						
						dateBeforeFound= true;
						
					}

					
				}
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			return dateBeforeFound;
		
	}
	
	public final static boolean dateAfterCurrent(String userDate)
	{
		
		 boolean dateAfterFound=false;
		
		
			try {
				
				if(userDate!=null)
				{
					
					Calendar calUserDate = Calendar.getInstance();
					calUserDate.setTime(Utility.getDate(userDate));
					
					System.out.println("calUserDate year "+calUserDate.get(Calendar.YEAR));
					System.out.println("calUserDate month "+calUserDate.get(Calendar.MONTH));
					System.out.println("calUserDate day "+calUserDate.get(Calendar.DAY_OF_MONTH));

					
					
					Calendar systemDate = Calendar.getInstance();
					systemDate.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
					
					
					System.out.println("year "+systemDate.get(Calendar.YEAR));
					System.out.println("month "+systemDate.get(Calendar.MONTH));
					System.out.println("day "+systemDate.get(Calendar.DAY_OF_MONTH));
					
					
					

					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					
					
					start.set(calUserDate.get(Calendar.YEAR),calUserDate.get(Calendar.MONTH), calUserDate.get(Calendar.DAY_OF_MONTH));
					end.set(systemDate.get(Calendar.YEAR),systemDate.get(Calendar.MONTH), systemDate.get(Calendar.DAY_OF_MONTH));
					
					Date givenDate = start.getTime();
					Date currentDate = end.getTime();
					
					long givenTime = givenDate.getTime();
					long currentTime = currentDate.getTime();
					
					if(givenTime>currentTime)
					{
						
						dateAfterFound= true;
						
					}

					
				}
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			return dateAfterFound;
		
	}*/


	

	public final static String trim(String strInput)
	{
		
		return (strInput==null?null:strInput.trim());
		
	}
	
	public final static BigDecimal trim(BigDecimal input)
	{
		/*DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);*/
		
		return (input==null?BigDecimal.ZERO:input);
		
	}
	public final static String replace(String input)
	{
		/*DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);*/
		
		return (input.replace("\"", ""));
		
	}
	public  final static String getTimeformat(int minutes) {
		
		
		 //String startTime = "00:00";
		 //int minutes = 190;
		 //int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
		 //int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
		 //String newtimeFormat = h+":"+m;
		 
		 String newtimeFormat=minutes/24/60 + ":" + minutes/60%24 + ':' + minutes%60;
		
		return newtimeFormat;
	}
	public  final static String getTATColor(int tat,int targetTAT) {
		
		return (tat>targetTAT ? "RED" : "GREEN");
	}
	

}
