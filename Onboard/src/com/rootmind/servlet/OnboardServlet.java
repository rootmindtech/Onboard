package com.rootmind.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.util.Base64;
//import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rootmind.controller.AutoLoans2Controller;
import com.rootmind.controller.CreditCard2Controller;
import com.rootmind.controller.CreditCardCCController;
import com.rootmind.controller.CreditCardController;
import com.rootmind.controller.DBRCalculatorController;
import com.rootmind.controller.FinalController;
import com.rootmind.controller.KYCDetailsController;
import com.rootmind.controller.KYCEDDController;
import com.rootmind.controller.LoanCalculatorController;
import com.rootmind.controller.LoginProfileController;
import com.rootmind.controller.OccupationDetailsController;
import com.rootmind.controller.PassportDetailsController;
import com.rootmind.controller.PersonalDetailsController;
import com.rootmind.controller.PersonalLoanController;
import com.rootmind.controller.UsersController;
import com.rootmind.helper.AccountDetailsWrapper;
import com.rootmind.helper.AccountDetailsHelper;
import com.rootmind.helper.AccountTrnWrapper;
import com.rootmind.helper.AccountsHelper;
import com.rootmind.helper.AccountsWrapper;
import com.rootmind.helper.AddressDetailsHelper;
import com.rootmind.helper.AddressDetailsWrapper;
//import com.google.gson.JsonParser;
import com.rootmind.helper.AutoLoans2Helper;
import com.rootmind.helper.AutoLoansHelper;
import com.rootmind.helper.AutoLoansWrapper;
import com.rootmind.helper.CKYCAddressHelper;
import com.rootmind.helper.CKYCContactHelper;
import com.rootmind.helper.CKYCCustomerHelper;
import com.rootmind.helper.CKYCPOIHelper;
import com.rootmind.helper.CKYCRelatedPersonHelper;
import com.rootmind.helper.ContactDetailsHelper;
import com.rootmind.helper.ContactDetailsWrapper;
import com.rootmind.helper.CreditCardHelper;
import com.rootmind.helper.CreditCardTrnWrapper;
import com.rootmind.helper.CreditCardWrapper;
import com.rootmind.helper.CreditCardsHelper;
import com.rootmind.helper.CreditCardsWrapper;
import com.rootmind.helper.CustomerSpendingHelper;
import com.rootmind.helper.CustomerSpendingWrapper;
import com.rootmind.helper.DashboardHelper;
import com.rootmind.helper.DashboardWrapper;
//import com.rootmind.helper.AccountsHelper;
//import com.rootmind.helper.ApnsPushNotificationService;
//import com.rootmind.helper.CreditCardsHelper;
import com.rootmind.helper.DataArrayWrapper;
import com.rootmind.helper.DedupHelper;
import com.rootmind.helper.DedupWrapper;
import com.rootmind.helper.DocChecklistHelper;
import com.rootmind.helper.DocChecklistWrapper;
import com.rootmind.helper.FatcaHelper;
import com.rootmind.helper.FatcaWrapper;
import com.rootmind.helper.GraphHelper;
import com.rootmind.helper.GraphWrapper;
import com.rootmind.helper.GroupMenuHelper;
import com.rootmind.helper.GroupMenuWrapper;
import com.rootmind.helper.ImageDetailsHelper;
import com.rootmind.helper.ImageDetailsWrapper;
import com.rootmind.helper.KYC2DetailsHelper;
import com.rootmind.helper.KYCEDDHelper;
import com.rootmind.helper.KYCTranHelper;
import com.rootmind.helper.KycDetailsHelper;
import com.rootmind.helper.KycDetailsWrapper;
import com.rootmind.helper.LiabilityHelper;
import com.rootmind.helper.LiabilityWrapper;
import com.rootmind.helper.LoanCalculatorWrapper;
import com.rootmind.helper.OccupationDetailsHelper;
import com.rootmind.helper.OccupationDetailsWrapper;
import com.rootmind.helper.OtpHelper;
import com.rootmind.helper.OtpWrapper;
import com.rootmind.helper.PassportHelper;
import com.rootmind.helper.PassportWrapper;
import com.rootmind.helper.PasswordWrapper;
import com.rootmind.helper.PathStatusHelper;
import com.rootmind.helper.PathStatusWrapper;
//import com.rootmind.helper.OnBoardHelper;
//import com.rootmind.helper.OnBoardWrapper;
import com.rootmind.helper.PersonalDetailsHelper;
import com.rootmind.helper.PersonalDetailsWrapper;
import com.rootmind.helper.PersonalLoanHelper;
import com.rootmind.helper.PersonalLoanWrapper;
import com.rootmind.helper.PopoverHelper;
import com.rootmind.helper.PopoverWrapper;
import com.rootmind.helper.RejectHelper;
import com.rootmind.helper.RejectWrapper;
import com.rootmind.helper.RevolvingOverdraftHelper;
import com.rootmind.helper.RevolvingOverdraftWrapper;
import com.rootmind.helper.SpendDataWrapper;
import com.rootmind.helper.UserAuditHelper;
import com.rootmind.helper.UserGroupHelper;
import com.rootmind.helper.UserGroupWrapper;
import com.rootmind.helper.UserMenuHelper;
import com.rootmind.helper.UserMenuWrapper;
//import com.rootmind.helper.UserMenuHelper;
//import com.rootmind.helper.UserMenuWrapper;
//import com.rootmind.helper.IncidentsHelper;
//import com.rootmind.helper.ProjectsHelper;
import com.rootmind.helper.UsersHelper;
import com.rootmind.helper.UsersWrapper;
//import com.rootmind.helper.Utility;

import com.rootmind.helper.SearchHelper;
import com.rootmind.helper.SearchWrapper;
import com.rootmind.helper.WorkflowStatusHelper;
import com.rootmind.helper.WorkflowStatusWrapper;
import com.rootmind.wrapper.CKYCAddressWrapper;
import com.rootmind.wrapper.CKYCContactWrapper;
import com.rootmind.wrapper.CKYCCustomerWrapper;
import com.rootmind.wrapper.CKYCPOIWrapper;
import com.rootmind.wrapper.CKYCRelatedPersonWrapper;
import com.rootmind.wrapper.DashboardWebWrapper;

/**
 * Servlet implementation class OnboardServlet
 */
