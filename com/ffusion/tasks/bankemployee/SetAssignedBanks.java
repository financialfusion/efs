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

public class SetAssignedBanks
  extends BaseTask
  implements BankEmployeeTask
{
  private String uw = "ModifiedBankEmployee";
  private String uv = "AssignedBanks";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute(this.uw);
    if (localBankEmployee == null)
    {
      str = this.taskErrorURL;
      this.error = 5502;
      return str;
    }
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute(this.uv);
    if (localAffiliateBanks == null)
    {
      str = this.taskErrorURL;
      this.error = 5529;
      return str;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localAffiliateBanks.iterator();
    while (localIterator.hasNext())
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
      localArrayList.add(Integer.toString(localAffiliateBank.getAffiliateBankID()));
    }
    localBankEmployee.setAffiliateBankIds(localArrayList);
    str = this.successURL;
    return str;
  }
  
  public void setBankEmployeeSessionName(String paramString)
  {
    this.uw = paramString;
  }
  
  public void setAssignedBanksSessionName(String paramString)
  {
    this.uv = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.SetAssignedBanks
 * JD-Core Version:    0.7.0.1
 */