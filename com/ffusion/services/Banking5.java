package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.csil.CSILException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface Banking5
  extends Banking4, SignOn3
{
  public abstract RecTransfer getRecTransferByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException;
  
  public abstract HashMap getTransactionTypes()
    throws BankingException;
  
  public abstract ArrayList getTransactionTypeDescriptions()
    throws BankingException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking5
 * JD-Core Version:    0.7.0.1
 */