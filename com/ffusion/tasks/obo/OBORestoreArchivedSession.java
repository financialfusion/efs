package com.ffusion.tasks.obo;

import com.ffusion.beans.SecureUser;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OBORestoreArchivedSession
  extends BaseTask
  implements OBOArchiveTask
{
  private String aQj = "FFISessionArchive";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute(getArchiveName());
    try
    {
      if ((localHashMap != null) || (localHashMap.size() == 0))
      {
        Enumeration localEnumeration = localHttpSession.getAttributeNames();
        while (localEnumeration.hasMoreElements())
        {
          localObject = (String)localEnumeration.nextElement();
          localHttpSession.removeAttribute((String)localObject);
        }
        Object localObject = localHashMap.keySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str2 = (String)((Iterator)localObject).next();
          localHttpSession.setAttribute(str2, localHashMap.get(str2));
        }
      }
      else
      {
        this.error = 100301;
        str1 = this.taskErrorURL;
      }
    }
    catch (Throwable localThrowable)
    {
      this.error = 100302;
      str1 = this.taskErrorURL;
    }
    if (this.error != 0)
    {
      localSecureUser.setAgent(null);
      localHttpSession.setAttribute("SecureUser", localSecureUser);
    }
    return str1;
  }
  
  public void setArchiveName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.aQj = paramString;
    }
  }
  
  public String getArchiveName()
  {
    return this.aQj;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.obo.OBORestoreArchivedSession
 * JD-Core Version:    0.7.0.1
 */