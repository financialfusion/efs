package com.ffusion.services.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.blockedaccts.BlockedAcctsAdapter;
import com.ffusion.services.IBlockedAccounts;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import java.util.HashMap;

public class BlockedAccountsService
  implements IBlockedAccounts
{
  public void initialize(HashMap paramHashMap)
    throws BLKException
  {
    BlockedAcctsAdapter.initialize(paramHashMap);
  }
  
  public void addBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    if (paramBlockedAccount != null) {
      paramBlockedAccount.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccount.getAccountNumber(), paramHashMap));
    } else {
      throw new BLKException("com.ffusion.services.blockedaccts.BlockedAccountsService.addBlockedAccount", 5, "One or more illegal arguments have been specified.");
    }
    BlockedAcctsAdapter.addBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
  }
  
  public void deleteBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    if (paramBlockedAccount != null) {
      paramBlockedAccount.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccount.getAccountNumber(), paramHashMap));
    } else {
      throw new BLKException("com.ffusion.services.blockedaccts.BlockedAccountsService.deleteBlockedAccount", 5, "One or more illegal arguments have been specified.");
    }
    BlockedAcctsAdapter.deleteBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
  }
  
  public void modifyBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount1, BlockedAccount paramBlockedAccount2, HashMap paramHashMap)
    throws BLKException
  {
    if ((paramBlockedAccount2 == null) || (paramBlockedAccount1 == null)) {
      throw new BLKException("com.ffusion.services.blockedaccts.BlockedAccountsService.modifyBlockedAccount", 5, "One or more illegal arguments have been specified.");
    }
    paramBlockedAccount2.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccount2.getAccountNumber(), paramHashMap));
    paramBlockedAccount1.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccount1.getAccountNumber(), paramHashMap));
    if (!paramBlockedAccount2.isEquivalentBlockedAccount(paramBlockedAccount1)) {
      BlockedAcctsAdapter.modifyBlockedAccount(paramSecureUser, paramBlockedAccount1, paramBlockedAccount2, paramHashMap);
    }
  }
  
  public int getNumBlockedAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws BLKException
  {
    return BlockedAcctsAdapter.getNumBlockedAccounts(paramSecureUser, paramHashMap);
  }
  
  public BlockedAccountSearchResults getBlockedAccounts(SecureUser paramSecureUser, BlockedAccountSearchCriteria paramBlockedAccountSearchCriteria, HashMap paramHashMap)
    throws BLKException
  {
    if (paramBlockedAccountSearchCriteria != null) {
      paramBlockedAccountSearchCriteria.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccountSearchCriteria.getAccountNumber(), paramHashMap));
    }
    return BlockedAcctsAdapter.getBlockedAccounts(paramSecureUser, paramBlockedAccountSearchCriteria, paramHashMap);
  }
  
  public boolean isBlockedAccount(SecureUser paramSecureUser, BlockedAccount paramBlockedAccount, HashMap paramHashMap)
    throws BLKException
  {
    if (paramBlockedAccount != null) {
      paramBlockedAccount.setStrippedAccountNumber(getStrippedAccountNumber(paramBlockedAccount.getAccountNumber(), paramHashMap));
    }
    return BlockedAcctsAdapter.isBlockedAccount(paramSecureUser, paramBlockedAccount, paramHashMap);
  }
  
  public String getStrippedAccountNumber(String paramString, HashMap paramHashMap)
    throws BLKException
  {
    return BlockedAcctsAdapter.getStrippedAccountNumber(paramString, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.blockedaccts.BlockedAccountsService
 * JD-Core Version:    0.7.0.1
 */