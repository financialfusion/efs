package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsHistory;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetItemHistory
  extends BaseTask
{
  private String aOn = null;
  private Integer aOm = null;
  private int aOl = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap1 = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale1 = getLocale(localHttpSession, localSecureUser);
    if (this.aOn == null)
    {
      this.error = 31000;
      str = this.taskErrorURL;
    }
    else if (this.aOm == null)
    {
      this.error = 31002;
      str = this.taskErrorURL;
    }
    else if (this.aOl == -1)
    {
      this.error = 31001;
      str = this.taskErrorURL;
    }
    else
    {
      Locale localLocale2 = (Locale)localHttpSession.getAttribute("Locale");
      if (localLocale2 == null) {
        localLocale2 = Locale.getDefault();
      }
      localHashMap1.put("CURRENT_LOCALE", localLocale2);
      try
      {
        HashMap localHashMap2 = new HashMap();
        localHashMap2.put("ObjectID", this.aOn);
        localHashMap2.put("ItemSubType", this.aOm);
        ApprovalsHistory localApprovalsHistory = Approvals.getItemHistory(this.aOl, localHashMap2, localHashMap1);
        if (localApprovalsHistory != null)
        {
          localApprovalsHistory.setLocale(localLocale1);
          localHttpSession.setAttribute("ApprovalsHistory", localApprovalsHistory);
        }
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
        this.error = MapError.mapError(localCSILException);
      }
    }
    return str;
  }
  
  public void setObjectID(String paramString)
  {
    this.aOn = paramString;
  }
  
  public void setItemSubType(String paramString)
  {
    this.aOm = new Integer(paramString);
  }
  
  public void setItemType(String paramString)
  {
    this.aOl = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.GetItemHistory
 * JD-Core Version:    0.7.0.1
 */