package com.rootmind.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

import javax.naming.NamingException;
import javax.swing.plaf.basic.BasicToolBarUI.DockingListener;

import com.rootmind.wrapper.AbstractWrapper;


public class ImageDetailsHelper extends Helper{

	public AbstractWrapper uploadImageDetails(UsersWrapper userProfileWrapper,ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		String sql=null;
		//int fileLength;
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		PreparedStatement pstmt =null;
	
		//File file=new File(imageDetailsWrapper.imageFile);
		//FileInputStream fis = new FileInputStream(file);
        //fileLength = (int)file.length();
        //System.out.println("Image File Length "+fileLength);

		try {
			
				if(imageDetailsWrapper.imageUploadStatus==true)
				{
					con = getConnection();
	
					//sql="INSERT INTO RMT_CustomerImages(RefNo, CIFNumber, ImageId, ImageFile, UploadUserId, UploadDateTime) Values(?,?,?,?,?,?)";
	
					sql="INSERT INTO RMT_CustomerImages(RefNo, CIFNumber, ImageId, ImageFileName, ImageFileFolder, UploadUserId, UploadDateTime, ImageStatus,DocID,OCRFlag,ImageFileType) Values(?,?,?,?,?,?,?,?,?,?,?)";
	
	
					System.out.println("sql " + sql);
					System.out.println("Image File Name "+imageDetailsWrapper.imageFileName);
					pstmt = con.prepareStatement(sql);
	
					pstmt.setString(1,Utility.trim(imageDetailsWrapper.refNo));
					pstmt.setString(2,Utility.trim(imageDetailsWrapper.cifNumber));
					pstmt.setString(3,Utility.trim(imageDetailsWrapper.imageId));
					
					//pstmt.setBinaryStream(4, fis, fileLength); 
					pstmt.setString(4,Utility.trim(imageDetailsWrapper.imageFileName));
					
					pstmt.setString(5,Utility.trim(imageDetailsWrapper.imageFileFolder));
					pstmt.setString(6,Utility.trim(imageDetailsWrapper.uploadUserId));
					pstmt.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis())); //uploaddateTime
					pstmt.setString(8,Utility.trim(imageDetailsWrapper.imageStatus));
					pstmt.setString(9,Utility.trim(imageDetailsWrapper.docID));
					System.out.println("DOC ID "+imageDetailsWrapper.docID);
					pstmt.setString(10,Utility.trim(imageDetailsWrapper.ocrFlag));
					pstmt.setString(11,Utility.trim(imageDetailsWrapper.imageFileType));
					pstmt.executeUpdate();
					pstmt.close();
					
					
					///-------To update in RMT_DocCheckList ------					
					updateDocChecklist(imageDetailsWrapper);
					
					///-------
					
					if(imageDetailsWrapper.ocrFlag=="Y" || imageDetailsWrapper.ocrFlag.equals("Y"))
					{
						sql="INSERT INTO RMT_CustomerOCR(RefNo, CIFNumber, ImageId, ImageFileName, ImageFileFolder, UploadUserId, "
								+ "UploadDateTime, ImageStatus,DocID,OCR,OCROriginal,OCREditFlag) Values(?,?,?,?,?,?,?,?,?,?,?,?)";
						
						
						System.out.println("sql " + sql);
						System.out.println("Image File Name "+imageDetailsWrapper.imageFileName);
						pstmt = con.prepareStatement(sql);
		
						pstmt.setString(1,Utility.trim(imageDetailsWrapper.refNo));
						pstmt.setString(2,Utility.trim(imageDetailsWrapper.cifNumber));
						pstmt.setString(3,Utility.trim(imageDetailsWrapper.imageId));
						
						//pstmt.setBinaryStream(4, fis, fileLength); 
						pstmt.setString(4,Utility.trim(imageDetailsWrapper.imageFileName));
						
						pstmt.setString(5,Utility.trim(imageDetailsWrapper.imageFileFolder));
						pstmt.setString(6,Utility.trim(imageDetailsWrapper.uploadUserId));
						pstmt.setTimestamp(7,new java.sql.Timestamp(System.currentTimeMillis())); //uploaddateTime
						pstmt.setString(8,Utility.trim(imageDetailsWrapper.imageStatus));
						pstmt.setString(9,Utility.trim(imageDetailsWrapper.docID));
						System.out.println("DOC ID "+imageDetailsWrapper.docID);
						
						pstmt.setString(10,Utility.trim(imageDetailsWrapper.ocrData));
						System.out.println("ocrData "+imageDetailsWrapper.ocrData);
						pstmt.setString(11,Utility.trim(imageDetailsWrapper.ocrOriginal));
						pstmt.setString(12,Utility.trim(imageDetailsWrapper.ocrEditFlag));
						
						
						pstmt.executeUpdate();
						pstmt.close();
					}
	
					imageDetailsWrapper.recordFound=true;
				}
				
				
				dataArrayWrapper.imageDetailsWrapper=new ImageDetailsWrapper[1];
				dataArrayWrapper.imageDetailsWrapper[0]=imageDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
				
