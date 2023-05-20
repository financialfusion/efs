package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateStocks
  extends BaseTask
  implements Task
{
  protected String list = null;
  protected String stocksKey = "StocksEdit";
  protected String errorMsgKey = "StocksValidationErrorMsg";
  protected String validateErrorBackURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Object localObject1;
    Object localObject2;
    Object localObject4;
    if (this.list == null)
    {
      localObject1 = (Stocks)localHttpSession.getAttribute(this.stocksKey);
      if (localObject1 == null)
      {
        this.error = 9038;
        return this.taskErrorURL;
      }
      localObject2 = new Stocks();
      Iterator localIterator = ((Stocks)localObject1).iterator();
      while (localIterator.hasNext())
      {
        localObject3 = (Stock)localIterator.next();
        if (!((Stock)localObject3).getSymbol().equals(""))
        {
          localObject4 = new Stock();
          ((Stock)localObject4).set((Stock)localObject3);
          ((Stocks)localObject2).add(localObject4);
        }
      }
      localObject1 = localObject2;
    }
    else
    {
      localObject1 = new Stocks();
      if (this.list.length() > 0)
      {
        localObject2 = this.list.split(",");
        for (int i = 0; i < localObject2.length; i++)
        {
          localObject3 = new Stock();
          ((Stock)localObject3).setSymbol(localObject2[i]);
          ((Stocks)localObject1).add(localObject3);
        }
      }
    }
    if (((Stocks)localObject1).size() == 0) {
      return this.successURL;
    }
    try
    {
      localObject2 = Portal.getStocks(localSecureUser, (Stocks)localObject1, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
      return this.serviceErrorURL;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    Object localObject3 = ((Stocks)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (Stock)((Iterator)localObject3).next();
      if ((((Stock)localObject4).getCompanyName() == null) || (((Stock)localObject4).getCompanyName().equals("")))
      {
        localStringBuffer.append(((Stock)localObject4).getSymbol());
        localStringBuffer.append(", ");
      }
    }
    if (localStringBuffer.length() > 0)
    {
      localStringBuffer.delete(localStringBuffer.length() - 2, localStringBuffer.length());
      localObject4 = new Object[1];
      localObject4[0] = localStringBuffer.toString();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.alerts.resources", "STOCKS_VALIDATION_ERROR_MSG", (Object[])localObject4);
      localHttpSession.setAttribute(this.errorMsgKey, localLocalizableString.localize(localSecureUser.getLocale()));
      return this.validateErrorBackURL;
    }
    return this.successURL;
  }
  
  public void setList(String paramString)
  {
    this.list = paramString;
  }
  
  public void setStocksKey(String paramString)
  {
    this.stocksKey = paramString;
  }
  
  public void setErrorMsgKey(String paramString)
  {
    this.errorMsgKey = paramString;
  }
  
  public void setValidateErrorBackURL(String paramString)
  {
    this.validateErrorBackURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.ValidateStocks
 * JD-Core Version:    0.7.0.1
 */