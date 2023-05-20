package com.ffusion.tasks.billpay;

import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Util;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditPayee
  extends Payee
  implements Task
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  private boolean HE = false;
  private Payee HC = null;
  int HD;
  
  public EditPayee()
  {
    this.error = 0;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Payees localPayees = (Payees)localHttpSession.getAttribute("Payees");
    String str = null;
    if (localPayees == null)
    {
      this.error = 2002;
      str = this.taskErrorURL;
    }
    else if (this.initFlag)
    {
      initProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else
    {
      try
      {
        this.error = validateInput(localHttpSession, localPayees);
      }
      catch (CSILException localCSILException)
      {
        MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          if (!this.HE)
          {
            this.HE = true;
            this.error = doProcess(localHttpSession, localPayees);
            if (this.error == 0) {
              str = this.successURL;
            } else {
              str = this.serviceErrorURL;
            }
            this.HE = false;
          }
          else
          {
            this.error = 2030;
            str = this.taskErrorURL;
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
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    Payee localPayee = (Payee)paramHttpSession.getAttribute("Payee");
    if (localPayee == null)
    {
      this.error = 2008;
    }
    else
    {
      set(localPayee);
      this.HC = localPayee;
    }
  }
  
  protected int validateInput(HttpSession paramHttpSession, Payees paramPayees)
    throws CSILException
  {
    int i = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (this.validate != null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      String str1 = localResourceBundle.getString("StateProvAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("StateProvNames").toUpperCase();
      Object localObject;
      if ((this.validate.indexOf("STREET") != -1) && ((this.street.length() == 0) || (this.street.length() > 32))) {
        i = 23;
      } else if ((this.street2 != null) && ((this.street2.length() == 0) || (this.street2.length() > 32))) {
        i = 52;
      } else if ((this.nickName != null) && ((this.nickName.length() == 0) || (this.nickName.length() > 32))) {
        i = 34;
      } else if ((this.validate.indexOf("CITY") != -1) && ((this.city.length() == 0) || (this.city.length() > 32))) {
        i = 24;
      } else if ((this.validate.indexOf("STATE") != -1) && (this.state.length() < 2)) {
        i = 25;
      } else if (this.validate.indexOf("STATE") != -1) {
        if (this.state.length() < 2)
        {
          i = 25;
        }
        else
        {
          localObject = Util.getStateProvinceDefnForState(localSecureUser, this.state, this.country, new HashMap());
          if (localObject == null) {
            i = 25;
          }
        }
      }
      if ((this.validate.indexOf("ZIPCODE") != -1) && ((this.zipCode == null) || (this.zipCode.toString().equals(""))))
      {
        if (this.zipCode.toString().equals("")) {
          this.zipCode = null;
        }
        this.validate = null;
        return 26;
      }
      if ((this.validate.indexOf("ZIPCODE") != -1) && (this.zipCode != null))
      {
        localObject = Util.validateZipCodeFormat(localSecureUser, this.country, this.zipCode.getString(), new HashMap());
        if (localObject == null)
        {
          this.validate = null;
          return 26;
        }
        if (!((String)localObject).equals("")) {
          this.zipCode.setFormat((String)localObject);
        }
      }
      if ((this.validate.indexOf("PHONE") != -1) && ((this.phone == null) || (this.phone.toString().equals(""))))
      {
        if (this.phone.toString().equals("")) {
          this.phone = null;
        }
        this.validate = null;
        return 27;
      }
      if ((this.validate.indexOf("PHONE") != -1) && (this.phone != null))
      {
        localObject = Util.validatePhoneFormat(localSecureUser, this.country, this.phone.getString(), new HashMap());
        if (localObject == null)
        {
          this.validate = null;
          return 27;
        }
        if (!((String)localObject).equals("")) {
          this.phone.setFormat((String)localObject);
        }
      }
      if (!this.noAccountNumber) {
        if (this.userAccountNumber != null)
        {
          if ((this.validate.indexOf("USERACCOUNTNUMBER") != -1) && ((this.userAccountNumber.length() == 0) || (this.userAccountNumber.length() > this.HD))) {
            i = 2011;
          }
        }
        else if (this.validate.indexOf("USERACCOUNTNUMBER") != -1) {
          i = 2011;
        }
      }
      this.validate = null;
    }
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession, Payees paramPayees)
  {
    int i = 0;
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      Payee localPayee1 = Billpay.modifyPayee(localSecureUser, this, this.HC, localHashMap);
      set(localPayee1);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException);
    }
    if (i == 0)
    {
      Payee localPayee2 = (Payee)paramHttpSession.getAttribute("Payee");
      if (localPayee2 != null) {
        localPayee2.set(this);
      }
    }
    return i;
  }
  
  public void setMaxUserAccountNumberLength(String paramString)
  {
    this.HD = Integer.valueOf(paramString).intValue();
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
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
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
  
  public String getStreet3()
  {
    if (this.street3 != null) {
      if (this.street3.trim().equals("")) {
        this.street3 = null;
      } else {
        this.street3 = this.street3.trim();
      }
    }
    return this.street3;
  }
  
  public String getStreet2()
  {
    if (this.street2 != null) {
      if (this.street2.trim().equals("")) {
        this.street2 = null;
      } else {
        this.street2 = this.street2.trim();
      }
    }
    return this.street2;
  }
  
  public void setStreet3(String paramString)
  {
    if (paramString != null) {
      if (paramString.trim().equals("")) {
        paramString = null;
      } else {
        paramString = paramString.trim();
      }
    }
    this.street3 = paramString;
  }
  
  public void setStreet2(String paramString)
  {
    if (paramString != null) {
      if (paramString.trim().equals("")) {
        paramString = null;
      } else {
        paramString = paramString.trim();
      }
    }
    this.street2 = paramString;
  }
  
  public void setUserAccountNumber(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.trim().equals(""))
      {
        this.userAccountNumber = null;
        this.noAccountNumber = true;
      }
      else
      {
        this.userAccountNumber = paramString.trim();
        this.noAccountNumber = false;
      }
    }
    else
    {
      this.userAccountNumber = null;
      this.noAccountNumber = true;
    }
  }
  
  public void setAccountType(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    this.payeeRoute.setAcctType(paramString);
    setPayeeRoute(this.payeeRoute);
  }
  
  public String getAccountType()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return getPayeeRoute().getAcctType();
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    this.payeeRoute.setCurrencyCode(paramString);
    setPayeeRoute(this.payeeRoute);
  }
  
  public String getCurrencyCode()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return getPayeeRoute().getCurrencyCode();
  }
  
  public void setNickName(String paramString)
  {
    if (paramString != null) {
      if (paramString.trim().equals("")) {
        this.nickName = null;
      } else {
        this.nickName = paramString.trim();
      }
    }
  }
  
  public String getNickName()
  {
    if ((this.nickName == null) || (this.nickName.trim().equals(""))) {
      return this.name;
    }
    return this.nickName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.EditPayee
 * JD-Core Version:    0.7.0.1
 */