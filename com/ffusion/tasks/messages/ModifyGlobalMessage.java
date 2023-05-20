package com.ffusion.tasks.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyGlobalMessage
  extends CreateGlobalMessage
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (!validate(localHttpSession)) {
      return this.taskErrorURL;
    }
    this._createdMessage.setFromID(localSecureUser.getProfileID());
    try
    {
      Messages.modifyGlobalMessage(localSecureUser, this._createdMessage, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setGlobalMsgID(String paramString)
  {
    this._createdMessage.setGlobalMsgID(paramString);
  }
  
  public void setCreateDate(String paramString)
  {
    if (paramString.length() > 0) {
      this._createdMessage.setCreateDate(paramString);
    } else {
      this._createdMessage.setCreateDate(new DateTime(Locale.getDefault()));
    }
  }
  
  public String getCreateDate()
  {
    return this._createdMessage.getCreateDate();
  }
  
  public void setStatus(String paramString)
  {
    this._createdMessage.setStatus(paramString);
  }
  
  public String getStatus()
  {
    return this._createdMessage.getStatus();
  }
  
  public void setGlobalMsgBodyID(String paramString)
  {
    this._createdMessage.setGlobalMsgBodyID(paramString);
  }
  
  public String getGlobalMsgBodyID()
  {
    return this._createdMessage.getGlobalMsgBodyID();
  }
  
  public Object get(Object paramObject)
  {
    return this._createdMessage.get(paramObject);
  }
  
  public void put(Object paramObject1, Object paramObject2)
  {
    this._createdMessage.put(paramObject1, paramObject2);
  }
  
  public GlobalMessage getGlobalMessage()
  {
    return this._createdMessage;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModifyGlobalMessage
 * JD-Core Version:    0.7.0.1
 */