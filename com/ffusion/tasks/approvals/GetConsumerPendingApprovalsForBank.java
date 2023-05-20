package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItems;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetConsumerPendingApprovalsForBank
  extends BaseTask
  implements IApprovalsTask
{
  private String aNV = "ApprovalsItems";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = getLocale(localHttpSession, localSecureUser);
    try
    {
      localHttpSession.removeAttribute(this.aNV);
      ApprovalsItems localApprovalsItems = Approvals.getConsumerPendingApprovalsForBank(localSecureUser, localHashMap);
      if (localApprovalsItems != null)
      {
        localApprovalsItems.setLocale(localLocale);
        localHttpSession.setAttribute(this.aNV, localApprovalsItems);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.aNV = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.aNV;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetConsumerPendingApprovalsForBank
 * JD-Core Version:    0.7.0.1
 */