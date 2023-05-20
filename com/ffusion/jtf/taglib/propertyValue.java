package com.ffusion.jtf.taglib;

import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public class propertyValue
{
  public static String Evaluate(String paramString, PageContext paramPageContext)
  {
    if (paramString == null) {
      return null;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    j = paramString.indexOf("${", i);
    if (j == -1) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    do
    {
      localStringBuffer.append(paramString.substring(i, j));
      k = paramString.indexOf('}', j + 2);
      if (k == -1)
      {
        a("\tUnmatched ${object.method} pair");
        break;
      }
      localStringBuffer.append(a(paramString.substring(j + 2, k), paramPageContext));
      i = k + 1;
      j = paramString.indexOf("${", i);
    } while (j != -1);
    localStringBuffer.append(paramString.substring(i));
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString, PageContext paramPageContext)
  {
    String str1 = null;
    int i = paramString.indexOf('.');
    if (i == -1) {
      str1 = paramString;
    } else {
      str1 = paramString.substring(0, i);
    }
    Object localObject1 = paramPageContext.findAttribute(str1);
    if (localObject1 == null) {
      localObject1 = paramPageContext.getSession().getAttribute(str1);
    }
    if (localObject1 == null)
    {
      a("\t" + paramString + " object not found " + str1);
      return "";
    }
    if (i != -1)
    {
      Method localMethod = null;
      String str2 = paramString.substring(i + 1);
      String str3 = str2;
      if (str2.length() > 0) {
        str2 = str2.substring(0, 1).toUpperCase() + str2.substring(1);
      }
      Object localObject2;
      if (str2.indexOf(".") != -1)
      {
        localObject2 = Reflection.drillDownToObject(localObject1, str2, str3);
        localObject1 = localObject2[0];
        str2 = (String)localObject2[1];
        str3 = (String)localObject2[2];
      }
      localMethod = Reflection.findGetMethod(localObject1, str2);
      if (localMethod == null) {
        if ((localObject1 instanceof Map))
        {
          localObject2 = (Map)localObject1;
          localObject1 = ((Map)localObject2).get(str3);
        }
        else
        {
          a("\t" + paramString + " : Method not found " + str3);
        }
      }
      if (localMethod != null) {
        try
        {
          localObject1 = localMethod.invoke(localObject1, null);
        }
        catch (Throwable localThrowable)
        {
          String str4 = "";
          if ((localThrowable instanceof InvocationTargetException))
          {
            InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable;
            str4 = localInvocationTargetException.getTargetException().toString();
          }
          else
          {
            str4 = localThrowable.toString();
          }
          a("\t" + paramString + " : Unable to invoke method on " + localObject1.getClass() + '.' + localMethod.getName() + " " + str4);
        }
      }
    }
    if (localObject1 != null) {
      return localObject1.toString() != null ? localObject1.toString() : "";
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
 * Qualified Name:     com.ffusion.jtf.taglib.propertyValue
 * JD-Core Version:    0.7.0.1
 */