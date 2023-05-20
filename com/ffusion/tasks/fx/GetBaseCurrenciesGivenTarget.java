package com.ffusion.tasks.fx;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBaseCurrenciesGivenTarget
  extends BaseTask
{
  private String WV = "FOREIGN_EXCHANGE_BASE_CURRENCIES_GIVEN_TARGET";
  private String WU;
  
  public void setFXRateSessionName(String paramString)
  {
    this.WV = paramString;
  }
  
  public void setTargetCurrencyCode(String paramString)
  {
    this.WU = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localHttpSession.removeAttribute(this.WV);
      FXCurrencies localFXCurrencies = FX.getBaseCurrenciesGivenTarget(localSecureUser, this.WU, localHashMap);
      if (localFXCurrencies != null) {
        localHttpSession.setAttribute(this.WV, localFXCurrencies);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.taskErrorURL;
      this.error = localCSILException.getCode();
      if ((localCSILException.childException != null) && ((localCSILException.childException instanceof FXException))) {
        this.error = ((FXException)localCSILException.childException).getErrorCode();
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.GetBaseCurrenciesGivenTarget
 * JD-Core Version:    0.7.0.1
 */