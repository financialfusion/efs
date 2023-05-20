package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.FileTypeDisplayNames;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListUploadFileDisplayNamesTask
  extends BaseTask
{
  private String Pu = "UploadFileTypesDisplayNames";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = new HashMap();
      FileTypeDisplayNames localFileTypeDisplayNames = FileImporter.getUploadFileTypeDisplayNames(localSecureUser, BaseTask.getLocale(localHttpSession, localSecureUser).toString(), localHashMap);
      localHttpSession.setAttribute(this.Pu, localFileTypeDisplayNames);
      str = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    return str;
  }
  
  public void setCollectionName(String paramString)
  {
    this.Pu = paramString;
  }
  
  public String getCollectionName()
  {
    return this.Pu;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ListUploadFileDisplayNamesTask
 * JD-Core Version:    0.7.0.1
 */