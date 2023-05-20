package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface ExternalTransferAdmin2
  extends ExternalTransferAdmin
{
  public abstract ExtTransferAccount verifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, int[] paramArrayOfInt, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ExtTransferAccount depositAmountsForVerify(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException;
  
  public abstract String getNumberOfVerifyDeposits()
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ExternalTransferAdmin2
 * JD-Core Version:    0.7.0.1
 */