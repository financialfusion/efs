package com.ffusion.webservices.servletfacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpServletRequestFacade
  implements HttpServletRequest
{
  private HttpSession a;
  
  public void setSession(HttpSession paramHttpSession)
  {
    this.a = paramHttpSession;
  }
  
  public String getAuthType()
  {
    return null;
  }
  
  public Cookie[] getCookies()
  {
    return null;
  }
  
  public long getDateHeader(String paramString)
  {
    return 0L;
  }
  
  public String getHeader(String paramString)
  {
    return null;
  }
  
  public Enumeration getHeaders(String paramString)
  {
    return null;
  }
  
  public Enumeration getHeaderNames()
  {
    return null;
  }
  
  public int getIntHeader(String paramString)
  {
    return 0;
  }
  
  public String getMethod()
  {
    return null;
  }
  
  public String getPathInfo()
  {
    return null;
  }
  
  public String getPathTranslated()
  {
    return null;
  }
  
  public String getContextPath()
  {
    return null;
  }
  
  public String getQueryString()
  {
    return null;
  }
  
  public String getRemoteUser()
  {
    return null;
  }
  
  public boolean isUserInRole(String paramString)
  {
    return false;
  }
  
  public Principal getUserPrincipal()
  {
    return null;
  }
  
  public String getRequestedSessionId()
  {
    return null;
  }
  
  public String getRequestURI()
  {
    return null;
  }
  
  public StringBuffer getRequestURL()
  {
    return null;
  }
  
  public String getServletPath()
  {
    return null;
  }
  
  public HttpSession getSession(boolean paramBoolean)
  {
    return this.a;
  }
  
  public HttpSession getSession()
  {
    return this.a;
  }
  
  public boolean isRequestedSessionIdValid()
  {
    return true;
  }
  
  public boolean isRequestedSessionIdFromCookie()
  {
    return false;
  }
  
  public boolean isRequestedSessionIdFromURL()
  {
    return true;
  }
  
  public boolean isRequestedSessionIdFromUrl()
  {
    return false;
  }
  
  public Object getAttribute(String paramString)
  {
    return null;
  }
  
  public Enumeration getAttributeNames()
  {
    return null;
  }
  
  public String getCharacterEncoding()
  {
    return null;
  }
  
  public void setCharacterEncoding(String paramString)
    throws UnsupportedEncodingException
  {}
  
  public int getContentLength()
  {
    return 0;
  }
  
  public String getContentType()
  {
    return null;
  }
  
  public ServletInputStream getInputStream()
    throws IOException
  {
    return null;
  }
  
  public String getParameter(String paramString)
  {
    return null;
  }
  
  public Enumeration getParameterNames()
  {
    return null;
  }
  
  public String[] getParameterValues(String paramString)
  {
    return null;
  }
  
  public Map getParameterMap()
  {
    return null;
  }
  
  public String getProtocol()
  {
    return null;
  }
  
  public String getScheme()
  {
    return null;
  }
  
  public String getServerName()
  {
    return null;
  }
  
  public int getServerPort()
  {
    return 0;
  }
  
  public BufferedReader getReader()
    throws IOException
  {
    return null;
  }
  
  public String getRemoteAddr()
  {
    return null;
  }
  
  public String getRemoteHost()
  {
    return null;
  }
  
  public void setAttribute(String paramString, Object paramObject) {}
  
  public void removeAttribute(String paramString) {}
  
  public Locale getLocale()
  {
    return null;
  }
  
  public Enumeration getLocales()
  {
    return null;
  }
  
  public boolean isSecure()
  {
    return false;
  }
  
  public RequestDispatcher getRequestDispatcher(String paramString)
  {
    return null;
  }
  
  public String getRealPath(String paramString)
  {
    return null;
  }
  
  public String getLocalAddr()
  {
    return null;
  }
  
  public String getLocalName()
  {
    return null;
  }
  
  public int getLocalPort()
  {
    return 0;
  }
  
  public int getRemotePort()
  {
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.HttpServletRequestFacade
 * JD-Core Version:    0.7.0.1
 */