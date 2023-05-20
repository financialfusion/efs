package com.ffusion.jtf.template;

import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PropertyValue
{
  public static String GET_PREFIX = "get";
  
  public static String Evaluate(String paramString, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    if (paramString == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    int k = 0;
    for (;;)
    {
      j = paramString.indexOf("$(", i);
      if (j == -1)
      {
        localStringBuffer.append(paramString.substring(i));
        break;
      }
      localStringBuffer.append(paramString.substring(i, j));
      k = paramString.indexOf(')', j + 2);
      if (k == -1)
      {
        System.out.println("propertyValue.evaluate: Unmatched $(object.method) pair");
        break;
      }
      localStringBuffer.append(a(paramString.substring(j + 2, k), paramHttpServlet, paramHttpServletRequest));
      i = k + 1;
    }
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString, HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = null;
    int i = paramString.indexOf('.');
    if (i == -1) {
      str1 = paramString;
    } else {
      str1 = paramString.substring(0, i);
    }
    Object localObject1 = paramHttpServletRequest.getAttribute(str1);
    if (localObject1 == null) {
      localObject1 = paramHttpServletRequest.getSession().getAttribute(str1);
    }
    Object localObject2;
    if (localObject1 == null)
    {
      localObject2 = paramHttpServlet.getServletContext();
      localObject1 = ((ServletContext)localObject2).getAttribute(str1);
    }
    if (localObject1 == null)
    {
      System.err.println("EvaluateExpression: object not found " + str1);
      return "";
    }
    if (i != -1)
    {
      localObject2 = null;
      String str2 = paramString.substring(i + 1);
      String str3 = str2;
      if (str2.length() > 0) {
        str2 = str2.substring(0, 1).toUpperCase() + str2.substring(1);
      }
      Object localObject3;
      if (str2.indexOf(".") != -1)
      {
        localObject3 = Reflection.drillDownToObject(localObject1, str2, str3);
        localObject1 = localObject3[0];
        str2 = (String)localObject3[1];
        str3 = (String)localObject3[2];
      }
      localObject2 = Reflection.findGetMethod(localObject1, str2);
      if (localObject2 == null) {
        if ((localObject1 instanceof Map))
        {
          localObject3 = (Map)localObject1;
          localObject1 = ((Map)localObject3).get(str3);
        }
        else
        {
          a("\t" + paramString + " : Method not found " + str3);
        }
      }
      if (localObject2 != null) {
        try
        {
          localObject1 = ((Method)localObject2).invoke(localObject1, null);
        }
        catch (Throwable localThrowable)
        {
          a("\t" + paramString + " : Unable to invoke method on " + localObject1.getClass() + '.' + ((Method)localObject2).getName());
        }
      }
    }
    if (localObject1 != null) {
      return localObject1.toString();
    }
    return "";
  }
  
  private static void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.PropertyValue
 * JD-Core Version:    0.7.0.1
 */