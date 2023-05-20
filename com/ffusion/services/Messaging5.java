package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.messages.GlobalMessageSearchCriteria;
import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Messaging5
  extends Messaging4
{
  public abstract int globalMsgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract int msgApprovalRequired(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract GlobalMessages getGlobalMessages(SecureUser paramSecureUser, GlobalMessageSearchCriteria paramGlobalMessageSearchCriteria, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract GlobalMessages getGlobalLoginMessages(String paramString, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract GlobalMessage getGlobalMessageById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void processGlobalMessages()
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging5
 * JD-Core Version:    0.7.0.1
 */