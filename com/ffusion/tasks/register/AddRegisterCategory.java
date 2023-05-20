package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddRegisterCategory
  extends EditRegisterCategory
{
  public AddRegisterCategory()
  {
    this.beanSessionName = "RegisterCategory";
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
    RegisterCategory localRegisterCategory = (RegisterCategory)localHttpSession.getAttribute(this.beanSessionName);
    if (localRegisterCategory == null)
    {
      this.error = 20005;
      return this.taskErrorURL;
    }
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.collectionSessionName);
    if (validateInput(localRegisterCategory, localRegisterCategories))
    {
      if (!addRegisterCategory(localRegisterCategory, localRegisterCategories, localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  protected boolean addRegisterCategory(RegisterCategory paramRegisterCategory, RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
  {
    try
    {
      paramRegisterCategory = Register.addRegisterCategory(paramSecureUser, paramRegisterCategory, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (paramRegisterCategories != null) {
        paramRegisterCategories.add(paramRegisterCategory);
      }
    }
    else {
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.AddRegisterCategory
 * JD-Core Version:    0.7.0.1
 */