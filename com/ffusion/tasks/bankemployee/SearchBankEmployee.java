package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchBankEmployee
  extends BankEmployee
  implements BankEmployeeTask
{
  private int oJ;
  private String oK;
  private String oL = "TE";
  private String oI = "SE";
  public static final String ALL_BANKS = "-1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.oJ = 0;
    String str = this.oK;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.oJ = MapError.mapError(localCSILException);
      str = this.oI;
    }
    if ((this.oJ == 0) || (this.oJ == 12))
    {
      localHttpSession.setAttribute("EmployeeSearchList", localBankEmployees);
      str = this.oK;
    }
    return str;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    super.addAffiliateBankId(Integer.parseInt(paramString));
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.oL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.oI = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.oK = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.oJ);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.SearchBankEmployee
 * JD-Core Version:    0.7.0.1
 */