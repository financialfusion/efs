package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsEntitled
  extends BaseTask
{
  private String Qi;
  private static final String Qg = "com.ffusion.util.logging.audit.task";
  private static final String Qh = "AuditMessage_IsEntitled_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    try
    {
      if (!Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement(this.Qi, null, null)))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.Qi;
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_IsEntitled_1", arrayOfObject);
        Initialize.logEntitlementFault(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException("IsEntitled", 20001);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setEntitlement(String paramString)
  {
    this.Qi = paramString;
  }
  
  public String getEntitlement()
  {
    return this.Qi;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.IsEntitled
 * JD-Core Version:    0.7.0.1
 */