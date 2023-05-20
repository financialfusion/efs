package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLastMatchingTransaction
  extends RegisterTransaction
  implements Task
{
  protected int error;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  private String FJ = "RegisterTransaction";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      this.error = 20009;
    }
    HashMap localHashMap = null;
    try
    {
      RegisterTransaction localRegisterTransaction = Register.getLastMatchingTransaction(localSecureUser, this, localHashMap);
      localHttpSession.setAttribute(getRegisterTransactionSessionID(), localRegisterTransaction);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  public void setRegisterTransactionSessionID(String paramString)
  {
    this.FJ = paramString;
  }
  
  public String getRegisterTransactionSessionID()
  {
    return this.FJ;
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
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetLastMatchingTransaction
 * JD-Core Version:    0.7.0.1
 */