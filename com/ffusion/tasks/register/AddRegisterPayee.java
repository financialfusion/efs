package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddRegisterPayee
  extends EditRegisterPayee
{
  public AddRegisterPayee()
  {
    this.beanSessionName = "RegisterPayee";
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
    RegisterPayee localRegisterPayee = (RegisterPayee)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterPayee == null)
    {
      this.error = 20007;
      return this.taskErrorURL;
    }
    RegisterPayees localRegisterPayees = (RegisterPayees)localHttpSession.getAttribute(this.collectionSessionName);
    if (validateInput(localRegisterPayee, localRegisterPayees))
    {
      if (!addRegisterPayee(localRegisterPayee, localRegisterPayees, localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean addRegisterPayee(RegisterPayee paramRegisterPayee, RegisterPayees paramRegisterPayees, SecureUser paramSecureUser)
  {
    try
    {
      paramRegisterPayee = Register.addRegisterPayee(paramSecureUser, paramRegisterPayee, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (paramRegisterPayees != null) {
        paramRegisterPayees.add(paramRegisterPayee);
      }
    }
    else {
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.AddRegisterPayee
 * JD-Core Version:    0.7.0.1
 */