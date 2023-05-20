package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.Consumers;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetConsumer
  extends BaseTask
  implements Task
{
  private long Ke;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    Consumers localConsumers = (Consumers)localHttpSession.getAttribute("Consumers");
    String str = null;
    if (localConsumers == null)
    {
      this.error = 6507;
      str = this.taskErrorURL;
    }
    else
    {
      Consumer localConsumer = localConsumers.getByConsumerID(this.Ke);
      if (localConsumer == null)
      {
        this.error = 6701;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute("Consumer", localConsumer);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    try
    {
      this.Ke = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetConsumer Task Exception: ", localException);
    }
  }
  
  public final String getID()
  {
    return Long.toString(this.Ke);
  }
  
  public final long getIDValue()
  {
    return this.Ke;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SetConsumer
 * JD-Core Version:    0.7.0.1
 */