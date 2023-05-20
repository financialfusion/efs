package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.EntitledMsgTypes;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEntitledGlobalMsgTypes
  extends BaseTask
  implements Task
{
  private String tm = "EntitledMsgTypes";
  private boolean tn = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitledMsgTypes localEntitledMsgTypes = null;
    try
    {
      if (this.tn) {
        localEntitledMsgTypes = Messages.getEntitledGlobalMsgTypesToView(localSecureUser, localHashMap);
      } else {
        localEntitledMsgTypes = Messages.getEntitledGlobalMsgTypesToCED(localSecureUser, localHashMap);
      }
      localEntitledMsgTypes.setSortedBy("MessageDescription");
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.tm, localEntitledMsgTypes);
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.tm = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.tm;
  }
  
  public void setCheckViewEntitlement(String paramString)
  {
    this.tn = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetEntitledGlobalMsgTypes
 * JD-Core Version:    0.7.0.1
 */