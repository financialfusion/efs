package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;

public class MessageQueueI18N
  extends ExtendABean
{
  protected String queueID = "";
  protected String language = "";
  protected String queueName = "";
  protected String queueAutoReplySubject = "";
  protected String queueAutoReplyText = "";
  protected String description = "";
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public String getQueueAutoReplySubject()
  {
    return this.queueAutoReplySubject;
  }
  
  public void setQueueAutoReplySubject(String paramString)
  {
    this.queueAutoReplySubject = paramString;
  }
  
  public String getQueueAutoReplyText()
  {
    return this.queueAutoReplyText;
  }
  
  public void setQueueAutoReplyText(String paramString)
  {
    this.queueAutoReplyText = paramString;
  }
  
  public String getQueueID()
  {
    return this.queueID;
  }
  
  public void setQueueID(String paramString)
  {
    this.queueID = paramString;
  }
  
  public String getQueueName()
  {
    return this.queueName;
  }
  
  public void setQueueName(String paramString)
  {
    this.queueName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueueI18N
 * JD-Core Version:    0.7.0.1
 */