package com.ffusion.tasks.dataconsolidator;

import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetImportedFile
  extends BaseTask
  implements Task
{
  protected String collectionIndex = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ImportedFile localImportedFile = null;
    ImportedFiles localImportedFiles = (ImportedFiles)localHttpSession.getAttribute("ImportedFileList");
    if (localImportedFiles == null) {
      this.error = 10044;
    } else if (this.collectionIndex == null) {
      this.error = 10046;
    } else {
      try
      {
        localImportedFile = (ImportedFile)localImportedFiles.get(Integer.valueOf(this.collectionIndex).intValue());
        if (localImportedFile != null)
        {
          localHttpSession.setAttribute("ImportedFile", localImportedFile);
          str = this.successURL;
        }
        else
        {
          this.error = 10045;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 10046;
      }
    }
    return str;
  }
  
  public void setImportedFileIndex(String paramString)
  {
    this.collectionIndex = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.dataconsolidator.SetImportedFile
 * JD-Core Version:    0.7.0.1
 */