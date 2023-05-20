package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.Contact;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.wiretransfers.WireAccountMap;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWireCreditInfo
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected String accountId;
  protected int businessId;
  
  public GetWireCreditInfo()
  {
    this.beanSessionName = "WireTransfer";
    this.collectionSessionName = "WiresAccounts";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser.getUserType() == 2)
    {
      if (this.businessId == 0)
      {
        this.error = 12034;
        return this.taskErrorURL;
      }
      localSecureUser.setBusinessID(this.businessId);
    }
    WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfer == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.collectionSessionName);
    if ((localAccounts == null) || (localAccounts.size() == 0))
    {
      this.error = 12002;
      return this.taskErrorURL;
    }
    Account localAccount = null;
    if (this.accountId != null) {
      localAccount = localAccounts.getByID(this.accountId);
    }
    if (localAccount == null)
    {
      this.error = 50;
      return this.taskErrorURL;
    }
    try
    {
      WireTransferPayee localWireTransferPayee = new WireTransferPayee(localSecureUser.getLocale());
      localWireTransferPayee.setAccountNum(localAccount.getNumber());
      localWireTransferPayee.setAccountType(WireAccountMap.mapAccountTypeToStr(localAccount.getTypeValue()));
      localAccount = AccountHandler.getAccountAddress(localSecureUser, localAccount, localHashMap);
      Contact localContact = localAccount.getContact();
      if (localContact != null)
      {
        localWireTransferPayee.setStreet(localContact.getStreet());
        localWireTransferPayee.setStreet2(localContact.getStreet2());
        localWireTransferPayee.setCity(localContact.getCity());
        localWireTransferPayee.setState(localContact.getState());
        localWireTransferPayee.setZipCode(localContact.getZipCode());
        localWireTransferPayee.setCountry(localContact.getCountry());
      }
      com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
      localBusiness.setBankId(localSecureUser.getBankID());
      localBusiness.setId(localSecureUser.getBusinessID());
      localBusiness = com.ffusion.csil.handlers.Business.getBusiness(localSecureUser, localBusiness, localHashMap);
      if (localBusiness == null)
      {
        this.error = 12049;
        return this.taskErrorURL;
      }
      localWireTransferPayee.setPayeeName(localBusiness.getBusinessName());
      localWireTransferPayee.setActivationDate(localBusiness.getActivationDate());
      String str2 = Wire.getBPWFIId(localSecureUser, String.valueOf(localBusiness.getId()));
      WireTransferBank localWireTransferBank = Wire.getBPWFIById(localSecureUser, str2, localHashMap);
      if (localWireTransferBank == null)
      {
        this.error = 12050;
        return this.taskErrorURL;
      }
      if ((localWireTransferBank.getCountry() == null) || (localWireTransferBank.getCountry().length() == 0)) {
        localWireTransferBank.setCountry("UNITED STATES");
      }
      localWireTransferPayee.setDestinationBank(localWireTransferBank);
      localWireTransfer.setWireCreditInfo(localWireTransferPayee);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setAccountID(String paramString)
  {
    this.accountId = paramString;
  }
  
  public void setBusinessID(String paramString)
  {
    try
    {
      this.businessId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireCreditInfo
 * JD-Core Version:    0.7.0.1
 */