package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.UserAlert;
import com.ffusion.beans.portal.Stock;
import com.ffusion.beans.portal.Stocks;
import com.ffusion.beans.user.ContactPoints;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FillStockAlert
  extends FillUserAlert
{
  protected String _stockPrefix = "STOCK";
  protected String FREEFORM_PREFIX = "freeform";
  protected String _freeformStockPrefix = this.FREEFORM_PREFIX;
  protected String _stockCollectionName = "STOCKS";
  protected int DEFAULT_NUM_FREEFORM_STOCKS = 12;
  protected int _numFreeformStocks = this.DEFAULT_NUM_FREEFORM_STOCKS;
  protected String _freeformStocksListKey = "freeformStocksListKey";
  private String lk;
  private String ll;
  protected boolean _init = false;
  protected boolean _updateExisitngAlertBean = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject3;
    if (this._init)
    {
      localObject1 = (UserAlert)localHttpSession.getAttribute(this._alertSessionKey);
      if (localObject1 == null)
      {
        this.error = 19001;
        str = this.taskErrorURL;
      }
      else
      {
        initDeliveryInfos(localHttpSession);
        localObject2 = ((UserAlert)localObject1).getAdditionalProperty("PortfolioStockList");
        localObject3 = ((UserAlert)localObject1).getAdditionalProperty("FreeFormStockList");
        String[] arrayOfString;
        int i;
        if ((localObject2 != null) && (((String)localObject2).length() > 0))
        {
          arrayOfString = ((String)localObject2).split(",");
          for (i = 0; i < arrayOfString.length; i++) {
            localHttpSession.setAttribute(this._stockPrefix + arrayOfString[i], arrayOfString[i]);
          }
        }
        if ((localObject3 != null) && (((String)localObject3).length() > 0))
        {
          arrayOfString = ((String)localObject3).split(",");
          for (i = 0; i < arrayOfString.length; i++)
          {
            int j = i + 1;
            localHttpSession.setAttribute(this._freeformStockPrefix + j, arrayOfString[i]);
          }
        }
      }
      return str;
    }
    Object localObject1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Object localObject2 = (ContactPoints)localHttpSession.getAttribute(this._contactPointsCollections);
    if (localObject2 == null)
    {
      this.error = 19307;
      return this.taskErrorURL;
    }
    if (jdMethod_null(localHttpSession))
    {
      if (this._updateExisitngAlertBean) {
        localObject3 = (UserAlert)localHttpSession.getAttribute(this._alertSessionKey);
      } else {
        localObject3 = new UserAlert();
      }
      ((UserAlert)localObject3).setAlertType(5);
      ((UserAlert)localObject3).setStatus(1);
      ((UserAlert)localObject3).setDirectoryId(((SecureUser)localObject1).getProfileID());
      ((UserAlert)localObject3).setDeliveryInfos(constructDeliveryInfos(localHttpSession));
      ((UserAlert)localObject3).setAdditionalProperty("PortfolioStockList", this.lk);
      ((UserAlert)localObject3).setAdditionalProperty("FreeFormStockList", this.ll);
      localHttpSession.setAttribute(this._alertSessionKey, localObject3);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  private boolean jdMethod_null(HttpSession paramHttpSession)
  {
    Stocks localStocks = (Stocks)paramHttpSession.getAttribute(this._stockCollectionName);
    if (localStocks != null)
    {
      StringBuffer localStringBuffer1 = new StringBuffer();
      for (int i = 0; i < localStocks.size(); i++)
      {
        String str1 = (String)paramHttpSession.getAttribute(this._stockPrefix + ((Stock)localStocks.get(i)).getSymbol());
        if (str1 != null)
        {
          localStringBuffer1.append(str1);
          localStringBuffer1.append(",");
        }
      }
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int j = 1; j <= this._numFreeformStocks; j++)
      {
        String str2 = (String)paramHttpSession.getAttribute(this._freeformStockPrefix + j);
        if ((str2 != null) && (str2.trim().length() > 0))
        {
          localStringBuffer2.append(str2);
          localStringBuffer2.append(",");
        }
      }
      paramHttpSession.setAttribute(this._freeformStocksListKey, localStringBuffer2.toString());
      if ((localStringBuffer1.length() == 0) && (localStringBuffer2.length() == 0))
      {
        this.error = 19310;
        return false;
      }
      if (localStringBuffer1.length() > 0) {
        localStringBuffer1.deleteCharAt(localStringBuffer1.length() - 1);
      }
      if (localStringBuffer2.length() > 0) {
        localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1);
      }
      this.lk = localStringBuffer1.toString();
      this.ll = localStringBuffer2.toString();
    }
    else
    {
      this.error = 19311;
      return false;
    }
    return validateDeliveryInfos(paramHttpSession);
  }
  
  public void setInit(String paramString)
  {
    try
    {
      this._init = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getInit()
  {
    return Boolean.toString(this._init);
  }
  
  public void setUpdateExisitngAlertBean(String paramString)
  {
    try
    {
      this._updateExisitngAlertBean = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getUpdateExisitngAlertBean()
  {
    return Boolean.toString(this._updateExisitngAlertBean);
  }
  
  public void setStockPrefix(String paramString)
  {
    this._stockPrefix = paramString;
  }
  
  public void setStockCollectionName(String paramString)
  {
    this._stockCollectionName = paramString;
  }
  
  public void setNumFreeformStocks(String paramString)
  {
    this._numFreeformStocks = Integer.parseInt(paramString);
  }
  
  public void setFreeformPrefix(String paramString)
  {
    this._freeformStockPrefix = paramString;
  }
  
  public void setFreeformStocksListKey(String paramString)
  {
    this._freeformStocksListKey = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FillStockAlert
 * JD-Core Version:    0.7.0.1
 */