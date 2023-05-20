package com.ffusion.alert.plugins.message;

import java.io.Serializable;

public class MessageList
  implements Serializable
{
  private Message a = new MessageImpl();
  private Message jdField_if = new MessageImpl();
  
  public void setSubject(String paramString)
  {
    if (getSecureMessage() != null) {
      getSecureMessage().setSubject(paramString);
    }
    if (getUnsecureMessage() != null) {
      getUnsecureMessage().setSubject(paramString);
    }
  }
  
  public Message getSecureMessage()
  {
    return this.a;
  }
  
  public void setSecureMessage(Message paramMessage)
  {
    this.a = paramMessage;
  }
  
  public Message getUnsecureMessage()
  {
    return this.jdField_if;
  }
  
  public void setUnsecureMessage(Message paramMessage)
  {
    this.jdField_if = paramMessage;
  }
  
  public boolean hasSecureMessage()
  {
    return this.a != null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.alert.plugins.message.MessageList
 * JD-Core Version:    0.7.0.1
 */