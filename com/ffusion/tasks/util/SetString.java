package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetString
  extends BaseTask
  implements Task
{
  public static final String SCOPE_SESSION = "session";
  public static final String SCOPE_REQUEST = "request";
  public static final String SCOPE_CONTEXT = "context";
  private String QK;
  private String QI;
  private String QJ = "session";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((this.QK != null) && (!this.QK.equals(""))) {
      if (this.QJ.equalsIgnoreCase("request")) {
        paramHttpServletRequest.setAttribute(this.QK, this.QI);
      } else if (this.QJ.equalsIgnoreCase("context")) {
        paramHttpServlet.getServletContext().setAttribute(this.QK, this.QI);
      } else {
        paramHttpServletRequest.getSession().setAttribute(this.QK, this.QI);
      }
    }
    return this.successURL;
  }
  
  public void setStringName(String paramString)
  {
    this.QK = paramString;
  }
  
  public String getStringName()
  {
    return this.QK;
  }
  
  public void setStringValue(String paramString)
  {
    this.QI = paramString;
  }
  
  public String getStringValue()
  {
    return this.QI;
  }
  
  public void setScope(String paramString)
  {
    this.QJ = paramString;
  }
  
  public String getScope()
  {
    return this.QJ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.SetString
 * JD-Core Version:    0.7.0.1
 */