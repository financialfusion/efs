package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHCompany
  extends ACHCompany
  implements Task
{
  protected String achCompaniesName = "ACHCOMPANIES";
  protected String achCompanyName = "ACHCOMPANY";
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String affiliateBankID;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private String Al = "";
  private Properties Ak = new Properties();
  private int Ai = 0;
  protected ACHCompany originalACHCompany;
  protected boolean _autoEntitleACHCompany = true;
  private static final String Am = "com.ffusion.util.logging.audit.task";
  private static final String An = "AuditMessage_ModifyACHCompany_1";
  private static final String Aj = "AuditMessage_ModifyACHCompany_2";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      if (init(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyACHCompany(localHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute(this.achCompanyName);
    if (localACHCompany == null)
    {
      this.error = 16506;
      return false;
    }
    set(localACHCompany);
    this.originalACHCompany = localACHCompany;
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.initFlag = false;
    return true;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.validate.indexOf("CUSTOMER_ID") != -1) {
        bool = validateCustomerID();
      }
      if ((bool) && (this.validate.indexOf("COID") != -1)) {
        bool = validateCompanyID();
      }
      if ((bool) && (this.validate.indexOf("CONAME") != -1)) {
        bool = validateCompanyName(paramHttpSession);
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateCustomerID()
  {
    if ((this.custID == null) || (this.custID.length() == 0)) {
      this.error = 16110;
    }
    return this.error == 0;
  }
  
  protected boolean validateCompanyID()
  {
    if ((this.companyID == null) || (this.companyID.length() == 0)) {
      this.error = 16111;
    } else if (this.companyID.length() > 10) {
      this.error = 16138;
    } else if ((Character.isWhitespace(this.companyID.charAt(0))) || (Character.isWhitespace(this.companyID.charAt(this.companyID.length() - 1)))) {
      this.error = 16111;
    }
    return this.error == 0;
  }
  
  protected boolean validateCompanyName(HttpSession paramHttpSession)
  {
    if ((this.companyName == null) || (this.companyName.length() == 0))
    {
      this.error = 16112;
    }
    else
    {
      ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.achCompaniesName);
      if (localACHCompanies == null) {
        this.error = 16505;
      } else {
        for (int i = 0; i < localACHCompanies.size(); i++)
        {
          ACHCompany localACHCompany = (ACHCompany)localACHCompanies.get(i);
          if ((this.companyName.equalsIgnoreCase(localACHCompany.getCompanyName())) && ((this.originalACHCompany == null) || (!this.originalACHCompany.getID().equals(localACHCompany.getID()))))
          {
            this.error = 16114;
            break;
          }
        }
      }
    }
    return this.error == 0;
  }
  
  protected String modifyACHCompany(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    Business localBusiness = (Business)paramHttpSession.getAttribute("Business");
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.achCompaniesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateBankID), localHashMap);
        ACH.modifyACHCompany(localSecureUser, this, this.originalACHCompany, localAffiliateBank.getFIBPWID(), localHashMap);
        processEntitlements(localSecureUser, localBusiness, true);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        paramHttpSession.setAttribute(this.achCompanyName, this);
        localACHCompanies.removeByCompanyID(getCompanyID());
        localACHCompanies.add(this);
      }
    }
    return str;
  }
  
  static HistoryTracker jdMethod_for(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, String paramString3)
  {
    int i = 0;
    String str = null;
    if (paramString2 == null)
    {
      str = Integer.toString(paramInt);
      try
      {
        EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramInt);
        if ("Division".equals(localEntitlementGroup.getEntGroupType())) {
          i = 6;
        } else if ("Group".equals(localEntitlementGroup.getEntGroupType())) {
          i = 7;
        } else if ("Business".equals(localEntitlementGroup.getEntGroupType())) {
          i = 2;
        }
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, "Failed to get history object type for " + paramString1 + " with id=\"" + paramInt + "\": " + localException.toString());
      }
    }
    else
    {
      i = 1;
      str = paramString2;
    }
    if (paramString3 != null) {
      str = paramString3;
    }
    return new HistoryTracker(paramSecureUser, i, str);
  }
  
  public final void setACHCompaniesInSessionName(String paramString)
  {
    this.achCompaniesName = paramString;
  }
  
  public final String getACHCompaniesInSessionName()
  {
    return this.achCompaniesName;
  }
  
  public final void setACHCompanyInSessionName(String paramString)
  {
    this.achCompanyName = paramString;
  }
  
  public final String getACHCompanyInSessionName()
  {
    return this.achCompanyName;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (paramString.trim().length() > 0) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.affiliateBankID = paramString;
  }
  
  public void setPropName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.Al = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.Ak.setProperty(this.Al, paramString);
    } else {
      this.Ak.remove(this.Al);
    }
  }
  
  public String getPropData()
  {
    return this.Ak.getProperty(this.Al, "");
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.Ai = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.Ai;
  }
  
  protected void processEntitlements(SecureUser paramSecureUser)
  {
    processEntitlements(paramSecureUser, null, false);
  }
  
  protected void processEntitlements(SecureUser paramSecureUser, Business paramBusiness, boolean paramBoolean)
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(paramSecureUser, 11, getID());
    try
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId());
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      Enumeration localEnumeration = this.Ak.keys();
      Object localObject1;
      String str;
      Object localObject2;
      while (localEnumeration.hasMoreElements())
      {
        localObject1 = (String)localEnumeration.nextElement();
        str = this.Ak.getProperty((String)localObject1);
        if (str != null)
        {
          localObject2 = new Entitlement();
          ((Entitlement)localObject2).setOperationName((String)localObject1);
          ((Entitlement)localObject2).setObjectType("ACHCompany");
          ((Entitlement)localObject2).setObjectId(com.ffusion.csil.core.EntitlementsUtil.getEntitlementObjectId(this));
          if (str.equalsIgnoreCase("true"))
          {
            if (localEntitlements.contains(localObject2)) {
              localArrayList2.add(localObject2);
            }
            localEntitlements.remove(localObject2);
          }
          else if (!localEntitlements.contains(localObject2))
          {
            localEntitlements.add(localObject2);
            localArrayList1.add(localObject2);
          }
        }
      }
      if (paramBoolean)
      {
        localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
        ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).addAll(localArrayList2);
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.Ai), (com.ffusion.csil.beans.entitlements.Entitlements)localObject1, 2, this._autoEntitleACHCompany, new HashMap());
      }
      if (localArrayList1.size() + localArrayList2.size() != 0)
      {
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), getEntitlementGroupId(), localEntitlements);
        EntitlementTypePropertyList localEntitlementTypePropertyList;
        LocalizableProperty localLocalizableProperty;
        Object[] arrayOfObject;
        LocalizableString localLocalizableString;
        for (int i = 0; i < localArrayList1.size(); i++)
        {
          str = TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localArrayList1.get(i)).getOperationName();
          localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localLocalizableProperty = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), (String)localObject2);
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableProperty;
          arrayOfObject[1] = getCompanyName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompany_1", arrayOfObject);
          if (paramBusiness == null) {
            Initialize.audit(paramSecureUser, localLocalizableString, str, 3225);
          } else {
            Initialize.audit(paramSecureUser, localLocalizableString, paramBusiness.getId(), str, 3225);
          }
          localHistoryTracker.logEntitlementAdd((Entitlement)localArrayList1.get(i), null);
        }
        for (i = 0; i < localArrayList2.size(); i++)
        {
          str = TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localArrayList2.get(i)).getOperationName();
          localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localLocalizableProperty = new LocalizableProperty("display name", localEntitlementTypePropertyList.getPropertiesMap(), (String)localObject2);
          arrayOfObject = new Object[2];
          arrayOfObject[0] = localLocalizableProperty;
          arrayOfObject[1] = getCompanyName();
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompany_2", arrayOfObject);
          if (paramBusiness == null) {
            Initialize.audit(paramSecureUser, localLocalizableString, str, 3225);
          } else {
            Initialize.audit(paramSecureUser, localLocalizableString, paramBusiness.getId(), str, 3225);
          }
          localHistoryTracker.logEntitlementDelete((Entitlement)localArrayList2.get(i), null);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for ModifyACHCompany: " + localProfileException.toString());
    }
  }
  
  public void setAutoEntitleACHCompany(String paramString)
  {
    this._autoEntitleACHCompany = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitleACHCompany()
  {
    return new Boolean(this._autoEntitleACHCompany).toString();
  }
  
  public void setCompanyName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    super.setCompanyName(paramString);
  }
  
  public void setCompanyID(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    super.setCompanyID(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHCompany
 * JD-Core Version:    0.7.0.1
 */