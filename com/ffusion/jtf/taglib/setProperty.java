package com.ffusion.jtf.taglib;

import com.ffusion.jtf.UrlEncryptor;
import com.ffusion.util.Reflection;
import com.ffusion.util.logging.DebugLog;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class setProperty
  extends TagSupport
{
  protected String TAG_NAME = "setProperty";
  String a = null;
  String jdField_for = null;
  String jdField_do = null;
  String jdField_if = null;
  
  public setProperty()
  {
    release();
  }
  
  public void release()
  {
    this.a = null;
    this.jdField_for = null;
    this.jdField_do = null;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
  
  public void setProperty(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public void setValue(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public void setURLEncrypt(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    Object[] arrayOfObject = new Object[1];
    try
    {
      jdField_do("");
      String str1 = propertyValue.Evaluate(this.a, this.pageContext);
      String str2 = propertyValue.Evaluate(this.jdField_for, this.pageContext);
      String str3 = propertyValue.Evaluate(this.jdField_do, this.pageContext);
      if (str3 == null) {
        str3 = "";
      }
      Object localObject2;
      Object localObject3;
      String str5;
      if (str2 == null)
      {
        boolean bool1 = Boolean.valueOf(this.jdField_if).booleanValue();
        if (bool1)
        {
          localObject2 = str3;
          int i = str3.indexOf("?");
          if ((i > 0) && (i < str3.length() - 1))
          {
            localObject3 = str3.substring(0, i + 1);
            str5 = str3.substring(i + 1);
            str3 = (String)localObject3 + jdField_if(str5);
          }
        }
        this.pageContext.getSession().setAttribute(str1, str3);
      }
      else
      {
        Object localObject1 = this.pageContext.findAttribute(str1);
        if (localObject1 == null) {
          localObject1 = this.pageContext.getSession().getAttribute(str1);
        }
        if (localObject1 != null)
        {
          localObject2 = null;
          String str4 = str2;
          if (str2.length() > 0) {
            str4 = str2.substring(0, 1).toUpperCase() + str2.substring(1);
          }
          if (str4.indexOf(".") != -1)
          {
            localObject3 = Reflection.drillDownToObject(localObject1, str4, str2);
            localObject1 = localObject3[0];
            str4 = (String)localObject3[1];
            str2 = (String)localObject3[2];
          }
          localObject2 = Reflection.findSetMethod(localObject1, str4);
          if (localObject2 == null) {
            if ((localObject1 instanceof Map))
            {
              localObject3 = (Map)localObject1;
              ((Map)localObject3).put(str2, str3);
            }
            else
            {
              jdField_do("No such method " + localObject1.getClass() + ".set" + this.jdField_for);
            }
          }
          if (localObject2 != null) {
            try
            {
              arrayOfObject[0] = propertyValue.Evaluate(str3, this.pageContext);
              boolean bool2 = Boolean.valueOf(this.jdField_if).booleanValue();
              if ((bool2) && ((arrayOfObject[0] instanceof String)))
              {
                str5 = (String)arrayOfObject[0];
                int j = str5.indexOf("?");
                if ((j > 0) && (j < str5.length() - 1))
                {
                  String str6 = str5.substring(0, j + 1);
                  String str7 = str5.substring(j + 1);
                  arrayOfObject[0] = (str6 + jdField_if(str7));
                }
              }
              ((Method)localObject2).invoke(localObject1, arrayOfObject);
            }
            catch (Throwable localThrowable2)
            {
              if ((localThrowable2 instanceof InvocationTargetException))
              {
                InvocationTargetException localInvocationTargetException = (InvocationTargetException)localThrowable2;
                str5 = localInvocationTargetException.getTargetException().toString();
              }
              else
              {
                str5 = localThrowable2.toString();
              }
              jdField_do("Unable to access method " + localObject1.getClass() + '.' + ((Method)localObject2).getName() + " " + str5);
            }
          }
        }
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
  
  private void jdField_do(String paramString)
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
    localStringBuffer.append("\t").append(getTagName()).append(" name=").append(this.a);
    if (this.jdField_for != null) {
      localStringBuffer.append(" property=").append(this.jdField_for);
    }
    if (this.jdField_do != null) {
      localStringBuffer.append(" value=").append(this.jdField_do);
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
  
  private String jdField_if(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return "";
    }
    UrlEncryptor localUrlEncryptor = (UrlEncryptor)this.pageContext.getSession().getAttribute("URL_ENCRYPTOR");
    return localUrlEncryptor.encrypt(paramString);
  }
  
  protected String getTagName()
  {
    return "setProperty";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.setProperty
 * JD-Core Version:    0.7.0.1
 */