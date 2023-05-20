package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteOldUnreconciledTransactions
  extends ExtendedBaseTask
  implements Task
{
  private int ES = 30;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    try
    {
      Register.deleteOldUnreconciledTransactions(localSecureUser, this.ES, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setDaysOld(String paramString)
  {
    this.ES = Integer.parseInt(paramString);
  }
  
  public String getDaysOld()
  {
    return String.valueOf(this.ES);
  }
  
  public int getDaysOldValue()
  {
    return this.ES;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.DeleteOldUnreconciledTransactions
 * JD-Core Version:    0.7.0.1
 */