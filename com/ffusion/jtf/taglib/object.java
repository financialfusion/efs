package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class object
  extends BodyTagSupport
  implements ObjectScopes
{
  private String jdField_if;
  private String id;
  private int a = 1;
  
  public object()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_if = null;
    this.id = null;
    this.a = 1;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getName()
  {
    return this.jdField_if;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getScope()
  {
    if (this.a == 1) {
      return "session";
    }
    if (this.a == 2) {
      return "application";
    }
    return "request";
  }
  
  public void setScope(String paramString)
  {
    int i = 1;
    if (paramString.equals("session")) {
      this.a = 1;
    } else if (paramString.equals("application")) {
      this.a = 2;
    } else if (paramString.equals("request")) {
      this.a = 0;
    } else {
      i = 0;
    }
  }
  
  public int doStartTag()
    throws JspException
  {
    return 2;
  }
  
  public void doInitBody()
    throws JspException
  {}
  
  public int doAfterBody()
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
      ServletRequest localServletRequest = this.pageContext.getRequest();
      HttpSession localHttpSession = this.pageContext.getSession();
      ServletContext localServletContext = this.pageContext.getServletContext();
      Class localClass = Class.forName(this.jdField_if);
      Object localObject = localClass.newInstance();
      switch (this.a)
      {
      case 0: 
        localServletRequest.setAttribute(this.id, localObject);
        break;
      case 1: 
        localHttpSession.setAttribute(this.id, localObject);
        break;
      case 2: 
        localServletContext.setAttribute(this.id, localObject);
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
    localStringBuffer.append("\tobject id=" + this.id);
    if (this.jdField_if != null) {
      localStringBuffer.append(" class=" + this.jdField_if);
    }
    localStringBuffer.append(" scope=" + this.a);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.object
 * JD-Core Version:    0.7.0.1
 */