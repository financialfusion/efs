package com.ffusion.tasks;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DummyTask
  extends BaseTask
{
  public static final String TASK_ERROR = "task";
  public static final String SERVICE_ERROR = "service";
  private String aQt = null;
  private String aQu = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    try
    {
      this.error = new Integer(this.aQu).intValue();
      if ((this.aQt != null) && ("service".equalsIgnoreCase(this.aQt)))
      {
        str = this.serviceErrorURL;
        return str;
      }
      String str = this.taskErrorURL;
      return str;
    }
    finally
    {
      this.aQt = null;
    }
  }
  
  public void setError(String paramString)
  {
    this.aQu = paramString;
  }
  
  public String getError()
  {
    return this.aQu;
  }
  
  public void setErrorType(String paramString)
  {
    this.aQt = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.DummyTask
 * JD-Core Version:    0.7.0.1
 */