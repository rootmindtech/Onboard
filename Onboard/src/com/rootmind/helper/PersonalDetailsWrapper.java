package com.rootmind.helper;

import com.rootmind.wrapper.AbstractWrapper;

public class PersonalDetailsWrapper extends AbstractWrapper implements Comparable<PersonalDetailsWrapper> {
	
	
	public int id;
	public String refNo=null;
	public String existingRefNo=null;
	public String accountType=null;
	public String extCustomerFlag=null;
	public String cifNumber=null;
	public String extAccountNo=null;
	public String extCreditCardNo=null;
	public String extRelationship=null;
	public String relationshipNo=null;
	public String customerName=null;
	public String firstName=null;
	public String middleName=null;
	public String lastName=null;
	
	public String title=null;
	public String categoryType=null;
	public String branch=null;
	public String jointOwn=null;
	public String natureOfRelation=null;
	public String residenceStatus=null;
	public String nationality=null;
	public String dob=null;
	public String educated=null;
	public String maritalStatus=null;
	public String gender=null;
	

	public String passportNo=null;
	public String passportIssueDate=null;
	public String passportExpDate=null;
	public String passportIssuePlace=null;
	public String passportIssueCountry=null;
	public String emiratesID=null;
	public String emiratesIDExpDate=null;
	
	public String recordStatus =null;
	public String makerId =null;
	public String makerDateTime=null;
	
	public String modifierId =null;
	public String modifierDateTime=null;
	
	public String approverId=null;
	public String approverDateTime=null;
	
	public String searchStartDate=null;
	public String searchEndDate=null;
	
	public String pathStatus =null;
	

	public String extRelationshipValue=null;
	public String titleValue=null;
	public String categoryTypeValue=null;
	public String branchValue=null;
	public String jointOwnValue=null;
	public String natureOfRelationValue=null;
	public String residenceStatusValue=null;
	public String nationalityValue=null;
	public String educatedValue=null;
	public String maritalStatusValue=null;
	public String genderValue=null;
	public String passportIssueCountryValue=null;
	
	public int queueNoRecords=0;
	
	
	public String decline=null;
	public String declineReason=null;
	public String remarks=null;
	public String searchCode=null;
    public boolean recordFound=false;
    
    
    public String targetTAT=null;
    public String tat=null; //turn around time
    public String tatColor=null;
  
    //-----for workflowTrack
   
    public String workflowGroup=null;
   // public String actionStatus=null;
   
  
	
//	public String preferredLanguage=null;
//	public String familyInUAE=null;
//	public String familySizeUAE;
//	public String carOwnership=null;
//	public String carYear=null;
//	public String media=null;
//	public String favouriteCity=null;
//	public String domicile=null;
	
	
	    //public String preferredLanguageValue=null;
		//public String familyInUAEValue=null;
		//public String familySizeUAEValue;
		//public String carOwnershipValue=null;
		
		//public String mediaValue=null;
		//public String favouriteCityValue=null;
	
//	public String domicile=null;
//	public String passportNo=null;
//	public String passportIssueDate=null;
//	public String passportExpDate=null;
//	public String passportIssuePlace=null;
//	public String passportIssueCountry=null;
//	public String immiFileNumber=null;
//	public String visaIssuePlace=null;
//	public String visaIssueDate=null;
//	public String visaExpDate=null;
//	public String drivingLicenseNo=null;
//	public String drivingLicenseExpDate=null;
//	public String laborCardNo=null;
//	public String laborCardExpDate=null;
//	public String emiratesID=null;
//	public String emiratesIDExpDate=null;
//	public String guardianName=null;
//	public String introName=null;
//	public String introAccountNo=null;
//	public String introCustType=null;
//	public String introSegment=null;
//	public String introIndustry=null;
//	public String introBusinessGroup=null;
//	public String introBranch=null;
//	public String introGeography=null;
//	public String introRMID=null;
//	public String introSSO=null;
	
//	public String eNameCheckFlag=null;
//	public String eNamePerformedBy=null;
//	public String eNamePerformedDate=null;
//	public String eNameMatchFlag=null;
//	public String eNamePerformedName=null;
//	public String eNameResult=null;
//	public String eNameComment=null
//	public String shortName=null;
	/*public String duplicateCheck=null;
	public String occupationType=null;
	public String occupation=null;
	public String designation=null;
	public String orgName=null;
	public String otherOrgName=null;
	public String joiningDate=null;
	public BigDecimal monthlySalary=null;
	public String employeeNo=null;
	public String mailOption=null;
	public String debitCardDeliveryChnl=null;
	public String eStatement=null;
	public String email=null;
	public String email2=null;
	public String mobile=null;
	public String mobileSP=null;
	public String mobile2=null;*/
	
	
//	public String makerId=null;
//	public String makerDate=null;
//	public String approverId=null;
//	public String approverDate=null;
//	




