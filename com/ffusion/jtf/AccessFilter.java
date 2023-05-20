package com.ffusion.jtf;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AccessFilter
  implements Filter
{
  public static final String CONFIG_PARAM_INIT_FILE = "initFile";
  public static final String TAG_NOT_FOUND_URL = "NotFoundURL";
  public static final String TAG_VALID_PREFIX = "ValidPrefix";
  public static final String TAG_INVALID_PREFIX = "InvalidPrefix";
  private String[] jdField_do;
  private String[] a;
  private String jdField_if;
  
  public void init(FilterConfig paramFilterConfig)
    throws ServletException
  {
    String str1 = paramFilterConfig.getInitParameter("initFile");
    if (str1 == null) {
      throw new ServletException("Missing initialization parameter 'initFile'");
    }
    InputStream localInputStream = null;
    String str2 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, str1);
      if (localInputStream == null) {
        throw new ServletException("Unable to find initialization file " + str1);
      }
      str2 = Strings.streamToString(localInputStream, "UTF-8");
    }
    catch (ServletException localServletException)
    {
      throw localServletException;
    }
    catch (Exception localException1)
    {
      throw new ServletException("Unable to read initialization file " + str1, localException1);
    }
    finally
    {
      if (localInputStream != null)
      {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException3) {}
        localInputStream = null;
      }
    }
    XMLTag localXMLTag1 = null;
    try
    {
      localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str2);
      str2 = null;
    }
    catch (Exception localException2)
    {
      throw new ServletException("Error while parsing access configuration file '" + str1 + "'", localException2);
    }
    ArrayList localArrayList1 = localXMLTag1.getContainedTagList();
    Iterator localIterator1 = localArrayList1.iterator();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    String str3;
    while (localIterator1.hasNext())
    {
      XMLTag localXMLTag2 = (XMLTag)localIterator1.next();
      if (localXMLTag2.getTagName().equals("NotFoundURL"))
      {
        if (this.jdField_if != null) {
          throw new ServletException("Multiple tags for 'NotFoundURL' appear in the '" + str1 + "' initialization file, but only one is allowed.");
        }
        this.jdField_if = localXMLTag2.getTagContent();
        DebugLog.log(Level.INFO, "AccessFilter using notFoundURL='" + this.jdField_if + "'");
      }
      else if (localXMLTag2.getTagName().equals("ValidPrefix"))
      {
        str3 = localXMLTag2.getTagContent().trim();
        if (str3.length() == 0) {
          throw new ServletException("The initialization file '" + str1 + "' contains an empty " + "ValidPrefix" + " tag.");
        }
        if (!str3.startsWith("/")) {
          throw new ServletException("The initialization file '" + str1 + "' contains a " + "ValidPrefix" + " tag with the value '" + str3 + "' which does not begin with a leading '/' character. All prefixes must begin with a '/' character.");
        }
        localArrayList2.add(str3);
        DebugLog.log(Level.INFO, "AccessFilter allowing prefix: " + str3);
      }
      else if (localXMLTag2.getTagName().equals("InvalidPrefix"))
      {
        str3 = localXMLTag2.getTagContent().trim();
        if (str3.length() == 0) {
          throw new ServletException("The initialization file '" + str1 + "' contains an empty " + "InvalidPrefix" + " tag.");
        }
        if (!str3.startsWith("/")) {
          throw new ServletException("The initialization file '" + str1 + "' contains an " + "InvalidPrefix" + " tag with the value '" + str3 + "' which does not begin with a leading '/' character. All prefixes must begin with a '/' character.");
        }
        Iterator localIterator2 = localArrayList2.iterator();
        int j = 0;
        while (localIterator2.hasNext())
        {
          String str4 = (String)localIterator2.next();
          if (str3.startsWith(str4))
          {
            j = 1;
            break;
          }
        }
        if (j != 0)
        {
          localArrayList3.add(str3);
          DebugLog.log(Level.INFO, "AccessFilter disallowing prefix: " + str3);
        }
        else
        {
          DebugLog.log(Level.WARNING, "AccessFilter: the initialization file '" + str1 + "' contains an " + "InvalidPrefix" + "tag with the value '" + str3 + "' which does not match one of the allowed prefixes. This tag will have no effect and will be ignored.");
        }
      }
      else
      {
        throw new ServletException("An unknown tag '" + localXMLTag2.getTagName() + "' appears in the '" + str1 + "' initialization file.");
      }
    }
    if (this.jdField_if == null) {
      throw new ServletException("The initialization file '" + str1 + "' must contain exactly one " + "NotFoundURL" + " tag.");
    }
    if (localArrayList2.size() == 0) {
      throw new ServletException("The initialization file '" + str1 + "' must contain at least one " + "ValidPrefix" + " tag.");
    }
    localIterator1 = localArrayList2.iterator();
    int i = 0;
    while (localIterator1.hasNext())
    {
      str3 = (String)localIterator1.next();
      if (this.jdField_if.startsWith(str3))
      {
        i = 1;
        break;
      }
    }
    if (i == 0)
    {
      localArrayList2.add(this.jdField_if);
      DebugLog.log(Level.INFO, "AccessFilter allowing prefix: " + this.jdField_if);
    }
    i = 1;
    localIterator1 = localArrayList3.iterator();
    while (localIterator1.hasNext())
    {
      str3 = (String)localIterator1.next();
      if (this.jdField_if.startsWith(str3))
      {
        i = 0;
        break;
      }
    }
    if (i == 0) {
      throw new ServletException("The initialization file '" + str1 + "' contains a " + "NotFoundURL" + " tag which specifies a URL which is in the " + "InvalidPrefix" + " list. The " + "NotFoundURL" + " URL must be allowed by the filter.");
    }
    this.jdField_do = new String[localArrayList2.size()];
    this.jdField_do = ((String[])localArrayList2.toArray(this.jdField_do));
    this.a = new String[localArrayList3.size()];
    this.a = ((String[])localArrayList3.toArray(this.a));
    DebugLog.log(Level.INFO, "AccessFilter initialized successfully.");
  }
  
  public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
    throws IOException, ServletException
  {
    int i = 0;
    if ((paramServletRequest instanceof HttpServletRequest))
    {
      HttpServletRequest localHttpServletRequest = (HttpServletRequest)paramServletRequest;
      String str = localHttpServletRequest.getRequestURI();
      for (int j = 0; j < this.jdField_do.length; j++) {
        if (str.startsWith(this.jdField_do[j]))
        {
          i = 1;
          break;
        }
      }
      if (i != 0) {
        for (j = 0; j < this.a.length; j++) {
          if (str.startsWith(this.a[j]))
          {
            i = 0;
            break;
          }
        }
      }
      if (i == 0)
      {
        DebugLog.log(Level.WARNING, "AccessFilter denying access to URI '" + str + "'");
        RequestDispatcher localRequestDispatcher = paramServletRequest.getRequestDispatcher(this.jdField_if);
        localRequestDispatcher.forward(paramServletRequest, paramServletResponse);
      }
    }
    else
    {
      i = 1;
    }
    if (i != 0) {
      paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
    }
  }
  
  public void destroy()
  {
    this.a = null;
    this.jdField_do = null;
    this.jdField_if = null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.AccessFilter
 * JD-Core Version:    0.7.0.1
 */