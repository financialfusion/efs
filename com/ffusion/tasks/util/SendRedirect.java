package com.ffusion.tasks.util;

import com.ffusion.jtf.Task;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendRedirect
  implements Task
{
  protected final String CRLF = "\r\n";
  protected String redirectURL;
  protected boolean includeContent;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((this.redirectURL == null) || (this.redirectURL.length() == 0)) {
      throw new IOException("SendRedirect Task Error: RedirectURL not set.");
    }
    paramHttpServletResponse.sendRedirect(this.redirectURL);
    if (this.includeContent)
    {
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.println(jdMethod_new());
      localPrintWriter.flush();
    }
    return "";
  }
  
  public void setRedirectURL(String paramString)
  {
    this.redirectURL = paramString;
  }
  
  public String getRedirectURL()
  {
    return this.redirectURL;
  }
  
  public void setIncludeContent(String paramString)
  {
    this.includeContent = paramString.equalsIgnoreCase("true");
  }
  
  private String jdMethod_new()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
    localStringBuffer.append("<html>");
    localStringBuffer.append("<head>");
    localStringBuffer.append("<title>_</title>");
    localStringBuffer.append("</head><body>");
    localStringBuffer.append("The document you have requested has temporarily moved to:<br>");
    localStringBuffer.append("<a href=\"").append(this.redirectURL).append("\">").append(this.redirectURL).append("</a>");
    localStringBuffer.append("</body></html>");
    localStringBuffer.append("\r\n").append("\r\n");
    return localStringBuffer.toString();
  }
  
  private String jdMethod_int()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
    localStringBuffer.append("<html>");
    localStringBuffer.append("<head>");
    localStringBuffer.append("<title>_</title>");
    localStringBuffer.append("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">");
    localStringBuffer.append("<SCRIPT LANGUAGE=\"JavaScript\">");
    localStringBuffer.append("function setLocation()");
    localStringBuffer.append("{");
    localStringBuffer.append(" document.location = \"").append(this.redirectURL).append("\";");
    localStringBuffer.append("}");
    localStringBuffer.append("</SCRIPT>");
    localStringBuffer.append("</head><body onload=\"setLocation();\"></body></html>");
    localStringBuffer.append("\r\n").append("\r\n");
    return localStringBuffer.toString();
  }
  
  private String jdMethod_for()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
    localStringBuffer.append("<html>");
    localStringBuffer.append("<head>");
    localStringBuffer.append("<title>_</title>");
    localStringBuffer.append("<META HTTP-EQUIV=\"PRAGMA\" CONTENT=\"NO-CACHE\">");
    localStringBuffer.append("</head><body>");
    localStringBuffer.append("<SCRIPT LANGUAGE=\"JavaScript\">");
    localStringBuffer.append("function setLocation()");
    localStringBuffer.append("{");
    localStringBuffer.append(" location.replace(\"").append(this.redirectURL).append("\"); ");
    localStringBuffer.append("} ");
    localStringBuffer.append("window.onload=setLocation; ");
    localStringBuffer.append("</SCRIPT>");
    localStringBuffer.append("</body></html>");
    localStringBuffer.append("\r\n").append("\r\n");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.SendRedirect
 * JD-Core Version:    0.7.0.1
 */