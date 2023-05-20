package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendOrderDepositEnvelopesMessage
  extends BaseTask
{
  public static final String SEND_ORDER_DEPOSIT_ENVELOPES_COMMENT = "SendOrderDepositEnvelopesComment";
  public static final String SEND_ORDER_DEPOSIT_ENVELOPES_MEMO = "SendOrderDepositEnvelopesMemo";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    LocalizableString localLocalizableString = null;
    Message localMessage = null;
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        localMessage = new Message(localSecureUser.getLocale());
        ServiceCenterTask.setupServiceCenterMessage(localSecureUser, localMessage, "27", localHashMap);
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendOrderDepositEnvelopesComment", null);
        localMessage.setComment((String)localLocalizableString.localize(localSecureUser.getLocale()));
        localLocalizableString = new LocalizableString("com.ffusion.tasks.service_center", "SendOrderDepositEnvelopesMemo", null);
        localMessage.setMemo((String)localLocalizableString.localize(localSecureUser.getLocale()));
        Messages.sendMessage(localSecureUser, localMessage, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendOrderDepositEnvelopesMessage
 * JD-Core Version:    0.7.0.1
 */