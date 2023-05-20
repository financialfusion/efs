package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAffiliateBank
  extends BaseTask
  implements AffiliateBankTask
{
  private int aPR = -1;
  private String aPS = "AffiliateBank";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute("AffiliateBanks");
    if (localAffiliateBanks == null)
    {
      this.error = 25505;
      str = this.taskErrorURL;
    }
    else
    {
      AffiliateBank localAffiliateBank = localAffiliateBanks.getAffiliateBankByAffiliateBankID(this.aPR);
      if (localAffiliateBank != null)
      {
        localHttpSession.setAttribute(this.aPS, localAffiliateBank);
      }
      else
      {
        this.error = 25504;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.aPR = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aPR = -1;
    }
  }
  
  public void setAffiliateBankSessionName(String paramString)
  {
    this.aPS = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SetAffiliateBank
 * JD-Core Version:    0.7.0.1
 */