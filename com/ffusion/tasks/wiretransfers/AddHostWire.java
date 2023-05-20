package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddHostWire
  extends ModifyHostWire
{
  protected static long timeoutValue = 120000L;
  protected boolean currentlyProcessing = false;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  
  public AddHostWire()
  {
    this.beanSessionName = "WireTransfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.initFlag == true)
    {
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      str = initialize(paramHttpServletRequest, localHttpSession);
    }
    else
    {
      str = addHostWire(localHttpSession);
    }
    return str;
  }
  
  protected String initialize(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    setLocale(this.locale);
    this.currentlyProcessing = false;
    this.haveProcessed = false;
    DateTime localDateTime = new DateTime(this.locale);
    localDateTime.setFormat(getDateFormat());
    setDueDate((DateTime)localDateTime.clone());
    return this.successURL;
  }
  
  protected String addHostWire(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    if (validateInput(paramHttpSession) == true)
    {
      if (this.processFlag == true)
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if ((!this.currentlyProcessing) && (!this.haveProcessed))
          {
            this.currentlyProcessing = true;
            i = 1;
          }
        }
        if (i == 1)
        {
          try
          {
            str = n(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = false;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while ((!this.haveProcessed) && (this.currentlyProcessing == true))
          {
            if (System.currentTimeMillis() - l > timeoutValue)
            {
              if (this.error != 0) {
                break;
              }
              this.error = 1;
              break;
            }
            try
            {
              Thread.sleep(2000L);
            }
            catch (Exception localException2) {}
          }
          str = this.nextURL;
        }
      }
    }
    else {
      return this.taskErrorURL;
    }
    return str;
  }
  
  private String n(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer1 = null;
    HashMap localHashMap = null;
    try
    {
      setBankID(localSecureUser.getBPWFIID());
      setCustomerID(localSecureUser.getBusinessID());
      setSubmittedBy(localSecureUser.getProfileID());
      setUserID(localSecureUser.getProfileID());
      setWireSource("HOST");
      setWireType("SINGLE");
      setWireDestination("HOST");
      localHashMap = new HashMap();
      localWireTransfer1 = Wire.addHostWire(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
      localWireTransfer2.set(localWireTransfer1);
      paramHttpSession.setAttribute(this.beanSessionName, localWireTransfer2);
      this.haveProcessed = true;
    }
    else
    {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    if (this.validate == null) {
      return true;
    }
    if ((this.validate.indexOf("HOSTID") != -1) && (isNullOrEmpty(getHostID()) == true))
    {
      this.error = 12037;
      return false;
    }
    if (this.validate.indexOf("AMOUNT") != -1)
    {
      Currency localCurrency = getAmountValue();
      if ((localCurrency == null) || (localCurrency.doubleValue() <= 0.0D))
      {
        this.error = 12008;
        return false;
      }
    }
    if (!validateDate(paramHttpSession, true)) {
      return false;
    }
    this.validate = null;
    return true;
  }
  
  public void setTimeOutValue(String paramString)
  {
    try
    {
      timeoutValue = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddHostWire
 * JD-Core Version:    0.7.0.1
 */