package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployeeDefines;
import com.ffusion.beans.history.Histories;
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

public class AddBankEmployeeHistory
  extends BaseTask
  implements BankEmployeeTask, BankEmployeeDefines
{
  private String oD = "";
  private String oF = "";
  private static final String oE = "QUEUE_ASSIGNMENTS_UPDATED";
  private static final String oC = "PRODUCT_STATUS_ASSIGNMENTS_UPDATED";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.taskErrorURL;
    BankEmployee localBankEmployee1 = (BankEmployee)localHttpSession.getAttribute("BankEmployee");
    BankEmployee localBankEmployee2 = (BankEmployee)localHttpSession.getAttribute("ModifiedBankEmployee");
    if (localBankEmployee1 == null)
    {
      this.error = 5502;
    }
    else if (localBankEmployee2 == null)
    {
      this.error = 5505;
    }
    else
    {
      Object localObject1;
      Object localObject2;
      Object localObject3;
      if (this.oD.equals("Modify"))
      {
        localObject1 = (Locale)localHttpSession.getAttribute("java.util.Locale");
        localObject2 = new Histories((Locale)localObject1);
        localObject3 = (String)localHttpSession.getAttribute("QueuesUpdated");
        if ((localObject3 != null) && (((String)localObject3).equals("true"))) {
          ((Histories)localObject2).add(AddBankEmployee.buildHistory(localHttpSession, localBankEmployee2, "QUEUE_ASSIGNMENTS_UPDATED", "", "", ""));
        }
        String str2 = (String)localHttpSession.getAttribute("ProductStatusUpdated");
        if ((str2 != null) && (str2.equals("true"))) {
          ((Histories)localObject2).add(AddBankEmployee.buildHistory(localHttpSession, localBankEmployee2, "PRODUCT_STATUS_ASSIGNMENTS_UPDATED", "", "", ""));
        }
        if (((Histories)localObject2).size() > 0) {
          try
          {
            HashMap localHashMap = null;
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            BankEmployeeAdmin.addHistory(localSecureUser, (Histories)localObject2, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
          }
        }
        str1 = this.successURL;
      }
      else if (this.oD.equals("Add"))
      {
        str1 = this.successURL;
      }
      else
      {
        localObject1 = new Histories((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localObject2 = AddBankEmployee.buildHistory(localHttpSession, localBankEmployee2, "Comment", "", "", this.oF);
        ((Histories)localObject1).add(localObject2);
        jdMethod_for(localHttpSession, (Histories)localObject1);
        if (this.error == 0)
        {
          str1 = this.successURL;
          localObject3 = (Histories)localHttpSession.getAttribute("EmployeeHistories");
          if (localObject3 != null) {
            ((Histories)localObject3).add(localObject2);
          }
        }
        else
        {
          str1 = this.serviceErrorURL;
        }
      }
    }
    return str1;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, Histories paramHistories)
  {
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      BankEmployeeAdmin.addHistory(localSecureUser, paramHistories, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
  }
  
  public void setAction(String paramString)
  {
    this.oD = paramString;
  }
  
  public void setComments(String paramString)
  {
    this.oF = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.AddBankEmployeeHistory
 * JD-Core Version:    0.7.0.1
 */