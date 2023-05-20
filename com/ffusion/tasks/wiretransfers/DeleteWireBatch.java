package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteWireBatch
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public DeleteWireBatch()
  {
    this.beanSessionName = "WireBatch";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    WireBatch localWireBatch = (WireBatch)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireBatch == null)
    {
      this.error = 12032;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    WireTransfers localWireTransfers = localWireBatch.getWires();
    if (localWireTransfers == null)
    {
      localWireTransfers = (WireTransfers)localHttpSession.getAttribute("WireTransfersByBatchId");
      if (localWireTransfers != null) {
        localWireBatch.setWires(localWireTransfers);
      }
    }
    try
    {
      Wire.deleteWireBatch(localSecureUser, localWireBatch, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.DeleteWireBatch
 * JD-Core Version:    0.7.0.1
 */