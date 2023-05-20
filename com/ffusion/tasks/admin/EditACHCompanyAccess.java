package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableProperty;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditACHCompanyAccess
  extends BaseTask
  implements AdminTask
{
  protected boolean _initOnly = false;
  com.ffusion.csil.beans.entitlements.Entitlements ad0 = null;
  Limits aes = null;
  com.ffusion.csil.beans.entitlements.Entitlements aef = null;
  com.ffusion.csil.beans.entitlements.Entitlements aeb = null;
  Limits aev = null;
  Limits aeo = null;
  Limits ad3 = null;
  ArrayList ael = null;
  EntitlementTypePropertyLists aea = null;
  EntitlementTypePropertyLists ad9 = null;
  protected boolean _autoEntitle = true;
  private static final String ad2 = "perBatchCredit";
  private static final String aej = "dailyCredit";
  private static final String aex = "perBatchDebit";
  private static final String aeA = "dailyDebit";
  private static final String aeq = "approvePerBatchCredit";
  private static final String aen = "approveDailyCredit";
  private static final String aec = "approvePerBatchDebit";
  private static final String aeB = "approveDailyDebit";
  private static final String ADMIN = "admin";
  private String ad7;
  private String aew;
  private String aeE;
  private int ad8;
  private String adY;
  private String aep;
  private String aek;
  private String ad4;
  private String adX = "ACHCompany";
  protected boolean _enrollingBusiness = false;
  private static final String aeG = "com.ffusion.util.logging.audit.task";
  private static final String aee = "AuditMessage_EditACHCompanyAccess";
  private static final String aeF = "_1.1";
  private static final String aed = "_1.2";
  private static final String ad5 = "_2.1";
  private static final String adW = "_2.2";
  private static final String aet = "_3.1";
  private static final String aeg = "_3.2";
  private static final String aem = "_3.3";
  private static final String ad1 = "_3.4";
  private static final String aeu = "_4.1";
  private static final String aeC = "_4.2";
  private static final String aer = "_4.3";
  private static final String aeh = "_4.4";
  private static final String aei = "_5.1";
  private static final String adZ = "_5.2";
  private static final String aez = "_5.3";
  private static final String aey = "_5.4";
  private static final String ad6 = "_entfault_1";
  private static final String aeD = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    if (localEntitlementGroupMember1 == null)
    {
      localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember1);
    }
    EntitlementGroupMember localEntitlementGroupMember2 = null;
    if (this.aep != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setId(this.adY);
      localEntitlementGroupMember2.setMemberType(this.aep);
      localEntitlementGroupMember2.setMemberSubType(this.aek);
      localEntitlementGroupMember2.setEntitlementGroupId(this.ad8);
    }
    int i = 1;
    if (this._initOnly) {
      i = this.aeb == null ? 1 : 0;
    }
    if ((!this._initOnly) || (i != 0))
    {
      str = modifyACHCompanyEntitlementsPhase1(localSecureUser, localEntitlementGroupMember1, localEntitlementGroupMember2, localHttpSession, null);
      if (this.error != 0) {
        return str;
      }
    }
    if ((!this._initOnly) || (i == 0))
    {
      try
      {
        EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
        if (localSecureUser.getUserType() == 2)
        {
          EntitlementGroupMember localEntitlementGroupMember3 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember3, new Entitlement("BusinessProfileEdit", null, null)))
          {
            LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_EditACHCompanyAccess_entfault_1", null);
            Initialize.logEntitlementFault(localSecureUser, localLocalizableString, com.ffusion.util.logging.TrackingIDGenerator.GetNextID());
            throw new CSILException("EditACHCompanyAccess", 20001);
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
        return str;
      }
      HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditACHCompanyAccess", this.ad8, this.adY, this.ad7);
      str = modifyACHCompanyEntitlementsPhase2(localSecureUser, localEntitlementGroupMember1, localEntitlementGroupMember2, localHttpSession, localHistoryTracker);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for EditACHCompanyAccess: " + localProfileException.toString());
      }
    }
    return str;
  }
  
  protected String modifyACHCompanyEntitlementsPhase1(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, HttpSession paramHttpSession, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = new ArrayList();
    if ((this.ad4 == null) || (this.ad4.equals(""))) {
      localArrayList1.add("cross ACH company");
    } else {
      localArrayList1.add("per ACH company");
    }
    localHashMap.put("category", localArrayList1);
    try
    {
      this.ad9 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = 1000;
      return super.getTaskErrorURL();
    }
    if ((this.aew == null) || (this.aew.equals("")))
    {
      this.error = 94;
      return this.taskErrorURL;
    }
    this.aea = ((EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.aew));
    if (this.aea == null)
    {
      this.error = 4549;
      return this.taskErrorURL;
    }
    this.ad0 = null;
    this.aes = null;
    this.aef = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.aeb = new com.ffusion.csil.beans.entitlements.Entitlements();
    this.aev = new Limits();
    this.aeo = new Limits();
    this.ad3 = new Limits();
    this.ael = new ArrayList();
    int[] arrayOfInt = { 1, 2, 1, 2 };
    try
    {
      if (paramEntitlementGroupMember2 == null)
      {
        this.ad0 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember1, this.ad8);
        this.aes = com.ffusion.csil.core.Entitlements.getGroupLimits(this.ad8);
      }
      else
      {
        this.ad0 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2);
        this.aes = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember2);
      }
      HashSet localHashSet = new HashSet(this.aea.size() * 4 / 3 + 1);
      Iterator localIterator = this.aea.iterator();
      while (localIterator.hasNext())
      {
        localObject1 = (EntitlementTypePropertyList)localIterator.next();
        localHashSet.add(((EntitlementTypePropertyList)localObject1).getOperationName());
        if (((EntitlementTypePropertyList)localObject1).isPropertySet("admin partner")) {
          localHashSet.add(((EntitlementTypePropertyList)localObject1).getPropertyValue("admin partner", 0));
        }
      }
      localIterator = this.aea.iterator();
      Entitlement localEntitlement1;
      Object localObject3;
      while (localIterator.hasNext())
      {
        localObject1 = (EntitlementTypePropertyList)localIterator.next();
        localObject2 = ((EntitlementTypePropertyList)localObject1).getPropertyValue("form element name", 0);
        int i = 0;
        if (((EntitlementTypePropertyList)localObject1).isPropertySet("admin partner")) {
          i = 1;
        }
        if ((localObject2 != null) && (!((String)localObject2).equals("")))
        {
          localEntitlement1 = null;
          Entitlement localEntitlement2 = null;
          localObject3 = null;
          String str3 = null;
          if (i != 0) {
            if ((this.ad4 == null) || (this.ad4.equals(""))) {
              localEntitlement1 = new Entitlement(((EntitlementTypePropertyList)localObject1).getPropertyValue("admin partner", 0), null, null);
            } else {
              localEntitlement1 = new Entitlement(((EntitlementTypePropertyList)localObject1).getPropertyValue("admin partner", 0), this.adX, this.ad4);
            }
          }
          if ((this.ad4 == null) || (this.ad4.equals(""))) {
            localEntitlement2 = new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), null, null);
          } else {
            localEntitlement2 = new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), this.adX, this.ad4);
          }
          str3 = (String)paramHttpSession.getAttribute(this.aeE + (String)localObject2);
          localObject3 = (String)paramHttpSession.getAttribute("admin" + (String)localObject2);
          if (i != 0) {
            if ((this.ad0.contains(localEntitlement1)) && (!((EntitlementTypePropertyList)localObject1).isPropertySet("hide admin box", "yes")))
            {
              if (localObject3 != null)
              {
                this.aeb.add(localEntitlement1);
                this.ad0.remove(localEntitlement1);
                localHashSet.remove(localObject3);
              }
            }
            else if (localObject3 == null)
            {
              this.aef.add(localEntitlement1);
              this.ad0.add(localEntitlement1);
            }
            else
            {
              localHashSet.remove(localObject3);
            }
          }
          if (str3 == null)
          {
            if (!this.ad0.contains(localEntitlement2))
            {
              this.aef.add(localEntitlement2);
              this.ad0.add(localEntitlement2);
            }
          }
          else if ((this.ad0.contains(localEntitlement2)) && (!((EntitlementTypePropertyList)localObject1).isPropertySet("hide box", "yes")))
          {
            this.aeb.add(localEntitlement2);
            this.ad0.remove(localEntitlement2);
            localHashSet.remove(str3);
          }
          else
          {
            localHashSet.remove(str3);
          }
          ArrayList localArrayList2 = new ArrayList();
          ArrayList localArrayList3 = new ArrayList();
          ArrayList localArrayList4 = new ArrayList();
          int k = 0;
          int m = 0;
          int n = 0;
          if (((EntitlementTypePropertyList)localObject1).isPropertySet("credit", "yes"))
          {
            m = 1;
            k += 2;
          }
          if (((EntitlementTypePropertyList)localObject1).isPropertySet("debit", "yes"))
          {
            n = 1;
            k += 2;
          }
          if (m != 0)
          {
            localArrayList2.add((String)paramHttpSession.getAttribute("perBatchCredit" + (String)localObject2));
            localArrayList2.add((String)paramHttpSession.getAttribute("dailyCredit" + (String)localObject2));
            localArrayList3.add((String)paramHttpSession.getAttribute("approvePerBatchCredit" + (String)localObject2));
            localArrayList3.add((String)paramHttpSession.getAttribute("approveDailyCredit" + (String)localObject2));
            if ((this.ad4 == null) || (this.ad4.equals(""))) {
              localArrayList4.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName() + " (Credit)", null, null));
            } else {
              localArrayList4.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName() + " (Credit)", this.adX, this.ad4));
            }
          }
          if (n != 0)
          {
            localArrayList2.add((String)paramHttpSession.getAttribute("perBatchDebit" + (String)localObject2));
            localArrayList2.add((String)paramHttpSession.getAttribute("dailyDebit" + (String)localObject2));
            localArrayList3.add((String)paramHttpSession.getAttribute("approvePerBatchDebit" + (String)localObject2));
            localArrayList3.add((String)paramHttpSession.getAttribute("approveDailyDebit" + (String)localObject2));
            if ((this.ad4 == null) || (this.ad4.equals(""))) {
              localArrayList4.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName() + " (Debit)", null, null));
            } else {
              localArrayList4.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName() + " (Debit)", this.adX, this.ad4));
            }
          }
          for (int i1 = 0; i1 < k; i1++)
          {
            String str4 = (String)localArrayList2.get(i1);
            String str5 = (String)localArrayList3.get(i1);
            Entitlement localEntitlement3 = null;
            if (i1 < 2) {
              localEntitlement3 = (Entitlement)localArrayList4.get(0);
            } else {
              localEntitlement3 = (Entitlement)localArrayList4.get(1);
            }
            if ((str4 != null) && (str4.length() > 0)) {
              try
              {
                Float.parseFloat(str4);
              }
              catch (NumberFormatException localNumberFormatException)
              {
                this.error = 4543;
                return this.taskErrorURL;
              }
            }
            if ((str4 != null) && (str4.length() > 30))
            {
              this.error = 86;
              return this.taskErrorURL;
            }
            boolean bool2;
            if (str5 == null) {
              bool2 = false;
            } else {
              bool2 = str5.equalsIgnoreCase("true");
            }
            Limit localLimit2 = new Limit(this.ad8, arrayOfInt[i1], str4, localEntitlement3, bool2);
            if (paramEntitlementGroupMember2 == null)
            {
              localLimit2.setRunningTotalType('G');
            }
            else
            {
              localLimit2.setMemberId(paramEntitlementGroupMember2.getId());
              localLimit2.setMemberType(paramEntitlementGroupMember2.getMemberType());
              localLimit2.setMemberSubType(paramEntitlementGroupMember2.getMemberSubType());
              localLimit2.setRunningTotalType('U');
            }
            int i2 = 0;
            for (int i3 = 0; i3 < this.aes.size(); i3++)
            {
              Limit localLimit3 = (Limit)this.aes.get(i3);
              if (localLimit3.isLimitInfoIdentical(localLimit2))
              {
                if ((str4 == null) || (str4.equals("")))
                {
                  this.ad3.add(localLimit3);
                }
                else if ((!str4.equals(localLimit3.getData())) || (!localLimit2.getAllowApproval().equals(localLimit3.getAllowApproval())))
                {
                  this.ael.add(localLimit3.getData());
                  localLimit3.setData(str4);
                  localLimit3.setAllowApproval(localLimit2.isAllowedApproval());
                  this.aeo.add(localLimit3);
                }
                i2 = 1;
                break;
              }
            }
            if ((i2 == 0) && (str4 != null) && (!str4.equals(""))) {
              this.aev.add(localLimit2);
            }
          }
        }
      }
      localIterator = this.aea.iterator();
      String str2;
      while (localIterator.hasNext())
      {
        localObject1 = (EntitlementTypePropertyList)localIterator.next();
        localObject2 = null;
        if ((this.ad4 == null) || (this.ad4.equals(""))) {
          localObject2 = new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), null, null);
        } else {
          localObject2 = new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), this.adX, this.ad4);
        }
        str2 = ((EntitlementTypePropertyList)localObject1).getPropertyValue("admin partner", 0);
        localEntitlement1 = null;
        if ((this.ad4 == null) || (this.ad4.equals(""))) {
          localEntitlement1 = new Entitlement(str2, null, null);
        } else {
          localEntitlement1 = new Entitlement(str2, this.adX, this.ad4);
        }
        boolean bool1 = jdMethod_new(paramEntitlementGroupMember2, (EntitlementTypePropertyList)localObject1, (Entitlement)localObject2, localEntitlement1, false);
        if ((!this._initOnly) && (!bool1))
        {
          this.error = 4548;
          return this.taskErrorURL;
        }
      }
      Object localObject1 = null;
      Object localObject2 = localHashSet.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        str2 = (String)((Iterator)localObject2).next();
        if ((this.ad4 == null) || (this.ad4.equals(""))) {
          localObject1 = new Entitlement(str2, null, null);
        } else {
          localObject1 = new Entitlement(str2, this.adX, this.ad4);
        }
        if (!this.ad0.contains(localObject1)) {
          this.ad0.add(localObject1);
        }
        if (str2.indexOf(" (admin)") == -1)
        {
          if ((this.ad4 == null) || (this.ad4.equals(""))) {
            localObject1 = new Entitlement(str2 + " (Credit)", null, null);
          } else {
            localObject1 = new Entitlement(str2 + " (Credit)", this.adX, this.ad4);
          }
          Limit localLimit1;
          for (int j = 0; j < this.aes.size(); j++)
          {
            localLimit1 = (Limit)this.aes.get(j);
            localObject3 = localLimit1.getEntitlement();
            if (((Entitlement)localObject1).equals(localObject3)) {
              this.ad3.add(localLimit1);
            }
          }
          if ((this.ad4 == null) || (this.ad4.equals(""))) {
            localObject1 = new Entitlement(str2 + " (Debit)", null, null);
          } else {
            localObject1 = new Entitlement(str2 + " (Debit)", this.adX, this.ad4);
          }
          for (j = 0; j < this.aes.size(); j++)
          {
            localLimit1 = (Limit)this.aes.get(j);
            localObject3 = localLimit1.getEntitlement();
            if (((Entitlement)localObject1).equals(localObject3)) {
              this.ad3.add(localLimit1);
            }
          }
        }
      }
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  protected String modifyACHCompanyEntitlementsPhase2(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, HttpSession paramHttpSession, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    try
    {
      String str2 = (String)paramHttpSession.getAttribute("Section");
      String str3 = (String)paramHttpSession.getAttribute("Context");
      if (str2 == null)
      {
        this.error = 4560;
        return this.taskErrorURL;
      }
      if (str3 == null)
      {
        this.error = 4561;
        return this.taskErrorURL;
      }
      int i;
      if (paramSecureUser.getUserType() == 2)
      {
        Business localBusiness = (Business)paramHttpSession.getAttribute("ModifyBusiness");
        i = localBusiness.getIdValue();
      }
      else
      {
        i = paramSecureUser.getBusinessID();
      }
      Object localObject1;
      String str4;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (this.aef.size() + this.aeb.size() != 0)
      {
        if (paramEntitlementGroupMember2 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember1, this.ad8, this.ad0);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, this.ad0);
        }
        String str5;
        LocalizableString localLocalizableString;
        for (j = 0; j < this.aef.size(); j++)
        {
          localObject1 = (Entitlement)this.aef.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditACHCompanyAccess" + getContextAuditKey(str2) + "_1.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditACHCompanyAccess" + getContextAuditKey(str2) + "_1.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementAdd((Entitlement)localObject1, this.aea);
        }
        for (j = 0; j < this.aeb.size(); j++)
        {
          localObject1 = (Entitlement)this.aeb.get(j);
          str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
          localObject2 = ((Entitlement)localObject1).getOperationName();
          localObject3 = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties((String)localObject2);
          localObject4 = new LocalizableProperty("display name", ((EntitlementTypePropertyList)localObject3).getPropertiesMap(), (String)localObject2);
          if (((Entitlement)localObject1).getObjectId() == null)
          {
            localObject5 = new Object[2];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            str5 = "AuditMessage_EditACHCompanyAccess" + getContextAuditKey(str2) + "_2.1";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          else
          {
            localObject5 = new Object[3];
            localObject5[0] = str3;
            localObject5[1] = localObject4;
            localObject5[2] = ((Entitlement)localObject1).getObjectId();
            str5 = "AuditMessage_EditACHCompanyAccess" + getContextAuditKey(str2) + "_2.2";
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", str5, (Object[])localObject5);
          }
          Initialize.audit(paramSecureUser, localLocalizableString, i, str4, 3225);
          paramHistoryTracker.logEntitlementDelete((Entitlement)localObject1, this.aea);
        }
      }
      for (int j = 0; j < this.aev.size(); j++)
      {
        localObject1 = (Limit)this.aev.get(j);
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember1, (Limit)localObject1);
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_3.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitAdd((Limit)localObject1, this.aea);
      }
      for (j = 0; j < this.aeo.size(); j++)
      {
        localObject1 = (Limit)this.aeo.get(j);
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember1, (Limit)this.aeo.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_4.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitChange((Limit)localObject1, this.aea, (String)this.ael.get(j));
      }
      for (j = 0; j < this.ad3.size(); j++)
      {
        localObject1 = (Limit)this.ad3.get(j);
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember1, (Limit)this.ad3.get(j));
        str4 = com.ffusion.csil.core.TrackingIDGenerator.GetNextID();
        localObject2 = null;
        if (((Limit)localObject1).getLimitName() != null)
        {
          localObject3 = com.ffusion.csil.core.Entitlements.getLimitTypeWithProperties(((Limit)localObject1).getLimitName());
          localObject2 = new LocalizableProperty("display name", ((LimitTypePropertyList)localObject3).getPropertiesMap(), ((Limit)localObject1).getLimitName());
        }
        if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[3];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[2] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.1";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() != null) && (((Limit)localObject1).getObjectId() == null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.2";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else if ((((Limit)localObject1).getLimitName() == null) && (((Limit)localObject1).getObjectId() != null))
        {
          localObject3 = new Object[4];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[3] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.3";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        else
        {
          localObject3 = new Object[5];
          localObject3[0] = ((Limit)localObject1).getData();
          localObject3[4] = ((Limit)localObject1).getCurrencyCode();
          localObject3[1] = str3;
          localObject3[2] = localObject2;
          localObject3[3] = ((Limit)localObject1).getObjectId();
          localObject5 = "AuditMessage_EditACHCompanyAccess" + getPeriodAuditKey(((Limit)localObject1).getPeriod()) + getContextAuditKey(str2) + "_5.4";
          if (((Limit)localObject1).isCrossCurrency()) {
            localObject5 = (String)localObject5 + "_xcurr";
          }
          localObject4 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject3);
        }
        Initialize.audit(paramSecureUser, (ILocalizable)localObject4, i, str4, 3225);
        paramHistoryTracker.logLimitDelete((Limit)localObject1, this.aea);
      }
      if ((paramEntitlementGroupMember2 == null) && (!this._enrollingBusiness)) {
        AutoEntitleAdmin.autoEntitleFeatures(paramSecureUser, com.ffusion.csil.core.Entitlements.getEntitlementGroup(this.ad8), this.aeb, 2, this._autoEntitle, new HashMap());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.ad0 = null;
    this.aes = null;
    this.aef = null;
    this.aeb = null;
    this.aev = null;
    this.aeo = null;
    this.ad3 = null;
    this.ael = null;
    this.aea = null;
    return str1;
  }
  
  private boolean jdMethod_new(EntitlementGroupMember paramEntitlementGroupMember, EntitlementTypePropertyList paramEntitlementTypePropertyList, Entitlement paramEntitlement1, Entitlement paramEntitlement2, boolean paramBoolean)
    throws CSILException
  {
    String str1 = "EditACHCompanyAccess.performParentChildCheck";
    if (paramEntitlementTypePropertyList == null) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement1 == null) && (paramEntitlement2 == null)) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement1 != null) && (!paramEntitlementTypePropertyList.getOperationName().equals(paramEntitlement1.getOperationName()))) {
      throw new CSILException(str1, 4548);
    }
    if ((paramEntitlement2 != null) && (!paramEntitlementTypePropertyList.isPropertySet("admin partner", paramEntitlement2.getOperationName()))) {
      throw new CSILException(str1, 4548);
    }
    String str2 = paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
    int i = paramEntitlement1 == null ? 1 : 0;
    int j = (paramEntitlement2 == null) || (str2 == null) ? 1 : 0;
    if ((i == 0) && (!paramBoolean) && (this.ad0.contains(paramEntitlement1))) {
      i = 1;
    }
    if ((j == 0) && (!paramBoolean) && (str2 != null) && (this.ad0.contains(paramEntitlement2))) {
      j = 1;
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    if ((i == 0) && (!paramBoolean) && (!this.aeb.contains(paramEntitlement1))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.ad8, paramEntitlement1)) {
          i = 1;
        }
      }
      else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement1)) {
        i = 1;
      }
    }
    if ((j == 0) && (!paramBoolean) && (!this.aeb.contains(paramEntitlement2))) {
      if (paramEntitlementGroupMember == null)
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(this.ad8, paramEntitlement2)) {
          j = 1;
        }
      }
      else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement2)) {
        j = 1;
      }
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    if (!paramEntitlementTypePropertyList.isPropertySet("control parent")) {
      return true;
    }
    if ((i == 0) && (!paramBoolean) && (paramEntitlementTypePropertyList.isPropertySet("hide box", "yes"))) {
      i = 1;
    }
    if ((j == 0) && (!paramBoolean) && (paramEntitlementTypePropertyList.isPropertySet("hide admin box", "yes"))) {
      j = 1;
    }
    if ((i != 0) && (j != 0)) {
      return true;
    }
    int k = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    for (int m = 0; m < k; m++)
    {
      String str3 = paramEntitlementTypePropertyList.getPropertyValue("control parent", m);
      Entitlement localEntitlement1 = null;
      if ((this.ad4 == null) || (this.ad4.equals(""))) {
        localEntitlement1 = new Entitlement(str3, null, null);
      } else {
        localEntitlement1 = new Entitlement(str3, this.adX, this.ad4);
      }
      EntitlementTypePropertyList localEntitlementTypePropertyList = this.ad9.getByOperationName(str3);
      String str4 = localEntitlementTypePropertyList.getPropertyValue("admin partner", 0);
      Entitlement localEntitlement2 = null;
      if ((this.ad4 == null) || (this.ad4.equals(""))) {
        localEntitlement2 = new Entitlement(str4, null, null);
      } else {
        localEntitlement2 = new Entitlement(str4, this.adX, this.ad4);
      }
      if ((i == 0) && (this.aeb.contains(localEntitlement1))) {
        i = 1;
      }
      if ((j == 0) && (this.aeb.contains(localEntitlement2))) {
        j = 1;
      }
      if ((i == 0) && (this.ad0.contains(localEntitlement1))) {
        return false;
      }
      if ((j == 0) && (str4 != null) && (this.ad0.contains(localEntitlement2))) {
        return false;
      }
      if ((this.ad4 != null) && (!this.ad4.equals("")))
      {
        localEntitlement1.setObjectType(null);
        localEntitlement1.setObjectId(null);
        localEntitlement2.setObjectType(null);
        localEntitlement2.setObjectId(null);
        if ((i == 0) && (this.ad0.contains(localEntitlement1))) {
          return false;
        }
        if ((j == 0) && (str4 != null) && (this.ad0.contains(localEntitlement2))) {
          return false;
        }
        localEntitlement1.setObjectType(this.adX);
        localEntitlement1.setObjectId(this.ad4);
        localEntitlement2.setObjectType(this.adX);
        localEntitlement2.setObjectId(this.ad4);
      }
    }
    return true;
  }
  
  protected String getPeriodAuditKey(int paramInt)
  {
    if (paramInt == 1) {
      return "_batch";
    }
    if (paramInt == 2) {
      return "_day";
    }
    return null;
  }
  
  protected String getContextAuditKey(String paramString)
  {
    if (paramString.equals("Business")) {
      return "_business";
    }
    if (paramString.equals("Company")) {
      return "_company";
    }
    if (paramString.equals("Divisions")) {
      return "_division";
    }
    if (paramString.equals("Groups")) {
      return "_group";
    }
    return "_default";
  }
  
  public void setProfileId(String paramString)
  {
    this.ad7 = paramString;
  }
  
  public void setPrefixName(String paramString)
  {
    this.aeE = paramString;
  }
  
  public void setEntsListSessionName(String paramString)
  {
    this.aew = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.ad8 = Integer.parseInt(paramString);
  }
  
  public void setMemberId(String paramString)
  {
    this.adY = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this.aep = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.aek = paramString;
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.ad4 = paramString;
  }
  
  public void setAutoEntitle(String paramString)
  {
    this._autoEntitle = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAutoEntitle()
  {
    return new Boolean(this._autoEntitle).toString();
  }
  
  public void setInitOnly(String paramString)
  {
    this._initOnly = Boolean.valueOf(paramString).booleanValue();
    this.ad0 = null;
    this.aes = null;
    this.aef = null;
    this.aeb = null;
    this.aev = null;
    this.aeo = null;
    this.ad3 = null;
    this.ael = null;
    this.aea = null;
  }
  
  public String getInitOnly()
  {
    return new Boolean(this._initOnly).toString();
  }
  
  public String getNumGrantedEntitlements()
  {
    return Integer.toString(this.aeb.size());
  }
  
  public void setEnrollingBusiness(String paramString)
  {
    this._enrollingBusiness = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getEnrollingBusiness()
  {
    return this._enrollingBusiness;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.EditACHCompanyAccess
 * JD-Core Version:    0.7.0.1
 */