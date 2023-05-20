package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUsersForThreads
  extends BaseTask
  implements Task
{
  private String fv = "MessageThreads";
  private String fw = "Users";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MessageThreads localMessageThreads = (MessageThreads)localHttpSession.getAttribute(this.fv);
    Users localUsers = null;
    if (localSecureUser == null)
    {
      this.error = 1037;
      str1 = this.taskErrorURL;
    }
    else if (localMessageThreads == null)
    {
      this.error = 8025;
      str1 = this.taskErrorURL;
    }
    else
    {
      HashSet localHashSet = new HashSet();
      ArrayList localArrayList = new ArrayList();
      String str2 = null;
      Iterator localIterator = localMessageThreads.iterator();
      while (localIterator.hasNext())
      {
        str2 = ((MessageThread)localIterator.next()).getDirectoryID();
        localHashSet.add(str2);
      }
      localIterator = localHashSet.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(localIterator.next());
      }
      try
      {
        localUsers = UserAdmin.getUsersByIds(localSecureUser, localArrayList, localHashMap);
        localHttpSession.setAttribute(this.fw, localUsers);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setMessageThreadsKeyName(String paramString)
  {
    this.fv = paramString;
  }
  
  public void setUsersKeyName(String paramString)
  {
    this.fw = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetUsersForThreads
 * JD-Core Version:    0.7.0.1
 */