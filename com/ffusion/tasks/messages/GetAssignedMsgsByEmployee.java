package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAssignedMsgsByEmployee
  extends BaseTask
  implements Task
{
  private String sl = null;
  private String sk = "MessageCounts";
  private int sj = 0;
  private String sm = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (!g(this.sl))
    {
      this.error = 8032;
      return this.taskErrorURL;
    }
    MessageCounts localMessageCounts = null;
    try
    {
      localMessageCounts = Messages.getAssignedMessageCountsByEmployee(localSecureUser, this.sl, localHashMap, getAffiliateBankId());
      if (localMessageCounts != null) {
        jdMethod_for(localMessageCounts.size());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.sk, localMessageCounts);
    }
    return str;
  }
  
  public String getEmployeeId()
  {
    return this.sl;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.sl = paramString;
  }
  
  private boolean g(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.sk = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.sk;
  }
  
  public String getAffiliateBankId()
  {
    return this.sm;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.sm = paramString;
  }
  
  public int getCount()
  {
    return this.sj;
  }
  
  private void jdMethod_for(int paramInt)
  {
    this.sj = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetAssignedMsgsByEmployee
 * JD-Core Version:    0.7.0.1
 */