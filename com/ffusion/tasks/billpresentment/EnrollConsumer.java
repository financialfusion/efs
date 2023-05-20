package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollConsumer
  extends ModifyConsumer
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    int i = enrollConsumer(localHttpSession);
    if (i == 0) {
      str = getSuccessURL();
    } else if (i == -2) {
      str = getServiceErrorURL();
    } else if (i == -1) {
      str = getTaskErrorURL();
    }
    return str;
  }
  
  protected int enrollConsumer(HttpSession paramHttpSession)
  {
    int i = 0;
    Consumer localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
    if (localConsumer != null)
    {
      Publisher localPublisher = (Publisher)paramHttpSession.getAttribute("Publisher");
      if (localPublisher == null)
      {
        setError(6511);
        i = -1;
      }
      setError(0);
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        localConsumer = BillPresentment.enrollConsumer(localSecureUser, localPublisher, localConsumer, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        int j = MapError.mapError(localCSILException1);
        setError(j);
      }
      if (getErrorValue() == 0)
      {
        localConsumer.setStatusCode("ACTIVE");
        ConsumerStatus localConsumerStatus = new ConsumerStatus();
        localConsumerStatus.setID(localConsumer.getConsumerIDValue());
        try
        {
          localConsumerStatus = BillPresentment.getConsumerStatus(localSecureUser, localConsumer, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          int k = MapError.mapError(localCSILException2);
          setError(k);
        }
        paramHttpSession.setAttribute("Consumer", localConsumer);
        if (getErrorValue() == 0) {
          paramHttpSession.setAttribute("ConsumerStatus", localConsumerStatus);
        } else {
          i = -2;
        }
      }
      else
      {
        i = -2;
      }
    }
    else
    {
      i = -1;
      setError(6506);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.EnrollConsumer
 * JD-Core Version:    0.7.0.1
 */