package com.ffusion.tasks.user;

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

public class CreateSession
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    Enumeration localEnumeration = localHttpSession.getAttributeNames();
    while (localEnumeration.hasMoreElements())
    {
      localObject = (String)localEnumeration.nextElement();
      localHashMap.put(localObject, localHttpSession.getAttribute((String)localObject));
    }
    localHttpSession.invalidate();
    localHttpSession = paramHttpServletRequest.getSession(true);
    Object localObject = localHashMap.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      localHttpSession.setAttribute(str, localHashMap.get(str));
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.CreateSession
 * JD-Core Version:    0.7.0.1
 */