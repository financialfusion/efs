package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCutOffDefinition
  extends BaseTask
  implements AffiliateBankTask
{
  private int aPT;
  private int aPV;
  private String aPU = "AffiliateBank_CutOff_Definition";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    CutOffDefinition localCutOffDefinition = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localCutOffDefinition = AffiliateBankAdmin.getCutOffDefinition(localSecureUser, this.aPT, this.aPV, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      if (localCutOffDefinition != null) {
        localHttpSession.setAttribute(this.aPU, localCutOffDefinition);
      }
      str = this.successURL;
    }
    return str;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.aPT = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAffiliateBankIDValue(int paramInt)
  {
    this.aPT = paramInt;
  }
  
  public String getAffiliateBankID()
  {
    return String.valueOf(this.aPT);
  }
  
  public int getAffiliateBankIDValue()
  {
    return this.aPT;
  }
  
  public void setCutOffType(String paramString)
  {
    try
    {
      this.aPV = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setCutOffTypeValue(int paramInt)
  {
    this.aPV = paramInt;
  }
  
  public String getCutOffType()
  {
    return String.valueOf(this.aPV);
  }
  
  public int getCutOffTypeValue()
  {
    return this.aPV;
  }
  
  public void setSessionName(String paramString)
  {
    this.aPU = paramString;
  }
  
  public String getSessionName()
  {
    return this.aPU;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetCutOffDefinition
 * JD-Core Version:    0.7.0.1
 */