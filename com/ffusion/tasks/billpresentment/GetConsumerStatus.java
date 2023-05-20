package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetConsumerStatus
  extends ConsumerStatus
  implements Task
{
  private String Js;
  private String Jp;
  private String Jr;
  private int Jq;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    setLocale(localLocale);
    String str = null;
    Consumer localConsumer = (Consumer)localHttpSession.getAttribute("Consumer");
    if (localConsumer == null)
    {
      this.Jq = 6506;
      return this.Js;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.Jq = 0;
    try
    {
      ConsumerStatus localConsumerStatus = BillPresentment.getConsumerStatus(localSecureUser, localConsumer, localHashMap);
      set(localConsumerStatus);
    }
    catch (CSILException localCSILException)
    {
      this.Jq = MapError.mapError(localCSILException);
    }
    if (this.Jq == 0)
    {
      str = this.Jr;
      localHttpSession.setAttribute("ConsumerStatus", this);
    }
    else
    {
      str = this.Jp;
    }
    return str;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Jr = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Jp = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Js = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Jq);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetConsumerStatus
 * JD-Core Version:    0.7.0.1
 */