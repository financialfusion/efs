package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddStocksToAlert
  extends BaseTask
  implements Task
{
  private Alert lg = null;
  private AlertStock lf = null;
  private String lh = null;
  private String le = null;
  
  public static void execute(Alert paramAlert, AlertStock paramAlertStock)
  {
    if ((paramAlert != null) && (paramAlert.getDeliveryInfosValue() != null) && (paramAlert.getDeliveryInfosValue().get(0) != null))
    {
      DeliveryInfo localDeliveryInfo = paramAlert.getDeliveryInfosValue().getByID("1");
      String str = null;
      if (paramAlertStock.getSymbol() != null)
      {
        str = (String)localDeliveryInfo.getPropertiesValue().get("Symbol");
        if (str != null) {
          localDeliveryInfo.getPropertiesValue().remove("Symbol");
        }
        localDeliveryInfo.getPropertiesValue().put("Symbol", paramAlertStock.getSymbol());
      }
      if (paramAlertStock.getCriteria() != null)
      {
        str = (String)localDeliveryInfo.getPropertiesValue().get("Criteria");
        if (str != null) {
          localDeliveryInfo.getPropertiesValue().remove("Criteria");
        }
        localDeliveryInfo.getPropertiesValue().put("Criteria", paramAlertStock.getCriteria());
      }
      if (paramAlertStock.getAmount() != null)
      {
        str = (String)localDeliveryInfo.getPropertiesValue().get("Amount");
        if (str != null) {
          localDeliveryInfo.getPropertiesValue().remove("Amount");
        }
        localDeliveryInfo.getPropertiesValue().put("Amount", paramAlertStock.getAmount());
      }
      if (paramAlertStock.getOnetime() != null)
      {
        str = (String)localDeliveryInfo.getPropertiesValue().get("Onetime");
        if (str != null) {
          localDeliveryInfo.getPropertiesValue().remove("Onetime");
        }
        localDeliveryInfo.getPropertiesValue().put("Onetime", paramAlertStock.getOnetime());
      }
      localDeliveryInfo.getPropertiesValue().put("AlertType", "ProcessStockPortfolio");
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    setAlert((Alert)localHttpSession.getAttribute(getAlertSessionKey()));
    setAlertStock((AlertStock)localHttpSession.getAttribute(getAlertStockSessionKey()));
    if ((getAlert() != null) && (getAlertStock() != null)) {
      execute(getAlert(), getAlertStock());
    }
    return str;
  }
  
  public Alert getAlert()
  {
    return this.lg;
  }
  
  public void setAlert(Alert paramAlert)
  {
    this.lg = paramAlert;
  }
  
  public AlertStock getAlertStock()
  {
    return this.lf;
  }
  
  public void setAlertStock(AlertStock paramAlertStock)
  {
    this.lf = paramAlertStock;
  }
  
  public String getAlertSessionKey()
  {
    return this.le;
  }
  
  public void setAlertSessionKey(String paramString)
  {
    this.le = paramString;
  }
  
  public String getAlertStockSessionKey()
  {
    return this.lh;
  }
  
  public void setAlertStockSessionKey(String paramString)
  {
    this.lh = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.AddStocksToAlert
 * JD-Core Version:    0.7.0.1
 */