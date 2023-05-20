package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAssignedBanks
  extends BaseTask
  implements BankEmployeeTask
{
  private String tQ = "ModifiedBankEmployee";
  private String tO = "AffiliateBanks";
  private String tP = "AssignedBanks";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute(this.tQ);
    if (localBankEmployee == null)
    {
      str = this.taskErrorURL;
      this.error = 5502;
      return str;
    }
    AffiliateBanks localAffiliateBanks1 = (AffiliateBanks)localHttpSession.getAttribute(this.tO);
    if (localAffiliateBanks1 == null)
    {
      str = this.taskErrorURL;
      this.error = 5530;
      return str;
    }
    str = this.successURL;
    AffiliateBanks localAffiliateBanks2 = new AffiliateBanks();
    Iterator localIterator = localBankEmployee.getAffiliateBankIds().iterator();
    while (localIterator.hasNext()) {
      try
      {
        int i = Integer.parseInt((String)localIterator.next());
        AffiliateBank localAffiliateBank = localAffiliateBanks1.getAffiliateBankByAffiliateBankID(i);
        if (localAffiliateBank != null)
        {
          localAffiliateBanks2.add(localAffiliateBank);
          localAffiliateBanks1.removeByAffiliateBankID(i);
        }
      }
      catch (Exception localException) {}
    }
    localHttpSession.setAttribute(this.tP, localAffiliateBanks2);
    return str;
  }
  
  public void setBankEmployeeSessionName(String paramString)
  {
    this.tQ = paramString;
  }
  
  public void setAffiliateBanksSessionName(String paramString)
  {
    this.tO = paramString;
  }
  
  public void setAssignedBanksSessionName(String paramString)
  {
    this.tP = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetAssignedBanks
 * JD-Core Version:    0.7.0.1
 */