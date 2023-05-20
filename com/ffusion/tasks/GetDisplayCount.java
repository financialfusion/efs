package com.ffusion.tasks;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Accounts;
import com.ffusion.csil.core.Applications;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Business;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Wire;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDisplayCount
  extends BaseTask
{
  private String aQy = "DisplayCount";
  private String aQx = "MaxResultCount";
  public static String BUSINESS = "BUSINESS";
  public static String USER = "USER";
  public static String CONSUMER = "CONSUMER";
  public static String CASE = "CASE";
  public static String APPLICATION = "APPLICATION";
  public static String EMPLOYEE = "EMPLOYEE";
  public static String ACH = "ACH";
  public static String ACHPAYEE = "ACHPAYEE";
  public static String ACCOUNT = "ACCOUNT";
  public static String CASHCON = "CASHCON";
  public static String WIRETEMPLATE = "WIRETEMPLATE";
  public static String ADMINGLOBALPAYEES = "ADMIN_GLOBALPAYEES";
  public static String GLOBALPAYEES = "GLOBALPAYEES";
  private String aQz = null;
  
  public void setSearchTypeName(String paramString)
  {
    this.aQz = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str2 = "";
    String str3 = "";
    try
    {
      if (this.aQz != null) {
        if (this.aQz.equals(WIRETEMPLATE))
        {
          str2 = Wire.getWireTemplateResultSize();
          str3 = Wire.getWireTemplateResultSize();
        }
        else if (this.aQz.equals(BUSINESS))
        {
          str2 = Business.getDisplayCount(localSecureUser);
          str3 = Business.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(USER))
        {
          str2 = UserAdmin.getUserDisplayCount(localSecureUser);
          str3 = UserAdmin.getUserMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(CONSUMER))
        {
          str2 = UserAdmin.getConsumerDisplayCount(localSecureUser);
          str3 = UserAdmin.getConsumerMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(CASE))
        {
          str2 = Messages.getDisplayCount(localSecureUser);
          str3 = Messages.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(APPLICATION))
        {
          str2 = Applications.getDisplayCount(localSecureUser);
          str3 = Applications.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(EMPLOYEE))
        {
          str2 = BankEmployeeAdmin.getDisplayCount(localSecureUser);
          str3 = BankEmployeeAdmin.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(ACH))
        {
          str2 = ACH.getDisplayCount(localSecureUser);
          str3 = ACH.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(ACHPAYEE))
        {
          str2 = ACH.getDisplayPayeeCount(localSecureUser);
          str3 = ACH.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(ACCOUNT))
        {
          str2 = Accounts.getAccountDisplaySize();
          str3 = Accounts.getAccountMaxResultSize();
        }
        else if (this.aQz.equals(CASHCON))
        {
          str2 = CashCon.getDisplayCount(localSecureUser);
          str3 = CashCon.getMaxResultCount(localSecureUser);
        }
        else if (this.aQz.equals(ADMINGLOBALPAYEES))
        {
          str2 = PaymentsAdmin.getGlobalPayeeDisplayCount(localSecureUser);
        }
        else if (this.aQz.equals(GLOBALPAYEES))
        {
          str2 = Billpay.getGlobalPayeeDisplayCount(localSecureUser);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aQy, str2);
      localHttpSession.setAttribute(this.aQx, str3);
      str1 = this.successURL;
    }
    return str1;
  }
  
  public void setDisplayCountName(String paramString)
  {
    this.aQy = paramString;
  }
  
  public void setMaxResultCountName(String paramString)
  {
    this.aQx = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.GetDisplayCount
 * JD-Core Version:    0.7.0.1
 */