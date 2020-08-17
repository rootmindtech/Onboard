package com.rootmind.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.naming.NamingException;

import org.apache.commons.codec.binary.Base64;

import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.LoanCalculatorWrapper;
import com.rootmind.helper.Utility;
import com.rootmind.wrapper.AbstractWrapper;

public class LoanCalculatorController {
	
	public AbstractWrapper connectBRMS(LoanCalculatorWrapper loanCalculatorWrapper)throws Exception {
			
			//Connection con = null;
			//ResultSet resultSet = null;
	
			DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
			//String sql=null;
			
			
			//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			formatter.applyPattern("###,###,###,##0.00");
			formatter.setDecimalFormatSymbols(symbols);
			//PreparedStatement pstmt=null;
			
			//String gcmKey=null;
			//String gcmActivate=null;
			//String gcmURL=null;
			String result = "";
			
			try {
					//con = getConnection();
					
					//-----GCMKey GCMActivate code--
					
					/*sql="SELECT GCMKey,GCMActivate,GCMURL from MST_Parameter";
					
					pstmt = con.prepareStatement(sql);
				
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) 
					{
							
						gcmKey=Utility.trim(resultSet.getString("GCMKey"));
						gcmActivate=Utility.trim(resultSet.getString("GCMActivate"));
						gcmURL=Utility.trim(resultSet.getString("GCMURL"));
					}
					
					resultSet.close();
					pstmt.close();*/
					
					//----------
					/*if(gcmActivate !=null && gcmActivate.equals("Y"))
					{*/
				
				
				/*{
					  "commands" : [ 
					    { "insert" : { "out-identifier":"loan",
					             "return-object":"true","object" : {
					                  "org.drools.miniloan.Loan":{
					                     "amount":"100",
					                     "duration":"20",
					                     "interestRate":"5"
					                  }
					             }   } }, 
					  
					    { "fire-all-rules" : { } }
					  ]
					}*/
				
						JsonObject mainJsonObj = new JsonObject();
						JsonObject childJsonObj = new JsonObject();
						JsonObject objectJsonObj = new JsonObject();
						JsonObject insertJsonObj = new JsonObject();
						JsonObject rulesJsonObj = new JsonObject();
						
						JsonObject wrapperJsonObj = new JsonObject();
						wrapperJsonObj.addProperty("dateOfBirth", Utility.setDate(loanCalculatorWrapper.dateOfBirth,"yyyy-mm-dd"));
						wrapperJsonObj.addProperty("nationality", loanCalculatorWrapper.nationality);
						wrapperJsonObj.addProperty("employer", loanCalculatorWrapper.employer);
						wrapperJsonObj.addProperty("designation", loanCalculatorWrapper.designation);
						wrapperJsonObj.addProperty("residenceStatus", loanCalculatorWrapper.residenceStatus);
						wrapperJsonObj.addProperty("salaryTransfer", loanCalculatorWrapper.salaryTransfer);
						wrapperJsonObj.addProperty("salary", loanCalculatorWrapper.salary);
						wrapperJsonObj.addProperty("loanOutstanding", loanCalculatorWrapper.loanOutstanding);
						wrapperJsonObj.addProperty("creditCardLimit", loanCalculatorWrapper.creditCardLimit);
						wrapperJsonObj.addProperty("amountRequest", loanCalculatorWrapper.amountRequest);
						
						
						childJsonObj.add("org.drools.miniloan.Loan",wrapperJsonObj);
						objectJsonObj.add("object",childJsonObj);
						
						objectJsonObj.addProperty("out-identifier","loan");
						objectJsonObj.addProperty("return-object","true");
						
						insertJsonObj.add("insert",objectJsonObj);
						rulesJsonObj.addProperty("fire-all-rules","");
						
						
						JsonArray jsonArray = new JsonArray();
						
						jsonArray.add(insertJsonObj);
						jsonArray.add(rulesJsonObj);
						
						
						System.out.println("jsonArrayValues is = " + jsonArray);

						mainJsonObj.add("commands", jsonArray);
						
						/*{
							  "commands" : [ 
							    { "insert" : { "out-identifier":"loan",
							             "return-object":"true","object" : {
							                  "org.drools.miniloan.Loan":{
							                     "amount":"100",
							                     "duration":"20",
							                     "interestRate":"5"
							                  }
							             }   } }, 
							  
							    { "fire-all-rules" : { } }
							  ]
							}*/
						
						
						String jsonFormattedString  = mainJsonObj.toString();
						String  urlParameters = jsonFormattedString.replaceAll("\\\\", "");
						
						System.out.println("urlParameters is = " + urlParameters);
			  
						String credentials="user2:password";
						String encoding = new String(Base64.encodeBase64(credentials.getBytes()));
						byte[] postData       = urlParameters.getBytes("UTF-8");
						int    postDataLength = postData.length;
						String request        = "http://192.168.1.110:8080/kie-server-6.4.0.Final-webc/services/rest/server/containers/instances/MiniLoan";//gcmURL;						//"https://gcm-http.googleapis.com/gcm/send";
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
				        loanCalculatorWrapper.requestMessage=result;
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
				        		
				        		if(arrayJsonObject.getAsJsonPrimitive("key").toString().replaceAll("\"", "").equals("loan"))
				        		{

				        			JsonObject valueJsonObject= arrayJsonObject.get("value").getAsJsonObject();
				        			
					        		System.out.println("valueJsonObject "+valueJsonObject.toString());
					        		
					        		JsonObject containerJsonObject= valueJsonObject.get("org.drools.miniloan.Loan").getAsJsonObject();
					        			
					        		System.out.println("containerJsonObject "+containerJsonObject.toString());
					        		
						        	loanCalculatorWrapper = gson.fromJson(containerJsonObject, LoanCalculatorWrapper.class);
						        	
						        	System.out.println("loanCalculatorWrapper.salary "+loanCalculatorWrapper.salary);
						        	
						        	System.out.println("loanCalculatorWrapper.approved "+loanCalculatorWrapper.approved);
					
						        	loanCalculatorWrapper.responseStatus=true;
					        		
				        		}
				        	}
				        }
				        else
				        {
				        	loanCalculatorWrapper.responseStatus=false;
				        }
				        
