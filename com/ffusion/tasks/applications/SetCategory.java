package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCategory
  extends BaseTask
  implements Task
{
  protected String id = "0";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Categories localCategories = (Categories)localHttpSession.getAttribute("Categories");
    if (localCategories == null)
    {
      this.error = 7230;
      return this.taskErrorURL;
    }
    Category localCategory = localCategories.getByID(this.id);
    if (localCategory != null)
    {
      localHttpSession.setAttribute("Category", localCategory);
      return this.successURL;
    }
    this.error = 7231;
    return this.taskErrorURL;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SetCategory
 * JD-Core Version:    0.7.0.1
 */