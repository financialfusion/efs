package com.ffusion.beans.business;

public abstract interface BusinessDefines
{
  public static final String MARKETSEGMENTNAME = "MARKETSEGMENTNAME";
  public static final String SERVICESADMINGROUPID = "SERVICESADMINGROUPID";
  public static final String SERVICESPACKAGENAME = "SERVICESPACKAGENAME";
  public static final String PROP_DESCRIPTION = "Description";
  public static final String PROP_AFFILIATEBANKID = "AffiliateBankId";
  public static final String PROP_AUTH_LOGIN_TOKEN = "AuthLoginToken";
  public static final String PROP_AUTH_LOGIN_SCRATCH = "AuthLoginScratch";
  public static final String PROP_AUTH_LOGIN_CHALLENGE = "AuthLoginChallenge";
  public static final String PROP_AUTH_TRANS_TOKEN = "AuthTransToken";
  public static final String PROP_AUTH_TRANS_SCRATCH = "AuthTransScratch";
  public static final String PROP_AUTH_TRANS_CHALLENGE = "AuthTransChallenge";
  public static final String PROP_AUTH_PRIV_TOKEN = "AuthPrivToken";
  public static final String PROP_AUTH_PRIV_SCRATCH = "AuthPrivScratch";
  public static final String PROP_AUTH_PRIV_CHALLENGE = "AuthPrivChallenge";
  public static final String NOT_SELECTED = "0";
  public static final String SELECTED = "1";
  public static final String ENROLL = "0";
  public static final String ACTIVE = "1";
  public static final String INACTIVE = "2";
  public static final String CC_ENROLL = "4";
  public static final String DELETED = "8";
  public static final String REJECTED = "16";
  public static final String IN_PROCESS = "32";
  public static final String PPAY_PAY = "Pay";
  public static final String PPAY_RETURN = "Return";
  public static final String PPAY_NONE = "None";
  public static final int ACH_DEFAULT_CREDIT_LEAD_DAYS = 2;
  public static final int ACH_DEFAULT_DEBIT_LEAD_DAYS = 1;
  public static final int ACH_LOWER_BOUND_LEAD_DAYS = 1;
  public static final int ACH_UPPER_BOUND_LEAD_DAYS = 5;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.BusinessDefines
 * JD-Core Version:    0.7.0.1
 */