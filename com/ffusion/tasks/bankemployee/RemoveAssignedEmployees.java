package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RemoveAssignedEmployees
  extends BaseTask
  implements BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees1 = (BankEmployees)localHttpSession.getAttribute("AssignedEmployees");
    if (localBankEmployees1 == null)
    {
      this.error = 5522;
      str1 = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator = localBankEmployees1.iterator();
      while (localIterator.hasNext())
      {
        BankEmployee localBankEmployee1 = (BankEmployee)localIterator.next();
        String str2 = localBankEmployee1.getSupervisor();
        localBankEmployee1.setSupervisor("0");
        try
        {
          HashMap localHashMap = null;
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          localBankEmployee1 = BankEmployeeAdmin.modifyBankEmployee(localSecureUser, localBankEmployee1, localHashMap);
          if ((localBankEmployee1.getAdminAccess().contains("ACCESS_CSR")) && (!checkNull(localBankEmployee1.getSupervisor()).equals(checkNull(str2))))
          {
            BankEmployees localBankEmployees2 = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
            BankEmployee localBankEmployee2 = localBankEmployees2.getByID(str2);
            BankEmployee localBankEmployee3 = localBankEmployees2.getByID(localBankEmployee1.getSupervisor());
            String str3 = "";
            String str4 = "";
            if (localBankEmployee2 != null) {
              str3 = localBankEmployee2.getFirstName() + " " + localBankEmployee2.getLastName();
            }
            if (localBankEmployee3 != null) {
              str4 = localBankEmployee3.getFirstName() + " " + localBankEmployee3.getLastName();
            }
            Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
            Histories localHistories = new Histories(localLocale);
            localHistories.add(AddBankEmployee.buildHistory(localHttpSession, localBankEmployee1, "SUPERVISOR", str3, str4, ""));
            try
            {
              BankEmployeeAdmin.addHistory(localSecureUser, localHistories, localHashMap);
            }
            catch (CSILException localCSILException2)
            {
              this.error = MapError.mapError(localCSILException2);
            }
          }
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
          str1 = this.serviceErrorURL;
        }
        if (this.error == 0) {
          localIterator.remove();
        }
      }
    }
    return str1;
  }
  
  public String checkNull(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    return paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.RemoveAssignedEmployees
 * JD-Core Version:    0.7.0.1
 */