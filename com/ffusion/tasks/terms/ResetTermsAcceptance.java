package com.ffusion.tasks.terms;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Terms;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResetTermsAcceptance
  extends BaseTask
  implements TermsTask
{
  protected AffiliateBanks _resetAffiliateBanks = new AffiliateBanks();
  protected String _affiliateBanksListName = "CheckAffiliateBanks";
  protected boolean _processFlag = false;
  protected boolean _validateFlag = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute("AffiliateBanks");
    ArrayList localArrayList = (ArrayList)localHttpSession.getAttribute(this._affiliateBanksListName);
    if (localSecureUser == null) {
      this.error = 38;
    }
    for (int i = 0; i < localArrayList.size(); i++)
    {
      String str2 = (String)localArrayList.get(i);
      AffiliateBank localAffiliateBank = localAffiliateBanks.getByID(Integer.valueOf(str2).intValue());
      if (localAffiliateBank != null) {
        this._resetAffiliateBanks.add(localAffiliateBank);
      } else {
        this.error = 91005;
      }
    }
    if (this._validateFlag) {
      validateInput(localSecureUser);
    }
    if (this.error == 0)
    {
      if (this._processFlag) {
        try
        {
          Terms.resetTermsAcceptance(localSecureUser, this._resetAffiliateBanks, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
      }
    }
    else {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setAffiliateBanksListName(String paramString)
  {
    this._affiliateBanksListName = paramString;
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this._validateFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validateInput(SecureUser paramSecureUser)
  {
    boolean bool = false;
    if ((this._resetAffiliateBanks != null) && (this._resetAffiliateBanks.getSize() != "0")) {
      for (int i = 0; i < this._resetAffiliateBanks.size(); i++)
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)this._resetAffiliateBanks.get(i);
        String str = String.valueOf(localAffiliateBank.getAffiliateBankID());
        if ((str == null) || (str.equals("")) || (str.equals("0"))) {
          this.error = 91005;
        }
      }
    } else {
      this.error = 91004;
    }
    if (this.error == 0) {
      bool = true;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.terms.ResetTermsAcceptance
 * JD-Core Version:    0.7.0.1
 */