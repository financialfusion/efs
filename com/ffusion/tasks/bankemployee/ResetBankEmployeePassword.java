package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.PasswordUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetBankEmployeePassword
  extends BaseTask
  implements BankEmployeeTask
{
  private String ur = null;
  private String us = null;
  private int ut = 2;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("ModifiedBankEmployee");
    if (localBankEmployee == null)
    {
      this.error = 5505;
      return this.taskErrorURL;
    }
    if (this.ur == null)
    {
      this.error = 3;
      return this.taskErrorURL;
    }
    if (this.us == null)
    {
      this.error = 3;
      return this.taskErrorURL;
    }
    this.error = verifyPassword(localHttpSession);
    if (this.error != 0) {
      return this.taskErrorURL;
    }
    try
    {
      BankEmployeeAdmin.resetBankEmployeePassword(localSecureUser, localBankEmployee, this.ur, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getCode();
      return this.serviceErrorURL;
    }
    return str;
  }
  
  protected int verifyPassword(HttpSession paramHttpSession)
  {
    if (!this.ur.equals(this.us)) {
      return 5;
    }
    return PasswordUtil.validatePassword(this.ur, this.ut);
  }
  
  public void setNewPassword(String paramString)
  {
    this.ur = paramString;
  }
  
  public void setConfirmPassword(String paramString)
  {
    this.us = paramString;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.ut = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.ut = 1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.ResetBankEmployeePassword
 * JD-Core Version:    0.7.0.1
 */