package com.ffusion.tasks.fx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConvertToTargetCurrency
  extends BaseTask
{
  private String WX = null;
  private String WY = null;
  private String WW = null;
  private String WZ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 34101;
      return this.taskErrorURL;
    }
    if ((this.WX == null) || (this.WX.length() == 0))
    {
      this.error = 34102;
      return this.taskErrorURL;
    }
    BigDecimal localBigDecimal1 = null;
    try
    {
      localBigDecimal1 = new BigDecimal(this.WX);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localBigDecimal1 = new BigDecimal(0.0D);
    }
    try
    {
      HashMap localHashMap = new HashMap();
      FXRate localFXRate = FX.getFXRate(localSecureUser, this.WY, this.WZ, 4, String.valueOf(localSecureUser.getProfileID()), localHashMap);
      if (localFXRate == null)
      {
        this.error = 34105;
        return this.serviceErrorURL;
      }
      BigDecimal localBigDecimal2 = localBigDecimal1.multiply(localFXRate.getBuyPrice().getAmountValue());
      int i = FX.getNumDecimals(this.WZ, localHashMap);
      localBigDecimal2 = localBigDecimal2.setScale(i, 6);
      this.WW = localBigDecimal2.toString();
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getCode();
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof FXException))) {
        this.error = ((FXException)localCSILException.childException).getErrorCode();
      }
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setBaseAmount(String paramString)
  {
    this.WX = paramString;
  }
  
  public String getBaseAmount()
  {
    return this.WX;
  }
  
  public void setBaseAmtCurrency(String paramString)
  {
    this.WY = paramString;
  }
  
  public String getBaseAmtCurrency()
  {
    return this.WY;
  }
  
  public String getTargetAmount()
  {
    return this.WW;
  }
  
  public void setTargetAmtCurrency(String paramString)
  {
    this.WZ = paramString;
  }
  
  public String getTargetAmtCurrency()
  {
    return this.WZ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.ConvertToTargetCurrency
 * JD-Core Version:    0.7.0.1
 */