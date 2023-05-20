package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.ListIterator;

public class Enroll
  extends Initialize
{
  private static final String aqf = "com.ffusion.util.logging.audit.enroll";
  private static final String aqg = "AuditMessage_1";
  private static final String aqe = "AuditList_1";
  
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.Enroll.initialize");
    com.ffusion.csil.handlers.Enroll.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Enroll.getService();
  }
  
  public static HashMap enroll(HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str1 = "Enroll.Enroll";
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    HashMap localHashMap = com.ffusion.csil.handlers.Enroll.enroll(paramHashMap1, paramHashMap2);
    PerfLog.log(str1, l, true);
    return localHashMap;
  }
  
  public static Accounts activateAccount(SecureUser paramSecureUser, User paramUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Enroll.ActivateAccount";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    String str2 = TrackingIDGenerator.GetNextID();
    Accounts localAccounts = com.ffusion.csil.handlers.Enroll.activateAccount(paramSecureUser, paramUser, paramAccounts, paramHashMap);
    PerfLog.log(str1, l, true);
    ListIterator localListIterator = paramAccounts.listIterator();
    while (localListIterator.hasNext())
    {
      LocalizableList localLocalizableList = new LocalizableList("com.ffusion.util.logging.audit.enroll", "AuditList_1");
      for (int i = 0; (i < 10) && (localListIterator.hasNext()); i++)
      {
        localObject = (Account)localListIterator.next();
        localLocalizableList.add(((Account)localObject).buildLocalizableAccountID());
      }
      Object localObject = new Object[2];
      localObject[0] = localLocalizableList;
      localObject[1] = paramUser.getId();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.enroll", "AuditMessage_1", (Object[])localObject);
      audit(paramSecureUser, localLocalizableString, str2, 2300);
    }
    debug(paramSecureUser, str1);
    return localAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Enroll
 * JD-Core Version:    0.7.0.1
 */