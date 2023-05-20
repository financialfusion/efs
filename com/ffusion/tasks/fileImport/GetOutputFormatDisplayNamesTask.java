package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
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

public class GetOutputFormatDisplayNamesTask
  extends BaseTask
{
  private String Pv = "OutputFormatDisplayNames";
  
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
      OutputFormatDisplayNames localOutputFormatDisplayNames = FileImporter.getOutputFormatDisplayNames(localSecureUser, BaseTask.getLocale(localHttpSession, localSecureUser).toString(), localHashMap);
      localHttpSession.setAttribute(this.Pv, localOutputFormatDisplayNames);
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
    this.Pv = paramString;
  }
  
  public String getCollectionName()
  {
    return this.Pv;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.GetOutputFormatDisplayNamesTask
 * JD-Core Version:    0.7.0.1
 */