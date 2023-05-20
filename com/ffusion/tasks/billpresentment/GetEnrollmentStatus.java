package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.EnrollmentStatus;
import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEnrollmentStatus
  extends BaseTask
  implements Task, EnrollmentStatus
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    int i = getEnrollmentStatus(localHttpSession);
    if (i == 0) {
      str = this.successURL;
    } else if (i == -1) {
      str = this.taskErrorURL;
    } else if (i == -2) {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected int getEnrollmentStatus(HttpSession paramHttpSession)
  {
    Consumer localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
    if (localConsumer == null)
    {
      this.error = 6506;
      return -1;
    }
    Publisher localPublisher = (Publisher)paramHttpSession.getAttribute("Publisher");
    if (localPublisher == null)
    {
      this.error = 6512;
      return -1;
    }
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      int i = BillPresentment.getEnrollmentStatus(localSecureUser, localPublisher, localConsumer, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      return 0;
    }
    return -2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetEnrollmentStatus
 * JD-Core Version:    0.7.0.1
 */