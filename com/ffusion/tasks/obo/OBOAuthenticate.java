package com.ffusion.tasks.obo;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OBOAuthenticate
  extends BaseTask
  implements OBOTask
{
  protected static final long INVALID_TIMESTAMP = -1L;
  protected String userName;
  protected String password;
  protected String businessCustId;
  protected String bankID;
  protected String csrUserName;
  protected String csrPassword;
  protected String OBOEnabled;
  protected long timeStamp = 0L;
  protected long timeStampThreshold = 0L;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.timeStampThreshold != 0L) {
      if (this.timeStampThreshold < 0L)
      {
        this.error = 100301;
        str = this.taskErrorURL;
      }
      else if ((this.timeStamp < 0L) || (this.timeStamp + this.timeStampThreshold < System.currentTimeMillis()))
      {
        this.error = 100300;
        str = this.taskErrorURL;
      }
    }
    if (this.error != 0) {
      return str;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser1 = new SecureUser();
    if ((this.userName != null) && (this.userName.length() != 0))
    {
      localSecureUser1.setUserName(this.userName);
      localSecureUser1.setPassword(this.password);
      localSecureUser1.setBusinessCustId(this.businessCustId);
      localSecureUser1.setBankID(this.bankID);
      localSecureUser1.put("OBO", Boolean.TRUE);
      try
      {
        HashMap localHashMap1 = null;
        localSecureUser1 = UserAdmin.authenticate(localSecureUser1, localHashMap1);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str = this.serviceErrorURL;
      }
    }
    BankEmployee localBankEmployee = new BankEmployee(localLocale);
    localBankEmployee.setUserName(this.csrUserName);
    localBankEmployee.setPassword(this.csrPassword);
    SecureUser localSecureUser2 = new SecureUser();
    localSecureUser2.setUserName(this.csrUserName);
    localSecureUser2.setPassword(this.csrPassword);
    localSecureUser2.setUserType(2);
    if (this.OBOEnabled.equals("2")) {
      localSecureUser2.setViewOnlyOBO(true);
    }
    localSecureUser2.setLocale(localLocale);
    try
    {
      HashMap localHashMap2 = new HashMap();
      localBankEmployee = BankEmployeeAdmin.signonBankEmployee(localSecureUser2, localBankEmployee, localHashMap2);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localSecureUser2.setProfileID(localBankEmployee.getId());
      localSecureUser2.setId(localBankEmployee.getId());
      localSecureUser2.setBankID(localBankEmployee.getBankId());
      localSecureUser1.setAgent(localSecureUser2);
      localHttpSession.setAttribute("SecureUser", localSecureUser1);
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setBusinessCustId(String paramString)
  {
    this.businessCustId = paramString;
  }
  
  public String getBusinessCustId()
  {
    return this.businessCustId;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setCSRUserName(String paramString)
  {
    this.csrUserName = paramString;
  }
  
  public String getCSRUserName()
  {
    return this.csrUserName;
  }
  
  public void setCSRPassword(String paramString)
  {
    this.csrPassword = paramString;
  }
  
  public String getCSRPassword()
  {
    return this.csrPassword;
  }
  
  public void setOBOEnabled(String paramString)
  {
    this.OBOEnabled = paramString;
  }
  
  public String getOBOEnabled()
  {
    return this.OBOEnabled;
  }
  
  public void setTimestamp(String paramString)
  {
    try
    {
      this.timeStamp = Long.parseLong(paramString, 16);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.timeStamp = -1L;
    }
  }
  
  public void setTimestampThreshold(String paramString)
  {
    try
    {
      this.timeStampThreshold = Long.parseLong(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.timeStampThreshold = -1L;
    }
  }
  
  public String getTimestampThreshold()
  {
    return Long.toString(this.timeStampThreshold);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.obo.OBOAuthenticate
 * JD-Core Version:    0.7.0.1
 */