package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMappingDefinitionsTask
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if (localHttpSession.getAttribute("MappingDefinitions") == null)
      {
        HashMap localHashMap = new HashMap();
        MappingDefinitions localMappingDefinitions = FileImporter.getMappingDefinitions(localSecureUser, localHashMap);
        localHttpSession.setAttribute("MappingDefinitions", localMappingDefinitions);
      }
      str = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.GetMappingDefinitionsTask
 * JD-Core Version:    0.7.0.1
 */