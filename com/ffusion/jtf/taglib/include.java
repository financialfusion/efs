package com.ffusion.jtf.taglib;

import com.ffusion.jtf.template.TemplateCache;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class include
  extends TagSupport
{
  String a = null;
  
  public include()
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
  
  public String getPage()
  {
    return this.a;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 1;
  }
  
  public int doEndTag()
    throws JspException
  {
    a("");
    long l = System.currentTimeMillis();
    String str = propertyValue.Evaluate(this.a, this.pageContext);
    try
    {
      TemplateCache localTemplateCache = (TemplateCache)this.pageContext.getRequest().getAttribute("jtfTemplateCache");
      if (localTemplateCache == null)
      {
        a("***** Couldn't find template cache in request");
      }
      else
      {
        str = localTemplateCache.findTemplatePath(this.pageContext.getServletContext(), str);
        if (str == null)
        {
          a("page not found (page=" + this.a + ")");
        }
        else
        {
          try
          {
            this.pageContext.getOut().flush();
          }
          catch (Throwable localThrowable2) {}
          RequestDispatcher localRequestDispatcher = this.pageContext.getServletContext().getRequestDispatcher(str);
          localRequestDispatcher.include(this.pageContext.getRequest(), this.pageContext.getResponse());
        }
      }
    }
    catch (IOException localIOException)
    {
      a(localIOException);
    }
    catch (Exception localException)
    {
      a(localException);
    }
    catch (Throwable localThrowable1)
    {
      a(new Exception(localThrowable1.toString()));
    }
    PerfLog.log("taglib.include " + this.a, l, true);
    if (this.pageContext.getRequest().getAttribute("AbortingResponse") != null) {
      return 5;
    }
    return 6;
  }
  
  private void a(String paramString)
  {
    if (DebugLog.getLogger().isLoggable(Level.FINEST)) {
      DebugLog.log(Level.FINEST, "include page=" + this.a + " " + paramString);
    }
  }
  
  private void a(Throwable paramThrowable)
  {
    DebugLog.throwing("include page=" + this.a + " \n\tException: ", paramThrowable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.include
 * JD-Core Version:    0.7.0.1
 */