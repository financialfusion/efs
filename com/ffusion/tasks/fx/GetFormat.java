package com.ffusion.tasks.fx;

import com.ffusion.beans.fx.FXCurrencyFormat;
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

public class GetFormat
  extends BaseTask
{
  private String W2 = "FOREIGN_EXCHANGE_CURRENCY_FORMAT";
  private String W0;
  private String W1;
  
  public void setFXSessionName(String paramString)
  {
    this.W2 = paramString;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.W0 = paramString;
  }
  
  public void setLocale(String paramString)
  {
    this.W1 = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    try
    {
      localHttpSession.removeAttribute(this.W2);
      FXCurrencyFormat localFXCurrencyFormat = null;
      if (this.W1 == null) {
        localFXCurrencyFormat = FX.getFormat(this.W0, localHashMap);
      } else {
        localFXCurrencyFormat = FX.getFormat(this.W0, this.W1, localHashMap);
      }
      if (localFXCurrencyFormat != null) {
        localHttpSession.setAttribute(this.W2, localFXCurrencyFormat);
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
 * Qualified Name:     com.ffusion.tasks.fx.GetFormat
 * JD-Core Version:    0.7.0.1
 */