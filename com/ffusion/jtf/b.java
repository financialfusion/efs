package com.ffusion.jtf;

import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class b
  extends HttpServletRequestWrapper
{
  private static final String jdField_byte = "URL_ENCRYPTOR";
  private static final String a = "'";
  private static final String jdField_for = "&#39;";
  private String jdField_int = "ISO-8859-1";
  private String jdField_if = "UTF-8";
  private boolean jdField_try = false;
  private HashMap jdField_new = new HashMap();
  private HttpServletRequest jdField_do;
  
  public b(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2)
  {
    super(paramHttpServletRequest);
    this.jdField_int = paramString1;
    this.jdField_if = paramString2;
    this.jdField_do = paramHttpServletRequest;
  }
  
  public String getAuthType()
  {
    return this.jdField_do.getAuthType();
  }
  
  public String getContextPath()
  {
    return this.jdField_do.getContextPath();
  }
  
  public Cookie[] getCookies()
  {
    return this.jdField_do.getCookies();
  }
  
  public long getDateHeader(String paramString)
  {
    return this.jdField_do.getDateHeader(paramString);
  }
  
  public String getHeader(String paramString)
  {
    return this.jdField_do.getHeader(paramString);
  }
  
  public Enumeration getHeaderNames()
  {
    return this.jdField_do.getHeaderNames();
  }
  
  public Enumeration getHeaders(String paramString)
  {
    return this.jdField_do.getHeaders(paramString);
  }
  
  public int getIntHeader(String paramString)
  {
    return this.jdField_do.getIntHeader(paramString);
  }
  
  public String getMethod()
  {
    return this.jdField_do.getMethod();
  }
  
  public String getPathInfo()
  {
    return this.jdField_do.getPathInfo();
  }
  
  public String getPathTranslated()
  {
    return this.jdField_do.getPathTranslated();
  }
  
  public String getQueryString()
  {
    String str1 = this.jdField_do.getQueryString();
    UrlEncryptor localUrlEncryptor = (UrlEncryptor)this.jdField_do.getSession().getAttribute("URL_ENCRYPTOR");
    String str2 = localUrlEncryptor.decrypt(str1);
    if (str2 != null)
    {
      if (!str2.equals(str1)) {
        try
        {
          str2 = new String(str2.getBytes(this.jdField_if), this.jdField_int);
        }
        catch (Exception localException)
        {
          StringWriter localStringWriter = new StringWriter();
          PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
          localException.printStackTrace(localPrintWriter);
          DebugLog.log(Level.FINE, localStringWriter.toString());
        }
      }
      str2 = URLDecoder.decode(str2);
      str2 = a(str2, "&#39;", "'");
    }
    return str2;
  }
  
  private String a(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1;
    if ((paramString1 != null) && (paramString1.indexOf(paramString2) != -1))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString1);
      int i = 0;
      int j = 0;
      int k = paramString2.length();
      int m = localStringBuffer.length();
      while (j != m)
      {
        i = localStringBuffer.toString().indexOf(paramString2, j);
        if (i != -1)
        {
          j = i + k;
          if (j > m) {
            j = m;
          }
          localStringBuffer.replace(i, j, paramString3);
        }
        else
        {
          j = m;
        }
      }
      str = localStringBuffer.toString();
    }
    return str;
  }
  
  public String getRemoteUser()
  {
    return this.jdField_do.getRemoteUser();
  }
  
  public String getRequestedSessionId()
  {
    return this.jdField_do.getRequestedSessionId();
  }
  
  public String getRequestURI()
  {
    return this.jdField_do.getRequestURI();
  }
  
  public String getServletPath()
  {
    return this.jdField_do.getServletPath();
  }
  
  public HttpSession getSession()
  {
    return this.jdField_do.getSession();
  }
  
  public HttpSession getSession(boolean paramBoolean)
  {
    return this.jdField_do.getSession(paramBoolean);
  }
  
  public Principal getUserPrincipal()
  {
    return this.jdField_do.getUserPrincipal();
  }
  
  public boolean isRequestedSessionIdFromCookie()
  {
    return this.jdField_do.isRequestedSessionIdFromCookie();
  }
  
  public boolean isRequestedSessionIdFromUrl()
  {
    return this.jdField_do.isRequestedSessionIdFromUrl();
  }
  
  public boolean isRequestedSessionIdFromURL()
  {
    return this.jdField_do.isRequestedSessionIdFromURL();
  }
  
  public boolean isRequestedSessionIdValid()
  {
    return this.jdField_do.isRequestedSessionIdValid();
  }
  
  public boolean isUserInRole(String paramString)
  {
    return this.jdField_do.isUserInRole(paramString);
  }
  
  public Object getAttribute(String paramString)
  {
    return this.jdField_do.getAttribute(paramString);
  }
  
  public Enumeration getAttributeNames()
  {
    return this.jdField_do.getAttributeNames();
  }
  
  public String getCharacterEncoding()
  {
    return this.jdField_do.getCharacterEncoding();
  }
  
  public int getContentLength()
  {
    return this.jdField_do.getContentLength();
  }
  
  public String getContentType()
  {
    return this.jdField_do.getContentType();
  }
  
  public ServletInputStream getInputStream()
    throws IOException
  {
    return this.jdField_do.getInputStream();
  }
  
  public Locale getLocale()
  {
    return this.jdField_do.getLocale();
  }
  
  public Enumeration getLocales()
  {
    return this.jdField_do.getLocales();
  }
  
  private void a()
  {
    String str1 = getQueryString();
    if ((str1 == null) || (str1.length() <= 0)) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, "&");
    Object localObject1 = null;
    String str2 = null;
    Object localObject2;
    while (localStringTokenizer.hasMoreElements())
    {
      localObject2 = (String)localStringTokenizer.nextElement();
      int i = ((String)localObject2).indexOf("=");
      if (i < 0)
      {
        if (str2 != null) {
          str2 = str2 + "&" + (String)localObject2;
        }
      }
      else
      {
        if (localObject1 != null)
        {
          String[] arrayOfString = new String[1];
          arrayOfString[0] = str2;
          this.jdField_new.put(localObject1, arrayOfString);
        }
        localObject1 = ((String)localObject2).substring(0, i);
        str2 = ((String)localObject2).substring(i + "=".length(), ((String)localObject2).length());
      }
    }
    if (localObject1 != null)
    {
      localObject2 = new String[1];
      localObject2[0] = str2;
      this.jdField_new.put(localObject1, localObject2);
    }
  }
  
  public String getParameter(String paramString)
  {
    if (!this.jdField_try)
    {
      a();
      this.jdField_try = true;
    }
    if (!this.jdField_new.containsKey(paramString)) {
      return this.jdField_do.getParameter(paramString);
    }
    String[] arrayOfString = (String[])this.jdField_new.get(paramString);
    if (arrayOfString.length == 0) {
      return null;
    }
    return arrayOfString[0];
  }
  
  public Enumeration getParameterNames()
  {
    if (!this.jdField_try)
    {
      a();
      this.jdField_try = true;
    }
    Iterator localIterator = this.jdField_new.keySet().iterator();
    Vector localVector = new Vector();
    while (localIterator.hasNext()) {
      localVector.add(localIterator.next());
    }
    Enumeration localEnumeration = this.jdField_do.getParameterNames();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if (!localVector.contains(localObject)) {
        localVector.add(localObject);
      }
    }
    return localVector.elements();
  }
  
  public String[] getParameterValues(String paramString)
  {
    if (!this.jdField_try)
    {
      a();
      this.jdField_try = true;
    }
    if (!this.jdField_new.containsKey(paramString)) {
      return this.jdField_do.getParameterValues(paramString);
    }
    return (String[])this.jdField_new.get(paramString);
  }
  
  public String getProtocol()
  {
    return this.jdField_do.getProtocol();
  }
  
  public BufferedReader getReader()
    throws IOException
  {
    return this.jdField_do.getReader();
  }
  
  public String getRealPath(String paramString)
  {
    return this.jdField_do.getRealPath(paramString);
  }
  
  public String getRemoteAddr()
  {
    return this.jdField_do.getRemoteAddr();
  }
  
  public String getRemoteHost()
  {
    return this.jdField_do.getRemoteHost();
  }
  
  public RequestDispatcher getRequestDispatcher(String paramString)
  {
    return this.jdField_do.getRequestDispatcher(paramString);
  }
  
  public String getScheme()
  {
    return this.jdField_do.getScheme();
  }
  
  public String getServerName()
  {
    return this.jdField_do.getServerName();
  }
  
  public int getServerPort()
  {
    return this.jdField_do.getServerPort();
  }
  
  public boolean isSecure()
  {
    return this.jdField_do.isSecure();
  }
  
  public void removeAttribute(String paramString)
  {
    this.jdField_do.removeAttribute(paramString);
  }
  
  public void setAttribute(String paramString, Object paramObject)
  {
    this.jdField_do.setAttribute(paramString, paramObject);
  }
  
  public StringBuffer getRequestURL()
  {
    return null;
  }
  
  public Map getParameterMap()
  {
    return null;
  }
  
  public void setCharacterEncoding(String paramString)
    throws UnsupportedEncodingException
  {}
  
  public void jdField_if(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public void a(String paramString)
  {
    this.jdField_if = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.b
 * JD-Core Version:    0.7.0.1
 */