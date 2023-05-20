package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWireTransfer
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  private String Dl;
  
  public SetWireTransfer()
  {
    this.beanSessionName = "WireTransfer";
    this.collectionSessionName = "WireTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfers localWireTransfers = null;
    Object localObject;
    if (this.collectionSessionName.indexOf(".") == -1)
    {
      localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.collectionSessionName);
    }
    else
    {
      localObject = this.collectionSessionName.substring(0, this.collectionSessionName.indexOf("."));
      WireBatch localWireBatch = (WireBatch)localHttpSession.getAttribute((String)localObject);
      localWireTransfers = localWireBatch.getWires();
    }
    if (localWireTransfers == null)
    {
      this.error = 12004;
    }
    else if (((this.id == null) || (this.id.length() == 0)) && ((this.Dl == null) || (this.Dl.length() == 0)))
    {
      this.error = 81;
    }
    else
    {
      localObject = null;
      if ((this.id != null) && (this.id.length() > 0)) {
        localObject = (WireTransfer)localWireTransfers.getFirstByFilter("ID=" + this.id);
      } else {
        localObject = (WireTransfer)localWireTransfers.getFirstByFilter("TrackingID=" + this.Dl);
      }
      if (localObject == null)
      {
        this.error = 81;
      }
      else
      {
        localHttpSession.setAttribute(this.beanSessionName, localObject);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setTrackingID(String paramString)
  {
    this.Dl = paramString;
  }
  
  public String getTrackingID()
  {
    return this.Dl;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireTransfer
 * JD-Core Version:    0.7.0.1
 */