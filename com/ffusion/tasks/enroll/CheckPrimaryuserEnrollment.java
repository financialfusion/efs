package com.ffusion.tasks.enroll;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckPrimaryuserEnrollment
  extends BaseTask
  implements com.ffusion.tasks.register.Task, com.ffusion.tasks.applications.Task
{
  private boolean uW = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    if (!this.uW)
    {
      this.error = 7032;
      str = this.taskErrorURL;
    }
    else
    {
      str = this.successURL;
    }
    return str;
  }
  
  public void setPrimaryBusinessEmployeePresent(String paramString)
  {
    this.uW = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getPrimaryBusinessEmployeePresent()
  {
    return new Boolean(this.uW).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.enroll.CheckPrimaryuserEnrollment
 * JD-Core Version:    0.7.0.1
 */