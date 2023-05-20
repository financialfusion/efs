package com.ffusion.tasks.ach;

import com.ffusion.banklookup.FinancialInstitutionException;
import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.WireUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHParticipantBanks
  extends BaseTask
{
  protected static boolean reload = false;
  protected String achBanks = "ACHParticipantBanks";
  private FinancialInstitution UJ = null;
  protected String searchBankRTN = "";
  private String UK = "BANK_LOOKUP_SETTINGS_MAXIMUM_MATCHES";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FinancialInstitutions localFinancialInstitutions = (FinancialInstitutions)localHttpSession.getAttribute(this.achBanks);
    String str1 = this.successURL;
    this.error = 0;
    if (reload)
    {
      localFinancialInstitutions = null;
      localHttpSession.removeAttribute(this.achBanks);
      reload = false;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    int i = 10;
    String str2 = (String)localHttpSession.getAttribute(this.UK);
    try
    {
      i = Integer.parseInt(str2);
    }
    catch (Exception localException) {}
    try
    {
      this.UJ.set("NON_NULL_ROUTING_NUMBER", "fi.AchRoutingNumber");
      if (!WireUtil.isNullOrEmpty(this.searchBankRTN)) {
        this.UJ.setAchRoutingNumberForSearch(this.searchBankRTN);
      }
      localFinancialInstitutions = ACH.getFinancialInstitutions(localSecureUser, this.UJ, i, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    if (localFinancialInstitutions == null) {
      localFinancialInstitutions = new FinancialInstitutions();
    }
    Collections.sort(localFinancialInstitutions);
    localHttpSession.setAttribute(this.achBanks, localFinancialInstitutions);
    this.UJ = new FinancialInstitution();
    return str1;
  }
  
  public void setReload(String paramString)
  {
    reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setBanksName(String paramString)
  {
    this.achBanks = paramString;
  }
  
  public void setBankName(String paramString)
  {
    this.UJ.setInstitutionName(paramString);
  }
  
  public void setBankCity(String paramString)
  {
    this.UJ.setCity(paramString);
  }
  
  public void setBankState(String paramString)
  {
    this.UJ.setStateCode(paramString);
  }
  
  public void setBankCountry(String paramString)
  {
    this.UJ.setCountry(paramString);
  }
  
  public void setAchRTN(String paramString)
    throws FinancialInstitutionException
  {
    this.searchBankRTN = paramString;
  }
  
  public void setWireRTN(String paramString)
    throws FinancialInstitutionException
  {
    this.UJ.setWireRoutingNumberForSearch(paramString);
  }
  
  public void setSwiftRTN(String paramString)
  {
    this.UJ.setSwiftBIC(paramString);
  }
  
  public void setChipsRTN(String paramString)
  {
    this.UJ.setChipsUID(paramString);
  }
  
  public void setNationalID(String paramString)
  {
    this.UJ.setNationalID(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHParticipantBanks
 * JD-Core Version:    0.7.0.1
 */