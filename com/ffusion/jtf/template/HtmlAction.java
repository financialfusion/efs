package com.ffusion.jtf.template;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class HtmlAction
  implements Action
{
  private String ap;
  
  public void process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (paramHttpServletRequest.getAttribute("JTF_CONTENT_TYPE") == null) {
      a(paramHttpServletRequest, paramHttpServletResponse);
    }
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.print(this.ap);
  }
  
  private void a(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = (String)localHttpSession.getAttribute("JTF_CONTENT_TYPE");
    if (str != null) {
      paramHttpServletResponse.setContentType(str);
    }
    String[] arrayOfString1 = (String[])localHttpSession.getAttribute("HEADER_NAMES");
    if (arrayOfString1 != null)
    {
      String[] arrayOfString2 = (String[])localHttpSession.getAttribute("HEADER_VALUES");
      for (int i = 0; (i < arrayOfString1.length) && (i < arrayOfString2.length); i++) {
        paramHttpServletResponse.setHeader(arrayOfString1[i], arrayOfString2[i]);
      }
    }
    paramHttpServletRequest.setAttribute("JTF_CONTENT_TYPE", Boolean.TRUE);
  }
  
  public String evaluate(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest)
  {
    return this.ap;
  }
  
  protected int parse(String paramString, int paramInt1, int paramInt2)
  {
    this.ap = paramString.substring(paramInt1, paramInt2);
    return paramInt2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.HtmlAction
 * JD-Core Version:    0.7.0.1
 */