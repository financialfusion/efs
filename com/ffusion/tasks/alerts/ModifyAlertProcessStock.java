package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import java.util.Locale;
import javax.servlet.http.HttpSession;

public class ModifyAlertProcessStock
  extends ModifyAlertStock
{
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    Alert localAlert = (Alert)paramHttpSession.getAttribute(this.alertName);
    if (paramHttpSession.getAttribute(this.alertName) == null)
    {
      this.error = 19001;
    }
    else
    {
      this.error = 0;
      set(localAlert);
      this.alertStock = GetStockFromAlert.execute(localAlert);
      paramHttpSession.setAttribute(this.alertStockName, this.alertStock);
    }
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.ModifyAlertProcessStock
 * JD-Core Version:    0.7.0.1
 */