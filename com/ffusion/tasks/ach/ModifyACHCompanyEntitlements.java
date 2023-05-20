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
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHCompanyEntitlements
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private String AE = "";
  private String AH = "";
  private int AC = 0;
  private HashMap AK = new HashMap();
  private String AF;
  private String AG;
  private String AL;
  public static final String ENTITLEMENT = "entitlement";
  private String AJ = "FILTERED_ACHCOMPANIES";
  private static final String AI = "com.ffusion.util.logging.audit.task";
  private static final String AM = "AuditMessage_ModifyACHCompanyEntitlements_1";
  private static final String AD = "AuditMessage_ModifyACHCompanyEntitlements_2";
  
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
      HistoryTracker localHistoryTracker = ModifyACHCompany.jdMethod_for(localSecureUser, "ModifyACHCompanyEntitlements", this.AC, null, null);
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
    this.AK.clear();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.AG != null) && (this.AL != null) && (this.AF != null) && (this.AC != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.AC);
      localEntitlementGroupMember.setMemberType(this.AG);
      localEntitlementGroupMember.setMemberSubType(this.AL);
      localEntitlementGroupMember.setId(this.AF);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = null;
    try
    {
      int i = this.AC;
      EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(i);
      int j = localEntitlementGroup.getParentId();
      localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(j);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2;
      if (localEntitlementGroupMember != null)
      {
        localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroupMember);
        localEntitlements2 = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(i);
      }
      else
      {
        localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), i);
        localEntitlements2 = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(localEntitlementGroup.getGroupId());
      }
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties("category", "per ACH company");
      Iterator localIterator1 = localACHCompanies.iterator();
      while ((localIterator1.hasNext()) && (localEntitlementTypePropertyLists != null))
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator1.next();
        Properties localProperties = new Properties();
        this.AK.put(localACHCompany.getCompanyID(), localProperties);
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
      this.AE = paramString;
    }
  }
  
  public void setCompanyID(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0)) {
      this.AH = paramString;
    }
  }
  
  public void setPropData(String paramString)
  {
    Properties localProperties = (Properties)this.AK.get(this.AH);
    if (localProperties == null)
    {
      localProperties = new Properties();
      this.AK.put(this.AH, localProperties);
    }
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      localProperties.setProperty(this.AE, paramString);
    } else {
      localProperties.remove(this.AE);
    }
  }
  
  public String getPropData()
  {
    Properties localProperties = (Properties)this.AK.get(this.AH);
    if ((localProperties != null) && (localProperties.getProperty(this.AE) != null)) {
      return localProperties.getProperty(this.AE);
    }
    return "";
  }
  
  public String getSize()
  {
    Properties localProperties = (Properties)this.AK.get(this.AH);
    if (localProperties != null) {
      return "" + localProperties.size();
    }
    return "0";
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.AC = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.AC;
  }
  
  public void setMemberId(String paramString)
  {
    this.AF = paramString;
  }
  
  public String getMemberId()
  {
    return this.AF;
  }
  
  public void setMemberType(String paramString)
  {
    this.AG = paramString;
  }
  
  public String getMemberType()
  {
    return this.AG;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.AL = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.AL;
  }
  
  protected void processEntitlements(HttpSession paramHttpSession, HistoryTracker paramHistoryTracker)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    ACHCompanies localACHCompanies1 = (ACHCompanies)paramHttpSession.getAttribute("ACHCOMPANIES");
    EntitlementGroupMember localEntitlementGroupMember = null;
    if ((this.AG != null) && (this.AL != null) && (this.AF != null) && (this.AC != 0))
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setEntitlementGroupId(this.AC);
      localEntitlementGroupMember.setMemberType(this.AG);
      localEntitlementGroupMember.setMemberSubType(this.AL);
      localEntitlementGroupMember.setId(this.AF);
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
      ACHCompanies localACHCompanies2 = (ACHCompanies)paramHttpSession.getAttribute(this.AJ);
      Iterator localIterator = localACHCompanies2.iterator();
      Properties localProperties = null;
      int i = -1;
      Object localObject1;
      int j;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      while (localIterator.hasNext())
      {
        ACHCompany localACHCompany = (ACHCompany)localIterator.next();
        String str = localACHCompany.getCompanyID();
        i++;
        localObject1 = (String)paramHttpSession.getAttribute("entitlement" + i);
        j = 1;
        if (localObject1 == null) {
          j = 0;
        }
        localObject2 = new Entitlement("ACHBatch", "ACHCompany", str);
        if (j != 0)
        {
          if (localEntitlements.contains(localObject2))
          {
            localArrayList4.add(localACHCompany.getCompanyName());
            localArrayList2.add(localObject2);
          }
          localEntitlements.remove(localObject2);
        }
        else if (!localEntitlements.contains(localObject2))
        {
          localEntitlements.add(localObject2);
          localArrayList3.add(localACHCompany.getCompanyName());
          localArrayList1.add(localObject2);
        }
        if (!localEntitlements.contains(localObject2))
        {
          localProperties = (Properties)this.AK.get(str);
          localObject3 = localProperties.keys();
          while (((Enumeration)localObject3).hasMoreElements())
          {
            localObject4 = (String)((Enumeration)localObject3).nextElement();
            localObject5 = localProperties.getProperty((String)localObject4);
            if (localObject5 != null)
            {
              localObject6 = new Entitlement();
              ((Entitlement)localObject6).setOperationName((String)localObject4);
              ((Entitlement)localObject6).setObjectType("ACHCompany");
              ((Entitlement)localObject6).setObjectId(str);
              if ((j != 0) && (((String)localObject5).equalsIgnoreCase("true")))
              {
                if (localEntitlements.contains(localObject6))
                {
                  localArrayList2.add(localObject6);
                  localArrayList4.add(localACHCompany.getCompanyName());
                }
                localEntitlements.remove(localObject6);
              }
              else if (!localEntitlements.contains(localObject6))
              {
                localEntitlements.add(localObject6);
                localArrayList1.add(localObject6);
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
        LocalizableString localLocalizableString;
        for (j = 0; j < localArrayList1.size(); j++)
        {
          localObject2 = TrackingIDGenerator.GetNextID();
          localObject3 = ((Entitlement)localArrayList1.get(j)).getOperationName();
          localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
          localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
          localObject6 = new Object[3];
          localObject6[0] = localObject5;
          localObject6[1] = ((BusinessEmployee)localObject1).getName();
          localObject6[2] = localArrayList3.get(j);
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyEntitlements_1", (Object[])localObject6);
          Initialize.audit(localSecureUser, localLocalizableString, (String)localObject2, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localArrayList1.get(j), null);
        }
        for (j = 0; j < localArrayList2.size(); j++)
        {
          localObject2 = TrackingIDGenerator.GetNextID();
          localObject3 = ((Entitlement)localArrayList2.get(j)).getOperationName();
          localObject4 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject3);
          localObject5 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject4).getPropertiesMap(), (String)localObject3);
          localObject6 = new Object[3];
          localObject6[0] = localObject5;
          localObject6[1] = ((BusinessEmployee)localObject1).getName();
          localObject6[2] = localArrayList4.get(j);
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_ModifyACHCompanyEntitlements_2", (Object[])localObject6);
          Initialize.audit(localSecureUser, localLocalizableString, (String)localObject2, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localArrayList2.get(j), null);
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
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHCompanyEntitlements
 * JD-Core Version:    0.7.0.1
 */