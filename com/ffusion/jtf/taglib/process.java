package com.ffusion.jtf.taglib;

import com.ffusion.jtf.Task;
import com.ffusion.jtf.template.TemplateCache;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class process
  extends TagSupport
{
  String jdField_if;
  String a;
  
  public process()
  {
    release();
  }
  
  public void release()
  {
    this.jdField_if = null;
    this.a = null;
  }
  
  public void setName(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setAssignTo(String paramString)
  {
    this.a = paramString;
  }
  
  public int doEndTag()
    throws JspException
  {
    int i = 6;
    jdField_if("");
    long l = System.currentTimeMillis();
    String str1 = null;
    try
    {
      Object localObject1 = null;
      String str2 = null;
      str1 = propertyValue.Evaluate(this.jdField_if, this.pageContext);
      String str3 = propertyValue.Evaluate(this.a, this.pageContext);
      Object localObject2 = this.pageContext.findAttribute(str1);
      if (localObject2 == null) {
        localObject2 = this.pageContext.getSession().getAttribute(str1);
      }
      if (localObject2 != null)
      {
        this.pageContext.getSession().setAttribute("CurrentTask", localObject2);
        FakeHttpServlet localFakeHttpServlet = new FakeHttpServlet(this.pageContext.getServletContext());
        BaseTask localBaseTask = null;
        if ((localObject2 instanceof Task))
        {
          if ((localObject2 instanceof BaseTask))
          {
            localBaseTask = (BaseTask)localObject2;
            if (localBaseTask.getServiceErrorURL() == null) {
              localBaseTask.setServiceErrorURL("SE");
            }
            if (localBaseTask.getTaskErrorURL() == null) {
              localBaseTask.setServiceErrorURL("TE");
            }
            localObject1 = localBaseTask.process(localFakeHttpServlet, (HttpServletRequest)this.pageContext.getRequest(), (HttpServletResponse)this.pageContext.getResponse());
          }
          else
          {
            Task localTask = (Task)localObject2;
            localObject1 = localTask.process(localFakeHttpServlet, (HttpServletRequest)this.pageContext.getRequest(), (HttpServletResponse)this.pageContext.getResponse());
          }
          PerfLog.log("Task -> " + str1, l, true);
          l = 0L;
          if (str3 != null)
          {
            try
            {
              this.pageContext.setAttribute(str3, localObject1);
            }
            catch (Exception localException) {}
          }
          else if ((localObject1 != null) && (((String)localObject1).length() > 0))
          {
            int j = 0;
            if (((String)localObject1).equals("SE"))
            {
              localObject1 = (String)this.pageContext.getSession().getAttribute("com.ffusion.tasks.BaseTask.taskErrorURL");
              j = 1;
            }
            else if (((String)localObject1).equals("TE"))
            {
              localObject1 = (String)this.pageContext.getSession().getAttribute("com.ffusion.tasks.BaseTask.serviceErrorURL");
              j = 1;
            }
            else if ((localBaseTask != null) && ((((String)localObject1).equals(localBaseTask.getServiceErrorURL())) || (((String)localObject1).equals(localBaseTask.getTaskErrorURL()))))
            {
              j = 1;
            }
            if (localObject1 != null)
            {
              TemplateCache localTemplateCache = (TemplateCache)this.pageContext.getRequest().getAttribute("jtfTemplateCache");
              if (localTemplateCache == null)
              {
                jdField_if("***** Couldn't find template cache in request");
              }
              else
              {
                Object localObject3;
                if (j == 0)
                {
                  if ((localObject1 != null) && (((String)localObject1).indexOf(".jsp") != -1))
                  {
                    localObject3 = (String)this.pageContext.getSession().getAttribute("com.ffusion.jtf.JTF.TaskRedirectPageURL");
                    if (localObject3 != null)
                    {
                      this.pageContext.getSession().setAttribute("com.ffusion.jtf.JTF.TaskNextURL", localObject1);
                      localObject1 = localObject3;
                    }
                  }
                  localObject1 = localTemplateCache.findTemplatePath(this.pageContext.getServletContext(), (String)localObject1);
                }
                if (localObject1 != null) {
                  try
                  {
                    if (j != 0)
                    {
                      str2 = (String)this.pageContext.getSession().getAttribute("com.ffusion.tasks.BaseTask.errorRedirectURL");
                      str2 = localTemplateCache.findTemplatePath(this.pageContext.getServletContext(), str2);
                      this.pageContext.getSession().setAttribute("ERROR_REDIRECT_URL", localObject1);
                      localObject3 = this.pageContext.getServletContext().getRequestDispatcher(str2);
                    }
                    else
                    {
                      localObject3 = this.pageContext.getServletContext().getRequestDispatcher((String)localObject1);
                    }
                    if (localObject3 != null) {
                      ((RequestDispatcher)localObject3).include(this.pageContext.getRequest(), this.pageContext.getResponse());
                    }
                  }
                  catch (Throwable localThrowable2)
                  {
                    a(localThrowable2);
                  }
                }
              }
              this.pageContext.getRequest().setAttribute("AbortingResponse", "true");
              i = 5;
            }
          }
        }
      }
      else
      {
        jdField_if(" : Object not found " + this.jdField_if);
      }
    }
    catch (Throwable localThrowable1)
    {
      a(localThrowable1);
      i = 5;
    }
    finally
    {
      if (l != 0L) {
        PerfLog.log("Task -> " + str1, l, true);
      }
    }
    return i;
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
    localStringBuffer.append("ffi:process ");
    localStringBuffer.append(this.jdField_if);
    if (this.a != null) {
      localStringBuffer.append(" assignTo=" + this.a);
    }
    localStringBuffer.append(paramString);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.process
 * JD-Core Version:    0.7.0.1
 */