package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetStock
  extends BaseTask
  implements Task
{
  private String mv = "Stock";
  private String mt = "Stocks";
  private int mu;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Stocks localStocks = (Stocks)localHttpSession.getAttribute(this.mt);
    if (localStocks != null)
    {
      Stock localStock = localStocks.getByCounterID(this.mu);
      if (localStock != null)
      {
        localHttpSession.setAttribute(this.mv, localStock);
        str = this.successURL;
      }
      else
      {
        this.error = 9039;
      }
    }
    else
    {
      this.error = 9038;
    }
    return str;
  }
  
  public void setStockCollection(String paramString)
  {
    this.mt = paramString;
  }
  
  public String getStockCollection()
  {
    return this.mt;
  }
  
  public void setStock(String paramString)
  {
    this.mv = paramString;
  }
  
  public String getStock()
  {
    return this.mv;
  }
  
  public void setCounterID(String paramString)
  {
    this.mu = Integer.parseInt(paramString);
  }
  
  public String getCounterID()
  {
    return new Integer(this.mu).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.SetStock
 * JD-Core Version:    0.7.0.1
 */