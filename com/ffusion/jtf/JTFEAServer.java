package com.ffusion.jtf;

import com.ffusion.jtf.template.TemplateAction;
import com.ffusion.jtf.template.TemplateCache;
import com.ffusion.jtf.template.TemplateParseException;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JTFEAServer
  extends JTF
{
  protected void processTemplate(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, HttpSession paramHttpSession, TemplateCache paramTemplateCache, String paramString)
    throws IOException
  {
    TemplateAction localTemplateAction = null;
    if ((paramString.endsWith(".jsp")) || (paramString.endsWith(".css")))
    {
      long l = System.currentTimeMillis();
      String str = null;
      try
      {
        if ((paramString == null) || (paramString.length() == 0)) {
          throw new IllegalArgumentException();
        }
        paramHttpServletRequest.setAttribute("jtfTemplateCache", paramTemplateCache);
        str = paramTemplateCache.findTemplatePath(getServletContext(), paramString);
        if (str != null)
        {
          log("JSP Template -> " + str);
          RequestDispatcher localRequestDispatcher = getServletContext().getRequestDispatcher(str);
          if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") == null) {
            setResponseHeaders(paramHttpServletRequest, paramHttpServletResponse);
          }
          if (localRequestDispatcher != null) {
            localRequestDispatcher.forward(paramHttpServletRequest, paramHttpServletResponse);
          }
        }
        else
        {
          processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
        }
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.SEVERE, "JSP -> " + paramString + " " + localException.getMessage() + " " + localException.toString());
        localException.printStackTrace(System.out);
      }
      finally
      {
        PerfLog.log("JSP Page -> " + str, l, true);
      }
    }
    else
    {
      try
      {
        localTemplateAction = paramTemplateCache.getTemplate(getServletContext(), paramString);
      }
      catch (TemplateParseException localTemplateParseException)
      {
        localTemplateParseException.log(this, paramString);
      }
      if (localTemplateAction == null) {
        processInvalidRequest(paramHttpServletRequest, paramHttpServletResponse, paramHttpSession, paramTemplateCache, paramString);
      } else {
        processTemplateFound(paramHttpServletRequest, paramHttpServletResponse, paramTemplateCache, localTemplateAction, paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.JTFEAServer
 * JD-Core Version:    0.7.0.1
 */