				System.out.println("Image uploaded successflly");
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return dataArrayWrapper;
	}
	
/*	public AbstractWrapper updateImageDetails(ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
	

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		//String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
	
		File file=new File(imageDetailsWrapper.imageFile);
		FileInputStream fis = new FileInputStream(file);
        int fileLength = (int)file.length();

		
		
		System.out.println("Update Image Details");
		
		
		try {
			con = getConnection("ONBOARD");
			
			PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_CustomerImages SET RefNo=?, CIFNumber=?, ImageId=?, ImageFile=?, ModifiedUserId=?, ModifiedDateTime=? where RefNo=?");
			
			pstmt.setString(1,Utility.trim(imageDetailsWrapper.refNo));
			pstmt.setString(2,Utility.trim(imageDetailsWrapper.cifNumber));
			pstmt.setString(3,Utility.trim(imageDetailsWrapper.imageId));
			pstmt.setBinaryStream(4, fis, fileLength);
			pstmt.setString(5,Utility.trim(imageDetailsWrapper.modifiedUserId));
			pstmt.setTimestamp(6,new java.sql.Timestamp(System.currentTimeMillis())); //modifiedDateTime
			pstmt.setString(7,Utility.trim(imageDetailsWrapper.refNo));
			
			pstmt.executeUpdate();

			pstmt.close();
			
			imageDetailsWrapper.recordFound=true;
			
			dataArrayWrapper.imageDetailsWrapper=new ImageDetailsWrapper[1];
			dataArrayWrapper.imageDetailsWrapper[0]=imageDetailsWrapper;
			
			System.out.println("Image details updated successfully" );
			
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return dataArrayWrapper;
	}
*/
	public AbstractWrapper fetchImageDetails(ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		//byte[] fileBytes;
		boolean imageFoundStatus=imageDetailsWrapper.imageFoundStatus;
		String ocrFlag=imageDetailsWrapper.ocrFlag;
	
		try {		
				ImageDetailsWrapper imageDetailsWrapperPath=fetchImagePath();	
				
				PopoverHelper popoverHelper = new PopoverHelper();
				
				con = getConnection();
				String sql="SELECT RefNo, CIFNumber, ImageId, ImageFile, ImageFileName, ImageFileFolder, "
						+ " UploadUserId, UploadDateTime,ImageStatus, "
						+ "ModifiedUserId, ModifiedDateTime, DocID, ImageFileType FROM RMT_CustomerImages ";
				
			
				if(imageDetailsWrapper.refNo!=null && !imageDetailsWrapper.refNo.equals(""))
				{
					sql=sql + " WHERE RefNo=? AND ImageId=?";
				}
				else
				{
					sql=sql + " WHERE CIFNumber=? AND ImageId=?";
				}
				
				
				if(ocrFlag.equals("Y"))
				{
					sql=sql+" AND OCRFlag='Y'";
				}
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				System.out.println("Image Details RefNo is" + imageDetailsWrapper.refNo);
				
				if(imageDetailsWrapper.refNo!=null && !imageDetailsWrapper.refNo.equals(""))
				{
					pstmt.setString(1,imageDetailsWrapper.refNo.trim());
				}
				else
				{
					pstmt.setString(1,imageDetailsWrapper.cifNumber.trim());
				}
				
				pstmt.setString(2, imageDetailsWrapper.imageId.trim());	
				
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) 
				{
					
					imageDetailsWrapper= new ImageDetailsWrapper();
					
					
					imageDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + imageDetailsWrapper.refNo);
					
	
					imageDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
					System.out.println("cifNumber " + imageDetailsWrapper.cifNumber);
					
					imageDetailsWrapper.imageId=Utility.trim(resultSet.getString("ImageId"));
					
					//fileBytes = resultSet.getBytes("ImageFile");
                    //OutputStream targetFile=  new FileOutputStream("D://RetriveImages//NewImageFromServer.JPG");

                    //targetFile.write(fileBytes);
                    //targetFile.close();

					//imageDetailsWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					
					imageDetailsWrapper.imageFileName=Utility.trim(resultSet.getString("ImageFileName"));
					imageDetailsWrapper.imageFileFolder=imageDetailsWrapperPath.imagesPath+Utility.trim(resultSet.getString("ImageFileFolder"));
					imageDetailsWrapper.uploadUserId=Utility.trim(resultSet.getString("UploadUserId"));
					
					imageDetailsWrapper.uploadDateTime=Utility.trim(resultSet.getString("UploadDateTime"));
					imageDetailsWrapper.imageStatus=Utility.trim(resultSet.getString("ImageStatus"));
					
					imageDetailsWrapper.modifiedUserId=Utility.trim(resultSet.getString("ModifiedUserId"));
					
					imageDetailsWrapper.modifiedDateTime=Utility.trim(resultSet.getString("ModifiedDateTime"));	
					imageDetailsWrapper.docID=Utility.trim(resultSet.getString("DocID"));	
					imageDetailsWrapper.imageFileType=Utility.trim(resultSet.getString("ImageFileType"));
					imageDetailsWrapper.imageFoundStatus=imageFoundStatus;
					
				
					imageDetailsWrapper.docIDValue=popoverHelper.fetchPopoverDesc(imageDetailsWrapper.docID,"RMT_DocChecklistMaster");
					
					System.out.println("Fetch Image Details:docID ImageID "+imageDetailsWrapper.docID);
					System.out.println("Fetch Image Details:docIDValue ImageValue"+imageDetailsWrapper.docIDValue);
					
					if(ocrFlag.equals("Y"))
					{
						if(resultSet !=null) resultSet.close();
						if(pstmt !=null) pstmt.close();
						
						sql="SELECT RefNo, CIFNumber, ImageId, ImageFile, ImageFileName, ImageFileFolder, "
								+ " OCR,OCROriginal,OCREditFlag FROM RMT_CustomerOCR WHERE RefNo=? AND ImageId=?";
						
					
						pstmt = con.prepareStatement(sql);
						
						System.out.println("Image Details RefNo is" + imageDetailsWrapper.refNo);
						
						pstmt.setString(1,imageDetailsWrapper.refNo.trim());
						pstmt.setString(2, imageDetailsWrapper.imageId.trim());	
						
						resultSet = pstmt.executeQuery();
						if (resultSet.next()) 
						{
							
							
							
							imageDetailsWrapper.ocrData=Utility.trim(resultSet.getString("OCR"));
							System.out.println("ocrData" + imageDetailsWrapper.ocrData);
							
							imageDetailsWrapper.ocrOriginal=Utility.trim(resultSet.getString("OCROriginal"));
							imageDetailsWrapper.ocrEditFlag=Utility.trim(resultSet.getString("OCREditFlag"));
							
			
						}
						if(resultSet !=null) resultSet.close();
						if(pstmt !=null) pstmt.close();
					}	
					
					imageDetailsWrapper.recordFound=true;
				
					vector.addElement(imageDetailsWrapper);

			}
			
			/*if (vector.size()>0)
			{
				dataArrayWrapper.imageDetailsWrapper = new ImageDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.imageDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());
				
				System.out.println("Fetch Image Details Successful");

			}
			else
			{
				dataArrayWrapper.imageDetailsWrapper = new ImageDetailsWrapper[1];
				dataArrayWrapper.imageDetailsWrapper[0]=imageDetailsWrapper;
				dataArrayWrapper.recordFound=true;
			}*/
 
			if(resultSet !=null) resultSet.close();
			if(pstmt !=null) pstmt.close();

			
			
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return imageDetailsWrapper;
	}
	
	public AbstractWrapper fetchImageFileNames(ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

		//byte[] fileBytes;
		String ocrFlag=imageDetailsWrapper.ocrFlag;
		
	
		try {		
				//PopoverHelper popoverHelper = new PopoverHelper();
				
				con = getConnection();
				
				String sql="SELECT RefNo, CIFNumber, ImageId, ImageFile, ImageFileName, ImageFileFolder, UploadUserId, UploadDateTime, "
						+"ImageStatus,ModifiedUserId, ModifiedDateTime, ImageFileType, a.DocID as DocID, b.Description as DocName, b.Filter as AccountType "  
						+"FROM RMT_CustomerImages a LEFT JOIN RMT_DocChecklistMaster b ON a.DOCID=b.Code";
				
			
				
				if(imageDetailsWrapper.refNo!=null)
				{
					sql=sql + " WHERE RefNo=? and ImageStatus='ACTIVE' and LEFT(RefNo,2)=Filter";
				}
				else
				{
					sql=sql + " WHERE CIFNumber=? and ImageStatus='ACTIVE' and LEFT(RefNo,2)=Filter" ;
				}
				if(ocrFlag.equals("Y"))
				{
					sql=sql+" AND OCRFlag='Y'";
				}
				
				sql =sql + " ORDER BY DocID";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				System.out.println("Image Details RefNo is" + imageDetailsWrapper.refNo);
				
				if(imageDetailsWrapper.refNo!=null)
				{
					pstmt.setString(1,imageDetailsWrapper.refNo.trim());
				}
				else
				{
					pstmt.setString(1,imageDetailsWrapper.cifNumber.trim());
				}
					
				
				resultSet = pstmt.executeQuery();
				
				
				while(resultSet.next()) 
				{
					
					imageDetailsWrapper= new ImageDetailsWrapper();
					
					imageDetailsWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println("RefNo" + imageDetailsWrapper.refNo);
					
	
					imageDetailsWrapper.cifNumber=Utility.trim(resultSet.getString("CIFNumber"));
					System.out.println("cifNumber " + imageDetailsWrapper.cifNumber);
					
					imageDetailsWrapper.imageId=Utility.trim(resultSet.getString("ImageId"));
					
					//fileBytes = resultSet.getBytes("ImageFile");
                    //OutputStream targetFile=  new FileOutputStream("D://RetriveImages//NewImageFromServer.JPG");

                    //targetFile.write(fileBytes);
                    //targetFile.close();

					//imageDetailsWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					
					imageDetailsWrapper.imageFileName=Utility.trim(resultSet.getString("ImageFileName"));
					
					imageDetailsWrapper.imageFileFolder=Utility.trim(resultSet.getString("ImageFileFolder"));
					
					imageDetailsWrapper.uploadUserId=Utility.trim(resultSet.getString("UploadUserId"));
					
					imageDetailsWrapper.uploadDateTime=Utility.trim(resultSet.getString("UploadDateTime"));
					
					imageDetailsWrapper.imageStatus=Utility.trim(resultSet.getString("ImageStatus"));
					
					imageDetailsWrapper.modifiedUserId=Utility.trim(resultSet.getString("ModifiedUserId"));
					
					imageDetailsWrapper.modifiedDateTime=Utility.trim(resultSet.getString("ModifiedDateTime"));	
					imageDetailsWrapper.imageFileType=Utility.trim(resultSet.getString("ImageFileType"));
					imageDetailsWrapper.docID=Utility.trim(resultSet.getString("DocID"));	
					
					imageDetailsWrapper.docName=Utility.trim(resultSet.getString("DocName"));
					
					System.out.println("Doc Name " + imageDetailsWrapper.docName);
					
					//imageDetailsWrapper.docIDValue=popoverHelper.fetchPopoverDesc(imageDetailsWrapper.docID,"IMAGEDOC");
					
					//System.out.println("Fetch Image FileNames:docID "+imageDetailsWrapper.docID);
					//System.out.println("Fetch Image FileNames:docIDValue "+imageDetailsWrapper.docIDValue);
					
					imageDetailsWrapper.imageFoundStatus=true;
					imageDetailsWrapper.recordFound=true;
			
					vector.addElement(imageDetailsWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.imageDetailsWrapper = new ImageDetailsWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.imageDetailsWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			else
			{
				dataArrayWrapper.imageDetailsWrapper = new ImageDetailsWrapper[1];
				dataArrayWrapper.imageDetailsWrapper[0]= imageDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
			}
 
			if(resultSet !=null) resultSet.close();
			pstmt.close();

			System.out.println("Fetch Image FileNames Successful");
			
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return dataArrayWrapper;
	}
	
	
	public AbstractWrapper updateImageStatus(ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
	

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		//String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
	
		/*File file=new File(imageDetailsWrapper.imageFile);
		FileInputStream fis = new FileInputStream(file);
        int fileLength = (int)file.length();*/

		
		
		System.out.println("Update Image Status");
		
		
		try {
				con = getConnection();
				
				PreparedStatement pstmt = con.prepareStatement("UPDATE RMT_CustomerImages SET ImageStatus=? WHERE RefNo=? and ImageId=?");
				
				pstmt.setString(1,Utility.trim(imageDetailsWrapper.imageStatus));
				pstmt.setString(2,Utility.trim(imageDetailsWrapper.refNo));
				pstmt.setString(3,Utility.trim(imageDetailsWrapper.imageId));
				
				pstmt.executeUpdate();
	
				pstmt.close();
				
				///-------To update in RMT_DocCheckList ------
				
				updateDocChecklist(imageDetailsWrapper);
				
				///-------
				
				imageDetailsWrapper.recordFound=true;
				
				dataArrayWrapper.imageDetailsWrapper=new ImageDetailsWrapper[1];
				dataArrayWrapper.imageDetailsWrapper[0]=imageDetailsWrapper;
				dataArrayWrapper.recordFound=true;
				
				System.out.println("Image Status updated successfully" );
				
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return dataArrayWrapper;
	}
	
	
	public AbstractWrapper updateDocChecklist(ImageDetailsWrapper imageDetailsWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
	

		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		//String sql=null;
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
	
		/*File file=new File(imageDetailsWrapper.imageFile);
		FileInputStream fis = new FileInputStream(file);
        int fileLength = (int)file.length();*/

		
		
		System.out.println("Update Image Status");
		
		
		try {
			
			
				//---This is to verify whther records existing into the RMT_DocChecklist or not, if not avialable records will be inserted---
				DocChecklistHelper docChecklistHelper=new DocChecklistHelper();
				DocChecklistWrapper docChecklistWrapper= new DocChecklistWrapper();
				docChecklistWrapper.refNo=imageDetailsWrapper.refNo;
				docChecklistWrapper.accountType=imageDetailsWrapper.accountType;
				
				
	        	System.out.println("DocChecklist Helper ");
	       
	        	docChecklistHelper.fetchDocChecklist(docChecklistWrapper);
	        	
	        	
	        	//------
	        	
	        	//------------To update status in RMT_DocCheckList table from Images----
	        	DocChecklistWrapper[] docChecklistWrapperArray=new DocChecklistWrapper[1];
	        	
	        	docChecklistWrapper.docID=imageDetailsWrapper.docID;
	        	if(imageDetailsWrapper.imageStatus.trim().equals("ACTIVE"))
	        	{
	        		docChecklistWrapper.uploaded="Y";
	        	}
	        	else
	        	{
	        		docChecklistWrapper.uploaded="N";
	        	}
	        	
	        	docChecklistWrapperArray[0]=docChecklistWrapper;
	        	dataArrayWrapper = (DataArrayWrapper)docChecklistHelper.updateDocChecklist(docChecklistWrapperArray);
			
				
				
				System.out.println("DocCheckList Status updated successfully from Image Upload" );
				
			
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException(ne.getMessage());
		}
		 catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				releaseConnection(resultSet, con);
			} 
			catch (SQLException se)
			{
				se.printStackTrace();
				throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
			}
		}

		return dataArrayWrapper;
	}
	//---------------------- Start fetchImagePath-----------------
		public ImageDetailsWrapper fetchImagePath()throws Exception {

				Connection con = null;
				ResultSet resultSet = null;
				
				
				ImageDetailsWrapper imageDetailsWrapper=null;
				
				
				try {
						con=getConnection();
						
						
						PreparedStatement pstmt = con.prepareStatement("SELECT ImagesPath,ImagesDPI from RMT_Parameter");
					
						resultSet = pstmt.executeQuery();
						if (resultSet.next()) 
						{
							imageDetailsWrapper=new ImageDetailsWrapper();							
							imageDetailsWrapper.imagesPath=resultSet.getString("ImagesPath");
							imageDetailsWrapper.imagesDPI=resultSet.getInt("ImagesDPI");
							
							System.out.println("Images Path "+ imageDetailsWrapper.imagesPath);
							
						}
						
						resultSet.close();
						pstmt.close();
						
						//----------

					
				} catch (SQLException se) {
					se.printStackTrace();
					throw new SQLException(se.getSQLState()+ " ; "+ se.getMessage());
				} catch (NamingException ne) {
					ne.printStackTrace();
					throw new NamingException(ne.getMessage());
				}
				 catch (Exception ex) {
					ex.printStackTrace();
					throw new Exception(ex.getMessage());
				}
				finally
				{
					try
					{
						releaseConnection(resultSet, con);
					} 
					catch (SQLException se)
					{
						se.printStackTrace();
						throw new Exception(se.getSQLState()+ " ; "+ se.getMessage());
					}
				}
		
				return imageDetailsWrapper;
		}
		//---------------------- End fetchImagePath-----------------
	
	
}
