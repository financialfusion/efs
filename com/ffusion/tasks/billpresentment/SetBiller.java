package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.beans.billpresentment.Billers;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBiller
  extends BaseTask
  implements Task
{
  private long JH;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    int i = setBiller(localHttpSession);
    if (i == -1) {
      str = this.taskErrorURL;
    } else if (i == -2) {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public int setBiller(HttpSession paramHttpSession)
  {
    int i = 0;
    Billers localBillers = (Billers)paramHttpSession.getAttribute("Billers");
    if (localBillers != null)
    {
      Biller localBiller = localBillers.getByBillerID(this.JH);
      if (localBiller != null)
      {
        paramHttpSession.setAttribute("Biller", localBiller);
      }
      else
      {
        i = -1;
        this.error = 6502;
      }
    }
    else
    {
      i = -1;
      this.error = 6503;
    }
    return i;
  }
  
  public final void setID(String paramString)
  {
    try
    {
      this.JH = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetBiller Task Exception: ", localException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SetBiller
 * JD-Core Version:    0.7.0.1
 */