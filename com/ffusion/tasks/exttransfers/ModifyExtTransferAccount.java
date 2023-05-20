package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyExtTransferAccount
  extends ExtTransferAccount
  implements Task
{
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean modify = true;
  protected boolean isBankIdentifier = false;
  protected String fiId = null;
  protected String affiliateId = null;
  protected String numbers = null;
  protected ExtTransferAccount currentExtTransferAccount;
  protected ExtTransferAccount originalExtTransferAccount;
  protected ExtTransferCompany originalExtTransferCompany;
  protected ExtTransferCompany currentExtTransferCompany;
  protected String nextURL = null;
  protected String custID = null;
  private Properties iD = new Properties();
  private String iE = "";
  private HashMap iG = new HashMap();
  private String iF;
  private String iH;
  protected User user;
  protected String userSessionName = "User";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = modifyTransferAccount(paramHttpServletRequest, localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.nextURL = this.successURL;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)paramHttpSession.getAttribute("ExternalTransferACCOUNT");
    if (localExtTransferAccount == null)
    {
      this.error = 4206;
      this.nextURL = this.taskErrorURL;
    }
    else
    {
      set(localExtTransferAccount);
      setNumbers(localExtTransferAccount.getNumber() + ":" + localExtTransferAccount.getRoutingNumber() + ":" + localExtTransferAccount.getTypeValue());
      this.originalExtTransferAccount = new ExtTransferAccount(this.locale);
      this.originalExtTransferAccount.set(this);
    }
    this.user = ((User)paramHttpSession.getAttribute(this.userSessionName));
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    initializeLimits(localSecureUser);
    if (this.error != 0) {
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  protected String modifyTransferAccount(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
        this.prenote = false;
        while (localEnumeration.hasMoreElements())
        {
          localObject = (String)localEnumeration.nextElement();
          if (((String)localObject).toUpperCase().indexOf("PRENOTE") != -1) {
            this.prenote = true;
          }
        }
        Object localObject = new ExtTransferCompany(this.locale);
        if (((ExtTransferCompany)localObject).getCustID() == null) {
          ((ExtTransferCompany)localObject).setCustID(this.custID);
        }
        str = processModifyExtTransferAccount((ExtTransferCompany)localObject, paramHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processModifyExtTransferAccount(ExtTransferCompany paramExtTransferCompany, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      ExternalTransferAdmin.modifyExtTransferAccount(localSecureUser, this, this.originalExtTransferAccount, paramExtTransferCompany, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when adding transfer:");
      localException.printStackTrace();
    }
    if (this.error == 0)
    {
      processEntitlementsAndLimits(localSecureUser);
      paramHttpSession.setAttribute("ExternalTransferACCOUNT", this);
      this.nextURL = this.successURL;
    }
    else
    {
      this.nextURL = this.serviceErrorURL;
    }
    return this.nextURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
    }
    if (("FEDABA".equals(this.acctBankIDType)) && (!this.isBankIdentifier))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.numbers, ":");
      if ((this.recipientName == null) || (this.recipientName.trim().length() < 1) || (this.recipientName.trim().length() > 22)) {
        return setError(4212);
      }
      if ((this.recipientId != null) && (this.recipientId.trim().length() > 15)) {
        return setError(4213);
      }
      if (localStringTokenizer.countTokens() < 3) {
        return setError(4214);
      }
    }
    bool = validateLimits();
    return bool;
  }
  
  protected boolean validateLimits()
  {
    Iterator localIterator1 = this.iG.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      HashMap localHashMap = (HashMap)this.iG.get(str1);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        a locala = (a)localHashMap.get(str2);
        String str3 = null;
        if (locala != null)
        {
          str3 = locala.a();
          if ((locala.jdMethod_if()) && ((str3 == null) || (str3.length() == 0))) {
            return setError(154);
          }
        }
        if (!Currency.isValid(str3, this.locale)) {
          return setError(16117);
        }
      }
    }
    return true;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setCurrentExtTransferCompany(ExtTransferCompany paramExtTransferCompany)
  {
    this.currentExtTransferCompany = paramExtTransferCompany;
  }
  
  public void setCurrentExtTransferAccount(ExtTransferAccount paramExtTransferAccount)
  {
    this.currentExtTransferAccount = paramExtTransferAccount;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public void setNumbers(String paramString)
  {
    this.numbers = paramString;
  }
  
  public void setIsBankIdentifier(String paramString)
  {
    this.isBankIdentifier = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getIsBankIdentifier()
  {
    return String.valueOf(this.isBankIdentifier);
  }
  
  public void parseNumbers(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ":");
    this.number = localStringTokenizer.nextToken();
    this.routingNumber = localStringTokenizer.nextToken();
    setType(localStringTokenizer.nextToken());
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public void setAffiliateId(String paramString)
  {
    this.affiliateId = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  public void setPropName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.iE = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.iD.setProperty(this.iE, paramString);
    } else {
      this.iD.remove(this.iE);
    }
  }
  
  public String getPropData()
  {
    return this.iD.getProperty(this.iE, "");
  }
  
  public void setCurrentPeriod(String paramString)
  {
    this.iF = paramString;
  }
  
  public void setCurrentEntitlement(String paramString)
  {
    this.iH = paramString;
  }
  
  public void setLimitData(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      paramString = "";
    }
    HashMap localHashMap = (HashMap)this.iG.get(this.iH);
    if (localHashMap == null)
    {
      localHashMap = new HashMap(6);
      this.iG.put(this.iH, localHashMap);
    }
    a locala = (a)localHashMap.get(this.iF);
    if (locala == null) {
      locala = new a(null);
    }
    locala.a(paramString);
    localHashMap.put(this.iF, locala);
  }
  
  public String getLimitData()
  {
    if ((this.iF == null) || (this.iH == null)) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.iG.get(this.iH);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(this.iF);
    if ((locala != null) && (locala.a() != null)) {
      return locala.a();
    }
    return "";
  }
  
  public void setLimitAllowApproval(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      paramString = "";
    }
    HashMap localHashMap = (HashMap)this.iG.get(this.iH);
    if (localHashMap == null)
    {
      localHashMap = new HashMap(6);
      this.iG.put(this.iH, localHashMap);
    }
    a locala = (a)localHashMap.get(this.iF);
    if (locala == null) {
      locala = new a(null);
    }
    locala.a(Boolean.valueOf(paramString).booleanValue());
    localHashMap.put(this.iF, locala);
  }
  
  public String getLimitAllowApproval()
  {
    if ((this.iF == null) || (this.iH == null)) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.iG.get(this.iH);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(this.iF);
    if (locala != null) {
      return String.valueOf(locala.jdMethod_if());
    }
    return "";
  }
  
  protected void initializeLimits(SecureUser paramSecureUser)
  {
    if (this.user == null) {
      return;
    }
    Limit localLimit1 = new Limit();
    setLimitCurrencyInformation(localLimit1);
    Account localAccount = null;
    try
    {
      localAccount = new Account(this.locale, AccountSettings.buildAccountId(this.number, "" + this.type), this.routingNumber, this.bankId);
    }
    catch (Exception localException1)
    {
      DebugLog.log(Level.SEVERE, "Unable to create account: " + localException1.toString());
    }
    Entitlement localEntitlement = new Entitlement(null, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
    localLimit1.setEntitlement(localEntitlement);
    try
    {
      HashMap localHashMap1 = new HashMap();
      Limits localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.user.getEntitlementGroupId(), localLimit1, localHashMap1);
      for (int i = 0; i < localLimits.size(); i++)
      {
        Limit localLimit2 = (Limit)localLimits.get(i);
        HashMap localHashMap2 = (HashMap)this.iG.get(localLimit2.getEntitlement().getOperationName());
        if (localHashMap2 == null)
        {
          localHashMap2 = new HashMap(6);
          this.iG.put(localLimit2.getEntitlement().getOperationName(), localHashMap2);
        }
        localHashMap2.put("" + localLimit2.getPeriod(), new a(localLimit2.getData(), localLimit2.isAllowedApproval(), null));
      }
    }
    catch (Exception localException2)
    {
      DebugLog.log(Level.SEVERE, "Unable to get ALL Limit List: " + localException2.toString());
    }
  }
  
  protected void processEntitlementsAndLimits(SecureUser paramSecureUser)
  {
    if (this.user == null) {
      return;
    }
    Account localAccount = null;
    try
    {
      localAccount = new Account(this.locale, AccountSettings.buildAccountId(this.number, "" + this.type), this.routingNumber, this.bankId);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Unable to create account: " + localException.toString());
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    try
    {
      localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.user.getEntitlementGroupId());
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Enumeration localEnumeration = this.iD.keys();
    String str2;
    Object localObject1;
    Object localObject2;
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      str2 = this.iD.getProperty(str1);
      if (str2 != null)
      {
        localObject1 = new Entitlement(str1, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
        int i = 0;
        localObject2 = localEntitlements1.iterator();
        while ((((Iterator)localObject2).hasNext()) && (i == 0))
        {
          Entitlement localEntitlement1 = (Entitlement)((Iterator)localObject2).next();
          if (((Entitlement)localObject1).equals(localEntitlement1)) {
            i = 1;
          }
        }
        if ((i != 0) && (str2.equalsIgnoreCase("true"))) {
          localEntitlements3.add(localObject1);
        }
        if ((i == 0) && (str2.equalsIgnoreCase("false")))
        {
          localEntitlements2.add(localObject1);
          if (this.iG != null) {
            this.iG.remove(str1);
          }
        }
      }
    }
    if (localEntitlements2.size() > 0) {
      try
      {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.user.getEntitlementGroupId(), localEntitlements2);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
    }
    if (localEntitlements3.size() > 0) {
      try
      {
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.user.getEntitlementGroupId(), localEntitlements3);
      }
      catch (CSILException localCSILException3)
      {
        this.error = MapError.mapError(localCSILException3);
      }
    }
    Iterator localIterator1 = this.iG.keySet().iterator();
    while (localIterator1.hasNext())
    {
      str2 = (String)localIterator1.next();
      localObject1 = (HashMap)this.iG.get(str2);
      Iterator localIterator2 = ((HashMap)localObject1).keySet().iterator();
      while (localIterator2.hasNext())
      {
        localObject2 = (String)localIterator2.next();
        int j = Integer.parseInt((String)localObject2);
        a locala = (a)((HashMap)localObject1).get(localObject2);
        String str3 = null;
        boolean bool = false;
        if (locala != null)
        {
          str3 = locala.a();
          bool = locala.jdMethod_if();
        }
        if (!Currency.isValid(str3, this.locale))
        {
          this.error = 16117;
          break;
        }
        try
        {
          Entitlement localEntitlement2 = new Entitlement(str2, "Account", EntitlementsUtil.getEntitlementObjectId(localAccount));
          Limit localLimit1 = new Limit();
          setLimitCurrencyInformation(localLimit1);
          localLimit1.setEntitlement(localEntitlement2);
          localLimit1.setPeriod(j);
          localLimit1.setGroupId(this.user.getEntitlementGroupId());
          Limits localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.user.getEntitlementGroupId(), localLimit1, null);
          Iterator localIterator3 = localLimits.iterator();
          localLimit1.setRunningTotalType('U');
          Object localObject3 = null;
          Object localObject4 = null;
          Limit localLimit2 = null;
          if (localIterator3.hasNext())
          {
            localLimit2 = (Limit)localIterator3.next();
            if ((str3 == null) || (str3.length() <= 0))
            {
              com.ffusion.csil.core.Entitlements.deleteGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
            }
            else
            {
              localLimit1.setData(str3);
              localLimit1.setAllowApproval(bool);
              if ((localLimit1.getAmount().toString().equals(localLimit2.getAmount().toString())) && (localLimit1.getAllowApproval() == localLimit2.getAllowApproval())) {
                continue;
              }
              localLimit2.setData(str3);
              localLimit2.setAllowApproval(bool);
              localLimit2 = com.ffusion.csil.core.Entitlements.modifyGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
            }
          }
          else if ((str3 != null) && (str3.length() > 0))
          {
            localLimit2 = localLimit1;
            localLimit2.setData(str3);
            localLimit2.setAllowApproval(bool);
            localLimit2 = com.ffusion.csil.core.Entitlements.addGroupLimit(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localLimit2);
          }
          this.nextURL = this.successURL;
        }
        catch (CSILException localCSILException4)
        {
          localCSILException4.printStackTrace(System.out);
        }
      }
    }
  }
  
  public String getEntitlementObjectID()
  {
    Account localAccount = null;
    try
    {
      localAccount = new Account(this.locale, AccountSettings.buildAccountId(this.number, "" + this.type), this.routingNumber, this.bankId);
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Unable to create account: " + localException.toString());
    }
    return EntitlementsUtil.getEntitlementObjectId(localAccount);
  }
  
  public void setPrimaryAcctHolder(String paramString)
  {
    super.setPrimaryAcctHolder(paramString);
    setRecipientName(paramString);
  }
  
  public String getPrimaryAcctHolder()
  {
    String str = super.getPrimaryAcctHolder();
    if ((str == null) || (str.length() == 0)) {
      str = getRecipientName();
    }
    return str;
  }
  
  protected void setLimitCurrencyInformation(Limit paramLimit)
  {
    String str = Util.getLimitBaseCurrency();
    paramLimit.setCurrencyCode(str);
    paramLimit.setCrossCurrency(true);
  }
  
  public void setRoutingNumber(String paramString)
  {
    super.setRoutingNumber(paramString.trim());
  }
  
  private class a
  {
    private String a;
    private boolean jdField_if;
    
    private a()
    {
      this.a = "";
      this.jdField_if = false;
    }
    
    private a(String paramString, boolean paramBoolean)
    {
      this.a = paramString;
      this.jdField_if = paramBoolean;
    }
    
    private void a(String paramString)
    {
      this.a = paramString;
    }
    
    private void a(boolean paramBoolean)
    {
      this.jdField_if = paramBoolean;
    }
    
    private String a()
    {
      return this.a;
    }
    
    private boolean jdField_if()
    {
      return this.jdField_if;
    }
    
    a(ModifyExtTransferAccount.1 param1)
    {
      this();
    }
    
    a(String paramString, boolean paramBoolean, ModifyExtTransferAccount.1 param1)
    {
      this(paramString, paramBoolean);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.ModifyExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */