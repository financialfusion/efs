package com.ffusion.jtf.tasks;

import com.ffusion.jtf.Task;
import com.ffusion.jtf.template.TemplateCacheManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UseCache
  implements Task
{
  private static final String a58 = "Template caching is now on.";
  private static final String a57 = "Template caching is now off.";
  private boolean a56 = true;
  
  public UseCache(boolean paramBoolean)
  {
    this.a56 = paramBoolean;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    TemplateCacheManager localTemplateCacheManager = (TemplateCacheManager)localServletContext.getAttribute("com.ffusion.jtf.template.TemplateCacheManager");
    localTemplateCacheManager.setUseCache(this.a56);
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    if (this.a56) {
      localPrintWriter.println("Template caching is now on.");
    } else {
      localPrintWriter.println("Template caching is now off.");
    }
    localPrintWriter.flush();
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.UseCache
 * JD-Core Version:    0.7.0.1
 */