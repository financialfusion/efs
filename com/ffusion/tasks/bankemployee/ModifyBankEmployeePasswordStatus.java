package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBankEmployeePasswordStatus
  extends BaseTask
  implements BankEmployeeTask
{
  private String tR;
  
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
    Integer localInteger = null;
    try
    {
      localInteger = new Integer(this.tR);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 5536;
      return this.taskErrorURL;
    }
    localBankEmployee.put("PASSWORD_STATUS", localInteger);
    try
    {
      BankEmployeeAdmin.modifyBankEmployeePasswordStatus(localSecureUser, localBankEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getCode();
      return this.serviceErrorURL;
    }
    return str;
  }
  
  public void setStatus(String paramString)
  {
    this.tR = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.ModifyBankEmployeePasswordStatus
 * JD-Core Version:    0.7.0.1
 */