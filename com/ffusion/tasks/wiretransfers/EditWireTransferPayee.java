package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.MapError;
import com.ffusion.util.Strings;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditWireTransferPayee
  extends WireTransferPayee
  implements WireTaskDefines
{
  protected String payeeSessionName = "WireTransferPayee";
  protected int error = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean currentlyProcessing = false;
  protected int maxPayeeNameLength = 50;
  protected boolean clearDestBank = false;
  protected boolean clearIntermediaryBanks = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag == true)
    {
      initProcess(localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else if ((this.clearDestBank == true) || (this.clearIntermediaryBanks == true))
    {
      Object localObject;
      if (this.clearDestBank == true)
      {
        this.clearDestBank = false;
        localObject = new WireTransferBank();
        setDestinationBank((WireTransferBank)localObject);
      }
      if (this.clearIntermediaryBanks == true)
      {
        this.clearIntermediaryBanks = false;
        localObject = new WireTransferBanks();
        setIntermediaryBanks((WireTransferBanks)localObject);
      }
      str = this.successURL;
    }
    else
    {
      try
      {
        this.error = validateInput(localHttpSession);
        if (this.error == 0)
        {
          if (this.processFlag)
          {
            this.processFlag = false;
            if (!this.currentlyProcessing)
            {
              this.currentlyProcessing = true;
              this.error = doProcess(localHttpSession);
              if (this.error == 0)
              {
                str = this.successURL;
                localHttpSession.removeAttribute("OldWireTransferPayee");
              }
              else
              {
                str = this.serviceErrorURL;
              }
              this.currentlyProcessing = false;
            }
            else
            {
              this.error = 12017;
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
      catch (CSILException localCSILException)
      {
        MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    WireTransferPayee localWireTransferPayee1 = (WireTransferPayee)paramHttpSession.getAttribute(this.payeeSessionName);
    if (localWireTransferPayee1 == null)
    {
      this.error = 12016;
    }
    else
    {
      set(localWireTransferPayee1);
      WireTransferPayee localWireTransferPayee2 = new WireTransferPayee();
      localWireTransferPayee2.set(localWireTransferPayee1);
      paramHttpSession.setAttribute("OldWireTransferPayee", localWireTransferPayee2);
    }
    this.initFlag = false;
  }
  
  protected int validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (this.validate == null) {
      return 0;
    }
    Object localObject1;
    if (this.validate.indexOf("PAYEENAME") != -1)
    {
      localObject1 = getPayeeName();
      if ((localObject1 == null) || (((String)localObject1).length() == 0)) {
        return 12022;
      }
      if (((String)localObject1).length() > this.maxPayeeNameLength) {
        return 12029;
      }
    }
    if ((this.validate.indexOf("ACCOUNT_NUM") != -1) && ((getAccountNum() == null) || (getAccountNum().length() == 0))) {
      return 12023;
    }
    if (!Strings.isValidAccountNumber(getAccountNum(), localSecureUser.getLocale())) {
      return 12058;
    }
    if ((this.validate.indexOf("DESTINATION_BANK") != -1) && ((getDestinationBank().getBankName() == null) || (getDestinationBank().getBankName().length() == 0))) {
      return 12020;
    }
    if ((this.validate.indexOf("CITY") != -1) && ((getCity() == null) || (getCity().length() == 0))) {
      return 12026;
    }
    Object localObject2;
    Object localObject3;
    if ((this.country != null) && (this.country.length() > 0))
    {
      localObject1 = new HashMap();
      localObject2 = Util.getCodeForBankLookupCountryName(localSecureUser, this.country, (HashMap)localObject1);
      if (this.validate.indexOf("ZIPCODE") != -1)
      {
        if ((getZipCode() == null) || (getZipCode().length() == 0)) {
          return 12028;
        }
        localObject3 = Util.validateZipCodeFormat(localSecureUser, (String)localObject2, this.zipCode.getString(), (HashMap)localObject1);
        if (localObject3 == null) {
          return 12054;
        }
        if (!((String)localObject3).equals("")) {
          this.zipCode.setFormat((String)localObject3);
        }
      }
    }
    if ((this.validate.indexOf("STREET") != -1) && ((getStreet() == null) || (getStreet().length() == 0))) {
      return 12040;
    }
    if ((this.validate.indexOf("COUNTRY") != -1) && ((getCountry() == null) || (getCountry().length() == 0))) {
      return 12041;
    }
    if (this.validate.indexOf("WIRE_TRANSFER_BANK") != -1)
    {
      localObject1 = new HashMap();
      localObject2 = getDestinationBank();
      localObject3 = getIntermediaryBanks();
      Object localObject4;
      WireTransferBanks localWireTransferBanks;
      if (((WireTransferBank)localObject2).getRoutingNumber().length() == 0)
      {
        if ((localObject3 == null) || (((WireTransferBanks)localObject3).size() == 0)) {
          return 12030;
        }
        int i = 1;
        for (int j = 0; j < ((WireTransferBanks)localObject3).size(); j++)
        {
          localObject4 = (WireTransferBank)((WireTransferBanks)localObject3).get(j);
          if (((WireTransferBank)localObject4).getRoutingNumber().length() > 0)
          {
            i = 0;
            break;
          }
        }
        if (i == 1) {
          return 12030;
        }
      }
      else
      {
        localWireTransferBanks = new WireTransferBanks();
        try
        {
          localWireTransferBanks = Wire.getWireTransferBanks(localSecureUser, (WireTransferBank)localObject2, 1, (HashMap)localObject1);
          if (localWireTransferBanks.size() == 0) {
            return 12051;
          }
        }
        catch (CSILException localCSILException1)
        {
          localCSILException1.printStackTrace(System.err);
          return 12051;
        }
        String str1 = ((WireTransferBank)localObject2).getAction();
        localObject4 = ((WireTransferBank)localObject2).getSrvrBankID();
        if (isNullOrEmpty(str1)) {
          str1 = "mod";
        }
        ((WireTransferBank)localObject2).set((WireTransferBank)localWireTransferBanks.get(0));
        ((WireTransferBank)localObject2).setAction(str1);
        ((WireTransferBank)localObject2).setSrvrBankID((String)localObject4);
      }
      if (localObject3 != null)
      {
        if (((WireTransferBanks)localObject3).size() > 2) {
          return 12055;
        }
        localWireTransferBanks = new WireTransferBanks();
        for (int k = 0; k < ((WireTransferBanks)localObject3).size(); k++)
        {
          localObject4 = (WireTransferBank)((WireTransferBanks)localObject3).get(k);
          try
          {
            localWireTransferBanks = Wire.getWireTransferBanks(localSecureUser, (WireTransferBank)localObject4, 1, (HashMap)localObject1);
          }
          catch (CSILException localCSILException2)
          {
            localCSILException2.printStackTrace(System.err);
            return 12051;
          }
          if (localWireTransferBanks.size() == 0) {
            return 12051;
          }
          String str2 = ((WireTransferBank)localObject4).getAction();
          if (isNullOrEmpty(str2)) {
            str2 = "mod";
          }
          String str3 = ((WireTransferBank)localObject4).getSrvrBankID();
          ((WireTransferBank)localObject4).set((WireTransferBank)localWireTransferBanks.get(0));
          ((WireTransferBank)localObject4).setAction(str2);
          ((WireTransferBank)localObject4).setSrvrBankID(str3);
        }
      }
    }
    if (this.validate.indexOf("PAYEE_SCOPE") != -1)
    {
      localObject1 = (WireTransferPayee)paramHttpSession.getAttribute("OldWireTransferPayee");
      if (localObject1 != null)
      {
        localObject2 = ((WireTransferPayee)localObject1).getPayeeScope();
        localObject3 = getPayeeScope();
        if ((((String)localObject2).equals("BUSINESS")) && (((String)localObject3).equals("USER"))) {
          return 12025;
        }
      }
    }
    this.validate = null;
    return 0;
  }
  
  protected int doProcess(HttpSession paramHttpSession)
  {
    int i = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    WireTransferPayee localWireTransferPayee1 = (WireTransferPayee)paramHttpSession.getAttribute("OldWireTransferPayee");
    if (localWireTransferPayee1 == null) {
      return 12045;
    }
    try
    {
      Wire.modifyWireTransferPayee(localSecureUser, this, localWireTransferPayee1, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() == 31056)
      {
        i = 0;
        paramHttpSession.setAttribute("PAYEE_MODIFICATION_ERROR", localHashMap.get("PAYEE_MODIFICATION_ERROR"));
      }
      else
      {
        i = MapError.mapError(localCSILException);
      }
    }
    if (i == 0)
    {
      WireTransferPayee localWireTransferPayee2 = (WireTransferPayee)paramHttpSession.getAttribute(this.payeeSessionName);
      if (localWireTransferPayee2 != null) {
        localWireTransferPayee2.set(this);
      }
    }
    return i;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.payeeSessionName = paramString;
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
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setMaxPayeeNameLength(String paramString)
  {
    int i;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    this.maxPayeeNameLength = i;
  }
  
  public void setWirePayeeNameLength(int paramInt)
  {
    this.maxPayeeNameLength = paramInt;
  }
  
  public void setClearDestinationBank(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.clearDestBank = true;
    } else {
      this.clearDestBank = false;
    }
  }
  
  public String getClearDestinationBank()
  {
    if (this.clearDestBank) {
      return "true";
    }
    return "false";
  }
  
  public void setClearIntermediaryBanks(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.clearIntermediaryBanks = true;
    } else {
      this.clearIntermediaryBanks = false;
    }
  }
  
  public String getClearIntermediaryBanks()
  {
    if (this.clearIntermediaryBanks) {
      return "true";
    }
    return "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.EditWireTransferPayee
 * JD-Core Version:    0.7.0.1
 */