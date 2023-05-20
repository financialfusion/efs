package com.ffusion.services;

import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface Banking8
  extends Banking7
{
  public abstract HashMap getTransactionTypes(int paramInt, HashMap paramHashMap)
    throws BankingException;
  
  public abstract TransactionTypeInfo getTransactionTypeInfo(int paramInt, HashMap paramHashMap)
    throws BankingException;
  
  public abstract int addTransferBatch(TransferBatch paramTransferBatch);
  
  public abstract int cancelTransferBatch(TransferBatch paramTransferBatch);
  
  public abstract int modifyTransferBatch(TransferBatch paramTransferBatch);
  
  public abstract FundsTransactions getPagedFundsTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws BankingException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Banking8
 * JD-Core Version:    0.7.0.1
 */