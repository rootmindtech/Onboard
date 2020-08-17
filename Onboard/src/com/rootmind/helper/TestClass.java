
package com.rootmind.helper;



import java.util.Calendar;




public class TestClass {
	
	
	
	  
	
	
	    public final static int timeDifference(String strDate,String time)
		{
			
		
			int returnTime=0;
			
				try {
					
					if(strDate!=null)
					{	
						System.out.println("strDate "+ strDate);
						
						// SimpleDateFormat ddmmyyyyhhmm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						 //SimpleDateFormat yyyymmddhhmm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						// System.out.println(" after con "+ddmmyyyyhhmm.format(yyyymmddhhmm.parse(strDate)));
						
						 // DateFormat formatter;
					      //formatter = new SimpleDateFormat("dd/MM/yyyy");
					      //Date date = (Date) yyyymmddhhmm.parse(strDate);
					      
					      //System.out.println("date "+ date);
					      
					      //java.sql.Timestamp timeStampDate = new Timestamp(date);
						
					    java.sql.Timestamp tsstrDate = java.sql.Timestamp.valueOf(strDate);
						
						
					    System.out.println("tsstrDate "+ tsstrDate);
						

						
						Calendar calDOB = Calendar.getInstance();
						calDOB.setTime(tsstrDate);
						
					
						Calendar cal = Calendar.getInstance();
						cal.setTime(new java.sql.Timestamp(System.currentTimeMillis()));
						
					
						/*Calendar start = Calendar.getInstance();
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
						int age = Math.round(diffDays /365);*/
						

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
						
						
						
						/*if (time.equals("M"))
						{
							returnTime= Math.round(diffTime / (1000 * 60));
						}*/
						
						returnTime= Math.round(diffInMinute);
						
						System.out.println("returnTime is "+returnTime);
						
						
					}
				
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return returnTime;
			
		}
		
	
	
	 public static void main(String args[]){
		   
		 	//String strDate="2016-12-28 11:04:46.0";
		 	//String time="M";
		 	
		 	//timeDifference(strDate, time);
		 
			 //String startTime = "00:00";
			 int minutes = 0;
			 //int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
			 //int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
			// String newtime = h+":"+m;
			 String newtime=minutes/24/60 + ":" + minutes/60%24 + ':' + minutes%60;
			 System.out.println(" time "+minutes/24/60 + ":" + minutes/60%24 + ':' + minutes%60 ); ;
			 
			 System.out.println(" newtime "+newtime );
		 
		 	//System.out.println(" targetTAT " + (30/60));
		    
		  }//main() ends here
		

}
