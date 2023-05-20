package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Accounts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedCalculations
  extends BaseTask
  implements Task
{
  private String jS = null;
  private String jR = null;
  private String jQ = null;
  private String jO = null;
  private String jP = "RestrictedCalculations";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.jO = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ArrayList localArrayList = null;
    if (this.jS == null)
    {
      this.error = 18009;
      this.jO = this.taskErrorURL;
    }
    else if (this.jR == null)
    {
      this.error = 18010;
      this.jO = this.taskErrorURL;
    }
    else if (this.jQ == null)
    {
      this.error = 18011;
      this.jO = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      try
      {
        Account localAccount = new Account();
        localAccount.setID(this.jS);
        localAccount.setBankID(this.jR);
        localAccount.setRoutingNum(this.jQ);
        localArrayList = Accounts.getRestrictedCalculations(localSecureUser, localAccount, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.jO = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.jP, localArrayList);
      this.jO = this.successURL;
    }
    return this.jO;
  }
  
  public void setAccountId(String paramString)
  {
    this.jS = paramString;
  }
  
  public void setBankId(String paramString)
  {
    this.jR = paramString;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.jQ = paramString;
  }
  
  public void setRestrictedCalculationsListName(String paramString)
  {
    this.jP = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetRestrictedCalculations
 * JD-Core Version:    0.7.0.1
 */