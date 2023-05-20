package com.ffusion.tasks.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BPTW;
import com.ffusion.services.bptw.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransfers
  extends BaseTask
  implements Task
{
  private String kt = "";
  private int kw = 0;
  private boolean ky = false;
  private String ku = "";
  private String kx = "";
  private String kv = null;
  private String ks = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Banking localBanking = (Banking)localHttpSession.getAttribute("BptwBanking");
    User localUser = (User)localHttpSession.getAttribute("User");
    if (localUser == null)
    {
      this.error = 17006;
      return this.taskErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    Accounts localAccounts = new Accounts(localLocale);
    Account localAccount = localAccounts.create(localBanking.getBankID(), this.kt, this.kw);
    localAccount.setFilterable("TransferFrom");
    String str = this.kv;
    if ((this.kv == null) || (this.kv.trim().equals(""))) {
      str = localUser.getId();
    }
    HashMap localHashMap = null;
    if (localBanking != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBanking);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      BPTW.signOnBanking(localSecureUser, str, "0", localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    localHashMap = new HashMap();
    localHashMap.put("User", localUser);
    Object localObject;
    if (this.ky)
    {
      localObject = new RecTransfers(localLocale);
      if (localBanking != null) {
        localHashMap.put("SERVICE", localBanking);
      }
      localHashMap.put("RECTRANSFERS", localObject);
      try
      {
        localObject = BPTW.getRecTransfers(localSecureUser, localAccounts, localHashMap);
        if (this.ks != null) {
          ((RecTransfers)localObject).setDateFormat(this.ks);
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return this.serviceErrorURL;
      }
      localHttpSession.setAttribute("RecTransfers", localObject);
      this.successURL = this.ku;
    }
    else
    {
      localObject = new Transfers(localLocale);
      if (localBanking != null) {
        localHashMap.put("SERVICE", localBanking);
      }
      localHashMap.put("TRANSFERS", localObject);
      try
      {
        localObject = BPTW.getTransfers(localSecureUser, localAccounts, localHashMap);
        if (this.ks != null) {
          ((Transfers)localObject).setDateFormat(this.ks);
        }
      }
      catch (CSILException localCSILException3)
      {
        this.error = MapError.mapError(localCSILException3);
        return this.serviceErrorURL;
      }
      localHttpSession.setAttribute("Transfers", localObject);
      this.successURL = this.kx;
    }
    if (this.error != 0) {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setAccountNum(String paramString)
  {
    this.kt = paramString;
  }
  
  public String getAccountNum()
  {
    return this.kt;
  }
  
  public void setAccountType(String paramString)
  {
    try
    {
      this.kw = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setAccountType(int paramInt)
  {
    this.kw = paramInt;
  }
  
  public String getAccountType()
  {
    return String.valueOf(this.kw);
  }
  
  public int getAccountTypeValue()
  {
    return this.kw;
  }
  
  public void setRecurring(String paramString)
  {
    if (paramString.equalsIgnoreCase("true")) {
      this.ky = true;
    } else {
      this.ky = false;
    }
  }
  
  public void setRecurring(boolean paramBoolean)
  {
    this.ky = paramBoolean;
  }
  
  public String getRecurring()
  {
    if (this.ky) {
      return "true";
    }
    return "false";
  }
  
  public boolean getRecurringValue()
  {
    return this.ky;
  }
  
  public void setRecurringURL(String paramString)
  {
    this.ku = paramString;
  }
  
  public String getRecurringURL()
  {
    return this.ku;
  }
  
  public void setNonrecurringURL(String paramString)
  {
    this.kx = paramString;
  }
  
  public String getNonrecurringURL()
  {
    return this.kx;
  }
  
  public void setUserId(String paramString)
  {
    this.kv = paramString;
  }
  
  public String getUserId()
  {
    return this.kv;
  }
  
  public void setDateFormat(String paramString)
  {
    String str = paramString == null ? null : paramString.trim();
    if ((str == null) || (str.length() == 0)) {
      this.ks = null;
    } else {
      this.ks = str;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.GetTransfers
 * JD-Core Version:    0.7.0.1
 */