package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.beans.alerts.Alerts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAlertStock
  extends BaseTask
  implements Task
{
  private String k5 = "ALERTSTOCKS";
  private String k4 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    AlertStocks localAlertStocks = new AlertStocks();
    Alerts localAlerts = (Alerts)localHttpSession.getAttribute(getAlertsKey());
    if (localAlerts != null)
    {
      Iterator localIterator = localAlerts.iterator();
      Alert localAlert = null;
      while (localIterator.hasNext())
      {
        localAlert = (Alert)localIterator.next();
        if (localAlert != null)
        {
          AlertStock localAlertStock = GetStockFromAlert.execute(localAlert);
          if (localAlertStock != null)
          {
            localAlertStock.setAlert(localAlert);
            localAlertStocks.add(localAlertStock);
          }
        }
      }
    }
    localHttpSession.setAttribute(getAlertStocksSessionKey(), localAlertStocks);
    return str;
  }
  
  public String getAlertStocksSessionKey()
  {
    return this.k5;
  }
  
  public void setAlertStocksSessionKey(String paramString)
  {
    this.k5 = paramString;
  }
  
  public String getAlertsKey()
  {
    return this.k4;
  }
  
  public void setAlertsKey(String paramString)
  {
    this.k4 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.SetAlertStock
 * JD-Core Version:    0.7.0.1
 */