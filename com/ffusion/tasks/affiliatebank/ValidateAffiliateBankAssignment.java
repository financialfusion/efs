package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateAffiliateBankAssignment
  extends BaseTask
  implements AffiliateBankTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localHttpSession.removeAttribute("TempAssignedBanks");
    AffiliateBanks localAffiliateBanks1 = new AffiliateBanks();
    AffiliateBank localAffiliateBank1 = (AffiliateBank)localHttpSession.getAttribute("CurrentAffiliateBank");
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("AddBankEmployee");
    AffiliateBanks localAffiliateBanks2 = (AffiliateBanks)localHttpSession.getAttribute("AffiliateBanks");
    if (localAffiliateBank1 != null)
    {
      localAffiliateBanks1.add(localAffiliateBank1);
      if (localBankEmployee != null) {
        localBankEmployee.setDefaultAffiliateBankId(localAffiliateBank1.getId());
      }
      AffiliateBankAssignment localAffiliateBankAssignment = new AffiliateBankAssignment();
      if (localAffiliateBanks2 != null)
      {
        Iterator localIterator = localAffiliateBanks2.iterator();
        AffiliateBank localAffiliateBank2 = null;
        String str = localAffiliateBank1.getId();
        while (localIterator.hasNext())
        {
          localAffiliateBank2 = (AffiliateBank)localIterator.next();
          if (localAffiliateBank2.getId().equals(str)) {
            localIterator.remove();
          }
        }
      }
      localAffiliateBankAssignment.synchLists(localHttpSession);
    }
    localHttpSession.setAttribute("TempAssignedBanks", localAffiliateBanks1);
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.ValidateAffiliateBankAssignment
 * JD-Core Version:    0.7.0.1
 */