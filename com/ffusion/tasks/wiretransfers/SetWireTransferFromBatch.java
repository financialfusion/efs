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

public class SetWireTransferFromBatch
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected int index = -1;
  
  public SetWireTransferFromBatch()
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
    WireTransfer localWireTransfer = null;
    if (localWireBatch == null)
    {
      this.error = 12032;
    }
    else
    {
      WireTransfers localWireTransfers = localWireBatch.getWires();
      if (localWireTransfers == null)
      {
        this.error = 12004;
      }
      else
      {
        if (this.index != -1) {
          localWireTransfer = (WireTransfer)localWireTransfers.get(this.index);
        }
        if (localWireTransfer == null) {
          localWireTransfer = (WireTransfer)localWireTransfers.getFirstByFilter("ID=" + this.id);
        }
        if (localWireTransfer == null)
        {
          this.error = 12005;
        }
        else
        {
          ModifyWireTransfer localModifyWireTransfer = new ModifyWireTransfer();
          localModifyWireTransfer.set(localWireTransfer);
          localHttpSession.setAttribute(this.beanSessionName, localModifyWireTransfer);
          localHttpSession.setAttribute("OldWireTransfer", localWireTransfer);
          str = this.successURL;
        }
      }
    }
    return str;
  }
  
  public void setIndex(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.index = i;
    }
    catch (Exception localException)
    {
      this.index = -1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireTransferFromBatch
 * JD-Core Version:    0.7.0.1
 */