package com.ffusion.jtf.tasks;

import com.ffusion.jtf.Task;
import com.ffusion.jtf.template.TemplateCacheManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearCache
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    TemplateCacheManager localTemplateCacheManager = (TemplateCacheManager)localServletContext.getAttribute("com.ffusion.jtf.template.TemplateCacheManager");
    localTemplateCacheManager.clearCache();
    String str = "The template cache has been cleared.";
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println(str);
    localPrintWriter.flush();
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.ClearCache
 * JD-Core Version:    0.7.0.1
 */