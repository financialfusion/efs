package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.Consumers;
import com.ffusion.beans.billpresentment.Publisher;
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

public class GetConsumers
  extends Consumers
  implements Task
{
  private String Kr;
  private String Ko;
  private String Kq;
  private int Kp;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    clear();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    setLocale(localLocale);
    Consumers localConsumers = (Consumers)clone();
    String str = null;
    Publisher localPublisher = (Publisher)localHttpSession.getAttribute("Publisher");
    Consumer localConsumer = (Consumer)localHttpSession.getAttribute("Consumer");
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.Kp = 0;
    try
    {
      localConsumers = BillPresentment.getConsumerList(localSecureUser, localPublisher, localConsumer, localHashMap);
      addAll(localConsumers);
    }
    catch (CSILException localCSILException)
    {
      this.Kp = MapError.mapError(localCSILException);
    }
    if (this.Kp == 0)
    {
      str = this.Kq;
      localHttpSession.setAttribute("Consumers", this);
    }
    else
    {
      str = this.Ko;
    }
    return str;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Kq = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Ko = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Kr = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Kp);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetConsumers
 * JD-Core Version:    0.7.0.1
 */