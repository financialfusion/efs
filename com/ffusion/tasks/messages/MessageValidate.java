package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.Message;
import com.ffusion.tasks.BaseTask;

public abstract class MessageValidate
  extends BaseTask
  implements Task
{
  protected String validate = null;
  protected int maxMemoLength = 1024;
  protected int maxSubjectLength = 100;
  protected int maxCommentLength = 1024;
  protected int maxFromLength = 100;
  protected int maxTemplateNameLength = 255;
  public static String MEMO = "MEMO";
  public static String SUBJECT = "SUBJECT";
  public static String COMMENT = "COMMENT";
  
  public void setValidate(String paramString)
  {
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  protected boolean validate(Message paramMessage)
  {
    boolean bool = true;
    if (this.validate == null) {
      return bool;
    }
    if (this.validate.indexOf(SUBJECT) != -1)
    {
      if (!valueExists(paramMessage.getSubject()))
      {
        this.error = 8005;
        bool = false;
        return bool;
      }
      if (!isValidStringField(paramMessage.getSubject(), this.maxSubjectLength))
      {
        this.error = 8060;
        bool = false;
        return bool;
      }
    }
    if (this.validate.indexOf(MEMO) != -1)
    {
      if (!valueExists(paramMessage.getMemo()))
      {
        this.error = 8007;
        bool = false;
        return bool;
      }
      if (!isValidStringField(paramMessage.getMemo(), this.maxMemoLength))
      {
        this.error = 8084;
        bool = false;
        return bool;
      }
    }
    if ((this.validate.indexOf(COMMENT) != -1) && (paramMessage.getComment() != null) && (!isValidStringField(paramMessage.getComment(), this.maxCommentLength)))
    {
      this.error = 8082;
      bool = false;
      return bool;
    }
    this.validate = null;
    return bool;
  }
  
  protected static boolean isValidStringField(String paramString, int paramInt)
  {
    return (paramString != null) && (paramString.length() != 0) && (paramString.length() <= paramInt);
  }
  
  protected boolean valueExists(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
  
  protected static boolean isValidId(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      if (i <= 0) {
        return false;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    return true;
  }
  
  public int getMaxFromLength()
  {
    return this.maxFromLength;
  }
  
  public void setMaxFromLength(String paramString)
  {
    this.maxFromLength = Integer.parseInt(paramString);
  }
  
  public int getMaxTemplateNameLength()
  {
    return this.maxTemplateNameLength;
  }
  
  public void setMaxTemplateNameLength(String paramString)
  {
    this.maxTemplateNameLength = Integer.parseInt(paramString);
  }
  
  public int getMaxCommentLength()
  {
    return this.maxCommentLength;
  }
  
  public void setMaxCommentLength(String paramString)
  {
    this.maxCommentLength = Integer.parseInt(paramString);
  }
  
  public int getMaxMemoLength()
  {
    return this.maxMemoLength;
  }
  
  public void setMaxMemoLength(String paramString)
  {
    this.maxMemoLength = Integer.parseInt(paramString);
  }
  
  public int getMaxSubjectLength()
  {
    return this.maxSubjectLength;
  }
  
  public void setMaxSubjectLength(String paramString)
  {
    this.maxSubjectLength = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.MessageValidate
 * JD-Core Version:    0.7.0.1
 */