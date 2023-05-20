package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.BankMessageInfo;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Messaging8
  extends Messaging7
{
  public abstract BankMessageInfo getEmailSettings(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract MessageCounts getMessageCountsByHelpCasesProvidedAndClosed(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging8
 * JD-Core Version:    0.7.0.1
 */