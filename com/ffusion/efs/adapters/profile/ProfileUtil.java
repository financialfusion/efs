package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Locale;

public class ProfileUtil
  implements ProfileDefines
{
  public static Timestamp getTimestamp(DateTime paramDateTime)
  {
    if (paramDateTime == null) {
      return null;
    }
    return new Timestamp(paramDateTime.getTime().getTime());
  }
  
  public static DateTime getDateTime(Timestamp paramTimestamp)
  {
    if (paramTimestamp == null) {
      return null;
    }
    return new DateTime(paramTimestamp, Locale.getDefault());
  }
  
  public static DateTime getDateTime(Timestamp paramTimestamp, Locale paramLocale)
  {
    if (paramTimestamp == null) {
      return null;
    }
    return new DateTime(paramTimestamp, paramLocale);
  }
  
  public static DateTime getDateTime(java.sql.Date paramDate, Locale paramLocale)
  {
    if (paramDate == null) {
      return null;
    }
    return new DateTime(paramDate, paramLocale);
  }
  
  public static String buildInsertSql(String paramString, String[] paramArrayOfString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append("insert into ").append(paramString).append(" (");
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      localStringBuffer.append(paramArrayOfString[i]);
      if (i < paramArrayOfString.length - 1) {
        localStringBuffer.append(",");
      }
    }
    localStringBuffer.append(") values (");
    for (i = 0; i < paramArrayOfString.length; i++)
    {
      localStringBuffer.append("?");
      if (i < paramArrayOfString.length - 1) {
        localStringBuffer.append(",");
      }
    }
    localStringBuffer.append(")");
    return localStringBuffer.toString();
  }
  
  public static String buildDeleteSql(String paramString, String[] paramArrayOfString, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append("delete from ").append(paramString).append(" where ");
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      localStringBuffer.append(paramArrayOfString[i]).append("=?");
      if (i < paramArrayOfString.length - 1) {
        localStringBuffer.append(" and ");
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String buildUpdateSql(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append("update ").append(paramString).append(" set ");
    for (int i = 0; i < paramArrayOfString1.length; i++)
    {
      localStringBuffer.append(paramArrayOfString1[i]).append("=?");
      if (i < paramArrayOfString1.length - 1) {
        localStringBuffer.append(",");
      }
    }
    if (paramArrayOfString2 != null)
    {
      localStringBuffer.append(" where ");
      for (i = 0; i < paramArrayOfString2.length; i++)
      {
        localStringBuffer.append(paramArrayOfString2[i]).append("=?");
        if (i < paramArrayOfString2.length - 1) {
          localStringBuffer.append(" and ");
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String buildSelectSql(String paramString1, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString2, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    localStringBuffer.append("select ");
    int i;
    if ((paramArrayOfString1 == null) || (paramArrayOfString1.length == 0)) {
      localStringBuffer.append("*");
    } else {
      for (i = 0; i < paramArrayOfString1.length; i++)
      {
        localStringBuffer.append(paramArrayOfString1[i]);
        if (i < paramArrayOfString1.length - 1) {
          localStringBuffer.append(",");
        }
      }
    }
    localStringBuffer.append(" from ").append(paramString1);
    if (paramArrayOfString2 != null)
    {
      localStringBuffer.append(" where ");
      for (i = 0; i < paramArrayOfString2.length; i++)
      {
        localStringBuffer.append(paramArrayOfString2[i]).append("=?");
        if (i < paramArrayOfString2.length - 1) {
          localStringBuffer.append(" and ");
        }
      }
    }
    if (paramString2 != null) {
      localStringBuffer.append(" order by ").append(paramString2);
    }
    return localStringBuffer.toString();
  }
  
  public static Accounts processAccountsRS(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    Accounts localAccounts = new Accounts();
    Account localAccount = null;
    while (paramResultSet.next())
    {
      localAccount = jdMethod_for(paramResultSet, paramString, localAccounts);
      Timestamp localTimestamp = paramResultSet.getTimestamp("EXPORT_BEGIN_DATE");
      DateTime localDateTime;
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, Locale.getDefault(), "MM-dd-yyyy");
        localAccount.put("EXPORT_BEGIN_DATE", localDateTime.toString());
      }
      localTimestamp = paramResultSet.getTimestamp("EXPORT_END_DATE");
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, Locale.getDefault(), "MM-dd-yyyy");
        localAccount.put("EXPORT_END_DATE", localDateTime.toString());
      }
    }
    return localAccounts;
  }
  
  public static Accounts processAccountsRS(ResultSet paramResultSet, String paramString, Locale paramLocale)
    throws Exception
  {
    Accounts localAccounts = new Accounts(paramLocale);
    Account localAccount = null;
    while (paramResultSet.next())
    {
      localAccount = jdMethod_for(paramResultSet, paramString, localAccounts);
      Timestamp localTimestamp = paramResultSet.getTimestamp("EXPORT_BEGIN_DATE");
      DateTime localDateTime;
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, paramLocale, "MM-dd-yyyy");
        localAccount.put("EXPORT_BEGIN_DATE", localDateTime.toString());
      }
      localTimestamp = paramResultSet.getTimestamp("EXPORT_END_DATE");
      if (localTimestamp != null)
      {
        localDateTime = new DateTime(localTimestamp, paramLocale, "MM-dd-yyyy");
        localAccount.put("EXPORT_END_DATE", localDateTime.toString());
      }
    }
    return localAccounts;
  }
  
  public static Account processAccountRS(ResultSet paramResultSet, String paramString)
    throws Exception
  {
    return jdMethod_for(paramResultSet, paramString, new Accounts());
  }
  
  private static Account jdMethod_for(ResultSet paramResultSet, String paramString, Accounts paramAccounts)
    throws Exception
  {
    String str1 = Profile.getRSString(paramResultSet, "account_id");
    String str2 = Profile.getRSString(paramResultSet, "ACCOUNT_NUM");
    int i = paramResultSet.getInt("ACCOUNT_TYPE");
    Account localAccount = paramAccounts.create(paramString, str1, str2, i);
    localAccount.setCurrencyCode(Profile.getRSString(paramResultSet, "currency_type"));
    localAccount.setBankName(Profile.getRSString(paramResultSet, "bank_name"));
    localAccount.setPrimaryAccount(Profile.getRSString(paramResultSet, "primary_account"));
    localAccount.setCoreAccount(Profile.getRSString(paramResultSet, "core_account"));
    localAccount.setPersonalAccount(Profile.getRSString(paramResultSet, "personal_account"));
    localAccount.setBicAccount(Profile.getRSString(paramResultSet, "bic_account"));
    localAccount.setPositivePay(Profile.getRSString(paramResultSet, "positive_pay"));
    localAccount.setRoutingNum(Profile.getRSString(paramResultSet, "routing_num"));
    localAccount.setZBAFlag(Profile.getRSString(paramResultSet, "ZBAFLAG"));
    localAccount.setShowPreviousDayOpeningLedger(Profile.getRSString(paramResultSet, "SHOWPREVOPENLEDGER"));
    localAccount.setContactId(paramResultSet.getInt("CONTACT_ID"));
    jdMethod_for(localAccount, paramResultSet);
    localAccount.setAccountGroup(b(localAccount.getTypeValue()));
    String str3 = Profile.getRSString(paramResultSet, "IS_MASTER");
    boolean bool1;
    if (str3.equals("Y")) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localAccount.setMaster(bool1);
    String str4 = Profile.getRSString(paramResultSet, "INC_ZBACR_INROLLUP");
    boolean bool2;
    if (str4.equals("Y")) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    localAccount.setIncludeZBACreditInRollup(bool2);
    String str5 = Profile.getRSString(paramResultSet, "INC_ZBADB_INROLLUP");
    boolean bool3;
    if (str5.equals("Y")) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    localAccount.setIncludeZBADebitInRollup(bool3);
    String str6 = Profile.getRSString(paramResultSet, "WITHHOLD_NZB_SUB");
    boolean bool4;
    if (str6.equals("Y")) {
      bool4 = true;
    } else {
      bool4 = false;
    }
    localAccount.setWithholdNonZeroBalanceSubAccounts(bool4);
    localAccount.setStrippedAccountNumber(Profile.getRSString(paramResultSet, "STRIPPED_ACCOUNT_NUM"));
    localAccount.setDirectoryID(paramResultSet.getInt("DIRECTORY_ID"));
    return localAccount;
  }
  
  static int b(int paramInt)
  {
    return Account.getAccountGroupFromType(paramInt);
  }
  
  private static void jdMethod_for(Account paramAccount, ResultSet paramResultSet)
    throws Exception
  {
    paramAccount.setNickName(Profile.getRSString(paramResultSet, "NICKNAME"));
    paramAccount.put("HIDE", Profile.getRSString(paramResultSet, "HIDE"));
    Timestamp localTimestamp = paramResultSet.getTimestamp("REG_RETRIEVAL_DATE");
    if (localTimestamp != null) {
      paramAccount.put("REG_RETRIEVAL_DATE", new DateTime(localTimestamp, Locale.getDefault()));
    }
    paramAccount.put("REG_DEFAULT", Profile.getRSString(paramResultSet, "REG_DEFAULT"));
    paramAccount.put("REG_ENABLED", Profile.getRSString(paramResultSet, "REG_ENABLED"));
    Profile.setXBeanFields(paramResultSet, paramAccount, "accounts");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ProfileUtil
 * JD-Core Version:    0.7.0.1
 */