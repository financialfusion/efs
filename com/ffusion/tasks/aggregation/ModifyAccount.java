package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAccount
  extends Account
  implements Task
{
  private String nH = "AggregatedAccount";
  private String nG = "com.ffusion.services.AccountAggregation";
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int maxAccountSize = 37;
  protected int maxNicknameSize = 40;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processModifyAccount(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (paramHttpSession.getAttribute(this.nH) == null)
    {
      this.error = 11009;
    }
    else
    {
      this.error = 0;
      set((Account)paramHttpSession.getAttribute(this.nH));
    }
    return this.error == 0;
  }
  
  protected String processModifyAccount(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyAccount(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String modifyAccount(HttpSession paramHttpSession)
  {
    String str = null;
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)paramHttpSession.getAttribute(this.nG);
    if (paramHttpSession.getAttribute(this.nH) == null)
    {
      this.error = 11009;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = null;
      if (localAccountAggregation != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localAccountAggregation);
      }
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        Account localAccount1 = com.ffusion.csil.core.AccountAggregation.modifyAccount(localSecureUser, this, localHashMap);
        set(localAccount1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Account localAccount2 = (Account)paramHttpSession.getAttribute(this.nH);
        localAccount2.set(this);
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (getNumber() == null)
    {
      this.error = 11015;
      bool = false;
    }
    else if ((getNumber().length() == 0) || (getNumber().length() > this.maxAccountSize))
    {
      this.error = 11015;
      bool = false;
    }
    else if ((getNickName() != null) && (getNickName().length() > this.maxNicknameSize))
    {
      this.error = 11016;
      bool = false;
    }
    return bool;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
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
  
  public void setAccountInSessionName(String paramString)
  {
    this.nH = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.nH;
  }
  
  public void setServiceName(String paramString)
  {
    this.nG = paramString;
  }
  
  public String getServiceName()
  {
    return this.nG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.ModifyAccount
 * JD-Core Version:    0.7.0.1
 */