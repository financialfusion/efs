package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWorkflowLevels
  extends BaseTask
{
  String aOd = null;
  String aOe = null;
  String aOg = null;
  int aOf = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localHttpSession.removeAttribute("ApprovalsLevels");
      ApprovalsLevels localApprovalsLevels = null;
      if ((this.aOe != null) && (this.aOg != null))
      {
        int i = 0;
        if (localSecureUser.getUserType() == 1) {
          i = localSecureUser.getBusinessID();
        } else if (localSecureUser.getUserType() == 2) {
          i = Integer.parseInt(this.aOg);
        }
        localApprovalsLevels = Approvals.getWorkflowLevels(localSecureUser, i, this.aOf, this.aOe, this.aOd, localHashMap);
      }
      else
      {
        localApprovalsLevels = Approvals.getWorkflowLevels(localSecureUser, this.aOd, localHashMap);
      }
      if (localApprovalsLevels != null) {
        localHttpSession.setAttribute("ApprovalsLevels", localApprovalsLevels);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setOperationType(String paramString)
  {
    this.aOd = paramString;
  }
  
  public String getOperationType()
  {
    return this.aOd;
  }
  
  public void setLevelType(String paramString)
  {
    this.aOe = paramString;
  }
  
  public String getLevelType()
  {
    return this.aOe;
  }
  
  public void setBusinessID(String paramString)
  {
    this.aOg = paramString;
  }
  
  public String getBusinessID()
  {
    return this.aOg;
  }
  
  public void setObjectType(String paramString)
  {
    this.aOf = Integer.parseInt(paramString);
  }
  
  public String getObjectType()
  {
    return String.valueOf(this.aOf);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetWorkflowLevels
 * JD-Core Version:    0.7.0.1
 */