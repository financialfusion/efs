package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteEntitlementGroup
  extends BaseTask
  implements Task
{
  private int Mq = 3224;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute("Entitlement_EntitlementGroup");
    if (localEntitlementGroup == null)
    {
      this.error = 35001;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
      Entitlements.deleteEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
      String str2 = TrackingIDGenerator.GetNextID();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Remove " + localEntitlementGroup.getEntGroupType().toLowerCase() + " '");
      localStringBuffer.append(localEntitlementGroup.getGroupName());
      localStringBuffer.append("'.");
      Initialize.audit(localSecureUser, localStringBuffer.toString(), str2, this.Mq);
      int i = 0;
      int j = 0;
      if ("Division".equals(localEntitlementGroup.getEntGroupType()))
      {
        j = 6;
        i = 1;
      }
      else if ("Group".equals(localEntitlementGroup.getEntGroupType()))
      {
        j = 7;
        i = 1;
      }
      if (i != 0)
      {
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, j, Integer.toString(localEntitlementGroup.getGroupId()));
        jdMethod_for(localHistoryTracker, localEntitlementGroup, localHistoryTracker.buildLocalizableComment(2));
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for DeleteEntitlementGroup: " + localProfileException.toString());
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str1 = this.successURL;
    } else {
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  private void jdMethod_for(HistoryTracker paramHistoryTracker, EntitlementGroup paramEntitlementGroup, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(paramEntitlementGroup.getClass().getName(), "ID", paramEntitlementGroup.getGroupId(), paramILocalizable);
    paramHistoryTracker.logDelete(paramEntitlementGroup.getClass().getName(), "NAME", paramEntitlementGroup.getGroupName(), paramILocalizable);
    paramHistoryTracker.logDelete(paramEntitlementGroup.getClass().getName(), "PARENTID", paramEntitlementGroup.getParentId(), paramILocalizable);
  }
  
  public void setAuditLogTranType(String paramString)
  {
    try
    {
      this.Mq = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.DeleteEntitlementGroup
 * JD-Core Version:    0.7.0.1
 */