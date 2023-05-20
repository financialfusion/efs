package com.ffusion.jtf.taglib;

import com.ffusion.util.HTMLUtil;
import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class getProperty
  extends TagSupport
{
  String jdField_do;
  String jdField_new = null;
  String jdField_if = null;
  String a = "";
  boolean jdField_int = true;
  boolean jdField_for = false;
  
  public getProperty()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_do = null;
    this.jdField_new = null;
    this.jdField_if = null;
    this.jdField_int = true;
  }
  
  public void setName(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public void setAssignTo(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setProperty(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public void setDefault(String paramString)
  {
    this.a = paramString;
  }
  
  public void setEncode(String paramString)
  {
    this.jdField_int = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setEncodeLeadingSpaces(String paramString)
  {
    this.jdField_for = Boolean.valueOf(paramString).booleanValue();
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    String str1 = null;
    try
    {
      jdField_if("");
      String str2 = null;
      String str3 = propertyValue.Evaluate(this.jdField_do, this.pageContext);
      String str4 = propertyValue.Evaluate(this.jdField_new, this.pageContext);
      str1 = propertyValue.Evaluate(this.jdField_if, this.pageContext);
      Object localObject1 = this.pageContext.findAttribute(str3);
      if (localObject1 == null) {
        localObject1 = this.pageContext.getSession().getAttribute(str3);
      }
      if (localObject1 != null) {
        if (str4 == null)
        {
          str2 = localObject1.toString();
        }
        else
        {
          Method localMethod = null;
          String str5 = str4;
          if (str4.length() > 0) {
            str5 = str4.substring(0, 1).toUpperCase() + str4.substring(1);
          }
          Object localObject2;
          if (str5.indexOf(".") != -1)
          {
            localObject2 = Reflection.drillDownToObject(localObject1, str5, str4);
            localObject1 = localObject2[0];
            str5 = (String)localObject2[1];
            str4 = (String)localObject2[2];
          }
          if (localObject1 != null)
          {
            localMethod = Reflection.findGetMethod(localObject1, str5);
            Object localObject3;
            if (localMethod == null) {
              if ((localObject1 instanceof Map))
              {
                localObject2 = (Map)localObject1;
                localObject3 = ((Map)localObject2).get(str4);
                if (localObject3 != null) {
                  str2 = localObject3.toString();
                } else {
                  jdField_if("No such method " + localObject1.getClass() + ".get" + this.jdField_new);
                }
              }
              else
              {
                jdField_if("No such method " + localObject1.getClass() + ".get" + this.jdField_new);
              }
            }
            if (localMethod != null) {
              try
              {
                localObject2 = localMethod.invoke(localObject1, null);
                if (localObject2 != null) {
                  str2 = localObject2.toString();
                }
              }
              catch (Throwable localThrowable2)
              {
                if ((localThrowable2 instanceof InvocationTargetException))
                {
                  InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable2;
                  localObject3 = localInvocationTargetException.getTargetException().toString();
                }
                else
                {
                  localObject3 = localThrowable2.toString();
                }
                jdField_if("Unable to invoke method on " + localObject1.getClass() + '.' + localMethod.getName() + " " + (String)localObject3);
              }
            }
          }
        }
      }
      try
      {
        if (str2 != null) {
          if (str1 == null)
          {
            if (this.jdField_int) {
              str2 = HTMLUtil.encode(str2);
            }
            if (this.jdField_for) {
              str2 = HTMLUtil.encodeLeadingSpaces(str2);
            }
            this.pageContext.getOut().write(str2);
          }
          else
          {
            this.pageContext.setAttribute(str1, str2);
          }
        }
      }
      catch (Exception localException2) {}
    }
    catch (NullPointerException localNullPointerException)
    {
      if (str1 == null) {
        try
        {
          this.pageContext.getOut().write(this.a);
        }
        catch (Exception localException1) {}
      }
    }
    catch (Throwable localThrowable1)
    {
      a(localThrowable1);
      if (!TagHandlerUtil.a(localThrowable1, this.pageContext)) {
        return 5;
      }
    }
    return 6;
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
    localStringBuffer.append("\tgetProperty name=" + this.jdField_do);
    if (this.jdField_new != null) {
      localStringBuffer.append(" property=" + this.jdField_new);
    }
    if (this.jdField_if != null) {
      localStringBuffer.append(" assignTo=" + this.jdField_if);
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.getProperty
 * JD-Core Version:    0.7.0.1
 */