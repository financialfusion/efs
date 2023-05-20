package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.smartcalendar.SCException;
import java.util.HashMap;

public abstract interface SmartCalendar2
  extends SmartCalendar
{
  public abstract boolean getIgnoreForTransfers(SecureUser paramSecureUser, BankIdentifier paramBankIdentifier, HashMap paramHashMap)
    throws SCException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SmartCalendar2
 * JD-Core Version:    0.7.0.1
 */