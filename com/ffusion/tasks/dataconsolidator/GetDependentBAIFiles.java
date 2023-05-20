package com.ffusion.tasks.dataconsolidator;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDependentBAIFiles
  extends BaseTask
  implements Task
{
  protected String datetype;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ImportedFiles localImportedFiles = null;
    ImportedFile localImportedFile = (ImportedFile)localHttpSession.getAttribute("ImportedFile");
    if (localImportedFile == null)
    {
      this.error = 10045;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        HashMap localHashMap = new HashMap();
        localImportedFiles = DataConsolidator.getDependentFiles(localSecureUser, localImportedFile, localHashMap);
        localImportedFiles.setDateFormat(this.datetype);
        localHttpSession.setAttribute("DependantFileList", localImportedFiles);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.dataconsolidator.GetDependentBAIFiles
 * JD-Core Version:    0.7.0.1
 */