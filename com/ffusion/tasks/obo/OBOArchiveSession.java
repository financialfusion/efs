package com.ffusion.tasks.obo;

import com.ffusion.beans.SecureUser;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OBOArchiveSession
  extends BaseTask
  implements OBOArchiveTask
{
  private String aQn = "FFISessionArchive";
  private String aQk;
  private String aQm;
  private String aQl;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      SecureUser localSecureUser1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
      SecureUser localSecureUser2 = new SecureUser(localSecureUser1.getLocale());
      HashMap localHashMap = new HashMap();
      Enumeration localEnumeration = localHttpSession.getAttributeNames();
      while (localEnumeration.hasMoreElements())
      {
        String str2 = (String)localEnumeration.nextElement();
        Object localObject = localHttpSession.getAttribute(str2);
        if (!(localObject instanceof OBOArchiveSession)) {
          localHashMap.put(str2, localObject);
        }
        localHttpSession.removeAttribute(str2);
      }
      localSecureUser2.setAgent(localSecureUser1);
      localSecureUser2.setUserName(this.aQk);
      localSecureUser2.setPassword(this.aQm);
      localHttpSession.setAttribute("AffiliateBankURL", getAffiliateBankURL());
      localHttpSession.setAttribute("SecureUser", localSecureUser2);
      localHttpSession.setAttribute(getArchiveName(), localHashMap);
    }
    catch (Throwable localThrowable)
    {
      this.error = 100300;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setArchiveName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.aQn = paramString;
    }
  }
  
  public String getArchiveName()
  {
    return this.aQn;
  }
  
  public void setUserName(String paramString)
  {
    this.aQk = paramString;
  }
  
  public String getUserName()
  {
    return this.aQk;
  }
  
  public void setPassword(String paramString)
  {
    this.aQm = paramString;
  }
  
  public String getPassword()
  {
    return this.aQm;
  }
  
  public String getAffiliateBankURL()
  {
    return this.aQl;
  }
  
  public void setAffiliateBankURL(String paramString)
  {
    this.aQl = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.obo.OBOArchiveSession
 * JD-Core Version:    0.7.0.1
 */