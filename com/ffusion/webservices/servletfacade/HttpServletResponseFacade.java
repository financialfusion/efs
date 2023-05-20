package com.ffusion.webservices.servletfacade;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseFacade
  implements HttpServletResponse
{
  public void addCookie(Cookie paramCookie) {}
  
  public boolean containsHeader(String paramString)
  {
    return true;
  }
  
  public String encodeURL(String paramString)
  {
    return null;
  }
  
  public String encodeRedirectURL(String paramString)
  {
    return null;
  }
  
  public String encodeUrl(String paramString)
  {
    return null;
  }
  
  public String encodeRedirectUrl(String paramString)
  {
    return null;
  }
  
  public void sendError(int paramInt, String paramString)
    throws IOException
  {}
  
  public void sendError(int paramInt)
    throws IOException
  {}
  
  public void sendRedirect(String paramString)
    throws IOException
  {}
  
  public void setDateHeader(String paramString, long paramLong) {}
  
  public void addDateHeader(String paramString, long paramLong) {}
  
  public void setHeader(String paramString1, String paramString2) {}
  
  public void addHeader(String paramString1, String paramString2) {}
  
  public void setIntHeader(String paramString, int paramInt) {}
  
  public void addIntHeader(String paramString, int paramInt) {}
  
  public void setStatus(int paramInt) {}
  
  public void setStatus(int paramInt, String paramString) {}
  
  public String getCharacterEncoding()
  {
    return null;
  }
  
  public ServletOutputStream getOutputStream()
    throws IOException
  {
    return null;
  }
  
  public PrintWriter getWriter()
    throws IOException
  {
    return null;
  }
  
  public void setContentLength(int paramInt) {}
  
  public void setContentType(String paramString) {}
  
  public void setBufferSize(int paramInt) {}
  
  public int getBufferSize()
  {
    return 0;
  }
  
  public void flushBuffer()
    throws IOException
  {}
  
  public void resetBuffer() {}
  
  public boolean isCommitted()
  {
    return false;
  }
  
  public void reset() {}
  
  public void setLocale(Locale paramLocale) {}
  
  public Locale getLocale()
  {
    return null;
  }
  
  public String getContentType()
  {
    return null;
  }
  
  public void setCharacterEncoding(String paramString) {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.webservices.servletfacade.HttpServletResponseFacade
 * JD-Core Version:    0.7.0.1
 */