package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetProcessingWindows
  extends ExtendedBaseTask
{
  private String GR;
  private String GQ;
  private String GS;
  
  public GetProcessingWindows()
  {
    this.collectionSessionName = "ProcessingWindows";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    this.error = 0;
    ProcessingWindows localProcessingWindows = null;
    localProcessingWindows = (ProcessingWindows)localHttpSession.getAttribute(this.collectionSessionName);
    ProcessingWindow localProcessingWindow = new ProcessingWindow(this.locale);
    localProcessingWindow.setPaymentType(this.GR);
    localProcessingWindow.setBankId(this.GQ);
    localProcessingWindow.setCustomerId(this.GS);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if ((this.GR == null) || (this.GR.length() == 0))
      {
        this.error = 36002;
        str = this.taskErrorURL;
      }
      else
      {
        localProcessingWindows = PaymentsAdmin.getProcessingWindows(localSecureUser, localProcessingWindow, localHashMap);
        localHttpSession.setAttribute(this.collectionSessionName, localProcessingWindows);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setPaymentType(String paramString)
  {
    this.GR = paramString;
  }
  
  public String getPaymentType()
  {
    return this.GR;
  }
  
  public void setBankId(String paramString)
  {
    this.GQ = paramString;
  }
  
  public String getBankId()
  {
    return this.GQ;
  }
  
  public void setCustomerId(String paramString)
  {
    this.GS = paramString;
  }
  
  public String getCustomerId()
  {
    return this.GS;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.GetProcessingWindows
 * JD-Core Version:    0.7.0.1
 */