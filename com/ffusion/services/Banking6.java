package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface Banking6
  extends Banking5, SignOn3
{
  public abstract RecTransfer getRecTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfer getTransferByID(SecureUser paramSecureUser, Transfer paramTransfer, HashMap paramHashMap)
    throws BankingException;
  
  public abstract Transfers getTransferTemplates(SecureUser paramSecureUser, Transfer paramTransfer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking6
 * JD-Core Version:    0.7.0.1
 */