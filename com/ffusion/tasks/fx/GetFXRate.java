package com.ffusion.tasks.fx;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FX;
import com.ffusion.fx.FXException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFXRate
  extends BaseTask
{
  private String WI = "FOREIGN_EXCHANGE_RATE";
  private String WG;
  private String WH;
  private DateTime WF;
  private int WJ = 0;
  private String WE = "0";
  
  public void setFXSessionName(String paramString)
  {
    this.WI = paramString;
  }
  
  public void setBaseCurrencyCode(String paramString)
  {
    this.WG = paramString;
  }
  
  public void setTargetCurrencyCode(String paramString)
  {
    this.WH = paramString;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.WF = paramDateTime;
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
      localHttpSession.removeAttribute(this.WI);
      FXRate localFXRate = null;
      if (this.WF == null) {
        localFXRate = FX.getFXRate(localSecureUser, this.WG, this.WH, this.WJ, this.WE, localHashMap);
      } else {
        localFXRate = FX.getFXRate(localSecureUser, this.WG, this.WH, this.WF, this.WJ, this.WE, localHashMap);
      }
      if (localFXRate != null) {
        localHttpSession.setAttribute(this.WI, localFXRate);
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
  
  public String getObjectID()
  {
    return this.WE;
  }
  
  public void setObjectID(String paramString)
  {
    this.WE = paramString;
  }
  
  public int getObjectTypeValue()
  {
    return this.WJ;
  }
  
  public String getObjectType()
  {
    return Integer.toString(this.WJ);
  }
  
  public void setObjectType(int paramInt)
  {
    this.WJ = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fx.GetFXRate
 * JD-Core Version:    0.7.0.1
 */