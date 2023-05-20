package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.AlertStocks;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyAlertStock
  extends ModifyAlert
{
  protected String alertStockName = "ALERTSTOCK";
  protected String alertStockID;
  protected AlertStock alertStock = new AlertStock();
  
  public AlertStock getAlertStock()
  {
    return this.alertStock;
  }
  
  public void setAlertStockID(String paramString)
  {
    this.alertStockID = paramString;
  }
  
  public void setSymbol(String paramString)
  {
    this.alertStock.setSymbol(paramString);
  }
  
  public void setAmount(String paramString)
  {
    this.alertStock.setAmount(paramString);
  }
  
  public void setCriteria(String paramString)
  {
    this.alertStock.setCriteria(paramString);
  }
  
  public void setOnetime(String paramString)
  {
    this.alertStock.setOnetime(paramString);
  }
  
  public String getSymbol()
  {
    return this.alertStock.getSymbol();
  }
  
  public String getAmount()
  {
    return this.alertStock.getAmount();
  }
  
  public String getCriteria()
  {
    return this.alertStock.getCriteria();
  }
  
  public String getOnetime()
  {
    return this.alertStock.getOnetime();
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if (!validateSymbol()) {
      str = this.taskErrorURL;
    } else {
      str = processModifyAlertStock(localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    if (paramHttpSession.getAttribute(this.alertName) == null)
    {
      this.error = 19001;
    }
    else
    {
      this.error = 0;
      set((Alert)paramHttpSession.getAttribute(this.alertName));
      this.alertStocks = getStocks();
      this.alertStock = this.alertStocks.getByID(this.alertStockID);
      paramHttpSession.setAttribute(this.alertStockName, this.alertStock);
    }
    return this.error == 0;
  }
  
  public String processModifyAlertStock(HttpSession paramHttpSession)
    throws IOException
  {
    String str = null;
    if (!validateInput(paramHttpSession))
    {
      str = this.taskErrorURL;
      return str;
    }
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    Alert localAlert1 = (Alert)paramHttpSession.getAttribute(this.alertName);
    setStocks(this.alertStocks);
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      Alert localAlert2 = new Alert();
      localAlert2 = com.ffusion.csil.core.Alerts.modifyAlert(localSecureUser, localAlert1, localHashMap);
      paramHttpSession.setAttribute(this.alertName, localAlert2);
      com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts(this.locale);
      localHashMap.put("ALERTSLIST", localAlerts1);
      localAlerts1 = com.ffusion.csil.core.Alerts.getAlerts(localSecureUser, localHashMap);
      paramHttpSession.setAttribute(this.alertsName, localAlerts1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  protected boolean validateSymbol()
  {
    this.error = 0;
    if ((getSymbol() == null) || (getSymbol().trim().length() == 0)) {
      this.error = 63;
    }
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.ModifyAlertStock
 * JD-Core Version:    0.7.0.1
 */