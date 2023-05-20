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

public class ExternalTransferAccessAndLimits
  extends FeatureAccessAndLimits
{
  private static final Entitlement aMv = new Entitlement("External Transfers", null, null);
  private static final Entitlement aMt = new Entitlement("External Transfers From", null, null);
  private static final Entitlement aMw = new Entitlement("External Transfers To", null, null);
  private String aMu = "EnableExternalTransfers";
  private boolean aMs = false;
  
  public void setEnableTransfersInputSessionName(String paramString)
  {
    if (paramString == null) {
      this.aMu = "EnableExternalTransfers";
    } else {
      this.aMu = paramString;
    }
  }
  
  public void setReset(String paramString)
  {
    super.setReset(paramString);
    setAddOperationToList("External Transfers From");
    setAddOperationToList("External Transfers To");
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
      this.aMs = Boolean.valueOf((String)localHttpSession.getAttribute(this.aMu)).booleanValue();
      if (!this.aMs)
      {
        try
        {
          HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, this.secondaryUser.getId());
          EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
          localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
          if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMw)) && (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMw)))
          {
            localEntitlements.add(aMw);
            localHistoryTracker.logEntitlementAdd(aMw, localEntitlementTypePropertyLists);
          }
          if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMt)) && (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMt)))
          {
            localEntitlements.add(aMt);
            localHistoryTracker.logEntitlementAdd(aMt, localEntitlementTypePropertyLists);
          }
          com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
          localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
          if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMv)) && (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMv)))
          {
            localEntitlements.add(aMv);
            localHistoryTracker.logEntitlementAdd(aMv, localEntitlementTypePropertyLists);
          }
          com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
          try
          {
            HistoryAdapter.addHistory(localHistoryTracker.getHistories());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.ExternalTransferAccessAndLimits: " + localProfileException.toString());
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
          i = 0;
        }
        if (i != 0) {
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
    try
    {
      if (this.aMs)
      {
        HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, this.secondaryUser.getId());
        EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
        localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
        if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMv)) && (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMv)))
        {
          localEntitlements.add(aMv);
          localHistoryTracker.logEntitlementDelete(aMv, localEntitlementTypePropertyLists);
        }
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
        localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
        if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMw)) && (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMw)))
        {
          localEntitlements.add(aMw);
          localHistoryTracker.logEntitlementDelete(aMw, localEntitlementTypePropertyLists);
        }
        if ((com.ffusion.csil.core.Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), aMt)) && (!com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember2, aMt)))
        {
          localEntitlements.add(aMt);
          localHistoryTracker.logEntitlementDelete(aMt, localEntitlementTypePropertyLists);
        }
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.ExternalTransferAccessAndLimits: " + localProfileException.toString());
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
    if ((i == 0) && ((this.aMu == null) || (this.aMu.length() == 0))) {
      i = 28011;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.ExternalTransferAccessAndLimits
 * JD-Core Version:    0.7.0.1
 */