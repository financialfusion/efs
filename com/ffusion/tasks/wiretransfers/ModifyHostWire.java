package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyHostWire
  extends WireTransfer
  implements WireTaskDefines
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String beanSessionName = null;
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean dateChanged = false;
  protected boolean nonBusinessDay = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = this.successURL;
    if (this.initFlag == true) {
      str = initialize(localHttpSession);
    } else {
      str = modifyWireTransfer(localHttpSession);
    }
    return str;
  }
  
  protected String modifyWireTransfer(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    if (validateInput(paramHttpSession) == true)
    {
      if (this.processFlag == true)
      {
        this.processFlag = false;
        WireTransfer localWireTransfer1 = (WireTransfer)paramHttpSession.getAttribute("OldWireTransfer");
        if (localWireTransfer1 == null)
        {
          this.error = 12006;
          return this.taskErrorURL;
        }
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        if (localSecureUser == null)
        {
          this.error = 12014;
          return this.taskErrorURL;
        }
        HashMap localHashMap = new HashMap();
        try
        {
          Wire.modifyHostWire(localSecureUser, this, localWireTransfer1, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
            paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
          }
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
          localWireTransfer2.set(this);
          paramHttpSession.setAttribute(this.beanSessionName, localWireTransfer2);
          paramHttpSession.removeAttribute("OldWireTransfer");
        }
      }
    }
    else {
      return this.taskErrorURL;
    }
    return str;
  }
  
  protected String initialize(HttpSession paramHttpSession)
  {
    WireTransfer localWireTransfer1 = (WireTransfer)paramHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfer1 == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    set(localWireTransfer1);
    WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
    localWireTransfer2.set(localWireTransfer1);
    paramHttpSession.setAttribute("OldWireTransfer", localWireTransfer2);
    this.initFlag = false;
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    if (this.validate == null) {
      return true;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
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
    if (!validateDate(paramHttpSession, false)) {
      return false;
    }
    this.validate = null;
    return true;
  }
  
  public boolean validateDate(HttpSession paramHttpSession, boolean paramBoolean)
  {
    this.dateChanged = false;
    this.nonBusinessDay = false;
    if (isNullOrEmpty(getDueDate()) == true)
    {
      this.error = 12035;
      return false;
    }
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    DateTime localDateTime1 = new DateTime(localLocale);
    try
    {
      DateTime localDateTime2 = getDueDateValue();
      DateTime localDateTime3 = new DateTime(localLocale);
      localDateTime3.fromString(getDueDate());
      localDateTime3 = (DateTime)localDateTime2.clone();
      localDateTime3.set(1, localDateTime1.get(1));
      localDateTime3.set(2, localDateTime1.get(2));
      localDateTime3.set(5, localDateTime1.get(5));
      if ((paramBoolean == true) && (localDateTime2.before(localDateTime3) == true))
      {
        this.error = 12042;
        return false;
      }
      if ((localDateTime2.equals(localDateTime3)) || (localDateTime2.before(localDateTime3)))
      {
        localObject1 = new ProcessingWindow();
        ((ProcessingWindow)localObject1).setBankId(localSecureUser.getBPWFIID());
        ((ProcessingWindow)localObject1).setCustomerId(localSecureUser.getBusinessID());
        ((ProcessingWindow)localObject1).setPaymentType("WIRES");
        ((ProcessingWindow)localObject1).setPaymentSubType("HOST");
        localObject2 = null;
        try
        {
          localObject2 = PaymentsAdmin.getProcessingWindows(localSecureUser, (ProcessingWindow)localObject1, null);
          if (((ProcessingWindows)localObject2).size() == 0)
          {
            ((ProcessingWindow)localObject1).setCustomerId(null);
            localObject2 = PaymentsAdmin.getProcessingWindows(localSecureUser, (ProcessingWindow)localObject1, null);
          }
          if (((ProcessingWindows)localObject2).size() > 0)
          {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
            String str = localSimpleDateFormat.format(new Date());
            int i = 0;
            for (int j = 0; j < ((ProcessingWindows)localObject2).size(); j++)
            {
              ProcessingWindow localProcessingWindow = (ProcessingWindow)((ProcessingWindows)localObject2).get(j);
              if (str.compareTo(localProcessingWindow.getCloseTime()) <= 0)
              {
                i = 1;
                break;
              }
            }
            if (i == 0)
            {
              localDateTime2.add(5, 1);
              this.dateChanged = true;
            }
          }
        }
        catch (CSILException localCSILException2)
        {
          DebugLog.log("WARNING: Unable to validate DueDate is within Processing Window");
        }
      }
      Object localObject1 = Wire.getSmartDate(localSecureUser, localDateTime2);
      Object localObject2 = new DateTime((Date)localObject1, localLocale);
      setDateToPost((DateTime)localObject2);
      localDateTime3 = (DateTime)((DateTime)localObject2).clone();
      localDateTime3.set(1, localDateTime2.get(1));
      localDateTime3.set(2, localDateTime2.get(2));
      localDateTime3.set(5, localDateTime2.get(5));
      if (((DateTime)localObject2).after(localDateTime3)) {
        this.nonBusinessDay = true;
      }
      if (!isNullOrEmpty(getSettlementDate()))
      {
        localDateTime3 = new DateTime(localLocale);
        localDateTime3.fromString(getSettlementDate());
        localDateTime3 = (DateTime)getSettlementDateValue().clone();
        localDateTime3.set(1, ((DateTime)localObject2).get(1));
        localDateTime3.set(2, ((DateTime)localObject2).get(2));
        localDateTime3.set(5, ((DateTime)localObject2).get(5));
        if (getSettlementDateValue().before(localDateTime3) == true)
        {
          DebugLog.log("HostWire: The SettlementDate occurs before DateToPost, so changing SettlementDate to DateToPost.");
          setSettlementDate((DateTime)localObject2);
        }
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.error = 12035;
      return false;
    }
    catch (CSILException localCSILException1) {}
    return true;
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
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = paramString;
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
  
  public String getDateChanged()
  {
    if (this.dateChanged == true) {
      return "true";
    }
    return "false";
  }
  
  public String getNonBusinessDay()
  {
    if (this.nonBusinessDay == true) {
      return "true";
    }
    return "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.ModifyHostWire
 * JD-Core Version:    0.7.0.1
 */