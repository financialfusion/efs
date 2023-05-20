package com.ffusion.services;

import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Messaging6
  extends Messaging5
{
  public abstract GlobalMessages getGlobalLoginMessages(String paramString, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging6
 * JD-Core Version:    0.7.0.1
 */