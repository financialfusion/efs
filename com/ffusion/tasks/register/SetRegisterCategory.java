package com.ffusion.tasks.register;

import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetRegisterCategory
  extends ExtendedBaseTask
  implements Task
{
  public SetRegisterCategory()
  {
    this.beanSessionName = "RegisterCategory";
    this.collectionSessionName = "RegisterCategories";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.id.equals(""))
    {
      RegisterCategory localRegisterCategory1 = new RegisterCategory();
      localHttpSession.setAttribute(this.beanSessionName, localRegisterCategory1);
      return this.successURL;
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
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.collectionSessionName);
    if (localRegisterCategories != null)
    {
      RegisterCategory localRegisterCategory2 = localRegisterCategories.getById(this.id);
      if (localRegisterCategory2 != null)
      {
        RegisterCategory localRegisterCategory3 = new RegisterCategory();
        localRegisterCategory3.set(localRegisterCategory2);
        localHttpSession.setAttribute(this.beanSessionName, localRegisterCategory3);
      }
      else
      {
        this.error = 20203;
        return this.taskErrorURL;
      }
    }
    else
    {
      this.error = 20006;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetRegisterCategory
 * JD-Core Version:    0.7.0.1
 */