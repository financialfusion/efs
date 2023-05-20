package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHEntry
  extends BaseTask
  implements Task
{
  protected String achEntryID;
  protected String batchName = "ACHBatch";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if ((this.batchName == null) || (this.batchName.trim().length() == 0)) {
      this.batchName = "ACHBatch";
    }
    ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.batchName);
    if (localACHBatch != null)
    {
      ACHEntry localACHEntry = localACHBatch.getByID(this.achEntryID);
      if (localACHEntry != null)
      {
        localHttpSession.setAttribute("ACHEntry", localACHEntry);
        str = this.successURL;
      }
      else
      {
        this.error = 16001;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.achEntryID = paramString;
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
 * Qualified Name:     com.ffusion.tasks.ach.SetACHEntry
 * JD-Core Version:    0.7.0.1
 */