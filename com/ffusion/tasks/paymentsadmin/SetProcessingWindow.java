package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetProcessingWindow
  extends ExtendedBaseTask
  implements TaskDefines
{
  public SetProcessingWindow()
  {
    this.beanSessionName = "ProcessingWindow";
    this.collectionSessionName = "ProcessingWindows";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ProcessingWindows localProcessingWindows = (ProcessingWindows)localHttpSession.getAttribute(this.collectionSessionName);
    ProcessingWindow localProcessingWindow1 = (ProcessingWindow)localProcessingWindows.getFirstByFilter("Id=" + this.id);
    if (localProcessingWindow1 == null)
    {
      this.error = 36003;
      str = this.taskErrorURL;
    }
    else
    {
      ProcessingWindow localProcessingWindow2 = new ProcessingWindow();
      localProcessingWindow2.set(localProcessingWindow1);
      localHttpSession.setAttribute(this.beanSessionName, localProcessingWindow2);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.SetProcessingWindow
 * JD-Core Version:    0.7.0.1
 */