package com.ffusion.webservices.servletfacade;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletContextFacade
  implements ServletContext
{
  public ServletContext getContext(String paramString)
  {
    return null;
  }
  
  public int getMajorVersion()
  {
    return 0;
  }
  
  public int getMinorVersion()
  {
    return 0;
  }
  
  public String getMimeType(String paramString)
  {
    return "";
  }
  
  public Set getResourcePaths(String paramString)
  {
    return null;
  }
  
  public URL getResource(String paramString)
    throws MalformedURLException
  {
    return new URL("");
  }
  
  public InputStream getResourceAsStream(String paramString)
  {
    return null;
  }
  
  public RequestDispatcher getRequestDispatcher(String paramString)
  {
    return null;
  }
  
  public RequestDispatcher getNamedDispatcher(String paramString)
  {
    return null;
  }
  
  public Servlet getServlet(String paramString)
    throws ServletException
  {
    return null;
  }
  
  public Enumeration getServlets()
  {
    return null;
  }
  
  public Enumeration getServletNames()
  {
    return null;
  }
  
  public void log(String paramString) {}
  
  public void log(Exception paramException, String paramString) {}
  
  public void log(String paramString, Throwable paramThrowable) {}
  
  public String getRealPath(String paramString)
  {
    return null;
  }
  
  public String getServerInfo()
  {
    return null;
  }
  
  public String getInitParameter(String paramString)
  {
    return null;
  }
  
  public Enumeration getInitParameterNames()
  {
    return null;
  }
  
  public Object getAttribute(String paramString)
  {
    return null;
  }
  
  public Enumeration getAttributeNames()
  {
    return null;
  }
  
  public void setAttribute(String paramString, Object paramObject) {}
  
  public void removeAttribute(String paramString) {}
  
  public String getServletContextName()
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.ServletContextFacade
 * JD-Core Version:    0.7.0.1
 */