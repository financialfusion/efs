package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;

public class EntitledMsgType
  extends ExtendABean
{
  private String aWQ;
  private String aWP;
  public static final String DESCRIPTION = "MessageDescription";
  public static final String TYPE = "MessageType";
  
  public EntitledMsgType(int paramInt, String paramString)
  {
    this.aWQ = Integer.toString(paramInt);
    this.aWP = paramString;
  }
  
  public String getMessageType()
  {
    return this.aWQ;
  }
  
  public String getMessageDescription()
  {
    return this.aWP;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.EntitledMsgType
 * JD-Core Version:    0.7.0.1
 */