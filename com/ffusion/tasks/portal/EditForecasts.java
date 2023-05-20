package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Forecasts;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditForecasts
  extends EditPortal
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Forecasts localForecasts = (Forecasts)localHttpSession.getAttribute("ForecastsEdit");
    if (localForecasts == null)
    {
      this.error = 9005;
      str = this.taskErrorURL;
    }
    else
    {
      localHttpSession.setAttribute("ForecastSettings", localForecasts);
      if (!processShowItem(paramHttpServletRequest, "FORECASTS"))
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditForecasts
 * JD-Core Version:    0.7.0.1
 */