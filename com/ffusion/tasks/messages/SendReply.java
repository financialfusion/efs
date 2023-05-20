package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class SendReply
  extends SendMessage
  implements Task
{
  protected String objectName = "ThreadMessage";
  protected String status;
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    this.processFlag = false;
    HashMap localHashMap = new HashMap();
    Object localObject = paramHttpSession.getAttribute(this.objectName);
    if ((localObject == null) || (!(localObject instanceof Message)))
    {
      this.error = 8002;
      return this.taskErrorURL;
    }
    if ((getMemo() == null) || (getMemo().trim().length() == 0))
    {
      this.error = 8007;
      return this.taskErrorURL;
    }
    if ((this.status == null) || (this.status.length() == 0)) {
      this.status = String.valueOf(4);
    }
    set("Status", this.status);
    localHashMap.put("OBJECT", localObject);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      Message localMessage = (Message)localObject;
      set("MESSAGE_ID", (String)localMessage.get("MESSAGE_ID"));
      setTo((String)localMessage.get("FROM_ID"));
      setToType(String.valueOf(0));
      setFrom((String)localMessage.get("TO_ID"));
      setFromType(String.valueOf(1));
      setType(String.valueOf(2));
      setCaseNum(localMessage.getCaseNum());
      MessageAdmin.sendReply(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public void setObjectName(String paramString)
  {
    this.objectName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendReply
 * JD-Core Version:    0.7.0.1
 */