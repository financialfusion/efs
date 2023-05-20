package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRegisterCategories
  extends ExtendedBaseTask
  implements Task
{
  public GetRegisterCategories()
  {
    this.collectionSessionName = "RegisterCategories";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterCategories != null)
    {
      localRegisterCategories.setFilter("All");
      return this.successURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return this.taskErrorURL;
    }
    localRegisterCategories = new RegisterCategories();
    HashMap localHashMap = new HashMap();
    localHashMap.put("REGISTERCATEGORIES", localRegisterCategories);
    try
    {
      localRegisterCategories = Register.getRegisterCategories(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.collectionSessionName, localRegisterCategories);
    } else {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetRegisterCategories
 * JD-Core Version:    0.7.0.1
 */