	public PersonalDetailsWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}


	


	public PersonalDetailsWrapper(int id, String refNo, String existingRefNo,
			String accountType, String extCustomerFlag, String cifNumber,
			String extAccountNo, String extCreditCardNo,
			String extRelationship, String relationshipNo, String customerName,
			String firstName, String middleName, String lastName, String title,
			String categoryType, String branch, String jointOwn,
			String natureOfRelation, String residenceStatus,
			String nationality, String dob, String educated,
			String maritalStatus, String gender, String passportNo,
			String passportIssueDate, String passportExpDate,
			String passportIssuePlace, String passportIssueCountry,
			String emiratesID, String emiratesIDExpDate, String recordStatus,
			String makerId, String makerDateTime, String approverId,
			String approverDateTime, String pathStatus,
			String extRelationshipValue, String titleValue,
			String categoryTypeValue, String branchValue, String jointOwnValue,
			String natureOfRelationValue, String residenceStatusValue,
			String nationalityValue, String educatedValue,
			String maritalStatusValue, String genderValue,
			String passportIssueCountryValue, boolean recordFound) {
		super();
		this.id = id;
		this.refNo = refNo;
		this.existingRefNo = existingRefNo;
		this.accountType = accountType;
		this.extCustomerFlag = extCustomerFlag;
		this.cifNumber = cifNumber;
		this.extAccountNo = extAccountNo;
		this.extCreditCardNo = extCreditCardNo;
		this.extRelationship = extRelationship;
		this.relationshipNo = relationshipNo;
		this.customerName = customerName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.title = title;
		this.categoryType = categoryType;
		this.branch = branch;
		this.jointOwn = jointOwn;
		this.natureOfRelation = natureOfRelation;
		this.residenceStatus = residenceStatus;
		this.nationality = nationality;
		this.dob = dob;
		this.educated = educated;
		this.maritalStatus = maritalStatus;
		this.gender = gender;
		this.passportNo = passportNo;
		this.passportIssueDate = passportIssueDate;
		this.passportExpDate = passportExpDate;
		this.passportIssuePlace = passportIssuePlace;
		this.passportIssueCountry = passportIssueCountry;
		this.emiratesID = emiratesID;
		this.emiratesIDExpDate = emiratesIDExpDate;
		this.recordStatus = recordStatus;
		this.makerId = makerId;
		this.makerDateTime = makerDateTime;
		this.approverId = approverId;
		this.approverDateTime = approverDateTime;
		this.pathStatus = pathStatus;
		this.extRelationshipValue = extRelationshipValue;
		this.titleValue = titleValue;
		this.categoryTypeValue = categoryTypeValue;
		this.branchValue = branchValue;
		this.jointOwnValue = jointOwnValue;
		this.natureOfRelationValue = natureOfRelationValue;
		this.residenceStatusValue = residenceStatusValue;
		this.nationalityValue = nationalityValue;
		this.educatedValue = educatedValue;
		this.maritalStatusValue = maritalStatusValue;
		this.genderValue = genderValue;
		this.passportIssueCountryValue = passportIssueCountryValue;
		this.recordFound = recordFound;
	}





	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}





	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}





	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}





	/**
	 * @param refNo the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}





	/**
	 * @return the existingRefNo
	 */
	public String getExistingRefNo() {
		return existingRefNo;
	}





	/**
	 * @param existingRefNo the existingRefNo to set
	 */
	public void setExistingRefNo(String existingRefNo) {
		this.existingRefNo = existingRefNo;
	}





	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}





	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}





	/**
	 * @return the extCustomerFlag
	 */
	public String getExtCustomerFlag() {
		return extCustomerFlag;
	}





	/**
	 * @param extCustomerFlag the extCustomerFlag to set
	 */
	public void setExtCustomerFlag(String extCustomerFlag) {
		this.extCustomerFlag = extCustomerFlag;
	}





	/**
	 * @return the cifNumber
	 */
	public String getCifNumber() {
		return cifNumber;
	}





	/**
	 * @param cifNumber the cifNumber to set
	 */
	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}





	/**
	 * @return the extAccountNo
	 */
	public String getExtAccountNo() {
		return extAccountNo;
	}





	/**
	 * @param extAccountNo the extAccountNo to set
	 */
	public void setExtAccountNo(String extAccountNo) {
		this.extAccountNo = extAccountNo;
	}





	/**
	 * @return the extCreditCardNo
	 */
	public String getExtCreditCardNo() {
		return extCreditCardNo;
	}





	/**
	 * @param extCreditCardNo the extCreditCardNo to set
	 */
	public void setExtCreditCardNo(String extCreditCardNo) {
		this.extCreditCardNo = extCreditCardNo;
	}





	/**
	 * @return the extRelationship
	 */
	public String getExtRelationship() {
		return extRelationship;
	}





	/**
	 * @param extRelationship the extRelationship to set
	 */
	public void setExtRelationship(String extRelationship) {
		this.extRelationship = extRelationship;
	}





	/**
	 * @return the relationshipNo
	 */
	public String getRelationshipNo() {
		return relationshipNo;
	}





	/**
	 * @param relationshipNo the relationshipNo to set
	 */
	public void setRelationshipNo(String relationshipNo) {
		this.relationshipNo = relationshipNo;
	}





	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}





	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}





	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}





	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}





	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}





	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}





	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}





	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}





	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}





	/**
	 * @return the categoryType
	 */
	public String getCategoryType() {
		return categoryType;
	}





	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}





	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}





	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}





	/**
	 * @return the jointOwn
	 */
	public String getJointOwn() {
		return jointOwn;
	}





	/**
	 * @param jointOwn the jointOwn to set
	 */
	public void setJointOwn(String jointOwn) {
		this.jointOwn = jointOwn;
	}





	/**
	 * @return the natureOfRelation
	 */
	public String getNatureOfRelation() {
		return natureOfRelation;
	}





	/**
	 * @param natureOfRelation the natureOfRelation to set
	 */
	public void setNatureOfRelation(String natureOfRelation) {
		this.natureOfRelation = natureOfRelation;
	}





	/**
	 * @return the residenceStatus
	 */
	public String getResidenceStatus() {
		return residenceStatus;
	}





	/**
	 * @param residenceStatus the residenceStatus to set
	 */
	public void setResidenceStatus(String residenceStatus) {
		this.residenceStatus = residenceStatus;
	}





	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}





	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}





	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}





	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}





	/**
	 * @return the educated
	 */
	public String getEducated() {
		return educated;
	}





	/**
	 * @param educated the educated to set
	 */
	public void setEducated(String educated) {
		this.educated = educated;
	}





	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}





	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}





	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}





	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}





	/**
	 * @return the passportNo
	 */
	public String getPassportNo() {
		return passportNo;
	}





	/**
	 * @param passportNo the passportNo to set
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}





	/**
	 * @return the passportIssueDate
	 */
	public String getPassportIssueDate() {
		return passportIssueDate;
	}





	/**
	 * @param passportIssueDate the passportIssueDate to set
	 */
	public void setPassportIssueDate(String passportIssueDate) {
		this.passportIssueDate = passportIssueDate;
	}





	/**
	 * @return the passportExpDate
	 */
	public String getPassportExpDate() {
		return passportExpDate;
	}





	/**
	 * @param passportExpDate the passportExpDate to set
	 */
	public void setPassportExpDate(String passportExpDate) {
		this.passportExpDate = passportExpDate;
	}





	/**
	 * @return the passportIssuePlace
	 */
	public String getPassportIssuePlace() {
		return passportIssuePlace;
	}





	/**
	 * @param passportIssuePlace the passportIssuePlace to set
	 */
	public void setPassportIssuePlace(String passportIssuePlace) {
		this.passportIssuePlace = passportIssuePlace;
	}





	/**
	 * @return the passportIssueCountry
	 */
	public String getPassportIssueCountry() {
		return passportIssueCountry;
	}





	/**
	 * @param passportIssueCountry the passportIssueCountry to set
	 */
	public void setPassportIssueCountry(String passportIssueCountry) {
		this.passportIssueCountry = passportIssueCountry;
	}





	/**
	 * @return the emiratesID
	 */
	public String getEmiratesID() {
		return emiratesID;
	}





	/**
	 * @param emiratesID the emiratesID to set
	 */
	public void setEmiratesID(String emiratesID) {
		this.emiratesID = emiratesID;
	}





	/**
	 * @return the emiratesIDExpDate
	 */
	public String getEmiratesIDExpDate() {
		return emiratesIDExpDate;
	}





	/**
	 * @param emiratesIDExpDate the emiratesIDExpDate to set
	 */
	public void setEmiratesIDExpDate(String emiratesIDExpDate) {
		this.emiratesIDExpDate = emiratesIDExpDate;
	}





	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}





	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}





	/**
	 * @return the makerId
	 */
	public String getMakerId() {
		return makerId;
	}





	/**
	 * @param makerId the makerId to set
	 */
	public void setMakerId(String makerId) {
		this.makerId = makerId;
	}





	/**
	 * @return the makerDateTime
	 */
	public String getMakerDateTime() {
		return makerDateTime;
	}





	/**
	 * @param makerDateTime the makerDateTime to set
	 */
	public void setMakerDateTime(String makerDateTime) {
		this.makerDateTime = makerDateTime;
	}





	/**
	 * @return the approverId
	 */
	public String getApproverId() {
		return approverId;
	}





	/**
	 * @param approverId the approverId to set
	 */
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}





	/**
	 * @return the approverDateTime
	 */
	public String getApproverDateTime() {
		return approverDateTime;
	}





	/**
	 * @param approverDateTime the approverDateTime to set
	 */
	public void setApproverDateTime(String approverDateTime) {
		this.approverDateTime = approverDateTime;
	}





	/**
	 * @return the pathStatus
	 */
	public String getPathStatus() {
		return pathStatus;
	}





	/**
	 * @param pathStatus the pathStatus to set
	 */
	public void setPathStatus(String pathStatus) {
		this.pathStatus = pathStatus;
	}





	/**
	 * @return the extRelationshipValue
	 */
	public String getExtRelationshipValue() {
		return extRelationshipValue;
	}





	/**
	 * @param extRelationshipValue the extRelationshipValue to set
	 */
	public void setExtRelationshipValue(String extRelationshipValue) {
		this.extRelationshipValue = extRelationshipValue;
	}





	/**
	 * @return the titleValue
	 */
	public String getTitleValue() {
		return titleValue;
	}





	/**
	 * @param titleValue the titleValue to set
	 */
	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}





	/**
	 * @return the categoryTypeValue
	 */
	public String getCategoryTypeValue() {
		return categoryTypeValue;
	}





	/**
	 * @param categoryTypeValue the categoryTypeValue to set
	 */
	public void setCategoryTypeValue(String categoryTypeValue) {
		this.categoryTypeValue = categoryTypeValue;
	}





	/**
	 * @return the branchValue
	 */
	public String getBranchValue() {
		return branchValue;
	}





	/**
	 * @param branchValue the branchValue to set
	 */
	public void setBranchValue(String branchValue) {
		this.branchValue = branchValue;
	}





	/**
	 * @return the jointOwnValue
	 */
	public String getJointOwnValue() {
		return jointOwnValue;
	}





	/**
	 * @param jointOwnValue the jointOwnValue to set
	 */
	public void setJointOwnValue(String jointOwnValue) {
		this.jointOwnValue = jointOwnValue;
	}





	/**
	 * @return the natureOfRelationValue
	 */
	public String getNatureOfRelationValue() {
		return natureOfRelationValue;
	}





	/**
	 * @param natureOfRelationValue the natureOfRelationValue to set
	 */
	public void setNatureOfRelationValue(String natureOfRelationValue) {
		this.natureOfRelationValue = natureOfRelationValue;
	}





	/**
	 * @return the residenceStatusValue
	 */
	public String getResidenceStatusValue() {
		return residenceStatusValue;
	}





	/**
	 * @param residenceStatusValue the residenceStatusValue to set
	 */
	public void setResidenceStatusValue(String residenceStatusValue) {
		this.residenceStatusValue = residenceStatusValue;
	}





	/**
	 * @return the nationalityValue
	 */
	public String getNationalityValue() {
		return nationalityValue;
	}





	/**
	 * @param nationalityValue the nationalityValue to set
	 */
	public void setNationalityValue(String nationalityValue) {
		this.nationalityValue = nationalityValue;
	}





	/**
	 * @return the educatedValue
	 */
	public String getEducatedValue() {
		return educatedValue;
	}





	/**
	 * @param educatedValue the educatedValue to set
	 */
	public void setEducatedValue(String educatedValue) {
		this.educatedValue = educatedValue;
	}





	/**
	 * @return the maritalStatusValue
	 */
	public String getMaritalStatusValue() {
		return maritalStatusValue;
	}





	/**
	 * @param maritalStatusValue the maritalStatusValue to set
	 */
	public void setMaritalStatusValue(String maritalStatusValue) {
		this.maritalStatusValue = maritalStatusValue;
	}





	/**
	 * @return the genderValue
	 */
	public String getGenderValue() {
		return genderValue;
	}





	/**
	 * @param genderValue the genderValue to set
	 */
	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}





	/**
	 * @return the passportIssueCountryValue
	 */
	public String getPassportIssueCountryValue() {
		return passportIssueCountryValue;
	}





	/**
	 * @param passportIssueCountryValue the passportIssueCountryValue to set
	 */
	public void setPassportIssueCountryValue(String passportIssueCountryValue) {
		this.passportIssueCountryValue = passportIssueCountryValue;
	}





	/**
	 * @return the recordFound
	 */
	public boolean isRecordFound() {
		return recordFound;
	}





	/**
	 * @param recordFound the recordFound to set
	 */
	public void setRecordFound(boolean recordFound) {
		this.recordFound = recordFound;
	}





	@Override
	public int compareTo(PersonalDetailsWrapper o) {
		// TODO Auto-generated method stub
		return this.refNo.toLowerCase().compareTo(o.getRefNo().toLowerCase());
	}


}
