package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.ChangePassword;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class ChangeBankEmployeePassword
  extends ChangePassword
  implements BankEmployeeTask
{
  protected String changePassword(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute("BankEmployee");
    if (localBankEmployee == null)
    {
      this.error = 5502;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      BankEmployeeAdmin.modifyBankEmployeePassword(localSecureUser, localBankEmployee, this.currentPassword, this.newPassword, new HashMap());
    }
    catch (CSILException localCSILException1)
    {
      this.error = localCSILException1.getCode();
      return this.serviceErrorURL;
    }
    HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 18, localBankEmployee.getId());
    localHistoryTracker.logChange(BankEmployee.BEAN_NAME, "PASSWORD", (String)null, (String)null, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      localBankEmployee.put("PASSWORD_STATUS", new Integer(0));
      BankEmployeeAdmin.modifyBankEmployeePasswordStatus(localSecureUser, localBankEmployee, new HashMap());
      BankEmployeeAdmin.addHistory(localSecureUser, localHistoryTracker.getHistories(), new HashMap());
    }
    catch (CSILException localCSILException2)
    {
      this.error = localCSILException2.getCode();
      return this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validatePasswords(HttpSession paramHttpSession)
  {
    boolean bool = false;
    bool = super.validatePasswords(paramHttpSession);
    if (bool == true)
    {
      BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute("BankEmployee");
      if (localBankEmployee == null)
      {
        this.error = 5502;
        bool = false;
      }
      else if (!this.currentPassword.equals(localBankEmployee.getPassword()))
      {
        this.error = 4;
        bool = false;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.ChangeBankEmployeePassword
 * JD-Core Version:    0.7.0.1
 */