package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
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

public class EditRegisterCategory
  extends ExtendedBaseTask
  implements Task
{
  public static final int MAX_CATEGORY_NAME_LEN = 40;
  public static final int MAX_CATEGORY_DESC_LEN = 50;
  
  public EditRegisterCategory()
  {
    this.beanSessionName = "RegisterCategory";
    this.collectionSessionName = "RegisterCategories";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
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
      if (!jdMethod_for(localRegisterCategory, localRegisterCategories, localSecureUser)) {
        return this.serviceErrorURL;
      }
    }
    else {
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private boolean jdMethod_for(RegisterCategory paramRegisterCategory, RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
  {
    try
    {
      Register.modifyRegisterCategory(paramSecureUser, paramRegisterCategory, null);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (paramRegisterCategories != null)
      {
        paramRegisterCategories.removeById(paramRegisterCategory.getId());
        paramRegisterCategories.add(paramRegisterCategory);
        paramRegisterCategories.setCurrent(paramRegisterCategory.getId());
        if (paramRegisterCategories.hasSubcategories())
        {
          RegisterCategories localRegisterCategories = paramRegisterCategories.getSubcategories();
          Iterator localIterator = localRegisterCategories.iterator();
          while (localIterator.hasNext())
          {
            RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
            localRegisterCategory.setType(paramRegisterCategory.getTypeValue());
          }
        }
      }
    }
    else {
      return false;
    }
    return true;
  }
  
  protected boolean validateInput(RegisterCategory paramRegisterCategory, RegisterCategories paramRegisterCategories)
  {
    if (this.validate != null)
    {
      if (this.validate.indexOf("NAME") >= 0)
      {
        if ((paramRegisterCategory.getName() == null) || (paramRegisterCategory.getName().trim().length() <= 0))
        {
          this.error = 20101;
          return false;
        }
        paramRegisterCategory.setName(paramRegisterCategory.getName().trim());
        if (paramRegisterCategories != null)
        {
          String str = paramRegisterCategories.getFilter();
          paramRegisterCategories.setFilter("PARENT_CATEGORY=" + paramRegisterCategory.getParentCategory());
          Iterator localIterator = paramRegisterCategories.iterator();
          while (localIterator.hasNext())
          {
            RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
            if ((localRegisterCategory.getIdValue() != paramRegisterCategory.getIdValue()) && (localRegisterCategory.getName().equals(paramRegisterCategory.getName())))
            {
              this.error = 20126;
              paramRegisterCategories.setFilter(str);
              return false;
            }
          }
          paramRegisterCategories.setFilter(str);
        }
      }
      if ((paramRegisterCategory.getName() != null) && (paramRegisterCategory.getName().length() > 40))
      {
        this.error = 20101;
        return false;
      }
      if ((this.validate.indexOf("DESCRIPTION") >= 0) && ((paramRegisterCategory.getDescription() == null) || (paramRegisterCategory.getDescription().length() <= 0)))
      {
        this.error = 20100;
        return false;
      }
      if ((paramRegisterCategory.getDescription() != null) && (paramRegisterCategory.getDescription().length() > 50))
      {
        this.error = 20100;
        return false;
      }
      if ((this.validate.indexOf("TYPE") != -1) && (paramRegisterCategory.getTypeValue() != 0) && (paramRegisterCategory.getTypeValue() != 1) && (paramRegisterCategory.getTypeValue() != 2) && (paramRegisterCategory.getTypeValue() != 3))
      {
        this.error = 20121;
        return false;
      }
      this.validate = null;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.EditRegisterCategory
 * JD-Core Version:    0.7.0.1
 */