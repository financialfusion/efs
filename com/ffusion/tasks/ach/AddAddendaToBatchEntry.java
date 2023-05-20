package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddAddendaToBatchEntry
  extends BaseTask
  implements Task
{
  protected String entryID = null;
  
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
          if (localACHAddendas != null) {
            localACHAddendas.create();
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddAddendaToBatchEntry
 * JD-Core Version:    0.7.0.1
 */