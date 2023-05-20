package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteRegisterCategory
  extends ExtendedBaseTask
  implements Task
{
  public DeleteRegisterCategory()
  {
    this.collectionSessionName = "RegisterCategories";
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
      this.error = 20107;
      return this.taskErrorURL;
    }
    RegisterCategory localRegisterCategory = new RegisterCategory();
    localRegisterCategory.setId(this.id);
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterCategories != null)
    {
      localRegisterCategories.setCurrent(this.id);
      localRegisterCategory.setName(localRegisterCategories.getName());
    }
    try
    {
      Register.deleteRegisterCategory(localSecureUser, localRegisterCategory, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (localRegisterCategories != null)
      {
        localRegisterCategories.removeById(this.id);
        localHttpSession.setAttribute(this.collectionSessionName, localRegisterCategories);
      }
    }
    else {
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.DeleteRegisterCategory
 * JD-Core Version:    0.7.0.1
 */