package com.rootmind.helper;

import com.rootmind.wrapper.AbstractWrapper;

public class PasswordWrapper extends AbstractWrapper {
	
	public String minLength=null;
	public String maxLength=null;
	public String capitalLetter=null;
	public String specialCharacter=null;
	public String oldPasswordRepeat=null;
	public boolean policyAvailable=false; 
	public boolean recordFound=false;
}