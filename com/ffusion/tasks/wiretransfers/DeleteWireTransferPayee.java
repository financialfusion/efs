package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteWireTransferPayee
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  public DeleteWireTransferPayee()
  {
    this.beanSessionName = "WireTransferPayee";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    try
    {
      Wire.deleteWireTransferPayee(localSecureUser, localWireTransferPayee, localHashMap);
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
 * Qualified Name:     com.ffusion.tasks.wiretransfers.DeleteWireTransferPayee
 * JD-Core Version:    0.7.0.1
 */