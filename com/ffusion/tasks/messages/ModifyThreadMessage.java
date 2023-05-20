package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyThreadMessage
  extends MessageThread
  implements Task
{
  protected static String MESSAGETHREADOWNER = "MESSAGETHREADOWNER";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    if (this.initFlag)
    {
      if (!init(localHttpSession)) {
        str = this.taskErrorURL;
      } else {
        str = this.successURL;
      }
    }
    else if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        str = modifyThreadMessage(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    MessageThread localMessageThread = (MessageThread)paramHttpSession.getAttribute("ThreadMessage");
    if (localMessageThread == null)
    {
      this.error = 8024;
      this.initFlag = false;
      return false;
    }
    setThreadID(localMessageThread.getThreadID());
    setEmployeeID(localMessageThread.getEmployeeID());
    this.initFlag = false;
    return true;
  }
  
  protected String modifyThreadMessage(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.processFlag = false;
    HashMap localHashMap = null;
    localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      MessageAdmin.modifyMessageThread(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      MessageThread localMessageThread = (MessageThread)paramHttpSession.getAttribute("ThreadMessage");
      if (localMessageThread == null)
      {
        this.error = 8024;
        str = this.taskErrorURL;
      }
      else
      {
        localMessageThread.setEmployeeID(getEmployeeID());
        MessageQueueMembers localMessageQueueMembers1 = (MessageQueueMembers)paramHttpSession.getAttribute("MessageQueueActiveMembers");
        MessageQueueMembers localMessageQueueMembers2 = (MessageQueueMembers)paramHttpSession.getAttribute("MessageQueueInactiveMembers");
        BankEmployee localBankEmployee = null;
        if (localMessageQueueMembers1 != null) {
          localBankEmployee = localMessageQueueMembers1.getByID(getEmployeeID());
        }
        if (localBankEmployee != null) {
          localMessageThread.setEmployeeName(localBankEmployee.getFullNameWithLoginId());
        }
        if (localMessageQueueMembers2 != null) {
          localBankEmployee = localMessageQueueMembers2.getByID(getEmployeeID());
        }
        paramHttpSession.setAttribute("ThreadMessage", localMessageThread);
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null)
    {
      if ((this.validate.indexOf(MESSAGETHREADOWNER) != -1) && ((getEmployeeID() == null) || (getEmployeeID().length() == 0))) {
        this.error = 8026;
      }
      this.validate = null;
    }
    return this.error == 0;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.ModifyThreadMessage
 * JD-Core Version:    0.7.0.1
 */