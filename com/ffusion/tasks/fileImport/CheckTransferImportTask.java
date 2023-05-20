package com.ffusion.tasks.fileImport;

import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckTransferImportTask
  extends BaseTask
{
  private String PM;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = super.getSuccessURL();
    ErrorMessages localErrorMessages = (ErrorMessages)localHttpSession.getAttribute("ImportErrors");
    if (localErrorMessages == null)
    {
      this.error = 23007;
      return super.getTaskErrorURL();
    }
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap == null)
    {
      this.error = 23002;
      return super.getTaskErrorURL();
    }
    if ((!localErrorMessages.operationCanContinue()) || (!localErrorMessages.isEmpty()))
    {
      localErrorMessages.sort();
      str = getImportErrorsURL();
    }
    return str;
  }
  
  public String getImportErrorsURL()
  {
    return this.PM;
  }
  
  public void setImportErrorsURL(String paramString)
  {
    this.PM = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.CheckTransferImportTask
 * JD-Core Version:    0.7.0.1
 */