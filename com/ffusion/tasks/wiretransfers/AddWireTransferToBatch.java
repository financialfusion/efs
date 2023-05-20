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

public class AddWireTransferToBatch
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean bSetAction = true;
  protected boolean bCreate = false;
  
  public AddWireTransferToBatch()
  {
    this.beanSessionName = "WireTransfer";
    this.collectionSessionName = "WireBatch";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireBatch localWireBatch = (WireBatch)localHttpSession.getAttribute(this.collectionSessionName);
    WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireBatch == null)
    {
      this.error = 12032;
    }
    else if ((localWireTransfer == null) && (!this.bCreate))
    {
      this.error = 12005;
    }
    else
    {
      WireTransfers localWireTransfers = localWireBatch.getWires();
      if (localWireTransfers == null)
      {
        localWireTransfers = new WireTransfers();
        localWireBatch.setWires(localWireTransfers);
      }
      if (this.bCreate)
      {
        localWireTransfer = (WireTransfer)localWireTransfers.createNoAdd();
        localHttpSession.setAttribute(this.beanSessionName, localWireTransfer);
      }
      if (this.bSetAction) {
        localWireTransfer.setAction("add");
      }
      localWireTransfers.add(localWireTransfer);
      str = this.successURL;
    }
    return str;
  }
  
  public void setAction(String paramString)
  {
    this.bSetAction = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCreate(String paramString)
  {
    this.bCreate = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddWireTransferToBatch
 * JD-Core Version:    0.7.0.1
 */