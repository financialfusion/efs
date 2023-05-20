package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CleanupObjects
  extends BaseTask
{
  protected ArrayList objectList = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.objectList != null)
    {
      Iterator localIterator = this.objectList.iterator();
      while (localIterator.hasNext()) {
        localHttpSession.removeAttribute((String)localIterator.next());
      }
      this.objectList.clear();
    }
    return this.successURL;
  }
  
  public void setObjectToRemove(String paramString)
  {
    if (this.objectList == null) {
      this.objectList = new ArrayList(5);
    }
    this.objectList.add(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.CleanupObjects
 * JD-Core Version:    0.7.0.1
 */