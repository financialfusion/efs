package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAddendaFromBatchEntry
  extends BaseTask
  implements Task
{
  protected String entryID = null;
  protected String addendaID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.entryID != null) && (this.entryID.length() > 0))
    {
      ACHEntries localACHEntries = (ACHEntries)localHttpSession.getAttribute("ACHEntries");
      if (localACHEntries == null)
      {
        this.error = 16001;
        str = this.taskErrorURL;
      }
      else
      {
        ACHEntry localACHEntry = localACHEntries.getByID(this.entryID);
        if (localACHEntry != null)
        {
          ACHAddendas localACHAddendas = localACHEntry.getAddendas();
          ACHAddenda localACHAddenda = localACHAddendas.getByID(this.addendaID);
          if (localACHAddenda != null) {
            localACHAddendas.remove(localACHAddenda);
          }
        }
      }
    }
    return str;
  }
  
  public final void setEntryID(String paramString)
  {
    this.entryID = paramString;
  }
  
  public final void setAddendaID(String paramString)
  {
    this.addendaID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteAddendaFromBatchEntry
 * JD-Core Version:    0.7.0.1
 */