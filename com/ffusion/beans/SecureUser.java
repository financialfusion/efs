package com.ffusion.beans;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;

public class SecureUser
  extends ExtendABean
  implements Serializable, CollectionElement
{
  public static final String SECUREUSER = "SECUREUSER";
  public static final String ID = "ID";
  public static final String PASSWORD = "PASSWORD";
  public static final String USERNAME = "USERNAME";
  public static final String BANKID = "BANKID";
  public static final String AFFILIATEID = "AFFILIATEID";
  public static final String BPWFIID = "BPWFIID";
  public static final String PRIMARYUSERID = "PRIMARYUSERID";
  public static final String PROFILEID = "PROFILEID";
  public static final String ENTITLEID = "ENTITLEID";
  public static final String BUSINESSID = "BUSINESSID";
  public static final String BUSINESSNAME = "BUSINESSNAME";
  public static final String BUSINESSCIF = "BUSINESSCIF";
  public static final String USERTYPE = "USERTYPE";
  public static final String VIEWONLYOBO = "VIEWONLYOBO";
  public static final String AGENT = "AGENT";
  public static final String BASECURRENCY = "BASECURRENCY";
  public static final String BUSINESSCUSTID = "BUSINESSCUSTID";
  public static final String AUTHENTICATE = "AUTHENTICATE";
  public static final String ISAUTHENTICATED = "ISAUTHENTICATED";
  public static final String OBO = "OBO";
  public static final int TYPE_INVALID = 0;
  public static final int TYPE_CUSTOMER = 1;
  public static final int TYPE_EMPLOYEE = 2;
  public static final int TYPE_AGENT = 3;
  protected String id;
  protected String userName;
  protected String password;
  protected String bankID;
  protected int affiliateID;
  protected String bpwFIID;
  protected int profileID;
  protected int entitleID;
  protected int businessID;
  protected String businessName;
  protected int businessStatus;
  protected String businessCIF;
  protected int userType;
  boolean qg = false;
  protected SecureUser agent;
  protected String baseCurrency;
  protected String businessCustId;
  protected int primaryUserID;
  protected String primaryUserCustID;
  protected String primaryUserProcessorPin;
  
  public SecureUser() {}
  
  public SecureUser(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void invalidate()
  {
    this.userType = 0;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
  }
  
  public void updateId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setId(String paramString)
  {
    if ((this.id != null) && (!this.id.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setId.  Cannot modify the Id value.");
      return;
    }
    if (this.id == null) {
      this.id = paramString;
    }
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void updateBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public void setBankID(String paramString)
  {
    if ((this.bankID != null) && (!this.bankID.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBankID.  Cannot modify the BankId value.");
      return;
    }
    if (this.bankID == null) {
      this.bankID = paramString;
    }
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public int getBankIDValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.bankID);
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void updateAffiliateID(String paramString)
  {
    try
    {
      this.affiliateID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void updateAffiliateID(int paramInt)
  {
    this.affiliateID = paramInt;
  }
  
  public void setAffiliateID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if ((this.affiliateID != 0) && (this.affiliateID != i))
      {
        DebugLog.log(Level.SEVERE, "SecureUser.setAffiliateID.  Cannot modify the AffiliateID value.");
        return;
      }
      if (this.affiliateID == 0) {
        this.affiliateID = i;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setAffiliateID(int paramInt)
  {
    if ((this.affiliateID != 0) && (this.affiliateID != paramInt))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setAffiliateID.  Cannot modify the AffiliateID value.");
      return;
    }
    if (this.affiliateID == 0) {
      this.affiliateID = paramInt;
    }
  }
  
  public String getAffiliateID()
  {
    return String.valueOf(this.affiliateID);
  }
  
  public int getAffiliateIDValue()
  {
    return this.affiliateID;
  }
  
  public void setBPWFIID(String paramString)
  {
    if ((this.bpwFIID != null) && (!this.bpwFIID.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBPWFIID.  Cannot modify the BPWFIID value.");
      return;
    }
    if (this.bpwFIID == null) {
      this.bpwFIID = paramString;
    }
  }
  
  public String getBPWFIID()
  {
    return this.bpwFIID;
  }
  
  public void updateProfileID(int paramInt)
  {
    this.profileID = paramInt;
  }
  
  public void setProfileID(int paramInt)
  {
    if ((this.profileID != 0) && (this.profileID != paramInt))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setProfileID.  Cannot modify the ProfileID value.");
      return;
    }
    if (this.profileID == 0) {
      this.profileID = paramInt;
    }
  }
  
  public void setProfileID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if ((this.profileID != 0) && (this.profileID != i))
      {
        DebugLog.log(Level.SEVERE, "SecureUser.setProfileID.  Cannot modify the ProfileID value.");
        return;
      }
      if (this.profileID == 0) {
        this.profileID = i;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getProfileID()
  {
    return this.profileID;
  }
  
  public void setEntitlementID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if ((this.entitleID != 0) && (this.entitleID != i))
      {
        DebugLog.log(Level.SEVERE, "SecureUser.setEntitlementID.  Cannot modify the EntitlementID value.");
        return;
      }
      if (this.entitleID == 0) {
        this.entitleID = i;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setEntitlementID(int paramInt)
  {
    if ((this.entitleID != 0) && (this.entitleID != paramInt))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setEntitlementID.  Cannot modify the EntitlementID value.");
      return;
    }
    if (this.entitleID == 0) {
      this.entitleID = paramInt;
    }
  }
  
  public int getEntitlementID()
  {
    return this.entitleID;
  }
  
  public void setPrimaryUserID(String paramString)
  {
    this.primaryUserID = new Integer(paramString).intValue();
  }
  
  public void setPrimaryUserID(int paramInt)
  {
    this.primaryUserID = paramInt;
  }
  
  public int getPrimaryUserID()
  {
    return this.primaryUserID;
  }
  
  public void setPrimaryUserCustID(String paramString)
  {
    this.primaryUserCustID = paramString;
  }
  
  public String getPrimaryUserCustID()
  {
    return this.primaryUserCustID;
  }
  
  public void setPrimaryUserProcessorPin(String paramString)
  {
    this.primaryUserProcessorPin = paramString;
  }
  
  public String getPrimaryUserProcessorPin()
  {
    return this.primaryUserProcessorPin;
  }
  
  public void setBusinessID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if ((this.businessID != 0) && (this.businessID != i))
      {
        DebugLog.log(Level.SEVERE, "SecureUser.setBusinessID.  Cannot modify the BusinessID value.");
        return;
      }
      if (this.businessID == 0) {
        this.businessID = i;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void updateBusinessID(int paramInt)
  {
    this.businessID = paramInt;
  }
  
  public void updateBusinessStatus(int paramInt)
  {
    this.businessStatus = paramInt;
  }
  
  public int getBusinessStatus()
  {
    return this.businessStatus;
  }
  
  public void setBusinessID(int paramInt)
  {
    if ((this.businessID != 0) && (this.businessID != paramInt))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBusinessID.  Cannot modify the BusinessID value.");
      return;
    }
    if (this.businessID == 0) {
      this.businessID = paramInt;
    }
  }
  
  public int getBusinessID()
  {
    return this.businessID;
  }
  
  public void updateBusinessName(String paramString)
  {
    this.businessName = paramString;
  }
  
  public void setBusinessName(String paramString)
  {
    if ((this.businessName != null) && (!this.businessName.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBusinessName.  Cannot modify the BusinessName value.");
      return;
    }
    if (this.businessName == null) {
      this.businessName = paramString;
    }
  }
  
  public String getBusinessName()
  {
    return this.businessName;
  }
  
  public void updateBusinessCIF(String paramString)
  {
    this.businessCIF = paramString;
  }
  
  public void setBusinessCIF(String paramString)
  {
    if ((this.businessCIF != null) && (!this.businessCIF.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBusinessCIF.  Cannot modify the BusinessCIF value.");
      return;
    }
    if (this.businessCIF == null) {
      this.businessCIF = paramString;
    }
  }
  
  public String getBusinessCIF()
  {
    return this.businessCIF;
  }
  
  public void updateUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public void setUserName(String paramString)
  {
    if ((this.userName != null) && (!this.userName.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setUserName.  Cannot modify the UserName value.");
      return;
    }
    if (this.userName == null) {
      this.userName = paramString;
    }
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserType(int paramInt)
  {
    if ((this.userType != 0) && (this.userType != paramInt))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setUserType.  Cannot modify the UserType value.");
      return;
    }
    if (this.userType == 0) {
      this.userType = paramInt;
    }
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if ((this.userType != 0) && (this.userType != i))
      {
        DebugLog.log(Level.SEVERE, "SecureUser.setUserType.  Cannot modify the UserType value.");
        return;
      }
      if (this.userType == 0) {
        this.userType = i;
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getUserType()
  {
    return this.userType;
  }
  
  public void setViewOnlyOBO(boolean paramBoolean)
  {
    if ((this.qg) && (this.qg != paramBoolean))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setViewOnlyOBO.  Cannot modify the ViewOnlyOBO value.");
      return;
    }
    if (!this.qg) {
      this.qg = paramBoolean;
    }
  }
  
  public void setViewOnlyOBO(String paramString)
  {
    boolean bool = Boolean.getBoolean(paramString);
    if ((this.qg) && (this.qg != bool))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setUserType.  Cannot modify the UserType value.");
      return;
    }
    if (!this.qg) {
      this.qg = bool;
    }
  }
  
  public String getViewOnlyOBO()
  {
    return String.valueOf(this.qg);
  }
  
  public boolean getViewOnlyOBOValue()
  {
    return this.qg;
  }
  
  public void updatePassword(String paramString)
  {
    this.password = paramString;
  }
  
  public void setPassword(String paramString)
  {
    if ((this.password != null) && (!this.password.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setPassword.  Cannot modify the Password value.");
      return;
    }
    if (this.password == null) {
      this.password = paramString;
    }
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setAgent(SecureUser paramSecureUser)
  {
    if ((this.agent != null) && (this.agent.getProfileID() != paramSecureUser.getProfileID()))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setAgent.  Cannot modify the Agent value.");
      return;
    }
    if (this.agent == null) {
      this.agent = paramSecureUser;
    }
  }
  
  public SecureUser getAgent()
  {
    return this.agent;
  }
  
  public void setBaseCurrency(String paramString)
  {
    this.baseCurrency = paramString;
  }
  
  public String getBaseCurrency()
  {
    return this.baseCurrency;
  }
  
  public void updateBusinessCustId(String paramString)
  {
    this.businessCustId = paramString;
  }
  
  public void setBusinessCustId(String paramString)
  {
    if ((this.businessCustId != null) && (!this.businessCustId.equals(paramString)))
    {
      DebugLog.log(Level.SEVERE, "SecureUser.setBusinessCustId.  Cannot modify the BusinessCustId value.");
      return;
    }
    if (this.businessCustId == null) {
      this.businessCustId = paramString;
    }
  }
  
  public String getBusinessCustId()
  {
    return this.businessCustId;
  }
  
  public void set(SecureUser paramSecureUser)
  {
    setId(paramSecureUser.getId());
    setUserName(paramSecureUser.getUserName());
    setUserType(paramSecureUser.getUserType());
    setPassword(paramSecureUser.getPassword());
    setEntitlementID(paramSecureUser.getEntitlementID());
    setBankID(paramSecureUser.getBankID());
    setAffiliateID(paramSecureUser.getAffiliateIDValue());
    setBPWFIID(paramSecureUser.getBPWFIID());
    setProfileID(paramSecureUser.getProfileID());
    setBusinessID(paramSecureUser.getBusinessID());
    setBusinessName(paramSecureUser.getBusinessName());
    setBusinessCIF(paramSecureUser.getBusinessCIF());
    setViewOnlyOBO(paramSecureUser.getViewOnlyOBOValue());
    if (paramSecureUser.getAgent() != null)
    {
      SecureUser localSecureUser = new SecureUser();
      localSecureUser.set(paramSecureUser.getAgent());
      setAgent(localSecureUser);
    }
    this.baseCurrency = paramSecureUser.getBaseCurrency();
    setBusinessCustId(paramSecureUser.getBusinessCustId());
    this.primaryUserID = paramSecureUser.getPrimaryUserID();
    this.primaryUserProcessorPin = paramSecureUser.getPrimaryUserProcessorPin();
    this.primaryUserCustID = paramSecureUser.getPrimaryUserCustID();
    super.set(paramSecureUser);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      setId(paramString2);
    } else if (paramString1.equals("PASSWORD")) {
      setPassword(paramString2);
    } else if (paramString1.equals("USERNAME")) {
      setUserName(paramString2);
    } else if (paramString1.equals("BANKID")) {
      setBankID(paramString2);
    } else if (paramString1.equals("AFFILIATEID")) {
      setAffiliateID(paramString2);
    } else if (paramString1.equals("BPWFIID")) {
      setBPWFIID(paramString2);
    } else if (paramString1.equals("PROFILEID")) {
      setProfileID(paramString2);
    } else if (paramString1.equals("ENTITLEID")) {
      setEntitlementID(paramString2);
    } else if (paramString1.equals("BUSINESSID")) {
      setBusinessID(paramString2);
    } else if (paramString1.equals("USERTYPE")) {
      setUserType(paramString2);
    } else if (paramString1.equals("BUSINESSNAME")) {
      setBusinessName(paramString2);
    } else if (paramString1.equals("BUSINESSCIF")) {
      setBusinessCIF(paramString2);
    } else if (paramString1.equals("VIEWONLYOBO")) {
      setViewOnlyOBO(paramString2);
    } else if (paramString1.equals("BASECURRENCY")) {
      setBaseCurrency(paramString2);
    } else if (paramString1.equals("BUSINESSCUSTID")) {
      setBusinessCustId(paramString2);
    } else if ("PRIMARYUSERID".equals(paramString1)) {
      setPrimaryUserID(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SECUREUSER");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "USERNAME", this.userName);
    XMLHandler.appendTag(localStringBuffer, "BANKID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "AFFILIATEID", this.affiliateID);
    XMLHandler.appendTag(localStringBuffer, "BPWFIID", this.bpwFIID);
    XMLHandler.appendTag(localStringBuffer, "ENTITLEID", String.valueOf(this.entitleID));
    XMLHandler.appendTag(localStringBuffer, "PROFILEID", String.valueOf(this.profileID));
    XMLHandler.appendTag(localStringBuffer, "PRIMARYUSERID", String.valueOf(this.primaryUserID));
    XMLHandler.appendTag(localStringBuffer, "BUSINESSID", String.valueOf(this.businessID));
    XMLHandler.appendTag(localStringBuffer, "BUSINESSNAME", String.valueOf(this.businessName));
    XMLHandler.appendTag(localStringBuffer, "BUSINESSCIF", String.valueOf(this.businessCIF));
    XMLHandler.appendTag(localStringBuffer, "USERTYPE", String.valueOf(this.userType));
    if (this.agent != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AGENT");
      XMLHandler.appendTag(localStringBuffer, "ID", this.agent.id);
      XMLHandler.appendTag(localStringBuffer, "USERNAME", this.agent.userName);
      XMLHandler.appendTag(localStringBuffer, "BANKID", this.agent.bankID);
      XMLHandler.appendTag(localStringBuffer, "AFFILIATEID", this.affiliateID);
      XMLHandler.appendTag(localStringBuffer, "BPWFIID", this.bpwFIID);
      XMLHandler.appendTag(localStringBuffer, "ENTITLEID", String.valueOf(this.agent.entitleID));
      XMLHandler.appendTag(localStringBuffer, "PROFILEID", String.valueOf(this.agent.profileID));
      XMLHandler.appendTag(localStringBuffer, "BUSINESSID", String.valueOf(this.agent.businessID));
      XMLHandler.appendTag(localStringBuffer, "BUSINESSNAME", String.valueOf(this.agent.businessName));
      XMLHandler.appendTag(localStringBuffer, "BUSINESSCIF", String.valueOf(this.agent.businessCIF));
      XMLHandler.appendTag(localStringBuffer, "USERTYPE", String.valueOf(this.agent.userType));
      XMLHandler.appendTag(localStringBuffer, "VIEWONLYOBO", String.valueOf(this.agent.qg));
      XMLHandler.appendEndTag(localStringBuffer, "AGENT");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "SECUREUSER");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  class InternalXMLHandler
    extends ExtendABean.InternalXMLHandler
  {
    InternalXMLHandler()
    {
      super();
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("AGENT"))
      {
        SecureUser.this.agent = new SecureUser();
        SecureUser.this.agent.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.SecureUser
 * JD-Core Version:    0.7.0.1
 */