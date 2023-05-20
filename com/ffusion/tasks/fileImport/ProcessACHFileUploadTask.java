package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.fileimporter.fileadapters.ACHAdapter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessACHFileUploadTask
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap == null) {
      return super.getTaskErrorURL();
    }
    String str = (String)localHashMap.get("FileUploadErrorMessage");
    if (str != null) {
      return super.getSuccessURL();
    }
    try
    {
      StringBuffer localStringBuffer = (StringBuffer)localHashMap.get("FileContent");
      ACH.uploadACHFile(localSecureUser, localStringBuffer, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      ACHAdapter.createFMLogRecordForACH(localHashMap, "Failed");
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ProcessACHFileUploadTask
 * JD-Core Version:    0.7.0.1
 */