package com.ffusion.jtf.taglib;

import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class map
  extends BodyTagSupport
{
  private Map jdField_int;
  private String jdField_do;
  private String jdField_if;
  private String jdField_for;
  private Iterator a;
  
  public map()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_do = null;
  }
  
  public String getMap()
  {
    return this.jdField_do;
  }
  
  public void setMap(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getKey()
  {
    return this.jdField_if;
  }
  
  public void setKey(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getValue()
  {
    return this.jdField_for;
  }
  
  public void setValue(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    jdField_if("");
    if ((jdField_if()) && (this.a.hasNext()))
    {
      a();
      return 2;
    }
    return 0;
  }
  
  public int doAfterBody()
    throws JspTagException
  {
    BodyContent localBodyContent = getBodyContent();
    if (localBodyContent != null) {
      try
      {
        String str = localBodyContent.getString();
        localBodyContent.getEnclosingWriter().write(str);
        localBodyContent.clear();
      }
      catch (IOException localIOException)
      {
        throw new JspTagException("Error parsing <JSP:list ... in doAfterBody\r\n" + localIOException);
      }
    }
    if (this.a.hasNext())
    {
      a();
      return 2;
    }
    return 0;
  }
  
  public int doEndTag()
    throws JspTagException
  {
    return 6;
  }
  
  private void a()
  {
    ServletRequest localServletRequest = this.pageContext.getRequest();
    try
    {
      if (this.a.hasNext())
      {
        Object localObject = this.a.next();
        localServletRequest.setAttribute(this.jdField_if, localObject);
        localServletRequest.setAttribute(this.jdField_for, this.jdField_int.get(localObject));
      }
      else
      {
        localServletRequest.setAttribute(this.jdField_if, "");
        localServletRequest.setAttribute(this.jdField_for, "");
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
    }
  }
  
  private boolean jdField_if()
    throws JspTagException
  {
    boolean bool = false;
    try
    {
      String str1 = propertyValue.Evaluate(this.jdField_do, this.pageContext);
      int i = str1.indexOf(".");
      if (i != -1)
      {
        String str2 = str1.substring(0, i);
        Object localObject1 = this.pageContext.findAttribute(str2);
        if (localObject1 == null) {
          localObject1 = this.pageContext.getSession().getAttribute(str2);
        }
        if (localObject1 != null)
        {
          Method localMethod = null;
          String str3 = str1.substring(i + 1, i + 2).toUpperCase() + str1.substring(i + 2);
          Object localObject2;
          if (str3.indexOf(".") != -1)
          {
            localObject2 = Reflection.drillDownToObject(localObject1, str3, str3);
            localObject1 = localObject2[0];
            str3 = (String)localObject2[1];
          }
          localMethod = Reflection.findGetMethod(localObject1, str3);
          if (localMethod != null) {
            try
            {
              localObject2 = localMethod.invoke(localObject1, null);
              if (localObject2 != null) {
                this.jdField_int = ((Map)localObject2);
              }
            }
            catch (Throwable localThrowable2)
            {
              String str4;
              if ((localThrowable2 instanceof InvocationTargetException))
              {
                InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable2;
                str4 = localInvocationTargetException.getTargetException().toString();
              }
              else
              {
                str4 = localThrowable2.toString();
              }
              jdField_if("Unable to invoke method on " + localObject1.getClass() + '.' + localMethod.getName() + " " + str4);
            }
          }
        }
      }
      if (this.jdField_int == null) {
        this.jdField_int = ((Map)this.pageContext.findAttribute(str1));
      }
      if (this.jdField_int == null) {
        this.jdField_int = ((Map)this.pageContext.getSession().getAttribute(str1));
      }
      if (this.jdField_int == null)
      {
        jdField_if("***** map = null");
      }
      else
      {
        this.a = this.jdField_int.keySet().iterator();
        bool = true;
      }
    }
    catch (Throwable localThrowable1)
    {
      a(localThrowable1);
    }
    return bool;
  }
  
  private void jdField_if(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, a(paramString));
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing(a("\n\tException: "), paramThrowable);
  }
  
  private String a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\tmap map=");
    localStringBuffer.append(this.jdField_do);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.map
 * JD-Core Version:    0.7.0.1
 */