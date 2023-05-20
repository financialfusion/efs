package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRecACHBatch
  extends BaseTask
  implements Task
{
  protected String batchName = "RecACHBatch";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (getRecACHBatch(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getRecACHBatch(HttpSession paramHttpSession)
  {
    ACHBatch localACHBatch = (ACHBatch)paramHttpSession.getAttribute(this.batchName);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.batchName, localACHBatch);
    }
    return this.error == 0;
  }
  
  public final void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this.batchName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetRecACHBatch
 * JD-Core Version:    0.7.0.1
 */