package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
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

public class ModifyConsumer
  extends Consumer
  implements Task
{
  private boolean ow = true;
  private boolean ov = true;
  private String oz;
  private String oy;
  private String ot;
  private String ox;
  private int ou;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    String str = null;
    if (this.ow) {
      bool = init(localHttpSession);
    }
    if (bool)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.ou = 0;
      try
      {
        Consumer localConsumer1 = BillPresentment.modifyConsumer(localSecureUser, this, localHashMap);
        set(localConsumer1);
      }
      catch (CSILException localCSILException)
      {
        this.ou = MapError.mapError(localCSILException);
      }
      if (this.ou == 0)
      {
        str = this.ox;
        Consumer localConsumer2 = new Consumer((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localConsumer2.set(this);
        localConsumer2.setLocale(getLocale());
        localHttpSession.setAttribute("Consumer", localConsumer2);
      }
      else
      {
        str = this.ot;
      }
    }
    else
    {
      str = this.oy;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    Consumer localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
    if (localConsumer == null)
    {
      this.ou = 6506;
      return false;
    }
    set(localConsumer);
    setLocale(localConsumer.getLocale());
    return true;
  }
  
  public final void setInitialize(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.ow = true;
    } else {
      this.ow = false;
    }
  }
  
  public final void setProcess(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.ov = true;
    } else {
      this.ov = false;
    }
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.oz = paramString.toUpperCase();
    } else {
      this.oz = null;
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.oz != null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      String str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("StateNames").toUpperCase();
      if ((this.oz.indexOf("STATUSCODE") != -1) && ((getStatusCode() == null) || (getStatusCode().length() == 0))) {
        this.ou = 6610;
      } else if ((this.oz.indexOf("STREET") != -1) && ((getStreet() == null) || (getStreet().length() == 0))) {
        this.ou = 6612;
      } else if ((this.oz.indexOf("STREET2") != -1) && ((getStreet2() == null) || (getStreet2().length() == 0))) {
        this.ou = 6613;
      } else if ((this.oz.indexOf("CITY") != -1) && ((getCity() == null) || (getCity().length() == 0))) {
        this.ou = 6614;
      } else if ((this.oz.indexOf("STATE") != -1) && ((getState() == null) || (getState().length() < 2))) {
        this.ou = 6615;
      } else if ((this.oz.indexOf("STATE") != -1) && (getState().length() == 2) && (str1.indexOf(getState().toUpperCase()) == -1)) {
        this.ou = 6615;
      } else if ((this.oz.indexOf("STATE") != -1) && (getState().length() > 2) && (str2.indexOf(getState().toUpperCase()) == -1)) {
        this.ou = 6615;
      } else if ((this.oz.indexOf("COUNTRY") != -1) && ((getCountry() == null) || (getCountry().length() == 0))) {
        this.ou = 6616;
      } else if ((this.oz.indexOf("ZIPCODE") != -1) && (getZipCodeValue() != null) && (!getZipCodeValue().isValid())) {
        this.ou = 6617;
      } else if ((this.oz.indexOf("ZIPCODE") != -1) && (getZipCodeValue() == null)) {
        this.ou = 6617;
      } else if ((this.oz.indexOf("PHONE") != -1) && (getPhoneValue() != null) && (!getPhoneValue().isValid())) {
        this.ou = 6618;
      } else if ((this.oz.indexOf("PHONE") != -1) && (getPhoneValue() == null)) {
        this.ou = 6618;
      } else if ((this.oz.indexOf("PHONE2") != -1) && (getPhone2Value() != null) && (!getPhone2Value().isValid())) {
        this.ou = 6619;
      } else if ((this.oz.indexOf("PHONE2") != -1) && (getPhone2Value() == null)) {
        this.ou = 6619;
      } else if ((this.oz.indexOf("EMAIL") != -1) && (getEmailValue() != null) && (!getEmailValue().isValid())) {
        this.ou = 6620;
      } else if ((this.oz.indexOf("EMAIL") != -1) && (getEmailValue() == null)) {
        this.ou = 6620;
      } else {
        this.ou = 0;
      }
      this.oz = null;
    }
    return bool;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.ox = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.ot = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.oy = paramString;
  }
  
  public final void setError(String paramString)
  {
    this.ou = Integer.parseInt(paramString);
  }
  
  public final void setError(int paramInt)
  {
    this.ou = paramInt;
  }
  
  public final String getError()
  {
    return String.valueOf(this.ou);
  }
  
  public final int getErrorValue()
  {
    return this.ou;
  }
  
  public final String getSuccessURL()
  {
    return this.ox;
  }
  
  public final String getServiceErrorURL()
  {
    return this.ot;
  }
  
  public final String getTaskErrorURL()
  {
    return this.oy;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyConsumer
 * JD-Core Version:    0.7.0.1
 */