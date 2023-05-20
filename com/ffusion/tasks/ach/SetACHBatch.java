package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHBatch
  extends BaseTask
  implements Task
{
  protected String id;
  protected String batchesName = "ACHBatches";
  protected String entriesName = "ACHEntries";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    ACHBatches localACHBatches = (ACHBatches)localHttpSession.getAttribute(this.batchesName);
    if (localACHBatches != null)
    {
      ACHBatch localACHBatch = localACHBatches.getByID(this.id);
      if (localACHBatch != null)
      {
        localHttpSession.setAttribute("ACHBatch", localACHBatch);
        localHttpSession.setAttribute(this.entriesName, localACHBatch.getACHEntries());
        str = this.successURL;
      }
      else
      {
        this.error = 16002;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 16010;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public final String getBatchesName()
  {
    return this.batchesName;
  }
  
  public final void setEntriesName(String paramString)
  {
    this.entriesName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHBatch
 * JD-Core Version:    0.7.0.1
 */