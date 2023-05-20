package com.ffusion.webservices.servletfacade;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class HttpServletFacade
  extends HttpServlet
{
  private ServletContext a = null;
  
  public HttpServletFacade(ServletContext paramServletContext)
  {
    this.a = paramServletContext;
  }
  
  public ServletContext getServletContext()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.HttpServletFacade
 * JD-Core Version:    0.7.0.1
 */