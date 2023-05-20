package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWireBatch
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public SetWireBatch()
  {
    this.beanSessionName = "WireBatch";
    this.collectionSessionName = "WiresBatches";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireBatches localWireBatches = (WireBatches)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireBatches == null)
    {
      this.error = 12031;
    }
    else if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 81;
    }
    else
    {
      WireBatch localWireBatch = (WireBatch)localWireBatches.getByID(this.id);
      if (localWireBatch == null)
      {
        this.error = 81;
      }
      else
      {
        localHttpSession.setAttribute(this.beanSessionName, localWireBatch);
        str = this.successURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireBatch
 * JD-Core Version:    0.7.0.1
 */