package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWireTransferBank
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public SetWireTransferBank()
  {
    this.beanSessionName = "WireTransferBank";
    this.collectionSessionName = "WireTransferBanks";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransferBanks localWireTransferBanks = (WireTransferBanks)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireTransferBanks == null)
    {
      this.error = 12018;
      str = this.taskErrorURL;
    }
    else if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 12019;
      str = this.taskErrorURL;
    }
    else
    {
      WireTransferBank localWireTransferBank = localWireTransferBanks.getByID(this.id);
      if (localWireTransferBank != null)
      {
        localHttpSession.setAttribute(this.beanSessionName, localWireTransferBank);
      }
      else
      {
        this.error = 12019;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireTransferBank
 * JD-Core Version:    0.7.0.1
 */