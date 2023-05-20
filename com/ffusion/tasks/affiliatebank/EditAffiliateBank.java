package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditAffiliateBank
  extends AddAffiliateBank
{
  boolean aP2 = false;
  boolean aP1 = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.aP2)
    {
      this.theBank = ((AffiliateBank)localHttpSession.getAttribute("AffiliateBank"));
      if (this.theBank == null)
      {
        this.error = 25504;
        str = this.taskErrorURL;
      }
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        convertEmptyStringsToNull(this.theBank);
        if (validateAffiliateBank(localHttpSession))
        {
          AffiliateBankAdmin.modifyAffiliateBank(localSecureUser, this.theBank, localHashMap);
          AffiliateBankAdmin.updateVirtualUserAndETFCompany(localSecureUser, this.theBank, localHashMap);
          if (this.error == 0) {
            str = this.successURL;
          }
        }
        else if (this.error == 0)
        {
          str = this._taskWarningURL;
        }
        else
        {
          str = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, localHttpSession);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    this.aP2 = bool;
    this.aP1 = (!bool);
  }
  
  public void setProcess(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    this.aP1 = bool;
    this.aP2 = (!bool);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.EditAffiliateBank
 * JD-Core Version:    0.7.0.1
 */