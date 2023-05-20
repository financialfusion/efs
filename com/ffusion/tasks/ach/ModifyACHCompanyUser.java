package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
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
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHCompanyUser
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private String AW = "";
  private String AZ = "";
  private int AT = 0;
  private HashMap A2 = new HashMap();
  private String AX;
  private String AY;
  private String A3;
  private static final String A1 = "com.ffusion.util.logging.audit.task";
  private static final String A4 = "AuditMessage_ModifyACHCompanyUser_1.1";
  private static final String AV = "AuditMessage_ModifyACHCompanyUser_1.2";
  private static final String AU = "AuditMessage_ModifyACHCompanyUser_2.1";
  private static final String A0 = "AuditMessage_ModifyACHCompanyUser_2.2";
  
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
    else if (this.processFlag)
    {
      this.processFlag = false;
      str = this.successURL;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HistoryTracker localHistoryTracker = ModifyACHCompany.jdMethod_for(localSecureUser, "ModifyACHCompanyUser", this.AT, null, null);
      processEntitlements(localHttpSession, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for ModifyACHCompany: " + localProfileException.toString());
      }
    }
    else
    {
      str = this.successURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute("ACHCOMPANIES");
    Business localBusiness = (Business)paramHttpSession.getAttribute("Business");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localACHCompanies == null)
    {
      this.error = 16505;
      return false;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return false;
    }
    this.A2.clear();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.AY != null) && (this.A3 != null) && (this.AX != null) && (this.AT != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.AT);
      localEntitlementGroupMember.setMemberType(this.AY);
      localEntitlementGroupMember.setMemberSubType(this.A3);
      localEntitlementGroupMember.setId(this.AX);
    }
    try
    {
      int i = this.AT;
      EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(i);
      for (String str = localEntitlementGroup.getEntGroupType(); !str.equals("BusinessAdmin"); str = localEntitlementGroup.getEntGroupType())
      {
        int j = localEntitlementGroup.getParentId();
        localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(j);
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup.getGroupId());
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties("category", "per ACH company");
      Iterator localIterator1 = localACHCompanies.iterator();
      while ((localIterator1.hasNext()) && (localEntitlementTypePropertyLists != null))
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator1.next();
        Properties localProperties = new Properties();
        this.A2.put(localACHCompany.getCompanyID(), localProperties);
        Iterator localIterator2 = localEntitlementTypePropertyLists.iterator();
        while (localIterator2.hasNext())
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
          Entitlement localEntitlement = new Entitlement();
          localEntitlement.setOperationName(localEntitlementTypePropertyList.getOperationName());
          localEntitlement.setObjectType("ACHCompany");
          localEntitlement.setObjectId(EntitlementsUtil.getEntitlementObjectId(localACHCompany));
          boolean bool1 = localEntitlements1.contains(localEntitlement);
          boolean bool2 = localEntitlements2.contains(localEntitlement);
          if (!bool2) {
            if (bool1) {
              localProperties.setProperty(localEntitlementTypePropertyList.getOperationName(), "false");
            } else {
              localProperties.setProperty(localEntitlementTypePropertyList.getOperationName(), "true");
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    this.initFlag = false;
    return this.error == 0;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
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
  
  public void setPropName(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.AW = paramString;
    }
  }
  
  public void setCompanyID(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.AZ = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    Properties localProperties = (Properties)this.A2.get(this.AZ);
    if (localProperties == null)
    {
      localProperties = new Properties();
      this.A2.put(this.AZ, localProperties);
    }
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      localProperties.setProperty(this.AW, paramString);
    } else {
      localProperties.remove(this.AW);
    }
  }
  
  public String getPropData()
  {
    Properties localProperties = (Properties)this.A2.get(this.AZ);
    if ((localProperties != null) && (localProperties.getProperty(this.AW) != null)) {
      return localProperties.getProperty(this.AW);
    }
    return "";
  }
  
  public String getSize()
  {
    Properties localProperties = (Properties)this.A2.get(this.AZ);
    if (localProperties != null) {
      return "" + localProperties.size();
    }
    return "0";
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.AT = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.AT;
  }
  
  public void setMemberId(String paramString)
  {
    this.AX = paramString;
  }
  
  public String getMemberId()
  {
    return this.AX;
  }
  
  public void setMemberType(String paramString)
  {
    this.AY = paramString;
  }
  
  public String getMemberType()
  {
    return this.AY;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.A3 = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.A3;
  }
  
  protected void processEntitlements(HttpSession paramHttpSession, HistoryTracker paramHistoryTracker)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute("ACHCOMPANIES");
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.AY != null) && (this.A3 != null) && (this.AX != null) && (this.AT != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.AT);
      localEntitlementGroupMember.setMemberType(this.AY);
      localEntitlementGroupMember.setMemberSubType(this.A3);
      localEntitlementGroupMember.setId(this.AX);
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    try
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
      if (localEntitlementGroupMember != null) {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember);
      } else {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), getEntitlementGroupId());
      }
      Set localSet = this.A2.keySet();
      Iterator localIterator = localSet.iterator();
      Properties localProperties = null;
      Object localObject1;
      String str2;
      String str3;
      Object localObject2;
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        ACHCompany localACHCompany = localACHCompanies.getByCompanyID(str1);
        localObject1 = new Entitlement("ACHBatch", "ACHCompany", str1);
        if (!localEntitlements.contains(localObject1))
        {
          localProperties = (Properties)this.A2.get(str1);
          Enumeration localEnumeration = localProperties.keys();
          while (localEnumeration.hasMoreElements())
          {
            str2 = (String)localEnumeration.nextElement();
            str3 = localProperties.getProperty(str2);
            if (str3 != null)
            {
              localObject2 = new Entitlement();
              ((Entitlement)localObject2).setOperationName(str2);
              ((Entitlement)localObject2).setObjectType("ACHCompany");
              ((Entitlement)localObject2).setObjectId(str1);
              if (str3.equalsIgnoreCase("true"))
              {
                if (localEntitlements.contains(localObject2))
                {
                  localArrayList2.add(localObject2);
                  localArrayList4.add(localACHCompany.getCompanyName());
                }
                localEntitlements.remove(localObject2);
              }
              else if (!localEntitlements.contains(localObject2))
              {
                localEntitlements.add(localObject2);
                localArrayList1.add(localObject2);
                localArrayList3.add(localACHCompany.getCompanyName());
              }
            }
          }
        }
      }
      if (localArrayList1.size() + localArrayList2.size() != 0)
      {
        localObject1 = (BusinessEmployee)paramHttpSession.getAttribute("BusinessEmployee");
        if (localEntitlementGroupMember != null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember, localEntitlements);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), getEntitlementGroupId(), localEntitlements);
        }
        LocalizableProperty localLocalizableProperty;
        Object[] arrayOfObject;
        LocalizableString localLocalizableString;
        for (int i = 0; i < localArrayList1.size(); i++)
        {
          str2 = TrackingIDGenerator.GetNextID();
          str3 = ((Entitlement)localArrayList1.get(i)).getOperationName();
          localObject2 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str3);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject2).getPropertiesMap(), str3);
          arrayOfObject = null;
          localLocalizableString = null;
          if (localObject1 == null)
          {
            arrayOfObject = new Object[2];
            arrayOfObject[0] = localLocalizableProperty;
            arrayOfObject[1] = localArrayList3.get(i);
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyUser_1.1", arrayOfObject);
          }
          else
          {
            arrayOfObject = new Object[3];
            arrayOfObject[0] = localLocalizableProperty;
            arrayOfObject[1] = localArrayList3.get(i);
            arrayOfObject[2] = ((BusinessEmployee)localObject1).getName();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyUser_1.2", arrayOfObject);
          }
          Initialize.audit(localSecureUser, localLocalizableString, str2, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localArrayList1.get(i), null);
        }
        for (i = 0; i < localArrayList2.size(); i++)
        {
          str2 = TrackingIDGenerator.GetNextID();
          str3 = ((Entitlement)localArrayList2.get(i)).getOperationName();
          localObject2 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str3);
          localLocalizableProperty = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject2).getPropertiesMap(), str3);
          arrayOfObject = null;
          localLocalizableString = null;
          if (localObject1 == null)
          {
            arrayOfObject = new Object[2];
            arrayOfObject[0] = localLocalizableProperty;
            arrayOfObject[1] = localArrayList4.get(i);
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyUser_2.1", arrayOfObject);
          }
          else
          {
            arrayOfObject = new Object[3];
            arrayOfObject[0] = localLocalizableProperty;
            arrayOfObject[1] = localArrayList4.get(i);
            arrayOfObject[2] = ((BusinessEmployee)localObject1).getName();
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyUser_2.2", arrayOfObject);
          }
          Initialize.audit(localSecureUser, localLocalizableString, str2, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localArrayList2.get(i), null);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHCompanyUser
 * JD-Core Version:    0.7.0.1
 */