package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteRegisterPayee
  extends ExtendedBaseTask
  implements Task
{
  public DeleteRegisterPayee()
  {
    this.collectionSessionName = "RegisterPayees";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    try
    {
      Integer.parseInt(this.id);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 20111;
      return this.taskErrorURL;
    }
    RegisterPayees localRegisterPayees = (RegisterPayees)localHttpSession.getAttribute(this.collectionSessionName);
    RegisterPayee localRegisterPayee = null;
    if (localRegisterPayees != null)
    {
      localRegisterPayee = localRegisterPayees.getById(this.id);
    }
    else
    {
      localRegisterPayee = new RegisterPayee();
      localRegisterPayee.setId(this.id);
    }
    try
    {
      Register.deleteRegisterPayee(localSecureUser, localRegisterPayee, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (localRegisterPayees != null)
      {
        localRegisterPayees.removeById(this.id);
        localHttpSession.setAttribute(this.collectionSessionName, localRegisterPayees);
      }
    }
    else {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.DeleteRegisterPayee
 * JD-Core Version:    0.7.0.1
 */