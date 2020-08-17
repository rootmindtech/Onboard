package com.rootmind.helper;



import com.rootmind.wrapper.AbstractWrapper;

public class LiabilityWrapper extends AbstractWrapper {
	public String refNo= null;
	public String seqNo=null;
	public String liabilityType=null;
	public String bankName =null;
	public String financedAmount=null;
	public String emi=null;
	public String tenor=null;
	public String emiPaid=null;
	public String balanceTenor=null;
	public String balanceOutstanding=null;
	public String creditLimit=null; //request credit limit
	public String category=null;//category emi(loan),mortgage loan(loan),creditcard(credit)
	public String deleteFlag=null;
	public String monthlyIncome =null;
	
	public String liabilityTypeValue=null;
	public String bankNameValue =null;
	public String approvedCreditLimit =null;
	public String dbr =null;
	
	public String requestMessage=null;
	public boolean responseStatus=false;
	
	public boolean recordFound=false;

}
