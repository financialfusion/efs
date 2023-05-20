package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyApplications
  extends BaseTask
  implements Task
{
  protected StringList appIDs = new StringList();
  protected String historyComment = "";
  protected String statusID;
  protected String owner;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.applications.Applications localApplications = (com.ffusion.beans.applications.Applications)localHttpSession.getAttribute("Applications");
    if (localApplications == null)
    {
      this.error = 7200;
      return this.taskErrorURL;
    }
    if (this.appIDs.size() == 0)
    {
      this.error = 7211;
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
    if ((this.owner != null) && (!this.owner.equals("")) && (!this.owner.equals("-1")))
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
    if (!this.statusID.equals("-1"))
    {
      localObject1 = (Statuses)localHttpSession.getAttribute("Statuses");
      if (localObject1 == null)
      {
        this.error = 7270;
        return this.taskErrorURL;
      }
      localObject2 = ((Statuses)localObject1).getByID(this.statusID);
      str2 = str2 + "_STATUS";
      localVector.add(((Status)localObject2).getName());
      i = 1;
    }
    if (i == 0) {
      return this.successURL;
    }
    localHashMap.put("APPLICATIONS", localApplications);
    Object localObject1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localObject2 = new LocalizableString("com.ffusion.beans.applications.resources", str2, localVector.toArray());
      localApplications = com.ffusion.csil.core.Applications.modifyApplications((SecureUser)localObject1, this.appIDs, this.owner, this.statusID, (String)((LocalizableString)localObject2).localize(((SecureUser)localObject1).getLocale()), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Iterator localIterator = this.appIDs.iterator();
      while (localIterator.hasNext())
      {
        String str3 = (String)localIterator.next();
        Application localApplication = localApplications.getByID(str3);
        if (!this.statusID.equals("-1")) {
          localApplication.setStatusID(this.statusID);
        }
        if (!this.owner.equals("-1")) {
          localApplication.setOwner(this.owner);
        }
      }
      localHttpSession.setAttribute("Applications", localApplications);
    }
    return str1;
  }
  
  public void setOwner(String paramString)
  {
    this.owner = paramString;
  }
  
  public void setStatusID(String paramString)
  {
    this.statusID = paramString;
  }
  
  public void setAppIDs(String paramString)
  {
    if (paramString.indexOf(",") != -1)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        this.appIDs.add(Strings.replaceStr(str, " ", ""));
      }
    }
    else
    {
      this.appIDs.add(paramString);
    }
  }
  
  public void setHistoryComment(String paramString)
  {
    this.historyComment = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ModifyApplications
 * JD-Core Version:    0.7.0.1
 */