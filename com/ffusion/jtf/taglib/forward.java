package com.ffusion.jtf.taglib;

import com.ffusion.util.logging.DebugLog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class forward
  extends TagSupport
{
  String a;
  
  public forward()
  {
    release();
  }
  
  public void release()
  {
    this.a = null;
  }
  
  public void setPage(String paramString)
  {
    this.a = paramString;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 0;
  }
  
  public int doEndTag()
    throws JspException
  {
    jdMethod_if("");
    try
    {
      String str1 = propertyValue.Evaluate(this.a, this.pageContext);
      String str2 = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
      if ((str2 != null) && (str2.length() > 0) && (str1.startsWith(str2))) {
        str1 = str1.substring(str2.length());
      }
      RequestDispatcher localRequestDispatcher = this.pageContext.getServletContext().getRequestDispatcher(str1);
      localRequestDispatcher.forward(this.pageContext.getRequest(), this.pageContext.getResponse());
    }
    catch (Exception localException)
    {
      a(localException);
    }
    catch (Throwable localThrowable)
    {
      a(new Exception(localThrowable.toString()));
    }
    return 5;
  }
  
  private void jdMethod_if(String paramString)
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
    localStringBuffer.append("\tforward page=");
    localStringBuffer.append(this.a);
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.forward
 * JD-Core Version:    0.7.0.1
 */