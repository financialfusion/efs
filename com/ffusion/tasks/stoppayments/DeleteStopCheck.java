package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteStopCheck
  extends BaseTask
  implements Task
{
  protected String collectionName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)localHttpSession.getAttribute("com.ffusion.services.Stops");
    StopChecks localStopChecks = null;
    if ((this.collectionName != null) && (this.collectionName.trim().length() > 0)) {
      localStopChecks = (StopChecks)localHttpSession.getAttribute(this.collectionName);
    } else {
      localStopChecks = (StopChecks)localHttpSession.getAttribute("StopChecks");
    }
    StopCheck localStopCheck = (StopCheck)localHttpSession.getAttribute("StopCheck");
    if (localStopCheck == null)
    {
      this.error = 13021;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap(1);
        if (localStops != null) {
          localHashMap.put("SERVICE", localStops);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        localStopCheck = com.ffusion.csil.core.Stops.deleteStopPayment(localSecureUser, localStopCheck, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if ((this.error == 0) && (localStopCheck.getStatusValue() != 13010) && (localStopChecks != null)) {
        localStopChecks.remove(localStopCheck);
      }
    }
    return str;
  }
  
  public void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.DeleteStopCheck
 * JD-Core Version:    0.7.0.1
 */