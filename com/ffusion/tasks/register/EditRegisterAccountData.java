package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditRegisterAccountData
  extends ExtendedBaseTask
  implements Task
{
  public EditRegisterAccountData()
  {
    this.beanSessionName = "Account";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    Account localAccount = (Account)localHttpSession.getAttribute(this.beanSessionName);
    if (localAccount == null)
    {
      this.error = 20001;
      return this.taskErrorURL;
    }
    if ((validateInput(localAccount)) && (!jdMethod_for(localAccount, localSecureUser))) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  private boolean jdMethod_for(Account paramAccount, SecureUser paramSecureUser)
  {
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("ModifyRegisterEnabledStatus", "true");
      Register.modifyRegisterAccountData(paramSecureUser, paramAccount, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return this.error == 0;
  }
  
  protected boolean validateInput(Account paramAccount)
  {
    if (this.validate != null)
    {
      if ((this.validate.indexOf("REG_DEFAULT") != -1) && ((paramAccount.get("REG_DEFAULT") == null) || ((!((String)paramAccount.get("REG_DEFAULT")).equalsIgnoreCase("true")) && (!((String)paramAccount.get("REG_DEFAULT")).equalsIgnoreCase("false")))))
      {
        this.error = 20117;
        return false;
      }
      if ((this.validate.indexOf("REG_ENABLED") != -1) && ((paramAccount.get("REG_ENABLED") == null) || ((!((String)paramAccount.get("REG_ENABLED")).equalsIgnoreCase("true")) && (!((String)paramAccount.get("REG_ENABLED")).equalsIgnoreCase("false")))))
      {
        this.error = 20118;
        return false;
      }
      if ((this.validate.indexOf("REG_RETRIEVAL_DATE") != -1) && (paramAccount.get("REG_ENABLED") == null))
      {
        this.error = 20119;
        return false;
      }
      this.validate = null;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterAccountData
 * JD-Core Version:    0.7.0.1
 */