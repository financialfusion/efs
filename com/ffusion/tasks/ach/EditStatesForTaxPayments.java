package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditStatesForTaxPayments
  extends BaseTask
{
  public static final int ERROR_MISSING_BUSINESS_ID = 70050;
  private static final String UG = "com.ffusion.util.states";
  private static final String UH = "Restricted";
  private static final String UE = "Allowed";
  public static final String TAXES = "taxes";
  public static final String CHILDSUPPORT = "childsupport";
  protected String prefix = "taxes";
  private int UF;
  private int UI = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    this.error = 0;
    String str1 = this.successURL;
    String str2 = "CCD + TXP";
    HashMap localHashMap = new HashMap();
    if ("taxes".equals(this.prefix))
    {
      str2 = "CCD + TXP";
      localHashMap.put("TaxFormType", Integer.toString(2));
    }
    if ("childsupport".equals(this.prefix))
    {
      str2 = "CCD + DED";
      localHashMap.put("TaxFormType", Integer.toString(4));
    }
    if (this.UI == -1)
    {
      this.error = 70050;
      str1 = this.taskErrorURL;
    }
    if (this.error == 0)
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      try
      {
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("BusinessProfileEdit", null, null)))
        {
          Initialize.logEntitlementFault(localSecureUser, "The user is not entitled to edit business profiles.", TrackingIDGenerator.GetNextID());
          throw new CSILException("EditStatesForTaxPayments", 20001);
        }
        com.ffusion.beans.business.Business localBusiness1 = new com.ffusion.beans.business.Business();
        localBusiness1.setId(this.UI);
        com.ffusion.beans.business.Business localBusiness2 = com.ffusion.csil.core.Business.getBusiness(localSecureUser, localBusiness1, new HashMap());
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        ArrayList localArrayList3 = new ArrayList();
        ArrayList localArrayList4 = new ArrayList();
        String[] arrayOfString = ACH.getStatesWithTaxForms(localSecureUser, localHashMap);
        int i = arrayOfString.length;
        for (int j = 0; j < i; j++) {
          localArrayList1.add(arrayOfString[j]);
        }
        com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember, this.UF);
        Iterator localIterator = localEntitlements.iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Entitlement)localIterator.next();
          if ((((Entitlement)localObject1).getOperationName().equals(str2)) && (((Entitlement)localObject1).getObjectType().equals("State")))
          {
            localIterator.remove();
            localArrayList2.add(((Entitlement)localObject1).getObjectId());
          }
        }
        Object localObject1 = paramHttpServletRequest.getParameterNames();
        while (((Enumeration)localObject1).hasMoreElements())
        {
          localObject2 = (String)((Enumeration)localObject1).nextElement();
          if (((String)localObject2).startsWith(this.prefix))
          {
            localObject3 = (String)localHttpSession.getAttribute((String)localObject2);
            localArrayList1.remove(localObject3);
            if (localArrayList2.contains(localObject3)) {
              localArrayList3.add(localObject3);
            }
            localHttpSession.removeAttribute((String)localObject2);
          }
        }
        localIterator = localArrayList1.iterator();
        while (localIterator.hasNext())
        {
          localObject2 = (String)localIterator.next();
          localObject3 = new Entitlement(str2, "State", (String)localObject2);
          if (!localArrayList2.contains(localObject2)) {
            localArrayList4.add(localObject2);
          }
          localEntitlements.add(localObject3);
        }
        com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(localEntitlementGroupMember, this.UF, localEntitlements);
        Object localObject2 = new StringBuffer();
        Object localObject3 = null;
        String str3 = "";
        int k = -1;
        ((StringBuffer)localObject2).append("Modified the set of states that the business can submit ");
        if ("taxes".equals(this.prefix))
        {
          ((StringBuffer)localObject2).append("tax");
          k = 1343;
          str3 = "Tax Payments - Allowed States";
        }
        else if ("childsupport".equals(this.prefix))
        {
          ((StringBuffer)localObject2).append("child support");
          k = 1357;
          str3 = "Child Support Payments - Allowed States";
        }
        ((StringBuffer)localObject2).append(" payments in.");
        localObject3 = TrackingIDGenerator.GetNextID();
        Initialize.audit(localSecureUser, ((StringBuffer)localObject2).toString(), this.UI, (String)localObject3, k);
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, Integer.toString(this.UI), (String)localObject3);
        StringBuffer localStringBuffer1 = null;
        StringBuffer localStringBuffer2 = null;
        for (int m = 0; m < localArrayList3.size(); m++)
        {
          localStringBuffer2 = new StringBuffer("State");
          localStringBuffer2.append((String)localArrayList3.get(m));
          localStringBuffer1 = new StringBuffer();
          localStringBuffer1.append(ResourceUtil.getString(localStringBuffer2.toString(), "com.ffusion.util.states", localSecureUser.getLocale()));
          localStringBuffer1.append(" State");
          localHistoryTracker.logChange(str3, "Restricted", "Allowed", localStringBuffer1.toString());
        }
        for (m = 0; m < localArrayList4.size(); m++)
        {
          localStringBuffer2 = new StringBuffer("State");
          localStringBuffer2.append((String)localArrayList4.get(m));
          localStringBuffer1 = new StringBuffer();
          localStringBuffer1.append(ResourceUtil.getString(localStringBuffer2.toString(), "com.ffusion.util.states", localSecureUser.getLocale()));
          localStringBuffer1.append(" State");
          localHistoryTracker.logChange(str3, "Allowed", "Restricted", localStringBuffer1.toString());
        }
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add history failed for EditStatesForTaxPayments: " + localProfileException);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setGroupId(String paramString)
  {
    this.UF = Integer.parseInt(paramString);
  }
  
  public void setPrefix(String paramString)
  {
    this.prefix = paramString;
  }
  
  protected String getObjectType()
  {
    return "State";
  }
  
  public void setBusinessID(String paramString)
  {
    try
    {
      this.UI = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.UI = -1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.EditStatesForTaxPayments
 * JD-Core Version:    0.7.0.1
 */