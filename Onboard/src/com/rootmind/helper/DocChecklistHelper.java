package com.rootmind.helper;

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

import com.rootmind.wrapper.AbstractWrapper;

public class DocChecklistHelper extends Helper{
	
	
	
	public AbstractWrapper fetchDocChecklist(DocChecklistWrapper docChecklistWrapper)throws Exception {

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

	
		try {
			
				//PopoverHelper popoverHelper = new PopoverHelper();
			
			
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT RefNo,SeqNo,AccountType,DocID,DocName,Mandatory,Uploaded FROM RMT_DocChecklist WHERE RefNo=?");
				
				pstmt.setString(1,docChecklistWrapper.refNo);
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) 
				{
					
				
					docChecklistWrapper= new DocChecklistWrapper();
					
					docChecklistWrapper.refNo=Utility.trim(resultSet.getString("RefNo"));
					System.out.println(" docChecklistWrapper RefNo" + docChecklistWrapper.refNo);
					docChecklistWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
					docChecklistWrapper.accountType=Utility.trim(resultSet.getString("AccountType"));
					System.out.println("AccountType " + docChecklistWrapper.accountType);
					docChecklistWrapper.docID=Utility.trim(resultSet.getString("DocID"));
					docChecklistWrapper.docName=Utility.trim(resultSet.getString("DocName"));
					docChecklistWrapper.mandatory=Utility.trim(resultSet.getString("Mandatory"));
					docChecklistWrapper.uploaded=Utility.trim(resultSet.getString("Uploaded"));

					
					//docChecklistWrapper.extRelationshipValue=popoverHelper.fetchPopoverDesc(docChecklistWrapper.extRelationship,"EXISTINGRELATIONSHIP");
			
					
					docChecklistWrapper.recordFound=true;
				
					vector.addElement(docChecklistWrapper);
	
				}
				
				if (vector.size()>0)
				{
					dataArrayWrapper.docChecklistWrapper = new DocChecklistWrapper[vector.size()];
					vector.copyInto(dataArrayWrapper.docChecklistWrapper);
					dataArrayWrapper.recordFound=true;
	
					System.out.println("total trn. in fetch " + vector.size());
	
				}
				else
				{
					
					dataArrayWrapper=(DataArrayWrapper)insertDocChecklist(docChecklistWrapper);

				}
	 
				if(resultSet !=null) resultSet.close();
				pstmt.close();
				System.out.println("fetch Document Checklist Successful " );
				
				
		

			
			
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
	
	public AbstractWrapper insertDocChecklist(DocChecklistWrapper docChecklistWrapper)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		String accountType=null;
		String refNo=null;
		
		DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		Vector<Object> vector = new Vector<Object>();

	
		try {
			
			//---------SELECT RMT_DOCCHECKLISTMASTER
			
			accountType=docChecklistWrapper.accountType;
			refNo=docChecklistWrapper.refNo;
			
			con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT SeqNo,Filter,Code,Description,Mandatory FROM RMT_DocChecklistMaster WHERE Filter=?");
			
			
			System.out.println("DOCCHECKLISTMASTER Account Type " + accountType);
			
			pstmt.setString(1,accountType.trim());
	
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) 
			{
				
			
				docChecklistWrapper= new DocChecklistWrapper();
				docChecklistWrapper.refNo=refNo;
				docChecklistWrapper.seqNo=Utility.trim(resultSet.getString("SeqNo"));
				docChecklistWrapper.accountType=Utility.trim(resultSet.getString("Filter"));
				System.out.println("AccountType" + docChecklistWrapper.accountType);
				docChecklistWrapper.docID=Utility.trim(resultSet.getString("Code"));
				docChecklistWrapper.docName=Utility.trim(resultSet.getString("Description"));
				docChecklistWrapper.mandatory=Utility.trim(resultSet.getString("Mandatory"));
				docChecklistWrapper.uploaded="N";
				
				
				//--INSERT RMT_DOCCHECKLIST
				PreparedStatement pstmtInsert = con.prepareStatement("INSERT INTO RMT_DocChecklist(RefNo,SeqNo,AccountType,DocID,DocName,Mandatory,Uploaded) VALUES(?,?,?,?,?,?,?)");
				
				pstmtInsert.setString(1,docChecklistWrapper.refNo);
				pstmtInsert.setString(2,docChecklistWrapper.seqNo);
				pstmtInsert.setString(3,docChecklistWrapper.accountType);
				pstmtInsert.setString(4,docChecklistWrapper.docID);
				pstmtInsert.setString(5,docChecklistWrapper.docName);
				pstmtInsert.setString(6,docChecklistWrapper.mandatory);
				pstmtInsert.setString(7,docChecklistWrapper.uploaded);
				
				pstmtInsert.executeUpdate();
				pstmtInsert.close();
				//-----------------------

				docChecklistWrapper.recordFound=true;
				
				vector.addElement(docChecklistWrapper);

			}
			
			if (vector.size()>0)
			{
				dataArrayWrapper.docChecklistWrapper = new DocChecklistWrapper[vector.size()];
				vector.copyInto(dataArrayWrapper.docChecklistWrapper);
				dataArrayWrapper.recordFound=true;

				System.out.println("total trn. in fetch " + vector.size());

			}
			
				resultSet.close();
				pstmt.close();
				System.out.println("fetch Document Checklist Successful " );
				
				
		

			
			
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
	
	public AbstractWrapper updateDocChecklist(DocChecklistWrapper[] docChecklistWrapperArray)throws Exception {
		
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
		PreparedStatement pstmt=null;
		
		String refNo=null;
		
		try {
				con = getConnection();
				
				for(int i=0;i<=docChecklistWrapperArray.length-1;i++)
				{
					pstmt = con.prepareStatement("UPDATE RMT_DocChecklist SET UPLOADED=? where RefNo=? and DocID=?");
			
					pstmt.setString(1,Utility.trim(docChecklistWrapperArray[i].uploaded));
					pstmt.setString(2,Utility.trim(docChecklistWrapperArray[i].refNo));
					System.out.println("Doc checklist Update RefNo "+ docChecklistWrapperArray[i].refNo);
					pstmt.setString(3,Utility.trim(docChecklistWrapperArray[i].docID));
					System.out.println("Doc checklist Update DocID "+ docChecklistWrapperArray[i].docID);
					pstmt.executeUpdate();
					pstmt.close();
					
					refNo=Utility.trim(docChecklistWrapperArray[i].refNo);
		
					docChecklistWrapperArray[i].recordFound=true;
				
				}

			
				dataArrayWrapper.docChecklistWrapper=docChecklistWrapperArray;
				
				dataArrayWrapper.recordFound=true;
				
				//---------update PathStatus
				PathStatusHelper pathStatusHelper=new PathStatusHelper();
				dataArrayWrapper.pathStatusWrapper=dataArrayWrapper.pathStatusWrapper=pathStatusHelper.updatePathStatus(refNo,"DOCS");
				//--
				
				System.out.println("Successfully RMT_DOCCHECKLIST Updated");
				
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
	
	public boolean fetchPendingDocChecklist(String refNo)throws Exception {

		Connection con = null;
		ResultSet resultSet = null;
		
		
		
		//DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
		
		//SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		formatter.applyPattern("###,###,###,##0.00");
		formatter.setDecimalFormatSymbols(symbols);
		
		//Vector<Object> vector = new Vector<Object>();
		boolean pendingUpload=true;
	
		try {
			
				//PopoverHelper popoverHelper = new PopoverHelper();
			
			
			
				con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("SELECT Mandatory, Uploaded FROM RMT_DocChecklist WHERE RefNo=? AND Mandatory=? AND Uploaded=?");
				
				pstmt.setString(1,refNo);
				pstmt.setString(2,"Y");
				pstmt.setString(3,"N");
				resultSet = pstmt.executeQuery();
				if(resultSet.next()) 
				{
					
					pendingUpload=true;
					
	
				}
				else{
					pendingUpload=false;
				}
				
				
	 
				if(resultSet !=null) resultSet.close();
				pstmt.close();
				
				
				
		

			
			
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

		return pendingUpload;
	}
	

}
