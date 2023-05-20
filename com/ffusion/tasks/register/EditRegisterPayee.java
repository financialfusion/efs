package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditRegisterPayee
  extends ExtendedBaseTask
  implements Task
{
  public EditRegisterPayee()
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
      if (!jdMethod_for(localRegisterPayee, localRegisterPayees, localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private boolean jdMethod_for(RegisterPayee paramRegisterPayee, RegisterPayees paramRegisterPayees, SecureUser paramSecureUser)
  {
    try
    {
      Register.modifyRegisterPayee(paramSecureUser, paramRegisterPayee, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (paramRegisterPayees != null)
      {
        paramRegisterPayees.removeById(paramRegisterPayee.getId());
        paramRegisterPayees.add(paramRegisterPayee);
      }
    }
    else {
      return false;
    }
    return true;
  }
  
  protected boolean validateInput(RegisterPayee paramRegisterPayee, RegisterPayees paramRegisterPayees)
  {
    if (this.validate != null)
    {
      if (this.validate.indexOf("NAME") != -1)
      {
        if ((paramRegisterPayee.getName() == null) || (paramRegisterPayee.getName().trim().length() == 0) || (paramRegisterPayee.getName().trim().length() > 40))
        {
          this.error = 20108;
          return false;
        }
        if (paramRegisterPayees != null)
        {
          Iterator localIterator = paramRegisterPayees.iterator();
          while (localIterator.hasNext())
          {
            RegisterPayee localRegisterPayee = (RegisterPayee)localIterator.next();
            if ((localRegisterPayee.getIdValue() != paramRegisterPayee.getIdValue()) && (localRegisterPayee.getName().equals(paramRegisterPayee.getName())))
            {
              this.error = 20127;
              return false;
            }
          }
        }
      }
      this.validate = null;
    }
    return true;
  }
  
  protected boolean validateInput(RegisterPayee paramRegisterPayee)
  {
    if (this.validate != null)
    {
      if ((this.validate.indexOf("NAME") != -1) && ((paramRegisterPayee.getName() == null) || (paramRegisterPayee.getName().trim().length() == 0)))
      {
        this.error = 20108;
        return false;
      }
      this.validate = null;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterPayee
 * JD-Core Version:    0.7.0.1
 */