package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWireTransferPayee
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public SetWireTransferPayee()
  {
    this.beanSessionName = "WireTransferPayee";
    this.collectionSessionName = "WireTransferPayees";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransferPayees localWireTransferPayees = (WireTransferPayees)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireTransferPayees == null)
    {
      this.error = 12015;
      return this.taskErrorURL;
    }
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
      return this.taskErrorURL;
    }
    WireTransferPayee localWireTransferPayee = localWireTransferPayees.getByID(this.id);
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localWireTransferPayee);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireTransferPayee
 * JD-Core Version:    0.7.0.1
 */