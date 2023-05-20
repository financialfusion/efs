package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.csil.core.Messages;
import com.ffusion.services.Messaging;
import com.ffusion.services.MessagingAdmin;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task
{
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    localHashMap.put("SERVICE", (Messaging)localHttpSession.getAttribute("com.ffusion.services.Messaging3"));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Messages.signOn(localSecureUser, this.userName, this.password, localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    localHashMap.put("SERVICE", (MessagingAdmin)localHttpSession.getAttribute("com.ffusion.services.MessagingAdmin3"));
    try
    {
      MessageAdmin.signOn(localSecureUser, this.userName, this.password, localHashMap);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
    }
    return this.error;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SignOn
 * JD-Core Version:    0.7.0.1
 */