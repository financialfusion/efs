package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.TreasuryDirect;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FFIStringTokenizer;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMasterSubStatistics
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOC = "SearchAccountsCollection";
  private String aOA = "currentAccounts";
  private String aOD = "Business";
  private HashMap aOB = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Accounts localAccounts1 = null;
    Business localBusiness = null;
    String str2 = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts1 = (Accounts)localHttpSession.getAttribute(this.aOC);
    localBusiness = (Business)localHttpSession.getAttribute(this.aOD);
    str2 = (String)localHttpSession.getAttribute(this.aOA);
    if (localSecureUser == null)
    {
      this.error = 38;
      str1 = this.taskErrorURL;
    }
    else if (localAccounts1 == null)
    {
      this.error = 80106;
      str1 = this.taskErrorURL;
    }
    else if (localBusiness == null)
    {
      this.error = 74;
      str1 = this.taskErrorURL;
    }
    else if (str2 == null)
    {
      this.error = 80107;
      str1 = this.taskErrorURL;
    }
    else
    {
      Accounts localAccounts2 = new Accounts(localAccounts1.getLocale());
      if (str2.indexOf("AllAccounts") != -1)
      {
        localAccounts2 = localAccounts1;
      }
      else
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",", false);
        while (localStringTokenizer.hasMoreTokens())
        {
          String str3 = localStringTokenizer.nextToken();
          FFIStringTokenizer localFFIStringTokenizer = new FFIStringTokenizer(str3, ":");
          int i = localFFIStringTokenizer.countTokens();
          if (i < 2)
          {
            this.error = 80108;
            return str1;
          }
          Account localAccount = new Account();
          localAccount.setID(localFFIStringTokenizer.nextToken());
          localAccount.setBankID(localFFIStringTokenizer.nextToken());
          if (i >= 3) {
            localAccount.setRoutingNum(localFFIStringTokenizer.nextToken());
          }
          if (i >= 4) {
            localAccount.setNickName(localFFIStringTokenizer.nextToken());
          }
          if (i >= 5) {
            localAccount.setCurrencyCode(localFFIStringTokenizer.nextToken());
          }
          int j = localAccounts1.indexOf(localAccount);
          if (j != -1) {
            localAccounts2.add(localAccounts1.get(j));
          }
        }
      }
      try
      {
        this.aOB = TreasuryDirect.getMasterSubStatistics(localSecureUser, localBusiness, localAccounts2, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public String getAvailableAccountsSessionName()
  {
    return this.aOC;
  }
  
  public void setAvailableAccountsSessionName(String paramString)
  {
    this.aOC = paramString;
  }
  
  public String getBusinessSessionName()
  {
    return this.aOD;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    this.aOD = paramString;
  }
  
  public String getSelectedAccountsSessionName()
  {
    return this.aOA;
  }
  
  public void setSelectedAccountsSessionName(String paramString)
  {
    this.aOA = paramString;
  }
  
  public int getNumMasterAccounts()
  {
    if (this.aOB == null) {
      return -1;
    }
    Integer localInteger = (Integer)this.aOB.get("NumberOfMasterAccounts");
    return localInteger.intValue();
  }
  
  public int getNumSubAccounts()
  {
    if (this.aOB == null) {
      return -1;
    }
    Integer localInteger = (Integer)this.aOB.get("NumberOfSubAccounts");
    return localInteger.intValue();
  }
  
  public int getNumAccounts()
  {
    if (this.aOB == null) {
      return -1;
    }
    Integer localInteger = (Integer)this.aOB.get("NumberOfAccounts");
    return localInteger.intValue();
  }
  
  public boolean getIsAnyIncludeZBAInRollup()
  {
    if (this.aOB == null) {
      return false;
    }
    Boolean localBoolean = (Boolean)this.aOB.get("AnyIncludeZBAInRollup");
    return localBoolean.booleanValue();
  }
  
  public boolean getIsAnyAccountMasterOrSub()
  {
    if (this.aOB == null) {
      return false;
    }
    Boolean localBoolean = (Boolean)this.aOB.get("AnyAccountMasterOrSub");
    return localBoolean.booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.GetMasterSubStatistics
 * JD-Core Version:    0.7.0.1
 */