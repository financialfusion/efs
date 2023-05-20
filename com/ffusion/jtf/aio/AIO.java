package com.ffusion.jtf.aio;

import com.ffusion.util.ResourceUtil;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class AIO
  implements Serializable
{
  public static final String PCL = "pcl";
  public static final String PLATFORMS = "platforms";
  private PlatformClassificationLayer jdField_if;
  private Platforms a;
  
  public AIO(ServletConfig paramServletConfig, ServletContext paramServletContext)
    throws Exception
  {
    String str = paramServletConfig.getInitParameter("pcl");
    if (str == null) {
      str = "pcl.xml";
    }
    URL localURL = a(paramServletContext, str);
    if (localURL == null) {
      throw new Exception("Unable to locate pcl file: " + str);
    }
    try
    {
      this.jdField_if = new PlatformClassificationLayer();
      this.jdField_if.build(localURL);
    }
    catch (Exception localException)
    {
      System.out.println("AIO Exception: " + localException);
      throw localException;
    }
    str = paramServletConfig.getInitParameter("platforms");
    if (str == null) {
      str = "platforms.xml";
    }
    localURL = a(paramServletContext, str);
    if (localURL == null) {
      throw new Exception("Unable to locate platforms file " + str);
    }
    this.a = new Platforms();
    this.a.build(localURL);
  }
  
  private URL a(ServletContext paramServletContext, String paramString)
  {
    URL localURL = null;
    try
    {
      localURL = paramServletContext.getResource(paramString);
    }
    catch (Exception localException)
    {
      System.out.println("AIO Exception:. (" + paramString + ") " + localException);
    }
    if (localURL == null)
    {
      System.out.println("AIO -> Trying ClassLoader.getSystemResource(" + paramString + ")");
      localURL = ResourceUtil.findResourceURL(this, paramString);
    }
    return localURL;
  }
  
  public String[] getGenericPaths(HttpServletRequest paramHttpServletRequest)
  {
    return this.a.getPaths("GENERIC");
  }
  
  public String[] getPaths(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getHeader("User-Agent");
    String str2 = this.jdField_if.getID(str1);
    if (str2 == null) {
      return null;
    }
    return this.a.getPaths(str2);
  }
  
  public String getContentType(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getHeader("User-Agent");
    String str2 = this.jdField_if.getID(str1);
    return this.a.getContentType(str2);
  }
  
  public ArrayList[] getEncodings()
  {
    return this.a.getEncodings();
  }
  
  public String getBrowserEncoding()
  {
    return this.a.getBrowserEncoding();
  }
  
  public String[] getHeaderNames(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getHeader("User-Agent");
    String str2 = this.jdField_if.getID(str1);
    return this.a.getHeaderNames(str2);
  }
  
  public String[] getHeaderValues(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getHeader("User-Agent");
    String str2 = this.jdField_if.getID(str1);
    return this.a.getHeaderValues(str2);
  }
  
  public Locale getLocale(HttpServletRequest paramHttpServletRequest)
  {
    Locale localLocale = null;
    String str1 = paramHttpServletRequest.getHeader("Accept-Language");
    try
    {
      if (str1 != null)
      {
        String str2 = str1.substring(0, 2);
        String str3;
        try
        {
          str3 = str1.substring(3, 5);
        }
        catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
        {
          str3 = "us";
        }
        localLocale = new Locale(str2, str3);
      }
    }
    catch (Exception localException)
    {
      localLocale = Locale.getDefault();
    }
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    return localLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.aio.AIO
 * JD-Core Version:    0.7.0.1
 */