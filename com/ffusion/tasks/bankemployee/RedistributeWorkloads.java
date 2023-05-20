package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedistributeWorkloads
  extends BaseTask
  implements BankEmployeeTask
{
  protected String id = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployee localBankEmployee1 = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBankEmployee1.setId(this.id);
    BankEmployee localBankEmployee2 = (BankEmployee)localHttpSession.getAttribute("BankEmployee");
    Object localObject;
    try
    {
      HashMap localHashMap = null;
      localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
      BankEmployeeAdmin.redistributeWorkloads((SecureUser)localObject, localBankEmployee2, localBankEmployee1, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
      if (localBankEmployees != null)
      {
        localBankEmployees.setFilter("All");
        localObject = localBankEmployees.getByID(this.id);
        localHttpSession.setAttribute("ModifiedBankEmployee", localObject);
      }
    }
    return str;
  }
  
  public void setCSR(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.RedistributeWorkloads
 * JD-Core Version:    0.7.0.1
 */