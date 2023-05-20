package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAlertStocks
  extends BaseTask
  implements Task
{
  protected String alertName = "Alert";
  protected String alertStocksName = "AlertStocks";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = SetAlertStocks(localHttpSession);
    if (this.error != 0) {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public int SetAlertStocks(HttpSession paramHttpSession)
  {
    int i = 0;
    Alert localAlert = (Alert)paramHttpSession.getAttribute(this.alertName);
    AlertStocks localAlertStocks = new AlertStocks();
    if (localAlert != null) {
      localAlertStocks = localAlert.getStocks();
    }
    paramHttpSession.setAttribute(this.alertStocksName, localAlertStocks);
    return i;
  }
  
  public final void SetAlertStocksName(String paramString)
  {
    this.alertStocksName = paramString;
  }
  
  public final String getAlertStocksName()
  {
    return this.alertStocksName;
  }
  
  public final String getAlertName()
  {
    return this.alertName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.SetAlertStocks
 * JD-Core Version:    0.7.0.1
 */