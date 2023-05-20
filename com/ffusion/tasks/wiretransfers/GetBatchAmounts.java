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

public class GetBatchAmounts
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected WireBatch clonedBatch = new WireBatch();
  protected boolean bAdd = true;
  
  public GetBatchAmounts()
  {
    this.beanSessionName = "WireBatch";
    this.collectionSessionName = "WireTransfers";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireBatch localWireBatch = (WireBatch)localHttpSession.getAttribute(this.beanSessionName);
    HashMap localHashMap = new HashMap();
    if (localWireBatch == null)
    {
      this.error = 12032;
      return this.taskErrorURL;
    }
    if (localWireBatch.getWires() == null)
    {
      WireTransfers localWireTransfers = (WireTransfers)localHttpSession.getAttribute(this.collectionSessionName);
      if (localWireTransfers == null)
      {
        this.error = 12004;
        return this.taskErrorURL;
      }
      localWireBatch.setWires(localWireTransfers);
    }
    try
    {
      this.clonedBatch.set(localWireBatch);
      Wire.getBatchAmounts(localSecureUser, this.clonedBatch, this.bAdd, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
  
  public void setAdd(String paramString)
  {
    this.bAdd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getTotalDebit()
  {
    return this.clonedBatch.getTotalDebit();
  }
  
  public String getTotalOrigAmount()
  {
    return this.clonedBatch.getOrigAmount();
  }
  
  public String getTotalPaymentAmount()
  {
    return this.clonedBatch.getPaymentAmount();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetBatchAmounts
 * JD-Core Version:    0.7.0.1
 */