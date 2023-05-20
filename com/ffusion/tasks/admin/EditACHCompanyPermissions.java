package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditACHCompanyPermissions
  extends BaseTask
  implements AdminTask
{
  public static final String ENTITLEMENT = "entitlement";
  public static final String TAX_PAYMENT = "taxPayment";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  private String aaB;
  private String aaC;
  private String aaN;
  private int aaO;
  private String aaM = "FILTERED_ACHCOMPANIES";
  private String aaQ;
  private String aaD;
  private String aaF = "";
  private String aax = "";
  private static final String aaJ = "com.ffusion.util.logging.audit.task";
  private static final String aaw = "AuditMessage_EditACHCompanyPermissions";
  private static final String aaK = "_1";
  private static final String aaE = "_2";
  private static final String aaA = "_3.1";
  private static final String aaR = "_3.2";
  private static final String aaH = "_4.1";
  private static final String aay = "_4.2";
  private static final String aaz = "_5.1";
  private static final String aav = "_5.2";
  private static final String aaP = "AuditMessage_LimitPrefix_DisplayName_ach";
  private static final String aaG = "AuditMessage_LimitPrefix_DisplayName_tax";
  private static final String aaL = "AuditMessage_LimitPrefix_DisplayName_child";
  private static final String aaI = "_xcurr";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    int i = !"false".equals(this.aaD) ? 1 : 0;
    if (i != 0) {
      localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    }
    if (localEntitlementGroupMember1 == null)
    {
      try
      {
        localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
        localEntitlementGroupMember1 = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return super.getServiceErrorURL();
      }
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember1);
    }
    EntitlementGroupMember localEntitlementGroupMember2 = null;
    if (this.aaC != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setMemberType(this.aaC);
      localEntitlementGroupMember2.setMemberSubType(this.aaN);
      localEntitlementGroupMember2.setId(this.aaB);
      localEntitlementGroupMember2.setEntitlementGroupId(this.aaO);
    }
    HistoryTracker localHistoryTracker = EditAccountPermissions.jdMethod_new(localSecureUser, "EditACHCompanyPermissions", this.aaO, this.aaB, this.aaQ);
    str = jdMethod_new(localSecureUser, localEntitlementGroupMember2, localHttpSession, paramHttpServletRequest, localEntitlementGroupMember1, localHistoryTracker);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for EditACHCompanyPermissions: " + localProfileException.toString());
    }
    if ((i != 0) && (this.error == 0)) {
      localHttpSession.removeAttribute("LastRequest");
    }
    return str;
  }
  
  private String jdMethod_new(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember1, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest, EntitlementGroupMember paramEntitlementGroupMember2, HistoryTracker paramHistoryTracker)
  {
    String str1 = this.successURL;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    Limits localLimits = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    ArrayList localArrayList5 = new ArrayList();
    ArrayList localArrayList6 = new ArrayList();
    ArrayList localArrayList7 = new ArrayList();
    ArrayList localArrayList8 = new ArrayList();
    int[] arrayOfInt = { 1, 2, 3, 4 };
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      if (paramEntitlementGroupMember1 == null)
      {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, this.aaO);
        localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(this.aaO);
      }
      else
      {
        localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1);
        localLimits = com.ffusion.csil.core.Entitlements.getGroupLimits(paramEntitlementGroupMember1);
      }
      ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.aaM);
      HashMap localHashMap1 = new HashMap();
      ArrayList localArrayList9 = new ArrayList();
      if ((this.aax == null) || (this.aax.length() == 0))
      {
        localArrayList9.add("");
      }
      else
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(this.aax, ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          localObject1 = localStringTokenizer.nextToken();
          localArrayList9.add(localObject1);
        }
      }
      int i = 0;
      Object localObject1 = localACHCompanies.iterator();
      Object localObject6;
      Object localObject7;
      Object localObject8;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ACHCompany)((Iterator)localObject1).next();
        localHashMap1.put(((ACHCompany)localObject2).getCompanyID(), ((ACHCompany)localObject2).getCompanyName());
        str2 = (String)paramHttpSession.getAttribute("entitlement" + i);
        LimitTypePropertyLists localLimitTypePropertyLists = (LimitTypePropertyLists)paramHttpSession.getAttribute(this.aaF);
        int k = localLimitTypePropertyLists.size();
        for (int m = 0; m < k; m++)
        {
          int n = 1;
          if (str2 == null)
          {
            n = 0;
            str2 = ((ACHCompany)localObject2).getCompanyID();
          }
          localObject6 = new Entitlement(((LimitTypePropertyList)localLimitTypePropertyLists.get(m)).getOperationName(), "ACHCompany", str2);
          if (n != 0)
          {
            if (localEntitlements.contains(localObject6))
            {
              localArrayList2.add(((ACHCompany)localObject2).getCompanyName());
              localArrayList4.add(localObject6);
            }
            localEntitlements.remove(localObject6);
          }
          else if (!localEntitlements.contains(localObject6))
          {
            localEntitlements.add(localObject6);
            localArrayList1.add(((ACHCompany)localObject2).getCompanyName());
            localArrayList3.add(localObject6);
          }
          String str3 = (String)localArrayList9.get(m);
          localObject7 = new String[] { (String)paramHttpSession.getAttribute(str3 + "transaction_limit" + i), (String)paramHttpSession.getAttribute(str3 + "day_limit" + i), (String)paramHttpSession.getAttribute(str3 + "week_limit" + i), (String)paramHttpSession.getAttribute(str3 + "month_limit" + i) };
          localObject8 = new String[] { (String)paramHttpSession.getAttribute(str3 + "transaction_exceed" + i), (String)paramHttpSession.getAttribute(str3 + "day_exceed" + i), (String)paramHttpSession.getAttribute(str3 + "week_exceed" + i), (String)paramHttpSession.getAttribute(str3 + "month_exceed" + i) };
          for (int i6 = 0; i6 < arrayOfInt.length; i6++)
          {
            this.error = TaskUtil.validateLimitAmount(localObject7[i6]);
            if (this.error != 0) {
              return this.taskErrorURL;
            }
            boolean bool;
            if (localObject8[i6] == null) {
              bool = false;
            } else {
              bool = localObject8[i6].equalsIgnoreCase("true");
            }
            Limit localLimit1 = new Limit(this.aaO, arrayOfInt[i6], localObject7[i6], (Entitlement)localObject6, bool);
            if (paramEntitlementGroupMember1 == null)
            {
              localLimit1.setRunningTotalType('G');
            }
            else
            {
              localLimit1.setMemberType(paramEntitlementGroupMember1.getMemberType());
              localLimit1.setMemberSubType(paramEntitlementGroupMember1.getMemberSubType());
              localLimit1.setMemberId(paramEntitlementGroupMember1.getId());
              localLimit1.setRunningTotalType('U');
            }
            int i7 = 0;
            for (int i8 = 0; i8 < localLimits.size(); i8++)
            {
              Limit localLimit2 = (Limit)localLimits.get(i8);
              if (localLimit2.isLimitInfoIdentical(localLimit1))
              {
                HashMap localHashMap3;
                if ((localObject7[i6] == null) || (localObject7[i6].equals("")) || (n == 0))
                {
                  localHashMap3 = new HashMap();
                  localHashMap3.put("Limit", localLimit2);
                  localHashMap3.put("LimitPrefix", str3);
                  localArrayList7.add(localHashMap3);
                }
                else if (((!localObject7[i6].equals(localLimit2.getData())) || (!localLimit1.getAllowApproval().equals(localLimit2.getAllowApproval()))) && (n != 0))
                {
                  localArrayList8.add(localLimit2.getData());
                  localLimit2.setData(localObject7[i6]);
                  localLimit2.setAllowApproval(localLimit1.isAllowedApproval());
                  localHashMap3 = new HashMap();
                  localHashMap3.put("Limit", localLimit2);
                  localHashMap3.put("LimitPrefix", str3);
                  localArrayList6.add(localHashMap3);
                }
                i7 = 1;
                break;
              }
            }
            if ((i7 == 0) && (localObject7[i6] != null) && (!localObject7[i6].equals("")) && (n != 0))
            {
              HashMap localHashMap2 = new HashMap();
              localHashMap2.put("Limit", localLimit1);
              localHashMap2.put("LimitPrefix", str3);
              localArrayList5.add(localHashMap2);
            }
          }
        }
        i++;
      }
      Object localObject2 = (String)paramHttpSession.getAttribute("Section");
      String str2 = (String)paramHttpSession.getAttribute("Context");
      if (localObject2 == null)
      {
        this.error = 4560;
        return this.taskErrorURL;
      }
      if (str2 == null)
      {
        this.error = 4561;
        return this.taskErrorURL;
      }
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (localArrayList1.size() + localArrayList2.size() != 0)
      {
        if (paramEntitlementGroupMember1 == null) {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, this.aaO, localEntitlements);
        } else {
          com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(paramEntitlementGroupMember2, paramEntitlementGroupMember1, localEntitlements);
        }
        for (j = 0; j < localArrayList1.size(); j++)
        {
          localObject3 = TrackingIDGenerator.GetNextID();
          localObject4 = new Object[2];
          localObject4[0] = str2;
          localObject4[1] = localArrayList1.get(j);
          localObject5 = "AuditMessage_EditACHCompanyPermissions" + getContextAuditKey((String)localObject2) + "_1";
          localObject6 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject4);
          if (paramSecureUser.getUserType() == 2) {
            try
            {
              int i1 = Integer.parseInt(localACHCompanies.getByName((String)localArrayList1.get(j)).getCustID());
              Initialize.audit(paramSecureUser, (ILocalizable)localObject6, i1, (String)localObject3, 3225);
            }
            catch (Exception localException1)
            {
              Initialize.audit(paramEntitlementGroupMember2, (ILocalizable)localObject6, (String)localObject3, 3225);
            }
          } else {
            Initialize.audit(paramEntitlementGroupMember2, (ILocalizable)localObject6, (String)localObject3, 3225);
          }
          paramHistoryTracker.logEntitlementAdd((Entitlement)localArrayList3.get(j), null);
        }
        for (j = 0; j < localArrayList2.size(); j++)
        {
          localObject3 = TrackingIDGenerator.GetNextID();
          localObject4 = new Object[2];
          localObject4[0] = str2;
          localObject4[1] = localArrayList2.get(j);
          localObject5 = "AuditMessage_EditACHCompanyPermissions" + getContextAuditKey((String)localObject2) + "_2";
          localObject6 = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject5, (Object[])localObject4);
          if (paramSecureUser.getUserType() == 2) {
            try
            {
              int i2 = Integer.parseInt(localACHCompanies.getByName((String)localArrayList2.get(j)).getCustID());
              Initialize.audit(paramSecureUser, (ILocalizable)localObject6, i2, (String)localObject3, 3225);
            }
            catch (Exception localException2)
            {
              Initialize.audit(paramEntitlementGroupMember2, (ILocalizable)localObject6, (String)localObject3, 3225);
            }
          } else {
            Initialize.audit(paramEntitlementGroupMember2, (ILocalizable)localObject6, (String)localObject3, 3225);
          }
          paramHistoryTracker.logEntitlementDelete((Entitlement)localArrayList4.get(j), null);
        }
      }
      LocalizableString localLocalizableString;
      for (int j = 0; j < localArrayList5.size(); j++)
      {
        localObject3 = (HashMap)localArrayList5.get(j);
        localObject4 = (String)((HashMap)localObject3).get("LimitPrefix");
        localObject5 = (Limit)((HashMap)localObject3).get("Limit");
        com.ffusion.csil.core.Entitlements.addGroupLimit(paramEntitlementGroupMember2, (Limit)localObject5);
        localObject6 = TrackingIDGenerator.GetNextID();
        if ((localObject4 != null) && (((String)localObject4).length() > 0))
        {
          localObject7 = new Object[5];
          localObject7[0] = ((Limit)localObject5).getData();
          localObject7[4] = ((Limit)localObject5).getCurrencyCode();
          localObject7[1] = str2;
          localObject7[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          localObject7[3] = getLimitPrefixDisplayText((String)localObject4);
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_3.2";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, (Object[])localObject7);
        }
        else
        {
          localObject7 = new Object[4];
          localObject7[0] = ((Limit)localObject5).getData();
          localObject7[3] = ((Limit)localObject5).getCurrencyCode();
          localObject7[1] = str2;
          localObject7[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_3.1";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, (Object[])localObject7);
        }
        if (paramSecureUser.getUserType() == 2) {
          try
          {
            int i3 = Integer.parseInt(localACHCompanies.getByName((String)localHashMap1.get(((Limit)localObject5).getObjectId())).getCustID());
            Initialize.audit(paramSecureUser, localLocalizableString, i3, (String)localObject6, 3225);
          }
          catch (Exception localException3)
          {
            Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
          }
        } else {
          Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
        }
        paramHistoryTracker.logLimitAdd((Limit)localObject5, null);
      }
      for (j = 0; j < localArrayList6.size(); j++)
      {
        localObject3 = (HashMap)localArrayList6.get(j);
        localObject4 = (String)((HashMap)localObject3).get("LimitPrefix");
        localObject5 = (Limit)((HashMap)localObject3).get("Limit");
        com.ffusion.csil.core.Entitlements.modifyGroupLimit(paramEntitlementGroupMember2, (Limit)localObject5);
        localObject6 = TrackingIDGenerator.GetNextID();
        Object[] arrayOfObject1;
        if ((localObject4 != null) && (((String)localObject4).length() > 0))
        {
          arrayOfObject1 = new Object[5];
          arrayOfObject1[0] = ((Limit)localObject5).getData();
          arrayOfObject1[4] = ((Limit)localObject5).getCurrencyCode();
          arrayOfObject1[1] = str2;
          arrayOfObject1[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          arrayOfObject1[3] = getLimitPrefixDisplayText((String)localObject4);
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_4.2";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject1);
        }
        else
        {
          arrayOfObject1 = new Object[4];
          arrayOfObject1[0] = ((Limit)localObject5).getData();
          arrayOfObject1[3] = ((Limit)localObject5).getCurrencyCode();
          arrayOfObject1[1] = str2;
          arrayOfObject1[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_4.1";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject1);
        }
        if (paramSecureUser.getUserType() == 2) {
          try
          {
            int i4 = Integer.parseInt(localACHCompanies.getByName((String)localHashMap1.get(((Limit)localObject5).getObjectId())).getCustID());
            Initialize.audit(paramSecureUser, localLocalizableString, i4, (String)localObject6, 3225);
          }
          catch (Exception localException4)
          {
            Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
          }
        } else {
          Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
        }
        paramHistoryTracker.logLimitChange((Limit)localObject5, null, (String)localArrayList8.get(j));
      }
      for (j = 0; j < localArrayList7.size(); j++)
      {
        localObject3 = (HashMap)localArrayList7.get(j);
        localObject4 = (String)((HashMap)localObject3).get("LimitPrefix");
        localObject5 = (Limit)((HashMap)localObject3).get("Limit");
        com.ffusion.csil.core.Entitlements.deleteGroupLimit(paramEntitlementGroupMember2, (Limit)localObject5);
        localObject6 = TrackingIDGenerator.GetNextID();
        Object[] arrayOfObject2;
        if ((localObject4 != null) && (((String)localObject4).length() > 0))
        {
          arrayOfObject2 = new Object[5];
          arrayOfObject2[0] = ((Limit)localObject5).getData();
          arrayOfObject2[4] = ((Limit)localObject5).getCurrencyCode();
          arrayOfObject2[1] = str2;
          arrayOfObject2[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          arrayOfObject2[3] = getLimitPrefixDisplayText((String)localObject4);
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_5.2";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject2);
        }
        else
        {
          arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = ((Limit)localObject5).getData();
          arrayOfObject2[3] = ((Limit)localObject5).getCurrencyCode();
          arrayOfObject2[1] = str2;
          arrayOfObject2[2] = localHashMap1.get(((Limit)localObject5).getObjectId());
          localObject8 = "AuditMessage_EditACHCompanyPermissions" + getPeriodAuditKey(((Limit)localObject5).getPeriod()) + getContextAuditKey((String)localObject2) + "_5.1";
          if (((Limit)localObject5).isCrossCurrency()) {
            localObject8 = (String)localObject8 + "_xcurr";
          }
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", (String)localObject8, arrayOfObject2);
        }
        if (paramSecureUser.getUserType() == 2) {
          try
          {
            int i5 = Integer.parseInt(localACHCompanies.getByName((String)localHashMap1.get(((Limit)localObject5).getObjectId())).getCustID());
            Initialize.audit(paramSecureUser, localLocalizableString, i5, (String)localObject6, 3225);
          }
          catch (Exception localException5)
          {
            Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
          }
        } else {
          Initialize.audit(paramEntitlementGroupMember2, localLocalizableString, (String)localObject6, 3225);
        }
        paramHistoryTracker.logLimitDelete((Limit)localObject5, null);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  protected String getPeriodAuditKey(int paramInt)
  {
    if (paramInt == 1) {
      return "_batch";
    }
    if (paramInt == 2) {
      return "_day";
    }
    if (paramInt == 3) {
      return "_week";
    }
    if (paramInt == 4) {
      return "_month";
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
  
  protected Object getLimitPrefixDisplayText(String paramString)
  {
    if (paramString.equalsIgnoreCase("ach")) {
      return new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_LimitPrefix_DisplayName_ach", null);
    }
    if (paramString.equalsIgnoreCase("tax")) {
      return new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_LimitPrefix_DisplayName_tax", null);
    }
    if (paramString.equalsIgnoreCase("child")) {
      return new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_LimitPrefix_DisplayName_child", null);
    }
    return paramString;
  }
  
  public void setMemberId(String paramString)
  {
    this.aaB = paramString;
  }
  
  public String getMemberId()
  {
    return this.aaB;
  }
  
  public void setMemberType(String paramString)
  {
    this.aaC = paramString;
  }
  
  public String getMemberType()
  {
    return this.aaC;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.aaN = paramString;
  }
  
  public String getMemberSubType()
  {
    return this.aaN;
  }
  
  public void setGroupId(String paramString)
  {
    this.aaO = Integer.parseInt(paramString);
  }
  
  public void setCompaniesList(String paramString)
  {
    this.aaM = paramString;
  }
  
  public void setProfileId(String paramString)
  {
    this.aaQ = paramString;
  }
  
  public void setSaveLastRequest(String paramString)
  {
    this.aaD = paramString;
  }
  
  public void setLimitPrefix(String paramString)
  {
    this.aax = paramString;
  }
  
  public void setEntitlementsListName(String paramString)
  {
    this.aaF = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.EditACHCompanyPermissions
 * JD-Core Version:    0.7.0.1
 */