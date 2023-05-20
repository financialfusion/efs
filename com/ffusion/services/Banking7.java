package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public abstract interface Banking7
  extends Banking6, SignOn3
{
  public abstract Transactions getRecentTransactions(Account paramAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract int getNumTransactions(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Properties paramProperties, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Date getEffectiveDate(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract Transfers getPagedTransfers(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
  
  public abstract HashMap getTransactionTypes(Locale paramLocale, HashMap paramHashMap)
    throws BankingException;
  
  public abstract ArrayList getTransactionTypeDescriptions(Locale paramLocale, HashMap paramHashMap)
    throws BankingException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking7
 * JD-Core Version:    0.7.0.1
 */