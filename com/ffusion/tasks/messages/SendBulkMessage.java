package com.ffusion.tasks.messages;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendBulkMessage
  extends BaseTask
  implements Task
{
  private String r6;
  private String r7;
  private String r4;
  private String r5;
  private String r3;
  private String r2;
  protected int maxSize = 1024;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (getMemo().length() > this.maxSize)
    {
      this.error = 8077;
      str = this.taskErrorURL;
      return str;
    }
    try
    {
      int i = Integer.parseInt(this.r7);
      int j = Integer.parseInt(this.r4);
      int k = Messages.sendBulkMessage(this.r6, i, j, this.r5, this.r3, this.r2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {}
    return str;
  }
  
  public void setBankId(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r6 = paramString;
    }
  }
  
  public void setToGroupType(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r7 = paramString;
    }
  }
  
  public void setToGroupId(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r4 = paramString;
    }
  }
  
  public void setFrom(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r5 = paramString;
    }
  }
  
  public void setSubject(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r3 = paramString;
    }
  }
  
  public void setMemo(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.r2 = paramString;
    }
  }
  
  public String getBankId()
  {
    return this.r6;
  }
  
  public String getToGroupType()
  {
    return this.r7;
  }
  
  public String getToGroupId()
  {
    return this.r4;
  }
  
  public String getFrom()
  {
    return this.r5;
  }
  
  public String getSubject()
  {
    return this.r3;
  }
  
  public String getMemo()
  {
    return this.r2;
  }
  
  public void setMaxMessageSize(String paramString)
  {
    this.maxSize = Integer.valueOf(paramString).intValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendBulkMessage
 * JD-Core Version:    0.7.0.1
 */