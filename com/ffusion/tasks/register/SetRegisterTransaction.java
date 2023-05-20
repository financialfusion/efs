package com.ffusion.tasks.register;

import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRegisterTransaction
  extends ExtendedBaseTask
  implements Task
{
  public SetRegisterTransaction()
  {
    this.beanSessionName = "RegisterTransaction";
    this.collectionSessionName = "RegisterTransactions";
    this.id = "";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.id.equals(""))
    {
      RegisterTransaction localRegisterTransaction1 = new RegisterTransaction();
      localHttpSession.setAttribute(this.beanSessionName, localRegisterTransaction1);
      return this.successURL;
    }
    try
    {
      Integer.parseInt(this.id);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 20106;
      return this.taskErrorURL;
    }
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterTransactions != null)
    {
      RegisterTransaction localRegisterTransaction2 = localRegisterTransactions.getByRegisterId(this.id);
      if (localRegisterTransaction2 != null)
      {
        RegisterTransaction localRegisterTransaction3 = new RegisterTransaction();
        localRegisterTransaction3.set(localRegisterTransaction2);
        localHttpSession.setAttribute(this.beanSessionName, localRegisterTransaction3);
      }
      else
      {
        this.error = 20201;
        return this.taskErrorURL;
      }
    }
    else
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetRegisterTransaction
 * JD-Core Version:    0.7.0.1
 */