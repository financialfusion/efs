package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.Register;
import com.ffusion.csil.core.StatementData;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetupAccounts
  extends BaseTask
  implements Task
{
  public static final String HIDE = "HIDE";
  public static final String SHOW_ACCOUNT = "0";
  public static final String HIDE_ACCOUNT = "1";
  public static final String REG_ENABLED = "REG_ENABLED";
  public static final String REG_DEFAULT = "REG_DEFAULT";
  private static final String jE = "on";
  protected String _accountsName = "Accounts";
  protected int MAX_ACCOUNT_NICKNAME_LENGTH = 40;
  private String jz = "";
  private static String jD = "register";
  private static String ju = "active";
  private static String jJ = "statements";
  private static String jI = "nickname";
  private static String jK = "Acct";
  private Entitlement jx = new Entitlement("Account Register", null, null);
  private Entitlement jw = new Entitlement("OnlineStatement", null, null);
  private Entitlement jy = new Entitlement("Account Setup", null, null);
  private static final String jB = "com.ffusion.beans.accounts.resources";
  private static final String jC = "Account_Activated";
  private static final String jG = "Account_Deactivated";
  private static final String jN = "com.ffusion.beans.accounts.resources";
  private static final String jM = "Account_Activated";
  private static final String jA = "Account_Deactivated";
  private static final String js = "Account_Activated_For_Register";
  private static final String jv = "Account_Deactivated_For_Register";
  private static final String jr = "Account_Activated_For_Statements";
  private static final String jH = "Account_Deactivated_For_Statements";
  private static final String jt = "Account_Nickname_Changed";
  private static final String jF = Account.class.getName();
  HistoryTracker jL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      boolean bool1 = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), this.jy);
      if (!bool1)
      {
        this.error = 20001;
        return this.serviceErrorURL;
      }
    }
    catch (CSILException localCSILException1)
    {
      return this.serviceErrorURL;
    }
    com.ffusion.beans.accounts.Accounts localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this._accountsName);
    if ((localAccounts1 != null) && (!localAccounts1.isEmpty()))
    {
      String str1 = localAccounts1.getFilter();
      localAccounts1.setFilter("");
      this.jL = new HistoryTracker(localSecureUser, 1, localSecureUser.getId());
      if (validate(localAccounts1, localHttpSession))
      {
        com.ffusion.beans.accounts.Accounts localAccounts2 = localAccounts1.getCoreAccounts();
        com.ffusion.beans.accounts.Accounts localAccounts3 = null;
        int i = 0;
        int j = 0;
        try
        {
          localAccounts3 = StatementData.getAccountsForIStatement(localSecureUser);
          i = 1;
        }
        catch (CSILException localCSILException2) {}
        for (int k = 1; k <= 2; k++)
        {
          for (int m = 0; m < localAccounts2.size(); m++)
          {
            Account localAccount = (Account)localAccounts2.get(m);
            String str2 = localAccount.getNumberMasked();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = localAccount.getNumber();
            boolean bool2 = jdMethod_for(localHttpSession, localAccount, str2, (String)localAccount.get("HIDE"), localSecureUser, arrayOfObject);
            boolean bool3 = jdMethod_for(localHttpSession, localAccount, str2, localSecureUser, arrayOfObject);
            if (this.error != 0) {
              return this.taskErrorURL;
            }
            if ((bool2) || (bool3)) {
              try
              {
                com.ffusion.csil.core.Accounts.modifyAccount(localSecureUser, localAccount, null);
              }
              catch (CSILException localCSILException4) {}
            }
            if (i != 0) {
              jdMethod_for(localHttpSession, localAccounts3, localAccount, str2, localSecureUser, arrayOfObject);
            }
            j = (jdMethod_int(localHttpSession, localAccount, str2, localSecureUser, arrayOfObject)) || (j != 0) ? 1 : 0;
          }
          if (j != 0) {
            try
            {
              Register.transferDefaultCategories(localSecureUser, null);
            }
            catch (CSILException localCSILException3) {}
          }
          localAccounts2 = localAccounts1.getNonCoreAccounts();
          i = 0;
          j = 0;
        }
        localAccounts1.setFilter(str1);
        try
        {
          HistoryAdapter.addHistory(this.jL.getHistories());
        }
        catch (ProfileException localProfileException) {}
        this.jL = null;
      }
      else
      {
        return this.taskErrorURL;
      }
    }
    return this.jz;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, Account paramAccount, String paramString1, String paramString2, SecureUser paramSecureUser, Object[] paramArrayOfObject)
  {
    String str1 = jK.concat(paramString1.concat(ju));
    String str2 = (String)paramHttpSession.getAttribute(str1);
    boolean bool = false;
    if (paramString2 == null) {
      paramString2 = "1";
    }
    LocalizableString localLocalizableString1;
    LocalizableString localLocalizableString2;
    if ((paramString2.equals("1")) && (str2 != null) && (str2.equals("on")))
    {
      paramAccount.set("HIDE", "0");
      bool = true;
      localLocalizableString1 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Activated", paramArrayOfObject);
      Initialize.audit(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID(), 1201);
      localLocalizableString2 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Activated", paramArrayOfObject);
      this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString2);
    }
    else if ((paramString2.equals("0")) && (str2 == null))
    {
      paramAccount.set("HIDE", "1");
      bool = true;
      localLocalizableString1 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Deactivated", paramArrayOfObject);
      Initialize.audit(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID(), 1201);
      localLocalizableString2 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Deactivated", paramArrayOfObject);
      this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString2);
    }
    return bool;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, Account paramAccount, String paramString, SecureUser paramSecureUser, Object[] paramArrayOfObject)
  {
    String str1 = jK.concat(paramString.concat(jI));
    String str2 = (String)paramHttpSession.getAttribute(str1);
    String str3 = paramAccount.getNickName();
    boolean bool = false;
    if ((str2 != null) && (!str3.equals(str2)))
    {
      paramAccount.setNickName(str2);
      bool = true;
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Nickname_Changed", paramArrayOfObject);
      this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString);
    }
    return bool;
  }
  
  private boolean jdMethod_int(HttpSession paramHttpSession, Account paramAccount, String paramString, SecureUser paramSecureUser, Object[] paramArrayOfObject)
  {
    boolean bool1 = false;
    try
    {
      boolean bool2 = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.jx);
      if (bool2)
      {
        String str1 = jK.concat(paramString.concat(jD));
        String str2 = (String)paramHttpSession.getAttribute(str1);
        boolean bool3 = Boolean.valueOf((String)paramAccount.get("REG_ENABLED")).booleanValue();
        bool1 = bool3;
        int i;
        if ((!bool3) && (str2 != null) && (str2.equals("on"))) {
          try
          {
            paramAccount.set("REG_ENABLED", String.valueOf(true));
            Register.modifyRegisterAccountData(paramSecureUser, paramAccount, null);
            bool1 = true;
            LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Activated_For_Register", paramArrayOfObject);
            this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString1);
          }
          catch (CSILException localCSILException2)
          {
            i = MapError.mapError(localCSILException2);
            if (i != 0) {
              bool1 = false;
            }
          }
        } else if ((bool3 == true) && (str2 == null)) {
          try
          {
            paramAccount.set("REG_ENABLED", String.valueOf(false));
            paramAccount.set("REG_DEFAULT", String.valueOf(false));
            Register.modifyRegisterAccountData(paramSecureUser, paramAccount, null);
            bool1 = false;
            LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Deactivated_For_Register", paramArrayOfObject);
            this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString2);
          }
          catch (CSILException localCSILException3)
          {
            i = MapError.mapError(localCSILException3);
            if (i != 0) {
              bool1 = true;
            }
          }
        }
      }
    }
    catch (CSILException localCSILException1) {}
    return bool1;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, com.ffusion.beans.accounts.Accounts paramAccounts, Account paramAccount, String paramString, SecureUser paramSecureUser, Object[] paramArrayOfObject)
  {
    try
    {
      boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), this.jw);
      if (bool)
      {
        String str1 = jK.concat(paramString.concat(jJ));
        String str2 = (String)paramHttpSession.getAttribute(str1);
        com.ffusion.beans.accounts.Accounts localAccounts;
        LocalizableString localLocalizableString;
        if ((paramAccounts == null) || ((paramAccounts != null) && (!jdMethod_for(paramAccounts, paramAccount)) && (str2 != null) && (str2.equals("on")))) {
          try
          {
            Locale localLocale1 = (Locale)paramHttpSession.getAttribute("java.util.Locale");
            localAccounts = new com.ffusion.beans.accounts.Accounts(localLocale1);
            localAccounts.add(paramAccount);
            StatementData.addAccountsForIStatement(paramSecureUser, localAccounts);
            localLocalizableString = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Activated_For_Statements", paramArrayOfObject);
            this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString);
          }
          catch (CSILException localCSILException2) {}
        } else if ((paramAccounts != null) && (jdMethod_for(paramAccounts, paramAccount)) && (str2 == null)) {
          try
          {
            Locale localLocale2 = (Locale)paramHttpSession.getAttribute("java.util.Locale");
            localAccounts = new com.ffusion.beans.accounts.Accounts(localLocale2);
            localAccounts.add(paramAccount);
            StatementData.removeAccountsForIStatement(paramSecureUser, localAccounts);
            localLocalizableString = new LocalizableString("com.ffusion.beans.accounts.resources", "Account_Deactivated_For_Statements", paramArrayOfObject);
            this.jL.logChange(jF, "ACCOUNT", "1", "0", localLocalizableString);
          }
          catch (CSILException localCSILException3) {}
        }
      }
    }
    catch (CSILException localCSILException1) {}
  }
  
  public void setAccountsName(String paramString)
  {
    this._accountsName = paramString;
  }
  
  public String getAccountsName()
  {
    return this._accountsName;
  }
  
  public void setActiveAccountsIdentifier(String paramString)
  {
    ju = paramString;
  }
  
  public String getActiveAccountsName()
  {
    return ju;
  }
  
  public void setNicknameIdentifier(String paramString)
  {
    jI = paramString;
  }
  
  public String getNicknameIdentifier()
  {
    return jI;
  }
  
  public void setRegisterIdentifier(String paramString)
  {
    jD = paramString;
  }
  
  public String getRegisterName()
  {
    return jD;
  }
  
  public void setStatementsIdentifier(String paramString)
  {
    jJ = paramString;
  }
  
  public String getStatementsName()
  {
    return jJ;
  }
  
  public void setFormElementPrefix(String paramString)
  {
    jK = paramString;
  }
  
  public String getFormElementPrefix()
  {
    return jK;
  }
  
  public void setErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getErrorURL()
  {
    return this.serviceErrorURL;
  }
  
  private boolean jdMethod_for(com.ffusion.beans.accounts.Accounts paramAccounts, Account paramAccount)
  {
    return paramAccounts.getByID(paramAccount.getID()) != null;
  }
  
  protected boolean validate(com.ffusion.beans.accounts.Accounts paramAccounts, HttpSession paramHttpSession)
  {
    boolean bool = true;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if (paramAccounts != null)
    {
      Iterator localIterator = paramAccounts.iterator();
      while ((localIterator.hasNext()) && (bool))
      {
        Account localAccount = (Account)localIterator.next();
        str1 = jK.concat(localAccount.getNumberMasked().concat(jI));
        str2 = (String)paramHttpSession.getAttribute(str1);
        str3 = localAccount.getNickName();
        if ((str2 != null) && (!str3.equals(str2)) && (str2.length() > this.MAX_ACCOUNT_NICKNAME_LENGTH))
        {
          bool = false;
          this.error = 11016;
        }
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SetupAccounts
 * JD-Core Version:    0.7.0.1
 */