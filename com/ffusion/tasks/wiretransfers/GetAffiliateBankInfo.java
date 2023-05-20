package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAffiliateBankInfo
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected int bankId;
  
  public GetAffiliateBankInfo()
  {
    this.beanSessionName = "WireTransferPayee";
    this.collectionSessionName = "AffiliateBanks";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute(this.collectionSessionName);
    if ((localAffiliateBanks == null) || (localAffiliateBanks.size() == 0))
    {
      this.error = 25505;
      return this.taskErrorURL;
    }
    AffiliateBank localAffiliateBank = null;
    localAffiliateBank = localAffiliateBanks.getByID(this.bankId);
    if (localAffiliateBank == null)
    {
      this.error = 25504;
      return this.taskErrorURL;
    }
    try
    {
      WireTransferBank localWireTransferBank = new WireTransferBank();
      localWireTransferBank.setAffiliateBank(localAffiliateBank);
      if (localWireTransferBank == null)
      {
        DebugLog.log("ERROR: No record found in BPW for bank with BPWFIId = " + localAffiliateBank.getFIBPWID());
        this.error = 12019;
        return this.taskErrorURL;
      }
      localWireTransferPayee.setDestinationBank(localWireTransferBank);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.error = 12019;
      return this.taskErrorURL;
    }
    return str;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.bankId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetAffiliateBankInfo
 * JD-Core Version:    0.7.0.1
 */