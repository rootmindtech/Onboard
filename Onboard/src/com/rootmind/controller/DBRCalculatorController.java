package com.rootmind.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.LiabilityWrapper;

import com.rootmind.wrapper.AbstractWrapper;

public class DBRCalculatorController {
	
	
	public AbstractWrapper connectBRMS(LiabilityWrapper liabilityWrapper) throws Exception {
		

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		//PreparedStatement pstmt=null;
		
		
		String result = "";
		
		String emi = "";
		String creditLimit = "";
		String monthlyIncome = "";
		
		System.out.println("Calculate DBR connectBRMS" );
		
		
		try {
				
			
					JsonObject mainJsonObj = new JsonObject();
					JsonObject childJsonObj = new JsonObject();
					JsonObject objectJsonObj = new JsonObject();
					JsonObject insertJsonObj = new JsonObject();
					JsonObject rulesJsonObj = new JsonObject();
					
					if(liabilityWrapper.emi !=null && !liabilityWrapper.emi.equals(""))
					{
						emi=liabilityWrapper.emi;
					}
					else{
						
						emi="0";
					}
					
					if(liabilityWrapper.creditLimit !=null && !liabilityWrapper.creditLimit.equals(""))
					{
						creditLimit=liabilityWrapper.creditLimit;
					}
					else{
						
						creditLimit="0";
					}
					if(liabilityWrapper.monthlyIncome !=null && !liabilityWrapper.monthlyIncome.equals(""))
					{
						monthlyIncome=liabilityWrapper.monthlyIncome;
					}
					else{
						
						monthlyIncome="1";
					}
					
					
					JsonObject wrapperJsonObj = new JsonObject();
					wrapperJsonObj.addProperty("refNo", liabilityWrapper.refNo);
					wrapperJsonObj.addProperty("EMI", emi);
					wrapperJsonObj.addProperty("creditLimit", creditLimit);
					wrapperJsonObj.addProperty("monthlyIncome", monthlyIncome);
					wrapperJsonObj.addProperty("DBR", (Integer.parseInt(emi)+Integer.parseInt(creditLimit)*0.05)/Integer.parseInt(monthlyIncome));
				
					childJsonObj.add("org.drools.dbrprj.DBRDB",wrapperJsonObj);
					objectJsonObj.add("object",childJsonObj);
					
					objectJsonObj.addProperty("out-identifier","dbr");
					objectJsonObj.addProperty("return-object","true");
					
					insertJsonObj.add("insert",objectJsonObj);
					rulesJsonObj.addProperty("fire-all-rules","");
					
					
					JsonArray jsonArray = new JsonArray();
					
					jsonArray.add(insertJsonObj);
					jsonArray.add(rulesJsonObj);
					
					
					System.out.println("jsonArrayValues is = " + jsonArray);

					mainJsonObj.add("commands", jsonArray);
					
					
					
					
					String jsonFormattedString  = mainJsonObj.toString();
					String  urlParameters = jsonFormattedString.replaceAll("\\\\", "");
					
					System.out.println("urlParameters is = " + urlParameters);
		  
					String credentials="user2:password";
					String encoding = new String(Base64.encodeBase64(credentials.getBytes()));
					byte[] postData       = urlParameters.getBytes("UTF-8");
					int    postDataLength = postData.length;
					String request        = "http://192.168.1.110:8080/kie-server-6.4.0.Final-webc/services/rest/server/containers/instances/DBRDB";//gcmURL;						//"https://gcm-http.googleapis.com/gcm/send";
					URL    url            = new URL( request );
					HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
					conn.setDoOutput( true );
					conn.setInstanceFollowRedirects( false );
					conn.setRequestProperty("Authorization","Basic " + encoding);  //AIzaSyChya7IJ7KWKIlJbQIxv-apjxtcuStIBUg
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json"); 
					conn.setRequestProperty("charset", "UTF-8");
					conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
					conn.setUseCaches(false);
					// POST
					DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			        System.out.println("before write "+postData.toString());
			        writer.write(postData);
			        System.out.println("after write ");
			        writer.flush();
			        
			       
			        String line;
			        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	
			        while ((line = reader.readLine()) != null) {
			            result += line;
			        }
			       
			        writer.close();
			        reader.close();
			        liabilityWrapper.requestMessage=result;
			        System.out.println("result "+result);
			        
			        ///parse response data
			        Gson gson = new Gson();
			        //JsonParser parser = new JsonParser();
			        //JsonObject jsonObject = parser.parse(result).getAsJsonObject();
			        
			       

			        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
			        
			        String type = jsonObject.get("type").getAsString();
			        
			        System.out.println("type "+type);
		
			        if(type!=null && type.equals("SUCCESS"))
			        {
			        				    
			        	JsonObject resultJsonObject=jsonObject.getAsJsonObject("result");
			        
			        	System.out.println("result "+resultJsonObject.toString());
		
			        	JsonObject execJsonObject= resultJsonObject.getAsJsonObject("execution-results");
			        	
			        	System.out.println("execution-results "+execJsonObject.toString());
		
		
			        	JsonArray resultsJsonArray = execJsonObject.getAsJsonArray("results");

			        	for(int i=0;i<=resultsJsonArray.size()-1;i++)
			        	{
			        		JsonObject arrayJsonObject = resultsJsonArray.get(i).getAsJsonObject();

			        		
			        		System.out.println("arrayJsonObject "+arrayJsonObject.toString());

			        		//System.out.println("arrayJsonObject bool "+arrayJsonObject.getAsJsonPrimitive("key").toString().replaceAll("\"", ""));
			        		
			        		if(arrayJsonObject.getAsJsonPrimitive("key").toString().replaceAll("\"", "").equals("dbr"))
			        		{

			        			JsonObject valueJsonObject= arrayJsonObject.get("value").getAsJsonObject();
			        			
				        		System.out.println("valueJsonObject "+valueJsonObject.toString());
				        		
				        		JsonObject containerJsonObject= valueJsonObject.get("org.drools.dbrprj.DBRDB").getAsJsonObject();
				        			
				        		System.out.println("containerJsonObject "+containerJsonObject.toString());
				        		
				        		liabilityWrapper = gson.fromJson(containerJsonObject, LiabilityWrapper.class);
					        	
					        	//System.out.println("loanCalculatorWrapper.salary "+liabilityWrapper.salary);
					        	
					        	//System.out.println("loanCalculatorWrapper.approved "+liabilityWrapper.approved);
				
					        	liabilityWrapper.responseStatus=true;
				        		
			        		}
			        	}
			        }
			        else
			        {
			        	liabilityWrapper.responseStatus=false;
			        }
			        
			        liabilityWrapper.recordFound=true;
					dataArrayWrapper.liabilityWrapper=new LiabilityWrapper[1];
					dataArrayWrapper.liabilityWrapper[0]=liabilityWrapper;
					dataArrayWrapper.recordFound=true;
					
			    	
			        
			        
			        
			        
			        
			
				
			
		} 
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			/*try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}*/
		}

		return dataArrayWrapper;
	}

}
