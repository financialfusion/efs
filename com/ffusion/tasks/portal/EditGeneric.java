package com.ffusion.tasks.portal;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditGeneric
  extends EditPortal
  implements Task
{
  private String mk = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    if (!processShowItem(paramHttpServletRequest, this.mk))
    {
      this.error = 9009;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setItemName(String paramString)
  {
    this.mk = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditGeneric
 * JD-Core Version:    0.7.0.1
 */