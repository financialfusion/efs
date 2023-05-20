package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.beans.billpresentment.Publishers;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPublisher
  extends BaseTask
  implements Task
{
  private long JG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    int i = setPublisher(paramHttpServletRequest.getSession());
    if (i == -1) {
      str = this.taskErrorURL;
    } else if (i == -2) {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public int setPublisher(HttpSession paramHttpSession)
  {
    int i = 0;
    Publishers localPublishers = (Publishers)paramHttpSession.getAttribute("Publishers");
    if (localPublishers != null)
    {
      Publisher localPublisher = localPublishers.getByPublisherID(this.JG);
      if (localPublisher != null)
      {
        paramHttpSession.setAttribute("Publisher", localPublisher);
      }
      else
      {
        paramHttpSession.removeAttribute("Publisher");
        i = -1;
        this.error = 6511;
      }
    }
    else
    {
      i = -1;
      this.error = 6512;
    }
    return i;
  }
  
  public final void setID(String paramString)
  {
    try
    {
      this.JG = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetPublisher Task Exception: ", localException);
    }
  }
  
  public final String getID()
  {
    return Long.toString(this.JG);
  }
  
  public final long getIDValue()
  {
    return this.JG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SetPublisher
 * JD-Core Version:    0.7.0.1
 */