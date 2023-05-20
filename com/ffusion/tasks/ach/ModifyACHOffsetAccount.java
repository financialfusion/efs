package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHOffsetAccount
  extends ACHOffsetAccount
  implements Task
{
  protected String achCompanyName = "ACHCOMPANY";
  protected String offsetAccountsName = "ACHOffsetAccounts";
  protected String offsetAccountName = "ACHOffsetAccount";
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected int error = 0;
  private int zK = 1;
  private int zN = 32;
  private int zM = 1;
  private int zL = 32;
  private int zI = 1;
  private int zJ = 32;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      if (init(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyOffsetAccount(localHttpSession);
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
  
  public boolean init(HttpSession paramHttpSession)
  {
    ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramHttpSession.getAttribute(this.offsetAccountName);
    if (localACHOffsetAccount == null)
    {
      this.error = 16014;
      return false;
    }
    set(localACHOffsetAccount);
    setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.initFlag = false;
    return true;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf("OFFSETACCOUNTNUMBER") != -1)) {
        bool = validateOffsetAccountNumber();
      }
      if ((bool) && (this.validate.indexOf("OFFSETACCOUNTNAME") != -1)) {
        bool = validateOffsetAccountName();
      }
      if ((bool) && (this.validate.indexOf("OFFSETACCOUNTTYPE") != -1)) {
        bool = validateOffsetAccountType();
      }
      if ((bool) && (this.validate.indexOf("OFFSETACCOUNTBANKID") != -1)) {
        bool = validateOffsetAccountRoutingNumber();
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateOffsetAccountNumber()
  {
    if (this.number == null) {
      this.error = 16508;
    } else if ((this.number.length() > this.zN) || (Strings.removeChars(this.number, ' ').length() > 17)) {
      this.error = 16509;
    } else if (this.number.length() < this.zK) {
      this.error = 16510;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountName()
  {
    if (this.nickName.length() > this.zJ) {
      this.error = 16512;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountType()
  {
    if ((this.type < 1) || (this.type > 4)) {
      this.error = 16516;
    }
    return this.error == 0;
  }
  
  protected boolean validateOffsetAccountRoutingNumber()
  {
    if ((this.routingNumber == null) || (this.routingNumber.length() == 0)) {
      this.error = 16514;
    } else if (!RoutingNumberUtil.isValidRoutingNumber(this.routingNumber, true)) {
      this.error = 16515;
    }
    return this.error == 0;
  }
  
  protected String modifyOffsetAccount(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)paramHttpSession.getAttribute(this.offsetAccountsName);
    ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute(this.achCompanyName);
    if (localACHOffsetAccounts == null)
    {
      this.error = 16013;
      str = this.taskErrorURL;
    }
    else if (localACHCompany == null)
    {
      this.error = 16506;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        ACH.modifyOffsetAccount(localSecureUser, this, localACHCompany, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        paramHttpSession.setAttribute(this.offsetAccountName, this);
        localACHOffsetAccounts.removeByID(getID());
        localACHOffsetAccounts.add(this);
      }
    }
    return str;
  }
  
  public final void setOffsetAccountsInSessionName(String paramString)
  {
    this.offsetAccountsName = paramString;
  }
  
  public final String getOffsetAccountsInSessionName()
  {
    return this.offsetAccountsName;
  }
  
  public final void setOffsetAccountInSessionName(String paramString)
  {
    this.offsetAccountName = paramString;
  }
  
  public final String getOffsetAccountInSessionName()
  {
    return this.offsetAccountName;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (paramString.trim().length() > 0) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setACHCompanyInSessionName(String paramString)
  {
    this.achCompanyName = paramString;
  }
  
  public final String getACHCompanyInSessionName()
  {
    return this.achCompanyName;
  }
  
  public void setMinAcctNumLength(int paramInt)
  {
    this.zK = paramInt;
  }
  
  public void setMaxAcctNumLength(int paramInt)
  {
    this.zN = paramInt;
  }
  
  public void setMinRtnNumLength(int paramInt)
  {
    this.zM = paramInt;
  }
  
  public void setMaxRtnNumLength(int paramInt)
  {
    this.zL = paramInt;
  }
  
  public void setMinAcctNameLength(int paramInt)
  {
    this.zI = paramInt;
  }
  
  public void setMaxAcctNameLength(int paramInt)
  {
    this.zJ = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHOffsetAccount
 * JD-Core Version:    0.7.0.1
 */