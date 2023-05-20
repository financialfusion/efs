package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddGlobalPayee
  extends Payee
  implements GlobalPayeeTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL = null;
  protected String fulfillmentSystemName;
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (validateInput(localHttpSession))
      {
        PaymentsAdmin.addGlobalPayee(localSecureUser, this, localHashMap);
        if (this.error == 0) {
          str = this.successURL;
        }
      }
      else
      {
        str = this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((getName() == null) || (getName().trim().equals("")))
    {
      this.error = 100400;
      return false;
    }
    if ((getPayeeGroup() == null) || (getPayeeGroup().trim().equals("")))
    {
      this.error = 100401;
      return false;
    }
    if ((getPayeeStreet() == null) || (getPayeeStreet().trim().equals("")))
    {
      this.error = 100402;
      return false;
    }
    if ((getPayeeCity() == null) || (getPayeeCity().trim().equals("")))
    {
      this.error = 100403;
      return false;
    }
    if ((getPayeeZipCode() == null) || (getPayeeZipCode().trim().equals("")))
    {
      this.error = 100404;
      return false;
    }
    if (!jdMethod_int(localSecureUser, getPayeeZipCode(), getPayeeCountry()))
    {
      this.error = 26;
      return false;
    }
    if (!jdMethod_for(localSecureUser, getPhone(), getPayeeCountry()))
    {
      this.error = 27;
      return false;
    }
    if (this.fulfillmentSystemName.equalsIgnoreCase("ON_US"))
    {
      if ((getAffiliateRoutingNumber() == null) || (getAffiliateRoutingNumber().trim().equals("")))
      {
        if (!CommBankIdentifier.getBankIdentifierFlag()) {
          this.error = 100410;
        } else {
          this.error = 100405;
        }
        return false;
      }
      if ((getAccountNumber() == null) || (getAccountNumber().trim().equals("")))
      {
        this.error = 100406;
        return false;
      }
    }
    boolean bool = false;
    if ((getAffiliateRoutingNumber() != null) && (!getAffiliateRoutingNumber().trim().equals("")))
    {
      try
      {
        bool = CommBankIdentifier.isValidCheckZeros(getAffiliateRoutingNumber());
      }
      catch (Exception localException)
      {
        bool = false;
      }
      if (!bool)
      {
        if (!CommBankIdentifier.getBankIdentifierFlag()) {
          this.error = 100407;
        } else {
          this.error = 100409;
        }
        return false;
      }
    }
    return true;
  }
  
  public void setRouteID(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    this.payeeRoute.setRouteID(paramString);
    setPayeeRoute(this.payeeRoute);
  }
  
  public String getRouteID()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return getPayeeRoute().getRouteID();
  }
  
  public void setAccountNumber(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    this.payeeRoute.setAccountID(paramString);
    setPayeeRoute(this.payeeRoute);
  }
  
  public String getAccountNumber()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return getPayeeRoute().getAccountID();
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
  
  public void setCustAcctRequired(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    if (paramString.equalsIgnoreCase("true")) {
      this.payeeRoute.setCustAcctRequired(true);
    } else {
      this.payeeRoute.setCustAcctRequired(false);
    }
    setPayeeRoute(this.payeeRoute);
  }
  
  public String getCustAcctRequired()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return String.valueOf(getPayeeRoute().isCustAcctRequired());
  }
  
  public void setPayeeGroup(String paramString)
  {
    put("IDScope", paramString);
  }
  
  public String getPayeeGroup()
  {
    return (String)get("IDScope");
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
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
  
  public String getFulfillmentSystemName()
  {
    return this.fulfillmentSystemName;
  }
  
  public void setFulfillmentSystemName(String paramString)
  {
    this.fulfillmentSystemName = paramString;
  }
  
  public String getFIID()
  {
    return getFiID();
  }
  
  public void setFIID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      setFiID(null);
    } else {
      setFiID(paramString);
    }
  }
  
  public String getAffiliateRoutingNumber()
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    return getPayeeRoute().getBankIdentifier();
  }
  
  public void setAffiliateRoutingNumber(String paramString)
  {
    this.payeeRoute = getPayeeRoute();
    if (this.payeeRoute == null) {
      this.payeeRoute = new PayeeRoute();
    }
    this.payeeRoute.setBankIdentifier(paramString);
    setPayeeRoute(this.payeeRoute);
  }
  
  public void setPayeeCity(String paramString)
  {
    super.setCity(paramString);
  }
  
  public String getPayeeCity()
  {
    return super.getCity();
  }
  
  public void setPayeeStreet(String paramString)
  {
    super.setStreet(paramString);
  }
  
  public String getPayeeStreet()
  {
    return super.getStreet();
  }
  
  public void setPayeeStreet2(String paramString)
  {
    super.setStreet2(paramString);
  }
  
  public String getPayeeStreet2()
  {
    return super.getStreet2();
  }
  
  public void setPayeeStreet3(String paramString)
  {
    super.setStreet3(paramString);
  }
  
  public String getPayeeStreet3()
  {
    return super.getStreet3();
  }
  
  public void setPayeeState(String paramString)
  {
    super.setState(paramString);
  }
  
  public String getPayeeState()
  {
    return super.getState();
  }
  
  public void setPayeeCountry(String paramString)
  {
    super.setCountry(paramString);
  }
  
  public String getPayeeCountry()
  {
    return super.getCountry();
  }
  
  public void setPayeeZipCode(String paramString)
  {
    super.setZipCode(paramString);
  }
  
  public String getPayeeZipCode()
  {
    return super.getZipCode();
  }
  
  public String getAffiliateBankName()
  {
    return null;
  }
  
  public void setCity(String paramString) {}
  
  public void setStreet(String paramString) {}
  
  public void setStreet2(String paramString) {}
  
  public void setStreet3(String paramString) {}
  
  public void setState(String paramString) {}
  
  public void setCountry(String paramString) {}
  
  public void setZipCode(String paramString) {}
  
  private boolean jdMethod_for(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return true;
    }
    if (paramString1.length() > 20) {
      return false;
    }
    if ((this.country != null) && (this.country.length() > 0))
    {
      HashMap localHashMap = new HashMap();
      if ((null != paramString1) && (paramString1.length() > 0))
      {
        String str = Util.validatePhoneFormat(paramSecureUser, paramString2, paramString1, localHashMap);
        if (str == null) {
          return false;
        }
        if (!str.equals("")) {
          getPhoneValue().setFormat(str);
        }
      }
    }
    return true;
  }
  
  private boolean jdMethod_int(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((this.country != null) && (this.country.length() > 0))
    {
      HashMap localHashMap = new HashMap();
      if ((null != paramString1) && (paramString1.length() > 0))
      {
        String str = Util.validateZipCodeFormat(paramSecureUser, paramString2, paramString1, localHashMap);
        if (str == null) {
          return false;
        }
        if (!str.equals("")) {
          getZipCodeValue().setFormat(str);
        }
      }
    }
    return true;
  }
  
  public void set(Payee paramPayee)
  {
    super.set(paramPayee);
    setPayeeCity(paramPayee.getCity());
    setPayeeStreet(paramPayee.getStreet());
    setPayeeStreet2(paramPayee.getStreet2());
    setPayeeStreet3(paramPayee.getStreet3());
    setPayeeState(paramPayee.getState());
    setPayeeCountry(paramPayee.getCountry());
    setPayeeZipCode(paramPayee.getZipCode());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.AddGlobalPayee
 * JD-Core Version:    0.7.0.1
 */