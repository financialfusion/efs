package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limits;
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

public class AddWireTransfer
  extends ModifyWireTransfer
{
  protected static long timeoutValue = 120000L;
  protected boolean currentlyProcessing = false;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected String approvalCollectionSessionName = null;
  protected String businessID = null;
  protected boolean loadFromTemplate = false;
  protected boolean validateAmount = false;
  protected boolean _checkWireTemplateLimits = false;
  protected boolean _wireTemplateExceedsLimits = false;
  
  public AddWireTransfer()
  {
    this.beanSessionName = "WireTransfer";
    this.collectionSessionName = "WireTransfers";
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
    else if (this.validateAmount == true)
    {
      str = validateAmountOnly();
    }
    else if (this._checkWireTemplateLimits == true)
    {
      str = checkWireTemplateExceedsLimits(localHttpSession);
    }
    else
    {
      str = addWireTransfer(localHttpSession);
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
    if (this.loadFromTemplate == true)
    {
      WireTransfer localWireTransfer = (WireTransfer)paramHttpSession.getAttribute(this.beanSessionName);
      set(localWireTransfer);
      paramHttpSession.removeAttribute(this.beanSessionName);
      this.loadFromTemplate = false;
    }
    return this.successURL;
  }
  
  protected String addWireTransfer(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    try
    {
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
              str = o(paramHttpSession);
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
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private String o(HttpSession paramHttpSession)
  {
    String str1 = getMathRule();
    Currency localCurrency = getAmountValue();
    String str2 = getAmount();
    String str3 = getAmtCurrencyType();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer1 = null;
    try
    {
      if (localSecureUser.getUserType() == 2)
      {
        if ((this.businessID == null) || (this.businessID.length() == 0))
        {
          this.error = 12034;
          return this.taskErrorURL;
        }
        setCustomerID(this.businessID);
        setBankID(Wire.getBPWFIId(localSecureUser, this.businessID));
      }
      else
      {
        setCustomerID(localSecureUser.getBusinessID());
        setBankID(localSecureUser.getBPWFIID());
      }
      setSubmittedBy(localSecureUser.getProfileID());
      setUserID(localSecureUser.getProfileID());
      if ((getWireType() != null) && ((getWireType().equals("TEMPLATE")) || (getWireType().equals("RECTEMPLATE"))))
      {
        localWireTransfer1 = Wire.addWireTemplate(localSecureUser, this, this._autoEntitleWireTransfer, localHashMap);
      }
      else
      {
        if ((getTemplateID() != null) && (getTemplateID().length() != 0)) {
          setWireSource("TEMPLATE");
        } else {
          setWireSource("FREEFORM");
        }
        localWireTransfer1 = Wire.addWireTransfer(localSecureUser, this, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      setMathRule(str1);
      if (localCurrency != null) {
        setAmount(localCurrency);
      }
      setAmount(str2);
      setAmtCurrencyType(str3);
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    catch (Throwable localThrowable)
    {
      setMathRule(str1);
      if (localCurrency != null) {
        setAmount(localCurrency);
      }
      setAmount(str2);
      setAmtCurrencyType(str3);
      localThrowable.printStackTrace(System.err);
      this.error = 1000;
      return this.taskErrorURL;
    }
    if (this.error == 0)
    {
      WireTransfer localWireTransfer2 = new WireTransfer(this.locale);
      localWireTransfer2.set(localWireTransfer1);
      paramHttpSession.setAttribute(this.beanSessionName, localWireTransfer2);
      WireTransfers localWireTransfers = null;
      if (localWireTransfer2.getStatus() == 100) {
        localWireTransfers = (WireTransfers)paramHttpSession.getAttribute(this.approvalCollectionSessionName);
      } else {
        localWireTransfers = (WireTransfers)paramHttpSession.getAttribute(this.collectionSessionName);
      }
      if (localWireTransfers != null) {
        localWireTransfers.add(localWireTransfer2);
      }
      this.haveProcessed = true;
    }
    else
    {
      setMathRule(str1);
      if (localCurrency != null) {
        setAmount(localCurrency);
      }
      setAmount(str2);
      setAmtCurrencyType(str3);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    if (this.validate == null) {
      return true;
    }
    String str = getWireType();
    if (str == null)
    {
      this.error = 12044;
      return false;
    }
    if ((this.validate.indexOf("FROM_ACCOUNT_ID") != -1) && (isNullOrEmpty(getFromAccountID()) == true))
    {
      this.error = 12007;
      return false;
    }
    Object localObject;
    if (this.validate.indexOf("AMOUNT") != -1)
    {
      localObject = getAmountValue();
      if ((str.equals("RECTEMPLATE")) || (str.equals("TEMPLATE")))
      {
        if ((localObject != null) && (((Currency)localObject).doubleValue() < 0.0D))
        {
          this.error = 12048;
          return false;
        }
      }
      else if ((localObject == null) || (((Currency)localObject).doubleValue() <= 0.0D))
      {
        this.error = 12008;
        return false;
      }
    }
    if ((!str.equals("RECTEMPLATE")) && (!str.equals("TEMPLATE")) && (!validateDate(paramHttpSession, true))) {
      return false;
    }
    if ((this.validate.indexOf("WIRE_PAYEE") != -1) && (isNullOrEmpty(getWirePayeeID()) == true) && (getWirePayee() != null) && (!validatePayee(paramHttpSession))) {
      return false;
    }
    if ((this.validate.indexOf("WIRE_TRANSFER_BANK") != -1) && (isNullOrEmpty(getWirePayeeID()) == true) && (getWirePayee() != null) && (!validateBank(paramHttpSession))) {
      return false;
    }
    if ((str.equals("RECTEMPLATE")) || (str.equals("TEMPLATE")))
    {
      if (isNullOrEmpty(getWireName()) == true)
      {
        this.error = 12053;
        return false;
      }
    }
    else
    {
      localObject = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.duplicateWire = Wire.isDuplicateWire((SecureUser)localObject, this, true, new HashMap());
    }
    if ((this.validate.indexOf("BY_ORDER_OF") != -1) && ((!isNullOrEmpty(getByOrderOfName())) || (!isNullOrEmpty(getByOrderOfAddress1())) || (!isNullOrEmpty(getByOrderOfAddress2())) || (!isNullOrEmpty(getByOrderOfAddress3())) || (!isNullOrEmpty(getByOrderOfAccount()))) && ((isNullOrEmpty(getByOrderOfName())) || (isNullOrEmpty(getByOrderOfAddress1())) || (isNullOrEmpty(getByOrderOfAccount()))))
    {
      this.error = 12057;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  protected String validateAmountOnly()
  {
    this.validateAmount = false;
    Currency localCurrency = getAmountValue();
    if ((localCurrency == null) || (localCurrency.doubleValue() <= 0.0D))
    {
      this.error = 12008;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setTimeOutValue(String paramString)
  {
    try
    {
      timeoutValue = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setApprovalCollectionSessionName(String paramString)
  {
    this.approvalCollectionSessionName = paramString;
  }
  
  public void setBusinessID(String paramString)
  {
    this.businessID = paramString;
  }
  
  public void setLoadFromTemplate(String paramString)
  {
    this.loadFromTemplate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidateAmount(String paramString)
  {
    this.validateAmount = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCheckWireTemplateLimits(String paramString)
  {
    this._checkWireTemplateLimits = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCheckWireTemplateLimits()
  {
    return String.valueOf(this._checkWireTemplateLimits);
  }
  
  public String getWireTemplateExceedsLimits()
  {
    return String.valueOf(this._wireTemplateExceedsLimits);
  }
  
  protected String checkWireTemplateExceedsLimits(HttpSession paramHttpSession)
  {
    if (getWireLimit().equals("")) {
      setWireLimit(getAmount());
    }
    this._checkWireTemplateLimits = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      if (localSecureUser.getUserType() == 2)
      {
        if ((this.businessID == null) || (this.businessID.length() == 0))
        {
          this.error = 12034;
          return this.taskErrorURL;
        }
        setCustomerID(this.businessID);
      }
      else
      {
        setCustomerID(localSecureUser.getBusinessID());
      }
      Limits localLimits = Wire.checkWireTemplateLimits(localSecureUser, this, true, new HashMap());
      this._wireTemplateExceedsLimits = ((localLimits != null) && (!localLimits.isEmpty()));
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddWireTransfer
 * JD-Core Version:    0.7.0.1
 */