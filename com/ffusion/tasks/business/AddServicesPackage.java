package com.ffusion.tasks.business;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.business.TransactionLimit;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddServicesPackage
  extends ServicesPackage
  implements BusinessTask
{
  String hS = "TE";
  String hT = "SE";
  String hR;
  String hX;
  boolean hZ = false;
  protected boolean initFlag = false;
  protected boolean authenticationFlag = false;
  protected boolean usePeriodicLimits = false;
  int hW = 0;
  String hQ = "ServicesPackage";
  protected String servicePackagesCollectionName = "ServicesPackages";
  protected String chargeableFeaturesCollectionName = "ChargeableFeatures";
  String hV = null;
  String hY = null;
  ServicesPackages hU = null;
  private HashMap hP = new HashMap();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.hW = 0;
    String str = this.hR;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    if (this.initFlag) {
      bool = jdMethod_else(localHttpSession);
    }
    if (bool)
    {
      this.hU = ((ServicesPackages)localHttpSession.getAttribute(this.servicePackagesCollectionName));
      if (this.hU == null)
      {
        this.hW = 4115;
        return this.hS;
      }
      if (this.authenticationFlag) {
        processAuthenticationSettings(paramHttpServletRequest);
      }
      if (validateInput(localHttpSession))
      {
        if (this.hZ)
        {
          this.hZ = false;
          try
          {
            HashMap localHashMap = null;
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            setBankId(localSecureUser.getBankID());
            setServiceFeatures((ServiceFeatures)localHttpSession.getAttribute("ServicesPackageFeatures"));
            setChargeableFeatures((ServiceFeatures)localHttpSession.getAttribute(this.chargeableFeaturesCollectionName));
            setLimits();
            Business.addServicesPackage(localSecureUser, this, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.hW = MapError.mapError(localCSILException);
            str = this.hT;
          }
          if (this.hW == 0)
          {
            this.hU.add(this);
            localHttpSession.setAttribute(this.hQ, this);
          }
        }
      }
      else {
        str = this.hS;
      }
    }
    else
    {
      str = this.hS;
    }
    return str;
  }
  
  protected void processAuthenticationSettings(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (localHttpSession.getAttribute("AuthLoginToken") != null)
    {
      setAuthLoginToken(true);
      localHttpSession.removeAttribute("AuthLoginToken");
    }
    else
    {
      setAuthLoginToken(false);
    }
    if (localHttpSession.getAttribute("AuthLoginScratch") != null)
    {
      setAuthLoginScratch(true);
      localHttpSession.removeAttribute("AuthLoginScratch");
    }
    else
    {
      setAuthLoginScratch(false);
    }
    if (localHttpSession.getAttribute("AuthLoginChallenge") != null)
    {
      setAuthLoginChallenge(true);
      localHttpSession.removeAttribute("AuthLoginChallenge");
    }
    else
    {
      setAuthLoginChallenge(false);
    }
    if (localHttpSession.getAttribute("AuthTransToken") != null)
    {
      setAuthTransToken(true);
      localHttpSession.removeAttribute("AuthTransToken");
    }
    else
    {
      setAuthTransToken(false);
    }
    if (localHttpSession.getAttribute("AuthTransScratch") != null)
    {
      setAuthTransScratch(true);
      localHttpSession.removeAttribute("AuthTransScratch");
    }
    else
    {
      setAuthTransScratch(false);
    }
    if (localHttpSession.getAttribute("AuthTransChallenge") != null)
    {
      setAuthTransChallenge(true);
      localHttpSession.removeAttribute("AuthTransChallenge");
    }
    else
    {
      setAuthTransChallenge(false);
    }
    if (localHttpSession.getAttribute("AuthPrivToken") != null)
    {
      setAuthPrivToken(true);
      localHttpSession.removeAttribute("AuthPrivToken");
    }
    else
    {
      setAuthPrivToken(false);
    }
    if (localHttpSession.getAttribute("AuthPrivScratch") != null)
    {
      setAuthPrivScratch(true);
      localHttpSession.removeAttribute("AuthPrivScratch");
    }
    else
    {
      setAuthPrivScratch(false);
    }
    if (localHttpSession.getAttribute("AuthPrivChallenge") != null)
    {
      setAuthPrivChallenge(true);
      localHttpSession.removeAttribute("AuthPrivChallenge");
    }
    else
    {
      setAuthPrivChallenge(false);
    }
    this.authenticationFlag = false;
  }
  
  public void setProcess(String paramString)
  {
    this.hZ = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.hX = null;
    if (!"".equalsIgnoreCase(paramString)) {
      this.hX = paramString.toUpperCase();
    }
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.hS = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.hT = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.hR = paramString;
  }
  
  public void setServicesPackageSessionName(String paramString)
  {
    this.hQ = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.hW = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.hW);
  }
  
  public int getErrorValue()
  {
    return this.hW;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  private boolean jdMethod_else(HttpSession paramHttpSession)
  {
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    ServiceFeatures localServiceFeatures1 = (ServiceFeatures)paramHttpSession.getAttribute("MarketSegmentFeatures");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localServiceFeatures1 == null)
    {
      this.hW = 4120;
      return false;
    }
    ServiceFeatures localServiceFeatures2 = new ServiceFeatures();
    Iterator localIterator = localServiceFeatures1.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = new ServiceFeature(this.locale);
      ((ServiceFeature)localObject).set((ServiceFeature)localIterator.next());
      localServiceFeatures2.add(localObject);
    }
    paramHttpSession.setAttribute("ServicesPackageFeatures", localServiceFeatures2);
    try
    {
      localObject = Business.getChargeableServiceFeaturesByGroupId(localSecureUser, getMarketSegmentIdValue(), new HashMap());
      if (localObject == null) {
        localObject = new ServiceFeatures();
      }
      paramHttpSession.setAttribute(this.chargeableFeaturesCollectionName, localObject);
    }
    catch (CSILException localCSILException) {}
    LimitTypePropertyLists localLimitTypePropertyLists = Business.getLimitTypeProps();
    TransactionLimits localTransactionLimits = new TransactionLimits(localLimitTypePropertyLists, "per package", this.locale);
    if (!this.usePeriodicLimits) {
      removePeriodicLimits(localTransactionLimits);
    }
    paramHttpSession.setAttribute("ServicesPackageTransactionLimits", localTransactionLimits);
    setTransactionLimits(localTransactionLimits);
    this.initFlag = false;
    return true;
  }
  
  protected void removePeriodicLimits(TransactionLimits paramTransactionLimits)
  {
    Iterator localIterator = paramTransactionLimits.iterator();
    while (localIterator.hasNext())
    {
      TransactionLimit localTransactionLimit = (TransactionLimit)localIterator.next();
      if ((localTransactionLimit.getPeriod() != 1) && (localTransactionLimit.getPeriod() != 0)) {
        localTransactionLimit.setHide(true);
      }
    }
  }
  
  protected void initializeLimits(SecureUser paramSecureUser, LimitTypePropertyLists paramLimitTypePropertyLists)
  {
    Limit localLimit1 = new Limit();
    setLimitCurrencyInformation(localLimit1);
    try
    {
      HashMap localHashMap1 = new HashMap();
      Limits localLimits = Entitlements.getGroupLimits(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getIdValue(), localLimit1, localHashMap1);
      for (int i = 0; i < localLimits.size(); i++)
      {
        Limit localLimit2 = (Limit)localLimits.get(i);
        LimitTypePropertyList localLimitTypePropertyList = paramLimitTypePropertyLists.getByOperationName(localLimit2.getEntitlement().getOperationName());
        if (localLimitTypePropertyList.isPropertySet("category", "per package"))
        {
          HashMap localHashMap2 = (HashMap)this.hP.get(localLimit2.getEntitlement().getOperationName());
          if (localHashMap2 == null)
          {
            localHashMap2 = new HashMap(6);
            this.hP.put(localLimit2.getEntitlement().getOperationName(), localHashMap2);
          }
          localHashMap2.put("" + localLimit2.getPeriod(), new a(localLimit2.getData(), localLimit2.isAllowedApproval(), null));
        }
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Unable to get ALL Limit List: " + localException.toString());
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.hX == null) {
      return bool;
    }
    Object localObject1;
    if (this.hX.indexOf("SERVICESPACKAGENAME") != -1)
    {
      if ((this.servicesPackageName == null) || (this.servicesPackageName.trim().length() == 0)) {
        return setError(4121);
      }
      localObject1 = (ServicesPackage)paramHttpSession.getAttribute("OldServicesPackage");
      int i = 1;
      if ((localObject1 != null) && (((ServicesPackage)localObject1).getServicesPackageName().equals(getServicesPackageName()))) {
        i = 0;
      }
      if ((i == 1) && (this.hU.getByServicesPackageName(this.servicesPackageName) != null)) {
        return setError(4122);
      }
    }
    if ((this.hX.indexOf("SERVICESADMINGROUPID") != -1) && ((this.servicesAdminGroupId == null) || (this.servicesAdminGroupId.trim().length() == 0))) {
      return setError(4119);
    }
    if (this.hX.indexOf(TRANSACTIONLIMITS) != -1)
    {
      Object localObject2;
      Object localObject3;
      if (!this.usePeriodicLimits)
      {
        localObject1 = this.transactionLimits.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (TransactionLimit)((Iterator)localObject1).next();
          localObject3 = ((TransactionLimit)localObject2).getData();
          if ((localObject3 != null) && (!Currency.isValid((String)localObject3, this.locale))) {
            return setError(4135);
          }
          if ((localObject3 != null) && ((((String)localObject3).startsWith("+")) || (((String)localObject3).startsWith("-")))) {
            return setError(4135);
          }
        }
      }
      else
      {
        localObject1 = this.hP.keySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (String)((Iterator)localObject1).next();
          localObject3 = (HashMap)this.hP.get(localObject2);
          Iterator localIterator = ((HashMap)localObject3).keySet().iterator();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            a locala = (a)((HashMap)localObject3).get(str);
            if ((locala != null) && (locala.jdMethod_if()) && ((locala.a() == null) || (locala.a().length() == 0))) {
              return setError(154);
            }
            if ((locala != null) && (locala.a() != null) && (locala.a().length() != 0) && (!Currency.isValid(locala.a(), this.locale))) {
              return setError(4135);
            }
            if ((locala != null) && (locala.a() != null) && ((locala.a().startsWith("+")) || (locala.a().startsWith("-")))) {
              return setError(4135);
            }
          }
        }
      }
    }
    this.hX = null;
    return bool;
  }
  
  public void setLimitName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.transactionLimits == null) {
        this.transactionLimits = new TransactionLimits();
      }
      this.hV = paramString;
    }
  }
  
  public void setLimitPeriod(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.hY = paramString;
    }
  }
  
  public void setLimitData(String paramString)
  {
    Object localObject;
    if (!this.usePeriodicLimits)
    {
      localObject = this.transactionLimits.getLimitByNameAndPeriod(this.hV, 1);
      if (localObject == null)
      {
        localObject = new TransactionLimit();
        ((TransactionLimit)localObject).setLimitName(this.hV);
        setLimitCurrencyInformation((TransactionLimit)localObject);
        this.transactionLimits.add(localObject);
      }
      ((TransactionLimit)localObject).setData(paramString);
    }
    else
    {
      if ((paramString == null) || (paramString.trim().length() == 0)) {
        paramString = "";
      }
      localObject = (HashMap)this.hP.get(this.hV);
      if (localObject == null)
      {
        localObject = new HashMap(6);
        this.hP.put(this.hV, localObject);
      }
      String str = "1";
      if (this.hY != null) {
        str = this.hY;
      }
      a locala = (a)((HashMap)localObject).get(str);
      if (locala == null) {
        locala = new a(paramString, false, null);
      } else {
        locala.a(paramString);
      }
      ((HashMap)localObject).put(str, locala);
    }
  }
  
  public String getLimitData()
  {
    String str = "1";
    if (this.hY != null) {
      str = this.hY;
    }
    if (this.hV == null) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.hP.get(this.hV);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(str);
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
    HashMap localHashMap = (HashMap)this.hP.get(this.hV);
    if (localHashMap == null)
    {
      localHashMap = new HashMap(6);
      this.hP.put(this.hV, localHashMap);
    }
    String str = "1";
    if (this.hY != null) {
      str = this.hY;
    }
    a locala = (a)localHashMap.get(str);
    if (locala == null) {
      locala = new a(null);
    }
    locala.a(Boolean.valueOf(paramString).booleanValue());
    localHashMap.put(str, locala);
  }
  
  public String getLimitAllowApproval()
  {
    String str = "1";
    if (this.hY != null) {
      str = this.hY;
    }
    if (this.hV == null) {
      return "";
    }
    HashMap localHashMap = (HashMap)this.hP.get(this.hV);
    if (localHashMap == null) {
      return "";
    }
    a locala = (a)localHashMap.get(str);
    if (locala != null) {
      return String.valueOf(locala.jdMethod_if());
    }
    return "";
  }
  
  protected void setLimits()
  {
    Iterator localIterator1 = this.hP.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      HashMap localHashMap = (HashMap)this.hP.get(str1);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        int i = Integer.parseInt(str2);
        a locala = (a)localHashMap.get(str2);
        String str3 = "";
        if (locala != null) {
          str3 = locala.a();
        }
        TransactionLimit localTransactionLimit = this.transactionLimits.getLimitByNameAndPeriod(str1, i);
        if (localTransactionLimit == null)
        {
          localTransactionLimit = new TransactionLimit(this.locale);
          localTransactionLimit.setLimitName(str1);
          localTransactionLimit.setPeriod(i);
          setLimitCurrencyInformation(localTransactionLimit);
          this.transactionLimits.add(localTransactionLimit);
        }
        localTransactionLimit.setData(str3);
        if (locala != null) {
          localTransactionLimit.setAllowApproval(locala.jdMethod_if());
        } else {
          localTransactionLimit.setAllowApproval(false);
        }
      }
    }
  }
  
  public void setDisplayName(String paramString) {}
  
  public void setCategory(String paramString) {}
  
  public String getServicePackagesCollectionName()
  {
    return this.servicePackagesCollectionName;
  }
  
  public void setServicePackagesCollectionName(String paramString)
  {
    this.servicePackagesCollectionName = paramString;
  }
  
  public String getChargeableFeaturesCollectionName()
  {
    return this.chargeableFeaturesCollectionName;
  }
  
  public void setChargeableFeaturesCollectionName(String paramString)
  {
    this.chargeableFeaturesCollectionName = paramString;
  }
  
  public void setAuthenticationFlag(String paramString)
  {
    this.authenticationFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUsePeriodicLimits(String paramString)
  {
    this.usePeriodicLimits = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected void setLimitCurrencyInformation(Limit paramLimit)
  {
    String str = Util.getLimitBaseCurrency();
    paramLimit.setCurrencyCode(str);
    paramLimit.setCrossCurrency(true);
  }
  
  protected void setLimitCurrencyInformation(TransactionLimit paramTransactionLimit)
  {
    String str = Util.getLimitBaseCurrency();
    paramTransactionLimit.setCurrencyCode(str);
    paramTransactionLimit.setCrossCurrency(true);
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
    
    a(String paramString, boolean paramBoolean, AddServicesPackage.1 param1)
    {
      this(paramString, paramBoolean);
    }
    
    a(AddServicesPackage.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.AddServicesPackage
 * JD-Core Version:    0.7.0.1
 */