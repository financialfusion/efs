package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class SendPaymentMessage
  extends SendMessage
{
  protected String paymentID;
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str = this.taskErrorURL;
    this.error = 0;
    this.processFlag = false;
    if (this.paymentID == null)
    {
      this.error = 8014;
    }
    else
    {
      Payments localPayments = (Payments)paramHttpSession.getAttribute("Payments");
      if (localPayments == null)
      {
        this.error = 8015;
      }
      else
      {
        Payment localPayment = localPayments.getByID(this.paymentID);
        if (localPayment == null)
        {
          this.error = 8016;
        }
        else
        {
          HashMap localHashMap = new HashMap();
          localHashMap.put("OBJECT", localPayment);
          localHashMap.put("SERVICE", (Messaging)paramHttpSession.getAttribute("com.ffusion.services.Messaging3"));
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          try
          {
            Messages.sendMessage(localSecureUser, this, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str = this.serviceErrorURL;
          }
          if (this.error == 0) {
            str = this.successURL;
          }
        }
      }
    }
    return str;
  }
  
  public void setPaymentID(String paramString)
  {
    this.paymentID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendPaymentMessage
 * JD-Core Version:    0.7.0.1
 */