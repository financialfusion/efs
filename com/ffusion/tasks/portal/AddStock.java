package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddStock
  extends Stock
  implements Task
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String validate;
  protected boolean processFlag = false;
  protected String stockCollection = "Stocks";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = doProcess(localHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf("SYMBOL") != -1)) {
        bool = validateSymbol();
      }
      if ((bool) && (this.validate.indexOf("SHARES") != -1)) {
        bool = validateShares();
      }
      if ((bool) && (this.validate.indexOf("PURCHASE_PRICE") != -1)) {
        bool = validatePurchasePrice();
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateSymbol()
  {
    this.error = 0;
    if ((getSymbol() == null) || (getSymbol().length() == 0)) {
      this.error = 9041;
    }
    return this.error == 0;
  }
  
  protected boolean validateShares()
  {
    this.error = 0;
    if ((getShares() == null) || (getShares().length() == 0)) {
      this.error = 9043;
    } else {
      try
      {
        double d = getDoubleValue(getShares());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.throwing("AddStock Task Exception: ", localNumberFormatException);
        this.error = 9043;
      }
    }
    return this.error == 0;
  }
  
  protected boolean validatePurchasePrice()
  {
    this.error = 0;
    if ((getPurchasePrice() == null) || (getPurchasePrice().length() == 0)) {
      this.error = 9044;
    } else {
      try
      {
        double d = getDoubleValue(getPurchasePrice());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        DebugLog.throwing("AddStock Task Exception: ", localNumberFormatException);
        this.error = 9044;
      }
    }
    return this.error == 0;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = this.taskErrorURL;
    Stocks localStocks = (Stocks)paramHttpSession.getAttribute(this.stockCollection);
    if (localStocks != null)
    {
      Stock localStock = null;
      int i = 0;
      Iterator localIterator = localStocks.iterator();
      while (localIterator.hasNext())
      {
        localStock = (Stock)localIterator.next();
        if (localStock.getSymbol().equals(""))
        {
          setCounter(localStock.getCounter());
          localStock.set(this);
          i = 1;
          str = this.successURL;
        }
      }
      if (i == 0) {
        this.error = 9042;
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
    this.stockCollection = paramString;
  }
  
  public String getStockCollection()
  {
    return this.stockCollection;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getProcess()
  {
    Boolean localBoolean = new Boolean(this.processFlag);
    return localBoolean.toString();
  }
  
  public void setValidate(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(""))) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public String getValidate()
  {
    if ((this.validate == null) || (this.validate.length() == 0)) {
      return "";
    }
    return new String(this.validate);
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.AddStock
 * JD-Core Version:    0.7.0.1
 */