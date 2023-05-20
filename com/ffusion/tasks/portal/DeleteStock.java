package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Stock;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteStock
  extends BaseTask
  implements Task
{
  protected String stockName = "Stock";
  protected String stockCollection = "Stocks";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Stock localStock = (Stock)localHttpSession.getAttribute(this.stockName);
    if (localStock == null)
    {
      this.error = 9040;
      str = this.taskErrorURL;
    }
    else
    {
      localStock.setSymbol("");
      localStock.setShares("");
      localStock.setPurchasePrice("");
      this.error = 0;
    }
    return str;
  }
  
  public void setStockCollection(String paramString)
  {
    this.stockCollection = paramString;
  }
  
  public String getStockCollection()
  {
    return this.stockCollection;
  }
  
  public void setStock(String paramString)
  {
    this.stockName = paramString;
  }
  
  public String getStock()
  {
    return this.stockName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.DeleteStock
 * JD-Core Version:    0.7.0.1
 */