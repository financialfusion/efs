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

public class UndoBAIBatch
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ImportedFile localImportedFile = (ImportedFile)localHttpSession.getAttribute("ImportedFile");
    ImportedFiles localImportedFiles = (ImportedFiles)localHttpSession.getAttribute("DependantFileList");
    HashMap localHashMap = new HashMap();
    String str2 = paramHttpServletRequest.getRemoteHost();
    localHashMap.put("HOST_NAME", str2);
    if (localImportedFile == null)
    {
      this.error = 10045;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        DataConsolidator.undoFile(localSecureUser, localImportedFile, localImportedFiles, localHashMap);
        str1 = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.dataconsolidator.UndoBAIBatch
 * JD-Core Version:    0.7.0.1
 */