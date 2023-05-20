package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActivateEBillAccount
  extends ModifyEBillAccount
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    setError(0);
    int i = activateEBillAccount(localHttpSession);
    if (i == 0) {
      str = getSuccessURL();
    } else if (i == -1) {
      str = getTaskErrorURL();
    } else if (i == -2) {
      str = getServiceErrorURL();
    }
    return str;
  }
  
  public int activateEBillAccount(HttpSession paramHttpSession)
  {
    int i = 0;
    if (!validateInput(paramHttpSession))
    {
      i = -1;
    }
    else if (getProcessValue())
    {
      Consumer localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
      if (localConsumer == null)
      {
        i = -1;
        setError(6506);
      }
      else
      {
        setConsumerID(localConsumer.getConsumerIDValue());
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        setError(0);
        try
        {
          EBillAccount localEBillAccount1 = BillPresentment.activateAccount(localSecureUser, this, localHashMap);
          set(localEBillAccount1);
        }
        catch (CSILException localCSILException)
        {
          setError(MapError.mapError(localCSILException));
        }
        if (getErrorValue() == 0)
        {
          EBillAccount localEBillAccount2 = new EBillAccount(getLocale());
          localEBillAccount2.set(this);
          paramHttpSession.setAttribute("EBillAccount", localEBillAccount2);
          EBillAccounts localEBillAccounts = (EBillAccounts)paramHttpSession.getAttribute("EBillAccounts");
          if (localEBillAccounts != null) {
            localEBillAccounts.add(localEBillAccount2);
          }
        }
        else
        {
          i = -2;
        }
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ActivateEBillAccount
 * JD-Core Version:    0.7.0.1
 */