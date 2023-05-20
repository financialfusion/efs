package com.ffusion.tasks.stoppayments;

import com.ffusion.services.Stops;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateService
  extends BaseTask
  implements Task
{
  private String kN;
  private String kM;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Stops localStops = (Stops)localHttpSession.getAttribute("com.ffusion.services.Stops");
    if (localStops != null)
    {
      if (this.kN != null) {
        localStops.setUserName(this.kN);
      }
      if (this.kM != null) {
        localStops.setPassword(this.kM);
      }
    }
    else
    {
      this.error = 13010;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this.kN = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.kM = paramString;
  }
  
  public String getUserName()
  {
    return this.kN;
  }
  
  public String getPassword()
  {
    return this.kM;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.UpdateService
 * JD-Core Version:    0.7.0.1
 */