package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHPayee
  extends BaseTask
  implements Task
{
  protected String payeeID;
  protected String payeeName = "ACHPayee";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute("ACHPayees");
    String str = null;
    this.error = 0;
    if (localACHPayees == null)
    {
      this.error = 16150;
      str = this.taskErrorURL;
    }
    else
    {
      ACHPayee localACHPayee = localACHPayees.getByID(this.payeeID);
      if (localACHPayee == null)
      {
        this.error = 16151;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.payeeName, localACHPayee);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.payeeID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHPayee
 * JD-Core Version:    0.7.0.1
 */