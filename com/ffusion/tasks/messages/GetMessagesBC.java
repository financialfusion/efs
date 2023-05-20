package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMessagesBC
  extends BaseTask
  implements Task
{
  private String rl = System.getProperty("line.separator", "\n");
  private String rn = "&nbsp;";
  private String rm = "<br>";
  protected boolean forceBR = false;
  protected boolean forceNBSP = false;
  protected String bankId = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    Message localMessage = null;
    String str2 = "";
    String str3 = "";
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.beans.messages.Messages localMessages = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    MessageThread localMessageThread = (MessageThread)localHttpSession.getAttribute("MessageThread");
    if (localMessageThread == null)
    {
      this.error = 8022;
      return this.taskErrorURL;
    }
    String str4 = localMessageThread.getThreadID();
    if ((str4 == null) || (str4.trim().length() == 0))
    {
      this.error = 8027;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    if ((this.bankId != null) && (this.bankId.length() > 0)) {
      localHashMap.put("BANK_ID", this.bankId);
    }
    try
    {
      localMessages = com.ffusion.csil.core.Messages.getMessages(localSecureUser, localHashMap, str4);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      for (int i = 0; i < localMessages.size(); i++)
      {
        localMessage = (Message)localMessages.get(i);
        str2 = localMessage.getMemo();
        str3 = localMessage.getComment();
        if (str2 != null)
        {
          if (this.forceNBSP) {
            str2 = c(str2);
          }
          if (this.forceBR) {
            str2 = str2.replaceAll(this.rl, this.rm);
          }
          localMessage.setMemo(str2);
        }
        if (str3 != null)
        {
          if (this.forceNBSP) {
            str3 = c(str3);
          }
          if (this.forceBR) {
            str3 = str3.replaceAll(this.rl, this.rm);
          }
          localMessage.setComment(str3);
        }
      }
      localHttpSession.setAttribute("Messages", localMessages);
    }
    else
    {
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public String getBankId()
  {
    return this.bankId;
  }
  
  public void setBankId(String paramString)
  {
    this.bankId = paramString;
  }
  
  public void setForceBR(String paramString)
  {
    this.forceBR = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setReplaceLeadingSpaces(String paramString)
  {
    this.forceNBSP = Boolean.valueOf(paramString).booleanValue();
  }
  
  private String c(String paramString)
  {
    int i = 0;
    String str1 = null;
    String str2 = null;
    StringBuffer localStringBuffer = null;
    StringTokenizer localStringTokenizer = null;
    if (paramString != null)
    {
      localStringBuffer = new StringBuffer(2 * paramString.length());
      localStringTokenizer = new StringTokenizer(paramString, this.rl, true);
      while (localStringTokenizer.hasMoreTokens())
      {
        str1 = localStringTokenizer.nextToken();
        if (this.rl.indexOf(str1) >= 0)
        {
          localStringBuffer.append(str1);
        }
        else
        {
          i = 0;
          while ((i < str1.length()) && (str1.charAt(i) == ' '))
          {
            i++;
            localStringBuffer.append(this.rn);
          }
          localStringBuffer.append(str1.substring(i));
        }
      }
      str2 = localStringBuffer.toString();
    }
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetMessagesBC
 * JD-Core Version:    0.7.0.1
 */