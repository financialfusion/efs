package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDateToPost
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public GetDateToPost()
  {
    this.beanSessionName = "WireTransfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireTransfer localWireTransfer = (WireTransfer)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransfer == null)
    {
      this.error = 12005;
      return this.taskErrorURL;
    }
    try
    {
      Date localDate = Wire.getSmartDate(localSecureUser, localWireTransfer.getDateToPostValue());
      localWireTransfer.setDateToPost(new DateTime(localDate, localSecureUser.getLocale()));
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetDateToPost
 * JD-Core Version:    0.7.0.1
 */