package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.services.blockedaccts.BLKException;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import java.util.HashMap;

public abstract interface IBlockedAccounts
{
  public abstract void initialize(HashMap paramHashMap)
    throws BLKException;
  
  public abstract void addBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException;
  
  public abstract void deleteBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException;
  
  public abstract void modifyBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, HashMap paramHashMap)
    throws BLKException;
  
  public abstract int getNumBlockedAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BLKException;
  
  public abstract BlockedAccountSearchResults getBlockedAccounts(SecureUser paramSecureUser, BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria, HashMap paramHashMap)
    throws BLKException;
  
  public abstract boolean isBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException;
  
  public abstract String getStrippedAccountNumber(String paramString, HashMap paramHashMap)
    throws BLKException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.IBlockedAccounts
 * JD-Core Version:    0.7.0.1
 */