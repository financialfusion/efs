package com.ffusion.tasks.banklookup;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankLookup;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditFinancialInstitution
  extends BaseTask
  implements BankLookupTask
{
  private boolean FZ = false;
  private boolean F0 = true;
  private boolean FU = false;
  private FinancialInstitution FV = new FinancialInstitution();
  private FinancialInstitutions FY = new FinancialInstitutions();
  private String FW = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    if (this.FZ == true)
    {
      FinancialInstitution localFinancialInstitution = (FinancialInstitution)localHttpSession.getAttribute("FinancialInstitutiontoModify");
      this.FY = ((FinancialInstitutions)localHttpSession.getAttribute("FinancialInstitutions"));
      if (localFinancialInstitution != null)
      {
        try
        {
          this.FV.set(localFinancialInstitution);
          this.FW = localFinancialInstitution.getAchRoutingNumber();
          if (this.FW.equals("000000000"))
          {
            this.FW = localFinancialInstitution.getWireRoutingNumber();
            this.FU = false;
          }
          else
          {
            this.FU = true;
          }
        }
        catch (Exception localException)
        {
          this.error = 32002;
          str = this.taskErrorURL;
        }
        this.FZ = false;
      }
      else
      {
        str = this.taskErrorURL;
        this.error = 32001;
      }
    }
    else if (validateInput())
    {
      boolean bool = false;
      try
      {
        bool = p(localHttpSession);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str = this.serviceErrorURL;
      }
      if (bool)
      {
        if (this.F0)
        {
          this.F0 = false;
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          HashMap localHashMap = new HashMap();
          try
          {
            this.FV = BankLookup.updateFinancialInstitution(localSecureUser, this.FV, localHashMap);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str = this.serviceErrorURL;
          }
          int j = this.FV.getInstitutionId();
          for (int k = 0; k < this.FY.size(); k++) {
            if (((FinancialInstitution)this.FY.get(k)).getInstitutionId() == j)
            {
              this.FY.set(k, this.FV);
              break;
            }
          }
          localHttpSession.setAttribute("FinancialInstitutions", this.FY);
        }
      }
      else {
        str = this.taskErrorURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput()
  {
    String str = this.FV.getInstitutionName();
    if ((str == null) || (str.length() == 0)) {
      this.error = 32003;
    } else if (str.length() > 64) {
      this.error = 32004;
    }
    str = this.FV.getStreet();
    if ((str != null) && (str.length() > 144)) {
      this.error = 32007;
    }
    str = this.FV.getStreet2();
    if ((str != null) && (str.length() > 144)) {
      this.error = 32008;
    }
    str = this.FV.getStreet3();
    if ((str != null) && (str.length() > 144)) {
      this.error = 32009;
    }
    str = this.FV.getCity();
    if ((str == null) || (str.length() == 0)) {
      this.error = 32005;
    } else if (str.length() > 64) {
      this.error = 32006;
    }
    str = this.FV.getState();
    str = this.FV.getCountry();
    if ((str == null) || (str.length() == 0)) {
      this.error = 32011;
    }
    str = this.FV.getZipCode();
    if ((str != null) && (str.length() > 32)) {
      this.error = 32012;
    }
    str = this.FV.getPhone();
    if ((str != null) && (str.length() > 32)) {
      this.error = 32013;
    }
    try
    {
      if (this.FU) {
        this.FV.setAchRoutingNumber(this.FW);
      } else {
        this.FV.setWireRoutingNumber(this.FW);
      }
    }
    catch (Exception localException)
    {
      this.error = 32014;
    }
    str = this.FV.getChipsUID();
    if ((str != null) && (str.length() > 32)) {
      this.error = 32015;
    }
    str = this.FV.getNationalID();
    if ((str != null) && (str.length() > 25)) {
      this.error = 32016;
    }
    str = this.FV.getSwiftBIC();
    if ((str != null) && (str.length() > 11)) {
      this.error = 32017;
    }
    return this.error == 0;
  }
  
  private boolean p(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str1 = Util.getCodeForBankLookupCountryName(localSecureUser, this.FV.getCountry(), null);
    String str2 = Util.validateZipCodeFormat(localSecureUser, str1, this.FV.getZipCode(), new HashMap());
    if (str2 == null)
    {
      this.error = 26;
      bool = false;
    }
    return bool;
  }
  
  public void setBankName(String paramString)
  {
    this.FV.setInstitutionName(paramString);
  }
  
  public String getBankName()
  {
    return this.FV.getInstitutionName();
  }
  
  public void setAddressLine1(String paramString)
  {
    this.FV.setStreet(paramString);
  }
  
  public String getAddressLine1()
  {
    return this.FV.getStreet();
  }
  
  public void setAddressLine2(String paramString)
  {
    this.FV.setStreet2(paramString);
  }
  
  public String getAddressLine2()
  {
    return this.FV.getStreet2();
  }
  
  public void setAddressLine3(String paramString)
  {
    this.FV.setStreet3(paramString);
  }
  
  public String getAddressLine3()
  {
    return this.FV.getStreet3();
  }
  
  public void setCity(String paramString)
  {
    this.FV.setCity(paramString);
  }
  
  public String getCity()
  {
    return this.FV.getCity();
  }
  
  public void setState(String paramString)
  {
    if ((!paramString.equals("None")) && (!paramString.equals("Unknown"))) {
      this.FV.setState(paramString);
    }
  }
  
  public String getState()
  {
    return this.FV.getState();
  }
  
  public String getStateCode()
  {
    return this.FV.getStateCode();
  }
  
  public void setStateCode(String paramString)
  {
    if ((!paramString.equals("None")) && (!paramString.equals("Unknown"))) {
      this.FV.setStateCode(paramString);
    }
  }
  
  public void setCountry(String paramString)
  {
    if ((!paramString.equals("None")) && (!paramString.equals("Unknown"))) {
      this.FV.setCountry(paramString);
    }
  }
  
  public String getCountry()
  {
    return this.FV.getCountry();
  }
  
  public void setPostalCode(String paramString)
  {
    this.FV.setZipCode(paramString);
  }
  
  public String getPostalCode()
  {
    return this.FV.getZipCode();
  }
  
  public void setPhone(String paramString)
  {
    this.FV.setPhone(paramString);
  }
  
  public String getPhone()
  {
    return this.FV.getPhone();
  }
  
  public void setFEDABA(String paramString)
  {
    this.FW = paramString;
  }
  
  public String getFEDABA()
  {
    return this.FW;
  }
  
  public void setChipsUID(String paramString)
  {
    this.FV.setChipsUID(paramString);
  }
  
  public String getChipsUID()
  {
    return this.FV.getChipsUID();
  }
  
  public void setNationalID(String paramString)
  {
    this.FV.setNationalID(paramString);
  }
  
  public String getNationalID()
  {
    return this.FV.getNationalID();
  }
  
  public void setSwiftBIC(String paramString)
  {
    this.FV.setSwiftBIC(paramString);
  }
  
  public String getSwiftBIC()
  {
    return this.FV.getSwiftBIC();
  }
  
  public void setInit(String paramString)
  {
    this.FZ = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banklookup.EditFinancialInstitution
 * JD-Core Version:    0.7.0.1
 */