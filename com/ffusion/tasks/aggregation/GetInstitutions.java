package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetInstitutions
  extends BaseTask
  implements Task
{
  private String iy = "Agg_Institutions_Banking";
  private String iz = "Agg_Institutions_CreditCards";
  private String iv = "Agg_Institutions_Investements";
  private String iB = "Agg_Institutions_Rewards";
  private String ix = "com.ffusion.services.AccountAggregation";
  private boolean iA = false;
  private String iw = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.ix);
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    Institutions localInstitutions1 = (Institutions)localServletContext.getAttribute(getInstitutionsContextName(String.valueOf(1)));
    Institutions localInstitutions2 = (Institutions)localServletContext.getAttribute(getInstitutionsContextName(String.valueOf(2)));
    Institutions localInstitutions3 = (Institutions)localServletContext.getAttribute(getInstitutionsContextName(String.valueOf(3)));
    Institutions localInstitutions4 = (Institutions)localServletContext.getAttribute(getInstitutionsContextName(String.valueOf(4)));
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((localInstitutions1 == null) || (this.iA)) {
      str = getInstitutions(localSecureUser, localServletContext, localAccountAggregation, localLocale, String.valueOf(1));
    }
    if ((this.error == 0) && ((localInstitutions2 == null) || (this.iA))) {
      str = getInstitutions(localSecureUser, localServletContext, localAccountAggregation, localLocale, String.valueOf(2));
    }
    if ((this.error == 0) && ((localInstitutions3 == null) || (this.iA))) {
      str = getInstitutions(localSecureUser, localServletContext, localAccountAggregation, localLocale, String.valueOf(3));
    }
    if ((this.error == 0) && ((localInstitutions4 == null) || (this.iA))) {
      str = getInstitutions(localSecureUser, localServletContext, localAccountAggregation, localLocale, String.valueOf(4));
    }
    if (this.error == 0) {
      this.iA = false;
    }
    return str;
  }
  
  public String getInstitutions(SecureUser paramSecureUser, ServletContext paramServletContext, com.ffusion.services.AccountAggregation paramAccountAggregation, Locale paramLocale, String paramString)
  {
    String str = this.successURL;
    Institutions localInstitutions = new Institutions(paramLocale);
    Institution localInstitution = localInstitutions.create();
    localInstitution.setType(paramString);
    HashMap localHashMap = new HashMap();
    if (paramAccountAggregation != null) {
      localHashMap.put("SERVICE", paramAccountAggregation);
    }
    localHashMap.put("INSTITUTIONS", localInstitutions);
    this.error = 0;
    try
    {
      localInstitutions = com.ffusion.csil.core.AccountAggregation.getInstitutions(paramSecureUser, localInstitution, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      paramServletContext.setAttribute(getInstitutionsContextName(paramString), localInstitutions);
    }
    return this.successURL;
  }
  
  public void setInstitutionsReload(String paramString)
  {
    this.iA = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAggBankingInstitutionsName(String paramString)
  {
    this.iy = paramString;
  }
  
  public void setAggCreditCardsInstitutionsName(String paramString)
  {
    this.iz = paramString;
  }
  
  public void setAggInvestmentsInstitutionsName(String paramString)
  {
    this.iv = paramString;
  }
  
  public void setAggRewardsInstitutionsName(String paramString)
  {
    this.iB = paramString;
  }
  
  public void setInstitutionsContextName(String paramString1, String paramString2)
  {
    if (paramString1.equals(String.valueOf(1))) {
      setAggBankingInstitutionsName(paramString2);
    } else if (paramString1.equals(String.valueOf(2))) {
      setAggCreditCardsInstitutionsName(paramString2);
    } else if (paramString1.equals(String.valueOf(3))) {
      setAggInvestmentsInstitutionsName(paramString2);
    } else if (paramString1.equals(String.valueOf(4))) {
      setAggRewardsInstitutionsName(paramString2);
    }
  }
  
  public void setInstitutionsContextName(String paramString)
  {
    String str = getType();
    setInstitutionsContextName(str, paramString);
  }
  
  public String getAggBankingInstitutionsName()
  {
    return this.iy;
  }
  
  public String getAggCreditCardsInstitutionsName()
  {
    return this.iz;
  }
  
  public String getAggInvestmentsInstitutionsName()
  {
    return this.iv;
  }
  
  public String getAggRewardsInstitutionsName()
  {
    return this.iB;
  }
  
  public String getInstitutionsContextName(String paramString)
  {
    String str = "";
    if (paramString.equals(String.valueOf(1))) {
      str = getAggBankingInstitutionsName();
    } else if (paramString.equals(String.valueOf(2))) {
      str = getAggCreditCardsInstitutionsName();
    } else if (paramString.equals(String.valueOf(3))) {
      str = getAggInvestmentsInstitutionsName();
    } else if (paramString.equals(String.valueOf(4))) {
      str = getAggRewardsInstitutionsName();
    }
    return str;
  }
  
  public String getInstitutionsContextName()
  {
    String str = getType();
    return getInstitutionsContextName(str);
  }
  
  public void setServiceName(String paramString)
  {
    this.ix = paramString;
  }
  
  public String getServiceName()
  {
    return this.ix;
  }
  
  public void setType(String paramString)
  {
    this.iw = paramString;
  }
  
  public String getType()
  {
    return this.iw;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.GetInstitutions
 * JD-Core Version:    0.7.0.1
 */