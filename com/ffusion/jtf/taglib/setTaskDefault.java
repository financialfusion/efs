package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class setTaskDefault
  extends TagSupport
{
  String jdField_do = null;
  String jdField_if = null;
  String a = null;
  String jdField_for = null;
  
  public setTaskDefault()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_do = null;
    this.jdField_if = null;
    this.a = null;
    this.jdField_for = null;
  }
  
  public void setErrorURL(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getErrorURL()
  {
    return this.jdField_do;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getServiceErrorURL()
  {
    return this.jdField_if;
  }
  
  public void setErrorRedirectURL(String paramString)
  {
    this.a = paramString;
  }
  
  public String getErrorRedirectURL()
  {
    return this.a;
  }
  
  public void setTaskRedirectURL(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getTaskRedirectURL()
  {
    return this.jdField_for;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      jdField_if("");
      String str;
      if (this.jdField_if != null)
      {
        str = propertyValue.Evaluate(this.jdField_if, this.pageContext);
        this.pageContext.getSession().setAttribute("com.ffusion.tasks.BaseTask.taskErrorURL", str);
      }
      if (this.jdField_do != null)
      {
        str = propertyValue.Evaluate(this.jdField_do, this.pageContext);
        this.pageContext.getSession().setAttribute("com.ffusion.tasks.BaseTask.serviceErrorURL", str);
      }
      if (this.a != null)
      {
        str = propertyValue.Evaluate(this.a, this.pageContext);
        this.pageContext.getSession().setAttribute("com.ffusion.tasks.BaseTask.errorRedirectURL", str);
      }
      if (this.jdField_for != null)
      {
        str = propertyValue.Evaluate(this.jdField_for, this.pageContext);
        this.pageContext.getSession().setAttribute("com.ffusion.jtf.JTF.TaskRedirectPageURL", str);
      }
    }
    catch (Throwable localThrowable)
    {
      a(localThrowable);
      if (!TagHandlerUtil.a(localThrowable, this.pageContext)) {
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
    localStringBuffer.append("\ttask");
    if (this.jdField_do != null) {
      localStringBuffer.append(" errorURL=" + this.jdField_do);
    }
    if (this.jdField_if != null) {
      localStringBuffer.append(" serviceErrorURL=" + this.jdField_if);
    }
    if (this.a != null) {
      localStringBuffer.append(" errorRedirectURL=" + this.a);
    }
    if (this.jdField_for != null) {
      localStringBuffer.append(" taskRedirectURL=" + this.jdField_for);
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.setTaskDefault
 * JD-Core Version:    0.7.0.1
 */