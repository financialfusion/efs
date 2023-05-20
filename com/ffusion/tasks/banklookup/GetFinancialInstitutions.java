package com.ffusion.tasks.banklookup;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankLookup;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFinancialInstitutions
  extends BaseTask
  implements BankLookupTask
{
  private FinancialInstitution FN = new FinancialInstitution();
  private int FM = 10;
  private String FO;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    FinancialInstitutions localFinancialInstitutions = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str2 = (String)localHttpSession.getAttribute("AddingGlobalPayee");
    if (str2 != null) {
      localHashMap.put("addingGlobalPayee", str2);
    }
    try
    {
      if ((this.FO != null) && (this.FO.length() > 0))
      {
        this.FN.setAchRoutingNumberForSearch(this.FO);
        this.FN.setWireRoutingNumberForSearch(this.FO);
      }
    }
    catch (Exception localException)
    {
      this.error = 32014;
      str1 = this.taskErrorURL;
    }
    try
    {
      localFinancialInstitutions = BankLookup.getFinancialInstitutions(localSecureUser, this.FN, this.FM, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("FinancialInstitutions", localFinancialInstitutions);
      str1 = this.successURL;
    }
    return str1;
  }
  
  public void setBankName(String paramString)
  {
    this.FN.setInstitutionName(paramString);
  }
  
  public String getBankName()
  {
    return this.FN.getInstitutionName();
  }
  
  public void setCity(String paramString)
  {
    this.FN.setCity(paramString);
  }
  
  public String getCity()
  {
    return this.FN.getCity();
  }
  
  public void setState(String paramString)
  {
    this.FN.setStateCode(paramString);
  }
  
  public String getState()
  {
    return this.FN.getStateCode();
  }
  
  public void setCountry(String paramString)
  {
    this.FN.setCountry(paramString);
  }
  
  public String getCountry()
  {
    return this.FN.getCountry();
  }
  
  public void setFEDABA(String paramString)
  {
    this.FO = paramString;
  }
  
  public String getFEDABA()
  {
    return this.FO;
  }
  
  public void setChipsUID(String paramString)
  {
    this.FN.setChipsUID(paramString);
  }
  
  public String getChipsUID()
  {
    return this.FN.getChipsUID();
  }
  
  public void setNationalID(String paramString)
  {
    this.FN.setNationalID(paramString);
  }
  
  public String getNationalID()
  {
    return this.FN.getNationalID();
  }
  
  public void setSwiftBIC(String paramString)
  {
    this.FN.setSwiftBIC(paramString);
  }
  
  public String getSwiftBIC()
  {
    return this.FN.getSwiftBIC();
  }
  
  public void setMaximumMatches(String paramString)
  {
    try
    {
      this.FM = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getMaximumMatches()
  {
    return String.valueOf(this.FM);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banklookup.GetFinancialInstitutions
 * JD-Core Version:    0.7.0.1
 */