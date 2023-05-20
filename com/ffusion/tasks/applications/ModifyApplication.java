package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyApplication
  extends BaseTask
  implements Task
{
  protected String historyComment = null;
  protected String statusID = "";
  protected String owner = "";
  protected String previousStatusID = "";
  protected String previousOwner = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)AuditAdapter.getLogLanguages().get(0);
    com.ffusion.beans.applications.Applications localApplications = (com.ffusion.beans.applications.Applications)localHttpSession.getAttribute("Applications");
    if (localApplications == null)
    {
      this.error = 7200;
      return this.taskErrorURL;
    }
    Application localApplication1 = (Application)localHttpSession.getAttribute("Application");
    if (localApplication1 == null)
    {
      this.error = 7201;
      return this.taskErrorURL;
    }
    int i = 0;
    String str2 = "HISTORY_COMMENT";
    Vector localVector = new Vector();
    if ((this.historyComment != null) && (!this.historyComment.equals("")))
    {
      str2 = str2 + "_USER";
      localVector.add(this.historyComment);
      i = 1;
    }
    Object localObject2;
    if ((this.owner != null) && (!this.owner.equals("")) && (!this.previousOwner.equals(this.owner)))
    {
      localObject1 = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
      if (localObject1 == null)
      {
        this.error = 5503;
        return this.taskErrorURL;
      }
      ((BankEmployees)localObject1).setFilter("All");
      localObject2 = ((BankEmployees)localObject1).getByID(this.owner);
      str2 = str2 + "_OWNER";
      localVector.add(((BankEmployee)localObject2).getName());
      i = 1;
    }
    if ((this.statusID != null) && (!this.previousStatusID.equals(this.statusID)))
    {
      localObject1 = (Statuses)localHttpSession.getAttribute("Statuses");
      if (localObject1 == null)
      {
        this.error = 7270;
        return this.taskErrorURL;
      }
      localObject2 = ((Statuses)localObject1).getByID(this.statusID);
      str2 = str2 + "_STATUS";
      localVector.add(((Status)localObject2).getName(localLocale.toString()));
      i = 1;
    }
    if (i == 0) {
      return this.successURL;
    }
    localApplication1.setStatusID(this.statusID);
    localApplication1.setOwner(this.owner);
    Object localObject1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localObject2 = new LocalizableString("com.ffusion.beans.applications.resources", str2, localVector.toArray());
      localApplication1 = com.ffusion.csil.core.Applications.modifyApplication((SecureUser)localObject1, localApplication1, (String)((LocalizableString)localObject2).localize(localLocale), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Application localApplication2 = localApplications.getByID(localApplication1.getAppID());
      localApplication2.setStatusID(this.statusID);
      localApplication2.setOwner(localApplication1.getOwner());
      localHttpSession.setAttribute("Applications", localApplications);
    }
    return str1;
  }
  
  public void setHistoryComment(String paramString)
  {
    this.historyComment = paramString;
  }
  
  public void setOwner(String paramString)
  {
    this.owner = paramString;
  }
  
  public void setStatusID(String paramString)
  {
    this.statusID = paramString;
  }
  
  public void setPreviousOwner(String paramString)
  {
    this.previousOwner = paramString;
  }
  
  public void setPreviousStatusID(String paramString)
  {
    this.previousStatusID = paramString;
  }
  
  public String getHistoryComment()
  {
    return this.historyComment;
  }
  
  public String getOwner()
  {
    return this.owner;
  }
  
  public String getStatusID()
  {
    return this.statusID;
  }
  
  public String getPreviousOwner()
  {
    return this.previousOwner;
  }
  
  public String getPreviousStatusID()
  {
    return this.previousStatusID;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ModifyApplication
 * JD-Core Version:    0.7.0.1
 */