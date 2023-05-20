package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.user.User;
import java.util.HashMap;

public abstract interface MessagingFilters
{
  public abstract boolean checkFiltersForSecureUser(GlobalMessage paramGlobalMessage, SecureUser paramSecureUser, HashMap paramHashMap);
  
  public abstract boolean checkFiltersForUser(GlobalMessage paramGlobalMessage, User paramUser, HashMap paramHashMap);
  
  public abstract boolean checkFiltersForDirID(GlobalMessage paramGlobalMessage, int paramInt, HashMap paramHashMap);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.MessagingFilters
 * JD-Core Version:    0.7.0.1
 */