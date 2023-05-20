package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class select
  extends TagSupport
{
  private String jdField_for = null;
  private String jdField_if = null;
  private String a = null;
  private String jdField_do = null;
  
  public select()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_for = null;
    this.jdField_if = null;
    this.a = null;
    this.jdField_do = null;
  }
  
  public void setTexts(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setSelectValues(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public void setArgs(String paramString)
  {
    this.a = paramString;
  }
  
  public void setSelected(String paramString)
  {
    this.jdField_do = paramString;
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
      String str2 = propertyValue.Evaluate(this.jdField_if, this.pageContext);
      String str3 = propertyValue.Evaluate(this.jdField_for, this.pageContext);
      String str4 = propertyValue.Evaluate(this.a, this.pageContext);
      String str5 = propertyValue.Evaluate(this.jdField_do, this.pageContext);
      StringBuffer localStringBuffer = new StringBuffer("<select");
      if (str4 != null)
      {
        localStringBuffer.append(" ");
        localStringBuffer.append(str4);
      }
      localStringBuffer.append(">");
      Collection localCollection1 = null;
      Collection localCollection2 = null;
      localCollection1 = (Collection)this.pageContext.findAttribute(str2);
      if (localCollection1 == null) {
        localCollection1 = (Collection)this.pageContext.getSession().getAttribute(str2);
      }
      if (localCollection1 == null)
      {
        jdField_if("*****ffi:select error. texts collection = null");
      }
      else
      {
        if ((this.jdField_for != null) && (this.jdField_for.length() > 0))
        {
          localCollection2 = (Collection)this.pageContext.findAttribute(str3);
          if (localCollection2 == null) {
            localCollection2 = (Collection)this.pageContext.getSession().getAttribute(str3);
          }
        }
        Object[] arrayOfObject1 = localCollection1.toArray();
        Object[] arrayOfObject2 = localCollection2 == null ? arrayOfObject1 : localCollection2.toArray();
        if (arrayOfObject1.length == arrayOfObject2.length)
        {
          for (int i = 0; i < arrayOfObject1.length; i++)
          {
            localStringBuffer.append("<option ");
            if ((str5 != null) && (str5.equalsIgnoreCase(arrayOfObject2[i].toString()))) {
              localStringBuffer.append("selected ");
            }
            localStringBuffer.append("value='");
            localStringBuffer.append(arrayOfObject2[i]);
            localStringBuffer.append("' />");
            localStringBuffer.append(arrayOfObject1[i]);
            localStringBuffer.append("</option>");
          }
          localStringBuffer.append("</select>");
          str1 = localStringBuffer.toString();
        }
        else
        {
          jdField_if("*****ffi:select error. text and values length are not equal");
        }
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      if (!TagHandlerUtil.a(localThrowable, this.pageContext)) {
        return 5;
      }
    }
    if ((str1 != null) && (str1.length() > 0)) {
      try
      {
        this.pageContext.getOut().write(str1);
      }
      catch (IOException localIOException) {}
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
    localStringBuffer.append("\tselect args=" + this.a);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.select
 * JD-Core Version:    0.7.0.1
 */