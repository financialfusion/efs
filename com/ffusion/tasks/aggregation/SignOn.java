package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task
{
  private String n7 = "com.ffusion.services.AccountAggregation";
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.n7);
    HashMap localHashMap = null;
    if (localAccountAggregation != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAccountAggregation);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localSecureUser = com.ffusion.csil.core.AccountAggregation.signOn(localSecureUser, this.userName, this.password, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return this.error;
  }
  
  public void setServiceName(String paramString)
  {
    this.n7 = paramString;
  }
  
  public String getServiceName()
  {
    return this.n7;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.SignOn
 * JD-Core Version:    0.7.0.1
 */