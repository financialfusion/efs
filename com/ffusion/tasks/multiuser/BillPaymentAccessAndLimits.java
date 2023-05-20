package com.ffusion.tasks.multiuser;

import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BillPaymentAccessAndLimits
  extends FeatureAccessAndLimits
{
  private static final Entitlement aMD = new Entitlement("Payments", null, null);
  private static final Entitlement aME = new Entitlement("Payees", null, null);
  private String aMG = "EnableBillPayments";
  private String aMF = "EnableManagePayees";
  private boolean aMC = false;
  private boolean aMH = false;
  
  public void setEnableBillPaymentsInputSessionName(String paramString)
  {
    if (paramString == null) {
      this.aMG = "EnableBillPayments";
    } else {
      this.aMG = paramString;
    }
  }
  
  public void setEnableManagePayeesInputSessionName(String paramString)
  {
    if (paramString == null) {
      this.aMF = "EnableManagePayees";
    } else {
      this.aMF = paramString;
    }
  }
  
  public void setReset(String paramString)
  {
    super.setReset(paramString);
    setAddOperationToList("Payments");
  }
  
  protected String beforeProcessing(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    int i = 1;
    if (i != 0)
    {
      str = super.beforeProcessing(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
      if (((str != null) && (!str.equals(this.successURL))) || ((str == null) && (str != this.successURL))) {
        i = 0;
      }
    }
    if (i != 0)
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(this.sUser);
      EntitlementGroupMember localEntitlementGroupMember2 = getEntitlementGroupMemberForSecondaryUser();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
      this.aMC = Boolean.valueOf((String)localHttpSession.getAttribute(this.aMG)).booleanValue();
      if (!this.aMC)
      {
        try
        {
          HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, this.secondaryUser.getId());
          EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aME))
          {
            localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
            localEntitlements.add(aME);
            localHistoryTracker.logEntitlementAdd(aME, localEntitlementTypePropertyLists);
            com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
          }
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMD))
          {
            localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
            localEntitlements.add(aMD);
            localHistoryTracker.logEntitlementAdd(aMD, localEntitlementTypePropertyLists);
            com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
          }
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.BillPaymentAccessAndLimits: " + localProfileException.toString());
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if ((str == this.successURL) || ((str != null) && (str.equals(this.successURL)))) {
          this.error = -1;
        }
      }
    }
    return str;
  }
  
  protected String afterProcessing(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(this.sUser);
    EntitlementGroupMember localEntitlementGroupMember2 = getEntitlementGroupMemberForSecondaryUser();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.aMH = Boolean.valueOf((String)localHttpSession.getAttribute(this.aMF)).booleanValue();
    try
    {
      if (this.aMC)
      {
        HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, this.secondaryUser.getId());
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
        if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMD))
        {
          localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
          localEntitlements.add(aMD);
          localHistoryTracker.logEntitlementDelete(aMD, localEntitlementTypePropertyLists);
          com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
        }
        if (this.aMH)
        {
          if (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aME))
          {
            localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
            localEntitlements.add(aME);
            localHistoryTracker.logEntitlementDelete(aME, localEntitlementTypePropertyLists);
            com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
          }
        }
        else if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aME))
        {
          localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
          localEntitlements.add(aME);
          localHistoryTracker.logEntitlementAdd(aME, localEntitlementTypePropertyLists);
          com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
        }
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.BillPaymentAccessAndLimits: " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected int performValidation(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    int i = 0;
    i = super.performValidation(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    if ((i == 0) && ((this.aMG == null) || (this.aMG.length() == 0))) {
      i = 28012;
    }
    if ((i == 0) && ((this.aMF == null) || (this.aMF.length() == 0))) {
      i = 28044;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.BillPaymentAccessAndLimits
 * JD-Core Version:    0.7.0.1
 */