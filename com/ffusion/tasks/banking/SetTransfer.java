package com.ffusion.tasks.banking;

import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetTransfer
  extends BaseTask
  implements Task
{
  protected String transferID;
  protected String name = "Transfers";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Transfers localTransfers = (Transfers)localHttpSession.getAttribute(this.name);
    if (localTransfers == null)
    {
      this.error = 1003;
      str = this.taskErrorURL;
    }
    else
    {
      Transfer localTransfer = localTransfers.getByID(this.transferID);
      if (localTransfer == null)
      {
        this.error = 1004;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("Transfer", localTransfer);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.transferID = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getID()
  {
    return this.transferID;
  }
  
  public String getName()
  {
    return this.name;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetTransfer
 * JD-Core Version:    0.7.0.1
 */