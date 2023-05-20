package com.ffusion.services;

import com.ffusion.beans.banking.BankingDays;
import java.util.HashMap;

public abstract interface PaymentsAdminService2
  extends PaymentsAdminService
{
  public abstract BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws PAException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PaymentsAdminService2
 * JD-Core Version:    0.7.0.1
 */