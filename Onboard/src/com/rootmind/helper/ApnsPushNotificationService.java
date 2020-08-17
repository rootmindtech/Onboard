package com.rootmind.helper;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.notnoop.*;
import com.notnoop.apns.*;
import com.notnoop.apns.internal.*;
import com.notnoop.exceptions.*;


//@Service
//@Qualifier("Apns")

public class ApnsPushNotificationService {

	
/*	 private Resource certResource;
	 private String certPassword;
	 private boolean useProductionServer;
	 private String serverAddress;
	 private static final int DEFAULT_APNS_PORT = 2195;
	 public void setCertResource(Resource certResource) {
	  this.certResource = certResource;
	 }
	 public void setCertPassword(String certPassword) {
	  this.certPassword = certPassword;
	 }
	 public void setUseProductionServer(boolean useProductionServer) {
	  this.useProductionServer = useProductionServer;
	 }
	 public void setServerAddress(String serverAddress) {
	  this.serverAddress = serverAddress;
	 }
	 public void pushNotification(Notification notification) {
	  List<Notification> notList = new ArrayList<Notification>();
	  notList.add(notification);
	  pushNotifications(notList);
	 }
	 public void pushNotifications(List<Notification> notifications) {
	  ApnsService service = createApnsService();
	  service.start();
	  for (Notification notification : notifications) {
	   doSendNotification(notification, service);
	  }
	  service.stop();
	 }
	 private ApnsService createApnsService() {
	  ApnsService service = null;
	  ApnsServiceBuilder serviceBuilder = APNS.newService()
	    .withCert(certResource.getInputStream(), certPassword);
	  if (null != serverAddress && serverAddress.length() > 0) {
	   serviceBuilder.withGatewayDestination(serverAddress,
	     DEFAULT_APNS_PORT);
	  } else if (useProductionServer) {
	   serviceBuilder.withProductionDestination();
	  } else {
	   serviceBuilder.withSandboxDestination();
	  }
	  service = serviceBuilder.build();
	  return service;
	 }
	 private void doSendNotification(Notification notification,
	   ApnsService service) {
	  PayloadBuilder payloadBuilder = APNS.newPayload();
	  payloadBuilder = payloadBuilder.badge(notification.getBadge());
	  payloadBuilder = payloadBuilder.sound(notification.getSound());
	  if (notification.getBody() != null) {
	   payloadBuilder = payloadBuilder.alertBody(notification.getBody());
	  }
	  String payload = payloadBuilder.build();
	  service.push(notification.getDeviceToken(), payload);
	 }*/

	
	public void pushMessage(String userid, String deviceToken,String message) {
        ApnsService service = null;
        try {
            // get the certificate
            InputStream certStream = this.getClass().getClassLoader().getResourceAsStream("techeliteidentity.p12");
            service = APNS.newService().withCert(certStream, "elitechat@123").withSandboxDestination().build();
            
            
/*            final InputStream certificate = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("TechEliteCredentials.p12");
            final char[] passwd = {'e','l','i','t','e','c','h','a','t','@','1','2','3'};
            service = com.notnoop.apns.APNS.newService()
                    .withCert(certificate, new String(passwd))
                    .withSandboxDestination().build();
            service.testConnection();*/
            
            
            // or
            // service = APNS.newService().withCert(certStream,
            // "your_cert_password").withProductionDestination().build();
            service.start();
            
            // You have to delete the devices from you list that no longer 
            //have the app installed, see method below
            //deleteInactiveDevices(service);
            // read your user list
            //List<User> userList = userDao.readUsers();
            //for (User user : userList) {
                try {
                    // we had a daily update here, so we need to know how many 
                    //days the user hasn't started the app
                    // so that we get the number of updates to display it as the badge.
                    int days = 1;//(int) ((System.currentTimeMillis() - user.getLastUpdate()) / 1000 / 60 / 60 / 24);
                    PayloadBuilder payloadBuilder = APNS.newPayload();
                    payloadBuilder = payloadBuilder.badge(days).alertBody(message);
                    // check if the message is too long (it won't be sent if it is) 
                    //and trim it if it is.
                    if (payloadBuilder.isTooLong()) {
                        payloadBuilder = payloadBuilder.shrinkBody();
                    }
                    String payload = payloadBuilder.build();
                    //String token = user.getToken();
                    System.out.println("device token before push "+ deviceToken);
                    System.out.println("payload before push "+ payload);
                    
                    service.push(deviceToken, payload);
                } catch (Exception ex) {
                    // some logging stuff
                //}
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
            // more logging
        } finally {
            // check if the service was successfull initialized and stop it here, if it was
            if (service != null) {
                service.stop();
            }
 
        }
    }
 
    //private void deleteInactiveDevices(ApnsService service) {
        // get the list of the devices that no longer have your app installed from apple
        //ignore the ="" after Date here, it's a bug...
       // Map<String, Date> inactiveDevices = service.getInactiveDevices();
        //for (String deviceToken : inactiveDevices.keySet()) {
         //   userDao.deleteByDeviceId(deviceToken);
        //}
 
   // }

}
