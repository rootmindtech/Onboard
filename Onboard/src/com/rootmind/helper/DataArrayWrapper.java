package com.rootmind.helper;

import com.rootmind.wrapper.AbstractWrapper;
import com.rootmind.wrapper.CKYCAddressWrapper;
import com.rootmind.wrapper.CKYCContactWrapper;
import com.rootmind.wrapper.CKYCCustomerWrapper;
import com.rootmind.wrapper.CKYCPOIWrapper;
import com.rootmind.wrapper.CKYCRelatedPersonWrapper;
import com.rootmind.wrapper.DashboardWebWrapper;
import com.rootmind.wrapper.ErrorWrapper;

public class DataArrayWrapper extends AbstractWrapper {

	public OnBoardWrapper[] onBoardWrapper = null;
	public PersonalDetailsWrapper[] personalDetailsWrapper = null;
	public ContactDetailsWrapper[] contactDetailsWrapper = null;
	public OccupationDetailsWrapper[] occupationDetailsWrapper = null;
	public OnBoardAddressWrapper[] onBoardAddressWrapper = null;
	public AddressDetailsWrapper[] addressDetailsWrapper = null;
	public AccountDetailsWrapper[] accountDetailsWrapper = null;

	public KycDetailsWrapper[] kycDetailsWrapper = null;
	public FatcaWrapper[] fatcaWrapper = null;
	public EmailApprovalWrapper[] emailApprovalWrapper = null;
	public PassportWrapper[] passportWrapper = null;
	public PopoverWrapper[] popoverWrapper = null;
	public CreditCardWrapper[] creditCardWrapper = null;
	public SearchWrapper [] searchWrapper = null;
	public DedupWrapper [] dedupWrapper = null;
	public SpendDataWrapper [] spendDataWrapper = null;
	
	public CreditCardsWrapper[] creditCardsWrapper = null;
	public AccountsWrapper[] accountsWrapper = null;
	public AccountTrnWrapper [] accountTrnWrapper=null;
	public DashboardWrapper [] dashboardWrapper=null;
	public CreditCardTrnWrapper [] creditCardTrnWrapper=null;
	
	public AutoLoansWrapper [] autoLoansWrapper=null; 
	
	public UsersWrapper[] usersWrapper=null;
	
	public EligibilityCalcWrapper[] eligibilityCalcWrapper=null;
	
	public CustomerSpendingWrapper[] customerSpendingWrapper=null;
	
	public ImageDetailsWrapper[] imageDetailsWrapper=null;
	public PathStatusWrapper[] pathStatusWrapper=null;
	public PersonalLoanWrapper[] personalLoanWrapper=null;
	public RevolvingOverdraftWrapper[] revolvingOverdraftWrapper=null;
	
	public RejectWrapper[] rejectWrapper=null;
	public DocChecklistWrapper[] docChecklistWrapper=null;
	public ErrorWrapper[] errorWrapper=null;
	public OtpWrapper[] otpWrapper=null;
	public PasswordWrapper[] passwordWrapper=null;
	
	public UsersWrapper[] userProfileWrapper=null;
	public UserMenuWrapper[] userMenuWrapper=null;
	
	public LoanCalculatorWrapper[] loanCalculatorWrapper=null;
	public UserGroupWrapper[] userGroupWrapper=null;
	
	public GroupMenuWrapper[] groupMenuWrapper=null;
	
	public WorkflowStatusWrapper[] workflowStatusWrapper=null;
	public LiabilityWrapper[] liabilityWrapper=null;
	
	public CKYCCustomerWrapper[] ckycCustomerWrapper=null;
	public CKYCAddressWrapper[] ckycAddressWrapper=null;
	public CKYCContactWrapper[] ckycContactWrapper=null;
	public CKYCPOIWrapper[] ckycPOIWrapper=null;
	public CKYCRelatedPersonWrapper[] ckycRelatedPersonWrapper=null;
	
	//-forGraph
	public GraphWrapper[] graphWrapper=null;
	public GraphWrapper[] appsCountWrapper=null; 
	public GraphWrapper[] avgAppProcessTimeWrapper=null;
	public GraphWrapper[] recordStatusWrapper=null;
	
	public DashboardWebWrapper[] dashboardWebWrapper=null;
	public GraphWrapper[] creditUserWrapper=null;
	public GraphWrapper[] avgReviewTimeWrapper=null;
	
	public String methodAction=null;
	
	
	/*
	 * public AccountsWrapper [] accountsWrapper=null; public AccountTrnWrapper
	 * [] accountTrnWrapper=null; public CreditCardsWrapper[]
	 * creditCardsWrapper=null; public CreditCardTrnWrapper[]
	 * creditCardTrnWrapper=null; public DashboardWrapper[]
	 * dashboardWrapper=null; public IncidentsWrapper[] incidentsWrapper=null;
	 * 
	 * 
	 * public ProjectsWrapper [] projectsWrapper=null; public
	 * ProjectFinancialsWrapper [] projectFinancialsWrapper=null; public
	 * ProjectRisksWrapper [] projectRisksWrapper=null; public
	 * ProjectStatusWrapper [] projectStatusWrapper=null; public
	 * ProjectTimelinesWrapper [] projectTimelinesWrapper=null;
	 * 
	 * public ProjectsSummaryWrapper [] projectsSummaryWrapper=null; public
	 * ProjectsSummaryWrapper [] businessUnitWrapper=null; public
	 * ProjectsSummaryWrapper [] purposeWrapper=null;
	 */

	public String errorCode = null;
	public String errorDescription = null;
	public boolean validSession = false;
	public boolean recordFound = false;

	public static void main(String[] args) {

	}

}
