package com.ffusion.tasks.billpay;

import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPayee
  extends ExtendedBaseTask
  implements Task
{
  public SetPayee()
  {
    this.beanSessionName = "Payee";
    this.collectionSessionName = "Payees";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    Payees localPayees = (Payees)localHttpSession.getAttribute(this.collectionSessionName);
    String str = null;
    if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
    }
    else
    {
      Payee localPayee = localPayees.getByID(this.id);
      if (localPayee == null)
      {
        this.error = 2008;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.beanSessionName, localPayee);
        str = this.successURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SetPayee
 * JD-Core Version:    0.7.0.1
 */