@WebServlet("/Onboard")
@MultipartConfig
public class OnboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OnboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String methodAction = request.getParameter("methodAction");

		if (methodAction == null)

		{

			PrintWriter errorOut = response.getWriter();

			response.setContentType("text/html");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");

			System.out.println("Request received at GET OnBoard but methodAction null");

			String msg = "<html><body><h1>Invalid request received at OnBoard</h1></body></html>";

			errorOut.println(msg);
			errorOut.flush();
			errorOut.close();

			return;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		try {

			/*
			 * //HttpSession session = request.getSession(); boolean isSessionNew =
			 * session.isNew(); System.out.println("IS new session :"+ isSessionNew); String
			 * localSessionid=(String)session.getId();
			 * System.out.println("local sessionid :" + localSessionid ); String
			 * paramSessionid = request.getParameter("sessionid");
			 * System.out.println("param session id :" +paramSessionid); String
			 * cookieFromRequestHeader = request.getHeader("cookie");
			 * System.out.println("cookie :" + cookieFromRequestHeader); String
			 * localUserid=(String)session.getAttribute("userid");
			 * System.out.println("local userid :" + localUserid);
			 */
			// String paramUserid=request.getParameter("userid");
			// System.out.println("param userid :" + paramUserid);
			// String paramPassword = request.getParameter("password");
			// System.out.println("param password :" + paramPassword);

			/*
			 * String cifNumber = request.getParameter("cifNumber");
			 * System.out.println("cifNumber :" + cifNumber); String accountNumber =
			 * request.getParameter("accountNumber"); System.out.println("accountNumber :" +
			 * accountNumber); String creditCardNumber =
			 * request.getParameter("creditCardNumber");
			 * System.out.println("creditCardNumber :" + creditCardNumber);
			 */

			// String deviceToken = request.getParameter("deviceToken");
			// System.out.println("deviceToken :" + deviceToken);

			System.out.println("enum header print9 :" );
//
//			Enumeration<String> headerNames = request.getHeaderNames();
//			while(headerNames.hasMoreElements()) {
//			  String headerName = headerNames.nextElement();
//			  System.out.println("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
//			}
//			
//			Enumeration<String> params = request.getParameterNames(); 
//			while(params.hasMoreElements()){
//			 String paramName = params.nextElement();
//			 System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
//			}
			
			
			String userProfile = request.getParameter("userProfile");
			System.out.println("param userProfile :" + userProfile);

			String message = request.getParameter("message");
			System.out.println("message :" + message);

			String methodAction = request.getParameter("methodAction");
			// String customerCode = request.getParameter("customerCode");

			System.out.println("method action in the servlet  " + methodAction);
			// System.out.println("customer code in the servlet " + customerCode);

			// String tableName = request.getParameter("tableName");

			PersonalDetailsWrapper personalDetailsWrapper = null;
			PassportWrapper passportWrapper = null;

			ContactDetailsWrapper contactDetailsWrapper = null;
			AddressDetailsWrapper addressDetailsWrapper = null;
			AccountDetailsWrapper accountDetailsWrapper = null;
			KycDetailsWrapper kycDetailsWrapper = null;
			PopoverWrapper popoverWrapper = null;
			PopoverWrapper[] popoverWrapperArray = null;
			OccupationDetailsWrapper occupationDetailsWrapper = null;
			SearchWrapper searchWrapper = null;
			AccountsWrapper accountsWrapper = null;
			DashboardWrapper dashboardWrapper = null;
			SpendDataWrapper spendDataWrapper = null;
			CreditCardTrnWrapper creditCardTrnWrapper = null;
			AccountTrnWrapper accountTrnWrapper = null;
			DedupWrapper dedupWrapper = null;
			CustomerSpendingWrapper customerSpendingWrapper = null;
			CreditCardsWrapper creditCardsWrapper = null;
			ImageDetailsWrapper imageDetailsWrapper = null;
			AutoLoansWrapper autoLoansWrapper = null;
			PathStatusWrapper pathStatusWrapper = null;
			PersonalLoanWrapper personalLoanWrapper = null;
			RevolvingOverdraftWrapper revolvingOverdraftWrapper = null;
			FatcaWrapper fatcaWrapper = null;
			LoanCalculatorWrapper loanCalculatorWrapper = null;

			DocChecklistWrapper docChecklistWrapper = null;
			DocChecklistWrapper[] docChecklistWrapperArray = null;
			CreditCardWrapper creditCardWrapper = null;
			OtpWrapper otpWrapper = null;
			UsersController usersController = null;
			PasswordWrapper passwordWrapper = null;
			UsersWrapper usersWrapper = null;
			UserGroupWrapper userGroupWrapper = null;
			UserGroupWrapper[] userGroupWrapperArray = null;
			UserMenuWrapper userMenuWrapper = null;
			UserMenuWrapper[] userMenuWrapperArray = null;

			GroupMenuWrapper groupMenuWrapper = null;
			GroupMenuWrapper[] groupMenuWrapperArray = null;

			RejectWrapper rejectWrapper = null;
			RejectWrapper[] rejectWrapperArray = null;

			WorkflowStatusWrapper workflowStatusWrapper = null;
			LiabilityWrapper liabilityWrapper = null;

			CKYCCustomerWrapper ckycCustomerWrapper = null;

			CKYCAddressWrapper ckycAddressWrapper = null;
			CKYCContactWrapper ckycContactWrapper = null;
			CKYCPOIWrapper ckycPOIWrapper = null;
			CKYCRelatedPersonWrapper ckycRelatedPersonWrapper = null;

			GraphWrapper graphWrapper = null;
			DashboardWebWrapper dashboardWebWrapper = null;

			String environment = "CLOUD";

			String errorCode = null;
			String errorDescription = null;

			ServletContext application = getServletConfig().getServletContext();

			System.out.println("min version  " + application.getMinorVersion());

			System.out.println("max version  " + application.getMajorVersion());

			UsersWrapper usersProfileWrapper = new UsersWrapper();
			DataArrayWrapper dataArrayWrapper = new DataArrayWrapper();
			UsersHelper usersHelper = new UsersHelper();
			UserAuditHelper userAuditHelper = new UserAuditHelper();

			System.out.println("methodaction  " + methodAction);

			System.out.println("before gson");
			Gson gson = new Gson();
			JsonObject mainJsonObj = new JsonObject();
			JsonElement elementObj = null;

			HttpSession session = null;
			dataArrayWrapper.usersWrapper = new UsersWrapper[1];

			// String localUserid=null;
			String localSessionid = null;

			session = request.getSession();
			// session.setAttribute("userid",usersWrapper.userid);
			// setting session to expiry in 30 mins
			localSessionid = (String) session.getId();
			System.out.println("local sessionid :" + localSessionid);
			boolean validSession = false;

			// ----------Customer section---//
			String appName = "OnBoard";

			if (methodAction == null)

			{

				PrintWriter errorOut = response.getWriter();

				response.setContentType("text/html");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type");
				response.setHeader("Access-Control-Max-Age", "86400");

				System.out.println("Request received at POST OnBoard but methodAction null");

				String msg = "<html><body><h1>Invalid request received at OnBoard</h1></body></html>";

				errorOut.println(msg);
				errorOut.flush();
				errorOut.close();

				return;

			}

			/*
			 * if(userProfile!=null) {
			 * 
			 * usersProfileWrapper= gson.fromJson(userProfile,UsersWrapper.class);
			 * usersHelper.updateUserDetails(usersProfileWrapper.userid,
			 * usersProfileWrapper.noLoginRetry,localSessionid ,
			 * usersProfileWrapper.deviceToken);
			 * 
			 * }
			 */

			if (userProfile != null) {

				usersProfileWrapper = gson.fromJson(userProfile, UsersWrapper.class);
				usersProfileWrapper.ipAddress = request.getRemoteAddr();
				usersHelper.updateUserDetails(usersProfileWrapper.userid, usersProfileWrapper.noLoginRetry,
						localSessionid, usersProfileWrapper.deviceToken);

				userAuditHelper.updateUserAudit(usersProfileWrapper.userid, localSessionid, methodAction, appName,
						message);

			}
			if (methodAction.equals("validateUser")) {
				usersWrapper = gson.fromJson(message, UsersWrapper.class);

				usersWrapper.ipAddress = request.getRemoteAddr();
				usersHelper.updateSessionDetails(usersWrapper.userid, usersProfileWrapper.noLoginRetry, localSessionid,
						usersWrapper.deviceToken);

				System.out.println("in validateUser");

				// usersWrapper =
				// (UsersWrapper)usersHelper.validateCredentials(usersWrapper.userid,usersWrapper.password,"",true);
				usersController = new UsersController();
				usersWrapper = (UsersWrapper) usersController.validate(usersWrapper);

				// session.setMaxInactiveInterval(usersWrapper.sessionExpiryTime);
				usersWrapper.sessionid = localSessionid;

				dataArrayWrapper.usersWrapper[0] = usersWrapper;

				validSession = true;

				/*
				 * if(usersWrapper.recordFound==true && usersWrapper.validUser==true) {
				 * 
				 * dataArrayWrapper.recordFound=true;
				 * 
				 * } else { System.out.println("usersWrapper.validUser :" +
				 * usersWrapper.validUser ); dataArrayWrapper.recordFound=true; }
				 */

				dataArrayWrapper.validSession = validSession;
				dataArrayWrapper.recordFound = true;

				elementObj = gson.toJsonTree(dataArrayWrapper);

				mainJsonObj.add(methodAction, elementObj);

				if (dataArrayWrapper.recordFound == true) {
					mainJsonObj.addProperty("success", true);
				} else {
					mainJsonObj.addProperty("success", false);
				}

			} else {

				UsersWrapper userSessionWrapper = (UsersWrapper) usersHelper
						.fetchSessionDetails(usersProfileWrapper.userid);

				System.out.println("usersProfileWrapper.sessionid " + usersProfileWrapper.sessionid);
				System.out.println("userSessionWrapper.sessionid " + userSessionWrapper.sessionid);

				if (usersProfileWrapper.sessionid == null || userSessionWrapper.sessionid == null
						|| !usersProfileWrapper.sessionid.equals(userSessionWrapper.sessionid)) {
					validSession = false;

					System.out.println("Invalid session");

					dataArrayWrapper.validSession = validSession;
					dataArrayWrapper.recordFound = true;

					elementObj = gson.toJsonTree(dataArrayWrapper);

					mainJsonObj.add(methodAction, elementObj);

					if (dataArrayWrapper.recordFound == true) {
						mainJsonObj.addProperty("success", true);
					} else {
						mainJsonObj.addProperty("success", false);
					}

				} else {
					validSession = true;
				}

			}

			/*
			 * if (methodAction.equals("validateUser")) { usersWrapper=
			 * gson.fromJson(message,UsersWrapper.class);
			 * 
			 * 
			 * System.out.println("in validateUser");
			 * 
			 * //usersWrapper =
			 * (UsersWrapper)usersHelper.validateCredentials(usersWrapper.userid,
			 * usersWrapper.password,"",true); usersController = new UsersController();
			 * usersWrapper = (UsersWrapper)usersController.validate(usersWrapper);
			 * 
			 * 
			 * session.setMaxInactiveInterval(usersWrapper.sessionExpiryTime);
			 * usersWrapper.sessionid=localSessionid;
			 * 
			 * dataArrayWrapper.usersWrapper[0]=usersWrapper;
			 * 
			 * 
			 * if(usersWrapper.recordFound==true && usersWrapper.validUser==true) {
			 * 
			 * dataArrayWrapper.recordFound=true;
			 * 
			 * } else { System.out.println("usersWrapper.validUser :" +
			 * usersWrapper.validUser ); dataArrayWrapper.recordFound=true; }
			 * 
			 * elementObj = gson.toJsonTree(dataArrayWrapper);
			 * 
			 * mainJsonObj.add(methodAction, elementObj);
			 * 
			 * if(dataArrayWrapper.recordFound==true){ mainJsonObj.addProperty("success",
			 * true); } else { mainJsonObj.addProperty("success", false); }
			 * 
			 * 
			 * }
			 */
			/*
			 * else { session = request.getSession(false);
			 * 
			 * if(session==null || session.getAttribute("userid")==null) {
			 * usersWrapper.validUser=true; dataArrayWrapper.usersWrapper[0]=usersWrapper;
			 * dataArrayWrapper.recordFound=true; errorCode="110";
			 * errorDescription="Invalid userid or sessionid";
			 * System.out.println("userid not valid "+ paramUserid); } else {
			 * localUserid=(String)session.getAttribute("userid");
			 * 
			 * 
			 * System.out.println("localUserid :" + localUserid );
			 * 
			 * Cookie[] cookies = request.getCookies(); if(cookies !=null){ for(Cookie
			 * cookie : cookies){
			 * 
			 * if(cookie.getName().equals("userid")) { System.out.println("cookie userid :"
			 * + cookie.getName()); String userName = cookie.getValue();
			 * System.out.println("cookie userid value :" + userName); }
			 * if(cookie.getName().equals("JSESSIONID")) {
			 * System.out.println("cookie sessionid :" + cookie.getName());
			 * localSessionid=cookie.getValue();
			 * System.out.println("cookie sessionid value :" + localSessionid); } }
			 * 
			 * } if(localUserid.equals(paramUserid)) {
			 * 
			 * usersWrapper =
			 * (UsersWrapper)usersHelper.validateCredentials(paramUserid,paramPassword,
			 * localSessionid,false); } else { usersWrapper.validUser=true;
			 * dataArrayWrapper.usersWrapper[0]=usersWrapper;
			 * dataArrayWrapper.recordFound=true; errorCode="120";
			 * errorDescription="Invalid userid or sessionid";
			 * System.out.println("userid not valid "+ paramUserid); }
			 * 
			 * }
			 * 
			 * 
			 * 
			 * }
			 */

			if (message != null) {

				if (methodAction.equals("fetchUsersList")) {

					usersWrapper = gson.fromJson(message, UsersWrapper.class);

				}

				if (methodAction.equals("insertLoginProfile")) {

					usersWrapper = gson.fromJson(message, UsersWrapper.class);
				}
				if (methodAction.equals("updateLoginProfile")) {

					usersWrapper = gson.fromJson(message, UsersWrapper.class);
				}
				if (methodAction.equals("insertPersonalDetails")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}

				if (methodAction.equals("fetchPersonalDetails")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}

				if (methodAction.equals("updatePersonalDetails")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}

				if (methodAction.equals("updatePassportDetails")) {
					passportWrapper = gson.fromJson(message, PassportWrapper.class);

				}
				if (methodAction.equals("updateContactDetails")) {
					contactDetailsWrapper = gson.fromJson(message, ContactDetailsWrapper.class);

				}
				if (methodAction.equals("insertAddressDetails")) {

					addressDetailsWrapper = gson.fromJson(message, AddressDetailsWrapper.class);

				}
				if (methodAction.equals("fetchOnBoardQueue")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}

				if (methodAction.equals("updateOccupationDetails")) {
					occupationDetailsWrapper = gson.fromJson(message, OccupationDetailsWrapper.class);

				}

				if (methodAction.equals("insertAccountDetails")) {
					accountDetailsWrapper = gson.fromJson(message, AccountDetailsWrapper.class);

				}

				if (methodAction.equals("insertKycDetails")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}

				if (methodAction.equals("fetchKycDetails")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("updateKycDetails")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("updateKYCTransaction")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("updateKYCTransaction2")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("fetchKYCTransaction")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("fetchKYCTransaction2")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("fetchKYC2Details")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("fetchKYCEDD")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("updateKYCEDD")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}
				if (methodAction.equals("updateKYC2Details")) {
					kycDetailsWrapper = gson.fromJson(message, KycDetailsWrapper.class);

				}

				if (methodAction.equals("fetchPopoverData")) {

					popoverWrapper = gson.fromJson(message, PopoverWrapper.class);

				}
				if (methodAction.equals("fetchMasterData")) {

					popoverWrapper = gson.fromJson(message, PopoverWrapper.class);

				}
				if (methodAction.equals("fetchTableNames")) {

					popoverWrapper = gson.fromJson(message, PopoverWrapper.class);

				}
				if (methodAction.equals("updateMasterData")) {

					popoverWrapper = gson.fromJson(message, PopoverWrapper.class);

				}
				if (methodAction.equals("deletePopoverData")) {

					popoverWrapper = gson.fromJson(message, PopoverWrapper.class);

				}

				if (methodAction.equals("fetchMultiPopoverData")) {

					popoverWrapperArray = gson.fromJson(message, PopoverWrapper[].class);

				}

				if (methodAction.equals("fetchEnquiry")) {

					searchWrapper = gson.fromJson(message, SearchWrapper.class);

				}
				if (methodAction.equals("fetchAccounts")) {

					accountsWrapper = gson.fromJson(message, AccountsWrapper.class);

				}
				if (methodAction.equals("fetchLoans")) {

					accountsWrapper = gson.fromJson(message, AccountsWrapper.class);

				}
				if (methodAction.equals("fetchDeposits")) {

					accountsWrapper = gson.fromJson(message, AccountsWrapper.class);

				}
				if (methodAction.equals("fetchDashboard")) {

					dashboardWrapper = gson.fromJson(message, DashboardWrapper.class);

				}
				if (methodAction.equals("fetchSpendData")) {

					spendDataWrapper = gson.fromJson(message, SpendDataWrapper.class);

				}

				if (methodAction.equals("fetchCreditCards")) {

					creditCardsWrapper = gson.fromJson(message, CreditCardsWrapper.class);

				}

				if (methodAction.equals("fetchCreditCardTrn")) {

					creditCardTrnWrapper = gson.fromJson(message, CreditCardTrnWrapper.class);

				}
				if (methodAction.equals("approveRecord")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}
				if (methodAction.equals("dedupEnquiry")) {

					dedupWrapper = gson.fromJson(message, DedupWrapper.class);

				}
				if (methodAction.equals("fetchDedup")) {

					dedupWrapper = gson.fromJson(message, DedupWrapper.class);

				}
				if (methodAction.equals("fetchAccountTrn")) {
					accountTrnWrapper = gson.fromJson(message, AccountTrnWrapper.class);

				}

				if (methodAction.equals("fetchPassportDetails")) {
					passportWrapper = gson.fromJson(message, PassportWrapper.class);

				}
				if (methodAction.equals("fetchOccupationDetails")) {
					occupationDetailsWrapper = gson.fromJson(message, OccupationDetailsWrapper.class);

				}
				if (methodAction.equals("fetchContactDetails")) {
					contactDetailsWrapper = gson.fromJson(message, ContactDetailsWrapper.class);

				}

				if (methodAction.equals("fetchAddressDetails")) {
					addressDetailsWrapper = gson.fromJson(message, AddressDetailsWrapper.class);

				}
				if (methodAction.equals("updateAddressDetails")) {
					addressDetailsWrapper = gson.fromJson(message, AddressDetailsWrapper.class);

				}

				if (methodAction.equals("fetchCustomerSpending")) {
					customerSpendingWrapper = gson.fromJson(message, CustomerSpendingWrapper.class);

				}
				if (methodAction.equals("fetchAccountDetails")) {
					accountDetailsWrapper = gson.fromJson(message, AccountDetailsWrapper.class);

				}
				if (methodAction.equals("updateAccountDetails")) {
					accountDetailsWrapper = gson.fromJson(message, AccountDetailsWrapper.class);

				}

				if (methodAction.equals("uploadImageDetails"))

				{
					imageDetailsWrapper = gson.fromJson(message, ImageDetailsWrapper.class);

				}
				if (methodAction.equals("fetchImageDetails"))

				{
					imageDetailsWrapper = gson.fromJson(message, ImageDetailsWrapper.class);

				}
				if (methodAction.equals("fetchImageFileNames"))

				{
					imageDetailsWrapper = gson.fromJson(message, ImageDetailsWrapper.class);

				}
				if (methodAction.equals("updateImageDetails"))

				{
					imageDetailsWrapper = gson.fromJson(message, ImageDetailsWrapper.class);

				}

				if (methodAction.equals("updateImageStatus"))

				{
					imageDetailsWrapper = gson.fromJson(message, ImageDetailsWrapper.class);

				}

				if (methodAction.equals("insertAutoLoans")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("updateAutoLoans")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("updateAutoLoans2")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("updateAutoLoans3")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("fetchAutoLoans")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("fetchAutoLoans2")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("fetchAutoLoans3")) {
					autoLoansWrapper = gson.fromJson(message, AutoLoansWrapper.class);

				}
				if (methodAction.equals("updateApplicationStatus")) {
					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}

				if (methodAction.equals("fetchPathStatus")) {
					pathStatusWrapper = gson.fromJson(message, PathStatusWrapper.class);

				}
				if (methodAction.equals("insertPersonalLoan")) {
					personalLoanWrapper = gson.fromJson(message, PersonalLoanWrapper.class);

				}
				if (methodAction.equals("fetchPersonalLoan")) {
					personalLoanWrapper = gson.fromJson(message, PersonalLoanWrapper.class);

				}
				if (methodAction.equals("updatePersonalLoan")) {
					personalLoanWrapper = gson.fromJson(message, PersonalLoanWrapper.class);

				}
				if (methodAction.equals("fetchPersonalLoan2")) {
					personalLoanWrapper = gson.fromJson(message, PersonalLoanWrapper.class);

				}
				if (methodAction.equals("updatePersonalLoan2")) {
					personalLoanWrapper = gson.fromJson(message, PersonalLoanWrapper.class);

				}

				if (methodAction.equals("insertRoD")) {
					revolvingOverdraftWrapper = gson.fromJson(message, RevolvingOverdraftWrapper.class);

				}
				if (methodAction.equals("fetchRoD")) {
					revolvingOverdraftWrapper = gson.fromJson(message, RevolvingOverdraftWrapper.class);

				}
				if (methodAction.equals("updateRoD")) {
					revolvingOverdraftWrapper = gson.fromJson(message, RevolvingOverdraftWrapper.class);

				}

				if (methodAction.equals("fetchDocChecklist")) {
					docChecklistWrapper = gson.fromJson(message, DocChecklistWrapper.class);

				}
				if (methodAction.equals("updateDocChecklist")) {

					docChecklistWrapperArray = gson.fromJson(message, DocChecklistWrapper[].class);

				}

				if (methodAction.equals("fetchCreditCard")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("fetchCreditCardCC")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("fetchCreditCard2")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("fetchCreditCard3")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("updateCreditCard")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("updateCreditCardCC")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("updateCreditCard2")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("updateCreditCard3")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("insertCreditCard")) {

					creditCardWrapper = gson.fromJson(message, CreditCardWrapper.class);

				}
				if (methodAction.equals("fetchExistingCustomer")) {

					personalDetailsWrapper = gson.fromJson(message, PersonalDetailsWrapper.class);

				}
				if (methodAction.equals("insertOTP")) {

					otpWrapper = gson.fromJson(message, OtpWrapper.class);

				}
				if (methodAction.equals("validateOTP")) {

					otpWrapper = gson.fromJson(message, OtpWrapper.class);

				}

				if (methodAction.equals("fetchPasswordPolicy")) {

					passwordWrapper = gson.fromJson(message, PasswordWrapper.class);

				}

				if (methodAction.equals("changePassword")) {

					usersWrapper = gson.fromJson(message, UsersWrapper.class);

				}

				/*
				 * if(methodAction.equals("fetchOnBoardSearch")) {
				 * 
				 * 
				 * personalDetailsWrapper= gson.fromJson(message,PersonalDetailsWrapper.class);
				 * 
				 * }
				 */

				if (methodAction.equals("fetchFATCA")) {

					fatcaWrapper = gson.fromJson(message, FatcaWrapper.class);

				}

				if (methodAction.equals("insertFATCA")) {

					fatcaWrapper = gson.fromJson(message, FatcaWrapper.class);

				}

				if (methodAction.equals("updateFATCA")) {

					fatcaWrapper = gson.fromJson(message, FatcaWrapper.class);

				}
				if (methodAction.equals("loanCalculator")) {

					loanCalculatorWrapper = gson.fromJson(message, LoanCalculatorWrapper.class);

				}
				if (methodAction.equals("fetchUserGroupList")) {

					userGroupWrapper = gson.fromJson(message, UserGroupWrapper.class);

				}
				if (methodAction.equals("updateUserGroupList")) {

					userGroupWrapperArray = gson.fromJson(message, UserGroupWrapper[].class);

				}
				if (methodAction.equals("fetchUserMenuList")) {

					userMenuWrapper = gson.fromJson(message, UserMenuWrapper.class);

				}
				if (methodAction.equals("updateUserMenuList")) {

					userMenuWrapperArray = gson.fromJson(message, UserMenuWrapper[].class);

				}
				if (methodAction.equals("fetchGroupMenuList")) {

					groupMenuWrapper = gson.fromJson(message, GroupMenuWrapper.class);

				}
				if (methodAction.equals("updateGroupMenuList")) {

					groupMenuWrapperArray = gson.fromJson(message, GroupMenuWrapper[].class);

				}

				if (methodAction.equals("fetchGroupMenu")) {

					groupMenuWrapper = gson.fromJson(message, GroupMenuWrapper.class);

				}

				if (methodAction.equals("insertRejectReason")) {

					rejectWrapperArray = gson.fromJson(message, RejectWrapper[].class);

				}
				if (methodAction.equals("fetchRejectReason")) {

					rejectWrapper = gson.fromJson(message, RejectWrapper.class);

				}
				if (methodAction.equals("fetchWorkflowStatus")) {

					workflowStatusWrapper = gson.fromJson(message, WorkflowStatusWrapper.class);

				}
				if (methodAction.equals("fetchLiability")) {

					liabilityWrapper = gson.fromJson(message, LiabilityWrapper.class);

				}
				if (methodAction.equals("updateLiability")) {

					liabilityWrapper = gson.fromJson(message, LiabilityWrapper.class);

				}
				if (methodAction.equals("fetchDBRLiabilities")) {

					liabilityWrapper = gson.fromJson(message, LiabilityWrapper.class);

				}
				if (methodAction.equals("fetchGraph")) {

					graphWrapper = gson.fromJson(message, GraphWrapper.class);

				}

				// --------------------CKYC---------------
				if (methodAction.equals("insertCKYCCustomer")) {

					ckycCustomerWrapper = gson.fromJson(message, CKYCCustomerWrapper.class);

				}
				if (methodAction.equals("updateCKYCCustomer")) {

					ckycCustomerWrapper = gson.fromJson(message, CKYCCustomerWrapper.class);

				}
				if (methodAction.equals("fetchCKYCCustomer")) {

					ckycCustomerWrapper = gson.fromJson(message, CKYCCustomerWrapper.class);

				}

				if (methodAction.equals("updateCKYCAddress")) {

					ckycAddressWrapper = gson.fromJson(message, CKYCAddressWrapper.class);

				}
				if (methodAction.equals("fetchCKYCAddress")) {

					ckycAddressWrapper = gson.fromJson(message, CKYCAddressWrapper.class);

				}

				if (methodAction.equals("updateCKYCContact")) {

					ckycContactWrapper = gson.fromJson(message, CKYCContactWrapper.class);

				}
				if (methodAction.equals("fetchCKYCContact")) {

					ckycContactWrapper = gson.fromJson(message, CKYCContactWrapper.class);

				}

				if (methodAction.equals("updateCKYCPOI")) {

					ckycPOIWrapper = gson.fromJson(message, CKYCPOIWrapper.class);

				}
				if (methodAction.equals("fetchCKYCPOI")) {

					ckycPOIWrapper = gson.fromJson(message, CKYCPOIWrapper.class);

				}
				if (methodAction.equals("updateCKYCRelatedPerson")) {

					ckycRelatedPersonWrapper = gson.fromJson(message, CKYCRelatedPersonWrapper.class);

				}
				if (methodAction.equals("fetchCKYCRelatedPerson")) {

					ckycRelatedPersonWrapper = gson.fromJson(message, CKYCRelatedPersonWrapper.class);

				}
				if (methodAction.equals("fetchCKYCCustomerQueue")) {

					ckycCustomerWrapper = gson.fromJson(message, CKYCCustomerWrapper.class);

				}
				if (methodAction.equals("fetchDashboardWeb")) {

					dashboardWebWrapper = gson.fromJson(message, DashboardWebWrapper.class);

				}

			}

			/*
			 * String errorCode=null; String errorDescription=null;
			 * 
			 * 
			 * 
			 * if(isSessionNew==true) { session.setAttribute("userid",paramUserid);
			 * paramSessionid=localSessionid;
			 * session.setAttribute("sessionid",paramSessionid);
			 * System.out.println("session not true :"+ localSessionid);
			 * System.out.println("session user not true:"+paramUserid); errorCode="100";
			 * errorDescription="New sessionid created"; } else {
			 * 
			 * if(localUserid.equals(paramUserid)) { System.out.println("userid valid "+
			 * paramUserid); } else { System.out.println("userid not valid "+ paramUserid);
			 * } Cookie[] cookies = request.getCookies(); if(cookies !=null){ for(Cookie
			 * cookie : cookies){
			 * 
			 * System.out.println("cookie name " + cookie.getName());
			 * System.out.println("cookie value " + cookie.getValue());
			 * if(cookie.getName().equals("userid") &&
			 * paramUserid.equals(cookie.getValue())) { paramUserid = cookie.getValue();
			 * System.out.println("cookie userid valid "+ paramUserid);
			 * 
			 * }else{ System.out.println("cookie userid not valid "+ paramUserid + " :" +
			 * cookie.getName()); } if(cookie.getName().equals("JSESSIONID") &&
			 * paramSessionid.equals(cookie.getValue())) {
			 * 
			 * paramSessionid = cookie.getValue(); System.out.println("session  valid "+
			 * paramSessionid); errorCode="101"; errorDescription="Sessionid verified";
			 * 
			 * }else{
			 * 
			 * errorCode="102"; errorDescription="Invalid sessionid";
			 * System.out.println("cookie not valid "+ cookie.getName());
			 * System.out.println("session not valid "+ paramSessionid); } } }
			 * 
			 * }
			 */

			PrintWriter out = response.getWriter();

			System.out.println("methodaction  " + methodAction);

			System.out.println("before gson :");

			if (methodAction.equals("insertLoginProfile")) {

				System.out.println("insert Login profile   " + methodAction);

				LoginProfileController loginProfileController = new LoginProfileController();

				dataArrayWrapper = (DataArrayWrapper) loginProfileController.validate(usersProfileWrapper,
						usersWrapper);

			}
			if (methodAction.equals("updateLoginProfile")) {

				UsersHelper usersHelperLoginProfile = new UsersHelper();

				System.out.println("update Login profile   " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) usersHelperLoginProfile.updateLoginProfile(usersProfileWrapper,
						usersWrapper);

			}
			if (methodAction.equals("insertPersonalDetails")) {

				// PersonalDetailsHelper personalDetailsHelper=new PersonalDetailsHelper();

				System.out.println("Insert Personal Details  " + methodAction);
				// dataArrayWrapper =
				// (DataArrayWrapper)personalDetailsHelper.insertPersonalDetails(personalDetailsWrapper);

				PersonalDetailsController personalDetailsController = new PersonalDetailsController();

				dataArrayWrapper = (DataArrayWrapper) personalDetailsController.validate(usersProfileWrapper,
						personalDetailsWrapper);

			}
			if (methodAction.equals("fetchPersonalDetails")) {

				PersonalDetailsHelper personalDetailsHelper = new PersonalDetailsHelper();

				System.out.println("fetch Personal Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) personalDetailsHelper
						.fetchPersonalDetails(personalDetailsWrapper);

			}

			if (methodAction.equals("fetchExistingCustomer")) {

				PersonalDetailsHelper personalDetailsHelper = new PersonalDetailsHelper();

				System.out.println("fetch Existing Customer " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) personalDetailsHelper
						.fetchExistingCustomer(personalDetailsWrapper);

			}

			if (methodAction.equals("updatePersonalDetails")) {

				PersonalDetailsHelper personalDetailsHelper = new PersonalDetailsHelper();

				System.out.println("Update Personal Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) personalDetailsHelper.updatePersonalDetails(usersProfileWrapper,
						personalDetailsWrapper);

			}

			if (methodAction.equals("updateApplicationStatus")) {

				// PersonalDetailsHelper personalDetailsHelper=new PersonalDetailsHelper();

				System.out.println("Update Application Status  " + methodAction);
				// dataArrayWrapper =
				// (DataArrayWrapper)personalDetailsHelper.updateApplicationStatus(usersProfileWrapper,
				// personalDetailsWrapper);

				FinalController finalController = new FinalController();

				dataArrayWrapper = (DataArrayWrapper) finalController.validate(usersProfileWrapper,
						personalDetailsWrapper);

			}
			if (methodAction.equals("fetchOnBoardQueue")) {

				PersonalDetailsController personalDetailsController = new PersonalDetailsController();

				System.out.println("fetch OnBoardQueue  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) personalDetailsController.fetchOnBoardQueue(usersProfileWrapper,
						personalDetailsWrapper);

			}

			if (methodAction.equals("updatePassportDetails")) {

				// PassportHelper passportHelper= new PassportHelper();

				PassportDetailsController passportDetailsController = new PassportDetailsController();

				System.out.println("updatePassportDetails  " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)passportHelper.updatePassportDetails(usersProfileWrapper,passportWrapper);

				dataArrayWrapper = (DataArrayWrapper) passportDetailsController.validate(usersProfileWrapper,
						passportWrapper);

			}
			if (methodAction.equals("updateContactDetails")) {

				ContactDetailsHelper contactDetailsHelper = new ContactDetailsHelper();

				System.out.println("updateContactDetails  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) contactDetailsHelper.updateContactDetails(usersProfileWrapper,
						contactDetailsWrapper);

			}
			if (methodAction.equals("updateOccupationDetails")) {

				OccupationDetailsController occupationDetailsController = new OccupationDetailsController();

				// OccupationDetailsHelper occupationDetailsHelper=new
				// OccupationDetailsHelper();

				System.out.println("updateOccupationDetails  " + methodAction);
				// dataArrayWrapper =
				// (DataArrayWrapper)occupationDetailsHelper.updateOccupationDetails(usersProfileWrapper,occupationDetailsWrapper);

				dataArrayWrapper = (DataArrayWrapper) occupationDetailsController.validate(usersProfileWrapper,
						occupationDetailsWrapper);

			}

			if (methodAction.equals("insertAddressDetails")) {

				AddressDetailsHelper addressDetailsHelper = new AddressDetailsHelper();

				System.out.println("insertAddressDetails  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) addressDetailsHelper.insertAddressDetails(addressDetailsWrapper);

			}
			if (methodAction.equals("insertAccountDetails")) {

				AccountDetailsHelper accountDetailsHelper = new AccountDetailsHelper();

				System.out.println("insert Account Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) accountDetailsHelper.insertAccountDetails(accountDetailsWrapper);

			}
			if (methodAction.equals("insertKycDetails")) {

				KycDetailsHelper kycDetailsHelper = new KycDetailsHelper();

				System.out.println("insert KYC Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycDetailsHelper.insertKycDetails(kycDetailsWrapper);

			}

			if (methodAction.equals("fetchKycDetails")) {

				KycDetailsHelper kycDetailsHelper = new KycDetailsHelper();

				System.out.println("Fetch  KYC Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycDetailsHelper.fetchKycDetails(kycDetailsWrapper);

			}

			if (methodAction.equals("updateKycDetails")) {

				// KycDetailsHelper kycDetailsHelper=new KycDetailsHelper();
				KYCDetailsController kycDetailsController = new KYCDetailsController();

				System.out.println("Update  KYC Details  " + methodAction);
				// dataArrayWrapper =
				// (DataArrayWrapper)kycDetailsHelper.updateKycDetails(kycDetailsWrapper);
				dataArrayWrapper = (DataArrayWrapper) kycDetailsController.validate(kycDetailsWrapper);

			}
			if (methodAction.equals("updateKYCTransaction")) {

				KYCTranHelper kycTranHelper = new KYCTranHelper();

				System.out.println("Update  KYC Transaction  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycTranHelper.updateKYCTransaction(kycDetailsWrapper);

			}
			if (methodAction.equals("updateKYCTransaction2")) {

				KYCTranHelper kycTranHelper = new KYCTranHelper();

				System.out.println("Update  KYC Transaction2  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycTranHelper.updateKYCTransaction2(kycDetailsWrapper);

			}
			if (methodAction.equals("fetchKYCTransaction")) {

				KYCTranHelper kycTranHelper = new KYCTranHelper();

				System.out.println("Fetch  KYC Transaction  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycTranHelper.fetchKYCTransaction(kycDetailsWrapper);

			}
			if (methodAction.equals("fetchKYCTransaction2")) {

				KYCTranHelper kycTranHelper = new KYCTranHelper();

				System.out.println("Fetch  KYC Transaction2 " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycTranHelper.fetchKYCTransaction2(kycDetailsWrapper);

			}
			if (methodAction.equals("fetchKYC2Details")) {

				KYC2DetailsHelper kyc2DetailsHelper = new KYC2DetailsHelper();

				System.out.println("Fetch  KYC 2 Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kyc2DetailsHelper.fetchKYC2Details(kycDetailsWrapper);

			}

			if (methodAction.equals("fetchKYCEDD")) {

				KYCEDDHelper kycEDDHelper = new KYCEDDHelper();

				System.out.println("Fetch  KYC Enhanced Due Diligence  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kycEDDHelper.fetchKYCEDD(kycDetailsWrapper);

			}
			if (methodAction.equals("updateKYCEDD")) {

				// KYCEDDHelper kycEDDHelper=new KYCEDDHelper();

				KYCEDDController kycEDDController = new KYCEDDController();

				System.out.println("Update  KYC Enhanced Due Diligence " + methodAction);
				// dataArrayWrapper =
				// (DataArrayWrapper)kycEDDHelper.updateKYCEDD(kycDetailsWrapper);

				dataArrayWrapper = (DataArrayWrapper) kycEDDController.validate(kycDetailsWrapper);

			}

			if (methodAction.equals("updateKYC2Details")) {

				KYC2DetailsHelper kyc2DetailsHelper = new KYC2DetailsHelper();

				System.out.println("Update  KYC 2 Details  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) kyc2DetailsHelper.updateKYC2Details(kycDetailsWrapper);

			}

			if (methodAction.equals("fetchPopoverData")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("fetch PopoverData  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.fetchPopoverData(popoverWrapper);

			}

			if (methodAction.equals("fetchTableNames")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("fetchTableNames PopoverData  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.fetchTableNames(popoverWrapper);

			}
			if (methodAction.equals("fetchMasterData")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("fetchMasterData   " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.fetchMasterData(popoverWrapper);

			}
			if (methodAction.equals("updateMasterData")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("update PopoverData  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.updateMasterData(popoverWrapper);

			}

			if (methodAction.equals("deletePopoverData")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("delete PopoverData  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.deletePopoverData(popoverWrapper);

			}

			if (methodAction.equals("fetchMultiPopoverData")) {

				PopoverHelper popoverHelper = new PopoverHelper();

				System.out.println("fetch MultiPopoverController  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) popoverHelper.fetchMultiPopoverData(popoverWrapperArray);

			}

			if (methodAction.equals("fetchEnquiry")) {

				SearchHelper searchHelper = new SearchHelper();

				System.out.println("Fetch Enquiry  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) searchHelper.fetchEnquiry(searchWrapper);

			}

			if (methodAction.equals("fetchAccounts")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetchaccounts  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchAccounts(accountsWrapper);

			}

			if (methodAction.equals("fetchDashboard")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetch dashboard  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchDashboard(dashboardWrapper);

			}

			if (methodAction.equals("fetchSpendData")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetch Spended Data  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchSpendData(spendDataWrapper);

			}

			if (methodAction.equals("fetchCreditCards")) {

				CreditCardsHelper creditCardsHelper = new CreditCardsHelper();

				System.out.println("fetchCreditCards  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardsHelper.fetchCreditCards(creditCardsWrapper);

			}

			if (methodAction.equals("fetchCreditCardTrn")) {

				CreditCardsHelper creditCardsHelper = new CreditCardsHelper();

				System.out.println("fetchCreditCardTrn  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardsHelper.fetchCreditCardTrn(creditCardTrnWrapper);

				elementObj = gson.toJsonTree(dataArrayWrapper);
				mainJsonObj.add(methodAction, elementObj);

				CreditCardsWrapper subCreditCardsWrapper = new CreditCardsWrapper();
				subCreditCardsWrapper.cifNumber = creditCardTrnWrapper.cifNumber;
				subCreditCardsWrapper.creditCardNumber = creditCardTrnWrapper.creditCardNumber;
				dataArrayWrapper = (DataArrayWrapper) creditCardsHelper.fetchCreditCards(subCreditCardsWrapper);

			}
			if (methodAction.equals("approveRecord")) {

				PersonalDetailsHelper personalDetailsHelper = new PersonalDetailsHelper();

				System.out.println("ApproveRecord  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) personalDetailsHelper.approveRecord(usersProfileWrapper,
						personalDetailsWrapper);

			}
			if (methodAction.equals("dedupEnquiry")) {

				DedupHelper dedupHelper = new DedupHelper();

				System.out.println("Dedup   " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) dedupHelper.dedupEnquiry(dedupWrapper);

			}
			if (methodAction.equals("fetchDedup")) {

				DedupHelper dedupHelper = new DedupHelper();

				System.out.println("Fetch Dedup   " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) dedupHelper.fetchDedup(dedupWrapper);

			}

			if (methodAction.equals("fetchAccountTrn")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetchAccountTrn  " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)accountsHelper.fetchAccountTrn(usersWrapper.cifNumber.trim(),accountNumber.trim());
				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchAccountTrn(accountTrnWrapper);
				elementObj = gson.toJsonTree(dataArrayWrapper);

				mainJsonObj.add(methodAction, elementObj);

				// header single account data
				AccountsWrapper subAccountsWrapper = new AccountsWrapper();
				subAccountsWrapper.cifNumber = accountTrnWrapper.cifNumber;
				subAccountsWrapper.accountNumber = accountTrnWrapper.accountNumber;
				subAccountsWrapper.accountType = "CASA";
				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchAccounts(subAccountsWrapper);

			}

			if (methodAction.equals("fetchPassportDetails")) {

				PassportHelper passportHelper = new PassportHelper();

				System.out.println("PassportHelper   " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) passportHelper.fetchPassportDetails(passportWrapper);

			}

			if (methodAction.equals("fetchOccupationDetails")) {

				OccupationDetailsHelper occupationDetailsHelper = new OccupationDetailsHelper();

				System.out.println("OccupationDetailsHelper   " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) occupationDetailsHelper
						.fetchOccupationDetails(occupationDetailsWrapper);

			}
			if (methodAction.equals("fetchContactDetails")) {

				ContactDetailsHelper contactDetailsHelper = new ContactDetailsHelper();

				System.out.println("Contact Details Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) contactDetailsHelper.fetchContactDetails(contactDetailsWrapper);

			}

			if (methodAction.equals("fetchAddressDetails")) {

				AddressDetailsHelper addressDetailsHelper = new AddressDetailsHelper();

				System.out.println("Address Details Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) addressDetailsHelper.fetchAddressDetails(addressDetailsWrapper);

			}
			if (methodAction.equals("updateAddressDetails")) {

				AddressDetailsHelper addressDetailsHelper = new AddressDetailsHelper();

				System.out.println("Address Details Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) addressDetailsHelper.updateAddressDetails(addressDetailsWrapper);

			}
			if (methodAction.equals("fetchCustomerSpending")) {

				CustomerSpendingHelper customerSpendingHelper = new CustomerSpendingHelper();

				System.out.println("fetchCustomerSpending Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) customerSpendingHelper
						.fetchCustomerSpending(customerSpendingWrapper);

			}
			if (methodAction.equals("fetchAccountDetails")) {

				AccountDetailsHelper accountDetailsHelper = new AccountDetailsHelper();

				System.out.println("fetchAccountDetails Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) accountDetailsHelper.fetchAccountDetails(accountDetailsWrapper);

			}
			if (methodAction.equals("updateAccountDetails")) {

				AccountDetailsHelper accountDetailsHelper = new AccountDetailsHelper();

				System.out.println("updateAccountDetails Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) accountDetailsHelper.updateAccountDetails(accountDetailsWrapper);

			}

			if (methodAction.equals("uploadImageDetails")) {

				/*
				 * String password = "password"; String passwordEnc =
				 * AESEncryption.encrypt(password);
				 */

				ImageDetailsHelper imageDetailsHelper = new ImageDetailsHelper();

				// Create path components to save the file
				String path = request.getParameter("destination"); // refno
				String originalFilePath = path;

				final Part filePart = request.getPart("file");
				String fileName = getSubmittedFileName(filePart);
				String fileNameSub = "";
				// final String fileName = getFileName(filePart);

				System.out.println("Original filename " + fileName);

				// int dotPos = fileName.indexOf(fileName);
				int dotPos = fileName.lastIndexOf('.');

				// System.out.println("dotPos " + dotPos);

				String fileType = "";

				if (dotPos > 0) {
					fileType = fileName.substring(dotPos + 1); // for file type after dot position
				}

				System.out.println("fileType  " + fileType);

				fileNameSub = imageDetailsWrapper.refNo + System.currentTimeMillis();
				fileName = fileNameSub + "." + fileType;

				System.out.println("Modified filename " + fileName);

				OutputStream outputStream = null;
				InputStream filecontent = null;
				OutputStream outputStreamThumbnail = null;
				InputStream filecontentThumbnail = null;
				// final PrintWriter writer = response.getWriter();

				try {

					System.out.println("path " + path);
					System.out.println("filePart " + filePart);
					System.out.println("fileName " + fileName);

					ImageDetailsWrapper imageDetailsWrapperPath = imageDetailsHelper.fetchImagePath();
					path = imageDetailsWrapperPath.imagesPath + path; // Parameter table path + refno

					// -------for Local
					if (environment.equals("LOCAL")) {

						File folderPath = new File(path);

						if (!folderPath.exists()) {
							System.out.println("creating directory: " + path);
							boolean result = false;

							try {
								folderPath.mkdirs();
								result = true;
							} catch (SecurityException se) {
								// handle it
								se.printStackTrace();
							}
							if (result) {
								System.out.println("DIR created");
							}
						}

						outputStream = new FileOutputStream(new File(path + File.separator + fileName));
						filecontent = filePart.getInputStream();

						int read = 0;
						final byte[] bytes = new byte[16384];

						while ((read = filecontent.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);

							// System.out.println("writing file " + bytes);
						}
					}
					if (environment.equals("CLOUD")) {

						
						this.uploadFile(filePart, "maabadi-270cf.appspot.com", "onboard/"+fileName);
						
					}
					// --to convert pdf to image
					if (fileType.toUpperCase().equals("PDF")) {
						System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

						PDDocument document = PDDocument.load(new File(path + File.separator + fileName));
						// PDDocument document = PDDocument.load(filePart.getInputStream());

						PDFRenderer pdfRenderer = new PDFRenderer(document);
						int pageCounter = 0;
						String imageId = imageDetailsWrapper.imageId;
						fileType = "png";

						imageDetailsWrapper.imageFileFolder = originalFilePath; // storing only refno
						imageDetailsWrapper.imageFileType = fileType;
						imageDetailsWrapper.imageUploadStatus = true;

						for (PDPage page : document.getPages()) {
							// note that the page number parameter is zero based
							BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter,
									imageDetailsWrapperPath.imagesDPI, ImageType.RGB);

							fileName = fileNameSub + "-" + (pageCounter++) + "." + fileType;

							// suffix in filename will be used as the file format
							ImageIOUtil.writeImage(bim, path + File.separator + fileName,
									imageDetailsWrapperPath.imagesDPI);

							imageDetailsWrapper.imageFileName = fileName;
							imageDetailsWrapper.imageId = imageId + pageCounter;

							System.out.println("PDF to Image Conversion fileName " + fileName);

							dataArrayWrapper = (DataArrayWrapper) imageDetailsHelper
									.uploadImageDetails(usersProfileWrapper, imageDetailsWrapper);
						}
						document.close();

					} else {

						// -----pdf to image conversion end

						imageDetailsWrapper.imageFileName = fileName;
						imageDetailsWrapper.imageFileFolder = originalFilePath; // storing only refno
						imageDetailsWrapper.imageFileType = fileType;
						imageDetailsWrapper.imageUploadStatus = true;
						// writer.println("New file " + fileName + " created at " + path);
						// LOGGER.log(Level.INFO, "File{0}being uploaded to {1}",
						// new Object[]{fileName, path});

						//System.out.println("bytes " + bytes.length);

						System.out.println("Upload Image Details Helper " + methodAction);

						dataArrayWrapper = (DataArrayWrapper) imageDetailsHelper.uploadImageDetails(usersProfileWrapper,
								imageDetailsWrapper);

					} // ---pddf condition end

					// -------create thumbnail from profile image
					/*
					 * if(imageDetailsWrapper.docID.trim().equals("DOC001") ||
					 * imageDetailsWrapper.docID.trim().equals("DOC002")) {
					 * 
					 * outputStreamThumbnail = new FileOutputStream(new File(path + File.separator
					 * +"thumbnail_"+fileName)); filecontentThumbnail= filePart.getInputStream();
					 * 
					 * int readThumbnail = 0; final byte[] bytesThumbnail = new byte[16384];
					 * 
					 * 
					 * while ((readThumbnail = filecontentThumbnail.read(bytesThumbnail)) != -1) {
					 * outputStreamThumbnail.write(bytesThumbnail, 0, readThumbnail);
					 * //System.out.println("writing file " + bytes); }
					 * 
					 * Image image = javax.imageio.ImageIO.read(new File(path + File.separator
					 * +"thumbnail_"+fileName));
					 * 
					 * int thumbWidth=70; int thumbHeight=70; //int quality=80; double thumbRatio =
					 * (double)thumbWidth/ (double)thumbHeight; int imageWidth =
					 * image.getWidth(null); int imageHeight = image.getHeight(null); double
					 * imageRatio = (double)imageWidth / (double)imageHeight; if (thumbRatio <
					 * imageRatio) { thumbHeight = (int)(thumbWidth / imageRatio); } else {
					 * thumbWidth = (int)(thumbHeight * imageRatio); }
					 * 
					 * if(imageWidth < thumbWidth && imageHeight < thumbHeight) { thumbWidth =
					 * imageWidth; thumbHeight = imageHeight; } else if(imageWidth < thumbWidth)
					 * thumbWidth = imageWidth; else if(imageHeight < thumbHeight) thumbHeight =
					 * imageHeight;
					 * 
					 * BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
					 * BufferedImage.TYPE_INT_RGB); Graphics2D graphics2D =
					 * thumbImage.createGraphics(); graphics2D.setBackground(Color.WHITE);
					 * graphics2D.setPaint(Color.WHITE); graphics2D.fillRect(0, 0, thumbWidth,
					 * thumbHeight); graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					 * RenderingHints.VALUE_INTERPOLATION_BILINEAR); graphics2D.drawImage(image, 0,
					 * 0, thumbWidth, thumbHeight, null);
					 * 
					 * javax.imageio.ImageIO.write(thumbImage, "JPG", new File(path + File.separator
					 * +"thumbnail_"+fileName));
					 * 
					 * ImageDetailsWrapper thumbnailImageWrapper = new ImageDetailsWrapper();
					 * thumbnailImageWrapper.refNo=imageDetailsWrapper.refNo;
					 * //thumbnailImageWrapper.brideGroomID=imageDetailsWrapper.brideGroomID;
					 * thumbnailImageWrapper.imageId=imageDetailsWrapper.imageId+"T";
					 * thumbnailImageWrapper.docID="DOC002";
					 * thumbnailImageWrapper.imageFileName="thumbnail_"+fileName;
					 * thumbnailImageWrapper.imageFileFolder=originalFilePath;
					 * thumbnailImageWrapper.imageUploadStatus=true;
					 * 
					 * imageDetailsHelper.uploadImageDetails(usersProfileWrapper,
					 * thumbnailImageWrapper);
					 * 
					 * System.out.println("Thumbnail details uploadImageDetails "); }
					 */
					// -------

				} catch (FileNotFoundException fne) {

					System.out.println("File Not Found ");

					fne.printStackTrace();

					// writer.println("You either did not specify a file to upload or are "
					// + "trying to upload a file to a protected or nonexistent "
					// + "location.");
					// writer.println("<br/> ERROR: " + fne.getMessage());

					// LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
					// new Object[]{fne.getMessage()});
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
					if (filecontent != null) {
						filecontent.close();
					}
					if (outputStreamThumbnail != null) {
						outputStreamThumbnail.close();
					}
					if (filecontentThumbnail != null) {
						filecontentThumbnail.close();
					}
					// if (writer != null) {
					// writer.close();
					// }
				}

			}
			if (methodAction.equals("fetchImageDetails")) {

				ImageDetailsHelper imageDetailsHelper = new ImageDetailsHelper();

				System.out.println("Fetch Image Details Helper " + methodAction);
				// File fileRead=null;
				BufferedImage img = null;
				byte[] imageInByte = null;
				// InputStream is=null;
				imageDetailsWrapper = (ImageDetailsWrapper) imageDetailsHelper.fetchImageDetails(imageDetailsWrapper);

				if (imageDetailsWrapper != null && imageDetailsWrapper.recordFound == true) {

					try {

						System.out.println("file path to fetch " + imageDetailsWrapper.imageFileFolder + File.separator
								+ imageDetailsWrapper.imageFileName);
						// System.out.println(new File(imageDetailsWrapper.imageFileFolder +
						// File.separator + imageDetailsWrapper.imageFileName).getCanonicalPath());

						if (environment.equals("LOCAL")) {

								ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
								img = ImageIO.read(new File(imageDetailsWrapper.imageFileFolder + File.separator
										+ imageDetailsWrapper.imageFileName));
		
								ImageIO.write(img, imageDetailsWrapper.imageFileType, baos);
								baos.flush();
		
								imageInByte = baos.toByteArray();
								
								baos.close();

						}
						
						if (environment.equals("CLOUD")) {

							System.out.println("inside read cloud image "+ imageDetailsWrapper.imageFileName);
							
							imageInByte=this.readFile("maabadi-270cf.appspot.com", "onboard/"+imageDetailsWrapper.imageFileName);
							
						}

						
						
						// String encodedString = Base64.getEncoder().encodeToString(imageInByte);
						String encodedString = new String(Base64.encodeBase64(imageInByte));
						mainJsonObj.addProperty("image", encodedString);


						System.out.println("Image added to JSON");
						imageDetailsWrapper.imageFoundStatus = true;

					} catch (IOException e) {

						System.out.println("Error occured during image reading in servlet");
						e.printStackTrace();
					}

					dataArrayWrapper.imageDetailsWrapper = new ImageDetailsWrapper[1];
					dataArrayWrapper.imageDetailsWrapper[0] = imageDetailsWrapper;
					dataArrayWrapper.recordFound = true;

					System.out.println("Fetch Image success in servlet ");

				}

			}

			if (methodAction.equals("updateImageStatus")) {

				ImageDetailsHelper imageDetailsHelper = new ImageDetailsHelper();

				System.out.println("Image Details Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) imageDetailsHelper.updateImageStatus(imageDetailsWrapper);

			}
			if (methodAction.equals("fetchImageFileNames")) {

				ImageDetailsHelper imageDetailsHelper = new ImageDetailsHelper();

				System.out.println("Image filenames  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) imageDetailsHelper.fetchImageFileNames(imageDetailsWrapper);

			}
			if (methodAction.equals("fetchPathStatus")) {

				PathStatusHelper pathStatusHelper = new PathStatusHelper();
				System.out.println("fetch PathStatus " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) pathStatusHelper.fetchPathStatus(pathStatusWrapper);

			}

			if (methodAction.equals("insertAutoLoans")) {

				AutoLoansHelper autoLoansHelper = new AutoLoansHelper();

				System.out.println("Insert AutoLoans Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoansHelper.insertAutoLoans(autoLoansWrapper);

			}
			if (methodAction.equals("updateAutoLoans")) {

				AutoLoansHelper autoLoansHelper = new AutoLoansHelper();

				System.out.println("Update AutoLoans Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoansHelper.updateAutoLoans(autoLoansWrapper);

			}
			if (methodAction.equals("updateAutoLoans2")) {

				// AutoLoans2Helper autoLoans2Helper=new AutoLoans2Helper();
				AutoLoans2Controller autoLoans2Controller = new AutoLoans2Controller();

				System.out.println("Update AutoLoans2 Helper " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)autoLoans2Helper.updateAutoLoans2(autoLoansWrapper);
				dataArrayWrapper = (DataArrayWrapper) autoLoans2Controller.validate(autoLoansWrapper);

			}
			if (methodAction.equals("updateAutoLoans3")) {

				AutoLoans2Helper autoLoans2Helper = new AutoLoans2Helper();

				System.out.println("Update AutoLoans3 Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoans2Helper.updateAutoLoans3(autoLoansWrapper);

			}
			if (methodAction.equals("fetchAutoLoans")) {

				AutoLoansHelper autoLoansHelper = new AutoLoansHelper();

				System.out.println("Fetch AutoLoans Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoansHelper.fetchAutoLoans(autoLoansWrapper);

			}
			if (methodAction.equals("fetchAutoLoans2")) {

				AutoLoans2Helper autoLoans2Helper = new AutoLoans2Helper();

				System.out.println("Fetch AutoLoans2 Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoans2Helper.fetchAutoLoans2(autoLoansWrapper);

			}
			if (methodAction.equals("fetchAutoLoans3")) {

				AutoLoans2Helper autoLoans2Helper = new AutoLoans2Helper();

				System.out.println("Fetch AutoLoans3 Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) autoLoans2Helper.fetchAutoLoans3(autoLoansWrapper);

			}

			if (methodAction.equals("insertPersonalLoan")) {

				PersonalLoanHelper personalLoanHelper = new PersonalLoanHelper();

				System.out.println("Insert Personal Loan Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) personalLoanHelper.insertPersonalLoan(personalLoanWrapper);

			}

			if (methodAction.equals("fetchPersonalLoan")) {

				PersonalLoanHelper personalLoanHelper = new PersonalLoanHelper();

				System.out.println("Fetch Personal Loan Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) personalLoanHelper.fetchPersonalLoan(personalLoanWrapper);

			}

			if (methodAction.equals("updatePersonalLoan")) {

				PersonalLoanHelper personalLoanHelper = new PersonalLoanHelper();

				System.out.println("Update Personal Loan Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) personalLoanHelper.updatePersonalLoan(personalLoanWrapper);

			}

			if (methodAction.equals("fetchPersonalLoan2")) {

				PersonalLoanHelper personalLoanHelper = new PersonalLoanHelper();

				System.out.println("Fetch Personal Loan 2 Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) personalLoanHelper.fetchPersonalLoan2(personalLoanWrapper);

			}

			if (methodAction.equals("updatePersonalLoan2")) {

				// PersonalLoanHelper personalLoanHelper=new PersonalLoanHelper();

				PersonalLoanController personalLoanController = new PersonalLoanController();

				System.out.println("Update Personal Loan 2 Helper " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)personalLoanHelper.updatePersonalLoan2(personalLoanWrapper);

				dataArrayWrapper = (DataArrayWrapper) personalLoanController.validate(personalLoanWrapper);

			}

			if (methodAction.equals("insertRoD")) {

				RevolvingOverdraftHelper revolvingOverdraftHelper = new RevolvingOverdraftHelper();

				System.out.println("Insert RoD Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) revolvingOverdraftHelper.insertRoD(revolvingOverdraftWrapper);

			}
			if (methodAction.equals("fetchRoD")) {

				RevolvingOverdraftHelper revolvingOverdraftHelper = new RevolvingOverdraftHelper();

				System.out.println("Fetch RoD Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) revolvingOverdraftHelper.fetchRoD(revolvingOverdraftWrapper);

			}
			if (methodAction.equals("updateRoD")) {

				RevolvingOverdraftHelper revolvingOverdraftHelper = new RevolvingOverdraftHelper();

				System.out.println("update RoD Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) revolvingOverdraftHelper.updateRoD(revolvingOverdraftWrapper);

			}

			if (methodAction.equals("fetchDocChecklist")) {

				DocChecklistHelper docChecklistHelper = new DocChecklistHelper();

				System.out.println("DocChecklist Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) docChecklistHelper.fetchDocChecklist(docChecklistWrapper);

			}

			if (methodAction.equals("updateDocChecklist")) {

				DocChecklistHelper docChecklistHelper = new DocChecklistHelper();

				System.out.println("DocChecklist Helper " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) docChecklistHelper.updateDocChecklist(docChecklistWrapperArray);

			}

			if (methodAction.equals("insertCreditCard")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("insert Credit Card  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.insertCreditCard(creditCardWrapper);

			}

			if (methodAction.equals("fetchCreditCard")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("fetch Credit Card  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.fetchCreditCard(creditCardWrapper);

			}
			if (methodAction.equals("fetchCreditCardCC")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("fetch Credit Card CC  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.fetchCreditCardCC(creditCardWrapper);

			}
			if (methodAction.equals("fetchCreditCard2")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("fetch Credit Card 2  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.fetchCreditCard2(creditCardWrapper);

			}
			if (methodAction.equals("fetchCreditCard3")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("fetch Credit Card 3 " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.fetchCreditCard3(creditCardWrapper);

			}
			if (methodAction.equals("updateCreditCard")) {

				// CreditCardHelper creditCardHelper = new CreditCardHelper();

				CreditCardController creditCardController = new CreditCardController();

				System.out.println("update Credit Card  " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)creditCardHelper.updateCreditCard(creditCardWrapper);
				dataArrayWrapper = (DataArrayWrapper) creditCardController.validate(creditCardWrapper);

			}
			if (methodAction.equals("updateCreditCardCC")) {

				// CreditCardHelper creditCardHelper = new CreditCardHelper();

				CreditCardCCController creditCardCCController = new CreditCardCCController();

				System.out.println("update Credit Card CC  " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)creditCardHelper.updateCreditCard(creditCardWrapper);
				dataArrayWrapper = (DataArrayWrapper) creditCardCCController.validate(creditCardWrapper);

			}
			if (methodAction.equals("updateCreditCard2")) {

				// CreditCardHelper creditCardHelper = new CreditCardHelper();
				CreditCard2Controller creditCard2Controller = new CreditCard2Controller();

				System.out.println("update Credit Card 2 " + methodAction);

				// dataArrayWrapper =
				// (DataArrayWrapper)creditCardHelper.updateCreditCard2(creditCardWrapper);
				dataArrayWrapper = (DataArrayWrapper) creditCard2Controller.validate(creditCardWrapper);

			}

			if (methodAction.equals("updateCreditCard3")) {

				CreditCardHelper creditCardHelper = new CreditCardHelper();

				System.out.println("update Credit Card 3 " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) creditCardHelper.updateCreditCard3(creditCardWrapper);

			}
			if (methodAction.equals("fetchLoans")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetchLoans  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchAccounts(accountsWrapper);

			}

			if (methodAction.equals("fetchDeposits")) {

				AccountsHelper accountsHelper = new AccountsHelper();

				System.out.println("fetch deposits  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) accountsHelper.fetchAccounts(accountsWrapper);

			}

			if (methodAction.equals("insertOTP")) {

				OtpHelper otpHelper = new OtpHelper();

				System.out.println("insert otp  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) otpHelper.insertOTP(otpWrapper);

			}
			if (methodAction.equals("validateOTP")) {

				OtpHelper otpHelper = new OtpHelper();

				System.out.println("validate otp  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) otpHelper.validateOTP(otpWrapper);

			}

			if (methodAction.equals("fetchPasswordPolicy")) {

				usersHelper = new UsersHelper();

				System.out.println("Fetch Password Policy " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) usersHelper.fetchPasswordPolicy(passwordWrapper);

			}

			if (methodAction.equals("changePassword")) {

				usersHelper = new UsersHelper();

				System.out.println("Change Password  " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) usersHelper.changePassword(usersWrapper);

			}

			/*
			 * if (methodAction.equals("fetchOnBoardSearch")) {
			 * 
			 * 
			 * PersonalDetailsHelper personalDetailsHelper=new PersonalDetailsHelper();
			 * 
			 * 
			 * System.out.println("SearchOnBoardQueue  " + methodAction); dataArrayWrapper =
			 * (DataArrayWrapper)personalDetailsHelper.fetchOnBoardSearch(
			 * usersProfileWrapper,personalDetailsWrapper);
			 * 
			 * 
			 * 
			 * }
			 */

			if (methodAction.equals("fetchFATCA")) {

				FatcaHelper fatcaHelper = new FatcaHelper();

				System.out.println("fetca fatca method action " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) fatcaHelper.fetchFATCA(fatcaWrapper);

			}

			if (methodAction.equals("insertFATCA")) {

				FatcaHelper fatcaHelper = new FatcaHelper();

				System.out.println("fetca fatca method action " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) fatcaHelper.insertFATCA(fatcaWrapper);

			}

			if (methodAction.equals("updateFATCA")) {

				FatcaHelper fatcaHelper = new FatcaHelper();

				System.out.println("fetca fatca method action " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) fatcaHelper.updateFATCA(fatcaWrapper);

			}

			if (methodAction.equals("loanCalculator")) {

				LoanCalculatorController loanCalculatorController = new LoanCalculatorController();

				System.out.println("loanCalculator method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) loanCalculatorController.connectBRMS(loanCalculatorWrapper);

			}
			if (methodAction.equals("fetchUsersList")) {

				UsersHelper usersHelperFetch = new UsersHelper();

				System.out.println("fetchUsersList method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) usersHelperFetch.fetchUsersList(usersWrapper);

			}
			if (methodAction.equals("fetchUserGroupList")) {

				UserGroupHelper userGroupHelper = new UserGroupHelper();

				System.out.println("fetchUserGroup method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) userGroupHelper.fetchUserGroupList(userGroupWrapper);

			}
			if (methodAction.equals("updateUserGroupList")) {

				UserGroupHelper userGroupHelper = new UserGroupHelper();

				System.out.println("updateUserGroup method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) userGroupHelper.updateUserGroupList(usersProfileWrapper,
						userGroupWrapperArray);

			}
			if (methodAction.equals("fetchUserMenuList")) {

				UserMenuHelper userMenuHelper = new UserMenuHelper();

				System.out.println("fetchUserMenuList method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) userMenuHelper.fetchUserMenuList(userMenuWrapper);

			}
			if (methodAction.equals("updateUserMenuList")) {

				UserMenuHelper userMenuHelper = new UserMenuHelper();

				System.out.println("updateUserMenuList method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) userMenuHelper.updateUserMenuList(usersProfileWrapper,
						userMenuWrapperArray);

			}

			if (methodAction.equals("fetchGroupMenuList")) {

				GroupMenuHelper groupMenuHelper = new GroupMenuHelper();

				System.out.println("fetchGroupMenuList method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) groupMenuHelper.fetchGroupMenuList(groupMenuWrapper);

			}
			if (methodAction.equals("updateGroupMenuList")) {

				GroupMenuHelper groupMenuHelper = new GroupMenuHelper();

				System.out.println("updateGroupMenuList method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) groupMenuHelper.updateGroupMenuList(usersProfileWrapper,
						groupMenuWrapperArray);

			}

			if (methodAction.equals("fetchGroupMenu")) {

				GroupMenuHelper groupMenuHelper = new GroupMenuHelper();

				System.out.println("fetchGroupMenu method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) groupMenuHelper.fetchGroupMenu(groupMenuWrapper);

			}

			if (methodAction.equals("insertRejectReason")) {

				RejectHelper rejectHelper = new RejectHelper();

				System.out.println("insertRejectReason method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) rejectHelper.insertRejectReason(usersProfileWrapper,
						rejectWrapperArray);

			}

			if (methodAction.equals("fetchRejectReason")) {

				RejectHelper rejectHelper = new RejectHelper();

				System.out.println("fetchRejectReason method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) rejectHelper.fetchRejectReason(rejectWrapper);

			}

			if (methodAction.equals("fetchWorkflowStatus")) {

				WorkflowStatusHelper workflowStatusHelper = new WorkflowStatusHelper();

				System.out.println("fetchWorkflowStatus method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) workflowStatusHelper.fetchWorkflowStatus(workflowStatusWrapper);

			}

			if (methodAction.equals("fetchLiability")) {

				LiabilityHelper liabilityHelper = new LiabilityHelper();

				System.out.println("fetchLiability method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) liabilityHelper.fetchLiability(usersProfileWrapper,
						liabilityWrapper);

			}

			if (methodAction.equals("updateLiability")) {

				LiabilityHelper liabilityHelper = new LiabilityHelper();

				System.out.println("updateLiability method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) liabilityHelper.updateLiability(usersProfileWrapper,
						liabilityWrapper);

			}

			if (methodAction.equals("fetchDBRLiabilities")) {

				LiabilityHelper liabilityHelper = new LiabilityHelper();

				System.out.println("fetchDBRLiabilities method action " + methodAction);

				liabilityWrapper = (LiabilityWrapper) liabilityHelper.fetchDBRLiabilities(liabilityWrapper);

				DBRCalculatorController dbrCalculatorController = new DBRCalculatorController();
				dataArrayWrapper = (DataArrayWrapper) dbrCalculatorController.connectBRMS(liabilityWrapper);

			}
			if (methodAction.equals("fetchGraph")) {

				GraphHelper graphHelper = new GraphHelper();

				System.out.println("Graph method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) graphHelper.fetchGraph(graphWrapper);

			}

			// -------------------------CKYC------------------------
			if (methodAction.equals("insertCKYCCustomer")) {

				CKYCCustomerHelper ckycCustomerHelper = new CKYCCustomerHelper();

				System.out.println("insertCKYCCustomer method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycCustomerHelper.insertCKYCCustomer(usersProfileWrapper,
						ckycCustomerWrapper);

			}
			if (methodAction.equals("updateCKYCCustomer")) {

				CKYCCustomerHelper ckycCustomerHelper = new CKYCCustomerHelper();

				System.out.println("updateCKYCCustomer method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycCustomerHelper.updateCKYCCustomer(usersProfileWrapper,
						ckycCustomerWrapper);

			}
			if (methodAction.equals("fetchCKYCCustomer")) {

				CKYCCustomerHelper ckycCustomerHelper = new CKYCCustomerHelper();

				System.out.println("fetchCKYCCustomer method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycCustomerHelper.fetchCKYCCustomer(ckycCustomerWrapper);

			}
			if (methodAction.equals("updateCKYCAddress")) {

				CKYCAddressHelper ckycAddressHelper = new CKYCAddressHelper();

				System.out.println("updateCKYCAddress method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycAddressHelper.updateCKYCAddress(usersProfileWrapper,
						ckycAddressWrapper);

			}
			if (methodAction.equals("fetchCKYCAddress")) {

				CKYCAddressHelper ckycAddressHelper = new CKYCAddressHelper();

				System.out.println("fetchCKYCAddress method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycAddressHelper.fetchCKYCAddress(ckycAddressWrapper);

			}
			if (methodAction.equals("updateCKYCContact")) {

				CKYCContactHelper ckycContactsHelper = new CKYCContactHelper();

				System.out.println("updateCKYCContact method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycContactsHelper.updateCKYCContact(usersProfileWrapper,
						ckycContactWrapper);

			}
			if (methodAction.equals("fetchCKYCContact")) {

				CKYCContactHelper ckycContactsHelper = new CKYCContactHelper();

				System.out.println("fetchCKYCContact method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycContactsHelper.fetchCKYCContact(ckycContactWrapper);

			}

			if (methodAction.equals("updateCKYCPOI")) {

				CKYCPOIHelper ckycPOIHelper = new CKYCPOIHelper();

				System.out.println("updateCKYCAddress method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycPOIHelper.updateCKYCPOI(usersProfileWrapper, ckycPOIWrapper);

			}
			if (methodAction.equals("fetchCKYCPOI")) {

				CKYCPOIHelper ckycPOIHelper = new CKYCPOIHelper();

				System.out.println("fetchCKYCPOI method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycPOIHelper.fetchCKYCPOI(ckycPOIWrapper);

			}
			if (methodAction.equals("updateCKYCRelatedPerson")) {

				CKYCRelatedPersonHelper ckycRelatedPersonHelper = new CKYCRelatedPersonHelper();

				System.out.println("updateCKYCRelatedPerson method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycRelatedPersonHelper
						.updateCKYCRelatedPerson(usersProfileWrapper, ckycRelatedPersonWrapper);

			}
			if (methodAction.equals("fetchCKYCRelatedPerson")) {

				CKYCRelatedPersonHelper ckycRelatedPersonHelper = new CKYCRelatedPersonHelper();

				System.out.println("fetchCKYCRelatedPerson method action " + methodAction);

				dataArrayWrapper = (DataArrayWrapper) ckycRelatedPersonHelper
						.fetchCKYCRelatedPerson(ckycRelatedPersonWrapper);

			}
			if (methodAction.equals("fetchCKYCCustomerQueue")) {

				CKYCCustomerHelper ckycCustomerHelper = new CKYCCustomerHelper();

				System.out.println("fetch CKYCCustomerQueue  " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) ckycCustomerHelper.fetchCKYCCustomerQueue(usersProfileWrapper,
						ckycCustomerWrapper);

			}
			if (methodAction.equals("fetchDashboardWeb")) {

				DashboardHelper dashboardHelper = new DashboardHelper();

				System.out.println("fetchDashboardWeb " + methodAction);
				dataArrayWrapper = (DataArrayWrapper) dashboardHelper.fetchDashboardWeb(usersProfileWrapper,
						dashboardWebWrapper);

			}
			elementObj = gson.toJsonTree(dataArrayWrapper);

			mainJsonObj.add(methodAction, elementObj);

			if (dataArrayWrapper.recordFound == true) {
				mainJsonObj.addProperty("success", true);
			} else {
				mainJsonObj.addProperty("success", false);
			}

			mainJsonObj.addProperty("errorCode", errorCode);
			mainJsonObj.addProperty("errorDescription", errorDescription);
			mainJsonObj.addProperty("userid", usersProfileWrapper.userid);
			// mainJsonObj.addProperty("password",paramPassword);
			// mainJsonObj.addProperty("sessionid",paramSessionid);
			// mainJsonObj.addProperty("cifNumber",usersWrapper.cifNumber);

			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Max-Age", "86400");

			System.out.println("user id is: " + usersProfileWrapper.userid);
			System.out.println("Before out method action is: " + methodAction);
			// System.out.println("mainJsonObj is: "+ mainJsonObj.toString());

			out.println(mainJsonObj.toString());
			out.flush();
			out.close();

			if (methodAction.equals("fetchImageDetails")) {
				mainJsonObj.remove("image");
				System.out.println("image removed from user audit");

				usersHelper.updateUserAudit(usersProfileWrapper.userid, localSessionid, methodAction, "OnBoard",
						mainJsonObj.toString());
			} else {
				usersHelper.updateUserAudit(usersProfileWrapper.userid, localSessionid, methodAction, "OnBoard",
						mainJsonObj.toString());
			}

			System.out.println("out close");

		} catch (JsonParseException jse) {
			jse.printStackTrace();
			System.out.println("parse exc " + jse.getMessage());

		}

		catch (Exception ex) {
			ex.printStackTrace();
			System.out.print("main exc " + ex.getMessage());
		}
	}

//	private String getFileName(final Part part) {
//		final String partHeader = part.getHeader("content-disposition");
//		// Logger.log(Level.INFO, "Part Header = {0}", partHeader);
//		for (String content : part.getHeader("content-disposition").split(";")) {
//			System.out.println("content filename " + content);
//			if (content.trim().startsWith("filename")) {
//				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
//			}
//		}
//		return null;
//	}

	private static String getSubmittedFileName(Part part) {

		System.out.println("part header " + part.getHeader("content-disposition"));

		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {

				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");

				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}

	/**
	 * Uploads a file to Google Cloud Storage to the bucket specified in the
	 * BUCKET_NAME environment variable, appending a timestamp to end of the
	 * uploaded filename.
	 */
	@SuppressWarnings("deprecation")
	public String uploadFile(Part filePart, final String bucketName, final String fileName) throws IOException {
		// DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
		// DateTime dt = DateTime.now(DateTimeZone.UTC);
		// String dtString = dt.toString(dtf);
		// final String fileName = filePart.getSubmittedFileName() + dtString;

		Storage storage;
		storage = StorageOptions.getDefaultInstance().getService();
		
//		BufferedImage originalImage = ImageIO.read(filePart.getInputStream());
//	    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
//	        : originalImage.getType();
//
//	    System.out.println("upload file before resize ");
		
		// the inputstream is closed by default, so we don't need to close it here
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
				// Modify access list to allow all users with link to read file
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
				filePart.getInputStream()); //filePart.getInputStream();
		// return the public download link
		return blobInfo.getMediaLink();
	}
	

	//read data from google cloud storage
	public byte[] readFile(final String bucketName, final String fileName) throws IOException {

		Storage storage;
		byte[] fileData=null;

		try {
			
			storage = StorageOptions.getDefaultInstance().getService();

			fileData= storage.readAllBytes(bucketName, fileName);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}


		return fileData;
		
	}
	
	
//	private static InputStream resizeImage(BufferedImage originalImage, int type) {
//		
//	    int IMG_WIDTH = 512;
//	    int IMG_CLAHEIGHT = 512;
//	    
//	    InputStream inputStream=null;
//	    
//	    try {
//	    	
//			    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_CLAHEIGHT,
//			        type);
//			    Graphics2D g = resizedImage.createGraphics();
//			    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_CLAHEIGHT, null);
//			    g.dispose();
//			    
//			    ByteArrayOutputStream os = new ByteArrayOutputStream();
//			    ImageIO.write(resizedImage, "png" , os);
//			    inputStream = new ByteArrayInputStream(os.toByteArray());
//			    
//			    System.out.println("image resized ");
//	    }
//	    catch(Exception ex) {
//	    	ex.printStackTrace();
//	    }
//	    
//	    return inputStream;
//	  }
}
