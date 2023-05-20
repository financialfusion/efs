package com.ffusion.tasks.alerts;

import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.AlertStock;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStockFromAlert
  extends AddStocksToAlert
  implements Task
{
  public static AlertStock execute(Properties paramProperties)
  {
    AlertStock localAlertStock = null;
    if (paramProperties != null)
    {
      localAlertStock = new AlertStock();
      localAlertStock.setSymbol((String)paramProperties.get("Symbol"));
      localAlertStock.setCriteria((String)paramProperties.get("Criteria"));
      localAlertStock.setAmount((String)paramProperties.get("Amount"));
      localAlertStock.setOnetime((String)paramProperties.get("Onetime"));
      if ((localAlertStock.getSymbol() == null) || (localAlertStock.getCriteria() == null)) {
        localAlertStock = null;
      }
    }
    return localAlertStock;
  }
  
  public static AlertStock execute(DeliveryInfo paramDeliveryInfo)
  {
    return execute(paramDeliveryInfo.getPropertiesValue());
  }
  
  public static AlertStock execute(Alert paramAlert)
  {
    AlertStock localAlertStock = null;
    if ((paramAlert != null) && (paramAlert.getDeliveryInfosValue() != null) && (paramAlert.getDeliveryInfosValue().get(0) != null))
    {
      DeliveryInfo localDeliveryInfo = paramAlert.getDeliveryInfosValue().getByID("1");
      localAlertStock = execute(localDeliveryInfo);
    }
    return localAlertStock;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    setAlert((Alert)localHttpSession.getAttribute(getAlertSessionKey()));
    if (getAlert() != null)
    {
      AlertStock localAlertStock = execute(getAlert());
      if (localAlertStock != null)
      {
        setAlertStock(localAlertStock);
        paramHttpServletRequest.setAttribute("ALERTSTOCK", localAlertStock);
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.GetStockFromAlert
 * JD-Core Version:    0.7.0.1
 */