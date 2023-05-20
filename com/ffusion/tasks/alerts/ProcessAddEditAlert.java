package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessAddEditAlert
  extends ModifyAlert
{
  protected String checkPositivePay;
  protected String checkAccountBalance;
  protected String checkStockPortfolio;
  protected String checkPaymentApprovals;
  protected String checkBankMessages;
  protected String checkNSF;
  protected String checkTransaction;
  protected int alertType;
  private String ld = "alert@bank.com";
  
  public int getAlertType(String paramString)
  {
    int i = 0;
    if (paramString.equals("AccountBalance")) {
      i = 1;
    } else if (paramString.equals("StockPortfolio")) {
      i = 2;
    } else if (paramString.equals("ProcessStockPortfolio")) {
      i = 7;
    } else if (paramString.equals("NSF")) {
      i = 3;
    } else if (paramString.equals("PositivePay")) {
      i = 6;
    } else if (paramString.equals("PaymentApprovals")) {
      i = 5;
    } else if (paramString.equals("BankMessage")) {
      i = 0;
    } else if (paramString.equals("Transaction")) {
      i = 4;
    }
    return i;
  }
  
  public int getAlertType(Alert paramAlert)
  {
    paramAlert.setCurrentDeliveryInfo("1");
    paramAlert.setDeliveryInfoPropertyKey("AlertType");
    return getAlertType(paramAlert.getDeliveryInfoPropertyValue());
  }
  
  public void setCheckPaymentApprovals(String paramString)
  {
    this.checkPaymentApprovals = paramString;
  }
  
  public String getCheckPaymentApprovals()
  {
    return this.checkPaymentApprovals;
  }
  
  public void setCheckPositivePay(String paramString)
  {
    this.checkPositivePay = paramString;
  }
  
  public String getCheckPositivePay()
  {
    return this.checkPositivePay;
  }
  
  public void setCheckAccountBalance(String paramString)
  {
    this.checkAccountBalance = paramString;
  }
  
  public String getCheckAccountBalance()
  {
    return this.checkAccountBalance;
  }
  
  public void setCheckStockPortfolio(String paramString)
  {
    this.checkStockPortfolio = paramString;
  }
  
  public String getCheckStockPortfolio()
  {
    return this.checkStockPortfolio;
  }
  
  public void setCheckBankMessages(String paramString)
  {
    this.checkBankMessages = paramString;
  }
  
  public String getCheckBankMessages()
  {
    return this.checkBankMessages;
  }
  
  public void setCheckNSF(String paramString)
  {
    this.checkNSF = paramString;
  }
  
  public String getCheckNSF()
  {
    return this.checkNSF;
  }
  
  public void setCheckTransaction(String paramString)
  {
    this.checkTransaction = paramString;
  }
  
  public String getCheckTransaction()
  {
    return this.checkTransaction;
  }
  
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
      str = processAddEditAlert(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    if ((this.alertName == null) || (this.alertName.trim().length() == 0)) {
      this.alertName = "Alert";
    }
    return this.error == 0;
  }
  
  protected boolean initDeliveryInfos(HttpSession paramHttpSession)
  {
    int i = Integer.parseInt((String)paramHttpSession.getAttribute("SecureAlert"));
    int j = Integer.parseInt((String)paramHttpSession.getAttribute("DeliveryInfo1"));
    int k = Integer.parseInt((String)paramHttpSession.getAttribute("DeliveryInfo2"));
    String str1 = (String)paramHttpSession.getAttribute("DeliveryInfo1To");
    String str2 = (String)paramHttpSession.getAttribute("DeliveryInfo2To");
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    User localUser = (User)paramHttpSession.getAttribute("User");
    if ((i == 0) && (j == 0) && (k == 0))
    {
      this.error = 19004;
      return false;
    }
    int m = 0;
    DeliveryInfos localDeliveryInfos = new DeliveryInfos(this.locale);
    DeliveryInfo localDeliveryInfo1 = new DeliveryInfo(this.locale);
    localDeliveryInfo1.setID("1");
    localDeliveryInfos.add(localDeliveryInfo1);
    localDeliveryInfo1.setChannelName("ToBeSet");
    Properties localProperties1 = new Properties();
    localProperties1.setProperty("SECUREUSER", localSecureUser.getXML());
    localProperties1.setProperty("PREFERREDLANGUAGE", localUser.getPreferredLanguage());
    localDeliveryInfo1.setProperties(localProperties1);
    m++;
    for (int n = 1; n <= 3; n++)
    {
      DeliveryInfo localDeliveryInfo2;
      Properties localProperties2;
      if (n == i)
      {
        m++;
        localDeliveryInfo2 = new DeliveryInfo(this.locale);
        localDeliveryInfo2.setID("2");
        localDeliveryInfos.add(localDeliveryInfo2);
        localDeliveryInfo2.setOrder(String.valueOf(i));
        localDeliveryInfo2.setChannelName("MessageCenter");
        localDeliveryInfo2.setMaxDelay(-1);
        localDeliveryInfo2.setSuspended(false);
        localProperties2 = new Properties();
        localProperties2.setProperty("SECUREUSER", localSecureUser.getXML());
        localProperties2.setProperty("PREFERREDLANGUAGE", localUser.getPreferredLanguage());
        localProperties2.setProperty("secure", Boolean.TRUE.toString());
        localDeliveryInfo2.setProperties(localProperties2);
      }
      if (n == j)
      {
        m++;
        localDeliveryInfo2 = new DeliveryInfo(this.locale);
        localDeliveryInfo2.setID("3");
        localDeliveryInfos.add(localDeliveryInfo2);
        localDeliveryInfo2.setChannelName("Email");
        localDeliveryInfo2.setOrder(j);
        localDeliveryInfo2.setMaxDelay(-1);
        localDeliveryInfo2.setSuspended(false);
        localProperties2 = new Properties();
        localProperties2.setProperty("to", str1);
        localProperties2.setProperty("from", getSendAddress());
        localDeliveryInfo2.setProperties(localProperties2);
      }
      if (n == k)
      {
        m++;
        localDeliveryInfo2 = new DeliveryInfo(this.locale);
        localDeliveryInfo2.setID("4");
        localDeliveryInfos.add(localDeliveryInfo2);
        localDeliveryInfo2.setChannelName("Email");
        localDeliveryInfo2.setOrder(k);
        localDeliveryInfo2.setMaxDelay(-1);
        localDeliveryInfo2.setSuspended(false);
        localProperties2 = new Properties();
        localProperties2.setProperty("to", str2);
        localProperties2.setProperty("from", getSendAddress());
        localDeliveryInfo2.setProperties(localProperties2);
      }
    }
    setDeliveryInfos(localDeliveryInfos);
    return true;
  }
  
  protected String processAddEditAlert(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag) {
        str = addEditAlert(paramHttpServletRequest, paramHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String addEditAlert(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    try
    {
      if (((this.checkAccountBalance == null) || (!this.checkAccountBalance.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 1) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 1);
      }
      if (((this.checkStockPortfolio == null) || (!this.checkStockPortfolio.equals("checked"))) && ((getSizeByAlertType(paramHttpSession, 2) > 0) || (getSizeByAlertType(paramHttpSession, 7) > 0)))
      {
        this.error = deleteAlerts(paramHttpSession, 7);
        this.error = deleteAlerts(paramHttpSession, 2);
      }
      if (((this.checkTransaction == null) || (!this.checkTransaction.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 4) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 4);
      }
      if ((this.checkNSF != null) && (this.checkNSF.equals("checked")))
      {
        if (getSizeByAlertType(paramHttpSession, 3) <= 0) {
          this.error = addAlert(paramHttpSession, "BalanceCheck", "NSF", 3);
        }
      }
      else if (((this.checkNSF == null) || (!this.checkNSF.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 3) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 3);
      }
      if ((this.checkBankMessages != null) && (this.checkBankMessages.equals("checked")))
      {
        if (getSizeByAlertType(paramHttpSession, 0) <= 0) {
          this.error = addAlert(paramHttpSession, "BankMessage", "BankMessage", 0);
        }
      }
      else if (((this.checkBankMessages == null) || (!this.checkBankMessages.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 0) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 0);
      }
      if ((this.checkPositivePay != null) && (this.checkPositivePay.equals("checked")))
      {
        if (getSizeByAlertType(paramHttpSession, 6) <= 0) {
          this.error = addAlert(paramHttpSession, "PendingCheck", "PositivePay", 6);
        }
      }
      else if (((this.checkPositivePay == null) || (!this.checkPositivePay.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 6) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 6);
      }
      if ((this.checkPaymentApprovals != null) && (this.checkPaymentApprovals.equals("checked")))
      {
        if (getSizeByAlertType(paramHttpSession, 5) <= 0) {
          this.error = addAlert(paramHttpSession, "PendingCheck", "PaymentApprovals", 5);
        }
      }
      else if (((this.checkPaymentApprovals == null) || (!this.checkPaymentApprovals.equals("checked"))) && (getSizeByAlertType(paramHttpSession, 5) > 0)) {
        this.error = deleteAlerts(paramHttpSession, 5);
      }
      if (this.error != 0) {
        if ((this.error >= 19000) && (this.error <= 19010)) {
          str = this.taskErrorURL;
        } else {
          str = this.serviceErrorURL;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected int addAlert(HttpSession paramHttpSession, String paramString1, String paramString2, int paramInt)
    throws CSILException
  {
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    HashMap localHashMap = null;
    if (localAlerts != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localAlerts);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    Alert localAlert1 = null;
    Alert localAlert2 = null;
    localAlert2 = new Alert();
    localAlert2.setPriority(1);
    localAlert2.setDateFormat("MM/dd/yyyy");
    localAlert2.setEndDate("01/01/2100");
    localAlert2.setInterval("86400000");
    localAlert2.setTimeZone("EST");
    localAlert2.setDateFormat("MM/dd/yyyy/H:mm");
    String str = DateFormatUtil.getFormatter("MM/dd/yyyy").format(new Date());
    localAlert2.setStartDate("01/01/2099/14:01");
    localAlert2.setAppInfo("ApplicationAlerts");
    localAlert2.setMessage(Task.MESSAGES[getAlertType(paramString2)] + " " + str + ":");
    localAlert2.setType(2);
    if (!initDeliveryInfos(paramHttpSession)) {
      return this.error;
    }
    for (int i = 0; i < this.deliveryInfos.size(); i++)
    {
      localObject = (DeliveryInfo)this.deliveryInfos.get(i);
      Properties localProperties = ((DeliveryInfo)localObject).getPropertiesValue();
      localProperties.setProperty("AlertType", paramString2);
      localProperties.setProperty("subject", Task.SUBJECTS[getAlertType(paramString2)]);
      if (((DeliveryInfo)localObject).getChannelName().equals("ToBeSet")) {
        ((DeliveryInfo)localObject).setChannelName(paramString1);
      } else {
        ((DeliveryInfo)localObject).setMaxDelay("14400000");
      }
      ((DeliveryInfo)localObject).setProperties(localProperties);
    }
    localAlert2.setDeliveryInfos(this.deliveryInfos);
    try
    {
      localAlert1 = com.ffusion.csil.core.Alerts.addAlert(localSecureUser, localAlert2, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
    Alert localAlert3 = new Alert(this.locale);
    localAlert3.set(localAlert1);
    Object localObject = (com.ffusion.beans.alerts.Alerts)paramHttpSession.getAttribute(this.alertsName);
    ((com.ffusion.beans.alerts.Alerts)localObject).add(localAlert3);
    paramHttpSession.setAttribute(this.alertName, localAlert1);
    this.error = 0;
    return this.error;
  }
  
  public int deleteAlerts(HttpSession paramHttpSession, int paramInt)
    throws Exception
  {
    String str = null;
    if ((this.serviceName == null) || (this.serviceName.trim().length() == 0)) {
      this.serviceName = "com.ffusion.services.Alerts";
    }
    com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHttpSession.getAttribute(this.serviceName);
    if ((this.alertsName == null) || (this.alertsName.trim().length() == 0)) {
      this.alertsName = "Alerts";
    }
    com.ffusion.beans.alerts.Alerts localAlerts1 = (com.ffusion.beans.alerts.Alerts)paramHttpSession.getAttribute(this.alertsName);
    Iterator localIterator = localAlerts1.iterator();
    while (localIterator.hasNext())
    {
      Alert localAlert1 = (Alert)localIterator.next();
      if (getAlertType(localAlert1) == paramInt)
      {
        HashMap localHashMap = null;
        if (localAlerts != null)
        {
          localHashMap = new HashMap();
          localHashMap.put("SERVICE", localAlerts);
        }
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        this.error = 0;
        Alert localAlert2 = com.ffusion.csil.core.Alerts.deleteAlert(localSecureUser, localAlert1, localHashMap);
        if ((this.error == 0) && (localAlerts1 == null))
        {
          this.error = 19002;
          str = this.taskErrorURL;
        }
      }
    }
    this.error = 0;
    return this.error;
  }
  
  protected int getSizeByAlertType(HttpSession paramHttpSession, int paramInt)
  {
    int i = 0;
    com.ffusion.beans.alerts.Alerts localAlerts = (com.ffusion.beans.alerts.Alerts)paramHttpSession.getAttribute(this.alertsName);
    Iterator localIterator = localAlerts.iterator();
    while (localIterator.hasNext())
    {
      Alert localAlert = (Alert)localIterator.next();
      if (getAlertType(localAlert) == paramInt) {
        i++;
      }
    }
    return i;
  }
  
  public String getSendAddress()
  {
    return this.ld;
  }
  
  public void setSendAddress(String paramString)
  {
    this.ld = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.ProcessAddEditAlert
 * JD-Core Version:    0.7.0.1
 */