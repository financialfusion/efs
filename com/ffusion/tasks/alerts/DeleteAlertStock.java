package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAlertStock
  extends BaseTask
  implements Task
{
  protected String serviceName = "com.ffusion.services.Alerts";
  protected String alertName = "Alert";
  protected String alertStockName = "ALERTSTOCK";
  protected String alertStocksName = "AlertStocks";
  protected String alertsName = "Alerts";
  protected String alertStockID;
  protected AlertStock alertStock;
  protected AlertStocks alertStocks;
  
  public AlertStock getAlertStock()
  {
    return this.alertStock;
  }
  
  public void setAlertStockID(String paramString)
  {
    this.alertStockID = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)localHttpSession.getAttribute(this.serviceName);
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    Alert localAlert1 = (Alert)localHttpSession.getAttribute(this.alertName);
    this.alertStocks = ((AlertStocks)localHttpSession.getAttribute(this.alertStocksName));
    this.alertStock = this.alertStocks.getByID(this.alertStockID);
    if (localAlert1 == null)
    {
      this.error = 19001;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap = null;
      if (localAlerts != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localAlerts);
      }
      this.alertStocks.remove(this.alertStock);
      localAlert1.setStocks(this.alertStocks);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        Alert localAlert2 = new Alert();
        localAlert2 = com.ffusion.csil.core.Alerts.modifyAlert(localSecureUser, localAlert1, localHashMap);
        localHttpSession.setAttribute(this.alertName, localAlert2);
        com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts(localLocale);
        localHashMap.put("ALERTSLIST", localAlerts1);
        localAlerts1 = com.ffusion.csil.core.Alerts.getAlerts(localSecureUser, localHashMap);
        localHttpSession.setAttribute(this.alertsName, localAlerts1);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
  
  public final void setAlertsName(String paramString)
  {
    this.alertsName = paramString;
  }
  
  public final String getAlertsName()
  {
    return this.alertsName;
  }
  
  public final void setAlertName(String paramString)
  {
    this.alertName = paramString;
  }
  
  public final String getAlertName()
  {
    return this.alertName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.DeleteAlertStock
 * JD-Core Version:    0.7.0.1
 */