package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyProcessingWindow
  extends ProcessingWindow
  implements TaskDefines
{
  protected String validate;
  protected boolean initFlag = false;
  protected boolean processFlag = false;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String beanSessionName = "ProcessingWindow";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processProcessingWindow(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    ProcessingWindow localProcessingWindow = (ProcessingWindow)paramHttpSession.getAttribute(this.beanSessionName);
    if (localProcessingWindow == null)
    {
      this.error = 36000;
      return false;
    }
    set(localProcessingWindow);
    paramHttpSession.setAttribute("OldProcessingWindow", localProcessingWindow.clone());
    this.error = 0;
    return true;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected String processProcessingWindow(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editProcessingWindow(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String editProcessingWindow(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 36004;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ProcessingWindow localProcessingWindow1 = null;
    try
    {
      localProcessingWindow1 = PaymentsAdmin.modifyProcessingWindow(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ProcessingWindow localProcessingWindow2 = new ProcessingWindow(this.locale);
      localProcessingWindow2.set(localProcessingWindow1);
      paramHttpSession.setAttribute(this.beanSessionName, localProcessingWindow2);
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    if ((this.validate == null) || (this.validate.length() == 0)) {
      return true;
    }
    if ((this.validate.indexOf("PAYMENT_TYPE") != -1) && (p(this.pmtType) == true))
    {
      this.error = 36002;
      return false;
    }
    if ((this.validate.indexOf("PAYMENT_SUB_TYPE") != -1) && (p(this.pmtSubType) == true))
    {
      this.error = 36005;
      return false;
    }
    if ((this.validate.indexOf("START_TIME") != -1) && (!validateTimeFormat(this.startTime))) {
      return false;
    }
    if ((this.validate.indexOf("CLOSE_TIME") != -1) && (!validateTimeFormat(this.closeTime))) {
      return false;
    }
    if (this.startTime.compareTo(this.closeTime) >= 0)
    {
      this.error = 36006;
      return false;
    }
    if (!p(this.customerId))
    {
      ProcessingWindow localProcessingWindow = (ProcessingWindow)paramHttpSession.getAttribute("BankProcessingWindow");
      if (localProcessingWindow != null)
      {
        String str1 = localProcessingWindow.getStartTime();
        String str2 = localProcessingWindow.getCloseTime();
        if ((this.startTime.compareTo(str1) == 0) && (this.closeTime.compareTo(str2) == 0))
        {
          this.error = 36007;
          return false;
        }
        if ((this.startTime.compareTo(str1) < 0) || (this.closeTime.compareTo(str2) > 0))
        {
          this.error = 36008;
          return false;
        }
      }
    }
    this.validate = null;
    return true;
  }
  
  protected boolean validateTimeFormat(String paramString)
  {
    if ((paramString == null) || (paramString.length() != 5))
    {
      this.error = 36001;
      return false;
    }
    if ((paramString.indexOf(":") == 2) && (paramString.indexOf(":", 3) == -1))
    {
      String str1 = paramString.substring(0, 2);
      String str2 = paramString.substring(3, 5);
      int i;
      int j;
      try
      {
        i = Integer.parseInt(str1);
        j = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 36001;
        return false;
      }
      if ((i >= 0) && (i < 24) && (j >= 0) && (j < 60)) {
        return true;
      }
      this.error = 36001;
      return false;
    }
    this.error = 36001;
    return false;
  }
  
  public String getValidate()
  {
    return this.validate;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
  
  public String getBeanSessionName()
  {
    return this.beanSessionName;
  }
  
  private boolean p(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.ModifyProcessingWindow
 * JD-Core Version:    0.7.0.1
 */