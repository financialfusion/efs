package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteACHEntryFromBatch
  extends BaseTask
  implements Task
{
  protected String batchName = "ACHBatch";
  protected String entryID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Object localObject;
    if ((this.entryID != null) && (this.entryID.length() > 0))
    {
      localObject = (ACHEntries)localHttpSession.getAttribute("ACHEntries");
      if (localObject == null)
      {
        this.error = 16001;
        str = this.taskErrorURL;
      }
      else
      {
        ((ACHEntries)localObject).remove(((ACHEntries)localObject).getByID(this.entryID));
      }
    }
    else
    {
      if ((this.batchName == null) || (this.batchName.trim().length() == 0)) {
        this.batchName = "ACHBatch";
      }
      localObject = (ACHBatch)localHttpSession.getAttribute(this.batchName);
      if (localObject == null)
      {
        this.error = 16002;
        str = this.taskErrorURL;
      }
      else
      {
        ACHEntry localACHEntry = (ACHEntry)localHttpSession.getAttribute("ACHEntry");
        if (localACHEntry == null)
        {
          this.error = 16001;
          str = this.taskErrorURL;
        }
        else
        {
          ((ACHBatch)localObject).remove(localACHEntry);
        }
      }
    }
    return str;
  }
  
  public final void setEntryID(String paramString)
  {
    this.entryID = paramString;
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
 * Qualified Name:     com.ffusion.tasks.ach.DeleteACHEntryFromBatch
 * JD-Core Version:    0.7.0.1
 */