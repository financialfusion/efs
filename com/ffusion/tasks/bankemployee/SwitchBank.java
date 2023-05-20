package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SwitchBank
  extends BaseTask
  implements BankEmployeeTask
{
  private String uu;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.uu == null) || (this.uu.length() == 0))
    {
      this.error = 5532;
      return this.taskErrorURL;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(this.uu);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i == 0)
    {
      this.error = 5532;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BankEmployee localBankEmployee = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBankEmployee.setId(String.valueOf(localSecureUser.getProfileID()));
    try
    {
      localBankEmployee = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, localBankEmployee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    int j = 0;
    ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
    for (int k = 0; k < localArrayList.size(); k++)
    {
      String str = (String)localArrayList.get(k);
      if (str.equals(this.uu))
      {
        j = 1;
        break;
      }
    }
    if (j == 0)
    {
      DebugLog.log(Level.SEVERE, "SwitchBank.process.  Can not switch to a bank the employee is not entitled to manage.");
      this.error = 5537;
      return this.taskErrorURL;
    }
    localSecureUser.updateAffiliateID(this.uu);
    return this.successURL;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.uu = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.SwitchBank
 * JD-Core Version:    0.7.0.1
 */