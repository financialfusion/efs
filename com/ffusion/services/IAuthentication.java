package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credential;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.services.authentication.AuthException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface IAuthentication
{
  public static final String SERVICE_INIT_XML = "authentication.xml";
  public static final String AUTHENTICATION_RESOURCE_BUNDLE = "com.ffusion.beans.authentication.resources";
  public static final String AUTH_SETTINGS = "AUTH_SETTINGS";
  public static final String AUTH_SETTING = "AUTH_SETTING";
  public static final String AUTH_SETTING_NAME = "NAME";
  public static final String AUTH_SETTING_VALUE = "VALUE";
  
  public abstract void initialize(HashMap paramHashMap)
    throws AuthException;
  
  public abstract ArrayList getSecrets(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws AuthException;
  
  public abstract Credentials getCredentialRequests(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws AuthException;
  
  public abstract void validateCredentials(SecureUser paramSecureUser, Credentials paramCredentials, HashMap paramHashMap)
    throws AuthException;
  
  public abstract Credential getScratchCardCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException;
  
  public abstract Credential getTokenCardCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException;
  
  public abstract Credential getChallengeCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException;
  
  public abstract void validateScratchCardCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException;
  
  public abstract void validateTokenCardCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException;
  
  public abstract void validateChallengeCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IAuthentication
 * JD-Core Version:    0.7.0.1
 */