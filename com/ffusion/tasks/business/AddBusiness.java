package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBusiness
  extends ModifyBusiness
{
  protected boolean loadTXLimitsFlag = true;
  protected boolean getBusinessInfoFromBackEnd = true;
  private String gp = this.successURL;
  private String gq = "";
  protected boolean addBusinessToBPWOnAdd = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool1 = true;
    this.gp = this.successURL;
    if ((bool1) && (this.initFlag)) {
      bool1 = jdMethod_char(localHttpSession);
    }
    if (bool1)
    {
      boolean bool2 = false;
      try
      {
        bool2 = validateInput(localHttpSession);
      }
      catch (CSILException localCSILException1)
      {
        MapError.mapError(localCSILException1);
        return this.serviceErrorURL;
      }
      if (bool2)
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          Object localObject1;
          if (this.loadTXLimitsFlag == true)
          {
            localObject1 = (TransactionLimits)localHttpSession.getAttribute("BusinessTransactionLimits");
            if (localObject1 == null)
            {
              this.error = 4125;
              return this.taskErrorURL;
            }
            setTransactionLimits((TransactionLimits)localObject1);
          }
          try
          {
            localObject1 = null;
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            setBankId(localSecureUser.getBankID());
            if (this.getBusinessInfoFromBackEnd)
            {
              localObject2 = com.ffusion.csil.core.Business.getBusinessInfoFromBackend(localSecureUser, getBusinessName(), getBusinessCIF(), (HashMap)localObject1);
              if ((getBusinessCIF() == null) || (!getBusinessCIF().equals(((User)localObject2).getPassword())))
              {
                this.error = 4132;
                return this.taskErrorURL;
              }
              jdMethod_for((User)localObject2);
            }
            if (this.enforceUnqiueCIF)
            {
              if (localObject1 == null) {
                localObject1 = new HashMap();
              }
              ((HashMap)localObject1).put("CheckCIFUnique", "true");
            }
            Object localObject2 = com.ffusion.csil.core.Business.addBusiness(localSecureUser, this, (HashMap)localObject1);
            if (this.addBusinessToBPWOnAdd)
            {
              HashMap localHashMap = new HashMap();
              localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
              com.ffusion.csil.core.Business.registerBusinessWithBPW(localSecureUser, (com.ffusion.beans.business.Business)localObject2, localHashMap);
            }
            if (this.error == 0) {
              localHttpSession.setAttribute("Business", this);
            }
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            this.gp = this.serviceErrorURL;
          }
        }
      }
      else if ((this.gp == null) || (this.gp.equals(this.successURL))) {
        this.gp = this.taskErrorURL;
      }
    }
    else
    {
      this.gp = this.taskErrorURL;
    }
    return this.gp;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate == null) {
      return bool;
    }
    if ((this.businessName == null) || (this.businessName.length() == 0))
    {
      bool = setError(4100);
      return bool;
    }
    if (this.businessName.length() > this.maxBusinessNameLength)
    {
      bool = setError(4150);
      return bool;
    }
    if (this.validate.indexOf(BUSINESSNAME) != -1)
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      Businesses localBusinesses = new Businesses((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBusiness.setBankId(localSecureUser.getBankID());
      localBusiness.setBusinessName(this.businessName);
      localBusiness.setBusinessCIF(this.businessCIF);
      localBusiness.setStatus(String.valueOf(-1));
      HashMap localHashMap = new HashMap(3);
      localHashMap.put("BUSINESSES", localBusinesses);
      localHashMap.put("UseLikeComparison", "false");
      try
      {
        int i = com.ffusion.csil.core.Business.getFilteredBusinessesCount(localSecureUser, localBusiness, null, localHashMap);
        if (i > 0) {
          return setError(4102);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.gp = this.serviceErrorURL;
      }
    }
    bool = super.otherValidationCheck(paramHttpSession);
    this.validate = null;
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.verifyFormat == null) {
      return bool;
    }
    bool = super.otherVerifyFormatCheck(paramHttpSession);
    this.verifyFormat = null;
    return bool;
  }
  
  private boolean jdMethod_char(HttpSession paramHttpSession)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    TransactionLimits localTransactionLimits = new TransactionLimits(com.ffusion.csil.core.Business.getLimitTypeProps(), "per business", localLocale);
    paramHttpSession.setAttribute("BusinessTransactionLimits", localTransactionLimits);
    setACHBatchType(1);
    this.initFlag = false;
    return true;
  }
  
  private void jdMethod_for(User paramUser)
  {
    if (paramUser == null) {
      return;
    }
    setStreet(paramUser.getStreet());
    setStreet2(paramUser.getStreet2());
    setCity(paramUser.getCity());
    setState(paramUser.getState());
    setZipCode(paramUser.getZipCode());
    setCountry(paramUser.getCountry());
    setEmail(paramUser.getEmail());
    setPhone(paramUser.getPhone());
    setPhone2(paramUser.getPhone2());
    setDataPhone(paramUser.getDataPhone());
    setFaxPhone(paramUser.getFaxPhone());
    setPreferredContactMethod(paramUser.getPreferredContactMethod());
  }
  
  public void setLoadTXLimits(String paramString)
  {
    this.loadTXLimitsFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setGetBusinessInfoFromBackEnd(String paramString)
  {
    this.getBusinessInfoFromBackEnd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAddBusinessToBPWOnAdd(String paramString)
  {
    this.addBusinessToBPWOnAdd = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setMarketSegmentId(String paramString)
  {
    this.gq = paramString;
  }
  
  public String getMarketSegmentId()
  {
    return this.gq;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.AddBusiness
 * JD-Core Version:    0.7.0.1
 */