				        loanCalculatorWrapper.recordFound=true;
						dataArrayWrapper.loanCalculatorWrapper=new LoanCalculatorWrapper[1];
						dataArrayWrapper.loanCalculatorWrapper[0]=loanCalculatorWrapper;
						dataArrayWrapper.recordFound=true;
						
				    	/*{
				    	  "type" : "SUCCESS",
				    	  "msg" : "Container MiniLoan successfully called.",
				    	  "result" : {
				    	    			"execution-results" : {
				    	      				"results" : [ {
				    	        				"key" : "loan",
				    	        				"value" : {
				    	        					"org.drools.miniloan.Loan":{
				    	  								"amount" : 0,
				    	  								"duration" : "20",
				    	  								"interestRate" : 5,
				    	  								"approved" : true,
				    	  								"salary" : 10000
				    								}
				    							}
				    	      				} ],
				    	      				
				    	      				"facts" : [ {
				    	        				"key" : "loan",
				    	        				"value" : {
				    	        					"org.drools.core.common.DefaultFactHandle":{
				    	  								"external-form" : "0:8:1204693240:1204693240:11:DEFAULT:NON_TRAIT:org.drools.miniloan.Loan"
				    									}
				    								}
				    	      				} ]
				    	    }
				    	  }
				    	}*/
				    
					       /*
					        {
					  		  "type" : "FAILURE",
					  		  "msg" : "Container MiniLoan is not instantiated.",
					  		  "result" : null
					  		}
					  	
					        */

				        
				        
				        
				        
				        
						
					//}
	
					
				
			} /*catch (SQLException se) {
				se.printStackTrace();
				throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
			} catch (NamingException ne) {
				ne.printStackTrace();
				throw new NamingException(ne.getMessage());
			}*/
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
