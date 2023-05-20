package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.user.UserTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrefillApplication
  extends BaseTask
  implements Task, UserTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Application localApplication1 = (Application)localHttpSession.getAttribute("Application");
    if (localApplication1 == null)
    {
      this.error = 7201;
      return this.taskErrorURL;
    }
    User localUser = (User)localHttpSession.getAttribute("User");
    if (localUser != null)
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      com.ffusion.beans.applications.Applications localApplications = new com.ffusion.beans.applications.Applications(localLocale);
      Application localApplication2 = new Application(localLocale);
      localApplication2.setBankID(localApplication1.getBankID());
      localApplication2.setCustomerID(localUser.getId());
      localHashMap.put("APPLICATIONS", localApplications);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localApplications = com.ffusion.csil.core.Applications.getApplications(localSecureUser, localApplication2, "", "", "", "", localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
      if ((this.error == 0) && (localApplications.size() > 0))
      {
        String str2 = localApplication1.getCategoryID();
        Object localObject = null;
        Iterator localIterator = localApplications.iterator();
        while (localIterator.hasNext())
        {
          Application localApplication3 = (Application)localIterator.next();
          String str3 = localApplication3.getCategoryID();
          if (str3.equalsIgnoreCase(str2))
          {
            localObject = localApplication3;
            break;
          }
        }
        if (localObject == null) {
          localObject = (Application)localApplications.get(0);
        }
        localHashMap.remove("APPLICATIONS");
        try
        {
          localObject = com.ffusion.csil.core.Applications.getApplication(localSecureUser, (Application)localObject, true, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          str1 = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          localApplication1.setFieldValues(((Application)localObject).getFieldValues());
          localApplication1.setFirstName(((Application)localObject).getFirstName());
          localApplication1.setLastName(((Application)localObject).getLastName());
          localApplication1.setSsn(((Application)localObject).getSsn());
          localApplication1.setEmailAddress(((Application)localObject).getEmailAddress());
          localHttpSession.setAttribute("Application", localApplication1);
        }
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.PrefillApplication
 * JD-Core Version:    0.7.0.1
 */