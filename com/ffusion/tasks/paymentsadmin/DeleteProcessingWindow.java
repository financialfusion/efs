package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteProcessingWindow
  extends ExtendedBaseTask
  implements TaskDefines
{
  public DeleteProcessingWindow()
  {
    this.beanSessionName = "ProcessingWindow";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    ProcessingWindow localProcessingWindow = (ProcessingWindow)localHttpSession.getAttribute(this.beanSessionName);
    if (localProcessingWindow == null)
    {
      this.error = 36000;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 36004;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      PaymentsAdmin.deleteProcessingWindow(localSecureUser, localProcessingWindow, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.DeleteProcessingWindow
 * JD-Core Version:    0.7.0.1
 */