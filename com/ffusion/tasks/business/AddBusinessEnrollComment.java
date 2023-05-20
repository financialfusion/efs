package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.BusinessDefines;
import com.ffusion.beans.history.History;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBusinessEnrollComment
  extends History
  implements BusinessTask, BusinessDefines
{
  private String fD = "SE";
  private String fG;
  private String fH = "TE";
  private String fF;
  private int fE = 0;
  private String fC = this.fG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = (Business)localHttpSession.getAttribute("Business");
    if (localSecureUser == null)
    {
      this.fE = 3513;
      this.fC = this.fH;
    }
    else if (localBusiness == null)
    {
      this.fE = 4104;
      this.fC = this.fH;
    }
    else
    {
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, localBusiness.getId());
      localHistoryTracker.logChange("EnrollmentComments", (String)null, (String)null, this.comment);
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for AddBusinessEnrollComment: " + localProfileException.toString());
      }
    }
    return this.fC;
  }
  
  public String getError()
  {
    return String.valueOf(this.fE);
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.fH = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.AddBusinessEnrollComment
 * JD-Core Version:    0.7.0.1
 */