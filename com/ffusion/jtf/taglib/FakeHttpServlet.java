package com.ffusion.jtf.taglib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class FakeHttpServlet
  extends HttpServlet
{
  ServletContext a = null;
  
  FakeHttpServlet(ServletContext paramServletContext)
  {
    this.a = paramServletContext;
  }
  
  public ServletContext getServletContext()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.FakeHttpServlet
 * JD-Core Version:    0.7.0.1
 */