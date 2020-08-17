package com.rootmind.helper;

import com.rootmind.wrapper.AbstractWrapper;

public class WorkflowStatusWrapper extends AbstractWrapper{
	
	
		public String refNo=null;
		public String workflowGroup=null; //SALESUSER,TEAMLEAD,BDMUSER,BOUSER,CREDITUSER
		public String status=null; //I=Inprogress; C=Complete; Q=To be received; 
		
		public String tat=null;
		
		public boolean recordFound=false;

}
