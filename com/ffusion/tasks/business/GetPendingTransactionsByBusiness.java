package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.PendingTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingTransactionsByBusiness
  extends BaseTask
  implements BusinessTask
{
  private String gP = "PendingTransactions";
  private String gO = null;
  private String gN = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PendingTransactions localPendingTransactions = null;
    if ((!jdMethod_new(this.gO)) && (!jdMethod_new(this.gN)))
    {
      this.error = 4130;
      return this.taskErrorURL;
    }
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      String str2 = null;
      boolean bool = jdMethod_new(this.gN);
      if (bool == true) {
        str2 = this.gN;
      } else {
        str2 = this.gO;
      }
      localPendingTransactions = Business.getPendingTransactionsByBusiness(localSecureUser, bool, str2, localHashMap);
      localHttpSession.setAttribute(this.gP, localPendingTransactions);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setBusinessID(String paramString)
  {
    this.gN = paramString;
    this.gO = null;
  }
  
  public String getBusinessID()
  {
    return this.gN;
  }
  
  public void setBusinessName(String paramString)
  {
    this.gO = paramString;
    this.gN = null;
  }
  
  public String getBusinessName()
  {
    return this.gO;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.gP = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.gP;
  }
  
  private boolean jdMethod_new(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetPendingTransactionsByBusiness
 * JD-Core Version:    0.7.0.1
 */