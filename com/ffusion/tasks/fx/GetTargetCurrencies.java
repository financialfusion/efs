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

public class GetTargetCurrencies
  extends BaseTask
{
  private String Xe = "FOREIGN_EXCHANGE_TARGET_CURRENCIES";
  
  public void setFXSessionName(String paramString)
  {
    this.Xe = paramString;
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
      localHttpSession.removeAttribute(this.Xe);
      FXCurrencies localFXCurrencies = FX.getTargetCurrencies(localSecureUser, localHashMap);
      if (localFXCurrencies != null) {
        localHttpSession.setAttribute(this.Xe, localFXCurrencies);
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
 * Qualified Name:     com.ffusion.tasks.fx.GetTargetCurrencies
 * JD-Core Version:    0.7.0.1
 */