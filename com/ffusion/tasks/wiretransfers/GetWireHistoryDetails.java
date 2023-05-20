package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireTransfer;
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

public class GetWireHistoryDetails
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  private String Dw = "WireTransfer";
  private String Dt = "WireBatch";
  private boolean Dv = false;
  private static final String Du = "false";
  
  public GetWireHistoryDetails()
  {
    this.beanSessionName = "WireHistory";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireHistory localWireHistory = (WireHistory)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireHistory == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer = new WireTransfer();
    WireBatch localWireBatch = new WireBatch();
    int i = 1;
    if (localWireHistory.getTransType().equals("BATCH")) {
      i = 0;
    }
    try
    {
      if (i == 1)
      {
        if (localWireHistory.getTransType().equals("RECURRING"))
        {
          if (this.Dv == true)
          {
            localHashMap.put("EXCLUDE_CANCELLED", "false");
            localWireTransfer = Wire.getRecWireTransferById(localSecureUser, localWireHistory.getRecurringID(), localHashMap);
            if (!localWireHistory.getID().equals(localWireHistory.getRecurringID()))
            {
              int j = localWireTransfer.getFrequencyValue();
              int k = localWireTransfer.getNumberTransfersValue();
              localWireTransfer = Wire.getWireTransferById(localSecureUser, localWireHistory.getID(), localHashMap);
              localWireTransfer.setFrequency(j);
              localWireTransfer.setNumberTransfers(k);
            }
            else
            {
              localWireTransfer.setDueDate((DateTime)localWireHistory.get("DUE_DATE"));
              localWireTransfer.setDateToPost(localWireHistory.getDateValue());
            }
          }
          else
          {
            localWireTransfer = Wire.getRecWireTransferById(localSecureUser, localWireHistory.getRecurringID(), localHashMap);
          }
        }
        else {
          localWireTransfer = Wire.getWireTransferById(localSecureUser, localWireHistory.getID(), localHashMap);
        }
      }
      else {
        localWireBatch = Wire.getWireBatchById(localSecureUser, localWireHistory.getID(), localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    if (i == 1) {
      localHttpSession.setAttribute(this.Dw, localWireTransfer);
    } else {
      localHttpSession.setAttribute(this.Dt, localWireBatch);
    }
    return str;
  }
  
  public void setWireSessionName(String paramString)
  {
    this.Dw = paramString;
  }
  
  public void setBatchSessionName(String paramString)
  {
    this.Dt = paramString;
  }
  
  public void setViewHistory(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return;
    }
    this.Dv = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireHistoryDetails
 * JD-Core Version:    0.7.0.1
 */