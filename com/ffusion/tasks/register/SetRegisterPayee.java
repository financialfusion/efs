package com.ffusion.tasks.register;

import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRegisterPayee
  extends ExtendedBaseTask
  implements Task
{
  public SetRegisterPayee()
  {
    this.beanSessionName = "RegisterPayee";
    this.collectionSessionName = "RegisterPayees";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.id.equals(""))
    {
      RegisterPayee localRegisterPayee1 = new RegisterPayee();
      localHttpSession.setAttribute(this.beanSessionName, localRegisterPayee1);
      return this.successURL;
    }
    try
    {
      Integer.parseInt(this.id);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 20111;
      return this.taskErrorURL;
    }
    RegisterPayees localRegisterPayees = (RegisterPayees)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterPayees != null)
    {
      RegisterPayee localRegisterPayee2 = localRegisterPayees.getById(this.id);
      if (localRegisterPayee2 != null)
      {
        RegisterPayee localRegisterPayee3 = new RegisterPayee();
        localRegisterPayee3.set(localRegisterPayee2);
        localHttpSession.setAttribute(this.beanSessionName, localRegisterPayee3);
      }
      else
      {
        this.error = 20204;
        return this.taskErrorURL;
      }
    }
    else
    {
      this.error = 20008;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetRegisterPayee
 * JD-Core Version:    0.7.0.1
 */