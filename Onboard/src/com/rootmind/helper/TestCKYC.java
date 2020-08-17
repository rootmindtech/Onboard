package com.rootmind.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.rootmind.wrapper.CKYCAddressWrapper;
import com.rootmind.wrapper.CKYCCustomerWrapper;

public class TestCKYC {
	

	 public static void main(String args[])throws IOException{
		 
		 DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		 Vector<Object> vector = new Vector<Object>();
		    String filePath="C://Users//ROOTMIND//Desktop//";
		 	String fiCode="IN0467";
		 	String rg="RG";
		    Date date = new Date();
		    SimpleDateFormat ddmmyyyhhmmss = new SimpleDateFormat("ddMMyyyyHHmmss");
		    SimpleDateFormat ddmmyyyy = new SimpleDateFormat("DD-MM-YYYY");
		    String currentDateStamp=ddmmyyyhhmmss.format(date);
		    String userid="IA000595";
		    String batchNo="00005";
		    int recordsCount=0;
		    String todayDate=ddmmyyyy.format(date);
		    String versionNo="v1.1";
		    String lineNumber="01";
		    String branchCode="BR";
		    String fatherSpouseFlag="Y";
		    String corrAddressType="05";
		    
		    String fileData="";
		  
		   //String record1[] = {"Mr.","Rama Rao","Ms."};
		    //String record2[]={"Ram Rao", ""};
		    
		    String record1[]={"KY08MAR2017188","DR","First Name","Middle Name","last Name","maidenNamePrefix","maidenFirstName","maidenMiddleName","maidenLastName","fatherSpousePrefix","fatherSpouseFirstName","fatherSpouseMiddleName","fatherSpouseLastName","motherPrefix","motherFirstName","motherMiddleName","motherLastName","15/08/2017","gender","maritalStatus","citizenship","residenceStatus","occupationType","residenceTaxPurpose","makerID","makerDateTime","queueNoRecords","genderValue","namePrefixValue"};
		    String record2[]={"KY08MAR2017189","DR2","First Name2","Middle Name2","last Name2","maidenNamePrefix2","maidenFirstName","maidenMiddleName","maidenLastName","fatherSpousePrefix","fatherSpouseFirstName","fatherSpouseMiddleName","fatherSpouseLastName","motherPrefix","motherFirstName","motherMiddleName","motherLastName","15/08/2017","gender","maritalStatus","citizenship","residenceStatus","occupationType","residenceTaxPurpose","makerID","makerDateTime","queueNoRecords","genderValue","namePrefixValue"};
		    
		    String addressRecord[]={"KY28MAR2017207","addressType","addressProof","addressProofNo","Address Line-1","Address Line-2","Address Line-3","Palakol","currentDistrict","534260","AP","sameAddressFlag","currentISO3166CountryCode","corrAddressLine1","corrAddressLine2","corrAddressLine3","corrCity","corrDistrict","corrPIN","corrStateUTCode","corrISO3166CountryCode","addressTypeValue","addressProofValue","currentStateUTCodeValue","currentDistrictValue"};
		    
		    CKYCCustomerWrapper ckycCustomerWrapper1 = new CKYCCustomerWrapper();
		    
		    for (int i=0; i<=record1.length-1;i++)
		    {
		    	ckycCustomerWrapper1.refNo=record1[0];
		    	ckycCustomerWrapper1.namePrefix=record1[1];
		    }
		   
		    
		    CKYCCustomerWrapper ckycCustomerWrapper2 = new CKYCCustomerWrapper();
		    
		    for (int i=0; i<=record1.length-1;i++)
		    {
		    	ckycCustomerWrapper2.refNo=record2[0];
		    	ckycCustomerWrapper2.namePrefix=record2[1];
		    	
		    }
		    
		    vector.addElement(ckycCustomerWrapper1);
		    vector.addElement(ckycCustomerWrapper2);
		    dataArrayWrapper.ckycCustomerWrapper = new CKYCCustomerWrapper[2];
			vector.copyInto(dataArrayWrapper.ckycCustomerWrapper);
			
			
			CKYCAddressWrapper ckycAddressWrapper = new CKYCAddressWrapper();
			
			for (int i=0; i<=addressRecord.length-1;i++)
		    {
				ckycAddressWrapper.refNo=addressRecord[0];
				ckycAddressWrapper.addressType=addressRecord[1];
				
		    }
			dataArrayWrapper.ckycAddressWrapper = new CKYCAddressWrapper[1];
			dataArrayWrapper.ckycAddressWrapper[0]=ckycAddressWrapper;
			
		    
		 	try{
			 		System.out.println("time stamp "+ddmmyyyhhmmss.format(date));
				    PrintWriter writer = new PrintWriter(filePath+fiCode+"_"+rg+"_"+currentDateStamp+"_"+userid+"_U"+batchNo+".txt", "UTF-8");
				    //writer.println("The first line");
				    //writer.println("The second line");
				    /*while(rs.next()){
				    	
				    			rs.getString("Title");
					    	}
					    }
				 	*/
				    String header="10|"+batchNo+"|"+fiCode+"|"+rg+"|"+recordsCount+"|"+todayDate+"|"+versionNo+"|||||";
				    writer.println(header);
				    
				 	for (int i=0;i<=dataArrayWrapper.ckycCustomerWrapper.length-1;i++)
				 	{
				 		fileData="20|"+lineNumber+"|"+dataArrayWrapper.ckycCustomerWrapper[i].applicationType+"|"+branchCode+"|||||||||||"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].constitutionType+"|||"+dataArrayWrapper.ckycCustomerWrapper[i].accountType+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].refNo+"|"+dataArrayWrapper.ckycCustomerWrapper[i].namePrefixValue+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].firstName+"|"+dataArrayWrapper.ckycCustomerWrapper[i].middleName+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].lastName+"|||||||"+fatherSpouseFlag+"|"+dataArrayWrapper.ckycCustomerWrapper[i].fatherSpousePrefix+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].fatherSpouseFirstName+"|"+dataArrayWrapper.ckycCustomerWrapper[i].fatherSpouseMiddleName+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].fatherSpouseLastName+"|"+dataArrayWrapper.ckycCustomerWrapper[i].motherPrefix+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].motherFirstName+"|"+dataArrayWrapper.ckycCustomerWrapper[i].motherMiddleName+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].motherLastName+"||"+dataArrayWrapper.ckycCustomerWrapper[i].gender+"|"+dataArrayWrapper.ckycCustomerWrapper[i].maritalStatus+"|"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].citizenship+"|"+dataArrayWrapper.ckycCustomerWrapper[i].occupationType+"|"+dataArrayWrapper.ckycCustomerWrapper[i].dob+"|||||||||"
				 					+dataArrayWrapper.ckycCustomerWrapper[i].residenceStatus+"|"+dataArrayWrapper.ckycCustomerWrapper[i].residenceTaxPurpose+"|||||";
				 			//record=record+dataArrayWrapper.ckycCustomerWrapper[i].refNo;
				 			//cyckwrapper[i].title;
				 	
					 			writer.print(fileData);
					 			fileData=dataArrayWrapper.ckycAddressWrapper[0].addressType+"|"+dataArrayWrapper.ckycAddressWrapper[0].currentAddressLine1+"|||"
					 					+dataArrayWrapper.ckycAddressWrapper[0].currentCity+"|"+dataArrayWrapper.ckycAddressWrapper[0].currentDistrict+"|"+dataArrayWrapper.ckycAddressWrapper[0].currentStateUTCode+"|"
					 					+dataArrayWrapper.ckycAddressWrapper[0].currentISO3166CountryCode+"|"+dataArrayWrapper.ckycAddressWrapper[0].currentPIN+"|"+dataArrayWrapper.ckycAddressWrapper[0].addressProof+"||"
					 					+dataArrayWrapper.ckycAddressWrapper[0].sameAddressFlag+"|||||||||||||||||||||||||||||||";
							 	writer.println(fileData);
							 	
							 	fileData=dataArrayWrapper.ckycCustomerWrapper[i].appDeclDate+"|"+dataArrayWrapper.ckycCustomerWrapper[i].appDeclPlace+"|"+dataArrayWrapper.ckycCustomerWrapper[i].kycVerifiedDate+"|"
							 			+dataArrayWrapper.ckycCustomerWrapper[i].docsReceived+"|"+dataArrayWrapper.ckycCustomerWrapper[i].kycVerifiedEmpName+"|"+dataArrayWrapper.ckycCustomerWrapper[i].kycVerifiedEmpDesignation+"|"
							 			+dataArrayWrapper.ckycCustomerWrapper[i].kycVerifiedEmpBranch+"|"+dataArrayWrapper.ckycCustomerWrapper[i].kycVerifiedEmpCode+"|"+dataArrayWrapper.ckycCustomerWrapper[i].instDetails+"|"
							 			+dataArrayWrapper.ckycCustomerWrapper[i].instCode;
							 	
							 	
				    }
				 	
				 	
				 	
				    
				    writer.close();
			} catch (IOException e) {
			   // do something
			}
		    
	 }//main() ends here
		

}
