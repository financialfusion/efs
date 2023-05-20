package com.ffusion.tasks.banking;

import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRecTransfer
  extends SetTransfer
{
  public SetRecTransfer()
  {
    this.name = "RecTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute(this.name);
    if (localRecTransfers == null)
    {
      this.error = 1005;
      str = this.taskErrorURL;
    }
    else
    {
      Transfer localTransfer = localRecTransfers.getByID(this.transferID);
      if (localTransfer == null)
      {
        this.error = 1006;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetRecTransfer
 * JD-Core Version:    0.7.0.1
 */