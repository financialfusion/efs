package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.billpresentment.EBillAccount;
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

public class ModifyEBillAccount
  extends EBillAccount
  implements Task
{
  private static final String Ij = "MODIFYSTATUS";
  private int Ig;
  private boolean H9;
  private boolean Ik = false;
  private String Ie;
  private String Ib;
  private String Ic;
  private String Ih;
  private int Ia = 32;
  private boolean If;
  private String Ii = "ACTIVE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.Ib;
    this.Ig = 0;
    int i = modifyEBillAccount(localHttpSession);
    if (i == -1) {
      str = this.Ic;
    } else if (i == -2) {
      str = this.Ie;
    }
    return str;
  }
  
  public int modifyEBillAccount(HttpSession paramHttpSession)
  {
    int i = 0;
    if (this.H9)
    {
      if (init(paramHttpSession)) {
        i = 0;
      } else {
        i = -1;
      }
    }
    else if (this.Ik) {
      if (!validateInput(paramHttpSession))
      {
        i = -1;
      }
      else
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        this.Ig = 0;
        try
        {
          EBillAccount localEBillAccount1 = BillPresentment.modifyAccount(localSecureUser, this, this.If, localHashMap);
          set(localEBillAccount1);
        }
        catch (CSILException localCSILException)
        {
          this.Ig = MapError.mapError(localCSILException);
        }
        if (this.Ig == 0)
        {
          this.If = false;
          EBillAccount localEBillAccount2 = (EBillAccount)paramHttpSession.getAttribute("EBillAccount");
          localEBillAccount2.set(this);
        }
        else
        {
          i = -2;
        }
      }
    }
    return i;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    this.If = false;
    boolean bool = true;
    EBillAccount localEBillAccount = (EBillAccount)paramHttpSession.getAttribute("EBillAccount");
    if (localEBillAccount != null)
    {
      set(localEBillAccount);
      if (localEBillAccount.getLocale() != null) {
        setLocale(localEBillAccount.getLocale());
      } else {
        setLocale((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      }
      if (localEBillAccount.getDateFormat() != null) {
        setDateFormat(localEBillAccount.getDateFormat());
      } else {
        setDateFormat("MEDIUM");
      }
    }
    else
    {
      this.Ig = 6509;
      bool = false;
    }
    this.H9 = false;
    this.If = false;
    return bool;
  }
  
  public void setInitialize(String paramString)
  {
    this.H9 = paramString.trim().toLowerCase().equals("true");
  }
  
  public boolean validateInput(HttpSession paramHttpSession)
  {
    if (this.Ih != null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      String str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("StateNames").toUpperCase();
      if (this.Ih.indexOf("MODIFYSTATUS") != -1)
      {
        if ((this.Ii == null) || (this.Ii.indexOf(getStatusCode()) == -1)) {
          this.Ig = 6607;
        }
      }
      else if ((this.Ih.indexOf("ACCOUNTNUMBER") != -1) && ((getAccountNumber() == null) || (getAccountNumber().length() == 0) || (getAccountNumber().length() > this.Ia))) {
        this.Ig = 6608;
      } else if ((this.Ih.indexOf("ACCOUNTHOLDERSNAME") != -1) && ((getAccountHoldersName() == null) || (getAccountHoldersName().length() == 0))) {
        this.Ig = 6609;
      } else if ((this.Ih.indexOf("STATUSCODE") != -1) && ((getStatusCode() == null) || (getStatusCode().length() == 0))) {
        this.Ig = 6610;
      } else if ((this.Ih.indexOf("NICKNAME") != -1) && ((getNickName() == null) || (getNickName().length() == 0))) {
        this.Ig = 6611;
      } else if ((this.Ih.indexOf("STREET") != -1) && ((getStreet() == null) || (getStreet().length() == 0))) {
        this.Ig = 6612;
      } else if ((this.Ih.indexOf("STREET2") != -1) && ((getStreet2() == null) || (getStreet2().length() == 0))) {
        this.Ig = 6613;
      } else if ((this.Ih.indexOf("CITY") != -1) && ((getCity() == null) || (getCity().length() == 0))) {
        this.Ig = 6614;
      } else if ((this.Ih.indexOf("STATE") != -1) && ((getState() == null) || (getState().length() < 2))) {
        this.Ig = 6615;
      } else if ((this.Ih.indexOf("STATE") != -1) && (getState().length() == 2) && (str1.indexOf(getState().toUpperCase()) == -1)) {
        this.Ig = 6615;
      } else if ((this.Ih.indexOf("STATE") != -1) && (getState().length() > 2) && (str2.indexOf(getState().toUpperCase()) == -1)) {
        this.Ig = 6615;
      } else if ((this.Ih.indexOf("COUNTRY") != -1) && ((getCountry() == null) || (getCountry().length() == 0))) {
        this.Ig = 6616;
      } else if ((this.Ih.indexOf("ZIPCODE") != -1) && (getZipCodeValue() != null) && (!getZipCodeValue().isValid())) {
        this.Ig = 6617;
      } else if ((this.Ih.indexOf("ZIPCODE") != -1) && (getZipCodeValue() == null)) {
        this.Ig = 6617;
      } else if ((this.Ih.indexOf("PHONE") != -1) && (getPhoneValue() != null) && (!getPhoneValue().isValid())) {
        this.Ig = 6618;
      } else if ((this.Ih.indexOf("PHONE") != -1) && (getPhoneValue() == null)) {
        this.Ig = 6618;
      } else if ((this.Ih.indexOf("PHONE2") != -1) && (getPhone2Value() != null) && (!getPhone2Value().isValid())) {
        this.Ig = 6619;
      } else if ((this.Ih.indexOf("PHONE2") != -1) && (getPhone2Value() == null)) {
        this.Ig = 6619;
      } else if ((this.Ih.indexOf("EMAIL") != -1) && (getEmailValue() != null) && (!getEmailValue().isValid())) {
        this.Ig = 6620;
      } else if ((this.Ih.indexOf("EMAIL") != -1) && (getEmailValue() == null)) {
        this.Ig = 6620;
      } else {
        this.Ig = 0;
      }
      this.Ih = null;
    }
    return this.Ig == 0;
  }
  
  public final void setProcess(String paramString)
  {
    this.Ik = paramString.trim().toLowerCase().equals("true");
  }
  
  public final void setValidate(String paramString)
  {
    this.Ih = paramString;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Ib = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Ie = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Ic = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Ig);
  }
  
  public final void setError(String paramString)
  {
    this.Ig = Integer.parseInt(paramString);
  }
  
  public final void setError(int paramInt)
  {
    this.Ig = paramInt;
  }
  
  public final int getErrorValue()
  {
    return this.Ig;
  }
  
  public void setStreet(String paramString)
  {
    String str = getStreet();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setStreet(paramString);
  }
  
  public void setStreet2(String paramString)
  {
    String str = getStreet2();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setStreet2(paramString);
  }
  
  public void setCity(String paramString)
  {
    String str = getCity();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setCity(paramString);
  }
  
  public void setState(String paramString)
  {
    String str = getState();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setState(paramString);
  }
  
  public void setZipCode(String paramString)
  {
    String str = getZipCode();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setZipCode(paramString);
  }
  
  public void setPhone(String paramString)
  {
    String str = getPhone();
    if (str == null) {
      str = "";
    }
    if (paramString == null) {
      paramString = "";
    }
    if (str.trim().compareTo(paramString.trim()) != 0) {
      this.If = true;
    }
    super.setPhone(paramString);
  }
  
  public final void setValidModifyStatus(String paramString)
  {
    this.Ii = paramString;
  }
  
  public final String getProcess()
  {
    return String.valueOf(this.Ik);
  }
  
  public final boolean getProcessValue()
  {
    return this.Ik;
  }
  
  public final String getSuccessURL()
  {
    return this.Ib;
  }
  
  public final String getServiceErrorURL()
  {
    return this.Ie;
  }
  
  public final String getTaskErrorURL()
  {
    return this.Ic;
  }
  
  public void setMaxAccountNumberLength(String paramString)
  {
    this.Ia = Integer.valueOf(paramString).intValue();
  }
  
  public String getMaxAccountNumberLength()
  {
    return String.valueOf(this.Ia);
  }
  
  public int getMaxAccountNumberLengthValue()
  {
    return this.Ia;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyEBillAccount
 * JD-Core Version:    0.7.0.1
 */