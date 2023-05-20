package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Messaging3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPaymentMessages
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    if (localHttpSession.getAttribute("com.ffusion.tasks.messages.PaymentMessages") == null)
    {
      Messaging3 localMessaging3 = (Messaging3)localHttpSession.getAttribute("com.ffusion.services.Messaging3");
      com.ffusion.beans.messages.Messages localMessages = (com.ffusion.beans.messages.Messages)localHttpSession.getAttribute("Messages");
      if (localMessages == null)
      {
        localObject = (Locale)localHttpSession.getAttribute("java.util.Locale");
        localMessages = new com.ffusion.beans.messages.Messages((Locale)localObject);
        localHttpSession.setAttribute("Messages", localMessages);
      }
      localHashMap = new HashMap();
      Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localMessages = com.ffusion.csil.core.Messages.getMessages((SecureUser)localObject, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        localHttpSession.setAttribute("Messages", localMessages);
        localHttpSession.setAttribute("com.ffusion.tasks.messages.PaymentMessages", Boolean.TRUE);
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetPaymentMessages
 * JD-Core Version:    0.7.0.1
 */