package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteGlobalPayee
  extends Payee
  implements GlobalPayeeTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    Payee localPayee = new Payee();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localPayee = (Payee)localHttpSession.getAttribute("Payee");
      this.error = 100408;
      if (localPayee == null)
      {
        str = this.taskErrorURL;
        return str;
      }
      set(localPayee);
      PaymentsAdmin.deleteGlobalPayee(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.DeleteGlobalPayee
 * JD-Core Version:    0.7.0.1
 */