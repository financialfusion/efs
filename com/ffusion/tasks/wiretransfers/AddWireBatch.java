package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddWireBatch
  extends ModifyWireBatch
{
  protected static long timeoutValue = 120000L;
  protected boolean currentlyProcessing = false;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  protected String wireSessionName = null;
  protected String templatesSessionName = null;
  protected WireTransfers wireTemplates = null;
  protected HashMap wireMap = null;
  protected String wireParameter = null;
  protected boolean wireFromTemplateFlag = false;
  protected boolean wireFreeFormFlag = false;
  protected boolean wireFromHostFlag = false;
  
  public AddWireBatch()
  {
    this.beanSessionName = "WireBatch";
    this.wires = new WireTransfers();
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.wireFreeFormFlag == true)
    {
      str = getWireFreeForm(localHttpSession);
    }
    else if (this.wireFromHostFlag == true)
    {
      str = getWireFromHost(localHttpSession);
    }
    else if (this.wireFromTemplateFlag == true)
    {
      str = getWireFromTemplate(localHttpSession);
    }
    else if (this.initFlag == true)
    {
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
      str = initialize(paramHttpServletRequest, localHttpSession);
    }
    else
    {
      str = addWireBatch(localHttpSession);
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
    if ((getBatchType() != null) && (getBatchType().equals("TEMPLATE") == true))
    {
      this.wireTemplates = ((WireTransfers)paramHttpSession.getAttribute(this.templatesSessionName));
      if (this.wireTemplates == null)
      {
        this.error = 12038;
        return this.taskErrorURL;
      }
    }
    return this.successURL;
  }
  
  protected String addWireBatch(HttpSession paramHttpSession)
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
              str = m(paramHttpSession);
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
  
  private String m(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 12014;
      return this.taskErrorURL;
    }
    WireBatch localWireBatch1 = null;
    HashMap localHashMap = null;
    try
    {
      setBankID(localSecureUser.getBPWFIID());
      setCustomerID(localSecureUser.getBusinessID());
      setSubmittedBy(localSecureUser.getProfileID());
      setUserID(localSecureUser.getProfileID());
      localHashMap = new HashMap();
      localWireBatch1 = Wire.addWireBatch(localSecureUser, this, localHashMap);
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
      WireBatch localWireBatch2 = new WireBatch(this.locale);
      localWireBatch2.set(localWireBatch1);
      paramHttpSession.setAttribute(this.beanSessionName, localWireBatch2);
      this.haveProcessed = true;
    }
    else
    {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    setAmount(calculateTotalAmount());
    if (this.validate == null) {
      return true;
    }
    if (this.validate.indexOf("BATCH_NAME") != -1)
    {
      if (isNullOrEmpty(getBatchName()))
      {
        this.error = 12036;
        return false;
      }
      if ((getBatchName().trim().length() == 0) || (getBatchName().trim().length() > 128))
      {
        this.error = 12056;
        return false;
      }
    }
    if ((this.validate.indexOf("DATE_TO_POST") != -1) && (!validateDate(paramHttpSession, true))) {
      return false;
    }
    WireTransfers localWireTransfers = getWires();
    if (this.validate.indexOf("WIRE_TRANSFERS") != -1)
    {
      if ((localWireTransfers == null) || (localWireTransfers.size() == 0))
      {
        this.error = 12052;
        return false;
      }
      for (int i = 0; i < localWireTransfers.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
        if ((localWireTransfer != null) && (!validateWire(paramHttpSession, localWireTransfer, this.validate))) {
          return false;
        }
      }
    }
    if (!getBatchType().equals("HOST"))
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.duplicateWire = Wire.isDuplicateWireInBatch(localSecureUser, this, true, new HashMap());
    }
    this.validate = null;
    return true;
  }
  
  protected boolean validateWire(HttpSession paramHttpSession, WireTransfer paramWireTransfer, String paramString)
    throws CSILException
  {
    if (paramString.indexOf("AMOUNT") != -1)
    {
      Currency localCurrency = paramWireTransfer.getAmountValue();
      if ((localCurrency == null) || (localCurrency.doubleValue() <= 0.0D))
      {
        this.error = 12008;
        return false;
      }
    }
    if (getBatchType().equals("HOST"))
    {
      if (isNullOrEmpty(paramWireTransfer.getHostID()) == true)
      {
        this.error = 12037;
        return false;
      }
    }
    else
    {
      if ((paramString.indexOf("FROM_ACCOUNT_ID") != -1) && (isNullOrEmpty(paramWireTransfer.getFromAccountID()) == true))
      {
        this.error = 12007;
        return false;
      }
      if ((isNullOrEmpty(paramWireTransfer.getWirePayeeID()) == true) && (paramWireTransfer.getWirePayee() != null))
      {
        if ((paramString.indexOf("WIRE_PAYEE") != -1) && (!validatePayee(paramHttpSession, paramWireTransfer))) {
          return false;
        }
        if ((paramString.indexOf("WIRE_TRANSFER_BANK") != -1) && (!validateBank(paramHttpSession, paramWireTransfer))) {
          return false;
        }
      }
    }
    if ((paramString.indexOf("BY_ORDER_OF") != -1) && (!validateByOrderOf(paramWireTransfer)))
    {
      this.error = 12057;
      return false;
    }
    paramWireTransfer.setDateToPost(getDateToPostValue());
    return true;
  }
  
  public String getWireFromTemplate(HttpSession paramHttpSession)
  {
    this.wireFromTemplateFlag = false;
    String[] arrayOfString = parse(this.wireParameter);
    this.wireParameter = null;
    if ((arrayOfString == null) || (arrayOfString.length != 3))
    {
      this.error = 12039;
      return this.taskErrorURL;
    }
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = arrayOfString[2];
    if ((str1.length() == 0) || (str2.length() == 0))
    {
      this.error = 12039;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(str1);
    if (localWireTransfer != null)
    {
      if (!localWireTransfer.getTemplateID().equals(str2))
      {
        this.wireMap.remove(str1);
        localWireTransfer = loadFromTemplate(str2);
        this.wireMap.put(str1, localWireTransfer);
      }
      localWireTransfer.setAmount(str3);
    }
    else
    {
      localWireTransfer = loadFromTemplate(str2);
      localWireTransfer.setAmount(str3);
      this.wireMap.put(str1, localWireTransfer);
    }
    paramHttpSession.setAttribute(this.wireSessionName, localWireTransfer);
    return this.successURL;
  }
  
  public void setWireFromTemplate(String paramString)
  {
    String[] arrayOfString = parse(paramString);
    if ((arrayOfString == null) || (arrayOfString.length != 3))
    {
      this.error = 12039;
      return;
    }
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = arrayOfString[2];
    if ((str1.length() == 0) || (str2.length() == 0)) {
      return;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(str1);
    if (localWireTransfer != null)
    {
      if (!localWireTransfer.getTemplateID().equals(str2)) {
        localWireTransfer = loadFromTemplate(str2);
      }
      localWireTransfer.setAmount(str3);
    }
    else
    {
      localWireTransfer = loadFromTemplate(str2);
      localWireTransfer.setAmount(str3);
    }
    this.wires.add(localWireTransfer);
  }
  
  public String getWireFreeForm(HttpSession paramHttpSession)
  {
    this.wireFreeFormFlag = false;
    if ((this.wireParameter == null) || (this.wireParameter.trim().length() == 0))
    {
      this.error = 12039;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(this.wireParameter);
    if (localWireTransfer == null)
    {
      localWireTransfer = new WireTransfer();
      localWireTransfer.set("WireIndex", this.wireParameter);
      this.wireMap.put(this.wireParameter, localWireTransfer);
    }
    paramHttpSession.setAttribute(this.wireSessionName, localWireTransfer);
    this.wireParameter = null;
    return this.successURL;
  }
  
  public void setWireFreeForm(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      return;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(paramString);
    if (localWireTransfer != null) {
      this.wires.add(localWireTransfer);
    }
  }
  
  public String getWireFromHost(HttpSession paramHttpSession)
  {
    this.wireFromHostFlag = false;
    String[] arrayOfString = parse(this.wireParameter);
    this.wireParameter = null;
    if ((arrayOfString == null) || (arrayOfString.length != 3))
    {
      this.error = 12039;
      return this.taskErrorURL;
    }
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = arrayOfString[2];
    if ((str1.length() == 0) || (str2.length() == 0))
    {
      this.error = 12039;
      return this.taskErrorURL;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(str1);
    if (localWireTransfer != null)
    {
      localWireTransfer.setHostID(str2);
      localWireTransfer.setAmount(str3);
    }
    else
    {
      localWireTransfer = new WireTransfer();
      localWireTransfer.setHostID(str2);
      localWireTransfer.setAmount(str3);
      this.wireMap.put(str1, localWireTransfer);
    }
    paramHttpSession.setAttribute(this.wireSessionName, localWireTransfer);
    return this.successURL;
  }
  
  public void setWireFromHost(String paramString)
  {
    String[] arrayOfString = parse(paramString);
    if ((arrayOfString == null) || (arrayOfString.length != 3))
    {
      this.error = 12039;
      return;
    }
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = arrayOfString[2];
    if ((str1.length() == 0) || (str2.length() == 0)) {
      return;
    }
    WireTransfer localWireTransfer = (WireTransfer)this.wireMap.get(str1);
    if (localWireTransfer != null)
    {
      localWireTransfer.setHostID(str2);
      localWireTransfer.setAmount(str3);
    }
    else
    {
      localWireTransfer = new WireTransfer();
      localWireTransfer.setHostID(str2);
      localWireTransfer.setAmount(str3);
    }
    this.wires.add(localWireTransfer);
  }
  
  public void setDeleteWireFromBatch(String paramString)
  {
    Object localObject = this.wireMap.remove(paramString);
    this.wires.remove(localObject);
  }
  
  public void setClearWiresInBatch(String paramString)
  {
    this.wires = new WireTransfers();
  }
  
  public String[] parse(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    String[] arrayOfString = new String[localStringTokenizer.countTokens()];
    try
    {
      int i = 0;
      while (localStringTokenizer.hasMoreTokens()) {
        arrayOfString[(i++)] = localStringTokenizer.nextToken().trim();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.err);
      return null;
    }
    return arrayOfString;
  }
  
  public WireTransfer loadFromTemplate(String paramString)
  {
    WireTransfer localWireTransfer1 = null;
    WireTransfer localWireTransfer2 = (WireTransfer)this.wireTemplates.getByID(paramString);
    if (localWireTransfer2 != null)
    {
      localWireTransfer1 = (WireTransfer)localWireTransfer2.clone();
      if (localWireTransfer2.getWireType().equals("RECTEMPLATE")) {
        localWireTransfer1.setWireType("RECURRING");
      } else {
        localWireTransfer1.setWireType("SINGLE");
      }
      localWireTransfer1.setWireSource("TEMPLATE");
      if (localWireTransfer2.getWireDestination().equals("INTERNATIONAL"))
      {
        localWireTransfer1.setAmount(localWireTransfer2.getOrigAmount());
        localWireTransfer1.setAmtCurrencyType(localWireTransfer2.getOrigCurrency());
      }
    }
    else
    {
      localWireTransfer1 = new WireTransfer();
    }
    return localWireTransfer1;
  }
  
  public void setTimeOutValue(String paramString)
  {
    try
    {
      timeoutValue = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setWireSessionName(String paramString)
  {
    this.wireSessionName = paramString;
  }
  
  public void setTemplatesSessionName(String paramString)
  {
    this.templatesSessionName = paramString;
  }
  
  public void setWireParameter(String paramString)
  {
    this.wireParameter = paramString;
  }
  
  public void setWireFromTemplateFlag(String paramString)
  {
    this.wireFromTemplateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setWireFreeFormFlag(String paramString)
  {
    this.wireFreeFormFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setWireFromHostFlag(String paramString)
  {
    this.wireFromHostFlag = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddWireBatch
 * JD-Core Version:    0.7.0.1
 */