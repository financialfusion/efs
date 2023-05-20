package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;

public abstract interface BPWExternalAPI
{
  public abstract void processIntraTrnRslt(IntraTrnRslt[] paramArrayOfIntraTrnRslt)
    throws Exception;
  
  public abstract void processPmtTrnRslt(PmtTrnRslt[] paramArrayOfPmtTrnRslt)
    throws Exception;
  
  public abstract void processOnePmtRslt(PmtTrnRslt paramPmtTrnRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt)
    throws Exception;
  
  public abstract void processCustPayeeRslt(CustPayeeRslt[] paramArrayOfCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void processOneCustPayeeRslt(CustPayeeRslt paramCustPayeeRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void processPayeeRslt(PayeeRslt[] paramArrayOfPayeeRslt)
    throws Exception;
  
  public abstract void ProcessFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception;
  
  public abstract void ProcessOneFundAllocRslt(FundsAllocRslt paramFundsAllocRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract String addPayeeFromBackend(PayeeInfo paramPayeeInfo)
    throws Exception;
  
  public abstract void addPayeeRouteInfo(PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception;
  
  public abstract PmtInfo[] getFailedPmt(String paramString)
    throws Exception;
  
  public abstract PmtInfo[] getNewFailedPmt()
    throws Exception;
  
  public abstract String[] getPayeeNames(String paramString)
    throws Exception;
  
  public abstract String[] getPayeeIDs(String paramString)
    throws Exception;
  
  public abstract PayeeInfo[] getPayees(String paramString)
    throws Exception;
  
  public abstract PayeeInfo[] getMostUsedPayees(int paramInt)
    throws Exception;
  
  public abstract PayeeInfo[] getLinkedPayees()
    throws Exception;
  
  public abstract PayeeInfo[] searchGlobalPayees(String paramString)
    throws Exception;
  
  public abstract PayeeInfo[] getPreferedPayees(String paramString)
    throws Exception;
  
  public abstract void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws Exception;
  
  public abstract void deletePayee(String paramString)
    throws Exception;
  
  public abstract void deletePayees(String[] paramArrayOfString)
    throws Exception;
  
  public abstract PayeeInfo findPayeeByID(String paramString)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWExternalAPI
 * JD-Core Version:    0.7.0.1
 */