package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReleaseWires
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean canReleaseOwnWires = true;
  protected DateTime startDate = null;
  protected DateTime endDate = null;
  protected String datetype = null;
  
  public GetReleaseWires()
  {
    this.beanSessionName = "WiresRelease";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransfers localWireTransfers = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("ReleaseOwnWires", "" + this.canReleaseOwnWires);
    if (!validateInput(localHttpSession)) {
      return this.taskErrorURL;
    }
    try
    {
      if (this.startDate != null) {
        localHashMap.put("START_DATE", this.startDate);
      }
      if (this.endDate != null) {
        localHashMap.put("END_DATE", this.endDate);
      }
      localWireTransfers = Wire.getReleaseWires(localSecureUser, localHashMap);
      localHttpSession.setAttribute(this.beanSessionName, localWireTransfers);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setCanReleaseOwnWires(String paramString)
  {
    this.canReleaseOwnWires = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanReleaseOwnWires()
  {
    return String.valueOf(this.canReleaseOwnWires);
  }
  
  public void setStartDate(String paramString)
  {
    DateTime localDateTime = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localDateTime = new DateTime(paramString, this.locale, this.datetype);
        localDateTime.set(11, 0);
        localDateTime.set(12, 0);
        localDateTime.set(13, 0);
        this.startDate = localDateTime;
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetReleaseWires.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return null;
  }
  
  public void setEndDate(String paramString)
  {
    DateTime localDateTime = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localDateTime = new DateTime(paramString, this.locale, this.datetype);
        localDateTime.set(11, 23);
        localDateTime.set(12, 59);
        localDateTime.set(13, 59);
        this.endDate = localDateTime;
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetReleaseWires.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public boolean validateInput(HttpSession paramHttpSession)
  {
    if ((this.startDate != null) && (this.endDate != null) && (this.startDate.after(this.endDate)))
    {
      this.error = 79;
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetReleaseWires
 * JD-Core Version:    0.7.0.1
 */