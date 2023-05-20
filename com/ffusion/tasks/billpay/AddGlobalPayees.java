package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddGlobalPayees
  extends Payees
  implements Task
{
  protected int errorCode = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String alternateServiceName = null;
  protected String alternateServiceErrorURL = "SE";
  protected String payeeName;
  protected String validate;
  protected String required;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected Payee currentPayee;
  protected boolean showAll = false;
  private Boolean He = Boolean.FALSE;
  private boolean Hg = false;
  private String Hf = null;
  protected int successCnt = 0;
  protected String currentLanguage = "en_US";
  int Hh = 32;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.errorCode = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    synchronized (this)
    {
      if ((!this.He.booleanValue()) && (!this.Hg))
      {
        this.He = Boolean.TRUE;
        i = 1;
      }
    }
    if (i != 0)
    {
      try
      {
        if (localHttpSession.getAttribute(this.payeeName) == null)
        {
          this.errorCode = 2002;
          str = this.taskErrorURL;
        }
        else if (this.initFlag)
        {
          this.errorCode = initProcess(localHttpSession);
          if (this.errorCode == 0) {
            str = this.successURL;
          } else {
            str = this.taskErrorURL;
          }
        }
        else
        {
          this.errorCode = validateInput();
          if (this.errorCode == 0)
          {
            if (this.processFlag)
            {
              this.processFlag = false;
              removePayees();
              if (size() == 0)
              {
                this.errorCode = 2028;
                str = this.taskErrorURL;
              }
              else
              {
                this.errorCode = doProcess(localHttpSession);
                if (this.errorCode == 0)
                {
                  this.Hf = this.successURL;
                  this.Hg = true;
                }
                else
                {
                  str = this.serviceErrorURL;
                }
                if ((this.errorCode == 0) && (this.alternateServiceName != null))
                {
                  this.errorCode = doAlternateProcess(localHttpSession);
                  if (this.errorCode != 0) {
                    str = this.alternateServiceErrorURL;
                  }
                }
              }
            }
            else
            {
              str = this.successURL;
            }
          }
          else {
            str = this.taskErrorURL;
          }
        }
        this.Hf = str;
      }
      catch (Exception localException1)
      {
        this.errorCode = 1;
        this.Hf = this.serviceErrorURL;
      }
      finally
      {
        this.He = Boolean.FALSE;
      }
    }
    else
    {
      long l1 = System.currentTimeMillis();
      long l2 = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      while ((!this.Hg) && (this.He.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l1 > l2)
        {
          if (this.errorCode != 0) {
            break;
          }
          this.errorCode = 1;
          break;
        }
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException2) {}
      }
      str = this.Hf;
    }
    return str;
  }
  
  protected int initProcess(HttpSession paramHttpSession)
  {
    int i = 0;
    this.initFlag = false;
    clear();
    try
    {
      Payees localPayees1 = (Payees)paramHttpSession.getAttribute(this.payeeName);
      Payees localPayees2 = (Payees)paramHttpSession.getAttribute("Payees");
      Iterator localIterator = localPayees1.iterator();
      while (localIterator.hasNext())
      {
        Payee localPayee1 = (Payee)localIterator.next();
        String str = "and,HOSTID==" + localPayee1.getHostID() + "," + "STREET" + "==" + localPayee1.getStreet() + "," + "CITY" + "==" + localPayee1.getCity() + "," + "STATE" + "==" + localPayee1.getState() + "," + "COUNTRY" + "==" + localPayee1.getCountry() + "," + "ZIPCODE" + "==" + localPayee1.getZipCode();
        Payee localPayee2 = (Payee)localPayees2.getFirstByFilter(str);
        if (localPayee2 == null)
        {
          Payee localPayee3 = create();
          localPayee1.setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
          localPayee3.setSearchLanguage(localPayee1.getSearchLanguage());
          localPayee3.setCurrentLanguage(localPayee1.getCurrentLanguage());
          localPayee3.set(localPayee1);
          localPayee3.setNickName("");
        }
      }
    }
    catch (Exception localException)
    {
      i = 2008;
    }
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    int i = 0;
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Payees localPayees1 = new Payees();
    try
    {
      localPayees1 = Billpay.addPayees(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException);
    }
    Payees localPayees2 = (Payees)paramHttpSession.getAttribute("Payees");
    Iterator localIterator = localPayees1.iterator();
    while (localIterator.hasNext())
    {
      Payee localPayee1 = (Payee)localIterator.next();
      if (localPayee1.getErrorValue() == 0)
      {
        Payee localPayee2 = localPayees2.create();
        localPayee2.set(localPayee1);
        localPayee2.setCurrentLanguage(localSecureUser.getLocaleLanguage());
        this.successCnt += 1;
      }
    }
    localPayees2.setSortedBy("NAME");
    return i;
  }
  
  protected int doAlternateProcess(HttpSession paramHttpSession)
  {
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute(this.alternateServiceName);
    int i = 0;
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      Payees localPayees = Billpay.addPayees(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException);
    }
    return i;
  }
  
  protected int validateInput()
  {
    int i = 0;
    i = validateRequired();
    if ((i == 0) && (this.validate != null))
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Payee localPayee = (Payee)localIterator.next();
        String str1 = localPayee.getUserAccountNumber();
        String str2 = localPayee.getNickName();
        if (((str1 != null) && (str1.length() != 0)) || ((str2 != null) && (str2.length() != 0)))
        {
          if ((this.validate.indexOf("USERACCOUNTNUMBER") != -1) && ((str1 == null) || (str1.length() == 0) || (str1.length() > this.Hh))) {
            i = 2011;
          }
          if ((this.validate.indexOf("NICK_NAME") != -1) && ((str2 == null) || (str2.length() == 0))) {
            i = 34;
          }
          if (i != 0) {
            break;
          }
        }
      }
      this.validate = null;
    }
    return i;
  }
  
  protected void removePayees()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Payee localPayee = (Payee)localIterator.next();
      String str1 = localPayee.getUserAccountNumber();
      String str2 = localPayee.getNickName();
      if (((str1 == null) || (str1.length() == 0)) && ((str2 == null) || (str2.length() == 0))) {
        localIterator.remove();
      }
    }
  }
  
  protected int validateRequired()
  {
    int i = 0;
    if (this.required != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Payee localPayee = (Payee)localIterator.next();
        String str1 = localPayee.getUserAccountNumber();
        String str2 = localPayee.getNickName();
        if (((str1 != null) && (str1.length() != 0)) || ((str2 != null) && (str2.length() != 0)))
        {
          if ((this.required.indexOf("USERACCOUNTNUMBER") != -1) && ((str1 == null) || (str1.length() == 0))) {
            i = 2011;
          }
          if ((this.required.indexOf("NICK_NAME") != -1) && ((str2 == null) || (str2.length() == 0))) {
            i = 34;
          }
          if (i != 0) {
            break;
          }
        }
      }
      this.required = null;
    }
    return i;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setPayeeID(String paramString)
  {
    this.currentPayee = ((Payee)getFirstByFilter("ID=" + paramString + ",PREFERRED_LANG=" + this.currentLanguage + ",AND"));
  }
  
  public void setPayeeLanguage(String paramString)
  {
    this.currentLanguage = paramString;
  }
  
  public String getPayeeID()
  {
    return this.currentPayee.getID();
  }
  
  public void setUserAccountNumber(String paramString)
  {
    if (this.currentPayee != null) {
      this.currentPayee.setUserAccountNumber(paramString);
    }
  }
  
  public void setNickName(String paramString)
  {
    if (this.currentPayee != null) {
      this.currentPayee.setNickName(paramString);
    }
  }
  
  public String getNickName()
  {
    if (this.currentPayee != null) {
      return this.currentPayee.getNickName();
    }
    return null;
  }
  
  public String getSuccessCount()
  {
    return String.valueOf(this.successCnt);
  }
  
  public String getFailureCount()
  {
    int i = size() - this.successCnt;
    return String.valueOf(i);
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
    this.validate = null;
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setRequired(String paramString)
  {
    this.required = null;
    if (!"".equals(paramString)) {
      this.required = paramString.toUpperCase();
    }
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
    return String.valueOf(this.errorCode);
  }
  
  public void setMaxUserAccountNumberLength(String paramString)
  {
    this.Hh = Integer.valueOf(paramString).intValue();
  }
  
  public void setAlternateServiceName(String paramString)
  {
    this.alternateServiceName = paramString;
  }
  
  public void setAlternateServiceErrorURL(String paramString)
  {
    this.alternateServiceErrorURL = paramString;
  }
  
  public void setShowAll(String paramString)
  {
    this.showAll = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.AddGlobalPayees
 * JD-Core Version:    0.7.0.1
 */