package com.ffusion.treasurydirect;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreasuryDirectUtil
{
  public static boolean validateLocationID(String paramString)
  {
    boolean bool = true;
    StringCharacterIterator localStringCharacterIterator = null;
    if (paramString.length() > 12) {
      bool = false;
    }
    localStringCharacterIterator = new StringCharacterIterator(paramString);
    int j;
    for (int i = localStringCharacterIterator.next(); (bool) && (i != 65535); j = localStringCharacterIterator.next()) {
      bool = (bool) && (Character.isLetterOrDigit(i));
    }
    return bool;
  }
  
  public static boolean hasUniqueLocationIds(Accounts paramAccounts)
  {
    boolean bool = true;
    Account localAccount = null;
    HashSet localHashSet = new HashSet(paramAccounts.size());
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      bool = (bool) && (localHashSet.add(localAccount.getLocationID()));
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.treasurydirect.TreasuryDirectUtil
 * JD-Core Version:    0.7.0.1
 */