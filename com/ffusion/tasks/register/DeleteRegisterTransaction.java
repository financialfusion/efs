package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteRegisterTransaction
  extends ExtendedBaseTask
  implements Task
{
  public DeleteRegisterTransaction()
  {
    this.beanSessionName = "Transfer";
    this.collectionSessionName = "RegisterTransactions";
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
    RegisterTransaction localRegisterTransaction = null;
    if (this.id == null)
    {
      localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute("RegisterTransaction");
      if (localRegisterTransaction == null)
      {
        this.error = 20003;
        return this.taskErrorURL;
      }
    }
    else
    {
      try
      {
        Integer.parseInt(this.id);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 20106;
        return this.taskErrorURL;
      }
      localRegisterTransaction = (RegisterTransaction)localHttpSession.getAttribute("RegisterTransaction");
      if (localRegisterTransaction == null) {
        localRegisterTransaction = new RegisterTransaction();
      }
      localRegisterTransaction.setRegisterId(this.id);
    }
    try
    {
      Register.deleteRegisterTransaction(localSecureUser, localRegisterTransaction, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.collectionSessionName);
      if (localRegisterTransactions != null)
      {
        localRegisterTransactions.removeByRegisterId(this.id);
        localHttpSession.setAttribute(this.collectionSessionName, localRegisterTransactions);
      }
    }
    else
    {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.DeleteRegisterTransaction
 * JD-Core Version:    0.7.0.1
 */